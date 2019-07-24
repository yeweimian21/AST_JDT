package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Test class for iTrust creation of UAP's.
 */
public class CreateUAPTest extends iTrustSeleniumTest {

	/**
	 * Set up for testing by creating necessary data.
	 */
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.admin1();
		gen.hcp0();
		gen.cptCodes();
	}
	
	/**
	 * Test creating a standard UAP. The form should submit without any issues.
	 */
	public void testCreateUAP1() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000000", "pw");
		driver.findElement(By.linkText("UAP")).click();
		assertEquals("iTrust - Add UAP", driver.getTitle());
		
		WebElement firstName = driver.findElement(By.name("firstName"));
		firstName.sendKeys("Drake");
		WebElement lastName = driver.findElement(By.name("lastName"));
		lastName.sendKeys("Ramoray");
		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys("drake@drake.com");
		WebElement form = driver.findElement(By.name("formIsFilled"));
		form.submit();

	}
	
	/**
	 * When creating a UAP, the entered email needs to be validated
	 * against the RFC3696 specification. This specification
	 * allows for quoted strings, escaped characters, and underscores.
	 * 
	 * In this test, Joe Bob with the email _"joe@email"\\email@gmail.com
	 * should validate.
	 */
	public void testCreateUAPRFC3696() throws Exception {
		try {
			// Log in
			HtmlUnitDriver driver = (HtmlUnitDriver) login("9000000000", "pw"); 
			
			// Click on the add UAP link
			driver.findElement(By.linkText("UAP")).click();
			
			// Fill in the fields
			driver.findElement(By.name("firstName")).sendKeys("Joe");
			driver.findElement(By.name("lastName")).sendKeys("Bob");
			String email = "_\"joe@email\"\\\\email@gmail.com";
			driver.findElement(By.name("email")).sendKeys(email);
			
			// Submit
			driver.findElement(By.cssSelector("input[type='submit']")).click();
			
			// Verify that the success text appears
			assertTrue("Success Message", 
					driver.findElement(By.className("iTrustMessage")).getText().contains("succesfully added!"));
		}
		catch (NoSuchElementException e) {
			fail(e.getMessage());
		}
	}
}
