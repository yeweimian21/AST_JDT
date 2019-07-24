package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * OfficeVisitBasicHealthTest contains http unit tests for editing a patient's basic 
 * health metrics during an office visit.
 */
public class OVBasicHealthTest extends iTrustSeleniumTest {
	
	/**
	 * This is called before each test to set up each one. It clears all the database tables
	 * and generates a new set of test data.
	 */
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	/**
	 * Create an office visit and enter health metrics for a patient that is 5 
	 * months old. This test uses patient Brynn McClain (born May 1, 2013).
	 * 
	 * @throws Exception
	 */
	public void testOfficeVisit5MonthOldHealthMetrics() throws Exception{
		//Login as HCP Shelly Vang
		WebDriver driver = login("8000000011", "pw");	
		assertLogged(TransactionType.HOME_VIEW, 8000000011L, 0L, "");
		
		//Click Document Office Visit Link
		driver.findElement(By.linkText("Document Office Visit")).click();
		//Search for patient 101
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("101");
		driver.findElement(By.xpath("//input[@value='101']")).submit();
		//Verify Document Office Visit page
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		//Click Yes, Document Office Visit
		driver.findElement(By.xpath("//input[@value='Yes, Document Office Visit']")).submit();
		//Verify Edit Office Visit page
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		//Add a new office visit
		//Enter 10/01/2013 for the Office Visit Date
		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("10/01/2013");
		//Select Central Hospital for the hospital
		Select hospital = new Select(driver.findElement(By.name("hospitalID")));
		hospital.selectByVisibleText("Central Hospital");
		//Enter "Brynn can start eating rice cereal mixed with breast milk or formula once a day" for Notes
		driver.findElement(By.name("notes")).clear();
		driver.findElement(By.name("notes")).sendKeys("Brynn can start eating rice cereal mixed with breast milk or formula once a day.");
		//Click the create button
		driver.findElement(By.xpath("//input[@value='Create']")).click();
		
		//Verify "Information Successfully Updated" message
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		
		//Enter Health Metrics
		//Enter Weight
		driver.findElement(By.name("weight")).clear();
		driver.findElement(By.name("weight")).sendKeys("16.5");
		//Enter Length
		driver.findElement(By.name("height")).clear();
		driver.findElement(By.name("height")).sendKeys("22.3");
		//Enter Head Circumference
		driver.findElement(By.name("headCircumference")).clear();
		driver.findElement(By.name("headCircumference")).sendKeys("16.1");
		//Select household smoking status
		Select smoking = new Select(driver.findElement(By.name("householdSmokingStatus")));
		smoking.selectByVisibleText("1 - non-smoking household");
		//Click the Add Record button
		driver.findElement(By.xpath("//input[@value='Add Record']")).click();
				
		//Verify "Health information successfully updated." message
		assertTrue(driver.getPageSource().contains("Health information successfully updated."));
	}
	
