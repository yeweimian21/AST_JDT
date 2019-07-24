/**
 * 
 */
package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import edu.ncsu.csc.itrust.action.ViewReceivingReferralsAction;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.ReferralBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

/**
 * ViewReceivingReferralsActionTest
 */
public class ViewReceivingReferralsActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private ViewReceivingReferralsAction action;
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		action = new ViewReceivingReferralsAction(factory, 9000000003L);
		gen.clearAllTables();
		gen.standardData();
	}
	
	
	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.ViewReceivingReferralsAction#getReferralsForReceivingHCP()}.
	 * @throws DBException 
	 */
	public void testGetReferralsForReceivingHCP() throws DBException {
		List<ReferralBean> bean = action.getReferralsForReceivingHCP();
		assertEquals(9, bean.size());
		assertEquals(1, bean.get(0).getPriority());
		assertEquals(1, bean.get(1).getPriority());
		assertEquals(1, bean.get(2).getPriority());
		assertEquals(1, bean.get(3).getPriority());
		assertEquals(2, bean.get(4).getPriority());
		assertEquals(2, bean.get(5).getPriority());
		assertEquals(3, bean.get(6).getPriority());
		
		assertEquals(1, bean.get(1).getId());
		assertEquals(1, bean.get(1).getPriority());
		assertEquals(2L, bean.get(1).getPatientID());
		assertEquals(9000000000L, bean.get(1).getSenderID());
		assertEquals(9000000003L, bean.get(1).getReceiverID());
		assertEquals("Gandalf will make sure that the virus does not get past your immune system", bean.get(1).getReferralDetails());
		assertEquals(955L, bean.get(1).getOvid());
		assertFalse(bean.get(1).isViewedByHCP());
		assertFalse(bean.get(1).isViewedByPatient());
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.ViewReceivingReferralsAction#getReferralsForReceivingHCPUnread()}.
	 * @throws DBException 
	 */
	public void testGetReferralsForReceivingHCPUnread() throws DBException {
		int number = action.getReferralsForReceivingHCPUnread();
		assertEquals(7, number);
		
		ReferralBean b = action.getReferralByID(1);
		b.setViewedByHCP(true);
		action.updateReferral(b);
		
		number = action.getReferralsForReceivingHCPUnread();
		assertEquals(6, number);
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.ViewReceivingReferralsAction#getReferralByID(int)}.
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
	 * Test method for {@link edu.ncsu.csc.itrust.action.ViewReceivingReferralsAction#updateReferral(edu.ncsu.csc.itrust.beans.ReferralBean)}.
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
	 * Test method for {@link edu.ncsu.csc.itrust.action.ViewReceivingReferralsAction#getOVDate(long)}.
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
