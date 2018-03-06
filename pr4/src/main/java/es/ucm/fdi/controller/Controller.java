package es.ucm.fdi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import es.ucm.fdi.eventbuilders.EventBuilder;
import es.ucm.fdi.eventbuilders.JunctionEventBuilder;
import es.ucm.fdi.eventbuilders.MakeVehicleFaultyEventBuilder;
import es.ucm.fdi.eventbuilders.RoadEventBuilder;
import es.ucm.fdi.eventbuilders.VehicleEventBuilder;
import es.ucm.fdi.events.Event;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;

public class Controller {
	private static EventBuilder[] avaliableEvents = { new VehicleEventBuilder(),
		new RoadEventBuilder(), new JunctionEventBuilder(),
		new MakeVehicleFaultyEventBuilder() };

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

	public void run() throws IOException {
		simulation.run(ticks);
	}

	public void reset() {
		// No se si sirve de algo
	}

	public void loadEvents() throws IOException {
		Ini init = new Ini(in);
		List<IniSection> list = init.getSections();

		for (IniSection i : list) {
			boolean found = false;
			for (EventBuilder eventB : avaliableEvents) {
				if (i.getTag().equals(eventB.getTitle())) {
					simulation.addEvent(eventB.parse(i));
					found = true;
				}
			}

			if(!found) throw new IllegalArgumentException();
		}
	}
	
}