	/**
	 * Create an office visit and enter health metrics for a patient that is 2 
	 * years old. This test uses patient Caldwell Hudson (born September 29, 2011). This
	 * test also adds a Penicillin prescription and a Streptococcal sore throat diagnosis.
	 * 
	 * @throws Exception
	 */
	public void testOfficeVisit2YrOldHealthMetrics() throws Exception{
		//Login as HCP Shelly Vang
		WebDriver driver = login("8000000011", "pw");
		assertLogged(TransactionType.HOME_VIEW, 8000000011L, 0L, "");
		
		//Click Document Office Visit Link
		driver.findElement(By.linkText("Document Office Visit")).click();
		//Search for patient 102
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("102");
		driver.findElement(By.xpath("//input[@value='102']")).submit();
		//Verify Document Office Visit page
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		//Click Yes, Document Office Visit
		driver.findElement(By.xpath("//input[@value='Yes, Document Office Visit']")).submit();
		//Verify Edit Office Visit page
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		//Add a new office visit
		//Enter the Office Visit Date
		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("10/28/2013");
		//Select Central Hospital for the hospital
		Select hospital = new Select(driver.findElement(By.name("hospitalID")));
		hospital.selectByVisibleText("Central Hospital");
		//Enter Notes
		driver.findElement(By.name("notes")).clear();
		driver.findElement(By.name("notes")).sendKeys("Diagnosed with strep throat. Avoid contact with others for first 24 hours of antibiotics.");
		//Click the create button
		driver.findElement(By.xpath("//input[@value='Create']")).click();
				
		//Verify "Information Successfully Updated" message
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		
		//Enter Health Metrics
		//Enter Weight
		driver.findElement(By.name("weight")).clear();
		driver.findElement(By.name("weight")).sendKeys("30.2");
		//Enter Length
		driver.findElement(By.name("height")).clear();
		driver.findElement(By.name("height")).sendKeys("34.7");
		//Enter Head Circumference
		driver.findElement(By.name("headCircumference")).clear();
		driver.findElement(By.name("headCircumference")).sendKeys("19.4");
		//Select household smoking status
		Select smoking = new Select(driver.findElement(By.name("householdSmokingStatus")));
		smoking.selectByVisibleText("3 - indoor smokers");
		//Click the Add Record button
		driver.findElement(By.xpath("//input[@value='Add Record']")).click();
		
		//Verify "Health information successfully updated." message
		assertTrue(driver.getPageSource().contains("Health information successfully updated."));
		
		//Enter prescription 
		//Select Penicillin (664662530) for the Medication
		Select med = new Select(driver.findElement(By.name("medID")));
		med.selectByVisibleText("664662530 - Penicillin");
		//Enter 50 mg for the Dosage
		driver.findElement(By.name("dosage")).clear();
		driver.findElement(By.name("dosage")).sendKeys("50");
		//Enter 10/28/2013 for the Start Date
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("10/28/2013");
		//Enter 11/03/2013 for the End Date
		driver.findElement(By.name("endDate")).clear();
		driver.findElement(By.name("endDate")).sendKeys("11/03/2013");
		//Enter "Take three times a day" for the Instructions
		driver.findElement(By.name("instructions")).clear();
		driver.findElement(By.name("instructions")).sendKeys("Take three times a day");
		//Click the Add Prescription button
		driver.findElement(By.xpath("//input[@value='Add Prescription']")).click();
		
		//Verify "Prescription information successfully updated." message
		assertTrue(driver.getPageSource().contains("Prescription information successfully updated."));
		
		//Enter Diagnosis
		//Select '34.00 - Streptococcal sore throat' for the diagnosis
		Select diag = new Select(driver.findElement(By.name("ICDCode")));
		diag.selectByVisibleText("34.00 - Streptococcal sore throat");
		//Click the Add Diagnosis button
		driver.findElement(By.xpath("//input[@value='Add Diagnosis']")).click();
		
		//Verify "Diagnosis information successfully updated" message
		assertTrue(driver.getPageSource().contains("Diagnosis information successfully updated"));
	}
	
	/**
	 * Create an office visit and enter health metrics for a patient that is 5 
	 * years old. This test uses patient Fulton Gray (born October 10, 2008).
	 * 
	 * @throws Exception
	 */
	public void testOfficeVisit5YrOldHealthMetrics() throws Exception{
		//Login as HCP Shelly Vang
		WebDriver driver = login("8000000011", "pw");
		assertLogged(TransactionType.HOME_VIEW, 8000000011L, 0L, "");
		
		//Click Document Office Visit Link
		driver.findElement(By.linkText("Document Office Visit")).click();
		//Search for patient 103
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("103");
		driver.findElement(By.xpath("//input[@value='103']")).submit();
		//Verify Document Office Visit page
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		//Click Yes, Document Office Visit
		driver.findElement(By.xpath("//input[@value='Yes, Document Office Visit']")).submit();
		//Verify Edit Office Visit page
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		//Add a new office visit
		//Enter the Office Visit Date
		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("10/14/2013");
		//Select Central Hospital for the hospital
		Select hospital = new Select(driver.findElement(By.name("hospitalID")));
		hospital.selectByVisibleText("Central Hospital");
		//Enter Notes
		driver.findElement(By.name("notes")).clear();
		driver.findElement(By.name("notes")).sendKeys("Fulton has all required immunizations to start kindergarten next year.");
		//Click the create button
		driver.findElement(By.xpath("//input[@value='Create']")).click();
						
		//Verify "Information Successfully Updated" message
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		
		//Enter Health Metrics
		//Enter Weight
		driver.findElement(By.name("weight")).clear();
		driver.findElement(By.name("weight")).sendKeys("37.9");
		//Enter Length
		driver.findElement(By.name("height")).clear();
		driver.findElement(By.name("height")).sendKeys("42.9");
		//Enter Blood pressure
		driver.findElement(By.name("bloodPressureN")).clear();
		driver.findElement(By.name("bloodPressureN")).sendKeys("95");
		driver.findElement(By.name("bloodPressureD")).clear();
		driver.findElement(By.name("bloodPressureD")).sendKeys("65");
		//Select household smoking status
		Select smoking = new Select(driver.findElement(By.name("householdSmokingStatus")));
		smoking.selectByVisibleText("2 - outdoor smokers");
		//Click the Add Record button
		driver.findElement(By.cssSelector("#addHR")).click();
		assertEquals(ADDRESS + "auth/hcp-uap/editOfficeVisit.jsp#basic-health", driver.getCurrentUrl());
	}
	
