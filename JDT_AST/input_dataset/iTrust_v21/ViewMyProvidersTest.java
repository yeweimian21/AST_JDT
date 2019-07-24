package edu.ncsu.csc.itrust.selenium;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;



/**
 * Test class for the viewVisitedHCPs.jsp
 */
public class ViewMyProvidersTest extends iTrustSeleniumTest {

	private HtmlUnitDriver driver;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp(); // clear tables is called in super
		gen.clearAllTables();
		gen.standardData();
	}

	/**
	 * testViewMyProviders1
	 * @throws Exception
	 */
	public void testViewMyProviders1() throws Exception {
		driver = (HtmlUnitDriver)login("1","pw");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Providers")).click();
		assertEquals("iTrust - My Providers", driver.getTitle());
		TableElement table = new TableElement(driver.findElement(By.id("hcp_table")));
		assertEquals(4, table.getRowSize());
		assertEquals("Gandalf Stormcrow", table.getCellAsText(1, 0));
		assertEquals("Kelly Doctor", table.getCellAsText(2, 0));
	    driver.findElement(By.name("filter_name")).clear();
	    driver.findElement(By.name("filter_name")).sendKeys("Doctor");
	    driver.findElement(By.name("update_filter")).click();
	    assertEquals("iTrust - My Providers", driver.getTitle());
	    table = new TableElement(driver.findElement(By.id("hcp_table")));
	    // Only Kelly Doctor should be listed now.
	 	assertEquals(3, table.getRowSize());
	 	assertEquals("Kelly Doctor", table.getCellAsText(1, 0));
		// Gandalf Stormcrow is no longer listed.
	 	assertFalse("Gandalf Stormcrow".equals(table.getCellAsText(2, 0)));
	}
	
	/**
	 * testViewMyProviders2
	 * @throws Exception
	 */
	public void testViewMyProviders2() throws Exception {
		driver = (HtmlUnitDriver)login("1","pw");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Providers")).click();
		assertEquals("iTrust - My Providers", driver.getTitle());
		TableElement table = new TableElement(driver.findElement(By.id("hcp_table")));
		assertEquals(4, table.getRowSize());
		assertEquals("Gandalf Stormcrow", table.getCellAsText(1, 0));
		assertEquals("Kelly Doctor", table.getCellAsText(2, 0));
		
	    driver.findElement(By.name("filter_specialty")).clear();
	    driver.findElement(By.name("filter_specialty")).sendKeys("surgeon");
	    driver.findElement(By.name("update_filter")).click();
	    assertEquals("iTrust - My Providers", driver.getTitle());
	    table = new TableElement(driver.findElement(By.id("hcp_table")));
	    // Only Kelly Doctor should be listed now.
	 	assertEquals(3, table.getRowSize());
	 	assertEquals("Kelly Doctor", table.getCellAsText(1, 0));
		// Gandalf Stormcrow is no longer listed.
	 	assertFalse("Gandalf Stormcrow".equals(table.getCellAsText(2, 0)));
	}
	
	/**
	 * TableElement a helper class for Selenium test htmlunitdriver retrieving
	 * data from tables.
	 */
	private class TableElement {
		WebElement tableElement;
		List<List<WebElement>> table;
		
		/**
		 * Constructor.
		 * This object will help user to get data from each cell of the table.
		 * @param tableElement The table WebElement.
		 */
		public TableElement(WebElement tableElement) {
			this.tableElement = tableElement;
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
		public String getCellAsText(int row, int column){
			return table.get(row).get(column).getText();
		}
		
		public int getRowSize(){
			 return tableElement.findElements(By.xpath("tbody/tr")).size();
		}
		

	}

}