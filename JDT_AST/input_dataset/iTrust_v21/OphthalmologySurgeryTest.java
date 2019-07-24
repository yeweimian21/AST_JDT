package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Selenium test cases for the Ophthalmology Surgery feature in UC86.
 */
public class OphthalmologySurgeryTest extends iTrustSeleniumTest{

	/**
	 * Sets up the required base data needed for each test case. This includes two HCP's and two patients.
	 */
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.hcp0();
		gen.hcp11();
		gen.hcp12();
		gen.patient27();
		gen.patient28();
	}
	
	/**
	 * Clears the database after each test.
	 */
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	/**
	 * Selenium test for UC86 Acceptance Scenario 1.
	 * @throws Exception
	 */
	public void testCreateOphthalmologySurgery() throws Exception{
		//Login as Lamar Bridges
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000086", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000086L, 0L, "");
		
		//Click the Add Surgical Ophthalmology Office Visit link
		driver.findElement(By.linkText("Add Surgical Ophthalmology Office Visit")).click();
		
		//Select Brody Franco as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("407");
		driver.findElement(By.xpath("//input[@value='407']")).submit();
		
		// Wait for the page change
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
		wait.until(ExpectedConditions.titleIs("iTrust - Add Surgical Ophthalmology Office Visit"));
		
		//Enable javascript
		driver.setJavascriptEnabled(true);
		
		//fill in form and create
		driver.findElement(By.id("date")).clear();
		driver.findElement(By.id("date")).sendKeys("10/14/2015");
		driver.findElement(By.id("vaNumOD")).clear();
		driver.findElement(By.id("vaNumOD")).sendKeys("20");
		driver.findElement(By.id("vaDenOD")).clear();
		driver.findElement(By.id("vaDenOD")).sendKeys("40");
		driver.findElement(By.id("vaNumOS")).clear();
		driver.findElement(By.id("vaNumOS")).sendKeys("20");
		driver.findElement(By.id("vaDenOS")).clear();
		driver.findElement(By.id("vaDenOS")).sendKeys("40");
		driver.findElement(By.id("sphereOD")).clear();
		driver.findElement(By.id("sphereOD")).sendKeys("-3.25");
		driver.findElement(By.id("sphereOS")).clear();
		driver.findElement(By.id("sphereOS")).sendKeys("-3.00");
		driver.findElement(By.id("cylinderOD")).clear();
		driver.findElement(By.id("cylinderOD")).sendKeys("-1.25");
		driver.findElement(By.id("axisOD")).clear();
		driver.findElement(By.id("axisOD")).sendKeys("50");
		driver.findElement(By.id("addOD")).clear();
		driver.findElement(By.id("addOD")).sendKeys("+1.00");
		driver.findElement(By.id("addOS")).clear();
		driver.findElement(By.id("addOS")).sendKeys("+1.00");
		Select select = new Select(driver.findElementByName("surgery"));
		select.selectByVisibleText("Cataract surgery");
		driver.findElement(By.id("surgeryNotes")).clear();
		driver.findElement(By.id("surgeryNotes")).sendKeys("Surgery completed with no issues.");
		driver.findElement(By.id("submit")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Verify that we are returned to the Ophthalmology Homepage
		wait.until(ExpectedConditions.titleIs("iTrust - Surgical Ophthalmology"));
		
		//Verify that the action was logged
		assertTrue(driver.getPageSource().contains("Surgical Ophthalmology Office Visit successfully added"));
		assertLogged(TransactionType.CREATE_OPHTHALMOLOGY_SURGERY, 9000000086L, 407L, "");
		
		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/14/2015"));
	}
	
	/**
	 * Selenium test for UC86 Acceptance Scenario 2.
	 * @throws Exception
	 */
	public void testInvalidSPHValue() throws Exception{
		//Login as Lamar Bridges
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000086", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000086L, 0L, "");
		
		//Click the Add Ophthalmology Office Visit link
		driver.findElement(By.linkText("Add Surgical Ophthalmology Office Visit")).click();
		
		//Select Freya Chandler as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("408");
		driver.findElement(By.xpath("//input[@value='408']")).submit();
		
		// Wait for the page change
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
		wait.until(ExpectedConditions.titleIs("iTrust - Add Surgical Ophthalmology Office Visit"));
		
		//Enable javascript
		driver.setJavascriptEnabled(true);
		
		//fill in form and create
		driver.findElement(By.id("date")).clear();
		driver.findElement(By.id("date")).sendKeys("10/15/2015");
		driver.findElement(By.id("vaNumOD")).clear();
		driver.findElement(By.id("vaNumOD")).sendKeys("20");
		driver.findElement(By.id("vaDenOD")).clear();
		driver.findElement(By.id("vaDenOD")).sendKeys("10");
		driver.findElement(By.id("vaNumOS")).clear();
		driver.findElement(By.id("vaNumOS")).sendKeys("20");
		driver.findElement(By.id("vaDenOS")).clear();
		driver.findElement(By.id("vaDenOS")).sendKeys("40");
		driver.findElement(By.id("sphereOD")).clear();
		driver.findElement(By.id("sphereOD")).sendKeys("+1.75");
		driver.findElement(By.id("sphereOS")).clear();
		driver.findElement(By.id("sphereOS")).sendKeys("+17.50");
		driver.findElement(By.id("addOD")).clear();
		driver.findElement(By.id("addOD")).sendKeys("+1.25");
		driver.findElement(By.id("addOS")).clear();
		driver.findElement(By.id("addOS")).sendKeys("+1.25");
		driver.findElement(By.id("submit")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Verify that our submission was rejected and we are still on the Add Ophthalmology
		wait.until(ExpectedConditions.textToBePresentInElement(By.className("iTrustError"),
				"The following field are not properly filled in: [SphereOS is required, must be between "
				+ "-10.00 and 10.00 inclusive, and must be rounded to the nearest quarter diopter.]"));
		
		//Fill in the correct values and submit the form
		driver.findElement(By.id("date")).clear();
		driver.findElement(By.id("date")).sendKeys("10/15/2015");
		driver.findElement(By.id("vaNumOD")).clear();
		driver.findElement(By.id("vaNumOD")).sendKeys("20");
		driver.findElement(By.id("vaDenOD")).clear();
		driver.findElement(By.id("vaDenOD")).sendKeys("10");
		driver.findElement(By.id("vaNumOS")).clear();
		driver.findElement(By.id("vaNumOS")).sendKeys("20");
		driver.findElement(By.id("vaDenOS")).clear();
		driver.findElement(By.id("vaDenOS")).sendKeys("40");
		driver.findElement(By.id("sphereOD")).clear();
		driver.findElement(By.id("sphereOD")).sendKeys("+1.75");
		driver.findElement(By.id("sphereOS")).clear();
		driver.findElement(By.id("sphereOS")).sendKeys("+1.50");
		driver.findElement(By.id("addOD")).clear();
		driver.findElement(By.id("addOD")).sendKeys("+1.25");
		driver.findElement(By.id("addOS")).clear();
		driver.findElement(By.id("addOS")).sendKeys("+1.25");
		driver.findElement(By.id("submit")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Verify that we are returned to the Ophthalmology Homepage
		wait.until(ExpectedConditions.titleIs("iTrust - Surgical Ophthalmology"));
		assertTrue(driver.getPageSource().contains("Surgical Ophthalmology Office Visit successfully added"));
		
		//Verify that the action was logged
		assertLogged(TransactionType.CREATE_OPHTHALMOLOGY_SURGERY, 9000000086L, 408L, "");
		
		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/15/2015"));
	}
	
	/**
	 * Selenium test for UC86 Acceptance Scenario 3.
	 * @throws Exception
	 */
	public void testOptometristCreateOphthalmologySurgery() throws Exception{
		//Set up the outcome of Scenario 1
		gen.ophthalmologyScenario1();
		
		//Login as Brooke Tran
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000085", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000085L, 0L, "");
		
		//Click the Add Ophthalmology Office Visit link
		driver.findElement(By.linkText("Add Surgical Ophthalmology Office Visit")).click();
		
		//Select Brody Franco as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("407");
		driver.findElement(By.xpath("//input[@value='407']")).submit();
		
		//Verify that we got redirected to the regular oph office visit page
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
		wait.until(ExpectedConditions.titleIs("iTrust - Add Ophthalmology Office Visit"));
	}
	
	/**
	 * Selenium test for UC86 Acceptance Scenario 4.
	 * @throws Exception
	 */
	public void testEditOphthalmologySurgery() throws Exception{
		//Set up the outcome of Scenario 2
		testInvalidSPHValue();
		
		//Login as Lamar Bridges
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000086", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000086L, 0L, "");

		//Click the Ophthalmology Home link
		driver.findElement(By.linkText("Surgical Ophthalmology Home")).click();

		//Select Freya Chandler as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("408");
		driver.findElement(By.xpath("//input[@value='408']")).submit();
		
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
		wait.until(ExpectedConditions.titleIs("iTrust - Surgical Ophthalmology"));
		
		WebElement myElement = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.linkText("10/15/2015")));
		myElement.click();
		
		//Fill in the correct values and submit the form
		driver.findElement(By.id("surgeryNotes")).clear();
		driver.findElement(By.id("surgeryNotes")).sendKeys("Surgery completed with no issues.");
		driver.findElement(By.id("submit")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Verify that we are returned to the Ophthalmology Homepage
		wait.until(ExpectedConditions.titleIs("iTrust - Surgical Ophthalmology"));
		assertTrue(driver.getPageSource().contains("Surgical Ophthalmology Office Visit successfully edited"));

		//Verify that the action was logged
		assertLogged(TransactionType.EDIT_OPHTHALMOLOGY_SURGERY, 9000000086L, 408L, "");

		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/15/2015"));
	}
}