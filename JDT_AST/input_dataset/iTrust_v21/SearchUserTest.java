package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class SearchUserTest extends iTrustSeleniumTest{

	private HtmlUnitDriver driver;
	private String baseUrl;
	private WebDriverWait wait;

	@Before
	public void setUp() throws Exception {
	  super.setUp();
	  gen.clearAllTables();
	  gen.standardData();	
	  driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_3_6);
	  baseUrl = "http://localhost:8080/iTrust/";
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  driver.setJavascriptEnabled(true);

	  // Create the explicit wait
	  wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
	}
	
	@Test
	public void testGetPatient() throws Exception {
	  driver.get(baseUrl);
	  driver.findElement(By.id("j_username")).clear();
	  driver.findElement(By.id("j_username")).sendKeys("9000000000");
	  driver.findElement(By.id("j_password")).clear();
	  driver.findElement(By.id("j_password")).sendKeys("pw");
      driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
      
      assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
      wait.until(ExpectedConditions.titleIs("iTrust - HCP Home"));
      
	  driver.findElement(By.cssSelector("h2.panel-title")).click();
	  driver.findElement(By.linkText("Basic Health Information")).click();
	  assertEquals("iTrust - Please Select a Patient", driver.getTitle());
	  driver.findElement(By.id("searchBox")).sendKeys("Random Person");
	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { 
	    		if ("Random".equals(driver.findElement(By.xpath("//div[@id='searchTarget']/table/tbody/tr[2]/td[2]")).getText())) 
	    		break; 
	    	} catch (Exception e) {}
	    	Thread.sleep(1000);
	    }
	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { 
	    		if ("Person".equals(driver.findElement(By.xpath("//div[@id='searchTarget']/table/tbody/tr[2]/td[3]")).getText())) 
	    		break; 
	    	} catch (Exception e) {}
	    	Thread.sleep(1000);
	    }


	}
	
	  @Test
	  public void testGetPatient2() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    wait.until(ExpectedConditions.titleIs("iTrust - HCP Home"));
	    
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
	    wait.until(ExpectedConditions.titleIs("iTrust - Please Select a Patient"));
	    //assertEquals("iTrust - Please Select a Patient", driver.getTitle());
	    driver.findElement(By.id("searchBox")).sendKeys("Andy");
	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { 
	    		if ("Andy".equals(driver.findElement(By.xpath("//div[@id='searchTarget']/table/tbody/tr[2]/td[2]")).getText())) 
	    		break; 
	    	} catch (Exception e) {}
	    	Thread.sleep(1000);
	    }
	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { 
	    		if ("Programmer".equals(driver.findElement(By.xpath("//div[@id='searchTarget']/table/tbody/tr[2]/td[3]")).getText())) 
	    		break; 
	    	} catch (Exception e) {}
	    	Thread.sleep(1000);
	    }
	  }	
	  
	  @Test
	  public void testGetPatient3() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).click();
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).click();
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    wait.until(ExpectedConditions.titleIs("iTrust - HCP Home"));
	    
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[10]/div/h2")).click();
	    driver.findElement(By.linkText("UAPs")).click();
	    assertEquals("iTrust - Please Select a Personnel", driver.getTitle());
	    driver.findElement(By.name("FIRST_NAME")).sendKeys("Kelly");
	    driver.findElement(By.name("LAST_NAME")).sendKeys("Doctor");
	    driver.findElement(By.xpath("//input[@value='User Search']")).click();
	    assertEquals("MID", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[2]/tbody/tr/td")).getText());
	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if ("".equals(driver.findElement(By.xpath("(//input[@value='9000000000'])[2]")).getText())) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }

	    
	  }

}