package edu.ncsu.csc.itrust.unit.dao.phr;

import java.sql.Timestamp;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.HealthRecordsDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class GetPHRTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();

	private HealthRecordsDAO hrDAO = factory.getHealthRecordsDAO();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient1();
		gen.patient2();
		gen.uc51();
		gen.uc52();
	}

	public void testGetPHRForNonExistent() throws Exception {
		assertEquals(0, hrDAO.getAllHealthRecords(-1).size());
	}

	public void testGetPHRFor1() throws Exception {
		assertEquals(2, hrDAO.getAllHealthRecords(1).size());
	}

	public void testGetPHRFor2() throws Exception {
		List<HealthRecord> records = hrDAO.getAllHealthRecords(2L);
		assertEquals(2, records.size());
		assertEquals(62.0, records.get(0).getHeight());
		assertEquals(210.0, records.get(0).getWeight());
		assertTrue(records.get(0).isSmoker());
		assertEquals(195, records.get(0).getBloodPressureN());
		assertEquals(250, records.get(0).getBloodPressureD());
		assertEquals(36, records.get(0).getCholesterolHDL());
		assertEquals(215, records.get(0).getCholesterolLDL());
		assertEquals(280, records.get(0).getCholesterolTri());
		assertEquals(9000000000L, records.get(0).getPersonnelID());
		assertEquals(Timestamp.valueOf("2007-06-07 20:34:58"), records.get(0)
				.getDateRecorded());
		// Already checked the loader, now check that it got the right second
		// record
		assertEquals(60.0, records.get(1).getHeight());
	}
	
	public void testGetAllPatientHealthRecordsByHospital() throws Exception {
		List<HealthRecord> records = hrDAO.getAllPatientHealthRecordsByHospital(103L, "1");
		assertEquals(3, records.size());
		assertEquals(94L, records.get(0).getOfficeVisitID());
		assertEquals(95L, records.get(1).getOfficeVisitID());
		assertEquals(96L, records.get(2).getOfficeVisitID());
	}
}