	/**
	 * Create an office visit and enter health metrics for a patient that is 20 
	 * years old. This test uses patient Daria Griffin (born October 25, 1993).
	 * 
	 * @throws Exception
	 */
	public void testOfficeVisit20YrOldHealthMetrics() throws Exception{
		//Login as HCP Shelly Vang
		WebDriver driver = login("8000000011", "pw");
		assertLogged(TransactionType.HOME_VIEW, 8000000011L, 0L, "");
		
		//Click Document Office Visit Link
		driver.findElement(By.linkText("Document Office Visit")).click();
		//Search for patient 104
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("104");
		driver.findElement(By.xpath("//input[@value='104']")).submit();
		//Verify Document Office Visit page
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		//Click Yes, Document Office Visit
		driver.findElement(By.xpath("//input[@value='Yes, Document Office Visit']")).submit();
		//Verify Edit Office Visit page
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		//Add a new office visit
		//Enter the Office Visit Date
		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("10/25/2013");
		//Select Central Hospital for the hospital
		Select hospital = new Select(driver.findElement(By.name("hospitalID")));
		hospital.selectByVisibleText("Central Hospital");
		//Enter Notes
		driver.findElement(By.name("notes")).clear();
		driver.findElement(By.name("notes")).sendKeys("Patient is healthy");
		//Click the create button
		driver.findElement(By.xpath("//input[@value='Create']")).click();
						
		//Verify "Information Successfully Updated" message
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		
		//Enter Health Metrics
		//Enter Weight
		driver.findElement(By.name("weight")).clear();
		driver.findElement(By.name("weight")).sendKeys("124.3");
		//Enter Length
		driver.findElement(By.name("height")).clear();
		driver.findElement(By.name("height")).sendKeys("62.3");
		//Enter Blood pressure
		driver.findElement(By.name("bloodPressureN")).clear();
		driver.findElement(By.name("bloodPressureN")).sendKeys("110");
		driver.findElement(By.name("bloodPressureD")).clear();
		driver.findElement(By.name("bloodPressureD")).sendKeys("75");
		//Select household smoking status
		Select smoking = new Select(driver.findElement(By.name("householdSmokingStatus")));
		smoking.selectByVisibleText("1 - non-smoking household");
		//Select Patient smoking status
		Select smokingP = new Select(driver.findElement(By.name("isSmoker")));
		smokingP.selectByVisibleText("3 - Former smoker");
		//Enter cholesterol
		driver.findElement(By.name("cholesterolHDL")).clear();
		driver.findElement(By.name("cholesterolHDL")).sendKeys("65");
		driver.findElement(By.name("cholesterolLDL")).clear();
		driver.findElement(By.name("cholesterolLDL")).sendKeys("102");
		driver.findElement(By.name("cholesterolTri")).clear();
		driver.findElement(By.name("cholesterolTri")).sendKeys("147");
		//Click the Add Record button
		driver.findElement(By.xpath("//input[@value='Add Record']")).click();
		
		//Verify "Health information successfully updated." message
		assertTrue(driver.getPageSource().contains("Health information successfully updated."));
	}
	
