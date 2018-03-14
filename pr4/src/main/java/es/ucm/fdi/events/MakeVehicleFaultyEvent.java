package es.ucm.fdi.events;

import java.util.Map;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.exceptions.SimulatorError;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simobject.Vehicle;

public class MakeVehicleFaultyEvent extends Event {
	int tiempoAveria;
	String vehicles;

	public MakeVehicleFaultyEvent(int time, int tiempoAveria, String vehicles) {
		super(time);
		this.tiempoAveria = tiempoAveria;
		this.vehicles = vehicles;
	}

	@Override
	public void execute(RoadMap things) {
		String[] arrayVehicles = vehicles.split(",");
		if (tiempoAveria < 0)
			throw new SimulatorError(
					"You want to travel back in time and crash a vehicle? Really?");
		
		for (String vehicle : arrayVehicles) {
			Vehicle unlucky = things.getVehicle(vehicle);
			if(unlucky == null) throw new SimulatorError("Hitting the air");
			unlucky.setTiempoAveria(tiempoAveria);
		}
	}

	public static class Builder extends Event.Builder {
		public Builder() {
			super("make_vehicle_faulty");
		}

		public Event parse(IniSection ini) {
			try {
				Map<String, String> sec = ini.getKeysMap();
				int tiempoAveria = Integer.parseInt(sec.get("duration"));

				int time = 0;
				if (sec.containsKey("time"))
					time = Integer.parseInt(sec.get("time"));
				
				if(!sec.containsKey("vehicles")) throw new Exception();
				String listaIds = sec.get("vehicles");
				
				return new MakeVehicleFaultyEvent(time, tiempoAveria, listaIds);
			} catch (Exception e) {
				throw new IllegalArgumentException(
						"Incorrect arguments for make_vehicle_faulty");
			}
		}
	}
}
