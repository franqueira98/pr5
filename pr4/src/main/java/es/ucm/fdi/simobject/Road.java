package es.ucm.fdi.simobject;

import java.util.ArrayList;
import java.util.Map;
import java.util.NavigableSet;

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
		if (maxVel < (maxVel / n) + 1)
			return maxVel;
		return (maxVel / (int) n) + 1;
	}

	public void avanza() {
		MultiTreeMap<Integer, Vehicle> nuevos = new MultiTreeMap<>((a, b) -> a
				- b);
		int velocidadBase = calcularVelBase();
		int factorReduccion = 1;
		//int local = -1;
		for (Vehicle v : vehicles.innerValues()) {
			/*
			if (factorReduccion == 1 && local == -1) {
				if (v.getTiempoAveria() != 0) {
					local = v.getLocation();
				}
			} else if (local != v.getLocation()) {
				factorReduccion = 2;
				local = -1;
			}*/
			if(factorReduccion == 1)
				if(v.getTiempoAveria() != 0)
					factorReduccion=2;
			if (v.getTiempoAveria() == 0)
				v.setVelocidadActual(velocidadBase / factorReduccion);
			if(v.getLocation() < longitud) v.avanza();
			nuevos.putValue(v.getLocation(), v);
		}/*
		NavigableSet<Integer> locations = vehicles.descendingKeySet();
		for(int here : locations){
			ArrayList<Vehicle> vehiclesInTime = vehicles.get(here);
			for(Vehicle v : vehiclesInTime){
				if(factorReduccion == 1)
					if(v.getTiempoAveria() != 0)
						factorReduccion=2;
				if (v.getTiempoAveria() == 0)
					v.setVelocidadActual(velocidadBase / factorReduccion);
				if(v.getLocation() < longitud) v.avanza();
				nuevos.putValue(v.getLocation(), v);
			}
		}*/
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