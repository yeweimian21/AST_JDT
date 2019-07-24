package edu.ncsu.csc.itrust.risk.factors;

import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.enums.Gender;

/**
 * Checks if the patient's gender matches the given at-risk one.
 * See {@link PatientRiskFactor} for details on what each method typically does.
 * 
 *  
 * 
 */
public class GenderFactor extends PatientRiskFactor {
	private PatientBean patient;
	private Gender gender;

	public GenderFactor(PatientBean patient, Gender gender) {
		this.patient = patient;
		this.gender = gender;
	}

	@Override
	public String getDescription() {
		return "Patient is " + patient.getGender().toString().toLowerCase();
	}

	@Override
	public boolean hasFactor() {
		return gender.equals(patient.getGender()) && !gender.equals(Gender.NotSpecified);
	}
}
