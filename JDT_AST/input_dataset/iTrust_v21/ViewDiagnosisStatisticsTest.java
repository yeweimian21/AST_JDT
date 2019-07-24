package edu.ncsu.csc.itrust.selenium;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Test Diagnosis Trends / Epidemics page
 */
public class ViewDiagnosisStatisticsTest extends iTrustSeleniumTest {

	private HtmlUnitDriver driver;
	/**
	 * Sets up the test. Clears the tables then adds necessary data
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.patient_hcp_vists();
		gen.hcp_diagnosis_data();
	}

	/*
	 * Authenticate PHA
	 * MID 7000000001
	 * Password: pw
	 * Choose "Diagnosis Trends"
	 * Enter Fields:
	 * ICDCode: 72.00
	 * ZipCode: 27695
	 * StartDate: 06/28/2011, EndDate: 09/28/2011
	 */
	/**
	 * testViewDiagnosisTrends_PHAView1
	 * @throws Exception
	 */
	@Test
	public void testViewDiagnosisTrends_PHAView1() throws Exception {
		driver = (HtmlUnitDriver)login("7000000001", "pw");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[2]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		WebElement elem = null;
		try {
			elem = driver.findElement(By.id("diagnosisStatisticsTable"));
			fail("Element should not be visible");
		} catch (NoSuchElementException d) {
			assertNull(elem);
		}
		//assertFalse(isElementPresent(By.id("diagnosisStatisticsTable")));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("72.00 - Mumps");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27695");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("06/28/2011");
		driver.findElement(By.name("endDate")).clear();
		driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		try {
			elem = driver.findElement(By.id("diagnosisStatisticsTable"));
			assertTrue(elem != null);
		} catch (NoSuchElementException d) {
			fail("Element should be present");
		}
		//assertTrue(isElementPresent(By.id("diagnosisStatisticsTable")));
		WebElement zip = elem.findElements(By.tagName("td")).get(2);
		/* I know this is a stupid assert. This is how it was originally */
		assertTrue(zip.getText().contains("0"));
		WebElement region = elem.findElements(By.tagName("td")).get(3);
		assertTrue(region.getText().contains("2"));
	}

