package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.EditReferralsAction;
import edu.ncsu.csc.itrust.beans.ReferralBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class EditReferralsActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditReferralsAction action;

	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	public void testGetReferrals() throws Exception {
		action = new EditReferralsAction(factory, 9000000000L, "2", "955");
		assertEquals(4, action.getReferrals().size());

		action = new EditReferralsAction(factory, 9000000000L, "1", "11");
		assertEquals(0, action.getReferrals().size());

		// An EditPrescriptionAction without an ovID returns an empty list.
		action = new EditReferralsAction(factory, 9000000000L, "2");
		assertEquals(0, action.getReferrals().size());
	}

	public void testGetPatientName() throws Exception {
		action = new EditReferralsAction(factory, 9000000000L, "2", "955");
		ReferralBean bean = action.getReferral(1L);
		assertEquals("Andy Programmer", action.getPatientName(bean));
	}

	public void testGetReceivingHCPName() throws Exception {
		action = new EditReferralsAction(factory, 9000000000L, "2", "955");
		ReferralBean bean = action.getReferral(1L);
		assertEquals("Gandalf Stormcrow", action.getReceivingHCPName(bean));
	}

}
