package edu.ncsu.csc.itrust.unit.dao.referral;

import edu.ncsu.csc.itrust.beans.ReferralBean;
import edu.ncsu.csc.itrust.dao.mysql.ReferralDAO;
import edu.ncsu.csc.itrust.dao.mysql.ReferralDAO.ReferralListQuery;
import edu.ncsu.csc.itrust.enums.SortDirection;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import junit.framework.TestCase;

public class ReferralDAOExceptionTest extends TestCase {
	
	private ReferralDAO dao = EvilDAOFactory.getEvilInstance().getReferralDAO();

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testGetReferralsFromOV() {
		try {
			dao.getReferralsFromOV(1L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetReferralsSentFrom() {
		try {
			dao.getReferralsSentFrom(1L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetReferral() {
		try {
			dao.getReferral(1L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetReferralsSentTo() {
		try {
			dao.getReferralsSentTo(1L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetReferralsForPatient() {
		try {
			dao.getReferralsForPatient(1L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetReferralsForPatientUnread() {
		try {
			dao.getReferralsForPatientUnread(1L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testEditReferral() {
		ReferralBean bean = new ReferralBean();
		try {
			dao.editReferral(bean);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testAddReferral() {
		ReferralBean bean = new ReferralBean();
		try {
			dao.addReferral(bean);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testRemoveReferral() {
		try {
			dao.removeReferral(1L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetSenderQuery() {
		try {
			ReferralListQuery q = dao.getSenderQuery(1L);
			q.query("receiverName", SortDirection.ASCENDING);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetPatientQuery() {
		try {
			ReferralListQuery q = dao.getPatientQuery(1L);
			q.query("receiverName", SortDirection.ASCENDING);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetReferralsForReceivingHCP() {
		try {
			dao.getReferralsForReceivingHCP(1L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetReferralsForReceivingHCPUnread() {
		try {
			dao.getReferralsForReceivingHCPUnread(1L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}


}
