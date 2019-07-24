package edu.ncsu.csc.itrust.selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class EditApptTest extends iTrustSeleniumTest{
  private HtmlUnitDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	super.setUp();
	gen.clearAllTables();
	gen.standardData();
    driver = new HtmlUnitDriver();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }

  @Test
  public void testSetPassedDate() throws Exception {
	  gen.uc22();
	  driver = (HtmlUnitDriver)login("9000000000", "pw");
	  driver.setJavascriptEnabled(true);
	  assertEquals("iTrust - HCP Home", driver.getTitle());
	  driver.findElement(By.linkText("View My Appointments")).click();
	  assertLogged(TransactionType.APPOINTMENT_ALL_VIEW, 9000000000L, 0L, "");
	  driver.findElements(By.tagName("td")).get(35).findElement(By.tagName("a")).click();
	  driver.findElement(By.name("schedDate")).clear();
	  driver.findElement(By.name("schedDate")).sendKeys("10/10/2009");
	  driver.findElement(By.id("changeButton")).click();
	  assertTrue(driver.getPageSource().contains("The scheduled date of this appointment"));
	  assertTrue(driver.getPageSource().contains("has already passed")); 
	  assertNotLogged(TransactionType.APPOINTMENT_EDIT, 9000000000L, 100L, "");
  }

  @Test
  public void testRemoveAppt() throws Exception {
	  gen.uc22();
	  driver = (HtmlUnitDriver)login("9000000000", "pw");
	  driver.setJavascriptEnabled(true);
	  assertEquals("iTrust - HCP Home", driver.getTitle());
	  driver.findElement(By.linkText("View My Appointments")).click();
	  assertLogged(TransactionType.APPOINTMENT_ALL_VIEW, 9000000000L, 0L, "");
	  driver.findElements(By.tagName("td")).get(23).findElement(By.tagName("a")).click();
	  driver.findElement(By.id("removeButton")).click();
	  assertTrue(driver.getPageSource().contains("Success: Appointment removed"));
	  assertLoggedNoSecondary(TransactionType.APPOINTMENT_REMOVE, 9000000000L, 0L, "");
  }
  
  @Test
  public void testEditAppt() throws Exception {
	  driver = (HtmlUnitDriver)login("9000000000", "pw");
	  driver.setJavascriptEnabled(true);
    assertEquals("iTrust - HCP Home", driver.getTitle());
    driver.findElement(By.linkText("View My Appointments")).click();
    assertLogged(TransactionType.APPOINTMENT_ALL_VIEW, 9000000000L, 0L, "");
    List<WebElement> rows = driver.findElements(By.tagName("td"));
    //should be the last one
    WebElement mine = rows.get(rows.size() - 1);
    mine.findElement(By.tagName("a")).click();
    assertTrue(driver.getPageSource().contains("Andy Programmer"));
    driver.findElement(By.name("comment")).clear();
    driver.findElement(By.name("comment")).sendKeys("New comment!");
    driver.findElement(By.id("changeButton")).click();
    assertTrue(driver.getPageSource().contains("Success: Appointment changed"));
    assertLogged(TransactionType.APPOINTMENT_EDIT, 9000000000L, 2L, "");
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