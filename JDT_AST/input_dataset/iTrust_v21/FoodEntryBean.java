package edu.ncsu.csc.itrust.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ncsu.csc.itrust.enums.MealType;

/**
 * FoodEntryBean.java
 * Version 1
 * 2/21/2015
 * Copyright notice: none
 * Contains all of the information for an entry into the Food Diary.
 */
public class FoodEntryBean extends EntryBean {
	
	/**
	 * Unique Primary key so entries can be edited and deleted
	 */
	private long entryID;
	
	/**
	 * The Date this food was eaten
	 */
	private String dateEatenStr = new SimpleDateFormat("MM/dd/yyyy")
								.format(new Date());
	
	/**
	 * Which meal this was (Lunch, Dinner, Snack, Breakfast)
	 */
	private MealType mealType;
	
	/**
	 * The name of the food.
	 */
	private String food;
	
	/**
	 * How many servings
	 */
	private double servings;
	
	/**
	 * How many calories per serving
	 */
	private double calories;
	
	/**
	 * How many grams of fat
	 */
	private double fatGrams;
	
	/**
	 * How many milligrams of sodium
	 */
	private double milligramsSodium;
	
	/**
	 * How many grams of carbs
	 */
	private double carbGrams;
	
	/**
	 * How many grams of sugar
	 */
	private double sugarGrams;
	
	/**
	 * How many grams of fiber
	 */
	private double fiberGrams;
	
	/**
	 * How many grams of protein
	 */
	private double proteinGrams;
	
	/**
	 * The MID of the user this Food Entry belongs to
	 */
	private long patientID;

	/**
	 * EntryID of the label belonging to this entry
	 */
	private long labelID;
	
	/**
	 * Returns the id of this entry so it can be edited/deleted.
	 * @return unique id of the food entry
	 */
	public long getEntryID() {
		return entryID;
	}
	
	/**
	 * Sets the id of this entry
	 * @param id the unique id of a food entry
	 */
	public void setEntryID(long id) {
		entryID = id;
	}
	
	/**
	 * Returns a string representation of when the food was eaten
	 * @return string representation of the date the food was eaten on
	 */
	public String getDateEatenStr() {
		return dateEatenStr;
	}

	/**
	 * Parses the dateEatenStr to produce a date in the format
	 * MM/dd/yyyy
	 * @return the date the food was eaten on
	 */
	public Date getDateEaten() {
		try {
			return new SimpleDateFormat("MM/dd/yyyy").parse(dateEatenStr);
		} catch (ParseException e) {
			
			return null;
		}
	}
	
	/**
	 * Sets the dateEaten as a string
	 * @param dateEaten when the food was eaten
	 */
	public void setDateEatenStr(String dateEaten) {
		this.dateEatenStr = dateEaten;
	}

	/**
	 * Which type of meal (Breakfast, Lunch, Snack, Dinner) was eaten
	 * @return the type of meal
	 */
	public MealType getMealType() {
		return mealType;
	}

	/**
	 * Sets the meal type
	 * @param mealType what type of meal was it
	 */
	public void setMealType(String mealType) {
		this.mealType = MealType.parse(mealType);
	}

	/**
	 * What was the name of the food
	 * @return the name of the food
	 */
	public String getFood() {
		return food;
	}

	/**
	 * Set the name of the food
	 * @param food the name of the food that was eaten
	 */
	public void setFood(String food) {
		this.food = food;
	}

	/**
	 * How many servings were eaten
	 * @return number of servings eaten
	 */
	public double getServings() {
		return servings;
	}

	/**
	 * Set the number of servings
	 * @param servings number of servings eaten
	 */
	public void setServings(double servings) {
		this.servings = servings;
	}

	/**
	 * How many calories per serving were eaten
	 * @return calories per serving
	 */
	public double getCalories() {
		return calories;
	}
	
	/**
	 * How many calories were eaten
	 * @param calories calories per serving
	 */
	public void setCalories(double calories) {
		this.calories = calories;
	}

	/**
	 * The number of grams of fat per serving
	 * @return grams of fat per serving
	 */
	public double getFatGrams() {
		return fatGrams;
	}

	/**
	 * How many grams of fat per serving
	 * @param fatGrams grams of fat per serving
	 */
	public void setFatGrams(double fatGrams) {
		this.fatGrams = fatGrams;
	}

	/**
	 * The  number of milligrams of sodium per serving
	 * @return number of milligrams of sodium per serving
	 */
	public double getMilligramsSodium() {
		return milligramsSodium;
	}

	/**
	 * Number of milligrams of sodium per serving
	 * @param milligramsSodium number milligrams of sodium per serving
	 */
	public void setMilligramsSodium(double milligramsSodium) {
		this.milligramsSodium = milligramsSodium;
	}

	/**
	 * Number of grams of carbs per serving
	 * @return grams of carbs per serving
	 */
	public double getCarbGrams() {
		return carbGrams;
	}

	/**
	 * Number of grams of carbs per serving
	 * @param carbGrams grams of carbs per serving
	 */
	public void setCarbGrams(double carbGrams) {
		this.carbGrams = carbGrams;
	}

	/**
	 * Number of grams of sugar per serving
	 * @return grams of sugar per serving
	 */
	public double getSugarGrams() {
		return sugarGrams;
	}

	/**
	 * Number of grams of sugar per serving
	 * @param sugarGrams grams of sugar per serving
	 */
	public void setSugarGrams(double sugarGrams) {
		this.sugarGrams = sugarGrams;
	}

	/**
	 * Number of grams of fiber per serving
	 * @return grams of fiber per serving
	 */
	public double getFiberGrams() {
		return fiberGrams;
	}

	/**
	 * Number of grams of fiber per serving
	 * @param fiberGrams grams of fiber per serving
	 */
	public void setFiberGrams(double fiberGrams) {
		this.fiberGrams = fiberGrams;
	}

	/**
	 * The number of grams of protein per serving
	 * @return grams of protein per serving
	 */
	public double getProteinGrams() {
		return proteinGrams;
	}

	/**
	 * Number of grams of protein per serving
	 * @param proteinGrams grams of protein per serving
	 */
	public void setProteinGrams(double proteinGrams) {
		this.proteinGrams = proteinGrams;
	}
	
	/**
	 * The patient that ate this meal
	 * @return patient ID that ate this meal
	 */
	public long getPatientID() {
		return patientID;
	}
	
	/**
	 * Patient that ate this meal
	 * @param patientID patient id of who ate this meal
	 */
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}
	
	/**
	 * Label of this meal
	 * @return label of this meal
	 */
	public long getLabelID() {
		return labelID;
	}
	
	/**
	 * Label of this meal
	 * @param label of this meal
	 */
	public void setLabelID(long labelID) {
		this.labelID = labelID;
	}
}
