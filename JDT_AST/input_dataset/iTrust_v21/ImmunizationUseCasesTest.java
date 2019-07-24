package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.unit.DBBuilder;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;


@SuppressWarnings("unused")
/**
 * Use Cases 9, 11 & 17
 * Selenium conversion for HttpUnit ImmunizationUseCasesTest
 */
public class ImmunizationUseCasesTest extends iTrustSeleniumTest {

	private WebDriver driver;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();	
		driver = new HtmlUnitDriver();
		driver.get("http://localhost:8080/iTrust/");
	}

	/*
	 * Test viewing immunizations for Bowser Koopa and Princess Peach
	 */
	public void testDocumentAndViewImmunizations() throws Exception {
		
		WebElement element;
		
		driver.findElement(By.id("j_username")).sendKeys("9000000003");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		assertLogged(TransactionType.HOME_VIEW, 9000000003L, 0L, "");
		
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/hcp/visitReminders.jsp']")).click();
		assertEquals("iTrust - Visit Reminders", driver.getTitle());
		
		Select dropDown = new Select(driver.findElement(By.id("ReminderType")));
		dropDown.selectByVisibleText("Immunization Needers");
		driver.findElement(By.id("getReminders")).submit();
		
		List <WebElement> tableList = driver.findElements(By.cssSelector("table[class='fTable']"));
	
		assertTrue(tableList.get(0).getText().contains("Bowser Koopa"));
		assertTrue(tableList.get(0).getText().contains("Needs Immunization:"));
		assertTrue(tableList.get(0).getText().contains("90371 Hepatitis B (birth), 90681 Rotavirus (6 weeks), 90696 Diphtheria, Tetanus, Pertussis (6 weeks), 90645 Haemophilus influenzae (6 weeks), 90669 Pneumococcal (6 weeks), 90712 Poliovirus (6 weeks), 90707 Measles, Mumps, Rubekka (12 months), 90396 Varicella (12 months), 90633 Hepatits A (12 months)"));
		
		assertTrue(tableList.get(1).getText().contains("Princess Peach"));
		assertTrue(tableList.get(1).getText().contains("Needs Immunization:"));
		assertTrue(tableList.get(1).getText().contains("90371 Hepatitis B (birth), 90681 Rotavirus (6 weeks), 90696 Diphtheria, Tetanus, Pertussis (6 weeks), 90645 Haemophilus influenzae (6 weeks), 90669 Pneumococcal (6 weeks), 90712 Poliovirus (6 weeks), 90707 Measles, Mumps, Rubekka (12 months), 90396 Varicella (12 months), 90633 Hepatits A (12 months)"));
	
		assertLogged(TransactionType.PATIENT_REMINDERS_VIEW, 9000000003L, 0L, "");
	}
	
	
	/*
	 * Test viewing immunization records
	 */
	public void testViewImmunizationRecord() throws Exception {
		
		WebElement element;
		
		driver.findElement(By.id("j_username")).sendKeys("6");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		assertLogged(TransactionType.HOME_VIEW, 6L, 0L, "");
		
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/patient/viewMyRecords.jsp']")).click();
		assertEquals("iTrust - View My Records", driver.getTitle());
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 6L, 6L, "");
		
		List <WebElement> tableList = driver.findElements(By.cssSelector("table[class='fTable']"));
		element = tableList.get(6);
		
		assertTrue(element.getText().contains("90649Human Papillomavirus2004-07-10"));
		assertTrue(element.getText().contains("90649Human Papillomavirus2004-05-10"));
		assertTrue(element.getText().contains("90707Measles, Mumps, Rubella1999-05-10"));
		assertTrue(element.getText().contains("90396Varicella1999-05-10"));
		assertTrue(element.getText().contains("90633Hepatitis A1996-11-10"));
		assertTrue(element.getText().contains("90645Haemophilus influenzae1996-05-10"));
		assertTrue(element.getText().contains("90707Measles, Mumps, Rubella1996-05-10"));
		assertTrue(element.getText().contains("90396Varicella1996-05-10"));
		assertTrue(element.getText().contains("90633Hepatitis A1996-05-10"));
		assertTrue(element.getText().contains("90696Diphtheria, Tetanus, Pertussis1995-11-10"));
		assertTrue(element.getText().contains("90669Pneumococcal1995-11-10"));
		assertTrue(element.getText().contains("90712Poliovirus1995-11-10"));
		assertTrue(element.getText().contains("90681Rotavirus1995-09-10"));
		assertTrue(element.getText().contains("90696Diphtheria, Tetanus, Pertussis1995-09-10"));
		assertTrue(element.getText().contains("90645Haemophilus influenzae1995-09-10"));
		assertTrue(element.getText().contains("90669Pneumococcal1995-09-10"));
		assertTrue(element.getText().contains("90712Poliovirus1995-09-10"));
		assertTrue(element.getText().contains("90681Rotavirus1995-06-22"));	
		assertTrue(element.getText().contains("90696Diphtheria, Tetanus, Pertussis1995-06-22"));
		assertTrue(element.getText().contains("90645Haemophilus influenzae1995-06-22"));
		assertTrue(element.getText().contains("90669Pneumococcal1995-06-22"));
		assertTrue(element.getText().contains("90712Poliovirus1995-06-22"));
		assertTrue(element.getText().contains("90371Hepatitis B1995-06-10"));
		assertTrue(element.getText().contains("90371Hepatitis B1995-05-10"));
	}
	
	/*
	 * Test viewing immunization records with "no data"
	 */
	public void testViewImmunizationRecord2() throws Exception {
		
		WebElement element; 
		
		driver.findElement(By.id("j_username")).sendKeys("2");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/patient/viewMyRecords.jsp']")).click();
		assertEquals("iTrust - View My Records", driver.getTitle());
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
		
		List <WebElement> tableList = driver.findElements(By.cssSelector("table[class='fTable']"));
		element = tableList.get(6);
		assertTrue(element.getText().contains("No Data"));
		
	}
	
	/*
	 * HCP views immunization records of Patient (MID 6) 
	 */
	public void testDocumentImmunization() throws Exception {
		
		WebElement element;
		
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/hcp-uap/documentOfficeVisit.jsp']")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("6");
		driver.findElement(By.xpath("//input[@value='6']")).submit();
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/hcp-uap/editOfficeVisit.jsp?ovID=2029']")).click();
		
		
		element = driver.findElement(By.id("immunizationsTable"));
		assertTrue(element.getText().contains("90649"));
		assertLogged(TransactionType.OFFICE_VISIT_VIEW, 9000000000L, 6L, "Office visit");
		
	}
	
	/*
	 * HCP views and updates immunizations of Patient (MID 7)
	 */
	public void testDocumentImmunization2() throws Exception {
	
		WebElement element;
		
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/hcp-uap/documentOfficeVisit.jsp']")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("7");
		driver.findElement(By.xpath("//input[@value='7']")).submit();
		
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/hcp-uap/editOfficeVisit.jsp?ovID=3011']")).click();
		
		element = driver.findElement(By.id("immunizationsTable"));
		assertTrue(element.getText().contains("90696"));
		
		driver.findElement(By.cssSelector("a[href='javascript:removeImmID('3011');']")).submit();
		driver.navigate().to("http://localhost:8080/iTrust/auth/hcp-uap/editOfficeVisit.jsp");
		
		element = driver.findElement(By.id("immunizationsTable"));
		assertTrue(element.getText().contains("No immunizations on record"));
		
		assertLogged(TransactionType.OFFICE_VISIT_VIEW, 9000000000L, 7L, "Office visit");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}