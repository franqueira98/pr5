package es.ucm.fdi.events;

import es.ucm.fdi.simobject.*;

import java.util.*;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.ini.IniSection;

public class newVehicleEvent extends Event {
	private String id;
	private int maxSpeed;
	private String itinerary;

	public newVehicleEvent(int time, String id, int maxSpeed, String itinerary) {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.itinerary = itinerary;
	}

	@Override
	public void execute(RoadMap things) {
		List<Junction> it = new ArrayList<>();
		String[] junctions = itinerary.split(",");
		for(String s : junctions) it.add(things.getJunction(s));
		
		Vehicle v = new Vehicle(maxSpeed, it, id);
		v.avanza();
		things.addVehicle(v);

	}

	public static class Builder extends Event.Builder {

		public Builder() {
			super("new_vehicle");
		}
		
		public Event parse(IniSection ini) {
			if (!ini.getTag().equals("new_vehicle")) return null;

			Map<String, String> sec = ini.getKeysMap();
			int time = Integer.parseInt(sec.get("time"));
			String id = sec.get("id");
			int maxSpeed = Integer.parseInt(sec.get("max_speed"));
			String itinerary = sec.get("itinerary");
			return new newVehicleEvent(time, id, maxSpeed, itinerary);
		}
	}
}
