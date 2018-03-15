package es.ucm.fdi.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.exceptions.SimulatorError;
import es.ucm.fdi.simobject.Junction;
import es.ucm.fdi.simobject.Vehicle;

public class NewVehicleEvent extends Event {

	protected String id;
	protected int maxSpeed;
	protected String[] junctions;

	public NewVehicleEvent(int time, String id, int maxSpeed, String[] junctions) {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.junctions = junctions;
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

		Vehicle v = new Vehicle(maxSpeed, it, id);
		things.addVehicle(v);
		things.getJunction(junctions[0]).moveToNextRoad(v);
	}

	public static class Builder implements Event.Builder {
		
		public boolean canParse(String title, String type){
			return "new_vehicle".equals(title) && "".equals(type);
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

				return new NewVehicleEvent(time, id, maxSpeed, junctions);
			} catch (IllegalArgumentException e) {
				throw e;
			} catch (Exception e) {
				throw new IllegalArgumentException(
						"Incorrect arguments for new_vehicle");
			}
		}
	}
}
