package es.ucm.fdi.events;

import java.util.Map;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.ini.IniSection;

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
		for (String vehicle : arrayVehicles) {
			things.getVehicle(vehicle).setTiempoAveria(tiempoAveria);
		}
	}

	public static class Builder extends Event.Builder {
		public Builder() {
			super("make_vehicle_faulty");
		}

		public Event parse(IniSection ini) {
			if (!ini.getTag().equals("make_vehicle_faulty"))
				return null;

			Map<String, String> sec = ini.getKeysMap();
			int time = 0;
			if (sec.containsKey("time"))
				time = Integer.parseInt(sec.get("time"));
			int tiempoAveria = Integer.parseInt(sec.get("duration"));
			String listaIds = sec.get("vehicles");

			return new MakeVehicleFaultyEvent(time, tiempoAveria, listaIds);
		}
	}
}
