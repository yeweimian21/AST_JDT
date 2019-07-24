package edu.ncsu.csc.itrust.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.action.AddSleepEntryAction;
import edu.ncsu.csc.itrust.action.DeleteSleepEntryAction;
import edu.ncsu.csc.itrust.action.ViewSleepEntryAction;
import edu.ncsu.csc.itrust.beans.SleepEntryBean;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Tests the functionality of the new Sleep Diary portion of iTrust. Tests
 * that you can add new entries, you can view entries, HCPs with the role of
 * trainer can view entries, and error checks that the user enters the
 * appropriate data in the appropriate format.
 */
public class SleepDiaryTest extends iTrustSeleniumTest {

	/**
	 * Sets up the standard testing data.
	 */
	public void setUp() throws Exception {
		super.setUp();
		gen.standardData();
	}

	/**
	 * Sanity check.
	 */
	public void tearDown() throws Exception {
		gen.clearAllTables();
	}

	/**
	 * Tests that a patient can add an entry to an empty Sleep Diary.
	 * 
	 * @throws Exception
	 */
	public void testAddSleepEntryToEmptySleepDiary() throws Exception {
		{ // Empty the diary.
			ViewSleepEntryAction viewAction = new ViewSleepEntryAction(
					TestDAOFactory.getTestInstance(), 1);
			DeleteSleepEntryAction delAction = new DeleteSleepEntryAction(
					TestDAOFactory.getTestInstance(), 1);
			List<SleepEntryBean> diary = viewAction.getDiary(1);
			for (SleepEntryBean entry : diary) {
				delAction.deleteEntry(entry.getEntryID());
			}
		}

		WebDriver driver = new HtmlUnitDriver(true);
		driver = login("1", "pw"); // login as Random Person

		// View my sleep diary so we can add a new entry
		driver.findElement(By.linkText("My Sleep Diary")).click();
		assertEquals("iTrust - View My Sleep Diary", driver.getTitle());
		driver.findElement(By.linkText("Add an entry to your Sleep Diary."))
				.click();

		assertEquals("Add a Sleep Entry", driver.getTitle());

		// Fill in all of the info and submit
		WebElement strDate = driver.findElement(By.name("strDate"));
		strDate.clear();
		strDate.sendKeys("12/12/2012");
		Select sleep = new Select(driver.findElement(By.name("strType")));
		sleep.selectByValue("Nap");
		WebElement strHours = driver.findElement(By.name("strHours"));
		strHours.clear();
		strHours.sendKeys("1.0");
		driver.findElement(By.tagName("form")).submit();

		assertEquals("iTrust - View My Sleep Diary", driver.getTitle());

		// Ensure the entry was added.
		WebElement valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/12/2012", valDate.getAttribute(VALUE));
		Select valType = new Select(driver.findElement(By
				.name("SleepType:0")));
		assertEquals("Nap", valType.getFirstSelectedOption()
				.getAttribute(VALUE));
		WebElement valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("1.0", valHours.getAttribute(VALUE));
	}

