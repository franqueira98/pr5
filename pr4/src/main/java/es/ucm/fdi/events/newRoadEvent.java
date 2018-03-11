package es.ucm.fdi.events;

import java.util.Map;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simobject.Junction;
import es.ucm.fdi.simobject.Road;

public class newRoadEvent extends Event {

	private String id;
	private int maxSpeed;
	private int length;
	private String src;
	private String dest;

	public newRoadEvent(int time, String id, int maxSpeed, int length,
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
		Junction a = things.getJunction(src);
		Junction b = things.getJunction(dest);
		Road r = new Road(id, length, maxSpeed, a, b);
		a.insertSaliente(r);
		b.insertEntrante(r);
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
			int time = Integer.parseInt(sec.get("time"));
			String id = sec.get("id");
			String ideJunctionSurc = sec.get("src");
			String ideJunctionDest = sec.get("dest");
			int maxSpeed = Integer.parseInt(sec.get("max_speed"));
			int length = Integer.parseInt(sec.get("length"));

			return new newRoadEvent(time, id, maxSpeed, length,
					ideJunctionSurc, ideJunctionDest);
		}
	}
}
