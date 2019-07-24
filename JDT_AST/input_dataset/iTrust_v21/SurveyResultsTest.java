package edu.ncsu.csc.itrust.unit.dao.surveyresults;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.SurveyResultBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.SurveyResultDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * 
 *
 */

public class SurveyResultsTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private SurveyResultDAO surveyResultDAO = factory.getSurveyResultDAO();
	
	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		gen.surveyResults();
	}
	
	public void testSurveyResults1() throws Exception {
		List<SurveyResultBean> list = surveyResultDAO.getSurveyResultsForZip("27613", SurveyResultBean.ANY_SPECIALTY);	
		assertEquals(2, list.size());
		SurveyResultBean bean = list.get(0);
		assertEquals(9100000000L, bean.getHCPMID());
		assertEquals("Good", bean.getHCPFirstName());
		assertEquals("Doctor", bean.getHCPLastName());
		assertEquals("Street 1", bean.getHCPaddress1());
		assertEquals("Street 2", bean.getHCPaddress2());
		assertEquals("Raleigh", bean.getHCPcity());
		assertEquals("NC", bean.getHCPstate());
		assertEquals("27613", bean.getHCPzip());
		assertEquals(SurveyResultBean.ANY_SPECIALTY, bean.getHCPspecialty());
		assertEquals("na", bean.getHCPhospital());
		assertEquals(10.0F, bean.getAvgWaitingRoomMinutes());
		assertEquals(10.0F, bean.getAvgExamRoomMinutes());
		assertEquals(4.6667F, bean.getAvgVisitSatisfaction());
		assertEquals(5.0F, bean.getAvgTreatmentSatisfaction());
		assertEquals(50F, bean.getPercentSatisfactionResults());
		bean = list.get(1);
		assertEquals(9100000001L, bean.getHCPMID());
		assertEquals("Bad", bean.getHCPFirstName());
		assertEquals("Doctor", bean.getHCPLastName());
		assertEquals("Avenue 1", bean.getHCPaddress1());
		assertEquals("Avenue 2", bean.getHCPaddress2());
		assertEquals("Raleigh", bean.getHCPcity());
		assertEquals("NC", bean.getHCPstate());
		assertEquals("27613", bean.getHCPzip());
		assertEquals(SurveyResultBean.HEART_SPECIALTY, bean.getHCPspecialty());
		assertEquals("na", bean.getHCPhospital());
		assertEquals(20.0F, bean.getAvgWaitingRoomMinutes());
		assertEquals(30.0F, bean.getAvgExamRoomMinutes());
		assertEquals(1.0F, bean.getAvgVisitSatisfaction());
		assertEquals(2.0F, bean.getAvgTreatmentSatisfaction());
		assertEquals(75F, bean.getPercentSatisfactionResults());
	}
	
	public void testSurveyResultsNoZip() throws Exception {
		List<SurveyResultBean> list = surveyResultDAO.getSurveyResultsForZip("99999", SurveyResultBean.ANY_SPECIALTY);	
		assertEquals(0, list.size());
	}
	
	public void testSurveyResultsHeartSpecialty() throws Exception {
		List<SurveyResultBean> list = surveyResultDAO.getSurveyResultsForZip("27613", SurveyResultBean.HEART_SPECIALTY);	
		SurveyResultBean bean = list.get(0);
		assertEquals(1, list.size());
		assertEquals(9100000001L, bean.getHCPMID());
		assertEquals("Bad", bean.getHCPFirstName());
		assertEquals("Doctor", bean.getHCPLastName());
		assertEquals("Avenue 1", bean.getHCPaddress1());
		assertEquals("Avenue 2", bean.getHCPaddress2());
		assertEquals("Raleigh", bean.getHCPcity());
		assertEquals("NC", bean.getHCPstate());
		assertEquals("27613", bean.getHCPzip());
		assertEquals(SurveyResultBean.HEART_SPECIALTY, bean.getHCPspecialty());
		assertEquals("na", bean.getHCPhospital());
		assertEquals(20.0F, bean.getAvgWaitingRoomMinutes());
		assertEquals(30.0F, bean.getAvgExamRoomMinutes());
		assertEquals(1.0F, bean.getAvgVisitSatisfaction());
		assertEquals(2.0F, bean.getAvgTreatmentSatisfaction());
		assertEquals(75F, bean.getPercentSatisfactionResults());
	}
	
	public void testSurveyResultsHospitalAnySpecialty() throws Exception {
		List<SurveyResultBean> list = surveyResultDAO.getSurveyResultsForHospital("8181818181", SurveyResultBean.ANY_SPECIALTY);	
		assertEquals(3, list.size());
		SurveyResultBean bean = list.get(1);
		assertEquals(9100000000L, bean.getHCPMID());
		assertEquals("Good", bean.getHCPFirstName());
		assertEquals("Doctor", bean.getHCPLastName());
		assertEquals("Street 1", bean.getHCPaddress1());
		assertEquals("Street 2", bean.getHCPaddress2());
		assertEquals("Raleigh", bean.getHCPcity());
		assertEquals("NC", bean.getHCPstate());
		assertEquals("27613", bean.getHCPzip());
		assertEquals(SurveyResultBean.ANY_SPECIALTY, bean.getHCPspecialty());
		assertEquals("8181818181", bean.getHCPhospital());
		assertEquals(10.0F, bean.getAvgWaitingRoomMinutes());
		assertEquals(10.0F, bean.getAvgExamRoomMinutes());
		assertEquals(4.6667F, bean.getAvgVisitSatisfaction());
		assertEquals(5.0F, bean.getAvgTreatmentSatisfaction());
		assertEquals(50F, bean.getPercentSatisfactionResults());
		bean = list.get(2);
		assertEquals(9100000001L, bean.getHCPMID());
		assertEquals("Bad", bean.getHCPFirstName());
		assertEquals("Doctor", bean.getHCPLastName());
		assertEquals("Avenue 1", bean.getHCPaddress1());
		assertEquals("Avenue 2", bean.getHCPaddress2());
		assertEquals("Raleigh", bean.getHCPcity());
		assertEquals("NC", bean.getHCPstate());
		assertEquals("27613", bean.getHCPzip());
		assertEquals(SurveyResultBean.HEART_SPECIALTY, bean.getHCPspecialty());
		assertEquals("8181818181", bean.getHCPhospital());
		assertEquals(20.0F, bean.getAvgWaitingRoomMinutes());
		assertEquals(30.0F, bean.getAvgExamRoomMinutes());
		assertEquals(1.0F, bean.getAvgVisitSatisfaction());
		assertEquals(2.0F, bean.getAvgTreatmentSatisfaction());
		assertEquals(75F, bean.getPercentSatisfactionResults());
	}
	
	public void testSurveyResultsBadHospitalID() throws Exception {
		List<SurveyResultBean> list = surveyResultDAO.getSurveyResultsForHospital("badhospid", SurveyResultBean.ANY_SPECIALTY);	
		assertEquals(0, list.size());
	}

	public void testSurveyResultsHospitalAnySpecialty2() throws Exception {
		List<SurveyResultBean> list = surveyResultDAO.getSurveyResultsForHospital("9191919191", SurveyResultBean.ANY_SPECIALTY);	
		assertEquals(2, list.size());
		SurveyResultBean bean = list.get(1);
		assertEquals(9100000000L, bean.getHCPMID());
		assertEquals("Good", bean.getHCPFirstName());
		assertEquals("Doctor", bean.getHCPLastName());
		assertEquals(50F, bean.getPercentSatisfactionResults());
	}
	
	public void testSurveyResultsNothing() throws Exception {
		List<SurveyResultBean> list = surveyResultDAO.getSurveyResultsForHospital("9191919191", SurveyResultBean.HEART_SPECIALTY);	
		assertEquals(0, list.size());
	}
	
	public void testSurveyResultsHeartSpecialtyAndHospital() throws Exception {
		List<SurveyResultBean> list = surveyResultDAO.getSurveyResultsForHospital("8181818181", SurveyResultBean.HEART_SPECIALTY);	
		assertEquals(1, list.size());
		SurveyResultBean bean = list.get(0);
		assertEquals(9100000001L, bean.getHCPMID());
		assertEquals("Bad", bean.getHCPFirstName());
		assertEquals("Doctor", bean.getHCPLastName());
		assertEquals(75F, bean.getPercentSatisfactionResults());
	}
	
}
