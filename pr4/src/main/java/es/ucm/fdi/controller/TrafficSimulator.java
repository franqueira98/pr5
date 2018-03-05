package es.ucm.fdi.controller;

import java.io.OutputStream;
import java.util.*;

import es.ucm.fdi.events.Event;
import es.ucm.fdi.util.MultiTreeMap;

public class TrafficSimulator {
	private RoadMap objects;
	private MultiTreeMap<Integer,ArrayList<Event>> events;
	private int clock;
	private OutputStream out;
	
	public TrafficSimulator(OutputStream out) {
		this.objects = new RoadMap();
		this.events = new MultiTreeMap<>();
		this.clock = 0;
		this.out = out;
	}
	
	public void addEvent(Event e){
		
	}
	
	public void run(){
		
	}
	
	public void reset(){
		
	}
	
}
