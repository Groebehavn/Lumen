package hu.radioactiveworks.lumiere.lumen.builder.parsing;

import org.xml.sax.Attributes;

public class Element {
	private String name;
	private Attributes attributes;
	private String value;
	
	public Element(String name, Attributes attributes)
	{
		this.name = name;
		this.attributes = attributes;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public String getValue() {
		return value;
	}
}
