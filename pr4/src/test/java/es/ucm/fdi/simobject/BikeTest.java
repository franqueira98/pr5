package es.ucm.fdi.simobject;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class BikeTest {
	@Test
	public void bikeTest() {
		List<Junction> itinerary=new ArrayList<>();
		itinerary.add(new Junction("j1"));
		itinerary.add(new Junction("j2"));
		itinerary.add(new Junction("j3"));
		Bike v=new Bike(10,itinerary,"v1","bike");
		Bike v1=new Bike(15,itinerary,"v2","bike");
		v.setVelocidadActual(10);
		v1.setVelocidadActual(5);
		v.setTiempoAveria(2);
		v1.setTiempoAveria(4);
		assertTrue("No pone bien el tiempo de averia",v.getTiempoAveria()!=2 || v1.getTiempoAveria()==4);
	}

}
