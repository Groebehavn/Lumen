package hu.radioactiveworks.lumiere.lumen.builder.parsing;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import hu.radioactiveworks.lumiere.lumen.builder.model.InterpolationDescription;
import hu.radioactiveworks.lumiere.lumen.builder.model.LightProgram;
import hu.radioactiveworks.lumiere.lumen.builder.model.LightQuantum;
import hu.radioactiveworks.lumiere.lumen.builder.model.LightQuantum.LED_POSITION;
import hu.radioactiveworks.lumiere.lumen.builder.model.RGBColor;

public final class ProgramBuilder {
	
	private static enum EParsableElements 
	{
		LightProgram,
		Quantums,
		Quantum,
		Interpolation,
		Enable,
		Time,
		Leds,
		Led,
		R,
		G,
		B
	}
	
	private Map<EParsableElements,Boolean> phases;
	private LightProgram programUnderParse;
	private LightQuantum quantumUnderParse;
	private LED_POSITION ledPosition;
	private RGBColor rgbColor;
	private InterpolationDescription interpolationDesc;
	
	public ProgramBuilder()
	{
		this.phases = new HashMap<EParsableElements,Boolean>();
	}
	
	public void startBuild()
	{
		for(EParsableElements element : EParsableElements.values())
		{
			phases.put(element, false);
		}
		programUnderParse = new LightProgram();
	}
	
	public void startPhase(String elementName) throws BuilderException
	{
		
	}
	
	private void setPhase(String elementName, boolean state) throws BuilderException
	{
		boolean foundPhase = false;
		for(EParsableElements element : EParsableElements.values())
		{
			if(element.toString().equalsIgnoreCase(elementName))
			{
				phases.put(element, state);
				foundPhase = true;
				break;
			}
		}
		if(foundPhase == false)
		{
			throw new BuilderException("No matching phase to: " + elementName);
		}
	}
	
	//Nem jó, mert nem egyszerre jön be az attribútum és a value, és ha már builderrõl beszélünk,
	//ez legyen lekezelve...
	public void addParams(String elementName, Map<String,String> attributes, String value)
	{
		//No need to check if the key is there... xsd check already done
		if(elementName.equalsIgnoreCase(EParsableElements.Led.toString()))
		{
			ledPosition = LED_POSITION.valueOf(attributes.get("specifier"));
		}
		if(elementName.equalsIgnoreCase(EParsableElements.R.toString()))
		{
			rgbColor.setRed(Integer.parseInt(value));
		}
		if(elementName.equalsIgnoreCase(EParsableElements.G.toString()))
		{
			rgbColor.setGreen(Integer.parseInt(value));
		}
		if(elementName.equalsIgnoreCase(EParsableElements.B.toString()))
		{
			rgbColor.setBlue(Integer.parseInt(value));
		}
	}
	
	public LightProgram build() throws BuilderException
	{
		if(true == phases.get(EParsableElements.LightProgram))
		{
			throw new BuilderException("Builder is not finished yet!");
		}
		return programUnderParse;
	}
}
