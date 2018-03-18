package es.ucm.fdi.simobject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class RoundRobinTest {
	@Test
	public void roundRobinTest() {
		List<Junction> itinerary = new ArrayList<>();
		List<Junction> itinerary2 = new ArrayList<>();
		Junction j0 = new Junction("j0");
		RoundRobin j1 = new RoundRobin("j1", 2, 4, "RoundRobin");
		itinerary.add(j0);
		Junction j2=new Junction("j2");
		itinerary.add(j1);
		Junction j3=new Junction("j3");
		itinerary.add(j1);
		itinerary2.add(j3);
		itinerary2.add(j1);
		itinerary2.add(j2);
		Vehicle c = new Vehicle(10, itinerary2, "v4");
		Vehicle c1 = new Vehicle(10, itinerary2, "v5");
		Vehicle v = new Vehicle(10, itinerary, "v1");
		Vehicle v1 = new Vehicle(15, itinerary, "v2");
		Vehicle v2 = new Vehicle(15, itinerary, "v3");
		Road r1 = new Road("r1", 30, 10, j0,j1);
		Road r2 = new Road("r2", 15, 20,j1, j2);
		Road r3 = new Road("r3", 20, 20, j3, j1);
		itinerary.get(1).newIncoming(r3);
		itinerary.get(1).newIncoming(r1);
		itinerary.get(1).newOutgoing(r2);
		itinerary.get(2).newIncoming(r2);
		c.moveToNextRoad(r3);
		c1.moveToNextRoad(r3);
		v.moveToNextRoad(r1);
		v1.moveToNextRoad(r1);
		v2.moveToNextRoad(r1);
		r1.avanza();
		r1.avanza();
		r1.avanza();
		// aqui debería tener todos los coches de r1 en la rotonda
		// lso de r3 sin moverse
		j1.avanza();
		j1.avanza();
		assertTrue("No espera 2 ciclos para cambiar el semáforo", v1.getRoad()
				.getLongitud() == 30 || v2.getRoad().getLongitud() == 30);
		j1.avanza();
		j1.avanza();
		assertTrue("Mal cambio de semáforo", v1.getRoad() != r2
				|| v2.getRoad() != r2);
		j1.newVehicle(c);
		j1.avanza();
		assertTrue("Debería pasar el coche",c.getRoad()!=r2);
		// no se si quieres probar más de esta.
	}

}
