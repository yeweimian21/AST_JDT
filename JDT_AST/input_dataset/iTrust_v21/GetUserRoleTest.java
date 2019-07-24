package edu.ncsu.csc.itrust.unit.dao.auth;

import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class GetUserRoleTest extends TestCase{
	private TestDataGenerator gen = new TestDataGenerator(); 
	
	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
	}
	
	public void testHCPMeganHunt() throws Exception {
		gen.hcp0();
		assertEquals("HCP 90..0", "hcp", TestDAOFactory.getTestInstance().getAuthDAO().getUserRole(9000000000L).getUserRolesString());
	}
	
}
