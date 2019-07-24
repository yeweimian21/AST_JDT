package edu.ncsu.csc.itrust.unit.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.SurveyBean;

public class SurveyBeanTest extends TestCase {
	
	public void testSurveyBean() throws Exception {
		SurveyBean b = new SurveyBean();
		assertEquals(0L, b.getVisitID());
		
		// test setters
		b.setVisitID(1L);
		b.setSurveyDateString("01/02/2008 12:34");
		b.setWaitingRoomMinutes(100);
		b.setExamRoomMinutes(10);
		b.setVisitSatisfaction(1);
		b.setTreatmentSatisfaction(5);
		
		// confirm with getters
		assertEquals(1L, b.getVisitID());
		assertEquals("01/02/2008 12:34", b.getSurveyDateString());
		assertEquals(100, b.getWaitingRoomMinutes());
		assertEquals(10, b.getExamRoomMinutes());
		assertEquals(1, b.getVisitSatisfaction());
		assertEquals(5, b.getTreatmentSatisfaction());
	}

}
