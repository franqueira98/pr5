package es.ucm.fdi.simobject;

import java.util.Map;

public class MostCrowed extends Junction {
	
	protected String type;

	public MostCrowed(String id, String type) {
		super(id);
		this.type = type;
	}
	
	public void newIncoming(Road r) {
		IncomingRoad ir = new IncomingRoad(r.getId());
		if (entrantes.isEmpty())
			ir.semaforoVerde = true;
		saberInc.put(r, ir);
		entrantes.add(ir);
	}

	public void avanza() {
		if (!entrantes.isEmpty()) {
			IncomingRoad roadGreen = (IncomingRoad) entrantes.get(semaforo);
			if (!roadGreen.cola.isEmpty()) {
				Vehicle lucky = roadGreen.cola.getFirst();
				lucky.getRoad().removeVehicle(lucky);
				roadGreen.cola.pop();
				moveToNextRoad(lucky);
			}

			roadGreen.timeUnitsUsed++;
			if (roadGreen.timeUnitsUsed == roadGreen.timeInterval) 
				avanzaSemaforo();
		}
	}
	
	public void avanzaSemaforo(){
		IncomingRoad roadGreen = (IncomingRoad) entrantes.get(semaforo);
		roadGreen.semaforoVerde = false;

		IncomingRoad moreVehicles = (IncomingRoad) entrantes.get(0);
		int max = 0;
		for (int i = 0; i < entrantes.size(); i++) {
			IncomingRoad ir = (IncomingRoad) entrantes.get(i);
			if (ir.cola.size() > moreVehicles.cola.size()) {
				moreVehicles = (IncomingRoad) ir;
				max = i;
			}
		}
		semaforo = max;
		moreVehicles.semaforoVerde = true;
		max = moreVehicles.cola.size() / 2;
		max = Math.max(1,  max);
		moreVehicles.timeInterval = max;
		moreVehicles.timeUnitsUsed = 0;
	}
	
	public void preparaSemaforo(){
		if(!entrantes.isEmpty()) avanzaSemaforo();
	}
	
	protected void fillReportDetails(Map<String, String> out) {
		super.fillReportDetails(out);
		out.put("type", type);
	}

	protected class IncomingRoad extends Junction.IncomingRoad {

		protected int timeInterval;
		protected int timeUnitsUsed;

		public IncomingRoad(String r) {
			super(r);
			this.timeInterval = 0;
			this.timeUnitsUsed = 0;
		}

		protected String semaforoReport() {
			StringBuilder r = new StringBuilder();
			r.append(super.semaforoReport());
			if (semaforoVerde)
				r.append(":" + (timeInterval - timeUnitsUsed));
			return r.toString();
		}
	}
}
