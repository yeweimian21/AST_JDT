package edu.ncsu.csc.itrust.unit.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.UploadReferenceTablesAction;
import edu.ncsu.csc.itrust.beans.CDCStatsBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.CDCBmiStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCHeadCircStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCHeightStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCWeightStatsDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * UploadReferenceTablesActionTest
 */
public class UploadReferenceTablesActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DAOFactory evilFactory = EvilDAOFactory.getEvilInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private CDCWeightStatsDAO weightDAO;
	private CDCHeightStatsDAO heightDAO;
	private CDCHeadCircStatsDAO headCircDAO;
	private CDCBmiStatsDAO bmiDAO;
	private UploadReferenceTablesAction action;
	
	/**
	 * setUp
	 */
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		weightDAO = new CDCWeightStatsDAO(factory);
		heightDAO = new CDCHeightStatsDAO(factory);
		headCircDAO = new CDCHeadCircStatsDAO(factory);
		bmiDAO = new CDCBmiStatsDAO(factory);
		action = new UploadReferenceTablesAction(factory);
	}
	
	/**
	 * testStoreWeightStats
	 * @throws DBException
	 */
	public void testStoreWeightStats() throws DBException {
		String testData = "Sex,Agemos,L,M,S\n1,2,3.0,4.0,5.0\n";
		InputStream testStream = null;
		try {
			testStream = new ByteArrayInputStream(testData.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			//If an exception is thrown fail the test
			fail();
		}
		
		//Store the weight
		assertTrue(action.storeWeightStats(testStream));
		
		//Check that the stats actually stored
		CDCStatsBean testBean = weightDAO.getCDCStats(1, 2);
		assertEquals(1, testBean.getSex());
		assertEquals(2.0, testBean.getAge(), .01);
		assertEquals(3.0, testBean.getL(), .01);
		assertEquals(4.0, testBean.getM(), .01);
		assertEquals(5.0, testBean.getS(), .01);
	}
	
	/**
	 * testStoreHeightStats
	 * @throws DBException
	 */
	public void testStoreHeightStats() throws DBException {
		String testData = "Sex,Agemos,L,M,S\n1,2,3.0,4.0,5.0\n";
		InputStream testStream = null;
		try {
			testStream = new ByteArrayInputStream(testData.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			//If an exception is thrown fail the test
			fail();
		}
		
		//Store the height
		assertTrue(action.storeHeightStats(testStream));
		
		//Check that the stats actually stored
		CDCStatsBean testBean = heightDAO.getCDCStats(1, 2);
		assertEquals(1, testBean.getSex());
		assertEquals(2.0, testBean.getAge(), .01);
		assertEquals(3.0, testBean.getL(), .01);
		assertEquals(4.0, testBean.getM(), .01);
		assertEquals(5.0, testBean.getS(), .01);
	}
	
	/**
	 * testStoreHeadCircStats
	 * @throws DBException
	 */
	public void testStoreHeadCircStats() throws DBException {
		String testData = "Sex,Agemos,L,M,S\n1,2,3.0,4.0,5.0\n";
		InputStream testStream = null;
		try {
			testStream = new ByteArrayInputStream(testData.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			//If an exception is thrown fail the test
			fail();
		}
		
		//Store the head circumference
		assertTrue(action.storeHeadCircStats(testStream));
		
		//Check that the stats actually stored
		CDCStatsBean testBean = headCircDAO.getCDCStats(1, 2);
		assertEquals(1, testBean.getSex());
		assertEquals(2.0, testBean.getAge(), .01);
		assertEquals(3.0, testBean.getL(), .01);
		assertEquals(4.0, testBean.getM(), .01);
		assertEquals(5.0, testBean.getS(), .01);
	}
	
	/**
	 * testStoreBMIStats
	 * @throws DBException
	 */
	public void testStoreBMIStats() throws DBException {
		String testData = "Sex,Agemos,L,M,S\n1,2,3.0,4.0,5.0\n";
		InputStream testStream = null;
		try {
			testStream = new ByteArrayInputStream(testData.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			//If an exception is thrown fail the test
			fail();
		}
		
		//Store the bmi
		assertTrue(action.storeBMIStats(testStream));
		
		//Check that the stats actually stored
		CDCStatsBean testBean = bmiDAO.getCDCStats(1, 2);
		assertEquals(1, testBean.getSex());
		assertEquals(2.0, testBean.getAge(), .01);
		assertEquals(3.0, testBean.getL(), .01);
		assertEquals(4.0, testBean.getM(), .01);
		assertEquals(5.0, testBean.getS(), .01);
	}
	
	/**
	 * testEvilFactory
	 */
	public void testEvilFactory() {
		action = new UploadReferenceTablesAction(evilFactory);
		String testData = "Sex,Agemos,L,M,S\n1,2,3.0,4.0,5.0\n";
		InputStream testStream = null;
		try {
			testStream = new ByteArrayInputStream(testData.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			//If an exception is thrown fail the test
			fail();
		}
		
		//Attempt to store the bmi
		assertFalse(action.storeBMIStats(testStream));
	}
 }
