package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.beans.OphthalmologyDiagnosisBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validates an ICD9CM code
 * 
 *  
 * 
 */
public class OphthalmologyDiagnosisBeanValidator extends BeanValidator<OphthalmologyDiagnosisBean> {
	/**
	 * The default constructor.
	 */
	public OphthalmologyDiagnosisBeanValidator() {
	}

	/**
	 * Performs the act of validating the bean in question, which varies depending on the
	 * type of validator.  If the validation does not succeed, a {@link FormValidationException} is thrown.
	 * 
	 * @param p A bean of the type to be validated.
	 */
	@Override
	public void validate(OphthalmologyDiagnosisBean d) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("ICD9CM Code", d.getICDCode(), ValidationFormat.ICD9CM, false));
		errorList.addIfNotNull(checkFormat("Description", d.getDescription(),
				ValidationFormat.ICD_CODE_DESCRIPTION, false));
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}

}
