package edu.ncsu.csc.itrust.unit.risk.factors;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.enums.Gender;
import edu.ncsu.csc.itrust.risk.factors.GenderFactor;
import edu.ncsu.csc.itrust.risk.factors.PatientRiskFactor;

public class GenderFactorTest extends TestCase {
	private PatientRiskFactor factor;
	private PatientBean p;

	@Override
	protected void setUp() throws Exception {
		p = new PatientBean();
		factor = new GenderFactor(p, Gender.Male);
	}

	public void testGenderRisk() throws Exception {
		assertFalse(factor.hasRiskFactor());
		p.setGender(Gender.Female);
		factor = new GenderFactor(p, Gender.Female);
		assertTrue(factor.hasRiskFactor());
	}
	
	public void testNotSpecified() throws Exception {
		p.setGender(Gender.NotSpecified);
		factor = new GenderFactor(p, Gender.NotSpecified);
		assertFalse(factor.hasRiskFactor());
	}
}
