package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.*;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class PersonnelTest extends iTrustSeleniumTest {
	
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	/**ADDRESS*/
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	
	/**
	 * testViewPrescriptionRecords
	 * @throws Exception
	 */
	@Test
	public void testViewPrescriptionRecords() throws Exception {
        // Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        //And now use this to visit iTrust
        driver.get(ADDRESS);
        
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        assertEquals("iTrust - Login", driver.getTitle());

        //Find the text input element by its name
        driver.findElement(By.name("j_username")).sendKeys("9000000000");
        driver.findElement(By.name("j_password")).sendKeys("pw");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
      
        //Check to make sure this is the correct page
        assertEquals("iTrust - HCP Home", driver.getTitle());
        
        //Click on All Patients
        driver.findElement(By.cssSelector("h2.panel-title")).click();
        driver.findElement(By.linkText("All Patients")).click();
        assertEquals("Past Patients", driver.findElement(By.cssSelector("#iTrustContent > h2")).getText());
		assertLogged(TransactionType.PATIENT_LIST_VIEW, 9000000000L, 0L, "");
        
        //Click on Andy Programmer
        driver.findElement(By.linkText("Andy Programmer")).click();
        assertEquals("Andy Programmer", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/div/table/tbody/tr[2]/td[2]")).getText());
        assertEquals("iTrust - Edit Personal Health Record",driver.getTitle());
        assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW, 9000000000L, 2L, "");
        
        //Go to prescription report page
        driver.findElement(By.linkText("Get Prescription Report")).click();
        assertEquals("iTrust - Get Prescription Report",driver.getTitle());
        assertLogged(TransactionType.PRESCRIPTION_REPORT_VIEW, 9000000000L, 2L, "");
        
        //Check fields to make sure they are correct
        assertEquals("00904-2407", driver.findElement(By.cssSelector("td")).getText());
        assertEquals("Tetracycline", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[2]/td[2]")).getText());
        assertEquals("10/10/2006 to 10/11/2006", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[2]/td[3]")).getText());
        assertEquals("Kelly Doctor", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[2]/td[4]")).getText());
        assertEquals("00904-2407", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td")).getText());
        assertEquals("Tetracycline", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[2]")).getText());
        assertEquals("10/10/2006 to 10/11/2006", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[3]")).getText());
        assertEquals("Kelly Doctor", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[4]")).getText());
        assertEquals("64764-1512", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[4]/td")).getText());
        assertEquals("Prioglitazone", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[4]/td[2]")).getText());
        assertEquals("10/10/2006 to 10/11/2020", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[4]/td[3]")).getText());
        assertEquals("Kelly Doctor", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[4]/td[4]")).getText());


        //Close the driver
        driver.quit();
		
		
      }
}