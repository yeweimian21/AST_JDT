package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class DesignateAndViewHCPUseCaseTest extends iTrustSeleniumTest {
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.patient_hcp_vists();
	}
	
	public void testReportSeenHCPs0() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		driver = login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		driver.findElement(By.linkText("My Providers")).click();
		assertEquals("iTrust - My Providers", driver.getTitle());
		
		assertEquals(driver.findElement(By.xpath("//table/tbody/tr[2]/td")).getText(), "Gandalf Stormcrow");
		assertEquals(driver.findElement(By.xpath("//table/tbody/tr[3]/td")).getText(), "Mary Shelley");
		assertEquals(driver.findElement(By.xpath("//table/tbody/tr[4]/td")).getText(), "Lauren Frankenstein");
		assertEquals(driver.findElement(By.xpath("//table/tbody/tr[5]/td")).getText(), "Jason Frankenstein");
		assertEquals(driver.findElement(By.xpath("//table/tbody/tr[6]/td")).getText(), "Kelly Doctor");
	}
	
	public void testReportSeenHCPs1() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		driver = login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		driver.findElement(By.linkText("My Providers")).click();
		assertEquals("iTrust - My Providers", driver.getTitle());
		
		WebElement removeID = driver.findElement(By.name("removeID"));
		removeID.clear();
		removeID.sendKeys("Jason Frakenstein");

		assertEquals(driver.findElement(By.xpath("//table/tbody/tr[5]/td")).getText(), "Jason Frankenstein");
	}
	
	public void testReportSeenHCPs2() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		driver = login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		driver.findElement(By.linkText("My Providers")).click();
		assertEquals("iTrust - My Providers", driver.getTitle());
		
		WebElement lastName = driver.findElement(By.name("filter_name"));
		lastName.sendKeys("Frank");
		WebElement specialty = driver.findElement(By.name("filter_specialty"));
		specialty.sendKeys("pediatrician");
		specialty.submit();
		
		assertEquals(driver.findElement(By.xpath("//table/tbody/tr/td")).getText(), "Lauren Frankenstein");
	}
}
