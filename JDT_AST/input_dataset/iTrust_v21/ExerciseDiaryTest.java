package edu.ncsu.csc.itrust.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.action.AddExerciseEntryAction;
import edu.ncsu.csc.itrust.action.DeleteExerciseEntryAction;
import edu.ncsu.csc.itrust.action.ViewExerciseEntryAction;
import edu.ncsu.csc.itrust.beans.ExerciseEntryBean;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Tests the functionality of the new Exercise Diary portion of iTrust. Tests
 * that you can add new entries, you can view entries, HCPs with the role of
 * trainer can view entries, and error checks that the user enters the
 * appropriate data in the appropriate format.
 */
public class ExerciseDiaryTest extends iTrustSeleniumTest {

	/**
	 * Sets up the standard testing data.
	 */
	public void setUp() throws Exception {
		super.setUp();
		gen.standardData();
	}

	/**
	 * Tests that a patient can add an entry to an empty Exercise Diary.
	 * 
	 * @throws Exception
	 */
	public void testAddExerciseEntryToEmptyExerciseDiary() throws Exception {
		{ // Empty the diary.
			ViewExerciseEntryAction viewAction = new ViewExerciseEntryAction(
					TestDAOFactory.getTestInstance(), 1);
			DeleteExerciseEntryAction delAction = new DeleteExerciseEntryAction(
					TestDAOFactory.getTestInstance(), 1);
			List<ExerciseEntryBean> diary = viewAction.getDiary(1);
			for (ExerciseEntryBean entry : diary) {
				delAction.deleteEntry(entry.getEntryID());
			}
		}

		HtmlUnitDriver driver = new HtmlUnitDriver(true);
		driver = (HtmlUnitDriver)login("1", "pw"); // login as Random Person
		driver.setJavascriptEnabled(true);
		
		// view my exercise diary so we can add a new entry
		driver.findElement(By.linkText("My Exercise Diary")).click();
		assertEquals("iTrust - View My Exercise Diary", driver.getTitle());
		driver.findElement(By.linkText("Add an entry to your Exercise Diary."))
				.click();

		assertEquals("Add an Exercise Entry", driver.getTitle());

		// now fill in all of the info and submit
		WebElement strDate = driver.findElement(By.name("strDate"));
		strDate.clear();
		strDate.sendKeys("12/12/2012");

		Select exercise = new Select(driver.findElement(By.name("strType")));
		exercise.selectByValue("Weight Training");
		WebElement strName = driver.findElement(By.name("strName"));
		strName.clear();
		strName.sendKeys("Bench Press");
		WebElement strHours = driver.findElement(By.name("strHours"));
		strHours.clear();
		strHours.sendKeys("0.5");
		WebElement strCalories = driver.findElement(By.name("strCalories"));
		strCalories.clear();
		strCalories.sendKeys("50");
		WebElement strSets = driver.findElement(By.name("strSets"));
		strSets.clear();
		strSets.sendKeys("3");
		WebElement strReps = driver.findElement(By.name("strReps"));
		strReps.clear();
		strReps.sendKeys("10");
		
		driver.setJavascriptEnabled(false);

		driver.findElement(By.tagName("form")).submit();
		assertEquals("iTrust - View My Exercise Diary", driver.getTitle());

		// Ensure the entry was added.
		WebElement valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/12/2012", valDate.getAttribute(VALUE));
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
	}

