package edu.ncsu.csc.itrust.selenium;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class ActivityFeedTest extends iTrustSeleniumTest {
	private WebDriver driver = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		
	}
	
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	/**
	 * Tests the limit for activities on the home page and then for all the activities.
	 * @throws Exception
	 */
	public void testOlderActivities() throws Exception {
		gen.transactionLog6();
		
		//Login
		driver = login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		//Get the panels and select the activity panel
		List<WebElement> panels = driver.findElements(By.className("panel-group"));
		WebElement activityPanel = panels.get(panels.size() - 1);
		
		//Get the list items and count how many items there are, should be 20
		List<WebElement> listItems = activityPanel.findElements(By.tagName("li"));
		assertEquals(20, listItems.size());
		
		//Now we check the all the activities
		driver.findElement(By.linkText("Older Activities")).click();
		
		//Get the panels and select the activity panel
		panels = driver.findElements(By.className("panel-group"));
		activityPanel = panels.get(panels.size() - 1);
		
		//Get the list items and count how many items there are, should be 40
		listItems = activityPanel.findElements(By.tagName("li"));
		//Plus three because of the two refresh links and then the one padding <li>
		assertEquals(40 + 3, listItems.size());
	}
	
	/**
	 * Tests the refresh functionality of the activity panel, which refreshes and activity 
	 * log on the homepage and limits it to 20.
	 * @throws Exception
	 */
	public void testUpdateActivityFeed() throws Exception {
		gen.transactionLog6();
		
		//Login
		driver = login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		//Click on Older Activities to show all activity
		driver.findElement(By.linkText("Older Activities")).click();
		//Then refresh to show only newest 20
		driver.findElement(By.linkText("Refresh")).click();
		
		//Get the panels and select toe activity panel
		List<WebElement> panels = driver.findElements(By.className("panel-group"));
		WebElement activityPanel = panels.get(panels.size() - 1);
		
		//Get the list items and count how many items there are, should be 20
		List<WebElement> listItems = activityPanel.findElements(By.tagName("li"));
		assertEquals(20, listItems.size());
	}
	
	/**
	 * Tests for valid information in Activity Feed
	 * @throws Exception
	 */
	public void testViewActivityFeed() throws Exception {
		gen.transactionLog5();

		//Login
		driver = login("1", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		Date d = new Date();
		d.setTime(d.getTime() - 3*24*60*60*1000);
		new SimpleDateFormat("MM/dd/yyyy");
		
		//Get the panels and select toe activity panel
		List<WebElement> panels = driver.findElements(By.className("panel-group"));
		WebElement activityPanel = panels.get(panels.size() - 1);
		
		//Get the list items and count how many items there are, should be 20
		List<WebElement> listItems = activityPanel.findElements(By.tagName("li"));
		
		assertEquals(8, listItems.size());
		assertTrue(listItems.get(0).getText().contains("You successfully authenticated today at "));
		assertEquals("Kelly Doctor viewed your risk factors yesterday at 1:15PM.", listItems.get(1).getText());
		assertEquals("FirstUAP LastUAP viewed your risk factors yesterday at 1:02PM.", listItems.get(2).getText());
		assertEquals("FirstUAP LastUAP viewed your lab procedure results yesterday at 12:02PM.", listItems.get(3).getText());
		assertEquals("Justin Time created an emergency report for you yesterday at 10:04AM.", listItems.get(4).getText());
		assertEquals("Andy Programmer viewed your prescription report yesterday at 9:43AM.", listItems.get(5).getText());
		assertEquals("Kelly Doctor viewed your prescription report yesterday at 8:15AM.", listItems.get(6).getText());
		assertTrue(listItems.get(7).getText().contains("Kelly Doctor edited your office visit on "));
	}
	
	/**
	 * Tests to see if hidden activities show up on activity page
	 * DOES NOT SEEM TO ACTUALLY TEST THIS THOUGH!!!!!
	 * @throws Exception
	 */
	public void testDLHCPActivityHiddenInFeed1() throws Exception {
		//Login
		driver = login("9000000008", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
		//Go to Patient Information page
		driver.findElement(By.linkText("Patient Information")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("21");
		element.submit();
		assertEquals("iTrust - Edit Patient", driver.getTitle());
		
		driver.findElement(By.linkText("Basic Health History")).click();;
		assertEquals("iTrust - Edit Basic Health Record", driver.getTitle());
		
		//logout
		driver.get("http://localhost:8080/iTrust/logout.jsp");
		assertEquals("iTrust - Login", driver.getTitle());
		
		//Login
		driver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
		//Go to Patient Information page
		driver.findElement(By.linkText("Document Office Visit")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		
		element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("21");
		element.submit();
		
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
				
		driver.findElement(By.id("formMain")).submit();
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		element = driver.findElement(By.id("mainForm"));
		element.submit();
		
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		
		//Let's manually logout
		driver.get("http://localhost:8080/iTrust/logout.jsp");
		assertEquals("iTrust - Login", driver.getTitle());
		
		//Login
		driver = login("21", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		//Find the activity log
		//Without doing this, the whole getPageSource will format with random \n
		element = driver.findElement(By.xpath("//div[@id='act-accord']//div[2]"));
				
		assertTrue(element.getText().contains("Kelly Doctor created an office visit"));
		assertTrue(element.getText().contains("Curious George viewed your health records history today at"));
		assertTrue(element.getText().contains("Curious George viewed your demographics"));
		
		//Enable javascript
		((HtmlUnitDriver) driver).setJavascriptEnabled(true);
		
		driver.findElement(By.linkText("My Providers")).click();
		assertEquals("iTrust - My Providers", driver.getTitle());
		
		//Find Gandalf Stormcrow checkbox
		element = driver.findElement(By.xpath("//form[@id='mainForm']//input[@value='Gandalf Stormcrow']"));
		//Deselect it
		if(element.isSelected()) {
			element.click();
		}
		
		//Find Kelly Doctor checkbox
		element = driver.findElement(By.xpath("//form[@id='mainForm']//input[@value='Kelly Doctor']"));
		//Select it
		if(!element.isSelected()) {
			element.click();
		}

		//Disable javascript or else some links will not appear
		((HtmlUnitDriver) driver).setJavascriptEnabled(false);
		
		//Go to homepage
		driver.get("http://localhost:8080/iTrust/logout.jsp");
		driver = login("21", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		//Get the activity log as an element so that the text is not formatted
		element = driver.findElement(By.xpath("//div[@id='act-accord']//div[2]"));
		assertFalse(element.getText().contains("Kelly Doctor created an office visit"));
		assertTrue(element.getText().contains("Curious George viewed your health records history today at"));
		assertTrue(element.getText().contains("Curious George viewed your demographics"));
	}
	
	public void testDLHCPActivityHiddenInFeed2() throws Exception {
		driver = login("23", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		WebElement element = driver.findElement(By.xpath("//div[@id='act-accord']//div[2]"));
		assertFalse(element.getText().contains("Beaker Beaker viewed your demographics"));
		assertFalse(element.getText().contains("Beaker Beaker edited your demographics"));
		assertFalse(element.getText().contains("Beaker Beaker added you to the telemedicine monitoring list"));

	}
	
	/**
	 * Tests to see that certain activities from the patients DLHCP are showing up in the activity field
	 * as well as verifying that other certain activities from the patients DLHCP are hidden.
	 * 
	 * This tests Black Box Test Case ViewActivityFeed_ShowRequiredDLHCPActivity
	 * The scenario is that Patient Devil's Advocate logs into iTrust and then checks his Activity Feed.
	 * The Activity Feed should not be displaying certain actions that his DLHCP performed, such as looking
	 * at his demographics etc. It should be displaying events from other LHCP's as stated fully in the requirements doc.
	 */
	public void testShowRequiredDLHCPActivity() throws Exception {
		//Login
		driver = login("24", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		//Get the activity log as an element
		//Verify that certain activities from the patient's DLHCP are present, and that others are hidden
		WebElement element = driver.findElement(By.xpath("//div[@id='act-accord']//div[2]"));
		assertFalse("DLHCP activity that is supposed to be hidden is shown in Activity Feed.",element.getText().contains("Kelly Doctor viewed patient's immunization report"));
		assertFalse("DLHCP activity that is supposed to be hidden is shown in Activity Feed.",element.getText().contains("Kelly Doctor viewed your health records history today"));
		assertFalse("DLHCP activity that is supposed to be hidden is shown in Activity Feed.",element.getText().contains("Kelly Doctor edited your demographics"));
		assertTrue("DLHCP activity that is supposed to shown is not present in Activity Feed.",element.getText().contains("Kelly Doctor added you to the telemedicine monitoring list"));
	}
}
