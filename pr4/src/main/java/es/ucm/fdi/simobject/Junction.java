package es.ucm.fdi.simobject;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Junction extends SimulationObject{
	private Map<Road,IncomingRoad> cola;
	private List<IncomingRoad> semaforo;
	
	
	
	public Junction(String id){
		super(id);
		cola = new HashMap<>();
		semaforo=new ArrayList<>();
	}
	// hay que ver como gestionamos la creación de Roads e Incoming Roads.
	public void entraVehiculo(Vehicle c){
		
		
		
	}
	public void avanza(int lon){
		
		
	}
	protected void fillReportDetails(Map<String, String> out){
		// falta por implementar
		
	}
	protected String getReportHeader(){
		
		return "junction_report";
		}
	private class IncomingRoad{
		private ArrayDeque<Vehicle> cola;
		private boolean semaforo;
		
		public void meterVehiculo(Vehicle entra){
			cola.addLast(entra);
		}
		public void sacarVehiculo(){
			cola.removeFirst(); // creo que hay que hacer comprobaciones antes de hacer esto.
			
		}
	
	}
}
