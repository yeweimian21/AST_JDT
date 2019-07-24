package edu.ncsu.csc.itrust.selenium;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Use Case 10
 */
public class PersonalHealthRecordsUseCaseTest extends iTrustSeleniumTest {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		driver = new Driver();
		baseUrl = "http://localhost:8080";
		driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
	}

	/**
	 * testEditPatient
	 * 
	 * @throws Exception
	 */
	public void testEditPatient() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Welcome, Kelly Doctor", driver);
		driver.findElement(By.linkText("PHR Information")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		assertTextPresent("Andy Programmer", driver);
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW,
				9000000000L, 2L, "");
	}

	/**
	 * testInvalidPatientDates
	 * 
	 * @throws Exception
	 */
	public void testInvalidPatientDates() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Welcome, Kelly Doctor", driver);
		driver.findElement(By.linkText("Patient Information")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		assertTextPresent("Patient Information", driver);
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 9000000000L, 2L, "");

		driver.findElement(By.name("dateOfDeathStr")).sendKeys("01/03/2050");
		driver.findElement(By.name("action")).click();
		assertTextPresent("future", driver);
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 9000000000L, 2L, "");
	}

	/**
	 * testInvalidPatientBirthDates
	 * 
	 * @throws Exception
	 */
	public void testInvalidPatientBirthDates() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Welcome, Kelly Doctor", driver);
		driver.findElement(By.linkText("Patient Information")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		assertTextPresent("Patient Information", driver);
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 9000000000L, 2L, "");

		driver.findElement(By.name("dateOfDeathStr")).sendKeys("");
		driver.findElement(By.name("dateOfBirthStr")).sendKeys("01/03/2050");
		driver.findElement(By.name("action")).click();
		assertTextPresent("future", driver);
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 9000000000L, 2L, "");
	}

	/**
	 * testRepresent
	 * 
	 * @throws Exception
	 */
	public void testRepresent() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Welcome, Kelly Doctor", driver);
		driver.findElement(By.linkText("PHR Information")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW,
				9000000000L, 2L, "");

		driver.findElement(By.linkText("Baby Programmer")).click();

		// Clicking on a representee's name takes you to their records
		assertTextPresent("Andy Programmer", driver);
		assertTextPresent("Diabetes with ketoacidosis", driver);
		assertTextPresent("Grandparent", driver);
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW,
				9000000000L, 5L, "");
		driver.findElement(By.linkText("Random Person")).click();
		assertTextPresent("nobody@gmail.com", driver);
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW,
				9000000000L, 1L, "");
	}

	/**
	 * testAllergy
	 * 
	 * @throws Exception
	 */
	public void testAllergy() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Welcome, Kelly Doctor", driver);
		driver.findElement(By.linkText("PHR Information")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW,
				9000000000L, 2L, "");

		// Add allergy
		driver.findElement(By.name("description")).sendKeys("081096");
		driver.findElement(By.name("addA")).click();
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_EDIT,
				9000000000L, 2L, "");
		assertTextPresent("Allergy Added", driver);
	}

	/**
	 * testAllergy2
	 * 
	 * @throws Exception
	 */
	public void testAllergy2() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Welcome, Kelly Doctor", driver);
		driver.findElement(By.linkText("PHR Information")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW,
				9000000000L, 2L, "");

		// Add allergy
		driver.findElement(By.name("description")).sendKeys("Penicillin");
		driver.findElement(By.name("addA")).click();
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_EDIT,
				9000000000L, 2L, "");
		assertTextPresent("664662530 - Penicilli", driver);
	}

	/**
	 * testEditSmokingStatus
	 * 
	 * @throws Exception
	 */
	public void testEditSmokingStatus() throws Exception {
		// Login
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Welcome, Kelly Doctor", driver);

		// Click Document Office Visit
		driver.findElement(By.linkText("Document Office Visit")).click();
		assertEquals(
				ADDRESS
						+ "auth/getPatientID.jsp?forward=/iTrust/auth/hcp-uap/documentOfficeVisit.jsp",
				driver.getCurrentUrl());

		// Choose patient 2
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp",
				driver.getCurrentUrl());

		// Click Yes, Document Office Visit
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// Verify Edit Office Visit page
		assertEquals("iTrust - Document Office Visit", driver.getTitle());

		// Click the create button
		driver.findElement(By.id("update")).click();

		// Verify "Information Successfully Updated" message
		assertTextPresent("Information Successfully Updated", driver);
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 2L, "");

		// Create Health Metrics Record
		driver.findElement(By.name("height")).clear();
		driver.findElement(By.name("height")).sendKeys("56");
		driver.findElement(By.name("weight")).clear();
		driver.findElement(By.name("weight")).sendKeys("111");
		new Select(driver.findElement(By.id("isSmoker")))
				.selectByVisibleText("2 - Current some day smoker");
		new Select(driver.findElement(By.id("householdSmokingStatus")))
				.selectByVisibleText("1 - non-smoking household");
		driver.findElement(By.name("bloodPressureN")).clear();
		driver.findElement(By.name("bloodPressureN")).sendKeys("999");
		driver.findElement(By.name("bloodPressureD")).clear();
		driver.findElement(By.name("bloodPressureD")).sendKeys("000");
		driver.findElement(By.name("cholesterolHDL")).clear();
		driver.findElement(By.name("cholesterolHDL")).sendKeys("50");
		driver.findElement(By.name("cholesterolLDL")).clear();
		driver.findElement(By.name("cholesterolLDL")).sendKeys("200");
		driver.findElement(By.name("cholesterolTri")).clear();
		driver.findElement(By.name("cholesterolTri")).sendKeys("200");
		
		driver.findElement(By.cssSelector("input#addHR")).click();

		// Verify "Health information successfully updated." message
		assertTextPresent("Health information successfully updated.", driver);
		assertLogged(TransactionType.CREATE_BASIC_HEALTH_METRICS, 9000000000L,
				2L, "");
		// Verify create health metrics log

		// Change the smoking status
		new Select(driver.findElement(By.id("isSmoker")))
				.selectByVisibleText("1 - Current every day smoker");
		new Select(driver.findElement(By.id("householdSmokingStatus")))
				.selectByVisibleText("1 - non-smoking household");
		driver.findElement(By.id("addHR")).click();

		// Verify "Health information successfully updated." message
		assertTextPresent("Health information successfully updated.", driver);
		// Verify edit health metrics log
		assertLogged(TransactionType.EDIT_BASIC_HEALTH_METRICS, 9000000000L,
				2L, "");
	}

	/**
	 * testAddAdditionalDemographics1
	 * 
	 * @throws Exception
	 */
	public void testAddAdditionalDemographics1() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Welcome, Kelly Doctor", driver);
		driver.findElement(By.linkText("Patient Information")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		assertTextPresent("Patient Information", driver);
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 9000000000L, 2L, "");

		driver.findElement(By.name("religion")).sendKeys("Jedi");
		driver.findElement(By.name("action")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 9000000000L, 2L, "");

		driver.findElement(By.linkText("PHR Information")).click();
		assertEquals("iTrust - Edit Personal Health Record", driver.getTitle());
		assertEquals(
				"Religion:",
				driver.findElement(
						By.xpath("//div[@id='iTrustContent']/div/div/table/tbody/tr[7]/td"))
						.getText());
		assertEquals(
				"Jedi",
				driver.findElement(
						By.xpath("//div[@id='iTrustContent']/div/div/table/tbody/tr[7]/td[2]"))
						.getText());
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW,
				9000000000L, 2L, "");
	}

	/**
	 * testAddAdditionDemographicss2
	 * 
	 * @throws Exception
	 */
	public void testAddAdditionalDemographics2() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Welcome, Kelly Doctor", driver);
		driver.findElement(By.linkText("Patient Information")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		assertTextPresent("Patient Information", driver);
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 9000000000L, 2L, "");

		driver.findElement(By.name("spiritualPractices")).sendKeys(
				"Sleeps in class");
		driver.findElement(By.name("action")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 9000000000L, 2L, "");

		driver.findElement(By.linkText("PHR Information")).click();
		assertEquals("iTrust - Edit Personal Health Record", driver.getTitle());
		assertEquals(
				"Spiritual Practices:",
				driver.findElement(
						By.xpath("//div[@id='iTrustContent']/div/div/table/tbody/tr[9]/td"))
						.getText());
		assertEquals(
				"Sleeps in class",
				driver.findElement(
						By.xpath("//div[@id='iTrustContent']/div/div/table/tbody/tr[9]/td[2]"))
						.getText());
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW,
				9000000000L, 2L, "");
	}

	/**
	 * testAddAdditionDemographicss3
	 * 
	 * @throws Exception
	 */
	public void testAddAdditionalDemographics3() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Welcome, Kelly Doctor", driver);
		driver.findElement(By.linkText("Patient Information")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		assertTextPresent("Patient Information", driver);
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 9000000000L, 2L, "");

		driver.findElement(By.name("alternateName")).sendKeys("Randy");
		driver.findElement(By.name("action")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 9000000000L, 2L, "");

		driver.findElement(By.linkText("PHR Information")).click();
		assertEquals("iTrust - Edit Personal Health Record", driver.getTitle());
		assertEquals(
				"Alternate Name:",
				driver.findElement(
						By.xpath("//div[@id='iTrustContent']/div/div/table/tbody/tr[10]/td"))
						.getText());
		assertEquals(
				"Randy",
				driver.findElement(
						By.xpath("//div[@id='iTrustContent']/div/div/table/tbody/tr[10]/td[2]"))
						.getText());
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW,
				9000000000L, 2L, "");
	}

	/**
	 * testAddDupAllergy
	 * 
	 * @throws Exception
	 */
	public void testAddDupAllergy() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Welcome, Kelly Doctor", driver);
		driver.findElement(By.linkText("PHR Information")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("25");
		driver.findElement(By.xpath("//input[@value='25']")).submit();
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW,
				9000000000L, 25L, "");

		// Selects Patient Trend Setter
		assertEquals(ADDRESS + "auth/hcp-uap/editPHR.jsp",
				driver.getCurrentUrl());

		driver.findElement(By.name("description")).sendKeys("Penicillin");
		driver.findElement(By.name("addA")).click();
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_EDIT,
				9000000000L, 25L, "");
		assertTextPresent("Allergy Added", driver);

		driver.findElement(By.name("description")).sendKeys("Penicillin");
		driver.findElement(By.name("addA")).click();
		// Add Penicillin Allergy again
		assertTextPresent(
				"Allergy 664662530 - Penicillin has already been added for Trend Setter.",
				driver);
		// This is the error that should appear when this allergy is added a
		// second time.
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_EDIT,
				9000000000L, 25L, "");
	}

	/**
	 * testAddAllergyPrevRX
	 * 
	 * @throws Exception
	 */
	public void testAddAllergyPrevRX() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Welcome, Kelly Doctor", driver);
		driver.findElement(By.linkText("Document Office Visit")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("25");
		driver.findElement(By.xpath("//input[@value='25']")).submit();
		// Selects Patient Trend Setter
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp",
				driver.getCurrentUrl());

		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// Verify Edit Office Visit page
		assertEquals("iTrust - Document Office Visit", driver.getTitle());

		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("01/01/2012");
		driver.findElement(By.name("notes")).sendKeys("just some more notes");
		driver.findElement(By.cssSelector("input#update")).click();
		// Create new OV on date 01/01/2012

		assertTextPresent("Information Successfully Updated", driver);
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 25L,
				"Office visit");

		new Select(driver.findElement(By.id("medID")))
				.selectByVisibleText("664662530 - Penicillin");
		driver.findElement(By.name("dosage")).sendKeys("60");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("01/01/2012");
		driver.findElement(By.name("endDate")).clear();
		driver.findElement(By.name("endDate")).sendKeys("01/31/2012");
		driver.findElement(By.name("instructions")).clear();
		driver.findElement(By.name("instructions")).sendKeys(
				"Take three times daily with food.");

		driver.findElement(By.id("addprescription")).click();
		// Add Penicillin RX, 60mg, 01/01/2012 - 01/31/2012, thrice daily w/
		// food
		assertTextPresent("Prescription information successfully updated.",
				driver);
		assertLogged(TransactionType.OFFICE_VISIT_EDIT, 9000000000L, 25L, "");

		driver.findElement(By.linkText("PHR Information")).click(); // Clicks
																	// PHR Info
		assertEquals("iTrust - Edit Personal Health Record", driver.getTitle());

		driver.findElement(By.name("description")).sendKeys("Penicillin");
		driver.findElement(By.name("addA")).click();
		// Add Penicillin Allergy (will be firstFound on today's date)
		assertTextPresent("Allergy Added", driver);
		// No error should appear when this allergy is added.
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_EDIT,
				9000000000L, 25L, "");
	}

	/**
	 * testAddAllergyFutRX
	 * 
	 * @throws Exception
	 */
	public void testAddAllergyFutRX() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Welcome, Kelly Doctor", driver);
		driver.findElement(By.linkText("All Patients")).click();
		assertEquals("iTrust - View All Patients", driver.getTitle());
		assertLogged(TransactionType.PATIENT_LIST_VIEW, 9000000000L, 0L, "");

		driver.findElement(By.linkText("Anakin Skywalker")).click();
		// Select Anakin Skywalker, this seemed to be easier than what the
		// acceptance tests did.
		assertEquals("iTrust - Edit Personal Health Record", driver.getTitle());
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW,
				9000000000L, 100L, "");

		/*
		 * Since we don't worry about preconditions and it takes us straight to
		 * the correct page anyway, we can skip a lot of stuff here.
		 */
		driver.findElement(By.name("description")).sendKeys("Midichlominene");
		driver.findElement(By.name("addA")).click();
		// Add M-minene Allergy (will be firstFound on today's date)
		assertTextPresent(
				"Medication 483012382 - Midichlominene is currently prescribed to Anakin Skywalker.",
				driver);
		// This is the error that should appear when this allergy is added.
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_EDIT,
				9000000000L, 100L, "");
	}

	/**
	 * testAddAllergyExistRX
	 * 
	 * @throws Exception
	 */
	public void testAddAllergyExistRX() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Welcome, Kelly Doctor", driver);
		driver.findElement(By.linkText("Document Office Visit")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("25");
		driver.findElement(By.xpath("//input[@value='25']")).submit();
		// Selects Patient Trend Setter
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp",
				driver.getCurrentUrl());

		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// Verify Edit Office Visit page
		assertEquals("iTrust - Document Office Visit", driver.getTitle());

		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("02/01/2012");
		driver.findElement(By.name("notes")).sendKeys("just some more notes");
		driver.findElement(By.id("update")).click();
		// Create new OV on date 02/01/2012
		assertTextPresent("Information Successfully Updated", driver);
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 25L,
				"Office visit");

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		cal.add(Calendar.DAY_OF_YEAR, 7);

		new Select(driver.findElement(By.id("medID")))
				.selectByVisibleText("00882219 - Lantus");
		driver.findElement(By.name("dosage")).sendKeys("100");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("02/01/2012");
		driver.findElement(By.name("endDate")).clear();
		driver.findElement(By.name("endDate")).sendKeys(
				format.format(cal.getTime()));
		driver.findElement(By.name("instructions")).clear();
		driver.findElement(By.name("instructions")).sendKeys("Take once daily");

		driver.findElement(By.id("addprescription")).click();
		// Add Lantus RX, 100mg, 02/01/2012 - 08/01/2012, once daily
		assertTextPresent("Prescription information successfully updated.",
				driver);
		assertLogged(TransactionType.OFFICE_VISIT_EDIT, 9000000000L, 25L, "");

		driver.findElement(By.linkText("PHR Information")).click(); // Clicks
																	// PHR Info
		assertEquals("iTrust - Edit Personal Health Record", driver.getTitle());

		driver.findElement(By.name("description")).sendKeys("Lantus");
		driver.findElement(By.name("addA")).click();
		// Add Lantus Allergy (will be firstFound on today's date)
		assertTextPresent(
				"Medication 00882219 - Lantus is currently prescribed to Trend Setter.",
				driver);
		// This is the error that should appear when this allergy is added.
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_EDIT,
				9000000000L, 25L, "");

	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
	
	/**
     * Asserts that the text is on the page
     * @param text
     * @param driver
     */
    public void assertTextPresent(String text, WebDriver driver2) {
        List<WebElement> list = driver2.findElements(By
                .xpath("//*[contains(body, \"" + text + "\")]"));
        assertTrue("Text not found!", list.size() > 0);
    }

    /**
     * Asserts that the text is not on the page. Does not pause for text to appear.
     * @param text
     * @param driver
     */
    public void assertTextNotPresent(String text, WebDriver driver2) {
        assertFalse("Text should not be found!",
                driver2.findElement(By.cssSelector("BODY")).getText().contains(text));
    }
}