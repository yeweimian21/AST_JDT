package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class NDCodeTest extends iTrustSeleniumTest {

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
		gen.ndCodes();
		gen.ndCodes1();
		gen.ndCodes2();
		gen.ndCodes3();
		gen.ndCodes4();
		// turn off htmlunit warnings
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	    java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
	}
	
	public void testRemoveNDCode() throws Exception {
		driver = login("9000000001", "pw");
		assertEquals(driver.getTitle(), "iTrust - Admin Home");
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		driver.findElement(By.linkText("Edit ND Codes")).click();
		assertEquals(driver.getTitle(), "iTrust - Maintain ND Codes");
		driver.findElement(By.name("code1")).clear();
		driver.findElement(By.name("code1")).sendKeys("08109");
		driver.findElement(By.name("code2")).clear();
		driver.findElement(By.name("code2")).sendKeys("6");
		driver.findElement(By.name("description")).clear();
		driver.findElement(By.name("description")).sendKeys("Aspirin");
		driver.findElement(By.name("deleteND")).click();
		assertLogged(TransactionType.DRUG_CODE_REMOVE, 9000000001L, 0L, "081096");
		assertTrue(driver.getPageSource().contains("Success: 081096 - Aspirin removed"));
	}

	public void testUpdateNDCode() throws Exception {
		driver = login("9000000001", "pw");
		assertEquals(driver.getTitle(), "iTrust - Admin Home");
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		driver.findElement(By.linkText("Edit ND Codes")).click();
		assertEquals(driver.getTitle(), "iTrust - Maintain ND Codes");
		driver.findElement(By.name("code1")).clear();
		driver.findElement(By.name("code1")).sendKeys("00060");
		driver.findElement(By.name("code2")).clear();
		driver.findElement(By.name("code2")).sendKeys("431");
		driver.findElement(By.name("description")).clear();
		driver.findElement(By.name("description")).sendKeys("Benzoyl Peroxidez");
		driver.findElement(By.name("update")).click();
		assertLogged(TransactionType.DRUG_CODE_EDIT, 9000000001L, 0L, "00060431");
		assertTrue(driver.getPageSource().contains("Success: 1 row(s) updated"));
	}
}