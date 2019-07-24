package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.*;


import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ReportAdverseImmuEventTest extends iTrustSeleniumTest {
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.hcp0();
		gen.cptCodes();
		gen.ovImmune();
		gen.patient1();
	    driver = new HtmlUnitDriver();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }


  /**
   * Test adverse event reporting. 
   * @throws Exception
   */
@Test
  public void testReport() throws Exception {
	
	driver.get("http://localhost:8080/iTrust/");
	driver.findElement(By.id("j_username")).sendKeys("1");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");

	
    //driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
    //driver.findElement(By.linkText("Patient 1")).click();
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
    driver.findElement(By.linkText("View My Records")).click();
    //I have no idea where this link actually is on the page.
    driver.findElement(By.linkText("Report")).click();
    
    driver.findElement(By.name("Comment")).clear();
    driver.findElement(By.name("Comment")).sendKeys("I've been experiencing extreme fatigue and severe nausea following this immunization.");
    driver.findElement(By.name("addReport")).click();
	assertLogged(TransactionType.ADVERSE_EVENT_REPORT, 1L, 0L, "");
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