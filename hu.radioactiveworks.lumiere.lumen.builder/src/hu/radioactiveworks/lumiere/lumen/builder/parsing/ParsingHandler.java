package hu.radioactiveworks.lumiere.lumen.builder.parsing;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import hu.radioactiveworks.lumiere.lumen.builder.model.LightProgram;
import hu.radioactiveworks.lumiere.lumen.builder.model.LightQuantum;

final class ParsingHandler extends DefaultHandler {
	
	
	
	protected static final String MARKER_TYPE = "hu.radioactiveworks.lumiere.lumen.builder.parsingProblem";
	
	private IFile file;
	private ElementBuilder eBuilder;
	private ProgramBuilder pbuilder;

	public ParsingHandler(IFile file) {
		this.file = file;
		this.eBuilder = new ElementBuilder();
	}

	private void addMarker(SAXParseException e, int severity) {
		addMarker(file, e.getMessage(), e
				.getLineNumber(), severity);
	}

	public void error(SAXParseException exception) throws SAXException {
		addMarker(exception, IMarker.SEVERITY_ERROR);
	}

	public void fatalError(SAXParseException exception) throws SAXException {
		addMarker(exception, IMarker.SEVERITY_ERROR);
	}

	public void warning(SAXParseException exception) throws SAXException {
		addMarker(exception, IMarker.SEVERITY_WARNING);
	}
	
	private void addMarker(IFile file, String message, int lineNumber,
			int severity) {
		try {
			IMarker marker = file.createMarker(MARKER_TYPE);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
			if (lineNumber == -1) {
				lineNumber = 1;
			}
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
		} catch (CoreException e) {
			e.printStackTrace(System.err);
		}
	}
	
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		//Reinit variables
		eBuilder.startDocument();
	}
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		//TODO: Itt most az jön, hogy fogom a qName-et, és ha R/G/B, akkor kiveszem a színt
		// ha led akkor megnézem az attribútumát stb...
		eBuilder.startElement(qName, attributes);
	}
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		
		//TODO: Megnézem, hogy az elkészült RGBColor kész van-e, megvan-e benne minden szín
		// majd ha megvan, megnézem az interpolationt, ha az is megvan, elkészítem a quantumot
		// ide kellhet amúgy egy Builder?
		try {
			eBuilder.endElement(qName);
		} catch (BuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		eBuilder.addValue((new String(ch)).substring(start, start+length));
	}
}
