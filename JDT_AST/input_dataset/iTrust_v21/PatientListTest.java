package edu.ncsu.csc.itrust.unit.dao.remotemonitoring;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.TelemedicineBean;
import edu.ncsu.csc.itrust.dao.mysql.RemoteMonitoringDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class PatientListTest extends TestCase {
	private RemoteMonitoringDAO rmDAO = TestDAOFactory.getTestInstance().getRemoteMonitoringDAO();
	private TestDataGenerator gen;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient2();
		gen.hcp0();
	}

	public void testAddRemoveFromList() throws Exception {
		TelemedicineBean tBean = new TelemedicineBean();
		assertTrue(rmDAO.addPatientToList(2L, 9000000000L, tBean));
		assertTrue(rmDAO.removePatientFromList(2L, 9000000000L));
	}
}
