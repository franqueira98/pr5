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
		mapVehicles.put(v.getId(), v);
		vehicles.add(v);
	}

	public void addRoad(Road r) {
		mapRoads.put(r.getId(), r);
		roads.add(r);
	}

	public void addJunction(Junction j) {
		mapJunctions.put(j.getId(),j);
		junctions.add(j);
	}

	public String generateReport() {
	
		StringBuilder report = new StringBuilder();
		for (Junction j : junctions)
			report.append(j.generateReport());
		for (Road r : roads)
			report.append(r.generateReport());
		for (Vehicle v : vehicles)
			report.append(v.generateReport());
		return report.toString();
	}
}
