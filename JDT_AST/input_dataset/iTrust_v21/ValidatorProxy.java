package edu.ncsu.csc.itrust.unit.testutils;

import edu.ncsu.csc.itrust.enums.Gender;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.BeanValidator;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class ValidatorProxy extends BeanValidator<TestBean> {
	
	@Override
	public String checkFormat(String name, Long value, ValidationFormat format, boolean isNullable) {
		return super.checkFormat(name, value, format, isNullable);
	}
	
	@Override
	public String checkFormat(String name, String value, ValidationFormat format, boolean isNullable) {
		return super.checkFormat(name, value, format, isNullable);
	}
	
	@Override
	public String checkGender(String name, Gender gen, ValidationFormat format, boolean isNullable) {
		return super.checkGender(name, gen, format, isNullable);
	}
	
	@Override
	public String checkInt(String name, String value, int lower, int upper, boolean isNullable) {
		return super.checkInt(name, value, lower, upper, isNullable);
	}
	
	@Override 
	public String checkDouble(String name, String value, double lower, double upper) {
		return super.checkDouble(name, value, lower, upper);
	}

	@Override
	public void validate(TestBean bean) throws FormValidationException {
		throw new IllegalStateException("Mock object acts as a proxy to protected BeanValidator classes. Do not call this method");
	}
}
