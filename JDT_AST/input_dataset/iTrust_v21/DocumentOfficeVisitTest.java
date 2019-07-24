package edu.ncsu.csc.itrust.selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Tests that you can add new office visits as a UAP and HCP
 */
public class DocumentOfficeVisitTest extends iTrustSeleniumTest {

	/**
	 * set up standard test data
	 */
	public void setUp() throws Exception {
		super.setUp();
		gen.standardData();
	}
	
	/**
	 * Authenticate UAP
	 * MID 8000000009
	 * Password: uappass1
	 * Choose "Document Office Visit"
	 * Enter Patient MID 1
	 * Enter Fields:
	 * Date: 2005-11-21
	 * Notes: "I like diet-coke"
	 */
	public void testDocumentOfficeVisit6() throws Exception {
		//login UAP
		WebDriver driver = new HtmlUnitDriver();
		driver = login("8000000009", "uappass1");
		
		//make sure we are on the home page for UAP's
		assertEquals("iTrust - UAP Home",driver.getTitle());
		
		//select to document an office visit
		driver.findElement(By.linkText("Document Office Visit")).click();
		//search for patient 1 by MID
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='1']")).submit();

		//we should now be on the document office visit page
		assertEquals(iTrustSeleniumTest.ADDRESS + 
				"auth/hcp-uap/documentOfficeVisit.jsp", 
				driver.getCurrentUrl().toString());
		//click the Yes, document office visit button
		WebElement form = driver.findElement(By.id("formMain"));
		form.submit();
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		WebElement ovForm = driver.findElement(By.id("mainForm"));
		WebElement ovDate = ovForm.findElement(By.name("visitDate"));
		ovDate.clear();
		ovDate.sendKeys("11/21/2005");
		WebElement ovNotes = ovForm.findElement(By.name("notes"));
		ovNotes.sendKeys("I like diet-coke");
		
		//get the dropdown options for the apptType
		Select apptOptions = new Select(driver.findElement(By.name("apptType")));
		apptOptions.selectByIndex(0);
		ovForm.findElement(By.id("update")).click();
		
		//check that the creation was successful
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 8000000009L, 1L, "Office visit");
		
	}
	
	/**
	 * Authenticate HCP
	 * MID 9000000000
	 * Password: pw
	 * Choose Document Office Visit
	 * Enter Patient MID 2 and confirm
	 * Choose to document new office visit.
	 * Enter Fields:
	 * Date: 2005-11-2
	 * Notes: Great patient!
	 */
	public void testDocumentOfficeVisit1() throws Exception {
		//login
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
		//click Document Office Visit
		driver.findElement(By.linkText("Document Office Visit")).click();
		
		//search for patient 2 by MID
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='2']")).submit();
		
		assertEquals(iTrustSeleniumTest.ADDRESS + 
				"auth/hcp-uap/documentOfficeVisit.jsp", 
				driver.getCurrentUrl().toString());
		
		//click to document an office visit
		WebElement form = driver.findElement(By.id("formMain"));
		form.submit();
		
		//now create an office visit
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		WebElement ovForm = driver.findElement(By.id("mainForm"));
		WebElement ovDate = ovForm.findElement(By.name("visitDate"));
		ovDate.clear();
		ovDate.sendKeys("11/02/2005");
		WebElement ovNotes = ovForm.findElement(By.name("notes"));
		ovNotes.sendKeys("Great Patient!");
		
		ovForm.findElement(By.id("update")).click();
		
		//check that the creation was successful
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 2L, "Office visit");
	}
	
	/**
	 * Authenticate HCP
	 * MID 9000000000
	 * Password: pw
	 * Choose Document Office Visit
	 * Enter Patient MID 2 and confirm
	 * Choose to document new office vist.
	 * Enter Fields:
	 * Date: 2005-11-21
	 * Notes: <script>alert('ha ha ha');</script>
	 */
	public void testDocumentOfficeVisit2() throws Exception {
		//login
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
		//click document office visit
		driver.findElement(By.linkText("Document Office Visit")).click();
		//choose patient 2
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='2']")).submit();
		
		assertEquals(iTrustSeleniumTest.ADDRESS + 
				"auth/hcp-uap/documentOfficeVisit.jsp", 
				driver.getCurrentUrl().toString());
		
		//click yes, document office visit
		WebElement form = driver.findElement(By.id("formMain"));
		form.submit();
		
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		//add a new office visit
		WebElement ovForm = driver.findElement(By.id("mainForm"));
		WebElement date = ovForm.findElement(By.name("visitDate"));
		date.clear();
		date.sendKeys("11/21/2005");
		WebElement notes = ovForm.findElement(By.name("notes"));
		notes.sendKeys("<script>alert('ha ha ha');</script>");
		ovForm.submit();
		assertTrue(driver.getPageSource().contains("Notes: Up to 300 alphanumeric characters, with space, and other punctuation"));
		assertNotLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 2L, "Office visit");
	}
	
	/**
	 * Tests that the notes section of a new office visit can include
	 * a semicolon.
	 * @throws Exception
	 */
	public void testUpdateOfficeVisitSemicolon() throws Exception {
		//login UAP
		WebDriver driver = new HtmlUnitDriver();
		driver = login("8000000009", "uappass1");
		assertEquals("iTrust - UAP Home", driver.getTitle());
		//click document office visit
		driver.findElement(By.linkText("Document Office Visit")).click();
		//choose patient 1
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='1']")).submit();
		
		assertEquals(iTrustSeleniumTest.ADDRESS + 
				"auth/hcp-uap/documentOfficeVisit.jsp", 
				driver.getCurrentUrl().toString());
		
		//click yes, document office visit
		WebElement form = driver.findElement(By.id("formMain"));
		form.submit();
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		//add a new office visit
		WebElement ovForm = driver.findElement(By.id("mainForm"));
		WebElement date = ovForm.findElement(By.name("visitDate"));
		date.clear();
		date.sendKeys("11/21/2005");
		WebElement notes = ovForm.findElement(By.name("notes"));
		notes.sendKeys("I like diet-coke ;");
		ovForm.submit();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 8000000009L, 1L, "Office visit");
	}
	
	/**
	 * Authenticate UAP
	 * MID 8000000009
	 * Password: uappass1
	 * Choose Document Office Visit
	 * Enter Patient MID 1 and confirm
	 * Choose to document new office vist.
	 * Enter Fields:
	 * Date: 2005-11-21
	 */
	public void testUpdateOfficeVisitOctothorpe() throws Exception {
		//login UAP
		WebDriver driver = new HtmlUnitDriver();
		driver = login("8000000009", "uappass1");
		assertEquals("iTrust - UAP Home", driver.getTitle());
		//click document office visit
		driver.findElement(By.linkText("Document Office Visit")).click();
		//choose patient 1
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='1']")).submit();
		
		assertEquals(iTrustSeleniumTest.ADDRESS + 
				"auth/hcp-uap/documentOfficeVisit.jsp", 
				driver.getCurrentUrl().toString());
		
		//click yes, document office visit
		WebElement form = driver.findElement(By.id("formMain"));
		form.submit();
		//add a new office visit
		WebElement ovForm = driver.findElement(By.id("mainForm"));
		WebElement date = ovForm.findElement(By.name("visitDate"));
		date.clear();
		date.sendKeys("11/21/2005");
		WebElement notes = ovForm.findElement(By.name("notes"));
		notes.sendKeys("I like diet-coke #");
		Select apptType = new Select(driver.findElement(By.name("apptType")));
		apptType.selectByIndex(0);
		ovForm.submit();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 8000000009L, 1L, "Office visit");
	}	
}
