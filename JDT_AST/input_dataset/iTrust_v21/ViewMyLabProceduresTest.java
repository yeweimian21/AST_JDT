package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.ViewMyLabProceduresAction;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ViewMyLabProceduresTest extends TestCase {

	private ViewMyLabProceduresAction action;
	private long mId = 1L;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();

		action = new ViewMyLabProceduresAction(TestDAOFactory.getTestInstance(), this.mId);
	}

	public void testGetPendingCount() throws DBException {
		assertEquals(1, action.getUnviewedCount());
	}	

	public void testGetLabProcedures() throws DBException {
		assertEquals(2, action.getLabProcedures().size());
		assertEquals(LabProcedureBean.Allow, action.getLabProcedures().get(0).getRights());
		assertEquals(LabProcedureBean.Allow, action.getLabProcedures().get(1).getRights());
	}	
}
