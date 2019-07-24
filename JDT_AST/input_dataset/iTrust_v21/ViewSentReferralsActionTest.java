package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import edu.ncsu.csc.itrust.action.ViewSentReferralsAction;
import edu.ncsu.csc.itrust.beans.ReferralBean;
import edu.ncsu.csc.itrust.beans.VerboseReferralBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.SortDirection;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ViewSentReferralsActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private ViewSentReferralsAction action;

	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	public void testGetReferrals() throws Exception {
		long hcpid = 9000000000L;
		action = new ViewSentReferralsAction(factory, hcpid);
		String field = "priority";
		SortDirection dir = SortDirection.ASCENDING;
		List<VerboseReferralBean> list =  action.getReferrals(field, dir);
		assertEquals(1, list.get(0).getPriority());
		assertEquals(1, list.get(1).getPriority());
		assertEquals(1, list.get(2).getPriority());
		assertEquals(1, list.get(3).getPriority());
	}

	public void testGetPatientName() throws Exception {
		long hcpid = 9000000000L;
		action = new ViewSentReferralsAction(factory, hcpid);
		ReferralBean bean = action.getReferral(2L);
		assertEquals("Andy Programmer", action.getPatientName(bean));
	}

	public void testGetReceivingHCPName() throws Exception {
		long hcpid = 9000000000L;
		action = new ViewSentReferralsAction(factory, hcpid);
		ReferralBean bean = action.getReferral(3L);
		assertEquals("Gandalf Stormcrow", action.getReceivingHCPName(bean));
	}

}
