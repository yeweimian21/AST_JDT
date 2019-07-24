package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class CreateHCPTest extends iTrustSeleniumTest {
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.admin1();
		gen.hcp0();
		gen.cptCodes();
	}
	

	
	public void testEditValidPeronnel() throws Exception {
		//login as admin
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000001", "pw");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		
		driver.findElement(By.linkText("Edit Personnel")).click();
		assertEquals("iTrust - Please Select a Personnel", driver.getTitle());
		
		WebElement firstName = driver.findElement(By.name("FIRST_NAME"));
		firstName.sendKeys("Kelly");
		WebElement lastName = driver.findElement(By.name("LAST_NAME"));
		lastName.sendKeys("Doctor");
		WebElement form = driver.findElement(By.id("userSearchForm"));
		form.submit();
		WebElement user = driver.findElement(By.id("9000000000"));
		user.submit();
		assertEquals("iTrust - Edit Personnel", driver.getTitle());
		
		WebElement city = driver.findElement(By.name("city"));
		city.clear();
		city.sendKeys("Brooklyn");
		city.submit();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
	}
	
	public void testEditHospitalAssignments() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		//login as admin
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000001", "pw");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		
		driver.findElement(By.linkText("Edit HCP Assignment to Hospital")).click();
		assertEquals("iTrust - Please Select a Personnel", driver.getTitle());
		
		WebElement firstName = driver.findElement(By.name("FIRST_NAME"));
		firstName.sendKeys("Kelly");
		WebElement lastName = driver.findElement(By.name("LAST_NAME"));
		lastName.sendKeys("Doctor");
		WebElement form = driver.findElement(By.id("userSearchForm"));
		form.submit();
		WebElement user = driver.findElement(By.id("9000000000")); //mid of the user search result
		user.submit();
		assertEquals("iTrust - Hospital Staffing Assignments", driver.getTitle());
		
		driver.findElement(By.linkText("Assign")).click();
		assertTrue(driver.getPageSource().contains("HCP has been assigned"));
		
		driver.findElement(By.linkText("Unassign")).click();
		assertTrue(driver.getPageSource().contains("HCP has been unassigned"));
	}
}
