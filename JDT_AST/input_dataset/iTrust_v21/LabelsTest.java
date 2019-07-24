package edu.ncsu.csc.itrust.selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;


public class LabelsTest extends iTrustSeleniumTest {

	/**
	 * Sets up the standard testing data.
	 */
	public void setUp() throws Exception {
		super.setUp();
		gen.standardData();
	}

	/**
	 * Tests a patient can create a new label when there 
	 * are no other labels
	 */
	public void testCreateLabel() throws Exception{
		WebDriver driver = new HtmlUnitDriver();
		driver = login("1", "pw"); //login as Random Person
		
		//go to label management page
		driver.findElement(By.linkText("My Labels")).click();
		assertEquals("iTrust - My Labels", driver.getTitle());
		
		//add a label name, use default label color - white/#FFFFFF
		WebElement labelName = driver.findElement(By.name("createLabelName"));
		labelName.clear();
		labelName.sendKeys("Test");
		labelName.submit();
		
		//table contains text boxes for editting purposes
		//so we need to get the value inside the textbox
		labelName = driver.findElement(By.name("Label:0"));
		assertEquals("Test", labelName.getAttribute(VALUE));
	}
	
	/**
	 * Tests a patient can edit a label 
	 */
	public void testEditLabel() throws Exception {
		//need to enable javascript
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("1", "pw"); //login as Random Person
		driver.setJavascriptEnabled(true);
		driver.findElement(By.linkText("My Labels")).click();
		assertEquals("iTrust - My Labels", driver.getTitle());
		driver.setJavascriptEnabled(true);

		//add a label name, use default label color - white/#FFFFFF
		WebElement labelName = driver.findElement(By.name("createLabelName")); 
		labelName.clear();
		labelName.sendKeys("Test");
		driver.findElement(By.cssSelector("input[value='Create Label']")).click();
		 
		//edit the label name field and submit
		WebElement editButton = driver.findElement(By.cssSelector("input[value='Edit Entry']"));
		editButton.click();
		labelName = driver.findElement(By.name("Label:0"));	
		labelName.clear();
		labelName.sendKeys("Updated");
		WebElement submitButton = driver.findElement(By.cssSelector("input[value='Submit']"));
		submitButton.click();
		
		//table contains text boxes for editting purposes
		//so we need to get the value inside the textbox
		labelName = driver.findElement(By.name("Label:0"));
		assertEquals("Updated", labelName.getAttribute(VALUE));

	}
	
	/**
	 * Tests a patient can delete a label 
	 */
	public void testDeleteLabel() throws Exception {
		//need to enable javascript
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("1", "pw"); //login as Random Person
		driver.setJavascriptEnabled(true);
		driver.findElement(By.linkText("My Labels")).click();
		assertEquals("iTrust - My Labels", driver.getTitle());
		driver.setJavascriptEnabled(true);
		
		//add a "Test" label
		WebElement labelName = driver.findElement(By.name("createLabelName")); 
		labelName.clear();
		labelName.sendKeys("Test");
		driver.findElement(By.cssSelector("input[value='Create Label']")).click();
		//add "Test 2" label
		labelName = driver.findElement(By.name("createLabelName")); 
		labelName.clear();
		labelName.sendKeys("Test 2");
		driver.findElement(By.cssSelector("input[value='Create Label']")).click();
		driver.findElement(By.cssSelector("input[value='Delete Label']")).click();
		labelName = driver.findElement(By.name("Label:0"));
		assertEquals("Test 2", labelName.getAttribute(VALUE));
	}
	
	/**
	 * Tests a patient can apply a label to their food diary
	 */
	public void testApplyLabeltoFood() throws Exception {
		//need to enable javascript
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("1", "pw"); //login as Random Person
		driver.setJavascriptEnabled(true);
		
		//view my food diary so we can add a new entry
		driver.findElement(By.linkText("My Food Diary")).click();
		assertEquals("iTrust - View My Food Diary", driver.getTitle());
		driver.findElement(By.linkText("Add an entry to your Food Diary.")).click();		
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
		driver.findElement(By.cssSelector("input[value='Add entry to Food Diary']")).click();
		assertEquals("iTrust - View My Food Diary", driver.getTitle()); 
		
		//navigate to My Labels page
		driver.findElement(By.linkText("My Labels")).click();
		assertEquals("iTrust - My Labels", driver.getTitle());
		driver.setJavascriptEnabled(true);
		
		//add a "Test" label
		WebElement labelName = driver.findElement(By.name("createLabelName")); 
		labelName.clear();
		labelName.sendKeys("Test");
		driver.findElement(By.cssSelector("input[value='Create Label']")).click();
		
		//Select food diary, label, start and end dates and apply the label
		Select diary = new Select(driver.findElement(By.name("diaries")));
		Select label = new Select(driver.findElement(By.name("labels")));
		diary.selectByValue("food");
		label.selectByIndex(0);
		WebElement startDate = driver.findElement(By.name("startDate"));
		startDate.clear();
		startDate.sendKeys("02/04/2015");
		WebElement endDate = driver.findElement(By.name("endDate"));
		endDate.clear();
		endDate.sendKeys("02/04/2015");		
		driver.findElement(By.cssSelector("input[value='Apply Label']")).click();
		
		//go back to view my food diary
		driver.findElement(By.linkText("My Food Diary")).click();
		assertEquals("iTrust - View My Food Diary", driver.getTitle());
		
		//make sure the label was applied
		Select appliedLabel = new Select(driver.findElement(By.name("LabelEntry:0")));
		WebElement appliedLabelName = appliedLabel.getFirstSelectedOption();
		assertEquals("Test", appliedLabelName.getText());
	}

