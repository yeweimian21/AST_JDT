package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Selenium test cases for the Ophthalmology Office Visit feature in UC83.
 */
public class OphthalmologyDiseaseTest extends iTrustSeleniumTest{

	/**
	 * Sets up the required base data needed for each test case. This includes two HCP's and two patients.
	 */
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.hcp0();
		gen.hcp11();
		gen.patient27();
		gen.patient28();
		gen.icd9cmCodes();
	}
	
	/**
	 * Clears the database after each test.
	 */
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	/**
	 * Selenium test for UC85 Acceptance Scenario 1. Diagnoses Cataracts
	 * @throws Exception
	 */
	public void testCreateOphthalmologyOfficeVisit() throws Exception{
		//Login as Brooke Tran
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000085", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000085L, 0L, "");
		
		//Click the Add Ophthalmology Office Visit link
		driver.findElement(By.linkText("Add Ophthalmology Office Visit")).click();
		
		//Select Brody Franco as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("407");
		driver.findElement(By.xpath("//input[@value='407']")).submit();
		String title = driver.getTitle();
		assertEquals("iTrust - Add Ophthalmology Office Visit", title);
		
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
		Select select = new Select(driver.findElementByName("ICDCode"));
		select.selectByVisibleText("26.80 - Cataracts");
		driver.findElement(By.id("submit")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Verify that we are returned to the Ophthalmology Homepage
		title = driver.getTitle();
		assertEquals("iTrust - Ophthalmology", title);
		
		//Verify that the action was logged
		assertTrue(driver.getPageSource().contains("Ophthalmology Office Visit successfully added"));
		assertLogged(TransactionType.CREATE_OPHTHALMOLOGY_OV, 9000000085L, 407L, "");
		
		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/14/2015"));
	}
	
	
	

	
	/**
	 * Selenium test for UC85 Acceptance Scenario 2, tests patient view of Scenario 1
	 * @throws Exception
	 */
	public void testPatientViewOfOphthalmologyOfficeVisit() throws Exception{
		//Set up the outcome of Scenario 1
		testCreateOphthalmologyOfficeVisit();
		
		//Login as Brody Franco
		HtmlUnitDriver driver = (HtmlUnitDriver)login("407", "pw");
		assertLogged(TransactionType.HOME_VIEW, 407L, 0L, "");
		
		//Click the View Ophthalmology Office Visit link
		driver.findElement(By.linkText("View Ophthalmology Records")).click();
		
		//Click the proper office visit link
		driver.findElement(By.linkText("10/14/2015")).click();
		
		//Verify that the action was logged
		assertLogged(TransactionType.PATIENT_VIEW_OPHTHALMOLOGY_OV, 407L, 407L, "");
		
		//Verify that the Office Visit it shown
		assertTrue(driver.getPageSource().contains("10/14/2015"));
		assertTrue(driver.getPageSource().contains("Brooke Tran"));
		assertTrue(driver.getPageSource().contains("20"));
		assertTrue(driver.getPageSource().contains("40"));
		assertTrue(driver.getPageSource().contains("-3.25"));
		assertTrue(driver.getPageSource().contains("-3.0"));
		assertTrue(driver.getPageSource().contains("-1.25"));
		assertTrue(driver.getPageSource().contains("50"));
		assertTrue(driver.getPageSource().contains("1.0"));
		assertTrue(driver.getPageSource().contains("1.0"));
		assertTrue(driver.getPageSource().contains("Cataracts"));
	}
	
	/**
	 * Selenium test for UC85 Acceptance Scenario 3. Diagnoses No disease
	 * @throws Exception
	 */
	public void testCreateOphthalmologyOfficeVisitNoDiseases() throws Exception{
		//Login as Brooke Tran
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000085", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000085L, 0L, "");
		
		//Click the Add Ophthalmology Office Visit link
		driver.findElement(By.linkText("Add Ophthalmology Office Visit")).click();
		
		//Select Brody Franco as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("408");
		driver.findElement(By.xpath("//input[@value='408']")).submit();
		String title = driver.getTitle();
		assertEquals("iTrust - Add Ophthalmology Office Visit", title);
		
		//Enable javascript
		driver.setJavascriptEnabled(true);
		
		//fill in form and create
		driver.findElement(By.id("date")).clear();
		driver.findElement(By.id("date")).sendKeys("10/15/2015");
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
		driver.findElement(By.id("submit")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Verify that we are returned to the Ophthalmology Homepage
		title = driver.getTitle();
		assertEquals("iTrust - Ophthalmology", title);
		
		//Verify that the action was logged
		assertTrue(driver.getPageSource().contains("Ophthalmology Office Visit successfully added"));
		assertLogged(TransactionType.CREATE_OPHTHALMOLOGY_OV, 9000000085L, 408L, "");
		
		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/15/2015"));
	}
	
	/**
	 * Selenium test for UC83 Acceptance Scenario 4. Edits the visit by removing an old disease and adding 2 more.
	 * @throws Exception
	 */
	public void testEditOphthalmologyOfficeVisit() throws Exception{
		//Set up the outcome of Scenario 2
		testCreateOphthalmologyOfficeVisit();
		
		//Login as Brooke Tran
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000085", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000085L, 0L, "");

		//Click the Ophthalmology Home link
		driver.findElement(By.linkText("Ophthalmology Home")).click();

		//Select Freya Chandler as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("407");
		driver.findElement(By.xpath("//input[@value='407']")).submit();
		String title = driver.getTitle();
		assertEquals("iTrust - Ophthalmology", title);
		
		//Click the proper office visit link
		driver.findElement(By.linkText("10/14/2015")).click();
		
		//Fill in the correct values and submit the form
		driver.findElement(By.id("removeDiagID")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Select select = new Select(driver.findElementByName("ICDCode"));
		select.selectByVisibleText("40.89 - Glaucoma");
		driver.findElement(By.id("add_diagnosis")).click();
		select = new Select(driver.findElementByName("ICDCode"));
		select.selectByVisibleText("35.00 - Amblyopia");
		driver.findElement(By.id("add_diagnosis")).click();
		
		driver.findElement(By.id("submit")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Verify that we are returned to the Ophthalmology Homepage
		title = driver.getTitle();
		assertEquals("iTrust - Ophthalmology", title);
		assertTrue(driver.getPageSource().contains("Ophthalmology Office Visit successfully edited"));

		//Verify that the action was logged
		assertLogged(TransactionType.EDIT_OPHTHALMOLOGY_OV, 9000000085L, 407L, "");

		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/14/2015"));
	}
	
	/**
	 * Selenium test for UC85 Acceptance Scenario 5. Tests HCP view of disease diagnosis by Oph HCP
	 * @throws Exception
	 */
	public void testGenericHCPCreateOphthalmologyOfficeVisit() throws Exception{
		//Set up the outcome of Scenario 1
		testCreateOphthalmologyOfficeVisit();
		
		//Login as Kelly Doctor
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		
		//Click the Ophthalmology Home link
		driver.findElement(By.linkText("Ophthalmology Home")).click();
		
		//Select Brody Franco as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("407");
		driver.findElement(By.xpath("//input[@value='407']")).submit();
		
		//Verify that we are returned to the Ophthalmology Homepage
		String title = driver.getTitle();
		assertEquals("iTrust - Ophthalmology", title);
		
		//Click the proper office visit link
		driver.findElement(By.linkText("10/14/2015")).click();
		
		//Verify that the action was logged
		assertLogged(TransactionType.VIEW_OPHTHALMOLOGY_OV, 9000000000L, 407L, "");
		
		//Verify that the Office Visit is shown
		assertTrue(driver.getPageSource().contains("10/14/2015"));
		assertTrue(driver.getPageSource().contains("20"));
		assertTrue(driver.getPageSource().contains("40"));
		assertTrue(driver.getPageSource().contains("20"));
		assertTrue(driver.getPageSource().contains("40"));
		assertTrue(driver.getPageSource().contains("-3.25"));
		assertTrue(driver.getPageSource().contains("-3.0"));
		assertTrue(driver.getPageSource().contains("-1.25"));
		assertTrue(driver.getPageSource().contains("50"));
		assertTrue(driver.getPageSource().contains("1.0"));
		assertTrue(driver.getPageSource().contains("1.0"));
		assertTrue(driver.getPageSource().contains("Cataracts"));
	}
}