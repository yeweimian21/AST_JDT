package edu.ncsu.csc.itrust.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Tests the functionality of the new Food Diary portion of iTrust.
 * Tests that you can add new entries, you can view entries, HCPs with the role
 * of nutritionist can view entries, and error checks that the user enters the appropriate
 * data in the appropriate format.
 */
public class FoodDiaryTest extends iTrustSeleniumTest {
	
	/**
	 * Sets up the standard testing data.
	 */
	public void setUp() throws Exception {
		super.setUp();
		gen.standardData();
	}
	
	/**
	 * Tests that a patient can add an entry to an empty Food Diary.
	 * @throws Exception
	 */
	public void testAddFoodEntryToEmptyFoodDiary() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		driver = login("333", "pw"); //login as Derek Morgan
		
		//view my food diary so we can add a new entry
		driver.findElement(By.linkText("My Food Diary")).click();
		assertEquals("iTrust - View My Food Diary", driver.getTitle());
		driver.findElement(By.linkText("Add an entry to your Food Diary."))
			.click();
		
		assertEquals("Add a Food Entry", driver.getTitle());
		
		//now fill in all of the info and submit
		WebElement dateEaten = driver.findElement(By.name("dateEatenStr"));
		dateEaten.clear();
		dateEaten.sendKeys("02/04/2015");
		Select meal = new Select(driver.findElement(By.name("mealType")));
		meal.selectByValue("Dinner");
		WebElement food = driver.findElement(By.name("food"));
		food.clear();
		food.sendKeys("Fruity Pebbles");
		WebElement servings = driver.findElement(By.name("servings"));
		servings.clear();
		servings.sendKeys("7");
		WebElement cals = driver.findElement(By.name("calories"));
		cals.clear();
		cals.sendKeys("110");
		WebElement fat = driver.findElement(By.name("fat"));
		fat.clear();
		fat.sendKeys("1");
		WebElement sodium = driver.findElement(By.name("sodium"));
		sodium.clear();
		sodium.sendKeys("170");
		WebElement carbs = driver.findElement(By.name("carb"));
		carbs.clear();
		carbs.sendKeys("24");
		WebElement fiber = driver.findElement(By.name("fiber"));
		fiber.clear();
		fiber.sendKeys("0");
		WebElement sugar = driver.findElement(By.name("sugar"));
		sugar.clear();
		sugar.sendKeys("11");
		WebElement protein = driver.findElement(By.name("protein"));
		protein.clear();
		protein.sendKeys("1");
		driver.findElement(By.tagName("form")).submit();
		
		assertEquals("iTrust - View My Food Diary", driver.getTitle());
		
