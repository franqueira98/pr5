package es.ucm.fdi.events;

import es.ucm.fdi.simobject.*;

import java.util.*;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.ini.IniSection;

public class newVehicleEvent extends Event {
	private String id;
	private List<Junction> itinerary;
	private int maxSpeed;

	public newVehicleEvent(int time, List<Junction> itinerary, int maxSpeed,
			String id) {
		super(time);
		this.itinerary = itinerary;
		this.maxSpeed = maxSpeed;
		this.id = id;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(RoadMap things) {
		// TODO Auto-generated method stub

	}

	public class Builder extends Event.Builder {

		public Builder() {
			super("new_vehicle");
		}

		public String getTitle() {
			return title;
		}

		// cundia dejar hecho en el parse lo de cambiar el String por la lista
		// sería más logico que dejarlo para el execute.
		public Event parse(IniSection ini) {
			if (!ini.getTag().equals("new_vehicle")) {
				return null;
			} else {
				String id;
				String aux;
				int maxSpeed;
				int time;
				List<Junction> it = new ArrayList();
				Map<String, String> sec = ini.getKeysMap();
				String idJunc = "";
				// habría que hacerlo diferente si queremos lanzar excep
				// si nos ponen los datos en otro orden
				time = Integer.parseInt(sec.get("time"));
				id = sec.get("id");
				maxSpeed = Integer.parseInt(sec.get("max_speed"));
				aux = sec.get("itinerary");
				for (int i = 0; i < aux.length(); i++) {
					// hace falta acceso a roadMap para pasar el Junction a
					// partir del id.
					if (aux.charAt(i) != ',')
						idJunc += aux.charAt(i);
					else {
						// it.add(c)//aqui estaría el id guardado y solo habria
						// que llamar al metodo

						idJunc = "";
					}
				}
				return new newVehicleEvent(time, it, maxSpeed, id);
			}
		}

	}
}
