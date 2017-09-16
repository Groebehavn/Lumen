package hu.radioactiveworks.lumiere.lumen.communication.serial.api;

public interface ISerialCommunicator {
	
	public void setComPort(String comPortName);
	
	public void sendStart() throws SerialCommunicationException;
	
	public void sendStop() throws SerialCommunicationException;
	
	public void sendHeader(byte[] headerData) throws SerialCommunicationException;
	
	public void sendQuantum(byte[] quantumData) throws SerialCommunicationException;
	
}
