package hu.radioactiveworks.lumiere.lumen.communication.serial.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import hu.radioactiveworks.lumiere.lumen.communication.serial.api.ISerialCommunicator;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception
	{
        ISerialCommunicator service = new DefaultRxTxLibSerialCommunicator();
        // Third parameter is a hashmap which allows to configure the service
        // Not required in this example
        context.registerService(ISerialCommunicator.class.getName(), service,
                null);
        System.out.println("ISerialCommunicator is registered");
    }

	@Override
	public void stop(BundleContext context) throws Exception
	{
		
	}

}
