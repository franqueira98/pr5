package es.ucm.fdi.events;

import java.util.Map;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.exceptions.SimulatorError;
import es.ucm.fdi.simobject.Dirt;
import es.ucm.fdi.simobject.Junction;
import es.ucm.fdi.simobject.Road;

public class NewDirtEvent extends NewRoadEvent {

	public NewDirtEvent(int time, String id, int maxSpeed, int length,
			String src, String dest) {
		super(time, id, maxSpeed, length, src, dest);
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
		Road r = new Dirt(id, length, maxSpeed, a, b, "dirt");
		// Esto vuelve a ser comun

		a.newOutgoing(r);
		b.newIncoming(r);
		things.addRoad(r);
	}

	public static class Builder implements Event.Builder {
		
		public boolean canParse(String title, String type){
			return "new_road".equals(title) && "dirt".equals(type);
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
				return new NewDirtEvent(time, id, maxSpeed, length,
						ideJunctionSurc, ideJunctionDest);
			} catch (IllegalArgumentException e) {
				throw e;
			} catch (Exception e) {
				throw new IllegalArgumentException(
						"Incorrect arguments for new_road");
			}
		}
	}
}
