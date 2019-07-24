package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.beans.forms.RecordsReleaseForm;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;


public class RecordsReleaseFormValidator extends BeanValidator<RecordsReleaseForm> {
	
	/**
	 * Performs the act of validating the bean in question, which varies depending on the
	 * type of validator.  If the validation does not succeed, a {@link FormValidationException} is thrown.
	 * 
	 * @param p A bean of the type to be validated.
	 */
	@Override
	public void validate(RecordsReleaseForm bean) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("Release Hospital ID", bean.getReleaseHospitalID(), ValidationFormat.HOSPITAL_ID, false));
		errorList.addIfNotNull(checkFormat("Recipient hospital name", bean.getRecipientHospitalName(), ValidationFormat.HOSPITAL_NAME, false));
		errorList.addIfNotNull(checkFormat("Recipient hospital address", bean.getRecipientHospitalAddress(), ValidationFormat.FULL_ADDRESS, false));
		errorList.addIfNotNull(checkFormat("Doctor's first name", bean.getRecipientFirstName(), ValidationFormat.NAME, false));
		errorList.addIfNotNull(checkFormat("Doctor's last name", bean.getRecipientLastName(), ValidationFormat.NAME, false));
		errorList.addIfNotNull(checkFormat("Doctor's phone number", bean.getRecipientPhone(), ValidationFormat.PHONE_NUMBER, false));
		errorList.addIfNotNull(checkFormat("Doctor's email address", bean.getRecipientEmail(), ValidationFormat.EMAIL, false));
		errorList.addIfNotNull(checkFormat("Release request justification", bean.getRequestJustification(), ValidationFormat.NOTES, true));
		errorList.addIfNotNull(checkBoolean("Digital Signature", bean.getDigitalSignature().toString()));
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
	

}