package edu.ncsu.csc.itrust.unit.risk.factors;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.risk.factors.CholesterolFactor;
import edu.ncsu.csc.itrust.risk.factors.PatientRiskFactor;

public class CholesterolFactorTest extends TestCase {
	private PatientRiskFactor factor;
	private HealthRecord hr;

	@Override
	protected void setUp() throws Exception {
		hr = new HealthRecord();
		factor = new CholesterolFactor(hr);
		hr.setCholesterolHDL(36);
		hr.setCholesterolLDL(239);
		hr.setCholesterolTri(249);
	}

	public void testAllGood() throws Exception {
		assertFalse(factor.hasRiskFactor());
	}

	public void testBadHDL() throws Exception {
		hr.setCholesterolHDL(34);
		assertTrue(factor.hasRiskFactor());
	}

	public void testBadLDL() throws Exception {
		hr.setCholesterolLDL(241);
		assertTrue(factor.hasRiskFactor());
	}

	public void testBadTri() throws Exception {
		hr.setCholesterolTri(251);
		assertTrue(factor.hasRiskFactor());
	}
}
