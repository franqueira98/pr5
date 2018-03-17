package es.ucm.fdi.simobject;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.*;
import org.junit.Test;

public class RoadTest {
	@Test
	public void roadTest() {
		List<Junction> itinerary=new ArrayList<>();
		itinerary.add(new Junction("j1"));
		itinerary.add(new Junction("j2"));
		itinerary.add(new Junction("j3"));
		Vehicle v=new Vehicle(5,itinerary,"v1");
		Vehicle v1=new Vehicle(15,itinerary,"v2");
		Road r1= new Road("r1",30,10,itinerary.get(0),itinerary.get(1));
		Road r2= new Road("r1",15,20,itinerary.get(1),itinerary.get(2));
		itinerary.get(1).newIncoming(r1);
		itinerary.get(1).newOutgoing(r2);
		v.moveToNextRoad(r1);
		v1.moveToNextRoad(r1);
		r1.newVehicleR(v);
		r1.newVehicleR(v1);
		r1.avanza();
		assertTrue("Los vehiculos no van a velocidad adecuada",v.getLocation()!=5 || v1.getLocation()!=10);
		v.setTiempoAveria(2);
		r1.avanza();
		assertTrue("Los vehiculos no van a velocidad adecuada",v.getLocation()!=5 || v1.getLocation()!=20);
		
		
	}

}
