package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Test;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class EditPatientTest extends iTrustSeleniumTest {
	  private String baseUrl;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @Override
	  public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	  }

	  @Test
	  public void testCauseOfDeathValidation() throws Exception {
		//set up for the start location and driver
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
		
		//log in processes
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    //make sure that it correctly log-in as HCP
	    try {
	      assertEquals("iTrust - HCP Home", driver.getTitle());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Patient Information")).click();
	    //Does the search 
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2']")).submit();
	    try {
	    	//check if it on the page correctly
	      assertEquals("http://localhost:8080/iTrust/auth/hcp-uap/editPatient.jsp", driver.getCurrentUrl());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    //enters a bad date
	    driver.findElement(By.name("dateOfDeathStr")).clear();
	    driver.findElement(By.name("dateOfDeathStr")).sendKeys(" ");
	    driver.findElement(By.name("action")).click();
	    assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 9000000000L, 2L, "");
	    try {
	    	//checks for the error message for bad date
	    	assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Date of Death: MM/DD/YYYY, Cause of Death cannot be specified without Date of Death!]", driver.findElement(By.cssSelector("span.iTrustError")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    
	  }
	  
	  @Test
	  public void testViewDemographicsTest() throws Exception {
		//set up for the start location and driver
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
	
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    try {
	      assertEquals("iTrust - HCP Home", driver.getTitle());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Patient Information")).click();
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2']")).submit();
	    //check if its on the correct page
	    try {
	      assertEquals("http://localhost:8080/iTrust/auth/hcp-uap/editPatient.jsp", driver.getCurrentUrl());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    //clears and enters a email
	    driver.findElement(By.name("email")).clear();
	    driver.findElement(By.name("email")).sendKeys("history@gmail.com");
	    driver.findElement(By.name("action")).click();
	    try {
	      //get the massage that the info has been updated
	      assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    //submits
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    try {
	    	//checks for new email
	      assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*history@gmail\\.com[\\s\\S]*$"));
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	  }
	  
	  
	  @Test
	  public void testMFWithPersonnelMID() throws Exception {
		//set up for the start location and driver
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
	
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    try {
	      assertEquals("iTrust - HCP Home", driver.getTitle());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Patient Information")).click();
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2']")).submit();
	    try {
	      assertEquals("http://localhost:8080/iTrust/auth/hcp-uap/editPatient.jsp", driver.getCurrentUrl());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    //enters bad info for mothers and fathers MID
	    driver.findElement(By.name("motherMID")).clear();
	    driver.findElement(By.name("motherMID")).sendKeys("9");
	    driver.findElement(By.name("fatherMID")).clear();
	    driver.findElement(By.name("fatherMID")).sendKeys("98");
	    driver.findElement(By.name("action")).click();
	    try {
	    	//gets error message
	      assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Mother MID: 1-10 digit number not beginning with 9, Father MID: 1-10 digit number not beginning with 9]", driver.findElement(By.cssSelector("#iTrustContent > div")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	  }
	  
	  
	  @Test
	  public void testMisspellings() throws Exception {
		//set up for the start location and driver
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");

		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    try {
	      assertEquals("iTrust - HCP Home", driver.getTitle());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Patient Information")).click();
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2']")).submit();
	    try {
	      assertEquals("http://localhost:8080/iTrust/auth/hcp-uap/editPatient.jsp", driver.getCurrentUrl());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    //check that its not there
	    assertThat("Mother MIDs", is(not(driver.findElement(By.id("editForm")).getText())));
	  }

}