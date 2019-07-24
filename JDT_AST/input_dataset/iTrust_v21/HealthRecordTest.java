package edu.ncsu.csc.itrust.unit.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.HealthRecord;

public class HealthRecordTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
	}

	public void testZeroHeight() throws Exception {
		HealthRecord r = new HealthRecord();
		r.setHeight(0);
		r.setWeight(200);
		assertTrue(r.getBodyMassIndex() == -1);
		
	}
	

	public void testBodyMass() throws Exception {
		HealthRecord r = new HealthRecord();
		r.setHeight(71);
		r.setWeight(200);
		assertEquals(27.89, r.getBodyMassIndex(), 0.01);
	}
	
	public void testTotalCholesterol() throws Exception {
		HealthRecord r = new HealthRecord();
		r.setCholesterolHDL(5);
		r.setCholesterolLDL(6);
		r.setCholesterolTri(7);
	}
	
	public void testGetBloodPressure() throws Exception {
		HealthRecord r = new HealthRecord();
		r.setBloodPressureN(80);
		r.setBloodPressureD(120);
		assertEquals("80/120", r.getBloodPressure());
		r.setBloodPressureSystolic(90);
		r.setBloodPressureDiastolic(130);
		assertEquals("90/130", r.getBloodPressure());
	}
}
