package edu.ncsu.csc.itrust.selenium;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.selenium.iTrustSeleniumTest;

public class CalendarTest extends iTrustSeleniumTest {
	
    private HtmlUnitDriver driver = null;

	protected void setUp() throws Exception {
	    // Create a new instance of the driver
	    driver = new HtmlUnitDriver();

		super.setUp(); // clear tables is called in super
		gen.clearAllTables();
		gen.standardData();
		//gen.officeVisit5();
		gen.officeVisits();
	}

	@Override
	protected void tearDown() throws Exception {
		gen.clearAllTables();
		//gen.standardData();
	}

	public void testHCPViewAppointmentCalendar() throws Exception {
		// Login	
        driver = (HtmlUnitDriver) login("9000000000", "pw");
        assertTrue(driver.getTitle().contains("iTrust - HCP Home"));  

		// Click Calendar
        driver.findElement(By.linkText("Appointment Calendar")).click();

		// check title
		assertTrue(driver.getTitle().contains("Appointment Calendar"));
		assertLogged(TransactionType.CALENDAR_VIEW, 9000000000L, 0L, "");

		// check for the right appointments
		WebElement tableElem = driver.findElement(By.id("calendarTable"));
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		
		while(rowsOnTable.hasNext()) {
			WebElement row = rowsOnTable.next();
			List<WebElement> j = row.findElements(By.tagName("td"));
			Iterator<WebElement> columnsOnTable = j.iterator();

			while(columnsOnTable.hasNext()) {
				WebElement column = columnsOnTable.next();
			
				if(column.getText().startsWith("5")){
					// On the 5th: 1:30PM - General Checkup
					assertTrue(column.getText().contains("General Checkup"));
				} else if(column.getText().startsWith("18")){
					// On the 18th: 8:00AM - Colonoscopy
					assertTrue(column.getText().contains("Colonoscopy"));
				}
				else if(column.getText().startsWith("28")){
					// On the 28th: 9:00AM - Physical
					assertTrue(column.getText().contains("Physical"));
				}
			}
		}
	}

	public void testPatientViewFullCalendarOfficeVisitDetails() throws Exception {
		// Login
		driver = (HtmlUnitDriver) login("2", "pw");
        assertTrue(driver.getTitle().contains("iTrust - Patient Home"));

		// Click Calendar
        driver.findElement(By.linkText("Full Calendar")).click();

		// check title
		assertTrue(driver.getTitle().contains("Appointment Calendar"));
		assertLogged(TransactionType.CALENDAR_VIEW, 2L, 0L, "");

		// Patient 2 clicks the  487.00-Influenza  link on the 10th of the month.
        List<WebElement> elements = driver.findElements(By.tagName("a"));
        for(WebElement element : elements) {
        	System.out.println(element.getAttribute("name"));
        	if(element.getAttribute("name") != null && element.getAttribute("name").equals("487.00-Influenza-10")) {
        		element.click();
        		break;
        	}
        }

		// Date of Visit: <current month> 10, <current year>.
		// Physician: Kelly Doctor.
		// Note: Terrible cough.
		// Diagnoses: 487-Influenza.
		// Medical Procedures: 1270F-Injection Procedure.
		// Lab Procedure: No laboratory procedures on record.
		// Medications Prescribed: No prescriptions on record.
		// Immunizations: 90657-Influenza virus vaccine, split.

		assertTrue(driver.getPageSource().contains("Kelly Doctor"));
		assertTrue(driver.getPageSource().contains("Terrible cough."));
		assertTrue(driver.getPageSource().contains("487.00"));
		assertTrue(driver.getPageSource().contains("Influenza"));
		assertTrue(driver.getPageSource().contains("No Medications on record"));
		assertTrue(driver.getPageSource().contains("1270F"));
		assertTrue(driver.getPageSource().contains("Injection procedure"));
		assertTrue(driver.getPageSource().contains("90657"));
		assertTrue(driver.getPageSource().contains("Influenza virus vaccine, split"));
	}

	public void testPatientViewFullCalendarPrescriptionDetails() throws Exception {
		// Login
		driver = (HtmlUnitDriver) login("2", "pw");
        assertTrue(driver.getTitle().contains("iTrust - Patient Home"));

        // Click Calendar
        driver.findElement(By.linkText("Full Calendar")).click();

		// check title
		assertTrue(driver.getTitle().contains("Appointment Calendar"));
		assertLogged(TransactionType.CALENDAR_VIEW, 2L, 0L, "");

		// Patient 2 clicks the  487.00-Influenza  link on the 10th of the month.
        List<WebElement> elements = driver.findElements(By.tagName("a"));
        for(WebElement element : elements) {
        	System.out.println(element.getAttribute("name"));
        	if(element.getAttribute("name") != null && element.getAttribute("name").equals("664662530-Penicillin-21")) {
        		element.click();
        		break;
        	}
        }
		//driver.findElement(By.partialLinkText("664662530-Penicillin-21")).click();

		// Date prescribed: <current month> 21, <current year>.
		// Physician: Gandalf Stormcrow.
		// Medication: 664662530-Penicillin.
		// Start Date: <Current month> 21, <current year>.
		// End Date: <60 days from the current date>.
		// Instructions: Administer every 6 hours after meals.

		assertTrue(driver.getPageSource().contains("Gandalf Stormcrow"));
		assertTrue(driver.getPageSource().contains("Penicillin (664662530)"));
		assertTrue(driver.getPageSource().contains("250mg"));
		assertTrue(driver.getPageSource().contains("Administer every 6 hours after meals"));
		assertLogged(TransactionType.PRESCRIPTION_REPORT_VIEW, 2L, 2L, "");

		// calculate date range
		Calendar cal = Calendar.getInstance();
		int month1 = cal.get(Calendar.MONTH) + 1;
		int day1 = 21;
		int year1 = cal.get(Calendar.YEAR);

		assertTrue(driver.getPageSource().contains(month1 + "/" + day1 + "/" + year1 + " to "));
	}
	
	public void testHCPViewAppointmentCalendarDetails() throws Exception {
		// Login
        driver = (HtmlUnitDriver) login("9000000000", "pw");
        assertTrue(driver.getTitle().contains("iTrust - HCP Home"));  

		// Click Calendar
        driver.findElement(By.linkText("Appointment Calendar")).click();

		// check title
		assertTrue(driver.getTitle().contains("Appointment Calendar"));
		assertLogged(TransactionType.CALENDAR_VIEW, 9000000000L, 0L, "");
		
		List<WebElement> links = driver.findElements(By.tagName("a"));

		int count = 0;
		//get the second link with General Checkup-5
		for(int i = 0; i < links.size(); i++) {
			String name = links.get(i).getAttribute("name");
			
			if(name != null && name.contains("General Checkup-5")) {
				count++;
				if(count == 2) {
					links.get(i).click();
					break;
				}
				
			}
		}
		
		//ensure proper data is showing up
		assertTrue(driver.getPageSource().contains("Andy Programmer"));
		assertTrue(driver.getPageSource().contains("General Checkup"));
		
		assertTrue(driver.getPageSource().contains("45 minutes"));
		assertTrue(driver.getPageSource().contains("No Comment"));
		
		//get the current month and year
		Calendar cal = Calendar.getInstance();
		int month1 = cal.get(Calendar.MONTH) + 1;
		int day1 = 5;
		int year1 = cal.get(Calendar.YEAR);
		assertTrue(driver.getPageSource().contains(month1 + "/0" + day1 + "/" + year1 + " 09:10 AM"));
		
	}

	
}