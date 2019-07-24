package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.beans.OphthalmologyScheduleOVRecordBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validator class for OphthalmologyScheduleOVRecordBean. Used in order to verify that a beans
 * contents are consistent with the data format section of UC88.
 */
public class OphthalmologyScheduleOVValidator extends BeanValidator<OphthalmologyScheduleOVRecordBean> {
	
	/** Validates an scheduled Ophthalmology ov bean passed to it.
	 * @param bean The bean to be validated.
	 * @throws FormValidationException throws an exception as its way of saying that the bean passed is invalid.
	 */
	@Override
	public void validate(OphthalmologyScheduleOVRecordBean bean) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		if (bean != null) {
			//at this point, we can assume that all numerical fields have made it into the bean successfully,
			//we still need to check to make sure they are in the right ranges.
			errorList.addIfNotNull(checkFormat("Patient MID:", bean.getPatientmid(), ValidationFormat.MID, true));
			errorList.addIfNotNull(checkFormat("Patient MID:", bean.getDoctormid(), ValidationFormat.MID, true));
			
			
			if(bean.getDate() == null || bean.getDate().toString().equals("")){
				errorList.addIfNotNull("Date is a required field mm/dd/yyyy");
			}else{
				errorList.addIfNotNull(checkFormat("Visit Date:", bean.getDate().toString(), ValidationFormat.DATETIME, true));
			}
			errorList.addIfNotNull(checkFormat("Last Name:", bean.getDocLastName(), ValidationFormat.NAME, false));
			errorList.addIfNotNull(checkFormat("First Name:", bean.getDocFirstName(), ValidationFormat.NAME, false));
			
			//Commenting this out so that blank comments can be placed.
			//errorList.addIfNotNull(checkFormat("Appointment Comment", bean.getComment(), ValidationFormat.APPT_COMMENT, false));
		} else {
			errorList.addIfNotNull("Bean is null.");
		}
		
		if (errorList.hasErrors()){
			throw new FormValidationException(errorList);
		}
	}
}
