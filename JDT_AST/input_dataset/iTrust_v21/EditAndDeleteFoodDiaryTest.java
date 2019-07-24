package edu.ncsu.csc.itrust.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Tests that patients can edit and delete entries in their
 * food diary.
 */
public class EditAndDeleteFoodDiaryTest extends iTrustSeleniumTest {

	/**
	 * Make sure we get the standard data for each call
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp(); // clear tables is called in super
		gen.standardData();
	}
	
	/**
	 * Test that a user can edit entries in their food diary.
	 */
	public void testEditFoodDiaryEntryValidValues() throws Exception {
		//need to enable javascript
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("336", "pw"); //login as Emily Prentiss
		driver.setJavascriptEnabled(true);
		driver.findElement(By.linkText("My Food Diary")).click();
		assertEquals("iTrust - View My Food Diary", driver.getTitle());
		
		
		/* now find the values individually (name:row#)
			have to do it by value since they are input fields
			used for editing and deleting */
		WebElement dateVal = driver.findElement(By.name("Date:0"));
		assertEquals("03/16/2014", dateVal.getAttribute(VALUE));
		Select mealVal = new Select(driver.findElement(By.name("MealType:0")));
		assertEquals("Lunch", mealVal.getFirstSelectedOption().getAttribute(VALUE));
		WebElement foodVal = driver.findElement(By.name("FoodName:0"));
		assertEquals("Chocolate Shake", foodVal.getAttribute(VALUE));
		WebElement servVal = driver.findElement(By.name("Servings:0"));
		assertEquals("2.0", servVal.getAttribute(VALUE));
		WebElement calVal = driver.findElement(By.name("Calories:0"));
		assertEquals("500.0", calVal.getAttribute(VALUE));
		WebElement fatVal = driver.findElement(By.name("Fat:0"));
		assertEquals("23.5", fatVal.getAttribute(VALUE));
		WebElement sodiumVal = driver.findElement(By.name("Sodium:0"));
		assertEquals("259.0", sodiumVal.getAttribute(VALUE));
		WebElement carbVal = driver.findElement(By.name("Carb:0"));
		assertEquals("66.5", carbVal.getAttribute(VALUE));
		WebElement fiberVal = driver.findElement(By.name("Fiber:0"));
		assertEquals("0.0", fiberVal.getAttribute(VALUE));
		WebElement sugarVal = driver.findElement(By.name("Sugar:0"));
		assertEquals("42.4", sugarVal.getAttribute(VALUE));
		WebElement proteinVal = driver.findElement(By.name("Protein:0"));
		assertEquals("5.9", proteinVal.getAttribute(VALUE));
		
		//now assert that the totals are correct
		WebElement totalTable = 
				driver.findElements(By.tagName("table")).get(2);
		List<WebElement> tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("03/16/2014", tableRows.get(0).getText());
		assertEquals("1000.0", tableRows.get(1).getText());
		assertEquals("47.0", tableRows.get(2).getText());
		assertEquals("518.0", tableRows.get(3).getText());
		assertEquals("133.0", tableRows.get(4).getText());
		assertEquals("0.0", tableRows.get(5).getText());
		assertEquals("84.8", tableRows.get(6).getText());
		assertEquals("11.8", tableRows.get(7).getText());
		
		/* click the edit entry button
		 * (each row in table has 14 and edit is 2nd last)
		 */
		WebElement button = 
				driver.findElement(By.cssSelector("input[value='Edit Entry']"));
		button.click();
		//update entry (need to reget the values since they're now enabled
		servVal = driver.findElement(By.name("Servings:0"));
		servVal.clear();
		servVal.sendKeys("3");
		calVal = driver.findElement(By.name("Calories:0"));
		calVal.clear();
		calVal.sendKeys("1327");
		fatVal = driver.findElement(By.name("Fat:0"));
		fatVal.clear();
		fatVal.sendKeys("62.5");
		sodiumVal = driver.findElement(By.name("Sodium:0"));
		sodiumVal.clear();
		sodiumVal.sendKeys("687");
		carbVal = driver.findElement(By.name("Carb:0"));
		carbVal.clear();
		carbVal.sendKeys("176.4");
		sugarVal = driver.findElement(By.name("Sugar:0"));
		sugarVal.clear();
		sugarVal.sendKeys("112.4");
		proteinVal = driver.findElement(By.name("Protein:0"));
		proteinVal.clear();
		proteinVal.sendKeys("15.6");
		//now submit it
		driver.findElements(By.cssSelector("input[value='Submit']"))
				.get(0).click();
		assertTrue(driver.getPageSource().contains("Congratulations!"));
		
		//now assert everything updated, including totals
		dateVal = driver.findElement(By.name("Date:0"));
		assertEquals("03/16/2014", dateVal.getAttribute(VALUE));
		mealVal = new Select(driver.findElement(By.name("MealType:0")));
		assertEquals("Lunch", mealVal.getFirstSelectedOption().getAttribute(VALUE));
		foodVal = driver.findElement(By.name("FoodName:0"));
		assertEquals("Chocolate Shake", foodVal.getAttribute(VALUE));
		servVal = driver.findElement(By.name("Servings:0"));
		assertEquals("3.0", servVal.getAttribute(VALUE));
		calVal = driver.findElement(By.name("Calories:0"));
		assertEquals("1327.0", calVal.getAttribute(VALUE));
		fatVal = driver.findElement(By.name("Fat:0"));
		assertEquals("62.5", fatVal.getAttribute(VALUE));
		sodiumVal = driver.findElement(By.name("Sodium:0"));
		assertEquals("687.0", sodiumVal.getAttribute(VALUE));
		carbVal = driver.findElement(By.name("Carb:0"));
		assertEquals("176.4", carbVal.getAttribute(VALUE));
		fiberVal = driver.findElement(By.name("Fiber:0"));
		assertEquals("0.0", fiberVal.getAttribute(VALUE));
		sugarVal = driver.findElement(By.name("Sugar:0"));
		assertEquals("112.4", sugarVal.getAttribute(VALUE));
		proteinVal = driver.findElement(By.name("Protein:0"));
		assertEquals("15.6", proteinVal.getAttribute(VALUE));
		
		//now assert that the totals are correct
		totalTable = 
				driver.findElements(By.tagName("table")).get(2);
		tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("03/16/2014", tableRows.get(0).getText());
		assertEquals("3981.0", tableRows.get(1).getText());
		assertEquals("187.5", tableRows.get(2).getText());
		assertEquals("2061.0", tableRows.get(3).getText());
		assertEquals("529.2", tableRows.get(4).getText());
		assertEquals("0.0", tableRows.get(5).getText());
		assertEquals("337.2", tableRows.get(6).getText().substring(0, 5));
		assertEquals("46.8", tableRows.get(7).getText());
	}
	
