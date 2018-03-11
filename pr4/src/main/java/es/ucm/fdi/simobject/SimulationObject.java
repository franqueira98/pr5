package es.ucm.fdi.simobject;

import java.util.Map;

public abstract class SimulationObject {
	protected String id;

	public SimulationObject(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void report(int time, Map<String, String> out) {
		out.put("", getReportHeader());
		out.put("id", id);
		out.put("time", Integer.toString(time));
		fillReportDetails(out);
	}
	
	//Hay que ver como va el mapa, no me atrevi a hacerlo asi
	public String generateReport(){
		return null;
	}

	protected abstract void fillReportDetails(Map<String, String> out);

	// protected abstract IniSection getReportSection(); // este metodo no se si
	// hace falta
	protected abstract String getReportHeader();
}
