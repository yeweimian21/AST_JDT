package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class DetermineOperationalProfileTest extends iTrustSeleniumTest {
	

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.uap1();
		gen.tester();
	}


	/**
	 * Precondition: Sample data is in the database. CreatePatient2 has passed.
	 * Login with user 9999999999 and password pw.
	 */
	public void testDetermineOperationalProfile() throws Exception {
		// login as uap and add a patient
		WebDriver wd = login("8000000009", "uappass1");
		wd.get(ADDRESS + "auth/uap/home.jsp");
		assertEquals("iTrust - UAP Home", wd.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
		
		wd.findElement(By.linkText("Add Patient")).click();
		WebElement firstName = wd.findElement(By.name("firstName"));
		WebElement lastName = wd.findElement(By.name("lastName"));
		WebElement email = wd.findElement(By.name("email"));
		
		firstName.sendKeys("bob");
		lastName.sendKeys("bob");
		email.sendKeys("bob@bob.com");
		
		firstName.submit();

	}
	
	public int getRowNumber(String description)
	{
		TransactionType[] values = TransactionType.values();
		int rownumber = 0;
		for (int i=0; i<values.length; i++)
		{
			if (description.equals(values[i].getDescription()))
				rownumber = i+1;
		}
		
		return rownumber;
	}
}
