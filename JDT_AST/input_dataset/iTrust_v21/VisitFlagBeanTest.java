package edu.ncsu.csc.itrust.unit.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.VisitFlag;

public class VisitFlagBeanTest extends TestCase {
	
	
	public void testSetTypeAndValue()
	{
		VisitFlag vf = new VisitFlag("typeonly");
		vf.setType("a type");
		vf.setValue("the value");
		assertEquals("a type", vf.getType());
		assertEquals("the value", vf.getValue());
	}
}
