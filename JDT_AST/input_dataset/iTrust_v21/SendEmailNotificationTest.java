package edu.ncsu.csc.itrust.selenium;


import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class SendEmailNotificationTest extends iTrustSeleniumTest{

	private HtmlUnitDriver driver;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.icd9cmCodes();
		gen.ndCodes();
		gen.hospitals();
		gen.hcp1();
		gen.hcp2();
		gen.hcp3();
		gen.er4();
		gen.patient9();
			
		gen.UC32Acceptance();
		gen.clearLoginFailures();
		driver = new HtmlUnitDriver();
	}
	
	  @Test
	  public void testPrescriptionRenewalEmail() throws Exception {
		  driver = (HtmlUnitDriver)login("9900000000", "pw");
	    assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000000L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[9]/div")).click();
	    driver.findElement(By.linkText("Potential Prescription-Renewals")).click();
	    driver.findElement(By.linkText("Darryl Thompson")).click();
	    assertEquals("Send Email Form:", driver.findElement(By.cssSelector("h3")).getText());
		assertLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 99L, "");

	  }
	  
	  @Test
	  public void testOfficeVisitRemindersEmail() throws Exception {
		  driver = (HtmlUnitDriver)login("9900000000", "pw");
	    assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000000L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Office Visit Reminders")).click();
	    assertEquals("Patients Needing Visits", driver.findElement(By.cssSelector("#iTrustContent > div > h2")).getText());
	    driver.findElement(By.id("getReminders")).click();
	    assertEquals("Darryl Thompson", driver.findElement(By.linkText("Darryl Thompson")).getText());
	    driver.findElement(By.linkText("Darryl Thompson")).click();
	    assertEquals("Send Email Form:", driver.findElement(By.cssSelector("h3")).getText());
		assertLogged(TransactionType.PATIENT_REMINDERS_VIEW, 9900000000L, 0L, "");

	  }
	  
	  @Test
	  public void testSendAnEmail() throws Exception {
		  driver = (HtmlUnitDriver)login("9900000000", "pw");
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 9900000000L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div")).click();
	    driver.findElement(By.linkText("Office Visit Reminders")).click();
	    assertEquals("Patients Needing Visits", driver.findElement(By.cssSelector("#iTrustContent > div > h2")).getText());
	    driver.findElement(By.id("getReminders")).click();
	    assertEquals("Darryl Thompson", driver.findElement(By.linkText("Darryl Thompson")).getText());
	    driver.findElement(By.linkText("Darryl Thompson")).click();
	    assertEquals("Send Email Form:", driver.findElement(By.cssSelector("h3")).getText());
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("Your Email was sent:", driver.findElement(By.cssSelector("font")).getText());
		assertLogged(TransactionType.PATIENT_REMINDERS_VIEW, 9900000000L, 0L, "");

	  }
	  
	  
	  

}