	/**
	 * Tests that a patient can add an entry to a non empty exercise diary.
	 * 
	 * @throws Exception
	 */
	public void testAddExerciseEntryToNonEmptyExerciseDiary() throws Exception {
		WebDriver driver = new HtmlUnitDriver(true);
		driver = login("1", "pw"); // login as Random Person
		driver.findElement(By.linkText("My Exercise Diary")).click();
		assertEquals("iTrust - View My Exercise Diary", driver.getTitle());

		// Get the first values.
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

		// Get the second values.
		valDate = driver.findElement(By.name("Date:1"));
		assertEquals("12/12/2012", valDate.getAttribute(VALUE));
		valType = driver.findElement(By.name("ExerciseType:1"));
		assertEquals("Cardio", valType.getAttribute(VALUE));
		valName = driver.findElement(By.name("Name:1"));
		assertEquals("Running", valName.getAttribute(VALUE));
		valHours = driver.findElement(By.name("Hours:1"));
		assertEquals("1.0", valHours.getAttribute(VALUE));
		valCalories = driver.findElement(By.name("Calories:1"));
		assertEquals("100", valCalories.getAttribute(VALUE));

		// Assert that the totals are correct.
		WebElement totalTable = driver.findElements(By.tagName("table")).get(2);
		List<WebElement> tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("0.5", tableRows.get(1).getText());
		assertEquals("50", tableRows.get(2).getText());
		assertEquals("12/12/2012", tableRows.get(3).getText());
		assertEquals("1.0", tableRows.get(4).getText());
		assertEquals("100", tableRows.get(5).getText());

		// Add a new entry.
		driver.findElement(By.linkText("Add an entry to your Exercise Diary."))
				.click();
		assertEquals("Add an Exercise Entry", driver.getTitle());

		// Enter new entry.
		WebElement strDate = driver.findElement(By.name("strDate"));
		strDate.clear();
		strDate.sendKeys("12/13/2012");
		Select exercise = new Select(driver.findElement(By.name("strType")));
		exercise.selectByValue("Cardio");
		WebElement strName = driver.findElement(By.name("strName"));
		strName.clear();
		strName.sendKeys("Biking");
		WebElement strHours = driver.findElement(By.name("strHours"));
		strHours.clear();
		strHours.sendKeys("0.5");
		WebElement strCalories = driver.findElement(By.name("strCalories"));
		strCalories.clear();
		strCalories.sendKeys("100");
		driver.findElement(By.tagName("form")).submit();

		assertEquals("iTrust - View My Exercise Diary", driver.getTitle());

		// Make sure the new entry shows up with the old entries.
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
		
		
		valDate = driver.findElement(By.name("Date:1"));
		assertEquals("12/13/2012", valDate.getAttribute(VALUE));
		valType = driver.findElement(By.name("ExerciseType:1"));
		assertEquals("Cardio", valType.getAttribute(VALUE));
		valName = driver.findElement(By.name("Name:1"));
		assertEquals("Biking", valName.getAttribute(VALUE));
		valHours = driver.findElement(By.name("Hours:1"));
		assertEquals("0.5", valHours.getAttribute(VALUE));
		valCalories = driver.findElement(By.name("Calories:1"));
		assertEquals("100", valCalories.getAttribute(VALUE));

		valDate = driver.findElement(By.name("Date:2"));
		assertEquals("12/12/2012", valDate.getAttribute(VALUE));
		valType = driver.findElement(By.name("ExerciseType:2"));
		assertEquals("Cardio", valType.getAttribute(VALUE));
		valName = driver.findElement(By.name("Name:2"));
		assertEquals("Running", valName.getAttribute(VALUE));
		valHours = driver.findElement(By.name("Hours:2"));
		assertEquals("1.0", valHours.getAttribute(VALUE));
		valCalories = driver.findElement(By.name("Calories:2"));
		assertEquals("100", valCalories.getAttribute(VALUE));

		// Assert the totals are correct.
		totalTable = driver.findElements(By.tagName("table")).get(2);
		tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("0.5", tableRows.get(1).getText());
		assertEquals("50", tableRows.get(2).getText());
		
		assertEquals("12/13/2012", tableRows.get(3).getText());
		assertEquals("0.5", tableRows.get(4).getText());
		assertEquals("100", tableRows.get(5).getText());

		assertEquals("12/12/2012", tableRows.get(6).getText());
		assertEquals("1.0", tableRows.get(7).getText());
		assertEquals("100", tableRows.get(8).getText());
	}

	/**
	 * Tests that HCPs can view a patients exercise diary.
	 * 
	 * @throws Exception
	 */
	public void testHCPViewPatientExerciseDiary() throws Exception {
		WebDriver driver = new HtmlUnitDriver(true);
		driver = login("9000000081", "pw");
		driver.findElement(By.linkText("Patient Exercise Diaries")).click();

		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		// search for patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		// the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='1']")).submit();
		assertEquals("iTrust - View Patient Exercise Diaries",
				driver.getTitle());

		// different way of viewing it since viewing it through HCP
		WebElement entryTable = driver.findElements(By.tagName("table")).get(1);
		List<WebElement> tableRows = entryTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(1).getText());
		assertEquals("Weight Training", tableRows.get(2).getText());
		assertEquals("Bench Press", tableRows.get(3).getText());
		assertEquals("0.5", tableRows.get(4).getText());
		assertEquals("50", tableRows.get(5).getText());
		assertEquals("3", tableRows.get(6).getText());
		assertEquals("10", tableRows.get(7).getText());

