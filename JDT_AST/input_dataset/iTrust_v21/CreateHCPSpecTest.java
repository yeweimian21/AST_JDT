package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import edu.ncsu.csc.itrust.enums.TransactionType;
 
public class CreateHCPSpecTest extends iTrustSeleniumTest {
 
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testSpecialtyOnForm() throws Exception {
		WebDriver driver = login("9000000001", "pw");
		
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		
		driver.findElement(By.linkText("Add HCP")).click();

		assertEquals("iTrust - Add HCP", driver.getTitle());
		
		driver.findElement(By.name("firstName")).sendKeys("Firstname");
		driver.findElement(By.name("lastName")).sendKeys("Lastname");
		driver.findElement(By.name("email")).sendKeys("abcdef@ncsu.edu");
		Select select = new Select(driver.findElement(By.name("specialty")));
		select.selectByValue("pediatrician");
		driver.findElement(By.name("email")).submit();
		

	}
}