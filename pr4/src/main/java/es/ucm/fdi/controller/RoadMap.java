package es.ucm.fdi.controller;

import java.util.*;

import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simobject.*;

public class RoadMap {
	private List<Vehicle> vehicles;
	private List<Road> roads;
	private List<Junction> junctions;

	private Map<String, SimObject> map;

	public RoadMap() {
		this.vehicles = new ArrayList<>();
		this.roads = new ArrayList<>();
		this.junctions = new ArrayList<>();
		this.map = new HashMap<>();
	}

	public Vehicle getVehicle(String id) {
		return (Vehicle) map.get(id);
	}

	public Road getRoad(String id) {
		return (Road) map.get(id);
	}

	public Junction getJunction(String id) {
		return (Junction) map.get(id);
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

	public List<SimObject> getJRV() {
		List<SimObject> list = new ArrayList<>();
		list.addAll(junctions);
		list.addAll(roads);
		list.addAll(vehicles);
		return list;
	}

	public void addVehicle(Vehicle v) {
		map.put(v.getId(), v);
		vehicles.add(v);
	}

	public void addRoad(Road r) {
		map.put(r.getId(), r);
		roads.add(r);
	}

	public void addJunction(Junction j) {
		map.put(j.getId(), j);
		junctions.add(j);
	}

	public Ini generateReport(int time) {
		Ini report = new Ini();
		for (SimObject j : getJRV()) {
			Map<String, String> map = j.report(time);
			IniSection section = new IniSection(map.get(""));
			map.remove("");
			map.forEach((k, v) -> section.setValue(k, v));
			report.addsection(section);
		}
		return report;
	}
}
