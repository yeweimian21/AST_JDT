package edu.ncsu.csc.itrust.unit.dao.referral;

import java.util.List;

import edu.ncsu.csc.itrust.beans.VerboseReferralBean;
import edu.ncsu.csc.itrust.dao.mysql.ReferralDAO;
import edu.ncsu.csc.itrust.enums.SortDirection;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ReferralQueryDAOTest extends TestCase {
	
	private ReferralDAO dao = TestDAOFactory.getTestInstance().getReferralDAO();
	private TestDataGenerator gen;

	protected void setUp() throws Exception {
		super.setUp();
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hcp0();
		gen.hcp3();
		gen.hcp4();
		gen.hcp5();
		gen.patient1();
		gen.patient2();
		gen.patient5();
		gen.referral_query_testdata();
	}

	public void testSenderQueryPatientSort() throws Exception {
		ReferralDAO.ReferralListQuery query = dao.getSenderQuery(9000000000L);
		List<VerboseReferralBean> list = query.query("patientName", SortDirection.ASCENDING);
		assertEquals(4, list.size());
		assertEquals("Random Person", list.get(0).getPatientName());
		assertEquals("Andy Programmer", list.get(1).getPatientName());
		assertEquals("Andy Programmer", list.get(2).getPatientName());
		assertEquals("Baby Programmer", list.get(3).getPatientName());
	}

	public void testSenderQueryReceiverSort() throws Exception {
		ReferralDAO.ReferralListQuery query = dao.getSenderQuery(9000000000L);
		List<VerboseReferralBean> list = query.query("receiverName", SortDirection.DESCENDING);
		assertEquals(4, list.size());
		assertEquals("Gandalf Stormcrow", list.get(0).getReceiverName());
		assertEquals("Gandalf Stormcrow", list.get(1).getReceiverName());
		assertEquals("Gandalf Stormcrow", list.get(2).getReceiverName());
		assertEquals("Antonio Medico", list.get(3).getReceiverName());
	}

	public void testSenderQueryReceiverSort2() throws Exception {
		ReferralDAO.ReferralListQuery query = dao.getSenderQuery(9000000000L);
		List<VerboseReferralBean> list = query.query("receiverName", SortDirection.ASCENDING);
		assertEquals(4, list.size());
		assertEquals("Antonio Medico", list.get(0).getReceiverName());
		assertEquals("Gandalf Stormcrow", list.get(1).getReceiverName());
		assertEquals("Gandalf Stormcrow", list.get(2).getReceiverName());
		assertEquals("Gandalf Stormcrow", list.get(3).getReceiverName());
	}
	
	public void testReceiverQueryReceiver() throws Exception {
		ReferralDAO.ReferralListQuery query = dao.getReceiverQuery(9000000003L);
		List<VerboseReferralBean> list = query.query("referralDetails", SortDirection.ASCENDING);
		assertEquals(3, list.size());
		assertEquals(101, list.get(0).getOvid());
		assertEquals(103, list.get(1).getOvid());
		assertEquals(103, list.get(2).getOvid());
	}

	public void testPatientQueryReceiverSort() throws Exception {
		ReferralDAO.ReferralListQuery query = dao.getPatientQuery(2);
		List<VerboseReferralBean> list = query.query("receiverName", SortDirection.ASCENDING);
		assertEquals(3, list.size());
		assertEquals("Kelly Doctor", list.get(0).getReceiverName());
		assertEquals("Gandalf Stormcrow", list.get(1).getReceiverName());
		assertEquals("Gandalf Stormcrow", list.get(2).getReceiverName());
	}


}
