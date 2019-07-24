package edu.ncsu.csc.itrust.enums;

/**
 * SleepType.java Version 1 4/6/2015 Copyright notice: none
 * 
 * The types of sleep a patient can log.
 */
public enum SleepType {

	Nightly("Nightly"),
	Nap("Nap");

	private String name;

	/**
	 * Sets the name of the sleep
	 * 
	 * @param name
	 *            the name of the sleep
	 */
	private SleepType(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the sleep
	 * 
	 * @return the name of the sleep
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the name of the sleep
	 * 
	 * @return the name of the sleep
	 */
	public String toString() {
		return getName();
	}

	/**
	 * Tries to parse a string into one of the 2 valid sleep types. Throws an
	 * IllegalArgumentException if the string is not one of the given sleep
	 * types
	 * 
	 * @param str
	 *            the name of the sleep
	 * @return a SleepType with the appropriate name
	 */
	public static SleepType parse(String str) {
		for (SleepType xt : SleepType.values()) {
			if (xt.name.equals(str)) {
				return xt;
			}
		}
		throw new IllegalArgumentException("Sleep Type " + str
				+ " does not exist");
	}
}
