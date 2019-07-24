package edu.ncsu.csc.itrust.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Tests that patients can edit and delete entries in their
 * exercise diary.
 */
public class EditAndDeleteExerciseDiaryTest extends iTrustSeleniumTest {

	/**
	 * Make sure we get the standard data for each call
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp(); // clear tables is called in super
		gen.standardData();
	}
	
	/**
	 * Test that a user can edit entries in their exercise diary.
	 */
	public void testEditExerciseDiaryEntryValidValues() throws Exception {
		//need to enable javascript
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("1", "pw"); //login as Random Person
		driver.setJavascriptEnabled(true);
		
		driver.findElement(By.linkText("My Exercise Diary")).click();
		assertEquals("iTrust - View My Exercise Diary", driver.getTitle());

		/* now find the values individually (name:row#)
			have to do it by value since they are input fields
			used for editing and deleting */
		WebElement valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/14/2012", valDate.getAttribute(VALUE));
		WebElement valType = driver.findElement(By.name("ExerciseType:0"));
		assertEquals("Weight Training", valType.getAttribute(VALUE));
		WebElement valName = driver.findElement(By.name("Name:0"));
		assertEquals("Bench Press", valName.getAttribute(VALUE));
		WebElement valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("0.5", valHours.getAttribute(VALUE));
		WebElement valCalories = driver.findElement(By.name("Calories:0"));
		assertEquals("50", valCalories.getAttribute(VALUE));
		WebElement valSets = driver.findElement(By.name("Sets:0"));
		assertEquals("3", valSets.getAttribute(VALUE));
		WebElement valReps = driver.findElement(By.name("Reps:0"));
		assertEquals("10", valReps.getAttribute(VALUE));
		
		//now assert that the totals are correct
		WebElement totalTable = 
				driver.findElements(By.tagName("table")).get(2);
		List<WebElement> tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("0.5", tableRows.get(1).getText());
		assertEquals("50", tableRows.get(2).getText());
		assertEquals("12/12/2012", tableRows.get(3).getText());
		assertEquals("1.0", tableRows.get(4).getText());
		assertEquals("100", tableRows.get(5).getText());
		
		/* click the edit entry button
		 * (each row in table has 14 and edit is 2nd last)
		 */
		WebElement button = driver.findElement(By.cssSelector("input[value='Edit Entry']"));
		button.click();
		
		//update entry (need to re-get the values since they're now enabled
		valDate = driver.findElement(By.name("Date:0"));
		valDate.clear();
		valDate.sendKeys("12/13/2012");
		valName = driver.findElement(By.name("Name:0"));
		valName.clear();
		valName.sendKeys("Decline Bench Press");
		valHours = driver.findElement(By.name("Hours:0"));
		valHours.clear();
		valHours.sendKeys("0.75");
		valCalories = driver.findElement(By.name("Calories:0"));
		valCalories.clear();
		valCalories.sendKeys("75");
		valSets = driver.findElement(By.name("Sets:0"));
		valSets.clear();
		valSets.sendKeys("4");
		valReps = driver.findElement(By.name("Reps:0"));
		valReps.clear();
		valReps.sendKeys("8");
		//now submit it
		driver.findElements(By.cssSelector("input[value='Submit']"))
				.get(0).click();
		assertTrue(driver.getPageSource().contains("Congratulations!"));

		//now assert everything updated, including totals
		valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/13/2012", valDate.getAttribute(VALUE));
		valType = driver.findElement(By.name("ExerciseType:0"));
		assertEquals("Weight Training", valType.getAttribute(VALUE));
		valName = driver.findElement(By.name("Name:0"));
		assertEquals("Decline Bench Press", valName.getAttribute(VALUE));
		valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("0.75", valHours.getAttribute(VALUE));
		valCalories = driver.findElement(By.name("Calories:0"));
		assertEquals("75", valCalories.getAttribute(VALUE));
		valSets = driver.findElement(By.name("Sets:0"));
		assertEquals("4", valSets.getAttribute(VALUE));
		valReps = driver.findElement(By.name("Reps:0"));
		assertEquals("8", valReps.getAttribute(VALUE));
		
		//now assert that the totals are correct
		totalTable = driver.findElements(By.tagName("table")).get(2);
		tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/13/2012", tableRows.get(0).getText());
		assertEquals("0.75", tableRows.get(1).getText());
		assertEquals("75", tableRows.get(2).getText());

		assertEquals("12/12/2012", tableRows.get(3).getText());
		assertEquals("1.0", tableRows.get(4).getText());
		assertEquals("100", tableRows.get(5).getText());
	}
	
