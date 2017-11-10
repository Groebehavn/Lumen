package hu.radioactiveworks.lumiere.lumen.launcher;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;

import hu.radioactiveworks.lumiere.lumen.communication.serial.api.ISerialCommunicator;
import hu.radioactiveworks.lumiere.lumen.communication.serial.api.SerialCommunicationException;

public class PhotonLaunchConfiguration implements ILaunchConfigurationDelegate {

	private static ISerialCommunicator communicator;
	
	// Method will be used by DS to set the communicator service
    public synchronized void bindCommunicator(ISerialCommunicator service) {
        System.out.println("Service was set. Thank you DS!");
        communicator = service;
    }

    // Method will be used by DS to unset the communicator service
    public synchronized void unbindCommunicator(ISerialCommunicator service) {
        System.out.println("Service was unset. Why did you do this to me?");
        if (communicator == service) {
            communicator = null;
        }
    }

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		if(mode.equalsIgnoreCase(ILaunchManager.DEBUG_MODE))
		{
			throw new UnsupportedOperationException("Serial Launcher doesn't support debugging!");
		}
		
		String comPort = configuration.getAttribute("COMPORT","DEFAULT");
		communicator.setComPort(comPort);
		
		try {
			communicator.sendRun("69");
		} catch (SerialCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
