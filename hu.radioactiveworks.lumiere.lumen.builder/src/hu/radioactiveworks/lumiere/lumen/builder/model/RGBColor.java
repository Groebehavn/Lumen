package hu.radioactiveworks.lumiere.lumen.builder.model;

public class RGBColor {
	
	private static final int MINIMUM_VALUE = 0;
	private static final int MAXIMUM_VALUE = 255;
	
	private int red;
	private int green;
	private int blue;
	
	public RGBColor(int red, int green, int blue) {
		
		checkArguments(red,green,blue);
		
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	private void checkArguments(int red, int green, int blue) throws IllegalArgumentException {
		if(red < MINIMUM_VALUE || red > MAXIMUM_VALUE) {
			new IllegalArgumentException("RED");
		}
		if(green < MINIMUM_VALUE || green > MAXIMUM_VALUE) {
			new IllegalArgumentException("GREEN");
		}
		if(blue < MINIMUM_VALUE || blue > MAXIMUM_VALUE) {
			new IllegalArgumentException("BLUE");
		}
		
	}

	public RGBColor() {
		this(0, 0, 0);
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}
}
