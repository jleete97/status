package com.jonandvirginia.small.util;

public class FullCalendarStyle {

	private String textColor;
	private String backgroundColor;
	private String borderColor;
	private String color;
	
	
	public String getTextColor() {
		return textColor;
	}
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	
	public String getBackgroundColor() {
		return (backgroundColor != null) ? backgroundColor : color;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public String getBorderColor() {
		return (borderColor != null) ? borderColor : color;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return "[C: " + color
				+ ", BG: " + backgroundColor
				+ ", BO:" + borderColor
				+ ", T: " + textColor + "]";
	}
}
