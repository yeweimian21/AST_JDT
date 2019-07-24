package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.beans.OphthalmologySurgeryRecordBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ErrorList;

 /**
 * Validator class for OphthalmologySurgeryActionBean. Used in order to verify that a beans
 * contents are consistent with the data format section of UC83.
 */
public class OphthalmologySurgeryValidator extends BeanValidator<OphthalmologySurgeryRecordBean> {

	/** Validates an Ophthalmology bean passed to it.
	 * @param bean The bean to be validated.
	 * @throws FormValidationException throws an exception as its way of saying that the bean passed is invalid.
	 */
	@Override
	public void validate(OphthalmologySurgeryRecordBean bean) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		if (bean != null) {
			//at this point, we can assume that all numerical fields have made it into the bean successfully,
			//we still need to check to make sure they are in the right ranges.
			
			if(bean.getVisitDateString() == null || bean.getVisitDateString().equals("")){
				errorList.addIfNotNull("Date is a required field");
			}
			errorList.addIfNotNull(checkFormat("Visit Date:", bean.getVisitDateString(), ValidationFormat.DATE, true));
			
			errorList.addIfNotNull(checkFormat("Last Name:", bean.getLastName(), ValidationFormat.NAME, false));
			errorList.addIfNotNull(checkFormat("First Name:", bean.getFirstName(), ValidationFormat.NAME, false));
			
			if(bean.getVaNumOD() == null || (bean.getVaNumOD() <= 0)){ //if it was never changed, it will be 0, which will be caught here.
				errorList.addIfNotNull("Visual Acuity Numerator OD is required, and must be a positive integer");
			}
			
			if(bean.getVaDenOD() == null || (bean.getVaDenOD() <= 0)){ //if it was never changed, it will be 0, which will be caught here.
				errorList.addIfNotNull("Visual Acuity Denumerator OD is required, and must be a positive integer");
			}
			
			if(bean.getVaNumOS() == null || (bean.getVaNumOS() <= 0)){ //if it was never changed, it will be 0, which will be caught here.
				errorList.addIfNotNull("Visual Acuity Numerator OS is required, and must be a positive integer");
			}
			
			if(bean.getVaDenOS() == null || (bean.getVaDenOS() <= 0)){ //if it was never changed, it will be 0, which will be caught here.
				errorList.addIfNotNull("Visual Acuity Numerator OS is required, and must be a positive integer");
			}
			
			if(bean.getSphereOD() == null || (bean.getSphereOD() > 10.00 || bean.getSphereOD() < -10.00 || bean.getSphereOD() % .25 != 0)){ //if it was never changed, it will be 0. That's okay. The logic to prevent the user from not entering the value is in the JSP, as it should be.
				errorList.addIfNotNull("SphereOD is required, must be between -10.00 and 10.00 inclusive, and must be rounded to the nearest quarter diopter");
			}
			
			if(bean.getSphereOS() == null || (bean.getSphereOS() > 10.00 || bean.getSphereOD() < -10.00 || bean.getSphereOD() % .25 != 0)){ //if it was never changed, it will be 0. That's okay. The logic to prevent the user from not entering the value is in the JSP, as it should be.
				errorList.addIfNotNull("SphereOS is required, must be between -10.00 and 10.00 inclusive, and must be rounded to the nearest quarter diopter");
			}
			
			if(bean.getCylinderOD() != null && (bean.getCylinderOD() > 10.00 || bean.getCylinderOD() < -10.00 || bean.getCylinderOD() % .25 != 0)){ //if it was never changed, it will be 0. That's okay. The logic to prevent the user from not entering the value is in the JSP, as it should be.
				errorList.addIfNotNull("CylinderOD is required, must be between -10.00 and 10.00 inclusive, and must be rounded to the nearest quarter diopter");
			}
			
			if(bean.getCylinderOS() != null && (bean.getCylinderOS() > 10.00 || bean.getCylinderOS() < -10.00 || bean.getCylinderOS() % .25 != 0)){ //if it was never changed, it will be 0. That's okay. The logic to prevent the user from not entering the value is in the JSP, as it should be.
				errorList.addIfNotNull("CylinderOS is required, must be between -10.00 and 10.00 inclusive, and must be rounded to the nearest quarter diopter");
			}
			
			if(bean.getCylinderOD() != null){ // we have a cylOD
				if(bean.getAxisOD() == null){
					errorList.addIfNotNull("AxisOD is required if a CylinderOD is given, and must be between 1 and 180 inclusive");
				} else if(bean.getAxisOD() > 180 || bean.getAxisOD() < 1){
					errorList.addIfNotNull("AxisOD is required if a CylinderOD is given, and must be between 1 and 180 inclusive");
				}
			} else { //if we don't have a cylinder
				if(bean.getAxisOD() != null && (bean.getAxisOD() > 180 || bean.getAxisOD() < 1)){ //if we DO have one, and it isn't right
					errorList.addIfNotNull("AxisOD is required if a CylinderOD is given, and must be between 1 and 180 inclusive");
				}
			}
			
			if(bean.getCylinderOS() != null){ // we have a cylOS
				if(bean.getAxisOS() == null){
					errorList.addIfNotNull("AxisOS is required if a CylinderOS is given, and must be between 1 and 180 inclusive");
				} else if(bean.getAxisOS() > 180 || bean.getAxisOS() < 1){
					errorList.addIfNotNull("AxisOS is required if a CylinderOS is given, and must be between 1 and 180 inclusive");
				}
			} else { //if we don't have a cylinder
				if(bean.getAxisOS() != null && (bean.getAxisOS() > 180 || bean.getAxisOS() < 1)){ //if we DO have one, and it isn't right
					errorList.addIfNotNull("AxisOS is required if a CylinderOD is given, and must be between 1 and 180 inclusive");
				}
			}
			
			if(bean.getAddOD() == null || (bean.getAddOD() > 3.00 || bean.getAddOD() < .75 || bean.getAddOD() % 0.25 != 0)){
				errorList.addIfNotNull("AddOD is required, must be between 0.75 and 3.00 inclusive, and must be rounded to the nearest quarter diopter");
			}
			
			if(bean.getAddOS() == null || (bean.getAddOS() > 3.00 || bean.getAddOS() < .75 || bean.getAddOS() % 0.25 != 0)){
				errorList.addIfNotNull("AddOS is required, must be between 0.75 and 3.00 inclusive, and must be rounded to the nearest quarter diopter");
			}
			

		} else {
			errorList.addIfNotNull("Bean is null.");
		}
		
		if (errorList.hasErrors()){
			throw new FormValidationException(errorList);
		}
	}
}