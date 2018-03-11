package es.ucm.fdi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import es.ucm.fdi.events.*;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;

public class Controller {
	private static Event.Builder[] avaliableEvents = {
			new newVehicleEvent.Builder(), new newRoadEvent.Builder(),
			new newJunctionEvent.Builder(),
			new MakeVehicleFaultyEvent.Builder() };

	private TrafficSimulator simulation;
	private InputStream in;
	private int ticks;

	public Controller(InputStream in, OutputStream out, int ticks) {
		this.in = in;
		this.ticks = ticks;
		this.simulation = new TrafficSimulator(out);
	}

	public void run() throws IOException {
		loadEvents();
		simulation.run(ticks);
	}

	public void loadEvents() throws IOException {
		Ini init = new Ini(in);
		List<IniSection> list = init.getSections();

		for (IniSection i : list) {
			boolean found = false;
			for (Event.Builder eventB : avaliableEvents) {
				if (i.getTag().equals(eventB.getTitle())) {
					simulation.addEvent(eventB.parse(i));
					found = true;
				}
			}

			if (!found)
				throw new IllegalArgumentException();
		}
	}
}
