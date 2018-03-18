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
		itinerary.add(j1);
		itinerary.add(new Junction("j2"));
		itinerary.add(new Junction("j3"));
		itinerary2 = itinerary;
		itinerary2.remove(itinerary.get(0));
		itinerary2.add(0, j0);
		Vehicle c = new Vehicle(10, itinerary2, "v4");
		Vehicle c1 = new Vehicle(10, itinerary2, "v5");
		Vehicle v = new Vehicle(10, itinerary, "v1");
		Vehicle v1 = new Vehicle(15, itinerary, "v2");
		Vehicle v2 = new Vehicle(15, itinerary, "v3");
		Road r1 = new Road("r1", 30, 10, itinerary.get(0), itinerary.get(1));
		Road r2 = new Road("r2", 15, 20, itinerary.get(1), itinerary.get(2));
		Road r3 = new Road("r3", 20, 20, j0, itinerary.get(1));
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
		assertTrue("No espera 2 ciclos para cambiar el semáforo",
				v1.getRoad() != r1 || v2.getRoad() != r1);
		j1.avanza();
		j1.avanza();
		assertTrue("Mal cambio de semáforo", v1.getRoad() != r2
				| v2.getRoad() != r2);
		r3.avanza();
		r3.avanza();
		j1.avanza();
		j1.avanza();
		assertTrue("Debería pasar un solo coche",
				c.getRoad() == r1 | c1.getRoad() == r2);
		// no se si quieres probar más de esta.
	}

}
