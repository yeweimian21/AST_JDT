package edu.ncsu.csc.itrust.selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class CreditCardValidatorTest extends iTrustSeleniumTest {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.patient1();
		gen.clearLoginFailures();
	}


	public void testGoodMasterCards() throws Exception {
		// login patient 2
		WebDriver wd = login("1", "pw");
		assertEquals("iTrust - Patient Home", wd.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		// click on My Demographics
		wd.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
		
		Select creditCardType = new Select(wd.findElement(By.name("creditCardType")));
		creditCardType.selectByVisibleText("Mastercard");
		WebElement creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		creditCardNumber.sendKeys("5593090746812380");
		creditCardNumber.submit();
		
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		
		creditCardNumber.clear();
		creditCardNumber.sendKeys("5437693863890467");
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		creditCardNumber = wd.findElement(By.name("creditCardNumber"));

		creditCardNumber.clear();
		creditCardNumber.sendKeys("5343017708937494");
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
	}
	
	
	public void testBadMasterCards() throws Exception {
		// login patient 2
		WebDriver wd = login("1", "pw");
		assertEquals("iTrust - Patient Home", wd.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		// click on My Demographics
		wd.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
		
		Select creditCardType = new Select(wd.findElement(By.name("creditCardType")));
		creditCardType.selectByVisibleText("Mastercard");
		
		WebElement creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		creditCardNumber.sendKeys("1593090746812380");
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("not properly filled in: [Credit Card Number]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");

		
		creditCardType = new Select(wd.findElement(By.name("creditCardType")));
		creditCardType.selectByVisibleText("Mastercard");
		creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		creditCardNumber.clear();
		creditCardNumber.sendKeys("4539592576502361"); // Legit Visa
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("not properly filled in: [Credit Card Number]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
	}
	
	public void testGoodVisas() throws Exception {
		// login patient 2
		WebDriver wd = login("1", "pw");
		assertEquals("iTrust - Patient Home", wd.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		// click on My Demographics
		wd.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
		
		Select creditCardType = new Select(wd.findElement(By.name("creditCardType")));
		creditCardType.selectByVisibleText("Visa");
		WebElement creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		creditCardNumber.sendKeys("4539592576502361");
		creditCardNumber.submit();
		
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		
		creditCardNumber.clear();
		creditCardNumber.sendKeys("4716912133362668");
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		creditCardNumber = wd.findElement(By.name("creditCardNumber"));

		creditCardNumber.clear();
		creditCardNumber.sendKeys("4485333709241203");
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
	}
	
	
	public void testBadVisas() throws Exception {
		// login patient 2
		WebDriver wd = login("1", "pw");
		assertEquals("iTrust - Patient Home", wd.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		// click on My Demographics
		wd.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
		
		Select creditCardType = new Select(wd.findElement(By.name("creditCardType")));
		creditCardType.selectByVisibleText("Visa");
		
		WebElement creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		creditCardNumber.sendKeys("5593090746812380");
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("not properly filled in: [Credit Card Number]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");

		
		creditCardType = new Select(wd.findElement(By.name("creditCardType")));
		creditCardType.selectByVisibleText("Visa");
		creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		creditCardNumber.clear();
		creditCardNumber.sendKeys("6437693863890467");
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("not properly filled in: [Credit Card Number]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
	}
	
	public void testGoodDiscovers() throws Exception {
		// login patient 2
		WebDriver wd = login("1", "pw");
		assertEquals("iTrust - Patient Home", wd.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		// click on My Demographics
		wd.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
		
		Select creditCardType = new Select(wd.findElement(By.name("creditCardType")));
		creditCardType.selectByVisibleText("Discover");
		WebElement creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		creditCardNumber.sendKeys("6011263089803439");
		creditCardNumber.submit();
		
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		
		creditCardNumber.clear();
		creditCardNumber.sendKeys("6011953266156193");
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		creditCardNumber = wd.findElement(By.name("creditCardNumber"));

		creditCardNumber.clear();
		creditCardNumber.sendKeys("6011726402628022");
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
	}
	
	
	public void testBadDiscovers() throws Exception {
		// login patient 2
		WebDriver wd = login("1", "pw");
		assertEquals("iTrust - Patient Home", wd.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		// click on My Demographics
		wd.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
		
		Select creditCardType = new Select(wd.findElement(By.name("creditCardType")));
		creditCardType.selectByVisibleText("Discover");
		
		WebElement creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		creditCardNumber.sendKeys("5593090746812380");
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("not properly filled in: [Credit Card Number]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");

		
		creditCardType = new Select(wd.findElement(By.name("creditCardType")));
		creditCardType.selectByVisibleText("Discover");
		creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		creditCardNumber.clear();
		creditCardNumber.sendKeys("6437693863890467");
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("not properly filled in: [Credit Card Number]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
	}
	
	/*
	 * AMEX stands for American Express.
	 */
	public void testGoodAmex() throws Exception {
		// login patient 2
		WebDriver wd = login("1", "pw");
		assertEquals("iTrust - Patient Home", wd.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		// click on My Demographics
		wd.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
		
		Select creditCardType = new Select(wd.findElement(By.name("creditCardType")));
		creditCardType.selectByVisibleText("American Express");
		WebElement creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		creditCardNumber.sendKeys("343570480641495");
		creditCardNumber.submit();
		
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		
		creditCardNumber.clear();
		creditCardNumber.sendKeys("377199947956764");
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		creditCardNumber = wd.findElement(By.name("creditCardNumber"));

		creditCardNumber.clear();
		creditCardNumber.sendKeys("344558915054011");
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
	}
	
	
	public void testBadAmex() throws Exception {
		// login patient 2
		WebDriver wd = login("1", "pw");
		assertEquals("iTrust - Patient Home", wd.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		// click on My Demographics
		wd.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
		
		Select creditCardType = new Select(wd.findElement(By.name("creditCardType")));
		creditCardType.selectByVisibleText("American Express");
		
		WebElement creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		creditCardNumber.sendKeys("5593090746812380");
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("not properly filled in: [Credit Card Number]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");

		
		creditCardType = new Select(wd.findElement(By.name("creditCardType")));
		creditCardType.selectByVisibleText("American Express");
		creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		creditCardNumber.clear();
		creditCardNumber.sendKeys("6437693863890467");
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("not properly filled in: [Credit Card Number]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
	}
	
	public void testEmptyTypeEmptyNumber() throws Exception {
		// login patient 2
		WebDriver wd = login("1", "pw");
		assertEquals("iTrust - Patient Home", wd.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		// click on My Demographics
		wd.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
		
		Select creditCardType = new Select(wd.findElement(By.name("creditCardType")));
		creditCardType.selectByValue("");
				

		WebElement creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		creditCardNumber.sendKeys("");
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
	}
	
	
	public void testEmptyTypeFilledNumber() throws Exception {
		// login patient 2
		WebDriver wd = login("1", "pw");
		assertEquals("iTrust - Patient Home", wd.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		// click on My Demographics
		wd.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
		
		Select creditCardType = new Select(wd.findElement(By.name("creditCardType")));
		creditCardType.selectByValue("");
		
		WebElement creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		creditCardNumber.sendKeys("5593090746812380");
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("not properly filled in: [Credit Card Type]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");

		
		creditCardType = new Select(wd.findElement(By.name("creditCardType")));
		creditCardType.selectByValue("");
		creditCardNumber = wd.findElement(By.name("creditCardNumber"));
		creditCardNumber.clear();
		creditCardNumber.sendKeys("6437693863890467");
		creditCardNumber.submit();
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("not properly filled in: [Credit Card Type]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
	}
	
}
