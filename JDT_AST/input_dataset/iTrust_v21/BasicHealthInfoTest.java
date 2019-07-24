package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.*;

import edu.ncsu.csc.itrust.enums.TransactionType;
 
public class BasicHealthInfoTest extends iTrustSeleniumTest {
 
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testBasicHealthViewed() throws Exception{
		WebDriver driver = login("9000000000", "pw");
		
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.linkText("Basic Health Information")).click();
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.cssSelector("input[value='2']")).submit();
		
		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp", driver.getCurrentUrl());
		
		driver.findElement(By.cssSelector("a[href='/iTrust/logout.jsp']")).click(); //By.linkText won't work for some reason...
		
		assertEquals(ADDRESS + "auth/forwardUser.jsp", driver.getCurrentUrl());
		
		driver.quit();
		
		driver = login("2", "pw");
		
		assertTrue(driver.getPageSource().contains("Kelly Doctor"));
		assertTrue(driver.getPageSource().contains("viewed your health records history today at"));
	}
	
	public void testBasicHealthSmokingStatus() throws Exception {
		WebDriver driver = login("9000000000", "pw");
		
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.linkText("Document Office Visit")).click();

		assertEquals(ADDRESS + "auth/getPatientID.jsp?forward=/iTrust/auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.cssSelector("input[value='2']")).submit();
		
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		
		driver.findElement(By.cssSelector("input[value='Yes, Document Office Visit']")).submit();
		
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		driver.findElement(By.name("update")).submit();
		
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 2L, "");
		
		assertTrue(driver.getPageSource().contains("1 - Current every day smoker"));
		assertTrue(driver.getPageSource().contains("2 - Current some day smoker"));
		assertTrue(driver.getPageSource().contains("3 - Former smoker"));
		assertTrue(driver.getPageSource().contains("4 - Never smoker"));
		assertTrue(driver.getPageSource().contains("5 - Smoker, current status unknown"));
		assertTrue(driver.getPageSource().contains("9 - Unknown if ever smoked"));
	}
}