package es.ucm.fdi.exceptions;

@SuppressWarnings("serial")
public class SimulatorError extends RuntimeException {
	
	public SimulatorError(String message) {
		super(message);
	}

}