	/**
	 * Create an office visit and enter health metrics for a patient that is 20 
	 * years old. This test uses patient Thane Ross (born January 3, 1989).
	 * 
	 * @throws Exception
	 */
	public void testOfficeVisit24YrOldHealthMetrics() throws Exception{
		//Login as HCP Shelly Vang
		WebDriver driver = login("8000000011", "pw");
		assertLogged(TransactionType.HOME_VIEW, 8000000011L, 0L, "");
		
		//Click Document Office Visit Link
		driver.findElement(By.linkText("Document Office Visit")).click();
		//Search for patient 105
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("105");
		driver.findElement(By.xpath("//input[@value='105']")).submit();
		//Verify Document Office Visit page
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		//Click Yes, Document Office Visit
		driver.findElement(By.xpath("//input[@value='Yes, Document Office Visit']")).submit();
		//Verify Edit Office Visit page
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		//Add a new office visit
		//Enter the Office Visit Date
		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("10/25/2013");
		//Select Central Hospital for the hospital
		Select hospital = new Select(driver.findElement(By.name("hospitalID")));
		hospital.selectByVisibleText("Central Hospital");
		//Enter Notes
		driver.findElement(By.name("notes")).clear();
		driver.findElement(By.name("notes")).sendKeys("Thane should consider modifying diet and exercise to avoid future heart disease");
		//Click the create button
		driver.findElement(By.xpath("//input[@value='Create']")).click();
								
		//Verify "Information Successfully Updated" message
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		
		//Enter Health Metrics
		//Enter Weight
		driver.findElement(By.name("weight")).clear();
		driver.findElement(By.name("weight")).sendKeys("210.1");
		//Enter Length
		driver.findElement(By.name("height")).clear();
		driver.findElement(By.name("height")).sendKeys("73.1");
		//Enter Blood pressure
		driver.findElement(By.name("bloodPressureN")).clear();
		driver.findElement(By.name("bloodPressureN")).sendKeys("160");
		driver.findElement(By.name("bloodPressureD")).clear();
		driver.findElement(By.name("bloodPressureD")).sendKeys("100");
		//Select household smoking status
		Select smoking = new Select(driver.findElement(By.name("householdSmokingStatus")));
		smoking.selectByVisibleText("1 - non-smoking household");
		//Select Patient smoking status
		Select smokingP = new Select(driver.findElement(By.name("isSmoker")));
		smokingP.selectByVisibleText("4 - Never smoker");
		//Enter cholesterol
		driver.findElement(By.name("cholesterolHDL")).clear();
		driver.findElement(By.name("cholesterolHDL")).sendKeys("37");
		driver.findElement(By.name("cholesterolLDL")).clear();
		driver.findElement(By.name("cholesterolLDL")).sendKeys("141");
		driver.findElement(By.name("cholesterolTri")).clear();
		driver.findElement(By.name("cholesterolTri")).sendKeys("162");
		//Click the Add Record button
		driver.findElement(By.xpath("//input[@value='Add Record']")).click();
		
		//Verify "Health information successfully updated." message
		assertTrue(driver.getPageSource().contains("Health information successfully updated."));
	}
	
	
	
