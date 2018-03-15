package es.ucm.fdi.events;

import java.util.Map;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.exceptions.SimulatorError;
import es.ucm.fdi.simobject.Vehicle;

public class MakeVehicleFaultyEvent extends Event {
	
	protected int tiempoAveria;
	protected String vehicles;

	public MakeVehicleFaultyEvent(int time, int tiempoAveria, String vehicles) {
		super(time);
		this.tiempoAveria = tiempoAveria;
		this.vehicles = vehicles;
	}

	@Override
	public void execute(RoadMap things) {
		String[] arrayVehicles = vehicles.split(",");

		for (String vehicle : arrayVehicles) {
			Vehicle unlucky = things.getVehicle(vehicle);
			if (unlucky == null)
				throw new SimulatorError("Hitting the air");
			unlucky.setTiempoAveria(tiempoAveria);
		}
	}

	public static class Builder implements Event.Builder {
		
		public boolean canParse(String title, String type){
			return "make_vehicle_faulty".equals(title) && "".equals(type);
		}

		public Event fill(Map<String, String> map) {
			try {
				int time = 0;
				if (map.containsKey("time"))
					time = Integer.parseInt(map.get("time"));
				if (time < 0)
					throw new IllegalArgumentException("Negative time");
				

				int tiempoAveria = Integer.parseInt(map.get("duration"));
				if (tiempoAveria <= 0)
					throw new IllegalArgumentException("No positive duration");

				if (!map.containsKey("vehicles"))
					throw new Exception();
				String listaIds = map.get("vehicles");

				return new MakeVehicleFaultyEvent(time, tiempoAveria, listaIds);
			} catch (Exception e) {
				throw new IllegalArgumentException(
						"Incorrect arguments for make_vehicle_faulty");
			}
		}
	}
}
