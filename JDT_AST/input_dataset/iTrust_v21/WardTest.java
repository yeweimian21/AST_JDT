package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * WardTest
 */
@SuppressWarnings("unused")
public class WardTest extends iTrustSeleniumTest {
	
	private HtmlUnitDriver driver;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		//gen.insertwards();
		//gen.hcp0();

	}

	
	//6. Heart Doctor will assign 3 patients to the cardiac ward. If Heart Doctor tries to assign yet another, an error will be displayed static the ward is full.
	/**
	 * testhcpaddremovepatient
	 * @throws Exception
	 */
    public void testhcpaddremovepatient() throws Exception {
        driver = (HtmlUnitDriver)login("9000000000","pw");
        driver.findElement(By.linkText("Manage Wards")).click();
        new Select(driver.findElement(By.name("searchbyroomWard"))).selectByVisibleText("Pediatrics");
        driver.findElement(By.name("searchWards")).click();
        driver.findElement(By.name("removePatient")).click();
        new Select(driver.findElement(By.name("searchbyroomWard"))).selectByVisibleText("Pediatrics");
        driver.findElement(By.name("searchWards")).click();
        driver.findElement(By.name("assignPatient")).click();
        //select patient 1
        driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='1']")).submit();
 		assertTrue(driver.getPageSource().contains("Random Person"));
    }
    
    /**
     * testadminaddremoveward
     * @throws Exception
     */
	public void testadminaddremoveward() throws Exception {
		driver = (HtmlUnitDriver)login("9000000001","pw");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Manage Wards")).click();
	    new Select(driver.findElement(By.name("hospitals"))).selectByVisibleText("Facebook Rehab Center");
	    driver.findElement(By.name("selectHospital")).click();
	    driver.findElement(By.name("ward")).clear();
	    driver.findElement(By.name("ward")).sendKeys("ChatAddictionClinic");
	    driver.findElement(By.name("addWard")).click();
		assertTrue(driver.getPageSource().contains("ChatAddictionClinic"));
	    driver.findElement(By.name("removeWardButton")).click();
		//check make sure table is gone
		assertFalse(driver.getPageSource().contains("ChatAddictionClinic"));		
		
		
		
	}
	
	 //5. Admin will assigned "Heart Doctor (Heart Surgeon)" to the cardiac ward and "Baby Doctor (Pediatrician)" to the two pediatric wards. 
	 //  If the admin assigns the wrong doctor to the wrong ward, an error will be displayed.
	  //changed baby doctor to kelly doctor because testdata is already present
	/**
	 * testadminassignhcp
	 * @throws Exception
	 */
	public void testadminassignhcp() throws Exception {
		driver = (HtmlUnitDriver)login("9000000001","pw");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Manage Wards")).click();
	    new Select(driver.findElement(By.name("hospitals"))).selectByVisibleText("Health Institute Dr. E");
	    driver.findElement(By.name("selectHospital")).click();
	    Select hcpToAdd = new Select(driver.findElement(By.name("HCPtoAdd")));
	    hcpToAdd.selectByVisibleText("Kelly Doctor");
	    // ERROR: Caught exception [ERROR: Unsupported command [addSelection | name=HCPtoAdd | label=Kelly Doctor]]
	    driver.findElement(By.name("addHCP")).click();
	    Select hcpToRemove = new Select(driver.findElement(By.name("HCPtoRemove")));
	    hcpToRemove.selectByVisibleText("Kelly Doctor");
	    driver.findElement(By.name("removeHCP")).click();
	    // ERROR: Caught exception [ERROR: Unsupported command [addSelection | name=HCPtoRemove | label=Kelly Doctor]]		
	}
	
//4. Admin will create a three test wards to Central Hospital, and proceed some related operations on it, such as creating testing rooms.
	/**
	 * testaddwardtohospital
	 * @throws Exception
	 */
	public void testaddwardtohospital() throws Exception {
		driver = (HtmlUnitDriver)login("9000000001","pw");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Manage Wards")).click();
	    new Select(driver.findElement(By.name("hospitals"))).selectByVisibleText("Central Hospital");
	    driver.findElement(By.name("selectHospital")).click();
	    driver.findElement(By.name("ward")).clear();
	    driver.findElement(By.name("ward")).sendKeys("TestOneWard");
	    driver.findElement(By.name("addWard")).click();
	    driver.findElement(By.name("ward")).clear();
	    driver.findElement(By.name("ward")).sendKeys("TestTwoWard");
	    driver.findElement(By.name("addWard")).click();
	    driver.findElement(By.name("ward")).clear();
	    driver.findElement(By.name("ward")).sendKeys("TestThreeWard");
	    driver.findElement(By.name("addWard")).click();
	    driver.findElement(By.name("room")).clear();
	    driver.findElement(By.name("room")).sendKeys("TestOneRoom");
	    driver.findElement(By.name("addRoomButton")).click();
	    driver.findElement(By.name("room")).clear();
	    driver.findElement(By.name("room")).sendKeys("TestTwoRoom");
	    driver.findElement(By.name("addRoomButton")).click();
	    driver.findElement(By.xpath("(//input[@name='room'])[2]")).clear();
	    driver.findElement(By.xpath("(//input[@name='room'])[2]")).sendKeys("TestOneRoom");
	    driver.findElement(By.xpath("(//input[@name='addRoomButton'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@name='room'])[3]")).clear();
	    driver.findElement(By.xpath("(//input[@name='room'])[3]")).sendKeys("TestOneRoom");
	    driver.findElement(By.xpath("(//input[@name='addRoomButton'])[3]")).click();
	    Select hcpToAdd = new Select(driver.findElement(By.name("HCPtoAdd")));
	    hcpToAdd.selectByVisibleText("Shelly Vang");
	    // ERROR: Caught exception [ERROR: Unsupported command [addSelection | name=HCPtoAdd | label=Shelly Vang]]
	    driver.findElement(By.name("ward")).clear();
	    driver.findElement(By.name("ward")).sendKeys("Pediatric");
	    driver.findElement(By.name("addWard")).click();
	    driver.findElement(By.name("ward")).clear();
	    driver.findElement(By.name("ward")).sendKeys("Cardiac");
	    driver.findElement(By.name("addWard")).click();
	    hcpToAdd = new Select(driver.findElement(By.name("HCPtoAdd")));
	    hcpToAdd.selectByVisibleText("Shelly Vang");
	    // ERROR: Caught exception [ERROR: Unsupported command [addSelection | name=HCPtoAdd | label=Shelly Vang]]
	    driver.findElement(By.name("addHCP")).click();
	    driver.findElement(By.name("room")).clear();
	    driver.findElement(By.name("room")).sendKeys("TestOneRoomCardiac");
	    driver.findElement(By.name("addRoomButton")).click();
	    //Check if all wards have been created and rooms.
	    assertTrue(driver.getPageSource().contains("TestOneWard"));
	    assertTrue(driver.getPageSource().contains("TestTwoWard"));
	    assertTrue(driver.getPageSource().contains("TestThreeWard"));
	    assertTrue(driver.getPageSource().contains("TestOneRoom"));
	    assertTrue(driver.getPageSource().contains("TestTwoRoom"));
	    assertTrue(driver.getPageSource().contains("Pediatric"));
		
	}
	

}