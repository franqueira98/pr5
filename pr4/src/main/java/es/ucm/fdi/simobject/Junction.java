package es.ucm.fdi.simobject;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Junction extends SimObject {
	private Map<Road, IncomingRoad> saberInc;
	private List<IncomingRoad> semaforo;
	private List<Road> salientes;
	private Map<Junction, Road> saberSaliente;

	public Junction(String id) {
		super(id);
		saberInc = new HashMap<>(); // el profe puso Linked
		semaforo = new ArrayList<>();
		salientes = new ArrayList<>();
		saberSaliente = new HashMap<>();
	}

	public void entraVehiculo(Vehicle c) {
		saberInc.get(c.getRoad()).cola.add(c);
	}

	public void primerCruce(Vehicle v) {
		Road r = saberSaliente.get(v.getProxCruce());
		r.entraVehiculo(v);
		v.moverASiguienteCarretera(r);
	}

	public void insertSaliente(Road r) {
		salientes.add(r);
		saberSaliente.put(r.getFinal(), r);
	}

	public void insertEntrante(Road r) {
		IncomingRoad s = new IncomingRoad(r.getId());
		if (semaforo.isEmpty())
			s.semaforoVerde = true;
		saberInc.put(r, s);
		semaforo.add(s);

	}

	public void avanza() {
		if (!semaforo.isEmpty()) {
			boolean found = false;
			int i;
			for (i = 0; i < semaforo.size() && !found; i++) {
				if (semaforo.get(i).semaforoVerde) {
					if (!semaforo.get(i).cola.isEmpty()) {
						Vehicle aux = semaforo.get(i).cola.getFirst();
						Road saliente = saberSaliente.get(aux.getProxCruce());
						saliente.entraVehiculo(aux);
						aux.moverASiguienteCarretera(saliente);
						semaforo.get(i).cola.pop();
					}
					semaforo.get(i).semaforoVerde = false;
					found = true;
				}
			}
			if (i == semaforo.size())
				i = 0;
			semaforo.get(i).semaforoVerde = true;
		}
	}

	protected void fillReportDetails(Map<String, String> out) {
		StringBuilder reportJunct = new StringBuilder();
		semaforo.forEach(r -> reportJunct.append(r.GeneraReport() + " , "));

		if (semaforo.size() != 0)
			reportJunct.delete(reportJunct.length() - 3, reportJunct.length());

		out.put("queues", reportJunct.toString());
	}

	protected String getReportHeader() {
		return "junction_report";
	}

	private class IncomingRoad {
		private ArrayDeque<Vehicle> cola;
		private String id;
		private boolean semaforoVerde;

		public IncomingRoad(String r) {
			cola = new ArrayDeque<>();
			id = r;
			semaforoVerde = false;
		}

		protected String GeneraReport() {
			StringBuilder vehiculosCola = new StringBuilder();
			cola.forEach(v -> vehiculosCola.append(v.getId() + ", "));
			if (cola.size() != 0)
				vehiculosCola.delete(vehiculosCola.length() - 2,
						vehiculosCola.length());

			StringBuilder r = new StringBuilder();
			r.append("(" + id + ", ");
			if (semaforoVerde)
				r.append("green, ");
			else
				r.append("red, ");
			r.append("[" + vehiculosCola + "])");

			return r.toString();
		}
	}
}