	/**
	 * Tests that a Patient cannot enter invalid values
	 * when editing a Food Diary entry.
	 */
	public void testEditFoodDiaryEntryInvalidValues() throws Exception {
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("335", "pw");
		driver.setJavascriptEnabled(true);
		driver.findElement(By.linkText("My Food Diary")).click();
		assertEquals("iTrust - View My Food Diary", driver.getTitle());
		
		//assert that the original values are there
		WebElement dateVal = driver.findElement(By.name("Date:0"));
		assertEquals("04/13/2014", dateVal.getAttribute(VALUE));
		Select mealVal = new Select(driver.findElement(By.name("MealType:0")));
		assertEquals("Snack", mealVal.getFirstSelectedOption().getAttribute(VALUE));
		WebElement foodVal = driver.findElement(By.name("FoodName:0"));
		assertEquals("Oreos", foodVal.getAttribute(VALUE));
		WebElement servVal = driver.findElement(By.name("Servings:0"));
		assertEquals("53.0", servVal.getAttribute(VALUE));
		WebElement calVal = driver.findElement(By.name("Calories:0"));
		assertEquals("140.0", calVal.getAttribute(VALUE));
		WebElement fatVal = driver.findElement(By.name("Fat:0"));
		assertEquals("7.0", fatVal.getAttribute(VALUE));
		WebElement sodiumVal = driver.findElement(By.name("Sodium:0"));
		assertEquals("90.0", sodiumVal.getAttribute(VALUE));
		WebElement carbVal = driver.findElement(By.name("Carb:0"));
		assertEquals("21.0", carbVal.getAttribute(VALUE));
		WebElement fiberVal = driver.findElement(By.name("Fiber:0"));
		assertEquals("1.0", fiberVal.getAttribute(VALUE));
		WebElement sugarVal = driver.findElement(By.name("Sugar:0"));
		assertEquals("13.0", sugarVal.getAttribute(VALUE));
		WebElement proteinVal = driver.findElement(By.name("Protein:0"));
		assertEquals("0.0", proteinVal.getAttribute(VALUE));
		
		//second entry
		WebElement dateVal2 = driver.findElement(By.name("Date:1"));
		assertEquals("05/21/2013", dateVal2.getAttribute(VALUE));
		Select mealVal2 = new Select(driver.findElement(By.name("MealType:1")));
		assertEquals("Breakfast", mealVal2.getFirstSelectedOption().getAttribute(VALUE));
		WebElement foodVal2 = driver.findElement(By.name("FoodName:1"));
		assertEquals("Cheese and Bean Dip", foodVal2.getAttribute(VALUE));
		WebElement servVal2 = driver.findElement(By.name("Servings:1"));
		assertEquals("0.75", servVal2.getAttribute(VALUE));
		WebElement calVal2 = driver.findElement(By.name("Calories:1"));
		assertEquals("45.0", calVal2.getAttribute(VALUE));
		WebElement fatVal2 = driver.findElement(By.name("Fat:1"));
		assertEquals("2.0", fatVal2.getAttribute(VALUE));
		WebElement sodiumVal2 = driver.findElement(By.name("Sodium:1"));
		assertEquals("230.0", sodiumVal2.getAttribute(VALUE));
		WebElement carbVal2 = driver.findElement(By.name("Carb:1"));
		assertEquals("5.0", carbVal2.getAttribute(VALUE));
		WebElement fiberVal2 = driver.findElement(By.name("Fiber:1"));
		assertEquals("2.0", fiberVal2.getAttribute(VALUE));
		WebElement sugarVal2 = driver.findElement(By.name("Sugar:1"));
		assertEquals("0.0", sugarVal2.getAttribute(VALUE));
		WebElement proteinVal2 = driver.findElement(By.name("Protein:1"));
		assertEquals("2.0", proteinVal2.getAttribute(VALUE));
		
		//now assert that the totals are correct
		WebElement totalTable = 
				driver.findElements(By.tagName("table")).get(2);
		List<WebElement> tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("04/13/2014", tableRows.get(0).getText());
		assertEquals("7420.0", tableRows.get(1).getText());
		assertEquals("371.0", tableRows.get(2).getText());
		assertEquals("4770.0", tableRows.get(3).getText());
		assertEquals("1113.0", tableRows.get(4).getText());
		assertEquals("53.0", tableRows.get(5).getText());
		assertEquals("689.0", tableRows.get(6).getText());
		assertEquals("0.0", tableRows.get(7).getText());
		
		//second entry of totals
		assertEquals("05/21/2013", tableRows.get(8).getText());
		assertEquals("33.75", tableRows.get(9).getText());
		assertEquals("1.5", tableRows.get(10).getText());
		assertEquals("172.5", tableRows.get(11).getText());
		assertEquals("3.75", tableRows.get(12).getText());
		assertEquals("1.5", tableRows.get(13).getText());
		assertEquals("0.0", tableRows.get(14).getText());
		assertEquals("1.5", tableRows.get(15).getText());
		
		//make sure javascript is enabled
		driver.setJavascriptEnabled(true);
		
		//click to edit the first row
		WebElement button = driver.findElement(By.cssSelector("input[value='Edit Entry']"));
		button.click();
		driver.findElement(By.name("Servings:0")).clear();
		driver.findElement(By.name("Servings:0")).sendKeys("-17");
		//now click to submit the edit
		driver.findElements(By.cssSelector("input[value='Submit']")).get(0).click();
		assertTrue(driver.getPageSource().contains("Number of Servings "
				+ "must be greater than 0"));
		
		//assert nothing changed
		dateVal = driver.findElement(By.name("Date:0"));
		assertEquals("04/13/2014", dateVal.getAttribute(VALUE));
		mealVal = new Select(driver.findElement(By.name("MealType:0")));
		assertEquals("Snack", mealVal.getFirstSelectedOption().getAttribute(VALUE));
		foodVal = driver.findElement(By.name("FoodName:0"));
		assertEquals("Oreos", foodVal.getAttribute(VALUE));
		servVal = driver.findElement(By.name("Servings:0"));
		assertEquals("53.0", servVal.getAttribute(VALUE));
		calVal = driver.findElement(By.name("Calories:0"));
		assertEquals("140.0", calVal.getAttribute(VALUE));
		fatVal = driver.findElement(By.name("Fat:0"));
		assertEquals("7.0", fatVal.getAttribute(VALUE));
		sodiumVal = driver.findElement(By.name("Sodium:0"));
		assertEquals("90.0", sodiumVal.getAttribute(VALUE));
		carbVal = driver.findElement(By.name("Carb:0"));
		assertEquals("21.0", carbVal.getAttribute(VALUE));
		fiberVal = driver.findElement(By.name("Fiber:0"));
		assertEquals("1.0", fiberVal.getAttribute(VALUE));
		sugarVal = driver.findElement(By.name("Sugar:0"));
		assertEquals("13.0", sugarVal.getAttribute(VALUE));
		proteinVal = driver.findElement(By.name("Protein:0"));
		assertEquals("0.0", proteinVal.getAttribute(VALUE));
		
		//second entry
		dateVal2 = driver.findElement(By.name("Date:1"));
		assertEquals("05/21/2013", dateVal2.getAttribute(VALUE));
		mealVal2 = new Select(driver.findElement(By.name("MealType:1")));
		assertEquals("Breakfast", mealVal2.getFirstSelectedOption().getAttribute(VALUE));
		foodVal2 = driver.findElement(By.name("FoodName:1"));
		assertEquals("Cheese and Bean Dip", foodVal2.getAttribute(VALUE));
		servVal2 = driver.findElement(By.name("Servings:1"));
		assertEquals("0.75", servVal2.getAttribute(VALUE));
		calVal2 = driver.findElement(By.name("Calories:1"));
		assertEquals("45.0", calVal2.getAttribute(VALUE));
		fatVal2 = driver.findElement(By.name("Fat:1"));
		assertEquals("2.0", fatVal2.getAttribute(VALUE));
		sodiumVal2 = driver.findElement(By.name("Sodium:1"));
		assertEquals("230.0", sodiumVal2.getAttribute(VALUE));
		carbVal2 = driver.findElement(By.name("Carb:1"));
		assertEquals("5.0", carbVal2.getAttribute(VALUE));
		fiberVal2 = driver.findElement(By.name("Fiber:1"));
		assertEquals("2.0", fiberVal2.getAttribute(VALUE));
		sugarVal2 = driver.findElement(By.name("Sugar:1"));
		assertEquals("0.0", sugarVal2.getAttribute(VALUE));
		proteinVal2 = driver.findElement(By.name("Protein:1"));
		assertEquals("2.0", proteinVal2.getAttribute(VALUE));
		
		//now assert that the totals are correct
		totalTable = driver.findElements(By.tagName("table")).get(2);
		tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("04/13/2014", tableRows.get(0).getText());
		assertEquals("7420.0", tableRows.get(1).getText());
		assertEquals("371.0", tableRows.get(2).getText());
		assertEquals("4770.0", tableRows.get(3).getText());
		assertEquals("1113.0", tableRows.get(4).getText());
		assertEquals("53.0", tableRows.get(5).getText());
		assertEquals("689.0", tableRows.get(6).getText());
		assertEquals("0.0", tableRows.get(7).getText());
		
		//second entry of totals
		assertEquals("05/21/2013", tableRows.get(8).getText());
		assertEquals("33.75", tableRows.get(9).getText());
		assertEquals("1.5", tableRows.get(10).getText());
		assertEquals("172.5", tableRows.get(11).getText());
		assertEquals("3.75", tableRows.get(12).getText());
		assertEquals("1.5", tableRows.get(13).getText());
		assertEquals("0.0", tableRows.get(14).getText());
		assertEquals("1.5", tableRows.get(15).getText());
	}
	
