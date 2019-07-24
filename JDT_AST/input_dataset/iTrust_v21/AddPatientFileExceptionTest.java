package edu.ncsu.csc.itrust.unit.exception;

import edu.ncsu.csc.itrust.exception.AddPatientFileException;
import junit.framework.TestCase;

public class AddPatientFileExceptionTest extends TestCase {
	public void testMessage() throws Exception {
		try{
			throw new AddPatientFileException("Test");
		}catch(AddPatientFileException e){
			assertTrue(e.getMessage().equals("Test"));
		}
	}
}
