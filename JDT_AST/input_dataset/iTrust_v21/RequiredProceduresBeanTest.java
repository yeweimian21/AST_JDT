package edu.ncsu.csc.itrust.unit.bean;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.RequiredProceduresBean;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;

@SuppressWarnings("unused")
public class RequiredProceduresBeanTest extends TestCase {

	public void testGetSetCptCode() throws Exception {
		RequiredProceduresBean bean = new RequiredProceduresBean();
		
		String testString = "Test";
		//Test that the cptCode is properly set and gotten
		bean.setCptCode(testString);
		assertEquals(testString, bean.getCptCode());
	}
	
	public void testGetDescription() throws Exception {
		RequiredProceduresBean bean = new RequiredProceduresBean();
		
		String testString = "Test";
		//Test that the description is properly set and gotten
		bean.setDescription(testString);
		assertEquals(testString, bean.getDescription());
	}
	
	public void testGetSetAgeGroup() throws Exception {
		RequiredProceduresBean bean = new RequiredProceduresBean();
		
		int testInt = 1;
		//Test that the age group is properly set and gotten
		bean.setAgeGroup(testInt);
		assertEquals(testInt, bean.getAgeGroup());
	}
	
	public void testGetSetAttribute() throws Exception {
		RequiredProceduresBean bean = new RequiredProceduresBean();
		
		String testString = "Test";
		//Test that the attribute is properly set and gotten
		bean.setAttribute(testString);
		assertEquals(testString, bean.getAttribute());
	}
	
	public void testGetSetAgeMax() throws Exception {
		RequiredProceduresBean bean = new RequiredProceduresBean();
		
		int testInt = 1;
		//Test that the age group is properly set and gotten
		bean.setAgeMax(testInt);
		assertEquals(testInt, bean.getAgeMax());	
	}
}
