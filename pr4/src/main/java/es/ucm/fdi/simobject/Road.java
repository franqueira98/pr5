package es.ucm.fdi.simobject;

import es.ucm.fdi.util.MultiTreeMap;

public class Road {
	private int longitud;
	private int maxVel;
	private MultiTreeMap<Integer,Vehicle> vehicles;
	
	public Road(int lon,int maxv){
		longitud=lon;
		maxVel=maxv;
		vehicles=new MultiTreeMap<>((a,b)->a-b); //flata ver como recorrerlos para el metodo avanza
	}
	
	
}