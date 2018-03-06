package es.ucm.fdi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import es.ucm.fdi.eventbuilders.EventBuilderParser;
import es.ucm.fdi.events.Event;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;

public class Controller {
	private TrafficSimulator simulation;
	private InputStream in;
	private OutputStream out;
	private int ticks;
	
	public Controller(TrafficSimulator simulation, InputStream in,
			OutputStream out, int ticks) {
		this.simulation = simulation;
		this.in = in;
		this.out = out;
		this.ticks = ticks;
	}
	
	public void run() throws IOException{
		loadEvents();
		//cargar elementos de eventos de este tiempo
	}
	
	public void reset(){
		//No se si sirve de algo
	}
	
	public void loadEvents() throws IOException {
		Ini init = new Ini(in);
		List<IniSection> list = init.getSections();
		for(IniSection i : list){
			Event event = EventBuilderParser.parseBuilder(i).parse(i);
			simulation.addEvent(event);
		}
	}
	
}
