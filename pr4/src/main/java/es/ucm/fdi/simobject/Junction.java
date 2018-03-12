package es.ucm.fdi.simobject;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Junction extends SimObject {
	private Map<Road, IncomingRoad> saberInc;
	private List<IncomingRoad> entrantes;
	private int semaforo;
	private List<Road> salientes;
	private Map<Junction, Road> saberSaliente;

	public Junction(String id) {
		super(id);
		saberInc = new HashMap<>();
		entrantes = new ArrayList<>();
		semaforo = 0;
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
		if (entrantes.isEmpty())
			s.semaforoVerde = true;
		saberInc.put(r, s);
		entrantes.add(s);

	}

	public void avanza() {
		if (!entrantes.isEmpty()) {
			if (!entrantes.get(semaforo).cola.isEmpty()) {
				Vehicle vSaliente = entrantes.get(semaforo).cola.getFirst();
				Road rSaliente = saberSaliente.get(vSaliente.getProxCruce());
				rSaliente.entraVehiculo(vSaliente);
				vSaliente.moverASiguienteCarretera(rSaliente);
				entrantes.get(semaforo).cola.pop();
			}
			entrantes.get(semaforo).semaforoVerde = false;
			semaforo++;
			if (semaforo == entrantes.size())
				semaforo = 0;
			entrantes.get(semaforo).semaforoVerde = true;
		}
	}

	protected void fillReportDetails(Map<String, String> out) {
		StringBuilder reportJunct = new StringBuilder();
		entrantes.forEach(r -> reportJunct.append(r.GeneraReport() + " , "));

		if (entrantes.size() != 0)
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
