package edu.ncsu.csc.itrust.validate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ncsu.csc.itrust.beans.SleepEntryBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * SleepEntryValidator.java Version 1 4/6/2015 Copyright notice: none Ensures
 * that a sleep entry has only appropriate values before it is loaded into the
 * database
 */
public class SleepEntryValidator extends BeanValidator<SleepEntryBean> {

	/**
	 * Performs the act of validating the bean in question, which varies
	 * depending on the type of validator. If the validation does not succeed, a
	 * {@link FormValidationException} is thrown. The date performed must be of
	 * the form mm/dd/yyyy and it must be on or before today's date. The
	 * sleep type cannot be null and it must be either Weight Training or
	 * Cardio. The sleep name cannot be null. Hours must be greater than 0.
	 * 
	 * @param bean
	 *            The bean to be validated.
	 */
	@Override
	public void validate(SleepEntryBean bean) throws FormValidationException {
		ErrorList errorList = new ErrorList();

		errorList.addIfNotNull(checkFormat("Date Slept", bean.getStrDate().toString(),
				ValidationFormat.DATE, false));

		if ((checkFormat("Date Slept", bean.getStrDate(),
				ValidationFormat.DATE, false)).equals("")) {
			Date date = null;
			try {
				date = new SimpleDateFormat("MM/dd/yyyy").parse(bean.getStrDate());
				String dateError = null;
				if (date.after(new Date())) { // if it is after today's date
					dateError = "The Date Slept must be before or on "
							+ "today's Date.";
				}
				errorList.addIfNotNull(dateError);
			} catch (ParseException e) {
				errorList.addIfNotNull(ValidationFormat.DATE.getDescription());
			}
		}

		if (bean.getSleepType() == null) {
			errorList.addIfNotNull("Must enter a Sleep Type");
		}
		if (bean.getSleepType() != null) { // make sure we don't get a null
												// pointer
			errorList.addIfNotNull(checkFormat("Sleep Type", bean.getSleepType().getName(),
					ValidationFormat.SLEEPTYPE, false));
		}

		if (bean.getHoursSlept() <= 0) {
			errorList.addIfNotNull("Number of Hours must be "
					+ "greater than 0");
		}

		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}

	}

}
