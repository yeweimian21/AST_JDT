package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


/**
 * Selenium conversion test for HttpUnit ImportNDCodesTest
 */
@SuppressWarnings("unused")
public class ImportNDCodesTest extends iTrustSeleniumTest {
	
	private WebDriver driver;
	private WebElement element;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.admin1();
		driver = new HtmlUnitDriver();
		driver.get("http://localhost:8080/iTrust/");
	}
		
	/*
	 * Test that drugs can be updated from a list
	 */
	public void testImportDrugs() throws Exception {
		
		driver.findElement(By.id("j_username")).sendKeys("9000000001");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		assertEquals("iTrust - Admin Home", driver.getTitle());
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/admin/editNDCodes.jsp']")).click();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		driver.findElement(By.id("import")).submit();
		assertEquals("iTrust - Import ND Codes", driver.getTitle());
		
		File f = tempNDCFile();
		
		element = driver.findElement(By.name("fileIn"));
		element.sendKeys(f.getAbsolutePath());
		
		Select ndDropDown = new Select(driver.findElement(By.name("strategy")));
		ndDropDown.selectByValue("ignore");
		driver.findElement(By.id("import")).submit();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		
		List <WebElement> tableList = driver.findElements(By.className("fTable"));
		System.out.println(tableList.get(0).getText());
		assertTrue(tableList.get(0).getText().contains("Current Drug ND Codes"));
		assertTrue(tableList.get(0).getText().contains("05730-150 ADVIL"));
		assertTrue(tableList.get(0).getText().contains("10544-591 OxyContin"));
		assertTrue(tableList.get(0).getText().contains("11523-7197 Claritin"));
		assertTrue(tableList.get(0).getText().contains("50458-513 TYLENOL with Codeine"));
		
		f.delete();
		
	}

	/*
	 * Test that a new list of drugs can update an existing list
	 */
	public void testImportDrugs_UpdateDupes() throws Exception {
		
		driver.findElement(By.id("j_username")).sendKeys("9000000001");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		assertEquals("iTrust - Admin Home", driver.getTitle());
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/admin/editNDCodes.jsp']")).click();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		driver.findElement(By.id("import")).submit();
		assertEquals("iTrust - Import ND Codes", driver.getTitle());
		
		File f = tempNDCFile();
		
		element = driver.findElement(By.name("fileIn"));
		element.sendKeys(f.getAbsolutePath());
		
		Select ndDropDown = new Select(driver.findElement(By.name("strategy")));
		ndDropDown.selectByValue("ignore");
		driver.findElement(By.id("import")).submit();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		
		List <WebElement> tableList = driver.findElements(By.className("fTable"));
		
		assertTrue(tableList.get(0).getText().contains("Current Drug ND Codes"));
		assertTrue(tableList.get(0).getText().contains("05730-150 ADVIL"));
		assertTrue(tableList.get(0).getText().contains("10544-591 OxyContin"));
		assertTrue(tableList.get(0).getText().contains("11523-7197 Claritin"));
		assertTrue(tableList.get(0).getText().contains("50458-513 TYLENOL with Codeine"));
		
		f.delete();
		
		driver.findElement(By.id("import")).submit();
		assertEquals("iTrust - Import ND Codes", driver.getTitle());
		
		File f2 = tempNDCFile2();
		element = driver.findElement(By.name("fileIn"));
		element.sendKeys(f2.getAbsolutePath());
		
		ndDropDown = new Select(driver.findElement(By.name("strategy")));
		ndDropDown.selectByValue("update");
		driver.findElement(By.id("import")).submit();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		
		tableList = driver.findElements(By.className("fTable"));
	
		assertTrue(tableList.get(0).getText().contains("Current Drug ND Codes"));
		assertTrue(tableList.get(0).getText().contains("05730-150 ADVIL"));
		assertTrue(tableList.get(0).getText().contains("10544-591 OxyContin"));
		assertTrue(tableList.get(0).getText().contains("11523-7197 Claritin"));
		assertTrue(tableList.get(0).getText().contains("50458-513 TYLENOL with Coke"));
		
		f.delete();
		
	}
	
	/*
	 * Test the return button
	 */
	public void testImportDrugs_IgnoreDupes() throws Exception {
		
		driver.findElement(By.id("j_username")).sendKeys("9000000001");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/admin/editNDCodes.jsp']")).click();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		driver.findElement(By.id("import")).submit();
		assertEquals("iTrust - Import ND Codes", driver.getTitle());
		
		File f = tempNDCFile();
		
		element = driver.findElement(By.name("fileIn"));
		element.sendKeys(f.getAbsolutePath());
		
		Select ndDropDown = new Select(driver.findElement(By.name("strategy")));
		ndDropDown.selectByValue("ignore");
		driver.findElement(By.id("import")).submit();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		
		List <WebElement> tableList = driver.findElements(By.className("fTable"));
		
		assertTrue(tableList.get(0).getText().contains("Current Drug ND Codes"));
		assertTrue(tableList.get(0).getText().contains("05730-150 ADVIL"));
		assertTrue(tableList.get(0).getText().contains("10544-591 OxyContin"));
		assertTrue(tableList.get(0).getText().contains("11523-7197 Claritin"));
		assertTrue(tableList.get(0).getText().contains("50458-513 TYLENOL with Codeine"));
		
		f.delete();
		
		driver.findElement(By.id("import")).submit();
		assertEquals("iTrust - Import ND Codes", driver.getTitle());
		
		File f2 = tempNDCFile2();
		element = driver.findElement(By.name("fileIn"));
		element.sendKeys(f2.getAbsolutePath());
		
		ndDropDown = new Select(driver.findElement(By.name("strategy")));
		ndDropDown.selectByValue("ignore");
		driver.findElement(By.id("import")).submit();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		
		tableList = driver.findElements(By.className("fTable"));
		
		assertTrue(tableList.get(0).getText().contains("Current Drug ND Codes"));
		assertTrue(tableList.get(0).getText().contains("05730-150 ADVIL"));
		assertTrue(tableList.get(0).getText().contains("10544-591 OxyContin"));
		assertTrue(tableList.get(0).getText().contains("11523-7197 Claritin"));
		assertTrue(tableList.get(0).getText().contains("50458-513 TYLENOL with Codeine"));
		
		f2.delete();
		
	}
	
	/*
	 * Test file generator 1
	 */
	private File tempNDCFile() throws IOException {
		File f = File.createTempFile("ndcodes", null);
		FileWriter fw = new FileWriter(f);
		fw.write(
				"0573-0150	HUMAN OTC DRUG	ADVIL		IBUPROFEN	TABLET, COATED	ORAL	19840518		NDA	NDA018989	Pfizer Consumer Healthcare	IBUPROFEN	200	mg/1	Nonsteroidal Anti-inflammatory Drug [EPC], Cyclooxygenase Inhibitors [MoA], Nonsteroidal Anti-inflammatory Compounds [Chemical/Ingredient]	\n" +
				"50458-513	HUMAN PRESCRIPTION DRUG	TYLENOL with Codeine		ACETAMINOPHEN AND CODEINE PHOSPHATE	TABLET	ORAL	19770817		ANDA	ANDA085055	Janssen Pharmaceuticals, Inc.	ACETAMINOPHEN; CODEINE PHOSPHATE	300; 30	mg/1; mg/1		CIII\n" +
				"10544-591	HUMAN PRESCRIPTION DRUG	OxyContin		OXYCODONE HYDROCHLORIDE	TABLET, FILM COATED, EXTENDED RELEASE	ORAL	20100126		NDA	NDA020553	Blenheim Pharmacal, Inc.	OXYCODONE HYDROCHLORIDE	10	mg/1	Opioid Agonist [EPC], Full Opioid Agonists [MoA]	CII\n" +
				"11523-7197	HUMAN OTC DRUG	Claritin		LORATADINE	SOLUTION	ORAL	20110301		NDA	NDA020641	Schering Plough Healthcare Products Inc.	LORATADINE	5	mg/5mL		\n"
				);
		fw.flush();
		fw.close();
		return f;
	}
	
	/*
	 * Test file generator 2
	 */
	private File tempNDCFile2() throws IOException {
		File f = File.createTempFile("ndcodes2", null);
		FileWriter fw = new FileWriter(f);
		fw.write(
				"0573-0150	HUMAN OTC DRUG	ADVIL NEW		IBUPROFEN	TABLET, COATED	ORAL	19840518		NDA	NDA018989	Pfizer Consumer Healthcare	IBUPROFEN	200	mg/1	Nonsteroidal Anti-inflammatory Drug [EPC], Cyclooxygenase Inhibitors [MoA], Nonsteroidal Anti-inflammatory Compounds [Chemical/Ingredient]	\n" +
				"50458-513	HUMAN PRESCRIPTION DRUG	TYLENOL with Coke		ACETAMINOPHEN AND CODEINE PHOSPHATE	TABLET	ORAL	19770817		ANDA	ANDA085055	Janssen Pharmaceuticals, Inc.	ACETAMINOPHEN; CODEINE PHOSPHATE	300; 30	mg/1; mg/1		CIII\n"
				);
		fw.flush();
		fw.close();
		return f;
	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
	}


}