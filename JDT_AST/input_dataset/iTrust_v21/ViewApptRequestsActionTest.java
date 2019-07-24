package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.ViewApptRequestsAction;
import edu.ncsu.csc.itrust.beans.ApptRequestBean;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.dao.mysql.MessageDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ViewApptRequestsActionTest extends TestCase {

	private ViewApptRequestsAction action;
	private TestDataGenerator gen = new TestDataGenerator();
	private MessageDAO mDAO;

	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.apptRequestConflicts();
		action = new ViewApptRequestsAction(9000000000L, TestDAOFactory.getTestInstance());
		mDAO = TestDAOFactory.getTestInstance().getMessageDAO();
	}

	public void testGetApptRequests() throws Exception {
		List<ApptRequestBean> list = action.getApptRequests();
		assertEquals(1, list.size());
		assertEquals(2L, list.get(0).getRequestedAppt().getPatient());
	}

	public void testAcceptApptRequest() throws Exception {
		List<ApptRequestBean> list = action.getApptRequests();
		assertEquals(1, list.size());
		assertEquals(2L, list.get(0).getRequestedAppt().getPatient());
		String res = action.acceptApptRequest(list.get(0).getRequestedAppt().getApptID());
		assertEquals("The appointment request you selected has been accepted and scheduled.", res);
		list = action.getApptRequests();
		assertEquals(1, list.size());
		assertEquals(2L, list.get(0).getRequestedAppt().getPatient());
		assertTrue(list.get(0).isAccepted());
		List<MessageBean> msgs = mDAO.getMessagesFor(list.get(0).getRequestedAppt().getPatient());
		assertEquals(list.get(0).getRequestedAppt().getHcp(), msgs.get(0).getFrom());
		assertTrue(msgs.get(0).getBody().contains("has been accepted."));
	}

	public void testRejectApptRequest() throws Exception {
		List<ApptRequestBean> list = action.getApptRequests();
		assertEquals(1, list.size());
		assertEquals(2L, list.get(0).getRequestedAppt().getPatient());
		String res = action.rejectApptRequest(list.get(0).getRequestedAppt().getApptID());
		assertEquals("The appointment request you selected has been rejected.", res);
		list = action.getApptRequests();
		assertEquals(1, list.size());
		assertEquals(2L, list.get(0).getRequestedAppt().getPatient());
		assertFalse(list.get(0).isAccepted());
		List<MessageBean> msgs = mDAO.getMessagesFor(list.get(0).getRequestedAppt().getPatient());
		assertEquals(list.get(0).getRequestedAppt().getHcp(), msgs.get(0).getFrom());
		assertTrue(msgs.get(0).getBody().contains("has been rejected."));
	}

}
