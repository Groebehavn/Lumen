package hu.radioactiveworks.lumiere.lumen.launcher.tabs;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SerialPropertiesTab extends AbstractLaunchConfigurationTab {

	private Text comPortText;
	
	@Override
	public void createControl(Composite parent) {
		Composite comp = new Group(parent, SWT.BORDER);
        setControl(comp);

        GridLayoutFactory.swtDefaults().numColumns(2).applyTo(comp);

        Label label = new Label(comp, SWT.NONE);
        label.setText("Port:");
        GridDataFactory.swtDefaults().applyTo(label);

        comPortText = new Text(comp, SWT.BORDER);
        comPortText.setMessage("COM3");
        GridDataFactory.fillDefaults().grab(true, false).applyTo(comPortText);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
            String consoleText = configuration.getAttribute("COMPORT",
                    "DEFAULT");
            comPortText.setText(consoleText);
        } catch (CoreException e) {
            // ignore here
        }
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute("COMPORT", comPortText.getText());
	}

	@Override
	public String getName() {
		return "Connection";
	}
	
}
