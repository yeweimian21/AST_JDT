package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Use Case 24
 */
public class SurveyUseCaseTest extends iTrustSeleniumTest {
	
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.hcp0();
		gen.patient2();		
	}

	/*
	 * Precondition:
	 * Patient 2 and HCP 9000000000 are in the system. 
	 * Patient 2 had an office visit with HCP 9000000000 on 6/10/2007. 
	 * Patient 2 has successfully authenticated.
	 * Description:
	 * Patient 2 chooses to view his records.
	 * Patient 2 clicks a link next to his office visit on 6/10/2007 to take satisfaction survey.
	 * He inputs the following information and submits:
	 * 15 minutes 
	 * 10 minutes 
	 * 3 
	 * 5
	 * Expected Results:
	 * The survey answers are stored and the event is logged.
	 */
	public void testTakeSatisfactionSurveySuccess() throws Exception {
		WebDriver driver = login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		driver.findElement(By.xpath("//a[text()='View My Records']")).click();
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Patient Information"));
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");			
		
		driver.findElement(By.xpath("(//a[contains(text(),'Complete Visit Survey')])[1]")).click();	
		assertEquals("iTrust Patient Survey for Office Visit on Jun 10, 2007", driver.findElement(By.xpath("//h1")).getText());
		driver.findElement(By.name("waitingMinutesString")).sendKeys("15");
		driver.findElement(By.name("examMinutesString")).sendKeys("10");
		driver.findElement(By.name("Satradios")).sendKeys("satRadio3");
		driver.findElement(By.name("Treradios")).sendKeys("treRadio5");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Survey Successfully Submitted"));
		assertLogged(TransactionType.SATISFACTION_SURVEY_TAKE, 2L, 2L, "");
		
		// make sure survey cannot be taken again
		driver.findElement(By.xpath("(//a[contains(text(),'Complete Visit Survey')])[1]")).click();	
		assertNotSame("iTrust Patient Survey for Office Visit on Jun 10, 2007", driver.findElement(By.xpath("//h1")).getText());
		
	}
	
	/*
	 * Precondition:
	 * Patient 2 and HCP 9000000000 are in the system. 
	 * Patient 2 had an office visit with HCP 9000000000 on 6/10/2007. 
	 * Patient 2 has successfully authenticated.
	 * Description:
	 * Patient 2 chooses to view his records.
	 * Patient 2 clicks a link next to his office visit on 6/10/2007 to take satisfaction survey.
	 * He inputs the following information and submits:
	 * [none] 
	 * 10 minutes 
	 * 3 
	 * [none]
	 * Expected Results:
	 * The survey answers are stored and the event is logged.
	 */
	public void testTakeSatisfactionSurveySuccess2() throws Exception{
		WebDriver driver = login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		driver.findElement(By.xpath("//a[text()='View My Records']")).click();
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Patient Information"));
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
		
		driver.findElement(By.xpath("(//a[contains(text(),'Complete Visit Survey')])[1]")).click();	
		assertEquals("iTrust Patient Survey for Office Visit on Jun 10, 2007", driver.findElement(By.xpath("//h1")).getText());
		driver.findElement(By.name("examMinutesString")).sendKeys("10");
		driver.findElement(By.name("Satradios")).sendKeys("satRadio3");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Survey Successfully Submitted"));
		assertLogged(TransactionType.SATISFACTION_SURVEY_TAKE, 2L, 2L, "");
		
		// make sure survey cannot be taken again
		driver.findElement(By.xpath("(//a[contains(text(),'Complete Visit Survey')])[1]")).click();	
		assertNotSame("iTrust Patient Survey for Office Visit on Jun 10, 2007", driver.findElement(By.xpath("//h1")).getText());

	}
	
	/*
	 * Precondition:
	 * Patient 2 and HCP 9000000000 are in the system. 
	 * Patient 2 had an office visit with HCP 9000000000 on 6/10/2007. 
	 * Patient 2 has successfully authenticated.
	 * Description:
	 * Patient 2 chooses to view his records.
	 * Patient 2 clicks a link next to his office visit on 6/10/2007 to take satisfaction survey.
	 * Patient 2 changes his mind and decides to cancel his input.
	 */
	public void testTakeSatisfactionSurveyCancel() throws Exception{
		WebDriver driver = login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		driver.findElement(By.xpath("//a[text()='View My Records']")).click();
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Patient Information"));
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
		
		driver.findElement(By.xpath("(//a[contains(text(),'Complete Visit Survey')])[1]")).click();	
		assertEquals("iTrust Patient Survey for Office Visit on Jun 10, 2007", driver.findElement(By.xpath("//h1")).getText());
		driver.findElement(By.name("examMinutesString")).sendKeys("10");
		driver.findElement(By.name("Satradios")).sendKeys("satRadio3");
		
		// patient changes his mind and cancels his input
		driver.findElement(By.xpath("//a[text()='Home']")).click();
		assertEquals("iTrust - Patient Home", driver.getTitle());
		driver.findElement(By.xpath("//a[text()='View My Records']")).click();
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Patient Information"));
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
		
		// make sure survey CAN still be taken.  This will throw an exception if the survey is not available
		driver.findElement(By.xpath("(//a[contains(text(),'Complete Visit Survey')])[1]")).click();	
		assertEquals("iTrust Patient Survey for Office Visit on Jun 10, 2007", driver.findElement(By.xpath("//h1")).getText());
	}	
}