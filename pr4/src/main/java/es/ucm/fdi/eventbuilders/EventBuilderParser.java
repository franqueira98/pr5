package es.ucm.fdi.eventbuilders;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import es.ucm.fdi.ini.IniSection;

public class EventBuilderParser {
	private static EventBuilder[] avaliableEvents = {new VehicleEventBuilder(), new RoadEventBuilder(), new JunctionEventBuilder(), new MakeVehicleFaultyEventBuilder()};
	
	public static EventBuilder parseBuilder(IniSection init){
		for(EventBuilder event : avaliableEvents){
			if(init.getTag().equals(event.getTitle())) return event;
		}
		throw new IllegalArgumentException();
	}
	/*
	private static Map<String, EventBuilder> map;
	static {
		Map<String, EventBuilder> mapa = new HashMap<>();
		for(EventBuilder event : avaliableEvents){
			mapa.put(event.getTitle(), event);
		}
		map = Collections.unmodifiableMap(mapa);
	}
	
	public static EventBuilder parseBuilder(IniSection init){
		return map.get(init.getTag());
	}*/

}
