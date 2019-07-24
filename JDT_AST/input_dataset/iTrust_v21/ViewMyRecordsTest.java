package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ViewMyRecordsTest extends iTrustSeleniumTest {
	
	private HtmlUnitDriver driver;
	
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.icd9cmCodes();
		gen.ndCodes();
		gen.uap1();
		gen.patient2();
		gen.patient1();
		gen.patient4();
		gen.hcp0();
		gen.clearLoginFailures();
		gen.hcp3();
	}
	
	/*
	 * Authenticate Patient
	 * MID: 2
	 * Password: pw
	 * Choose option View My Records
	 */
	/**
	 * testViewRecords3
	 * @throws Exception
	 */
	public void testViewRecords3() throws Exception {
		driver = (HtmlUnitDriver)login("2", "pw");
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View My Records")).click();
	    assertTrue(driver.getPageSource().contains("210.0lbs"));
	    assertTrue(driver.getPageSource().contains("500 mg/dL"));
	    driver.findElement(By.linkText("Jun 10, 2007")).click();
	    assertTrue(driver.getPageSource().contains("Diabetes with ketoacidosis"));
		assertTrue(driver.getPageSource().contains("Prioglitazone"));
		assertTrue(driver.getPageSource().contains("Tetracycline"));
		assertTrue(driver.getPageSource().contains("Notes:"));
	    assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
	}
	
	/*
	 * Authenticate Patient
	 * MID: 4
	 * Password: pw
	 * Choose option View My Records
	 */
	/**
	 * testViewRecords4
	 * @throws Exception
	 */
	public void testViewRecords4() throws Exception {
		driver = (HtmlUnitDriver)login("4", "pw");
		assertLogged(TransactionType.HOME_VIEW, 4L, 0L, "");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View My Records")).click();
		assertFalse(driver.getPageSource().contains("Exception"));
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 4L, 4L, "");
	}
	
	/*
	 * Authenticate Patient
	 * MID: 2
	 * Password: pw
	 * Choose option View My Records
	 * Choose to view records for mid 1, the person he represents.
	 */
	/**
	 * testViewRecords5
	 * @throws Exception
	 */
	public void testViewRecords5() throws Exception {
		driver = (HtmlUnitDriver)login("2", "pw");
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View My Records")).click();
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
		
		assertTrue(driver.getPageSource().contains("Random Person"));
		driver.findElement(By.linkText("Random Person")).click();
		
		// check to make sure you are viewing patient 1's records
		assertTrue(driver.getPageSource().contains("You are currently viewing your representee's records"));
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 1L, "");
	}
}