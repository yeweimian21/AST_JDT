package edu.ncsu.csc.itrust.selenium;


import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class SecureMIDNFRTest extends iTrustSeleniumTest{
	
	private HtmlUnitDriver driver;
	private String baseUrl;

	@Before
	public void setUp() throws Exception {
	  super.setUp();
	  gen.clearAllTables();
	  gen.standardData();
	  driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_3_6);
	  baseUrl = "http://localhost:8080/iTrust/";
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  driver.setJavascriptEnabled(true);
	}
	
	  @Test
	  public void testMIDShown1() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("8000000009");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("uappass1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
	    assertEquals("iTrust - UAP Home", driver.getTitle());
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("Edit Patient")).click();
	    driver.findElement(By.id("searchBox")).sendKeys("2");

	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if ("".equals(driver.findElement(By.xpath("//input[@value='2']")).getText())) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }
	    JavascriptExecutor js =(JavascriptExecutor) driver; 
	    js.executeScript("parent.location.href='getPatientID.jsp?UID_PATIENTID=2&forward=hcp-uap/editPatient.jsp';");
	    assertEquals("iTrust - Edit Patient", driver.getTitle());
	    assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 8000000009L, 2L, "");
	  }
	  
	  @Test
	  public void testMIDShown2() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Patient Information")).click();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if ("".equals(driver.findElement(By.xpath("//input[@value='2']")).getText())) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }
	    JavascriptExecutor js =(JavascriptExecutor) driver; 
	    js.executeScript("parent.location.href='getPatientID.jsp?UID_PATIENTID=2&forward=hcp-uap/editPatient.jsp';");
	    assertEquals("iTrust - Edit Patient", driver.getTitle());
	    assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 9000000000L, 2L, "");
	  }
	  
	  @Test
	  public void testMIDShown3() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("PHR Information")).click();
	    assertEquals("iTrust - Please Select a Patient", driver.getTitle());
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if ("".equals(driver.findElement(By.xpath("//input[@value='2']")).getText())) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }
	    JavascriptExecutor js =(JavascriptExecutor) driver; 
	    js.executeScript("parent.location.href='getPatientID.jsp?UID_PATIENTID=2&forward=hcp-uap/editPHR.jsp';");
	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if ("Baby Programmer".equals(driver.findElement(By.partialLinkText("Baby Programmer")).getText())) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }
	    driver.findElement(By.partialLinkText("Programmer")).click();
	    assertEquals("iTrust - Edit Personal Health Record", driver.getTitle());
	    assertEquals("Baby Programmer", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/div/table/tbody/tr[2]/td[2]")).getText());
	    assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW, 9000000000L, 2L, "");
	  }

}