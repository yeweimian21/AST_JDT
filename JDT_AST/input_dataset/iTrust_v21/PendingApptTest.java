package edu.ncsu.csc.itrust.selenium;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Use Case 42
 */
public class PendingApptTest extends iTrustSeleniumTest {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.pendingAppointmentAlert();
		driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void testPendingAppointmentAlert() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Welcome, Kelly Doctor", driver);
		assertTextPresent("Appointment requests.", driver);
	}

	public void testAcceptAnAppointment() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.pendingAppointmentAlert();

		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Appointment requests.", driver);

		driver.findElement(By.linkText("Appointment Requests")).click();
		driver.findElement(By.linkText("Approve")).click();
		driver.get(baseUrl + "/iTrust/auth/hcp/home.jsp");
		assertTextPresent("No appointment requests.", driver);
	}

	public void testConflictingAppt() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.pendingAppointmentConflict();

		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTrue(driver.findElements(By.linkText("2")).size() > 0);

		driver.findElement(By.linkText("Appointment Requests")).click();
		driver.findElement(By.linkText("Approve")).click();
		driver.get(baseUrl + "/iTrust/auth/hcp/home.jsp");
		assertTrue(driver.findElements(By.linkText("2")).size() == 0);
		assertTrue(driver.findElements(By.linkText("1")).size() > 0);
	}

	public void testDeclineAnAppointment() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.pendingAppointmentAlert();

		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Appointment requests.", driver);

		driver.findElement(By.linkText("Appointment Requests")).click();
		driver.findElement(By.linkText("Reject")).click();
		driver.get(baseUrl + "/iTrust/auth/hcp/home.jsp");
		assertTextPresent("No appointment requests.", driver);
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