package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class MonitorAdverseEventTest extends iTrustSeleniumTest {

	/*
	 * The URL for iTrust, change as needed
	 */
	/**ADDRESS*/
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	private WebDriver driver;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.patient2();
		gen.hcp0();
		gen.pha1();
		gen.patient1();
		//gen.patient2();
		gen.patient3();
		gen.patient4();
		gen.patient10();
		gen.patient13();
		// turn off htmlunit warnings
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	    java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
	}
	
	@Override
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	public void testViewDrugAdverseEvents () throws Exception {
		gen.adverseEvent1();
		driver = login("7000000001", "pw");
		assertEquals(driver.getTitle(), "iTrust - PHA Home");
		assertLogged(TransactionType.HOME_VIEW, 7000000001L, 0L, "");
		
		driver.findElement(By.linkText("Monitor Adverse Events")).click();
		assertEquals(driver.getTitle(), "iTrust - Monitor Adverse Events");
		
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("02/05/1990");
		driver.findElement(By.name("endDate")).clear();
		driver.findElement(By.name("endDate")).sendKeys("10/15/2009");
		driver.findElement(By.name("prescriptions")).click();
		assertTrue(driver.getPageSource().contains("Citalopram Hydrobromide(548684985)"));
		driver.findElement(By.linkText("Get Details")).click();
		assertLogged(TransactionType.ADVERSE_EVENT_VIEW, 7000000001L, 0L, "");
		
		assertTrue(driver.getPageSource().contains("Andy Programmer"));
		assertTrue(driver.getPageSource().contains("2007-08-12 15:10:00.0"));
		assertTrue(driver.getPageSource().contains("Stomach cramps and migraine headaches after taking this drug"));
		driver.findElement(By.name("moreInfo")).click();
		assertTrue(driver.getPageSource().contains("Request sent"));
		assertLogged(TransactionType.ADVERSE_EVENT_REQUEST_MORE, 7000000001L, 0L, "Requested more info");
	}
	
	public void testRemoveImmunizationAdverseEventReport() throws Exception {
		gen.adverseEvent2();
		driver = login("7000000001", "pw");
		assertEquals(driver.getTitle(), "iTrust - PHA Home");
		assertLogged(TransactionType.HOME_VIEW, 7000000001L, 0L, "");
		
		driver.findElement(By.linkText("Monitor Adverse Events")).click();
		assertEquals(driver.getTitle(), "iTrust - Monitor Adverse Events");
		
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("08/05/2000");
		driver.findElement(By.name("endDate")).clear();
		driver.findElement(By.name("endDate")).sendKeys("10/17/2009");
		driver.findElement(By.name("immunizations")).click();
		driver.findElement(By.linkText("Get Details")).click();
		assertLogged(TransactionType.ADVERSE_EVENT_VIEW, 7000000001L, 0L, "");
		
		assertTrue(driver.getPageSource().contains("Random Person"));
		assertTrue(driver.getPageSource().contains("2009-05-19 08:34:00.0"));
		assertTrue(driver.getPageSource().contains("A rash began spreading outward from the injection spot"));
		driver.findElement(By.name("remove")).click();
		assertTrue(driver.getPageSource().contains("Report successfully removed"));
		assertLogged(TransactionType.ADVERSE_EVENT_REMOVE, 7000000001L, 0L, "");
	}
	
	public void testGetBarChart() throws Exception {
		gen.adverseEvent3();
		driver = login("7000000001", "pw");
		assertEquals(driver.getTitle(), "iTrust - PHA Home");
		assertLogged(TransactionType.HOME_VIEW, 7000000001L, 0L, "");
		
		driver.findElement(By.linkText("Monitor Adverse Events")).click();
		assertEquals(driver.getTitle(), "iTrust - Monitor Adverse Events");
		
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("08/05/2000");
		driver.findElement(By.name("endDate")).clear();
		driver.findElement(By.name("endDate")).sendKeys("10/17/2009");
		driver.findElement(By.name("immunizations")).click();
		
		driver.findElement(By.linkText("View Chart")).click();
		assertLogged(TransactionType.ADVERSE_EVENT_CHART_VIEW, 7000000001L, 0L, "");
	}

}