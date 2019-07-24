package edu.ncsu.csc.itrust.selenium;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuditPatientTest extends iTrustSeleniumTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.patientDeactivate();
	}
	
	@Test
	public void testHCPDeactivatePatient() throws Exception {
		WebDriver driver = login("9000000000", "pw");
		
		driver.findElement(By.linkText("Audit Patients")).click();
		assertEquals(driver.getTitle(), "iTrust - Please Select a Patient");
		
		//searching for patient 2
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.name("UID_PATIENTID")).submit();
		assertEquals(driver.getTitle(), "iTrust - Audit Page (UC62)");
		
		//typing out I understand
		driver.findElement(By.name("understand")).sendKeys("I UNDERSTAND");
		driver.findElement(By.name("understand")).submit();
		
		//asserting deletion
		assertEquals("Patient Successfully Deactivated", driver.findElement(By.className("iTrustMessage")).getText());
	}
	
	public void testHCPDeactivatePatientWrongConfirmation() throws Exception {
		WebDriver driver = login("9000000000", "pw");
		
		driver.findElement(By.linkText("Audit Patients")).click();
		assertEquals(driver.getTitle(), "iTrust - Please Select a Patient");
		
		//searching for patient 2
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.name("UID_PATIENTID")).submit();
		assertEquals(driver.getTitle(), "iTrust - Audit Page (UC62)");
		
		//typing out I understand
		driver.findElement(By.name("understand")).sendKeys("iunderstand");
		driver.findElement(By.name("understand")).submit();
		
		//asserting deletion
		assertEquals("You must type \"I UNDERSTAND\" in the textbox.", driver.findElement(By.className("iTrustError")).getText());
	}
	
	public void testHCPActivatePatient() throws Exception {
		WebDriver driver = login("9000000000", "pw");
		
		driver.findElement(By.linkText("Audit Patients")).click();
		assertEquals(driver.getTitle(), "iTrust - Please Select a Patient");
		
		//searching for patient 314159
		driver.findElement(By.id("allowDeactivated")).click();
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("314159");
		driver.findElement(By.name("UID_PATIENTID")).submit();
		assertEquals(driver.getTitle(), "iTrust - Audit Page (UC62)");
		
		//typing out I understand
		driver.findElement(By.name("understand")).sendKeys("I UNDERSTAND");
		driver.findElement(By.name("understand")).submit();
		
		//asserting activation
		assertEquals("Patient Successfully Activated", driver.findElement(By.className("iTrustMessage")).getText());
	}
		
	public void testHCPActivatePatientWrongConfirmation() throws Exception {
		WebDriver driver = login("9000000000", "pw");
		
		driver.findElement(By.linkText("Audit Patients")).click();
		assertEquals(driver.getTitle(), "iTrust - Please Select a Patient");
		
		//searching for patient 314159
		driver.findElement(By.id("allowDeactivated")).click();
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("314159");
		driver.findElement(By.name("UID_PATIENTID")).submit();
		assertEquals(driver.getTitle(), "iTrust - Audit Page (UC62)");
		
		//typing out I understand
		driver.findElement(By.name("understand")).sendKeys("iunderstand");
		driver.findElement(By.name("understand")).submit();
		
		//asserting activation
		assertEquals("You must type \"I UNDERSTAND\" in the textbox.", driver.findElement(By.className("iTrustError")).getText());
	}
}