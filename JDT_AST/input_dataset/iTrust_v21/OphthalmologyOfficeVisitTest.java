package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Selenium test cases for the Ophthalmology Office Visit feature in UC83.
 */
public class OphthalmologyOfficeVisitTest extends iTrustSeleniumTest{

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
	}
	
	/**
	 * Clears the database after each test.
	 */
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	/**
	 * Selenium test for UC83 Acceptance Scenario 1.
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
	 * Selenium test for UC83 Acceptance Scenario 2.
	 * @throws Exception
	 */
	public void testInvalidSPHValue() throws Exception{
		//Login as Brooke Tran
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000085", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000085L, 0L, "");
		
		//Click the Add Ophthalmology Office Visit link
		driver.findElement(By.linkText("Add Ophthalmology Office Visit")).click();
		
		//Select Freya Chandler as the patient
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
		assertTrue(driver.getPageSource().contains("The following field are not properly filled in: [SphereOS is required, must be between -10.00 and 10.00 inclusive, and must be rounded to the nearest quarter diopter.]"));
		
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
		title = driver.getTitle();
		assertEquals("iTrust - Ophthalmology", title);
		assertTrue(driver.getPageSource().contains("Ophthalmology Office Visit successfully added"));
		
		//Verify that the action was logged
		assertLogged(TransactionType.CREATE_OPHTHALMOLOGY_OV, 9000000085L, 408L, "");
		
		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/15/2015"));
	}
	
	/**
	 * Selenium test for UC83 Acceptance Scenario 3.
	 * @throws Exception
	 */
	public void testGenericHCPCreateOphthalmologyOfficeVisit() throws Exception{
		//Set up the outcome of Scenario 1
		gen.ophthalmologyScenario1();
		
		//Login as Kelly Doctor
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Click the Add Ophthalmology Office Visit link
		driver.findElement(By.linkText("Add Ophthalmology Office Visit")).click();
		
		//Select Brody Franco as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("407");
		driver.findElement(By.xpath("//input[@value='407']")).submit();
		
		//Verify that we got redirected to the regular office visit page
		String title = driver.getTitle();
		assertEquals("iTrust - Document Office Visit", title);

		//Click the Ophthalmology Home link
		driver.findElement(By.linkText("Ophthalmology Home")).click();
		
		//Verify that we are returned to the Ophthalmology Homepage
		title = driver.getTitle();
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
	}
	
	/**
	 * Selenium test for UC83 Acceptance Scenario 4.
	 * @throws Exception
	 */
	public void testEditOphthalmologyOfficeVisit() throws Exception{
		//Set up the outcome of Scenario 2
		gen.ophthalmologyScenario2();
		
		//Login as Brooke Tran
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000085", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000085L, 0L, "");

		//Click the Ophthalmology Home link
		driver.findElement(By.linkText("Ophthalmology Home")).click();

		//Select Freya Chandler as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("408");
		driver.findElement(By.xpath("//input[@value='408']")).submit();
		String title = driver.getTitle();
		assertEquals("iTrust - Ophthalmology", title);
		
		//Click the proper office visit link
		driver.findElement(By.linkText("10/15/2015")).click();
		
		//Fill in the correct values and submit the form
		driver.findElement(By.id("vaDenOD")).clear();
		driver.findElement(By.id("vaDenOD")).sendKeys("15");
		driver.findElement(By.id("submit")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Verify that we are returned to the Ophthalmology Homepage
		title = driver.getTitle();
		assertEquals("iTrust - Ophthalmology", title);
		assertTrue(driver.getPageSource().contains("Ophthalmology Office Visit successfully edited"));

		//Verify that the action was logged
		assertLogged(TransactionType.EDIT_OPHTHALMOLOGY_OV, 9000000085L, 408L, "");

		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/15/2015"));
	}
	
	/**
	 * Selenium test for UC84 Acceptance Scenario 1.
	 * @throws Exception
	 */
	public void testPatientViewOfOphthalmologyOfficeVisit() throws Exception{
		//Set up the outcome of Scenario 1
		gen.ophthalmologyScenario1();
		
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
	}
	
	/**
	 * Selenium test for UC84 Acceptance Scenario 2.
	 * @throws Exception
	 */
	public void testPatientViewOfEditedOfficeVisit() throws Exception{
		//Set up the outcome of Scenario 2
		gen.ophthalmologyScenario2();
		
		//Login as Freya Chandler
		HtmlUnitDriver driver = (HtmlUnitDriver)login("408", "pw");
		assertLogged(TransactionType.HOME_VIEW, 408L, 0L, "");
		
		//Click the View Ophthalmology Office Visit link
		driver.findElement(By.linkText("View Ophthalmology Records")).click();
				
		//Click the proper office visit link
		driver.findElement(By.linkText("10/15/2015")).click();
		
		//Verify that the action was logged
		assertLogged(TransactionType.PATIENT_VIEW_OPHTHALMOLOGY_OV, 408L, 408L, "");
		
		//Verify that the Office Visit it shown
		assertTrue(driver.getPageSource().contains("10/15/2015"));
		assertTrue(driver.getPageSource().contains("Brooke Tran"));
		assertTrue(driver.getPageSource().contains("20"));
		assertTrue(driver.getPageSource().contains("10"));
		assertTrue(driver.getPageSource().contains("1.75"));
		assertTrue(driver.getPageSource().contains("1.75"));
		assertTrue(driver.getPageSource().contains("1.25"));
		assertTrue(driver.getPageSource().contains("1.25"));
		
		
		//Simulate the change
		//First logout
		driver.get("http://localhost:8080/iTrust/logout.jsp");
		assertEquals("iTrust - Login", driver.getTitle());
		
		//Then basically rerun the code for UC83 Acceptance Test 4
		//Login as Brooke Tran
		driver = (HtmlUnitDriver)login("9000000085", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000085L, 0L, "");

		//Click the Ophthalmology Home link
		driver.findElement(By.linkText("Ophthalmology Home")).click();

		//Select Freya Chandler as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("408");
		driver.findElement(By.xpath("//input[@value='408']")).submit();
		String title = driver.getTitle();
		assertEquals("iTrust - Ophthalmology", title);

		//Click the proper office visit link
		driver.findElement(By.linkText("10/15/2015")).click();

		//Fill in the correct values and submit the form
		driver.findElement(By.id("vaDenOD")).clear();
		driver.findElement(By.id("vaDenOD")).sendKeys("15");
		driver.findElement(By.id("submit")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//Verify that we are returned to the Ophthalmology Homepage
		title = driver.getTitle();
		assertEquals("iTrust - Ophthalmology", title);
		assertTrue(driver.getPageSource().contains("Ophthalmology Office Visit successfully edited"));

		//Verify that the action was logged
		assertLogged(TransactionType.EDIT_OPHTHALMOLOGY_OV, 9000000085L, 408L, "");
		
		//Then logout
		driver.get("http://localhost:8080/iTrust/logout.jsp");
		assertEquals("iTrust - Login", driver.getTitle());
		
		//Re-Login as Freya Chandler
		driver = (HtmlUnitDriver)login("408", "pw");
		assertLogged(TransactionType.HOME_VIEW, 408L, 0L, "");

		//Click the View Ophthalmology Office Visit link
		driver.findElement(By.linkText("View Ophthalmology Records")).click();

		//Click the proper office visit link
		driver.findElement(By.linkText("10/15/2015")).click();
		
		//Verify that the action was logged
		assertLogged(TransactionType.PATIENT_VIEW_OPHTHALMOLOGY_OV, 408L, 408L, "");

		//Verify that the Office Visit it shown
		assertTrue(driver.getPageSource().contains("10/15/2015"));
		assertTrue(driver.getPageSource().contains("Brooke Tran"));
		assertTrue(driver.getPageSource().contains("20"));
		assertTrue(driver.getPageSource().contains("15"));
		assertTrue(driver.getPageSource().contains("40"));
		assertTrue(driver.getPageSource().contains("1.75"));
		assertTrue(driver.getPageSource().contains("1.75"));
		assertTrue(driver.getPageSource().contains("1.25"));
		assertTrue(driver.getPageSource().contains("1.25"));
	}
	
	/**
	 * Selenium test for UC84 Acceptance Scenario 3.
	 * @throws Exception
	 */
	public void testPatientViewOfDependentOfficeVisit() throws Exception{
		//Make sure that Brittany Franco is in the system
		gen.patient29();
		//Set up the outcome of Scenario 1
		gen.ophthalmologyScenario3();

		
		//Login as Brody Franco
		HtmlUnitDriver driver = (HtmlUnitDriver)login("407", "pw");
		assertLogged(TransactionType.HOME_VIEW, 407L, 0L, "");
		
		//Click the View Ophthalmology Office Visit link
		driver.findElement(By.linkText("View Dependent's Ophthalmology Records")).click();

		//Select Brittany as the dependent
		WebElement elem = driver.findElement(By.name("selectedDependent"));
		Select s = new Select(elem);
		s.selectByIndex(0);
		elem.submit();
		
		//Click the proper office visit link
		driver.findElement(By.linkText("10/30/2015")).click();
		
		//Verify that the action was logged
		assertLogged(TransactionType.PATIENT_VIEW_DEPENDENT_OPHTHALMOLOGY_OV, 407L, 409L, "");

		//Verify that the Office Visit it shown
		assertTrue(driver.getPageSource().contains("10/30/2015"));
		assertTrue(driver.getPageSource().contains("Brooke Tran"));
		assertTrue(driver.getPageSource().contains("20"));
		assertTrue(driver.getPageSource().contains("18"));
		assertTrue(driver.getPageSource().contains("40"));
		assertTrue(driver.getPageSource().contains(".75"));
		assertTrue(driver.getPageSource().contains(".5"));
		assertTrue(driver.getPageSource().contains("-0.25"));
		assertTrue(driver.getPageSource().contains("30"));
		assertTrue(driver.getPageSource().contains("1.0"));
		assertTrue(driver.getPageSource().contains("1.0"));
	}
	
	/**
	 * Tests to make sure that a patient with multiple dependents is able to view
	 * the Ophthalmology Office Visits of all their dependents.
	 * @throws Exception
	 */
	public void testPatientViewOfMultipleDependentsOfficeVisit() throws Exception{
		//Make sure that Brittany Franco is in the system.
		gen.patient29();
		//Make sure that James Franco is in the system.
		gen.patient30();
		//Set up the outcome of Scenario 1
		gen.ophthalmologyScenario3();

		//Login as Brody Franco
		HtmlUnitDriver driver = (HtmlUnitDriver)login("407", "pw");
		assertLogged(TransactionType.HOME_VIEW, 407L, 0L, "");
		
		//Click the View Ophthalmology Office Visit link
		driver.findElement(By.linkText("View Dependent's Ophthalmology Records")).click();

		//Select James as the dependent
		WebElement elem = driver.findElement(By.name("selectedDependent"));
		Select s = new Select(elem);
		s.selectByIndex(1);
		elem.submit();

		//Verify that no Office Visits are shown.
		assertTrue(driver.getPageSource().contains("No prior records"));
	}
	
	/**
	 * Tests to make sure that a patient with no dependents is not able to select anything
	 * on the "View Dependents Ophthalmology Office Visit" page.
	 * @throws Exception
	 */
	public void testPatientViewOfDependentsOfficeVisitWithNoDependents() throws Exception{
		//Login as Freya Chandler
		HtmlUnitDriver driver = (HtmlUnitDriver)login("408", "pw");
		assertLogged(TransactionType.HOME_VIEW, 408L, 0L, "");
		
		//Click the View Ophthalmology Office Visit link
		driver.findElement(By.linkText("View Dependent's Ophthalmology Records")).click();
		
		//Verify that no dependents are shown.
		assertTrue(driver.getPageSource().contains("User has no dependents"));
	}
	
	/**
	 * Tests the upward boundary of the SPH parameter
	 * @throws Exception
	 */
	public void testSPHBoundaryHigh() throws Exception{
		//Login as Brooke Tran
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000085", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000085L, 0L, "");
		
		//Click the Add Ophthalmology Office Visit link
		driver.findElement(By.linkText("Add Ophthalmology Office Visit")).click();
		
		//Select Freya Chandler as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("408");
		driver.findElement(By.xpath("//input[@value='408']")).submit();
		String title = driver.getTitle();
		assertEquals("iTrust - Add Ophthalmology Office Visit", title);
		
		//Enable javascript
		driver.setJavascriptEnabled(true);
		
		//fill in form and create
		driver.findElement(By.id("date")).clear();
		driver.findElement(By.id("date")).sendKeys("10/16/2015");
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
		driver.findElement(By.id("sphereOS")).sendKeys("+10.00"); //notice that this is exactly on the boundary
		driver.findElement(By.id("addOD")).clear();
		driver.findElement(By.id("addOD")).sendKeys("+1.25");
		driver.findElement(By.id("addOS")).clear();
		driver.findElement(By.id("addOS")).sendKeys("+1.25");
		driver.findElement(By.id("submit")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//submit form, and wait so that Jenkin's doesn't screw up
		
		//Verify that we are returned to the Ophthalmology Homepage
		title = driver.getTitle();
		assertEquals("iTrust - Ophthalmology", title);
		assertTrue(driver.getPageSource().contains("Ophthalmology Office Visit successfully added"));
		
		//Verify that the action was logged
		assertLogged(TransactionType.CREATE_OPHTHALMOLOGY_OV, 9000000085L, 408L, "");
		
		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/16/2015"));
	}
	
	/**
	 * Tests the lower bound of the SPH parameter
	 * @throws Exception
	 */
	public void testSPHBoundaryLow() throws Exception{
		//Login as Brooke Tran
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000085", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000085L, 0L, "");
		
		//Click the Add Ophthalmology Office Visit link
		driver.findElement(By.linkText("Add Ophthalmology Office Visit")).click();
		
		//Select Freya Chandler as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("408");
		driver.findElement(By.xpath("//input[@value='408']")).submit();
		String title = driver.getTitle();
		assertEquals("iTrust - Add Ophthalmology Office Visit", title);
		
		//Enable javascript
		driver.setJavascriptEnabled(true);
		
		//fill in form and create
		driver.findElement(By.id("date")).clear();
		driver.findElement(By.id("date")).sendKeys("10/17/2015");
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
		driver.findElement(By.id("sphereOS")).sendKeys("-10.00"); //notice that this is exactly on the boundary
		driver.findElement(By.id("addOD")).clear();
		driver.findElement(By.id("addOD")).sendKeys("+1.25");
		driver.findElement(By.id("addOS")).clear();
		driver.findElement(By.id("addOS")).sendKeys("+1.25");
		driver.findElement(By.id("submit")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		
		//Verify that we are returned to the Ophthalmology Homepage
		title = driver.getTitle();
		assertEquals("iTrust - Ophthalmology", title);
		assertTrue(driver.getPageSource().contains("Ophthalmology Office Visit successfully added"));
		
		//Verify that the action was logged
		assertLogged(TransactionType.CREATE_OPHTHALMOLOGY_OV, 9000000085L, 408L, "");
		
		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/17/2015"));
	}
	
	/**
	 * Tests what happens when the user puts in a non-number for SPH.
	 * @throws Exception
	 */
	public void testSphereODText() throws Exception{
		//Login as Brooke Tran
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000085", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000085L, 0L, "");
		
		//Click the Add Ophthalmology Office Visit link
		driver.findElement(By.linkText("Add Ophthalmology Office Visit")).click();
		
		//Select Freya Chandler as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("408");
		driver.findElement(By.xpath("//input[@value='408']")).submit();
		String title = driver.getTitle();
		assertEquals("iTrust - Add Ophthalmology Office Visit", title);
		
		//Enable javascript
		driver.setJavascriptEnabled(true);
		
		//fill in form and create
		driver.findElement(By.id("date")).clear();
		driver.findElement(By.id("date")).sendKeys("10/18/2015");
		driver.findElement(By.id("vaNumOD")).clear();
		driver.findElement(By.id("vaNumOD")).sendKeys("20");
		driver.findElement(By.id("vaDenOD")).clear();
		driver.findElement(By.id("vaDenOD")).sendKeys("10");
		driver.findElement(By.id("vaNumOS")).clear();
		driver.findElement(By.id("vaNumOS")).sendKeys("20");
		driver.findElement(By.id("vaDenOS")).clear();
		driver.findElement(By.id("vaDenOS")).sendKeys("40");
		driver.findElement(By.id("sphereOD")).clear();
		driver.findElement(By.id("sphereOD")).sendKeys("bacon"); //notice that this is not a number
		driver.findElement(By.id("sphereOS")).clear();
		driver.findElement(By.id("sphereOS")).sendKeys("+5.00");
		driver.findElement(By.id("addOD")).clear();
		driver.findElement(By.id("addOD")).sendKeys("+1.25");
		driver.findElement(By.id("addOS")).clear();
		driver.findElement(By.id("addOS")).sendKeys("+1.25");
		driver.findElement(By.id("submit")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
		//Verify that our submission was rejected and we are still on the Add Ophthalmology
		assertTrue(driver.getPageSource().contains("The following field are not properly filled in: [SphereOD is required, must be between -10.00 and 10.00 inclusive, and must be rounded to the nearest quarter diopter.]"));
		
		//Fill in the correct values and submit the form
		driver.findElement(By.id("date")).clear();
		driver.findElement(By.id("date")).sendKeys("10/18/2015");
		driver.findElement(By.id("vaNumOD")).clear();
		driver.findElement(By.id("vaNumOD")).sendKeys("20");
		driver.findElement(By.id("vaDenOD")).clear();
		driver.findElement(By.id("vaDenOD")).sendKeys("10");
		driver.findElement(By.id("vaNumOS")).clear();
		driver.findElement(By.id("vaNumOS")).sendKeys("20");
		driver.findElement(By.id("vaDenOS")).clear();
		driver.findElement(By.id("vaDenOS")).sendKeys("40");
		driver.findElement(By.id("sphereOD")).clear();
		driver.findElement(By.id("sphereOD")).sendKeys("+1.75"); //notice that this is not a number
		driver.findElement(By.id("sphereOS")).clear();
		driver.findElement(By.id("sphereOS")).sendKeys("+5.00");
		driver.findElement(By.id("addOD")).clear();
		driver.findElement(By.id("addOD")).sendKeys("+1.25");
		driver.findElement(By.id("addOS")).clear();
		driver.findElement(By.id("addOS")).sendKeys("+1.25");
		driver.findElement(By.id("submit")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
		//Verify that we are returned to the Ophthalmology Homepage
		title = driver.getTitle();
		assertEquals("iTrust - Ophthalmology", title);
		assertTrue(driver.getPageSource().contains("Ophthalmology Office Visit successfully added"));
			
		//Verify that the action was logged
		assertLogged(TransactionType.CREATE_OPHTHALMOLOGY_OV, 9000000085L, 408L, "");
		
		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/18/2015"));
	}
	
	/**
	 * Tests what happens when a user puts in a non-integer for the axis.
	 * @throws Exception
	 */
	public void testAxisDecimal() throws Exception{
		//Login as Brooke Tran
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000085", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000085L, 0L, "");
		
		//Click the Add Ophthalmology Office Visit link
		driver.findElement(By.linkText("Add Ophthalmology Office Visit")).click();
		
		//Select Freya Chandler as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("408");
		driver.findElement(By.xpath("//input[@value='408']")).submit();
		String title = driver.getTitle();
		assertEquals("iTrust - Add Ophthalmology Office Visit", title);
		
		//Enable javascript
		driver.setJavascriptEnabled(true);
		
		//fill in form and create
		driver.findElement(By.id("date")).clear();
		driver.findElement(By.id("date")).sendKeys("10/19/2015");
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
		driver.findElement(By.id("axisOD")).sendKeys("50.1");
		driver.findElement(By.id("addOD")).clear();
		driver.findElement(By.id("addOD")).sendKeys("+1.00");
		driver.findElement(By.id("addOS")).clear();
		driver.findElement(By.id("addOS")).sendKeys("+1.00");
		driver.findElement(By.id("submit")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
		//Verify that our submission was rejected and we are still on the Add Ophthalmology
		assertTrue(driver.getPageSource().contains("AxisOD is required if a CylinderOD is given, and must be between 1 and 180 inclusive."));
		
		//fill in form and create
		driver.findElement(By.id("date")).clear();
		driver.findElement(By.id("date")).sendKeys("10/19/2015");
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
		assertTrue(driver.getPageSource().contains("Ophthalmology Office Visit successfully added"));
			
		//Verify that the action was logged
		assertLogged(TransactionType.CREATE_OPHTHALMOLOGY_OV, 9000000085L, 408L, "");
		
		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/19/2015"));
	}
	
	/**
	 * Tests what happens when a user puts in a cylinder, but no axis (axis is required if a cylinder is listed for that eye).
	 * @throws Exception
	 */
	public void testCylinderNoAxis() throws Exception{
		//Login as Brooke Tran
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000085", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000085L, 0L, "");
		
		//Click the Add Ophthalmology Office Visit link
		driver.findElement(By.linkText("Add Ophthalmology Office Visit")).click();
		
		//Select Freya Chandler as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("408");
		driver.findElement(By.xpath("//input[@value='408']")).submit();
		String title = driver.getTitle();
		assertEquals("iTrust - Add Ophthalmology Office Visit", title);
		
		//Enable javascript
		driver.setJavascriptEnabled(true);
		
		//fill in form and create
		driver.findElement(By.id("date")).clear();
		driver.findElement(By.id("date")).sendKeys("10/20/2015");
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
		driver.findElement(By.id("addOD")).clear();
		driver.findElement(By.id("addOD")).sendKeys("+1.00");
		driver.findElement(By.id("addOS")).clear();
		driver.findElement(By.id("addOS")).sendKeys("+1.00");
		driver.findElement(By.id("submit")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
		//Verify that our submission was rejected and we are still on the Add Ophthalmology
		assertTrue(driver.getPageSource().contains("AxisOD is required if a CylinderOD is given, and must be between 1 and 180 inclusive."));
		//Fill in the correct values and submit the form
		driver.findElement(By.id("date")).clear();
		driver.findElement(By.id("date")).sendKeys("10/20/2015");
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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //wait so that jenkins is okay
			
		//Verify that we are returned to the Ophthalmology Homepage
		title = driver.getTitle();
		assertEquals("iTrust - Ophthalmology", title);
		assertTrue(driver.getPageSource().contains("Ophthalmology Office Visit successfully added"));
			
		//Verify that the action was logged
		assertLogged(TransactionType.CREATE_OPHTHALMOLOGY_OV, 9000000085L, 408L, "");
		
		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/20/2015"));
	}
}