package edu.ncsu.csc.itrust.selenium;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Use Case 34
 */
public class TelemonitoringUseCaseTest extends iTrustSeleniumTest {
	
	/**
	 * setUp
	 */
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	/**
	 * testAddPatientsToMonitor
	 * @throws Exception
	 */
	public void testAddPatientsToMonitor() throws Exception {		
		// login HCP
		WebDriver driver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Click Edit Patient List
		driver.findElement(By.xpath("//a[text()='Edit Patient List']")).click();
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		
		// Allow Blood Pressure, Weight, and Pedometer
		driver.findElement(By.name("bloodPressure")).click();
	    driver.findElement(By.name("weight")).click();
	    driver.findElement(By.name("pedometer")).click();
	    
		assertEquals("Add Andy Programmer", driver.findElement(By.name("fSubmit")).getAttribute(VALUE));
		driver.findElement(By.name("fSubmit")).click();
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Patient Andy Programmer Added"));

		assertLogged(TransactionType.PATIENT_LIST_ADD, 9000000000L, 2L, "");
	}

	/**
	 * testRemovePatientsToMonitor
	 * @throws Exception
	 */
	public void testRemovePatientsToMonitor() throws Exception {
		//Add patient 1 to HCP 9000000000's monitoring list
		gen.remoteMonitoring2();
		// login HCP
		HtmlUnitDriver driver = (HtmlUnitDriver) login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Click Edit Patient List
		driver.findElement(By.xpath("//a[text()='Edit Patient List']")).click();
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		driver.findElement(By.xpath("//input[@value='1']")).submit();

		assertEquals("Remove Random Person", driver.findElement(By.name("fSubmit")).getAttribute(VALUE));
		driver.findElement(By.name("fSubmit")).click();
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Patient Random Person Removed"));

		assertLogged(TransactionType.PATIENT_LIST_REMOVE, 9000000000L, 1L, "");
	}
	
