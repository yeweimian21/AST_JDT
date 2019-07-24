package edu.ncsu.csc.itrust.unit;

import java.util.ArrayList;
import java.util.Scanner;

import edu.ncsu.csc.itrust.CSVParser;
import edu.ncsu.csc.itrust.exception.CSVFormatException;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import junit.framework.TestCase;

public class CSVParserTest extends TestCase {

	public void testCSVParseHeader(){
		Scanner CSVHeader=new Scanner("test1,test2,test3");
		CSVParser test=null;
		try{
			test=new CSVParser(CSVHeader);
		}catch(Exception e){
			assertTrue(false);
		}
		ArrayList<String> header=test.getHeader();
		assertEquals(3, header.size());
		assertEquals("test1", header.get(0));
		assertEquals("test2", header.get(1));
		assertEquals("test3", header.get(2));
		assertFalse(test.getErrors().hasErrors());
	}
	
	public void testCSVParseData(){
		Scanner CSVHeader=new Scanner("test1,test2,test3\ntesting,testing,123");
		CSVParser test=null;
		try{
			test=new CSVParser(CSVHeader);
		}catch(Exception e){
			assertTrue(false);
		}
		ArrayList<ArrayList<String>> data=test.getData();
		assertEquals(1, data.size());
		assertEquals(3, data.get(0).size());
		assertEquals("testing", data.get(0).get(0));
		assertEquals("testing", data.get(0).get(1));
		assertEquals("123", data.get(0).get(2));
		assertFalse(test.getErrors().hasErrors());
	}
	@SuppressFBWarnings(value="DLS_DEAD_LOCAL_STORE")
	public void testEmptyCSV(){
		Scanner CSVHeader=new Scanner("");
		@SuppressWarnings("unused")
		CSVParser test=null;
		try{
			test=new CSVParser(CSVHeader);
		}catch(CSVFormatException e){
			//CSVFormatException is good here
			return;
		}catch(Exception e){
			//Other exceptions are bad
			assertTrue(false);
		}
	}
	
	public void testWrongNumberFields(){
		Scanner CSVHeader=new Scanner("test1,test2,test3\ntesting,123");
		CSVParser test=null;
		try{
			test=new CSVParser(CSVHeader);
		}catch(Exception e){
			assertTrue(false);
		}
		ArrayList<ArrayList<String>> data=test.getData();
		assertEquals(0, data.size());
		assertTrue(test.getErrors().hasErrors());
		assertEquals("[Field number mismatch on line 2]", test.getErrors().toString());
	}
	
	public void testCSVCommaInField(){
		Scanner CSVHeader=new Scanner("\"test,1\",\"test,2\",\"test,3\"");
		CSVParser test=null;
		try{
			test=new CSVParser(CSVHeader);
		}catch(Exception e){
			assertTrue(false);
		}
		ArrayList<String> header=test.getHeader();
		assertEquals(3, header.size());
		assertEquals("test,1", header.get(0));
		assertEquals("test,2", header.get(1));
		assertEquals("test,3", header.get(2));
		assertFalse(test.getErrors().hasErrors());
	}
	
	public void testCSVEndsWhileInQuote(){
		Scanner CSVHeader=new Scanner("test1,test2,test3\ntesting,testing,\"123");
		CSVParser test=null;
		try{
			test=new CSVParser(CSVHeader);
		}catch(Exception e){
			assertTrue(false);
		}
		ArrayList<ArrayList<String>> data=test.getData();
		assertEquals(0, data.size());
		assertEquals("[Line ended while inside quotes on line 2]", test.getErrors().toString());
	}
}
