package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.OphthalmologyOVRecordBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.action.AddOphthalmologyOVAction;
import edu.ncsu.csc.itrust.action.ViewOphthalmologyOVAction;
import edu.ncsu.csc.itrust.action.EditOphthalmologyOVAction;

public class AddEditViewOphthalmologyOVActionTest {
	private final long LOGGED_IN_MID = 9000000085L;
	private final long PATIENT_MID = 407L;
	private final long DEPENDENT_MID = 409L;
	
	@Before
	public void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}
	
	@Test
	public void testAddOphthalmologyOV() throws FormValidationException, ITrustException{
		DAOFactory prodDAO = TestDAOFactory.getTestInstance();
		
		AddOphthalmologyOVAction addAction = new AddOphthalmologyOVAction(prodDAO, LOGGED_IN_MID);
		
		//Create a valid bean
		OphthalmologyOVRecordBean bean1 = new OphthalmologyOVRecordBean();
		bean1.setMid(PATIENT_MID);
		bean1.setVisitDate("01/22/2015");
		bean1.setLastName("Tran");
		bean1.setFirstName("Brooke");
		bean1.setVaNumOD(27);
		bean1.setVaDenOD(12);
		bean1.setVaNumOS(27);
		bean1.setVaDenOS(12);
		bean1.setSphereOD(+9.25);
		bean1.setSphereOS(-8.25);
		bean1.setCylinderOD(1.0);
		bean1.setCylinderOS(1.25);
		bean1.setAxisOD(14);
		bean1.setAxisOS(23);
		bean1.setAddOD(2.5);
		bean1.setAddOS(0.75);
		
		//Create another valid bean
		OphthalmologyOVRecordBean bean2 = new OphthalmologyOVRecordBean();
		bean2.setMid(PATIENT_MID);
		bean2.setVisitDate("01/23/2015");
		bean2.setLastName("Tran");
		bean2.setFirstName("Brooke");
		bean2.setVaNumOD(27);
		bean2.setVaDenOD(12);
		bean2.setVaNumOS(27);
		bean2.setVaDenOS(12);
		bean2.setSphereOD(+9.25);
		bean2.setSphereOS(-8.25);
		bean2.setAddOD(2.50);
		bean2.setAddOS(0.75);
		
		//Create another valid bean
		OphthalmologyOVRecordBean bean3 = new OphthalmologyOVRecordBean();
		bean3.setMid(PATIENT_MID);
		bean3.setVisitDate("01/24/2015");
		bean3.setLastName("Tran");
		bean3.setFirstName("Brooke");
		bean3.setVaNumOD(27);
		bean3.setVaDenOD(12);
		bean3.setVaNumOS(27);
		bean3.setVaDenOS(12);
		bean3.setSphereOD(+9.25);
		bean3.setSphereOS(-8.25);
		bean3.setCylinderOS(1.25);
		bean3.setAxisOS(23);
		bean3.setAddOD(2.0);
		bean3.setAddOS(0.75);
		
		//Create another valid bean
		OphthalmologyOVRecordBean bean4 = new OphthalmologyOVRecordBean();
		bean4.setMid(PATIENT_MID);
		bean4.setVisitDate("01/26/2015");
		bean4.setLastName("Tran");
		bean4.setFirstName("Brooke");
		bean4.setVaNumOD(27);
		bean4.setVaDenOD(12);
		bean4.setVaNumOS(27);
		bean4.setVaDenOS(12);
		bean4.setSphereOD(+9.25);
		bean4.setSphereOS(-8.25);
		bean4.setCylinderOD(1.25);
		bean4.setAxisOD(23);
		bean4.setAddOD(2.75);
		bean4.setAddOS(0.75);
		
		//Create another valid bean
		OphthalmologyOVRecordBean bean5 = new OphthalmologyOVRecordBean();
		bean5.setMid(DEPENDENT_MID);
		bean5.setVisitDate("01/26/2015");
		bean5.setLastName("Tran");
		bean5.setFirstName("Brooke");
		bean5.setVaNumOD(27);
		bean5.setVaDenOD(12);
		bean5.setVaNumOS(27);
		bean5.setVaDenOS(12);
		bean5.setSphereOD(+9.25);
		bean5.setSphereOS(-8.25);
		bean5.setCylinderOD(1.25);
		bean5.setAxisOD(23);
		bean5.setAddOD(2.75);
		bean5.setAddOS(0.75);
		
		
		
		//Add the beans
		addAction.addOphthalmologyOV(bean1);
		addAction.addOphthalmologyOV(bean2);
		addAction.addOphthalmologyOV(bean3);
		addAction.addOphthalmologyOV(bean4);
		addAction.addOphthalmologyOV(bean5);
		
		//Now test the view
		ViewOphthalmologyOVAction viewAction = new ViewOphthalmologyOVAction(prodDAO, LOGGED_IN_MID);
		
		List<PatientBean> depens = viewAction.getDependents(407L);
		assertTrue(depens.size() == 2);
		
		OphthalmologyOVRecordBean retBean = viewAction.getOphthalmologyOVForHCP(1L);
		assertEquals(bean1, retBean);
		
		List<OphthalmologyOVRecordBean> beans = viewAction.getOphthalmologyOVByMID(PATIENT_MID);
		assertEquals(beans.get(0), bean4);
		assertEquals(beans.get(1), bean3);
		assertEquals(beans.get(2), bean2);
		assertEquals(beans.get(3), bean1);
		
		EditOphthalmologyOVAction editAction = new EditOphthalmologyOVAction(prodDAO, LOGGED_IN_MID);
		editAction.editOphthalmologyOV(2L, bean3);
		bean3.setOid(2L);
		assertEquals(viewAction.getOphthalmologyOVForHCP(2L), bean3);
		
		//Now test the view for a Patient viewing their own records
		ViewOphthalmologyOVAction patientViewAction = new ViewOphthalmologyOVAction(prodDAO, PATIENT_MID);
		retBean = patientViewAction.getOphthalmologyOVForPatient(1L);
		assertEquals(bean1, retBean);
		
		//Now test the view for a Patient viewing a Dependent's records
		retBean = patientViewAction.getOphthalmologyOVForDependent(5L);
		assertEquals(bean5, retBean);
	}
	
	@Test
	public void testErrors() throws FormValidationException{
		DAOFactory prodDAO = TestDAOFactory.getTestInstance();
		AddOphthalmologyOVAction addAction = new AddOphthalmologyOVAction(prodDAO, 401L);
		EditOphthalmologyOVAction editAction = new EditOphthalmologyOVAction(prodDAO, 401L);
		
		OphthalmologyOVRecordBean nullBean = null;
		
		try{
			addAction.addOphthalmologyOV(nullBean);
			fail();
		}catch(ITrustException e){
			assertNull(nullBean);
		}
		
		try{
			editAction.editOphthalmologyOV(1, nullBean);
			fail();
		}catch(ITrustException e){
			assertNull(nullBean);
		}
	}
	
}
