package edu.ncsu.csc.itrust.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Tests the functionality of the Upload Patient File page in iTrust
 */
public class UploadPatientFileTest extends iTrustSeleniumTest {
  
	//Not sure what this is for, the Sel IDE just made it, might delete
	private StringBuffer verificationErrors = new StringBuffer();


  @Override
	protected void setUp() throws Exception {
	  	super.setUp();
		gen.clearAllTables();
		gen.standardData();
	  }

	  @Test
	  /**
	   * Tests the patient file upload with a valid file that should be successful
	   * @throws Exception
	   */
	  public void testHCPPatientUploadValidData() throws Exception {
		
		//This logs us into iTrust and returns the HtmlUnitDriver for use in this case
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		
		//Make sure we were able to log in
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[6]/div/h2")).click();
	    driver.findElement(By.linkText("Upload Patient File")).click();
	    assertEquals("iTrust - Upload Patient File", driver.getTitle());
	    driver.findElement(By.name("patientFile")).clear();
	    //These testing files already exist in iTrust, so we just need to specify the path to them
	    driver.findElement(By.name("patientFile")).sendKeys("/iTrust/testing-files/sample_patientupload/HCPPatientUploadValidData.csv");
	    driver.findElement(By.id("sendFile")).click();
	    try {
	      assertEquals("Upload Successful", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	  }
	  
	  /**
	   * Tests the patient file upload with a file that has a missing email field
	   * @throws Exception
	   */
	  public void testHCPPatientUploadRequiredFieldMissing() throws Exception {
			
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
			
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[6]/div/h2")).click();
	    driver.findElement(By.linkText("Upload Patient File")).click();
	    assertEquals("iTrust - Upload Patient File", driver.getTitle());
	    driver.findElement(By.name("patientFile")).clear();
	    //These testing files already exist in iTrust, so we just need to specify the path to them
	    driver.findElement(By.name("patientFile")).sendKeys("/iTrust/testing-files/sample_patientupload/HCPPatientUploadRequiredFieldMissing.csv");
	    driver.findElement(By.id("sendFile")).click();
	    try {
	      assertEquals("File upload was unsuccessful", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	  }
	  
	  /**
	   * Tests the file upload with a file that has an invalid field.
	   * @throws Exception
	   */
	  public void testHCPPatientUploadInvalidField() throws Exception {
			
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[6]/div/h2")).click();
	    driver.findElement(By.linkText("Upload Patient File")).click();
	    assertEquals("iTrust - Upload Patient File", driver.getTitle());
	    driver.findElement(By.name("patientFile")).clear();
	    //These testing files already exist in iTrust, so we just need to specify the path to them
	    driver.findElement(By.name("patientFile")).sendKeys("/iTrust/testing-files/sample_patientupload/HCPPatientUploadInvalidField.csv");
	    driver.findElement(By.id("sendFile")).click();
	    try {
	      assertEquals("File upload was unsuccessful", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	      assertEquals("Field \"invalidfield\" is invalid", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	  }
	  
	/**
	 * Tests the file upload with a file that has mismatched fields.
	 * @throws Exception
	 */
	public void testHCPPatientUploadInvalidData() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[6]/div/h2")).click();
		driver.findElement(By.linkText("Upload Patient File")).click();
		assertEquals("iTrust - Upload Patient File", driver.getTitle());
		driver.findElement(By.name("patientFile")).clear();
		//These testing files already exist in iTrust, so we just need to specify the path to them
		driver.findElement(By.name("patientFile")).sendKeys("/iTrust/testing-files/sample_patientupload/HCPPatientUploadInvalidData.csv");
		driver.findElement(By.id("sendFile")).click();
		try {
			assertEquals("File upload was successful, but some patients could not be added", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
			assertEquals("Field number mismatch on line 3", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
			assertEquals("Field number mismatch on line 4", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}
	
	/**
	 * Tests the file upload with a file is empty
	 * @throws Exception
	 */
	public void testHCPPatientUploadEmptyFile() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[6]/div/h2")).click();
		driver.findElement(By.linkText("Upload Patient File")).click();
		assertEquals("iTrust - Upload Patient File", driver.getTitle());
		driver.findElement(By.name("patientFile")).clear();
		//These testing files already exist in iTrust, so we just need to specify the path to them
		driver.findElement(By.name("patientFile")).sendKeys("/iTrust/testing-files/sample_patientupload/HCPPatientUploadEmptyFile.csv");
		driver.findElement(By.id("sendFile")).click();
		try {
			assertEquals("File upload was unsuccessful", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
			assertEquals("File is not valid CSV file", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}
	
	/**
	 * Tests the file upload with a file that has duplicate fields
	 * @throws Exception
	 */
	public void testHCPPatientUploadDuplicateField() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[6]/div/h2")).click();
		driver.findElement(By.linkText("Upload Patient File")).click();
		assertEquals("iTrust - Upload Patient File", driver.getTitle());
		driver.findElement(By.name("patientFile")).clear();
		//These testing files already exist in iTrust, so we just need to specify the path to them
		driver.findElement(By.name("patientFile")).sendKeys("/iTrust/testing-files/sample_patientupload/HCPPatientUploadDuplicateField.csv");
		driver.findElement(By.id("sendFile")).click();
		try {
			assertEquals("File upload was unsuccessful", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
			assertEquals("Duplicate field \"firstName\"", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}
	
	/**
	 * Tests the file upload with a file that is a different type and has binary data
	 * @throws Exception
	 */
	public void testHCPPatientUploadBinaryData() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[6]/div/h2")).click();
		driver.findElement(By.linkText("Upload Patient File")).click();
		assertEquals("iTrust - Upload Patient File", driver.getTitle());
		driver.findElement(By.name("patientFile")).clear();
		//These testing files already exist in iTrust, so we just need to specify the path to them
		driver.findElement(By.name("patientFile")).sendKeys("/iTrust/testing-files/sample_patientupload/HCPPatientUploadBinaryData.doc");
		driver.findElement(By.id("sendFile")).click();
		try {
			assertEquals("File upload was unsuccessful", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}
}