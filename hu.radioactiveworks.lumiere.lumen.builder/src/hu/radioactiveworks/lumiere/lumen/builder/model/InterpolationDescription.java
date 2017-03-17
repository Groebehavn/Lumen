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
	
	public int getTime() {
		return time;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
}
