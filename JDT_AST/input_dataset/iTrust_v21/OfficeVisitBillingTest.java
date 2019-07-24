
package edu.ncsu.csc.itrust.selenium;


import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;


public class OfficeVisitBillingTest extends iTrustSeleniumTest{
  private HtmlUnitDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();
  protected TestDataGenerator generator = new TestDataGenerator();

  //@Before
  @Override 
  public void setUp() throws Exception {
	super.setUp();
	gen.standardData();
	gen.uc60();
    driver = new HtmlUnitDriver();
  }

  @Test
  public void testPaymentLogging() throws Exception {
	driver = (HtmlUnitDriver)login("311", "pw");
	
	//look at my bills
    driver.findElement(By.linkText("My Bills")).click();
    driver.findElement(By.linkText("12/02/2013")).click();
    driver.findElement(By.id("Ins")).click();
    
    //fill out the information
    driver.findElement(By.name("insID")).clear();
    driver.findElement(By.name("insID")).sendKeys("2324198");
    driver.findElement(By.name("insHolder")).clear();
    driver.findElement(By.name("insHolder")).sendKeys("Sean Ford");
    driver.findElement(By.name("insProvider")).clear();
    driver.findElement(By.name("insProvider")).sendKeys("Blue Cross");
    driver.findElement(By.name("insAdd1")).clear();
    driver.findElement(By.name("insAdd1")).sendKeys("123 Fake Street");
    driver.findElement(By.name("insCity")).clear();
    driver.findElement(By.name("insCity")).sendKeys("Raleigh");
    driver.findElement(By.name("insState")).clear();
    driver.findElement(By.name("insState")).sendKeys("NC");
    driver.findElement(By.name("insZip")).clear();
    driver.findElement(By.name("insZip")).sendKeys("27606");
    driver.findElement(By.name("insPhone")).clear();
    driver.findElement(By.name("insPhone")).sendKeys("555-555-5555");
    
    //submit it and assert that it was logged
    driver.findElement(By.name("insPhone")).submit(); //submit the form
	assertLogged(TransactionType.PATIENT_SUBMITS_INSURANCE, 311L, 311L, "");

  }

  @Test
  public void testBadPolicyID() throws Exception {
	driver = (HtmlUnitDriver)login("311", "pw");
	
	//look at my bills
    driver.findElement(By.linkText("My Bills")).click();
    driver.findElement(By.linkText("12/02/2013")).click();

    //fill out the information (it is bad)
    driver.findElement(By.id("Ins")).click();
    driver.findElement(By.name("insID")).clear();
    driver.findElement(By.name("insID")).sendKeys("!@##%()");
    driver.findElement(By.name("insHolder")).clear();
    driver.findElement(By.name("insHolder")).sendKeys("2324198");
    driver.findElement(By.name("insProvider")).clear();
    driver.findElement(By.name("insProvider")).sendKeys("Blue Cross");
    driver.findElement(By.name("insAdd1")).clear();
    driver.findElement(By.name("insAdd1")).sendKeys("123 Fake Street");
    driver.findElement(By.name("insCity")).clear();
    driver.findElement(By.name("insCity")).sendKeys("Raleigh");
    driver.findElement(By.name("insState")).clear();
    driver.findElement(By.name("insState")).sendKeys("NC");
    driver.findElement(By.name("insZip")).clear();
    driver.findElement(By.name("insZip")).sendKeys("27606");
    driver.findElement(By.name("insPhone")).clear();
    driver.findElement(By.name("insPhone")).sendKeys("555-555-5555");
    
    //submit the form and confirm it doesn't get put through
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertTrue(driver.getPageSource().contains("Insurance IDs must consist of alphanumeric characters."));
    driver.findElement(By.linkText("My Bills")).click();
    assertTrue(driver.getPageSource().contains("Unsubmitted"));
    
  }
  public void testViewBilling() throws Exception {
	driver = (HtmlUnitDriver)login("311", "pw");
    driver.findElement(By.linkText("My Bills")).click();
    
    //check the first bill
    assertTrue(driver.getPageSource().contains("03/08/2012"));
    assertTrue(driver.getPageSource().contains("Kelly Doctor"));
    assertTrue(driver.getPageSource().contains("Submitted"));
    assertTrue(driver.getPageSource().contains("12/02/2013"));
    assertTrue(driver.getPageSource().contains("Meredith Palmer"));
    assertTrue(driver.getPageSource().contains("Unsubmitted"));
    
    //check the next one
    driver.findElement(By.linkText("12/02/2013")).click();
    assertTrue(driver.getPageSource().contains("Central Hospital"));
    assertTrue(driver.getPageSource().contains("General Checkup"));
    assertTrue(driver.getPageSource().contains("Sean needs to lower his sodium intake."));
  }
  
