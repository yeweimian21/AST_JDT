package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class AddNDTylenolTest extends iTrustSeleniumTest{
	
	protected WebDriver driver;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		
	}
	
	public void testCreateValidHCP() throws Exception {
		driver = login("9000000001", "pw");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		
		driver.findElement(By.linkText("Edit ND Codes")).click();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		
		//Set elements to forms
		driver.findElement(By.xpath("//form[1]//input[@name='code1']")).sendKeys("55154");
		driver.findElement(By.xpath("//form[1]//input[@name='code2']")).sendKeys("1922");
		driver.findElement(By.xpath("//form[1]//input[@name='description']")).sendKeys("Tylenol Tablets");
		driver.findElement(By.xpath("//form[1]//input[@type='submit' and @name='add']")).click();
		
		assertLogged(TransactionType.DRUG_CODE_ADD, 9000000001L, 0L, "551541922");
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		assertTrue(driver.findElement(By.xpath("//body")).getText().contains("Success: 551541922 - Tylenol Tablets added"));
		assertLogged(TransactionType.DRUG_CODE_VIEW, 9000000001L, 0L, "");
	}
	
	
	
	
}