	/**
	 * Tests that a user will be presented with a popup before
	 * completely deleting an entry.
	 */
	public void testDeleteFoodDiaryEntry() throws Exception {
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("334", "pw"); //login as Jennifer
		driver.setJavascriptEnabled(true);
		driver.findElement(By.linkText("My Food Diary")).click();
		assertEquals("iTrust - View My Food Diary", driver.getTitle());
		
		/* make sure the values are right to begin with
		now find the values individually (name:row#)
		have to do it by value since they are input fields
		used for editing and deleting */
		WebElement dateVal = driver.findElement(By.name("Date:0"));
		assertEquals("09/30/2012", dateVal.getAttribute(VALUE));
		Select mealVal = new Select(driver.findElement(By.name("MealType:0")));
		assertEquals("Breakfast", mealVal.getFirstSelectedOption().getAttribute(VALUE));
		WebElement foodVal = driver.findElement(By.name("FoodName:0"));
		assertEquals("Hot dog", foodVal.getAttribute(VALUE));
		WebElement servVal = driver.findElement(By.name("Servings:0"));
		assertEquals("4.0", servVal.getAttribute(VALUE));
		WebElement calVal = driver.findElement(By.name("Calories:0"));
		assertEquals("80.0", calVal.getAttribute(VALUE));
		WebElement fatVal = driver.findElement(By.name("Fat:0"));
		assertEquals("5.0", fatVal.getAttribute(VALUE));
		WebElement sodiumVal = driver.findElement(By.name("Sodium:0"));
		assertEquals("480.0", sodiumVal.getAttribute(VALUE));
		WebElement carbVal = driver.findElement(By.name("Carb:0"));
		assertEquals("2.0", carbVal.getAttribute(VALUE));
		WebElement fiberVal = driver.findElement(By.name("Fiber:0"));
		assertEquals("0.0", fiberVal.getAttribute(VALUE));
		WebElement sugarVal = driver.findElement(By.name("Sugar:0"));
		assertEquals("0.0", sugarVal.getAttribute(VALUE));
		WebElement proteinVal = driver.findElement(By.name("Protein:0"));
		assertEquals("5.0", proteinVal.getAttribute(VALUE));
		
		//get the second day's values
		WebElement dateVal2 = driver.findElement(By.name("Date:1"));
		assertEquals("09/30/2012", dateVal2.getAttribute(VALUE));
		Select mealVal2 = new Select
				(driver.findElement(By.name("MealType:1")));
		assertEquals("Lunch", mealVal2.getFirstSelectedOption().getAttribute(VALUE));
		WebElement foodVal2 = driver.findElement(By.name("FoodName:1"));
		assertEquals("Mango Passionfruit Juice", foodVal2.getAttribute(VALUE));
		WebElement servVal2 = driver.findElement(By.name("Servings:1"));
		assertEquals("1.2", servVal2.getAttribute(VALUE));
		WebElement calVal2 = driver.findElement(By.name("Calories:1"));
		assertEquals("130.0", calVal2.getAttribute(VALUE));
		WebElement fatVal2 = driver.findElement(By.name("Fat:1"));
		assertEquals("0.0", fatVal2.getAttribute(VALUE));
		WebElement sodiumVal2 = driver.findElement(By.name("Sodium:1"));
		assertEquals("25.0", sodiumVal2.getAttribute(VALUE));
		WebElement carbVal2 = driver.findElement(By.name("Carb:1"));
		assertEquals("32.0", carbVal2.getAttribute(VALUE));
		WebElement fiberVal2 = driver.findElement(By.name("Fiber:1"));
		assertEquals("0.0", fiberVal2.getAttribute(VALUE));
		WebElement sugarVal2 = driver.findElement(By.name("Sugar:1"));
		assertEquals("29.0", sugarVal2.getAttribute(VALUE));
		WebElement proteinVal2 = driver.findElement(By.name("Protein:1"));
		assertEquals("1.0", proteinVal2.getAttribute(VALUE));
		
		//now assert that the totals are correct
		WebElement totalTable = 
				driver.findElements(By.tagName("table")).get(2);
		List<WebElement> tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("09/30/2012", tableRows.get(0).getText());
		assertEquals("476.0", tableRows.get(1).getText());
		assertEquals("20.0", tableRows.get(2).getText());
		assertEquals("1950.0", tableRows.get(3).getText());
		assertEquals("46.4", tableRows.get(4).getText());
		assertEquals("0.0", tableRows.get(5).getText());
		assertEquals("34.8", tableRows.get(6).getText());
		assertEquals("21.2", tableRows.get(7).getText());
		
		//now click to delete the first entry
		driver.setJavascriptEnabled(true);
		WebElement button = driver.findElement(By.cssSelector("input[value='Delete Entry']"));
		
		/*htmlunitdriver doesn't work with confirms well, and this is the
			best workaround there is */
		driver.executeScript("window.confirm=function() {return true;}");
		button.click();
		
		//assert entries and totals updated
		dateVal2 = driver.findElement(By.name("Date:0"));
		assertEquals("09/30/2012", dateVal2.getAttribute(VALUE));
		mealVal2 = new Select
				(driver.findElement(By.name("MealType:0")));
		assertEquals("Lunch", mealVal2.getFirstSelectedOption().getAttribute(VALUE));
		foodVal2 = driver.findElement(By.name("FoodName:0"));
		assertEquals("Mango Passionfruit Juice", foodVal2.getAttribute(VALUE));
		servVal2 = driver.findElement(By.name("Servings:0"));
		assertEquals("1.2", servVal2.getAttribute(VALUE));
		calVal2 = driver.findElement(By.name("Calories:0"));
		assertEquals("130.0", calVal2.getAttribute(VALUE));
		fatVal2 = driver.findElement(By.name("Fat:0"));
		assertEquals("0.0", fatVal2.getAttribute(VALUE));
		sodiumVal2 = driver.findElement(By.name("Sodium:0"));
		assertEquals("25.0", sodiumVal2.getAttribute(VALUE));
		carbVal2 = driver.findElement(By.name("Carb:0"));
		assertEquals("32.0", carbVal2.getAttribute(VALUE));
		fiberVal2 = driver.findElement(By.name("Fiber:0"));
		assertEquals("0.0", fiberVal2.getAttribute(VALUE));
		sugarVal2 = driver.findElement(By.name("Sugar:0"));
		assertEquals("29.0", sugarVal2.getAttribute(VALUE));
		proteinVal2 = driver.findElement(By.name("Protein:0"));
		assertEquals("1.0", proteinVal2.getAttribute(VALUE));
		
		//now assert that the totals are correct
		totalTable = driver.findElements(By.tagName("table")).get(2);
		tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("09/30/2012", tableRows.get(0).getText());
		assertEquals("156.0", tableRows.get(1).getText());
		assertEquals("0.0", tableRows.get(2).getText());
		assertEquals("30.0", tableRows.get(3).getText());
		assertEquals("38.4", tableRows.get(4).getText());
		assertEquals("0.0", tableRows.get(5).getText());
		assertEquals("34.8", tableRows.get(6).getText());
		assertEquals("1.2", tableRows.get(7).getText());
	}
	
