package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.unit.DBBuilder;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Selenium test conversion for HttpUnit HealthDataChartTest
 */

@SuppressWarnings("unused")
public class HealthDataChartTest extends iTrustSeleniumTest {
	
	private HtmlUnitDriver driver;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();	
		driver = new HtmlUnitDriver();
		driver.get("http://localhost:8080/iTrust/");
		
	}

	/*
	 * Test ability to view line chart for patient weight
	 */
	public void testGetWeightLineChart() throws Exception {
		
		WebElement element;
		
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/hcp-uap/viewBasicHealth.jsp']")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		assertLogged(TransactionType.HCP_VIEW_BASIC_HEALTH_METRICS, 9000000000L, 2L, "");
		
		driver.findElement(By.id("viewWeightChart")).click();
		assertEquals("iTrust - Weight Chart", driver.getTitle());
		assertLogged(TransactionType.BASIC_HEALTH_CHARTS_VIEW, 9000000000L, 2L, "Weight");
	}
	
	/*
	 * Test if patient BMI is viewable
	 */
	public void testCalculateBMI() throws Exception {
		
		WebElement element;
		
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/hcp-uap/viewBasicHealth.jsp']")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		assertLogged(TransactionType.HCP_VIEW_BASIC_HEALTH_METRICS, 9000000000L, 2L, "");
		
		assertTrue(driver.getPageSource().contains("Andy Programmer's Basic Adult Health History"));
		element = driver.findElement(By.id("HealthRecordsTable"));
		
		boolean matchedOctober = false;
		boolean matchedAugust = false; 
		
		if(element.getText().contains("2007-08-12 08:34:58.0") && element.getText().contains("37.34"))
			matchedAugust = true;
		else if(element.getText().contains("2007-10-30 10:54:22.0") && element.getText().contains("38.24"))
			matchedOctober = true;
		
	}
	
	/*
	 * Test ability to view line chart for patient height
	 */
	public void testGetHeightLineChart() throws Exception {
		
		WebElement element;
		
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/hcp-uap/viewBasicHealth.jsp']")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		assertLogged(TransactionType.HCP_VIEW_BASIC_HEALTH_METRICS, 9000000000L, 2L, "");
		
		driver.findElement(By.id("viewHeightChart")).click();
		assertEquals("iTrust - Height Chart", driver.getTitle());
		assertLogged(TransactionType.BASIC_HEALTH_CHARTS_VIEW, 9000000000L, 2L, "Height");	
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}