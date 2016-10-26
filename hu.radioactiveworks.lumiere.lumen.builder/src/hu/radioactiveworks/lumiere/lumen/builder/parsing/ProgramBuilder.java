package hu.radioactiveworks.lumiere.lumen.builder.parsing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import hu.radioactiveworks.lumiere.lumen.builder.model.InterpolationDescription;
import hu.radioactiveworks.lumiere.lumen.builder.model.LightProgram;
import hu.radioactiveworks.lumiere.lumen.builder.model.LightQuantum;
import hu.radioactiveworks.lumiere.lumen.builder.model.LightQuantum.LED_POSITION;
import hu.radioactiveworks.lumiere.lumen.builder.model.RGBColor;

public final class ProgramBuilder {
	
	private Map<String,Boolean> elementMap;
	
	private LightProgram programUnderParse;
	private LightQuantum quantumUnderParse;
	private LED_POSITION ledPosition;
	private List<RGBColor> rgbList;
	private Map<String,Integer> rgbValues;
	private InterpolationDescription interpolationDesc;
	
	public ProgramBuilder()
	{
		elementMap = new HashMap<>();	//16
		elementMap.put("lightprogram", false);
		elementMap.put("quantums", false);
		elementMap.put("quantum", false);
		elementMap.put("interpolation", false);
		elementMap.put("enable", false);
		elementMap.put("time", false);
		elementMap.put("leds", false);
		elementMap.put("led", false);
		elementMap.put("r", false);
		elementMap.put("g", false);
		elementMap.put("b", false);
		
		rgbValues = new HashMap<>(6);
		rgbList = new ArrayList<>(3);
	}
	
	public LightProgram build(List<Element> parsedElements) throws BuilderException
	{
		programUnderParse = new LightProgram();
		
		//Going backwards in the list
		for(int i=parsedElements.size()-1; i>=0; i--)
		{
			parseElement(parsedElements.get(i));
		}
		
		System.out.println(programUnderParse);
		//TODO: kimarad az elsõ quantum!!!???
		return programUnderParse;
	}

	private void parseElement(Element element) throws BuilderException {
		Iterator<String> it = elementMap.keySet().iterator();
		String currentElementName = null;
		
		//Set flag in the map
		while(it.hasNext())
		{
			String str = it.next();
			if(element.getName().equalsIgnoreCase(str))
			{
				elementMap.put(str, true);
				currentElementName = str;
				break;
			}
		}
		
		if(currentElementName == null)
		{
			return;
		}
		
		//Switch with the name of the element in map (safe for case sensitivity)
		switch(currentElementName)
		{
		case "led":
			parseElementLed(element);
			break;
		case "r":
		case "g":
		case "b":
			parseElementRGBValues(element, currentElementName);
			break;
		case "leds":
			break;
		case "interpolation":
			interpolationDesc = new InterpolationDescription();
			break;
		case "time":
			
			break;
		case "enable":
			
			break;
		case "quantum":
			addQuantumToProgram();
		default:
			
		}
		
		int j=0;
		j++;
	}

	private void addQuantumToProgram() {
		if(quantumUnderParse != null && quantumUnderParse.isValid())
		{
			programUnderParse.getQuantumList().addFirst(quantumUnderParse);
		}
		
		quantumUnderParse = new LightQuantum();
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
			//TODO
		}
		
	}
}
