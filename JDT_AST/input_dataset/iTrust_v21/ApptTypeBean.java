package edu.ncsu.csc.itrust.beans;

public class ApptTypeBean {
	
	private String name;
	private int duration;
	private int price; //added for UC60
	
	public ApptTypeBean() {
		this.name = null;
		this.duration = 0;
	}
	
	public ApptTypeBean(String name, int duration) {
		this.name = name;
		this.duration = duration;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
}