	/**
	 * Tests that a user can cancel deletion of an entry and that
	 * nothing will happen.
	 */
	public void testCancelDelete() throws Exception {
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("335", "pw");
		driver.setJavascriptEnabled(true);
		driver.findElement(By.linkText("My Food Diary")).click();
		assertEquals("iTrust - View My Food Diary", driver.getTitle());
		
		//assert that the original values are there
		WebElement dateVal = driver.findElement(By.name("Date:0"));
		assertEquals("04/13/2014", dateVal.getAttribute(VALUE));
		Select mealVal = new Select(driver.findElement(By.name("MealType:0")));
		assertEquals("Snack", mealVal.getFirstSelectedOption().getAttribute(VALUE));
		WebElement foodVal = driver.findElement(By.name("FoodName:0"));
		assertEquals("Oreos", foodVal.getAttribute(VALUE));
		WebElement servVal = driver.findElement(By.name("Servings:0"));
		assertEquals("53.0", servVal.getAttribute(VALUE));
		WebElement calVal = driver.findElement(By.name("Calories:0"));
		assertEquals("140.0", calVal.getAttribute(VALUE));
		WebElement fatVal = driver.findElement(By.name("Fat:0"));
		assertEquals("7.0", fatVal.getAttribute(VALUE));
		WebElement sodiumVal = driver.findElement(By.name("Sodium:0"));
		assertEquals("90.0", sodiumVal.getAttribute(VALUE));
		WebElement carbVal = driver.findElement(By.name("Carb:0"));
		assertEquals("21.0", carbVal.getAttribute(VALUE));
		WebElement fiberVal = driver.findElement(By.name("Fiber:0"));
		assertEquals("1.0", fiberVal.getAttribute(VALUE));
		WebElement sugarVal = driver.findElement(By.name("Sugar:0"));
		assertEquals("13.0", sugarVal.getAttribute(VALUE));
		WebElement proteinVal = driver.findElement(By.name("Protein:0"));
		assertEquals("0.0", proteinVal.getAttribute(VALUE));
		
		//second entry
		WebElement dateVal2 = driver.findElement(By.name("Date:1"));
		assertEquals("05/21/2013", dateVal2.getAttribute(VALUE));
		Select mealVal2 = new Select(driver.findElement(By.name("MealType:1")));
		assertEquals("Breakfast", mealVal2.getFirstSelectedOption().getAttribute(VALUE));
		WebElement foodVal2 = driver.findElement(By.name("FoodName:1"));
		assertEquals("Cheese and Bean Dip", foodVal2.getAttribute(VALUE));
		WebElement servVal2 = driver.findElement(By.name("Servings:1"));
		assertEquals("0.75", servVal2.getAttribute(VALUE));
		WebElement calVal2 = driver.findElement(By.name("Calories:1"));
		assertEquals("45.0", calVal2.getAttribute(VALUE));
		WebElement fatVal2 = driver.findElement(By.name("Fat:1"));
		assertEquals("2.0", fatVal2.getAttribute(VALUE));
		WebElement sodiumVal2 = driver.findElement(By.name("Sodium:1"));
		assertEquals("230.0", sodiumVal2.getAttribute(VALUE));
		WebElement carbVal2 = driver.findElement(By.name("Carb:1"));
		assertEquals("5.0", carbVal2.getAttribute(VALUE));
		WebElement fiberVal2 = driver.findElement(By.name("Fiber:1"));
		assertEquals("2.0", fiberVal2.getAttribute(VALUE));
		WebElement sugarVal2 = driver.findElement(By.name("Sugar:1"));
		assertEquals("0.0", sugarVal2.getAttribute(VALUE));
		WebElement proteinVal2 = driver.findElement(By.name("Protein:1"));
		assertEquals("2.0", proteinVal2.getAttribute(VALUE));
		
		//now assert that the totals are correct
		WebElement totalTable = 
				driver.findElements(By.tagName("table")).get(2);
		List<WebElement> tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("04/13/2014", tableRows.get(0).getText());
		assertEquals("7420.0", tableRows.get(1).getText());
		assertEquals("371.0", tableRows.get(2).getText());
		assertEquals("4770.0", tableRows.get(3).getText());
		assertEquals("1113.0", tableRows.get(4).getText());
		assertEquals("53.0", tableRows.get(5).getText());
		assertEquals("689.0", tableRows.get(6).getText());
		assertEquals("0.0", tableRows.get(7).getText());
		
		//second entry of totals
		assertEquals("05/21/2013", tableRows.get(8).getText());
		assertEquals("33.75", tableRows.get(9).getText());
		assertEquals("1.5", tableRows.get(10).getText());
		assertEquals("172.5", tableRows.get(11).getText());
		assertEquals("3.75", tableRows.get(12).getText());
		assertEquals("1.5", tableRows.get(13).getText());
		assertEquals("0.0", tableRows.get(14).getText());
		assertEquals("1.5", tableRows.get(15).getText());

		//now click to delete the first entry
		driver.setJavascriptEnabled(true);
		WebElement button = driver.findElement(By.cssSelector("input[value='Delete Entry']"));
		
		/*htmlunitdriver doesn't work with confirms well, and this is the
			best workaround there is */
		driver.executeScript("window.confirm=function() {return false;}");
		button.click();
		
		//assert that nothing changed
		dateVal = driver.findElement(By.name("Date:0"));
		assertEquals("04/13/2014", dateVal.getAttribute(VALUE));
		mealVal = new Select(driver.findElement(By.name("MealType:0")));
		assertEquals("Snack", mealVal.getFirstSelectedOption().getAttribute(VALUE));
		foodVal = driver.findElement(By.name("FoodName:0"));
		assertEquals("Oreos", foodVal.getAttribute(VALUE));
		servVal = driver.findElement(By.name("Servings:0"));
		assertEquals("53.0", servVal.getAttribute(VALUE));
		calVal = driver.findElement(By.name("Calories:0"));
		assertEquals("140.0", calVal.getAttribute(VALUE));
		fatVal = driver.findElement(By.name("Fat:0"));
		assertEquals("7.0", fatVal.getAttribute(VALUE));
		sodiumVal = driver.findElement(By.name("Sodium:0"));
		assertEquals("90.0", sodiumVal.getAttribute(VALUE));
		carbVal = driver.findElement(By.name("Carb:0"));
		assertEquals("21.0", carbVal.getAttribute(VALUE));
		fiberVal = driver.findElement(By.name("Fiber:0"));
		assertEquals("1.0", fiberVal.getAttribute(VALUE));
		sugarVal = driver.findElement(By.name("Sugar:0"));
		assertEquals("13.0", sugarVal.getAttribute(VALUE));
		proteinVal = driver.findElement(By.name("Protein:0"));
		assertEquals("0.0", proteinVal.getAttribute(VALUE));
		
		//second entry
		dateVal2 = driver.findElement(By.name("Date:1"));
		assertEquals("05/21/2013", dateVal2.getAttribute(VALUE));
		mealVal2 = new Select(driver.findElement(By.name("MealType:1")));
		assertEquals("Breakfast", mealVal2.getFirstSelectedOption().getAttribute(VALUE));
		foodVal2 = driver.findElement(By.name("FoodName:1"));
		assertEquals("Cheese and Bean Dip", foodVal2.getAttribute(VALUE));
		servVal2 = driver.findElement(By.name("Servings:1"));
		assertEquals("0.75", servVal2.getAttribute(VALUE));
		calVal2 = driver.findElement(By.name("Calories:1"));
		assertEquals("45.0", calVal2.getAttribute(VALUE));
		fatVal2 = driver.findElement(By.name("Fat:1"));
		assertEquals("2.0", fatVal2.getAttribute(VALUE));
		sodiumVal2 = driver.findElement(By.name("Sodium:1"));
		assertEquals("230.0", sodiumVal2.getAttribute(VALUE));
		carbVal2 = driver.findElement(By.name("Carb:1"));
		assertEquals("5.0", carbVal2.getAttribute(VALUE));
		fiberVal2 = driver.findElement(By.name("Fiber:1"));
		assertEquals("2.0", fiberVal2.getAttribute(VALUE));
		sugarVal2 = driver.findElement(By.name("Sugar:1"));
		assertEquals("0.0", sugarVal2.getAttribute(VALUE));
		proteinVal2 = driver.findElement(By.name("Protein:1"));
		assertEquals("2.0", proteinVal2.getAttribute(VALUE));
		
		//now assert that the totals are correct
		totalTable = driver.findElements(By.tagName("table")).get(2);
		tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("04/13/2014", tableRows.get(0).getText());
		assertEquals("7420.0", tableRows.get(1).getText());
		assertEquals("371.0", tableRows.get(2).getText());
		assertEquals("4770.0", tableRows.get(3).getText());
		assertEquals("1113.0", tableRows.get(4).getText());
		assertEquals("53.0", tableRows.get(5).getText());
		assertEquals("689.0", tableRows.get(6).getText());
		assertEquals("0.0", tableRows.get(7).getText());
		
		//second entry of totals
		assertEquals("05/21/2013", tableRows.get(8).getText());
		assertEquals("33.75", tableRows.get(9).getText());
		assertEquals("1.5", tableRows.get(10).getText());
		assertEquals("172.5", tableRows.get(11).getText());
		assertEquals("3.75", tableRows.get(12).getText());
		assertEquals("1.5", tableRows.get(13).getText());
		assertEquals("0.0", tableRows.get(14).getText());
		assertEquals("1.5", tableRows.get(15).getText());
	}
	
