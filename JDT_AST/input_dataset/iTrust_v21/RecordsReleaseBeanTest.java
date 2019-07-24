package edu.ncsu.csc.itrust.unit.bean;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import edu.ncsu.csc.itrust.beans.RecordsReleaseBean;
import junit.framework.TestCase;

public class RecordsReleaseBeanTest extends TestCase {
	private RecordsReleaseBean bean;
	
	@Override
	protected void setUp() throws Exception {
		bean = new RecordsReleaseBean();
	}
	
	public void testReleaseID() {
		long testID = 0L;
		bean.setReleaseID(testID);
		assertEquals(testID, bean.getReleaseID());
		
		testID = 5L;
		bean.setReleaseID(testID);
		assertEquals(testID, bean.getReleaseID());
	}
	
	public void testPid() {
		long testID = 0L;
		bean.setPid(testID);
		assertEquals(testID, bean.getPid());
		
		testID = 5L;
		bean.setPid(testID);
		assertEquals(testID, bean.getPid());
	}
	
	public void testReleaseHospitalID() {
		String testID = "0";
		bean.setReleaseHospitalID(testID);
		assertEquals(testID, bean.getReleaseHospitalID());
		
		testID = "5";
		bean.setReleaseHospitalID(testID);
		assertEquals(testID, bean.getReleaseHospitalID());
	}
	
	public void testRecHospitalName() {
		String testName = "Test Hospital";
		bean.setRecHospitalName(testName);
		assertEquals(testName, bean.getRecHospitalName());
		
		testName = "Test Hospital 2";
		bean.setRecHospitalName(testName);
		assertEquals(testName, bean.getRecHospitalName());
	}
	
	public void testRecHospitalAddress() {
		String testAddress = "0 Test Drive";
		bean.setRecHospitalAddress(testAddress);
		assertEquals(testAddress, bean.getRecHospitalAddress());
		
		testAddress = "5 Test Drive";
		bean.setRecHospitalAddress(testAddress);
		assertEquals(testAddress, bean.getRecHospitalAddress());
	}
	
	public void testDocFirstName() {
		String testName = "Stupid";
		bean.setDocFirstName(testName);
		assertEquals(testName, bean.getDocFirstName());
		
		testName = "Cool";
		bean.setDocFirstName(testName);
		assertEquals(testName, bean.getDocFirstName());
	}
	
	public void testDocLastName() {
		String testName = "Stupid";
		bean.setDocLastName(testName);
		assertEquals(testName, bean.getDocLastName());
		
		testName = "Cool";
		bean.setDocLastName(testName);
		assertEquals(testName, bean.getDocLastName());
	}
	
	public void testDocPhone() {
		String testPhone = "000-000-0000";
		bean.setDocPhone(testPhone);
		assertEquals(testPhone, bean.getDocPhone());
		
		testPhone = "111-111-1111";
		bean.setDocPhone(testPhone);
		assertEquals(testPhone, bean.getDocPhone());
	}
	
	public void testDocEmail() {
		String testEmail = "a@a.com";
		bean.setDocEmail(testEmail);
		assertEquals(testEmail, bean.getDocEmail());
		
		testEmail = "b@b.com";
		bean.setDocEmail(testEmail);
		assertEquals(testEmail, bean.getDocEmail());
	}
	
	public void testJustification() {
		String testJustification = "Justify Test1";
		bean.setJustification(testJustification);
		assertEquals(testJustification, bean.getJustification());
		
		testJustification = "Justify Test2";
		bean.setJustification(testJustification);
		assertEquals(testJustification, bean.getJustification());
	}
	
	public void testStatus() {
		//Test pending status
		int testStatus = 0;
		String statusMsg = "Pending";
		bean.setStatus(testStatus);
		assertEquals(testStatus, bean.getStatus());
		assertEquals(statusMsg, bean.getStatusStr());
		
		//Test approved status
		testStatus = 1;
		statusMsg = "Approved";
		bean.setStatus(testStatus);
		assertEquals(testStatus, bean.getStatus());
		assertEquals(statusMsg, bean.getStatusStr());
		
		//Test denied status
		testStatus = 2;
		statusMsg = "Denied";
		bean.setStatus(testStatus);
		assertEquals(testStatus, bean.getStatus());
		assertEquals(statusMsg, bean.getStatusStr());
		
		//Test invalid status number
		testStatus = 3;
		statusMsg = "";
		bean.setStatus(testStatus);
		assertEquals(testStatus, bean.getStatus());
		assertEquals(statusMsg, bean.getStatusStr());
	}
	
	public void testDateRequested() {
		Timestamp testDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		bean.setDateRequested(testDate);
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String testDateString = df.format(testDate);
		assertEquals(testDate, bean.getDateRequested());
		assertEquals(testDateString, bean.getDateRequestedStr());
	}
}
