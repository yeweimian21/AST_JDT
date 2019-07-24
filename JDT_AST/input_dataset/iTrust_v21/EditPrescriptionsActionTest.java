package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import edu.ncsu.csc.itrust.action.EditPrescriptionsAction;
import edu.ncsu.csc.itrust.beans.MedicationBean;
import edu.ncsu.csc.itrust.beans.OverrideReasonBean;
import edu.ncsu.csc.itrust.beans.PrescriptionBean;
import edu.ncsu.csc.itrust.beans.forms.EditPrescriptionsForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.PrescriptionFieldException;
import edu.ncsu.csc.itrust.exception.PrescriptionWarningException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

/**
 * Test Prescription actions
 *
 */
public class EditPrescriptionsActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditPrescriptionsAction action;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	/**
	 * Test edit prescription
	 * @throws Exception
	 */
	public void testEditPrescription() throws Exception  {
		action = new EditPrescriptionsAction(factory, 9000000000L, "2", "955");
		PrescriptionBean bean = action.getPrescriptions().get(0);
		assertEquals(5, bean.getDosage());
		bean.setDosage(42);
		action.editPrescription(bean);
		bean = action.getPrescriptions().get(0);
		assertEquals(42, bean.getDosage());
	}

	/**
	 * Test getPrescription
	 * @throws Exception
	 */
	public void testGetPrescriptions() throws Exception {
		action = new EditPrescriptionsAction(factory, 9000000000L, "2", "955");
		List<PrescriptionBean> list = action.getPrescriptions();
		assertEquals(3, list.size());
		assertEquals("009042407", list.get(0).getMedication().getNDCode());
		assertEquals("009042407", list.get(1).getMedication().getNDCode());
		assertEquals("647641512", list.get(2).getMedication().getNDCode());

		action = new EditPrescriptionsAction(factory, 9000000000L, "1", "11");
		assertEquals(0, action.getPrescriptions().size());

		// An EditPrescriptionAction without an ovID returns an empty list.
		action = new EditPrescriptionsAction(factory, 9000000000L, "2");
		assertEquals(0, action.getPrescriptions().size());
	}

	/**
	 * Test if prescription have been added for scenario 1
	 * @throws Exception
	 */
	public void testAddPrescription1() throws Exception  {
		action = new EditPrescriptionsAction(factory, 9000000000L, "1", "11");
		assertEquals(0, action.getPrescriptions().size());
		PrescriptionBean bean = new PrescriptionBean();
		MedicationBean med = factory.getNDCodesDAO().getNDCode("009042407");
		bean.setMedication(med);
		bean.setDosage(10);
		bean.setStartDateStr("01/31/2011");
		bean.setEndDateStr("02/12/2011");
		bean.setInstructions("Take as needed");
		bean.setVisitID(11);
		action.addPrescription(bean);
		
		List<PrescriptionBean> list = action.getPrescriptions();
		assertEquals(1, list.size());
		assertEquals("009042407", list.get(0).getMedication().getNDCode());
	}
	
	/**
	 * Test prescription for scenario 2
	 * @throws Exception
	 */
	public void testAddPrescription2() throws Exception {
		action = new EditPrescriptionsAction(factory, 9000000000L, "2", "952");
		PrescriptionBean bean = new PrescriptionBean();
		MedicationBean med = factory.getNDCodesDAO().getNDCode("664662530");
		bean.setMedication(med);
		bean.setDosage(10);
		bean.setStartDateStr("01/31/2011");
		bean.setEndDateStr("02/12/2011");
		bean.setInstructions("Take as needed");
		bean.setVisitID(952);
		
		try {
			action.addPrescription(bean);
			fail("Expected a PrescriptionWarningException.");
		} catch (PrescriptionWarningException e) {
			assertTrue(e.getDisplayMessage().contains("Allergy: Penicillin"));
		}
	}
	
	/**
	 * Test add prescription for scenario 3
	 * @throws Exception
	 */
	public void testAddPrescription3() throws Exception {
		action = new EditPrescriptionsAction(factory, 9000000000L, "2", "952");
		assertEquals(0, action.getPrescriptions().size());
		PrescriptionBean bean = new PrescriptionBean();
		MedicationBean med = factory.getNDCodesDAO().getNDCode("664662530");
		bean.setMedication(med);
		bean.setDosage(10);
		bean.setStartDateStr("01/31/2011");
		bean.setEndDateStr("02/12/2011");
		bean.setInstructions("Take as needed");
		bean.setVisitID(952);
		bean.addReason(new OverrideReasonBean("0006"));
		
		action.addPrescription(bean);
		assertEquals(1, action.getPrescriptions().size());
	}
	
	/**
	 * Test add prescription for scenario 4
	 * @throws Exception 
	 */
	public void testAddPrescription4() throws Exception {
		action = new EditPrescriptionsAction(factory, 9000000000L, "1", "11");
		assertEquals(0, action.getPrescriptions().size());
		PrescriptionBean bean = new PrescriptionBean();
		MedicationBean med = factory.getNDCodesDAO().getNDCode("678771191");
		bean.setMedication(med);
		bean.setDosage(10);
		bean.setStartDateStr("01/31/2011");
		bean.setEndDateStr("02/12/2011");
		bean.setInstructions("Take as needed");
		bean.setVisitID(11);
		
		action.addPrescription(bean);
		assertEquals(1, action.getPrescriptions().size());

		med = factory.getNDCodesDAO().getNDCode("081096");
		bean.setMedication(med);
		bean.setDosage(10);
		bean.setStartDateStr("01/31/2011");
		bean.setEndDateStr("02/12/2011");
		bean.setInstructions("Take as needed");
		bean.setVisitID(11);

		try {
			action.addPrescription(bean);
			fail("Expected a PrescriptionWarningException.");
		} catch (PrescriptionWarningException e) {
			assertTrue(e.getDisplayMessage().contains("Currently Prescribed: Ibuprofen"));
		}
	}
	
	public void testAddPrescription5() throws Exception {
		action = new EditPrescriptionsAction(factory, 9000000000L, "2", "952");
		PrescriptionBean bean = new PrescriptionBean();
		MedicationBean med = factory.getNDCodesDAO().getNDCode("081096");
		bean.setMedication(med);
		bean.setDosage(10);
		bean.setStartDateStr("01/31/2011");
		bean.setEndDateStr("02/12/2011");
		bean.setVisitID(952);
		
		try {
			action.addPrescription(bean);
			fail("Expected a PrescriptionFieldException.");
		} catch (PrescriptionFieldException e) {
			assertTrue(e.getMessage().contains("Instructions are required"));
		}
	}

	/**
	 * Test if prescription has been deleted
	 * @throws Exception
	 */
	public void testDeletePrescription() throws Exception  {
		action = new EditPrescriptionsAction(factory, 9000000000L, "2", "955");
		List<PrescriptionBean> list = action.getPrescriptions();
		assertEquals(3, list.size());
		PrescriptionBean bean = list.get(2);
		assertEquals("647641512", bean.getMedication().getNDCode());
		action.deletePrescription(bean);
		
		list = action.getPrescriptions();
		assertEquals(2, list.size());
		assertEquals("009042407", list.get(0).getMedication().getNDCode());
		assertEquals("009042407", list.get(1).getMedication().getNDCode());
	}

	/**
	 * Test Medication
	 * @throws Exception
	 */
	public void testGetMedications() throws Exception  {
		action = new EditPrescriptionsAction(factory, 9000000000L, "2", "955");
		List<MedicationBean> list = action.getMedications();
		assertEquals(15, list.size());
		
		// It can also be retrieved for an undefined office visit
		action = new EditPrescriptionsAction(factory, 9000000000L, "1");
		list = action.getMedications();
		assertEquals(15, list.size());
	}

	/**
	 * testFormToBean
	 * @throws Exception
	 */
	public void testFormToBean() throws Exception  {
		action = new EditPrescriptionsAction(factory, 9000000000L, "2", "955");
		EditPrescriptionsForm form = new EditPrescriptionsForm();
		form.setMedID("548684985");
		form.setDosage("5");
		form.setStartDate("02/28/2011");
		form.setEndDate("03/07/2011");
		form.setInstructions("Try it.");
		String[] overrideCodes =  {"asdf"};
		form.setOverrideCodes(overrideCodes);
		form.setOverrideOther("yeeees?");
		
		PrescriptionBean bean = action.formToBean(form, "-- Instructions --");
		assertEquals("548684985", bean.getMedication().getNDCode());
		assertEquals("asdf", bean.getReasons().get(0).getORCode());
		assertEquals("yeeees?", bean.getOverrideReasonOther());
	}
	
	/**
	 * testAddPrescription_Allergy
	 * @throws Exception
	 */
	public void testAddPrescription_Allergy() throws Exception {
		
		action = new EditPrescriptionsAction(factory, 9000000000L, "100", "1093");
		
		PrescriptionBean pres = new PrescriptionBean();
		pres.setDosage(50);
		pres.setInstructions("Take it");
		pres.setStartDateStr("01/31/2011");
		pres.setEndDateStr("02/12/2011");
		MedicationBean med = factory.getNDCodesDAO().getNDCode("00882219");
		
		pres.setMedication(med);
		pres.setVisitID(1093);
		
		try{
			action.addPrescription(pres);
			fail("Should have thrown exception");
		}catch(PrescriptionWarningException e){
			assertTrue(e.getDisplayMessage().contains("Allergy: Lantus"));
		}catch(Exception e){
			fail("Wrong exception thrown");
		}
		
		
	}
	
	/**
	 * testAddPrescription_AllergyOverride
	 * @throws Exception
	 */
	public void testAddPrescription_AllergyOverride() throws Exception {
		
		action = new EditPrescriptionsAction(factory, 9000000000L, "100", "1093");
		
		PrescriptionBean pres = new PrescriptionBean();
		pres.setDosage(50);
		pres.setInstructions("Take it");
		pres.setStartDateStr("01/31/2011");
		pres.setEndDateStr("02/12/2011");
		pres.addReason(new OverrideReasonBean("1234"));
		MedicationBean med = factory.getNDCodesDAO().getNDCode("00882219");
		
		pres.setMedication(med);
		pres.setVisitID(1093);
		
		try{
			action.addPrescription(pres);
		}catch(Exception e){
			fail("Exception should not be thrown since override set");
		}
		
		boolean emailSent = factory.getFakeEmailDAO().getEmailWithBody("Allergy: Lantus").size()>0;
		assertTrue(emailSent);
		
	}

	/**
	 * testAddPrescription_AllergyFuture
	 * @throws Exception
	 */
	public void testAddPrescription_AllergyFuture() throws Exception {
		
		action = new EditPrescriptionsAction(factory, 9000000000L, "100", "1093");
		
		PrescriptionBean pres = new PrescriptionBean();
		pres.setDosage(50);
		pres.setInstructions("Take it");
		pres.setStartDateStr("01/31/2111");
		pres.setEndDateStr("02/12/2111");
		MedicationBean med = factory.getNDCodesDAO().getNDCode("00882219");
		
		pres.setMedication(med);
		pres.setVisitID(1093);
		
		try{
			action.addPrescription(pres);
			fail("Should have thrown exception");
		}catch(PrescriptionWarningException e){
			assertTrue(e.getDisplayMessage().contains("Allergy: Lantus"));
		}catch(Exception e){
			fail("Wrong exception thrown");
		}
	}
	
	/**
	 * testAddPrescription_Intercation
	 * @throws Exception
	 */
	public void testAddPrescription_Interaction() throws Exception {
		
		action = new EditPrescriptionsAction(factory, 9000000000L, "100", "1093");
		
		PrescriptionBean pres = new PrescriptionBean();
		pres.setDosage(50);
		pres.setInstructions("Take it");
		pres.setStartDateStr("01/31/2111");
		pres.setEndDateStr("02/12/2111");
		//Nexium, which interacts with aspirin
		MedicationBean med = factory.getNDCodesDAO().getNDCode("01864020");
		
		pres.setMedication(med);
		pres.setVisitID(1093);
		
		
		PrescriptionBean pres2 = new PrescriptionBean();
		pres2.setDosage(50);
		pres2.setInstructions("Take it too");
		pres2.setStartDateStr("01/31/2111");
		pres2.setEndDateStr("02/12/2111");
		//aspirin, which interacts with nexium
		MedicationBean med2 = factory.getNDCodesDAO().getNDCode("081096");
		
		pres2.setMedication(med2);
		pres2.setVisitID(1093);
		
		try{
			action.addPrescription(pres);
			action.addPrescription(pres2);
			fail("Should have thrown exception");
		}catch(PrescriptionWarningException e){
			assertTrue(e.getDisplayMessage().contains("Interaction"));
		}catch(Exception e){
			fail("Wrong exception thrown");
		}
	}
}
