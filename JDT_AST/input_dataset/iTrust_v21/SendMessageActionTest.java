package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.SendMessageAction;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.MessageDAO;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;

import junit.framework.TestCase;


public class SendMessageActionTest extends TestCase {

	private DAOFactory factory;
	private GregorianCalendar gCal;
	private MessageDAO messageDAO;
	private SendMessageAction smAction;
	private TestDataGenerator gen;
	private long pateientId;
	private long hcpId;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		
		this.pateientId = 2L;
		this.hcpId = 9000000000L;
		this.factory = TestDAOFactory.getTestInstance();
		this.messageDAO = new MessageDAO(this.factory);
		this.smAction = new SendMessageAction(this.factory, this.pateientId);
		this.gCal = new GregorianCalendar();
	}

	public void testSendMessage() throws ITrustException, SQLException, FormValidationException {
		String body = "UNIT TEST - SendMessageActionText";
		MessageBean mBean = new MessageBean();
		Timestamp timestamp = new Timestamp(this.gCal.getTimeInMillis());
		
		mBean.setFrom(this.pateientId);
		mBean.setTo(this.hcpId);
		mBean.setSubject(body);
		mBean.setSentDate(timestamp);
		mBean.setBody(body);
		
		this.smAction.sendMessage(mBean);
		
		List<MessageBean> mbList = this.messageDAO.getMessagesFor(this.hcpId);
		
		assertEquals(15, mbList.size());
		MessageBean mBeanDB = mbList.get(0);
		assertEquals(body, mBeanDB.getBody());
	}
	
	public void testGetPatientName() throws ITrustException {
		assertEquals("Andy Programmer", this.smAction.getPatientName(this.pateientId));
	}
	
	public void testGetPersonnelName() throws ITrustException {
		assertEquals("Kelly Doctor", this.smAction.getPersonnelName(this.hcpId));
	}
	
	public void testGetMyRepresentees() throws ITrustException {
		List<PatientBean> pbList = this.smAction.getMyRepresentees();
		assertEquals("Random Person", pbList.get(0).getFullName());
		assertEquals("05/10/1950", pbList.get(0).getDateOfBirthStr());
		assertEquals("Care Needs", pbList.get(1).getFullName());
		assertEquals("Baby Programmer", pbList.get(2).getFullName());
		assertEquals("Baby A", pbList.get(3).getFullName());
		assertEquals("Baby B", pbList.get(4).getFullName());
		assertEquals("Baby C", pbList.get(5).getFullName());
		assertEquals(6, pbList.size());
	}
	
	public void testGetMyDLHCPs() throws ITrustException {
		List<PersonnelBean> pbList = this.smAction.getDLHCPsFor(this.pateientId);
		assertEquals(1, pbList.size());
	}
	
	public void testGetMyDLHCPs2() throws ITrustException {
		List<PersonnelBean> pbList = this.smAction.getMyDLHCPs();
		assertEquals(1, pbList.size());
	}
	
	public void testGetDLCHPsFor() throws ITrustException {
		List<PersonnelBean> pbList = this.smAction.getDLHCPsFor(this.pateientId);
		
		assertEquals(1, pbList.size());
	}
	
}
