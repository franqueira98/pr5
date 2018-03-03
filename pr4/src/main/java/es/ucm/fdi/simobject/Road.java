package es.ucm.fdi.simobject;

import java.util.Map;

import es.ucm.fdi.util.MultiTreeMap;

public class Road extends SimulationObject{
	private int longitud;
	private int maxVel;
	private Junction start;
	private Junction end;
	private MultiTreeMap<Integer,Vehicle> vehicles;
	
	public Road(String ide,int lon,int maxv,Junction princ,Junction fin){
		super(ide);
		longitud=lon;
		maxVel=maxv;
		vehicles=new MultiTreeMap<>((a,b)->a-b); //falta ver como recorrerlos para el metodo avanza
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
	public void avanza(){
		MultiTreeMap<Integer,Vehicle> nuevos=new MultiTreeMap<>((a,b)->a-b);
		
		for(Vehicle v:vehicles.innerValues()){
			v.avanza();
			nuevos.putValue(v.getLocation(), v);
		}
		vehicles=nuevos;
		// se encarga el recolector de basura de borrar el otro.
		
	}
	public void entraVehiculo(Vehicle ent){
		vehicles.putValue(0, ent);
		
	}
	public void saleVehículo(Vehicle ent){
		end.entraVehiculo(ent);
		vehicles.removeValue(longitud,ent);		
	}
	protected void fillReportDetails(Map<String, String> out){
		
		// falta por implementar
	}
protected String getReportHeader(){
		
		return "road_report";
		}
}