package edu.ncsu.csc.itrust.unit.testutils;


public class OkayBean {
	private String thing;

	public OkayBean() {
		setThing("");
	}

	public String getThing() {
		return thing;
	}

	public void setThing(String thing) {
		this.thing = thing;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass().equals(this.getClass()) && this.equals((OkayBean) obj);
	}
	
	@Override
	public int hashCode() {
		return 42; // any arbitrary constant will do 
	}

	private boolean equals(OkayBean other) {
		return true;
	}

}
