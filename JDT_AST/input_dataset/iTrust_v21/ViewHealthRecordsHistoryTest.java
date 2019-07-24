package edu.ncsu.csc.itrust.selenium;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Selenium tests for ViewHealthRecordsHistory
 */
public class ViewHealthRecordsHistoryTest extends iTrustSeleniumTest {
	
	private HtmlUnitDriver driver;
	/**
	 * Sets up the test environment
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		//Generate standard data
		gen.standardData();
	}
	
	/**
	 * testOfficeVisit4MonthOldViewHealthMetrics
	 * @throws Exception
	 */
	@Test
	public void testOfficeVisit4MonthOldViewHealthMetrics() throws Exception{
		//Login as HCP Shelly Vang
		driver = (HtmlUnitDriver)login("8000000011", "pw");
		assertLogged(TransactionType.HOME_VIEW, 8000000011L, 0L, "");
		
		//Click Document Office Visit Link
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    //Search for Brynn McClain (MID 101)
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("101");
		
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='101']")).submit();
	    //Verify Document Office Visit page
	    assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
	    //Click Yes, Document Office Visit
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    //Verify Edit Office Visit page
	    assertEquals("iTrust - Document Office Visit", driver.getTitle());
	    
	    //Add a new office visit
	    //Enter 10/01/2013 for the Office Visit Date
	    driver.findElement(By.name("visitDate")).clear();
	    driver.findElement(By.name("visitDate")).sendKeys("10/01/2013");
	    //Select Central Hospital for the hospital
	    new Select(driver.findElement(By.name("hospitalID"))).selectByVisibleText("Central Hospital");
	    //Enter "Brynn can start eating rice cereal mixed with breast milk or formula once a day" for Notes
	    driver.findElement(By.name("notes")).clear();
	    driver.findElement(By.name("notes")).sendKeys("Brynn can start eating rice cereal mixed with breast milk or formula once a day.");
	    //Click the create button
	    driver.findElement(By.id("update")).click();
	    
	    //Verify "Information Successfully Updated" message
	    assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
	    
	    //Enter Health Metrics
	    //Enter 22.3 in for Length
	    driver.findElement(By.name("height")).clear();
	    driver.findElement(By.name("height")).sendKeys("22.3");
	    //Enter 16.5 lbs for Weight
	    driver.findElement(By.name("weight")).clear();
	    driver.findElement(By.name("weight")).sendKeys("16.5");
	    //Enter 16.1 in for Head Circumference
	    driver.findElement(By.name("headCircumference")).clear();
	    driver.findElement(By.name("headCircumference")).sendKeys("16.1");
	    //Select '1 - non-smoking household' for household smoking status
	    new Select(driver.findElement(By.id("householdSmokingStatus"))).selectByVisibleText("1 - non-smoking household");
	    //Click the Add Record button
	    driver.findElement(By.id("addHR")).click();
	    
	    //Verify "Health information successfully updated." message
	    assertTrue(driver.getPageSource().contains("Health information successfully updated."));
	    
	    //Click Basic Health Information link
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
	  
	    //Verify Basic Health Information page
  		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp",driver.getCurrentUrl());
	    
	    //Verify adult health record table displays
  		WebElement hrTable = driver.findElement(By.id("HealthRecordsTable"));
  		List<WebElement> tableRows = hrTable.findElements(By.tagName("tr"));
  		List<WebElement> entriesToCheck = tableRows.get(2).findElements(By.tagName("td"));
  		//Verify the table has the provided information: Header, field descriptions, 1 row of health records
  		assertEquals(3, tableRows.size());
  		
