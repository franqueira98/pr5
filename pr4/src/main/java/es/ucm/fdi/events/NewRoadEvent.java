package es.ucm.fdi.events;

import java.util.Map;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.exceptions.SimulatorError;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simobject.Junction;
import es.ucm.fdi.simobject.Road;

public class NewRoadEvent extends Event {

	private String id;
	private int maxSpeed;
	private int length;
	private String src;
	private String dest;

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
		Road saved = things.getRoad(id);
		if (saved != null)
			throw new SimulatorError("Id repeated: " + id);
		
		Junction a = things.getJunction(src);
		Junction b = things.getJunction(dest);
		Road r = new Road(id, length, maxSpeed, a, b);
		a.newOutgoing(r);
		b.newIncoming(r);
		things.addRoad(r);
	}

	public static class Builder extends Event.Builder {
		public Builder() {
			super("new_road");
		}

		public Event parse(IniSection ini) {
			if (!ini.getTag().equals("new_road"))
				return null;

			Map<String, String> sec = ini.getKeysMap();
			String id = sec.get("id");
			if (!isValidId(id))
				throw new IllegalArgumentException();
			int time = Integer.parseInt(sec.get("time"));
			String ideJunctionSurc = sec.get("src");
			String ideJunctionDest = sec.get("dest");
			int maxSpeed = Integer.parseInt(sec.get("max_speed"));
			int length = Integer.parseInt(sec.get("length"));

			return new NewRoadEvent(time, id, maxSpeed, length,
					ideJunctionSurc, ideJunctionDest);
		}
	}
}
