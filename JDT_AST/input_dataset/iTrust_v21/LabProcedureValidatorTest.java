package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.LabProcedureValidator;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

/**
 * LabProcedureValidatorTest
 */
public class LabProcedureValidatorTest extends TestCase {
	
	/**
	 * testLabProcedureAllCorrect
	 * @throws Exception
	 */
	public void testLabProcedureAllCorrect() throws Exception {
		LabProcedureBean l = new LabProcedureBean();
		l.setCommentary("This is it");
		l.setLoinc("00000-0");
		l.setPid(1L);
		l.statusPending();
		l.allow();
		new LabProcedureValidator().validate(l);
	}

	/**
	 * testLabProcedureAllErrors
	 * @throws Exception
	 */
	public void testLabProcedureAllErrors() throws Exception {
		LabProcedureBean l = new LabProcedureBean();
		l.setCommentary("This is it");
		l.setLoinc("0000-00");
		l.setPid(1L);
		l.setResults(">");
		l.setCommentary("<");
		l.setStatus("not allowed");
		l.setRights("DENIED");
		
		try {
			new LabProcedureValidator().validate(l);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("LOINC: " + ValidationFormat.LOINC.getDescription(), e.getErrorList().get(0));
			assertEquals("Commentary: " + ValidationFormat.LABPROCEDURE_COMMENTS.getDescription(), e.getErrorList().get(1));
			assertEquals("Results: " + ValidationFormat.LABPROCEDURE_COMMENTS.getDescription(), e.getErrorList().get(2));
			assertEquals("Status: " + ValidationFormat.LAB_STATUS.getDescription(), e.getErrorList().get(3));
			assertEquals("Rights: " + ValidationFormat.LAB_RIGHTS.getDescription(), e.getErrorList().get(4));
			assertEquals("number of errors", 5, e.getErrorList().size());
		}
	}
	
	/**
	 * testValidNumericalResults
	 * @throws Exception
	 */
	public void testValidNumericalResults() throws Exception {
		LabProcedureBean bean = new LabProcedureBean();
		bean.setCommentary("This is it");
		bean.setLoinc("00000-0");
		bean.setPid(1L);
		bean.statusPending();
		bean.allow();
		
		bean.setNumericalResult("1.0");
		bean.setUpperBound("-3.");
		bean.setLowerBound("-.5");
		new LabProcedureValidator().validate(bean);
		bean.setNumericalResult("1");
		new LabProcedureValidator().validate(bean);
	}
	
	/**
	 * testInvalidNumericalResults
	 * @throws Exception
	 */
	public void testInvalidNumericalResults() throws Exception {
		LabProcedureValidator validator = new LabProcedureValidator();
		LabProcedureBean bean = new LabProcedureBean();
		bean.setCommentary("This is it");
		bean.setLoinc("00000-0");
		bean.setPid(1L);
		bean.statusPending();
		bean.allow();
		
		bean.setNumericalResult("123456789012345678901"); // Too long
		try {
			validator.validate(bean);
			fail("Expected validation to fail.");
		} catch (FormValidationException e) {
			assertEquals(1, e.getErrorList().size());
			String expect = "Numerical Result: " + ValidationFormat.LABPROCEDURE_NUMRESULT_LENGTH.getDescription();
			assertEquals(expect, e.getErrorList().get(0));
		}
		
		bean.setNumericalResult("-"); // No digits
		try {
			validator.validate(bean);
			fail("Expected validation to fail.");
		} catch (FormValidationException e) {
			assertEquals(1, e.getErrorList().size());
			String expect = "Numerical Result: " + ValidationFormat.LABPROCEDURE_NUMRESULT_CONTENT.getDescription();
			assertEquals(expect, e.getErrorList().get(0));
		}

		bean.setNumericalResult("1.2.3"); // Extra decimal point
		try {
			validator.validate(bean);
			fail("Expected validation to fail.");
		} catch (FormValidationException e) {
			assertEquals(1, e.getErrorList().size());
			String expect = "Numerical Result: " + ValidationFormat.LABPROCEDURE_NUMRESULT_CONTENT.getDescription();
			assertEquals(expect, e.getErrorList().get(0));
		}

		bean.setNumericalResult("1-2"); // Sign in wrong location
		try {
			validator.validate(bean);
			fail("Expected validation to fail.");
		} catch (FormValidationException e) {
			assertEquals(1, e.getErrorList().size());
			String expect = "Numerical Result: " + ValidationFormat.LABPROCEDURE_NUMRESULT_CONTENT.getDescription();
			assertEquals(expect, e.getErrorList().get(0));
		}
	}
}
