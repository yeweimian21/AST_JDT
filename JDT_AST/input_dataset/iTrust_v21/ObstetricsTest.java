package edu.ncsu.csc.itrust.selenium;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;
public class ObstetricsTest extends iTrustSeleniumTest{

	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.uc64();
		gen.hcp0();
		gen.patient21();
		gen.patient22();
	}
	/**
	 * test adding an initial record for a patient w/ no prior pregs
	 * @throws Exception
	 */
	public void testAddPatientNoPriors() throws Exception{

		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();
		//find patient
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("400");
		wd.findElement(By.xpath("//input[@value='400']")).submit();
		String title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);

		wd.setJavascriptEnabled(true);
		//click initialize
		List<WebElement> inputs = wd.findElements(By.tagName("input"));
		inputs.get(0).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Initialize Obstetrics Record", title);
		
		//fill form and submit
		WebElement lmp = wd.findElement(By.id("lmp"));
		lmp.clear();
		lmp.sendKeys("7/1/2014");
		WebElement date = wd.findElement(By.id("date"));
		date.clear();
		date.sendKeys("9/23/2014");
		WebElement ss = wd.findElement(By.id("submit"));
		ss.click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//success?
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Record successfully added"));
		assertLogged(TransactionType.CREATE_INITIAL_OBSTETRICS_RECORD, 9000000012L, 400L, "");
		
		
	}
	
	/**
	 * test adding an initial record for a patient w/ 2 prior pregs
	 * @throws Exception
	 */
	public void testAddPatient2Priors() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();
		//find patient
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("401");
		wd.findElement(By.xpath("//input[@value='401']")).submit();
		String title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("2012-Completed"));
		assertTrue(wd.getPageSource().contains("2010-Completed"));
		assertTrue(wd.getPageSource().contains("addInitialButtonForm"));
		
		wd.setJavascriptEnabled(true);
		//click initialize
		List<WebElement> inputs = wd.findElements(By.tagName("input"));
		inputs.get(0).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Initialize Obstetrics Record", title);
		
		//enter Data
		WebElement lmp = wd.findElement(By.id("lmp"));
		lmp.clear();
		lmp.sendKeys("7/1/2014");
		WebElement date = wd.findElement(By.id("date"));
		date.clear();
		date.sendKeys("9/23/2014");
		//submit
		WebElement ss = wd.findElement(By.id("submit"));
		ss.click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//check success
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Record successfully added"));
		assertLogged(TransactionType.CREATE_INITIAL_OBSTETRICS_RECORD, 9000000012L, 401L, "");
		
	}

	public void testAddPatientEnterPrior() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();
		
		//find patient
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("402");
		wd.findElement(By.xpath("//input[@value='402']")).submit();
		String title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		
		wd.setJavascriptEnabled(true);
		
		//click initialize
		List<WebElement> input = wd.findElements(By.tagName("input"));
		input.get(0).click();
		title = wd.getTitle();
		assertEquals("iTrust - Initialize Obstetrics Record", title);
		
		//enter Data
		WebElement lmp = wd.findElement(By.id("lmp"));
		lmp.clear();
		lmp.sendKeys("8/24/2014");
		WebElement date = wd.findElement(By.id("date"));
		date.clear();
		date.sendKeys("10/4/2014");
		
		//prior preg form
		wd.findElement(By.id("priorPregnancy")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		assertTrue(wd.getPageSource().contains("Prior Pregnancy 1"));
		assertTrue(wd.getPageSource().contains("id=\"deliveryType0\""));
		
		WebElement yearConception = wd.findElement(By.id("yearConception0"));
		yearConception.clear();
		yearConception.sendKeys("2011");
		WebElement weeksPregnant = wd.findElement(By.id("weeksPregnant0"));
		weeksPregnant.clear();
		weeksPregnant.sendKeys("30");
		WebElement daysPregnant = wd.findElement(By.id("daysPregnant0"));
		daysPregnant.clear();
		daysPregnant.sendKeys("1");
		WebElement hoursInLabor = wd.findElement(By.id("hoursInLabor0"));
		hoursInLabor.clear();
		hoursInLabor.sendKeys("25");
		WebElement drop = wd.findElement(By.id("deliveryType0"));
		Select deliveryType = new Select(drop);
		//List<WebElement> ops = deliveryType.getOptions();
		deliveryType.selectByVisibleText("Miscarriage");
		
		WebElement ss = wd.findElement(By.id("submit"));
		
		ss.click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Record successfully added"));
		assertLogged(TransactionType.CREATE_INITIAL_OBSTETRICS_RECORD, 9000000012L, 402L, "");
		
		assertTrue(wd.getPageSource().contains("2011-Completed"));
		
		
	}

	public void testAddPatientMale() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();
		

		//find patient
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("403");
		wd.findElement(By.xpath("//input[@value='403']")).submit();
		assertTrue(wd.getPageSource().contains("ITrustException: The patient is not eligible for obstetrics care."));
	}
	
	public void testViewPatientNonObstetricsHCP() throws Exception {
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();
		
		//find patient
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("401");
		wd.findElement(By.xpath("//input[@value='401']")).submit();
		String title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertFalse(wd.getPageSource().contains("addInitialButtonForm"));
		wd.findElement(By.linkText("10/14/2014-Initial")).click();
		title = wd.getTitle();
		assertEquals("iTrust - View Obstetrics Record",title);
	}
	
	public void testAddNonExistentPatient() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String title = wd.getTitle();
		assertEquals("iTrust - Please Select a Patient", title);

		wd.findElement(By.name("UID_PATIENTID")).sendKeys("222");
		wd.findElement(By.xpath("//input[@value='222']")).submit();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertTrue(!title.equals("iTrust - Obstetrics"));
	}
	
	public void testAddPatientByID() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();

		//find patient
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("404");
		wd.findElement(By.xpath("//input[@value='404']")).submit();
		String title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		wd.setJavascriptEnabled(true);
		
		//click initialize
		List<WebElement> inputs = wd.findElements(By.tagName("input"));
		inputs.get(0).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Initialize Obstetrics Record", title);
		WebElement lmp = wd.findElement(By.id("lmp"));
		lmp.clear();
		lmp.sendKeys("03/14/2013");
		WebElement date = wd.findElement(By.id("date"));
		date.clear();
		date.sendKeys("03/22/2013");
		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Record successfully added"));
		assertLogged(TransactionType.CREATE_INITIAL_OBSTETRICS_RECORD, 9000000012L, 404L, "");
		
		
		
				

	}
	
	public void testViewPatientObstetricsHCP() throws Exception {
		testAddPatientByID();
		// login HCP Harry Potter
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000013", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000013L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();
		//find patient
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("404");
		wd.findElement(By.xpath("//input[@value='404']")).submit();
		String title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		wd.findElement(By.linkText("03/22/2013-Initial")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - View Obstetrics Record", title);

		assertLogged(TransactionType.VIEW_INITIAL_OBSTETRICS_RECORD, 9000000013L, 404L, "");
	}
	
	public void testAddPatientFutureLMP() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();


		//find patient
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("405");
		wd.findElement(By.xpath("//input[@value='405']")).submit();
		
		String title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("No prior records"));
		
		wd.findElement(By.id("addInitialButtonForm")).submit();
		title = wd.getTitle();
		assertEquals("iTrust - Initialize Obstetrics Record", title);
		
		WebElement lmp = wd.findElement(By.id("lmp"));
		lmp.clear();
		lmp.sendKeys("05/14/2045");
		WebElement date = wd.findElement(By.id("date"));
		date.clear();
		date.sendKeys("10/04/2014");
		WebElement ss = wd.findElement(By.id("submit"));
		ss.submit();
		assertTrue(wd.getPageSource().contains("Last menstrual period cannot be after Date of visit"));
	}
	
	public void testAddPatientChangeMind() throws Exception{
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();
		
		//find patient
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("406");
		wd.findElement(By.xpath("//input[@value='406']")).submit();
		
		String title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("No prior records"));
		
		wd.findElement(By.id("addInitialButtonForm")).submit();
		title = wd.getTitle();
		assertEquals("iTrust - Initialize Obstetrics Record", title);
		wd.findElement(By.linkText("Back to Home")).click();
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
	}

	public void testAddObstetricsOV() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();

		//find patient
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("400");
		wd.findElement(By.xpath("//input[@value='400']")).submit();
		String title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		//enable js and go to add office vist
		wd.setJavascriptEnabled(true);
		wd.findElement(By.linkText("Add Obstetrics Office Visit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Add Obstetrics Office Visit", title);
		//fill in form and create
		wd.findElement(By.id("date")).clear();
		wd.findElement(By.id("date")).sendKeys("10/07/2014");
		wd.findElement(By.id("weight")).clear();
		wd.findElement(By.id("weight")).sendKeys("137");
		wd.findElement(By.id("bloodPressureS")).clear();
		wd.findElement(By.id("bloodPressureS")).sendKeys("103");
		wd.findElement(By.id("bloodPressureD")).clear();
		wd.findElement(By.id("bloodPressureD")).sendKeys("62");
		wd.findElement(By.id("fhr")).clear();
		wd.findElement(By.id("fhr")).sendKeys("152");
		wd.findElement(By.id("fhu")).clear();
		wd.findElement(By.id("fhu")).sendKeys("14");
		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Office Visit successfully added"));
		assertLogged(TransactionType.CREATE_OBSTETRICS_OV, 9000000012L, 400L, "");
	}
	
	public void testAddEditObstetricsOV() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();
		
		//find patient
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("401");
		wd.findElement(By.xpath("//input[@value='401']")).submit();
		String title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		wd.setJavascriptEnabled(true);
		wd.findElement(By.linkText("Add Obstetrics Office Visit")).click();
		title = wd.getTitle();
		assertEquals("iTrust - Add Obstetrics Office Visit", title);
		//WebElement test = wd.findElement(By.id("lmp"));
		wd.findElement(By.id("date")).clear();
		wd.findElement(By.id("date")).sendKeys("11/25/2014");
		wd.findElement(By.id("weight")).clear();
		wd.findElement(By.id("weight")).sendKeys("147.2");
		wd.findElement(By.id("bloodPressureS")).clear();
		wd.findElement(By.id("bloodPressureS")).sendKeys("104");
		wd.findElement(By.id("bloodPressureD")).clear();
		wd.findElement(By.id("bloodPressureD")).sendKeys("58");
		wd.findElement(By.id("fhr")).clear();
		wd.findElement(By.id("fhr")).sendKeys("143");
		wd.findElement(By.id("fhu")).clear();
		wd.findElement(By.id("fhu")).sendKeys("17");
		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Office Visit successfully added"));
		assertLogged(TransactionType.CREATE_OBSTETRICS_OV, 9000000012L, 401L, "");
		
		wd.findElement(By.linkText("11/25/2014-Office Visit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wd.findElement(By.id("fhu")).clear();
		wd.findElement(By.id("fhu")).sendKeys("18");
		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Office Visit successfully edited"));
		assertLogged(TransactionType.EDIT_OBSTETRICS_OV, 9000000012L, 401L, "");
		
		
	}
	
	public void testAddObstetricsNonObHCP() throws Exception {
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		wd.findElement(By.linkText("Add Obstetrics Office Visit")).click();
		
		//find patient
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("21");
		wd.findElement(By.xpath("//input[@value='21']")).submit();
		String title = wd.getTitle();
		assertEquals("iTrust - Document Office Visit", title);
	}

	public void testAddObstetricsOVPregnancyOver() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Add Obstetrics Office Visit")).click();
		
		//find patient
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("404");
		wd.findElement(By.xpath("//input[@value='404']")).submit();
		String title = wd.getTitle();
		assertEquals("iTrust - Add Obstetrics Office Visit", title);

		wd.findElement(By.id("date")).clear();
		wd.findElement(By.id("date")).sendKeys("03/01/2014");
		wd.findElement(By.id("weight")).clear();
		wd.findElement(By.id("weight")).sendKeys("145.6");
		wd.findElement(By.id("bloodPressureS")).clear();
		wd.findElement(By.id("bloodPressureS")).sendKeys("101");
		wd.findElement(By.id("bloodPressureD")).clear();
		wd.findElement(By.id("bloodPressureD")).sendKeys("60");
		wd.findElement(By.id("fhr")).clear();
		wd.findElement(By.id("fhr")).sendKeys("158");
		wd.findElement(By.id("fhu")).clear();
		wd.findElement(By.id("fhu")).sendKeys("24");
		wd.findElement(By.id("submit")).submit();
		
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("The patient chosen is not a current obstetrics patient"));
	}

	public void testAddObstetricsOVNotInitialized() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Add Obstetrics Office Visit")).click();

		wd.findElement(By.name("UID_PATIENTID")).sendKeys("21");
		wd.findElement(By.xpath("//input[@value='21']")).submit();	
		
		String title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("The patient chosen is not a current obstetrics patient"));
	}
	/**
	 * initialize patient
	 * Verify twins not set in initial record
	 * add office visit without twins checked
	 * verify twins not set
	 * edit office visit, check twins
	 * verify twins set
	 * edit office visit, uncheck twins
	 * verify twins not set
	 * @throws Exception
	 */
	public void testAllTwinsFlag() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		wd.setJavascriptEnabled(false);
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();
		
		
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("21");
		wd.findElement(By.xpath("//input[@value='21']")).submit();
		wd.setJavascriptEnabled(true);

		String title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		
		List<WebElement> inputs = wd.findElements(By.tagName("input"));
		inputs.get(0).click();
		title = wd.getTitle();
		assertEquals("iTrust - Initialize Obstetrics Record", title);
		
		wd.findElement(By.id("lmp")).clear();
		wd.findElement(By.id("lmp")).sendKeys("10/14/2014");
		wd.findElement(By.id("date")).clear();
		wd.findElement(By.id("date")).sendKeys("10/29/2014");
		
		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Record successfully added"));
		
		wd.findElement(By.linkText("10/29/2014-Initial")).click();
		assertTrue(!wd.getPageSource().contains("checked"));

		wd.findElement(By.linkText("Add Obstetrics Office Visit")).click();
		title = wd.getTitle();
		assertEquals("iTrust - Add Obstetrics Office Visit", title);

		wd.findElement(By.id("date")).clear();
		wd.findElement(By.id("date")).sendKeys("11/05/2014");
		wd.findElement(By.id("weight")).clear();
		wd.findElement(By.id("weight")).sendKeys("140.2");
		wd.findElement(By.id("bloodPressureS")).clear();
		wd.findElement(By.id("bloodPressureS")).sendKeys("104");
		wd.findElement(By.id("bloodPressureD")).clear();
		wd.findElement(By.id("bloodPressureD")).sendKeys("58");
		wd.findElement(By.id("fhr")).clear();
		wd.findElement(By.id("fhr")).sendKeys("143");
		wd.findElement(By.id("fhu")).clear();
		wd.findElement(By.id("fhu")).sendKeys("17");
		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Office Visit successfully added"));
		
		wd.findElement(By.linkText("10/29/2014-Initial")).click();
		assertTrue(!wd.getPageSource().contains("checked"));
		wd.findElement(By.linkText("Back to Home")).click();
		
		wd.findElement(By.linkText("11/05/2014-Office Visit")).click();
		wd.findElement(By.id("twins")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Office Visit successfully edited"));

		wd.findElement(By.linkText("10/29/2014-Initial")).click();
		assertTrue(wd.getPageSource().contains("checked"));
		wd.findElement(By.linkText("Back to Home")).click();

		wd.findElement(By.linkText("11/05/2014-Office Visit")).click();
		wd.findElement(By.id("twins")).click();
		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Office Visit successfully edited"));
		
		wd.findElement(By.linkText("10/29/2014-Initial")).click();
		assertTrue(!wd.getPageSource().contains("checked"));
	}
	/**
	 * initialize patient
	 * verify placenta flag not set
	 * add office visit, without placenta checked
	 * verify placenta flag not set
	 * edit office visit, check placenta
	 * verify placenta flag set
	 * edit office visit, uncheck placenta
	 * verify placenta flag not set
	 * @throws Exception
	 */
	public void testAllPlacentaFlag() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();
		
		
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("21");
		wd.findElement(By.xpath("//input[@value='21']")).submit();	

		String title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		wd.setJavascriptEnabled(true);
		List<WebElement> inputs = wd.findElements(By.tagName("input"));
		inputs.get(0).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Initialize Obstetrics Record", title);
		
		wd.findElement(By.id("lmp")).clear();
		wd.findElement(By.id("lmp")).sendKeys("10/14/2014");
		wd.findElement(By.id("date")).clear();
		wd.findElement(By.id("date")).sendKeys("10/29/2014");
		
		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Record successfully added"));
		assertTrue(!wd.getPageSource().contains("Low-Lying Placenta"));
		wd.findElement(By.linkText("Add Obstetrics Office Visit")).click();
		title = wd.getTitle();
		assertEquals("iTrust - Add Obstetrics Office Visit", title);
		wd.setJavascriptEnabled(true);

		wd.findElement(By.id("date")).clear();
		wd.findElement(By.id("date")).sendKeys("11/05/2014");
		wd.findElement(By.id("weight")).clear();
		wd.findElement(By.id("weight")).sendKeys("140.2");
		wd.findElement(By.id("bloodPressureS")).clear();
		wd.findElement(By.id("bloodPressureS")).sendKeys("104");
		wd.findElement(By.id("bloodPressureD")).clear();
		wd.findElement(By.id("bloodPressureD")).sendKeys("58");
		wd.findElement(By.id("fhr")).clear();
		wd.findElement(By.id("fhr")).sendKeys("143");
		wd.findElement(By.id("fhu")).clear();
		wd.findElement(By.id("fhu")).sendKeys("17");
		wd.findElement(By.id("submit")).click();
		
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Office Visit successfully added"));
		
		assertTrue(!wd.getPageSource().contains("Low-Lying Placenta"));
		
		wd.findElement(By.linkText("11/05/2014-Office Visit")).click();
		wd.findElement(By.id("placenta")).click();
		wd.findElement(By.id("submit")).click();
		
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Office Visit successfully edited"));

		assertTrue(wd.getPageSource().contains("Low-Lying Placenta"));
		wd.findElement(By.linkText("11/05/2014-Office Visit")).click();
		wd.findElement(By.id("placenta")).click();
		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Office Visit successfully edited"));

		assertTrue(!wd.getPageSource().contains("Low-Lying Placenta"));
	}
	
	public void testDocumentObOVAgeHBPWeight() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();

		wd.findElement(By.name("UID_PATIENTID")).sendKeys("401");
		wd.findElement(By.xpath("//input[@value='401']")).submit();	
		
		String title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		wd.setJavascriptEnabled(true);

		List<WebElement> inputs = wd.findElements(By.tagName("input"));
		inputs.get(0).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		title = wd.getTitle();
		assertEquals("iTrust - Initialize Obstetrics Record", title);
		
		
		wd.findElement(By.id("lmp")).clear();
		wd.findElement(By.id("lmp")).sendKeys("10/05/2014");
		wd.findElement(By.id("date")).clear();
		wd.findElement(By.id("date")).sendKeys("10/29/2014");

		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Record successfully added"));
		
		wd.findElement(By.linkText("Add Obstetrics Office Visit")).click();
		title = wd.getTitle();
		assertEquals("iTrust - Add Obstetrics Office Visit", title);
		
		wd.findElement(By.id("date")).clear();
		wd.findElement(By.id("date")).sendKeys("11/05/2014");
		wd.findElement(By.id("weight")).clear();
		wd.findElement(By.id("weight")).sendKeys("125");
		wd.findElement(By.id("bloodPressureS")).clear();
		wd.findElement(By.id("bloodPressureS")).sendKeys("100");
		wd.findElement(By.id("bloodPressureD")).clear();
		wd.findElement(By.id("bloodPressureD")).sendKeys("91");
		wd.findElement(By.id("fhr")).clear();
		wd.findElement(By.id("fhr")).sendKeys("160");
		wd.findElement(By.id("fhu")).clear();
		wd.findElement(By.id("fhu")).sendKeys("14");
		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Office Visit successfully added"));
		assertTrue(wd.getPageSource().contains("High Blood Pressure"));
		assertTrue(wd.getPageSource().contains("Advanced Maternal Age"));
		assertTrue(wd.getPageSource().contains("Abnormal Weight Change"));
		
	}
	
	public void testDocumentObOVHighFHR() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();

		wd.findElement(By.name("UID_PATIENTID")).sendKeys("401");
		wd.findElement(By.xpath("//input[@value='401']")).submit();	
		
		String title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);

		wd.setJavascriptEnabled(true);

		List<WebElement> inputs = wd.findElements(By.tagName("input"));
		inputs.get(0).click();
		title = wd.getTitle();
		assertEquals("iTrust - Initialize Obstetrics Record", title);
		
		
		wd.findElement(By.id("lmp")).clear();
		wd.findElement(By.id("lmp")).sendKeys("10/05/2014");
		wd.findElement(By.id("date")).clear();
		wd.findElement(By.id("date")).sendKeys("10/29/2014");

		wd.findElement(By.id("submit")).click();
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Record successfully added"));
		
		wd.findElement(By.linkText("Add Obstetrics Office Visit")).click();
		title = wd.getTitle();
		assertEquals("iTrust - Add Obstetrics Office Visit", title);
		
		wd.findElement(By.id("date")).clear();
		wd.findElement(By.id("date")).sendKeys("11/05/2014");
		wd.findElement(By.id("weight")).clear();
		wd.findElement(By.id("weight")).sendKeys("140");
		wd.findElement(By.id("bloodPressureS")).clear();
		wd.findElement(By.id("bloodPressureS")).sendKeys("130");
		wd.findElement(By.id("bloodPressureD")).clear();
		wd.findElement(By.id("bloodPressureD")).sendKeys("80");
		wd.findElement(By.id("fhr")).clear();
		wd.findElement(By.id("fhr")).sendKeys("171");
		wd.findElement(By.id("fhu")).clear();
		wd.findElement(By.id("fhu")).sendKeys("14");
		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Office Visit successfully added"));
		assertTrue(wd.getPageSource().contains("Advanced Maternal Age"));
		assertTrue(wd.getPageSource().contains("Abnormal FHR"));
	}

	public void testDocumentAbnormalFHRBoundaryValue() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();

		wd.findElement(By.name("UID_PATIENTID")).sendKeys("401");
		wd.findElement(By.xpath("//input[@value='401']")).submit();	
		
		String title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);

		wd.setJavascriptEnabled(true);
		//initialize patient
		List<WebElement> inputs = wd.findElements(By.tagName("input"));
		inputs.get(0).click();
		title = wd.getTitle();
		assertEquals("iTrust - Initialize Obstetrics Record", title);
		
		
		wd.findElement(By.id("lmp")).clear();
		wd.findElement(By.id("lmp")).sendKeys("10/05/2014");
		wd.findElement(By.id("date")).clear();
		wd.findElement(By.id("date")).sendKeys("10/29/2014");

		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Record successfully added"));
		
		wd.findElement(By.linkText("Add Obstetrics Office Visit")).click();
		title = wd.getTitle();
		assertEquals("iTrust - Add Obstetrics Office Visit", title);
		//set boundary value for abnormal fhr
		wd.findElement(By.id("date")).clear();
		wd.findElement(By.id("date")).sendKeys("11/05/2014");
		wd.findElement(By.id("weight")).clear();
		wd.findElement(By.id("weight")).sendKeys("140");
		wd.findElement(By.id("bloodPressureS")).clear();
		wd.findElement(By.id("bloodPressureS")).sendKeys("130");
		wd.findElement(By.id("bloodPressureD")).clear();
		wd.findElement(By.id("bloodPressureD")).sendKeys("80");
		wd.findElement(By.id("fhr")).clear();
		wd.findElement(By.id("fhr")).sendKeys("105");
		wd.findElement(By.id("fhu")).clear();
		wd.findElement(By.id("fhu")).sendKeys("14");
		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//make sure abnormal fhr flag not set
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Office Visit successfully added"));
		assertTrue(wd.getPageSource().contains("Advanced Maternal Age"));
		assertTrue(!wd.getPageSource().contains("Abnormal FHR"));
		
		wd.findElement(By.linkText("11/05/2014-Office Visit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Edit Obstetrics Record", title);
		wd.findElement(By.id("fhr")).clear();
		wd.findElement(By.id("fhr")).sendKeys("104");
		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Office Visit successfully edited"));
		assertTrue(wd.getPageSource().contains("Advanced Maternal Age"));
		assertTrue(wd.getPageSource().contains("Abnormal FHR"));
	}
	
	public void testAddAllergy() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("PHR Information")).click();

		wd.findElement(By.name("UID_PATIENTID")).sendKeys("401");
		wd.findElement(By.xpath("//input[@value='401']")).submit();	

		String title = wd.getTitle();
		assertEquals("iTrust - Edit Personal Health Record", title);
		
		wd.findElement(By.id("description")).clear();
		wd.findElement(By.id("description")).sendKeys("Penicillin");
		WebElement we = wd.findElement(By.name("addA"));
		we.click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//WebElement we1 = wd.findElement(By.id("description"));
		assertTrue(wd.getPageSource().contains("Penicillin"));
		assertTrue(wd.getPageSource().contains("Allergy Added"));
		
		wd.findElement(By.linkText("View Obstetrics Records")).click();
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		
		assertTrue(wd.getPageSource().contains("Penicillin"));
	}
	public void testAddSecondAllergy() throws Exception {
		testAddAllergy();
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("PHR Information")).click();

		wd.findElement(By.name("UID_PATIENTID")).sendKeys("401");
		wd.findElement(By.xpath("//input[@value='401']")).submit();	

		String title = wd.getTitle();
		assertEquals("iTrust - Edit Personal Health Record", title);
		
		wd.findElement(By.id("description")).clear();
		wd.findElement(By.id("description")).sendKeys("Dubstep");
		WebElement we = wd.findElement(By.name("addA"));
		we.click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//WebElement we1 = wd.findElement(By.id("description"));
		assertTrue(wd.getPageSource().contains("Penicillin"));
		assertTrue(wd.getPageSource().contains("Dubstep"));
		assertTrue(wd.getPageSource().contains("Allergy Added"));
		
		wd.findElement(By.linkText("View Obstetrics Records")).click();
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		
		assertTrue(wd.getPageSource().contains("Penicillin"));
		assertTrue(wd.getPageSource().contains("Dubstep"));
		
	}

	public void testAddInvalidAllergy() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("PHR Information")).click();

		wd.findElement(By.name("UID_PATIENTID")).sendKeys("401");
		wd.findElement(By.xpath("//input[@value='401']")).submit();	

		String title = wd.getTitle();
		assertEquals("iTrust - Edit Personal Health Record", title);
		
		wd.findElement(By.id("description")).clear();
		wd.findElement(By.id("description")).sendKeys("Human !@#$");
		WebElement we = wd.findElement(By.name("addA"));
		we.click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//WebElement we1 = wd.findElement(By.id("description"));
		assertTrue(!wd.getPageSource().contains("Human !@#$"));
		assertTrue(wd.getPageSource().contains("This form has not been validated correctly."
				+ " The following field are not properly filled in: [Allergy Description: Up"
				+ " to 30 characters, letters, numbers, and a space]"));
		
		wd.findElement(By.linkText("View Obstetrics Records")).click();
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		
		assertTrue(!wd.getPageSource().contains("Human !@#$"));
	}

	public void testBasicLaborDeliveryReport() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();

		wd.findElement(By.name("UID_PATIENTID")).sendKeys("401");
		wd.findElement(By.xpath("//input[@value='401']")).submit();	

		String title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		wd.setJavascriptEnabled(true);
		List<WebElement> inputs = wd.findElements(By.tagName("input"));
		inputs.get(0).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Initialize Obstetrics Record", title);
	
		wd.findElement(By.id("lmp")).clear();
		wd.findElement(By.id("lmp")).sendKeys("10/05/2014");
		wd.findElement(By.id("date")).clear();
		wd.findElement(By.id("date")).sendKeys("10/29/2014");
		
		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Record successfully added"));

		wd.findElement(By.linkText("Labor and Delivery Report")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Labor and Delivery Report", title);
		
		assertTrue(wd.getPageSource().contains("Miscarriage"));
		assertTrue(wd.getPageSource().contains("Vaginal Delivery"));
		
		assertTrue(wd.getPageSource().contains("07/12/2015"));
	}

	public void testLaborDeliveryReportFlags() throws Exception {
		// login HCP Kathryn Evans
		HtmlUnitDriver wd = (HtmlUnitDriver)this.login("9000000012", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		wd.findElement(By.linkText("Obstetrics Home")).click();

		wd.findElement(By.name("UID_PATIENTID")).sendKeys("21");
		wd.findElement(By.xpath("//input[@value='21']")).submit();	
		
		wd.setJavascriptEnabled(true);
		String title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		
		List<WebElement> inputs = wd.findElements(By.tagName("input"));
		inputs.get(0).click();
		title = wd.getTitle();
		assertEquals("iTrust - Initialize Obstetrics Record", title);
	
		wd.findElement(By.id("lmp")).clear();
		wd.findElement(By.id("lmp")).sendKeys("10/05/2014");
		wd.findElement(By.id("date")).clear();
		wd.findElement(By.id("date")).sendKeys("10/29/2014");
		
		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Record successfully added"));
		
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		
		wd.findElement(By.linkText("PHR Information")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Edit Personal Health Record", title);
		
		wd.findElement(By.id("description")).clear();
		wd.findElement(By.id("description")).sendKeys("Penicillin");
		WebElement we = wd.findElement(By.name("addA"));
		we.click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//WebElement we1 = wd.findElement(By.id("description"));
		assertTrue(wd.getPageSource().contains("Penicillin"));
		assertTrue(wd.getPageSource().contains("Allergy Added"));
		
		wd.findElement(By.linkText("Add Obstetrics Office Visit")).click();
		title = wd.getTitle();
		assertEquals("iTrust - Add Obstetrics Office Visit", title);
		
		wd.findElement(By.id("date")).clear();
		wd.findElement(By.id("date")).sendKeys("11/05/2014");
		wd.findElement(By.id("weight")).clear();
		wd.findElement(By.id("weight")).sendKeys("140");
		wd.findElement(By.id("bloodPressureS")).clear();
		wd.findElement(By.id("bloodPressureS")).sendKeys("190");
		wd.findElement(By.id("bloodPressureD")).clear();
		wd.findElement(By.id("bloodPressureD")).sendKeys("80");
		wd.findElement(By.id("fhr")).clear();
		wd.findElement(By.id("fhr")).sendKeys("200");
		wd.findElement(By.id("fhu")).clear();
		wd.findElement(By.id("fhu")).sendKeys("14");
		wd.findElement(By.id("twins")).click();
		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics", title);
		assertTrue(wd.getPageSource().contains("Obstetrics Office Visit successfully added"));
		String flags = wd.getPageSource();
		assertTrue(flags.contains("High Blood Pressure"));
		assertTrue(flags.contains("Maternal Allergies"));
		assertTrue(flags.contains("Penicillin"));
		assertTrue(flags.contains("Abnormal FHR"));
		assertTrue(flags.contains("Twins"));
		
		wd.findElement(By.linkText("Patient Pre-existing Conditions")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		title = wd.getTitle();
		assertEquals("iTrust - Obstetrics Record Pre-Existing Conditions", title);
		
		wd.findElement(By.id("condition")).clear();
		wd.findElement(By.id("condition")).sendKeys("Diabetes");
		wd.findElement(By.id("submit")).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		assertTrue(wd.getPageSource().contains("Pre-existing condition added OK"));
		assertTrue(wd.getPageSource().contains("Diabetes"));
		
		wd.findElement(By.linkText("Labor and Delivery Report")).click();
		title = wd.getTitle();
		assertEquals("iTrust - Labor and Delivery Report", title);
		
		flags = wd.getPageSource();
		assertTrue(flags.contains("High Blood Pressure"));
		assertTrue(flags.contains("Maternal Allergies"));
		assertTrue(flags.contains("Penicillin"));
		assertTrue(flags.contains("Abnormal FHR"));
		assertTrue(flags.contains("Twins"));
		assertTrue(flags.contains("Pregnancy relevant pre-existing conditions"));
		assertTrue(flags.contains("Diabetes"));
	}
}