package edu.ncsu.csc.itrust.selenium;

import org.junit.Test;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class EditDiagnosesTest extends iTrustSeleniumTest{
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	super.setUp();
	gen.standardData();
    driver = new HtmlUnitDriver();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }

  @Test
  public void testAdminEditDiagnoses() throws Exception {
	  driver = login("9000000001", "pw");
    assertEquals("iTrust - Admin Home", driver.getTitle());
    driver.findElement(By.linkText("Edit Diagnoses URLs")).click();
    new Select(driver.findElement(By.name("diagnoses"))).selectByVisibleText("11.40 - Tuberculosis of the lung");
    driver.findElement(By.id("action")).click();
    driver.findElement(By.name("url")).clear();
    driver.findElement(By.name("url")).sendKeys("http://www.google.com/");
    driver.findElement(By.id("action")).click();
    assertEquals("iTrust - Maintain Diagnoses Links", driver.getTitle());
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Tuberculosis of the lung's \\(11\\.40\\) URL has been successfully updated to http://www\\.google\\.com/[\\s\\S]*$"));
  }
  
  @Test
  public void testDiagnosesLink() throws Exception {
	  driver = login("9000000000", "pw");
    assertEquals("iTrust - HCP Home", driver.getTitle());
    driver.findElement(By.linkText("Edit Diagnoses URLs")).click();
    new Select(driver.findElement(By.name("diagnoses"))).selectByVisibleText("715.09 - Osteoarthrosis, generalized, multiple sites");
    driver.findElement(By.id("action")).click();
    driver.findElement(By.name("url")).clear();
    driver.findElement(By.name("url")).sendKeys("http://www.wikipedia.org/");
    driver.findElement(By.id("action")).click();
    assertEquals("iTrust - Maintain Diagnoses Links", driver.getTitle());
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*URL has been successfully updated to http://www\\.wikipedia\\.org/[\\s\\S]*$"));
    ((HtmlUnitDriver) driver).setJavascriptEnabled(false);
    driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
    ((HtmlUnitDriver) driver).setJavascriptEnabled(true);
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("1");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("My Diagnoses")).click();
    driver.findElement(By.linkText("http://www.wikipedia.org/")).click();
    assertEquals("Wikipedia", driver.getTitle());
  }
  
  @Test
  public void testHCPEditDiagnoses() throws Exception {
	  driver = login("9000000000", "pw");
    assertEquals("iTrust - HCP Home", driver.getTitle());
    driver.findElement(By.linkText("Edit Diagnoses URLs")).click();
    new Select(driver.findElement(By.name("diagnoses"))).selectByVisibleText("11.40 - Tuberculosis of the lung");
    driver.findElement(By.id("action")).click();
    driver.findElement(By.name("url")).clear();
    driver.findElement(By.name("url")).sendKeys("http://www.google.com/");
    driver.findElement(By.id("action")).click();
    assertEquals("iTrust - Maintain Diagnoses Links", driver.getTitle());
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Tuberculosis of the lung's \\(11\\.40\\) URL has been successfully updated to http://www\\.google\\.com/[\\s\\S]*$"));
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