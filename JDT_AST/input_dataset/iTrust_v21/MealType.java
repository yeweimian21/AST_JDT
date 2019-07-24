package edu.ncsu.csc.itrust.enums;

/**
 * MealType.java
 * Version 1
 * 2/21/2015
 * Copyright notice: none
 * 
 * All of the different types of meals that can be entered
 * into the food diary.
 */
public enum MealType {
	/** Meal was eaten for breakfast */
	Breakfast("Breakfast"), 
	/** Meal was eaten for lunch */
	Lunch("Lunch"), 
	/** Meal was eaten as a snack */
	Snack("Snack"), 
	/** Meal was eaten for dinner */
	Dinner("Dinner");
	
	private String name;
	
	/**
	 * Sets the name of the meal
	 * @param name the name of the meal
	 */
	private MealType(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of the meal
	 * @return the name of the meal
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the name of the meal
	 * @return the name of the meal
	 */
	public String toString() {
		return getName();
	}
	
	/**
	 * Tries to parse a string into one of the 4 valid meal types.
	 * Throws an illegalargumentexception if the string is not one of the 
	 * given meal types
	 * @param str the name of the meal
	 * @return a MealType with the appropriate name
	 */
	public static MealType parse(String str) {
		for (MealType meal : MealType.values()) {
			if (meal.name.equals(str)) {
				return meal;
			}
		}
		throw new IllegalArgumentException("Meal Type " + str 
				+ " does not exist");
	}
}
