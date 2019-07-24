package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.action.AddRemoteMonitoringDataAction;
import edu.ncsu.csc.itrust.beans.RemoteMonitoringDataBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validator used to validate adding new remote monitoring data in {@link AddRemoteMonitoringDataAction}
 * 
 */
public class RemoteMonitoringDataBeanValidator extends BeanValidator<RemoteMonitoringDataBean> {
	/**
	 * The default constructor.
	 */
	public RemoteMonitoringDataBeanValidator() {}
	
	/**
	 * Performs the act of validating the bean in question, which varies depending on the
	 * type of validator.  If the validation does not succeed, a {@link FormValidationException} is thrown.
	 * 
	 * @param p A bean of the type to be validated.
	 */
	@Override
	public void validate(RemoteMonitoringDataBean m) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		
		// Skip validation if values were not submitted (0 or -1)
		if (!(m.getSystolicBloodPressure() == 0 || m.getSystolicBloodPressure() == -1))
			errorList.addIfNotNull(checkFormat("Systolic Blood Pressure", "" + m.getSystolicBloodPressure(),
					ValidationFormat.SYSTOLIC_BLOOD_PRESSURE, true));
		if (!(m.getDiastolicBloodPressure() == 0 || m.getDiastolicBloodPressure() == -1))
			errorList.addIfNotNull(checkFormat("Diastolic Blood Pressure", "" + m.getDiastolicBloodPressure(),
					ValidationFormat.DIASTOLIC_BLOOD_PRESSURE, true));
		if (!(m.getGlucoseLevel() == 0 || m.getGlucoseLevel() == -1))
			errorList.addIfNotNull(checkFormat("Glucose Level", "" + m.getGlucoseLevel(),
					ValidationFormat.GLUCOSE_LEVEL, true));
		if (!(m.getPedometerReading() == 0 || m.getPedometerReading() == -1))
			errorList.addIfNotNull(checkFormat("Pedometer Reading", "" + m.getPedometerReading(),
					ValidationFormat.PEDOMETER_READING, true));
		if (!(m.getHeight() == 0 || m.getHeight() == -1))
			errorList.addIfNotNull(checkFormat("Height", "" + m.getHeight(),
					ValidationFormat.HEIGHT, true));
		if (!(m.getWeight() == 0 || m.getWeight() == -1))
			errorList.addIfNotNull(checkFormat("Weight", "" + m.getWeight(),
					ValidationFormat.WEIGHT, true));
		if (errorList.hasErrors()){
			throw new FormValidationException(errorList);
		}
	}
}
