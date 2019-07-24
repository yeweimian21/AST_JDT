package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.beans.ApptTypeBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

public class ApptTypeBeanValidator extends BeanValidator<ApptTypeBean> {

	@Override
	public void validate(ApptTypeBean a) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("Appointment Type Name", a.getName(), ValidationFormat.APPT_TYPE_NAME, false));
		errorList.addIfNotNull(checkFormat("Appointment Type Duration", a.getDuration()+"", ValidationFormat.APPT_TYPE_DURATION, false));
		if(a.getDuration()<=0){
			errorList.addIfNotNull("Appointment duration must be greater than zero.");
		}
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}

}
