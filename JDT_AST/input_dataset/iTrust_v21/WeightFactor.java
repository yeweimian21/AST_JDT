package edu.ncsu.csc.itrust.risk.factors;

import edu.ncsu.csc.itrust.beans.HealthRecord;

/**
 * Checks the patient's body mass index over the given threshold
 * See {@link PatientRiskFactor} for details on what each method typically does.
 * 
 *  
 * 
 */
public class WeightFactor extends PatientRiskFactor {
	private HealthRecord currentHealthRecord;
	private int bmiThreshold;

	public WeightFactor(HealthRecord currentHealthRecord, int bmiThreshold) {
		this.currentHealthRecord = currentHealthRecord;
		this.bmiThreshold = bmiThreshold;
	}

	@Override
	public String getDescription() {
		return "Patient's body mass index is over " + bmiThreshold;
	}

	@Override
	public boolean hasFactor() {
		double bmi = currentHealthRecord.getBodyMassIndex();
		return bmi > bmiThreshold && !Double.isInfinite(bmi);
	}
}
