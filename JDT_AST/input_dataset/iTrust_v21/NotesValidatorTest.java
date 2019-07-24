package edu.ncsu.csc.itrust.unit.validate.regex;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.unit.testutils.ValidatorProxy;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

/**
 * NotesValidatorTest
 */
public class NotesValidatorTest extends TestCase {
	private ValidatorProxy validatorProxy = new ValidatorProxy();
	private static final ValidationFormat VALIDATION_FORMAT = ValidationFormat.NOTES;
	private static final String PASSED = "";
	private static final String FAILED = "Name: " + VALIDATION_FORMAT.getDescription();

	/**
	 * testNotesGood
	 * @throws Exception
	 */
	public void testNotesGood() throws Exception {
		String value = "This is a very long set of notes?!_.";
		String errorMessage = PASSED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}

	/**
	 * testNotesTooLong
	 * @throws Exception
	 */
	public void testNotesTooLong() throws Exception {
		String chunkOfTen = "1234567890";
		String value = "a";
		for (int i = 0; i < 30; i++) {
			value += chunkOfTen; // make 301 - BVA baby!
		}
		String errorMessage = FAILED;
		assertEquals(errorMessage, validatorProxy.checkFormat("Name", value, VALIDATION_FORMAT, false));
	}
}
