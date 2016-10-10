package hu.radioactiveworks.lumiere.lumen.builder.model;

import java.util.ArrayList;

public class LightProgram {
	
	private ArrayList<LightQuantum> quantumList;
	
	public LightProgram() {
		quantumList = new ArrayList<LightQuantum>();
	}
	
	public ArrayList<LightQuantum> getQuantumList() {
		return quantumList;
	}

	public byte[] serialize() {
		return null;
	}
	
}
