package edu.ncsu.csc.itrust.selenium;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ComprehensiveReportingTest extends iTrustSeleniumTest{
	//private static WebDriver driver = null;
	
	@Before
	public void setUp() throws Exception {
	    // Create a new instance of the driver
	    //driver = new HtmlUnitDriver();
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testComprehensiveAcceptanceSuccess() throws Exception {
		// HCP 9000000000 logs in
		WebDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertTrue(driver.getTitle().contains("iTrust - HCP Home"));
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		// HCP 9000000000 moves to the add new report request page
		driver.findElement(By.linkText("My Report Requests")).click();
		driver.findElement(By.linkText("Add a new Report Request")).click();
		assertTrue(driver.getPageSource().contains("Please Select a Patient"));
		
		// HCP 9000000000 requests a report on patient 2
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.cssSelector("input[value='2']")).submit();
		assertTrue(driver.getPageSource().contains("Report Request Accepted"));
		assertLogged(TransactionType.COMPREHENSIVE_REPORT_ADD, 9000000000L, 2L, "Report ID:");
	}
	
	public void testHCPChoosesInvalidPatient() throws Exception {
		// HCP 9000000000 logs in
		WebDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertTrue(driver.getTitle().contains("iTrust - HCP Home"));
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		// HCP 9000000000 moves to the add new report request page
		driver.findElement(By.linkText("My Report Requests")).click();
		driver.findElement(By.linkText("Add a new Report Request")).click();
		assertTrue(driver.getPageSource().contains("Please Select a Patient"));
		
		// HCP 9000000000 requests a report on patient 260
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("260");
		driver.findElement(By.cssSelector("input[value='260']")).submit();
        assertNotLogged(TransactionType.COMPREHENSIVE_REPORT_ADD, 9000000000L, 23L, "Report ID:");
	}
	
	public void testHCPChoosesIncorrectPatient() throws Exception {
		// HCP 9000000000 logs in
		WebDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertTrue(driver.getTitle().contains("iTrust - HCP Home"));
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
				
		// HCP 9000000000 moves to the add new report request page
		driver.findElement(By.linkText("My Report Requests")).click();
		driver.findElement(By.linkText("Add a new Report Request")).click();
		assertTrue(driver.getPageSource().contains("Please Select a Patient"));
		
		// HCP 9000000000 requests a report on patient 260
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		assertNotLogged(TransactionType.COMPREHENSIVE_REPORT_ADD, 9000000000L, 2L, "Report ID:");
	}
}