package edu.ncsu.csc.itrust.enums;

/**
 * ExerciseType.java Version 1 4/2/2015 Copyright notice: none
 * 
 * The types of exercise a patient can log.
 * 
 */
public enum ExerciseType {
	/** Running, cycling, etc */
	Cardio("Cardio"),
	/** Bench press, curls, etc */
	Weights("Weight Training");

	private String name;

	/**
	 * Sets the name of the exercise
	 * 
	 * @param name
	 *            the name of the exercise
	 */
	private ExerciseType(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the exercise
	 * 
	 * @return the name of the exercise
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the name of the exercise
	 * 
	 * @return the name of the exercise
	 */
	public String toString() {
		return getName();
	}

	/**
	 * Tries to parse a string into one of the 2 valid exercise types. Throws an
	 * IllegalArgumentException if the string is not one of the given exercise
	 * types
	 * 
	 * @param str
	 *            the name of the exercise
	 * @return a ExerciseType with the appropriate name
	 */
	public static ExerciseType parse(String str) {
		for (ExerciseType xt : ExerciseType.values()) {
			if (xt.name.equals(str)) {
				return xt;
			}
		}
		throw new IllegalArgumentException("Exercise Type " + str
				+ " does not exist");
	}
}
