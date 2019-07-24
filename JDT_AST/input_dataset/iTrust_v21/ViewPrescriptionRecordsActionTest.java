package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.ViewPrescriptionRecordsAction;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.beans.PrescriptionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ViewPrescriptionRecordsActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private ViewPrescriptionRecordsAction action;
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testGetPatient() throws Exception {
		action = new ViewPrescriptionRecordsAction(factory, 9000000000L);
		PatientBean patient = action.getPatient(1L);
		assertEquals("Random", patient.getFirstName());
		assertEquals("Person", patient.getLastName());
	}
	
	public void testGetRepresentees() throws Exception {
		action = new ViewPrescriptionRecordsAction(factory, 2L);
		List<PatientBean> representees = action.getRepresentees();
		assertEquals(6, representees.size());
		assertEquals(1, representees.get(0).getMID());
		assertEquals(3, representees.get(1).getMID());
	}
	
	public void testGetPrescribingDoctor() throws Exception {
		action = new ViewPrescriptionRecordsAction(factory, 2L);
		PrescriptionBean prescription = new PrescriptionBean();
		prescription.setVisitID(955L);
		PersonnelBean personnel = action.getPrescribingDoctor(prescription);
		assertEquals(9000000000L, personnel.getMID());
	}
	
	public void testGetPrescriptionsForPatient() throws Exception {
		action = new ViewPrescriptionRecordsAction(factory, 1L);
		List<PrescriptionBean> prescriptions = action.getPrescriptionsForPatient(1L);
		assertEquals(0, prescriptions.size());
		
		action = new ViewPrescriptionRecordsAction(factory, 2L);
		prescriptions = action.getPrescriptionsForPatient(2L);
		assertEquals(3, prescriptions.size());
		assertEquals("647641512", prescriptions.get(2).getMedication().getNDCode());
		assertEquals("009042407", prescriptions.get(0).getMedication().getNDCode());
		assertEquals("009042407", prescriptions.get(1).getMedication().getNDCode());
		
		prescriptions = action.getPrescriptionsForPatient(1L);
		assertEquals(0, prescriptions.size());
		
		action = new ViewPrescriptionRecordsAction(factory, 9000000000L);
		prescriptions = action.getPrescriptionsForPatient(2L);
		assertEquals(3, prescriptions.size());
		assertEquals("647641512", prescriptions.get(2).getMedication().getNDCode());		
		assertEquals("009042407", prescriptions.get(0).getMedication().getNDCode());
		assertEquals("009042407", prescriptions.get(1).getMedication().getNDCode());
	}
}