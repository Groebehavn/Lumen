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
		switch (delta.getKind()) {
		case IResourceDelta.ADDED:
			// handle added resource
			lpCompiler.createFromResource(resource);
			break;
		case IResourceDelta.REMOVED:
			// handle removed resource
			lpCompiler.removeBinary(resource);
			break;
		case IResourceDelta.CHANGED:
			// handle changed resource
			lpCompiler.removeBinary(resource);
			lpCompiler.createFromResource(resource);
			break;
		}
		//return true to continue visiting children.
		return true;
	}
}
