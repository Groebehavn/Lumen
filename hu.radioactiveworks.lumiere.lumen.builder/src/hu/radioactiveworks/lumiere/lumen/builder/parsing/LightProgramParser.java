package hu.radioactiveworks.lumiere.lumen.builder.parsing;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.xml.sax.SAXException;

import hu.radioactiveworks.lumiere.lumen.builder.model.LightProgram;

public class LightProgramParser {
	
	private SAXParserFactory parserFactory;
	
	private SAXParser getParser() throws ParserConfigurationException, SAXException {
		if (parserFactory == null) {
			try
			{
				parserFactory = SAXParserFactory.newInstance();
				//TODO: HACK!!!
				//parserFactory.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File("D:\\Work\\Lumiere\\Lumen\\hu.radioactiveworks.lumiere.lumen.builder\\resources\\schema.xsd")));
				//TODO: Ez meg még mindig szar!
				URL url = getClass().getResource("/resources/schema.xsd");
				System.out.println(url);
				URI uri = getClass().getResource("/resources/schema.xsd").toURI();
				System.out.println(uri);
				parserFactory.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(getClass().getResource("/resources/schema.xsd")));
			}
			catch(URISyntaxException ex)
			{
				ex.printStackTrace();
				parserFactory = null;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				parserFactory = null;
				throw ex;
			}
		}
		return parserFactory.newSAXParser();
	}
	
	public String getMarker() {
		return ParsingHandler.MARKER_TYPE;
	}
	
	public void parseProgram(IResource resource, LightProgram lightProgram) {
		if (resource instanceof IFile && resource.getName().endsWith(".xml")) {
			IFile file = (IFile) resource;
			try {
				file.deleteMarkers(getMarker(), false, IResource.DEPTH_ZERO);
			} catch (CoreException e) {
				e.printStackTrace(System.err);
			}
			ParsingHandler reporter = new ParsingHandler(file);
			try {
				getParser().parse(file.getContents(), reporter);
			} catch (Exception e1) {
				e1.printStackTrace(System.err);
			}
		}
	}
}
