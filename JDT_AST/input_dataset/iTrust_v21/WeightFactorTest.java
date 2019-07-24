package edu.ncsu.csc.itrust.unit.risk.factors;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.risk.factors.PatientRiskFactor;
import edu.ncsu.csc.itrust.risk.factors.WeightFactor;

public class WeightFactorTest extends TestCase {
	private PatientRiskFactor factor;
	private HealthRecord hr;

	@Override
	protected void setUp() throws Exception {
		hr = new HealthRecord();
		factor = new WeightFactor(hr, 28);
		hr.setHeight(71);
		hr.setWeight(200);
	}

	public void testAllGood() throws Exception {
		assertFalse(factor.hasRiskFactor());
	}

	public void testGainFivePounds() throws Exception {
		hr.setWeight(205);
		assertTrue(factor.hasRiskFactor());
	}

	public void testShrink2Inches() throws Exception {
		hr.setHeight(69);
		assertTrue(factor.hasRiskFactor());
	}

	// Rationale: can't say they have the risk factor if the info is missing
	public void testZeroHeight() throws Exception {
		hr.setHeight(0);
		assertFalse(factor.hasRiskFactor());
	}
}
