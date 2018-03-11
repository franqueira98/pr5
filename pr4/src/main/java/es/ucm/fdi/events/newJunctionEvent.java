package es.ucm.fdi.events;

import java.util.Map;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simobject.Junction;

public class newJunctionEvent extends Event {
	String id;

	public newJunctionEvent(int time, String id) {
		super(time);
		this.id = id;
	}

	@Override
	public void execute(RoadMap things) {
		things.addJunction(new Junction(id));
	}

	public static class Builder extends Event.Builder {

		public Builder() {
			super("new_junction");
		}

		public Event parse(IniSection ini) {
			if (!ini.getTag().equals("new_junction"))
				return null;

			Map<String, String> sec = ini.getKeysMap();
			int time = Integer.parseInt(sec.get("time"));
			String id = sec.get("id");
			
			return new newJunctionEvent(time, id);
		}
	}
}
