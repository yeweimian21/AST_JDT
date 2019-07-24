package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.action.EditOfficeVisitAction;
import edu.ncsu.csc.itrust.beans.forms.EditOfficeVisitForm;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Used to validate updating an office visit, by {@link EditOfficeVisitAction}
 * 
 *  
 * 
 */
public class EditOfficeVisitValidator extends BeanValidator<EditOfficeVisitForm> {

	/**
	 * The default constructor.
	 */
	public EditOfficeVisitValidator() {
	}

	/**
	 * Performs the act of validating the bean in question, which varies depending on the
	 * type of validator.  If the validation does not succeed, a {@link FormValidationException} is thrown.
	 * 
	 * @param p A bean of the type to be validated.
	 */
	@Override
	public void validate(EditOfficeVisitForm form) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("HCP ID", form.getHcpID(), ValidationFormat.MID, false));
		errorList.addIfNotNull(checkFormat("Hospital ID", form.getHospitalID(), ValidationFormat.HOSPITAL_ID,
				true));
		errorList.addIfNotNull(checkFormat("Notes", form.getNotes(), ValidationFormat.NOTES, true));
		errorList.addIfNotNull(checkFormat("Patient ID", form.getPatientID(), ValidationFormat.MID, false));
		errorList.addIfNotNull(checkFormat("Visit Date", form.getVisitDate(), ValidationFormat.DATE, false));
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}
