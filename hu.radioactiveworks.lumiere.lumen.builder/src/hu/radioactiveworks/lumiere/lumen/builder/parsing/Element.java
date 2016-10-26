package hu.radioactiveworks.lumiere.lumen.builder.parsing;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;

public class Element {
	private String name;
	private Map<String,String> attributes;
	private String value;
	
	public Element(String name, Attributes attributes)
	{
		this.name = name;
		//TODO: Miért is 6 az initialCapacity? Ez azt jelenti, hogy 4 után jön a rehash!
		//4-nél tuti nem lesz több attribute soha?
		this.attributes = new HashMap<>(6);
		for(int i=0; i<attributes.getLength(); i++)
		{
			this.attributes.put(attributes.getLocalName(i), attributes.getValue(i));
		}
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Map<String,String> getAttributes() {
		return attributes;
	}

	public String getValue() {
		return value;
	}
}
