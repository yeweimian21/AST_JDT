package edu.ncsu.csc.itrust.selenium;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ExperiencedLHCPsUseCaseTest extends iTrustSeleniumTest{
  private HtmlUnitDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	super.setUp();
    driver = new HtmlUnitDriver();
    gen.standardData();
    gen.patient_hcp_vists();
    gen.hcp_diagnosis_data();
  }

  @Test
  public void testViewDiagnoses() throws Exception {
	  driver = (HtmlUnitDriver)login("1", "pw");
	  
	  // Create the explicit wait
	  WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
	  
	  assertEquals("iTrust - Patient Home", driver.getTitle());
	  assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
	  
	  driver.findElement(By.linkText("My Diagnoses")).click();
	  wait.until(ExpectedConditions.titleIs("iTrust - My Diagnoses"));
	  
	  assertLogged(TransactionType.DIAGNOSES_LIST_VIEW, 1L, 1L, "");
		
	  assertTrue(driver.getPageSource().contains("Echovirus(79.10)"));
	  assertTrue(driver.getPageSource().contains("Acute Lycanthropy(250.00)"));
  }

  @Test
  public void testViewDiagnosesisEchoVirus() throws Exception {
	  driver = (HtmlUnitDriver)login("1", "pw");
	  
	  // Create the explicit wait
	  WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
	  
	  assertEquals("iTrust - Patient Home", driver.getTitle());
	  assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
	  
	  driver.findElement(By.linkText("My Diagnoses")).click();
	  wait.until(ExpectedConditions.titleIs("iTrust - My Diagnoses"));
	  assertLogged(TransactionType.DIAGNOSES_LIST_VIEW, 1L, 1L, "");
	  
	  driver.findElement(By.linkText("Echovirus(79.10)")).click();
	  assertLogged(TransactionType.EXPERIENCED_LHCP_FIND, 1L, 0L, "");
  }
  
  @Test
  public void testViewHCPDetails() throws Exception {
	  driver = (HtmlUnitDriver)login("1", "pw");
	  
	  // Create the explicit wait
	  WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
	  
	  assertEquals("iTrust - Patient Home", driver.getTitle());
	  assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
	  
	  driver.findElement(By.linkText("My Diagnoses")).click();
	  wait.until(ExpectedConditions.titleIs("iTrust - My Diagnoses"));
	  assertLogged(TransactionType.DIAGNOSES_LIST_VIEW, 1L, 1L, "");
	  driver.setJavascriptEnabled(true);
	  driver.findElement(By.linkText("Echovirus(79.10)")).click();;
	  driver.findElement(By.partialLinkText("Jason Frankenstein")).click();

	  assertEquals("iTrust - View Personnel Details", driver.getTitle());
	  assertTrue(driver.getPageSource().contains("Jason Frankenstein"));	
	  assertLogged(TransactionType.PERSONNEL_VIEW, 1L, 9000000004L, "");
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