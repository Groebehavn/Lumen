package hu.radioactiveworks.lumiere.lumen.communication;

import java.io.InputStream;
import java.io.OutputStream;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class SerialBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
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
	            commPort.close();
	        }
	        else
	        {
	            System.out.println("Error: Only serial ports are handled by this example.");
	        }
	    }  
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
