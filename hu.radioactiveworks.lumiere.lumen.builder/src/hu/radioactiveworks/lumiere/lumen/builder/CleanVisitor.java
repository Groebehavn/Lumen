package hu.radioactiveworks.lumiere.lumen.builder;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.IProgressMonitor;

import hu.radioactiveworks.lumiere.lumen.builder.compilation.LightProgramCompiler;

class CleanVisitor implements IResourceVisitor {
	
	private LightProgramCompiler lpCompiler;
	private IProgressMonitor monitor;
	private IProject project;
	
	public CleanVisitor(IProject iProject, LightProgramCompiler lpCompiler, IProgressMonitor monitor) {
		this.lpCompiler = lpCompiler;
		this.monitor = monitor;
		this.project = iProject;
	}
	
	public boolean visit(IResource resource) {
		monitor.beginTask("Clean project", 2);
		monitor.subTask("Cleaning compiled files");
		lpCompiler.cleanBinary(resource);
		monitor.worked(1);
		monitor.subTask("Cleaning compile folder");
		cleanFolder(project.getFolder("bin"));
		monitor.done();
		//return true to continue visiting children.
		return true;
	}
	
	private void cleanFolder(IFolder folder)
	{
		if(folder.exists() && folder.getRawLocation().toFile().isDirectory())
		{
			folder.getRawLocation().toFile().delete();
		}
	}
}
