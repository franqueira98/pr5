package es.ucm.fdi.controller;

import java.io.InputStream;
import java.io.OutputStream;

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
	
	public void run(){
		
	}
	
	public void reset(){
		
	}
}
