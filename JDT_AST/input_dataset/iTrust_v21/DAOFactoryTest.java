package edu.ncsu.csc.itrust.unit.dao;

import java.sql.SQLException;

import edu.ncsu.csc.itrust.dao.DAOFactory;
import junit.framework.TestCase;

public class DAOFactoryTest extends TestCase {
	// Show that the production driver can't be access during a unit test
	public void testRealProductionDriver() throws Exception {
		try {
			DAOFactory.getProductionInstance().getConnection();
			fail("Exception should have been thrown");
		} catch (SQLException e) {
			assertTrue(e.getMessage().contains("Context Lookup Naming Exception"));
		}
	}
}
