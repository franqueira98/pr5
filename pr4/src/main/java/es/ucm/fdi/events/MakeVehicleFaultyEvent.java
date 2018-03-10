package es.ucm.fdi.events;

import java.util.List;
import java.util.Map;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simobject.Vehicle;

public class MakeVehicleFaultyEvent extends Event {
	int tiempoAveria;
	List<Vehicle> vehicles;

	public MakeVehicleFaultyEvent(int time) {
		super(time);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(RoadMap things) {
		// TODO Auto-generated method stub

	}

	public class Builder extends Event.Builder {
		public Builder() {
			super("MakeVehicleFaultyEventBuilder");
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

				// falta cambiar ese string de ids por una lista de cochees.
				return null;
				// return new newJunctionEvent(time,ide);
			}
		}

	}
}
