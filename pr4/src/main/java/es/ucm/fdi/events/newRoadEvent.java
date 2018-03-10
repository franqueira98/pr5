package es.ucm.fdi.events;

import java.util.Map;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simobject.Junction;
import es.ucm.fdi.simobject.Road;

public class newRoadEvent extends Event {

	String id;
	int maxSpeed;
	int length;
	String src, dest;

	public newRoadEvent(int time, String id, int maxSpeed, int length,
			String src, String dest) {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.length = length;
		this.src = src;
		this.dest = dest;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(RoadMap things) {
		Junction a, b;
		a = things.getJunction(src);
		b = things.getJunction(dest);
		Road r = new Road(id, length, maxSpeed, a, b);
		a.insertSaliente(b,r);
		b.insertEntrante(r);
		things.addRoad(r);
	}

	public static class Builder extends Event.Builder {
		public Builder() {
			super("new_road");
		}

		public Event parse(IniSection ini) {
			if (!ini.getTag().equals("new_road")) {
				return null;
			} else {
				String ide;
				int time, maxSpeed, length;
				Map<String, String> sec = ini.getKeysMap();
				time = Integer.parseInt(sec.get("time"));
				ide = sec.get("id");
				maxSpeed = Integer.parseInt(sec.get("max_speed"));
				String ideJunctionSurc, ideJunctionDest;
				ideJunctionSurc = sec.get("src");
				ideJunctionDest = sec.get("dest");
				length = Integer.parseInt(sec.get("length"));

				return new newRoadEvent(time, ide, maxSpeed, length,
						ideJunctionSurc, ideJunctionDest);
			}
		}
	}
}
