package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.ViewSurveyResultAction;
import edu.ncsu.csc.itrust.beans.SurveyResultBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class ViewSurveyResultsActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private ViewSurveyResultAction action = new ViewSurveyResultAction(factory);
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		gen.surveyResults();
	}

	public void testGetResultsByZipAndSpecialty() throws Exception {
		SurveyResultBean bean = new SurveyResultBean();
		bean.setHCPzip("10453");
		bean.setHCPspecialty(SurveyResultBean.SURGEON_SPECIALTY);
		List<SurveyResultBean> list = action.getSurveyResultsForZip(bean);
		SurveyResultBean bean0 = list.get(0);
		assertEquals("10453", bean0.getHCPzip());
		assertEquals("Doctor", bean0.getHCPLastName());
		assertEquals("surgeon", bean0.getHCPspecialty()); // hardcoded surgeon b/c of the capitalization
															// difference

	}

	public void testGetResultsByHopsitalID() throws Exception {
		SurveyResultBean bean = new SurveyResultBean();
		bean.setHCPhospital("9191919191");
		bean.setHCPspecialty(SurveyResultBean.ANY_SPECIALTY);
		List<SurveyResultBean> list = action.getSurveyResultsForHospital(bean);
		SurveyResultBean bean0 = list.get(0);
		assertEquals("10453", bean0.getHCPzip());
		assertEquals("Doctor", bean0.getHCPLastName());
		assertEquals("surgeon", bean0.getHCPspecialty()); // hardcoded surgeon b/c of the capitalization
															// difference
	}

	public void testGetResultsByWrongZip() throws Exception {
		SurveyResultBean bean = new SurveyResultBean();
		bean.setHCPzip("12388a");
		bean.setHCPspecialty(SurveyResultBean.SURGEON_SPECIALTY);
		try {
			action.getSurveyResultsForZip(bean);
			fail("Exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals(1, e.getErrorList().size());
			assertEquals("Zip Code: " + ValidationFormat.ZIPCODE.getDescription(), e.getErrorList().get(0));
		}
	}

}
