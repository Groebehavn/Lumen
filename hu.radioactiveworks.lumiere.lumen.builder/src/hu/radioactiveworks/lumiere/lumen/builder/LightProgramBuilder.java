package hu.radioactiveworks.lumiere.lumen.builder;

import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import hu.radioactiveworks.lumiere.lumen.builder.compilation.LightProgramCompiler;
import hu.radioactiveworks.lumiere.lumen.builder.parsing.LightProgramParser;

public class LightProgramBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "hu.radioactiveworks.lumiere.lumen.builder.lightProgramBuilder";

	
	private LightProgramParser lpParser;
	private LightProgramCompiler lpCompiler;

	@Override
	protected void startupOnInitialize() {
		super.startupOnInitialize();
		
		lpParser = new LightProgramParser();
		lpCompiler = new LightProgramCompiler(lpParser);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.internal.events.InternalBuilder#build(int,
	 *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected IProject[] build(int kind, @SuppressWarnings("rawtypes") Map args, IProgressMonitor monitor)
			throws CoreException {
		if(kind == INCREMENTAL_BUILD)
		{
			incrementalBuild(getDelta(getProject()), monitor);
		}
		else
		{
			fullBuild(monitor);
		}
		
		return null;
	}

	protected void clean(IProgressMonitor monitor) throws CoreException {
		// delete markers set and files created
		monitor.beginTask("Clean Markers", 2);
		getProject().deleteMarkers(lpParser.getMarker(), true, IResource.DEPTH_INFINITE);
		monitor.worked(1);
		getProject().deleteMarkers(lpCompiler.getMarker(), true, IResource.DEPTH_INFINITE);
		monitor.worked(2);
		monitor.done();
	}

	protected void fullBuild(final IProgressMonitor monitor)
			throws CoreException {
		try {
			getProject().accept(new FullBuildVisitor(lpCompiler, monitor));
		} catch (CoreException e) {
		}
	}
	
	protected void incrementalBuild(IResourceDelta delta,
			IProgressMonitor monitor) throws CoreException {
		// the visitor does the work.
		delta.accept(new IncrementalBuildVisitor(lpCompiler, monitor));
	}
}