	/**
	 * Tests that a Patient cannot enter invalid values
	 * when editing a Exercise Diary entry.
	 */
	public void testEditExerciseDiaryEntryInvalidValues() throws Exception {
		//need to enable javascript
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("1", "pw"); //login as Random Person
		driver.setJavascriptEnabled(true);
		
		driver.findElement(By.linkText("My Exercise Diary")).click();
		assertEquals("iTrust - View My Exercise Diary", driver.getTitle());

		/* now find the values individually (name:row#)
			have to do it by value since they are input fields
			used for editing and deleting */
		WebElement valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/14/2012", valDate.getAttribute(VALUE));
		WebElement valType = driver.findElement(By.name("ExerciseType:0"));
		assertEquals("Weight Training", valType.getAttribute(VALUE));
		WebElement valName = driver.findElement(By.name("Name:0"));
		assertEquals("Bench Press", valName.getAttribute(VALUE));
		WebElement valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("0.5", valHours.getAttribute(VALUE));
		WebElement valCalories = driver.findElement(By.name("Calories:0"));
		assertEquals("50", valCalories.getAttribute(VALUE));
		WebElement valSets = driver.findElement(By.name("Sets:0"));
		assertEquals("3", valSets.getAttribute(VALUE));
		WebElement valReps = driver.findElement(By.name("Reps:0"));
		assertEquals("10", valReps.getAttribute(VALUE));
		
		//now assert that the totals are correct
		WebElement totalTable = 
				driver.findElements(By.tagName("table")).get(2);
		List<WebElement> tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("0.5", tableRows.get(1).getText());
		assertEquals("50", tableRows.get(2).getText());
		assertEquals("12/12/2012", tableRows.get(3).getText());
		assertEquals("1.0", tableRows.get(4).getText());
		assertEquals("100", tableRows.get(5).getText());
		
		/* click the edit entry button
		 * (each row in table has 14 and edit is 2nd last)
		 */
		WebElement button = driver.findElement(By.cssSelector("input[value='Edit Entry']"));
		button.click();
		
		//update entry (need to re-get the values since they're now enabled
		valDate = driver.findElement(By.name("Date:0"));
		valDate.clear();
		valDate.sendKeys("12/12/2012");
		valName = driver.findElement(By.name("Name:0"));
		valName.clear();
		valName.sendKeys("");
		valHours = driver.findElement(By.name("Hours:0"));
		valHours.clear();
		valHours.sendKeys("0");
		valCalories = driver.findElement(By.name("Calories:0"));
		valCalories.clear();
		valCalories.sendKeys("0");
		valSets = driver.findElement(By.name("Sets:0"));
		valSets.clear();
		valSets.sendKeys("0");
		valReps = driver.findElement(By.name("Reps:0"));
		valReps.clear();
		valReps.sendKeys("0");
		//now submit it
		driver.findElements(By.cssSelector("input[value='Submit']"))
				.get(0).click();
		assertTrue(driver.getPageSource().contains("This form has not been validated correctly."));

		//now assert everything updated, including totals
		valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/14/2012", valDate.getAttribute(VALUE));
		valType = driver.findElement(By.name("ExerciseType:0"));
		assertEquals("Weight Training", valType.getAttribute(VALUE));
		valName = driver.findElement(By.name("Name:0"));
		assertEquals("Bench Press", valName.getAttribute(VALUE));
		valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("0.5", valHours.getAttribute(VALUE));
		valCalories = driver.findElement(By.name("Calories:0"));
		assertEquals("50", valCalories.getAttribute(VALUE));
		valSets = driver.findElement(By.name("Sets:0"));
		assertEquals("3", valSets.getAttribute(VALUE));
		valReps = driver.findElement(By.name("Reps:0"));
		assertEquals("10", valReps.getAttribute(VALUE));
		
		//now assert that the totals are correct
		totalTable = driver.findElements(By.tagName("table")).get(2);
		tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("0.5", tableRows.get(1).getText());
		assertEquals("50", tableRows.get(2).getText());
		assertEquals("12/12/2012", tableRows.get(3).getText());
		assertEquals("1.0", tableRows.get(4).getText());
		assertEquals("100", tableRows.get(5).getText());
	}
	
