package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.BeanValidator;
import edu.ncsu.csc.itrust.validate.HospitalBeanValidator;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class HospitalBeanValidatorTest extends TestCase {
	private BeanValidator<HospitalBean> validator = new HospitalBeanValidator();

	public void testAllCorrect() throws Exception {
		HospitalBean h = new HospitalBean();
		h.setHospitalName("Sta. Maria's Children Hospital");
		h.setHospitalID("1234567890");
		validator.validate(h);
	}

	public void testHospitalAllErrors() throws Exception {
		HospitalBean h = new HospitalBean();
		h.setHospitalName("A Hospital!");
		h.setHospitalID("-1");
		h.setHospitalAddress("1000 Toooooooooooooooooo Manyyyyyyyyyyyyyyyyyy Charsssssssssssssssss Streetttttttttttttttt");
		h.setHospitalCity("Longggggggggggggggggggggggg Nameeeeeeeeeeeeeeeeeeeeeeeee Cityyyyyyyyyyyyyyyyy");
		h.setHospitalState("Zx");
		h.setHospitalZip("2-304-22-");
		try {
			validator.validate(h);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("Hospital ID: " + ValidationFormat.HOSPITAL_ID.getDescription(), e.getErrorList().get(0));
			assertEquals("Hospital Name: " + ValidationFormat.HOSPITAL_NAME.getDescription(), e.getErrorList().get(1));
			assertEquals("Hospital Address: " + ValidationFormat.ADDRESS.getDescription(), e.getErrorList().get(2));
			assertEquals("Hospital City: " + ValidationFormat.CITY.getDescription(), e.getErrorList().get(3));
			assertEquals("Hospital State: " + ValidationFormat.STATE.getDescription(), e.getErrorList().get(4));
			assertEquals("Hospital Zip: " + ValidationFormat.ZIPCODE.getDescription(), e.getErrorList().get(5));
			assertEquals("number of errors", 6, e.getErrorList().size());
		}
	}
}
