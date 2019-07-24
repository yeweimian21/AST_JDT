package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ObstetricsRecordBean;
import edu.ncsu.csc.itrust.enums.PregnancyStatus;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.ObstetricsRecordValidator;

public class AddObstetricsValidatorTest extends TestCase {
	
	public void testObstetricsPastPregnancy() throws Exception {
		ObstetricsRecordBean p = new ObstetricsRecordBean();
		
		try {
			p.setDateVisit(null);
			p.setPregnancyStatus(PregnancyStatus.Complete);
			new ObstetricsRecordValidator().validate(p);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("Date Visit is a required field", e.getErrorList().get(0));
		}
		
		p.setDateVisit("12/24/2014");
		p.setYearConception(2013);
		p.setHoursInLabor(10.2);
		p.setWeeksPregnant("34-2");
		new ObstetricsRecordValidator().validate(p);
		
		try {
			p.setDateVisit(null);
			p.setPregnancyStatus(PregnancyStatus.Office);
			new ObstetricsRecordValidator().validate(p);
		} catch (FormValidationException e) {
			assertEquals("Date Visit is a required field", e.getErrorList().get(0));
		}
	}
}

