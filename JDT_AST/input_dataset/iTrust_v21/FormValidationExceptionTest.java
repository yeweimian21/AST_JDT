package edu.ncsu.csc.itrust.unit.exception;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import junit.framework.TestCase;

public class FormValidationExceptionTest extends TestCase {
	public void testMessage() throws Exception {
		ErrorList el = new ErrorList();
		el.addIfNotNull("a");
		FormValidationException e = new FormValidationException(el);
		assertEquals("This form has not been validated correctly. "
				+ "The following field are not properly filled in: [a]", e.getMessage());
		MockJSPWriter writer = new MockJSPWriter();
		e.printHTML(writer);
		assertEquals("<h2>Information not valid</h2><div class=\"errorList\">a<br /></div>", writer.input);
		assertEquals("<h2>Information not valid</h2><div class=\"errorList\">a<br /></div>", e.printHTMLasString());
	}
}
