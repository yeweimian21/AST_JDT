package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.unit.DBBuilder;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Use Case 17, Selenium test conversion for HttpUnit GetVisitRemindersTest 
 */
@SuppressWarnings("unused")
public class GetVisitRemindersTest extends iTrustSeleniumTest {

	private WebDriver driver;

	@Before
	protected void setUp() throws Exception  {
		super.setUp();
		gen.hcp0();
		gen.standardData();
		driver = new Driver();
		// Implicitly wait at most 2 seconds for each element to load
		driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
		driver.get("http://localhost:8080/iTrust/");
	}

	/*
	 * Test navigation to iTrust - Visit Reminders page
	 */
	public void testGetVisitReminders_TestInitialPage() throws Exception {
		
		WebElement element;
		
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		driver.findElement(By.cssSelector("a[href= '/iTrust/auth/hcp/visitReminders.jsp']")).click();
		assertEquals("iTrust - Visit Reminders", driver.getTitle());
		
		element = driver.findElement(By.id("ReminderType"));
		assertTrue(element.getText().contains("Diagnosed Care Needers"));
		assertTrue(element.getText().contains("Flu Shot Needers"));
		assertTrue(element.getText().contains("Immunization Needers"));	
		
		element = driver.findElement(By.id("getReminders"));
		assertEquals(element.getAttribute(VALUE), "Get Reminders");
	}
	
	/*
	 * Test access to visit reminders for Diagnosed Care Needers
	 */
	public void testGetVisitReminders_DiagnosedCareNeeders() throws Exception {
	
		WebElement element;
	
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		
		driver.findElement(By.cssSelector("a[href= '/iTrust/auth/hcp/visitReminders.jsp']")).click();
		assertEquals("iTrust - Visit Reminders", driver.getTitle());
		
		Select dropDown = new Select(driver.findElement(By.id("ReminderType")));
		dropDown.selectByVisibleText("Diagnosed Care Needers");
		driver.findElement(By.id("getReminders")).submit();
		
		assertEquals("iTrust - Visit Reminders", driver.getTitle());
		List<WebElement> tableList = driver.findElements(By.className("fTable"));
		assertEquals(7, tableList.size());
		
		element = driver.findElement(By.cssSelector("a[href='sendEmailNotification.jsp?mid=10']"));
		assertEquals("Zappic Clith", element.getText());
		element = tableList.get(2);
		assertTrue(element.getText().contains("Phone Number:919-555-9213"));
		
		element = driver.findElement(By.cssSelector("a[href='sendEmailNotification.jsp?mid=1']"));
		assertEquals("Random Person", element.getText());
		element = tableList.get(3);
		assertTrue(element.getText().contains("Phone Number:919-971-0000"));
		
		element = driver.findElement(By.cssSelector("a[href='sendEmailNotification.jsp?mid=100']"));
		assertEquals("Anakin Skywalker", element.getText());
		element = tableList.get(4);
		assertTrue(element.getText().contains("Phone Number:919-419-5555"));
		
		element = driver.findElement(By.cssSelector("a[href='sendEmailNotification.jsp?mid=99']"));
		assertEquals("Darryl Thompson", element.getText());
		element = tableList.get(5);		
		assertTrue(element.getText().contains("Phone Number:919-555-6709"));

	}
	
	/*
	 * Test access to visit reminders for Flu Shot Needers
	 */
	public void testGetVisitReminders_FluShotNeeders() throws Exception {

		WebElement element;
		
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		driver.findElement(By.cssSelector("a[href= '/iTrust/auth/hcp/visitReminders.jsp']")).click();
		assertEquals("iTrust - Visit Reminders", driver.getTitle());
		
		Select dropDown = new Select(driver.findElement(By.id("ReminderType")));
		dropDown.selectByVisibleText("Flu Shot Needers");
		driver.findElement(By.id("getReminders")).submit();
		
		assertEquals("iTrust - Visit Reminders", driver.getTitle());
		List<WebElement> tableList = driver.findElements(By.className("fTable"));
		
		boolean thisYear = DateUtil.currentlyInMonthRange(8, 11);
		String pretext = "Missed";
		if (thisYear)
			pretext = "Currently Missing";
		
		element = driver.findElement(By.cssSelector("a[href='sendEmailNotification.jsp?mid=4']"));
		assertEquals("NoRecords Has", element.getText());
		element = tableList.get(1);

		assertTrue(element.getText().contains("Phone Number:919-971-0000"));
		assertTrue(element.getText().contains(pretext + " Medication:    Flu Shot"));
		
		element = driver.findElement(By.cssSelector("a[href='sendEmailNotification.jsp?mid=42']"));
		assertEquals("Bad Horse", element.getText());
		element = tableList.get(2);
		assertTrue(element.getText().contains("Phone Number:919-123-4567"));
		assertTrue(element.getText().contains(pretext + " Medication:    Flu Shot"));
		
		element = driver.findElement(By.cssSelector("a[href='sendEmailNotification.jsp?mid=3']"));
		assertEquals("Care Needs", element.getText());
		element = tableList.get(3);
		assertTrue(element.getText().contains("Phone Number:919-971-0000"));
		assertTrue(element.getText().contains(pretext + " Medication:    Flu Shot"));
		
		element = driver.findElement(By.cssSelector("a[href='sendEmailNotification.jsp?mid=1']"));
		assertEquals("Random Person", element.getText());
		element = tableList.get(4);
		assertTrue(element.getText().contains("Phone Number:919-971-0000"));
		assertTrue(element.getText().contains(pretext + " Medication:    Flu Shot"));
	}
	
	/*
	 * Test access to visit reminders for Immunization Needers
	 */
	public void testGetVisitReminders_ImmunizationNeeders() throws Exception {
		
		WebElement element;
		
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		driver.findElement(By.cssSelector("a[href= '/iTrust/auth/hcp/visitReminders.jsp']")).click();
		assertEquals("iTrust - Visit Reminders", driver.getTitle());
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		driver.findElement(By.cssSelector("a[href= '/iTrust/auth/hcp/visitReminders.jsp']")).click();
		assertEquals("iTrust - Visit Reminders", driver.getTitle());
	
		Select dropDown = new Select(driver.findElement(By.id("ReminderType")));
		dropDown.selectByVisibleText("Immunization Needers");
		driver.findElement(By.id("getReminders")).submit();
		
		assertEquals("iTrust - Visit Reminders", driver.getTitle());
		List<WebElement> tableList = driver.findElements(By.className("fTable"));
		assertEquals(0, tableList.size());
	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
	}


}