package hu.radioactiveworks.lumiere.lumen.builder.parsing;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.xml.sax.Attributes;

public class ElementBuilder {
	
	private List<Element> openedElements;
	private List<Element> finishedElements;
	
	public ElementBuilder() {
		//TODO: Jó ez így? Véletlen sem csak az init 10 elemig jó ugye?
		openedElements = new ArrayList<Element>();
		finishedElements = new ArrayList<Element>();
	}
	
	public void startDocument()
	{
		openedElements.clear();
		finishedElements.clear();
	}
	
	public void startElement(String name, Attributes attributes)
	{
		Element element = new Element(name, attributes);
		openedElements.add(element);
	}
	
	public void addValue(String value)
	{
		openedElements.get(openedElements.size()-1).setValue(value);
	}
	
	public void endElement(String name) throws BuilderException
	{
		Iterator<Element> it = openedElements.iterator();
		
		while(it.hasNext())
		{
			Element element = it.next();
			if(element.getName().equalsIgnoreCase(name))
			{
				it.remove();
				finishedElements.add(element);
				return;
			}
		}
		
		throw new BuilderException("No element to end with the name: " + name);
	}
	
	public List<Element> getFinishedElements()
	{
		return finishedElements;
	}
}
