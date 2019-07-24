package edu.ncsu.csc.itrust.beans;

public class TelemedicineBean {
	private boolean systolicBloodPressureAllowed = true;
	private boolean diastolicBloodPressureAllowed = true;
	private boolean glucoseLevelAllowed = true;
	private boolean heightAllowed = true;
	private boolean weightAllowed = true;
	private boolean pedometerReadingAllowed = true;
	
	public boolean isHeightAllowed() {
		return heightAllowed;
	}

	public void setHeightAllowed(boolean heightAllowed) {
		this.heightAllowed = heightAllowed;
	}

	public boolean isSystolicBloodPressureAllowed() {
		return systolicBloodPressureAllowed;
	}
	
	public void setSystolicBloodPressureAllowed(boolean systolicBloodPressureAllowed) {
		this.systolicBloodPressureAllowed = systolicBloodPressureAllowed;
	}
	
	public boolean isDiastolicBloodPressureAllowed() {
		return diastolicBloodPressureAllowed;
	}
	
	public void setDiastolicBloodPressureAllowed(boolean diastolicBloodPressureAllowed) {
		this.diastolicBloodPressureAllowed = diastolicBloodPressureAllowed;
	}
	
	public boolean isGlucoseLevelAllowed() {
		return glucoseLevelAllowed;
	}
	
	public void setGlucoseLevelAllowed(boolean glucoseLevelAllowed) {
		this.glucoseLevelAllowed = glucoseLevelAllowed;
	}
	
	public boolean isWeightAllowed() {
		return weightAllowed;
	}
	
	public void setWeightAllowed(boolean weightAllowed) {
		this.weightAllowed = weightAllowed;
	}
	
	public boolean isPedometerReadingAllowed() {
		return pedometerReadingAllowed;
	}
	
	public void setPedometerReadingAllowed(boolean pedometerReadingAllowed) {
		this.pedometerReadingAllowed = pedometerReadingAllowed;
	}
}
