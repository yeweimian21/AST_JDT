package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Tests that patients can choose a designated nutritionist
 * who is the only person able to view their nutritional information.
 */
public class DesignateNutritionistTest extends iTrustSeleniumTest {

	/**
	 * Make sure we get the standard data for each call
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp(); // clear tables is called in super
		gen.clearAllTables();
		gen.standardData();
	}
	
	/**
	 * teardown all of the data
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		gen.clearAllTables();
	}
	
	/**
	 * Tests that a hcp cannot view a patient's food diary,
	 * the patient (who previously has no designated nutritionist) selects
	 * this hcp as his nutritionist, and then the hcp can view his food diary.
	 */
	public void testAddDesignatedNutritionist() throws Exception {
		HtmlUnitDriver driver = new HtmlUnitDriver();
		//login as spencer reid
		driver = (HtmlUnitDriver)login("9000000071", "pw");
		
		//search for the patient's food diary
		driver.findElement(By.linkText("Patient Food Diaries")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		//search for patient 
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='1']")).submit();
		
		//should be back at home page with exception
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertTrue(driver.getPageSource().contains("ITrustException: "
				+ "You do not have permission to view the Food Diary"));
		//driver.findElement(By.partialLinkText("Logout")).click();
		//now login as random person
		driver = (HtmlUnitDriver)login("1", "pw");
		
		driver.findElement(By.linkText("Designate a Nutritionist")).click();
		assertEquals("iTrust - Designate a Nutritionist", driver.getTitle());
		
		//choose spencer reid as your nutritionist
		Select hcp = new Select(driver.findElement(By.name("nutritionist")));
		assertEquals("None", hcp.getFirstSelectedOption().getText());
		hcp.selectByVisibleText("Spencer Reid");
		driver.findElement(By.id("desNutrition")).submit();
		
		hcp = new Select(driver.findElement(By.name("nutritionist")));
		assertEquals("iTrust - Designate a Nutritionist", driver.getTitle());
		assertEquals("Spencer Reid", hcp.getFirstSelectedOption().getText());
		assertTrue(driver.getPageSource().contains("Congratulations! You "
				+ "switched your designated Nutritionist to Spencer Reid"));
		
		//assert that it was logged
		assertLogged(TransactionType.EDIT_DESIGNATED_NUTRITIONIST, 1, 1, "");
		
		//now logout as random person and log back in as spencer reid
		//driver.findElement(By.linkText("Logout")).click();
		driver = (HtmlUnitDriver)login("9000000071", "pw");
		
		//search for the patient's food diary
		driver.findElement(By.linkText("Patient Food Diaries")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		//search for patient 
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='1']")).submit();
		
		assertEquals("iTrust - View Patient Food Diaries", driver.getTitle());
		assertTrue(driver.getPageSource().contains("The selected patient's "
				+ "food diary is empty. If you were expecting entries "
				+ "please try again later!"));
	}
	
	/**
	 * Tests that a patient can select none as his designated nutritionist
	 * and that an hcp that could view his food diaries no longer can
	 */
	public void testNoDesignatedNutritionist() throws Exception {
		HtmlUnitDriver driver = new HtmlUnitDriver();
		//login as spencer reid
		driver = (HtmlUnitDriver)login("9000000071", "pw");
		
		//search for the patient's food diary
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
		//driver.findElement(By.partialLinkText("Logout")).click();
		//now login as Derek Morgan
		driver = (HtmlUnitDriver)login("333", "pw");
		
		driver.findElement(By.linkText("Designate a Nutritionist")).click();
		assertEquals("iTrust - Designate a Nutritionist", driver.getTitle());
		
		//choose to have no nutritionist
		Select hcp = new Select(driver.findElement(By.name("nutritionist")));
		assertEquals("Spencer Reid", hcp.getFirstSelectedOption().getText());
		hcp.selectByVisibleText("None");
		driver.findElement(By.id("desNutrition")).submit();
		
		hcp = new Select(driver.findElement(By.name("nutritionist")));
		assertEquals("iTrust - Designate a Nutritionist", driver.getTitle());
		assertEquals("None", hcp.getFirstSelectedOption().getText());
		assertTrue(driver.getPageSource().contains("Congratulations! You "
				+ "no longer have a designated Nutritionist"));
		
		//assert that it was logged
		assertLogged(TransactionType.EDIT_DESIGNATED_NUTRITIONIST, 333, 333, "");
		
		//now logout as random person and log back in as spencer reid
		//driver.findElement(By.linkText("Logout")).click();
		driver = (HtmlUnitDriver)login("9000000071", "pw");
		
		//search for the patient's food diary
		driver.findElement(By.linkText("Patient Food Diaries")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		//search for patient 
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("333");
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='333']")).submit();
		
		//should be back at home page with exception
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertTrue(driver.getPageSource().contains("ITrustException: "
				+ "You do not have permission to view the Food Diary"));
	}
	
	/**
	 * Change your designated nutritionist to a different hcp
	 */
	public void testSwitchDesignatedNutritionists() throws Exception {
		HtmlUnitDriver driver = new HtmlUnitDriver();
		//login as spencer reid
		driver = (HtmlUnitDriver)login("9000000071", "pw");
		
		//search for the patient's food diary
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
		//driver.findElement(By.partialLinkText("Logout")).click();
		
		//now check that Ben Matlock cannot view it
		driver = (HtmlUnitDriver)login("9000000072", "pw");
		
		//search for the patient's food diary
		driver.findElement(By.linkText("Patient Food Diaries")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		//search for patient 
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("333");
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='333']")).submit();
		
		//should be back at home page with exception
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertTrue(driver.getPageSource().contains("ITrustException: "
				+ "You do not have permission to view the Food Diary"));
		
		//now login as Derek Morgan
		driver = (HtmlUnitDriver)login("333", "pw");
		driver.findElement(By.linkText("Designate a Nutritionist")).click();
		assertEquals("iTrust - Designate a Nutritionist", driver.getTitle());
		
		//choose ben matlock as your nutritionist
		Select hcp = new Select(driver.findElement(By.name("nutritionist")));
		assertEquals("Spencer Reid", hcp.getFirstSelectedOption().getText());
		hcp.selectByVisibleText("Ben Matlock");
		driver.findElement(By.id("desNutrition")).submit();
		
		hcp = new Select(driver.findElement(By.name("nutritionist")));
		assertEquals("iTrust - Designate a Nutritionist", driver.getTitle());
		assertEquals("Ben Matlock", hcp.getFirstSelectedOption().getText());
		assertTrue(driver.getPageSource().contains("Congratulations! You "
				+ "switched your designated Nutritionist to Ben Matlock"));
		
		//assert that it was logged
		assertLogged(TransactionType.EDIT_DESIGNATED_NUTRITIONIST, 333, 333, "");
		
		//now logout as random person and log back in as spencer reid
		//driver.findElement(By.linkText("Logout")).click();
		driver = (HtmlUnitDriver)login("9000000071", "pw");
		
		//search for the patient's food diary
		driver.findElement(By.linkText("Patient Food Diaries")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		//search for patient 
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("333");
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='333']")).submit();
		
		//should be back at home page with exception
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertTrue(driver.getPageSource().contains("ITrustException: "
				+ "You do not have permission to view the Food Diary"));
		
		//now make sure ben matlock can view the diary
		driver = (HtmlUnitDriver)login("9000000072", "pw");
		
		//search for the patient's food diary
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
}
