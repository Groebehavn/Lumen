package hu.radioactiveworks.lumiere.lumen.builder.model;

import java.util.HashMap;
import java.util.Map;

public class LightQuantum {

	private static final int LEDNUM = LED_POSITION.values().length;
	
	public static enum LED_POSITION {
		LEFT,
		CENTER,
		RIGHT
	}
	
	private InterpolationDescription interpolationDesc;
	private Map<LED_POSITION,RGBColor> colorMap;
	
	public LightQuantum() {
		colorMap = new HashMap<>(LEDNUM);
		
		interpolationDesc = new InterpolationDescription();
	}
	
	public LightQuantum(
			InterpolationDescription interpolDesc,
			RGBColor left, RGBColor center, RGBColor right)
	{
		interpolationDesc = interpolDesc;
		colorMap = new HashMap<>(LEDNUM);
		
		colorMap.put(LED_POSITION.LEFT, left);
		colorMap.put(LED_POSITION.CENTER, center);
		colorMap.put(LED_POSITION.RIGHT, right);
	}

	public InterpolationDescription getInterpolationDesc() {
		return interpolationDesc;
	}

	public void setInterpolationDesc(InterpolationDescription interpolationDesc) {
		this.interpolationDesc = interpolationDesc;
	}
	
	public boolean isValid() {
		return (null != interpolationDesc) && (LEDNUM == colorMap.size());
	}
	
	public void putColorToQuantum(LED_POSITION pos, RGBColor color) {
		colorMap.put(pos, color);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(2048);
		builder.append("Quantum of: \n");
		builder.append("Int.enable " + Boolean.toString(interpolationDesc.enabled) + "\n");
		builder.append("Int.timeout " + Integer.toString(interpolationDesc.time) + "\n");
		builder.append("Q left " + colorMap.get(LED_POSITION.LEFT) + "\n");
		builder.append("Q center " + colorMap.get(LED_POSITION.CENTER) + "\n");
		builder.append("Q right " + colorMap.get(LED_POSITION.RIGHT) + "\n");
		
		return builder.toString();
	}
}
