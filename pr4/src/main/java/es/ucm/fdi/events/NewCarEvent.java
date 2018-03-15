package es.ucm.fdi.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.exceptions.SimulatorError;
import es.ucm.fdi.simobject.Car;
import es.ucm.fdi.simobject.Junction;
import es.ucm.fdi.simobject.Vehicle;

public class NewCarEvent extends NewVehicleEvent {

	protected int resistanceKm;
	protected double faultProbability;
	protected int maxFaultDuration;
	protected long seed;

	public NewCarEvent(int time, String id, int maxSpeed,
			String[] junctions, int resistanceKm, double faultProbability,
			int maxFaultDuration, long seed) {
		super(time, id, maxSpeed, junctions);
		this.resistanceKm = resistanceKm;
		this.faultProbability = faultProbability;
		this.maxFaultDuration = maxFaultDuration;
		this.seed = seed;
	}


	@Override
	public void execute(RoadMap things) {
		if (things.getObject(id) != null)
			throw new SimulatorError("Ups, " + id + " already exists");

		List<Junction> it = new ArrayList<>();
		for (String s : junctions) {
			Junction step = things.getJunction(s);
			if (step == null)
				throw new SimulatorError("A vehicle goes over ghost junctions");
			it.add(step);
		}
		
		//Esto ya no es comun:
		Vehicle v = new Car(maxSpeed, it, id, "car", resistanceKm, faultProbability, maxFaultDuration, seed);
		//Esto vuelve a ser comun:
		
		things.addVehicle(v);
		things.getJunction(junctions[0]).moveToNextRoad(v);
	}

	public static class Builder implements Event.Builder {
		
		public boolean canParse(String title, String type){
			return "new_vehicle".equals(title) && "car".equals(type);
		}

		public Event fill(Map<String, String> map) {
			try {
				String id = map.get("id");
				if (!isValidId(id))
					throw new IllegalArgumentException("Invalid id");

				int time = 0;
				if (map.containsKey("time"))
					time = Integer.parseInt(map.get("time"));
				if (time < 0)
					throw new IllegalArgumentException("Negative time");

				if (!map.containsKey("max_speed"))
					throw new IllegalArgumentException("Missing max_speed");
				int maxSpeed = Integer.parseInt(map.get("max_speed"));
				if (maxSpeed <= 0)
					throw new IllegalArgumentException("No positive max speed");

				if (!map.containsKey("itinerary"))
					throw new IllegalArgumentException("Missing itinerary");
				String itinerary = map.get("itinerary");
				String[] junctions = itinerary.split(",");
				if (junctions.length < 2)
					throw new SimulatorError("Missing destination");
				
				//Hasta aqui es comun
				int resistanceKm = Integer.parseInt(map.get("resistance"));
				if (resistanceKm <= 0)
					throw new IllegalArgumentException("No positive resistance");
				
				double faultProbability = Double.parseDouble(map.get("fault_probability"));
				if (faultProbability < 0)
					throw new IllegalArgumentException("Negative fault_probability");
				
				int maxFaultDuration = Integer.parseInt(map.get("max_fault_duration"));
				if (maxFaultDuration <= 0)
					throw new IllegalArgumentException("No positive max_fault_duration");
				
				long seed = System.currentTimeMillis();
				if(map.containsKey(seed))
					seed = Long.parseLong(map.get("seed"));
				if (seed <= 0)
					throw new IllegalArgumentException("No positive seed");
				

				return new NewCarEvent(time, id, maxSpeed, junctions, resistanceKm, faultProbability, maxFaultDuration, seed);
			} catch (IllegalArgumentException e) {
				throw e;
			} catch (Exception e) {
				throw new IllegalArgumentException(
						"Incorrect arguments for new_vehicle");
			}
		}
	}
}
