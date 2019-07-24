package edu.ncsu.csc.itrust.selenium;

import java.util.List;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ChangePasswordTest extends iTrustSeleniumTest {
	
    private WebDriver driver = null;
	
	@Before
	public void setUp() throws Exception {
	    // Create a new instance of the driver
	    driver = new HtmlUnitDriver();
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testChangePassword_Invalid_Length() throws Exception {
		//Patient1 logs into iTrust
		driver = (HtmlUnitDriver)login("1", "pw");
		assertTrue(driver.getTitle().contains("iTrust - Patient Home"));  
		
		//User goes to change password
		List<WebElement> links = driver.findElements(By.tagName("a"));

		int count = 0;

		for(int i = 0; i < links.size(); i++) {
			if(links.get(i).getAttribute("href").contains("changePassword"))
			{
				count = i;
				break;
			}
		}
		
		links.get(count).click();
		
		//User types in their current, new, and confirm passwords
        driver.findElement(By.name("oldPass")).sendKeys("pw");
        driver.findElement(By.name("newPass")).sendKeys("pas1");
        driver.findElement(By.name("confirmPass")).sendKeys("pas1");
        driver.findElement(By.name("oldPass")).submit();
		
		//User submits password change. Change logged
        assertTrue(driver.getPageSource().contains("Invalid password"));
		assertLogged(TransactionType.PASSWORD_CHANGE_FAILED, 1L, 0, "");
		
		//User logs out
		links = driver.findElements(By.tagName("a"));
		links.get(count - 1).click();	
		
		//User can log in with old password, but can't with new one
		try {
			driver = (HtmlUnitDriver)login("1", "pas1");
			fail("Should have thrown an exception");
		} catch (IllegalArgumentException d) {
			assertTrue(driver.getTitle().contains("iTrust - Login"));  
			driver = (HtmlUnitDriver)login("1", "pw");
			assertTrue(driver.getTitle().contains("iTrust - Patient Home"));  
		}
	}
	
	public void testChangePassword_Invalid_Characters() throws Exception {
		//Patient1 logs into iTrust
		driver = (HtmlUnitDriver)login("1", "pw");
		assertTrue(driver.getTitle().contains("iTrust - Patient Home"));  
		
		//User goes to change password
		List<WebElement> links = driver.findElements(By.tagName("a"));

		int count = 0;

		for(int i = 0; i < links.size(); i++) {
			if(links.get(i).getAttribute("href").contains("changePassword"))
			{
				count = i;
				break;
			}
		}
		
		links.get(count).click();

		//User types in their current, new, and confirm passwords
        driver.findElement(By.name("oldPass")).sendKeys("pw");
        driver.findElement(By.name("newPass")).sendKeys("password");
        driver.findElement(By.name("confirmPass")).sendKeys("password");
        driver.findElement(By.name("oldPass")).submit();
		
		//User submits password change. Change logged
        assertTrue(driver.getPageSource().contains("Invalid password"));
		assertLogged(TransactionType.PASSWORD_CHANGE_FAILED, 1L, 0, "");
		
		//User logs out
		links = driver.findElements(By.tagName("a"));
		links.get(count - 1).click();	
		
		//User can log in with old password, but can't with new one
		try {
			driver = (HtmlUnitDriver)login("1", "password");
			fail("Should have thrown an exception");
		} catch (IllegalArgumentException d) {
			assertTrue(driver.getTitle().contains("iTrust - Login"));  
			driver = (HtmlUnitDriver)login("1", "pw");
			assertTrue(driver.getTitle().contains("iTrust - Patient Home"));  
		}
	}

	public void testChangePassword_Failed_Confirmation() throws Exception {
		//Patient1 logs into iTrust
		driver = (HtmlUnitDriver)login("1", "pw");
		assertTrue(driver.getTitle().contains("iTrust - Patient Home"));  
		
		//User goes to change password
		List<WebElement> links = driver.findElements(By.tagName("a"));

		int count = 0;

		for(int i = 0; i < links.size(); i++) {
			if(links.get(i).getAttribute("href").contains("changePassword"))
			{
				count = i;
				break;
			}
		}
		
		links.get(count).click();
		
		//User types in their current, new, and confirm passwords
        driver.findElement(By.name("oldPass")).sendKeys("pw");
        driver.findElement(By.name("newPass")).sendKeys("pass1");
        driver.findElement(By.name("confirmPass")).sendKeys("pass2");
        driver.findElement(By.name("oldPass")).submit();
		
		//User submits password change. Change logged
        assertTrue(driver.getPageSource().contains("Invalid password"));
		assertLogged(TransactionType.PASSWORD_CHANGE_FAILED, 1L, 0, "");
		
		//User logs out
		links = driver.findElements(By.tagName("a"));
		links.get(count - 1).click();	
		
		//User can log in with old password, but can't with new one
		try {
			driver = (HtmlUnitDriver)login("1", "pas1");
			fail("Should have thrown an exception");
		} catch (IllegalArgumentException d) {
			assertTrue(driver.getTitle().contains("iTrust - Login"));  
			driver = (HtmlUnitDriver)login("1", "pw");
			assertTrue(driver.getTitle().contains("iTrust - Patient Home")); 
		}
	}

	public void testChangePassword_Invalid_Password() throws Exception {
		//Patient1 logs into iTrust
		driver = (HtmlUnitDriver)login("1", "pw");
		assertTrue(driver.getTitle().contains("iTrust - Patient Home"));  
		
		//User goes to change password
		List<WebElement> links = driver.findElements(By.tagName("a"));

		int count = 0;

		for(int i = 0; i < links.size(); i++) {
			if(links.get(i).getAttribute("href").contains("changePassword"))
			{
				count = i;
				break;
			}
		}
		
		links.get(count).click();

		//User types in their current, new, and confirm passwords
        driver.findElement(By.name("oldPass")).sendKeys("password");
        driver.findElement(By.name("newPass")).sendKeys("pass1");
        driver.findElement(By.name("confirmPass")).sendKeys("pass1");
        driver.findElement(By.name("oldPass")).submit();
		
		//User submits password change. Change logged
        assertTrue(driver.getPageSource().contains("Invalid password"));
		assertLogged(TransactionType.PASSWORD_CHANGE_FAILED, 1L, 0, "");
		
		//User logs out
		links = driver.findElements(By.tagName("a"));
		links.get(count - 1).click();	
		
		//User can log in with old password, but can't with new one
		try {
			driver = (HtmlUnitDriver)login("1", "pass1");
			fail("Should have thrown an exception");
		} catch (IllegalArgumentException d) {
			assertTrue(driver.getTitle().contains("iTrust - Login"));  
			driver = (HtmlUnitDriver)login("1", "pw");
			assertTrue(driver.getTitle().contains("iTrust - Patient Home"));
		}
	}

}