	/**
	 * Tests that a patient can add an entry to a non empty sleep diary.
	 * 
	 * @throws Exception
	 */
	public void testAddSleepEntryToNonEmptySleepDiary() throws Exception {
		WebDriver driver = new HtmlUnitDriver(true);
		driver = login("1", "pw"); // login as Random Person
		driver.findElement(By.linkText("My Sleep Diary")).click();
		assertEquals("iTrust - View My Sleep Diary", driver.getTitle());

		// Get the first values.
		WebElement valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/14/2012", valDate.getAttribute(VALUE));
		Select valType = new Select(driver.findElement(By.name("SleepType:0")));
		assertEquals("Nap", valType.getFirstSelectedOption().getAttribute(VALUE));
		WebElement valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("1.0", valHours.getAttribute(VALUE));

		// Get the second values.
		valDate = driver.findElement(By.name("Date:1"));
		assertEquals("12/12/2012", valDate.getAttribute(VALUE));
		valType = new Select(driver.findElement(By.name("SleepType:1")));
		assertEquals("Nightly", valType.getFirstSelectedOption().getAttribute(VALUE));
		valHours = driver.findElement(By.name("Hours:1"));
		assertEquals("2.0", valHours.getAttribute(VALUE));

		// Assert that the totals are correct.
		WebElement totalTable = driver.findElements(By.tagName("table")).get(2);
		List<WebElement> tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("1.0", tableRows.get(1).getText());
		assertEquals("12/12/2012", tableRows.get(2).getText());
		assertEquals("2.0", tableRows.get(3).getText());

		// Add a new entry.
		driver.findElement(By.linkText("Add an entry to your Sleep Diary."))
				.click();
		assertEquals("Add a Sleep Entry", driver.getTitle());

		// Enter new entry.
		WebElement strDate = driver.findElement(By.name("strDate"));
		strDate.clear();
		strDate.sendKeys("12/13/2012");
		Select sleep = new Select(driver.findElement(By.name("strType")));
		sleep.selectByValue("Nightly");
		WebElement strHours = driver.findElement(By.name("strHours"));
		strHours.clear();
		strHours.sendKeys("3.0");
		driver.findElement(By.tagName("form")).submit();

		assertEquals("iTrust - View My Sleep Diary", driver.getTitle());

		// Make sure the new entry shows up with the old entries.
		valDate = driver.findElement(By.name("Date:0"));
		assertEquals("12/14/2012", valDate.getAttribute(VALUE));
		valType =  new Select(driver.findElement(By.name("SleepType:0")));
		assertEquals("Nap", valType.getFirstSelectedOption().getAttribute(VALUE));
		valHours = driver.findElement(By.name("Hours:0"));
		assertEquals("1.0", valHours.getAttribute(VALUE));

		valDate = driver.findElement(By.name("Date:1"));
		assertEquals("12/13/2012", valDate.getAttribute(VALUE));
		valType =  new Select(driver.findElement(By.name("SleepType:1")));
		assertEquals("Nightly", valType.getFirstSelectedOption().getAttribute(VALUE));
		valHours = driver.findElement(By.name("Hours:1"));
		assertEquals("3.0", valHours.getAttribute(VALUE));

		valDate = driver.findElement(By.name("Date:2"));
		assertEquals("12/12/2012", valDate.getAttribute(VALUE));
		valType = new Select(driver.findElement(By.name("SleepType:2")));
		assertEquals("Nightly", valType.getFirstSelectedOption().getAttribute(VALUE));
		valHours = driver.findElement(By.name("Hours:2"));
		assertEquals("2.0", valHours.getAttribute(VALUE));

		// Assert the totals are correct.
		totalTable = driver.findElements(By.tagName("table")).get(2);
		tableRows = totalTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(0).getText());
		assertEquals("1.0", tableRows.get(1).getText());
		assertEquals("12/13/2012", tableRows.get(2).getText());
		assertEquals("3.0", tableRows.get(3).getText());