	/*
	 * Authenticate PHA
	 * MID 7000000001
	 * Password: pw
	 * Choose "Epidemics"
	 * Enter Fields:
	 * Diagnosis: 84.50 Malaria
	 * ZipCode: 12345
	 * StartDate: 1/23/12
	 * Threshold: [leave blank]
	 */
	/**
	 * testViewDiagnosisTrendsEpidemic_InvalidThreshold
	 * @throws Exception
	 */
	@Test
	public void testViewDiagnosisTrendsEpidemic_InvalidThreshold()
			throws Exception {
		driver = (HtmlUnitDriver)login("7000000001", "pw");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[2]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("epidemics");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("12345");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("01/23/2012");

		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_EPIDEMICS_VIEW, 7000000001L, 0L,	"");
	}

	/*
	 * Authenticate HCP MID 9000000008 Password: pw Choose "Diagnosis Trends"
	 * Enter Fields: ICDCode: 487.00 ZipCode: 27695 StartDate: 08/28/2011,
	 * EndDate: 09/28/2011 Document new office visit Add new diagnosis (487.00)
	 * Choose "Diagnosis Trends" Enter Fields: ICDCode: 487.00 ZipCode: 27695
	 * StartDate: 08/28/2011, EndDate: 09/28/2011
	 */
	/**
	 * testViewDiagnosisTrends_LHCPObserveIncrease
	 * 
	 * @throws Exception
	 */
	@Test
	public void testViewDiagnosisTrends_LHCPObserveIncrease() throws Exception {
		driver = (HtmlUnitDriver)login("9000000008", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]"))
				.click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27695");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("08/28/2011");
		driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");

		WebElement table = driver.findElement(By.id("diagnosisStatisticsTable"));
		long local1 = Long.parseLong(table.findElements(By.tagName("td")).get(2).getText());
		long region1 = Long.parseLong(table.findElements(By.tagName("td")).get(3).getText());
		
		// Click Document Office Visit
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("Document Office Visit")).click();

		// Search and choose patient 25
		//search for patient 25 by MID
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("25");
		
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='25']")).submit();

		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp"));
		assertTrue(driver.getPageSource().contains("Click on an old office visit to modify:"));

		// Click "Yes, Document Office Visit"
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-uap/editOfficeVisit.jsp"));

		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("09/28/2011");
		driver.findElement(By.name("notes")).clear();
		driver.findElement(By.name("notes")).sendKeys("I like diet-coke");
		driver.findElement(By.id("update")).click();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000008L, 25L, "Office visit");

		new Select(driver.findElement(By.name("ICDCode"))).selectByVisibleText("487.00 - Influenza");
		driver.findElement(By.id("add_diagnosis")).click();
		assertTrue(driver.getPageSource().contains("Diagnosis information successfully updated."));

		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[2]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend again
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27606-1234");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("08/28/2011");
		driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");

		WebElement table2 = driver.findElement(By.id("diagnosisStatisticsTable"));
		long local2 = Long.parseLong(table2.findElements(By.tagName("td")).get(2).getText());
		long region2 = Long.parseLong(table2.findElements(By.tagName("td")).get(3).getText());

		assertEquals(local1 + 1, local2);
		assertEquals(region1 + 1, region2);
	}

	/*
	 * Authenticate HCP MID 9000000008 Password: pw Choose "Diagnosis Trends"
	 * Enter Fields: ICDCode: 487.00 ZipCode: 276 StartDate: 08/28/2011,
	 * EndDate: 09/28/2011
	 */
	/**
	 * testViewDiagnosisTrends_InvalidZip
	 * 
	 * @throws Exception
	 */
	public void testViewDiagnosisTrends_InvalidZip() throws Exception {
		driver = (HtmlUnitDriver)login("9000000008", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("276");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("08/28/2011");
		driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");

		assertTrue(driver.getPageSource().contains("Information not valid"));
		assertTrue(driver.getPageSource().contains("Zip Code must be 5 digits!"));
	}
	
	/*
	 * Authenticate HCP
	 * MID 9000000008
	 * Password: pw
	 * Choose "Diagnosis Trends"
	 * Enter Fields:
	 * ICDCode: 84.50
	 * ZipCode: 27519
	 * StartDate: 09/28/2011, EndDate: 08/28/2011
	 */
	/**
	 * testViewDiagnosisTrends_InvalidDates
	 * @throws Exception
	 */
	public void testViewDiagnosisTrends_InvalidDates() throws Exception {
		driver = (HtmlUnitDriver)login("9000000008", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]"))
				.click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27519");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("09/28/2011");
		driver.findElement(By.name("endDate")).sendKeys("08/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");

		assertTrue(driver.getPageSource().contains("Information not valid"));
		assertTrue(driver.getPageSource().contains("Start date must be before end date!"));		
	}
	
	/*
	 * Authenticate HCP
	 * MID 9000000008
	 * Password: pw
	 * Choose "Diagnosis Trends"
	 * Enter Fields:
	 * ICDCode: 487.00
	 * ZipCode: 27695
	 * StartDate: 08/28/2011, EndDate: 09/28/2011
	 * Enter Fields:
	 * ICDCode: 487.00
	 * ZipCode: 27606
	 * StartDate: 08/28/2011, EndDate: 09/28/2011
	 */
	/**
	 * testViewDiagnosisTrends_SameRegionCount
	 * @throws Exception
	 */
	public void testViewDiagnosisTrends_SameRegionCount() throws Exception {
		driver = (HtmlUnitDriver)login("9000000008", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
		driver.findElement(By.id("select_View")).click();
		
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27695");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("06/28/2011");
		driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");
		
		WebElement table = driver.findElement(By.id("diagnosisStatisticsTable"));
		long region1 = Long.parseLong(table.findElements(By.tagName("td")).get(3).getText());
		
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27606");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("06/28/2011");
		driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");
		WebElement table2 = driver.findElement(By.id("diagnosisStatisticsTable"));
		long region2 = Long.parseLong(table2.findElements(By.tagName("td")).get(3).getText());
		
		assertEquals(region1, region2);
	}
	
	/*
	 * Authenticate HCP
	 * MID 9000000000
	 * Password: pw
	 * Choose "Diagnosis Trends"
	 * Enter Fields:
	 * ICDCode: 84.50
	 * ZipCode: 27519
	 * StartDate: 09/28/2011, EndDate: 09/28/2011
	 */
	/**
	 * testViewDiagnosisTrends_SameDateStartEnd
	 * @throws Exception
	 */
	public void testViewDiagnosisTrends_SameDateStartEnd() throws Exception {
		driver = (HtmlUnitDriver)login("9000000008", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27519");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("09/28/2011");
		driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");

		WebElement table = driver.findElement(By.id("diagnosisStatisticsTable"));
		long local = Long.parseLong(table.findElements(By.tagName("td")).get(2).getText());
		long region = Long.parseLong(table.findElements(By.tagName("td")).get(3).getText());
		assertEquals(local, 0);
		assertEquals(region, 0);
	}
	
	/*
	 * Authenticate HCP
	 * MID 9000000008
	 * Password: pw
	 * Choose "Diagnosis Trends"
	 * Enter Fields:
	 * ICDCode: 487.00
	 * ZipCode: 27607
	 * StartDate: 08/28/2011, EndDate: 09/28/2011
	 */
	/**
	 * testViewDiagnosisTrends_RegionNotLess
	 * @throws Exception
	 */
	public void testViewDiagnosisTrends_RegionNotLess() throws Exception {
		driver = (HtmlUnitDriver)login("9000000008", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27607");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("08/28/2011");
		driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");

		WebElement table = driver.findElement(By.id("diagnosisStatisticsTable"));
		long local = Long.parseLong(table.findElements(By.tagName("td")).get(2).getText());
		long region = Long.parseLong(table.findElements(By.tagName("td")).get(3).getText());
		assertTrue(local <= region);
	}
	
	/*
	 * Authenticate HCP
	 * MID 9000000008
	 * Password: pw
	 * Choose "Diagnosis Trends"
	 * Enter Fields:
	 * ICDCode: ""
	 * ZipCode: 27695
	 * StartDate: 08/28/2011, EndDate: 09/28/2011
	 */
	/**
	 * testViewDiagnosisTrends_NoDiagnosisSelected
	 * @throws Exception
	 */
	public void testViewDiagnosisTrends_NoDiagnosisSelected() throws Exception {
		driver = (HtmlUnitDriver)login("9000000008", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
		driver.findElement(By.id("select_View")).click();
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27695");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("06/28/2011");
		driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");
		
		assertTrue(driver.getPageSource().contains("Information not valid"));
		assertTrue(driver.getPageSource().contains("ICDCode must be valid diagnosis!"));
	}
	
	/**
	 * viewDiagnosisEpidemics_NoEpidemicRecords
	 * @throws Exception
	 */
	public void testViewDiagnosisEpidemics_NoEpidemicRecords() throws Exception {
		driver = (HtmlUnitDriver)login("9000000000", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("epidemics");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("38201");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("06/02/2010");
		driver.findElement(By.name("threshold")).clear();
		driver.findElement(By.name("threshold")).sendKeys("110");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_EPIDEMICS_VIEW, 9000000000L, 0L, "");
		
		assertTrue(driver.getPageSource().contains("There is no epidemic occurring in the region."));
	}
	
	/**
	 * viewDiagnosisEpidemics_YesEpidemic
	 * @throws Exception
	 */
	public void testViewDiagnosisEpidemics_YesEpidemic() throws Exception {
		gen.influenza_epidemic();
		
		driver = (HtmlUnitDriver)login("9000000007", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("epidemics");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27607");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("11/02/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_EPIDEMICS_VIEW, 9000000007L, 0L, "");
		assertFalse(driver.getPageSource().contains("THERE IS AN EPIDEMIC OCCURRING IN THIS REGION!"));
	}
	
	/**
	 * viewDiagnosisEpidemics_NoEpidemic
	 * @throws Exception
	 */
	public void testViewDiagnosisEpidemics_NoEpidemic() throws Exception {
		gen.influenza_epidemic();
		driver = (HtmlUnitDriver)login("9000000007", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("epidemics");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27607");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("02/15/2010");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_EPIDEMICS_VIEW, 9000000007L, 0L, "");
		assertTrue(driver.getPageSource().contains("There is no epidemic occurring in the region."));
	}
}