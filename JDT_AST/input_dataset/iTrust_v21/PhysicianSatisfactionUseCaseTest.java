package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class PhysicianSatisfactionUseCaseTest extends iTrustSeleniumTest {

	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.standardData();		
	}
	
	/**ADDRESS*/
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	
	/*
	 * 	Preconditions: Patient 2 is in the system and has authenticated successfully. 
	 *  HCP 9000000000 is in the system with address 4321 My Road St, PO BOX 2, CityName, NY, 12345-1234 and physician type Surgeon. 
	 *  Patient 2 has had 2 office visits with HCP 9000000000, and no other office visits are in the system. 
	 *  TakeSatisfactionSurveySuccess and TakeSatisfactionSurveySuccess2 have passed successfully.
	 *  1. Patient 2 chooses to view satisfaction survey results.
	 *  2. Patient 2 inputs Surgeon for physician type and zip code 12377.
	 *  3. Submit.
	 * 
	 */
	public void testSearchForHCPSurveyResults1() throws Exception {
		// Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        //And now use this to visit iTrust
        driver.get(ADDRESS);
        
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        assertEquals("iTrust - Login", driver.getTitle());

        //Find the text input element by its name
        driver.findElement(By.name("j_username")).sendKeys("1");
        driver.findElement(By.name("j_password")).sendKeys("pw");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
        //Check to make sure this is the correct page
        assertEquals("iTrust - Patient Home", driver.getTitle());
		
	    driver.findElement(By.linkText("Satisfaction Survey Results")).click();
	    driver.findElement(By.name("hcpZip")).clear();
	    driver.findElement(By.name("hcpZip")).sendKeys("10453");
	    new Select(driver.findElement(By.name("hcpSpecialty"))).selectByVisibleText("Surgeon");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Survey Results", driver.getTitle());
	    assertEquals("Survey Results", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/h2[2]")).getText());
	    assertEquals("Kelly Doctor", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td")).getText());
	    driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[2]")).click();
	    assertEquals("4321 My Road St", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[2]")).getText());
	    assertEquals("PO BOX 2", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[3]")).getText());
	    assertEquals("NY", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[5]")).getText());
	    assertEquals("10453", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[6]")).getText());
	    assertEquals("surgeon", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[7]")).getText());
	    assertEquals("na", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[8]")).getText());
	
	    //quit driver
	    driver.quit();
	}
	
	
	
	public void testSearchForHCPSurveyResults2() throws Exception {
		gen.surveyResults();
		
		// Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        //And now use this to visit iTrust
        driver.get(ADDRESS);
        
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        assertEquals("iTrust - Login", driver.getTitle());

        //Find the text input element by its name
        driver.findElement(By.name("j_username")).sendKeys("8000000009");
        driver.findElement(By.name("j_password")).sendKeys("uappass1");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        
        //Check to make sure this is the correct page
        assertEquals("iTrust - UAP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
		

	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[5]/div/h2")).click();
	    driver.findElement(By.linkText("Satisfaction Survey Results")).click();
	    assertEquals("Search HCP Survey Results", driver.findElement(By.cssSelector("#iTrustContent > div > h2")).getText());
	    driver.findElement(By.name("hcpZip")).clear();
	    driver.findElement(By.name("hcpZip")).sendKeys("27613");
	    new Select(driver.findElement(By.name("hcpSpecialty"))).selectByVisibleText("Heart Specialist");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    assertEquals("Bad Doctor", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td")).getText());
	    assertEquals("Avenue 1", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[2]")).getText());
	    assertEquals("Avenue 2", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[3]")).getText());
	    assertEquals("Raleigh", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[4]")).getText());
	    assertEquals("Heart Specialist", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[7]")).getText());
	    assertEquals("27613", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[6]")).getText());
	    
	    //quit driver
	    driver.quit();
		
	}
	
	
	
	public void testSearchForHCPSurveyResults3() throws Exception {		
		gen.surveyResults();
		
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
        
        driver.findElement(By.cssSelector("div.panel-heading")).click();
        driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[9]/div/h2")).click();
        driver.findElement(By.linkText("Satisfaction Survey Results")).click();
        driver.findElement(By.name("hcpZip")).clear();
        driver.findElement(By.name("hcpZip")).sendKeys("27613-1234");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("Search HCP Survey Results", driver.findElement(By.cssSelector("#iTrustContent > div > h2")).getText());
   
	    assertEquals("Good Doctor", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td")).getText());
	    assertEquals("Street 1", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[2]")).getText());
	    assertEquals("Street 2", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[3]")).getText());
	    assertEquals("Raleigh", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[4]")).getText());
	    assertEquals("NC", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[5]")).getText());
	    assertEquals("27613", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[6]")).getText());
	    
	    assertEquals("Bad Doctor", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[4]/td")).getText());
	    assertEquals("Avenue 1", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[4]/td[2]")).getText());
	    assertEquals("Avenue 2", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[4]/td[3]")).getText());
	    assertEquals("Raleigh", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[4]/td[4]")).getText());
	    assertEquals("Heart Specialist", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[4]/td[7]")).getText());
	    assertEquals("27613", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[4]/td[6]")).getText());
	    
	    assertLogged(TransactionType.SATISFACTION_SURVEY_VIEW, 9000000000L, 0L, "");
	    
        //quit driver
        driver.quit();
	}
	
	public void testSearchByHospitalSurveyResults1() throws Exception {
		gen.surveyResults();

		// Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        //And now use this to visit iTrust
        driver.get(ADDRESS);
        
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        assertEquals("iTrust - Login", driver.getTitle());

        //Find the text input element by its name
        driver.findElement(By.name("j_username")).sendKeys("2");
        driver.findElement(By.name("j_password")).sendKeys("pw");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
        //Check to make sure this is the correct page
        assertEquals("iTrust - Patient Home", driver.getTitle());
	    
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[7]/div")).click();
	    driver.findElement(By.linkText("Satisfaction Survey Results")).click();
	    assertEquals("Search HCP Survey Results", driver.findElement(By.cssSelector("#iTrustContent > div > h2")).getText());
	    
	    driver.findElement(By.name("hcpHospitalID")).click();
	    new Select(driver.findElement(By.name("hcpHospitalID"))).selectByVisibleText("8181818181");
	    driver.findElement(By.cssSelector("option[value=\"8181818181\"]")).click();
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("LHCP Search Results:", driver.findElement(By.cssSelector("th")).getText());
	    assertEquals("8181818181", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[8]")).getText());	    
	    
	    //quit the driver
	    driver.quit();
	}
	
	public void testSearchByHospitalSurveyResults2() throws Exception {
		gen.surveyResults();

		// Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        //And now use this to visit iTrust
        driver.get(ADDRESS);
        
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        assertEquals("iTrust - Login", driver.getTitle());

        //Find the text input element by its name
        driver.findElement(By.name("j_username")).sendKeys("8000000009");
        driver.findElement(By.name("j_password")).sendKeys("uappass1");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
		
        //Check to make sure this is the correct page
        assertEquals("iTrust - UAP Home", driver.getTitle());
	    
        driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[5]/div/h2")).click();
	    driver.findElement(By.linkText("Satisfaction Survey Results")).click();
	    assertEquals("Search HCP Survey Results", driver.findElement(By.cssSelector("#iTrustContent > div > h2")).getText());
	
	    driver.findElement(By.cssSelector("option[value=\"9191919191\"]")).click();
	    new Select(driver.findElement(By.name("hcpHospitalID"))).selectByVisibleText("9191919191");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("LHCP Search Results:", driver.findElement(By.cssSelector("th")).getText());
	    
	    //quit driver
	    driver.quit();
	}
	
	public void testSearchByHospitalSurveyResults3() throws Exception {
		gen.surveyResults();

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
        
        driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[5]/div/h2")).click();
	    driver.findElement(By.linkText("Satisfaction Survey Results")).click();
	    assertEquals("Search HCP Survey Results", driver.findElement(By.cssSelector("#iTrustContent > div > h2")).getText());
	
	    driver.findElement(By.cssSelector("option[value=\"8181818181\"]")).click();
	    new Select(driver.findElement(By.name("hcpHospitalID"))).selectByVisibleText("8181818181");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("LHCP Search Results:", driver.findElement(By.cssSelector("th")).getText());
	    assertLogged(TransactionType.SATISFACTION_SURVEY_VIEW, 9000000000L, 0L, "");
        
        //quit driver
        driver.quit();
	}
	
	public void testSurveyResultsNoInput() throws Exception {
		// Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        //And now use this to visit iTrust
        driver.get(ADDRESS);
        
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        assertEquals("iTrust - Login", driver.getTitle());

        //Find the text input element by its name
        driver.findElement(By.name("j_username")).sendKeys("2");
        driver.findElement(By.name("j_password")).sendKeys("pw");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
        //Check to make sure this is the correct page
        assertEquals("iTrust - Patient Home", driver.getTitle());

	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[7]/div")).click();
	    driver.findElement(By.linkText("Satisfaction Survey Results")).click();
	    assertEquals("Search HCP Survey Results", driver.findElement(By.cssSelector("#iTrustContent > div > h2")).getText());
	    
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("You must enter either a zip code or a hospital ID.", driver.findElement(By.cssSelector("#iTrustContent > div > span")).getText());

	    //quit driver
	    driver.quit();
	}

	public void testSurveyResultsTooMuchInput() throws Exception {
		// Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        //And now use this to visit iTrust
        driver.get(ADDRESS);
        
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        assertEquals("iTrust - Login", driver.getTitle());

        //Find the text input element by its name
        driver.findElement(By.name("j_username")).sendKeys("2");
        driver.findElement(By.name("j_password")).sendKeys("pw");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
        //Check to make sure this is the correct page
        assertEquals("iTrust - Patient Home", driver.getTitle());
		
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[7]/div/h2")).click();
	    driver.findElement(By.linkText("Satisfaction Survey Results")).click();
	    assertEquals("Search HCP Survey Results", driver.findElement(By.cssSelector("#iTrustContent > div > h2")).getText());
	    driver.findElement(By.name("hcpZip")).clear();
	    driver.findElement(By.name("hcpZip")).sendKeys("27613");
	    driver.findElement(By.name("hcpHospitalID")).click();
	    new Select(driver.findElement(By.name("hcpHospitalID"))).selectByVisibleText("1");
	    driver.findElement(By.cssSelector("option[value=\"1\"]")).click();
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("Data for both Zip Code and Hospital ID is not allowed. Please choose either Zip or Hospital ID.", driver.findElement(By.cssSelector("#iTrustContent > div > span")).getText());
		
	    //quit driver
	    driver.quit();
	}

	public void testSurveyResultsZipCodeFormat1() throws Exception {
		// Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        //And now use this to visit iTrust
        driver.get(ADDRESS);
        
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        assertEquals("iTrust - Login", driver.getTitle());

        //Find the text input element by its name
        driver.findElement(By.name("j_username")).sendKeys("2");
        driver.findElement(By.name("j_password")).sendKeys("pw");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
        //Check to make sure this is the correct page
        assertEquals("iTrust - Patient Home", driver.getTitle());
		
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[7]/div/h2")).click();
	    driver.findElement(By.linkText("Satisfaction Survey Results")).click();
	    assertEquals("Search HCP Survey Results", driver.findElement(By.cssSelector("#iTrustContent > div > h2")).getText());
	    driver.findElement(By.name("hcpZip")).clear();
	    driver.findElement(By.name("hcpZip")).sendKeys("123");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("Information not valid", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/h2[2]")).getText());
	    assertEquals("Zip Code: xxxxx or xxxxx-xxxx", driver.findElement(By.cssSelector("div.errorList")).getText());
	
	    //quit driver
	    driver.quit();
	}

	public void testSurveyResultsZipCodeFormat2() throws Exception {
		// Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        //And now use this to visit iTrust
        driver.get(ADDRESS);
        
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        assertEquals("iTrust - Login", driver.getTitle());

        //Find the text input element by its name
        driver.findElement(By.name("j_username")).sendKeys("2");
        driver.findElement(By.name("j_password")).sendKeys("pw");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
        //Check to make sure this is the correct page
        assertEquals("iTrust - Patient Home", driver.getTitle());
		
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[7]/div/h2")).click();
	    driver.findElement(By.linkText("Satisfaction Survey Results")).click();
	    assertEquals("Search HCP Survey Results", driver.findElement(By.cssSelector("#iTrustContent > div > h2")).getText());
	    driver.findElement(By.name("hcpZip")).click();
	    driver.findElement(By.name("hcpZip")).clear();
	    driver.findElement(By.name("hcpZip")).sendKeys("123456");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("Zip Code: xxxxx or xxxxx-xxxx", driver.findElement(By.cssSelector("div.errorList")).getText());

	    //quit driver
	    driver.quit();
	}

	public void testSurveyResultsZipCodeFormat3() throws Exception {
		// Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        //And now use this to visit iTrust
        driver.get(ADDRESS);
        
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        assertEquals("iTrust - Login", driver.getTitle());

        //Find the text input element by its name
        driver.findElement(By.name("j_username")).sendKeys("2");
        driver.findElement(By.name("j_password")).sendKeys("pw");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
        //Check to make sure this is the correct page
        assertEquals("iTrust - Patient Home", driver.getTitle());
		
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[7]/div/h2")).click();
	    driver.findElement(By.linkText("Satisfaction Survey Results")).click();
	    assertEquals("Search HCP Survey Results", driver.findElement(By.cssSelector("#iTrustContent > div > h2")).getText());
	    driver.findElement(By.name("hcpZip")).click();
	    driver.findElement(By.name("hcpZip")).clear();
	    driver.findElement(By.name("hcpZip")).sendKeys("abc");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("Zip Code: xxxxx or xxxxx-xxxx", driver.findElement(By.cssSelector("div.errorList")).getText());
	
	    //quit driver
	    driver.quit();
	}
}