/**
 * 
 */
package edu.ncsu.csc.itrust.validate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import edu.ncsu.csc.itrust.beans.forms.EditPrescriptionsForm;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 *  
 *
 */
public class EditPrescriptionsValidator extends BeanValidator<EditPrescriptionsForm> {
	
	private String defaultInstructions;
	
	public EditPrescriptionsValidator(String defaultInstructions) {
		this.defaultInstructions = defaultInstructions;
	}
	
	/* (non-Javadoc)
	 * @see edu.ncsu.csc.itrust.validate.BeanValidator#validate(java.lang.Object)
	 */
	@Override
	public void validate(EditPrescriptionsForm form) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("Start Date", form.getStartDate(), ValidationFormat.DATE,
										   false));
		errorList.addIfNotNull(checkFormat("End Date", form.getEndDate(), ValidationFormat.DATE, false));
		errorList.addIfNotNull(checkFormat("Instructions", form.getInstructions(),
										   ValidationFormat.NOTES, false));
		errorList.addIfNotNull(form.getInstructions().equals(defaultInstructions) ? 
							    "Instructions: " + ValidationFormat.NOTES.getDescription() : "");
		errorList.addIfNotNull(checkInt("Dosage", form.getDosage(), 0, 9999, false));
		
		if ((checkFormat("Start Date", form.getStartDate(), ValidationFormat.DATE, false)).equals("")
				&& (checkFormat("End Date", form.getEndDate(), ValidationFormat.DATE, false)).equals("")) {
			Date sd = null;
			Date ed = null;
			try {
				sd = new SimpleDateFormat("MM/dd/yyyy").parse(form.getStartDate());
				ed = new SimpleDateFormat("MM/dd/yyyy").parse(form.getEndDate());
				String dateError = null;
				if (sd.after(ed)) {
					dateError = "The start date of the prescription must be before the end date.";
				}
				errorList.addIfNotNull(dateError);
			} catch (ParseException e) {
				errorList.addIfNotNull(ValidationFormat.DATE.getDescription());
			}

		}
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);

	}

}
