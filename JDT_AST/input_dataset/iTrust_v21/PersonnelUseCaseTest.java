package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;


public class PersonnelUseCaseTest extends iTrustSeleniumTest {
	
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	/**ADDRESS*/
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	
	/**
	 * testAddER
	 * @throws Exception
	 */
	public void testAddER() throws Exception {
		// Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        //And now use this to visit iTrust
        driver.get(ADDRESS);
        
	    //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        assertEquals("iTrust - Login", driver.getTitle());

        //Find the text input element by its name
        driver.findElement(By.name("j_username")).sendKeys("9000000001");
        driver.findElement(By.name("j_password")).sendKeys("pw");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
      
        //Check to make sure this is the correct page
        assertEquals("iTrust - Admin Home", driver.getTitle());
		
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Add ER")).click();
	    assertEquals("iTrust - Add ER", driver.getTitle());
	    
	    //quit driver
	    driver.quit();
	}

	
}