package es.ucm.fdi.simobject;

import es.ucm.fdi.util.MultiTreeMap;

public class Road {
	private int longitud;
	private int maxVel;
	private Junction start;
	private Junction end;
	private MultiTreeMap<Integer,Vehicle> vehicles;
	
	public Road(String ide,int lon,int maxv,Junction princ,Junction fin){
		longitud=lon;
		maxVel=maxv;
		vehicles=new MultiTreeMap<>((a,b)->a-b); //flata ver como recorrerlos para el metodo avanza
		id=ide;
		start=princ;
		end=fin;
	}
	public Junction getPrincipio(){
		return start;
	}
	public Junction getFinal(){
		
		return end;
	}
	public int getLongitud(){
		return longitud;
		
	}
	public void entraVehiculo(Vehicle ent){
		vehicles.putValue(0, ent);
		
	}
	public void saleVehículo(Vehicle ent){
		vehicles.removeValue(longitud,ent);		
	}
	
	
}