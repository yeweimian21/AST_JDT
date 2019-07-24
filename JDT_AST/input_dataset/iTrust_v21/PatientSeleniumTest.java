package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Test;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class PatientSeleniumTest extends iTrustSeleniumTest {
	
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	
	@Test
	public void testViewPrescriptionRecords1() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("1", "pw");
		assertTrue(driver.getTitle().equals("iTrust - Patient Home"));
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		driver.findElement(By.linkText("Prescription Records")).click();
		assertEquals("iTrust - Get My Prescription Report", driver.getTitle());
		driver.findElement(By.name("mine")).click();
		assertLogged(TransactionType.PRESCRIPTION_REPORT_VIEW, 1L, 1L, "");
		
		WebElement table = driver.findElement(By.className("fTable"));
		assertTrue(table.getText().contains("No prescriptions found"));
		assertLogged(TransactionType.PRESCRIPTION_REPORT_VIEW, 1L, 1L, "");
	}
	
	@Test
	public void testViewPrescriptionRecords2() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("2", "pw");
		assertTrue(driver.getTitle().equals("iTrust - Patient Home"));
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		driver.findElement(By.linkText("Prescription Records")).click();
		assertEquals("iTrust - Get My Prescription Report", driver.getTitle());
		driver.findElement(By.name("mine")).click();
		assertLogged(TransactionType.PRESCRIPTION_REPORT_VIEW, 2L, 2L, "");
		WebElement table = driver.findElement(By.className("fTable"));
		assertEquals("64764-1512", table.findElements(By.tagName("tr")).get(2).findElements(By.tagName("td")).get(0).getText());
		assertEquals("Prioglitazone", table.findElements(By.tagName("tr")).get(2).findElements(By.tagName("td")).get(1).getText());
		assertEquals("10/10/2006 to 10/11/2020", table.findElements(By.tagName("tr")).get(2).findElements(By.tagName("td")).get(2).getText());
		assertEquals("Kelly Doctor", table.findElements(By.tagName("tr")).get(2).findElements(By.tagName("td")).get(3).getText());
	}
	
	@Test
	public void testCodeInjection() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("2", "pw");
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		driver.get(ADDRESS + "auth/patient/myDiagnoses.jsp?icd=%3Cscript%3Ewindow.location=%22http://bit.ly/4kb77v%22%3C/script%3E"); //Gotta make ADDRESS work. Something with transaction types
		assertFalse(driver.getPageSource().contains("RickRoll'D"));
		assertTrue(driver.getTitle().equals("iTrust - My Diagnoses"));
		assertLogged(TransactionType.DIAGNOSES_LIST_VIEW, 2L, 2L, "");
	}
	
}