	/**
	 * Create an office visit and enter health metrics for a patient that is >20 
	 * years old. This test uses patient Random Person and no patient smoking status
	 * is entered into the Health Metrics. An invalid error message is displayed.
	 * @throws Exception
	 */
	public void testNoPatientSmokingStatusSpecified() throws Exception{
		//Login as HCP Kelly Doctor
		WebDriver driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Click Document Office Visit Link
		driver.findElement(By.linkText("Document Office Visit")).click();
		//Search for patient 1
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		driver.findElement(By.xpath("//input[@value='1']")).submit();
		//Verify Document Office Visit page
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		//Click Yes, Document Office Visit
		driver.findElement(By.xpath("//input[@value='Yes, Document Office Visit']")).submit();
		//Verify Edit Office Visit page
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		//Add a new office visit
		//Enter the Office Visit Date
		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("12/25/2013");
		//Select Central Hospital for the hospital
		Select hospital = new Select(driver.findElement(By.name("hospitalID")));
		hospital.selectByVisibleText("Central Hospital");
		//Enter Notes
		driver.findElement(By.name("notes")).clear();
		driver.findElement(By.name("notes")).sendKeys("Came in complaining of splinters");
		//Click the create button
		driver.findElement(By.xpath("//input[@value='Create']")).click();
										
		//Verify "Information Successfully Updated" message
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		
		//Enter Health Metrics
		//Enter Weight
		driver.findElement(By.name("weight")).clear();
		driver.findElement(By.name("weight")).sendKeys("500");
		//Enter Length
		driver.findElement(By.name("height")).clear();
		driver.findElement(By.name("height")).sendKeys("480.0");
		//Enter Blood pressure
		driver.findElement(By.name("bloodPressureN")).clear();
		driver.findElement(By.name("bloodPressureN")).sendKeys("180");
		driver.findElement(By.name("bloodPressureD")).clear();
		driver.findElement(By.name("bloodPressureD")).sendKeys("110");
		//Select household smoking status
		Select smoking = new Select(driver.findElement(By.name("householdSmokingStatus")));
		smoking.selectByVisibleText("1 - non-smoking household");
		//Enter cholesterol
		driver.findElement(By.name("cholesterolHDL")).clear();
		driver.findElement(By.name("cholesterolHDL")).sendKeys("40");
		driver.findElement(By.name("cholesterolLDL")).clear();
		driver.findElement(By.name("cholesterolLDL")).sendKeys("190");
		driver.findElement(By.name("cholesterolTri")).clear();
		driver.findElement(By.name("cholesterolTri")).sendKeys("500");
		//Click the Add Record button
		driver.findElement(By.xpath("//input[@value='Add Record']")).click();
		driver.findElement(By.xpath("//input[@value='Add Record']")).submit();
		
		//Verify "Information not valid" message
		//Verify "Smoker must be an integer in [0,10]" message
		assertTrue(driver.getPageSource().contains("Information not valid"));
		assertTrue(driver.getPageSource().contains("Smoker must be an integer in [0,10]"));
	}
	
	/**
	 * Create an office visit and enter health metrics for a patient that is >20 
	 * years old. This test uses patient Random Person and no patient high blood pressure
	 * is entered into the Health Metrics. An invalid error message is displayed. 
	 * @throws Exception
	 */
	public void testNoHighBloodPressure() throws Exception{
		//Login as HCP Kelly Doctor
		WebDriver driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Click Document Office Visit Link
		driver.findElement(By.linkText("Document Office Visit")).click();
		//Search for patient 1
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		driver.findElement(By.xpath("//input[@value='1']")).submit();
		//Verify Document Office Visit page
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		//Click Yes, Document Office Visit
		driver.findElement(By.xpath("//input[@value='Yes, Document Office Visit']")).submit();
		//Verify Edit Office Visit page
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		//Add a new office visit
		//Enter the Office Visit Date
		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("12/01/2013");
		//Select Central Hospital for the hospital
		Select hospital = new Select(driver.findElement(By.name("hospitalID")));
		hospital.selectByVisibleText("Central Hospital");
		//Enter Notes
		driver.findElement(By.name("notes")).clear();
		driver.findElement(By.name("notes")).sendKeys("It doesn't matter");
		//Click the create button
		driver.findElement(By.xpath("//input[@value='Create']")).click();
		
		//Verify "Information Successfully Updated" message
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		
		//Enter Health Metrics
		//Enter Weight
		driver.findElement(By.name("weight")).clear();
		driver.findElement(By.name("weight")).sendKeys("160");
		//Enter Length
		driver.findElement(By.name("height")).clear();
		driver.findElement(By.name("height")).sendKeys("55.5");
		//Enter Blood pressure
		driver.findElement(By.name("bloodPressureN")).clear();
		driver.findElement(By.name("bloodPressureN")).sendKeys("");
		driver.findElement(By.name("bloodPressureD")).clear();
		driver.findElement(By.name("bloodPressureD")).sendKeys("70");
		//Select household smoking status
		Select smoking = new Select(driver.findElement(By.name("householdSmokingStatus")));
		smoking.selectByVisibleText("1 - non-smoking household");
		//Select Patient smoking status
		Select smokingP = new Select(driver.findElement(By.name("isSmoker")));
		smokingP.selectByVisibleText("4 - Never smoker");
		//Enter cholesterol
		driver.findElement(By.name("cholesterolHDL")).clear();
		driver.findElement(By.name("cholesterolHDL")).sendKeys("50");
		driver.findElement(By.name("cholesterolLDL")).clear();
		driver.findElement(By.name("cholesterolLDL")).sendKeys("100");
		driver.findElement(By.name("cholesterolTri")).clear();
		driver.findElement(By.name("cholesterolTri")).sendKeys("345");
		//Click the Add Record button
		driver.findElement(By.xpath("//input[@value='Add Record']")).click();
		
		//Verify "Information not valid" message
		//Verify "Systolic blood pressure must be an integer in [0,999]" message
		assertTrue(driver.getPageSource().contains("Information not valid"));
		assertTrue(driver.getPageSource().contains("Systolic blood pressure must be an integer in [0,999]"));
	}
	
