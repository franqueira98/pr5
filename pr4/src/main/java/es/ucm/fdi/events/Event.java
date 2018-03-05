package es.ucm.fdi.events;

public class Event {

	
	private int time;
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
