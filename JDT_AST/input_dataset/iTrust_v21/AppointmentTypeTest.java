package edu.ncsu.csc.itrust.selenium;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class AppointmentTypeTest extends iTrustSeleniumTest{
	private WebDriver driver = null;
		
	@Before
	public void setUp() throws Exception {
	    // Create a new instance of the driver
	    driver = new HtmlUnitDriver();
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testAddAppointmentType() throws Exception {
		// HCP 9000000001 logs in.
		driver = (HtmlUnitDriver)login("9000000001", "pw");
		assertTrue(driver.getTitle().contains("iTrust - Admin Home"));
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		
		// HCP 9000000001 moves to the edit appointment types page.
		driver.findElement(By.linkText("Edit Appointment Types")).click();
		assertTrue(driver.getPageSource().contains("iTrust - Maintain Appointment Types"));
		assertLogged(TransactionType.APPOINTMENT_TYPE_VIEW, 9000000001L, 0L, "");
		
		// HCP 9000000001 adds a new appointment type.
		driver.findElement(By.name("name")).sendKeys("Immunization");
		driver.findElement(By.name("duration")).sendKeys("30");
        driver.findElement(By.name("add")).click();
		assertLogged(TransactionType.APPOINTMENT_TYPE_ADD, 9000000001L, 0L, "");
		assertLogged(TransactionType.APPOINTMENT_TYPE_VIEW, 9000000001L, 0L, "");
	}
	
	public void testEditAppointmentTypeDuration() throws Exception {
		// HCP 9000000001 logs in.
		driver = (HtmlUnitDriver)login("9000000001", "pw");
		assertTrue(driver.getTitle().contains("iTrust - Admin Home"));
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
				
		// HCP 9000000001 moves to the edit appointment types page.
		driver.findElement(By.linkText("Edit Appointment Types")).click();
		assertTrue(driver.getPageSource().contains("iTrust - Maintain Appointment Types"));
		assertLogged(TransactionType.APPOINTMENT_TYPE_VIEW, 9000000001L, 0L, "");
		
		// HCP 9000000001 edits an existing appointment type.
		driver.findElement(By.name("name")).sendKeys("Physical");
		driver.findElement(By.name("duration")).sendKeys("45");
		driver.findElement(By.name("update")).click();
		assertLogged(TransactionType.APPOINTMENT_TYPE_EDIT, 9000000001L, 0L, "");
		assertLogged(TransactionType.APPOINTMENT_TYPE_VIEW, 9000000001L, 0L, "");
	}
	
	public void testEditAppointmentTypeDurationStringInput() throws Exception {
		// HCP 9000000001 logs in.
		driver = (HtmlUnitDriver)login("9000000001", "pw");
		assertTrue(driver.getTitle().contains("iTrust - Admin Home"));
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
						
		// HCP 9000000001 moves to the edit appointment types page.
		driver.findElement(By.linkText("Edit Appointment Types")).click();
		assertTrue(driver.getPageSource().contains("iTrust - Maintain Appointment Types"));
		assertLogged(TransactionType.APPOINTMENT_TYPE_VIEW, 9000000001L, 0L, "");
				
		// HCP 9000000001 edits an existing appointment type with a bad duration.
		driver.findElement(By.name("name")).sendKeys("Physical");
		driver.findElement(By.name("duration")).sendKeys("foo");
		driver.findElement(By.name("update")).click();
		assertNotLogged(TransactionType.APPOINTMENT_TYPE_EDIT, 9000000001L, 0L, "");
		
		assertTrue(driver.getPageSource().contains("Error: Physical - Duration: must be an integer value."));
		assertLogged(TransactionType.APPOINTMENT_TYPE_VIEW, 9000000001L, 0L, "");
	}
	
	public void testScheduleAppointment() throws Exception {
		// HCP 9000000000 logs in.
		driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertTrue(driver.getTitle().contains("iTrust - HCP Home"));
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
								
		// HCP 9000000000 moves to the schedule new appointment page.
		driver.findElement(By.linkText("Schedule Appointment")).click();
		assertTrue(driver.getPageSource().contains("iTrust - Please Select a Patient"));
		
		// HCP 9000000000 selects patient 1
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		driver.findElement(By.cssSelector("input[value='1']")).submit();
		assertTrue(driver.getPageSource().contains("iTrust - Schedule an Appointment"));
	
		// HCP 9000000000 inputs the appointment information and submits
		int year = Calendar.getInstance().get(Calendar.YEAR) + 1;
		String scheduledDate = "07/06/" + year;
		Select dropdown = new Select(driver.findElement(By.name("apptType")));
		dropdown.selectByValue("General Checkup");
		driver.findElement(By.name("schedDate")).clear();
		driver.findElement(By.name("schedDate")).sendKeys(scheduledDate);
		dropdown = new Select(driver.findElement(By.name("time1")));
		dropdown.selectByValue("09");
		dropdown = new Select(driver.findElement(By.name("time2")));
		dropdown.selectByValue("00");
		dropdown = new Select(driver.findElement(By.name("time3")));
		dropdown.selectByValue("AM");
		driver.findElement(By.name("comment")).sendKeys("This is the next checkup for your blood pressure medication.");
		driver.findElement(By.name("scheduleButton")).submit();

		assertTrue(driver.getPageSource().contains("iTrust - Schedule an Appointment"));
		assertTrue(driver.getPageSource().contains("Success"));
	}
	
	public void testPatientViewUpcomingAppointments() throws Exception {
		gen.clearAppointments();
		gen.appointmentCase1();
		
		// Patient 2 logs in.
		driver = (HtmlUnitDriver)login("2", "pw");
		assertTrue(driver.getTitle().contains("iTrust - Patient Home"));
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
										
		// Patient 2 moves to the view my appointments page.
		driver.findElement(By.linkText("View My Appointments")).click();
		assertTrue(driver.getPageSource().contains("iTrust - View My Messages"));
		
		// Create timestamp
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Timestamp time = new Timestamp(new Date().getTime());
				
		
		// Patient 2 checks for the right appointments
		WebElement tableElem = driver.findElements(By.tagName("table")).get(0);
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		int x = 0;
		while(rowsOnTable.hasNext()) {
			WebElement row = rowsOnTable.next(); 
			if(x == 1) {
				Timestamp time1 = new Timestamp(time.getTime()+(14*24*60*60*1000));
				String dt1 = dateFormat.format(new Date(time1.getTime()));
				assertTrue(row.getText().contains("Kelly Doctor"));
				assertTrue(row.getText().contains("General Checkup"));
				assertTrue(row.getText().contains(dt1 + " 10:30 AM"));
				assertTrue(row.getText().contains("45 minutes"));
				assertTrue(row.getText().contains("Read Comment"));
			}
			else if(x == 2) {
				assertTrue(row.getText().contains("Kelly Doctor"));
				assertTrue(row.getText().contains("Consultation"));
				assertTrue(row.getText().contains("06/04/" + (Calendar.getInstance().get(Calendar.YEAR)+1) + " 10:30 AM"));
				assertTrue(row.getText().contains("30 minutes"));
				assertTrue(row.getText().contains("Read Comment"));
			}
			else if(x == 3) {
				assertTrue(row.getText().contains("Kelly Doctor"));
				assertTrue(row.getText().contains("Colonoscopy"));
				assertTrue(row.getText().contains("10/14/" + (Calendar.getInstance().get(Calendar.YEAR)+1) + " 08:00 AM"));
				assertTrue(row.getText().contains("90 minutes"));
				assertTrue(row.getText().contains("No Comment"));
			}
			x++;
		}		
		assertLogged(TransactionType.APPOINTMENT_ALL_VIEW, 2L, 0L, "");
	}
	
	public void testHcpViewUpcomingAppointments() throws Exception {
		// Create DB for this test case
		gen.clearAppointments();
		gen.appointmentCase2();
		
		// HCP 9000000000 logs in.
		driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertTrue(driver.getTitle().contains("iTrust - HCP Home"));
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
												
		// HCP 9000000000 moves to the view my appointments page.
		driver.findElement(By.linkText("View My Appointments")).click();
		assertTrue(driver.getPageSource().contains("iTrust - View My Messages"));
		
		// Create timestamp
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Timestamp time = new Timestamp(new Date().getTime());
				
		
		// HCP 9000000000 checks for the right appointments
		WebElement tableElem = driver.findElements(By.tagName("table")).get(0);
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		int x = 0;
		while(rowsOnTable.hasNext()) {
			WebElement row = rowsOnTable.next(); 
			if(x == 1) {
				Timestamp time1 = new Timestamp(time.getTime()+(7*24*60*60*1000));
				String dt1 = dateFormat.format(new Date(time1.getTime()));
				assertTrue(row.getText().contains("Random Person"));
				assertTrue(row.getText().contains("Consultation"));
				assertTrue(row.getText().contains(dt1 + " 09:10 AM"));
				assertTrue(row.getText().contains("30 minutes"));
				assertTrue(row.getText().contains("No Comment"));
			}
			else if(x == 2) {
				Timestamp time1 = new Timestamp(time.getTime()+(7*24*60*60*1000));
				String dt1 = dateFormat.format(new Date(time1.getTime()));
				assertTrue(row.getText().contains("Baby Programmer"));
				assertTrue(row.getText().contains("General Checkup"));
				assertTrue(row.getText().contains(dt1 + " 09:30 AM"));
				assertTrue(row.getText().contains("45 minutes"));
				assertTrue(row.getText().contains("Read Comment"));
			}
			else if(x == 3) {
				Timestamp time2 = new Timestamp(time.getTime()+(10*24*60*60*1000));
				String dt2 = dateFormat.format(new Date(time2.getTime()));
				assertTrue(row.getText().contains("Baby Programmer"));
				assertTrue(row.getText().contains("General Checkup"));
				assertTrue(row.getText().contains(dt2 + " 04:00 PM"));
				assertTrue(row.getText().contains("45 minutes"));
				assertTrue(row.getText().contains("Read Comment"));
			}
			else if(x == 4) {
				Timestamp time3 = new Timestamp(time.getTime()+(14*24*60*60*1000));
				String dt3 = dateFormat.format(new Date(time3.getTime()));
				assertTrue(row.getText().contains("Random Person"));
				assertTrue(row.getText().contains("Ultrasound"));
				assertTrue(row.getText().contains(dt3 + " 01:30 PM"));
				assertTrue(row.getText().contains("30 minutes"));
				assertTrue(row.getText().contains("No Comment"));
			}
			else if(x == 5) {
				Timestamp time3 = new Timestamp(time.getTime()+(14*24*60*60*1000));
				String dt3 = dateFormat.format(new Date(time3.getTime()));
				assertTrue(row.getText().contains("Andy Programmer"));
				assertTrue(row.getText().contains("General Checkup"));
				assertTrue(row.getText().contains(dt3 + " 01:45 PM"));
				assertTrue(row.getText().contains("45 minutes"));
				assertTrue(row.getText().contains("No Comment"));
			}
			x++;
		}		
		assertLogged(TransactionType.APPOINTMENT_ALL_VIEW, 9000000000L, 0L, "");
	}
	
	public void testAddAppointmentTypeLengthZero() throws Exception {
		// HCP 9000000001 logs in.
		driver = (HtmlUnitDriver)login("9000000001", "pw");
		assertTrue(driver.getTitle().contains("iTrust - Admin Home"));
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
				
		// HCP 9000000001 moves to the edit appointment types page.
		driver.findElement(By.linkText("Edit Appointment Types")).click();
		assertTrue(driver.getPageSource().contains("iTrust - Maintain Appointment Types"));
		assertLogged(TransactionType.APPOINTMENT_TYPE_VIEW, 9000000001L, 0L, "");
		
		// HCP 9000000001 edits an existing appointment type.
		driver.findElement(By.name("name")).sendKeys("Immunization");
		driver.findElement(By.name("duration")).sendKeys("0");
		driver.findElement(By.name("add")).click();
		assertTrue(driver.getPageSource().contains("This form has not been validated correctly."));
	}
}