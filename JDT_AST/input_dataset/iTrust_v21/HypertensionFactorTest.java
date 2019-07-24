package edu.ncsu.csc.itrust.unit.risk.factors;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.risk.factors.HypertensionFactor;
import edu.ncsu.csc.itrust.risk.factors.PatientRiskFactor;

public class HypertensionFactorTest extends TestCase {
	private PatientRiskFactor factor;
	private HealthRecord hr;

	@Override
	protected void setUp() throws Exception {
		hr = new HealthRecord();
		factor = new HypertensionFactor(hr);
		hr.setBloodPressureDiastolic(119);
		hr.setBloodPressureSystolic(239);
	}

	public void testAllGood() throws Exception {
		assertFalse(factor.hasRiskFactor());
	}

	public void testBadDiastolic() throws Exception {
		hr.setBloodPressureDiastolic(121);
		assertTrue(factor.hasRiskFactor());
	}

	public void testBadSystolic() throws Exception {
		hr.setBloodPressureSystolic(241);
		assertTrue(factor.hasRiskFactor());
	}
}
