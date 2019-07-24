package edu.ncsu.csc.itrust.unit.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.loaders.AllergyBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.DiagnosisBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.FamilyBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.HospitalBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.LOINCBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.LabProcedureBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.MedicationBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.OfficeVisitLoader;
import edu.ncsu.csc.itrust.beans.loaders.OperationalProfileLoader;
import edu.ncsu.csc.itrust.beans.loaders.PrescriptionReportBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.RemoteMonitoringListsBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.ReportRequestBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.SurveyLoader;
import edu.ncsu.csc.itrust.beans.loaders.SurveyResultBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.TransactionBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.VisitReminderReturnFormLoader;

public class BeanLoaderTest extends TestCase {
	
	public void testLoadParameters() throws Exception {
		
		try {
			new AllergyBeanLoader().loadParameters(null, null);
			fail("Should have thrown Exception");
		} catch (IllegalStateException ex) {
			assertEquals("unimplemented!", ex.getMessage());
		}
		
		try {
			new DiagnosisBeanLoader().loadParameters(null, null);
			fail("Should have thrown Exception");
		} catch (IllegalStateException ex) {
			assertEquals("unimplemented!", ex.getMessage());
		}
		
		try {
			new FamilyBeanLoader("self").loadParameters(null, null);
			fail("Should have thrown Exception");
		} catch (IllegalStateException ex) {
			assertEquals("unimplemented!", ex.getMessage());
		}
		
		try {
			new DiagnosisBeanLoader().loadParameters(null, null);
			fail("Should have thrown Exception");
		} catch (IllegalStateException ex) {
			assertEquals("unimplemented!", ex.getMessage());
		}
		
		try {
			new HospitalBeanLoader().loadParameters(null, null);
			fail("Should have thrown Exception");
		} catch (IllegalStateException ex) {
			assertEquals("unimplemented!", ex.getMessage());
		}
		
		try {
			new LabProcedureBeanLoader().loadParameters(null, null);
			fail("Should have thrown Exception");
		} catch (IllegalStateException ex) {
			assertEquals("unimplemented!", ex.getMessage());
		}
		
		try {
			new LOINCBeanLoader().loadParameters(null, null);
			fail("Should have thrown Exception");
		} catch (IllegalStateException ex) {
			assertEquals("unimplemented!", ex.getMessage());
		}
		
		try {
			new MedicationBeanLoader().loadParameters(null, null);
			fail("Should have thrown Exception");
		} catch (IllegalStateException ex) {
			assertEquals("unimplemented!", ex.getMessage());
		}
		
		try {
			new OfficeVisitLoader().loadParameters(null, null);
			fail("Should have thrown Exception");
		} catch (IllegalStateException ex) {
			assertEquals("unimplemented!", ex.getMessage());
		}
		
		try {
			new OperationalProfileLoader().loadParameters(null, null);
			fail("Should have thrown Exception");
		} catch (IllegalStateException ex) {
			assertEquals("unimplemented!", ex.getMessage());
		}
		
		try {
			new PrescriptionReportBeanLoader().loadParameters(null, null);
			fail("Should have thrown Exception");
		} catch (IllegalStateException ex) {
			assertEquals("unimplemented!", ex.getMessage());
		}
		
		try {
			new RemoteMonitoringListsBeanLoader().loadParameters(null, null);
			fail("Should have thrown Exception");
		} catch (IllegalStateException ex) {
			assertEquals("unimplemented!", ex.getMessage());
		}
		
		try {
			new ReportRequestBeanLoader().loadParameters(null, null);
			fail("Should have thrown Exception");
		} catch (IllegalStateException ex) {
			assertEquals("unimplemented!", ex.getMessage());
		}
		
		try {
			new SurveyLoader().loadParameters(null, null);
			fail("Should have thrown Exception");
		} catch (IllegalStateException ex) {
			assertEquals("unimplemented!", ex.getMessage());
		}
		
		try {
			new SurveyResultBeanLoader().loadParameters(null, null);
			fail("Should have thrown Exception");
		} catch (IllegalStateException ex) {
			assertEquals("unimplemented!", ex.getMessage());
		}
		
		try {
			new TransactionBeanLoader().loadParameters(null, null);
			fail("Should have thrown Exception");
		} catch (IllegalStateException ex) {
			assertEquals("unimplemented!", ex.getMessage());
		}
		
		try {
			new VisitReminderReturnFormLoader().loadParameters(null, null);
			fail("Should have thrown Exception");
		} catch (IllegalStateException ex) {
			assertEquals("unimplemented!", ex.getMessage());
		}
		
	}

}
