package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

/*
 * RequestRecordsReleaseTest contains http unit tests for requesting and viewing
 * patient records release requests via a patient, HCP, or UAP role. 
 * 
 */
public class RequestRecordsReleaseTest extends iTrustSeleniumTest {
	
	// Create a new instance of the html unit driver
	private WebDriver driver;
	private Select hos;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		driver = new HtmlUnitDriver();
		//go to iTrust home page
		driver.get("http://localhost:8080/iTrust/");
	}

	
	@Test
	/*
	 * Adding new record using patient 102 
	 */
	public void testPatientRequestNewRecordsRelease() throws Exception{
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("102");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.linkText("Request Records Release")).click();
		driver.findElement(By.id("submitReq")).click();
		//used for drop down selection
		hos = new Select(driver.findElement(By.name("releaseHospital")));
		hos.selectByIndex(1);
		driver.findElement(By.id("recFirstName")).sendKeys("Mike");
		driver.findElement(By.id("recLastName")).sendKeys("Myers");
		driver.findElement(By.id("recPhone")).sendKeys("919-123-1234");
		driver.findElement(By.id("recEmail")).sendKeys("mike.myers@hospital.org");
		driver.findElement(By.id("recHospitalName")).sendKeys("Testing Hospital");
		driver.findElement(By.id("recHospitalAddress1")).sendKeys("101 Testing Hospital Drive");
		driver.findElement(By.id("recHospitalAddress2")).sendKeys(" ");
		driver.findElement(By.id("recHospitalCity")).sendKeys("Raleigh");
		driver.findElement(By.id("recHospitalState")).sendKeys("NC");
		driver.findElement(By.id("recHospitalZip")).sendKeys("27606");
		driver.findElement(By.id("releaseJustification")).sendKeys("Annual records request");
		driver.findElement(By.id("verifyForm")).click();
		driver.findElement(By.id("digitalSig")).sendKeys("Caldwell Hudson");
		driver.findElement(By.id("submit")).click();
		
		//testing if the new record successfully added 
		Assert.assertTrue(driver.getPageSource().contains(("Request successfully sent")));
		
	}
	
	@Test
	/*
	 * Adding a new record without patient signature
	 */
	public void testMedicalRecordsRelease_Patient_NoSignature() throws Exception{
		
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("102");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.linkText("Request Records Release")).click();
		driver.findElement(By.id("submitReq")).click();
		hos = new Select(driver.findElement(By.name("releaseHospital")));
		hos.selectByIndex(1);
		driver.findElement(By.id("recFirstName")).sendKeys("Mike");
		driver.findElement(By.id("recLastName")).sendKeys("Myers");
		driver.findElement(By.id("recPhone")).sendKeys("919-123-1234");
		driver.findElement(By.id("recEmail")).sendKeys("mike.myers@hospital.org");
		driver.findElement(By.id("recHospitalName")).sendKeys("Testing Hospital");
		driver.findElement(By.id("recHospitalAddress1")).sendKeys("101 Testing Hospital Drive");
		driver.findElement(By.id("recHospitalAddress2")).sendKeys("");
		driver.findElement(By.id("recHospitalCity")).sendKeys("Raleigh");
		driver.findElement(By.id("recHospitalState")).sendKeys("NC");
		driver.findElement(By.id("recHospitalZip")).sendKeys("27606");
		driver.findElement(By.id("releaseJustification")).sendKeys("Annual records request");
		driver.findElement(By.id("submit")).click();
		
		//testing if a new record not added
		Assert.assertFalse(driver.getPageSource().contains(("Request successfully sent")));
	}
	
	
	@Test
	/*
	 * testing for adding new record without filling the required field
	 */
	public void testMedicalRecordsRelease_Patient_NotAllFields() throws Exception{
		
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("102");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.linkText("Request Records Release")).click();
		driver.findElement(By.id("submitReq")).click();
		hos = new Select(driver.findElement(By.name("releaseHospital")));
		hos.selectByIndex(1);
		driver.findElement(By.id("verifyForm")).click();
		driver.findElement(By.id("digitalSig")).sendKeys("Caldwell Hudson");
		driver.findElement(By.id("submit")).click();
		
		Assert.assertFalse(driver.getPageSource().contains(("Request successfully sent")));
	}
	
	
	@Test
	/*
	 * testing to view approved request by the patient
	 */
	public void testPatientViewApprovedRequest() throws Exception{
		
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("102");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.linkText("Request Records Release")).click();
		driver.findElement(By.xpath("(//a[contains(text(),'View')])[8]")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		Assert.assertTrue(driver.getPageSource().contains(("Approved")));
		Assert.assertTrue(driver.getPageSource().contains(("Health Institute Dr. E")));
		Assert.assertTrue(driver.getPageSource().contains(("First name: Monica")));
		Assert.assertTrue(driver.getPageSource().contains(("Last name: Brown")));
		Assert.assertTrue(driver.getPageSource().contains(("Phone number: 329-818-7734")));
		Assert.assertTrue(driver.getPageSource().contains(("Email address: monica.brown@hartfordradiology.com")));
		Assert.assertTrue(driver.getPageSource().contains(("Hospital: Hartford Radiology Ltd.")));
		Assert.assertTrue(driver.getPageSource().contains(("Hospital address: 8941 Hargett Way, Hartford, CT 01243")));
		
	}
	
	/*
	 * testing for HCP approve a request
	 * HCP:9000000000
	 */
	@Test
	public void testHCPApprovesRequest() throws Exception{
		
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Records Release Requests")).click();
	    driver.findElement(By.linkText("View")).click();
	    
	    Assert.assertTrue(driver.getPageSource().contains(("Pending")));
	    driver.findElement(By.id("Approve")).click();
	    Assert.assertTrue(driver.getPageSource().contains(("Approved")));   
	}
	
	/*
	 * testing for HCP denies a request
	 * HCP: 9000000000
	 */
	@Test
	public void testHCPDeniesRequest() throws Exception{
		
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Records Release Requests")).click();
	    driver.findElement(By.linkText("View")).click();
	    
	    Assert.assertTrue(driver.getPageSource().contains(("Pending")));
	    driver.findElement(By.id("Deny")).click();
	    Assert.assertTrue(driver.getPageSource().contains(("Denied")));
	}
	
	/*
	 * Testing for UAP denies a request
	 * UAP: 8000000009
	 */
	@Test
	public void testUAPDeniesRequest() throws Exception{
		
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("8000000009");
	    driver.findElement(By.id("j_password")).sendKeys("uappass1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Records Release Requests")).click();
	    driver.findElement(By.xpath("(//a[contains(text(),'View')])[9]")).click();
	    
	    Assert.assertTrue(driver.getPageSource().contains(("Pending")));
	    driver.findElement(By.id("Deny")).click();
	    Assert.assertTrue(driver.getPageSource().contains(("Denied")));
	}
	
	/*
	 * testing for UAP viewing approved request
	 * UAP: 8000000009
	 */
	@Test
	public void testUAPViewsApprovedRequest() throws Exception{
		
		driver.get("http://localhost:8080/iTrust/");
	    driver.findElement(By.id("j_username")).sendKeys("8000000009");
	    driver.findElement(By.id("j_password")).sendKeys("uappass1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Records Release Requests")).click();
	    driver.findElement(By.xpath("(//a[contains(text(),'View')])[8]")).click();
	    
	    Assert.assertTrue(driver.getPageSource().contains(("Approved")));
	    Assert.assertTrue(driver.getPageSource().contains(("First name: Monica")));
	    Assert.assertTrue(driver.getPageSource().contains(("Last name: Brown")));
	    Assert.assertTrue(driver.getPageSource().contains(("Phone number: 329-818-7734")));
	}
	
	/*
	 * testing for invalid input SQL injection
	 * UAP: 8000000009
	 */
	@Test
	public void testInvalidInputSQLInjection() throws Exception{
		
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("102");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.linkText("Request Records Release")).click();
		driver.findElement(By.id("submitReq")).click();
		hos = new Select(driver.findElement(By.name("releaseHospital")));
		hos.selectByIndex(1);
		driver.findElement(By.id("recFirstName")).sendKeys("\'");
		driver.findElement(By.id("recLastName")).sendKeys("\'");
		driver.findElement(By.id("recPhone")).sendKeys("\'");
		driver.findElement(By.id("recEmail")).sendKeys("\'");
		driver.findElement(By.id("recHospitalName")).sendKeys("\'");
		driver.findElement(By.id("recHospitalAddress1")).sendKeys("\'");
		driver.findElement(By.id("recHospitalAddress2")).sendKeys("\'");
		driver.findElement(By.id("recHospitalCity")).sendKeys("\'");
		driver.findElement(By.id("recHospitalState")).sendKeys("\'");
		driver.findElement(By.id("recHospitalZip")).sendKeys("\'");
		driver.findElement(By.id("releaseJustification")).sendKeys("Annual records request");
		driver.findElement(By.id("verifyForm")).click();
		driver.findElement(By.id("digitalSig")).sendKeys("Caldwell Hudson");
		driver.findElement(By.id("submit")).click();
		
		Assert.assertFalse(driver.getPageSource().contains(("Request successfully sent")));
	}

}