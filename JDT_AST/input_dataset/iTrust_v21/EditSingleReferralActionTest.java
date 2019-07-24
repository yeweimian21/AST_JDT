package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import edu.ncsu.csc.itrust.action.EditSingleReferralAction;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.beans.ReferralBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.MessageDAO;
import edu.ncsu.csc.itrust.dao.mysql.ReferralDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class EditSingleReferralActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private MessageDAO msgDAO = factory.getMessageDAO();
	private ReferralDAO refDAO = factory.getReferralDAO();
	private TestDataGenerator gen;
	private EditSingleReferralAction action;

	protected void setUp() throws Exception {
		super.setUp();
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}
	
	// helper method
	private int countMessageWithSubject(List<MessageBean> msgs, String subject) {
		int count = 0;
		for (MessageBean m: msgs) {
			if (m.getSubject().equals(subject)) {
				count++;
			}
		}
		return count;
	}

	public void testAddReferral() throws Exception {
		// When adding a referral, it should appear in the database, and 
		// messages should be sent to the associated users.
		long senderid = 9000000000L;
		long receiverid = 9000000003L;
		long patientid = 2L;
		
		action = new EditSingleReferralAction(factory, senderid);
		
		// Ensure no referral messages exist for any user.
		List<MessageBean> msgs = msgDAO.getMessagesFor(senderid);
		assertEquals(0, countMessageWithSubject(msgs, "You Created a New Referral"));
		msgs = msgDAO.getMessagesFor(receiverid);
		assertEquals(0, countMessageWithSubject(msgs, "You Received a New Referral"));
		msgs = msgDAO.getMessagesFor(patientid);
		assertEquals(0, countMessageWithSubject(msgs, "You Received a New Referral"));
		
		// construct the referral
		ReferralBean bean = new ReferralBean();
		bean.setOvid(955L);
		bean.setPatientID(patientid);
		bean.setReceiverID(receiverid);
		bean.setSenderID(senderid);
		bean.setPriority(1);
		bean.setReferralDetails("A Test Referral!!!");
		
		// send the referral
		action.addReferral(bean);
		
		// get stored referral
		List<ReferralBean> refs = refDAO.getReferralsFromOV(955L);
		bean = null;
		for (ReferralBean r: refs) {
			if (r.getReferralDetails().equals("A Test Referral!!!")) {
				bean = r;
				break;
			}
		}
		assertTrue(bean != null); // make sure we actually found a bean
		
		// check the contents of the bean
		assertEquals(patientid, bean.getPatientID());		
		assertEquals(senderid, bean.getSenderID());		
		assertEquals(receiverid, bean.getReceiverID());	
		
		// check that messages were sent
		msgs = msgDAO.getMessagesFor(senderid);
		assertEquals(1, countMessageWithSubject(msgs, "You Created a New Referral"));
		msgs = msgDAO.getMessagesFor(receiverid);
		assertEquals(1, countMessageWithSubject(msgs, "You Received a New Referral"));
		msgs = msgDAO.getMessagesFor(patientid);
		assertEquals(1, countMessageWithSubject(msgs, "You Received a New Referral"));
	}
	
	public void testEditReferral() throws Exception {
		long senderid = 9000000000L;
		action = new EditSingleReferralAction(factory, senderid);
		// get existing referral and edit it
		ReferralBean bean = action.getReferral(3L);
		assertEquals("Gandalf will help you defeat the orcs!", bean.getReferralDetails());
		bean.setReferralDetails("abc123");
		// save modified referral
		action.editReferral(bean);
		// retrieve referral and see that it has been modified
		bean = action.getReferral(3L);
		assertEquals("abc123", bean.getReferralDetails());
	}
	
	public void testDeleteReferral() throws Exception {
		long senderid = 9000000000L;
		long receiverid = 9000000003L;
		long patientid = 2L;

		action = new EditSingleReferralAction(factory, senderid);
		// get existing referral
		ReferralBean bean = action.getReferral(3L);
		assertEquals("Gandalf will help you defeat the orcs!", bean.getReferralDetails());
		// delete referral
		action.deleteReferral(bean);
		// trying to retrieve deleted referral should return null
		assertEquals(null, action.getReferral(3L));
		
		// part 2: check that messages were sent
		List<MessageBean> msgs = msgDAO.getMessagesFor(senderid);
		assertEquals(0, countMessageWithSubject(msgs, "Your Referral Was Cancelled"));
		msgs = msgDAO.getMessagesFor(receiverid);
		assertEquals(1, countMessageWithSubject(msgs, "Your Referral Was Cancelled"));
		msgs = msgDAO.getMessagesFor(patientid);
		assertEquals(1, countMessageWithSubject(msgs, "Your Referral Was Cancelled"));
	}

}
