package edu.ncsu.csc.itrust.selenium;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Tests potential prescriptions renewals
 */
public class PatientsWithExpiringPrescriptionsTest extends iTrustSeleniumTest {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.icd9cmCodes();
		gen.ndCodes();
		gen.hospitals();
		gen.hcp1();
		gen.hcp2();
		gen.hcp3();
		gen.patient9();
		gen.patient10();
		gen.patient11();
		gen.patient12();
		gen.patient13();
		gen.patient14();
		
		driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/auth/forwardUser.jsp";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/*
	 * An equivalence class test for a patient who should appear on the list.
	 * (Designated Tester Arehart, special-diagnosis-history, prescription expires in 5 days)
	 * The prescriptions were NOT made on the same visit as a special-diagnosis.
	 */
	public void testPatient9() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9900000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.linkText("Potential Prescription-Renewals")).click();

		assertTextPresent("Tester Arehart", driver);
		assertTextNotPresent("9900000000", driver);
		assertTextPresent("Darryl", driver);
		assertTextPresent("Thompson", driver);
		assertTextPresent("a@b.com", driver);
		assertTextPresent("919-555-6709", driver);
		assertLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 99L, "");
	}
	
	/*
	 * An equivalence class test for a patient who should NOT appear on the list.
	 * (Designated Tester Arehart, special-diagnosis-history, prescription expires in 10 days)
	 */
	public void testPatientTen() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9900000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.linkText("Potential Prescription-Renewals")).click();

		assertTextPresent("Tester Arehart", driver);
		assertTextNotPresent("Zappic Clith", driver);
		assertNotLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 10L, "");
	}

	/*
	 * A boundary-value test for a patient who should appear on the list.
	 * (Designated Tester Arehart, special-diagnosis-history, prescription expires in 7 days)
	 * Diagnosed with 493.99
	 */
	public void testPatientEleven() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9900000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.linkText("Potential Prescription-Renewals")).click();

		assertTextPresent("Tester Arehart", driver);
		assertTextPresent("Marie", driver);
		assertTextPresent("Thompson", driver);
		assertTextPresent("e@f.com", driver);
		assertTextPresent("919-555-9213", driver);
		assertLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 11L, "");
	}
	
	/*
	 * A boundary-value test for a patient who should NOT appear on the list.
	 * (Designated Tester Arehart, special-diagnosis-history, prescription expires in 8 days)
	 */
	public void testPatientTwelve() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9900000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.linkText("Potential Prescription-Renewals")).click();
		
		assertTextPresent("Tester Arehart", driver);
		assertTextNotPresent("9900000000", driver);
		assertTextNotPresent("Blammo", driver);
		assertTextNotPresent("Volcano", driver);
		assertNotLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 12L, "");
	}
	
	/*
	 * An equivalence class test for a patient who should NOT appear on the list.
	 * (Designated Tester Arehart, NOT special-diagnosis-history, prescription expires in 5 days)
	 */
	public void testPatientThirteen() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9900000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.linkText("Potential Prescription-Renewals")).click();
		
		assertTextPresent("Tester Arehart", driver);
		assertTextNotPresent("9900000000", driver);
		assertTextNotPresent("Blim Cildron", driver);
		assertNotLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 13L, "");
	}
	
	/*
	 * A boundary-value test for a patient who should appear on the list.
	 * (Designated Tester Arehart, special-diagnosis-history, prescription expires today)
	 * Diagnosed with 459.99 (This is the closest possible to 460 because the table uses
	 *  decimal(5,2) )
	 */
	public void testPatientFourteen() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9900000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.linkText("Potential Prescription-Renewals")).click();
		
		assertTextPresent("Tester Arehart", driver);
		assertTextNotPresent("9900000000", driver);
		assertTextPresent("Zack", driver);
		assertTextPresent("Arthur", driver);
		assertTextPresent("k@l.com", driver);
		assertTextPresent("919-555-1234", driver);	
		assertLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 14L, "");
	}
	
	/*
	 * A boundary-value test for a patient who should NOT appear on the list.
	 * (Designated Tester Arehart, special-diagnosis-history, prescription expired yesterday)
	 */
	public void testPatientFifteen() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9900000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.linkText("Potential Prescription-Renewals")).click();
		
		assertTextPresent("Tester Arehart", driver);
		assertTextNotPresent("9900000000", driver);
		assertTextNotPresent("Malk", driver);
		assertTextNotPresent("Flober", driver);
		assertNotLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 15L, "");
	}
	
	/*
	 * A boundary-value test for a patient who should appear on the list.
	 * (Designated Tester Arehart, special-diagnosis-history, prescription expires today)
	 */
	public void testPatientOrdering() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9900000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.linkText("Potential Prescription-Renewals")).click();

		assertEquals("Zack Arthur", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td")).getText());
		assertEquals("Darryl Thompson", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[4]/td")).getText());
		assertEquals("Marie Thompson", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[5]/td")).getText());

		assertTextPresent("Tester Arehart", driver);
		assertTextNotPresent("9900000000", driver);
		
		assertLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 99L, "");
		assertLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 11L, "");
		assertLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 14L, "");
	}
	
	/*
	 * A boundary-value test for a patient who should appear on the list.
	 * (Designated Tester Gandalf Stormcrow)
	 */
	public void testAcceptance() throws Exception {
		gen.UC32Acceptance();

		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000003");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.linkText("Potential Prescription-Renewals")).click();

		assertEquals("Andy Koopa", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td")).getText());
		assertEquals("David Prince", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[4]/td")).getText());
		
		assertTextPresent("Gandalf Stormcrow", driver);
		assertTextNotPresent("9000000003", driver);
		assertLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9000000003L, 16L, "");
		assertLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9000000003L, 17L, "");
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