package edu.ncsu.csc.itrust.unit.dao.transaction;

import java.text.SimpleDateFormat;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class AccessRecordTest extends TestCase {
	private TransactionDAO tranDAO = TestDAOFactory.getTestInstance().getTransactionDAO();
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.transactionLog();
	}

	// note - testing the actual loader is done elsewhere. Just check that we're getting the right
	// ones here
	public void testGetAllAccesses() throws Exception {
		List<TransactionBean> transactions = tranDAO.getAllRecordAccesses(2L, -1, false);
		assertEquals(5, transactions.size());
		for (int i = 0; i < 5; i++) {
			assertEquals(9000000000L, transactions.get(i).getLoggedInMID());
			assertEquals(2L, transactions.get(i).getSecondaryMID());
		}
	}

	public void testGetSomeAccesses() throws Exception {
		List<TransactionBean> transactions = tranDAO.getRecordAccesses(2L, -1, new SimpleDateFormat("MM/dd/yyyy")
				.parse("06/23/2007"), new SimpleDateFormat("MM/dd/yyyy").parse("06/24/2007"), false);
		assertEquals(3, transactions.size());
		transactions = tranDAO.getRecordAccesses(1L, -1, new SimpleDateFormat("MM/dd/yyyy").parse("06/23/2007"),
				new SimpleDateFormat("MM/dd/yyyy").parse("06/24/2007"), false);
		assertEquals(0, transactions.size());
	}
}