		/*	now find the values individually (name:row#)
			have to do it by value since they are input fields
			used for editing and deleting */
		WebElement dateVal = driver.findElement(By.name("Date:0"));
		assertEquals("02/04/2015", dateVal.getAttribute(VALUE));
		Select mealVal = new Select(driver.findElement(By.name("MealType:0")));
		assertEquals("Dinner", mealVal.getFirstSelectedOption().getAttribute(VALUE));
		WebElement foodVal = driver.findElement(By.name("FoodName:0"));
		assertEquals("Fruity Pebbles", foodVal.getAttribute(VALUE));
		WebElement servVal = driver.findElement(By.name("Servings:0"));
		assertEquals("7.0", servVal.getAttribute(VALUE));
		WebElement calVal = driver.findElement(By.name("Calories:0"));
		assertEquals("110.0", calVal.getAttribute(VALUE));
		WebElement fatVal = driver.findElement(By.name("Fat:0"));
		assertEquals("1.0", fatVal.getAttribute(VALUE));
		WebElement sodiumVal = driver.findElement(By.name("Sodium:0"));
		assertEquals("170.0", sodiumVal.getAttribute(VALUE));
		WebElement carbVal = driver.findElement(By.name("Carb:0"));
		assertEquals("24.0", carbVal.getAttribute(VALUE));
		WebElement fiberVal = driver.findElement(By.name("Fiber:0"));
		assertEquals("0.0", fiberVal.getAttribute(VALUE));
		WebElement sugarVal = driver.findElement(By.name("Sugar:0"));
		assertEquals("11.0", sugarVal.getAttribute(VALUE));
		WebElement proteinVal = driver.findElement(By.name("Protein:0"));
		assertEquals("1.0", proteinVal.getAttribute(VALUE));
	}
   
	
	/**
	 * Tests that a patient can add an entry to a non empty food diary.
	 * @throws Exception
	 */
	public void testAddFoodEntryToNonEmptyFoodDiary() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		driver = login("334", "pw"); //login as Jennifer Jareau
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
		
		//go to add a new entry
		driver.findElement(By.linkText("Add an entry to your Food Diary."))
			.click();
		assertEquals("Add a Food Entry", driver.getTitle());
		
		//enter new entry
		WebElement dateEaten = driver.findElement(By.name("dateEatenStr"));
		dateEaten.clear();
		dateEaten.sendKeys("11/12/2014");
		Select meal = new Select(driver.findElement(By.name("mealType")));
		meal.selectByValue("Snack");
		WebElement food = driver.findElement(By.name("food"));
		food.clear();
		food.sendKeys("Cookie Dough Ice Cream");
		WebElement servings = driver.findElement(By.name("servings"));
		servings.clear();
		servings.sendKeys(".5");
		WebElement cals = driver.findElement(By.name("calories"));
		cals.clear();
		cals.sendKeys("160");
		WebElement fat = driver.findElement(By.name("fat"));
		fat.clear();
		fat.sendKeys("8");
		WebElement sodium = driver.findElement(By.name("sodium"));
		sodium.clear();
		sodium.sendKeys("45");
		WebElement carbs = driver.findElement(By.name("carb"));
		carbs.clear();
		carbs.sendKeys("21");
		WebElement fiber = driver.findElement(By.name("fiber"));
		fiber.clear();
		fiber.sendKeys("0");
		WebElement sugar = driver.findElement(By.name("sugar"));
		sugar.clear();
		sugar.sendKeys("16");
		WebElement protein = driver.findElement(By.name("protein"));
		protein.clear();
		protein.sendKeys("2");
		driver.findElement(By.tagName("form")).submit();
		
		assertEquals("iTrust - View My Food Diary", driver.getTitle());
		
		//make sure the new entry shows up with the old entries
		dateVal = driver.findElement(By.name("Date:0"));
		assertEquals("11/12/2014", dateVal.getAttribute(VALUE));
		mealVal = new Select(driver.findElement(By.name("MealType:0")));
		assertEquals("Snack", mealVal.getFirstSelectedOption().getAttribute(VALUE));
		foodVal = driver.findElement(By.name("FoodName:0"));
		assertEquals("Cookie Dough Ice Cream", foodVal.getAttribute(VALUE));
		servVal = driver.findElement(By.name("Servings:0"));
		assertEquals("0.5", servVal.getAttribute(VALUE));
		calVal = driver.findElement(By.name("Calories:0"));
		assertEquals("160.0", calVal.getAttribute(VALUE));
		fatVal = driver.findElement(By.name("Fat:0"));
		assertEquals("8.0", fatVal.getAttribute(VALUE));
		sodiumVal = driver.findElement(By.name("Sodium:0"));
		assertEquals("45.0", sodiumVal.getAttribute(VALUE));
		carbVal = driver.findElement(By.name("Carb:0"));
		assertEquals("21.0", carbVal.getAttribute(VALUE));
		fiberVal = driver.findElement(By.name("Fiber:0"));
		assertEquals("0.0", fiberVal.getAttribute(VALUE));
		sugarVal = driver.findElement(By.name("Sugar:0"));
		assertEquals("16.0", sugarVal.getAttribute(VALUE));
		proteinVal = driver.findElement(By.name("Protein:0"));
		assertEquals("2.0", proteinVal.getAttribute(VALUE));
		
		//get the second day's values
		dateVal2 = driver.findElement(By.name("Date:1"));
		assertEquals("09/30/2012", dateVal2.getAttribute(VALUE));
		mealVal2 = new Select
				(driver.findElement(By.name("MealType:1")));
		assertEquals("Breakfast", mealVal2.getFirstSelectedOption().getAttribute(VALUE));
		foodVal2 = driver.findElement(By.name("FoodName:1"));
		assertEquals("Hot dog", foodVal2.getAttribute(VALUE));
		servVal2 = driver.findElement(By.name("Servings:1"));
		assertEquals("4.0", servVal2.getAttribute(VALUE));
		calVal2 = driver.findElement(By.name("Calories:1"));
		assertEquals("80.0", calVal2.getAttribute(VALUE));
		fatVal2 = driver.findElement(By.name("Fat:1"));
		assertEquals("5.0", fatVal2.getAttribute(VALUE));
		sodiumVal2 = driver.findElement(By.name("Sodium:1"));
		assertEquals("480.0", sodiumVal2.getAttribute(VALUE));
		carbVal2 = driver.findElement(By.name("Carb:1"));
		assertEquals("2.0", carbVal2.getAttribute(VALUE));
		fiberVal2 = driver.findElement(By.name("Fiber:1"));
		assertEquals("0.0", fiberVal2.getAttribute(VALUE));
		sugarVal2 = driver.findElement(By.name("Sugar:1"));
		assertEquals("0.0", sugarVal2.getAttribute(VALUE));
		proteinVal2 = driver.findElement(By.name("Protein:1"));
		assertEquals("5.0", proteinVal2.getAttribute(VALUE));
		
		//get the third day's values
		WebElement dateVal3 = driver.findElement(By.name("Date:2"));
		assertEquals("09/30/2012", dateVal3.getAttribute(VALUE));
		Select mealVal3 = new Select
				(driver.findElement(By.name("MealType:2")));
		assertEquals("Lunch", mealVal3.getFirstSelectedOption().getAttribute(VALUE));
		WebElement foodVal3 = driver.findElement(By.name("FoodName:2"));
		assertEquals("Mango Passionfruit Juice", foodVal3.getAttribute(VALUE));
		WebElement servVal3 = driver.findElement(By.name("Servings:2"));
		assertEquals("1.2", servVal3.getAttribute(VALUE));
		WebElement calVal3 = driver.findElement(By.name("Calories:2"));
		assertEquals("130.0", calVal3.getAttribute(VALUE));
		WebElement fatVal3 = driver.findElement(By.name("Fat:2"));
		assertEquals("0.0", fatVal3.getAttribute(VALUE));
		WebElement sodiumVal3 = driver.findElement(By.name("Sodium:2"));
		assertEquals("25.0", sodiumVal3.getAttribute(VALUE));
		WebElement carbVal3 = driver.findElement(By.name("Carb:2"));
		assertEquals("32.0", carbVal3.getAttribute(VALUE));
		WebElement fiberVal3 = driver.findElement(By.name("Fiber:2"));
		assertEquals("0.0", fiberVal3.getAttribute(VALUE));
		WebElement sugarVal3 = driver.findElement(By.name("Sugar:2"));
		assertEquals("29.0", sugarVal3.getAttribute(VALUE));
		WebElement proteinVal3 = driver.findElement(By.name("Protein:2"));
		assertEquals("1.0", proteinVal3.getAttribute(VALUE));
		
		/* assert the totals are correct
		 * (7 values per unique date)
		 */
		totalTable = 
				driver.findElements(By.tagName("table")).get(2);
		tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("11/12/2014", tableRows.get(0).getText());
		assertEquals("80.0", tableRows.get(1).getText());
		assertEquals("4.0", tableRows.get(2).getText());
		assertEquals("22.5", tableRows.get(3).getText());
		assertEquals("10.5", tableRows.get(4).getText());
		assertEquals("0.0", tableRows.get(5).getText());
		assertEquals("8.0", tableRows.get(6).getText());
		assertEquals("1.0", tableRows.get(7).getText());
		//second date
		assertEquals("09/30/2012", tableRows.get(8).getText());
		assertEquals("476.0", tableRows.get(9).getText());
		assertEquals("20.0", tableRows.get(10).getText());
		assertEquals("1950.0", tableRows.get(11).getText());
		assertEquals("46.4", tableRows.get(12).getText());
		assertEquals("0.0", tableRows.get(13).getText());
		assertEquals("34.8", tableRows.get(14).getText());
		assertEquals("21.2", tableRows.get(15).getText());
	}
	
	/**
	 * Tests that HCPs can view a patients food diary.
	 * @throws Exception
	 */
	public void testHCPViewPatientFoodDiary() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000071", "pw");
		driver.findElement(By.linkText("Patient Food Diaries")).click();
		
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		//search for patient 
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("335");
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='335']")).submit();
		assertEquals("iTrust - View Patient Food Diaries", driver.getTitle());
		
		//different way of viewing it since viewing it through HCP
		WebElement entryTable = 
				driver.findElements(By.tagName("table")).get(1);
		List<WebElement> tableRows = entryTable.findElements(By.tagName("td"));
		assertEquals("04/13/2014", tableRows.get(1).getText());
		assertEquals("Snack", tableRows.get(2).getText());
		assertEquals("Oreos", tableRows.get(3).getText());
		assertEquals("53.0", tableRows.get(4).getText());
		assertEquals("140.0", tableRows.get(5).getText());
		assertEquals("7.0", tableRows.get(6).getText());
		assertEquals("90.0", tableRows.get(7).getText());
		assertEquals("21.0", tableRows.get(8).getText());
		assertEquals("1.0", tableRows.get(9).getText());
		assertEquals("13.0", tableRows.get(10).getText());
		assertEquals("0.0", tableRows.get(11).getText());
		
		assertEquals("05/21/2013", tableRows.get(13).getText());
		assertEquals("Breakfast", tableRows.get(14).getText());
		assertEquals("Cheese and Bean Dip", tableRows.get(15).getText());
		assertEquals("0.75", tableRows.get(16).getText());
		assertEquals("45.0", tableRows.get(17).getText());
		assertEquals("2.0", tableRows.get(18).getText());
		assertEquals("230.0", tableRows.get(19).getText());
		assertEquals("5.0", tableRows.get(20).getText());
		assertEquals("2.0", tableRows.get(21).getText());
		assertEquals("0.0", tableRows.get(22).getText());
		assertEquals("2.0", tableRows.get(23).getText());
	}
	
	/**
	 * Tests that HCPs can sort a patients food diary.
	 * @throws Exception
	 */
	public void testHCPCategorizePatientFoodDiary() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000071", "pw");
		driver.findElement(By.linkText("Patient Food Diaries")).click();
		
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		//search for patient 
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("335");
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='335']")).submit();
		assertEquals("iTrust - View Patient Food Diaries", driver.getTitle());
		
		// Only show May 2013 entries.
		WebElement startDate = driver.findElement(By.name("startDate"));
		startDate.clear();
		startDate.sendKeys("05/01/2013");
		WebElement endDate = driver.findElement(By.name("endDate"));
		endDate.clear();
		endDate.sendKeys("05/31/2013");
		WebElement submit = driver.findElement(By.name("btn_filter"));
		submit.click();
		
		//different way of viewing it since viewing it through HCP
		WebElement entryTable = 
				driver.findElements(By.tagName("table")).get(1);
		List<WebElement> tableRows = entryTable.findElements(By.tagName("td"));
		assertEquals("05/21/2013", tableRows.get(1).getText());
		assertEquals("Breakfast", tableRows.get(2).getText());
		assertEquals("Cheese and Bean Dip", tableRows.get(3).getText());
		assertEquals("0.75", tableRows.get(4).getText());
		assertEquals("45.0", tableRows.get(5).getText());
		assertEquals("2.0", tableRows.get(6).getText());
		assertEquals("230.0", tableRows.get(7).getText());
		assertEquals("5.0", tableRows.get(8).getText());
		assertEquals("2.0", tableRows.get(9).getText());
		assertEquals("0.0", tableRows.get(10).getText());
		assertEquals("2.0", tableRows.get(11).getText());
	}
	
	/**
	 * Tests that HCPs can sort a patients food diary.
	 * @throws Exception
	 */
	public void testHCPCategorizeBadRange() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000071", "pw");
		driver.findElement(By.linkText("Patient Food Diaries")).click();
		
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		//search for patient 
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("335");
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='335']")).submit();
		assertEquals("iTrust - View Patient Food Diaries", driver.getTitle());
		
		// Only show May 2013 entries.
		WebElement startDate = driver.findElement(By.name("startDate"));
		startDate.clear();
		startDate.sendKeys("05/31/2013");
		WebElement endDate = driver.findElement(By.name("endDate"));
		endDate.clear();
		endDate.sendKeys("05/01/2013");
		WebElement submit = driver.findElement(By.name("btn_filter"));
		try {
		submit.click();
		
		//different way of viewing it since viewing it through HCP
		WebElement entryTable = 
				driver.findElements(By.tagName("table")).get(1);
		List<WebElement> tableRows = entryTable.findElements(By.tagName("td"));
		assertEquals("05/21/2013", tableRows.get(1).getText());
		assertEquals("Breakfast", tableRows.get(2).getText());
		assertEquals("Cheese and Bean Dip", tableRows.get(3).getText());
		assertEquals("0.75", tableRows.get(4).getText());
		assertEquals("45.0", tableRows.get(5).getText());
		assertEquals("2.0", tableRows.get(6).getText());
		assertEquals("230.0", tableRows.get(7).getText());
		assertEquals("5.0", tableRows.get(8).getText());
		assertEquals("2.0", tableRows.get(9).getText());
		assertEquals("0.0", tableRows.get(10).getText());
		assertEquals("2.0", tableRows.get(11).getText());
		
		fail("Start date was after End date.");
		
		} catch (Exception e) {
			assertTrue(driver.getPageSource().contains("Start date must be before end date!"));
		}
	}
	
	/**
	 * Tests that HCPs can sort a patients food diary.
	 * @throws Exception
	 */
	public void testHCPCategorizeNoEntriesInRange() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000071", "pw");
		driver.findElement(By.linkText("Patient Food Diaries")).click();
		
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		//search for patient 
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("335");
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='335']")).submit();
		assertEquals("iTrust - View Patient Food Diaries", driver.getTitle());
		
		// Only show May 2013 entries.
		WebElement startDate = driver.findElement(By.name("startDate"));
		startDate.clear();
		startDate.sendKeys("05/01/1950");
		WebElement endDate = driver.findElement(By.name("endDate"));
		endDate.clear();
		endDate.sendKeys("05/31/1950");
		WebElement submit = driver.findElement(By.name("btn_filter"));
		try {
		submit.click();
		
		//different way of viewing it since viewing it through HCP
		WebElement entryTable = 
				driver.findElements(By.tagName("table")).get(1);
		List<WebElement> tableRows = entryTable.findElements(By.tagName("td"));
		assertEquals("05/21/2013", tableRows.get(1).getText());
		assertEquals("Breakfast", tableRows.get(2).getText());
		assertEquals("Cheese and Bean Dip", tableRows.get(3).getText());
		assertEquals("0.75", tableRows.get(4).getText());
		assertEquals("45.0", tableRows.get(5).getText());
		assertEquals("2.0", tableRows.get(6).getText());
		assertEquals("230.0", tableRows.get(7).getText());
		assertEquals("5.0", tableRows.get(8).getText());
		assertEquals("2.0", tableRows.get(9).getText());
		assertEquals("0.0", tableRows.get(10).getText());
		assertEquals("2.0", tableRows.get(11).getText());
		
		fail("Start date was after End date.");
		
		} catch (Exception e) {
			assertTrue(driver.getPageSource().contains("There are no entries in the specified range."));
		}
	}
	
	/**
	 * Tests to ensure that a patient has to enter something for the food name.
	 * @throws Exception
	 */
	public void testAddEmptyFoodType() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		driver = login("333", "pw");
		
		driver.findElement(By.linkText("My Food Diary")).click();
		assertEquals("iTrust - View My Food Diary", driver.getTitle());
		
		driver.findElement(By.linkText("Add an entry to your Food Diary."))
			.click();
		
		assertEquals("Add a Food Entry", driver.getTitle());
		
		//now leave out a food name
		WebElement dateEaten = driver.findElement(By.name("dateEatenStr"));
		dateEaten.clear();
		dateEaten.sendKeys("02/04/2015");
		Select meal = new Select(driver.findElement(By.name("mealType")));
		meal.selectByValue("Lunch");
		WebElement food = driver.findElement(By.name("food"));
		food.clear();
		food.sendKeys("");
		WebElement servings = driver.findElement(By.name("servings"));
		servings.clear();
		servings.sendKeys("1");
		WebElement cals = driver.findElement(By.name("calories"));
		cals.clear();
		cals.sendKeys("175");
		WebElement fat = driver.findElement(By.name("fat"));
		fat.clear();
		fat.sendKeys("4");
		WebElement sodium = driver.findElement(By.name("sodium"));
		sodium.clear();
		sodium.sendKeys("430");
		WebElement carbs = driver.findElement(By.name("carb"));
		carbs.clear();
		carbs.sendKeys("8");
		WebElement fiber = driver.findElement(By.name("fiber"));
		fiber.clear();
		fiber.sendKeys("2");
		WebElement sugar = driver.findElement(By.name("sugar"));
		sugar.clear();
		sugar.sendKeys("6");
		WebElement protein = driver.findElement(By.name("protein"));
		protein.clear();
		protein.sendKeys("2");
		driver.findElement(By.tagName("form")).submit();
		
		//should stay on same page and have error message
		assertEquals("Add a Food Entry", driver.getTitle());
		assertTrue(driver.getPageSource().contains("The following field are "
				+ "not properly filled in: "
				+ "[Must enter the Name of the Food]"));
	}
	
	/**
	 * Tests to ensures users have to enter dates in the correct format 
	 * (mm/dd/yy).
	 * @throws Exception
	 */
	public void testInvalidDateEntry() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		driver = login("333", "pw");
		
		driver.findElement(By.linkText("My Food Diary")).click();
		assertEquals("iTrust - View My Food Diary", driver.getTitle());
		
		driver.findElement(By.linkText("Add an entry to your Food Diary."))
			.click();
		
		assertEquals("Add a Food Entry", driver.getTitle());
		
		//now enter improper date
		WebElement dateEaten = driver.findElement(By.name("dateEatenStr"));
		dateEaten.clear();
		dateEaten.sendKeys("2015/04/03");
		Select meal = new Select(driver.findElement(By.name("mealType")));
		meal.selectByValue("Dinner");
		WebElement food = driver.findElement(By.name("food"));
		food.clear();
		food.sendKeys("Soup");
		WebElement servings = driver.findElement(By.name("servings"));
		servings.clear();
		servings.sendKeys("1");
		WebElement cals = driver.findElement(By.name("calories"));
		cals.clear();
		cals.sendKeys("175");
		WebElement fat = driver.findElement(By.name("fat"));
		fat.clear();
		fat.sendKeys("4");
		WebElement sodium = driver.findElement(By.name("sodium"));
		sodium.clear();
		sodium.sendKeys("430");
		WebElement carbs = driver.findElement(By.name("carb"));
		carbs.clear();
		carbs.sendKeys("8");
		WebElement fiber = driver.findElement(By.name("fiber"));
		fiber.clear();
		fiber.sendKeys("2");
		WebElement sugar = driver.findElement(By.name("sugar"));
		sugar.clear();
		sugar.sendKeys("6");
		WebElement protein = driver.findElement(By.name("protein"));
		protein.clear();
		protein.sendKeys("2");
		driver.findElement(By.tagName("form")).submit();
		
		//should stay on same page and have error message
		assertEquals("Add a Food Entry", driver.getTitle());
		assertTrue(driver.getPageSource().contains("The following field are "
				+ "not properly filled in: "
				+ "[Enter dates in MM/dd/yyyy]"));
	}
	
	/**
	 * Tests to ensure that HCPs can view a Patient's Food Diary when it is 
	 * empty.
	 * @throws Exception
	 */
	public void testViewEmptyFoodDiaryAsHCP() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000071", "pw");
		driver.findElement(By.linkText("Patient Food Diaries")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		
		//search for patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("333");
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='333']")).submit();
		assertEquals("iTrust - View Patient Food Diaries", driver.getTitle());
		assertTrue(driver.getPageSource().contains("The selected patient's "
				+ "food diary is empty. If you were expecting entries "
				+ "please try again later!"));
	}
	
	/**
	 * Tests that you are not allowed to set the number of servings to 0.
	 * @throws Exception
	 */
	public void testSetNumberOfServingsToZero() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		driver = login("333", "pw");
		
		driver.findElement(By.linkText("My Food Diary")).click();
		assertEquals("iTrust - View My Food Diary", driver.getTitle());
		
		driver.findElement(By.linkText("Add an entry to your Food Diary."))
			.click();
		
		assertEquals("Add a Food Entry", driver.getTitle());
		
		//now enter 0 for servings
		WebElement dateEaten = driver.findElement(By.name("dateEatenStr"));
		dateEaten.clear();
		dateEaten.sendKeys("03/04/2014");
		Select meal = new Select(driver.findElement(By.name("mealType")));
		meal.selectByValue("Dinner");
		WebElement food = driver.findElement(By.name("food"));
		food.clear();
		food.sendKeys("Soup");
		WebElement servings = driver.findElement(By.name("servings"));
		servings.clear();
		servings.sendKeys("0");
		WebElement cals = driver.findElement(By.name("calories"));
		cals.clear();
		cals.sendKeys("175");
		WebElement fat = driver.findElement(By.name("fat"));
		fat.clear();
		fat.sendKeys("4");
		WebElement sodium = driver.findElement(By.name("sodium"));
		sodium.clear();
		sodium.sendKeys("430");
		WebElement carbs = driver.findElement(By.name("carb"));
		carbs.clear();
		carbs.sendKeys("8");
		WebElement fiber = driver.findElement(By.name("fiber"));
		fiber.clear();
		fiber.sendKeys("2");
		WebElement sugar = driver.findElement(By.name("sugar"));
		sugar.clear();
		sugar.sendKeys("6");
		WebElement protein = driver.findElement(By.name("protein"));
		protein.clear();
		protein.sendKeys("2");
		driver.findElement(By.tagName("form")).submit();
		
		//should stay on same page and have error message
		assertEquals("Add a Food Entry", driver.getTitle());
		assertTrue(driver.getPageSource().contains("The following field are "
				+ "not properly filled in: "
				+ "[Number of Servings must be greater than 0]"));
	}
	
	/**
	 * Tests to ensure that you cannot enter a negative number of calories.
	 * @throws Exception
	 */
	public void testNegativeEntry() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		driver = login("333", "pw");
		
		driver.findElement(By.linkText("My Food Diary")).click();
		assertEquals("iTrust - View My Food Diary", driver.getTitle());
		
		driver.findElement(By.linkText("Add an entry to your Food Diary."))
			.click();
		
		assertEquals("Add a Food Entry", driver.getTitle());
		
		//now enter negative number for calories
		WebElement dateEaten = driver.findElement(By.name("dateEatenStr"));
		dateEaten.clear();
		dateEaten.sendKeys("03/04/2014");
		Select meal = new Select(driver.findElement(By.name("mealType")));
		meal.selectByValue("Dinner");
		WebElement food = driver.findElement(By.name("food"));
		food.clear();
		food.sendKeys("Soup");
		WebElement servings = driver.findElement(By.name("servings"));
		servings.clear();
		servings.sendKeys("1");
		WebElement cals = driver.findElement(By.name("calories"));
		cals.clear();
		cals.sendKeys("-175");
		WebElement fat = driver.findElement(By.name("fat"));
		fat.clear();
		fat.sendKeys("4");
		WebElement sodium = driver.findElement(By.name("sodium"));
		sodium.clear();
		sodium.sendKeys("430");
		WebElement carbs = driver.findElement(By.name("carb"));
		carbs.clear();
		carbs.sendKeys("8");
		WebElement fiber = driver.findElement(By.name("fiber"));
		fiber.clear();
		fiber.sendKeys("2");
		WebElement sugar = driver.findElement(By.name("sugar"));
		sugar.clear();
		sugar.sendKeys("6");
		WebElement protein = driver.findElement(By.name("protein"));
		protein.clear();
		protein.sendKeys("2");
		driver.findElement(By.tagName("form")).submit();
		
		//should stay on same page and have error message
		assertEquals("Add a Food Entry", driver.getTitle());
		assertTrue(driver.getPageSource().contains("The following field are "
				+ "not properly filled in: "
				+ "[Calories per Serving cannot be negative]"));
	}	
}
