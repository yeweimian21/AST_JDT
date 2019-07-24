package edu.ncsu.csc.itrust.unit.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.AddRemoteMonitoringDataAction;
import edu.ncsu.csc.itrust.beans.RemoteMonitoringDataBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * AddRemoteMonitoringDataActionTest
 */
public class AddRemoteMonitoringDataActionTest extends TestCase {
	AddRemoteMonitoringDataAction action;
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hcp0();
		gen.patient1();
		gen.patient2();
		action = new AddRemoteMonitoringDataAction(TestDAOFactory.getTestInstance(), 1, 1);
	}

	/**
	 * testAddRemoteMOnitoringData
	 * @throws Exception
	 */
	public void testAddRemoteMonitoringData() throws Exception {
		try {
			RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
			b.setGlucoseLevel(80);
			b.setSystolicBloodPressure(80);
			b.setDiastolicBloodPressure(80);
			action.addRemoteMonitoringData(b);
		} catch(FormValidationException e) {
			fail();
		}
	}
	
	/**
	 * testAddRemoteMonitoringDataUAP
	 * @throws Exception
	 */
	public void testAddRemoteMonitoringDataUAP() throws Exception {
		gen.uap1();
		gen.remoteMonitoringUAP();
		AddRemoteMonitoringDataAction actionUAP = new AddRemoteMonitoringDataAction(TestDAOFactory.getTestInstance(), Long.parseLong("8000000009"), 2);
		try {
			RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
			b.setGlucoseLevel(80);
			b.setSystolicBloodPressure(80);
			b.setDiastolicBloodPressure(80);
			actionUAP.addRemoteMonitoringData(b);
		} catch(FormValidationException e) {
			fail();
		}
	}
	
	/**
	 * testAddRemoteMonitoringDataGlucoseOnly
	 * @throws Exception
	 */
	public void testAddRemoteMonitoringDataGlucoseOnly() throws Exception {
		try {
			RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
			b.setGlucoseLevel(80);
			action.addRemoteMonitoringData(b);
		} catch(FormValidationException e) {
			fail();
		}
	}

	/**
	 * testAddRemoteMonitoringDataGlucoseOnlyUAP
	 * @throws Exception
	 */
	public void testAddRemoteMonitoringDataGlucoseOnlyUAP() throws Exception {
		gen.uap1();
		gen.remoteMonitoringUAP();
		AddRemoteMonitoringDataAction actionUAP = new AddRemoteMonitoringDataAction(TestDAOFactory.getTestInstance(), Long.parseLong("8000000009"), 2);
		try {
			RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
			b.setGlucoseLevel(80);
			actionUAP.addRemoteMonitoringData(b);
		} catch(FormValidationException e) {
			fail();
		}
	}

	/**
	 * testAddRemoteMonitoringDataBloodPressureOnly
	 * @throws Exception
	 */
	public void testAddRemoteMonitoringDataBloodPressureOnly() throws Exception {
		try {
			RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
			b.setSystolicBloodPressure(100);
			b.setDiastolicBloodPressure(80);
			action.addRemoteMonitoringData(b);
		} catch(FormValidationException e) {
			fail();
		}
	}

	/**
	 * testAddRemoteMonitoringDataBloodPressureOnlyUAP
	 * @throws Exception
	 */
	public void testAddRemoteMonitoringDataBloodPressureOnlyUAP() throws Exception {
		gen.uap1();
		gen.remoteMonitoringUAP();
		AddRemoteMonitoringDataAction actionUAP = new AddRemoteMonitoringDataAction(TestDAOFactory.getTestInstance(), Long.parseLong("8000000009"), 2);
		try {
			RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
			b.setSystolicBloodPressure(100);
			b.setDiastolicBloodPressure(80);
			actionUAP.addRemoteMonitoringData(b);
		} catch(FormValidationException e) {
			fail();
		}
	}
	
	/**
	 * testAddRemoteMonitoringWeightDataOnly
	 * @throws Exception
	 */
	public void testAddRemoteMonitoringWeightDataOnly() throws Exception {
		try {
			RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
			b.setWeight(100);
			action.addRemoteMonitoringData(b);
		} catch(FormValidationException e) {
			fail();
		}
	}
	
	/**
	 * testAddRemoteMonitoringPedometerReadingDataOnly
	 * @throws Exception
	 */
	public void testAddRemoteMonitoringPedometerReadingDataOnly() throws Exception {
		try {
			RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
			b.setPedometerReading(1234);
			action.addRemoteMonitoringData(b);
		} catch(FormValidationException e) {
			fail();
		}
	}
	
	/**
	 * testAddRemoteMonitoringExternalDataOnly
	 * @throws Exception
	 */
	public void testAddRemoteMonitoringExternalDataOnly() throws Exception {
		try {
			RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
			b.setWeight(122);
			b.setPedometerReading(1234);
			action.addRemoteMonitoringData(b);
		} catch(FormValidationException e) {
			fail();
		}
	}
	
	/**
	 * testAddRemoteMonitoringHeightWeightDataOnly
	 * @throws Exception
	 */
	public void testAddRemoteMonitoringHeightWeightDataOnly() throws Exception {
		try {
			RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
			b.setHeight(155.2f);
			b.setWeight(140.0f);
			action.addRemoteMonitoringData(b);
		} catch(FormValidationException e) {
			fail();
		}
	}

	/**
	 * testAddBadRemoteMonitoringData
	 * @throws Exception
	 */
	public void testAddBadRemoteMonitoringData() throws Exception {
		RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
		try {
			b.setSystolicBloodPressure(39);
			b.setDiastolicBloodPressure(100);
			action.addRemoteMonitoringData(b);
			fail();
		} catch(FormValidationException e) {
			//TODO
		}
		
		try {
			b = new RemoteMonitoringDataBean();
			b.setSystolicBloodPressure(240);
			b.setDiastolicBloodPressure(151);
			b.setGlucoseLevel(100);
			action.addRemoteMonitoringData(b);
			fail();
		} catch(FormValidationException e) {
			//TODO
		}
		
		try {
			b = new RemoteMonitoringDataBean();
			b.setSystolicBloodPressure(40);
			b.setDiastolicBloodPressure(150);
			b.setGlucoseLevel(1000);
			action.addRemoteMonitoringData(b);
			fail();
		} catch(FormValidationException e) {
			//TODO
		}
		
		try {
			b = new RemoteMonitoringDataBean();
			b.setSystolicBloodPressure(-5);
			b.setDiastolicBloodPressure(20);
			b.setGlucoseLevel(0);
			action.addRemoteMonitoringData(b);
			fail();
		} catch(FormValidationException e) {
			//TODO
		}
		
		b = new RemoteMonitoringDataBean();
		b.setSystolicBloodPressure(100);
		b.setDiastolicBloodPressure(80);
		b.setGlucoseLevel(100);
		
		for (int i = 0; i < 10; i++)
			action.addRemoteMonitoringData(b);
		
		try {
			b = new RemoteMonitoringDataBean();
			b.setSystolicBloodPressure(100);
			b.setDiastolicBloodPressure(80);
			b.setGlucoseLevel(100);
			action.addRemoteMonitoringData(b);
			fail(); //Should throw an exception - 11 entries.
		} catch(ITrustException e) {
			String s = e.getMessage();
			assertTrue(s.startsWith("Patient "));
			assertTrue(s.endsWith(" entries for today cannot exceed 10."));
		} try {
			b = new RemoteMonitoringDataBean();
			b.setWeight(122);
			b.setPedometerReading(1233);
			action.addRemoteMonitoringData(b);
			action.addRemoteMonitoringData(b);
			fail(); //Should throw an exception - 2 entries
		} catch(ITrustException e){
			String s = e.getMessage();
			assertTrue(s.startsWith("Patient "));
			assertTrue(s.endsWith(" entries for today cannot exceed 1."));
		}
	}
	
	/**
	 * testAddBadRemoteMonitoringWerightData
	 * @throws Exception
	 */
	public void testAddBadRemoteMonitoringWeightData() throws Exception {
		try {
			RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
			b.setWeight(100);
			action.addRemoteMonitoringData(b);
			action.addRemoteMonitoringData(b);
			fail();
		} catch(ITrustException e) {
			assertEquals("Patient weight entries for today cannot exceed 1.", e.getMessage());
		}
	}
	
	/**
	 * testAddBadRemoteMonitoringPedometerReadingData
	 * @throws Exception
	 */
	public void testAddBadRemoteMonitoringPedometerReadingData() throws Exception {
		try {
			RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
			b.setPedometerReading(100);
			action.addRemoteMonitoringData(b);
			action.addRemoteMonitoringData(b);
			fail();
		} catch(ITrustException e) {
			assertEquals("Patient pedometer reading entries for today cannot exceed 1.", e.getMessage());
		}
	}
	
	/**
	 * testRepresentativeReportStatus
	 * @throws Exception
	 */
	public void testRepresentativeReportStatus() throws Exception {
		action = new AddRemoteMonitoringDataAction(TestDAOFactory.getTestInstance(), 2, 1);
		try {
			RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
			b.setPedometerReading(100);
			action.addRemoteMonitoringData(b);
		} catch(ITrustException e) {
			fail();
		}
	}
	
	/**
	 * testAddBadRemoteMonitoringGlucoseLevelData
	 * @throws Exception
	 */
	public void testAddBadRemoteMonitoringGlucoseLevelData() throws Exception {
		try {
			RemoteMonitoringDataBean b = new RemoteMonitoringDataBean();
			b.setGlucoseLevel(100);
			
			for (int i = 0; i < 12; i++)
				action.addRemoteMonitoringData(b);
			fail();
		} catch(ITrustException e) {
			assertEquals("Patient glucose level entries for today cannot exceed 10.", e.getMessage());
		}
	}
}
