package edu.ncsu.csc.itrust.enums;

public enum DeliveryType {
	Vaginal("Vaginal Delivery"), Caesarean("Caesarean Section"), Miscarriage("Miscarriage");
	private String name;
	
	private DeliveryType(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		if (name == null)
			return "";
		return name;
	}
}
