package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class BMICalculationTest extends iTrustSeleniumTest {

	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testUnderweightEC() throws Exception {
		//log in as HCP Kelly Doctor
		WebDriver driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.linkText("Basic Health Information")).click();
		assertEquals(driver.getTitle(), "iTrust - Please Select a Patient");
		
		// HCP 9000000000 requests a report on patient 2
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("106");
		driver.findElement(By.cssSelector("input[value='106']")).submit();
		assertEquals(driver.getTitle(), "iTrust - Edit Basic Health Record");
		
		//checking number of rows
		int rowCount=driver.findElements(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr")).size();
		assertEquals(5, rowCount);
		
		//Row 3 Date
		WebElement cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[1]"));
		assertEquals("01/01/2013", cell.getText());
		//Patient BMI
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[4]"));
		assertEquals("9.0", cell.getText());
		//Patient Weight Status
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[5]"));
		assertEquals("Underweight", cell.getText());
		
		//Row 2 Date
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[1]"));
		assertEquals("01/02/2013", cell.getText());
		//Patient BMI
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[4]"));
		assertEquals("0.1", cell.getText());
		//Patient Weight Status
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[5]"));
		assertEquals("Underweight", cell.getText());
		
		//Row 2 Date
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[1]"));
		assertEquals("01/03/2013", cell.getText());
		//Patient BMI
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[4]"));
		assertEquals("18.4", cell.getText());
		//Patient Weight Status
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[5]"));
		assertEquals("Underweight", cell.getText());
		
	}
	
	public void testNormalEC() throws Exception {
		//log in as HCP Kelly Doctor
		WebDriver driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.linkText("Basic Health Information")).click();
		assertEquals(driver.getTitle(), "iTrust - Please Select a Patient");
		
		// HCP 9000000000 requests a report on patient 2
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("107");
		driver.findElement(By.cssSelector("input[value='107']")).submit();
		assertEquals(driver.getTitle(), "iTrust - Edit Basic Health Record");
		
		//checking number of rows
		int rowCount=driver.findElements(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr")).size();
		assertEquals(5, rowCount);
		
		//Row 3 Date
		WebElement cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[1]"));
		assertEquals("01/01/2013", cell.getText());
		//Patient BMI
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[4]"));
		assertEquals("21.75", cell.getText());
		//Patient Weight Status
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[5]"));
		assertEquals("Normal", cell.getText());
		
		//Row 2 Date
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[1]"));
		assertEquals("01/02/2013", cell.getText());
		//Patient BMI
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[4]"));
		assertEquals("18.5", cell.getText());
		//Patient Weight Status
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[5]"));
		assertEquals("Normal", cell.getText());
		
		//Row 1 Date
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[1]"));
		assertEquals("01/03/2013", cell.getText());
		//Patient BMI
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[4]"));
		assertEquals("24.9", cell.getText());
		//Patient Weight Status
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[5]"));
		assertEquals("Normal", cell.getText());
		
	}

	public void testOverweightEC() throws Exception {
		//log in as HCP Kelly Doctor
		WebDriver driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.linkText("Basic Health Information")).click();
		assertEquals(driver.getTitle(), "iTrust - Please Select a Patient");
		
		// HCP 9000000000 requests a report on patient 2
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("108");
		driver.findElement(By.cssSelector("input[value='108']")).submit();
		assertEquals(driver.getTitle(), "iTrust - Edit Basic Health Record");
		
		//checking number of rows
		int rowCount=driver.findElements(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr")).size();
		assertEquals(5, rowCount);
		
		//Row 3 Date
		WebElement cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[1]"));
		assertEquals("01/01/2013", cell.getText());
		//Patient BMI
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[4]"));
		assertEquals("27.5", cell.getText());
		//Patient Weight Status
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[5]"));
		assertEquals("Overweight", cell.getText());
		
		//Row 2 Date
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[1]"));
		assertEquals("01/02/2013", cell.getText());
		//Patient BMI
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[4]"));
		assertEquals("25.0", cell.getText());
		//Patient Weight Status
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[5]"));
		assertEquals("Overweight", cell.getText());
		
		//Row 1 Date
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[1]"));
		assertEquals("01/03/2013", cell.getText());
		//Patient BMI
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[4]"));
		assertEquals("29.9", cell.getText());
		//Patient Weight Status
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[5]"));
		assertEquals("Overweight", cell.getText());
	}

	public void testObeseEC() throws Exception {
		//log in as HCP Kelly Doctor
		WebDriver driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.linkText("Basic Health Information")).click();
		assertEquals(driver.getTitle(), "iTrust - Please Select a Patient");
		
		// HCP 9000000000 requests a report on patient 2
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("109");
		driver.findElement(By.cssSelector("input[value='109']")).submit();
		assertEquals(driver.getTitle(), "iTrust - Edit Basic Health Record");
		
		//checking number of rows
		int rowCount=driver.findElements(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr")).size();
		assertEquals(4, rowCount);
		
		//Row 2 Date
		WebElement cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[1]"));
		assertEquals("01/01/2013", cell.getText());
		//Patient BMI
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[4]"));
		assertEquals("50.0", cell.getText());
		//Patient Weight Status
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[5]"));
		assertEquals("Obese", cell.getText());
		
		//Row 1 Date
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[1]"));
		assertEquals("01/02/2013", cell.getText());
		//Patient BMI
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[4]"));
		assertEquals("30.0", cell.getText());
		//Patient Weight Status
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[5]"));
		assertEquals("Obese", cell.getText());	
	}
	
	public void testAgeBounds() throws Exception {
		//log in as HCP Kelly Doctor
		WebDriver driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.linkText("Basic Health Information")).click();
		assertEquals(driver.getTitle(), "iTrust - Please Select a Patient");
		
		// HCP 9000000000 requests a report on patient 2
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("110");
		driver.findElement(By.cssSelector("input[value='110']")).submit();
		assertEquals(driver.getTitle(), "iTrust - Edit Basic Health Record");
		
		//checking number of rows
		int rowCount=driver.findElements(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr")).size();
		assertEquals(5, rowCount);
		
		//Row 3 Date
		WebElement cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[1]"));
		assertEquals("01/01/2003", cell.getText());
		//Patient BMI
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[4]"));
		assertEquals("21.76", cell.getText());
		//Patient Weight Status
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[5]"));
		assertEquals("N/A", cell.getText());
		
		//Row 2 Date
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[1]"));
		assertEquals("12/31/2009", cell.getText());
		//Patient BMI
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[4]"));
		assertEquals("21.76", cell.getText());
		//Patient Weight Status
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[5]"));
		assertEquals("N/A", cell.getText());
		
		//Row 1 Date
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[1]"));
		assertEquals("01/01/2010", cell.getText());
		//Patient BMI
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[4]"));
		assertEquals("21.76", cell.getText());
		//Patient Weight Status
		cell = driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[5]"));
		assertEquals("Normal", cell.getText());
	}
}