	/**
	 * Tests that a user will be presented with a popup before
	 * completely deleting an entry.
	 */
	public void testDeleteExerciseDiaryEntry() throws Exception {
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("1", "pw"); //login as Random
		driver.setJavascriptEnabled(true);
		driver.findElement(By.linkText("My Exercise Diary")).click();
		assertEquals("iTrust - View My Exercise Diary", driver.getTitle());
		
		/* make sure the values are right to begin with
		now find the values individually (name:row#)
		have to do it by value since they are input fields
		used for editing and deleting */
		WebElement valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/14/2012", valDate.getAttribute(VALUE));
		WebElement valType = driver.findElement(By.name("ExerciseType:0"));
		assertEquals("Weight Training", valType.getAttribute(VALUE));
		WebElement valName = driver.findElement(By.name("Name:0"));
		assertEquals("Bench Press", valName.getAttribute(VALUE));
		WebElement valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("0.5", valHours.getAttribute(VALUE));
		WebElement valCalories = driver.findElement(By.name("Calories:0"));
		assertEquals("50", valCalories.getAttribute(VALUE));
		WebElement valSets = driver.findElement(By.name("Sets:0"));
		assertEquals("3", valSets.getAttribute(VALUE));
		WebElement valReps = driver.findElement(By.name("Reps:0"));
		assertEquals("10", valReps.getAttribute(VALUE));
		
		//now assert that the totals are correct
		WebElement totalTable = 
				driver.findElements(By.tagName("table")).get(2);
		List<WebElement> tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("0.5", tableRows.get(1).getText());
		assertEquals("50", tableRows.get(2).getText());
		assertEquals("12/12/2012", tableRows.get(3).getText());
		assertEquals("1.0", tableRows.get(4).getText());
		assertEquals("100", tableRows.get(5).getText());
		
		//now click to delete the first entry
		WebElement button = driver.findElement(By.cssSelector("input[value='Delete Entry']"));
		
		/*htmlunitdriver doesn't work with confirms well, and this is the
			best workaround there is */
		driver.executeScript("window.confirm=function() {return true;}");
		button.click();
		
		//assert entries and totals updated (Running is now the first entry)
		valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/12/2012", valDate.getAttribute(VALUE));
		valType = driver.findElement(By.name("ExerciseType:0"));
		assertEquals("Cardio", valType.getAttribute(VALUE));
		valName = driver.findElement(By.name("Name:0"));
		assertEquals("Running", valName.getAttribute(VALUE));
		valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("1.0", valHours.getAttribute(VALUE));
		valCalories = driver.findElement(By.name("Calories:0"));
		assertEquals("100", valCalories.getAttribute(VALUE));
		valSets = driver.findElement(By.name("Sets:0"));
		assertEquals("N/A", valSets.getAttribute(VALUE));
		valReps = driver.findElement(By.name("Reps:0"));
		assertEquals("N/A", valReps.getAttribute(VALUE));
		
		//now assert that the totals are correct
		totalTable = driver.findElements(By.tagName("table")).get(2);
		tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/12/2012", tableRows.get(0).getText());
		assertEquals("1.0", tableRows.get(1).getText());
		assertEquals("100", tableRows.get(2).getText());
	}
	
