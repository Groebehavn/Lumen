package hu.radioactiveworks.lumiere.lumen.builder.compilation;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

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
	
	public void createFromResource(IResource resource) {
		if (resource instanceof IFile && resource.getName().endsWith(".xml")) {
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
	
	public void removeBinary(IResource resource) {
		
	}
}
