package es.ucm.fdi.events;

public class Event {

	
	private int time;
	//el otro multitree map iría aqui, para saber tiempo y tipo de evento
	// deberíamos tener un campo que sea tipo de evento?
	// segun la profe pasan un integer asi que es lo más posible
	// pero por ejemplo  NewJunctionEvent no se le pasa ningun integer a la constructora
	public Event(int t){
		time=t;
	}
	public int getTime(){
		return time;
	}
	public int compareTo(Event r){
		
		// no se que va a haber que poner aqui
		return 0;
	}
}