	/**
	 * testReportPatientStatus
	 * @throws Exception
	 */
	public void testReportPatientStatus() throws Exception {
		//Add patient 1 to HCP 9000000000's monitoring list
		gen.remoteMonitoring2();
		// login Patient
		WebDriver driver = login("1", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		//Click Report Status
		driver.findElement(By.xpath("//a[text()='Report Telemedicine Status']")).click();

		driver.findElement(By.name("systolicBloodPressure")).sendKeys("100");
		driver.findElement(By.name("diastolicBloodPressure")).sendKeys("75");
		driver.findElement(By.name("glucoseLevel")).sendKeys("120");
		driver.findElement(By.name("action")).submit();

		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Information Successfully Added"));
		assertLogged(TransactionType.TELEMEDICINE_DATA_REPORT, 1L, 1L, "");
	}
	
	/**
	 * testReportPatientWeightAndPedometer
	 * @throws Exception
	 */
	public void testReportPatientWeightAndPedometer () throws Exception {
		//Add patient 1 to HCP 9000000000's monitoring list
		gen.remoteMonitoring2();
		
		// login Patient
		WebDriver driver = login("1", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
				
		//Click Report Status
		driver.findElement(By.xpath("//a[text()='Report Telemedicine Status']")).click();

		driver.findElement(By.name("weight")).sendKeys("174");
		driver.findElement(By.name("pedometerReading")).sendKeys("8238");
		driver.findElement(By.name("action")).submit();

		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Information Successfully Added"));
		assertLogged(TransactionType.TELEMEDICINE_DATA_REPORT, 1L, 1L, "");

	}
	
	/**
	 * testViewMonitoringList
	 * @throws Exception
	 */
	public void testViewMonitoringList() throws Exception {
		//Sets up all preconditions listed in acceptance test
		gen.remoteMonitoring3();
		// login HCP
		WebDriver driver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.xpath("//a[text()='Monitor Patients']")).click();

		//Verify all data
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Patient Physiologic Statistics"));
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        
        assertEquals("Random Person (MID 1)", driver.findElement(By.xpath("//table/tbody/tr[3]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//table/tbody/tr[3]/td[2]")).getText().contains(dateFormat.format(date)));
		assertTrue(driver.findElement(By.xpath("//table/tbody/tr[3]/td[2]")).getText().contains("08:00:00"));
		assertEquals("160", driver.findElement(By.xpath("//table/tbody/tr[3]/td[3]")).getText());
		assertEquals("110", driver.findElement(By.xpath("//table/tbody/tr[3]/td[4]")).getText());
		assertEquals("60", driver.findElement(By.xpath("//table/tbody/tr[3]/td[5]")).getText());
		assertEquals("Andy Programmer", driver.findElement(By.xpath("//table/tbody/tr[3]/td[6]")).getText());		
		//Highlighting for abnormal data
		assertEquals("#ffff00", driver.findElement(By.xpath("//table/tbody/tr[3]")).getAttribute("bgcolor"));
		
		assertEquals("Random Person (MID 1)", driver.findElement(By.xpath("//table/tbody/tr[4]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//table/tbody/tr[4]/td[2]")).getText().contains(dateFormat.format(date)));
		assertTrue(driver.findElement(By.xpath("//table/tbody/tr[4]/td[2]")).getText().contains("07:15:00"));
		assertEquals("100", driver.findElement(By.xpath("//table/tbody/tr[4]/td[3]")).getText());
		assertEquals("70", driver.findElement(By.xpath("//table/tbody/tr[4]/td[4]")).getText());
		assertEquals("90", driver.findElement(By.xpath("//table/tbody/tr[4]/td[5]")).getText());
		assertEquals("FirstUAP LastUAP", driver.findElement(By.xpath("//table/tbody/tr[4]/td[6]")).getText());		
		
		assertEquals("Random Person (MID 1)", driver.findElement(By.xpath("//table/tbody/tr[5]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//table/tbody/tr[5]/td[2]")).getText().contains(dateFormat.format(date)));
		assertTrue(driver.findElement(By.xpath("//table/tbody/tr[5]/td[2]")).getText().contains("05:30:00"));
		assertEquals("90", driver.findElement(By.xpath("//table/tbody/tr[5]/td[3]")).getText());
		assertEquals("60", driver.findElement(By.xpath("//table/tbody/tr[5]/td[4]")).getText());
		assertEquals("80", driver.findElement(By.xpath("//table/tbody/tr[5]/td[5]")).getText());
		assertEquals("Random Person", driver.findElement(By.xpath("//table/tbody/tr[5]/td[6]")).getText());		
		
		assertEquals("Baby Programmer (MID 5)", driver.findElement(By.xpath("//table/tbody/tr[6]/td[1]")).getText());
		assertEquals("No Information Provided", driver.findElement(By.xpath("//table/tbody/tr[6]/td[2]")).getText());
		assertEquals("", driver.findElement(By.xpath("//table/tbody/tr[6]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//table/tbody/tr[6]/td[4]")).getText());
		assertEquals("", driver.findElement(By.xpath("//table/tbody/tr[6]/td[5]")).getText());		
		//Highlighting for abnormal data
		assertEquals("#ff6666", driver.findElement(By.xpath("//table/tbody/tr[6]")).getAttribute("bgcolor"));
		
		assertLogged(TransactionType.TELEMEDICINE_DATA_VIEW, 9000000000L, 0L, "");
	}
	
	/**
	 * testViewWeightAndPedometerReports
	 * @throws Exception
	 */
	public void testViewWeightAndPedometerReports () throws Exception {
		//Sets up all preconditions listed in acceptance test
		gen.remoteMonitoring5();
		// login HCP
		WebDriver driver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.xpath("//a[text()='Monitor Patients']")).click();
		//Verify all data
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Patient External Statistics"));		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        
        assertEquals("Random Person (MID 1)", driver.findElement(By.xpath("//table[2]/tbody/tr[3]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//table[2]/tbody/tr[3]/td[2]")).getText().contains(dateFormat.format(date)));
		assertTrue(driver.findElement(By.xpath("//table[2]/tbody/tr[3]/td[2]")).getText().contains("07:17:00"));
		assertEquals("186.5", driver.findElement(By.xpath("//table/tbody/tr[3]/td[4]")).getText());
		assertEquals("", driver.findElement(By.xpath("//table/tbody/tr[3]/td[5]")).getText());
		assertEquals("Random Person", driver.findElement(By.xpath("//table/tbody/tr[3]/td[6]")).getText());		
	
		//Highlighting for abnormal data
		assertEquals("#ffff00", driver.findElement(By.xpath("//table[2]/tbody/tr[3]")).getAttribute("bgcolor"));			
		assertLogged(TransactionType.TELEMEDICINE_DATA_VIEW, 9000000000L, 0L, "");		
	}
	
