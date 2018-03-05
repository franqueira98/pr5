package es.ucm.fdi.simobject;

import java.io.OutputStream;
import java.util.Map;

import es.ucm.fdi.ini.IniSection;

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

	protected abstract void fillReportDetails(Map<String, String> out);

	// protected abstract IniSection getReportSection(); // este metodo no se si
	// hace falta
	protected abstract String getReportHeader();
}
