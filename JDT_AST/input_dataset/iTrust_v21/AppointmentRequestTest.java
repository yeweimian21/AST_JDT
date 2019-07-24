package edu.ncsu.csc.itrust.selenium;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class AppointmentRequestTest  extends iTrustSeleniumTest{

	WebDriver driver;
	WebElement element;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp(); 
		gen.clearAllTables();
		gen.standardData();
		gen.hcp9();
		gen.apptRequestConflicts();
	}
	
	public void testAppointmnetRequestExpire() throws Exception {
		driver = login("9000000010", "pw"); //log in as Zoidberg
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
		//go to the page
		element = driver.findElement(By.linkText("Appointment Requests"));
		element.click();
		
		assertEquals("iTrust - View My Appointment Requests", driver.getTitle());
		assertLogged(TransactionType.APPOINTMENT_REQUEST_VIEW, 9000000010L, 0L, "");
		
		assertFalse(driver.findElement(By.xpath("//*[@id='iTrustContent']")).getText().contains("Request from: Philip Fry"));
	}
	
	public void testHCPAppointmentRequestConflictReject() throws Exception {
		driver = login("26", "pw"); //log in as Philip Fry
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		//go to the page
		element = driver.findElement(By.linkText("Appointment Requests"));
		element.click();
		assertEquals("iTrust - Appointment Requests", driver.getTitle());
		
		
		//create date info
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		cal.add(Calendar.DAY_OF_YEAR, 7);
		
		//fill form
		Select select;
		select = new Select (driver.findElement(By.name("apptType")));
		select.selectByValue("General Checkup");
		select = new Select (driver.findElement(By.name("time1")));
		select.selectByValue("01");
		select = new Select (driver.findElement(By.name("time2")));
		select.selectByValue("45");
		select = new Select (driver.findElement(By.name("time3")));
		select.selectByValue("PM");
		select = new Select (driver.findElement(By.name("lhcp")));
		select.selectByValue("9000000010");
		element = driver.findElement(By.name("startDate"));
		element.clear();
		element.sendKeys(format.format(cal.getTime()));;
		element.submit();
		
		//confirm that a warning was displayed, as the request conflicts with other appointments
		assertEquals("iTrust - Appointment Requests", driver.getTitle());
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']/span")).getText().contains("conflicts with other existing appointments"));
		
		//select another time for the appointment
		element = driver.findElement(By.xpath("//*[@id='iTrustContent']/div[1]/form/input[8]"));
		element.submit();
		assertLogged(TransactionType.APPOINTMENT_REQUEST_SUBMITTED, 26L, 9000000010L, "");
		
		//login to Zoidburg
		driver.close();
		driver = login("9000000010", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
		//go to the page
		element = driver.findElement(By.linkText("Appointment Requests"));
		element.click();
		assertEquals("iTrust - View My Appointment Requests", driver.getTitle());
		assertLogged(TransactionType.APPOINTMENT_REQUEST_VIEW, 9000000010L, 0L, "");
		
		//check to make sure the request went through and approve it
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']")).getText().contains("Philip"));
		driver.findElement(By.linkText("Approve")).click();		
	}
	
	public void testHCPAppointmentRequestNoConflictApprove() throws Exception {
		driver = login("26", "pw"); //log in as Philip Fry
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		//go to the page
		element = driver.findElement(By.linkText("Appointment Requests"));
		element.click();
		assertEquals("iTrust - Appointment Requests", driver.getTitle());
		
		
		//create date info
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		cal.add(Calendar.DAY_OF_YEAR, 1);
		
		//fill form
		Select select;
		select = new Select (driver.findElement(By.name("apptType")));
		select.selectByValue("General Checkup");
		select = new Select (driver.findElement(By.name("time1")));
		select.selectByValue("09");
		select = new Select (driver.findElement(By.name("time2")));
		select.selectByValue("45");
		select = new Select (driver.findElement(By.name("time3")));
		select.selectByValue("PM");
		select = new Select (driver.findElement(By.name("lhcp")));
		select.selectByValue("9000000010");
		element = driver.findElement(By.name("startDate"));
		element.clear();
		element.sendKeys(format.format(cal.getTime()));;
		element.submit();
		
		//confirm that the request was accepted
		assertEquals("iTrust - Appointment Requests", driver.getTitle());
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']")).getText().contains("Your appointment request has been saved and is pending."));
		assertLogged(TransactionType.APPOINTMENT_REQUEST_SUBMITTED, 26L, 9000000010L, "");
		
		//login to zoidburg
		driver.close();
		driver = login("9000000010", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
		//go to the page
		element = driver.findElement(By.linkText("Appointment Requests"));
		element.click();
		assertEquals("iTrust - View My Appointment Requests", driver.getTitle());
		assertLogged(TransactionType.APPOINTMENT_REQUEST_VIEW, 9000000010L, 0L, "");
		
		//check to make sure the request went through and approve it
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']")).getText().contains("Philip"));
		driver.findElement(By.linkText("Approve")).click();	
		
		//confirm on right page and that the appointment was approved
		assertEquals("iTrust - View My Appointment Requests", driver.getTitle());
		assertLogged(TransactionType.APPOINTMENT_REQUEST_APPROVED, 9000000010L, 26L, "");

	}
	
	public void testHCPAppointmentRequestNoConflictReject() throws Exception {
		driver = login("26", "pw"); //log in as Philip Fry
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		//go to the page
		element = driver.findElement(By.linkText("Appointment Requests"));
		element.click();
		assertEquals("iTrust - Appointment Requests", driver.getTitle());
		
		
		//create date info
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		cal.add(Calendar.DAY_OF_YEAR, 1);
		
		//fill form
		Select select;
		select = new Select (driver.findElement(By.name("apptType")));
		select.selectByValue("General Checkup");
		select = new Select (driver.findElement(By.name("time1")));
		select.selectByValue("09");
		select = new Select (driver.findElement(By.name("time2")));
		select.selectByValue("45");
		select = new Select (driver.findElement(By.name("time3")));
		select.selectByValue("PM");
		select = new Select (driver.findElement(By.name("lhcp")));
		select.selectByValue("9000000010");
		element = driver.findElement(By.name("startDate"));
		element.clear();
		element.sendKeys(format.format(cal.getTime()));;
		element.submit();
		
		//confirm that the request was accepted
		assertEquals("iTrust - Appointment Requests", driver.getTitle());
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']")).getText().contains("Your appointment request has been saved and is pending."));
		assertLogged(TransactionType.APPOINTMENT_REQUEST_SUBMITTED, 26L, 9000000010L, "");
		
		//login to zoidburg
		driver.close();
		driver = login("9000000010", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
		//go to the page
		element = driver.findElement(By.linkText("Appointment Requests"));
		element.click();
		assertEquals("iTrust - View My Appointment Requests", driver.getTitle());
		assertLogged(TransactionType.APPOINTMENT_REQUEST_VIEW, 9000000010L, 0L, "");
		
		//check to make sure the request went through and approve it
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']")).getText().contains("Philip"));
		driver.findElement(By.linkText("Reject")).click();	
		
		//confirm on right page and that the appointment was approved
		assertEquals("iTrust - View My Appointment Requests", driver.getTitle());
		assertLogged(TransactionType.APPOINTMENT_REQUEST_REJECTED, 9000000010L, 26L, "");
	}
	
	public void testPatientAppointmentRequestConflict() throws Exception {
		driver = login("26", "pw"); //log in as Philip Fry
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		//go to the page
		element = driver.findElement(By.linkText("Appointment Requests"));
		element.click();
		assertEquals("iTrust - Appointment Requests", driver.getTitle());
		
		
		//create date info
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		cal.add(Calendar.DAY_OF_YEAR, 7);
		
		//fill form
		Select select;
		select = new Select (driver.findElement(By.name("apptType")));
		select.selectByValue("General Checkup");
		select = new Select (driver.findElement(By.name("time1")));
		select.selectByValue("01");
		select = new Select (driver.findElement(By.name("time2")));
		select.selectByValue("45");
		select = new Select (driver.findElement(By.name("time3")));
		select.selectByValue("PM");
		select = new Select (driver.findElement(By.name("lhcp")));
		select.selectByValue("9000000010");
		element = driver.findElement(By.name("startDate"));
		element.clear();
		element.sendKeys(format.format(cal.getTime()));;
		element.submit();
		
		//confirm that a warning was displayed, as the request conflicts with other appointments
		assertEquals("iTrust - Appointment Requests", driver.getTitle());
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']")).getText().contains("conflicts with other existing appointments"));
		
		//select another time for the appointment
		element = driver.findElement(By.xpath("//*[@id='iTrustContent']/div[1]/form/input[8]"));
		element.submit();
		assertLogged(TransactionType.APPOINTMENT_REQUEST_SUBMITTED, 26L, 9000000010L, "");
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']")).getText().contains("Your appointment request has been saved and is pending."));
		
		
		//login to Zoidburg
		driver.close();
		driver = login("9000000010", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
		//go to the page
		element = driver.findElement(By.linkText("Appointment Requests"));
		element.click();
		assertEquals("iTrust - View My Appointment Requests", driver.getTitle());
		assertLogged(TransactionType.APPOINTMENT_REQUEST_VIEW, 9000000010L, 0L, "");
		
		//check to make sure the request went through
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']")).getText().contains("Philip"));
	}
	
	public void testPatientAppointmentRequestNoConflict() throws Exception {
		driver = login("26", "pw"); //log in as Philip Fry
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		//go to the page
		element = driver.findElement(By.linkText("Appointment Requests"));
		element.click();
		assertEquals("iTrust - Appointment Requests", driver.getTitle());
		
		
		//create date info
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		cal.add(Calendar.DAY_OF_YEAR, 1);
		
		//fill form
		Select select;
		select = new Select (driver.findElement(By.name("apptType")));
		select.selectByValue("General Checkup");
		select = new Select (driver.findElement(By.name("time1")));
		select.selectByValue("09");
		select = new Select (driver.findElement(By.name("time2")));
		select.selectByValue("45");
		select = new Select (driver.findElement(By.name("time3")));
		select.selectByValue("PM");
		select = new Select (driver.findElement(By.name("lhcp")));
		select.selectByValue("9000000010");
		element = driver.findElement(By.name("startDate"));
		element.clear();
		element.sendKeys(format.format(cal.getTime()));;
		element.submit();
		
		//confirm that the request was accepted
		assertEquals("iTrust - Appointment Requests", driver.getTitle());
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']")).getText().contains("Your appointment request has been saved and is pending."));
		assertLogged(TransactionType.APPOINTMENT_REQUEST_SUBMITTED, 26L, 9000000010L, "");
	}
	
	public void testBadDate() throws Exception {
		driver = login("26", "pw"); //log in as Philip Fry
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		//go to the page
		element = driver.findElement(By.linkText("Appointment Requests"));
		element.click();
		assertEquals("iTrust - Appointment Requests", driver.getTitle());
		
		//fill form
		Select select;
		select = new Select (driver.findElement(By.name("apptType")));
		select.selectByValue("General Checkup");
		select = new Select (driver.findElement(By.name("time1")));
		select.selectByValue("09");
		select = new Select (driver.findElement(By.name("time2")));
		select.selectByValue("45");
		select = new Select (driver.findElement(By.name("time3")));
		select.selectByValue("PM");
		select = new Select (driver.findElement(By.name("lhcp")));
		select.selectByValue("9000000010");
		element = driver.findElement(By.name("startDate"));
		element.clear();
		element.sendKeys("123/12/2014");
		element.submit();
		
		//Verify that the form was rejected
		assertTrue(driver.getPageSource().contains("ERROR: Date must by in the format: MM/dd/yyyy"));
	}
}