	/**
	 * testUAPReportPatientStatus
	 * @throws Exception
	 */
	public void testUAPReportPatientStatus() throws Exception{
		gen.remoteMonitoringUAP();
		WebDriver driver = login("8000000009", "uappass1");
		assertEquals("iTrust - UAP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
		
		driver.findElement(By.xpath("//a[text()='Report Telemedicine Status']")).click();
		assertEquals("iTrust - View Monitored Patients", driver.getTitle());
		
		driver.findElement(By.linkText("Andy Programmer")).click();
		assertEquals("iTrust - Report Status", driver.getTitle());
		
		driver.findElement(By.name("systolicBloodPressure")).sendKeys("100");
		driver.findElement(By.name("diastolicBloodPressure")).sendKeys("75");
		driver.findElement(By.name("glucoseLevel")).sendKeys("120");

		driver.findElement(By.name("action")).submit();

		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Information Successfully Added"));
		assertLogged(TransactionType.TELEMEDICINE_DATA_REPORT, 8000000009L, 2L, "");
	}
	
	/**
	 * testRepresentativeReportPatientStatus
	 * @throws Exception
	 */
	public void testRepresentativeReportPatientStatus() throws Exception {
		gen.remoteMonitoring4();
		HtmlUnitDriver driver = (HtmlUnitDriver)login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		driver.findElement(By.xpath("//a[text()='Report Telemedicine Status']")).click();
		assertEquals("iTrust - Report Status", driver.getTitle());
		
		//have to use JS
		driver.setJavascriptEnabled(true);
		driver.findElement(By.linkText("Random Person")).click();

		driver.findElement(By.name("glucoseLevel")).sendKeys("120");
		driver.findElement(By.name("action")).click();

		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Information Successfully Added"));
		assertLogged(TransactionType.TELEMEDICINE_DATA_REPORT, 2L, 1L, "");
	}
	
	/**
	 * testRepresentativeReportWeight
	 * @throws Exception
	 */
	public void testRepresentativeReportWeight() throws Exception {
		//Add patient 1 to HCP 9000000000's monitoring list
		//Also add three reports
		gen.remoteMonitoring2();
		// login Patient
		HtmlUnitDriver driver = (HtmlUnitDriver) login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		//Click Report Status
		driver.findElement(By.xpath("//a[text()='Report Telemedicine Status']")).click();
		assertEquals("iTrust - Report Status", driver.getTitle());
		
		//have to use JS
		driver.setJavascriptEnabled(true);
		driver.findElement(By.linkText("Random Person")).click();

		driver.findElement(By.name("weight")).sendKeys("174");
		driver.findElement(By.name("action")).click();

		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Information Successfully Added"));
		assertLogged(TransactionType.TELEMEDICINE_DATA_REPORT, 2L, 1L, "");
		
	}
	
	/**
	 * testUAPReportPatientPedometerReading
	 * @throws Exception
	 */
	public void testUAPReportPatientPedometerReading () throws Exception {
		//Add patient 1 to HCP 9000000000's monitoring list
		//Also add three reports
		gen.remoteMonitoring2();
		gen.remoteMonitoringUAP();
		// login Patient
		HtmlUnitDriver driver = (HtmlUnitDriver) login("8000000009", "uappass1");
		assertEquals("iTrust - UAP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
		
		//Click Report Status
		driver.findElement(By.xpath("//a[text()='Report Telemedicine Status']")).click();
				
		//have to use JS
		driver.setJavascriptEnabled(true);
		driver.findElement(By.linkText("Andy Programmer")).click();
		
		driver.findElement(By.name("pedometerReading")).sendKeys("9163");
		driver.findElement(By.name("action")).click();

		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Information Successfully Added"));
		assertLogged(TransactionType.TELEMEDICINE_DATA_REPORT, 8000000009L, 2L, "");
		
	}
	
	/**
	 * testUAPAddPatientToMonitorTest
	 * @throws Exception
	 */
	public void testUAPAddPatientToMonitorTest() throws Exception{
		HtmlUnitDriver driver = (HtmlUnitDriver) login("8000000009", "uappass1");
		assertEquals("iTrust - UAP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
		
		driver.findElement(By.xpath("//a[text()='Edit Patient List']")).click();
		
		//search patient with mid 2
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		
		assertEquals("Add Andy Programmer", driver.findElement(By.cssSelector("input[type=\"submit\"]")).getAttribute(VALUE));
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Patient Andy Programmer Added"));
		assertLogged(TransactionType.PATIENT_LIST_ADD, 8000000009L, 2L, "");
	}

	/**
	 * testUAPAddHCPMonitor
	 * @throws Exception
	 */
	public void testUAPAddHCPMonitor() throws Exception{
		gen.remoteMonitoring8();
		HtmlUnitDriver driver = (HtmlUnitDriver) login("8000000009", "uappass1");
		assertEquals("iTrust - UAP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");

		driver.findElement(By.xpath("//a[text()='Edit Patient List']")).click();
		//search patient with mid 2
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		assertEquals("Add Andy Programmer", driver.findElement(By.cssSelector("input[type=\"submit\"]")).getAttribute(VALUE));
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Patient Andy Programmer Added"));
		assertLogged(TransactionType.PATIENT_LIST_ADD, 8000000009L, 2L, "");
		
		//go to reporting page
		driver.findElement(By.xpath("//a[text()='Report Telemedicine Status']")).click();	
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Andy Programmer"));
		assertEquals("iTrust - Report Status", driver.getTitle());
		driver.findElement(By.name("systolicBloodPressure")).sendKeys("110");
		driver.findElement(By.name("diastolicBloodPressure")).sendKeys("85");
		driver.findElement(By.name("action")).click();

		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Information Successfully Added"));
		assertLogged(TransactionType.TELEMEDICINE_DATA_REPORT, 8000000009L, 2L, "");

		//logout
		driver.findElement(By.xpath("//a[text()='Logout']")).click();	
		assertLogged(TransactionType.LOGOUT, 8000000009L, 8000000009L, "");
		
		//log back in
		HtmlUnitDriver HCPdriver = (HtmlUnitDriver) login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", HCPdriver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		HCPdriver.findElement(By.xpath("//a[text()='Monitor Patients']")).click();

		assertEquals("Andy Programmer (MID 2)", HCPdriver.findElement(By.xpath("//table/tbody/tr[3]/td[1]")).getText());
		assertEquals("110", HCPdriver.findElement(By.xpath("//table/tbody/tr[3]/td[3]")).getText());
		assertEquals("85", HCPdriver.findElement(By.xpath("//table/tbody/tr[3]/td[4]")).getText());
		assertEquals("", HCPdriver.findElement(By.xpath("//table/tbody/tr[3]/td[5]")).getText());
		
  		assertLogged(TransactionType.TELEMEDICINE_DATA_VIEW, 9000000000L, 0L, "");
	}
	
	/**
	 * testUAPAddReportDeleteCannotReport
	 * @throws Exception
	 */
	public void testUAPAddReportDeleteCannotReport() throws Exception{
		gen.remoteMonitoring8();
		//log in to iTrust
		HtmlUnitDriver driver = (HtmlUnitDriver) login("8000000009", "uappass1");
		assertEquals("iTrust - UAP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
		
		//add Patient 2 to reporting list
		driver.findElement(By.xpath("//a[text()='Edit Patient List']")).click();	

		//search patient with mid 2
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		assertEquals("Add Andy Programmer", driver.findElement(By.cssSelector("input[type=\"submit\"]")).getAttribute(VALUE));
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Patient Andy Programmer Added"));
		assertLogged(TransactionType.PATIENT_LIST_ADD, 8000000009L, 2L, "");
		
		//go to reporting page
		driver.findElement(By.xpath("//a[text()='Report Telemedicine Status']")).click();	
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Andy Programmer"));
		assertEquals("iTrust - Report Status", driver.getTitle());
		driver.findElement(By.name("systolicBloodPressure")).sendKeys("100");
		driver.findElement(By.name("diastolicBloodPressure")).sendKeys("75");
		driver.findElement(By.name("action")).click();
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Information Successfully Added"));
		assertLogged(TransactionType.TELEMEDICINE_DATA_REPORT, 8000000009L, 2L, "");

		//remove Patient 2 from reporting list
		driver.findElement(By.xpath("//a[text()='Edit Patient List']")).click();	
		assertEquals("Remove Andy Programmer", driver.findElement(By.cssSelector("input[type=\"submit\"]")).getAttribute(VALUE));
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Patient Andy Programmer Removed"));
		assertLogged(TransactionType.PATIENT_LIST_REMOVE, 8000000009L, 2L, "");
	}
	
	// Test for UC34
	/**
	 * testWeightHighlighting
	 * @throws Exception
	 */
	public void testWeightHighlighting() throws Exception{
		gen.remoteMonitoring6();
		HtmlUnitDriver driver = (HtmlUnitDriver) login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.xpath("//a[text()='Monitor Patients']")).click();	

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        
        assertEquals("Random Person (MID 1)", driver.findElement(By.xpath("//table[2]/tbody/tr[3]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//table[2]/tbody/tr[3]/td[2]")).getText().contains(dateFormat.format(date)));
		assertTrue(driver.findElement(By.xpath("//table[2]/tbody/tr[3]/td[2]")).getText().contains("07:17:00"));
		assertEquals("70.0", driver.findElement(By.xpath("//table/tbody/tr[3]/td[3]")).getText());
		assertEquals("192.5", driver.findElement(By.xpath("//table/tbody/tr[3]/td[4]")).getText());
		assertEquals("", driver.findElement(By.xpath("//table/tbody/tr[3]/td[5]")).getText());
		assertEquals("Random Person", driver.findElement(By.xpath("//table/tbody/tr[3]/td[6]")).getText());		

   		//Highlighting for abnormal data
		assertEquals("#ffff00", driver.findElement(By.xpath("//table[2]/tbody/tr[3]")).getAttribute("bgcolor"));			
		assertLogged(TransactionType.TELEMEDICINE_DATA_VIEW, 9000000000L, 0L, "");
	}
	
	// Test for UC34
	/**
	 * testDetailedExternalData
	 * @throws Exception
	 */
	public void testDetailedExternalData() throws Exception {
		gen.remoteMonitoring6();
		HtmlUnitDriver driver = (HtmlUnitDriver) login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.xpath("//a[text()='Monitor Patients']")).click();	
		driver.findElement(By.xpath("//a[text()='Random Person (MID 1)']")).click();	
		
		int ONE_DAY = 24 * 60 * 60 * 1000;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		
		java.util.Date date = new java.util.Date();
		date.setTime(date.getTime() - 3*(long)ONE_DAY);
		Date yesterday = new Date();
		yesterday.setTime(yesterday.getTime() - ONE_DAY);
		Date twoDaysAgo = new Date(yesterday.getTime() - ONE_DAY);
		
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys(sdf.format(date));
		assertEquals(sdf.format(date), driver.findElement(By.name("startDate")).getAttribute(VALUE));
		driver.findElement(By.name("submit")).submit();
				
		// First entry:
		assertEquals(sdf2.format(new Date()) + " 07:17:00.0", driver.findElement(By.xpath("//table[4]/tbody/tr[3]/td[1]")).getText());
		assertEquals("70.0", driver.findElement(By.xpath("//table[4]/tbody/tr[3]/td[2]")).getText());
		assertEquals("192.5", driver.findElement(By.xpath("//table[4]/tbody/tr[3]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//table[4]/tbody/tr[3]/td[4]")).getText());

		// Second entry:
		assertEquals(sdf2.format(yesterday) + " 07:48:00.0", driver.findElement(By.xpath("//table[4]/tbody/tr[4]/td[1]")).getText());
		assertEquals("70.0", driver.findElement(By.xpath("//table[4]/tbody/tr[4]/td[2]")).getText());
		assertEquals("", driver.findElement(By.xpath("//table[4]/tbody/tr[4]/td[3]")).getText());
		assertEquals("8153", driver.findElement(By.xpath("//table[4]/tbody/tr[4]/td[4]")).getText());
		
		// Third entry:
		assertEquals(sdf2.format(twoDaysAgo) + " 08:19:00.0", driver.findElement(By.xpath("//table[4]/tbody/tr[5]/td[1]")).getText());
		assertEquals("70.0", driver.findElement(By.xpath("//table[4]/tbody/tr[5]/td[2]")).getText());
		assertEquals("180.0", driver.findElement(By.xpath("//table[4]/tbody/tr[5]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//table[4]/tbody/tr[5]/td[4]")).getText());

		assertLogged(TransactionType.TELEMEDICINE_DATA_VIEW, 9000000000L, 0L, "");
	}
	
	// Test for UC34
	/**
	 * testReportPatientHeight
	 * @throws Exception
	 */
	public void testReportPatientHeight() throws Exception {
		gen.remoteMonitoring7();
		HtmlUnitDriver driver = (HtmlUnitDriver) login("1", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		driver.findElement(By.xpath("//a[text()='Report Telemedicine Status']")).click();
		driver.findElement(By.name("height")).sendKeys("73.2");
		driver.findElement(By.name("action")).click();

		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Information Successfully Added"));
		assertLogged(TransactionType.TELEMEDICINE_DATA_REPORT, 1L, 1L, "");
		
		driver.findElement(By.xpath("//a[text()='Report Telemedicine Status']")).click();

		driver.findElement(By.name("height")).sendKeys("73.2");
		driver.findElement(By.name("action")).click();
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Invalid entry: Patient height entries for today cannot exceed 1."));
	}
}