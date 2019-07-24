package edu.ncsu.csc.itrust.unit.risk.factors;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.risk.factors.FamilyHistoryFactor;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class FamilyHistoryFactorTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private FamilyHistoryFactor factor;

	@Override
	protected void setUp() throws Exception {
		factor = new FamilyHistoryFactor(factory, 3L, 250.0, 251.0);
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hospitals();
		gen.hcp0();
		gen.icd9cmCodes();
		gen.family();
	}

	public void testJustMom() throws Exception {
		factor = new FamilyHistoryFactor(factory, 3L, 250.0, 251.0);
		assertTrue(factor.hasRiskFactor());
	}

	public void testJustSibling() throws Exception {
		factor = new FamilyHistoryFactor(factory, 6L, 250.0, 251.0);
		assertTrue(factor.hasRiskFactor());
	}

	public void testNobody() throws Exception {
		factor = new FamilyHistoryFactor(factory, 5L, 250.0, 251.0);
		assertFalse(factor.hasRiskFactor());
	}
	
	public void testDBException() throws Exception {
		this.factory = EvilDAOFactory.getEvilInstance();
		factor = new FamilyHistoryFactor(factory, 5L, 250.0, 251.0);
		assertFalse(factor.hasFactor());
	}
}
