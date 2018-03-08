package es.ucm.fdi.controller;

import java.util.*;

import es.ucm.fdi.simobject.*;

public class RoadMap {
	private List<Vehicle> vehicles;
	private List<Road> roads;
	private List<Junction> junctions;

	private Map<String, Vehicle> mapVehicles;
	private Map<String, Road> mapRoads;
	private Map<String, Junction> mapJunctions;

	public RoadMap() {
		this.vehicles = new ArrayList<>();
		this.roads = new ArrayList<>();
		this.junctions = new ArrayList<>();
		this.mapVehicles = new HashMap<>();
		this.mapRoads = new HashMap<>();
		this.mapJunctions = new HashMap<>();
	}

	public Vehicle getVehicle(String id) {
		return mapVehicles.get(id);
	}

	public Road getRoad(String id) {
		return mapRoads.get(id);
	}

	public Junction getJunction(String id) {
		return mapJunctions.get(id);
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public List<Road> getRoads() {
		return roads;
	}

	public List<Junction> getJunctions() {
		return junctions;
	}

	public void addVehicle(Vehicle v) {
		vehicles.add(v);
	} // habra que meterlo al mapa tambien n?

	public void addRoad(Road r) {
		roads.add(r);
	}

	public void addJunction(Junction j) {
		junctions.add(j);
	}

	public String generateReport() {
		String report = "";
		for (Junction j : junctions)
			report += j.generateReport();
		for (Road r : roads)
			report += r.generateReport();
		for (Vehicle v : vehicles)
			report += v.generateReport();
		return report;
	}

	/*
	 * public Vehicle getVehicle(String ide) { for (Vehicle v : vehicles) { if
	 * (v.getId().equals(ide)) return v; } return null; }
	 * 
	 * public Road getRoad(String ide) { for (Road r : roads) { if
	 * (r.getId().equals(ide)) return r; } return null; }
	 * 
	 * public Junction getJunction(String ide) { for (Junction j : junctions) {
	 * if (j.getId().equals(ide)) return j; } return null; }
	 */
}
