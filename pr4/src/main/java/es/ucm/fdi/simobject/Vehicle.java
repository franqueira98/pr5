package es.ucm.fdi.simobject;

import java.util.List;
import java.util.Map;

public class Vehicle extends SimObject {
	private int velMaxima;
	private int velActual;
	private Road actual;
	private int localizacion;
	private List<Junction> itinerario;
	private int proxCruce;
	private int tiempoAveria;
	private int kilometrage;
	private boolean haLlegado;

	public Vehicle(int velM, List<Junction> cruc, String ide) {
		super(ide);
		localizacion = 0;
		velMaxima = velM;
		velActual = 0;
		tiempoAveria = 0;
		itinerario = cruc;
		kilometrage = 0;
		proxCruce = 0;
		haLlegado = false;
	}

	public int getMaxSpeed() {
		return velMaxima;
	}

	public int getLocation() {
		return localizacion;
	}

	public void setTiempoAveria(int n) {
		tiempoAveria += n;
		velActual = 0;
	}

	public Junction getProxCruce() {
		if (proxCruce != itinerario.size())
			return itinerario.get(proxCruce);
		else
			return null;
	}

	public Road getRoad() {
		return actual;
	}

	public int getTiempoAveria() {
		return tiempoAveria;
	}

	public void setVelocidadActual(int vel) {
		if (vel > velMaxima)
			velActual = velMaxima;
		else
			velActual = vel;
	}

	public int getVelocidadActual() {
		return velActual;
	}

	public void avanza() {
		if (tiempoAveria > 0) {
			tiempoAveria--;
		} else {
			if (localizacion + velActual >= actual.getLongitud()) {
				kilometrage += actual.getLongitud() - localizacion;
				localizacion = actual.getLongitud();
				velActual = 0; // hay que hacer una comprobacion si ya se
								// llego
								// al final
				if (proxCruce == itinerario.size())
					haLlegado = true;
				else
					actual.getFinal().newVehicle(this);
				actual.saleVehiculo(this); // esta la usamos si queremos que
											// lo elimine de la carretera
			} else {
				kilometrage += velActual;
				localizacion += velActual;
			}
		}
	}

	void newRoad(Road r) {
		proxCruce++;
		actual = r;
		localizacion = 0;
		velActual = 0;
	}
	
	void arrived(){
		haLlegado = true;
	}

	protected void fillReportDetails(Map<String, String> out) {
		out.put("speed", "" + velActual);
		out.put("kilometrage", "" + kilometrage);
		out.put("faulty", "" + tiempoAveria);
		if (!haLlegado)
			out.put("location", "(" + actual.getId() + ", " + localizacion
					+ ")");
		else
			out.put("location", "arrived");
	}

	protected String getFillVehiculo() {
		return ("(" + id + ", " + localizacion + ")");

	}

	protected String getReportHeader() {
		return "vehicle_report";
	}
}
