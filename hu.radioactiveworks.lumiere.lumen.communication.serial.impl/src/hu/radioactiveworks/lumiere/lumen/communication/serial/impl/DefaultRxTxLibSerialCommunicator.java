package hu.radioactiveworks.lumiere.lumen.communication.serial.impl;

import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import hu.radioactiveworks.lumiere.lumen.communication.serial.api.ISerialCommunicator;
import hu.radioactiveworks.lumiere.lumen.communication.serial.api.SerialCommunicationException;

public class DefaultRxTxLibSerialCommunicator implements ISerialCommunicator {

	private String comPortName = "COM3";
	private static final byte delimiter = '\n';
	private static final byte[] startCommand = {'S','T','A','R','T',delimiter};
	private static final byte[] stopCommand = {'S','T','O','P',delimiter};
	private static final byte[] headerCommand = {'H','E','A','D','E','R',' '};
	private static final byte[] quantumCommand = {'Q',' '};
	
	@Override
	public void setComPort(String comPortName)
	{
		this.comPortName = comPortName;
	}
	
	@Override
	public void sendStart() throws SerialCommunicationException
	{
		sendBytes(startCommand);
	}

	@Override
	public void sendStop() throws SerialCommunicationException
	{
		sendBytes(stopCommand);
	}

	@Override
	public void sendHeader(byte[] headerData) throws SerialCommunicationException
	{
		sendBytes(concatenate(headerCommand, headerData));
		sendBytes(new byte[]{delimiter});
	}

	@Override
	public void sendQuantum(byte[] quantumData) throws SerialCommunicationException
	{
		sendBytes(concatenate(quantumCommand, quantumData));
		sendBytes(new byte[]{delimiter});
	}
	
	private void sendBytes(byte[] bytes) throws SerialCommunicationException
	{
		try
		{	
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(comPortName);
		    if ( portIdentifier.isCurrentlyOwned() )
		    {
		        throw new RuntimeException(String.format("Port %s is currently in use", comPortName));
		    }
		    else
		    {
		        System.out.println(String.format("Connecting to %s.", comPortName));
		        
		        CommPort commPort = null;
		        try
		        {
		        	//No javadoc: Name of application and milliseconds to wait for port
			        commPort = portIdentifier.open("DefaultRxTxLibSerialCommunicator", 3000);
		
			        if ( commPort instanceof SerialPort )
			        {
			            System.out.println(String.format("Connected and opened communication on port %s.", comPortName));
			            
			            SerialPort serialPort = (SerialPort) commPort;
			            serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
			            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
			            
			            OutputStream out = serialPort.getOutputStream();
		
			            out.write(bytes);
			            
			            out.flush();
			            
			            out.close();
			        }
			        else
			        {
			        	throw new RuntimeException(String.format("Only serial ports are handled, %s is not a serial port.", comPortName));
			        }
		        }
		        finally
		        {
		        	if(commPort != null)
		        	{
		        		commPort.close();
		        	}
		        }
		    }  
		}
		catch(Exception exp)
		{
			throw new SerialCommunicationException(exp.getMessage(), exp);
		}
	}
	
	private byte[] concatenate(byte[] a, byte[] b)
	{
		byte[] c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

}
