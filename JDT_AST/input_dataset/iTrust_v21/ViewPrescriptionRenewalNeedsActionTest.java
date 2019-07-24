package edu.ncsu.csc.itrust.unit.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.ViewPrescriptionRenewalNeedsAction;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

import java.util.List;


public class ViewPrescriptionRenewalNeedsActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private ViewPrescriptionRenewalNeedsAction action;
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hospitals();
		gen.hcp2();
		gen.hcp1();
		gen.hcp3();
		gen.hcp0();
		gen.ndCodes();
		gen.icd9cmCodes();
		gen.patient9();
		gen.patient11();
		gen.patient14();
		
		
		gen.UC32Acceptance();
	}

	public void testTwoPatients() throws Exception {
		action = new ViewPrescriptionRenewalNeedsAction(factory, 9000000003L);
		List<PatientBean> patients = action.getRenewalNeedsPatients();
		assertEquals("Andy", patients.get(0).getFirstName());
		assertEquals("Koopa", patients.get(0).getLastName());
		assertEquals("919-212-3433", patients.get(1).getPhone());
		assertEquals("prince@gmail.com", patients.get(1).getEmail());
		
	}

	public void testThreePatients() throws Exception {
		action = new ViewPrescriptionRenewalNeedsAction(factory, 9900000000L);
		List<PatientBean> patients = action.getRenewalNeedsPatients();
		assertEquals(3, patients.size());
		assertEquals("Zack", patients.get(0).getFirstName());
		assertEquals("Darryl", patients.get(1).getFirstName());
		assertEquals("Marie", patients.get(2).getFirstName());
	}

	public void testZeroPatients() throws Exception {
		action = new ViewPrescriptionRenewalNeedsAction(factory, 9990000000L);
		List<PatientBean> patients = action.getRenewalNeedsPatients();
		assertNotNull(patients);
		assertEquals(0, patients.size());
		
	}
	
	public void testDBException() throws Exception {
		factory = EvilDAOFactory.getEvilInstance();
		
		action = new ViewPrescriptionRenewalNeedsAction(factory, -1L);
		List<PatientBean> patients = action.getRenewalNeedsPatients();
		assert(patients == null);
		
	}
	

}
