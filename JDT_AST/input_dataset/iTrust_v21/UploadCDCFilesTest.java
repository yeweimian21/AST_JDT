package edu.ncsu.csc.itrust.selenium;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class UploadCDCFilesTest extends iTrustSeleniumTest {
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.admin1();
	}
	
	public void testCDCImport_Head() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000001", "pw");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		
		driver.findElement(By.linkText("Upload CDC Percentile Metrics")).click();
		assertEquals("iTrust - Import CDC Percentile Metrics", driver.getTitle());
		
		// input tag is under the form of importCDC 
		WebElement file_input = driver.findElement(By.xpath("//*[@id=\"NDCodeUploadForm\"]/input[1]"));
		
		// upload the file by its absolute path
		file_input.sendKeys(tempHeadFile().getAbsolutePath());
		
		Select selectMetric = new Select
				(driver.findElementByXPath("//*[@id=\"NDCodeUploadForm\"]/select"));
		selectMetric.selectByVisibleText("Head Circumfrence");
		
		WebElement submit_button = driver.findElement(By.xpath("//*[@id=\"import\"]"));
		submit_button.click();
		
		// Current page should contain a message
		WebElement currentPage = driver.findElement(By.id("iTrustContent"));
		// System.out.println(currentPage.getText());
		
		assertEquals("iTrust - Import CDC Percentile Metrics", driver.getTitle());
		assertTrue(currentPage.getText().contains("CDC Metrics updated successfully!"));
	}
	
	public void testCDCImport_BMI() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000001", "pw");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		
		driver.findElement(By.linkText("Upload CDC Percentile Metrics")).click();
		assertEquals("iTrust - Import CDC Percentile Metrics", driver.getTitle());
		
		// input tag is under the form of importCDC 
		WebElement file_input = driver.findElement(By.xpath("//*[@id=\"NDCodeUploadForm\"]/input[1]"));
		
		// upload the file by its absolute path
		file_input.sendKeys(tempBMIFile().getAbsolutePath());
		
		Select selectMetric = new Select
				(driver.findElementByXPath("//*[@id=\"NDCodeUploadForm\"]/select"));
		selectMetric.selectByVisibleText("Body Mass Index");
		
		WebElement submit_button = driver.findElement(By.xpath("//*[@id=\"import\"]"));
		submit_button.click();
		
		// Current page should contain a message
		WebElement currentPage = driver.findElement(By.id("iTrustContent"));
		// System.out.println(currentPage.getText());
		
		assertEquals("iTrust - Import CDC Percentile Metrics", driver.getTitle());
		assertTrue(currentPage.getText().contains("CDC Metrics updated successfully!"));
	}
	
	public void testImportCDC_Height() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000001", "pw");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		
		driver.findElement(By.linkText("Upload CDC Percentile Metrics")).click();
		assertEquals("iTrust - Import CDC Percentile Metrics", driver.getTitle());
		
		// input tag is under the form of importCDC 
		WebElement file_input = driver.findElement(By.xpath("//*[@id=\"NDCodeUploadForm\"]/input[1]"));
		
		// upload the file by its absolute path
		file_input.sendKeys(tempHeightFile().getAbsolutePath());
		
		Select selectMetric = new Select
				(driver.findElementByXPath("//*[@id=\"NDCodeUploadForm\"]/select"));
		selectMetric.selectByVisibleText("Height");
		
		WebElement submit_button = driver.findElement(By.xpath("//*[@id=\"import\"]"));
		submit_button.click();
		
		// Current page should contain a message
		WebElement currentPage = driver.findElement(By.id("iTrustContent"));
		// System.out.println(currentPage.getText());
		
		assertEquals("iTrust - Import CDC Percentile Metrics", driver.getTitle());
		assertTrue(currentPage.getText().contains("CDC Metrics updated successfully!"));
	}
	
	public void testImportCDC_Weight() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000001", "pw");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		
		driver.findElement(By.linkText("Upload CDC Percentile Metrics")).click();
		assertEquals("iTrust - Import CDC Percentile Metrics", driver.getTitle());
		
		// input tag is under the form of importCDC 
		WebElement file_input = driver.findElement(By.xpath("//*[@id=\"NDCodeUploadForm\"]/input[1]"));
		
		// upload the file by its absolute path
		file_input.sendKeys(tempWeightFile().getAbsolutePath());
		
		Select selectMetric = new Select
				(driver.findElementByXPath("//*[@id=\"NDCodeUploadForm\"]/select"));
		selectMetric.selectByVisibleText("Weight");
		
		WebElement submit_button = driver.findElement(By.xpath("//*[@id=\"import\"]"));
		submit_button.click();
		
		// Current page should contain a message
		WebElement currentPage = driver.findElement(By.id("iTrustContent"));
		// System.out.println(currentPage.getText());
		
		assertEquals("iTrust - Import CDC Percentile Metrics", driver.getTitle());
		assertTrue(currentPage.getText().contains("CDC Metrics updated successfully!"));
	}
	
	public void testImportCDC_Dirtyfile() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000001", "pw");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		
		driver.findElement(By.linkText("Upload CDC Percentile Metrics")).click();
		assertEquals("iTrust - Import CDC Percentile Metrics", driver.getTitle());
		
		// input tag is under the form of importCDC 
		WebElement file_input = driver.findElement(By.xpath("//*[@id=\"NDCodeUploadForm\"]/input[1]"));
		
		// upload the file by its absolute path
		file_input.sendKeys(tempDirtyFile().getAbsolutePath());
		
		Select selectMetric = new Select
				(driver.findElementByXPath("//*[@id=\"NDCodeUploadForm\"]/select"));
		selectMetric.selectByVisibleText("Weight");
		
		WebElement submit_button = driver.findElement(By.xpath("//*[@id=\"import\"]"));
		submit_button.click();
		
		// Current page should contain a message
		WebElement currentPage = driver.findElement(By.id("iTrustContent"));
		// System.out.println(currentPage.getText());
		
		assertEquals("iTrust - Import CDC Percentile Metrics", driver.getTitle());
		assertTrue(currentPage.getText().contains("Please make sure the file you are submitting is formatted as per the CDC's instructions!"));
	}
	
	//test file for weight
	private File tempWeightFile() throws IOException {
		File f = File.createTempFile("weightcodes", null);
		FileWriter fw = new FileWriter(f);
		fw.write(
				"Sex,Agemos,L,M,S,P3,P5,P10,P25,P50,P75,P90,P95,P97\n" +
				"1,0,90.989747,3.530203168,0.152385273,2.355450986,2.52690402,2.773801848,3.150611082,3.530203168,3.879076559,4.17249339,4.34029274,4.446488308\n" +
				"1,0.5,1.547523128,4.003106424,0.146025021,2.799548641,2.964655655,3.209510017,3.597395573,4.003106424,4.387422565,4.718161283,4.910130108,5.032624982\n" +
				"1,1.5,1.068795548,4.879525083,0.136478767,3.614688072,3.774848862,4.020561446,4.428872952,4.879525083,5.327327567,5.728152752,5.967101615,6.121929103\n" +
				"1,2.5,0.695973505,5.672888765,0.129677511,4.34234145,4.503255345,4.754479354,5.183377547,5.672888765,6.175598158,6.638979132,6.921119162,7.106250132\n" +
				"1,3.5,0.41981509,6.391391982,0.124717085,4.992897896,5.157411653,5.416802856,5.866806254,6.391391982,6.942217106,7.460702368,7.781401145,7.993878049\n" 
				);
		fw.flush();
		fw.close();
		return f;
	}
	
	//test file for height
	private File tempHeightFile() throws IOException {
		File f = File.createTempFile("heightcodes", null);
		FileWriter fw = new FileWriter(f);
		fw.write(
				"Sex,Agemos,L,M,S,P3,P5,P10,P25,P50,P75,P90,P95,P97\n" +
				"1,0,9.989747,3.530203168,0.152385273,2.355450986,2.52690402,2.773801848,3.150611082,3.530203168,3.879076559,4.17249339,4.34029274,4.446488308\n" +
				"1,0.5,1.547523128,4.003106424,0.146025021,2.799548641,2.964655655,3.209510017,3.597395573,4.003106424,4.387422565,4.718161283,4.910130108,5.032624982\n" +
				"1,1.5,1.068795548,4.879525083,0.136478767,3.614688072,3.774848862,4.020561446,4.428872952,4.879525083,5.327327567,5.728152752,5.967101615,6.121929103\n" +
				"1,2.5,0.695973505,5.672888765,0.129677511,4.34234145,4.503255345,4.754479354,5.183377547,5.672888765,6.175598158,6.638979132,6.921119162,7.106250132\n" +
				"1,3.5,0.41981509,6.391391982,0.124717085,4.992897896,5.157411653,5.416802856,5.866806254,6.391391982,6.942217106,7.460702368,7.781401145,7.993878049\n" 
				);
		fw.flush();
		fw.close();
		return f;
	}
	
	private File tempHeadFile() throws IOException {
		File f = File.createTempFile("heightcodes", null);
		FileWriter fw = new FileWriter(f);
		fw.write(
				"Sex,Agemos,L,M,S,P3,P5,P10,P25,P50,P75,P90,P95,P97\n" +
				"1,0,1.989747,3.530203168,0.152385273,2.355450986,2.52690402,2.773801848,3.150611082,3.530203168,3.879076559,4.17249339,4.34029274,4.446488308\n" +
				"1,0.5,1.547523128,4.003106424,0.146025021,2.799548641,2.964655655,3.209510017,3.597395573,4.003106424,4.387422565,4.718161283,4.910130108,5.032624982\n" +
				"1,1.5,1.068795548,4.879525083,0.136478767,3.614688072,3.774848862,4.020561446,4.428872952,4.879525083,5.327327567,5.728152752,5.967101615,6.121929103\n" +
				"1,2.5,0.695973505,5.672888765,0.129677511,4.34234145,4.503255345,4.754479354,5.183377547,5.672888765,6.175598158,6.638979132,6.921119162,7.106250132\n" +
				"1,3.5,0.41981509,6.391391982,0.124717085,4.992897896,5.157411653,5.416802856,5.866806254,6.391391982,6.942217106,7.460702368,7.781401145,7.993878049\n" 
				);
		fw.flush();
		fw.close();
		return f;
	}
	
	private File tempBMIFile() throws IOException {
		File f = File.createTempFile("heightcodes", null);
		FileWriter fw = new FileWriter(f);
		fw.write(
				"Sex,Agemos,L,M,S,P3,P5,P10,P25,P50,P75,P90,P95,P97\n" +
				"1,0,920.989747,3.530203168,0.152385273,2.355450986,2.52690402,2.773801848,3.150611082,3.530203168,3.879076559,4.17249339,4.34029274,4.446488308\n" +
				"1,0.5,1.547523128,4.003106424,0.146025021,2.799548641,2.964655655,3.209510017,3.597395573,4.003106424,4.387422565,4.718161283,4.910130108,5.032624982\n" +
				"1,1.5,1.068795548,4.879525083,0.136478767,3.614688072,3.774848862,4.020561446,4.428872952,4.879525083,5.327327567,5.728152752,5.967101615,6.121929103\n" +
				"1,2.5,0.695973505,5.672888765,0.129677511,4.34234145,4.503255345,4.754479354,5.183377547,5.672888765,6.175598158,6.638979132,6.921119162,7.106250132\n" +
				"1,3.5,0.41981509,6.391391982,0.124717085,4.992897896,5.157411653,5.416802856,5.866806254,6.391391982,6.942217106,7.460702368,7.781401145,7.993878049\n" 
				);
		fw.flush();
		fw.close();
		return f;
	}
	
	//dirty file to test the return on bad file formats.
	private File tempDirtyFile() throws IOException {
		File f = File.createTempFile("heightcodes", null);
		FileWriter fw = new FileWriter(f);
		fw.write(
				"TNG WAS BETTER THAN DS9"
				);
		fw.flush();
		fw.close();
		return f;
	}
}