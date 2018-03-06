package es.ucm.fdi.events;

import es.ucm.fdi.controller.RoadMap;

public abstract class Event {
	protected int time;
	
	public Event(int time) {
		this.time = time;
	}
	
	public abstract void execute(RoadMap things);
	
	//etc
	
}