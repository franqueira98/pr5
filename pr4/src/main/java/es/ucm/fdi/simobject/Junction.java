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
	private List<Road> salientes;

	public Junction(String id) {
		super(id);
		cola = new LinkedHashMap<>(); // porque Linked?
		semaforo = new ArrayList<>();
		salientes = new ArrayList<>();
	}

	// hay que ver como gestionamos la creación de Roads e Incoming Roads.
	public void entraVehiculo(Vehicle c) {
		cola.get(c.getRoad()).cola.add(c);

	}

	public void insertarSalida(Road salida) {
		salientes.add(salida);
	}

	public void avanza() { // porque tendría puesto con el int lon?
		Vehicle aux;
		boolean a = true;
		int i;
		for (i = 0; i < semaforo.size() && a; i++) {
			if (semaforo.get(i).semaforoVerde) {
				aux = semaforo.get(i).cola.getFirst();
				for (int j = 0; j < salientes.size() && a; j++) { // porque no
																	// iba a
																	// poder
																	// moverse?

					if (salientes.get(j).getFinal() == aux.nextJunction()) {// caso
																			// de
																			// la
																			// carretera
																			// correcta
						aux.moverASiguienteCarretera(salientes.get(j));
						semaforo.get(i).cola.poll();// n ose si pop vale
						a = false;
					}
				}
				semaforo.get(i).semaforoVerde = false;

			}

		}
		if (i == semaforo.size())
			i = 0;

		semaforo.get(i).semaforoVerde = true;

	}

	protected void fillReportDetails(Map<String, String> out) {
		// falta por implementar

	}

	protected String getReportHeader() {

		return "[junction_report]";
	}

	// hay que ver como poner semaforo a verde solo de la primera carretera.
	private class IncomingRoad {
		// como se el identificador de esta carretera para el report
		private ArrayDeque<Vehicle> cola;
		private String ide; // entender porque no hace falta
		private boolean semaforoVerde;
		// quite método de añadir y quitar el ultimo

	}
}
