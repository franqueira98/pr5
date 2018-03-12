package es.ucm.fdi.simobject;

import java.util.Map;

import es.ucm.fdi.util.MultiTreeMap;

public class Road extends SimObject {
	private int longitud;
	private int maxVel;
	private Junction start;
	private Junction end;
	private MultiTreeMap<Integer, Vehicle> vehicles;

	public Road(String ide, int lon, int maxv, Junction princ, Junction fin) {
		super(ide);
		longitud = lon;
		maxVel = maxv;
		vehicles = new MultiTreeMap<>((a, b) -> a - b);
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

	public void newVehicle(Vehicle v) {
		vehicles.putValue(0, v);
		v.changeRoad(this);
	}

	public void removeVehicle(Vehicle v) {
		vehicles.removeValue(longitud, v);
	}

	public int calcularVelBase() {
		long n = vehicles.sizeOfValues();
		if (n < 1)
			n = 1;
		if (maxVel < (maxVel / n))
			return maxVel;
		return (maxVel / (int) n);
	}

	public void avanza() {
		MultiTreeMap<Integer, Vehicle> nuevos = new MultiTreeMap<>((a, b) -> a
				- b);
		int velocidadBase = calcularVelBase();
		int factorReduccion = 1;
		int local = -1;
		for (Vehicle v : vehicles.innerValues()) {
			if (factorReduccion == 1 && local == -1) {
				if (v.getTiempoAveria() != 0) {
					local = v.getLocation();
				}
			} else if (local != v.getLocation()) {
				factorReduccion = 2;
				local = -1;
			}
			if (v.getTiempoAveria() == 0)
				v.setVelocidadActual(velocidadBase / factorReduccion);
			v.avanza();
			nuevos.putValue(v.getLocation(), v);
		}
		vehicles = nuevos;
	}

	protected void fillReportDetails(Map<String, String> out) {
		StringBuilder meter = new StringBuilder();
		for (Vehicle v : vehicles.innerValues()) {
			meter.append(v.getFillVehiculo() + ",");
		}
		
		if (!vehicles.isEmpty())
			meter.delete(meter.length() - 1, meter.length());
		out.put("state", meter.toString());
	}

	protected String getReportHeader() {
		return "road_report";
	}
}