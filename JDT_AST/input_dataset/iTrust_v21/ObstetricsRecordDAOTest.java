package edu.ncsu.csc.itrust.unit.dao.obstetrics;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.ObstetricsRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ObstetricsRecordDAO;
import edu.ncsu.csc.itrust.enums.DeliveryType;
import edu.ncsu.csc.itrust.enums.PregnancyStatus;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.DBBuilder;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ObstetricsRecordDAOTest {
	DAOFactory prodDAO = TestDAOFactory.getTestInstance();
	ObstetricsRecordDAO dao = new ObstetricsRecordDAO(prodDAO);
	ObstetricsRecordBean bean1;
	
	@Before
	public void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		
		//make a bean
		bean1 = new ObstetricsRecordBean();
		bean1.setMid(1L);
		bean1.setOid(1L);
		bean1.setPregId(1L);
		bean1.setLmp("01/05/2014");
		bean1.setEdd("10/11/2014");
		bean1.setWeeksPregnant("35-1");
		bean1.setDateVisit("05/06/2014");
		bean1.setYearConception(2001);
		bean1.setHoursInLabor(6.12);
		bean1.setDeliveryType(DeliveryType.Miscarriage);
		bean1.setPregnancyStatus(PregnancyStatus.Initial);
		bean1.setWeight(154.23);
		bean1.setBloodPressureS(101);
		bean1.setBloodPressureD(57);
		bean1.setFhr(72);
		bean1.setFhu(20);
	}
	
	@Test
	public void testEditRecord() throws DBException {
		//add the bean
		dao.addObstetricsRecord(bean1);
		assertEquals(dao.getObstetricsRecord(1), bean1);
		
		//edit the bean
		bean1.setWeeksPregnant("32-5");
		dao.editObstetricsRecord(1, bean1);
		
		//assert it exists by OID
		assertEquals(dao.getObstetricsRecord(1), bean1);
		
		//also assert it exists if gathered by MID
		List<ObstetricsRecordBean> beans = dao.getObstetricsRecordsByMID(1);
		assertEquals(beans.get(0), bean1);
	}
	
	@Test
	public void testErrors() throws DBException {
		DBBuilder builder = new DBBuilder();

		//get a single record
		try {
			builder.dropTables(); //drop all tables in the DB
			ObstetricsRecordBean bean = null;
			try {
				bean = dao.getObstetricsRecord(1);
			} catch (DBException e) {
				assertNull(bean);
			}

			builder.createTables(); //now put them back so future tests aren't broken
		} catch(Exception e) {
			fail();
		}
		
		//get all obstetrics records
		try {
			builder.dropTables(); //drop all tables in the DB
			List<ObstetricsRecordBean> beans = null;
			try {
				beans = dao.getObstetricsRecordsByMID(1);
			} catch (DBException e) {
				assertNull(beans);
			}

			builder.createTables(); //now put them back so future tests aren't broken
		} catch(Exception e) {
			fail();
		}
		
		//get a record by MID and pregId
		try {
			builder.dropTables(); //drop all tables in the DB
			ObstetricsRecordBean bean = null;
			try {
				bean = dao.getObstetricsRecordsByMIDLargestpregId(1);
			} catch (DBException e) {
				assertNull(bean);
			}

			builder.createTables(); //now put them back so future tests aren't broken
		} catch(Exception e) {
			fail();
		}
	}
}
