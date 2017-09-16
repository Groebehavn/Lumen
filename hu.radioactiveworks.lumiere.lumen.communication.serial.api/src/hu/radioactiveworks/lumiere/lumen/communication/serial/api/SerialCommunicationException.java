package hu.radioactiveworks.lumiere.lumen.communication.serial.api;

public class SerialCommunicationException extends Exception {
	
	private static final long serialVersionUID = -571529684983118813L;

	public SerialCommunicationException(String message, Throwable exp) {
		super(message, exp);
	}
	
}
