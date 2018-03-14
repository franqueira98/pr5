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
		if (things.getObject(id) != null)
			throw new SimulatorError("Ups, " + id + " already exists");
		things.addJunction(new Junction(id));
	}

	public static class Builder extends Event.Builder {

		public Builder() {
			super("new_junction");
		}

		public Event parse(IniSection ini) {
			if (!ini.getTag().equals("new_junction"))
				return null;
			try {
				Map<String, String> sec = ini.getKeysMap();
				String id = sec.get("id");
				if (!isValidId(id))
					throw new IllegalArgumentException("Invalid id");

				int time = 0;
				if (sec.containsKey("time"))
					time = Integer.parseInt(sec.get("time"));

				return new NewJunctionEvent(time, id);
			} catch (IllegalArgumentException e) {
				throw e;
			} catch (Exception e) {
				throw new IllegalArgumentException(
						"Incorrect arguments for new_junction");
			}
		}
	}
}
