package edu.ncsu.csc.itrust.unit.action;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import edu.ncsu.csc.itrust.action.ViewMyMessagesAction;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.MessageDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

/**
 * ViewMyMessagesActionTest
 */
public class ViewMyMessagesActionTest extends TestCase {

	private ViewMyMessagesAction action;
	private ViewMyMessagesAction action2;
	private ViewMyMessagesAction evilAction;
	private DAOFactory factory;
	private DAOFactory evilFactory;
	private long mId = 2L;
	private long hcpId = 9000000000L;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();

		this.factory = TestDAOFactory.getTestInstance();
		this.evilFactory = EvilDAOFactory.getEvilInstance();
		this.action = new ViewMyMessagesAction(this.factory, this.mId);
		this.action2 = new ViewMyMessagesAction(this.factory, this.hcpId);
		this.evilAction = new ViewMyMessagesAction(this.evilFactory, this.mId);
	}
	
	/**
	 * testGetAllMyMessages
	 * @throws SQLException
	 * @throws DBException
	 */
	public void testGetAllMyMessages() throws SQLException, DBException {
		List<MessageBean> mbList = action.getAllMyMessages();
		
		assertEquals(1, mbList.size());
		
		// Should send a message and recheck later.
	}

	/**
	 * testGetPatientName
	 * @throws ITrustException
	 */
	public void testGetPatientName() throws ITrustException {
		assertEquals("Andy Programmer", action.getName(this.mId));
	}
	
	/**
	 * testGetPersonnelName
	 * @throws ITrustException
	 */
	public void testGetPersonnelName() throws ITrustException {
		assertEquals("Kelly Doctor", action.getPersonnelName(this.hcpId));
	}
	
	/**
	 * testGetAllMyMessagesTimeAscending
	 * @throws DBException
	 * @throws SQLException
	 */
	public void testGetAllMyMessagesTimeAscending() throws DBException, SQLException {		
		
		List<MessageBean> mbList = action2.getAllMyMessagesTimeAscending();
		
		assertEquals(14, mbList.size());
		
		assertTrue(mbList.get(0).getSentDate().before(mbList.get(1).getSentDate()));
	}
	
	/**
	 * testGetAllMyMessagesNameAscending
	 * @throws DBException
	 * @throws SQLException
	 */
	public void testGetAllMyMessagesNameAscending() throws DBException, SQLException {		
		
		List<MessageBean> mbList = action2.getAllMyMessagesNameAscending();
		List<MessageBean> mbList2 = action.getAllMyMessagesNameAscending();
		
		assertEquals(14, mbList.size());
		assertEquals(1, mbList2.size());
		
		try {
			assertTrue(action2.getName(mbList.get(0).getFrom()).compareTo(action2.getName(mbList.get(13).getFrom())) >= 0);
		} catch (ITrustException e) {
			//TODO
		}
	}
	
	/**
	 * testGetAllMyMessagesNameDescending
	 * @throws DBException
	 * @throws SQLException
	 */
	public void testGetAllMyMessagesNameDescending() throws DBException, SQLException {		
		
		List<MessageBean> mbList = action2.getAllMyMessagesNameDescending();
		List<MessageBean> mbList2 = action.getAllMyMessagesNameDescending();
		
		assertEquals(14, mbList.size());
		assertEquals(1, mbList2.size());
		
		try {
			assertTrue(action2.getName(mbList.get(13).getFrom()).compareTo(action2.getName(mbList.get(0).getFrom())) >= 0);
		} catch (ITrustException e) {
			//TODO
		}
	}
	
	/**
	 * testGetAllMySentMessages
	 * @throws DBException
	 * @throws SQLException
	 */
	public void testGetAllMySentMessages() throws DBException, SQLException {		
		
		List<MessageBean> mbList = action2.getAllMySentMessages();
		
		assertEquals(2, mbList.size());
	}
	
	/**
	 * testGetAllMyMessagesFromTimeAscending
	 * @throws DBException
	 * @throws SQLException
	 */
	public void testGetAllMyMessagesFromTimeAscending() throws DBException, SQLException {		
		
		List<MessageBean> mbList = action2.getAllMySentMessagesTimeAscending();
		
		assertEquals(2, mbList.size());
		
		assertTrue(mbList.get(0).getSentDate().before(mbList.get(1).getSentDate()));
	}
	
	/**
	 * testGetAllMyMessagesFromNameAscending
	 * @throws DBException
	 * @throws SQLException
	 */
	public void testGetAllMyMessagesFromNameAscending() throws DBException, SQLException {		
		
		List<MessageBean> mbList = action2.getAllMySentMessagesNameAscending();
		List<MessageBean> mbList2 = action.getAllMySentMessagesNameAscending();
		
		assertEquals(2, mbList.size());
		assertEquals(3, mbList2.size());
		
		try {
			assertTrue(action2.getName(mbList.get(0).getFrom()).compareTo(action2.getName(mbList.get(1).getFrom())) >= 0);
		} catch (ITrustException e) {
			//TODO
		}
	}
	
	/**
	 * testGetAllMyMessagesFromNameDescending
	 * @throws DBException
	 * @throws SQLException
	 */
	public void testGetAllMyMessagesFromNameDescending() throws DBException, SQLException {		
		
		List<MessageBean> mbList = action2.getAllMySentMessagesNameDescending();
		List<MessageBean> mbList2 = action.getAllMySentMessagesNameDescending();
		
		assertEquals(2, mbList.size());
		assertEquals(3, mbList2.size());
		
		try {
			assertTrue(action2.getName(mbList.get(1).getFrom()).compareTo(action2.getName(mbList.get(0).getFrom())) >= 0);
		} catch (ITrustException e) {
			//TODO
		}
	}
	
	/**
	 * testUpdateRead
	 * @throws ITrustException
	 * @throws SQLException
	 * @throws FormValidationException
	 */
	public void testUpdateRead() throws ITrustException, SQLException, FormValidationException {
		List<MessageBean> mbList = action.getAllMyMessages();
		assertEquals(0, mbList.get(0).getRead());
		action.setRead(mbList.get(0));
		mbList = action.getAllMyMessages();
		assertEquals(1, mbList.get(0).getRead());
	}
	
	/**
	 * testAddMessage
	 * @throws SQLException
	 * @throws DBException
	 */
	public void testAddMessage() throws SQLException, DBException {
		MessageDAO test = new MessageDAO(factory);
		
		List<MessageBean> mbList = action.getAllMyMessages();
		
		test.addMessage(mbList.get(0));
		
		mbList = action.getAllMyMessages();
		
		assertEquals(2, mbList.size());
	}

	/**
	 * testFilterMessages
	 * @throws SQLException
	 * @throws ITrustException
	 * @throws ParseException
	 */
	public void testFilterMessages() throws SQLException, ITrustException, ParseException {
		List<MessageBean> mbList = action2.getAllMyMessages();
		
		mbList = action2.filterMessages(mbList, "Random Person,Appointment,Appointment,Lab,01/01/2010,01/31/2010");
		assertEquals(1, mbList.size());
	}
	
	/**
	 * testGetUnreadCount
	 * @throws DBException
	 * @throws SQLException
	 */
	public void testGetUnreadCount() throws DBException, SQLException {
		assertEquals(1, action.getUnreadCount());
		assertEquals(12, action2.getUnreadCount());
	}
	
	/**
	 * testLinkedToReferral
	 * @throws DBException
	 */
	public void testLinkedToReferral() throws DBException {
		assertEquals(0, action.linkedToReferral(1));
	}
	
	/**
	 * testGetCCdMessages
	 * @throws DBException
	 * @throws SQLException
	 */
	public void testGetCCdMessages() throws DBException, SQLException {
		assertEquals(0, action.getCCdMessages(1).size());
	}
	
	/**
	 * testThrowsExceptions
	 * @throws DBException
	 */
	public void testThrowsExceptions() throws DBException {
		List<MessageBean> resultList = null;
		try {
			resultList = evilAction.getAllMyMessages();
			fail();
		} catch (DBException e) {
			assertNull(resultList);
		} catch (SQLException e) {
			assertNull(resultList);
		}
		
		resultList = null;
		try {
			resultList = evilAction.getAllMyMessagesNameAscending();
			fail();
		} catch (DBException e) {
			assertNull(resultList);
		} catch (SQLException e) {
			assertNull(resultList);
		}
		
		resultList = null;
		try {
			resultList = evilAction.getAllMySentMessages();
			fail();
		} catch (DBException e) {
			assertNull(resultList);
		} catch (SQLException e) {
			assertNull(resultList);
		}
	}
}
