package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.LOINCbean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.LOINCBeanValidator;

public class LOINCBeanValidatorTest extends TestCase {
	public void testLOINCCorrect() throws Exception {
		LOINCbean bean = new LOINCbean();
		bean.setComponent(" ");
		bean.setKindOfProperty(" ");
		bean.setLabProcedureCode("21324-7");
		bean.setMethodType(" ");
		bean.setScaleType(" ");
		bean.setSystem(" ");
		bean.setTimeAspect(" ");
		new LOINCBeanValidator().validate(bean);
	}

	public void testLOINCIncorrect() throws Exception {
		LOINCbean bean = new LOINCbean();
		try {
			new LOINCBeanValidator().validate(bean);
		fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("You must have a Lab Procedure Code, Component and Kind Of Property", e.getErrorList().get(0));
		}
	}
	
	public void testLOINCcodeIncorrect() throws Exception {
		LOINCbean bean = new LOINCbean();
		bean.setComponent(" ");
		bean.setKindOfProperty(" ");
		bean.setLabProcedureCode("1232343");
		bean.setMethodType(" ");
		bean.setScaleType(" ");
		bean.setSystem(" ");
		bean.setTimeAspect(" ");
		try {
			new LOINCBeanValidator().validate(bean);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("LaboratoryProcedureCode: Must be 1-5 digits followed by a - then another digit", e.getErrorList().get(0));
		}
	}
	
	public void testLOINCRight() throws Exception {
		LOINCbean bean = new LOINCbean();
		bean.setComponent(" ");
		bean.setKindOfProperty(" ");
		bean.setLabProcedureCode("12323-3");
		bean.setMethodType("");
		bean.setScaleType("");
		bean.setSystem("");
		bean.setTimeAspect("");
			new LOINCBeanValidator().validate(bean);
	}

}
