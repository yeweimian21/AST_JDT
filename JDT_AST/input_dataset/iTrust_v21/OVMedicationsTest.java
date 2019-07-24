package edu.ncsu.csc.itrust.unit.dao.officevisit;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.MedicationBean;
import edu.ncsu.csc.itrust.beans.OverrideReasonBean;
import edu.ncsu.csc.itrust.beans.PrescriptionBean;
import edu.ncsu.csc.itrust.dao.mysql.PrescriptionOverrideDAO;
import edu.ncsu.csc.itrust.dao.mysql.PrescriptionsDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Test client OV medications
 */
public class OVMedicationsTest extends TestCase {
	private PrescriptionsDAO dao = TestDAOFactory.getTestInstance().getPrescriptionsDAO();
	private PrescriptionOverrideDAO overrideDAO = new PrescriptionOverrideDAO(TestDAOFactory.getTestInstance());
	private PrescriptionBean pres;
	private OverrideReasonBean override;

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.ndCodes();
		gen.officeVisit1();
		pres = new PrescriptionBean();
		MedicationBean medication = new MedicationBean();
		medication.setDescription("Tetracycline");
		medication.setNDCode("009042407");
		pres.setMedication(medication );
		pres.setDosage(50);
		pres.setStartDateStr("05/19/2007");
		pres.setEndDateStr("05/19/2010");
		pres.setVisitID(1L);
		pres.setInstructions("Take twice daily");
		
		override = new OverrideReasonBean("00000");
		

	}

	/**
	 * testAddRemoveOneOVPrescription
	 * @throws Exception
	 */
	public void testAddRemoveOneOVPrescription() throws Exception {
		assertEquals("no current prescriptions on office vist 1", 0, dao.getList(1).size());
		long ovMedID = dao.add(pres);
		List<PrescriptionBean> meds = dao.getList(1);
		assertEquals("now there's 1", 1, meds.size());
		assertEquals(pres, meds.get(0));
		dao.remove(ovMedID);
		assertEquals("now there's none", 0, dao.getList(1).size());
	}
	
	/**
	 * testOverridePrescription
	 * @throws Exception
	 */
	public void testOverridePrescription() throws Exception {
		long ovMedID = dao.add(pres);
		
		assertEquals("no current overrides on office visit 1", 0, overrideDAO.getList(ovMedID).size());
		
		override.setPresID(ovMedID);
		overrideDAO.add(override);
		List<OverrideReasonBean> overrides = overrideDAO.getList(ovMedID);
		assertEquals("now there's 1", 1, overrides.size());
		assertEquals(override.getORCode(), overrides.get(0).getORCode());
		overrideDAO.remove(ovMedID);
		assertEquals("now there's none", 0, overrideDAO.getList(ovMedID).size());
	}
	
	/**
	 * testAddBadPrescription
	 * @throws Exception
	 */
	public void testAddBadPrescription() throws Exception {
		PrescriptionBean bean = new PrescriptionBean();
		bean.setVisitID(-1);
		try {
			dao.add(bean);
			fail("Expected an exception.");
		} catch (DBException e) {		
			//TODO
		}
	}
	
	/**
	 * testAddMultipleRemoveSingle
	 * @throws Exception
	 */
	public void testAddMultipleRemoveSingle() throws Exception {
		assertEquals("no current prescriptions on office vist 1", 0, dao.getList(1).size());
		long ovMedID = dao.add(pres);
		dao.add(pres);
		List<PrescriptionBean> meds = dao.getList(1);
		assertEquals("now there's 2", 2, meds.size());
		assertEquals(pres, meds.get(0));
		dao.remove(ovMedID);
		assertEquals("now there's one", 1, dao.getList(1).size());
	}

	/**
	 * testRemoveNonExistant
	 * @throws Exception
	 */
	public void testRemoveNonExistant() throws Exception {
		assertEquals("no current prescriptions on office vist 1", 0, dao.getList(1).size());
		dao.remove(50L);
		assertEquals("no current prescriptions on office vist 1", 0, dao.getList(1).size());
	}

}
