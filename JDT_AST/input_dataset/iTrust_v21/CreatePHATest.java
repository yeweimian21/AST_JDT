package edu.ncsu.csc.itrust.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class CreatePHATest extends iTrustSeleniumTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.admin1();
		gen.cptCodes();
	}


	public void testCreateNullPHA() throws Exception {
		// Log in as an Admin.
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000001", "pw");

		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");

		// Click on Add PHA.
		driver.findElement(By.linkText("Add PHA")).click();
		assertEquals("iTrust - Add PHA", driver.getTitle());

		// Add the PHA.
		WebElement elem = driver.findElement(By.name("firstName"));
		elem.submit();

		// Make sure the text displays.
		List<WebElement> list = driver
				.findElements(By
						.xpath("//*[contains(text(),'"
								+ "This form has not been validated correctly."
								+ "')]"));
		assertTrue("Text not found!", list.size() > 0);

		// Make sure nothing happened.
		assertNotLogged(TransactionType.PHA_DISABLE, 9000000001L, 0L, "");
	}



}