	/**
	 * Tests a patient can apply a label to their exercise diary
	 */
	public void testApplyLabeltoExercise() throws Exception {
		//need to enable javascript
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("1", "pw"); //login as Random Person
		driver.setJavascriptEnabled(true);
		
		//navigate to My Labels page
		driver.findElement(By.linkText("My Labels")).click();
		assertEquals("iTrust - My Labels", driver.getTitle());
		driver.setJavascriptEnabled(true);
		
		//add a "Test" label
		WebElement labelName = driver.findElement(By.name("createLabelName")); 
		labelName.clear();
		labelName.sendKeys("Test");
		driver.findElement(By.cssSelector("input[value='Create Label']")).click();
		
		//Select exercise diary, label, start and end dates and apply the label
		Select diary = new Select(driver.findElement(By.name("diaries")));
		Select label = new Select(driver.findElement(By.name("labels")));
		diary.selectByValue("exercise");
		label.selectByIndex(0);
		WebElement startDate = driver.findElement(By.name("startDate"));
		startDate.clear();
		startDate.sendKeys("12/14/2012");
		WebElement endDate = driver.findElement(By.name("endDate"));
		endDate.clear();
		endDate.sendKeys("12/14/2012");		
		driver.findElement(By.cssSelector("input[value='Apply Label']")).click();
		
		//go to view my exercise diary
		driver.findElement(By.linkText("My Exercise Diary")).click();
		assertEquals("iTrust - View My Exercise Diary", driver.getTitle());
		
		//make sure the label was applied
		Select appliedLabel = new Select(driver.findElement(By.name("LabelEntry:0")));
		WebElement appliedLabelName = appliedLabel.getFirstSelectedOption();
		assertEquals("Test", appliedLabelName.getText());
	}
	
	/**
	 * Tests a patient can apply a label to their sleep diary
	 */
	public void testApplyLabeltoSleep() throws Exception {
		//need to enable javascript
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver = (HtmlUnitDriver)login("1", "pw"); //login as Random Person
		driver.setJavascriptEnabled(true);
		
		//navigate to My Labels page
		driver.findElement(By.linkText("My Labels")).click();
		assertEquals("iTrust - My Labels", driver.getTitle());
		driver.setJavascriptEnabled(true);
		
		//add a "Test" label
		WebElement labelName = driver.findElement(By.name("createLabelName")); 
		labelName.clear();
		labelName.sendKeys("Test");
		driver.findElement(By.cssSelector("input[value='Create Label']")).click();
		
		//Select sleep diary, label, start and end dates and apply the label
		Select diary = new Select(driver.findElement(By.name("diaries")));
		Select label = new Select(driver.findElement(By.name("labels")));
		diary.selectByValue("sleep");
		label.selectByIndex(0);
		WebElement startDate = driver.findElement(By.name("startDate"));
		startDate.clear();
		startDate.sendKeys("12/14/2012");
		WebElement endDate = driver.findElement(By.name("endDate"));
		endDate.clear();
		endDate.sendKeys("12/14/2012");		
		driver.findElement(By.cssSelector("input[value='Apply Label']")).click();
		
		//go to view my sleep diary
		driver.findElement(By.linkText("My Sleep Diary")).click();
		assertEquals("iTrust - View My Sleep Diary", driver.getTitle());
		
		//make sure the label was applied
		Select appliedLabel = new Select(driver.findElement(By.name("LabelEntry:0")));
		WebElement appliedLabelName = appliedLabel.getFirstSelectedOption();
		assertEquals("Test", appliedLabelName.getText());
	}
}