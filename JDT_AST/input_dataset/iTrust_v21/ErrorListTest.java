package edu.ncsu.csc.itrust.unit;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.exception.ErrorList;

public class ErrorListTest extends TestCase {
	public void testToString() throws Exception {
		ErrorList errorList = new ErrorList();
		// test add if not null
		errorList.addIfNotNull("a");
		errorList.addIfNotNull("");
		errorList.addIfNotNull("b");
		errorList.addIfNotNull(null);
		String toString = "[";
		// test iterator too
		for (String str : errorList) {
			toString += str + ", ";
		}
		toString = toString.substring(0, toString.length() - 2) + "]";
		assertEquals(toString, errorList.toString());
	}
}
