package edu.ncsu.csc.itrust.unit.dao.referral;

import java.util.List;

import edu.ncsu.csc.itrust.beans.ReferralBean;
import edu.ncsu.csc.itrust.dao.mysql.ReferralDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ReferralDAOTest extends TestCase {
	
	private ReferralDAO dao = TestDAOFactory.getTestInstance().getReferralDAO();
	private TestDataGenerator gen;

	protected void setUp() throws Exception {
		super.setUp();
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testGetReferralsFromOV() throws Exception {
		assertEquals(4, dao.getReferralsFromOV(955).size());
		assertEquals(0, dao.getReferralsFromOV(952).size());
	}
	
	public void testGetReferralsSendFrom() throws Exception {
		assertEquals(8, dao.getReferralsSentFrom(9000000000L).size());
		assertEquals(9, dao.getReferralsSentFrom(9000000003L).size());
	}
	
	public void testGetReferralsForPatient() {
		ReferralBean r = new ReferralBean();
		r.setId(1L);
		r.setSenderID(2L);
		r.setReceiverID(3L);
		r.setPatientID(4L);
		r.setReferralDetails("Five");
		r.setOvid(6L);
		r.setViewedByHCP(true);
		r.setViewedByPatient(true);
		try {
			dao.addReferral(r);
			List<ReferralBean> referrals = dao.getReferralsForPatient(4L);
			assertTrue(referrals.size()==1);
		} catch (DBException e) {
			fail("DBException should not have been thrown");
		}
	}



}
