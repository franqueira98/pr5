package es.ucm.fdi.events;

import es.ucm.fdi.simobject.*;

import java.util.*;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.ini.IniSection;

public class newVehicleEvent extends Event {
	private String id;
	private String itinerary;
	private int maxSpeed;

	public newVehicleEvent(int time, String itinerary, int maxSpeed, String id) {
		super(time);
		this.itinerary = itinerary;
		this.maxSpeed = maxSpeed;
		this.id = id;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(RoadMap things) {
		String idJunc = "";
		List<Junction> it = new ArrayList();
		for (int i = 0; i < itinerary.length(); i++) {

			if (itinerary.charAt(i) != ',')
				idJunc += itinerary.charAt(i);
			else {
				it.add(things.getJunction(idJunc));

				idJunc = "";
			}
		}
		it.add(things.getJunction(idJunc));
		Vehicle v=new Vehicle(maxSpeed, it, id);
		v.avanza();
		things.addVehicle(v);

	}

	public static class Builder extends Event.Builder { //tuve que hacerlas estaticas

		public Builder() {
			super("new_vehicle");
		}

		public String getTitle() {
			return title;
		}

		public Event parse(IniSection ini) {
			if (!ini.getTag().equals("new_vehicle")) {
				return null;
			} else {
				String id;
				String itinerary;
				int maxSpeed;
				int time;
				Map<String, String> sec = ini.getKeysMap();
				// habría que hacerlo diferente si queremos lanzar excep
				// si nos ponen los datos en otro orden
				time = Integer.parseInt(sec.get("time"));
				id = sec.get("id");
				maxSpeed = Integer.parseInt(sec.get("max_speed"));
				itinerary = sec.get("itinerary");
				return new newVehicleEvent(time, itinerary, maxSpeed, id);
			}
		}

	}
}
