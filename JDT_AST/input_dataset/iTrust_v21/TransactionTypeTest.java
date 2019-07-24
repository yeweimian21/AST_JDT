package edu.ncsu.csc.itrust.unit.enums;

import edu.ncsu.csc.itrust.enums.TransactionType;
import junit.framework.TestCase;

public class TransactionTypeTest extends TestCase {
	public void testParse() throws Exception {
		for(TransactionType type : TransactionType.values()){
			assertEquals(type, TransactionType.parse(type.getCode()));
		}
	}
	
	public void testBadParse() throws Exception {
		try{
			TransactionType.parse(37);
			TransactionType.parse(99);
			fail("exception should have been thrown");
		} catch(IllegalArgumentException e){
			assertEquals("No transaction type exists for code 37", e.getMessage());
		}
	}
	
}
