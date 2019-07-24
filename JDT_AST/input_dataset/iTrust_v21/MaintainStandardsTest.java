package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class MaintainStandardsTest extends iTrustSeleniumTest {

	/*
	 * The URL for iTrust, change as needed
	 */
	/**ADDRESS*/
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	private WebDriver driver;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.admin1();
		gen.cptCodes();
		// turn off htmlunit warnings
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	    java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
	}
	
	public void testMaintainStandardsList1() throws Exception {
		driver = login("9000000001", "pw");
		assertEquals(driver.getTitle(), "iTrust - Admin Home");
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		driver.findElement(By.linkText("Edit CPT ProcedureCodes")).click();
		driver.findElement(By.name("code")).clear();
		driver.findElement(By.name("code")).sendKeys("90736");
		driver.findElement(By.name("description")).clear();
		driver.findElement(By.name("description")).sendKeys("Shingles Vaccine");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTrue(driver.getPageSource().contains("Success: 90736 - Shingles Vaccine"));
		assertLogged(TransactionType.MEDICAL_PROCEDURE_CODE_ADD, 9000000001L, 0L, "");
	}

}