package es.ucm.fdi.events;

import java.util.Map;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.exceptions.SimulatorError;
import es.ucm.fdi.simobject.MostCrowed;

public class NewMostCrowedEvent extends NewJunctionEvent {
	
	public NewMostCrowedEvent(int time, String id) {
		super(time, id);
	}
	
	@Override
	public void execute(RoadMap things) {
		if (things.getObject(id) != null)
			throw new SimulatorError("Ups, " + id + " already exists");
		//Hasta aqui es igual
		things.addJunction(new MostCrowed(id,"mc"));
	}
	
	public static class Builder implements Event.Builder {
		
		public boolean canParse(String title, String type){
			return "new_junction".equals(title) && "mc".equals(type);
		}

		public Event fill(Map<String, String> map) {
			try {
				String id = map.get("id");
				if (!isValidId(id))
					throw new IllegalArgumentException("Invalid id");

				int time = 0;
				if (map.containsKey("time"))
					time = Integer.parseInt(map.get("time"));
				if (time < 0)
					throw new IllegalArgumentException("Negative time");
				
				//Hasta aqui es igual

				return new NewMostCrowedEvent(time, id);
			} catch (IllegalArgumentException e) {
				throw e;
			} catch (Exception e) {
				throw new IllegalArgumentException(
						"Incorrect arguments for new_junction");
			}
		}
	}
}
