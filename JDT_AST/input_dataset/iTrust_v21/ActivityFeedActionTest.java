package edu.ncsu.csc.itrust.unit.action;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import edu.ncsu.csc.itrust.action.ActivityFeedAction;
import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

import java.util.Date;
import java.util.List;

public class ActivityFeedActionTest extends TestCase {
	private ActivityFeedAction action;
	private DAOFactory factory;
	private long mid = 1L;
	TestDataGenerator gen;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		
		this.factory = TestDAOFactory.getTestInstance();
		this.action = new ActivityFeedAction(factory, mid);
	}
	
	public void testGetTransactions() throws FormValidationException, SQLException {
		try {
			List<TransactionBean> beans = action.getTransactions(new Date(), 1);
			assertTrue(beans.size() < 20);
		} catch (DBException e) {
			fail();
		}
	}
	
	public void testGetMessageAsSentence() {
		Date dNow = new Date();
		Timestamp tsNow = new Timestamp(dNow.getTime());
		Timestamp tsYesterday = new Timestamp(dNow.getTime() - 1000*60*60*24);
		Timestamp tsLongAgo = new Timestamp(dNow.getTime() - 1000*60*60*24*10);
		new SimpleDateFormat();
		
		String msg;
		
		msg = action.getMessageAsSentence("", tsNow, TransactionType.PATIENT_CREATE);
		assertTrue(msg.contains(TransactionType.PATIENT_CREATE.getActionPhrase()));
		msg = action.getMessageAsSentence("", tsNow, TransactionType.PATIENT_DISABLE);
		assertTrue(msg.contains(TransactionType.PATIENT_DISABLE.getActionPhrase()));
		msg = action.getMessageAsSentence("", tsNow, TransactionType.DEMOGRAPHICS_VIEW);
		assertTrue(msg.contains(TransactionType.DEMOGRAPHICS_VIEW.getActionPhrase()));
		msg = action.getMessageAsSentence("", tsYesterday, TransactionType.PATIENT_HEALTH_INFORMATION_EDIT);
		assertTrue(msg.contains(TransactionType.PATIENT_HEALTH_INFORMATION_EDIT.getActionPhrase()));
		msg = action.getMessageAsSentence("", tsLongAgo, TransactionType.PATIENT_HEALTH_INFORMATION_VIEW);
		assertTrue(msg.contains(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW.getActionPhrase()));
		msg = action.getMessageAsSentence("", tsLongAgo, TransactionType.OFFICE_VISIT_VIEW);
		assertTrue(msg.contains(TransactionType.OFFICE_VISIT_VIEW.getActionPhrase()));
	}
	
	/**
	 * Verifies that certain transactions from the DLHCP are hidden in the activity feed per use case 43.
	 * @throws Exception
	 */
	public void testHiddenActivityFromDLHCP() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		action = new ActivityFeedAction(TestDAOFactory.getTestInstance(), 2L);
		List<TransactionBean> accesses = action.getTransactions(new Date(), 1);

		for(TransactionBean tb : accesses)
			if(tb.getRole() != null && tb.getRole().equals("DLHCP"))
			{
				assertFalse(tb.getTransactionType() == TransactionType.DEMOGRAPHICS_VIEW);
				assertFalse(tb.getTransactionType() == TransactionType.DEMOGRAPHICS_EDIT);
				assertFalse(tb.getTransactionType() == TransactionType.OFFICE_VISIT_CREATE);
				assertFalse(tb.getTransactionType() == TransactionType.OFFICE_VISIT_EDIT);
				assertFalse(tb.getTransactionType() == TransactionType.OFFICE_VISIT_VIEW);
				assertFalse(tb.getTransactionType() == TransactionType.RISK_FACTOR_VIEW);
				assertFalse(tb.getTransactionType() == TransactionType.PATIENT_HEALTH_INFORMATION_EDIT);
				assertFalse(tb.getTransactionType() == TransactionType.PATIENT_HEALTH_INFORMATION_VIEW);
				assertFalse(tb.getTransactionType() == TransactionType.PRESCRIPTION_REPORT_VIEW);
				assertFalse(tb.getTransactionType() == TransactionType.COMPREHENSIVE_REPORT_ADD);
				assertFalse(tb.getTransactionType() == TransactionType.COMPREHENSIVE_REPORT_VIEW);
				assertFalse(tb.getTransactionType() == TransactionType.LAB_PROCEDURE_ADD);
				assertFalse(tb.getTransactionType() == TransactionType.LAB_PROCEDURE_EDIT);
				assertFalse(tb.getTransactionType() == TransactionType.LAB_PROCEDURE_REMOVE);
				assertFalse(tb.getTransactionType() == TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL);
				assertFalse(tb.getTransactionType() == TransactionType.PRESCRIPTION_ADD);
				assertFalse(tb.getTransactionType() == TransactionType.PRESCRIPTION_EDIT);
				assertFalse(tb.getTransactionType() == TransactionType.PATIENT_REMINDERS_VIEW);
				assertFalse(tb.getTransactionType() == TransactionType.EMERGENCY_REPORT_CREATE);
				assertFalse(tb.getTransactionType() == TransactionType.EMERGENCY_REPORT_VIEW);				
			}
	}
}
