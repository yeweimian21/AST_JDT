package edu.ncsu.csc.itrust.unit.dao.officevisit;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.ViewDiagnosisStatisticsAction;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.beans.DiagnosisStatisticsBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.DiagnosesDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Test client diagnoses
 */
public class OVDiagnosesTest extends TestCase {
	private DiagnosesDAO diagDAO = TestDAOFactory.getTestInstance().getDiagnosesDAO();
	private int thisYear = Calendar.getInstance().get(Calendar.YEAR);
	
	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		
		gen.standardData();
		gen.patient_hcp_vists();
		gen.hcp_diagnosis_data();
	}

	/**
	 * testAddRemoveOneOVDiagnosis
	 * @throws Exception
	 */
	public void testAddRemoveOneOVDiagnosis() throws Exception {
		assertEquals("no current diagnoses on office vist 1", 0, diagDAO.getList(1).size());
		DiagnosisBean bean = new DiagnosisBean();
		bean.setICDCode("250.1");
		bean.setVisitID(1);
		long ovDID = diagDAO.add(bean);
		List<DiagnosisBean> diagnoses = diagDAO.getList(1);
		assertEquals("now there's 1", 1, diagnoses.size());
		assertEquals("test the description", "Diabetes with ketoacidosis", diagnoses.get(0).getDescription());
		diagDAO.remove(ovDID);
		assertEquals("now there's none", 0, diagDAO.getList(1).size());
	}

	/**
	 * testAddBadDiagnosis
	 * @throws Exception
	 */
	public void testAddBadDiagnosis() throws Exception {
		DiagnosisBean bean = new DiagnosisBean();
		bean.setVisitID(-1);
		bean.setICDCode(null);
		try {
			diagDAO.add(bean);
			fail("expected an exception");
		} catch (DBException e) {
			//TODO
		}
	}
	
	/**
	 * testEditBadDiagnosis
	 * @throws Exception
	 */
	public void testEditBadDiagnosis() throws Exception {
		DiagnosisBean bean = new DiagnosisBean();
		bean.setVisitID(-1);
		bean.setICDCode(null);
		try {
			diagDAO.edit(bean);
		} catch (DBException e) {
			//TODO
		}
	}
	
	/**
	 * testGetStatisticsValid
	 * @throws Exception
	 */
	public void testGetStatisticsValid() throws Exception {
		Date lower = new SimpleDateFormat("MM/dd/yyyy").parse("06/28/2011");
		Date upper = new SimpleDateFormat("MM/dd/yyyy").parse("09/28/2011");
		DiagnosisStatisticsBean dsBean = diagDAO.getDiagnosisCounts("487.00", "27607", lower, upper);
		assertEquals(3, dsBean.getZipStats());
		assertEquals(5, dsBean.getRegionStats());
	}
	
	/**
	 * testGetWeeklyStatisticsValid
	 * @throws Exception
	 */
	public void testGetWeeklyStatisticsValid() throws Exception {
		Date lower = new SimpleDateFormat("MM/dd/yyyy").parse("06/28/2011");
		Date upper = new SimpleDateFormat("MM/dd/yyyy").parse("09/28/2011");
		List<DiagnosisStatisticsBean> db = diagDAO.getWeeklyCounts("487.00", "27607", lower, upper);
		
		assertEquals(new SimpleDateFormat("MM/dd/yyyy").parse("06/27/2011"), db.get(0).getStartDate());
		assertEquals(new SimpleDateFormat("MM/dd/yyyy").parse("07/03/2011"), db.get(0).getEndDate());
		assertEquals(new SimpleDateFormat("MM/dd/yyyy").parse("09/26/2011"), db.get(db.size()-1).getStartDate());
		
		long totalRegion = 0;
		for (DiagnosisStatisticsBean d : db) {
			totalRegion += d.getRegionStats();
		}
		//If previous test fails, this test may fail
		long totalRegionNonsplit = diagDAO.getDiagnosisCounts("487.00", "27607", lower, upper).getRegionStats();
		assertEquals(totalRegionNonsplit, totalRegion);
	}
	
	/**
	 * testMalaria
	 * @throws Exception
	 */
	public void testMalaria() throws Exception {
		DAOFactory factory = TestDAOFactory.getTestInstance();
		TestDataGenerator gen = new TestDataGenerator();
		gen.malaria_epidemic();
		
		ViewDiagnosisStatisticsAction a = new ViewDiagnosisStatisticsAction(factory);
		
		assertTrue(a.isMalariaEpidemic("11/02/" + thisYear, "27607", "110"));
		
	}
	
}
