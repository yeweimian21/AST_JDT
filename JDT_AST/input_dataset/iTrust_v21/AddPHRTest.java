package edu.ncsu.csc.itrust.unit.dao.phr;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.HealthRecordsDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class AddPHRTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private HealthRecordsDAO hrDAO = factory.getHealthRecordsDAO();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient1();
		gen.patient2();
		gen.hcp0();
	}

	public void testAddSimple() throws Exception {
		assertEquals(2, hrDAO.getAllHealthRecords(1L).size());
		HealthRecord hr = new HealthRecord();
		hr.setBloodPressureD(5);
		hr.setBloodPressureN(6);
		hr.setCholesterolHDL(52);
		hr.setCholesterolLDL(55);
		hr.setCholesterolTri(65);
		hr.setHeight(2);
		hr.setSmoker(5);
		hr.setWeight(50);
		hr.setPersonnelID(9000000000L);
		hr.setPatientID(2);
		hr.setOfficeVisitDateStr("10/25/2013");
		boolean confirm = hrDAO.add(hr);
		assertTrue(confirm);
		List<HealthRecord> records = hrDAO.getAllHealthRecords(2L);
		assertEquals(3, records.size());
		HealthRecord record = records.get(0);
		assertEquals(5, record.getBloodPressureD());
		assertEquals(6, record.getBloodPressureN());
		assertEquals(52, record.getCholesterolHDL());
		assertEquals(55, record.getCholesterolLDL());
		assertEquals(65, record.getCholesterolTri());
		assertEquals(2.0, record.getHeight());
		assertTrue(record.isSmoker());
		assertEquals(50.0, record.getWeight());
		assertEquals("10/25/2013", record.getVisitDateStr());
	}
}
