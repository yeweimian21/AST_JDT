package edu.ncsu.csc.itrust.validate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ncsu.csc.itrust.beans.FoodEntryBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * FoodEntryValidator.java
 * Version 1
 * 2/21/2015
 * Copyright notice: none
 * Ensures that a food entry has only appropriate values before it is loaded
 * into the database
 */
public class FoodEntryValidator extends BeanValidator<FoodEntryBean> {
	
	/**
	 * Performs the act of validating the bean in question, which varies 
	 * depending on the type of validator.  If the validation does 
	 * not succeed, a {@link FormValidationException} is thrown.
	 * The date eaten must be of the form mm/dd/yyyy and it must be on or
	 * before today's date. The meal type cannot be null and it must be either
	 * Breakfast, Lunch, Snack, or Dinner. The food name cannot be null.
	 * Servings must be greater than 0. All of the rest of the grams
	 * and milligrams must be greater than or equal to 0.
	 * @param p A bean of the type to be validated.
	 */
	@Override
	public void validate(FoodEntryBean bean) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		
		errorList.addIfNotNull(checkFormat("Date Eaten", 
				bean.getDateEatenStr().toString(), 
				ValidationFormat.DATE, false));
		
		if ((checkFormat("Date Eaten", bean.getDateEatenStr(), 
					ValidationFormat.DATE, false)).equals("")) {
			Date dateEaten = null;
			try {
				dateEaten = new SimpleDateFormat("MM/dd/yyyy")
						.parse(bean.getDateEatenStr());
				String dateError = null;
				if (dateEaten.after(new Date())) { //if it is after today's date
					dateError = "The Date Eaten must be before or on "
							+ "today's Date.";
				}
				errorList.addIfNotNull(dateError);
			} catch (ParseException e) {
				errorList.addIfNotNull(ValidationFormat.DATE.getDescription());
			}
		}
		if (bean.getMealType() == null) {
			errorList.addIfNotNull("Must enter a Meal Type");
		}
		if (bean.getMealType() != null) { //make sure we don't get a null pointer
			errorList.addIfNotNull(checkFormat("Meal Type",
					bean.getMealType().getName(), 
					ValidationFormat.MEALTYPE, false));
		}
		if (bean.getFood() == null || bean.getFood().isEmpty()) {
			errorList.addIfNotNull("Must enter the Name of the Food");
		}
		if (bean.getServings() <= 0) {
			errorList.addIfNotNull("Number of Servings must be "
					+ "greater than 0");
		}
		if (bean.getCalories() < 0) {
			errorList.addIfNotNull("Calories per Serving cannot "
					+ "be negative");
		}
		if (bean.getFatGrams() < 0) {
			errorList.addIfNotNull("Grams of Fat per Serving cannot "
					+ "be negative");
		}
		if (bean.getMilligramsSodium() < 0) {
			errorList.addIfNotNull("Milligrams of Sodium per Serving cannot "
					+ "be negative");
		}
		if (bean.getCarbGrams() < 0) {
			errorList.addIfNotNull("Grams of Carbs per Serving cannot "
					+ "be negative");
		}
		if (bean.getSugarGrams() < 0) {
			errorList.addIfNotNull("Grams of Sugars per Serving cannot "
					+ "be negative");
		}
		if (bean.getFiberGrams() < 0) {
			errorList.addIfNotNull("Grams of Fiber per Serving cannot "
					+ "be negative");
		}
		if (bean.getProteinGrams() < 0) {
			errorList.addIfNotNull("Grams of Protein per Serving cannot "
					+ "be negative");
		}
		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}
		
	}

}
