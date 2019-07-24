package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.action.AddOphthalmologySurgeryAction;
import edu.ncsu.csc.itrust.action.EditOphthalmologySurgeryAction;
import edu.ncsu.csc.itrust.action.ViewOphthalmologySurgeryAction;
import edu.ncsu.csc.itrust.beans.OphthalmologySurgeryRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class AddEditViewOphthalmologySurgeryActionTest {
	private final long LOGGED_IN_MID = 9000000086L;
	private final long PATIENT_MID = 407L;
	private final long DEPENDENT_MID = 409L;
	
	@Before
	public void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}
	
	@Test
	public void testAddOphthalmologySurgery() throws FormValidationException, ITrustException{
		DAOFactory prodDAO = TestDAOFactory.getTestInstance();
		
		AddOphthalmologySurgeryAction addAction = new AddOphthalmologySurgeryAction(prodDAO, LOGGED_IN_MID);
		
		//Create a valid bean
		OphthalmologySurgeryRecordBean bean1 = new OphthalmologySurgeryRecordBean();
		bean1.setMid(PATIENT_MID);
		bean1.setVisitDate("01/22/2015");
		bean1.setLastName("Bridges");
		bean1.setFirstName("Lamar");
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
		bean1.setSurgery("Cataract Surgery");
		bean1.setSurgeryNotes("Surgery successful.");
		
		//Create another valid bean
		OphthalmologySurgeryRecordBean bean2 = new OphthalmologySurgeryRecordBean();
		bean2.setMid(PATIENT_MID);
		bean2.setVisitDate("01/23/2015");
		bean2.setLastName("Bridges");
		bean2.setFirstName("Lamar");
		bean2.setVaNumOD(27);
		bean2.setVaDenOD(12);
		bean2.setVaNumOS(27);
		bean2.setVaDenOS(12);
		bean2.setSphereOD(+9.25);
		bean2.setSphereOS(-8.25);
		bean2.setAddOD(2.50);
		bean2.setAddOS(0.75);
		bean2.setSurgery("Laser Surgery");
		bean2.setSurgeryNotes("Surgery was boring.");
		
		//Create another valid bean
		OphthalmologySurgeryRecordBean bean3 = new OphthalmologySurgeryRecordBean();
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
		bean3.setSurgery("Refractive Surgery");
		bean3.setSurgeryNotes("Yet another surgery.");
		
		//Create another valid bean
		OphthalmologySurgeryRecordBean bean4 = new OphthalmologySurgeryRecordBean();
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
		// Surgery and notes aren't required
		
		//Create another valid bean
		OphthalmologySurgeryRecordBean bean5 = new OphthalmologySurgeryRecordBean();
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
		bean5.setSurgeryNotes("Surgery rescheduled");
		
		//Add the beans
		addAction.addOphthalmologySurgery(bean1);
		addAction.addOphthalmologySurgery(bean2);
		addAction.addOphthalmologySurgery(bean3);
		addAction.addOphthalmologySurgery(bean4);
		addAction.addOphthalmologySurgery(bean5);
		
		//Now test the view
		ViewOphthalmologySurgeryAction viewAction = new ViewOphthalmologySurgeryAction(prodDAO, LOGGED_IN_MID);
		
		OphthalmologySurgeryRecordBean retBean = viewAction.getOphthalmologySurgeryForHCP(1L);
		assertEquals(bean1, retBean);
		
		List<OphthalmologySurgeryRecordBean> beans = viewAction.getOphthalmologySurgeryByMID(PATIENT_MID);
		assertEquals(beans.get(0), bean4);
		assertEquals(beans.get(1), bean3);
		assertEquals(beans.get(2), bean2);
		assertEquals(beans.get(3), bean1);
		
		EditOphthalmologySurgeryAction editAction = new EditOphthalmologySurgeryAction(prodDAO, LOGGED_IN_MID);
		editAction.editOphthalmologySurgery(2L, bean3);
		bean3.setOid(2L);
		assertEquals(viewAction.getOphthalmologySurgeryForHCP(2L), bean3);
		
		// Patients don't have to be able to view their surgical records, per documentation
	}
	
	public void testErrors() throws FormValidationException{
		DAOFactory prodDAO = TestDAOFactory.getTestInstance();
		AddOphthalmologySurgeryAction addAction = new AddOphthalmologySurgeryAction(prodDAO, 401L);
		EditOphthalmologySurgeryAction editAction = new EditOphthalmologySurgeryAction(prodDAO, 401L);
		
		OphthalmologySurgeryRecordBean nullBean = null;
		
		try{
			addAction.addOphthalmologySurgery(nullBean);
			fail();
		}catch(ITrustException e){
			assertNull(nullBean);
		}
		
		try{
			editAction.editOphthalmologySurgery(1, nullBean);
			fail();
		}catch(ITrustException e){
			assertNull(nullBean);
		}
	}
	
}
