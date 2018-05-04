package es.ucm.fdi.control;

import java.io.OutputStream;
import java.util.*;

import javax.swing.SwingUtilities;

import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.simobject.Junction;
import es.ucm.fdi.model.simobject.Road;
import es.ucm.fdi.model.simobject.SimObject;
import es.ucm.fdi.model.simobject.Vehicle;
import es.ucm.fdi.util.MultiTreeMap;

public class TrafficSimulator {
	private RoadMap objects;
	private MultiTreeMap<Integer, Event> events;
	private int timeCounter;
	private List<Listener> listeners;
	private List<SimObject> filter;
	public TrafficSimulator() {
		listeners= new ArrayList<>();
		filter= new ArrayList<>();
		objects = new RoadMap();
		events = new MultiTreeMap<>();
		timeCounter = 0;
	}
	protected boolean hasEvents(){
		return events.size()!=0;
	}
	protected void setTimeCounter(int timeCounter) {
		this.timeCounter = timeCounter;
	}
	protected void setFilter(List<SimObject> filter){
		this.filter=filter;
	}
	public void addEvent(Event e) {
		if (e.getTime() < timeCounter) {
			throw new IllegalArgumentException("ERROR adding a event.\nWe don't travel back in time!\n");
		}
		events.putValue(e.getTime(), e);
		fireUpdateEvent(EventType.NEWEVENT, null);
	}

	public void run(int numSteps, OutputStream out){
		try{
			int timeLimit = timeCounter + numSteps - 1;
			while (timeCounter <= timeLimit) {
				List<Event> nowEvents = events.get(timeCounter);
				if (nowEvents != null) {
					for (Event e : nowEvents) {
						e.execute(objects);
					}
				}
				for (Road r : objects.getRoads()) {
					r.advance();
				}

				for (Junction j : objects.getJunctions()) {
					j.advance();
				}
				timeCounter++;
				if (out != null){
					generateReport(out);
				}
			}
		} catch(Exception e){
			fireUpdateEvent(EventType.ERROR,"Error running the simulator:\n"+e.getMessage());
		}
		fireUpdateEvent(EventType.ADVANCED, null); // no se si va a ir aquí
	}

	private void addSectionsFor(List<? extends SimObject> it, Ini report) {
		for (SimObject j : it) {
			Map<String, String> map = j.report(timeCounter);
			IniSection section = new IniSection(map.get(""));
			map.remove("");
			map.forEach((k, v) -> section.setValue(k, v));
			report.addsection(section);
		}
	}

	protected void reset() {
		objects.reset();
		events.clear();
		timeCounter = 0;
		fireUpdateEvent(EventType.RESET, null);

	}

	protected void generateReport(OutputStream out){
		try{
			Ini report = new Ini();
			if(filter.isEmpty()){
				addSectionsFor(objects.getJunctions(), report);
				addSectionsFor(objects.getRoads(), report);
				addSectionsFor(objects.getVehicles(), report);
			}else{
				addSectionsFor(filter,report);	
			}
		
			report.store(out);
		}catch(Exception e){
			fireUpdateEvent(EventType.ERROR,"Error saving in the ini:\n"+e.getMessage());
		}
	}

	private void fireUpdateEvent(EventType type, String error) {
		UpdateEvent ue = new UpdateEvent(type);
		for (Listener l : listeners) {
			SwingUtilities.invokeLater(() -> l.update(ue, error));
		}
	}

	public void addSimulatorListener(Listener l) {
		listeners.add(l);
		UpdateEvent ue = new UpdateEvent(EventType.REGISTERED);
		// evita pseudo-recursividad
		SwingUtilities.invokeLater(() -> l.update(ue, null));
	}

	public void removeListener(Listener l) {
		listeners.remove(l);
	}

	public interface Listener {
		void update(UpdateEvent ue, String error);
	}

	public enum EventType {
		REGISTERED, RESET, NEWEVENT, ADVANCED, ERROR;
	}

	// clase interna en el simulador
	public class UpdateEvent {
		private EventType evento;

		public UpdateEvent(EventType evento) {
			this.evento = evento;
		}

		public EventType getEvent() {
			return evento;
		}

		public List<Road> getRoads() {
			return objects.getRoads();
		}

		public RoadMap getRoadMap() {
			return objects;
		}

		public List<Junction> getJunctions() {
			return objects.getJunctions();
		}

		public List<Vehicle> getVehicles() {
			return objects.getVehicles();
		}

		public List<Event> getEvenQueue() {
			return events.valuesList();
		}

		public int getCurrentTime() {
			return (int) System.currentTimeMillis();
		}
	}
}
