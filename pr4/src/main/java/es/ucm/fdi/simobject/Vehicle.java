package es.ucm.fdi.simobject;
import java.util.ArrayList;

public class Vehicle { // creo que hace falta
		// hay que crear lo de id en SimObject.
		private int localizacion;
		private int velMaxima;
		private int velActual;
		private int tiempoAveria;
		private ArrayList<Junction> itinerario;
		private int kilometrage;//creo que también hace falta
		private Road actual;
		private int proxCruce;
		
		public Vehicle(int velM,ArrayList<Junction> cruc, String ide){
			localizacion=0;
			velMaxima=velM;
			velActual=0;
			tiempoAveria=0;
			itinerario=cruc;
			//id=ide; // esto creo que lo va a gestionar la superclase simobject
			kilometrage=0;
			proxCruce=0;
		}
		public int getMaxSpeed(){
			return velMaxima;
		}
		public int getLocation(){
			return localizacion;
		}
		
		public void setTiempoAveria(int n){
			tiempoAveria+=n;	
		}
		public int getTiempoAveria()
		{
			return tiempoAveria;
		}
		
		public void setVelocidadActual(int vel){
			velActual=vel;
		}
		public int getVelocidadActual(){
			return velActual;
			
		}
		public void avanza(){
			if(tiempoAveria>0){
				tiempoAveria--;	
			}
			else if(localizacion==actual.getLongitud()){ // en este caso no avanza.
			}
			else{
				if(localizacion+velActual>actual.getLongitud()){
					kilometrage+=actual.getLongitud()-localizacion;
					localizacion=actual.getLongitud();
					velActual=0; // hay que hacer una comprobacion si ya se llego al final
					itinerario.get(proxCruce).entraVehiculo(this); //El coche entra en cola para el cruce final de la carretera
					// como sabe el cruce de que carretera viene?
				}
				
			}
		}
		
		void moverASiguienteCarretera(Road c){
			proxCruce++;
			actual=c;
			localizacion=0;
		}
}
