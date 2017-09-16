package hu.radioactiveworks.examples.communication.serial;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public final class Serial {

	public static void main(String[] args) throws NoSuchPortException, PortInUseException, IOException, UnsupportedCommOperationException {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("COM3");
	    if ( portIdentifier.isCurrentlyOwned() )
	    {
	        System.out.println("Error: Port is currently in use");
	    }
	    else
	    {
	        System.out.println("Connect 1/2");
	        CommPort commPort = portIdentifier.open("Serial",6000);

	        if ( commPort instanceof SerialPort )
	        {
	            System.out.println("Connect 2/2");
	            SerialPort serialPort = (SerialPort) commPort;
	            System.out.println("BaudRate: " + serialPort.getBaudRate());
	            System.out.println("DataBIts: " + serialPort.getDataBits());
	            System.out.println("StopBits: " + serialPort.getStopBits());
	            System.out.println("Parity: " + serialPort.getParity());
	            System.out.println("FlowControl: " + serialPort.getFlowControlMode());
	            serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
	            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
	            System.out.println("BaudRate: " + serialPort.getBaudRate());
	            System.out.println("DataBIts: " + serialPort.getDataBits());
	            System.out.println("StopBits: " + serialPort.getStopBits());
	            System.out.println("Parity: " + serialPort.getParity());
	            System.out.println("FlowControl: " + serialPort.getFlowControlMode());
	            InputStream in = serialPort.getInputStream();
	            OutputStream out = serialPort.getOutputStream();

	            out.write(new byte[]{0x50,0x52,0x4F,0x53,0x49,0x47,0x4D,0x41,0x10,'\n'});
	            System.out.println("Written.");
	            out.flush();
	            System.out.println("Flushed.");
	            out.close();
	            System.out.println("Closed.");
	        }
	        else
	        {
	            System.out.println("Error: Only serial ports are handled by this example.");
	        }
	    }  
	}

}
