package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.action.UpdateHospitalListAction;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validates the input for hospital beans, {@link UpdateHospitalListAction}
 * 
 *  
 * 
 */
public class HospitalBeanValidator extends BeanValidator<HospitalBean> {
	/**
	 * The default constructor.
	 */
	public HospitalBeanValidator() {
		
	}

	/**
	 * Performs the act of validating the bean in question, which varies depending on the
	 * type of validator.  If the validation does not succeed, a {@link FormValidationException} is thrown.
	 * 
	 * @param p A bean of the type to be validated.
	 */
	@Override
	public void validate(HospitalBean h) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("Hospital ID", h.getHospitalID(), ValidationFormat.HOSPITAL_ID, false));
		errorList.addIfNotNull(checkFormat("Hospital Name", h.getHospitalName(), ValidationFormat.HOSPITAL_NAME, false));
		
		if(!h.getHospitalAddress().isEmpty()){
			errorList.addIfNotNull(checkFormat("Hospital Address", h.getHospitalAddress(), ValidationFormat.ADDRESS, false));
		}
		
		if(!h.getHospitalCity().isEmpty()){
			errorList.addIfNotNull(checkFormat("Hospital City", h.getHospitalCity(), ValidationFormat.CITY, false));
		}
		
		if(!h.getHospitalState().isEmpty()){
			errorList.addIfNotNull(checkFormat("Hospital State", h.getHospitalState(), ValidationFormat.STATE, false));
		}
		
		if(!h.getHospitalZip().isEmpty()){
			errorList.addIfNotNull(checkFormat("Hospital Zip", h.getHospitalZip(), ValidationFormat.ZIPCODE, false));
		}
		
		if (errorList.hasErrors()) {
			throw new FormValidationException(errorList);
		}
	}

}
