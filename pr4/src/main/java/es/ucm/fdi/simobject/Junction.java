package es.ucm.fdi.simobject;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Junction extends SimulationObject {
	private Map<Road, IncomingRoad> cola;
	private List<IncomingRoad> semaforo;

	public Junction(String id) {
		super(id);
		cola = new LinkedHashMap<>();
		semaforo = new ArrayList<>();
	}

	// hay que ver como gestionamos la creación de Roads e Incoming Roads.
	public void entraVehiculo(Vehicle c) {
		cola.get(c.getRoad()).cola.add(c);
		
	}

	public void avanza(int lon) {

	}

	protected void fillReportDetails(Map<String, String> out) {
		// falta por implementar

	}

	protected String getReportHeader() {

		return "junction_report";
	}

	private class IncomingRoad {
		// como se el identificador de esta carretera para el report
		private ArrayDeque<Vehicle> cola;
		private String ide; // entender porque no hace falta
		private boolean semaforoVerde;
		// quite método de añadir y quitar el ultimo

	}
}
