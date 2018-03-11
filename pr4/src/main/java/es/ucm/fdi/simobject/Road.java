package es.ucm.fdi.simobject;

import java.util.Map;

import es.ucm.fdi.util.MultiTreeMap;

public class Road extends SimObject {
	private int longitud;
	private int maxVel;
	private Junction start;
	private Junction end;
	private MultiTreeMap<Integer, Vehicle> vehicles;

	// necesita equals y hashcode
	public Road(String ide, int lon, int maxv, Junction princ, Junction fin) {
		super(ide);
		longitud = lon;
		maxVel = maxv;
		vehicles = new MultiTreeMap<>((a, b) -> a - b); // falta ver como
														// recorrerlos para el
														// metodo avanza
		start = princ;
		end = fin;
	}

	public Junction getPrincipio() {
		return start;
	}

	public Junction getFinal() {
		return end;
	}

	public int getLongitud() {
		return longitud;
	}

	public int calcularVelBase() {
		long n = vehicles.sizeOfValues();
		if (n < 1)
			n = 1;
		if (maxVel < (maxVel / n))
			return maxVel;
		return (maxVel / (int) n); // dudo que lleguemos a tener tanto coche
	}

	public void avanza() {
		MultiTreeMap<Integer, Vehicle> nuevos = new MultiTreeMap<>((a, b) -> a
				- b);
		int velocidadBase = calcularVelBase();
		int factorReduccion = 1;
		int local = -1;
		for (Vehicle v : vehicles.innerValues()) {
			// manera óptima de saber si cuando conseguí el factor de Reduccion
			// tenia la misma localizacion que el de ahora?
			if (factorReduccion == 1 && local == -1) {
				if (v.getTiempoAveria() != 0) {
					local = v.getLocation();
				}
			} else if (local == v.getLocation()) {
				factorReduccion = 2;
				local = -1; // para que no entre en este if siempre.
			}
			if (v.getTiempoAveria() == 0)
				v.setVelocidadActual(velocidadBase / factorReduccion);
			v.avanza();
			if (v.getLocation() != longitud)
				nuevos.putValue(v.getLocation(), v);
		}
		vehicles = nuevos;
		// se encarga el recolector de basura de borrar el otro.

	}

	public void entraVehiculo(Vehicle ent) {
		vehicles.putValue(0, ent);

	}

	public void saleVehiculo(Vehicle ent) {
		vehicles.removeValue(longitud, ent);
	}

	protected void fillReportDetails(Map<String, String> out) {
		StringBuilder meter = new StringBuilder();
		for (Vehicle v : vehicles.innerValues()) {
			meter.append(v.getFillVehiculo() + " , ");
		}
		// meter = meter.substring(0, meter.length() - 3);
		if(!vehicles.isEmpty())meter.delete(meter.length() - 3, meter.length() - 1);
		out.put("state", meter.toString());
		// falta por implementar
	}

	protected String getReportHeader() {
		return "road_report";
	}
}