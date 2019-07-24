package edu.ncsu.csc.itrust.selenium;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class InsuranceNotificationTest extends iTrustSeleniumTest {

	/*
	 * The URL for iTrust, change as needed
	 */
	/**ADDRESS*/
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	private WebDriver driver;
	//MIDs for various people.
	private static final long JOHN_SMITH = 313;
	private static final long JUAN_CARLOS = 315;
	private static final long ALEX_PAUL = 316;
	
	private static final long ROGER_KING = 9000000015L;
	private static final long JANE_SMITH = 9000000014L;
	private static final long MIKE_JONES = 9000000012L;
	
	//password for users.
	private static final String PW = "pw";

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.hospitals();
		gen.hospitals1();
		gen.hcp0();
		gen.uc51();
		gen.uc60();
		// turn off htmlunit warnings
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	    java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
	}

	@Test
	public void testCantViewSubmitted() throws Exception {
		driver = login("" + ALEX_PAUL, PW);
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("My Bills")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Bills");
		driver.findElement(By.linkText("01/25/2014")).click();
		
		driver.findElement(By.id("CC")).click();
		driver.findElement(By.name("ccNumber")).clear();
		driver.findElement(By.name("ccNumber")).sendKeys("343570480641495");
		final Select selectBox = new Select(driver.findElement(By.name("ccType")));
		selectBox.selectByValue("AmericanExpress");
		driver.findElement(By.name("ccHolder")).clear();
		driver.findElement(By.name("ccHolder")).sendKeys("Alex Paul");
		driver.findElement(By.name("billAddress")).clear();
		driver.findElement(By.name("billAddress")).sendKeys("206 Crest Road, Raleigh, NC 27606");
		driver.findElement(By.name("cvv")).clear();
		driver.findElement(By.name("cvv")).sendKeys("0123");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		driver = login("" + ROGER_KING, PW);
		assertTrue(driver.getPageSource().contains("No Pending Insurance Claims"));
		driver.findElement(By.linkText("View Insurance Claims")).click();
		assertTrue(driver.getPageSource().contains("No claims to display."));
	}
	
	@Test
	public void testClaimNotification() throws Exception {
		driver = login("" + JOHN_SMITH, PW);
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("My Bills")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Bills");
		assertTrue(driver.getPageSource().contains("Shelly Vang"));
		driver.findElement(By.linkText("01/10/2014")).click();
		
		assertTrue(driver.getPageSource().contains("$150"));
		assertTrue(driver.getPageSource().contains("Health Institute Dr. E"));
		assertTrue(driver.getPageSource().contains("General Checkup"));
		
		driver.findElement(By.id("Ins")).click();
		driver.findElement(By.name("insHolder")).clear();
		driver.findElement(By.name("insHolder")).sendKeys("John Smith");
		driver.findElement(By.name("insProvider")).clear();
		driver.findElement(By.name("insProvider")).sendKeys("ABC Insurance");
		driver.findElement(By.name("insID")).clear();
		driver.findElement(By.name("insID")).sendKeys("1234567A01");
		driver.findElement(By.name("insAdd1")).clear();
		driver.findElement(By.name("insAdd1")).sendKeys("365 Broad St");
		driver.findElement(By.name("insAdd2")).clear();
		driver.findElement(By.name("insAdd2")).sendKeys("");
		driver.findElement(By.name("insCity")).clear();
		driver.findElement(By.name("insCity")).sendKeys("Raleigh");
		driver.findElement(By.name("insState")).clear();
		driver.findElement(By.name("insState")).sendKeys("NC");
		driver.findElement(By.name("insZip")).clear();
		driver.findElement(By.name("insZip")).sendKeys("27606");
		driver.findElement(By.name("insPhone")).clear();
		driver.findElement(By.name("insPhone")).sendKeys("919-112-8234");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		driver = login("" + MIKE_JONES, PW);
		assertTrue(driver.getPageSource().contains("inboxUnread.png"));
		assertTrue(driver.getPageSource().contains("Pending Insurance Claim."));
		driver.findElement(By.linkText("1")).click();
		assertEquals(driver.getTitle(), "iTrust - View Insurance Claims");
	}
	
	@Test
	public void testApprovalNotification() throws Exception {
		driver = login("" + JOHN_SMITH, PW);
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("My Bills")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Bills");
		assertTrue(driver.getPageSource().contains("Shelly Vang"));
		driver.findElement(By.linkText("01/10/2014")).click();
		
		driver.findElement(By.id("Ins")).click();
		driver.findElement(By.name("insHolder")).clear();
		driver.findElement(By.name("insHolder")).sendKeys("John Smith");
		driver.findElement(By.name("insProvider")).clear();
		driver.findElement(By.name("insProvider")).sendKeys("ABC Insurance");
		driver.findElement(By.name("insID")).clear();
		driver.findElement(By.name("insID")).sendKeys("1234567A01");
		driver.findElement(By.name("insAdd1")).clear();
		driver.findElement(By.name("insAdd1")).sendKeys("365 Broad St");
		driver.findElement(By.name("insAdd2")).clear();
		driver.findElement(By.name("insAdd2")).sendKeys("");
		driver.findElement(By.name("insCity")).clear();
		driver.findElement(By.name("insCity")).sendKeys("Raleigh");
		driver.findElement(By.name("insState")).clear();
		driver.findElement(By.name("insState")).sendKeys("NC");
		driver.findElement(By.name("insZip")).clear();
		driver.findElement(By.name("insZip")).sendKeys("27606");
		driver.findElement(By.name("insPhone")).clear();
		driver.findElement(By.name("insPhone")).sendKeys("919-112-8234");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		driver = login("" + MIKE_JONES, PW);
		driver.findElement(By.linkText("View Insurance Claims")).click();
		driver.findElement(By.linkText(new SimpleDateFormat("MM/dd/YYYY").format(new Date()))).click();
		driver.findElement(By.xpath("//input[@value='Approve']")).submit();
		
		driver = login("" + JOHN_SMITH, PW);
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		assertTrue(driver.getPageSource().contains("approved.png"));
		assertTrue(driver.getPageSource().contains("approved insurance claim."));
		driver.findElement(By.linkText("1")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Bills");
		assertTrue(driver.getPageSource().contains("Approved"));
	}
	
	public void testDenialNotification() throws Exception{
		driver = login("" + JOHN_SMITH, PW);
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("My Bills")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Bills");
		assertTrue(driver.getPageSource().contains("Shelly Vang"));
		driver.findElement(By.linkText("01/10/2014")).click();
		
		assertTrue(driver.getPageSource().contains("$150"));
		assertTrue(driver.getPageSource().contains("Health Institute Dr. E"));
		assertTrue(driver.getPageSource().contains("General Checkup"));
		
		driver.findElement(By.id("Ins")).click();
		driver.findElement(By.name("insHolder")).clear();
		driver.findElement(By.name("insHolder")).sendKeys("John Smith");
		driver.findElement(By.name("insProvider")).clear();
		driver.findElement(By.name("insProvider")).sendKeys("ABC Insurance");
		driver.findElement(By.name("insID")).clear();
		driver.findElement(By.name("insID")).sendKeys("1234567A01");
		driver.findElement(By.name("insAdd1")).clear();
		driver.findElement(By.name("insAdd1")).sendKeys("365 Broad St");
		driver.findElement(By.name("insAdd2")).clear();
		driver.findElement(By.name("insAdd2")).sendKeys("");
		driver.findElement(By.name("insCity")).clear();
		driver.findElement(By.name("insCity")).sendKeys("Raleigh");
		driver.findElement(By.name("insState")).clear();
		driver.findElement(By.name("insState")).sendKeys("NC");
		driver.findElement(By.name("insZip")).clear();
		driver.findElement(By.name("insZip")).sendKeys("27606");
		driver.findElement(By.name("insPhone")).clear();
		driver.findElement(By.name("insPhone")).sendKeys("919-112-8234");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		driver = login("" + MIKE_JONES, PW);
		driver.findElement(By.linkText("View Insurance Claims")).click();
		driver.findElement(By.linkText(new SimpleDateFormat("MM/dd/YYYY").format(new Date()))).click();
		driver.findElement(By.xpath("//input[@value='Deny']")).submit();
		
		driver = login("" + JOHN_SMITH, PW);
		assertTrue(driver.getPageSource().contains("denied.png"));
		assertTrue(driver.getPageSource().contains("denied insurance claim."));
		driver.findElement(By.linkText("1")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Bills");
		assertTrue(driver.getPageSource().contains("Denied"));
	}
	
	public void testMultiplePatients() throws Exception {
		driver = login("" + JOHN_SMITH, PW);
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("My Bills")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Bills");
		assertTrue(driver.getPageSource().contains("Shelly Vang"));
		driver.findElement(By.linkText("01/10/2014")).click();
		
		assertTrue(driver.getPageSource().contains("$150"));
		assertTrue(driver.getPageSource().contains("Health Institute Dr. E"));
		assertTrue(driver.getPageSource().contains("General Checkup"));
		
		driver.findElement(By.id("Ins")).click();
		driver.findElement(By.name("insHolder")).clear();
		driver.findElement(By.name("insHolder")).sendKeys("John Smith");
		driver.findElement(By.name("insProvider")).clear();
		driver.findElement(By.name("insProvider")).sendKeys("ABC Insurance");
		driver.findElement(By.name("insID")).clear();
		driver.findElement(By.name("insID")).sendKeys("1234567A01");
		driver.findElement(By.name("insAdd1")).clear();
		driver.findElement(By.name("insAdd1")).sendKeys("365 Broad St");
		driver.findElement(By.name("insAdd2")).clear();
		driver.findElement(By.name("insAdd2")).sendKeys("");
		driver.findElement(By.name("insCity")).clear();
		driver.findElement(By.name("insCity")).sendKeys("Raleigh");
		driver.findElement(By.name("insState")).clear();
		driver.findElement(By.name("insState")).sendKeys("NC");
		driver.findElement(By.name("insZip")).clear();
		driver.findElement(By.name("insZip")).sendKeys("27606");
		driver.findElement(By.name("insPhone")).clear();
		driver.findElement(By.name("insPhone")).sendKeys("919-112-8234");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		driver = login("" + JUAN_CARLOS, PW);
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("My Bills")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Bills");
		assertTrue(driver.getPageSource().contains("Shelly Vang"));
		driver.findElement(By.linkText("02/07/2014")).click();
		
		assertTrue(driver.getPageSource().contains("$350"));
		assertTrue(driver.getPageSource().contains("Facebook Rehab Center"));
		assertTrue(driver.getPageSource().contains("Ultrasound"));
		
		driver.findElement(By.id("Ins")).click();
		driver.findElement(By.name("insHolder")).clear();
		driver.findElement(By.name("insHolder")).sendKeys("Juan Carlos");
		driver.findElement(By.name("insProvider")).clear();
		driver.findElement(By.name("insProvider")).sendKeys("LZA Insurance");
		driver.findElement(By.name("insID")).clear();
		driver.findElement(By.name("insID")).sendKeys("9871932F25");
		driver.findElement(By.name("insAdd1")).clear();
		driver.findElement(By.name("insAdd1")).sendKeys("222 Noname Dr");
		driver.findElement(By.name("insAdd2")).clear();
		driver.findElement(By.name("insAdd2")).sendKeys("");
		driver.findElement(By.name("insCity")).clear();
		driver.findElement(By.name("insCity")).sendKeys("Raleigh");
		driver.findElement(By.name("insState")).clear();
		driver.findElement(By.name("insState")).sendKeys("NC");
		driver.findElement(By.name("insZip")).clear();
		driver.findElement(By.name("insZip")).sendKeys("27604");
		driver.findElement(By.name("insPhone")).clear();
		driver.findElement(By.name("insPhone")).sendKeys("919-222-6579");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		driver = login("" + JANE_SMITH, PW);
		assertTrue(driver.getPageSource().contains("2"));
		assertTrue(driver.getPageSource().contains("Pending Insurance Claims."));
	}
}