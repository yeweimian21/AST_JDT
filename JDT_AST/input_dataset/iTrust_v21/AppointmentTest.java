package edu.ncsu.csc.itrust.selenium;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class AppointmentTest extends iTrustSeleniumTest{

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testAddApptPatientDeceased() throws Exception {
		//Login as HCP Kelly Doctor
		WebDriver driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		WebElement element;
		
		//go to the schedule appointment page
		element = driver.findElement(By.linkText("Schedule Appointment"));
		element.click();
		
		//use the old search to go to the patients page
		element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("2");
		element = driver.findElement(By.id("mainForm"));
		element.submit();
		
		//check to confirm cannot schedule appointment with dead patient
		element = driver.findElement(By.xpath("//*[@id='mainForm']/div/span"));
		assertTrue(element.getText().contains("Cannot schedule appointment"));
	}
	
	public void testEditApptConflictCancel() throws Exception {
		//Login as HCP Kelly Doctor
		gen.uc22();
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		driver.setJavascriptEnabled(true);
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		WebElement element;
		element = driver.findElement(By.linkText("View My Appointments"));
		element.click();		

		new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		DateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 12);
		c.set(Calendar.HOUR, 9);
		c.set(Calendar.AM_PM, Calendar.AM);
		c.set(Calendar.MINUTE, 45);
		
		
		//edit first entry to the desired time
		element = driver.findElements(By.linkText("Edit/Remove")).get(7);
		element.click();
		assertTrue(driver.getCurrentUrl().contains("http://localhost:8080/iTrust/auth/hcp/editAppt.jsp"));
		c.add(Calendar.DATE, -5);
		c.set(Calendar.HOUR, 10);
		c.set(Calendar.AM_PM, Calendar.AM);
		c.set(Calendar.MINUTE, 00);
		
		//fill form
		Select select;
		select = new Select (driver.findElement(By.name("apptType")));
		select.selectByIndex(0);
		select = new Select (driver.findElement(By.name("time1")));
		select.selectByIndex(9);
		select = new Select (driver.findElement(By.name("time2")));
		select.selectByIndex(0);
		select = new Select (driver.findElement(By.name("time3")));
		select.selectByIndex(0);
		element = driver.findElement(By.name("schedDate"));
		element.clear();
		element.sendKeys(format2.format(c.getTime()));
		driver.findElement(By.id("changeButton")).click();
		
		assertTrue(driver.getPageSource().contains("Warning"));
		assertNotLogged(TransactionType.APPOINTMENT_ADD, 9000000000L, 1L, "");
		//click 'cancel'
		driver.findElement(By.id("cancel")).click();
		 
		//fill form
		select = new Select (driver.findElement(By.name("apptType")));
		select.selectByIndex(0);
		select = new Select (driver.findElement(By.name("time1")));
		select.selectByIndex(1);
		select = new Select (driver.findElement(By.name("time2")));
		select.selectByIndex(0);
		select = new Select (driver.findElement(By.name("time3")));
		select.selectByIndex(1);
		element = driver.findElement(By.name("schedDate"));
		element.clear();
		element.sendKeys(format2.format(c.getTime()));
		driver.findElement(By.id("changeButton")).click();
		
		//confirm warning displayed and appointment not edited
		assertFalse(driver.getPageSource().contains("Warning"));		
	}
	
	public void testAddApptConflictNoOverride() throws Exception {
		gen.uc22();
		//Login
		WebDriver driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		WebElement element;
		element = driver.findElement(By.linkText("Schedule Appointment"));
		element.click();		
		
		//use the old search to go to the patients page
		element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("100");
		element = driver.findElement(By.id("mainForm"));
		element.submit();
		
		//set up date
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		cal.add(Calendar.DAY_OF_YEAR, 7);	
		
		//fill out form
		Select select;
		select = new Select (driver.findElement(By.name("apptType")));
		select.selectByValue("Physical");
		select = new Select (driver.findElement(By.name("time1")));
		select.selectByValue("09");
		select = new Select (driver.findElement(By.name("time2")));
		select.selectByValue("45");
		select = new Select (driver.findElement(By.name("time3")));
		select.selectByValue("AM");
		element = driver.findElement(By.name("schedDate"));
		element.clear();
		element.sendKeys(format.format(cal.getTime()));
		element.submit();
		
		//check to make sure warning displayed and add is not logged
		element = driver.findElement(By.xpath("//*[@id='conflictTable']/span"));
		assertTrue(element.getText().contains("Warning"));
		assertNotLogged(TransactionType.APPOINTMENT_ADD, 9000000000L, 100L, "");
	}
	
	public void testViewApptWithConflicts() throws Exception{
		gen.uc22();
		//Login
		WebDriver driver = login("100", "pw");
		assertLogged(TransactionType.HOME_VIEW, 100L, 0L, "");
		
		WebElement element;
		//go to the View My Appointments link
		element = driver.findElement(By.linkText("View My Appointments"));
		element.click();
		
		//confirm that appointments are showing
		assertFalse(driver.getPageSource().contains("You have no appointments"));
		assertLogged(TransactionType.APPOINTMENT_ALL_VIEW, 100L, 0L, "");
		
	}
	
	public void testAddApptSameEndStartTimes() throws Exception{
		gen.uc22();
		//Login
		WebDriver driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		WebElement element;
		element = driver.findElement(By.linkText("Schedule Appointment"));
		element.click();		
		
		//use the old search to go to the patients page
		element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("100");
		element = driver.findElement(By.id("mainForm"));
		element.submit();
		
		//set up date
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		cal.add(Calendar.DAY_OF_YEAR, 1);	
		
		//fill out form
		Select select;
		select = new Select (driver.findElement(By.name("apptType")));
		select.selectByValue("Physical");
		select = new Select (driver.findElement(By.name("time1")));
		select.selectByValue("10");
		select = new Select (driver.findElement(By.name("time2")));
		select.selectByValue("30");
		select = new Select (driver.findElement(By.name("time3")));
		select.selectByValue("AM");
		element = driver.findElement(By.name("schedDate"));
		element.clear();
		element.sendKeys(format.format(cal.getTime()));
		element.submit();
		
		//check that the appointment was successfully added
		assertTrue(driver.findElement(By.xpath("//*[@id='apptDiv']/span[1]")).getText().contains("Success: Physical"));
		assertLogged(TransactionType.APPOINTMENT_ADD, 9000000000L, 100L, "");
		
	}
	
	public void testAddApptInvalidDate() throws Exception {
		gen.uc22();
		//Login
		WebDriver driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		WebElement element;
		element = driver.findElement(By.linkText("Schedule Appointment"));
		element.click();		
		
		//use the old search to go to the patients page
		element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("100");
		element = driver.findElement(By.id("mainForm"));
		element.submit();
		
		//fill out form
		Select select;
		select = new Select (driver.findElement(By.name("apptType")));
		select.selectByValue("Physical");
		select = new Select (driver.findElement(By.name("time1")));
		select.selectByValue("09");
		select = new Select (driver.findElement(By.name("time2")));
		select.selectByValue("45");
		select = new Select (driver.findElement(By.name("time3")));
		select.selectByValue("AM");
		element = driver.findElement(By.name("schedDate"));
		element.clear();
		element.sendKeys("38/38/2025");
		element.submit();
		
		//check to make sure that appointment with invalid date was not added 
		assertNotLogged(TransactionType.APPOINTMENT_ADD, 9000000000L, 100L, "");
	}
	
	
	
}