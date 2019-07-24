package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.unit.DBBuilder;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;


/**
 * Selenium test conversion for HttpUnit ImmunizationReportTest
 */
@SuppressWarnings("unused")
public class ImmunizationReportTest extends iTrustSeleniumTest {

	private HtmlUnitDriver driver;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();	
		driver = new HtmlUnitDriver();
		driver.get("http://localhost:8080/iTrust/");
	}

	/*
	 * Test Kindergarten-age patient viewing immunization report
	 */
	public void testImmunizationReportKindergartenPatient() throws Exception {
		
		driver.findElement(By.id("j_username")).sendKeys("300");
		driver.findElement(By.id("j_password")).sendKeys("pw");
	
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/patient/viewMyImmunizations.jsp']")).click();
		assertEquals("iTrust - View My Immunization Records", driver.getTitle());
			
	    WebElement table_element = driver.findElement(By.id("immunizationReport"));
        List<WebElement> itemList = table_element.findElements(By.xpath("id('immunizationReport')/tbody/tr"));
	
        assertEquals(13, itemList.size());
        assertTrue(itemList.get(1).getText().contains("CPT Code"));
        assertTrue(itemList.get(1).getText().contains("Description"));
        assertTrue(itemList.get(1).getText().contains("Date Received"));
        assertTrue(itemList.get(1).getText().contains("HCP"));
        assertTrue(itemList.get(11).getText().contains("CPT Code"));
        assertTrue(itemList.get(11).getText().contains("Description"));
     
        assertTrue(itemList.get(2).getText().contains("90696"));
        assertTrue(itemList.get(2).getText().contains("Diphtheria, Tetanus, Pertussis"));
        assertTrue(itemList.get(2).getText().contains("Dec 31, 2012"));
        assertTrue(itemList.get(2).getText().contains("Kelly Doctor"));
   
        assertTrue(itemList.get(3).getText().contains("90712"));
        assertTrue(itemList.get(3).getText().contains("Poliovirus"));
        assertTrue(itemList.get(3).getText().contains("Jan 1, 2013"));
        assertTrue(itemList.get(3).getText().contains("Kelly Doctor"));
        
        assertTrue(itemList.get(4).getText().contains("90707"));
        assertTrue(itemList.get(4).getText().contains("Measles, Mumps, Rubella"));
        assertTrue(itemList.get(4).getText().contains("Jan 1, 2013"));
        assertTrue(itemList.get(4).getText().contains("Kelly Doctor"));
        
        assertTrue(itemList.get(5).getText().contains("90371"));
        assertTrue(itemList.get(5).getText().contains("Hepatitis B"));
        assertTrue(itemList.get(5).getText().contains("Jan 2, 2013"));
        assertTrue(itemList.get(5).getText().contains("Kelly Doctor"));
        
        assertTrue(itemList.get(6).getText().contains("90396"));
        assertTrue(itemList.get(6).getText().contains("Varicella"));
        assertTrue(itemList.get(6).getText().contains("Jan 3, 2013"));
        assertTrue(itemList.get(6).getText().contains("Kelly Doctor"));
   
        assertTrue(itemList.get(7).getText().contains("90681"));
        assertTrue(itemList.get(7).getText().contains("Rotavirus"));
        assertTrue(itemList.get(7).getText().contains("Jan 3, 2013"));
        assertTrue(itemList.get(7).getText().contains("Kelly Doctor"));
        
        assertTrue(itemList.get(8).getText().contains("90633"));
        assertTrue(itemList.get(8).getText().contains("Hepatitis A"));
        assertTrue(itemList.get(8).getText().contains("Jan 3, 2013"));
        assertTrue(itemList.get(8).getText().contains("Kelly Doctor"));
       
        assertTrue(itemList.get(9).getText().contains("90645"));
        assertTrue(itemList.get(9).getText().contains("Haemophilus influenzae"));
        assertTrue(itemList.get(9).getText().contains("Jan 4, 2013"));
        assertTrue(itemList.get(9).getText().contains("Kelly Doctor"));
        
        assertTrue(itemList.get(12).getText().contains("No further immunizations needed"));
        assertLogged(TransactionType.IMMUNIZATION_REPORT_PATIENT_VIEW, 300L, 0L, "");
        
	}
	
	/*
	 * Test data for sixth-grade patient viewing immunization report
	 */
	public void testImmunizationReportSixthGradePatient() throws Exception {

		driver.findElement(By.id("j_username")).sendKeys("301");
		driver.findElement(By.id("j_password")).sendKeys("pw");
	
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/patient/viewMyImmunizations.jsp']")).click();
		assertEquals("iTrust - View My Immunization Records", driver.getTitle());
			
	    WebElement table_element = driver.findElement(By.id("immunizationReport"));
        List<WebElement> itemList = table_element.findElements(By.xpath("id('immunizationReport')/tbody/tr"));
	
        assertEquals(13, itemList.size());
        assertTrue(itemList.get(1).getText().contains("CPT Code"));
        assertTrue(itemList.get(1).getText().contains("Description"));
        assertTrue(itemList.get(1).getText().contains("Date Received"));
        assertTrue(itemList.get(1).getText().contains("HCP"));
        assertTrue(itemList.get(11).getText().contains("CPT Code"));
        assertTrue(itemList.get(11).getText().contains("Description"));
        
        //Check the eight immunizations received by Natalie Portman
		assertTrue(itemList.get(2).getText().contains("90696"));
		assertTrue(itemList.get(3).getText().contains("90712"));
		assertTrue(itemList.get(4).getText().contains("90707"));
		assertTrue(itemList.get(5).getText().contains("90371"));
		assertTrue(itemList.get(6).getText().contains("90681"));
		assertTrue(itemList.get(7).getText().contains("90633"));
		assertTrue(itemList.get(8).getText().contains("90396"));
		assertTrue(itemList.get(9).getText().contains("90645"));
        
        //Check logging
		assertLogged(TransactionType.IMMUNIZATION_REPORT_PATIENT_VIEW, 301L, 0L, "");
		
	}
	
	/*
	 * Test data for an adult patient viewing his own immunization report
	 */
	public void testImmunizationReportAdultPatient() throws Exception {
	
		driver.findElement(By.id("j_username")).sendKeys("302");
		driver.findElement(By.id("j_password")).sendKeys("pw");
	
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/patient/viewMyImmunizations.jsp']")).click();
		assertEquals("iTrust - View My Immunization Records", driver.getTitle());
			
	    WebElement table_element = driver.findElement(By.id("immunizationReport"));
        List<WebElement> itemList = table_element.findElements(By.xpath("id('immunizationReport')/tbody/tr"));
	
        assertEquals(8, itemList.size());
   
        //Check the three immunizations received by Will Smith
		assertTrue(itemList.get(2).getText().contains("90696"));
		assertTrue(itemList.get(3).getText().contains("90707"));
		assertTrue(itemList.get(4).getText().contains("90371"));
		
		//Check that no immunizations are required
		assertTrue(itemList.get(7).getText().contains("No further immunizations needed"));
		
		//Check logging
		assertLogged(TransactionType.IMMUNIZATION_REPORT_PATIENT_VIEW, 302L, 0L, "");
		
	}

	/*
	 * Test data for patient with immunizations needed
	 */
	public void testImmunizationReportNeedImmunziations() throws Exception {
	
		driver.findElement(By.id("j_username")).sendKeys("303");
		driver.findElement(By.id("j_password")).sendKeys("pw");
	
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/patient/viewMyImmunizations.jsp']")).click();
		assertEquals("iTrust - View My Immunization Records", driver.getTitle());
			
	    WebElement table_element = driver.findElement(By.id("immunizationReport"));
        List<WebElement> itemList = table_element.findElements(By.xpath("id('immunizationReport')/tbody/tr"));
        assertEquals(10, itemList.size());
     
        assertTrue(itemList.get(2).getText().contains("90681"));
		assertTrue(itemList.get(3).getText().contains("90371"));	
		assertTrue(itemList.get(6).getText().contains("90696"));
		assertTrue(itemList.get(7).getText().contains("90712"));
		assertTrue(itemList.get(8).getText().contains("90707"));
		assertTrue(itemList.get(9).getText().contains("90396"));
		
		//Check logging
		assertLogged(TransactionType.IMMUNIZATION_REPORT_PATIENT_VIEW, 303L, 0L, "");
	}
	
	
	/*
	 * Test immunization of patient with previous Chicken Pox diagnosis
	 */
	public void testImmunizationReportPriorDiagnosis() throws Exception {
	
		driver.findElement(By.id("j_username")).sendKeys("305");
		driver.findElement(By.id("j_password")).sendKeys("pw");
	
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/patient/viewMyImmunizations.jsp']")).click();
		assertEquals("iTrust - View My Immunization Records", driver.getTitle());
			
	    WebElement table_element = driver.findElement(By.id("immunizationReport"));
        List<WebElement> itemList = table_element.findElements(By.xpath("id('immunizationReport')/tbody/tr"));
	
        assertEquals(11, itemList.size());
        
        assertTrue(itemList.get(2).getText().contains("90681"));
		assertTrue(itemList.get(3).getText().contains("90371"));
		assertTrue(itemList.get(4).getText().contains("90696"));
		assertTrue(itemList.get(5).getText().contains("90712"));
		assertTrue(itemList.get(6).getText().contains("90707"));
		assertTrue(itemList.get(7).getText().contains("90645"));
		
		//Check that no immunizations are logged as required.
		assertTrue(itemList.get(10).getText().contains("No further immunizations needed"));
		
		//Check logging
		assertLogged(TransactionType.IMMUNIZATION_REPORT_PATIENT_VIEW, 305L, 0L, "");
		
	}
	
	/*
	 * Test for patient over maximum vaccine age
	 */
	public void testImmunizationReportOverMaxAge() throws Exception {

		driver.findElement(By.id("j_username")).sendKeys("308");
		driver.findElement(By.id("j_password")).sendKeys("pw");
	
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/patient/viewMyImmunizations.jsp']")).click();
		assertEquals("iTrust - View My Immunization Records", driver.getTitle());
			
	    WebElement table_element = driver.findElement(By.id("immunizationReport"));
        List<WebElement> itemList = table_element.findElements(By.xpath("id('immunizationReport')/tbody/tr"));
	
        assertEquals(7, itemList.size());
        
        //Check that no immunizations logged as received
        assertTrue(itemList.get(2).getText().contains("No immunizations received."));
		assertTrue(itemList.get(5).getText().contains("90696"));
		assertTrue(itemList.get(6).getText().contains("90707"));
		
		//Check logging
		assertLogged(TransactionType.IMMUNIZATION_REPORT_PATIENT_VIEW, 308L, 0L, "");
	}
	
	/*
	 * Test HCP viewing patient immunization records
	 */
	public void testImmunizationReportHCPView() throws Exception {
		
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/hcp-uap/viewImmunizations.jsp']")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("308");
		driver.findElement(By.xpath("//input[@value='308']")).submit();

		WebElement table_element = driver.findElement(By.id("immunizationReport"));
	    List<WebElement> itemList = table_element.findElements(By.xpath("id('immunizationReport')/tbody/tr"));
	    assertEquals(7, itemList.size());
        
        //Check that no immunizations logged as received
        assertTrue(itemList.get(2).getText().contains("No immunizations received."));
		assertTrue(itemList.get(5).getText().contains("90696"));
		assertTrue(itemList.get(6).getText().contains("90707"));
		
		//Check logging
		assertLogged(TransactionType.IMMUNIZATION_REPORT_HCP_VIEW, 9000000000L, 308L, "");  	
	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
	
	
}