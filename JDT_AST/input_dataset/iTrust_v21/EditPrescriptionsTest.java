package edu.ncsu.csc.itrust.unit.dao.officevisit;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.EditPrescriptionsAction;
import edu.ncsu.csc.itrust.beans.PrescriptionBean;
import edu.ncsu.csc.itrust.dao.mysql.PrescriptionsDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Test Edit Prescription
 *
 */
public class EditPrescriptionsTest extends TestCase {
	private EditPrescriptionsAction epa;
	private PrescriptionBean pres;
	private PrescriptionsDAO prDAO = TestDAOFactory.getTestInstance().getPrescriptionsDAO();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.ndCodes();
		gen.hcp0();
		gen.patient2();
		epa = new EditPrescriptionsAction(TestDAOFactory.getTestInstance(), 9000000000L, "2", "955");

	}

	public void testEditInstructions() throws Exception {
		pres = prDAO.getList(955).get(0);
		assertEquals("Take twice daily", pres.getInstructions());
		
		pres.setInstructions("Take thrice daily");
		
		epa.editPrescription(pres);
		
		pres.setInstructions("fail");
		
		pres = prDAO.getList(955).get(0);
		assertEquals("Take thrice daily", pres.getInstructions());
		
	}
	
	public void testEditDosage() throws Exception {
		pres = prDAO.getList(955).get(0);
		assertEquals(5, pres.getDosage());
		
		pres.setDosage(10);
		
		epa.editPrescription(pres);
		
		pres.setDosage(1);
		
		pres = prDAO.getList(955).get(0);
		assertEquals(10, pres.getDosage());
		
	}
	
	public void testEditVisitID() throws Exception {
		pres = prDAO.getList(955).get(0);
		assertEquals(5, pres.getDosage());
		
		pres.setDosage(6001);
		pres.setVisitID(11);
		
		epa.editPrescription(pres);
		
		
		pres = prDAO.getList(11).get(0);
		assertEquals(6001, pres.getDosage());
		
	}


}
