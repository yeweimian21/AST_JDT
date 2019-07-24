package edu.ncsu.csc.itrust.unit.dao.transaction;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.OperationalProfile;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * OperationalProfileTest
 */
public class OperationalProfileTest extends TestCase {
	private TestDataGenerator gen;
	private TransactionDAO transDAO = TestDAOFactory.getTestInstance().getTransactionDAO();

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.operationalProfile();
		gen.tester();
	}

	/**
	 * testGetOperationalProfile
	 * @throws Exception
	 */
	public void testGetOperationalProfile() throws Exception {
		OperationalProfile op = transDAO.getOperationalProfile();
		Integer[] totalCounts = new Integer[43000];
		Integer[] patientCounts = new Integer[43000];
		Integer[] personnelCounts = new Integer[43000];
		
		for(int i=0; i<43000; i++)
		{
			totalCounts[i]=0;
			patientCounts[i]=0;
			personnelCounts[i]=0;
		}
		
		totalCounts[1]=1;
		personnelCounts[1]=1;
		
		assertEquals(1, op.getNumTotalTransactions());
		assertEquals(0, op.getNumPatientTransactions());
		assertEquals(1, op.getNumPersonnelTransactions());
		for (TransactionType type : TransactionType.values()) {
			assertEquals("for type " + type.getDescription() + "(" + type.getCode() + ")", totalCounts[type.getCode()],
					op.getTotalCount().get(type));
		}
		for (TransactionType type : TransactionType.values()) {
			assertEquals("for type " + type.getDescription() + "(" + type.getCode() + ")",
					patientCounts[type.getCode()], op.getPatientCount().get(type));
		}
		for (TransactionType type : TransactionType.values()) {
			assertEquals("for type " + type.getDescription() + "(" + type.getCode() + ")", personnelCounts[type
					.getCode()], op.getPersonnelCount().get(type));
		}
	}
	
	/**
	 * testOperationProfileException
	 * @throws Exception
	 */
	public void testOperationProfileException() throws Exception {
		TransactionDAO evilTranDAO = EvilDAOFactory.getEvilInstance().getTransactionDAO();
		try{
			evilTranDAO.getAllTransactions();
			fail("exception should have been thrown");
		}catch(DBException e){
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}
