/**
 * 
 */
package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.beans.VerboseReferralBean;
import edu.ncsu.csc.itrust.action.ViewPatientReferralsAction;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.ReferralBean;
import edu.ncsu.csc.itrust.enums.SortDirection;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

/**
 * ViewPatientReferralsActionTest
 */
public class ViewPatientReferralsActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private ViewPatientReferralsAction action;
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		action = new ViewPatientReferralsAction(factory, 2L);
		gen.clearAllTables();
		gen.standardData();
	}
	

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.ViewPatientReferralsAction#getReferrals(java.lang.String, edu.ncsu.csc.itrust.enums.SortDirection)}.
	 * @throws DBException 
	 */
	public void testGetReferralsStringSortDirection() throws DBException {
		List<VerboseReferralBean> bean = action.getReferrals("priority", SortDirection.parse("ascending"));
		
	
		assertEquals(5, bean.size());
		assertEquals(1, bean.get(0).getPriority());
		assertEquals(1, bean.get(1).getPriority());
		assertEquals(1, bean.get(2).getPriority());
		assertEquals(2, bean.get(3).getPriority());
		
		assertEquals(1, bean.get(0).getId());
		assertEquals(1, bean.get(0).getPriority());
		assertEquals(2L, bean.get(0).getPatientID());
		assertEquals(9000000000L, bean.get(0).getSenderID());
		assertEquals(9000000003L, bean.get(0).getReceiverID());
		assertEquals("Gandalf will make sure that the virus does not get past your immune system", bean.get(0).getReferralDetails());
		assertEquals(955L, bean.get(0).getOvid());
		assertFalse(bean.get(0).isViewedByHCP());
		assertFalse(bean.get(0).isViewedByPatient());
		assertEquals("07/15/2007 00:00 AM", bean.get(0).getTimeStamp());
		assertEquals("Kelly Doctor", bean.get(0).getSenderName());
		assertEquals("06/10/2007", bean.get(0).getOvDate());
		
		
		ReferralBean other = action.getReferralByID(1);
		ReferralBean verbBean = bean.get(0).toReferralBean();
		
		assertTrue(other.equals(verbBean));
		
		VerboseReferralBean nextOther = new VerboseReferralBean();
		nextOther.setId(1L);
		nextOther.setSenderID(9000000000L);
		nextOther.setReceiverID(9000000003L);
		nextOther.setOvid(955);
		nextOther.setViewedByHCP(false);
		nextOther.setViewedByPatient(false);
		nextOther.setTimeStamp("07/15/2007 00:00 AM");
		nextOther.setReferralDetails("Gandalf will make sure that the virus does not get past your immune system");
			
		assertTrue(nextOther.equals(bean.get(0)));
	
		bean.clear();
		bean = action.getReferrals("priority", SortDirection.parse("descending"));
		
		assertEquals(5, bean.size());
		assertEquals(1, bean.get(3).getPriority());
		assertEquals(1, bean.get(2).getPriority());
		assertEquals(2, bean.get(1).getPriority());
		assertEquals(3, bean.get(0).getPriority());
		
		//illegal sort direction, expecting an error
		try {
			bean = action.getReferrals("priority", SortDirection.parse("anywhere"));
			fail("Anywhere is not a sort direction");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("SortDirection anywhere does not exist"));
		}
		
	}


	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.ViewPatientReferralsAction#getReferralsForPatientUnread()}.
	 * @throws DBException 
	 */
	public void testGetReferralsForPatientUnread() throws DBException {
		int number = action.getReferralsForPatientUnread();
		assertEquals(5, number);
		
		ReferralBean b = action.getReferralByID(1);
		b.setViewedByPatient(true);
		action.updateReferral(b);
		
		number = action.getReferralsForPatientUnread();
		assertEquals(4, number);
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.ViewPatientReferralsAction#getReferralByID(int)}.
	 * @throws DBException 
	 */
	public void testGetReferralByID() throws DBException {
		ReferralBean b = action.getReferralByID(1);
		
		assertEquals(1, b.getId());
		assertEquals(1, b.getPriority());
		assertEquals(2L, b.getPatientID());
		assertEquals(9000000000L, b.getSenderID());
		assertEquals(9000000003L, b.getReceiverID());
		assertEquals("Gandalf will make sure that the virus does not get past your immune system", b.getReferralDetails());
		assertEquals(955L, b.getOvid());
		assertFalse(b.isViewedByHCP());
		assertFalse(b.isViewedByPatient());

	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.ViewPatientReferralsAction#updateReferral(edu.ncsu.csc.itrust.beans.ReferralBean)}.
	 * @throws DBException 
	 */
	public void testUpdateReferral() throws DBException {
		ReferralBean b = action.getReferralByID(1);
		assertEquals(1, b.getId());
		assertEquals(1, b.getPriority());
		assertEquals(2L, b.getPatientID());
		assertEquals(9000000000L, b.getSenderID());
		assertEquals(9000000003L, b.getReceiverID());
		assertEquals("Gandalf will make sure that the virus does not get past your immune system", b.getReferralDetails());
		assertEquals(955L, b.getOvid());
		assertFalse(b.isViewedByHCP());
		assertFalse(b.isViewedByPatient());
		
		b.setPriority(2);
		b.setViewedByHCP(true);
		b.setViewedByPatient(true);
		
		action.updateReferral(b);
		
		b = action.getReferralByID(1);
		
		assertEquals(1, b.getId());
		assertEquals(2, b.getPriority());
		assertEquals(2L, b.getPatientID());
		assertEquals(9000000000L, b.getSenderID());
		assertEquals(9000000003L, b.getReceiverID());
		assertEquals("Gandalf will make sure that the virus does not get past your immune system", b.getReferralDetails());
		assertEquals(955L, b.getOvid());
		assertTrue(b.isViewedByHCP());
		assertTrue(b.isViewedByPatient());
		
	}


	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.ViewPatientReferralsAction#getOVDate(long)}.
	 * @throws DBException 
	 */
	public void testGetOVDate() throws DBException {
		OfficeVisitBean b = action.getOVDate(955L);
		
		assertEquals(955, b.getID());
		assertEquals(9000000000L, b.getHcpID());
		assertEquals(2L, b.getPatientID());
		assertEquals("Yet another office visit.", b.getNotes());
		assertEquals("06/10/2007", b.getVisitDateStr());
		
		
	}

}
