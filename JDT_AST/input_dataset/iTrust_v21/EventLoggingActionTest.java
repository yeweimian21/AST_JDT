package edu.ncsu.csc.itrust.unit.action;

import java.sql.SQLException;

import edu.ncsu.csc.itrust.action.EventLoggingAction;
import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

import java.util.List;

public class EventLoggingActionTest extends TestCase {
	private EventLoggingAction action;
	private DAOFactory factory;
	private long mid = 1L;
	
	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		factory = TestDAOFactory.getTestInstance();
		action = new EventLoggingAction(factory);
	}
	
	public void testGetTransactions() throws FormValidationException, SQLException {
		try {
			action.logEvent(TransactionType.LOGIN_FAILURE, mid, 0, "");
			
			TransactionDAO dao = factory.getTransactionDAO();
			List<TransactionBean> all = dao.getAllTransactions();
	
			boolean passes = false;
			for(TransactionBean log: all) {
				if(log.getLoggedInMID() == mid && log.getTransactionType() == TransactionType.LOGIN_FAILURE) {
					passes = true;
					break;
				}
			}
			if (!passes) {
				fail();
			}
		} catch (DBException e) {
			fail();
		}
	}
}
