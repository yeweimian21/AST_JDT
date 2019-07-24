package edu.ncsu.csc.itrust.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Tests that patients can edit and delete entries in their
 * sleep diary.
 */
public class EditAndDeleteSleepDiaryTest extends iTrustSeleniumTest {

	/**
	 * Make sure we get the standard data for each call
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp(); // clear tables is called in super
		gen.standardData();
	}
	
	/**
	 * Test that a user can edit entries in their sleep diary.
	 */
	public void testEditSleepDiaryEntryValidValues() throws Exception {
		//need to enable javascript
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("1", "pw"); //login as Random Person
		driver.setJavascriptEnabled(true);
		
		driver.findElement(By.linkText("My Sleep Diary")).click();
		assertEquals("iTrust - View My Sleep Diary", driver.getTitle());

		/* now find the values individually (name:row#)
			have to do it by value since they are input fields
			used for editing and deleting */
		WebElement valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/14/2012", valDate.getAttribute(VALUE));
		Select valType = new Select(driver.findElement(By.name("SleepType:0")));
		assertEquals("Nap", valType.getFirstSelectedOption().getAttribute(VALUE));
		WebElement valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("1.0", valHours.getAttribute(VALUE));
		
		//now assert that the totals are correct
		WebElement totalTable = 
				driver.findElements(By.tagName("table")).get(2);
		List<WebElement> tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("1.0", tableRows.get(1).getText());
		assertEquals("12/12/2012", tableRows.get(2).getText());
		assertEquals("2.0", tableRows.get(3).getText());
		
		/* click the edit entry button
		 * (each row in table has 14 and edit is 2nd last)
		 */
		WebElement button = driver.findElement(By.cssSelector("input[value='Edit Entry']"));
		button.click();
		
		//update entry (need to re-get the values since they're now enabled
		valDate = driver.findElement(By.name("Date:0"));
		valDate.clear();
		valDate.sendKeys("12/13/2012");
		valHours = driver.findElement(By.name("Hours:0"));
		valHours.clear();
		valHours.sendKeys("0.5");
		//now submit it
		driver.findElements(By.cssSelector("input[value='Submit']"))
				.get(0).click();
		assertTrue(driver.getPageSource().contains("Congratulations!"));

		//now assert everything updated, including totals
		valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/13/2012", valDate.getAttribute(VALUE));
		valType = new Select(driver.findElement(By.name("SleepType:0")));
		assertEquals("Nap", valType.getFirstSelectedOption().getAttribute(VALUE));
		valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("0.5", valHours.getAttribute(VALUE));
		
		//now assert that the totals are correct
		totalTable = driver.findElements(By.tagName("table")).get(2);
		tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/13/2012", tableRows.get(0).getText());
		assertEquals("0.5", tableRows.get(1).getText());
		
		assertEquals("12/12/2012", tableRows.get(2).getText());
		assertEquals("2.0", tableRows.get(3).getText());
	}
	
