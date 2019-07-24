package edu.ncsu.csc.itrust.validate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ncsu.csc.itrust.beans.ExerciseEntryBean;
import edu.ncsu.csc.itrust.enums.ExerciseType;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * ExerciseEntryValidator.java Version 1 4/2/2015 Copyright notice: none Ensures
 * that a exercise entry has only appropriate values before it is loaded into the
 * database
 */
public class ExerciseEntryValidator extends BeanValidator<ExerciseEntryBean> {

	/**
	 * Performs the act of validating the bean in question, which varies
	 * depending on the type of validator. If the validation does not succeed, a
	 * {@link FormValidationException} is thrown. The date performed must be of
	 * the form mm/dd/yyyy and it must be on or before today's date. The
	 * exercise type cannot be null and it must be either Weight Training or
	 * Cardio. The exercise name cannot be null. Hours, calories, reps, and sets
	 * must be greater than 0.
	 * 
	 * @param bean
	 *            The bean to be validated.
	 */
	@Override
	public void validate(ExerciseEntryBean bean) throws FormValidationException {
		ErrorList errorList = new ErrorList();

		errorList.addIfNotNull(checkFormat("Date Performed", bean.getStrDate().toString(),
				ValidationFormat.DATE, false));

		if ((checkFormat("Date Performed", bean.getStrDate(),
				ValidationFormat.DATE, false)).equals("")) {
			Date date = null;
			try {
				date = new SimpleDateFormat("MM/dd/yyyy").parse(bean.getStrDate());
				String dateError = null;
				if (date.after(new Date())) { // if it is after today's date
					dateError = "The Date Performed must be before or on "
							+ "today's Date.";
				}
				errorList.addIfNotNull(dateError);
			} catch (ParseException e) {
				errorList.addIfNotNull(ValidationFormat.DATE.getDescription());
			}
		}

		if (bean.getExerciseType() == null) {
			errorList.addIfNotNull("Must enter a Exercise Type");
		}
		if (bean.getExerciseType() != null) { // make sure we don't get a null
												// pointer
			errorList.addIfNotNull(checkFormat("Exercise Type", bean.getExerciseType().getName(),
					ValidationFormat.EXERCISETYPE, false));
		}

		if (bean.getStrName() == null || bean.getStrName().isEmpty()) {
			errorList.addIfNotNull("Must enter the Name of the Exercise");
		}

		if (bean.getHoursWorked() <= 0) {
			errorList.addIfNotNull("Number of Hours must be "
					+ "greater than 0");
		}
		if (bean.getCaloriesBurned() <= 0) {
			errorList.addIfNotNull("Number of Calories Burned must be "
					+ "greater than 0");
		}
		
		// Only validate these if it's weight training.
		if (bean.getExerciseType() != null && bean.getExerciseType().equals(ExerciseType.Weights)) {
			if (bean.getNumSets() <= 0) {
				errorList.addIfNotNull("Number of Sets must be " + "greater than 0");
			}
			if (bean.getNumReps() <= 0) {
				errorList.addIfNotNull("Number of Reps must be " + "greater than 0");
			}
		}

		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}

	}

}
