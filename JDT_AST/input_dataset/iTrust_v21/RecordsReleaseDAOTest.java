package edu.ncsu.csc.itrust.unit.dao.recordsrelease;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import edu.ncsu.csc.itrust.beans.RecordsReleaseBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.RecordsReleaseDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

/**
 * RecordsReleaseDAOTest
 */
public class RecordsReleaseDAOTest extends TestCase {
	private TestDataGenerator gen = new TestDataGenerator();
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DAOFactory evilFactory = EvilDAOFactory.getEvilInstance();
	private RecordsReleaseDAO testDAO;
	private RecordsReleaseBean testBean;
	
	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		testDAO = new RecordsReleaseDAO(factory);
		testBean = new RecordsReleaseBean();
		testBean.setDateRequested(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		testBean.setReleaseHospitalID("1");
		testBean.setPid(1L);
		testBean.setRecHospitalName("Test Hospital");
		testBean.setRecHospitalAddress("5 Test Drive");
		testBean.setDocFirstName("Doctor");
		testBean.setDocLastName("Test");
		testBean.setDocPhone("555-555-5555");
		testBean.setDocEmail("test@test.com");
		testBean.setJustification("Justification");
		testBean.setStatus(0);
	}
	
	/**
	 * testAddRecordsRelease
	 * @throws DBException
	 */
	public void testAddRecordsRelease() throws DBException {
		assertTrue(testDAO.getAllRecordsReleasesByHospital("1").isEmpty());
		assertTrue(testDAO.addRecordsRelease(testBean));
		
		List<RecordsReleaseBean> list = testDAO.getAllRecordsReleasesByHospital("1");
		assertEquals(1, list.size());
	}
	
	/**
	 * testUpdateRecordsRelease
	 * @throws DBException
	 */
	public void testUpdateRecordsRelease() throws DBException {
		assertTrue(testDAO.getAllRecordsReleasesByHospital("1").isEmpty());
		testDAO.addRecordsRelease(testBean);
		testBean = testDAO.getAllRecordsReleasesByHospital("1").get(0);
		assertEquals("Test Hospital", testBean.getRecHospitalName());
		testBean.setRecHospitalAddress("Test");
		
		assertTrue(testDAO.updateRecordsRelease(testBean));
		List<RecordsReleaseBean> list = testDAO.getAllRecordsReleasesByHospital("1");
		assertEquals(1, list.size());
		assertEquals("Test", list.get(0).getRecHospitalAddress());
	}
	
	/**
	 * testGetRecordsReleaseByID
	 * @throws DBException
	 */
	public void testGetRecordsReleaseByID() throws DBException {
		assertTrue(testDAO.getAllRecordsReleasesByHospital("1").isEmpty());
		testDAO.addRecordsRelease(testBean);
		testBean = testDAO.getAllRecordsReleasesByHospital("1").get(0);
		
		RecordsReleaseBean idTestBean = testDAO.getRecordsReleaseByID(testBean.getReleaseID());
		assertTrue(idTestBean != null);
		assertEquals(testBean.getReleaseID(), idTestBean.getReleaseID());
		assertEquals(testBean.getDateRequested(), idTestBean.getDateRequested());
		assertEquals("1", idTestBean.getReleaseHospitalID());
		assertEquals(1L, idTestBean.getPid());
		assertEquals("Test Hospital", idTestBean.getRecHospitalName());
		assertEquals("5 Test Drive", idTestBean.getRecHospitalAddress());
		assertEquals("Doctor", idTestBean.getDocFirstName());
		assertEquals("Test", idTestBean.getDocLastName());
		assertEquals("555-555-5555", idTestBean.getDocPhone());
		assertEquals("test@test.com", idTestBean.getDocEmail());
		assertEquals("Justification", idTestBean.getJustification());
		assertEquals(0, idTestBean.getStatus());
	}
	
	/**
	 * testGetAllHealthRecordsByHospital
	 * @throws DBException
	 */
	public void testGetAllHealthRecordsByHospital() throws DBException {
		assertTrue(testDAO.getAllRecordsReleasesByHospital("1").isEmpty());
		testDAO.addRecordsRelease(testBean);
		
		List<RecordsReleaseBean> testList = testDAO.getAllRecordsReleasesByHospital("1");
		assertEquals(1, testList.size());
		
		testDAO.addRecordsRelease(testBean);
		testList = testDAO.getAllRecordsReleasesByHospital("1");
		assertEquals(2, testList.size());
		
		testBean.setReleaseHospitalID("2");
		testDAO.addRecordsRelease(testBean);
		testList = testDAO.getAllRecordsReleasesByHospital("1");
		assertEquals(2, testList.size());
	}
	
	/**
	 * testGetAllHealthRecordsByPid
	 * @throws DBException
	 */
	public void testGetAllHealthRecordsByPid() throws DBException {
		assertTrue(testDAO.getAllRecordsReleasesByPid(1L).isEmpty());
		testDAO.addRecordsRelease(testBean);
		
		List<RecordsReleaseBean> testList = testDAO.getAllRecordsReleasesByPid(1L);
		assertEquals(1, testList.size());
		
		testDAO.addRecordsRelease(testBean);
		testList = testDAO.getAllRecordsReleasesByPid(1L);
		assertEquals(2, testList.size());
		
		testBean.setPid(2L);
		testDAO.addRecordsRelease(testBean);
		testList = testDAO.getAllRecordsReleasesByPid(1L);
		assertEquals(2, testList.size());
	}
	
	/**
	 * testDBException
	 */
	public void testDBException() {
		testDAO = new RecordsReleaseDAO(evilFactory);
		
		try {
			testDAO.addRecordsRelease(testBean);
			//Fail if exception isn't caught
			fail();
		} catch (DBException e) {
			//TODO
		}
		
		try {
			testDAO.updateRecordsRelease(testBean);
			//Fail if exception isn't caught
			fail();
		} catch (DBException e) {
			//TODO
		}
		
		try {
			testDAO.getAllRecordsReleasesByHospital("1");
			//Fail if exception isn't caught
		} catch (DBException e) {
			//TODO
		}
		
		try {
			testDAO.getAllRecordsReleasesByPid(1L);
			//Fail if exception isn't caught
			fail();
		} catch (DBException e) {
			//TODO
		}
		
		try {
			testDAO.getRecordsReleaseByID(0L);
			//Fail if exception isn't caught
			fail();
		} catch (DBException e) {
			//TODO
		}
	}
}
