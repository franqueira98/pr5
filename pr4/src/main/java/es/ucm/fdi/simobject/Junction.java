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
	private Map<Junction, Road> saberSaliente;

	public Junction(String id) {
		super(id);
		saberInc = new HashMap<>();
		entrantes = new ArrayList<>();
		semaforo = 0;
		saberSaliente = new HashMap<>();
	}

	public void newVehicle(Vehicle c) {
		saberInc.get(c.getRoad()).cola.add(c);
	}

	public void newOutgoing(Road r) {
		saberSaliente.put(r.getFinal(), r);
	}

	public void newIncoming(Road r) {
		IncomingRoad ir = new IncomingRoad(r.getId());
		if (entrantes.isEmpty())
			ir.semaforoVerde = true;
		saberInc.put(r, ir);
		entrantes.add(ir);
	}

	public void moveToNextRoad(Vehicle v) {
		Junction nextJunction = v.getProxCruce();
		if (nextJunction != null) {
			Road r = saberSaliente.get(nextJunction);
			r.newVehicle(v);
		} else
			v.arrived();
	}

	public void avanza() {
		if (!entrantes.isEmpty()) {
			IncomingRoad roadGreen = entrantes.get(semaforo);
			if (!roadGreen.cola.isEmpty()) {
				Vehicle lucky = roadGreen.cola.getFirst();
				lucky.getRoad().removeVehicle(lucky);
				roadGreen.cola.pop();
				moveToNextRoad(lucky);
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
		entrantes.forEach(r -> reportJunct.append(r.GeneraReport() + ", "));

		if (entrantes.size() != 0)
			reportJunct.delete(reportJunct.length() - 2, reportJunct.length());

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
			cola.forEach(v -> vehiculosCola.append(v.getId() + ","));
			if (cola.size() != 0)
				vehiculosCola.delete(vehiculosCola.length() - 1,
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
