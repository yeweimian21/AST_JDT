package edu.ncsu.csc.itrust.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class DependentsTest extends iTrustSeleniumTest {

	public static final String ADDRESS = "http://localhost:8080/iTrust/auth/hcp-uap/addPatient.jsp";

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}


	/**
	 * Tests adding a dependent / representative relationship to existing
	 * patients
	 * 
	 * @throws Exception
	 */
	public void testEditDependentRepresentative() throws Exception {
		// Log in.
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

		// Click on Representatives.
		driver.findElement(By.linkText("Representatives")).click();

		// search for patient 103 by MID.
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("103");
		// the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='103']")).submit();

		assertTrue(driver.getTitle().equals("iTrust - Manage Representatives"));

		// Add Caldwell Hudson as a representative
		WebElement elem = driver.findElement(By.name("UID_repID"));
		elem.sendKeys("102");
		elem.submit();

		// Make sure the text displays.
		List<WebElement> list = driver.findElements(By
				.xpath("//*[contains(text(),'" + "Caldwell Hudson" + "')]"));
		assertTrue("Text not found!", list.size() > 0);
	}

	/**
	 * Tests that a dependent cannot login
	 * 
	 * @throws Exception
	 */
	public void testDependentLogin() throws Exception {
		// Load UC58 data
		gen.uc58();

		// Try to log in (dependents can't).
		WebDriver driver = new HtmlUnitDriver();
		driver.get("http://localhost:8080/iTrust/");

		WebElement elem = driver.findElement(By.name("j_username"));
		elem.sendKeys("580");
		driver.findElement(By.name("j_password"));
		elem.sendKeys("pw");
		elem.submit();

		// Make sure we didn't go anywhere.
		List<WebElement> list = driver.findElements(By
				.xpath("//*[contains(text(),'" + "iTrust - Login" + "')]"));
		assertTrue("Text not found!", list.size() > 0);
	}

	/**
	 * Tests that a list of a depedent's representatives is displayed to them
	 * 
	 * @throws Exception
	 */
	public void testListRepresentatives() throws Exception {
		// Load UC58 data
		gen.uc58();

		// Log in.
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

		// Click on Representatives.
		driver.findElement(By.linkText("Representatives")).click();

		// search for patient 103 by MID.
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("581");
		// the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='581']")).submit();

		assertTrue(driver.getTitle().equals("iTrust - Manage Representatives"));

		// Add Caldwell Hudson as a representative
		WebElement elem = driver.findElement(By.name("UID_repID"));
		elem.sendKeys("102");
		elem.submit();

		// Make sure the text displays.
		List<WebElement> list = driver.findElements(By
				.xpath("//*[contains(text(),'" + "Bob Marley" + "')]"));
		assertTrue("Text not found!", list.size() > 0);
	}

	/**
	 * Tests to make sure representatives can't be dependents themselves
	 * 
	 * @throws Exception
	 */
	public void testRepresentativeNotDependent() throws Exception {
		// Load UC58 data
		gen.uc58();

		// Log in.
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

		// Click on Representatives.
		driver.findElement(By.linkText("Representatives")).click();

		// search for patient 103 by MID.
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("580");
		// the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='580']")).submit();

		assertTrue(driver.getTitle().equals("iTrust - Manage Representatives"));

		// Make sure the text displays.
		List<WebElement> list = driver.findElements(By
				.xpath("//*[contains(text(),'" + "Bob Marley is a dependent."
						+ "')]"));
		assertTrue("Text not found!", list.size() > 0);
		list = driver.findElements(By.xpath("//*[contains(text(),'"
				+ "Dependent users cannot represent others." + "')]"));
		assertTrue("Text not found!", list.size() > 0);
	}

	public void testRequestRecordsForDependent() throws Exception {
		// Load UC59 data
		gen.uc59();

		// Log in.
		WebDriver driver = new HtmlUnitDriver();
		driver = login("750", "pw");
		assertLogged(TransactionType.HOME_VIEW, 750L, 0L, "");

		// Click on Representatives.
		driver.findElement(By.linkText("Request Records Release")).click();
		WebElement elem = driver.findElement(By.name("selectedPatient"));
		Select s = new Select(elem);
		s.selectByIndex(1);
		elem.submit();

		// Make sure the text displays.
		List<WebElement> list = driver.findElements(By
				.xpath("//*[contains(text(),'" + "Billy Ross" + "')]"));
		assertTrue("Text not found!", list.size() > 0);

		driver.findElement(By.id("submitReq")).click();
		assertTrue(driver.getTitle().equals("iTrust - Records Release Request"));

		// Fill in medical records release form
		s = new Select(driver.findElement(By.name("releaseHospital")));
		s.selectByIndex(1);
		elem = driver.findElement(By.name("recFirstName"));
		elem.sendKeys("Benedict");
		elem = driver.findElement(By.name("recLastName"));
		elem.sendKeys("Cucumberpatch");
		elem = driver.findElement(By.name("recPhone"));
		elem.sendKeys("555-666-7777");
		elem = driver.findElement(By.name("recEmail"));
		elem.sendKeys("a@b.com");
		elem = driver.findElement(By.name("recHospitalName"));
		elem.sendKeys("Rex Hospital");
		elem = driver.findElement(By.name("recHospitalAddress1"));
		elem.sendKeys("123 Broad St.");
		elem = driver.findElement(By.name("recHospitalAddress2"));
		elem.sendKeys(" ");
		elem = driver.findElement(By.name("recHospitalCity"));
		elem.sendKeys("Cary");
		elem = driver.findElement(By.name("recHospitalState"));
		elem.sendKeys("NC");
		elem = driver.findElement(By.name("recHospitalZip"));
		elem.sendKeys("27164");
		elem = driver.findElement(By.name("releaseJustification"));
		elem.sendKeys("Moving");
		elem = driver.findElement(By.name("verifyForm"));
		elem.click();
		elem = driver.findElement(By.name("digitalSig"));
		elem.sendKeys("Bob Ross");
		driver.findElement(By.id("submit")).click();

		list = driver.findElements(By.xpath("//*[contains(text(),'"
				+ "Request successfully sent" + "')]"));
		assertTrue("Text not found!", list.size() > 0);
	}

	public void testRequestRecordsWithDependentSignature() throws Exception {
		// Load UC59 data
		gen.uc59();

		// Log in.
		WebDriver driver = new HtmlUnitDriver();
		driver = login("750", "pw");
		assertLogged(TransactionType.HOME_VIEW, 750L, 0L, "");

		// Click on Representatives.
		driver.findElement(By.linkText("Request Records Release")).click();
		WebElement elem = driver.findElement(By.name("selectedPatient"));
		Select s = new Select(elem);
		s.selectByIndex(1);
		elem.submit();

		// Make sure the text displays.
		List<WebElement> list = driver.findElements(By
				.xpath("//*[contains(text(),'" + "Billy Ross" + "')]"));
		assertTrue("Text not found!", list.size() > 0);

		driver.findElement(By.id("submitReq")).click();
		assertTrue(driver.getTitle().equals("iTrust - Records Release Request"));

		// Fill in medical records release form
		s = new Select(driver.findElement(By.name("releaseHospital")));
		s.selectByIndex(1);
		elem = driver.findElement(By.name("recFirstName"));
		elem.sendKeys("Benedict");
		elem = driver.findElement(By.name("recLastName"));
		elem.sendKeys("Cucumberpatch");
		elem = driver.findElement(By.name("recPhone"));
		elem.sendKeys("555-666-7777");
		elem = driver.findElement(By.name("recEmail"));
		elem.sendKeys("a@b.com");
		elem = driver.findElement(By.name("recHospitalName"));
		elem.sendKeys("Rex Hospital");
		elem = driver.findElement(By.name("recHospitalAddress1"));
		elem.sendKeys("123 Broad St.");
		elem = driver.findElement(By.name("recHospitalAddress2"));
		elem.sendKeys(" ");
		elem = driver.findElement(By.name("recHospitalCity"));
		elem.sendKeys("Cary");
		elem = driver.findElement(By.name("recHospitalState"));
		elem.sendKeys("NC");
		elem = driver.findElement(By.name("recHospitalZip"));
		elem.sendKeys("27164");
		elem = driver.findElement(By.name("releaseJustification"));
		elem.sendKeys("Moving");
		elem = driver.findElement(By.name("verifyForm"));
		elem.click();
		elem = driver.findElement(By.name("digitalSig"));
		elem.sendKeys("Billy Ross");
		driver.findElement(By.id("submit")).click();

		list = driver.findElements(By.xpath("//*[contains(text(),'" + "Error"
				+ "')]"));
		assertTrue("Text not found!", list.size() > 0);
	}

	public void testRequestRecordsForNotRepresentedDependent() throws Exception {
		// Load UC59 data
		gen.uc59();

		// Log in.
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

		// Click on Representatives.
		driver.findElement(By.linkText("Representatives")).click();

		// search for patient 103 by MID.
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("750");
		// the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='750']")).submit();

		assertTrue(driver.getTitle().equals("iTrust - Manage Representatives"));

		// Remove the representative.
		driver.findElement(By.partialLinkText("Remove")).click();
		driver.findElement(By.xpath("//a[text()='Logout']")).click();

		// Log in as Bob Ross.
		driver = login("750", "pw");
		assertLogged(TransactionType.HOME_VIEW, 750L, 0L, "");

		// Click on Add Patient.
		driver.findElement(By.linkText("Request Records Release")).click();

		// Try to select Billy.
		try {
			// Should fail.
			WebElement elem = driver.findElement(By.name("selectedPatient"));
			Select s = new Select(elem);
			s.selectByIndex(2); // Billy.
			elem.submit();
			fail();
		} catch (Exception e) {
			assertEquals("iTrust - Records Release Request History",
					driver.getTitle());
		}
	}

	public void testViewRequestedRecordsForDependent() throws Exception {
		// Load UC59 data
		gen.uc59();

		// Log in.
		WebDriver driver = new HtmlUnitDriver();
		driver = login("750", "pw");
		assertLogged(TransactionType.HOME_VIEW, 750L, 0L, "");

		// Click on Add Patient.
		driver.findElement(By.linkText("Request Records Release")).click();
		WebElement elem = driver.findElement(By.name("selectedPatient"));
		Select s = new Select(elem);
		s.selectByIndex(0);
		elem.submit();

		driver.findElement(By.partialLinkText("View")).click();

		assertEquals("iTrust - View My Records", driver.getTitle());
	}

	public void testApproveRecordsRequestForDependent() throws Exception {
		// Load UC59 data
		gen.uc59();

		// Log in.
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

		// Click on Records Release Requests.
		driver.findElement(By.linkText("Records Release Requests")).click();

		driver.findElement(By.linkText("View")).click();

		assertEquals("iTrust - Records Release Request", driver.getTitle());

		driver.findElement(By.id("Approve")).click();

		assertEquals("iTrust - Records Release Request", driver.getTitle());

		List<WebElement> list = driver.findElements(By
				.xpath("//*[contains(text(),'" + "Approved" + "')]"));
		assertTrue("Text not found!", list.size() > 0);
	}
}
