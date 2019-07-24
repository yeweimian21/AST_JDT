package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

public class LabProcedureValidator extends BeanValidator<LabProcedureBean> {
	
	/**
	 * Performs the act of validating the bean in question, which varies depending on the
	 * type of validator.  If the validation does not succeed, a {@link FormValidationException} is thrown.
	 * 
	 * @param p A bean of the type to be validated.
	 */
	public void validate(LabProcedureBean b) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("LOINC", b.getLoinc(), ValidationFormat.LOINC, false));
		errorList.addIfNotNull(checkFormat("Commentary", b.getCommentary(), ValidationFormat.LABPROCEDURE_COMMENTS, true));
		errorList.addIfNotNull(checkFormat("Results", b.getResults(), ValidationFormat.LABPROCEDURE_COMMENTS, true));
		errorList.addIfNotNull(checkFormat("Status", b.getStatus(), ValidationFormat.LAB_STATUS, false));
		errorList.addIfNotNull(checkFormat("Rights", b.getRights(), ValidationFormat.LAB_RIGHTS, false));
		
		if (b.getNumericalResult()!=null && b.getNumericalResult().length() > 0) {
			errorList.addIfNotNull(checkFormat("Numerical Result", b.getNumericalResult(), 
											   ValidationFormat.LABPROCEDURE_NUMRESULT_CONTENT, 
											   false));
			errorList.addIfNotNull(checkFormat("Numerical Result", b.getNumericalResult(), 
											   ValidationFormat.LABPROCEDURE_NUMRESULT_LENGTH, 
											   false));
		}
		if (b.getUpperBound()!=null && b.getUpperBound().length() > 0) {
			errorList.addIfNotNull(checkFormat("Upper Bound", b.getUpperBound(), 
											   ValidationFormat.LABPROCEDURE_NUMRESULT_CONTENT, 
											   false));
			errorList.addIfNotNull(checkFormat("Upper Bound", b.getUpperBound(), 
											   ValidationFormat.LABPROCEDURE_NUMRESULT_LENGTH, 
											   false));
		}
		
		if (b.getLowerBound()!=null && b.getLowerBound().length() > 0) {
			errorList.addIfNotNull(checkFormat("Lower Bound", b.getLowerBound(), 
											   ValidationFormat.LABPROCEDURE_NUMRESULT_CONTENT, 
											   false));
			errorList.addIfNotNull(checkFormat("Lower Bound", b.getLowerBound(), 
											   ValidationFormat.LABPROCEDURE_NUMRESULT_LENGTH, 
											   false));
		}
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}

}