	/**
	 * Tests that a user can cancel deletion of an entry and that
	 * nothing will happen.
	 */
	public void testCancelDelete() throws Exception {
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("1", "pw"); //login as Random
		driver.setJavascriptEnabled(true);
		driver.findElement(By.linkText("My Exercise Diary")).click();
		assertEquals("iTrust - View My Exercise Diary", driver.getTitle());
		
		/* make sure the values are right to begin with
		now find the values individually (name:row#)
		have to do it by value since they are input fields
		used for editing and deleting */
		WebElement valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/14/2012", valDate.getAttribute(VALUE));
		WebElement valType = driver.findElement(By.name("ExerciseType:0"));
		assertEquals("Weight Training", valType.getAttribute(VALUE));
		WebElement valName = driver.findElement(By.name("Name:0"));
		assertEquals("Bench Press", valName.getAttribute(VALUE));
		WebElement valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("0.5", valHours.getAttribute(VALUE));
		WebElement valCalories = driver.findElement(By.name("Calories:0"));
		assertEquals("50", valCalories.getAttribute(VALUE));
		WebElement valSets = driver.findElement(By.name("Sets:0"));
		assertEquals("3", valSets.getAttribute(VALUE));
		WebElement valReps = driver.findElement(By.name("Reps:0"));
		assertEquals("10", valReps.getAttribute(VALUE));
		
		//now assert that the totals are correct
		WebElement totalTable = 
				driver.findElements(By.tagName("table")).get(2);
		List<WebElement> tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("0.5", tableRows.get(1).getText());
		assertEquals("50", tableRows.get(2).getText());
		assertEquals("12/12/2012", tableRows.get(3).getText());
		assertEquals("1.0", tableRows.get(4).getText());
		assertEquals("100", tableRows.get(5).getText());
		
		//now click to delete the first entry
		WebElement button = driver.findElement(By.cssSelector("input[value='Delete Entry']"));
		
		/*htmlunitdriver doesn't work with confirms well, and this is the
			best workaround there is */
		driver.executeScript("window.confirm=function() {return false;}");
		button.click();
		
		//assert entries and totals updated (Running is now the first entry)
		valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/14/2012", valDate.getAttribute(VALUE));
		valType = driver.findElement(By.name("ExerciseType:0"));
		assertEquals("Weight Training", valType.getAttribute(VALUE));
		valName = driver.findElement(By.name("Name:0"));
		assertEquals("Bench Press", valName.getAttribute(VALUE));
		valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("0.5", valHours.getAttribute(VALUE));
		valCalories = driver.findElement(By.name("Calories:0"));
		assertEquals("50", valCalories.getAttribute(VALUE));
		valSets = driver.findElement(By.name("Sets:0"));
		assertEquals("3", valSets.getAttribute(VALUE));
		valReps = driver.findElement(By.name("Reps:0"));
		assertEquals("10", valReps.getAttribute(VALUE));
		
		//now assert that the totals are correct
		totalTable = driver.findElements(By.tagName("table")).get(2);
		tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("0.5", tableRows.get(1).getText());
		assertEquals("50", tableRows.get(2).getText());
		assertEquals("12/12/2012", tableRows.get(3).getText());
		assertEquals("1.0", tableRows.get(4).getText());
		assertEquals("100", tableRows.get(5).getText());
	}
	
