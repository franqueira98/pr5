package es.ucm.fdi.events;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.ini.IniSection;

public abstract class Event {
	protected int time;

	public Event(int time) {
		this.time = time;
	}

	public int getTime() {
		return time;
	}

	public abstract void execute(RoadMap things);

	public static abstract class Builder {
		protected final String title;

		public Builder(String title) {
			this.title = title;
		}

		public String getTitle() {
			return title;
		}

		public abstract Event parse(IniSection ini);

	}
}