  public void testInsurance() throws Exception {
	driver = (HtmlUnitDriver)login("9000000011", "pw");
	
	//document an office visit
	driver.findElement(By.linkText("Document Office Visit")).click();
	//search for patient 1 by MID
	driver.findElement(By.name("UID_PATIENTID")).sendKeys("309");
	
	//the button to click should have the text of the MID
	driver.findElement(By.cssSelector("input[value='309']")).submit();
	//now have to say yes document office visit
	WebElement form = driver.findElement(By.id("formMain"));
	form.submit();
	
	//fill out the information
    driver.findElement(By.name("visitDate")).clear();
    driver.findElement(By.name("visitDate")).sendKeys("02/06/2014");
    driver.findElement(By.name("notes")).clear();
    driver.findElement(By.name("notes")).sendKeys("Patient seems to be doing well, Rob is encouraged to consume more iron.");
    new Select(driver.findElement(By.name("hospitalID"))).selectByVisibleText("Ninja Hospital");
    driver.findElement(By.name("isBilled")).click();
    new Select(driver.findElement(By.name("apptType"))).selectByVisibleText("General Checkup");
    driver.findElement(By.id("update")).click();
    
    //logout
    WebElement logout = driver.findElements(By.tagName("li")).get(1);
    logout.findElement(By.tagName("a")).click();
    
    //login as patient 309
    driver = (HtmlUnitDriver)login("309", "pw");
    driver.findElement(By.linkText("My Bills")).click();
    driver.findElement(By.linkText("02/06/2014")).click();

    //fill out the info and submit
    driver.findElement(By.id("Ins")).click();
    driver.findElement(By.name("insID")).clear();
    driver.findElement(By.name("insID")).sendKeys("2324199");
    driver.findElement(By.name("insHolder")).clear();
    driver.findElement(By.name("insHolder")).sendKeys("Rob Peterson");
    driver.findElement(By.name("insProvider")).clear();
    driver.findElement(By.name("insProvider")).sendKeys("Blue Cross");
    driver.findElement(By.name("insAdd1")).clear();
    driver.findElement(By.name("insAdd1")).sendKeys("123 Fake Street");
    driver.findElement(By.name("insAdd2")).clear();
    driver.findElement(By.name("insAdd2")).sendKeys("123 Faker Street");
    driver.findElement(By.name("insCity")).clear();
    driver.findElement(By.name("insCity")).sendKeys("Raleigh");
    driver.findElement(By.name("insState")).clear();
    driver.findElement(By.name("insState")).sendKeys("NC");
    driver.findElement(By.name("insZip")).clear();
    driver.findElement(By.name("insZip")).sendKeys("27606");
    driver.findElement(By.name("insPhone")).clear();
    driver.findElement(By.name("insPhone")).sendKeys("555-555-5555");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    
	assertLogged(TransactionType.PATIENT_SUBMITS_INSURANCE, 309L, 309L, "");
  }
  
