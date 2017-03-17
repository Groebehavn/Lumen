package hu.radioactiveworks.lumiere.lumen.builder;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.IProgressMonitor;

import hu.radioactiveworks.lumiere.lumen.builder.compilation.LightProgramCompiler;
import hu.radioactiveworks.lumiere.lumen.builder.model.LightProgram;
import hu.radioactiveworks.lumiere.lumen.builder.parsing.LightProgramParser;

class FullBuildVisitor implements IResourceVisitor {
	
	private LightProgramCompiler lpCompiler;
	private IProgressMonitor monitor;
	
	public FullBuildVisitor(LightProgramCompiler lpCompiler, IProgressMonitor monitor) {
		this.lpCompiler = lpCompiler;
		this.monitor = monitor;
	}
	
	public boolean visit(IResource resource) {
		monitor.beginTask("Full Build on " + resource.getName(), 2);
		monitor.subTask("Removing binaries");
		lpCompiler.removeBinary(resource);
		monitor.worked(1);
		monitor.subTask("Compiling binaries");
		lpCompiler.createFromResource(resource);
		monitor.worked(2);
		monitor.done();
		//return true to continue visiting children.
		return true;
	}
}