	/**
	 * Create an office visit and enter health metrics for a patient that is >20 
	 * years old. This test uses patient Random Person and invalid characters are
	 * entered to measure HDL cholesterol into the Health Metrics. An invalid 
	 * error message is displayed. 
	 * @throws Exception
	 */
	public void testInvalidCharactersForHDL() throws Exception{
		//Login as HCP Kelly Doctor
		WebDriver driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Click Document Office Visit Link
		driver.findElement(By.linkText("Document Office Visit")).click();
		//Search for patient 1
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		driver.findElement(By.xpath("//input[@value='1']")).submit();
		//Verify Document Office Visit page
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		//Click Yes, Document Office Visit
		driver.findElement(By.xpath("//input[@value='Yes, Document Office Visit']")).submit();
		//Verify Edit Office Visit page
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		//Add a new office visit
		//Enter the Office Visit Date
		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("10/31/2013");
		//Select Central Hospital for the hospital
		Select hospital = new Select(driver.findElement(By.name("hospitalID")));
		hospital.selectByVisibleText("Central Hospital");
		//Enter Notes
		driver.findElement(By.name("notes")).clear();
		driver.findElement(By.name("notes")).sendKeys("Testing Invalid Input");
		//Click the create button
		driver.findElement(By.xpath("//input[@value='Create']")).click();
												
		//Verify "Information Successfully Updated" message
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		
		//Enter Health Metrics
		//Enter Weight
		driver.findElement(By.name("weight")).clear();
		driver.findElement(By.name("weight")).sendKeys("150");
		//Enter Length
		driver.findElement(By.name("height")).clear();
		driver.findElement(By.name("height")).sendKeys("55.5");
		//Enter Blood pressure
		driver.findElement(By.name("bloodPressureN")).clear();
		driver.findElement(By.name("bloodPressureN")).sendKeys("120");
		driver.findElement(By.name("bloodPressureD")).clear();
		driver.findElement(By.name("bloodPressureD")).sendKeys("70");
		//Select household smoking status
		Select smoking = new Select(driver.findElement(By.name("householdSmokingStatus")));
		smoking.selectByVisibleText("1 - non-smoking household");
		//Select Patient smoking status
		Select smokingP = new Select(driver.findElement(By.name("isSmoker")));
		smokingP.selectByVisibleText("4 - Never smoker");
		//Enter cholesterol
		driver.findElement(By.name("cholesterolHDL")).clear();
		driver.findElement(By.name("cholesterolHDL")).sendKeys("AA");
		driver.findElement(By.name("cholesterolLDL")).clear();
		driver.findElement(By.name("cholesterolLDL")).sendKeys("101");
		driver.findElement(By.name("cholesterolTri")).clear();
		driver.findElement(By.name("cholesterolTri")).sendKeys("222");
		//Click the Add Record button
		driver.findElement(By.xpath("//input[@value='Add Record']")).click();
		
		//Verify "Information not valid" message
		//Verify "Cholesterol HDL must be an integer in [0,89]" message
		assertTrue(driver.getPageSource().contains("Information not valid"));
		assertTrue(driver.getPageSource().contains("Cholesterol HDL must be an integer in [0,89]"));
	}
	
	
	/**
	 * Create an office visit for a patient that is >20 years old. This test
	 * uses patient Random Person. The date of the Office Visit is set to an
	 * invalid date (Leap Day of 2014) and the date is automatically adjusted
	 * to account for this. 
	 * @throws Exception
	 */
	public void testLeapDay2014() throws Exception{
		//Login as HCP Kelly Doctor
		WebDriver driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Click Document Office Visit Link
		driver.findElement(By.linkText("Document Office Visit")).click();
		//Search for patient 1
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		driver.findElement(By.xpath("//input[@value='1']")).submit();
		//Verify Document Office Visit page
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		//Click Yes, Document Office Visit
		driver.findElement(By.xpath("//input[@value='Yes, Document Office Visit']")).submit();
		//Verify Edit Office Visit page
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		//Add a new office visit
		//Enter the Office Visit Date
		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("02/29/2014");
		//Select Central Hospital for the hospital
		Select hospital = new Select(driver.findElement(By.name("hospitalID")));
		hospital.selectByVisibleText("Central Hospital");
		//Enter Notes
		driver.findElement(By.name("notes")).clear();
		driver.findElement(By.name("notes")).sendKeys("Testing invalid leap year");
		//Click the create button
		driver.findElement(By.xpath("//input[@value='Create']")).click();
												
		//Verify "Information Successfully Updated" message
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		
		//Verify that date changed for to March 1st, 2014.
		assertEquals(driver.findElement(By.name("visitDate")).getAttribute(VALUE), "03/01/2014");
	}
	
