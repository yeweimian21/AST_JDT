package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class EditDemographicsTest extends iTrustSeleniumTest {
	
	private WebDriver driver = new HtmlUnitDriver();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	/*
	 * Authenticate UAP:
	 * MID: 8000000009
	 * Password: uappass1
	 * Choose Edit Patient.
	 * Select patient 2 and confirm.
	 * Change Field:
	 * Email address: ""
	 * Confirm and approve the selection
	 */
	public void testEditDemographics2() throws Exception {
		// login uap
		driver = login("8000000009", "uappass1");
		assertEquals("iTrust - UAP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
		
		driver.findElement(By.xpath("//*[@id=\"edit-menu\"]/ul/li[3]/a")).click();
		
		// choose Edit Patient
		// choose patient 2
		driver.get("http://localhost:8080/iTrust/" + "auth/getPatientID.jsp?UID_PATIENTID=2&forward=hcp-uap/editPatient.jsp");
		assertEquals(ADDRESS + "auth/hcp-uap/editPatient.jsp", driver.getCurrentUrl());
		
		// update email address to be blank
		driver.findElement(By.xpath("//*[@id=\"editForm\"]/table/tbody/tr/td[1]/table[1]/tbody/tr[4]/td[2]/input")).clear();
		driver.findElement(By.xpath("//*[@id=\"editForm\"]/div/input")).submit();
		
		// Make sure error was thrown
		assertTrue(driver.getPageSource().contains("Email: Up to 30 alphanumeric characters and symbols . and _ @"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 8000000009L, 2L, "");
	}

	/*
	 * Authenticate UAP:
	 * MID: 8000000009
	 * Password: uappass1
	 * Choose Edit Patient.
	 * Select patient 2 and confirm.
	 * Change Field:
	 * Street address 1: 100 New Address
	 * City: New Bern
	 * Zip: 28562
	 * Confirm and approve the selection
	 */
	public void testEditDemographics3() throws Exception {
		// login uap
		driver = login("8000000009", "uappass1");
		assertEquals("iTrust - UAP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
		
		driver.findElement(By.xpath("//*[@id=\"edit-menu\"]/ul/li[3]/a")).click();
		
		// choose Edit Patient
		// choose patient 2
		driver.get("http://localhost:8080/iTrust/" + "auth/getPatientID.jsp?UID_PATIENTID=2&forward=hcp-uap/editPatient.jsp");
		assertEquals(ADDRESS + "auth/hcp-uap/editPatient.jsp", driver.getCurrentUrl());
		
		// update street address, city and zip code
		driver.findElement(By.xpath("//*[@id=\"editForm\"]/table/tbody/tr/td[1]/table[1]/tbody/tr[5]/td[2]/input[1]")).clear();
		driver.findElement(By.xpath("//*[@id=\"editForm\"]/table/tbody/tr/td[1]/table[1]/tbody/tr[5]/td[2]/input[1]")).sendKeys("100 New Address");
		driver.findElement(By.xpath("//*[@id=\"editForm\"]/table/tbody/tr/td[1]/table[1]/tbody/tr[6]/td[2]/input")).clear();
		driver.findElement(By.xpath("//*[@id=\"editForm\"]/table/tbody/tr/td[1]/table[1]/tbody/tr[6]/td[2]/input")).sendKeys("New Bern");
		driver.findElement(By.xpath("//*[@id=\"editForm\"]/table/tbody/tr/td[1]/table[1]/tbody/tr[8]/td[2]/input")).clear();
		driver.findElement(By.xpath("//*[@id=\"editForm\"]/table/tbody/tr/td[1]/table[1]/tbody/tr[8]/td[2]/input")).sendKeys("28562");
		driver.findElement(By.xpath("//*[@id=\"editForm\"]/div/input")).submit();
		
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 8000000009L, 2L, "");
	}

	/*
	 * Authenticate UAP:
	 * MID: 8000000009
	 * Password: uappass1
	 * Choose Edit Patient.
	 * Select patient 2 and confirm.
	 * Change Field Street address 2 to a blank.
	 * Confirm and approve the selection
	 */
	public void testEditDemographics5() throws Exception {
		// login uap
		driver = login("8000000009", "uappass1");
		assertEquals("iTrust - UAP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
		
		driver.findElement(By.xpath("//*[@id=\"edit-menu\"]/ul/li[3]/a")).click();
		
		// choose Edit Patient
		// choose patient 2
		driver.get("http://localhost:8080/iTrust/" + "auth/getPatientID.jsp?UID_PATIENTID=2&forward=hcp-uap/editPatient.jsp");
		assertEquals(ADDRESS + "auth/hcp-uap/editPatient.jsp", driver.getCurrentUrl());
		
		// update email address to be blank
		driver.findElement(By.xpath("//*[@id=\"editForm\"]/table/tbody/tr/td[1]/table[1]/tbody/tr[5]/td[2]/input[2]")).clear();
		driver.findElement(By.xpath("//*[@id=\"editForm\"]/div/input")).submit();
		
		// Make sure the patient can have a blank address two
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 8000000009L, 2L, "");
	}

	/*
	 * Authenticate UAP:
	 * MID: 8000000009
	 * Password: uappass1
	 * Choose Edit Patient.
	 * Select patient 2 and confirm.
	 * Change phone to xxx-xxx-xxxx
	 * Confirm and approve the selection
	 */
	public void testEditDemographics6() throws Exception {
		// login uap
		driver = login("8000000009", "uappass1");
		assertEquals("iTrust - UAP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
		
		driver.findElement(By.xpath("//*[@id=\"edit-menu\"]/ul/li[3]/a")).click();
		
		// choose Edit Patient
		// choose patient 2
		driver.get("http://localhost:8080/iTrust/" + "auth/getPatientID.jsp?UID_PATIENTID=2&forward=hcp-uap/editPatient.jsp");
		assertEquals(ADDRESS + "auth/hcp-uap/editPatient.jsp", driver.getCurrentUrl());
		
		// update email address to be blank
		driver.findElement(By.xpath("//*[@id=\"editForm\"]/table/tbody/tr/td[3]/table[1]/tbody/tr[3]/td[2]/input")).clear();
		driver.findElement(By.xpath("//*[@id=\"editForm\"]/table/tbody/tr/td[3]/table[1]/tbody/tr[3]/td[2]/input")).sendKeys("xxx-xxx-xxxx");
		driver.findElement(By.xpath("//*[@id=\"editForm\"]/div/input")).submit();
		
		// Make sure that the form was not validated correctly
		assertTrue(driver.getPageSource().contains("This form has not been validated correctly."));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 8000000009L, 2L, "");
	}
	
	public void testEditDemographics7() throws Exception {
		// login as Patient 2
		driver = login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		// choose My Demographics
		driver.findElement(By.xpath("//*[@id=\"edit-menu\"]/ul/li/a")).click();
		assertTrue(driver.getTitle().equals("iTrust - Edit Patient"));
	}
}