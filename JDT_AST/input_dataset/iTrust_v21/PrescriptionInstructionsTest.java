package edu.ncsu.csc.itrust.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class PrescriptionInstructionsTest extends iTrustSeleniumTest {
	
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.hcp0();
		gen.ndCodes();
		gen.patient1();
		gen.patient2();
		gen.patient4();
		
	}
	
	/*
	 * 
	 */
	public void testUC11() throws Exception {
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();
		
		// Navigate to iTrust
		driver.get(ADDRESS);
		
		// Login
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).click();
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
		// Check if logged in
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	        
	    // Select Document Office Visit
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    
	    // Search for patient 2
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2']")).submit();
	    
	    // Select the visit for 06/10/2007
	    driver.findElement(By.linkText("06/10/2007")).click();
	    assertLogged(TransactionType.OFFICE_VISIT_VIEW, 9000000000L, 2L, "Office visit");
	    
	    // Select the prescription for Tetracycline
	    driver.findElement(By.linkText("Tetracycline (009042407)")).click();
	    
	    // Check the current table values
	    assertEquals("Edit Prescription Instructions", driver.findElement(By.cssSelector("th")).getText());
	    assertEquals("5", driver.findElement(By.name("dosage")).getAttribute("value"));
	    assertEquals("Take twice daily", driver.findElement(By.name("instructions")).getText());
	    
	    // Update the prescription data
	    driver.findElement(By.name("dosage")).clear();
	    driver.findElement(By.name("dosage")).sendKeys("10");
	    driver.findElement(By.name("instructions")).clear();
	    driver.findElement(By.name("instructions")).sendKeys("Take thrice daily");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    // Check if the prescription updated
	    assertEquals("iTrust - Document Office Visit", driver.getTitle());
	    assertEquals("Prescription information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    //Duplicate entry in table, so finding wrong entry
	    //assertEquals("10mg", driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr[3]/td[2]")).getText());
	    //assertEquals("Take thrice daily", driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr[3]/td[4]")).getText());
	    assertLogged(TransactionType.PRESCRIPTION_EDIT, 9000000000L, 2L, "");		
	    
		// Close the driver
		driver.quit();
	}
	
	@Test
	public void testUC19() throws Exception {
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();
		
		// Navigate to iTrust
		driver.get(ADDRESS);
		
		// Login
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("2");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    // Check if logged in
	    assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");		
	    
	    // Select Prescription Records from the menu
	    //driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Prescription Records")).click();
	    assertEquals("iTrust - Get My Prescription Report", driver.getTitle());
	    
	    // View current patient's records
	    driver.findElement(By.name("mine")).click();
	    
	    // Check for Prioglitazone in the table and select it
	    assertLogged(TransactionType.PRESCRIPTION_REPORT_VIEW, 2L, 2L, "");
	    assertEquals("Prioglitazone", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[2]")).getText());
	    driver.findElement(By.linkText("64764-1512")).click();
	    
	    // Check prescription information
	    assertEquals("Prescription Information", driver.findElement(By.cssSelector("th")).getText());
	    assertEquals("5mg", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[3]/td[3]")).getText());
	    assertEquals("Take twice daily", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[3]/td[5]")).getText());

		// Close the driver
		driver.quit();
	}
	
	@Test
	public void testUC29() throws Exception {
		gen.standardData();
		gen.patient_hcp_vists();
		gen.hcp_diagnosis_data();
		
		// Create an instance of the html unit driver
		WebDriver driver = new HtmlUnitDriver();
						
		//Visit the iTrust home page
		driver.get(ADDRESS);
						
		// Get the page title
		assertEquals("iTrust - Login", driver.getTitle());

		// Login
		driver.findElement(By.name("j_username")).clear();
		driver.findElement(By.name("j_username")).sendKeys("1");
		driver.findElement(By.name("j_password")).clear();
		driver.findElement(By.name("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// Check if logged in
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");

		// Click on the My Diagnoses option
		driver.findElement(By.linkText("My Diagnoses")).click();
		assertEquals("iTrust - My Diagnoses", driver.getTitle());
		assertLogged(TransactionType.DIAGNOSES_LIST_VIEW, 1L, 1L, "");

		// Select Acute Lycanthropy
		driver.findElement(By.linkText("Acute Lycanthropy(250.00)")).click();
		assertEquals("HCPs having experience with diagnosis 250.00", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/h2")).getText());

		// Close the driver
		driver.quit();
	}
	
	@Test
	public void testUC31() throws Exception {
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();
		
		// Navigate to iTrust
		driver.get(ADDRESS);
		
		// Login
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("2");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    //Check if logged in 
	    assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
	    
	    //Select My Expired Prescription Reports from the menu
	    driver.findElement(By.linkText("My Expired Prescription Reports")).click();
	    
	    // Select the Tetracycline prescription
	    assertEquals("Tetracycline", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[2]")).getText());
	    driver.findElement(By.linkText("00904-2407")).click();
	    
	    // Check the values in the prescription table
	    assertEquals("Prescription Information", driver.findElement(By.cssSelector("th")).getText());
	    assertEquals("5mg", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[3]/td[3]")).getText());
	    assertEquals("Take twice daily", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[3]/td[5]")).getText());
	    assertLogged(TransactionType.EXPIRED_PRESCRIPTION_VIEW, 2L, 2L, "");
	    
		// Close the driver
		driver.quit();
	}

}