	/**
	 * Tests that a user cannot enter an invalid date
	 * when editing a food diary entry.
	 */
	public void testEditEntryWithInvalidDate() throws Exception {
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("335", "pw");
		driver.setJavascriptEnabled(true);
		driver.findElement(By.linkText("My Food Diary")).click();
		assertEquals("iTrust - View My Food Diary", driver.getTitle());
		
		//assert that the original values are there
		WebElement dateVal = driver.findElement(By.name("Date:0"));
		assertEquals("04/13/2014", dateVal.getAttribute(VALUE));
		Select mealVal = new Select(driver.findElement(By.name("MealType:0")));
		assertEquals("Snack", mealVal.getFirstSelectedOption().getAttribute(VALUE));
		WebElement foodVal = driver.findElement(By.name("FoodName:0"));
		assertEquals("Oreos", foodVal.getAttribute(VALUE));
		WebElement servVal = driver.findElement(By.name("Servings:0"));
		assertEquals("53.0", servVal.getAttribute(VALUE));
		WebElement calVal = driver.findElement(By.name("Calories:0"));
		assertEquals("140.0", calVal.getAttribute(VALUE));
		WebElement fatVal = driver.findElement(By.name("Fat:0"));
		assertEquals("7.0", fatVal.getAttribute(VALUE));
		WebElement sodiumVal = driver.findElement(By.name("Sodium:0"));
		assertEquals("90.0", sodiumVal.getAttribute(VALUE));
		WebElement carbVal = driver.findElement(By.name("Carb:0"));
		assertEquals("21.0", carbVal.getAttribute(VALUE));
		WebElement fiberVal = driver.findElement(By.name("Fiber:0"));
		assertEquals("1.0", fiberVal.getAttribute(VALUE));
		WebElement sugarVal = driver.findElement(By.name("Sugar:0"));
		assertEquals("13.0", sugarVal.getAttribute(VALUE));
		WebElement proteinVal = driver.findElement(By.name("Protein:0"));
		assertEquals("0.0", proteinVal.getAttribute(VALUE));
		
		//second entry
		WebElement dateVal2 = driver.findElement(By.name("Date:1"));
		assertEquals("05/21/2013", dateVal2.getAttribute(VALUE));
		Select mealVal2 = new Select(driver.findElement(By.name("MealType:1")));
		assertEquals("Breakfast", mealVal2.getFirstSelectedOption().getAttribute(VALUE));
		WebElement foodVal2 = driver.findElement(By.name("FoodName:1"));
		assertEquals("Cheese and Bean Dip", foodVal2.getAttribute(VALUE));
		WebElement servVal2 = driver.findElement(By.name("Servings:1"));
		assertEquals("0.75", servVal2.getAttribute(VALUE));
		WebElement calVal2 = driver.findElement(By.name("Calories:1"));
		assertEquals("45.0", calVal2.getAttribute(VALUE));
		WebElement fatVal2 = driver.findElement(By.name("Fat:1"));
		assertEquals("2.0", fatVal2.getAttribute(VALUE));
		WebElement sodiumVal2 = driver.findElement(By.name("Sodium:1"));
		assertEquals("230.0", sodiumVal2.getAttribute(VALUE));
		WebElement carbVal2 = driver.findElement(By.name("Carb:1"));
		assertEquals("5.0", carbVal2.getAttribute(VALUE));
		WebElement fiberVal2 = driver.findElement(By.name("Fiber:1"));
		assertEquals("2.0", fiberVal2.getAttribute(VALUE));
		WebElement sugarVal2 = driver.findElement(By.name("Sugar:1"));
		assertEquals("0.0", sugarVal2.getAttribute(VALUE));
		WebElement proteinVal2 = driver.findElement(By.name("Protein:1"));
		assertEquals("2.0", proteinVal2.getAttribute(VALUE));
		
		//now assert that the totals are correct
		WebElement totalTable = 
				driver.findElements(By.tagName("table")).get(2);
		List<WebElement> tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("04/13/2014", tableRows.get(0).getText());
		assertEquals("7420.0", tableRows.get(1).getText());
		assertEquals("371.0", tableRows.get(2).getText());
		assertEquals("4770.0", tableRows.get(3).getText());
		assertEquals("1113.0", tableRows.get(4).getText());
		assertEquals("53.0", tableRows.get(5).getText());
		assertEquals("689.0", tableRows.get(6).getText());
		assertEquals("0.0", tableRows.get(7).getText());
		
		//second entry of totals
		assertEquals("05/21/2013", tableRows.get(8).getText());
		assertEquals("33.75", tableRows.get(9).getText());
		assertEquals("1.5", tableRows.get(10).getText());
		assertEquals("172.5", tableRows.get(11).getText());
		assertEquals("3.75", tableRows.get(12).getText());
		assertEquals("1.5", tableRows.get(13).getText());
		assertEquals("0.0", tableRows.get(14).getText());
		assertEquals("1.5", tableRows.get(15).getText());
		
		//make sure javascript is enabled
		driver.setJavascriptEnabled(true);
		
		//click to edit the first row
		WebElement button = driver
				.findElement(By.cssSelector("input[value='Edit Entry']"));
		button.click();
		driver.findElement(By.name("Date:0")).clear();
		driver.findElement(By.name("Date:0")).sendKeys("2015/04/04");
		//now click to submit the edit
		driver.findElements(By.cssSelector("input[value='Submit']"))
				.get(0).click();
		assertTrue(driver.getPageSource().contains("Date Eaten: "
				+ "MM/DD/YYYY"));
		
		//assert nothing changed
		dateVal = driver.findElement(By.name("Date:0"));
		assertEquals("04/13/2014", dateVal.getAttribute(VALUE));
		mealVal = new Select(driver.findElement(By.name("MealType:0")));
		assertEquals("Snack", mealVal.getFirstSelectedOption().getAttribute(VALUE));
		foodVal = driver.findElement(By.name("FoodName:0"));
		assertEquals("Oreos", foodVal.getAttribute(VALUE));
		servVal = driver.findElement(By.name("Servings:0"));
		assertEquals("53.0", servVal.getAttribute(VALUE));
		calVal = driver.findElement(By.name("Calories:0"));
		assertEquals("140.0", calVal.getAttribute(VALUE));
		fatVal = driver.findElement(By.name("Fat:0"));
		assertEquals("7.0", fatVal.getAttribute(VALUE));
		sodiumVal = driver.findElement(By.name("Sodium:0"));
		assertEquals("90.0", sodiumVal.getAttribute(VALUE));
		carbVal = driver.findElement(By.name("Carb:0"));
		assertEquals("21.0", carbVal.getAttribute(VALUE));
		fiberVal = driver.findElement(By.name("Fiber:0"));
		assertEquals("1.0", fiberVal.getAttribute(VALUE));
		sugarVal = driver.findElement(By.name("Sugar:0"));
		assertEquals("13.0", sugarVal.getAttribute(VALUE));
		proteinVal = driver.findElement(By.name("Protein:0"));
		assertEquals("0.0", proteinVal.getAttribute(VALUE));
		
		//second entry
		dateVal2 = driver.findElement(By.name("Date:1"));
		assertEquals("05/21/2013", dateVal2.getAttribute(VALUE));
		mealVal2 = new Select(driver.findElement(By.name("MealType:1")));
		assertEquals("Breakfast", mealVal2.getFirstSelectedOption().getAttribute(VALUE));
		foodVal2 = driver.findElement(By.name("FoodName:1"));
		assertEquals("Cheese and Bean Dip", foodVal2.getAttribute(VALUE));
		servVal2 = driver.findElement(By.name("Servings:1"));
		assertEquals("0.75", servVal2.getAttribute(VALUE));
		calVal2 = driver.findElement(By.name("Calories:1"));
		assertEquals("45.0", calVal2.getAttribute(VALUE));
		fatVal2 = driver.findElement(By.name("Fat:1"));
		assertEquals("2.0", fatVal2.getAttribute(VALUE));
		sodiumVal2 = driver.findElement(By.name("Sodium:1"));
		assertEquals("230.0", sodiumVal2.getAttribute(VALUE));
		carbVal2 = driver.findElement(By.name("Carb:1"));
		assertEquals("5.0", carbVal2.getAttribute(VALUE));
		fiberVal2 = driver.findElement(By.name("Fiber:1"));
		assertEquals("2.0", fiberVal2.getAttribute(VALUE));
		sugarVal2 = driver.findElement(By.name("Sugar:1"));
		assertEquals("0.0", sugarVal2.getAttribute(VALUE));
		proteinVal2 = driver.findElement(By.name("Protein:1"));
		assertEquals("2.0", proteinVal2.getAttribute(VALUE));
		
		//now assert that the totals are correct
		totalTable = driver.findElements(By.tagName("table")).get(2);
		tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("04/13/2014", tableRows.get(0).getText());
		assertEquals("7420.0", tableRows.get(1).getText());
		assertEquals("371.0", tableRows.get(2).getText());
		assertEquals("4770.0", tableRows.get(3).getText());
		assertEquals("1113.0", tableRows.get(4).getText());
		assertEquals("53.0", tableRows.get(5).getText());
		assertEquals("689.0", tableRows.get(6).getText());
		assertEquals("0.0", tableRows.get(7).getText());
		
		//second entry of totals
		assertEquals("05/21/2013", tableRows.get(8).getText());
		assertEquals("33.75", tableRows.get(9).getText());
		assertEquals("1.5", tableRows.get(10).getText());
		assertEquals("172.5", tableRows.get(11).getText());
		assertEquals("3.75", tableRows.get(12).getText());
		assertEquals("1.5", tableRows.get(13).getText());
		assertEquals("0.0", tableRows.get(14).getText());
		assertEquals("1.5", tableRows.get(15).getText());
	}
}
