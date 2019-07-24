package edu.ncsu.csc.itrust.unit.action;

import java.sql.SQLException;

import edu.ncsu.csc.itrust.action.ViewMyBillingAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ViewMyBillingActionTest extends TestCase{
	private ViewMyBillingAction action;
	private long mid = 311L; //Sean Ford

	public void setUp() throws Exception {
		super.setUp();
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		gen.uc60();
		
		action = new ViewMyBillingAction(TestDAOFactory.getTestInstance(), this.mid);
	}

	public void testGetMyUnpaidBills() throws DBException, SQLException {
		assertEquals(1, action.getMyUnpaidBills().size());
	}

	public void testGetAllMyBills() throws DBException, SQLException {
		assertEquals(2, action.getAllMyBills().size());
	}

}
