package es.ucm.fdi.simobject;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class DirtTest {

	public void dirtTest() {
		List<Junction> itinerary=new ArrayList<>();
		itinerary.add(new Junction("j1"));
		itinerary.add(new Junction("j2"));
		itinerary.add(new Junction("j3"));
		Vehicle v=new Vehicle(5,itinerary,"v1");
		Vehicle v1=new Vehicle(15,itinerary,"v2");
		Vehicle v2=new Vehicle(15,itinerary,"v3");
		Dirt r1= new Dirt("r1",30,10,itinerary.get(0),itinerary.get(1),"Dirt");
		Dirt r2= new Dirt("r1",15,20,itinerary.get(1),itinerary.get(2),"Dirt");
		itinerary.get(1).newIncoming(r1);
		itinerary.get(1).newOutgoing(r2);
		itinerary.get(2).newIncoming(r2);
		v.moveToNextRoad(r1);
		v1.moveToNextRoad(r1);
		v2.moveToNextRoad(r1);
		v.setTiempoAveria(2);
		v1.setTiempoAveria(2);
		assertTrue("Velocidad inadecuada",v2.getVelocidadActual()!=5);
	}

}
