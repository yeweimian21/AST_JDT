package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.action.AddObstetricsAction;
import edu.ncsu.csc.itrust.action.EditObstetricsAction;
import edu.ncsu.csc.itrust.action.ViewObstetricsAction;
import edu.ncsu.csc.itrust.beans.FlagsBean;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.ObstetricsRecordBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FlagsDAO;
import edu.ncsu.csc.itrust.dao.mysql.HealthRecordsDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.enums.BloodType;
import edu.ncsu.csc.itrust.enums.DeliveryType;
import edu.ncsu.csc.itrust.enums.FlagValue;
import edu.ncsu.csc.itrust.enums.PregnancyStatus;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class AddEditViewObstetricsActionTest {
	@Before
	public void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
	}
	
	@Test
	public void testAddRecords() throws FormValidationException, ITrustException {
		DAOFactory prodDAO = TestDAOFactory.getTestInstance();
		AddObstetricsAction add = new AddObstetricsAction(prodDAO, 1L);
		HealthRecordsDAO healthDAO = new HealthRecordsDAO(prodDAO);
		PatientDAO patientDAO = new PatientDAO(prodDAO);
		
		//make a patient bean for testing flag functionality
		long MID = patientDAO.addEmptyPatient();
		PatientBean p = new PatientBean();
		p.setMID(MID);
		p.setFirstName("Random");
		p.setLastName("Person");
		p.setBloodType(BloodType.ABNeg);
		p.setDateOfBirthStr("05/10/1970");
		p.setGenderStr("Female");
		p.setCity("Raleigh");
		p.setState("NC");
		p.setZip("27613-1234");
		p.setIcCity("Raleigh");
		p.setIcState("NC");
		p.setIcZip("27613-1234");
		p.setSecurityQuestion("Question");
		p.setSecurityAnswer("Answer");
		p.setPassword("password");
		p.setConfirmPassword("confirm");
		patientDAO.editPatient(p, 9000000012L);
	
		
		//make an obstetrics bean (note that these dates don't have to be correctly calculated)
		ObstetricsRecordBean bean1 = new ObstetricsRecordBean();
		bean1.setMid(MID);
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
		
		//make a bean
		ObstetricsRecordBean bean2 = new ObstetricsRecordBean();
		bean2.setMid(MID);
		bean2.setOid(2L);
		bean2.setPregId(2L);
		bean2.setLmp("06/27/2014");
		bean2.setEdd("02/15/2015");
		bean2.setWeeksPregnant("40-6");
		bean2.setDateVisit("02/07/2015");
		bean2.setWeight(192.1);
		bean2.setBloodPressureS(100);
		bean2.setBloodPressureD(55);
		bean2.setFhr(75);
		bean2.setFhu(16.17);
		bean2.setDeliveryType(DeliveryType.Caesarean);
		bean2.setPregnancyStatus(PregnancyStatus.Office);
		
		ObstetricsRecordBean miscBean = new ObstetricsRecordBean();
		miscBean.setMid(MID);
		miscBean.setOid(3L);
		miscBean.setPregId(3L);
		miscBean.setLmp("01/05/2014");
		miscBean.setEdd("10/11/2014");
		miscBean.setWeeksPregnant("35-1");
		miscBean.setDateVisit("05/06/2014");
		miscBean.setYearConception(2001);
		miscBean.setHoursInLabor(6.12);
		miscBean.setDeliveryType(DeliveryType.Miscarriage);
		miscBean.setPregnancyStatus(PregnancyStatus.Complete);
		
		ObstetricsRecordBean miscBean2 = new ObstetricsRecordBean();
		miscBean2.setMid(MID);
		miscBean2.setOid(4L);
		miscBean2.setPregId(4L);
		miscBean2.setLmp("01/05/2014");
		miscBean2.setEdd("10/11/2014");
		miscBean2.setWeeksPregnant("35-1");
		miscBean2.setDateVisit("05/06/2014");
		miscBean2.setYearConception(2001);
		miscBean2.setHoursInLabor(6.12);
		miscBean2.setDeliveryType(DeliveryType.Miscarriage);
		miscBean2.setPregnancyStatus(PregnancyStatus.Complete);
		
		ObstetricsRecordBean testBean = new ObstetricsRecordBean();
		testBean.setMid(MID);
		testBean.setOid(5L);
		testBean.setPregId(5L);
		testBean.setLmp("01/05/2014");
		testBean.setEdd("10/11/2014");
		testBean.setWeeksPregnant("35-1");
		testBean.setDateVisit("05/06/2014");
		testBean.setYearConception(2001);
		testBean.setHoursInLabor(6.12);
		testBean.setDeliveryType(DeliveryType.Vaginal);
		testBean.setPregnancyStatus(PregnancyStatus.Initial);
		
		//make a flag bean for testing flag functionality
		FlagsBean bean3 = new FlagsBean();
		bean3.setMid(MID);
		bean3.setPregId(1L);
		bean3.setFlagValue(FlagValue.Twins);
		
		FlagsBean fhr = new FlagsBean();
		FlagsDAO flagsDAO = prodDAO.getFlagsDAO();
		fhr.setMid(bean2.getMid());
		fhr.setPregId(bean2.getPregId());
		fhr.setFlagValue(FlagValue.AbnormalFHR);
		fhr = flagsDAO.getFlag(fhr);
		
		//make a health record bean for testing Abnormal weight change flag
		HealthRecord healthBean = new HealthRecord();
		healthBean.setPatientID(MID);
		healthBean.setOfficeVisitID(1L);
		healthBean.setOfficeVisitDateStr("11/05/2014");
		healthBean.setHeadCircumference(16.2);
		healthBean.setHeight(68.0);
		healthBean.setWeight(150.1);
		healthBean.setSmoker(4);
		healthBean.setHouseholdSmokingStatus(1);
		healthBean.setBloodPressureN(120);
		healthBean.setBloodPressureD(80);
		healthBean.setCholesterolHDL(80);
		healthBean.setCholesterolLDL(80);
		healthBean.setCholesterolTri(130);
		healthBean.setPersonnelID(1L);
		healthBean.setBodyMassIndex();
		
		//add the health record for our patient
		boolean healthAdded = healthDAO.add(healthBean);
		assertTrue(healthAdded);
		
		//add the bean TODO actually set flags before this
		add.addObstetricsRecord(bean1, new ArrayList<FlagsBean>());
		add.addObstetricsRecord(bean2, new ArrayList<FlagsBean>());
		add.addObstetricsRecord(miscBean, new ArrayList<FlagsBean>());
		add.addObstetricsRecord(miscBean, new ArrayList<FlagsBean>());
		add.addObstetricsRecord(testBean, new ArrayList<FlagsBean>());
		
		//now do view things
		ViewObstetricsAction view = new ViewObstetricsAction(prodDAO, 1L);
		
		//first get a single record (OID = 1)
		ObstetricsRecordBean singleBean = view.getViewableObstetricsRecords(1L);
		assertTrue(singleBean.equals(bean1));
		
		//then get all records by (MID = 1)
		List<ObstetricsRecordBean> twoBeans = view.getViewableObstetricsRecordsByMID(MID);
		assertEquals(twoBeans.get(0), bean2);
		assertEquals(twoBeans.get(1), bean1);
		
		//now, edit one of the beans
		EditObstetricsAction edit = new EditObstetricsAction(prodDAO, 1L);
		bean2.setPregnancyStatus(PregnancyStatus.Office);
		edit.editObstetricsRecord(2L, bean2, new ArrayList<FlagsBean>()); //TODO add actual flags testing
		
		//and get it again
		assertEquals(view.getViewableObstetricsRecords(2L), bean2);
		
		//test the update fhr flag function
		edit.updateFhrFlag(bean2);
		assertEquals(fhr.getFlagValue(), FlagValue.AbnormalFHR);
		
		//test flag functionality
		FlagsBean receivedBean = view.getFlagForRecord(bean1, FlagValue.Twins);
		assertEquals(receivedBean.getMid(), bean3.getMid());
		assertEquals(receivedBean.getFlagValue(), bean3.getFlagValue());
		
	}
	
	@Test
	public void testErrors() throws FormValidationException, ITrustException {
		DAOFactory prodDAO = TestDAOFactory.getTestInstance();
		AddObstetricsAction add = new AddObstetricsAction(prodDAO, 1L);
		EditObstetricsAction edit = new EditObstetricsAction(prodDAO, 1L);
		
		//a null obstetrics bean to test exceptions
		ObstetricsRecordBean bean4 = null;
						
		//test that trying to add a null bean throws an ITrustException
		try {
			add.addObstetricsRecord(bean4, new ArrayList<FlagsBean>());
			fail();
		} catch (ITrustException e) {
			assertNull(bean4);
		}
				
		//test that trying to add a null bean throws an ITrustException
		try {
			edit.editObstetricsRecord(3L, bean4, new ArrayList<FlagsBean>());
			fail();
		} catch (ITrustException e) {
			assertNull(bean4);
		}
	}
}
