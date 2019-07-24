package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;


/**
 * Tests viewing/editing dependent's demographic information as the "dependee"
 */
public class DependencyExtensionSeleniumTest extends iTrustSeleniumTest {

	/**
	 * set up standard test data
	 */
	public void setUp() throws Exception {
		super.setUp();
		gen.standardData();
	}
	
	/**
	 * Make sure the parent of a dependent can see the dependent's information.
	 * @throws Exception
	 */
	public void testViewableDependentsInMyDemographics() throws Exception {
		//add in the baby
		TestDataGenerator gen = new TestDataGenerator();
		gen.doBaby();
		
		//login
		WebDriver driver = new HtmlUnitDriver();
		driver = login("2", "pw");
		
		//make sure you can see baby
		driver.findElement(By.linkText("My Demographics")).click();
		assertTrue(driver.getPageSource().contains("Baby Programmer"));
	}
	
	/**
	 * Tests that you can edit one of your dependent's demographic data
	 */
	public void testEditableDependentsInMyDemographics() throws Exception {
		//add in baby again
		TestDataGenerator gen = new TestDataGenerator();
		gen.doBaby();
		WebDriver driver = new HtmlUnitDriver();
		driver = login("2", "pw");
		//make sure you can see baby
		driver.findElement(By.linkText("My Demographics")).click();
		assertTrue(driver.getPageSource().contains("Baby Programmer"));
		
		//get the form to update baby's info
		WebElement babyForm = driver.findElement(By.id("edit_2"));
		WebElement babyName = babyForm.findElement(By.name("firstName"));
		babyName.clear();
		babyName.sendKeys("BabyO");
		
		//submit the form
		babyForm.submit();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
	}
	
	
	/**
	 * Test that without adding in baby, there are no dependents
	 */
	public void testNoDependentsInMyDemographics() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		driver = login("2", "pw");
		
		//go to my demographics
		driver.findElement(By.linkText("My Demographics")).click();
		assertFalse(driver.getPageSource().contains("Baby Programmer"));
	}	
}
