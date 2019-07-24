package edu.ncsu.csc.itrust.unit.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.SurveyResultBean;

/**
 * 
 *
 */

public class SurveyResultBeanTest extends TestCase {
	
	public void testSurveyResultBean() {
		
		// build a bean and stuff in some data
		SurveyResultBean b = new SurveyResultBean();
		b.setHCPMID(55L);
		b.setHCPFirstName("Alfred");
		b.setHCPLastName("Kinsey");
		b.setHCPhospital("Kinsey Institute for Research in Sex, Gender and Reproduction");
		b.setHCPspecialty("Sexual Behavior");
		b.setHCPaddress1("1 Big O Street");
		b.setHCPaddress2("Obsession Suite");
		b.setHCPcity("Bloomington");
		b.setHCPstate("IN");
		b.setHCPzip("47401");
		b.setAvgExamRoomMinutes(55.55F);
		b.setAvgWaitingRoomMinutes(44.44F);
		b.setAvgVisitSatisfaction(2.2F);
		b.setAvgTreatmentSatisfaction(3.3F);
		b.setPercentSatisfactionResults(25.25F);
		
		// now test the getters
		assertEquals(55L, b.getHCPMID());
		assertEquals("Alfred", b.getHCPFirstName());
		assertEquals("Kinsey", b.getHCPLastName());
		assertEquals("Kinsey Institute for Research in Sex, Gender and Reproduction", b.getHCPhospital());
		assertEquals("Sexual Behavior", b.getHCPspecialty());
		assertEquals("1 Big O Street", b.getHCPaddress1());
		assertEquals("Obsession Suite", b.getHCPaddress2());
		assertEquals("Bloomington", b.getHCPcity());
		assertEquals("IN", b.getHCPstate());
		assertEquals("47401", b.getHCPzip());
		assertEquals(55.55F, b.getAvgExamRoomMinutes());
		assertEquals(44.44F, b.getAvgWaitingRoomMinutes());
		assertEquals(2.2F, b.getAvgVisitSatisfaction());
		assertEquals(3.3F, b.getAvgTreatmentSatisfaction());
		assertEquals(25.25F, b.getPercentSatisfactionResults());
	}

}
