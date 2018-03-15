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

	public interface Builder {
		
		public abstract boolean canParse(String title, String type);

		default boolean isValidId(String id) {
			return id.matches("[a-zA-Z0-9_]+");
		}
		
		public abstract Event fill(Map<String, String> map);
	}
}