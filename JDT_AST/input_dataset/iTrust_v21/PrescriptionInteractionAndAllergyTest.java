package edu.ncsu.csc.itrust.selenium;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class PrescriptionInteractionAndAllergyTest extends iTrustSeleniumTest {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		super.setUp();
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);
		gen.clearAllTables();
		gen.hcp0();
		gen.standardData();
		driver = new HtmlUnitDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test
	public void testNoAllergyPrescribe() throws Exception {
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// driver.findElement(By.linkText("HCP 1")).click();

		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
		driver.findElement(By.linkText("Document Office Visit")).click();

		driver.findElement(By.name("UID_PATIENTID")).sendKeys("25");
		driver.findElement(By.xpath("//input[@value='25']")).submit();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		driver.findElement(By.id("update")).click();

		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 25L, "");

		new Select(driver.findElement(By.id("medID")))
				.selectByVisibleText("01864020 - Nexium");
		driver.findElement(By.id("dosage")).clear();
		driver.findElement(By.id("dosage")).sendKeys("10");
		driver.findElement(By.id("startDate")).clear();
		driver.findElement(By.id("startDate")).sendKeys("03/01/2015");
		driver.findElement(By.id("instructions")).clear();
		driver.findElement(By.id("instructions")).sendKeys(
				"Take very frequently");
		driver.findElement(By.id("addprescription")).click();
		assertTrue(driver.getPageSource().contains("Nexium"));

	}

	@Test
	public void testAllergicPrescribe() throws Exception {
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// driver.findElement(By.linkText("HCP 1")).click();

		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
		driver.findElement(By.linkText("Document Office Visit")).click();

		driver.findElement(By.name("UID_PATIENTID")).sendKeys("25");
		driver.findElement(By.xpath("//input[@value='25']")).submit();

		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.id("update")).click();

		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 25L, "");

		new Select(driver.findElement(By.id("medID")))
				.selectByVisibleText("664662530 - Penicillin");
		driver.findElement(By.id("dosage")).clear();
		driver.findElement(By.id("dosage")).sendKeys("60");
		driver.findElement(By.id("startDate")).clear();

		driver.findElement(By.id("startDate")).sendKeys("02/26/2015");
		driver.findElement(By.id("endDate")).clear();
		driver.findElement(By.id("endDate")).sendKeys("02/26/2028");

		driver.findElement(By.id("instructions")).clear();
		driver.findElement(By.id("instructions")).sendKeys("Take frequently");
		driver.findElement(By.id("addprescription")).click();

		assertTrue(driver.getPageSource().contains("Penicillin"));

	}

	@Test
	public void testPrescribeOverrideCancel() throws Exception {
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// driver.findElement(By.linkText("HCP 1")).click();

		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
		driver.findElement(By.linkText("Document Office Visit")).click();

		driver.findElement(By.name("UID_PATIENTID")).sendKeys("100");
		driver.findElement(By.xpath("//input[@value='100']")).submit();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.id("update")).click();
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 100L,
				"Office visit");

		new Select(driver.findElement(By.id("medID")))
				.selectByVisibleText("00882219 - Lantus");
		driver.findElement(By.id("dosage")).clear();
		driver.findElement(By.id("dosage")).sendKeys("60");
		driver.findElement(By.id("startDate")).clear();
		driver.findElement(By.id("startDate")).sendKeys("02/26/2015");
		driver.findElement(By.id("endDate")).clear();
		driver.findElement(By.id("endDate")).sendKeys("02/26/2028");
		driver.findElement(By.id("instructions")).clear();
		driver.findElement(By.id("instructions")).sendKeys("Take frequently");
		driver.findElement(By.id("addprescription")).click();

		// Make sure this didn't get prescribed
		assertFalse(driver.getPageSource().contains("Lantus 02/26/2015"));

	}

	@Test
	public void testAllergicPrescribeTwice() throws Exception {

		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// driver.findElement(By.linkText("HCP 1")).click();

		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
		driver.findElement(By.linkText("Document Office Visit")).click();

		driver.findElement(By.name("UID_PATIENTID")).sendKeys("100");
		driver.findElement(By.xpath("//input[@value='100']")).submit();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		driver.findElement(By.id("update")).click();

		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 100L, "");

		new Select(driver.findElement(By.id("medID")))
				.selectByVisibleText("00882219 - Lantus");
		driver.findElement(By.id("dosage")).clear();
		driver.findElement(By.id("dosage")).sendKeys("60");
		driver.findElement(By.id("startDate")).clear();
		driver.findElement(By.id("startDate")).sendKeys("02/26/2015");
		driver.findElement(By.id("endDate")).clear();
		driver.findElement(By.id("endDate")).sendKeys("02/26/2028");
		driver.findElement(By.id("instructions")).clear();
		driver.findElement(By.id("instructions")).sendKeys("Take frequently");
		driver.findElement(By.id("addprescription")).click();
		// Second assigning
		new Select(driver.findElement(By.id("medID")))
				.selectByVisibleText("00882219 - Lantus");
		driver.findElement(By.id("dosage")).clear();
		driver.findElement(By.id("dosage")).sendKeys("60");
		driver.findElement(By.id("startDate")).clear();
		driver.findElement(By.id("startDate")).sendKeys("02/26/2015");
		driver.findElement(By.id("endDate")).clear();
		driver.findElement(By.id("endDate")).sendKeys("02/26/2028");
		driver.findElement(By.id("instructions")).clear();
		driver.findElement(By.id("instructions")).sendKeys("Take frequently");
		driver.findElement(By.id("addprescription")).click();

		assertTrue(driver.getPageSource().contains("Lantus"));

	}

	@Test
	public void testInteractionAndAllergyPrescribe() throws Exception {

		// From old test.
		gen.patient2();
		gen.officeVisit4();
		gen.ndCodes1();
		gen.drugInteractions3();
		gen.ORCodes();

		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// driver.findElement(By.linkText("HCP 1")).click();

		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
		driver.findElement(By.linkText("Document Office Visit")).click();

		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		driver.findElement(By.id("update")).click();
		// assertLogged(TransactionType.OFFICE_VISIT_VIEW, 9000000000L, 2L,
		// "Office visit");

		new Select(driver.findElement(By.id("medID")))
				.selectByVisibleText("619580501 - Adefovir");
		driver.findElement(By.id("dosage")).clear();
		driver.findElement(By.id("dosage")).sendKeys("600");
		driver.findElement(By.id("startDate")).clear();
		driver.findElement(By.id("startDate")).sendKeys("02/26/2015");
		driver.findElement(By.id("endDate")).clear();
		driver.findElement(By.id("endDate")).sendKeys("02/26/2025");

		driver.findElement(By.id("instructions")).clear();
		driver.findElement(By.id("instructions")).sendKeys(
				"Take every 0.01 minutes or with high fiber meals.");
		driver.findElement(By.id("addprescription")).click();

		new Select(driver.findElement(By.id("medID")))
				.selectByVisibleText("081096 - Aspirin");
		driver.findElement(By.id("dosage")).clear();
		driver.findElement(By.id("dosage")).sendKeys("1000");
		driver.findElement(By.id("startDate")).clear();
		driver.findElement(By.id("startDate")).sendKeys("03/01/2015");
		driver.findElement(By.id("instructions")).clear();
		driver.findElement(By.id("instructions")).sendKeys(
				"Take very frequently");
		driver.findElement(By.id("addprescription")).click();

		System.out.println("TITLE   : " + driver.getTitle());

		assertTrue(driver.getPageSource().contains("Aspirin"));
		assertTrue(driver.getPageSource().contains("Adefovir"));

	}

	@Test
	public void testAllergicPrescribeOverride() throws Exception {
		// From old test
		gen.patient2();
		gen.officeVisit4();
		gen.ndCodes1();
		gen.drugInteractions3();
		gen.ORCodes();

		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// driver.findElement(By.linkText("HCP 1")).click();

		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
		driver.findElement(By.linkText("Document Office Visit")).click();

		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		driver.findElement(By.id("update")).click();

		// assertLogged(TransactionType.OFFICE_VISIT_VIEW, 9000000000L, 2L,
		// "");

		new Select(driver.findElement(By.id("medID")))
				.selectByVisibleText("619580501 - Adefovir");
		driver.findElement(By.id("dosage")).clear();
		driver.findElement(By.id("dosage")).sendKeys("600");
		driver.findElement(By.id("startDate")).clear();
		driver.findElement(By.id("startDate")).sendKeys("02/26/2015");
		driver.findElement(By.id("endDate")).clear();
		driver.findElement(By.id("endDate")).sendKeys("02/26/2025");

		driver.findElement(By.id("instructions")).clear();
		driver.findElement(By.id("instructions")).sendKeys(
				"Take every 0.01 minutes or with high fiber meals.");
		driver.findElement(By.id("addprescription")).click();

		assertFalse(driver.getPageSource().contains("Adefovir 02/26/2015"));

	}

	@Test
	public void testInteractionCancel() throws Exception {
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// driver.findElement(By.linkText("HCP 1")).click();

		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
		driver.findElement(By.linkText("Document Office Visit")).click();

		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		driver.findElement(By.xpath("//input[@value='1']")).submit();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		driver.findElement(By.id("update")).click();
		new Select(driver.findElement(By.id("medID")))
				.selectByVisibleText("619580501 - Adefovir");
		driver.findElement(By.id("dosage")).clear();
		driver.findElement(By.id("dosage")).sendKeys("600");
		driver.findElement(By.id("startDate")).clear();
		driver.findElement(By.id("startDate")).sendKeys("02/26/2015");
		driver.findElement(By.id("endDate")).clear();
		driver.findElement(By.id("endDate")).sendKeys("02/26/2025");

		assertFalse(driver.getPageSource().contains("Adefovir 02/26/2015"));

	}

	@Test
	public void testInteractionOverride() throws Exception {
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// driver.findElement(By.linkText("HCP 1")).click();

		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
		driver.findElement(By.linkText("Document Office Visit")).click();

		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		driver.findElement(By.xpath("//input[@value='1']")).submit();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		driver.findElement(By.id("update")).click();
		new Select(driver.findElement(By.id("medID")))
				.selectByVisibleText("619580501 - Adefovir");
		driver.findElement(By.id("dosage")).clear();
		driver.findElement(By.id("dosage")).sendKeys("600");
		driver.findElement(By.id("startDate")).clear();
		driver.findElement(By.id("startDate")).sendKeys("02/26/2015");
		driver.findElement(By.id("endDate")).clear();
		driver.findElement(By.id("endDate")).sendKeys("02/26/2025");

		assertTrue(driver.getPageSource().contains("Adefovir"));
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