	/**
	 * Tests that a Patient cannot enter invalid values
	 * when editing a Sleep Diary entry.
	 */
	public void testEditSleepDiaryEntryInvalidValues() throws Exception {
		//need to enable javascript
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("1", "pw"); //login as Random Person
		driver.setJavascriptEnabled(true);
		
		driver.findElement(By.linkText("My Sleep Diary")).click();
		assertEquals("iTrust - View My Sleep Diary", driver.getTitle());

		/* now find the values individually (name:row#)
			have to do it by value since they are input fields
			used for editing and deleting */
		WebElement valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/14/2012", valDate.getAttribute(VALUE));
		Select valType = new Select(driver.findElement(By.name("SleepType:0")));
		assertEquals("Nap", valType.getFirstSelectedOption().getAttribute(VALUE));
		WebElement valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("1.0", valHours.getAttribute(VALUE));
		
		//now assert that the totals are correct
		WebElement totalTable = 
				driver.findElements(By.tagName("table")).get(2);
		List<WebElement> tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("1.0", tableRows.get(1).getText());
		assertEquals("12/12/2012", tableRows.get(2).getText());
		assertEquals("2.0", tableRows.get(3).getText());
		
		/* click the edit entry button
		 * (each row in table has 14 and edit is 2nd last)
		 */
		WebElement button = driver.findElement(By.cssSelector("input[value='Edit Entry']"));
		button.click();
		
		//update entry (need to re-get the values since they're now enabled
		valDate = driver.findElement(By.name("Date:0"));
		valDate.clear();
		valDate.sendKeys("12/12/2012");
		valHours = driver.findElement(By.name("Hours:0"));
		valHours.clear();
		valHours.sendKeys("-1.0");
		//now submit it
		driver.findElements(By.cssSelector("input[value='Submit']"))
				.get(0).click();
		assertTrue(driver.getPageSource().contains("This form has not been validated correctly."));

		//now assert everything updated, including totals
		valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/14/2012", valDate.getAttribute(VALUE));
		valType = new Select(driver.findElement(By.name("SleepType:0")));
		assertEquals("Nap", valType.getFirstSelectedOption().getAttribute(VALUE));
		valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("1.0", valHours.getAttribute(VALUE));
		
		//now assert that the totals are correct
		totalTable = driver.findElements(By.tagName("table")).get(2);
		tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("1.0", tableRows.get(1).getText());
		assertEquals("12/12/2012", tableRows.get(2).getText());
		assertEquals("2.0", tableRows.get(3).getText());
	}
	
	/**
	 * Tests that a user will be presented with a popup before
	 * completely deleting an entry.
	 */
	public void testDeleteSleepDiaryEntry() throws Exception {
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("1", "pw"); //login as Random
		driver.setJavascriptEnabled(true);
		driver.findElement(By.linkText("My Sleep Diary")).click();
		assertEquals("iTrust - View My Sleep Diary", driver.getTitle());
		
		/* make sure the values are right to begin with
		now find the values individually (name:row#)
		have to do it by value since they are input fields
		used for editing and deleting */
		WebElement valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/14/2012", valDate.getAttribute(VALUE));
		Select valType = new Select(driver.findElement(By.name("SleepType:0")));
		assertEquals("Nap", valType.getFirstSelectedOption().getAttribute(VALUE));
		WebElement valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("1.0", valHours.getAttribute(VALUE));
		
		//now assert that the totals are correct
		WebElement totalTable = 
				driver.findElements(By.tagName("table")).get(2);
		List<WebElement> tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("1.0", tableRows.get(1).getText());
		assertEquals("12/12/2012", tableRows.get(2).getText());
		assertEquals("2.0", tableRows.get(3).getText());
		
		//now click to delete the first entry
		WebElement button = driver.findElement(By.cssSelector("input[value='Delete Entry']"));
		
		/*htmlunitdriver doesn't work with confirms well, and this is the
			best workaround there is */
		driver.executeScript("window.confirm=function() {return true;}");
		button.click();
		
		//assert entries and totals updated (Nightly is now the first entry)
		valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/12/2012", valDate.getAttribute(VALUE));
		valType = new Select(driver.findElement(By.name("SleepType:0")));
		assertEquals("Nightly", valType.getFirstSelectedOption().getAttribute(VALUE));
		valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("2.0", valHours.getAttribute(VALUE));
		
		//now assert that the totals are correct
		totalTable = driver.findElements(By.tagName("table")).get(2);
		tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/12/2012", tableRows.get(0).getText());
		assertEquals("2.0", tableRows.get(1).getText());
	}
	
