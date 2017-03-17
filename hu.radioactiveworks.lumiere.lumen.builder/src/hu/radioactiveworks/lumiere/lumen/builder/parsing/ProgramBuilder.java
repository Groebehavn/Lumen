package hu.radioactiveworks.lumiere.lumen.builder.parsing;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.radioactiveworks.lumiere.lumen.builder.model.InterpolationDescription;
import hu.radioactiveworks.lumiere.lumen.builder.model.LightProgram;
import hu.radioactiveworks.lumiere.lumen.builder.model.LightQuantum;
import hu.radioactiveworks.lumiere.lumen.builder.model.LightQuantum.LED_POSITION;
import hu.radioactiveworks.lumiere.lumen.builder.model.RGBColor;

public final class ProgramBuilder {
	
	private LightProgram programUnderParse;
	private LightQuantum quantumUnderParse;
	private LED_POSITION ledPosition;
	private Map<String,Integer> rgbValues;
	private InterpolationDescription interpolationDesc;
	
	public ProgramBuilder()
	{
		rgbValues = new HashMap<>(6);
	}
	
	public LightProgram build(List<Element> parsedElements) throws BuilderException
	{
		//TODO: Ide akkor is beesik, ha van error marker a fájlon...
		programUnderParse = new LightProgram();
		
		//Going backwards in the list
		for(int i=parsedElements.size()-1; i>=0; i--)
		{
			parseElement(parsedElements.get(i));
		}
		
		System.out.println(Calendar.getInstance().getTime());
		System.out.println(programUnderParse);
		System.out.println("***END***");
		return programUnderParse;
	}

	private void parseElement(Element element) throws BuilderException {
		//Switch with the name in lower case
		String elementName = element.getName().toLowerCase();
		
		switch(elementName)
		{
		case "led":
			parseElementLed(element);
			break;
		case "r":
		case "g":
		case "b":
			parseElementRGBValues(element, elementName);
			break;
		case "leds":
			break;
		case "interpolation":
			addInterpolationDescToQuantum();
			break;
		case "time":
			interpolationDesc.setTime(Integer.parseInt(element.getValue()));
			break;
		case "enable":
			interpolationDesc.setEnabled(Boolean.parseBoolean(element.getValue()));
			break;
		case "quantum":
			addQuantumToProgram();
		default:
			
		}
	}

	private void addInterpolationDescToQuantum() {
		interpolationDesc = new InterpolationDescription();
		quantumUnderParse.setInterpolationDesc(interpolationDesc);
	}

	private void addQuantumToProgram() {
		quantumUnderParse = new LightQuantum();
		programUnderParse.getQuantumList().addFirst(quantumUnderParse);
	}

	private void parseElementRGBValues(Element element, String currentElementName) {
		rgbValues.put(currentElementName, Integer.parseInt(element.getValue()));
		if(rgbValues.size() == 3)
		{
			quantumUnderParse.putColorToQuantum(ledPosition, 
					new RGBColor(rgbValues.get("r"),
							rgbValues.get("g"),
							rgbValues.get("b")));
			rgbValues.clear();
		}
	}

	private void parseElementLed(Element element) throws BuilderException {
		if(element.getAttributes().containsKey("specifier"))
		{
			ledPosition = null;
			for(LED_POSITION pos : LED_POSITION.values())
			{
				if(element.getAttributes().get("specifier").equalsIgnoreCase(pos.toString()))
				{
					ledPosition = pos;
				}
			}
			
			if(ledPosition == null)
			{
				throw new BuilderException("No matching specifier for led! Value: " + element.getAttributes().containsKey("specifier"));
			}
		}
		else
		{
			ledPosition = null;
			throw new BuilderException("No LED specifier");
		}
		
	}
}
