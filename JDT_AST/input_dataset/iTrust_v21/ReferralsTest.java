/**
 * ReferralsTest
 */
package edu.ncsu.csc.itrust.selenium;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

/**
 */
public class ReferralsTest extends iTrustSeleniumTest {
	// Create a new instance of the html unit driver
	private WebDriver driver;

	/**
	 * setting up required web driver
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		driver = new HtmlUnitDriver();
		//navigating to iTrust home page
		driver.get("http://localhost:8080/iTrust/");
	}
	
	/*
	 * testing for creating new referral
	 */
	@Test
	public void testCreateNewReferral() throws Exception {
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("301");
	    driver.findElement(By.xpath("//input[@value='301']")).submit();
	    driver.findElement(By.linkText("01/05/2013")).click();
	    driver.findElement(By.id("add_referral")).click();
	    driver.findElement(By.name("FIRST_NAME")).clear();
	    driver.findElement(By.name("FIRST_NAME")).sendKeys("Gandalf");
	    driver.findElement(By.name("LAST_NAME")).clear();
	    driver.findElement(By.name("LAST_NAME")).sendKeys("Stormcrow");
	    driver.findElement(By.xpath("//input[@value='User Search']")).click();
	    driver.findElement(By.xpath("(//input[@value='9000000003'])[2]")).click();
	    driver.findElement(By.name("referralDetails")).clear();
	    driver.findElement(By.name("referralDetails")).sendKeys("See Gandalf. He will translate the engravings on that ring for you");
	    new Select(driver.findElement(By.name("priority"))).selectByVisibleText("1");
	    driver.findElement(By.id("submitCreate")).click();
	    
	    //we should go back to edit page
	    Assert.assertEquals(driver.getTitle(), "iTrust - Document Office Visit");
	    //check if it contain the provided comment
	    Assert.assertTrue(driver.getPageSource().contains(("Gandalf Stormcrow")));
	    Assert.assertTrue(driver.getPageSource().contains(("See Gandalf. He will translate the engravings on that ring for you")));
	    
	}

	/*
	 * testing for delete exciting referral
	 */
	@Test
	public void testDeleteExistingReferral() throws Exception {
		
		driver.get("http://localhost:8080/iTrust/");
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
	    driver.findElement(By.linkText("Sent Referrals")).click();
	    driver.findElement(By.cssSelector("#editReferralForm > input[type=\"submit\"]")).click();
	    //checking for edit page
	    Assert.assertEquals(driver.getTitle(), "iTrust - Edit Referral");
	    driver.findElement(By.id("submitDelete")).click();
	    driver.findElement(By.id("submitCreate")).click();
	    
	    //going back to referrals page
	    Assert.assertEquals(driver.getTitle(), "iTrust - View Sent Referrals");
	}
	
	/*
	 * testing for modifying exciting referral
	 */
	@Test
	public void testModifyExistingReferral() throws Exception {
		
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2']")).submit();
	    driver.findElement(By.linkText("06/10/2007")).click();
	    driver.findElement(By.cssSelector("form > input[type=\"submit\"]")).click();
	    driver.findElement(By.name("referralDetails")).clear();
	    driver.findElement(By.name("referralDetails")).sendKeys("Gandalf will take care of you--for a price!");
	    driver.findElement(By.id("submitEdit")).click();
	    
	    //we should go back to edit page
	    Assert.assertEquals(driver.getTitle(), "iTrust - Document Office Visit");
	    //check if it contain the provided comment
	    Assert.assertTrue(driver.getPageSource().contains(("Gandalf Stormcrow")));
	    Assert.assertTrue(driver.getPageSource().contains(("Gandalf will take care of you--for a price!")));
	}
	
