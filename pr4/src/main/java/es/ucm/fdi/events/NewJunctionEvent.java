package es.ucm.fdi.events;

import java.util.Map;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.exceptions.SimulatorError;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simobject.Junction;

public class NewJunctionEvent extends Event {
	String id;

	public NewJunctionEvent(int time, String id) {
		super(time);
		this.id = id;
	}

	@Override
	public void execute(RoadMap things) {
		Junction saved = things.getJunction(id);
		if (saved != null)
			throw new SimulatorError("Id repeated: " + id);
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
			if (!isValidId(id))
				throw new IllegalArgumentException();

			return new NewJunctionEvent(time, id);
		}
	}
}