	/**
	 * Create an office visit and enter health metrics for a patient that is under 3 
	 * years old. This test uses patient Brynn McClain. No head circumference is entered
	 * and an invalid message is displayed.
	 * @throws Exception
	 */
	public void testZeroHeadCircumferenceForUnderThreeYearOld() throws Exception{
		//Login as HCP Kelly Doctor
		WebDriver driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Click Document Office Visit Link
		driver.findElement(By.linkText("Document Office Visit")).click();
		//Search for patient 101
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("101");
		driver.findElement(By.xpath("//input[@value='101']")).submit();
		//Verify Document Office Visit page
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		//Click Yes, Document Office Visit
		driver.findElement(By.xpath("//input[@value='Yes, Document Office Visit']")).submit();
		//Verify Edit Office Visit page
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		//Add a new office visit
		//Enter the Office Visit Date
		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("10/26/2013");
		//Select Central Hospital for the hospital
		Select hospital = new Select(driver.findElement(By.name("hospitalID")));
		hospital.selectByVisibleText("Central Hospital");
		//Enter Notes
		driver.findElement(By.name("notes")).clear();
		driver.findElement(By.name("notes")).sendKeys("Brynn is a very healthy baby.");
		//Click the create button
		driver.findElement(By.xpath("//input[@value='Create']")).click();
												
		//Verify "Information Successfully Updated" message
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		
		//Enter Health Metrics
		//Enter Weight
		driver.findElement(By.name("weight")).clear();
		driver.findElement(By.name("weight")).sendKeys("90");
		//Enter Length
		driver.findElement(By.name("height")).clear();
		driver.findElement(By.name("height")).sendKeys("36.0");
		//Enter Head Circumference
		driver.findElement(By.name("headCircumference")).clear();
		driver.findElement(By.name("headCircumference")).sendKeys("0");
		//Select household smoking status
		Select smoking = new Select(driver.findElement(By.name("householdSmokingStatus")));
		smoking.selectByVisibleText("1 - non-smoking household");
		//Click the Add Record button
		driver.findElement(By.xpath("//input[@value='Add Record']")).click();
		driver.findElement(By.xpath("//input[@value='Add Record']")).submit();
		
		//Verify "Information not valid" message
		//Verify "Head Circumference must be greater than 0" message
		assertTrue(driver.getPageSource().contains("Information not valid"));
		assertTrue(driver.getPageSource().contains("Head Circumference must be greater than 0"));
		

	}
}