package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.OphthalmologyScheduleOVRecordBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.action.AddOphthalmologyScheduleOVAction;
import edu.ncsu.csc.itrust.action.ViewOphthalmologyScheduleOVAction;
import edu.ncsu.csc.itrust.action.EditOphthalmologyScheduleOVAction;

public class AddEditViewOphthalmologyScheduleOVActionTest {
	private final long LOGGED_IN_MID = 9000000085L;
	private final long PATIENT_MID = 407L;
	
	@Before
	public void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}
	
	@Test
	public void testAddOphthalmologyOV() throws FormValidationException, ITrustException{
		DAOFactory prodDAO = TestDAOFactory.getTestInstance();
		
		AddOphthalmologyScheduleOVAction addAction = new AddOphthalmologyScheduleOVAction(prodDAO, LOGGED_IN_MID);
		
		OphthalmologyScheduleOVRecordBean bean1 = new OphthalmologyScheduleOVRecordBean();
		bean1.setComment("Comment");
		bean1.setDoctormid(101);
		bean1.setPatientmid(102);
		bean1.setPending(true);
		bean1.setDocFirstName("bill");
		bean1.setDocLastName("phil");
		SimpleDateFormat frmt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Date d;
		try {
			d = frmt.parse("20/20/1994 10:22 PM");
			bean1.setDate(new Timestamp(d.getTime()));
		} catch (ParseException e1) {
			//Won't happen
		}
		
		OphthalmologyScheduleOVRecordBean bean2 = new OphthalmologyScheduleOVRecordBean();
		bean2.setComment("Comment");
		bean2.setDoctormid(101);
		bean2.setPatientmid(102);
		bean2.setPending(true);
		bean2.setDocFirstName("bill");
		bean2.setDocLastName("phil");
		frmt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		try {
			d = frmt.parse("20/20/1994 10:23 PM");
			bean2.setDate(new Timestamp(d.getTime()));
		} catch (ParseException e1) {
			//Won't happen
		}
		
		OphthalmologyScheduleOVRecordBean bean3 = new OphthalmologyScheduleOVRecordBean();
		bean3.setComment("Comment");
		bean3.setDoctormid(101);
		bean3.setPatientmid(102);
		bean3.setPending(true);
		bean3.setDocFirstName("bill");
		bean3.setDocLastName("phil");
		frmt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		try {
			d = frmt.parse("20/20/1994 10:24 PM");
			bean3.setDate(new Timestamp(d.getTime()));
		} catch (ParseException e1) {
			//Won't happen
		}
		
		OphthalmologyScheduleOVRecordBean bean4 = new OphthalmologyScheduleOVRecordBean();
		bean4.setComment("Comment");
		bean4.setDoctormid(101);
		bean4.setPatientmid(102);
		bean4.setPending(true);
		bean4.setDocFirstName("bill");
		bean4.setDocLastName("phil");
		frmt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		try {
			d = frmt.parse("20/20/1994 10:25 PM");
			bean4.setDate(new Timestamp(d.getTime()));
		} catch (ParseException e1) {
			//Won't happen
		}
		
		OphthalmologyScheduleOVRecordBean bean5 = new OphthalmologyScheduleOVRecordBean();
		bean5.setComment("Comment");
		bean5.setDoctormid(101);
		bean5.setPatientmid(102);
		bean5.setPending(true);
		bean5.setDocFirstName("bill");
		bean5.setDocLastName("phil");
		frmt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		try {
			d = frmt.parse("20/20/1994 10:26 PM");
			bean5.setDate(new Timestamp(d.getTime()));
		} catch (ParseException e1) {
			//Won't happen
		}
		
		
		
		//Add the beans
		addAction.addOphthalmologyOV(bean1);
		addAction.addOphthalmologyOV(bean2);
		addAction.addOphthalmologyOV(bean3);
		addAction.addOphthalmologyOV(bean4);
		addAction.addOphthalmologyOV(bean5);
		
		//Now test the view
		ViewOphthalmologyScheduleOVAction viewAction = new ViewOphthalmologyScheduleOVAction(prodDAO, LOGGED_IN_MID);
		
		OphthalmologyScheduleOVRecordBean retBean = viewAction.getOphthalmologyScheduleOVForHCP(1L);
		assertEquals(bean1, retBean);
		
		List<OphthalmologyScheduleOVRecordBean> beans = viewAction.getOphthalmologyScheduleOVByPATIENTMID(102L);
		assertEquals(beans.get(0), bean5);
		assertEquals(beans.get(1), bean4);
		assertEquals(beans.get(2), bean3);
		assertEquals(beans.get(3), bean2);
		
		EditOphthalmologyScheduleOVAction editAction = new EditOphthalmologyScheduleOVAction(prodDAO, LOGGED_IN_MID);
		editAction.editOphthalmologyScheduleOV(2L, bean3);
		bean3.setOid(2L);
		assertEquals(viewAction.getOphthalmologyScheduleOVForPatient(2L), bean3);
		
		//Now test the view for a Patient viewing their own records
		ViewOphthalmologyScheduleOVAction patientViewAction = new ViewOphthalmologyScheduleOVAction(prodDAO, PATIENT_MID);
		retBean = patientViewAction.getOphthalmologyScheduleOVForPatient(1L);
		assertEquals(bean1, retBean);
		
		//Now test the view for a Patient viewing a Dependent's records
		retBean = patientViewAction.getOphthalmologyScheduleOVForPatient(5L);
		assertEquals(bean5, retBean);
	}
	
	@Test
	public void testErrors() throws FormValidationException{
		DAOFactory prodDAO = TestDAOFactory.getTestInstance();
		AddOphthalmologyScheduleOVAction addAction = new AddOphthalmologyScheduleOVAction(prodDAO, 401L);
		EditOphthalmologyScheduleOVAction editAction = new EditOphthalmologyScheduleOVAction(prodDAO, 401L);
		
		OphthalmologyScheduleOVRecordBean nullBean = null;
		
		try{
			addAction.addOphthalmologyOV(nullBean);
			fail();
		}catch(ITrustException e){
			assertNull(nullBean);
		}
		
		try{
			editAction.editOphthalmologyScheduleOV(1, nullBean);
			fail();
		}catch(ITrustException e){
			assertNull(nullBean);
		}
	}
	
	@Test
	public void testGetPersonnel() throws DBException{
		DAOFactory prodDAO = TestDAOFactory.getTestInstance();
		AddOphthalmologyScheduleOVAction addAction = new AddOphthalmologyScheduleOVAction(prodDAO, 401L);
		PersonnelDAO perDAO = new PersonnelDAO(prodDAO);
		
		List<PersonnelBean> personnel = addAction.getAllOphthalmologyPersonnel();
		personnel.contains(perDAO.getPersonnel(9000000085L));
		personnel.contains(perDAO.getPersonnel(9000000086L));
	}
	
}
