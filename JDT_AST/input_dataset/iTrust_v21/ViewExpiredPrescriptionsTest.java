package edu.ncsu.csc.itrust.selenium;

import org.junit.*;
import org.openqa.selenium.*;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class ViewExpiredPrescriptionsTest extends iTrustSeleniumTest {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.icd9cmCodes();
		gen.ndCodes();
		gen.uap1();
		gen.patient2();
		gen.patient1();
		gen.patient4();
		gen.patient9();
		gen.hcp0();
		gen.hcp1();
		gen.hcp2();
		gen.clearLoginFailures();
		gen.hcp3();
	}

	@Test
	public void testViewExpired1() throws Exception {
		driver = login("2", "pw");
		driver.findElement(By.linkText("My Expired Prescription Reports")).click();
		assertTrue(driver.getPageSource().contains("00904-2407"));
		assertFalse(driver.getPageSource().contains("9000000000"));
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		driver.findElement(By.linkText("Kelly Doctor")).click();
		String text = driver.findElement(By.cssSelector("table.fTable")).getText();
	    assertTrue("Text not found!", text.contains("surgeon"));
	    assertTrue("Text not found!", text.contains("4321 My Road St"));
	    assertTrue("Text not found!", text.contains("New York"));
	    assertTrue("Text not found!", text.contains("NY"));
	    assertTrue("Text not found!", text.contains("10453"));
	    assertTrue("Text not found!", text.contains("999-888-7777"));
	    assertTrue("Text not found!", text.contains("kdoctor@iTrust.org"));
	    assertTrue("Text not found!", !text.contains("9000000000"));
	    
	}

	@Test
	public void testViewExpired2() throws Exception {
		driver = login("99", "pw");
		
		assertLogged(TransactionType.HOME_VIEW, 99L, 0L, "");

		// click on My Expired Prescription Reports
		driver.findElement(By.linkText("My Expired Prescription Reports")).click();
		assertTrue(driver.getPageSource().contains("00904-2407"));
		assertTrue(driver.getPageSource().contains("08109-6"));
		assertFalse(driver.getPageSource().contains("9000000000"));
		assertFalse(driver.getPageSource().contains("9900000000"));
		driver.findElement(By.linkText("Tester Arehart")).click();
		String text = driver.findElement(By.cssSelector("table.fTable")).getText();
	    assertTrue("Text not found!", text.contains("Neurologist"));
	    assertTrue("Text not found!", text.contains("2110 Thanem Circle"));
	    assertTrue("Text not found!", text.contains("Raleigh"));
	    assertTrue("Text not found!", text.contains("NC"));
	    assertTrue("Text not found!", text.contains("999-888-7777"));
	    assertTrue("Text not found!", text.contains("tarehart@iTrust.org"));
	    assertTrue("Text not found!", !text.contains("9900000000"));
	}
	
	@Test
	public void testViewExpired3() throws Exception {
		driver = super.login("99", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 99L, 0L, "");
		
		// click on My Expired Prescription Reports
		driver.findElement(By.linkText("My Expired Prescription Reports")).click();
		assertTrue(driver.getPageSource().contains("00904-2407"));
		assertTrue(driver.getPageSource().contains("08109-6"));
		assertFalse(driver.getPageSource().contains("9000000000"));
		assertFalse(driver.getPageSource().contains("9900000000"));
		assertLogged(TransactionType.EXPIRED_PRESCRIPTION_VIEW, 99L, 99L, "");
		driver.findElement(By.linkText("Kelly Doctor")).click();
		String text = driver.findElement(By.cssSelector("table.fTable")).getText();
	    assertTrue("Text not found!", text.contains("surgeon"));
	    assertTrue("Text not found!", text.contains("4321 My Road St"));
	    assertTrue("Text not found!", text.contains("New York"));
	    assertTrue("Text not found!", text.contains("NY"));
	    assertTrue("Text not found!", text.contains("999-888-7777"));
	    assertTrue("Text not found!", text.contains("kdoctor@iTrust.org"));
	    assertTrue("Text not found!", !text.contains("9000000000"));
	}
	
	@Test
	public void testViewExpired4() throws Exception {
		driver = super.login("99", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 99L, 0L, "");
		
		// click on My Expired Prescription Reports
		driver.findElement(By.linkText("My Expired Prescription Reports")).click();
		assertTrue(driver.getPageSource().contains("00904-2407"));
		assertTrue(driver.getPageSource().contains("08109-6"));
		assertFalse(driver.getPageSource().contains("9000000000"));
		assertFalse(driver.getPageSource().contains("9900000000"));
		assertLogged(TransactionType.EXPIRED_PRESCRIPTION_VIEW, 99L, 99L, "");
		driver.findElement(By.linkText("Jimmy Incomplete")).click();
		String text = driver.findElement(By.cssSelector("table.fTable")).getText();
	    assertTrue("Text not found!", !text.contains("null"));
	    assertTrue("Text not found!", !text.contains("AK"));
	    assertTrue("Text not found!", !text.contains("9990000000"));
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