package hu.radioactiveworks.lumiere.lumen.builder.model;

public class InterpolationDescription {
	boolean enabled;
	int time;
	
	public InterpolationDescription() {
		this(false,0);
	}
	
	public InterpolationDescription(boolean enabled, int time) {
		this.enabled = enabled;
		this.time = time;
	}
}
