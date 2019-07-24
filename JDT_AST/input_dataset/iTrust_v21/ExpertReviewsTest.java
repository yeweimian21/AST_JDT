package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class ExpertReviewsTest extends iTrustSeleniumTest{
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	super.setUp();
	gen.standardData();
	gen.reviews();
    driver = new HtmlUnitDriver();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }

  @Test
  public void testValidHCP() throws Exception {
	  driver = login("2", "pw");
    driver.findElement(By.linkText("Find an Expert")).click();
    new Select(driver.findElement(By.name("specialty"))).selectByVisibleText("Surgeon");
    new Select(driver.findElement(By.name("range"))).selectByVisibleText("250 Miles");
    driver.findElement(By.xpath("//form[@id='mainForm']/div[4]/button")).click();
    driver.findElement(By.linkText("View")).click();
    driver.findElement(By.xpath("//div[@id='iTrustContent']/a")).click();
    driver.findElement(By.name("title")).clear();
    driver.findElement(By.name("title")).sendKeys("Too bored?");
    new Select(driver.findElement(By.name("rating"))).selectByVisibleText("2");
    driver.findElement(By.name("description")).clear();
    driver.findElement(By.name("description")).sendKeys("They seemed nice, but they asked how I was then started snoring.");
    driver.findElement(By.name("addReview")).click();
    assertTrue(driver.getPageSource().contains("Too bored?"));
  }
  
  @Test
  public void testInvalidHCP() throws Exception {
	  driver = login("109", "pw");
    driver.findElement(By.linkText("Find an Expert")).click();
    new Select(driver.findElement(By.name("specialty"))).selectByVisibleText("Pediatrician");
    new Select(driver.findElement(By.name("range"))).selectByVisibleText("All");
    driver.findElement(By.xpath("//form[@id='mainForm']/div[4]/button")).click();
    assertTrue(driver.getPageSource().contains("Beaker Beaker"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Beaker Beaker"));
    driver.findElement(By.linkText("View")).click();
    assertFalse(driver.findElement(By.cssSelector("BODY")).getText().contains("Add a Review"));
  }
  
  @Test
  public void testDirectRating() throws Exception {
	  driver = login("109", "pw");
    driver.get(ADDRESS + "auth/patient/reviewsPage.jsp?expertID=9000000000");
    assertEquals(ADDRESS + "auth/patient/reviewsPage.jsp", driver.getCurrentUrl());
    assertTrue(driver.getPageSource().contains("Kelly Doctor is horrible!"));
    assertTrue(driver.getPageSource().contains("Best doctor at this hospital!"));
    assertTrue(driver.getPageSource().contains("So Bad."));
    assertTrue(driver.getPageSource().contains("I am pretty happy"));
  }
  
  @Test
  public void testOverallRating() throws Exception {
	  driver = login("22", "pw");
    driver.get(ADDRESS + "auth/patient/reviewsPage.jsp?expertID=9000000003");
    assertEquals(ADDRESS + "auth/patient/reviewsPage.jsp", driver.getCurrentUrl());
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Gandalf Stormcrow"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Pretty happy"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Good service."));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Add a Review"));
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