package edu.ncsu.csc.itrust.unit.testutils;

public class BadBean {
	// To make the coverage work...
	public BadBean() {
		setThing(5);
		setThing("");
	}

	public void setThing(String str) {
	}

	public void setThing(Integer str) {
	}
}