		assertEquals("12/12/2012", tableRows.get(9).getText());
		assertEquals("Cardio", tableRows.get(10).getText());
		assertEquals("Running", tableRows.get(11).getText());
		assertEquals("1.0", tableRows.get(12).getText());
		assertEquals("100", tableRows.get(13).getText());
	}

	/**
	 * Tests that HCPs can sort a patients exercise diary.
	 * 
	 * @throws Exception
	 */
	public void testHCPCategorizePatientExerciseDiary() throws Exception {
		{ // Add a different day's entry.
			AddExerciseEntryAction addAction = new AddExerciseEntryAction(
					TestDAOFactory.getTestInstance(), 1);
			ExerciseEntryBean entry = new ExerciseEntryBean();
			entry.setEntryID(83);
			entry.setStrDate("05/15/2013");
			entry.setExerciseType("Cardio");
			entry.setStrName("Biking");
			entry.setHoursWorked(0.5);
			entry.setCaloriesBurned(100);
			entry.setPatientID(1);
			addAction.addEntry(entry);
		}

		WebDriver driver = new HtmlUnitDriver(true);
		driver = login("9000000081", "pw");
		driver.findElement(By.linkText("Patient Exercise Diaries")).click();

		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		// search for patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		// the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='1']")).submit();
		assertEquals("iTrust - View Patient Exercise Diaries",
				driver.getTitle());

		// Only show May 2013 entries.
		WebElement startDate = driver.findElement(By.name("startDate"));
		startDate.clear();
		startDate.sendKeys("05/01/2013");
		WebElement endDate = driver.findElement(By.name("endDate"));
		endDate.clear();
		endDate.sendKeys("05/31/2013");
		WebElement submit = driver.findElement(By.name("btn_filter"));
		submit.click();

		// different way of viewing it since viewing it through HCP
		WebElement entryTable = driver.findElements(By.tagName("table")).get(1);
		List<WebElement> tableRows = entryTable.findElements(By.tagName("td"));
		assertEquals("05/15/2013", tableRows.get(1).getText());
		assertEquals("Cardio", tableRows.get(2).getText());
		assertEquals("Biking", tableRows.get(3).getText());
		assertEquals("0.5", tableRows.get(4).getText());
		assertEquals("100", tableRows.get(5).getText());
	}

	/**
	 * Tests that HCPs can sort a patients exercise diary.
	 * 
	 * @throws Exception
	 */
	public void testHCPCategorizeBadRange() throws Exception {
		{ // Add a different day's entry.
			AddExerciseEntryAction addAction = new AddExerciseEntryAction(
					TestDAOFactory.getTestInstance(), 1);
			ExerciseEntryBean entry = new ExerciseEntryBean();
			entry.setEntryID(83);
			entry.setStrDate("05/15/2013");
			entry.setExerciseType("Cardio");
			entry.setStrName("Biking");
			entry.setHoursWorked(0.5);
			entry.setCaloriesBurned(100);
			entry.setPatientID(1);
			addAction.addEntry(entry);
		}

		WebDriver driver = new HtmlUnitDriver(true);
		driver = login("9000000081", "pw");
		driver.findElement(By.linkText("Patient Exercise Diaries")).click();

		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		// search for patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		// the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='1']")).submit();
		assertEquals("iTrust - View Patient Exercise Diaries",
				driver.getTitle());

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

			// different way of viewing it since viewing it through HCP
			WebElement entryTable = driver.findElements(By.tagName("table"))
					.get(1);
			List<WebElement> tableRows = entryTable.findElements(By
					.tagName("td"));
			assertEquals("05/15/2012", tableRows.get(1).getText());
			assertEquals("Cardio", tableRows.get(2).getText());
			assertEquals("Biking", tableRows.get(3).getText());
			assertEquals("0.5", tableRows.get(4).getText());
			assertEquals("100", tableRows.get(5).getText());

			fail("Start date was after End date.");

		} catch (Exception e) {
			assertTrue(driver.getPageSource().contains(
					"Start date must be before end date!"));
		}
	}

	/**
	 * Tests that HCPs can sort a patients exercise diary.
	 * 
	 * @throws Exception
	 */
	public void testHCPCategorizeNoEntriesInRange() throws Exception {
		{ // Add a different day's entry.
			AddExerciseEntryAction addAction = new AddExerciseEntryAction(
					TestDAOFactory.getTestInstance(), 1);
			ExerciseEntryBean entry = new ExerciseEntryBean();
			entry.setEntryID(83);
			entry.setStrDate("05/15/2013");
			entry.setExerciseType("Cardio");
			entry.setStrName("Biking");
			entry.setHoursWorked(0.5);
			entry.setCaloriesBurned(100);
			entry.setPatientID(1);
			addAction.addEntry(entry);
		}

		WebDriver driver = new HtmlUnitDriver(true);
		driver = login("9000000081", "pw");
		driver.findElement(By.linkText("Patient Exercise Diaries")).click();

		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		// search for patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		// the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='1']")).submit();
		assertEquals("iTrust - View Patient Exercise Diaries",
				driver.getTitle());

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

			// different way of viewing it since viewing it through HCP
			WebElement entryTable = driver.findElements(By.tagName("table"))
					.get(1);
			List<WebElement> tableRows = entryTable.findElements(By
					.tagName("td"));
			assertEquals("05/15/2012", tableRows.get(1).getText());
			assertEquals("Cardio", tableRows.get(2).getText());
			assertEquals("Biking", tableRows.get(3).getText());
			assertEquals("0.5", tableRows.get(4).getText());
			assertEquals("100", tableRows.get(5).getText());

			fail("Start date was after End date.");

		} catch (Exception e) {
			assertTrue(driver.getPageSource().contains(
					"There are no entries in the specified range."));
		}
	}

	/**
	 * Tests to ensure that a patient has to enter something for the exercise
	 * name.
	 * 
	 * @throws Exception
	 */
	public void testAddEmptyExerciseType() throws Exception {
		WebDriver driver = new HtmlUnitDriver(true);
		driver = login("1", "pw");

		driver.findElement(By.linkText("My Exercise Diary")).click();
		assertEquals("iTrust - View My Exercise Diary", driver.getTitle());

		driver.findElement(By.linkText("Add an entry to your Exercise Diary."))
				.click();

		assertEquals("Add an Exercise Entry", driver.getTitle());

		// Leave out the exercise name
		WebElement strDate = driver.findElement(By.name("strDate"));
		strDate.clear();
		strDate.sendKeys("12/13/2012");
		Select exercise = new Select(driver.findElement(By.name("strType")));
		exercise.selectByValue("Cardio");
		WebElement strName = driver.findElement(By.name("strName"));
		strName.clear();
		strName.sendKeys("");
		WebElement strHours = driver.findElement(By.name("strHours"));
		strHours.clear();
		strHours.sendKeys("0.5");
		WebElement strCalories = driver.findElement(By.name("strCalories"));
		strCalories.clear();
		strCalories.sendKeys("100");
		driver.findElement(By.tagName("form")).submit();

		// should stay on same page and have error message
		assertEquals("Add an Exercise Entry", driver.getTitle());
		assertTrue(driver.getPageSource().contains(
				"The following field are " + "not properly filled in: "
						+ "[Must enter the Name of the Exercise]"));
	}

	/**
	 * Tests to ensures users have to enter dates in the correct format
	 * (mm/dd/yy).
	 * 
	 * @throws Exception
	 */
	public void testInvalidDateEntry() throws Exception {
		WebDriver driver = new HtmlUnitDriver(true);
		driver = login("1", "pw");

		driver.findElement(By.linkText("My Exercise Diary")).click();
		assertEquals("iTrust - View My Exercise Diary", driver.getTitle());

		driver.findElement(By.linkText("Add an entry to your Exercise Diary."))
				.click();

		assertEquals("Add an Exercise Entry", driver.getTitle());

		// Enter an improper date
		WebElement strDate = driver.findElement(By.name("strDate"));
		strDate.clear();
		strDate.sendKeys("2012/13/12");
		Select exercise = new Select(driver.findElement(By.name("strType")));
		exercise.selectByValue("Cardio");
		WebElement strName = driver.findElement(By.name("strName"));
		strName.clear();
		strName.sendKeys("Biking");
		WebElement strHours = driver.findElement(By.name("strHours"));
		strHours.clear();
		strHours.sendKeys("0.5");
		WebElement strCalories = driver.findElement(By.name("strCalories"));
		strCalories.clear();
		strCalories.sendKeys("100");
		driver.findElement(By.tagName("form")).submit();

		// should stay on same page and have error message
		assertEquals("Add an Exercise Entry", driver.getTitle());
		assertTrue(driver.getPageSource().contains("This form has not been validated correctly. The following field are not properly filled in: [Enter dates in MM/dd/yyyy]"));
	}

	/**
	 * Tests to ensure that HCPs can view a Patient's Exercise Diary when it is
	 * empty.
	 * 
	 * @throws Exception
	 */
	public void testViewEmptyExerciseDiaryAsHCP() throws Exception {
		WebDriver driver = new HtmlUnitDriver(true);
		driver = login("9000000081", "pw");
		driver.findElement(By.linkText("Patient Exercise Diaries")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());

		// search for patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		// the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='2']")).submit();
		assertEquals("iTrust - View Patient Exercise Diaries",
				driver.getTitle());
		assertTrue(driver.getPageSource().contains(
				"The selected patient's exercise diary is empty. If you were "
						+ "expecting entries please try again later!"));
	}

	/**
	 * Tests that you are not allowed to set the number of hours to 0.
	 * 
	 * @throws Exception
	 */
	public void testSetNumberOfHoursToZero() throws Exception {
		WebDriver driver = new HtmlUnitDriver(true);
		driver = login("1", "pw");

		driver.findElement(By.linkText("My Exercise Diary")).click();
		assertEquals("iTrust - View My Exercise Diary", driver.getTitle());

		driver.findElement(By.linkText("Add an entry to your Exercise Diary."))
				.click();

		assertEquals("Add an Exercise Entry", driver.getTitle());

		// now enter 0 for hours
		WebElement strDate = driver.findElement(By.name("strDate"));
		strDate.clear();
		strDate.sendKeys("12/13/2012");
		Select exercise = new Select(driver.findElement(By.name("strType")));
		exercise.selectByValue("Cardio");
		WebElement strName = driver.findElement(By.name("strName"));
		strName.clear();
		strName.sendKeys("Biking");
		WebElement strHours = driver.findElement(By.name("strHours"));
		strHours.clear();
		strHours.sendKeys("0");
		WebElement strCalories = driver.findElement(By.name("strCalories"));
		strCalories.clear();
		strCalories.sendKeys("100");
		driver.findElement(By.tagName("form")).submit();

		// should stay on same page and have error message
		assertEquals("Add an Exercise Entry", driver.getTitle());
		assertTrue(driver.getPageSource().contains("Number of Hours must be greater than 0"));
	}

	/**
	 * Tests to ensure that you cannot enter a negative number of calories.
	 * 
	 * @throws Exception
	 */
	public void testSetNumberOfCaloriesToNegative() throws Exception {
		WebDriver driver = new HtmlUnitDriver(true);
		driver = login("1", "pw");

		driver.findElement(By.linkText("My Exercise Diary")).click();
		assertEquals("iTrust - View My Exercise Diary", driver.getTitle());

		driver.findElement(By.linkText("Add an entry to your Exercise Diary."))
				.click();

		assertEquals("Add an Exercise Entry", driver.getTitle());

		// now enter negative number for calories
		WebElement strDate = driver.findElement(By.name("strDate"));
		strDate.clear();
		strDate.sendKeys("12/13/2012");
		Select exercise = new Select(driver.findElement(By.name("strType")));
		exercise.selectByValue("Cardio");
		WebElement strName = driver.findElement(By.name("strName"));
		strName.clear();
		strName.sendKeys("Biking");
		WebElement strHours = driver.findElement(By.name("strHours"));
		strHours.clear();
		strHours.sendKeys("0");
		WebElement strCalories = driver.findElement(By.name("strCalories"));
		strCalories.clear();
		strCalories.sendKeys("-100");
		driver.findElement(By.tagName("form")).submit();

		// should stay on same page and have error message
		assertEquals("Add an Exercise Entry", driver.getTitle());
		assertTrue(driver.getPageSource().contains("Number of Calories Burned must be greater than 0"));
	}
}