	/**
	 * Tests that a user can cancel deletion of an entry and that
	 * nothing will happen.
	 */
	public void testCancelDelete() throws Exception {
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("1", "pw"); //login as Random
		driver.setJavascriptEnabled(true);
		driver.findElement(By.linkText("My Sleep Diary")).click();
		assertEquals("iTrust - View My Sleep Diary", driver.getTitle());
		
		/* make sure the values are right to begin with
		now find the values individually (name:row#)
		have to do it by value since they are input fields
		used for editing and deleting */
		WebElement valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/14/2012", valDate.getAttribute(VALUE));
		Select valType = new Select(driver.findElement(By.name("SleepType:0")));
		assertEquals("Nap", valType.getFirstSelectedOption().getAttribute(VALUE));
		WebElement valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("1.0", valHours.getAttribute(VALUE));
		
		//now assert that the totals are correct
		WebElement totalTable = 
				driver.findElements(By.tagName("table")).get(2);
		List<WebElement> tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("1.0", tableRows.get(1).getText());
		assertEquals("12/12/2012", tableRows.get(2).getText());
		assertEquals("2.0", tableRows.get(3).getText());
		
		//now click to delete the first entry
		WebElement button = driver.findElement(By.cssSelector("input[value='Delete Entry']"));
		
		/*htmlunitdriver doesn't work with confirms well, and this is the
			best workaround there is */
		driver.executeScript("window.confirm=function() {return false;}");
		button.click();
		
		//assert entries and totals updated (Nightly is now the first entry)
		valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/14/2012", valDate.getAttribute(VALUE));
		valType = new Select(driver.findElement(By.name("SleepType:0")));
		assertEquals("Nap", valType.getFirstSelectedOption().getAttribute(VALUE));
		valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("1.0", valHours.getAttribute(VALUE));
		
		//now assert that the totals are correct
		totalTable = driver.findElements(By.tagName("table")).get(2);
		tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("1.0", tableRows.get(1).getText());
		assertEquals("12/12/2012", tableRows.get(2).getText());
		assertEquals("2.0", tableRows.get(3).getText());
	}
	
	/**
	 * Tests that a user cannot enter an invalid date
	 * when editing a sleep diary entry.
	 */
	public void testEditEntryWithInvalidDate() throws Exception {
		//need to enable javascript
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("1", "pw"); //login as Random Person
		driver.setJavascriptEnabled(true);
		
		driver.findElement(By.linkText("My Sleep Diary")).click();
		assertEquals("iTrust - View My Sleep Diary", driver.getTitle());

		/* now find the values individually (name:row#)
			have to do it by value since they are input fields
			used for editing and deleting */
		WebElement valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/14/2012", valDate.getAttribute(VALUE));
		Select valType = new Select(driver.findElement(By.name("SleepType:0")));
		assertEquals("Nap", valType.getFirstSelectedOption().getAttribute(VALUE));
		WebElement valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("1.0", valHours.getAttribute(VALUE));
		
		//now assert that the totals are correct
		WebElement totalTable = 
				driver.findElements(By.tagName("table")).get(2);
		List<WebElement> tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("1.0", tableRows.get(1).getText());
		assertEquals("12/12/2012", tableRows.get(2).getText());
		assertEquals("2.0", tableRows.get(3).getText());
		
		/* click the edit entry button
		 * (each row in table has 14 and edit is 2nd last)
		 */
		WebElement button = driver.findElement(By.cssSelector("input[value='Edit Entry']"));
		button.click();
		
		//update entry (need to re-get the values since they're now enabled
		valDate = driver.findElement(By.name("Date:0"));
		valDate.clear();
		valDate.sendKeys("2012/12/12");
		valHours = driver.findElement(By.name("Hours:0"));
		valHours.clear();
		valHours.sendKeys("1.0");
		//now submit it
		driver.findElements(By.cssSelector("input[value='Submit']"))
				.get(0).click();
		assertTrue(driver.getPageSource().contains("[Date Slept: MM/DD/YYYY]"));

		//now assert everything updated, including totals
		valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/14/2012", valDate.getAttribute(VALUE));
		valType = new Select(driver.findElement(By.name("SleepType:0")));
		assertEquals("Nap", valType.getFirstSelectedOption().getAttribute(VALUE));
		valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("1.0", valHours.getAttribute(VALUE));
		
		//now assert that the totals are correct
		totalTable = driver.findElements(By.tagName("table")).get(2);
		tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("1.0", tableRows.get(1).getText());
		assertEquals("12/12/2012", tableRows.get(2).getText());
		assertEquals("2.0", tableRows.get(3).getText());
	}
}