	/*
	 * testing for viewing sent referral to HCP
	 */
	@Test
	public void testHCPViewSentReferrals() throws Exception {
		driver.get("http://localhost:8080/iTrust/");
	    driver.findElement(By.id("j_username")).sendKeys("9000000003");
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
	    driver.findElement(By.linkText("Sent Referrals")).click();
	    
	    WebElement table_element = driver.findElement(By.id("sentReferralsTable"));
        List<WebElement> tr_collection=table_element.findElements(By.xpath("id('sentReferralsTable')/tbody/tr"));
        String s ="";
	    for(WebElement trElement : tr_collection)
        {
            List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
            for(WebElement tdElement : td_collection)
            {
                s+=tdElement.getText()+" ";
            }
        }
	    String s2 ="     Beaker Beaker Fozzie Bear 12/22/2011\n"
	    		+ "00:00 AM 2  Kelly Doctor Fozzie Bear 10/13/2010\n"
	    		+ "00:00 AM 3  Beaker Beaker Andy Programmer 09/10/2009\n"
	    		+ "00:00 AM 1  Kelly Doctor Random Person 10/10/2008\n"
	    		+ "00:00 AM 3  ";
	    //// original sort is by time stamp (descending)
	    Assert.assertEquals(s2,s);
	}
	
	/*
	 * testing for viewing referral edit by HCP
	 */
	@Test
	public void testHPCViewReferralsEdit() throws Exception {
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("9000000003");
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div")).click();
	    driver.findElement(By.linkText("Sent Referrals")).click();
	    //HCP viewing sent referrals
	    Assert.assertEquals(driver.getTitle(), "iTrust - View Sent Referrals");
	    driver.findElement(By.cssSelector("#editReferralForm > input[type=\"submit\"]")).click();
	    //HCP in editing page
	    Assert.assertEquals(driver.getTitle(), "iTrust - Edit Referral");
	    driver.findElement(By.id("submitEdit")).click();
	    //going back to viewing page
	    Assert.assertEquals(driver.getTitle(), "iTrust - View Sent Referrals");
	}
	
	/*
	 * testing for viewing referral with details by patient
	 */
	@Test
	public void testPatientViewReferralsWithDetails() throws Exception {
		
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("2");
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div")).click();
	    driver.findElement(By.linkText("My Referrals")).click();
	    //Patient in "My Referrals"
	    Assert.assertEquals(driver.getTitle(), "iTrust - View Referrals");
	    
	    driver.findElement(By.linkText("My Referrals")).click();
	    //driver.findElement(By.xpath("(//a[contains(text(),'View')])[5]")).click();
	    
	    //checking for record
	    Assert.assertEquals(driver.getTitle(), "iTrust - View Referrals");
	    Assert.assertTrue(driver.getPageSource().contains(("Gandalf Stormcrow")));
	}
	
	/*
	 * testing sending message to HCP by patient
	 */
	@Test
	public void testPatientSendsMessageToReceivingHCP() throws Exception {
		
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("2");
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Referrals")).click();
	    //needs to be finished
	    Assert.assertEquals(driver.getTitle(), "iTrust - View Referrals");
	}
	
	
	/*
	 * testing viewing referral list by HCP
	 */
	@Test
	public void testHCPViewsReferralsList() throws Exception {
		
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("9000000003");
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
	    driver.findElement(By.linkText("Received Referrals")).click();
	    
	    //checking for records
	    Assert.assertEquals(driver.getTitle(), "iTrust - View Received Referrals");
	    Assert.assertTrue(driver.getPageSource().contains(("Fozzie Bear")));
	    Assert.assertTrue(driver.getPageSource().contains(("Andy Programmer")));
	    Assert.assertTrue(driver.getPageSource().contains(("Dare Devil")));
	}
	
	/*
	 * testing viewing referral form by HCP
	 */
	@Test
	public void testHCPViewOVFromReferral() throws Exception{
		
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("9000000003");
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div")).click();
	    driver.findElement(By.linkText("Received Referrals")).click();
	    driver.findElement(By.linkText("View")).click();
		//needs to be finished
	    Assert.assertTrue(driver.getPageSource().contains(("Dare Devil")));
	}
}