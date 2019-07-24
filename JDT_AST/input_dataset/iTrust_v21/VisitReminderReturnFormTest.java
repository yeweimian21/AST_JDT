package edu.ncsu.csc.itrust.unit.dao.visitreminders;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.VisitFlag;
import edu.ncsu.csc.itrust.beans.forms.VisitReminderReturnForm;

public class VisitReminderReturnFormTest extends TestCase {
	
	
	public void testSetVisitFlags()
	{
		VisitReminderReturnForm vrrf = new VisitReminderReturnForm(90l, 2l, "JONES", "BOB", "111-222-3333");
		VisitFlag[] array = new VisitFlag[] {new VisitFlag("this is the type")};
		vrrf.setVisitFlags(array);
		assertEquals("this is the type", vrrf.getVisitFlags()[0].getType());
	}
	
	public void testOtherFields()
	{
		VisitReminderReturnForm vrrf = new VisitReminderReturnForm(90l, 2l, "JONES", "BOB", "111-222-3333");
		vrrf.setFirstName("actually, it's bob");
		assertEquals("actually, it's bob", vrrf.getFirstName());
		vrrf.setHcpID(2l);
		assertEquals(2l, vrrf.getHcpID());
		vrrf.setPatientID(1l);
		assertEquals(1l, vrrf.getPatientID());
		
	}
	
}
