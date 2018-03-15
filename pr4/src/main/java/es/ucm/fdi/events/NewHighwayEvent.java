package es.ucm.fdi.events;

import java.util.Map;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.exceptions.SimulatorError;
import es.ucm.fdi.simobject.Highway;
import es.ucm.fdi.simobject.Junction;
import es.ucm.fdi.simobject.Road;

public class NewHighwayEvent extends NewRoadEvent {

	protected int lanes;

	public NewHighwayEvent(int time, String id, int maxSpeed, int length,
			String src, String dest, int lanes) {
		super(time, id, maxSpeed, length, src, dest);
		this.lanes = lanes;
	}

	@Override
	public void execute(RoadMap things) {
		if (things.getObject(id) != null)
			throw new SimulatorError("Ups, " + id + " already exists");

		Junction a = things.getJunction(src);
		Junction b = things.getJunction(dest);
		if (a == null || b == null)
			throw new SimulatorError("Roads are not rainbows!: " + id + "=("
					+ src + "," + dest + ")");

		// Hasta aqui es comun
		Road r = new Highway(id, length, maxSpeed, a, b, "lanes", lanes);
		// Esto vuelve a ser comun

		a.newOutgoing(r);
		b.newIncoming(r);
		things.addRoad(r);
	}

	public static class Builder extends Event.Builder {
		
		public boolean canParse(String title, String type){
			return "new_road".equals(title) && "lanes".equals(type);
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

				if (!map.containsKey("src"))
					throw new Exception();
				String ideJunctionSurc = map.get("src");
				if (!map.containsKey("dest"))
					throw new Exception();
				String ideJunctionDest = map.get("dest");

				int maxSpeed = Integer.parseInt(map.get("max_speed"));
				if (maxSpeed <= 0)
					throw new IllegalArgumentException("No positive max speed");

				int length = Integer.parseInt(map.get("length"));
				if (length <= 0)
					throw new IllegalArgumentException("No positive length");

				// A partir de aqui cambia
				int lanes = Integer.parseInt(map.get("lanes"));
				if (lanes <= 0)
					throw new IllegalArgumentException("No positive lanes");

				return new NewHighwayEvent(time, id, maxSpeed, length,
						ideJunctionSurc, ideJunctionDest, lanes);
			} catch (IllegalArgumentException e) {
				throw e;
			} catch (Exception e) {
				throw new IllegalArgumentException(
						"Incorrect arguments for new_road");
			}
		}
	}
}
