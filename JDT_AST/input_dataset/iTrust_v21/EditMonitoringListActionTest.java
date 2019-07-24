package edu.ncsu.csc.itrust.unit.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.EditMonitoringListAction;
import edu.ncsu.csc.itrust.beans.TelemedicineBean;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class EditMonitoringListActionTest extends TestCase {
	EditMonitoringListAction action;
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hcp0();
		gen.patient1();
		action = new EditMonitoringListAction(TestDAOFactory.getTestInstance(), 9000000000L);
	}

	public void testAddToRemoveFromList() throws Exception {
		TelemedicineBean tBean = new TelemedicineBean();
		assertTrue(action.addToList(1L, tBean));
		assertFalse(action.addToList(1L, tBean));
		assertTrue(action.removeFromList(1L));
		assertFalse(action.removeFromList(1L));
	}
	
	public void testIsPatientInList() throws Exception {
		TelemedicineBean tBean = new TelemedicineBean();
		action.addToList(1L, tBean);
		assertTrue(action.isPatientInList(1));
		assertFalse(action.isPatientInList(2));
	}
}
