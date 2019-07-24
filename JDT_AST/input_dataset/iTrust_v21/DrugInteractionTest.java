package edu.ncsu.csc.itrust.selenium;

import org.junit.Test;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class DrugInteractionTest extends iTrustSeleniumTest{
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	super.setUp();
	gen.admin1();
	gen.ndCodes2();
	gen.drugInteractions();
    driver = new HtmlUnitDriver();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }

  @Test
  public void testAddNewOverrideReason() throws Exception {
    driver = login("9000000001", "pw");
    assertEquals("iTrust - Admin Home", driver.getTitle());
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("Edit Override Reason Codes")).click();
    assertEquals("iTrust - Maintain Override Reason Codes", driver.getTitle());
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("Interaction not applicable to this patient");
    driver.findElement(By.id("code")).clear();
    driver.findElement(By.id("code")).sendKeys("22222");
    driver.findElement(By.name("add")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Success: 22222 - Interaction not applicable to this patient added[\\s\\S]*$"));
	assertLogged(TransactionType.OVERRIDE_CODE_ADD, 9000000001L, 0L, "");
  }
  /*
   * Authenticate admin 90000000001
   * Choose "Edit ND Codes"
   * Choose "Tetracycline"
   * Choose "Isotretinoin" interaction
   * Click delete
   */
  @Test
  public void testDeleteDrugInteraction() throws Exception {
	  driver = login("9000000001", "pw");
    assertEquals("iTrust - Admin Home", driver.getTitle());
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("Edit ND Codes")).click();
    assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
    driver.findElement(By.linkText("Tetracycline")).click();
    driver.findElement(By.id("delete")).click();
    // Warning: assertTextPresent may require manual changes
    //assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Interaction deleted successfully[\\s\\S]*$"));
  }
  
  @Test
  public void testEditOverrideReason() throws Exception {
	  gen.ORCodes();
	  driver = login("9000000001", "pw");
    assertEquals("iTrust - Admin Home", driver.getTitle());
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("Edit Override Reason Codes")).click();
    assertEquals("iTrust - Maintain Override Reason Codes", driver.getTitle());
    driver.findElement(By.id("code")).clear();
    driver.findElement(By.id("code")).sendKeys("00001");
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("Alerted interaction not super duper significant");
    driver.findElement(By.name("update")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.getPageSource().contains("Success"));
	assertLogged(TransactionType.OVERRIDE_CODE_EDIT, 9000000001L, 0L, "");
  }
  
  /*
   * Authenticate admin 90000000001
   * Choose "Edit ND Codes"
   * Choose "Edit Interactions"
   * Choose "Adefovir" as one drug
   * Choose "Aspirin" as the other drug
   * Enter "May increase the risk and severity of nephrotoxicity due to additive effects on the kidney."
   * Click submit
   */
  @Test
  public void testRecordDrugInteraction() throws Exception {
    driver = login("9000000001", "pw");
    assertEquals("iTrust - Admin Home", driver.getTitle());
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("Edit ND Codes")).click();
    assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
    driver.findElement(By.id("editInt")).click();
    assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
    //new Select(driver.findElement(By.name("drug1"))).selectByVisibleText("61958-0501 Adefovir");
    //new Select(driver.findElement(By.name("drug2"))).selectByVisibleText("08109-6 Aspirin");
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("May increase the risk and severity of nephrotoxicity due to additive effects on the kidney.");
    driver.findElement(By.name("add")).click();
    // Warning: assertTextPresent may require manual changes
    //assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Interaction recorded successfully[\\s\\S]*$"));
  }
  
  /*
   * Authenticate admin 90000000001
   * Choose "Edit ND Codes"
   * Choose "Edit Interactions"
   * Choose "Adefovir" as both drug1 and drug2
   * Enter "Mixing this drug with itself will cause the person taking it to implode."
   * Click submit
   */
  @Test
  public void testRecordDrugInteractionSameDrugs() throws Exception {
	  driver = login("9000000001", "pw");
    assertEquals("iTrust - Admin Home", driver.getTitle());
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("Edit ND Codes")).click();
    assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
    driver.findElement(By.id("editInt")).click();
    assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
    //((Select) driver.findElement(By.name("drug1"))).selectByVisibleText("61958-0501 Adefovir");
    //((Select) driver.findElement(By.name("drug2"))).selectByVisibleText("61958-0501 Adefovir");
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("Mixing this drug with itself will cause the person taking it to implode.");
    driver.findElement(By.name("add")).click();
    // Warning: assertTextPresent may require manual changes
    //assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Interactions can only be recorded between two different drugs [\\s\\S]*$"));
    assertNotLogged(TransactionType.DRUG_INTERACTION_EDIT, 9000000001L, 0L, "Drug");
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