package edu.ncsu.csc.itrust.selenium;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/*
 * This is the Selenium equivalent of FindExpertTest.java
 */
public class FindExpertTest extends iTrustSeleniumTest{
	
	/**
	 * Selenium html unit driver.
	 */
	private HtmlUnitDriver driver;
	
	/**
	 * MID of the test user "Random Person"
	 */
	private static final String RANDOM_PERSON_MID = "1";
	
	/**
	 * Default user password.
	 */
	private static final String PASSWORD = "pw";
	
	/**
	 * Build the absolute URL for Find Expert off of the base URL and relative url.
	 */
	private static final String FIND_EXPERT = ADDRESS + "/auth/patient/findExpert.jsp";
	
	/**
	 * Set up for testing by clearing and recreating
	 * all standard data, then performing UC47
	 * specific data generation.
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp(); // clear tables is called in super
		gen.clearAllTables();
		gen.standardData();
		gen.uc47SetUp();
	}
	
	/**
	 * Remove the UC47 specific data and clear all tables.
	 */
	@Override
	protected void tearDown() throws Exception {
		gen.uc47TearDown();
		gen.clearAllTables();
	}
	
	public void testEditAndFindExpert() throws Exception {
		// login admin
		driver = (HtmlUnitDriver)login("9000000001", "pw");
		String title = driver.getTitle();
		assertEquals("iTrust - Admin Home", title);
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		
		// click on Edit Personnel
		driver.findElement(By.linkText("Edit Personnel")).click();
		// find hcp
		title = driver.getTitle();
		assertEquals("iTrust - Please Select a Personnel", title);
		driver.findElement(By.name("FIRST_NAME")).sendKeys("Kelly");
		driver.findElement(By.name("LAST_NAME")).sendKeys("Doctor");
		driver.findElement(By.name("LAST_NAME")).submit();
		
		//focus
		driver.findElement(By.xpath(".//*[@value='9000000000']")).click();
		//submit the form
		driver.findElement(By.xpath(".//*[@value='9000000000']")).submit();
		
		title = driver.getTitle();
		assertEquals("iTrust - Edit Personnel", title);
		driver.findElement(By.name("phone")).clear();
		driver.findElement(By.name("phone")).sendKeys("919-100-1000");
		driver.findElement(By.name("action")).click();
		
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.LHCP_EDIT, 9000000001L, 9000000000L, "");
		//logout admin
		WebElement link = driver.findElements(By.tagName("li")).get(1);
		link.findElement(By.tagName("a")).click();
		
		//login patient
		title = driver.getTitle();
		assertEquals("iTrust - Login", title);
		driver = (HtmlUnitDriver)login("1", "pw");
		title = driver.getTitle();
		assertEquals("iTrust - Patient Home", title);
		//click on Find an Expert
		driver.findElement(By.linkText("Find an Expert")).click();
		title = driver.getTitle();
		assertEquals("iTrust - Find an Expert", title);
		
		//Search for surgeon
	    Select oSelection = new Select(driver.findElement(By.name("specialty")));
        oSelection.selectByVisibleText("Surgeon");
		driver.findElement(By.name("findExpert")).click();
		title = driver.getTitle();
		assertEquals("iTrust - Find an Expert", title);
		assertTrue(driver.getPageSource().contains("Kelly Doctor"));
		
	}
	
	/**
	 * Test the displayed distances when searching for an expert. According to the iTrust Wiki
	 * experts should be displayed by hospital location, not by their personal listed address.
	 * 
	 * EX: A doctor that lives in New York but is a provider for a hospital in Raleigh, NC
	 * should be displayed as from Raleigh, NC. When a user from Raleigh, NC searches for an 
	 * expert, that doctor should be displayed with a distance of zero miles.
	 */
	public void testFindExpertDisplayedDistances() throws Exception {
		// Login as Random User
		driver = (HtmlUnitDriver) login(RANDOM_PERSON_MID, PASSWORD);
		
		// Navigate to the Find Expert page
		driver.get(FIND_EXPERT);
		
		// Verify Specialty: All Doctors, ZIP Code: 27606, Distance: All, Sort By: Distance
		
		Select select;
		
		select = new Select(driver.findElement(By.name("specialty")));
		select.selectByVisibleText("All Doctors");
		
		WebElement zipcode = driver.findElement(By.name("zipCode"));
		zipcode.clear();
		zipcode.sendKeys("27606");
		
		select = new Select(driver.findElement(By.name("range")));
		select.selectByVisibleText("All");
		
		select = new Select(driver.findElement(By.name("sortby")));
		select.selectByVisibleText("Distance");
		
		// Submit the form
		driver.findElement(By.name("findExpert")).click();;
		
		// Verify display distances for two of the doctors.
		// Kelly Doctor: Lives in New York, NY and practices in Raleigh, NC. 
		// Shelly Vang: Lives in Greensboro, NC and practecesin Greensboro, NC.
		
		// Get container elements that group doctor information
		List<WebElement> elements = driver.findElements(By.className("grey-border-container"));
		
		// Number of doctors found
		int doctors = 0;
		
		// Search through the list of doctors
		for (WebElement doc : elements) {
			
			// Get the doctor name
			WebElement docID = doc.findElements(By.tagName("p")).get(0);
			String name = docID.findElement(By.tagName("a")).getText();
			
			// Get the doctor's displayed distance
			String distance = doc.findElements(By.tagName("p")).get(1).getText();
			
			// If doctor is Shelly Vang, verify display distance is 62 miles
			if (name.equals("Shelly Vang")) {
				doctors++;
				assertTrue("Distance is 62 miles", distance.contains("62 miles"));
			}
			// If doctor is Kelly Doctor, verify display distance is 0 miles.
			else if (name.equals("Kelly Doctor")) {
				doctors++;
				assertTrue("Distance is 3 miles", distance.contains("3 miles"));
			}
			// If doctor is John Zoidberg, verify distance is unknown (invalid zip)
			else if (name.equals("John Zoidberg")) {
				doctors++;
				assertTrue("Distance is unknown", distance.contains("Unknown miles"));
			}
		}
		
		// Sanity check and verify that both doctors were found in the list.
		// If neither was found there may be an underlying issue with the feature
		// or the structure of the html page may have changed.
		assertTrue("All doctors found", doctors == 3);
	}
}