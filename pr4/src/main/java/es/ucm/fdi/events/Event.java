package es.ucm.fdi.events;

import java.util.Map;

import es.ucm.fdi.controller.RoadMap;

public abstract class Event {
	private int time;

	public Event(int time) {
		this.time = time;
	}

	public int getTime() {
		return time;
	}

	public abstract void execute(RoadMap things);

	public static abstract class Builder {
		protected final String title;
		protected final String type;

		public Builder(String title, String type) {
			this.title = title;
			this.type = type;
		}

		public String getTitle() {
			return title;
		}

		public String getType() {
			return type;
		}

		boolean isValidId(String id) {
			return id.matches("[a-zA-Z0-9_]+");
		}

		public abstract Event fill(Map<String, String> map);
	}
}