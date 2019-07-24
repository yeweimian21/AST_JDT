package edu.ncsu.csc.itrust.unit.dao.survey;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.SurveyAction;
import edu.ncsu.csc.itrust.beans.SurveyBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.SurveyDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Tests that a survey was added correctly by adding it to the database and then retrieving the information.
 *
 */
public class AddSurveyTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private SurveyDAO surveyDAO = factory.getSurveyDAO();

	
	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient2();
		gen.surveyResults();
	}
	
	public void testAddSurveyData() throws Exception {
		SurveyBean surveyBean = new SurveyBean();
		
		surveyBean.setSurveyDate(new Date());
		surveyBean.setExamRoomMinutes(30);
		surveyBean.setTreatmentSatisfaction(1);
		surveyBean.setVisitSatisfaction(2);
		
		SurveyAction surveyAction = new SurveyAction(TestDAOFactory.getTestInstance());
		surveyAction.addSurvey(surveyBean, 903);
		
		SurveyBean newBean = surveyDAO.getSurveyData(903);
		String dateStr = (new SimpleDateFormat(SurveyBean.dateFormat).format(new Date()));
		assertEquals(dateStr, newBean.getSurveyDateString());
		assertEquals(903, newBean.getVisitID());
		assertEquals(30, newBean.getExamRoomMinutes());
		assertEquals(1, newBean.getTreatmentSatisfaction());
		assertEquals(2, newBean.getVisitSatisfaction());
		
	}
	
	public void testAddSurveyData2() throws Exception {
		SurveyBean surveyBean = new SurveyBean();
		//all this data would generally be set in the JSP
		String date = "05/02/2008 13:30";
		surveyBean.setSurveyDateString(date);
		surveyBean.setExamRoomMinutes(33);
		surveyBean.setTreatmentSatisfaction(5);
		surveyBean.setVisitSatisfaction(4);
		
		SurveyAction surveyAction = new SurveyAction(TestDAOFactory.getTestInstance());
		surveyAction.addSurvey(surveyBean, 905);
		
		SurveyBean newBean = surveyDAO.getSurveyData(905);
		String dateStr = (new SimpleDateFormat(SurveyBean.dateFormat).format(new Date()));
		assertEquals(dateStr, newBean.getSurveyDateString());
		
		assertEquals(905, newBean.getVisitID());
		assertEquals(33, newBean.getExamRoomMinutes());
		assertEquals(5, newBean.getTreatmentSatisfaction());
		assertEquals(4, newBean.getVisitSatisfaction());
	}
	
	public void testAddSurveyData3() throws Exception {
		SurveyBean surveyBean = new SurveyBean();
		//all this data would generally be set in the JSP
		String date = "05/03/2008 17:30";
		surveyBean.setSurveyDate(new SimpleDateFormat(SurveyBean.dateFormat).parse(date));
		surveyBean.setExamRoomMinutes(2);
		surveyBean.setTreatmentSatisfaction(2);
		surveyBean.setVisitSatisfaction(1);
		
		SurveyAction surveyAction = new SurveyAction(TestDAOFactory.getTestInstance());
		surveyAction.addSurvey(surveyBean, 910);
		
		SurveyBean newBean = surveyDAO.getSurveyData(910);
		String dateStr = (new SimpleDateFormat(SurveyBean.dateFormat).format(new Date()));
		assertEquals(dateStr, newBean.getSurveyDateString());
		assertEquals(910, newBean.getVisitID());
		assertEquals(2, newBean.getExamRoomMinutes());
		assertEquals(2, newBean.getTreatmentSatisfaction());
		assertEquals(1, newBean.getVisitSatisfaction());
		
		//Daniel Rice adding for 80% statement coverage
		assertEquals(2, surveyAction.getPatientMID(910));
	}
	
	public void testIsSurveyCompleted() throws Exception {
		assertEquals(false, surveyDAO.isSurveyCompleted(951L));
		assertEquals(true, surveyDAO.isSurveyCompleted(952L));
	}

}