	/**
	 * Tests that a user cannot enter an invalid date
	 * when editing a exercise diary entry.
	 */
	public void testEditEntryWithInvalidDate() throws Exception {
		//need to enable javascript
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("1", "pw"); //login as Random Person
		driver.setJavascriptEnabled(true);
		
		driver.findElement(By.linkText("My Exercise Diary")).click();
		assertEquals("iTrust - View My Exercise Diary", driver.getTitle());

		/* now find the values individually (name:row#)
			have to do it by value since they are input fields
			used for editing and deleting */
		WebElement valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/14/2012", valDate.getAttribute(VALUE));
		WebElement valType = driver.findElement(By.name("ExerciseType:0"));
		assertEquals("Weight Training", valType.getAttribute(VALUE));
		WebElement valName = driver.findElement(By.name("Name:0"));
		assertEquals("Bench Press", valName.getAttribute(VALUE));
		WebElement valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("0.5", valHours.getAttribute(VALUE));
		WebElement valCalories = driver.findElement(By.name("Calories:0"));
		assertEquals("50", valCalories.getAttribute(VALUE));
		WebElement valSets = driver.findElement(By.name("Sets:0"));
		assertEquals("3", valSets.getAttribute(VALUE));
		WebElement valReps = driver.findElement(By.name("Reps:0"));
		assertEquals("10", valReps.getAttribute(VALUE));
		
		//now assert that the totals are correct
		WebElement totalTable = 
				driver.findElements(By.tagName("table")).get(2);
		List<WebElement> tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("0.5", tableRows.get(1).getText());
		assertEquals("50", tableRows.get(2).getText());
		assertEquals("12/12/2012", tableRows.get(3).getText());
		assertEquals("1.0", tableRows.get(4).getText());
		assertEquals("100", tableRows.get(5).getText());
		
		/* click the edit entry button
		 * (each row in table has 14 and edit is 2nd last)
		 */
		WebElement button = driver.findElement(By.cssSelector("input[value='Edit Entry']"));
		button.click();
		
		//update entry (need to re-get the values since they're now enabled
		valDate = driver.findElement(By.name("Date:0"));
		valDate.clear();
		valDate.sendKeys("2012/12/12");
		valName = driver.findElement(By.name("Name:0"));
		valName.clear();
		valName.sendKeys("Bench Press");
		valHours = driver.findElement(By.name("Hours:0"));
		valHours.clear();
		valHours.sendKeys("0.5");
		valCalories = driver.findElement(By.name("Calories:0"));
		valCalories.clear();
		valCalories.sendKeys("50");
		valSets = driver.findElement(By.name("Sets:0"));
		valSets.clear();
		valSets.sendKeys("3");
		valReps = driver.findElement(By.name("Reps:0"));
		valReps.clear();
		valReps.sendKeys("10");
		//now submit it
		driver.findElements(By.cssSelector("input[value='Submit']"))
				.get(0).click();
		assertTrue(driver.getPageSource().contains("[Date Performed: MM/DD/YYYY]"));

		//now assert everything updated, including totals
		valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/14/2012", valDate.getAttribute(VALUE));
		valType = driver.findElement(By.name("ExerciseType:0"));
		assertEquals("Weight Training", valType.getAttribute(VALUE));
		valName = driver.findElement(By.name("Name:0"));
		assertEquals("Bench Press", valName.getAttribute(VALUE));
		valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("0.5", valHours.getAttribute(VALUE));
		valCalories = driver.findElement(By.name("Calories:0"));
		assertEquals("50", valCalories.getAttribute(VALUE));
		valSets = driver.findElement(By.name("Sets:0"));
		assertEquals("3", valSets.getAttribute(VALUE));
		valReps = driver.findElement(By.name("Reps:0"));
		assertEquals("10", valReps.getAttribute(VALUE));
		
		//now assert that the totals are correct
		totalTable = driver.findElements(By.tagName("table")).get(2);
		tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("0.5", tableRows.get(1).getText());
		assertEquals("50", tableRows.get(2).getText());
		assertEquals("12/12/2012", tableRows.get(3).getText());
		assertEquals("1.0", tableRows.get(4).getText());
		assertEquals("100", tableRows.get(5).getText());
	}
}
