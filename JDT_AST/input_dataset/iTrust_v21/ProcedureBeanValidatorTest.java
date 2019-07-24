package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.BeanValidator;
import edu.ncsu.csc.itrust.validate.ProcedureBeanValidator;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class ProcedureBeanValidatorTest extends TestCase {
	private BeanValidator<ProcedureBean> validator = new ProcedureBeanValidator();

	public void testAllCorrect() throws Exception {
		ProcedureBean d = new ProcedureBean();
		d.setDescription("A description");
		d.setCPTCode("52563");
		d.setOvProcedureID(0L);
		validator.validate(d);
	}

	public void testPatientAllErrors() throws Exception {
		ProcedureBean d = new ProcedureBean();
		d.setDescription("An description!");
		d.setCPTCode("8000.84");
		try {
			validator.validate(d);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("CPT Code: " + ValidationFormat.CPT.getDescription(), e.getErrorList().get(0));
			assertEquals("Description: " + ValidationFormat.ICD_CODE_DESCRIPTION.getDescription(), e.getErrorList().get(1));
			assertEquals("number of errors", 2, e.getErrorList().size());
		}
	}
}
