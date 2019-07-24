package edu.ncsu.csc.itrust.selenium;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class InsuranceSubmissionTest extends iTrustSeleniumTest {

	/*
	 * The URL for iTrust, change as needed
	 */
	/**ADDRESS*/
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	private WebDriver driver;
	
	//MIDs for various users.
	private static final long JOHN_SMITH = 313;
	private static final long MARIA_LOPEZ = 314;
	private static final long JUAN_CARLOS = 315;
	
	private static final long MIKE_JONES = 9000000012L;
	private static final long DANIEL_WILLIAMS = 9000000013L;
	private static final long JANE_SMITH = 9000000014L;
	
	//Passwords
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
	
	public void testUAPApproval() throws Exception {
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
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertLogged(TransactionType.UAP_INITIAL_APPROVAL, MIKE_JONES, MIKE_JONES, "");
		
		driver = login("" + JOHN_SMITH, PW);
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("My Bills")).click();
		assertTrue(driver.getPageSource().contains("Approved"));
	}
	
	public void testUAPDenialThenApproval() throws Exception {
		driver = login("" + MARIA_LOPEZ, PW);
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("My Bills")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Bills");
		assertTrue(driver.getPageSource().contains("Kelly Doctor"));
		driver.findElement(By.linkText("02/17/2014")).click();
		assertTrue(driver.getPageSource().contains("$250"));
		assertTrue(driver.getPageSource().contains("Le Awesome Hospital"));
		assertTrue(driver.getPageSource().contains("Mammogram"));
		
		driver.findElement(By.id("Ins")).click();
		driver.findElement(By.name("insHolder")).clear();
		driver.findElement(By.name("insHolder")).sendKeys("Maria Lopez");
		driver.findElement(By.name("insProvider")).clear();
		driver.findElement(By.name("insProvider")).sendKeys("GMX Insurance");
		driver.findElement(By.name("insID")).clear();
		driver.findElement(By.name("insID")).sendKeys("4447157D13");
		driver.findElement(By.name("insAdd1")).clear();
		driver.findElement(By.name("insAdd1")).sendKeys("113 Seaboard Ave");
		driver.findElement(By.name("insAdd2")).clear();
		driver.findElement(By.name("insAdd2")).sendKeys("");
		driver.findElement(By.name("insCity")).clear();
		driver.findElement(By.name("insCity")).sendKeys("Raleigh");
		driver.findElement(By.name("insState")).clear();
		driver.findElement(By.name("insState")).sendKeys("NC");
		driver.findElement(By.name("insZip")).clear();
		driver.findElement(By.name("insZip")).sendKeys("27606");
		driver.findElement(By.name("insPhone")).clear();
		driver.findElement(By.name("insPhone")).sendKeys("919-468-1537");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		driver = login("" + DANIEL_WILLIAMS, PW);
		driver.findElement(By.linkText("View Insurance Claims")).click();
		driver.findElement(By.linkText(new SimpleDateFormat("MM/dd/YYYY").format(new Date()))).click();
		driver.findElement(By.xpath("//input[@value='Deny']")).submit();
		assertLogged(TransactionType.UAP_INITIAL_DENIAL, DANIEL_WILLIAMS, DANIEL_WILLIAMS, "");
		
		driver = login("" + MARIA_LOPEZ, PW);
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("My Bills")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Bills");
		assertTrue(driver.getPageSource().contains("Kelly Doctor"));
		driver.findElement(By.linkText("02/17/2014")).click();
		assertTrue(driver.getPageSource().contains("$250"));
		assertTrue(driver.getPageSource().contains("Le Awesome Hospital"));
		assertTrue(driver.getPageSource().contains("Mammogram"));
		
		driver.findElement(By.id("Ins")).click();
		driver.findElement(By.name("insHolder")).clear();
		driver.findElement(By.name("insHolder")).sendKeys("Maria Lopez");
		driver.findElement(By.name("insProvider")).clear();
		driver.findElement(By.name("insProvider")).sendKeys("GMX Insurance");
		driver.findElement(By.name("insID")).clear();
		driver.findElement(By.name("insID")).sendKeys("4447157D13");
		driver.findElement(By.name("insAdd1")).clear();
		driver.findElement(By.name("insAdd1")).sendKeys("113 Seaboard Ave");
		driver.findElement(By.name("insAdd2")).clear();
		driver.findElement(By.name("insAdd2")).sendKeys("");
		driver.findElement(By.name("insCity")).clear();
		driver.findElement(By.name("insCity")).sendKeys("Raleigh");
		driver.findElement(By.name("insState")).clear();
		driver.findElement(By.name("insState")).sendKeys("NC");
		driver.findElement(By.name("insZip")).clear();
		driver.findElement(By.name("insZip")).sendKeys("27606");
		driver.findElement(By.name("insPhone")).clear();
		driver.findElement(By.name("insPhone")).sendKeys("919-468-1537");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver = login("" + DANIEL_WILLIAMS, PW);
		driver.findElement(By.linkText("View Insurance Claims")).click();
		driver.findElement(By.linkText(new SimpleDateFormat("MM/dd/YYYY").format(new Date()))).click();
		driver.findElement(By.xpath("//input[@value='Approve']")).submit();
		assertLogged(TransactionType.UAP_INITIAL_DENIAL, DANIEL_WILLIAMS, DANIEL_WILLIAMS, "");
		
		driver = login("" + MARIA_LOPEZ, PW);
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("My Bills")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Bills");
		assertTrue(driver.getPageSource().contains("Approved"));
	}
	
	public void testTwoUAPDenials() throws Exception {
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
		driver.findElement(By.linkText("View Insurance Claims")).click();
		driver.findElement(By.linkText(new SimpleDateFormat("MM/dd/YYYY").format(new Date()))).click();
		driver.findElement(By.xpath("//input[@value='Deny']")).submit();
		assertLogged(TransactionType.UAP_INITIAL_DENIAL, JANE_SMITH, JANE_SMITH, "");
		
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
		driver.findElement(By.linkText("View Insurance Claims")).click();
		driver.findElement(By.linkText(new SimpleDateFormat("MM/dd/YYYY").format(new Date()))).click();
		driver.findElement(By.xpath("//input[@value='Deny']")).submit();
		assertLogged(TransactionType.UAP_SECOND_DENIAL, JANE_SMITH, JANE_SMITH, "");
		
		driver = login("" + JUAN_CARLOS, PW);
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("My Bills")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Bills");
		assertTrue(driver.getPageSource().contains("Denied"));
		driver.findElement(By.linkText("02/07/2014")).click();
		
		driver.findElement(By.id("CC")).click();
		driver.findElement(By.name("ccNumber")).clear();
		driver.findElement(By.name("ccNumber")).sendKeys("4539592576502361");
		final Select selectBox = new Select(driver.findElement(By.name("ccType")));
		selectBox.selectByValue("Visa");
		driver.findElement(By.name("ccHolder")).clear();
		driver.findElement(By.name("ccHolder")).sendKeys("Juan Carlos");
		driver.findElement(By.name("billAddress")).clear();
		driver.findElement(By.name("billAddress")).sendKeys("412 Conifer Dr, Raleigh, NC 27606");
		driver.findElement(By.name("cvv")).clear();
		driver.findElement(By.name("cvv")).sendKeys("007");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTrue(driver.getPageSource().contains("Payment Information"));
		driver.findElement(By.linkText("My Bills")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Bills");
		assertTrue(driver.getPageSource().contains("Submitted"));
	}

}