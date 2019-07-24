package edu.ncsu.csc.itrust.unit.dao.remotemonitoring;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.RemoteMonitoringDataBean;
import edu.ncsu.csc.itrust.beans.TelemedicineBean;
import edu.ncsu.csc.itrust.dao.mysql.RemoteMonitoringDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 */
public class PatientDataTest extends TestCase {
	private RemoteMonitoringDAO rmDAO = TestDAOFactory.getTestInstance().getRemoteMonitoringDAO();
	private RemoteMonitoringDAO EvilrmDAO = EvilDAOFactory.getEvilInstance().getRemoteMonitoringDAO();
	private TestDataGenerator gen;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient2();
		gen.hcp0();
		gen.remoteMonitoring1();
	}

	/**
	 * testStoreRetrievePatientNormalData
	 * @throws Exception
	 */
	public void testStoreRetrievePatientNormalData() throws Exception {
		RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
		b.setSystolicBloodPressure(100);
		b.setDiastolicBloodPressure(70);
		b.setGlucoseLevel(80);
		rmDAO.storePatientData(2, b, "self-reported", 2);
		List<RemoteMonitoringDataBean> d = rmDAO.getPatientsData(9000000000L);
		assertEquals(2, d.get(0).getPatientMID());
		assertEquals(100, d.get(0).getSystolicBloodPressure());
		assertEquals(70, d.get(0).getDiastolicBloodPressure());
		assertEquals(80, d.get(0).getGlucoseLevel());
	}
	
	/**
	 * testGetMonitoringHCPs
	 * @throws Exception
	 */
	public void testGetMonitoringHCPs() throws Exception {
		gen.remoteMonitoring5();
		assertTrue(rmDAO.getMonitoringHCPs(1).size() == 1);
	}
	
	/**
	 * testBadStoreRetrievePatientNormalDataBad
	 * @throws Exception
	 */
	public void testBadStoreRetrievePatientNormalDataBad() throws Exception{
		try {
			RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
			b.setSystolicBloodPressure(100);
			b.setDiastolicBloodPressure(70);
			b.setGlucoseLevel(80);
			EvilrmDAO.storePatientData(2, b, "self-reported", 2);
			fail();
		} catch (DBException e){
			assertSame(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	/**
	 * testBadStoreRetrievePatientGlucoseOnlyDataBad
	 * @throws Exception
	 */
	public void testBadStoreRetrievePatientGlucoseOnlyDataBad() throws Exception{
		try {
			RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
			b.setGlucoseLevel(80);
			EvilrmDAO.storePatientData(2, b, "self-reported", 2);
			fail();
		} catch (DBException e){
			assertSame(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	/**
	 * testBadStoreRetrievePatientBPOnlyDataBad
	 * @throws Exception
	 */
	public void testBadStoreRetrievePatientBPOnlyDataBad() throws Exception{
		try {
			RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
			b.setSystolicBloodPressure(80);
			b.setDiastolicBloodPressure(100);
			EvilrmDAO.storePatientData(2, b, "self-reported", 2);
			fail();
		} catch (DBException e){
			assertSame(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	/**
	 * testGetTelemadicineBean
	 * @throws Exception
	 */
	public void testGetTelemedicineBean() throws Exception {
		try {
			List<TelemedicineBean> tBeans = rmDAO.getTelemedicineBean(2L);
			assertEquals(1, tBeans.size());
		} catch (ITrustException e) {
			fail();
		}
	}

	/**
	 * testValidatePR
	 * @throws Exception
	 */
	public void testValidatePR() throws Exception{
		try {
			rmDAO.validatePR(2, 1);
			assert(true);
		} catch (ITrustException e){
			fail();
		}
	}

	/**
	 * testValidatePRError
	 * @throws Exception
	 */
	public void testValidatePRError() throws Exception{
		try {
			rmDAO.validatePR(1, 2);
			fail();
		} catch (ITrustException e){
			assertEquals("Representer is not valid for patient 2", e.getMessage());
		}
	}

	/**
	 * testRemovePatientFromListBad
	 * @throws Exception
	 */
	public void testRemovePatientFromListBad() throws Exception{
		try {
			EvilrmDAO.removePatientFromList(1, 2);
			fail();
		} catch (DBException e){
			assertSame(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}