  public void testCreditCard() throws Exception {
	driver = (HtmlUnitDriver)login("9000000011", "pw");
	//document an office visit for patient 310
	driver.findElement(By.linkText("Document Office Visit")).click();
	//search for patient 1 by MID
	driver.findElement(By.name("UID_PATIENTID")).sendKeys("310");
	
	//the button to click should have the text of the MID
	driver.findElement(By.cssSelector("input[value='310']")).submit();
	//now have to say yes document office visit
	WebElement form = driver.findElement(By.id("formMain"));
	form.submit();
	
    driver.findElement(By.name("visitDate")).clear();
    driver.findElement(By.name("visitDate")).sendKeys("01/21/2014");
    driver.findElement(By.name("notes")).clear();
    driver.findElement(By.name("notes")).sendKeys("Theresa has been complaining of extreme fatigue. Bloodwork sent in for analysis.");
    new Select(driver.findElement(By.name("hospitalID"))).selectByVisibleText("Ninja Hospital");
    driver.findElement(By.name("isBilled")).click();
    new Select(driver.findElement(By.name("apptType"))).selectByVisibleText("General Checkup");
    driver.findElement(By.id("update")).click();
    
    //logout
    WebElement logout = driver.findElements(By.tagName("li")).get(1);
    logout.findElement(By.tagName("a")).click();
    
    //login as patient 310
    driver = (HtmlUnitDriver)login("310", "pw");
    driver.findElement(By.linkText("My Bills")).click();
    driver.findElement(By.linkText("01/21/2014")).click();
    driver.findElement(By.id("CC")).click();

    driver.findElement(By.name("ccHolder")).clear();
    driver.findElement(By.name("ccHolder")).sendKeys("Theresa Clark");
    driver.findElement(By.name("billAddress")).clear();
    driver.findElement(By.name("billAddress")).sendKeys("123 Fake Street, Raleigh, NC 27607");
    new Select(driver.findElement(By.name("ccType"))).selectByVisibleText("MasterCard");
    driver.findElement(By.name("ccNumber")).clear();
    driver.findElement(By.name("ccNumber")).sendKeys("5593090746812380");
    driver.findElement(By.name("cvv")).clear();
    driver.findElement(By.name("cvv")).sendKeys("000");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    
    assertTrue(driver.getPageSource().contains("Payment Information"));
	assertLogged(TransactionType.PATIENT_PAYS_BILL, 310L, 310L, "");
  }
  
  
  public void testCancelPayment() throws Exception {
	driver = (HtmlUnitDriver)login("311", "pw");
    driver.findElement(By.linkText("My Bills")).click();
    
    //check that it has the right info
    assertTrue(driver.getPageSource().contains("03/08/2012"));
    assertTrue(driver.getPageSource().contains("Kelly Doctor"));
    assertTrue(driver.getPageSource().contains("Submitted"));
    assertTrue(driver.getPageSource().contains("12/02/2013"));
    assertTrue(driver.getPageSource().contains("Meredith Palmer"));
    assertTrue(driver.getPageSource().contains("Unsubmitted"));
    
    //check the next one
    driver.findElement(By.linkText("12/02/2013")).click();
    driver.findElement(By.id("Ins")).click();
    driver.findElement(By.name("insID")).clear();
    driver.findElement(By.name("insID")).sendKeys("Sean Ford");
    driver.findElement(By.name("insHolder")).clear();
    driver.findElement(By.name("insHolder")).sendKeys("2324198");
    driver.findElement(By.name("insProvider")).clear();
    driver.findElement(By.name("insProvider")).sendKeys("Blue Cross");
    driver.findElement(By.name("insAdd1")).clear();
    driver.findElement(By.name("insAdd1")).sendKeys("123 Fake Street");
    driver.findElement(By.name("insCity")).clear();
    driver.findElement(By.name("insCity")).sendKeys("Raleigh");
    driver.findElement(By.name("insState")).clear();
    driver.findElement(By.name("insState")).sendKeys("NC");
    driver.findElement(By.name("insZip")).clear();
    driver.findElement(By.name("insZip")).sendKeys("27606");
    driver.findElement(By.name("insPhone")).clear();
    driver.findElement(By.name("insPhone")).sendKeys("555-555-5555");
    driver.findElement(By.tagName("button")).click();
    driver.findElement(By.linkText("My Bills")).click();
    assertTrue(driver.getPageSource().contains("Unsubmitted"));
  }
  
  
  public void testBillNotification() throws Exception {
	driver = (HtmlUnitDriver)login("310", "pw");
    
    assertFalse(driver.getPageSource().contains("new bill"));
    //now logout
    WebElement logout = driver.findElements(By.tagName("li")).get(1);
    logout.findElement(By.tagName("a")).click();
    
    
    driver = (HtmlUnitDriver)login("9000000011", "pw");
	//document an office visit patient 310
    driver.findElement(By.linkText("Document Office Visit")).click();
	//search for patient 1 by MID
	driver.findElement(By.name("UID_PATIENTID")).sendKeys("310");
	
	//the button to click should have the text of the MID
	driver.findElement(By.cssSelector("input[value='310']")).submit();
	//now have to say yes document office visit
	WebElement form = driver.findElement(By.id("formMain"));
	form.submit();
	
	//fill out the info
    driver.findElement(By.name("visitDate")).clear();
    driver.findElement(By.name("visitDate")).sendKeys("01/21/2014");
    driver.findElement(By.name("notes")).clear();
    driver.findElement(By.name("notes")).sendKeys("Theresa has been complaining of extreme fatigue. Bloodwork sent in for analysis.");
    new Select(driver.findElement(By.name("hospitalID"))).selectByVisibleText("Ninja Hospital");
    driver.findElement(By.name("isBilled")).click();
    new Select(driver.findElement(By.name("apptType"))).selectByVisibleText("General Checkup");
    driver.findElement(By.id("update")).click();
    
    //logout
    logout = driver.findElements(By.tagName("li")).get(1);
    logout.findElement(By.tagName("a")).click();
    
    //login as 310
    driver = (HtmlUnitDriver)login("310", "pw");
    
    assertTrue(driver.getPageSource().contains("new bill"));
  }
  
  
  public void testVisitNotBilled() throws Exception {
	  driver = (HtmlUnitDriver)login("9000000011", "pw");
	  //document an office visit for patient 310
	  driver.findElement(By.linkText("Document Office Visit")).click();
	  //search for patient 1 by MID
	  driver.findElement(By.name("UID_PATIENTID")).sendKeys("310");
	
	  //the button to click should have the text of the MID
	  driver.findElement(By.cssSelector("input[value='310']")).submit();
	  //now have to say yes document office visit
	  WebElement form = driver.findElement(By.id("formMain"));
	  form.submit();
	
	  driver.findElement(By.name("visitDate")).clear();
	  driver.findElement(By.name("visitDate")).sendKeys("01/21/2014");
	  driver.findElement(By.name("notes")).clear();
	  driver.findElement(By.name("notes")).sendKeys("Theresa has been complaining of extreme fatigue. Bloodwork sent in for analysis.");
	  new Select(driver.findElement(By.name("hospitalID"))).selectByVisibleText("Ninja Hospital");
	  new Select(driver.findElement(By.name("apptType"))).selectByVisibleText("General Checkup");
	  driver.findElement(By.id("update")).click();
	  //now logout
	  WebElement logout = driver.findElements(By.tagName("li")).get(1);
	  logout.findElement(By.tagName("a")).click();
	   
	  //login as 310 again
	  driver = (HtmlUnitDriver)login("310", "pw");
	  driver.findElement(By.linkText("My Bills")).click();
	  assertFalse(driver.getPageSource().contains("1/21/2014"));
	  driver.findElement(By.linkText("View My Records")).click();
	  assertTrue(driver.getPageSource().contains("Jan 21, 2014"));
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