package edu.ncsu.csc.itrust.selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class ViewEmergencyReportTest extends iTrustSeleniumTest {
	private HtmlUnitDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

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
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testGenerateReport() throws Exception {
		// This logs us into iTrust and returns the HtmlUnitDriver for use in
		// this case
		driver = (HtmlUnitDriver)login("9000000006", "pw");
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Emergency Patient Report")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("99");
	    driver.findElement(By.xpath("//input[@value='99']")).submit();
	    List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + "Darryl Thompson" + "')]"));
	    assertTrue("Text not found!", list.size() > 0);
	    //logout
	    WebElement link = driver.findElements(By.tagName("li")).get(1);
		link.findElement(By.tagName("a")).click();
		assertTrue(driver.getPageSource().contains("patient-centered"));
	    
	    driver = (HtmlUnitDriver)login("9900000000", "pw");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
	    driver.findElement(By.linkText("Email History")).click();
	    String text = driver.findElement(By.cssSelector("table.fTable")).getText();
	    assertTrue(driver.getTitle().equals("iTrust - Email History"));
	    assertTrue("Text not found!", text.contains("tarehart@iTrust.org"));
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}