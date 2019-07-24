package edu.ncsu.csc.itrust.unit.bean;

import edu.ncsu.csc.itrust.beans.WardRoomBean;
import junit.framework.TestCase;

/**
 * WardRoomBeanTest
 */
public class WardRoomBeanTest extends TestCase {
	
	WardRoomBean wrb1;
	WardRoomBean wrb2;
	WardRoomBean wrb3;
	
	/**
	 * setUp
	 */
	public void setUp(){
		wrb1 = new WardRoomBean(0L, 0L, 0L, "", "");
		wrb2 = new WardRoomBean(0L, 0L, 0L, "", "");
		wrb3 = new WardRoomBean(0L, 0L, 0L, "", "");
	}
	
	/**
	 * testRoomID
	 */
	public void testRoomID(){
		wrb1.setRoomID(1L);
		assertEquals(1L, wrb1.getRoomID());
	}
	
	/**
	 * testOccupiedBy
	 */
	public void testOccupiedBy(){
		wrb1.setOccupiedBy(1L);
		assertTrue(1L == wrb1.getOccupiedBy());
	}
	
	/**
	 * testInWard
	 */
	public void testInWard(){
		wrb1.setInWard(1L);
		assertTrue(1L == wrb1.getInWard());
	}
	
	/**
	 * testRoomName
	 */
	public void testRoomName(){
		wrb1.setRoomName("name");
		assertTrue(wrb1.getRoomName().equals("name"));
	}
	
	/**
	 * testRoomStatus
	 */
	public void testRoomStatus(){
		wrb1.setStatus("name");
		assertTrue(wrb1.getStatus().equals("name"));
	}
	
	/**
	 * testEquals
	 */
	public void testEquals(){
		assertTrue(wrb2.equals(wrb3));
	}

}
