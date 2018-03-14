package es.ucm.fdi.events;

import es.ucm.fdi.simobject.*;

import java.util.*;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.exceptions.SimulatorError;
import es.ucm.fdi.ini.IniSection;

public class NewVehicleEvent extends Event {
	private String id;
	private int maxSpeed;
	private String itinerary;

	public NewVehicleEvent(int time, String id, int maxSpeed, String itinerary) {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.itinerary = itinerary;
	}

	@Override
	public void execute(RoadMap things) {
		if (things.getObject(id) != null)
			throw new SimulatorError("Ups, " + id + " already exists");

		List<Junction> it = new ArrayList<>();
		String[] junctions = itinerary.split(",");
		if (junctions.length < 2)
			throw new SimulatorError("Missing destination");
		for (String s : junctions) {
			Junction step = things.getJunction(s);
			if (step == null)
				throw new SimulatorError("A vehicle goes over ghost junctions");
			it.add(step);
		}

		if (maxSpeed <= 0)
			throw new SimulatorError("Destination is moving away");

		Vehicle v = new Vehicle(maxSpeed, it, id);
		things.addVehicle(v);
		things.getJunction(junctions[0]).moveToNextRoad(v);
	}

	public static class Builder extends Event.Builder {

		public Builder() {
			super("new_vehicle");
		}

		public Event parse(IniSection ini) {
			if (!ini.getTag().equals("new_vehicle"))
				return null;

			try {
				Map<String, String> sec = ini.getKeysMap();
				String id = sec.get("id");
				if (!isValidId(id))
					throw new IllegalArgumentException("Invalid id");

				int time = 0;
				if (sec.containsKey("time"))
					time = Integer.parseInt(sec.get("time"));

				if (!(sec.containsKey("max_speed") && sec
						.containsKey("itinerary")))
					throw new Exception();
				int maxSpeed = Integer.parseInt(sec.get("max_speed"));
				String itinerary = sec.get("itinerary");

				return new NewVehicleEvent(time, id, maxSpeed, itinerary);
			} catch (IllegalArgumentException e) {
				throw e;
			} catch (Exception e) {
				throw new IllegalArgumentException(
						"Incorrect arguments for new_vehicle");
			}
		}
	}
}
