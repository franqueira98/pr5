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
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(RoadMap things) {
		String idVehicle = "";
		for (int i = 0; i < vehicles.length(); i++) {
			if (vehicles.charAt(i) == ',') {
				things.getVehicle(idVehicle).setTiempoAveria(tiempoAveria);
				idVehicle = "";
			} else
				idVehicle += vehicles.charAt(i);

		}
		things.getVehicle(idVehicle).setTiempoAveria(tiempoAveria);
	}

	public static class Builder extends Event.Builder {
		public Builder() {
			super("make_vehicle_faulty");
		}

		public String getTitle() {
			return title;
		}

		public Event parse(IniSection ini) {
			if (!ini.getTag().equals("make_vehicle_faulty")) {
				return null;
			} else {
				int time;
				int tiempoAveria;
				String listaIds;
				Map<String, String> sec = ini.getKeysMap();
				time = Integer.parseInt(sec.get("time"));
				tiempoAveria = Integer.parseInt(sec.get("duration"));
				listaIds = sec.get("vehicles");

				return new MakeVehicleFaultyEvent(time, tiempoAveria, listaIds);
			}
		}

	}
}
