package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ViewMyReportRequestsTest extends iTrustSeleniumTest {

	private HtmlUnitDriver driver;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.hcp0();
		gen.patient2();
	}
	
	public void testViewMyReportRequests() throws Exception{
		
		driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.cssSelector("div.panel-heading")).click();
	    driver.findElement(By.linkText("My Report Requests")).click();
	    driver.findElement(By.linkText("Add a new Report Request")).click();
	    //search for patient 2
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		
		//the button to click should have the text of the MID
		driver.findElement(By.cssSelector("input[value='2']")).submit();
		TableElement table = new TableElement( driver.findElement(By.tagName("table")));
		assertLogged(TransactionType.COMPREHENSIVE_REPORT_ADD, 9000000000L, 2L, "");
		assertTrue(table.getTableCell(2, 4).getText().contains("Requested"));
	    driver.findElement(By.linkText("View")).click();
	    assertEquals("iTrust - Comprehensive Patient Report", driver.getTitle());
	    driver.findElement(By.cssSelector("div.panel-heading")).click();
	    driver.findElement(By.linkText("My Report Requests")).click();
		table = new TableElement( driver.findElement(By.tagName("table")));
		assertTrue(table.getTableCell(2, 4).getText().contains("Viewed"));
	    driver.findElement(By.linkText("View")).click();
	    assertEquals("iTrust - Comprehensive Patient Report", driver.getTitle());
		assertLogged(TransactionType.COMPREHENSIVE_REPORT_VIEW, 9000000000L, 2L, "");


	}
	
	/**
	 * TableElement a helper class for Selenium test htmlunitdriver retrieving
	 * data from tables.
	 */
	private class TableElement {
		List<List<WebElement>> table;
		
		/**
		 * Constructor.
		 * This object will help user to get data from each cell of the table.
		 * @param tableElement The table WebElement.
		 */
		public TableElement(WebElement tableElement) {
			// TODO Auto-generated constructor stub
			table = new ArrayList<List<WebElement>>();
			List<WebElement> trCollection = tableElement.findElements(By.xpath("tbody/tr"));
			for(WebElement trElement : trCollection){
				List<WebElement> tdCollection = trElement.findElements(By.xpath("td"));
				table.add(tdCollection);
			}
			
		}
		/**
		 * Get data from given row and column cell.
		 * @param row (start from 0)
		 * @param column(start from 0)
		 * @return The WebElement in that given cell.
		 */
		public WebElement getTableCell(int row, int column){
			return table.get(row).get(column);
		}
		
		

	}

}