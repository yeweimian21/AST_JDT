package edu.ncsu.csc.itrust.unit.dao.ophthalmologySurgery;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.unit.DBBuilder;
import edu.ncsu.csc.itrust.beans.OphthalmologySurgeryRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OphthalmologySurgeryRecordDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class OphthalmologySurgeryRecordDAOTest {
	DAOFactory factory = TestDAOFactory.getTestInstance();
	OphthalmologySurgeryRecordDAO dao = new OphthalmologySurgeryRecordDAO(factory);
	OphthalmologySurgeryRecordBean bean;
	
	@Before
	public void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		
		//Make the test bean
		bean = new OphthalmologySurgeryRecordBean();
		bean.setMid(401L);
		bean.setOid(1L);
		bean.setLastName("Lamar");
		bean.setFirstName("Bridges");
		bean.setVisitDate("01/22/2015");
		bean.setVaNumOD(27);
		bean.setVaDenOD(12);
		bean.setVaNumOS(27);
		bean.setVaDenOS(12);
		bean.setSphereOD(12.25);
		bean.setSphereOS(14.50);
		bean.setCylinderOD(1.5);
		bean.setCylinderOS(140.25);
		bean.setAxisOD(14);
		bean.setAxisOS(23);
		bean.setAddOD(2.75);
		bean.setAddOS(2.75);
		bean.setSurgery("Cataract surgery");
		bean.setSurgeryNotes("Surgery completed with no issues.");
	}
	
	@Test
	public void testAddEditRecord() throws DBException{
		dao.addOphthalmologySurgeryRecord(bean);
		assertEquals(dao.getOphthalmologySurgeryRecord(1), bean);
		List<OphthalmologySurgeryRecordBean> beans = dao.getOphthalmologySurgeryRecordsByMID(401);
		assertEquals(beans.get(0), bean);
		
		//edit the bean
		bean.setVaDenOD(15);
		dao.editOphthalmologySurgeryRecordsRecord(1, bean);
		assertEquals(dao.getOphthalmologySurgeryRecord(1), bean);
		beans = dao.getOphthalmologySurgeryRecordsByMID(401);
		assertEquals(beans.get(0), bean);
	}
	
	@Test
	public void testErrors(){
		DBBuilder builder = new DBBuilder();

		//get a single record
		try {
			builder.dropTables(); //drop all tables in the DB
			OphthalmologySurgeryRecordBean bean = null;
			try {
				bean = dao.getOphthalmologySurgeryRecord(1);
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
			List<OphthalmologySurgeryRecordBean> beans = null;
			try {
				beans = dao.getOphthalmologySurgeryRecordsByMID(1);
			} catch (DBException e) {
				assertNull(beans);
			}

			builder.createTables(); //now put them back so future tests aren't broken
		} catch(Exception e) {
			fail();
		}
	}
}
