package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class ReportAdversePrescriptionTest extends iTrustSeleniumTest {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.hcp0();
		gen.ovMed();
		gen.patient2();
		gen.patient1();
		driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/";
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
	}

	@Test
	public void testReport() throws Exception {
		driver = login("2", "pw");
	    driver.findElement(By.linkText("Prescription Records")).click();
	    
	    driver.findElement(By.name("mine")).click();
	    
	    driver.findElement(By.name("checking0")).clear();
	    driver.findElement(By.name("checking0")).sendKeys("Y");
	    
	    driver.findElement(By.name("adevent")).click();
	    driver.findElement(By.name("Comment")).sendKeys("YO THIS HURTS.");
	    driver.findElement(By.name("addReport")).click();
	    
	   assertLogged(TransactionType.ADVERSE_EVENT_REPORT, 2L, 0, "");
	}
	
	@Test
	public void testReportAdverseEventsButton() throws Exception {
		driver = login("2", "pw");
	    driver.findElement(By.linkText("Prescription Records")).click();
	    
	    driver.findElement(By.name("mine")).click();
	    
	    driver.findElement(By.name("checking0")).clear();
	    driver.findElement(By.name("checking0")).sendKeys("Y");
	    driver.findElement(By.name("adevent")).click();
	    
	    assertEquals("iTrust - Report Adverse Event", driver.getTitle());
	    
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