  		//Verify table contents
  		//Row 1 values
  		//Office visit date
  		assertEquals("10/01/2013", entriesToCheck.get(0).getText());
  		//Patient length
  		assertEquals("22.3in", entriesToCheck.get(1).getText());
  		//Patient weight
  		assertEquals("16.5lbs", entriesToCheck.get(2).getText());
  		//Patient head circumference
  		assertEquals("16.1in", entriesToCheck.get(4).getText());
  		//Patient household smoking status
  		assertEquals("Non-smoking household", entriesToCheck.get(5).getText());
	}
	
	/**
	 * testOfficeVisit24YrOldViewHealthMetrics
	 * @throws Exception
	 */
	@Test
	public void testOfficeVisit24YrOldViewHealthMetrics() throws Exception{
		//Login as Patient Thane Ross (MID 105)
		driver = (HtmlUnitDriver)login("105", "pw");
		assertLogged(TransactionType.HOME_VIEW, 105L, 0L, "");
		
		//Click View My Records link
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View My Records")).click();
		
		//Verify Basic Health Information page
		assertEquals(ADDRESS + "auth/patient/viewMyRecords.jsp", driver.getCurrentUrl());
		
		//Verify adult health record table displays
		WebElement hrTable = driver.findElement(By.id("HealthRecordsTable"));
		List<WebElement> tableRows = hrTable.findElements(By.tagName("tr"));
		List<WebElement> entriesToCheck = tableRows.get(2).findElements(By.tagName("td"));
		//Check that the table has 3 rows
		assertEquals(3, tableRows.size());
		//Office visit date
		assertEquals("10/25/2013", entriesToCheck.get(0).getText());
		//Patient height
		assertEquals("73.1in", entriesToCheck.get(1).getText());
		//Patient weight
		assertEquals("210.1lbs", entriesToCheck.get(2).getText());
		//Patient BMI
		assertEquals("27.64", entriesToCheck.get(3).getText());
		//Check weight status
		assertEquals("Overweight", entriesToCheck.get(4).getText());
		//Patient blood pressure
		assertEquals("160/100 mmHg", entriesToCheck.get(5).getText());
		//Patient smoking status
		assertEquals("N", entriesToCheck.get(6).getText());
		//Patient household smoking status
		assertEquals("Non-smoking household", entriesToCheck.get(7).getText());
		//Patient HDL levels
		assertEquals("37 mg/dL", entriesToCheck.get(8).getText());
		//Patient LDL levels
		assertEquals("141 mg/dL", entriesToCheck.get(9).getText());
		//Patient triglycerides
		assertEquals("162 mg/dL", entriesToCheck.get(10).getText());
	}
	
	/**
	 * testOfficeVisit20YrOldViewHealthMetrics
	 * @throws Exception
	 */
	@Test
	public void testOfficeVisit20YrOldViewHealthMetrics() throws Exception{
		//Login as HCP Shelly Vang
		driver = (HtmlUnitDriver)login("8000000011", "pw");	
		assertLogged(TransactionType.HOME_VIEW, 8000000011L, 0L, "");
		
		//Click Basic Health Information link
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
		//Search for Daria Griffin (MID 104)
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("104");
		
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='104']")).submit();
		
		//Verify Basic Health Information page
		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp", driver.getCurrentUrl());
		
		//Verify adult health record table displays
		WebElement hrTable = driver.findElement(By.id("HealthRecordsTable"));
		List<WebElement> tableRows = hrTable.findElements(By.tagName("tr"));
		List<WebElement> row1 = tableRows.get(2).findElements(By.tagName("td"));
		List<WebElement> row2 = tableRows.get(3).findElements(By.tagName("td"));
		List<WebElement> row3 = tableRows.get(4).findElements(By.tagName("td"));
		//Check that the table has 3 rows
		assertEquals(5, tableRows.size());
		//Verify table contents
				
		//Row 1 values
		//Office visit date
		assertEquals("10/25/2013", row1.get(0).getText());
		//Patient length
		assertEquals("62.3in", row1.get(1).getText());
		//Patient weight
		assertEquals("124.3lbs", row1.get(2).getText());
		//Patient BMI
		assertEquals("22.51", row1.get(3).getText());
		//Weight status
		assertEquals("Normal", row1.get(4).getText());
		//Patient blood pressure
		assertEquals("100/75 mmHg", row1.get(5).getText());
		//Patient smoking status
		assertEquals("N", row1.get(6).getText());
		//Patient household smoking status
		assertEquals("Non-smoking household", row1.get(7).getText());
		//Patient HDL levels
		assertEquals("65 mg/dL", row1.get(8).getText());
		//Patient LDL levels
		assertEquals("102 mg/dL", row1.get(9).getText());
		//Patient triglycerides
		assertEquals("147 mg/dL", row1.get(10).getText());
		
				
		//Row 2 values
		//Office visit date
		assertEquals("10/20/2012", row2.get(0).getText());
		//Patient length
		assertEquals("62.3in", row2.get(1).getText());
		//Patient weight
		assertEquals("120.7lbs", row2.get(2).getText());
		//Patient BMI
		assertEquals("21.86",row2.get(3).getText());
		//Weight status
		assertEquals("N/A", row2.get(4).getText());
		//Patient blood pressure
		assertEquals("107/72 mmHg", row2.get(5).getText());
		//Patient smoking status
		assertEquals("Y", row2.get(6).getText());
		//Patient household smoking status
		assertEquals("Indoor smokers", row2.get(7).getText());
		//Patient HDL levels
		assertEquals("63 mg/dL", row2.get(8).getText());
		//Patient LDL levels
		assertEquals("103 mg/dL", row2.get(9).getText());
		//Patient triglycerides
		assertEquals("145 mg/dL", row2.get(10).getText());
				
		//Row 3 values
		//Office visit date
		assertEquals("10/10/2011", row3.get(0).getText());
		//Patient length
		assertEquals("62.3in", row3.get(1).getText());
		//Patient weight
		assertEquals("121.3lbs", row3.get(2).getText());
		//Patient BMI
		assertEquals("21.97", row3.get(3).getText());
		//Weight status
		assertEquals("N/A", row3.get(4).getText());
		//Patient head circumference
		assertEquals("105/73 mmHg", row3.get(5).getText());
		//Patient smoking status
		assertEquals("Y", row3.get(6).getText());
		//Patient household smoking status
		assertEquals("Indoor smokers", row3.get(7).getText());
		//Patient HDL levels
		assertEquals("64 mg/dL", row3.get(8).getText());
		//Patient LDL levels
		assertEquals("102 mg/dL", row3.get(9).getText());
		//Patient triglycerides
		assertEquals("143 mg/dL", row3.get(10).getText());
	}
	
	/**
	 * testDeletedHealthRecord
	 * @throws Exception
	 */
	@Test
	public void testDeletedHealthRecord() throws Exception{
		//Login as HCP Kelly Doctor
		driver = (HtmlUnitDriver)login("9000000000", "pw");	
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Click Document Office Visit Link
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
		//Search for Random Person (MID 1)
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='1']")).submit();
		//Verify Document Office Visit page
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		//Click Yes, Document Office Visit
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//Verify Edit Office Visit page
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		//Add a new office visit
		//Enter 10/01/2013 for the Office Visit Date
		driver.findElement(By.name("visitDate")).clear();
	    driver.findElement(By.name("visitDate")).sendKeys("10/01/2013");
	    //Select Central Hospital for the hospital
	    new Select(driver.findElement(By.name("hospitalID"))).selectByVisibleText("Central Hospital");
	    //Enter "Random has consumed unknown seed 32912" for Notes
	    driver.findElement(By.name("notes")).clear();
	    driver.findElement(By.name("notes")).sendKeys("Random has consumed unknown seed 32912");
	    //Click the create button
	    driver.findElement(By.id("update")).click();
		
	    driver.setJavascriptEnabled(true);
		//Verify "Information Successfully Updated" message
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		//Enter Health Metrics
		//Enter 74 in for Height
		driver.findElement(By.name("height")).clear();
	    driver.findElement(By.name("height")).sendKeys("74");
	    //Enter 165.8 lbs for Weight
	    driver.findElement(By.name("weight")).clear();
	    driver.findElement(By.name("weight")).sendKeys("165.8");
	    //Enter 110/75 mmHg for Blood Pressure
	    driver.findElement(By.name("bloodPressureN")).clear();
	    driver.findElement(By.name("bloodPressureN")).sendKeys("110");
	    driver.findElement(By.name("bloodPressureD")).clear();
	    driver.findElement(By.name("bloodPressureD")).sendKeys("75");
	    //Select '1 - non-smoking household' for Household Smoking Status
	    new Select(driver.findElement(By.id("householdSmokingStatus"))).selectByVisibleText("1 - non-smoking household");
	    //Select '3 - Former smoker' for Patient Smoking Status
	    new Select(driver.findElement(By.id("isSmoker"))).selectByVisibleText("3 - Former smoker");
	    //Enter 68 for HDL
	    driver.findElement(By.name("cholesterolHDL")).clear();
	    driver.findElement(By.name("cholesterolHDL")).sendKeys("68");
	    //Enter 107 for LDL
	    driver.findElement(By.name("cholesterolLDL")).clear();
	    driver.findElement(By.name("cholesterolLDL")).sendKeys("107");
	    //Enter 162 for Triglycerides
	    driver.findElement(By.name("cholesterolTri")).clear();
	    driver.findElement(By.name("cholesterolTri")).sendKeys("162");
	    //Click Update Record
	    driver.findElement(By.id("addHR")).click();
		
		//Verify "Health information successfully updated." message
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		assertTrue(driver.getPageSource().contains("Health information successfully updated."));
		
		//Remove health record
		driver.findElement(By.linkText("Remove")).click();
		assertTrue(driver.getPageSource().contains("Health information successfully updated."));
		
		//Click Basic Health Information link
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
		//Verify Basic Health Information page
		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp", driver.getCurrentUrl());
		
		WebElement hrTable = driver.findElement(By.id("HealthRecordsTable"));
  		List<WebElement> tableRows = hrTable.findElements(By.tagName("tr"));
  		List<WebElement> row1 = tableRows.get(2).findElements(By.tagName("td"));
  		//Verify the table has the provided information: Header, field descriptions, 1 row of health records
  		assertEquals(4, tableRows.size());
		//Verify table contents
				
		//Row 1 values
		//Office visit date
		assertEquals("06/11/2007", row1.get(0).getText());
		//Patient length
		assertEquals("72.0in", row1.get(1).getText());
		//Patient weight
		assertEquals("185.0lbs", row1.get(2).getText());
		//Patient BMI
		assertEquals("25.09", row1.get(3).getText());
		//Weight status
		assertEquals("Overweight", row1.get(4).getText());
		//Patient blood pressure
		assertEquals("107/104 mmHg", row1.get(5).getText());
		//Patient smoking status
		assertEquals("N", row1.get(6).getText());
		//Patient household smoking status
		assertEquals("Non-smoking household", row1.get(7).getText());
		//Patient HDL levels
		assertEquals("41 mg/dL", row1.get(8).getText());
		//Patient LDL levels
		assertEquals("104 mg/dL", row1.get(9).getText());
		//Patient triglycerides
		assertEquals("101 mg/dL", row1.get(10).getText());
		//By personnel Kelly Doctor
		assertEquals("Kelly Doctor", row1.get(13).getText());
	}
	
	/**
	 * testOfficeVisitDateIsBirthDate
	 * @throws Exception
	 */
	@Test
	public void testOfficeVisitDateIsBirthDate() throws Exception{
		//Login as HCP Shelly Vang
		driver = (HtmlUnitDriver)login("8000000011", "pw");	
		assertLogged(TransactionType.HOME_VIEW, 8000000011L, 0L, "");
		
		//Click Document Office Visit Link
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
		//Search for Random Person (MID 1)
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("101");
		
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='101']")).submit();
		driver.setJavascriptEnabled(true);
		//Verify Document Office Visit page
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		//Click Yes, Document Office Visit
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//Verify Edit Office Visit page
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		//Add a new office visit
		//Enter 05/01/2016 for the Office Visit Date
		driver.findElement(By.name("visitDate")).clear();
	    driver.findElement(By.name("visitDate")).sendKeys("05/01/2016");
	    //Select Central Hospital for the hospital
	    new Select(driver.findElement(By.name("hospitalID"))).selectByVisibleText("Central Hospital");
	    //Enter "Brynn is growing into a beautiful sunflower" for Notes
	    driver.findElement(By.name("notes")).clear();
	    driver.findElement(By.name("notes")).sendKeys("Brynn is growing into a beautiful sunflower.");
	    //Click the create button
	    driver.findElement(By.id("update")).click();
		
		//Verify "Information Successfully Updated" message
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		
		//Enter Health Metrics
		//Enter 42.8 in for Height
		driver.findElement(By.name("height")).clear();
	    driver.findElement(By.name("height")).sendKeys("42.8");
	    //Enter 41.2 lbs for Weight
	    driver.findElement(By.name("weight")).clear();
	    driver.findElement(By.name("weight")).sendKeys("41.2");
	    //Enter 123/64 mmHg for Blood Pressure
	    driver.findElement(By.name("bloodPressureN")).clear();
	    driver.findElement(By.name("bloodPressureN")).sendKeys("123");
	    driver.findElement(By.name("bloodPressureD")).clear();
	    driver.findElement(By.name("bloodPressureD")).sendKeys("64");
	    //Select '1 - non-smoking household' for Household Smoking Status
	    new Select(driver.findElement(By.id("householdSmokingStatus"))).selectByVisibleText("1 - non-smoking household");
	    //Click the Add Record button
	    driver.findElement(By.id("addHR")).click();
		
		//Verify "Health information successfully updated." message
		assertTrue(driver.getPageSource().contains("Health information successfully updated."));
		
		//Click Basic Health Information link
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
		//Verify Basic Health Information page
		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp", driver.getCurrentUrl());
		
		//Verify youth health record table displays
		WebElement hrTable = driver.findElement(By.id("HealthRecordsTable"));
		List<WebElement> tableRows = hrTable.findElements(By.tagName("tr"));
		List<WebElement> row1 = tableRows.get(2).findElements(By.tagName("td"));
		//Verify the table has the provided information: Header, field descriptions, 1 row of health records
		assertEquals(3, tableRows.size());
		//Verify table contents
				
		//Row 1 youth values
		//Office visit date
		assertEquals("05/01/2016", row1.get(0).getText());
		//Patient length
		assertEquals("42.8in", row1.get(1).getText());
		//Patient weight
		assertEquals("41.2lbs", row1.get(2).getText());
		//Patient blood pressure
		assertEquals("123/64 mmHg", row1.get(4).getText());
		//Patient household smoking status
		assertEquals("Non-smoking household", row1.get(5).getText());
	}
	
	/**
	 * testHCPLoggingAction
	 * @throws Exception
	 */
	@Test
	public void testHCPLoggingAction() throws Exception{
		//Login as HCP Shelly Vang
		driver = (HtmlUnitDriver)login("8000000011", "pw");	
		assertLogged(TransactionType.HOME_VIEW, 8000000011L, 0L, "");
		
		//Click Basic Health Information link
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
		//Search for patient MID 102
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("102");
		
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='102']")).submit();
		//Verify Basic Health Information page
		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp", driver.getCurrentUrl());
		
		//Log out as Shelly Vang
		WebElement logout = driver.findElements(By.tagName("li")).get(1);
		logout.findElement(By.tagName("a")).click();
		assertEquals("iTrust - Login", driver.getTitle());
		
		//Log in as MID 102
		driver = (HtmlUnitDriver)login("102", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		//Make sure HCP action is appears in Your Activity Feed
		assertTrue(driver.getPageSource().contains("Shelly Vang"));
		assertTrue(driver.getPageSource().contains("viewed your health records history"));
	}
	
	/**
	 * testViewHealthMetricsByHCP
	 * @throws Exception
	 */
	@Test
	public void testViewHealthMetricsByHCP() throws Exception{
		//Login as HCP Shelly Vang
		driver = (HtmlUnitDriver)login("8000000011", "pw");	
		assertLogged(TransactionType.HOME_VIEW, 8000000011L, 0L, "");

		//Click Basic Health Information link
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
		//Search for Caldwell Hudson (MID 102)
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("102");
		
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='102']")).submit();
		//Verify Basic Health Information page
		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp", driver.getCurrentUrl());

		//Verify baby health record table displays
		WebElement hrTable = driver.findElement(By.id("HealthRecordsTable"));
		List<WebElement> tableRows = hrTable.findElements(By.tagName("tr"));
		List<WebElement> infantRow1 = tableRows.get(2).findElements(By.tagName("td"));
		List<WebElement> infantRow2 = tableRows.get(3).findElements(By.tagName("td"));
		List<WebElement> infantRow3 = tableRows.get(4).findElements(By.tagName("td"));
		List<WebElement> infantRow4 = tableRows.get(5).findElements(By.tagName("td"));
		List<WebElement> infantRow5 = tableRows.get(6).findElements(By.tagName("td"));
		//Verify the table has the provided information: Header, field descriptions, 3 rows of health records
		assertEquals(7, tableRows.size());

		//Verify table contents
		
		//Row 1 values
		//Office visit date
		assertEquals("10/28/2013", infantRow1.get(0).getText());
		//Patient length
		assertEquals("34.7in", infantRow1.get(1).getText());
		//Patient weight
		assertEquals("30.2lbs", infantRow1.get(2).getText());
		//Patient head circumference
		assertEquals("19.4in", infantRow1.get(4).getText());
		//Patient household smoking status
		assertEquals("Indoor smokers", infantRow1.get(5).getText());
		
		//Row 2 values
		//Office visit date
		assertEquals("02/02/2012", infantRow2.get(0).getText());
		//Patient length
		assertEquals("25.7in", infantRow2.get(1).getText());
		//Patient weight
		assertEquals("15.8lbs", infantRow2.get(2).getText());
		//Patient head circumference
		assertEquals("17.1in", infantRow2.get(4).getText());
		//Patient household smoking status
		assertEquals("Indoor smokers", infantRow2.get(5).getText());
		
		//Row 3 values
		//Office visit date
		assertEquals("12/01/2011", infantRow3.get(0).getText());
		//Patient length
		assertEquals("22.5in", infantRow3.get(1).getText());
		//Patient weight
		assertEquals("12.1lbs", infantRow3.get(2).getText());
		//Patient head circumference
		assertEquals("16.3in", infantRow3.get(4).getText());
		//Patient household smoking status
		assertEquals("Indoor smokers", infantRow3.get(5).getText());
		
		//Row 4 values
		//Office visit date
		assertEquals("11/01/2011", infantRow4.get(0).getText());
		//Patient length
		assertEquals("21.1in", infantRow4.get(1).getText());
		//Patient weight
		assertEquals("10.3lbs", infantRow4.get(2).getText());
		//Patient head circumference
		assertEquals("15.3in", infantRow4.get(4).getText());
		//Patient household smoking status
		assertEquals("Indoor smokers", infantRow4.get(5).getText());
		
		//Row 5 values
		//Office visit date
		assertEquals("10/01/2011", infantRow5.get(0).getText());
		//Patient length
		assertEquals("19.6in", infantRow5.get(1).getText());
		//Patient weight
		assertEquals("8.3lbs", infantRow5.get(2).getText());
		//Patient head circumference
		assertEquals("14.5in", infantRow5.get(4).getText());
		//Patient household smoking status
		assertEquals("Indoor smokers", infantRow5.get(5).getText());	
	}
	
	/**
	 * testOfficeVisit5YrOldViewHealthMetrics
	 * @throws Exception
	 */
	@Test
	public void testOfficeVisit5YrOldViewHealthMetrics() throws Exception{
		//Login as HCP Shelly Vang
		driver = (HtmlUnitDriver)login("8000000011", "pw");	
		assertLogged(TransactionType.HOME_VIEW, 8000000011L, 0L, "");
		
		//Click Basic Health Information link
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
		//Search for Fulton Gray (MID 103)
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("103");
		
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='103']")).submit();
		//Verify Basic Health Information page
		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp", driver.getCurrentUrl());
		
		//Verify youth health record table displays
		WebElement hrTable = driver.findElement(By.id("HealthRecordsTable"));
		List<WebElement> tableRows = hrTable.findElements(By.tagName("tr"));
		List<WebElement> infantRow1 = tableRows.get(2).findElements(By.tagName("td"));
		List<WebElement> youthRow1 = tableRows.get(5).findElements(By.tagName("td"));
		List<WebElement> youthRow2 = tableRows.get(6).findElements(By.tagName("td"));
		//Verify the table has the provided information: Header, field descriptions, 3 rows of health records
		assertEquals(7, tableRows.size());
		
		//Verify table contents
		
		//Row 1 youth values
		//Office visit date
		assertEquals("10/14/2013", youthRow1.get(0).getText());
		//Patient length
		assertEquals("42.9in", youthRow1.get(1).getText());
		//Patient weight
		assertEquals("37.9lbs", youthRow1.get(2).getText());
		//Patient blood pressure
		assertEquals("95/65 mmHg", youthRow1.get(4).getText());
		//Patient household smoking status
		assertEquals("Outdoor smokers", youthRow1.get(5).getText());
		
		//Row 2 youth values
		//Office visit date
		assertEquals("10/15/2012", youthRow2.get(0).getText());
		//Patient length
		assertEquals("41.3in", youthRow2.get(1).getText());
		//Patient weight
		assertEquals("35.8lbs", youthRow2.get(2).getText());
		//Patient blood pressure
		assertEquals("95/65 mmHg", youthRow2.get(4).getText());
		//Patient household smoking status
		assertEquals("Indoor smokers", youthRow2.get(5).getText());
		
	
		//Row 1 baby values
		//Office visit date
		assertEquals("10/01/2011", infantRow1.get(0).getText());
		//Patient length
		assertEquals("39.3in", infantRow1.get(1).getText());
		//Patient weight
		assertEquals("36.5lbs", infantRow1.get(2).getText());
		//Patient head circumference
		assertEquals("19.9in", infantRow1.get(4).getText());
		//Patient household smoking status
		assertEquals("Indoor smokers", infantRow1.get(5).getText());
	}
	
	/**
	 * testNoHealthRecordsExistByHCP
	 * @throws Exception
	 */
	@Test
	public void testNoHealthRecordsExistByHCP() throws Exception{
		//Login as HCP Shelly Vang
		driver = (HtmlUnitDriver)login("8000000011", "pw");	
		assertLogged(TransactionType.HOME_VIEW, 8000000011L, 0L, "");
		
		//Click Basic Health Information link
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Basic Health Information")).click();
		//Search for Brynn McClain (MID 101)
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("101");
		
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='101']")).submit();
		//Verify Basic Health Information page
		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp", driver.getCurrentUrl());
		
		//Verify that message displays to user
		assertTrue(driver.getPageSource().contains("No health records available"));
	}
	
	/**
	 * testNoHealthRecordsExistByPatient
	 * @throws Exception
	 */
	@Test
	public void testNoHealthRecordsExistByPatient() throws Exception{
		//Login as Patient Brynn McClain (MID 101)
		driver = (HtmlUnitDriver)login("101", "pw");	
		assertLogged(TransactionType.HOME_VIEW, 101L, 0L, "");
		
		//Click View My Records link
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("View My Records")).click();
		//Verify Basic Health Information page
		assertEquals(ADDRESS + "auth/patient/viewMyRecords.jsp", driver.getCurrentUrl());
		
		//Verify that message displays to user
		assertTrue(driver.getPageSource().contains("No health records available"));
	}
}