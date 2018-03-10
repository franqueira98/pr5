package es.ucm.fdi.eventbuilders;

import es.ucm.fdi.events.Event;
import es.ucm.fdi.ini.IniSection;

public abstract class EventBuilder {
	protected final String title;
	
	public EventBuilder(String title) {
		this.title = title;
	}
	
	public String getTitle() {return title;}
	
	public abstract Event parse(IniSection ini);
}
