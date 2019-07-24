package edu.ncsu.csc.itrust.enums;

public enum PregnancyStatus {
	Initial("Initial"), Office("Office Visit"), Complete("Complete");
	private String name;
	
	private PregnancyStatus(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		if (name == null)
			return "";
		return name;
	}
}
