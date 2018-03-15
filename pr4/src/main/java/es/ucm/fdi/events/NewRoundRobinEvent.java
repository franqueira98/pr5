package es.ucm.fdi.events;

import java.util.Map;

import es.ucm.fdi.controller.RoadMap;
import es.ucm.fdi.exceptions.SimulatorError;
import es.ucm.fdi.simobject.RoundRobin;

public class NewRoundRobinEvent extends NewJunctionEvent {
	
	protected int minTime;
	protected int maxTime;
	
	public NewRoundRobinEvent(int time, String id, int minTime, int maxTime) {
		super(time, id);
		this.minTime = minTime;
		this.maxTime = maxTime;
	}
	
	@Override
	public void execute(RoadMap things) {
		if (things.getObject(id) != null)
			throw new SimulatorError("Ups, " + id + " already exists");
		//Hasta aqui es igual
		things.addJunction(new RoundRobin(id,minTime,maxTime, "rr"));
	}
	
	public static class Builder extends Event.Builder {

		public Builder() {
			super("new_junction", "rr");
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
				if (!map.containsKey("max_time_slice"))
					throw new IllegalArgumentException("Missing max_time_slice");
				int maxTime = Integer.parseInt(map.get("max_time_slice"));
				if (maxTime <= 0)
					throw new IllegalArgumentException("No positive max time");
				
				if (!map.containsKey("min_time_slice"))
					throw new IllegalArgumentException("Missing min_time_slice");
				int minTime = Integer.parseInt(map.get("min_time_slice"));
				if (minTime <= 0)
					throw new IllegalArgumentException("No positive min time");
				
				if(minTime > maxTime) throw new IllegalArgumentException("Max time must be greater than Min time");

				return new NewRoundRobinEvent(time, id, minTime, maxTime);
			} catch (IllegalArgumentException e) {
				throw e;
			} catch (Exception e) {
				throw new IllegalArgumentException(
						"Incorrect arguments for new_junction");
			}
		}
	}
}
