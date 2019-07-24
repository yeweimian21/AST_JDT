package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Makes sure the "specialty" field is available when adding an HCP,
 * creates an HCP with the specialty "Medicine", and makes sure
 * that HCP was successfully created.
 *
 */
public class CreateLTSpecTest extends iTrustSeleniumTest {

	/**
	 * Gives us the standard testing data
	 */
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	/**
	 * Test we can create HCP's as an admin
	 */
	public void testSpecialtyOnForm() throws Exception {
		//login admin
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000001", "pw");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		
		//click on add hcp
		driver.findElement(By.linkText("Add LT")).click();
		
		//add the hcp
		assertEquals("iTrust - Add LT", driver.getTitle());
		WebElement form = driver.findElement(By.tagName("form"));
		
		//fill in the information
		WebElement firstName = form.findElement(By.name("firstName"));
		firstName.sendKeys("New");
		WebElement lastName = form.findElement(By.name("lastName"));
		lastName.sendKeys("Person");
		WebElement email = form.findElement(By.name("email"));
		email.sendKeys("nperson@gmail.com");
		//get the dropdown options for the apptType
		Select specialty = new Select(form.findElement(By.name("specialty")));
		//make sure there is such a field
		assertFalse(specialty == null);
		String currentVal = specialty.getAllSelectedOptions().get(0).toString();
		specialty.deselectByValue(currentVal);
		specialty.selectByValue("general");
		/* I know this seems crazy, selecting an option, and then creating
		 * a new select option to make sure that the right option is selected
		 * before we even submit the form, but this is as close to the 
		 * previous test this is based off of as I could
		 * think of making it.
		 */
		Select dropbox = new Select(form.findElement(By.name("specialty")));
		assertEquals(1, dropbox.getAllSelectedOptions().size());
		assertEquals("general", dropbox.getFirstSelectedOption().getAttribute(VALUE));
		form.submit();

	}
}
