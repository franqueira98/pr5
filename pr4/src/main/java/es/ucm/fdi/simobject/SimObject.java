package es.ucm.fdi.simobject;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class SimObject {
	protected String id;

	public SimObject(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public Map<String, String> report(int time) {
		Map<String, String> out = new LinkedHashMap<>();
		out.put("", getReportHeader());
		out.put("id", id);
		out.put("time", "" + time);
		fillReportDetails(out);
		return out;
	}

	protected abstract void fillReportDetails(Map<String, String> out);

	protected abstract String getReportHeader();
}