		assertEquals("12/12/2012", tableRows.get(4).getText());
		assertEquals("2.0", tableRows.get(5).getText());
	}

	/**
	 * Tests that HCPs can view a patients sleep diary.
	 * 
	 * @throws Exception
	 */
	public void testHCPViewPatientSleepDiary() throws Exception {
		WebDriver driver = new HtmlUnitDriver(true);
		driver = login("9000000000", "pw");
		driver.findElement(By.linkText("Patient Sleep Diaries")).click();

		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		// search for patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		// the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='1']")).submit();
		assertEquals("iTrust - View Patient Sleep Diaries",
				driver.getTitle());

		// different way of viewing it since viewing it through HCP
		WebElement entryTable = driver.findElements(By.tagName("table")).get(1);
		List<WebElement> tableRows = entryTable.findElements(By.tagName("td"));
		assertEquals("12/14/2012", tableRows.get(1).getText());
		assertEquals("Nap", tableRows.get(2).getText());
		assertEquals("1.0", tableRows.get(3).getText());

		assertEquals("12/12/2012", tableRows.get(5).getText());
		assertEquals("Nightly", tableRows.get(6).getText());
		assertEquals("2.0", tableRows.get(7).getText());
	}

	/**
	 * Tests that HCPs can sort a patients sleep diary.
	 * 
	 * @throws Exception
	 */
	public void testHCPCategorizePatientSleepDiary() throws Exception {
		{ // Add a different day's entry.
			AddSleepEntryAction addAction = new AddSleepEntryAction(
					TestDAOFactory.getTestInstance(), 1);
			SleepEntryBean entry = new SleepEntryBean();
			entry.setEntryID(83);
			entry.setStrDate("05/15/2013");
			entry.setSleepType("Nightly");
			entry.setHoursSlept(2.0);
			entry.setPatientID(1);
			addAction.addEntry(entry);
		}

		WebDriver driver = new HtmlUnitDriver(true);
		driver = login("9000000000", "pw");
		driver.findElement(By.linkText("Patient Sleep Diaries")).click();

		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		// search for patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		// the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='1']")).submit();
		assertEquals("iTrust - View Patient Sleep Diaries",
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
		assertEquals("Nightly", tableRows.get(2).getText());
		assertEquals("2.0", tableRows.get(3).getText());
	}

	/**
	 * Tests that HCPs can sort a patients sleep diary.
	 * 
	 * @throws Exception
	 */
	public void testHCPCategorizeBadRange() throws Exception {
		{ // Add a different day's entry.
			AddSleepEntryAction addAction = new AddSleepEntryAction(
					TestDAOFactory.getTestInstance(), 1);
			SleepEntryBean entry = new SleepEntryBean();
			entry.setEntryID(83);
			entry.setStrDate("05/15/2013");
			entry.setSleepType("Nightly");
			entry.setHoursSlept(2.0);
			entry.setPatientID(1);
			addAction.addEntry(entry);
		}

		WebDriver driver = new HtmlUnitDriver(true);
		driver = login("9000000000", "pw");
		driver.findElement(By.linkText("Patient Sleep Diaries")).click();

		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		// search for patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		// the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='1']")).submit();
		assertEquals("iTrust - View Patient Sleep Diaries",
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
			assertEquals("Nightly", tableRows.get(2).getText());
			assertEquals("2.0", tableRows.get(3).getText());

			fail("Start date was after End date.");

		} catch (Exception e) {
			assertTrue(driver.getPageSource().contains(
					"Start date must be before end date!"));
		}
	}

	/**
	 * Tests that HCPs can sort a patients sleep diary.
	 * 
	 * @throws Exception
	 */
	public void testHCPCategorizeNoEntriesInRange() throws Exception {
		{ // Add a different day's entry.
			AddSleepEntryAction addAction = new AddSleepEntryAction(
					TestDAOFactory.getTestInstance(), 1);
			SleepEntryBean entry = new SleepEntryBean();
			entry.setEntryID(83);
			entry.setStrDate("05/15/2013");
			entry.setSleepType("Nightly");
			entry.setHoursSlept(2.0);
			entry.setPatientID(1);
			addAction.addEntry(entry);
		}

		WebDriver driver = new HtmlUnitDriver(true);
		driver = login("9000000000", "pw");
		driver.findElement(By.linkText("Patient Sleep Diaries")).click();

		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		// search for patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		// the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='1']")).submit();
		assertEquals("iTrust - View Patient Sleep Diaries",
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
			assertEquals("Nightly", tableRows.get(2).getText());
			assertEquals("2.0", tableRows.get(3).getText());

			fail("Start date was after End date.");

		} catch (Exception e) {
			assertTrue(driver.getPageSource().contains(
					"There are no entries in the specified range."));
		}
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

		driver.findElement(By.linkText("My Sleep Diary")).click();
		assertEquals("iTrust - View My Sleep Diary", driver.getTitle());

		driver.findElement(By.linkText("Add an entry to your Sleep Diary."))
				.click();

		assertEquals("Add a Sleep Entry", driver.getTitle());

		// Enter an improper date
		WebElement strDate = driver.findElement(By.name("strDate"));
		strDate.clear();
		strDate.sendKeys("2012/13/12");
		Select sleep = new Select(driver.findElement(By.name("strType")));
		sleep.selectByValue("Nightly");
		WebElement strHours = driver.findElement(By.name("strHours"));
		strHours.clear();
		strHours.sendKeys("3.0");
		driver.findElement(By.tagName("form")).submit();

		// should stay on same page and have error message
		assertEquals("Add a Sleep Entry", driver.getTitle());
		assertTrue(driver.getPageSource().contains(
				"This form has not been validated correctly. The following field are not properly filled in: [Enter dates in MM/dd/yyyy]"));
	}

	/**
	 * Tests to ensure that HCPs can view a Patient's Sleep Diary when it is
	 * empty.
	 * 
	 * @throws Exception
	 */
	public void testViewEmptySleepDiaryAsHCP() throws Exception {
		WebDriver driver = new HtmlUnitDriver(true);
		driver = login("9000000000", "pw");
		driver.findElement(By.linkText("Patient Sleep Diaries")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());

		// search for patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		// the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='2']")).submit();
		assertEquals("iTrust - View Patient Sleep Diaries",
				driver.getTitle());
		assertTrue(driver.getPageSource().contains(
				"The selected patient's sleep diary is empty. If you were "
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

		driver.findElement(By.linkText("My Sleep Diary")).click();
		assertEquals("iTrust - View My Sleep Diary", driver.getTitle());

		driver.findElement(By.linkText("Add an entry to your Sleep Diary."))
				.click();

		assertEquals("Add a Sleep Entry", driver.getTitle());

		// now enter 0 for hours
		WebElement strDate = driver.findElement(By.name("strDate"));
		strDate.clear();
		strDate.sendKeys("12/13/2012");
		Select sleep = new Select(driver.findElement(By.name("strType")));
		sleep.selectByValue("Nightly");
		WebElement strHours = driver.findElement(By.name("strHours"));
		strHours.clear();
		strHours.sendKeys("0");
		driver.findElement(By.tagName("form")).submit();

		// should stay on same page and have error message
		assertEquals("Add a Sleep Entry", driver.getTitle());
		assertTrue(driver.getPageSource().contains("Number of Hours must be greater than 0"));
	}
}