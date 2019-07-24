package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.beans.OverrideReasonBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validates reason code beans, from {@link UpdateReasonCodeListAction}
 * 
 */
public class OverrideReasonBeanValidator extends BeanValidator<OverrideReasonBean> {
	/**
	 * The default constructor.
	 */
	public OverrideReasonBeanValidator() {
	}

	/**
	 * Performs the act of validating the bean in question, which varies depending on the
	 * type of validator.  If the validation does not succeed, a {@link FormValidationException} is thrown.
	 * 
	 * @param p A bean of the type to be validated.
	 */
	@Override
	public void validate(OverrideReasonBean orc) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("Reason Code", orc.getORCode(), ValidationFormat.ORC, false));
		errorList.addIfNotNull(checkFormat("Description", orc.getDescription(),
				ValidationFormat.OR_CODE_DESCRIPTION, false));
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}
