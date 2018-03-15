package es.ucm.fdi.events;

import java.util.Map;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.exceptions.SimulatorError;
import es.ucm.fdi.simobject.Junction;
import es.ucm.fdi.simobject.Road;

public class NewRoadEvent extends Event {

	protected String id;
	protected int maxSpeed;
	protected int length;
	protected String src;
	protected String dest;

	public NewRoadEvent(int time, String id, int maxSpeed, int length,
			String src, String dest) {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.length = length;
		this.src = src;
		this.dest = dest;
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

		Road r = new Road(id, length, maxSpeed, a, b);
		a.newOutgoing(r);
		b.newIncoming(r);
		things.addRoad(r);
	}

	public static class Builder implements Event.Builder {
		
		public boolean canParse(String title, String type){
			return "new_road".equals(title) && "".equals(type);
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

				return new NewRoadEvent(time, id, maxSpeed, length,
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
