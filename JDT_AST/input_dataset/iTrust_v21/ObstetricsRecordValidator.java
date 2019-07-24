package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.beans.ObstetricsRecordBean;
import edu.ncsu.csc.itrust.enums.PregnancyStatus;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * The validator used by {@link AddObstetricsAction}. 
 */
public class ObstetricsRecordValidator extends BeanValidator<ObstetricsRecordBean> {
	/**
	 * The default constructor.
	 */
	public ObstetricsRecordValidator() {
		
	}

	/**
	 * Performs the act of validating the bean in question, which varies depending on the
	 * type of validator.  If the validation does not succeed, a {@link FormValidationException} is thrown.
	 * 
	 * @param p A bean of the type to be validated.
	 */
	@Override
	public void validate(ObstetricsRecordBean p) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		if (p != null) {
			//if Initial record
			if (p.getPregnancyStatus().equals(PregnancyStatus.Initial)) {
				if (p.getLmp() != null && p.getDateVisit() != null && p.getLmp().after(p.getDateVisit())) {
					errorList.addIfNotNull("Last menstrual period cannot be after Date of visit");
				}
				errorList.addIfNotNull(checkFormat("Weeks Pregnant", p.getWeeksPregnant(), ValidationFormat.WEEKS_PREGNANT, false));
			}
			//else, if office visit
			else if (p.getPregnancyStatus().equals(PregnancyStatus.Office)){
				if (p.getDateVisit() == null || p.getDateVisitString().equals("")) {
					errorList.addIfNotNull("Date Visit is a required field");
				}
				//note that this weeks pregnant != the initial weeks pregnant b/c can be up to 49 weeks
				errorList.addIfNotNull(checkFormat("Weeks Pregnant", p.getWeeksPregnant(), ValidationFormat.WEEKS_PREGNANT_OV, false));
				errorList.addIfNotNull(checkFormat("Weight", p.getWeight(), ValidationFormat.WEIGHT, false));
				errorList.addIfNotNull(checkFormat("Blood Pressure Systolic", p.getBloodPressureS(), ValidationFormat.SYSTOLIC_BLOOD_PRESSURE, false));
				errorList.addIfNotNull(checkFormat("Blood Pressure Diastolic", p.getBloodPressureD(), ValidationFormat.DIASTOLIC_BLOOD_PRESSURE, false));
				errorList.addIfNotNull(checkFormat("FHR", p.getFhr(), ValidationFormat.FHR, false));
				errorList.addIfNotNull(checkFormat("FHU", p.getFhu(), ValidationFormat.FHU, false));
			}
			else {
				if (p.getDateVisit() == null || p.getDateVisitString().equals("")) {
					errorList.addIfNotNull("Date Visit is a required field");
				}
				errorList.addIfNotNull(checkFormat("Year Conception", p.getYearConception(), ValidationFormat.YEAR, false));
				errorList.addIfNotNull(checkFormat("Weeks Pregnant", p.getWeeksPregnant(), ValidationFormat.WEEKS_PREGNANT, false));
				errorList.addIfNotNull(checkFormat("Hours in Labor", p.getHoursInLabor(), ValidationFormat.HOURS_LABOR, false));
			}
			
			if (errorList.hasErrors())
				throw new FormValidationException(errorList);
		}
	}
}
