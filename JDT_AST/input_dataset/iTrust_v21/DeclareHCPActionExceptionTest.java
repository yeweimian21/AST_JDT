package edu.ncsu.csc.itrust.unit.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.DeclareHCPAction;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

public class DeclareHCPActionExceptionTest extends TestCase {
	private DeclareHCPAction action;

	@Override
	protected void setUp() throws Exception {
		action = new DeclareHCPAction(EvilDAOFactory.getEvilInstance(), 2L);
	}

	public void testDeclareMalformed() throws Exception {
		try {
			action.declareHCP("not a number");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("HCP's MID not a number", e.getMessage());
		}
	}

	public void testUnDeclareMalformed() throws Exception {
		try {
			action.undeclareHCP("not a number");
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals("HCP's MID not a number", e.getMessage());
		}
	}

}
