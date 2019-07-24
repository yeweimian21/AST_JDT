package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class CalculateMacronutrientsTest extends iTrustSeleniumTest {

	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.patient1();
		gen.uc68();
		gen.uc71();
		gen.hcp0();
	}
	
	public void testViewMacronutrientGraphs() throws Exception {
		// Login patient 1.
		WebDriver wd = login("341", "pw");
		assertEquals("iTrust - Patient Home", wd.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 341L, 0L, "");

		// Navigate to the Macronutrients page.
		wd.findElement(By.partialLinkText("Macronutrients")).click();
		assertEquals("iTrust - View Macronutrients", wd.getTitle());
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("Macronutrient Intake Totals"));
		
	}
	
	public void testViewEmptyMacronutrientGraphs() throws Exception {
		// Login patient 1.
		WebDriver wd = login("343", "pw");
		assertEquals("iTrust - Patient Home", wd.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 343L, 0L, "");

		// Navigate to the Macronutrients page.
		wd.findElement(By.partialLinkText("Macronutrients")).click();
		assertEquals("iTrust - View Macronutrients", wd.getTitle());
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("Add an entry to your Food Diary."));
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("You have no Food Diary entries."));
		
	}
	
	public void testViewMacronutrientsAsHCP() throws Exception {
		// Login patient 1.
		WebDriver wd = (HtmlUnitDriver) login("9000000071", "pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());

		// Navigate to the Macronutrients page.
		wd.findElement(By.partialLinkText("Patient Macronutrient")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("342");
		//the button to click should have the text of the MID
		wd.findElement(By.cssSelector("input[value='342']")).submit();
		
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("Hubert Farnsworth's Macronutrient Intake"));
		
		wd = (HtmlUnitDriver) login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());

		// Navigate to the Macronutrients page.
		wd.findElement(By.partialLinkText("Patient Macronutrient")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("342");
		//the button to click should have the text of the MID
		wd.findElement(By.cssSelector("input[value='342']")).submit();
		
		assertTrue(wd.findElement(By.tagName("body")).getText().contains("You do not have permission to view the Food Diary!"));
		
		
	}

}