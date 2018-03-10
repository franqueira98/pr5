package es.ucm.fdi.events;

import java.util.Map;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simobject.Junction;

public class newRoadEvent extends Event {

	String id;
	int maxSpeed;
	int length;
	Junction src, dest;

	public newRoadEvent(int time, String id, int maxSpeed, int length,
			Junction src, Junction dest) {
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
		// TODO Auto-generated method stub

	}

	public class Builder extends Event.Builder {
		public Builder() {
			super("newRoadEventBuilder");
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
				// faltaría aquí la conexion con roadMap para conectar la
				// carretera con las Junction.
				length = Integer.parseInt(sec.get("length"));

				return new newRoadEvent(time, ide, maxSpeed, length, j1, j2);// faltaría
																				// pasar
																				// los
																				// id
																				// a
																				// Junction.
			}
		}
	}
}
