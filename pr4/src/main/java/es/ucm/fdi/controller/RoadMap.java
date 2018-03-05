package es.ucm.fdi.controller;

import java.util.*;

import es.ucm.fdi.simobject.*;

public class RoadMap {
	ArrayList<Vehicle> vehicles;
	ArrayList<Road> roads;
	ArrayList<Junction> junctions;

	public Vehicle getVehicle(String ide) {
		for (Vehicle v : vehicles) {
			if (v.getId().equals(ide))
				return v;
		}
		return null;
	}

	public Road getRoad(String ide) {
		for (Road r : roads) {
			if (r.getId().equals(ide))
				return r;
		}
		return null;
	}

	public Junction getJunction(String ide) {
		for (Junction j : junctions) {
			if (j.getId().equals(ide))
				return j;
		}
		return null;
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
	}

	public void addRoad(Road r) {
		roads.add(r);
	}

	public void addJunction(Junction j) {
		junctions.add(j);
	}
	// La profe tambien tiene puesto un metodo generateReport y otro de clear,
	// no se si hacen falta.
}
