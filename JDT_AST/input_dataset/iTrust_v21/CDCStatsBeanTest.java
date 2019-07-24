package edu.ncsu.csc.itrust.unit.bean;

import edu.ncsu.csc.itrust.beans.CDCStatsBean;
import junit.framework.TestCase;

public class CDCStatsBeanTest extends TestCase {
	private CDCStatsBean stats;
	
	@Override
	protected void setUp() throws Exception {
		stats = new CDCStatsBean();
		stats.setSex(0);
		stats.setAge(0);
		stats.setL(0);
		stats.setM(0);
		stats.setS(0);
	}
	
	public void testChangeSex() {
		int testValue = 1;
		assertEquals(0, stats.getSex());
		stats.setSex(testValue);
		assertEquals(testValue, stats.getSex());
	}
	
	public void testChangeAge() {
		float testValue = 36;
		assertEquals((float) 0, stats.getAge());
		stats.setAge(testValue);
		assertEquals(testValue, stats.getAge());
	}
	
	public void testChangeLStat() {
		double testValue = 5.0;
		assertEquals(0.0, stats.getL());
		stats.setL(testValue);
		assertEquals(testValue, stats.getL());
	}
	
	public void testChangeMStat() {
		double testValue = 5.0;
		assertEquals(0.0, stats.getM());
		stats.setM(testValue);
		assertEquals(testValue, stats.getM());
	}
	
	public void testChangeSStat() {
		double testValue = 5.0;
		assertEquals(0.0, stats.getS());
		stats.setS(testValue);
		assertEquals(testValue, stats.getS());
	}
}
