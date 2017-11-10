package hu.radioactiveworks.lumiere.lumen.launcher;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

import hu.radioactiveworks.lumiere.lumen.launcher.tabs.SerialPropertiesTab;

public class LaunchConfigurationTabGroupSerial extends AbstractLaunchConfigurationTabGroup {

	public LaunchConfigurationTabGroupSerial() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		setTabs(new ILaunchConfigurationTab[] { new SerialPropertiesTab()});
	}

}
