package edu.ncsu.csc.itrust.unit.dao.officevisit;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PrescriptionReportBean;
import edu.ncsu.csc.itrust.dao.mysql.PrescriptionReportDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Test  prescriptions
 */
public class GetPrescriptionsTest extends TestCase {
	private PrescriptionReportDAO preportDAO = TestDAOFactory.getTestInstance().getPrescriptionReportDAO();
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient2();
		gen.hcp0();
		gen.hospitals();
		gen.ndCodes();
	}

	/**
	 * testGetPrescriptions
	 * @throws Exception
	 */
	public void testGetPrescriptions() throws Exception {
		List<Long> ovIDs = Arrays.asList(955L, 952L);
		List<PrescriptionReportBean> prescriptions = preportDAO.byOfficeVisitAndPatient(ovIDs, 2L);
		for (int i=0; i<3; i++)
		{
			if (prescriptions.get(i).getPrescription()
				.getMedication().getNDCode().equals("009042407"))
			{
			assertPrescription(prescriptions.get(i));
			}
			else if (prescriptions.get(i).getPrescription()
				.getMedication().getNDCode().equals("647641512"))
			{
			assertPrescription2(prescriptions.get(i));
			}
			else
			{
				fail("This prescription should not be in database");
			}
		}
		
	}

	private void assertPrescription(PrescriptionReportBean prescription) {
		assertEquals("009042407", prescription.getPrescription().getMedication().getNDCode());
		assertEquals("Tetracycline", prescription.getPrescription().getMedication().getDescription());
		assertEquals(955L, prescription.getPrescription().getVisitID());
		assertEquals(5, prescription.getPrescription().getDosage());
		assertEquals("10/10/2006", prescription.getPrescription().getStartDateStr());
		assertEquals("10/11/2006", prescription.getPrescription().getEndDateStr());
		assertEquals("Take twice daily", prescription.getPrescription().getInstructions());
		assertEquals(9000000000L, prescription.getOfficeVisit().getHcpID());
		assertEquals("1", prescription.getOfficeVisit().getHospitalID());
		assertEquals(2L, prescription.getOfficeVisit().getPatientID());
		assertEquals("06/10/2007", prescription.getOfficeVisit().getVisitDateStr());
	}
	
	private void assertPrescription2(PrescriptionReportBean prescription) {
		assertEquals("647641512", prescription.getPrescription().getMedication().getNDCode());
		assertEquals("Prioglitazone", prescription.getPrescription().getMedication().getDescription());
		assertEquals(955L, prescription.getPrescription().getVisitID());
		assertEquals(5, prescription.getPrescription().getDosage());
		assertEquals("10/10/2006", prescription.getPrescription().getStartDateStr());
		assertEquals("10/11/2020", prescription.getPrescription().getEndDateStr());
		assertEquals("Take twice daily", prescription.getPrescription().getInstructions());
		assertEquals(9000000000L, prescription.getOfficeVisit().getHcpID());
		assertEquals("1", prescription.getOfficeVisit().getHospitalID());
		assertEquals(2L, prescription.getOfficeVisit().getPatientID());
		assertEquals("06/10/2007", prescription.getOfficeVisit().getVisitDateStr());
	}

	/**
	 * testGetWithNotRightPID
	 * @throws Exception
	 */
	public void testGetWithNotRightPID() throws Exception {
		List<Long> ovIDs = Arrays.asList(5L, 2L);
		List<PrescriptionReportBean> prescriptions = preportDAO.byOfficeVisitAndPatient(ovIDs, 1L); // injection!
		assertEquals(0, prescriptions.size());
	}
}
