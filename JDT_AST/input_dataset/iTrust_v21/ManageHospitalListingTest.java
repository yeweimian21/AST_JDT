package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ManageHospitalListingTest extends iTrustSeleniumTest {

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
		gen.standardData();
		// turn off htmlunit warnings
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	    java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
	}
	
	public void testCreateHospital() throws Exception {
		driver = login("9000000001", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		driver.findElement(By.linkText("Manage Hospital Listing")).click();
		assertEquals(driver.getTitle(), "iTrust - Maintain Hospital Listing and Assignments");
		driver.findElement(By.name("hospitalID")).clear();
		driver.findElement(By.name("hospitalID")).sendKeys("777");
		driver.findElement(By.name("hospitalName")).clear();
		driver.findElement(By.name("hospitalName")).sendKeys("Pokemon Center");
		driver.findElement(By.name("hospitalAddress")).clear();
		driver.findElement(By.name("hospitalAddress")).sendKeys("123 Centenial Parkway");
		driver.findElement(By.name("hospitalCity")).clear();
		driver.findElement(By.name("hospitalCity")).sendKeys("Raleigh");
		driver.findElement(By.name("hospitalState")).clear();
		driver.findElement(By.name("hospitalState")).sendKeys("NC");
		driver.findElement(By.name("hospitalZip")).clear();
		driver.findElement(By.name("hospitalZip")).sendKeys("27607");
		driver.findElement(By.name("add")).click();
		assertTrue(driver.getPageSource().contains("Success"));
		assertTrue(driver.getPageSource().contains("777"));
		assertTrue(driver.getPageSource().contains("Pokemon Center"));
		assertTrue(driver.getPageSource().contains("123 Centenial Parkway"));
		assertTrue(driver.getPageSource().contains("Raleigh"));
		assertTrue(driver.getPageSource().contains("NC"));
		assertTrue(driver.getPageSource().contains("27607"));
	}
	
	public void testUpdateHospital() throws Exception {
		driver = login("9000000001", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		driver.findElement(By.linkText("Manage Hospital Listing")).click();
		assertEquals(driver.getTitle(), "iTrust - Maintain Hospital Listing and Assignments");
		driver.findElement(By.name("hospitalID")).clear();
		driver.findElement(By.name("hospitalID")).sendKeys("5");
		driver.findElement(By.name("hospitalName")).clear();
		driver.findElement(By.name("hospitalName")).sendKeys("Facebook Insane Asylum");
		driver.findElement(By.name("hospitalAddress")).clear();
		driver.findElement(By.name("hospitalAddress")).sendKeys("2 Yarborough Drive");
		driver.findElement(By.name("hospitalCity")).clear();
		driver.findElement(By.name("hospitalCity")).sendKeys("Raleigh");
		driver.findElement(By.name("hospitalState")).clear();
		driver.findElement(By.name("hospitalState")).sendKeys("NC");
		driver.findElement(By.name("hospitalZip")).clear();
		driver.findElement(By.name("hospitalZip")).sendKeys("27607");
		driver.findElement(By.name("update")).click();
		assertTrue(driver.getPageSource().contains("Success"));
		assertTrue(driver.getPageSource().contains("5"));
		assertTrue(driver.getPageSource().contains("Facebook Insane Asylum"));
		assertTrue(driver.getPageSource().contains("2 Yarborough Drive"));
		assertTrue(driver.getPageSource().contains("Raleigh"));
		assertTrue(driver.getPageSource().contains("NC"));
		assertTrue(driver.getPageSource().contains("27607"));
	}
	
	public void testUpdateWithoutID() throws Exception {
		driver = login("9000000001", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		driver.findElement(By.linkText("Manage Hospital Listing")).click();
		assertEquals(driver.getTitle(), "iTrust - Maintain Hospital Listing and Assignments");
		driver.findElement(By.name("hospitalName")).clear();
		driver.findElement(By.name("hospitalName")).sendKeys("Facebook Insane Asylum");
		driver.findElement(By.name("hospitalAddress")).clear();
		driver.findElement(By.name("hospitalAddress")).sendKeys("2 Yarborough Drive");
		driver.findElement(By.name("hospitalCity")).clear();
		driver.findElement(By.name("hospitalCity")).sendKeys("Raleigh");
		driver.findElement(By.name("hospitalState")).clear();
		driver.findElement(By.name("hospitalState")).sendKeys("NC");
		driver.findElement(By.name("hospitalZip")).clear();
		driver.findElement(By.name("hospitalZip")).sendKeys("27607");
		driver.findElement(By.name("update")).click();
		assertTrue(driver.getPageSource().contains("This form has not been validated correctly"));
	}
	
	public void testNameTooLong() throws Exception {
		driver = login("9000000001", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		driver.findElement(By.linkText("Manage Hospital Listing")).click();
		assertEquals(driver.getTitle(), "iTrust - Maintain Hospital Listing and Assignments");
		driver.findElement(By.name("hospitalID")).clear();
		driver.findElement(By.name("hospitalID")).sendKeys("777");
		driver.findElement(By.name("hospitalName")).clear();
		driver.findElement(By.name("hospitalName")).sendKeys("ABCABCABCABCABCABCABCABCABCABCABC");
		driver.findElement(By.name("hospitalAddress")).clear();
		driver.findElement(By.name("hospitalAddress")).sendKeys("2 Yarborough Drive");
		driver.findElement(By.name("hospitalCity")).clear();
		driver.findElement(By.name("hospitalCity")).sendKeys("Raleigh");
		driver.findElement(By.name("hospitalState")).clear();
		driver.findElement(By.name("hospitalState")).sendKeys("NC");
		driver.findElement(By.name("hospitalZip")).clear();
		driver.findElement(By.name("hospitalZip")).sendKeys("27607");
		driver.findElement(By.name("add")).click();
		assertTrue(driver.getPageSource().contains("This form has not been validated correctly"));
	}

}