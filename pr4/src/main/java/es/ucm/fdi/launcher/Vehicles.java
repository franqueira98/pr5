package es.ucm.fdi.launcher;

import java.util.ArrayList;

public class Vehicles { // creo que hace falta
		// hay que crear lo de id en SimObject.
		private int localizacion;
		private int velMaxima;
		private int velActual;
		private int tiempoAveria;
		private ArrayList<Junction> itinerario;
		private int kilometrage;//creo que también hace falta
		private Carretera actual;
		private int proxCruce;
		
		public Vehicles(int velM,ArrayList<String> cruc, String ide){
			localizacion=0;
			velMaxima=velM;
			velActual=0;
			tiempoAveria=0;
			itinerario=cruc;
			id=ide;
			kilometrage=0;
			proxCruce=0;
		}
		public void setTiempoAveria(int n){
			tiempoAveria+=n;	
		}
		public void setVelocidadActual(int vel){
			velActual=vel;
			
		}
		public void avanza(){
			if(tiempoAveria>0){
				tiempoAveria--;	
			}
			else if(localizacion==actual.getlongitud()){ // en este caso no avanza.
			}
			else{
				if(localizacion+velActual>actual.getlongitud()){
					kilometrage+=actual.getlongitud()-localizacion;
					localizacion=actual.getlongitud();
					velActual=0; // hay que hacer una comprobacion si ya se llego al final
					itinerario[proxCruce].entrar(this,actual); //El coche entra en cola para el cruce final de la carretera
					
				}
				
			}
		}
		
		void moverASiguienteCarretera(Carretera c){
			proxCruce++;
			actual=c.;
			localizacion=0;
		}
}
