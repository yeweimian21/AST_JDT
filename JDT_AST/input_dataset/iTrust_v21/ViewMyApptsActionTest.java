package edu.ncsu.csc.itrust.unit.action;

import java.sql.SQLException;

import edu.ncsu.csc.itrust.action.ViewMyApptsAction;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ViewMyApptsActionTest extends TestCase {
	private ViewMyApptsAction action;
	private DAOFactory factory;
	private long mid = 1L;
	private long hcpId = 9000000000L;
	
	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		
		this.factory = TestDAOFactory.getTestInstance();
		this.action = new ViewMyApptsAction(this.factory, this.hcpId);
	}
	
	public void testGetMyAppointments() throws SQLException, DBException {
		assertEquals(15, action.getAllMyAppointments().size());
	}
	
	
	public void testGetName() throws ITrustException {
		assertEquals("Kelly Doctor", action.getName(hcpId));
	}
	
	public void testGetName2() throws ITrustException {
		assertEquals("Random Person", action.getName(mid));
	}
}
