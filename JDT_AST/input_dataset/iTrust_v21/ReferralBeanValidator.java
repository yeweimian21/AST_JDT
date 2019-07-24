package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.action.UpdateNDCodeListAction;
import edu.ncsu.csc.itrust.beans.ReferralBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validates an ND code, from {@link UpdateNDCodeListAction}
 * 
 *  
 * 
 */
public class ReferralBeanValidator extends BeanValidator<ReferralBean> {
	/**
	 * The default constructor.
	 */
	public ReferralBeanValidator() {
	}

	/**
	 * Performs the act of validating the bean in question, which varies depending on the
	 * type of validator.  If the validation does not succeed, a {@link FormValidationException} is thrown.
	 * 
	 * @param p A bean of the type to be validated.
	 */
	@Override
	public void validate(ReferralBean p) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("Notes", p.getReferralDetails(), ValidationFormat.REFERRAL_NOTES, false));
		errorList.addIfNotNull(checkFormat("Priority", Integer.toString(p.getPriority()), ValidationFormat.PRIORITY, false));
		errorList.addIfNotNull(checkFormat("Patient ID", p.getPatientID(), ValidationFormat.MID, false));
		errorList.addIfNotNull(checkFormat("Receiver ID", p.getReceiverID(), ValidationFormat.MID, false));		
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}

}
