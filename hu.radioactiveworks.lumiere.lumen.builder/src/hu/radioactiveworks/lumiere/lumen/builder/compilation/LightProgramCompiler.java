package hu.radioactiveworks.lumiere.lumen.builder.compilation;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

import hu.radioactiveworks.lumiere.lumen.builder.model.LightProgram;
import hu.radioactiveworks.lumiere.lumen.builder.parsing.LightProgramParser;

public class LightProgramCompiler {
	
	private LightProgramParser lpParser;
	private LightProgram lightProgram;
	
	public LightProgramCompiler(LightProgramParser lpParser2) {
		lpParser = lpParser2;
	}

	public String getMarker() {
		return CompilationErrorHandler.MARKER_TYPE;
	}
	
	public void removeBinary(IResource resource) {
		//TODO: Bináris fájl nem tûnik el!
		if (resource instanceof IFile && resource.getName().endsWith(".xml"))
		{
			removeBinaryFileOfResource(resource);
		}
	}
	
	private void removeBinaryFileOfResource(IResource resource)
	{
		//TODO: Nem szedi ki a binárist, sõt... :(
		String fileName = ((IFile)resource).getName();
		IPath binaryFolderPath = ((IFile)resource).getFullPath().removeLastSegments(1);
		binaryFolderPath = binaryFolderPath.addTrailingSeparator();
		binaryFolderPath = binaryFolderPath.append("bin");
		binaryFolderPath = binaryFolderPath.addTrailingSeparator();
		binaryFolderPath = binaryFolderPath.append(((IFile)resource).getName().substring(0, ((IFile)resource).getName().length()-4));
		binaryFolderPath = binaryFolderPath.addFileExtension("bin");
		
		File binaryFile = new File(binaryFolderPath.toString() + fileName.substring(0, fileName.length()-3)+"bin");
		
		if(!binaryFile.exists())
		{
			return;
		}
		
		binaryFile.delete();
	}

	public void createFromResource(IResource resource) {
		if (resource instanceof IFile && resource.getName().endsWith(".xml"))
		{
			lpParser.parseProgram(resource, lightProgram);
			
			stuffData(lightProgram, createBinaryFile((IFile)resource));
		}
	}

	private void stuffData(LightProgram lightProgram, File binary) {
		//TODO:
		//Itt kéne kifosni a serializator segítségével a binárisba a parser által beolvasott struktúrát
	}

	private File createBinaryFile(IFile file) {
		IFile binaryFolder = file.getProject().getFile("bin");
		File folderFile = new File(binaryFolder.getRawLocationURI());
		if(!binaryFolder.exists()) {
			folderFile.mkdirs();
		}
		
		File binaryFile = new File(folderFile.getAbsolutePath().concat("\\" + file.getName().substring(0, file.getName().length()-3)+"bin"));
		try {
			binaryFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return binaryFile;
	}
}
