package hu.radioactiveworks.lumiere.lumen.builder.model;

import java.util.LinkedList;

public class LightProgram {
	
	private LinkedList<LightQuantum> quantumList;
	
	public LightProgram() {
		quantumList = new LinkedList<LightQuantum>();
	}
	
	public LinkedList<LightQuantum> getQuantumList() {
		return quantumList;
	}

	public byte[] serialize() {
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(4096);
		
		for(LightQuantum quantum : quantumList)
		{
			builder.append(">\n");
			builder.append(quantum);
			builder.append("\n");
		}
		
		return builder.toString();
	}
}
