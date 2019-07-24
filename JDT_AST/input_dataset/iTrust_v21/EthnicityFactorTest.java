package edu.ncsu.csc.itrust.unit.risk.factors;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.enums.Ethnicity;
import edu.ncsu.csc.itrust.risk.factors.EthnicityFactor;
import edu.ncsu.csc.itrust.risk.factors.PatientRiskFactor;

public class EthnicityFactorTest extends TestCase {
	private PatientRiskFactor factor;
	private PatientBean p;

	@Override
	protected void setUp() throws Exception {
		p = new PatientBean();
		p.setEthnicity(Ethnicity.Asian);
	}

	public void testSingleEthnicity() throws Exception {
		factor = new EthnicityFactor(p, Ethnicity.Caucasian);
		assertFalse(factor.hasRiskFactor());
		factor = new EthnicityFactor(p, Ethnicity.Asian);
		assertTrue(factor.hasRiskFactor());
	}

	public void testMultipleEthnicities() throws Exception {
		factor = new EthnicityFactor(p, Ethnicity.Caucasian, Ethnicity.AfricanAmerican);
		assertFalse(factor.hasRiskFactor());
		factor = new EthnicityFactor(p, Ethnicity.Caucasian, Ethnicity.AfricanAmerican, Ethnicity.Asian);
		assertTrue(factor.hasRiskFactor());
	}

	// May not make sense here, but this shows what the method does.
	public void testNoEthnicities() throws Exception {
		factor = new EthnicityFactor(p); // Why check for a non-existent risk?
		assertFalse(factor.hasRiskFactor());
	}
	
	public void testNotSpecified() throws Exception {
		p.setEthnicity(Ethnicity.NotSpecified);
		factor = new EthnicityFactor(p, Ethnicity.NotSpecified); 
		assertFalse(factor.hasRiskFactor());
	}
}
