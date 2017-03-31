package hu.radioactiveworks.lumiere.lumen.builder;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import hu.radioactiveworks.lumiere.lumen.builder.compilation.LightProgramCompiler;

class IncrementalBuildVisitor implements IResourceDeltaVisitor {
	
	private LightProgramCompiler lpCompiler;
	private IProgressMonitor monitor;
	
	public IncrementalBuildVisitor(LightProgramCompiler lpCompiler, IProgressMonitor monitor) {
		this.lpCompiler = lpCompiler;
		this.monitor = monitor;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
	 */
	public boolean visit(IResourceDelta delta) throws CoreException {
		IResource resource = delta.getResource();
		
		monitor.beginTask("Incremental Build on " + resource.getName(), 2);
		monitor.subTask("Removing binaries");
		
		if(delta.getKind() == IResourceDelta.REMOVED
				||
				delta.getKind() == IResourceDelta.CHANGED)
		{
			// handle removed or changed resource
			lpCompiler.removeBinaryOfProgram(resource);
		}
		
		monitor.worked(1);
		monitor.subTask("Compiling binaries");
		
		if(delta.getKind() == IResourceDelta.ADDED
				||
				delta.getKind() == IResourceDelta.CHANGED)
		{
			// handle added or changed resource
			lpCompiler.createFromResource(resource);
		}
		
		monitor.worked(2);
		monitor.done();
		
		//return true to continue visiting children.
		return true;
	}
}
