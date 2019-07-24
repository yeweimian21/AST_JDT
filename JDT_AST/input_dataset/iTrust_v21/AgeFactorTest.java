package edu.ncsu.csc.itrust.unit.risk.factors;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.risk.factors.AgeFactor;
import edu.ncsu.csc.itrust.risk.factors.PatientRiskFactor;

public class AgeFactorTest extends TestCase {
	private PatientRiskFactor factor;
	private PatientBean p;

	@Override
	protected void setUp() throws Exception {
		p = new PatientBean();
		factor = new AgeFactor(p, 45);
	}

	public void testDefaultAge() throws Exception {
		assertFalse(factor.hasRiskFactor());
		factor = new AgeFactor(p, -1);
		assertTrue(factor.hasRiskFactor());
	}

	public void testRegularAge() throws Exception {
		p.setDateOfBirthStr(DateUtil.yearsAgo(44));
		assertFalse(factor.hasRiskFactor());
		p.setDateOfBirthStr(DateUtil.yearsAgo(46));
		factor = new AgeFactor(p, 45);
		assertTrue(factor.hasRiskFactor());
	}
}
