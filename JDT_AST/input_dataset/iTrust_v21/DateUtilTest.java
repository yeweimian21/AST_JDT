package edu.ncsu.csc.itrust.unit;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.ncsu.csc.itrust.DateUtil;
import junit.framework.TestCase;

public class DateUtilTest extends TestCase {

	public void testYearsAgo() throws Exception {
		int yearsAgo = 50;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		String year = "" + (Integer.valueOf(formatter.format(new Date())) - yearsAgo);
		assertEquals(year, DateUtil.yearsAgo(yearsAgo).split("/")[2]);
	}

	public void testYearsFromNow() throws Exception {
		// This test is intended to test the logic of the conversion java.util.Date to the java.sql.Date
		// Along with the intent of "years ago".
		// This test no longer tests the arithmetic of adding/subtracting years, we assume Java got that right
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.YEAR, -2);
		Long twoYearsAgo = cal.getTimeInMillis();
		assertEquals("Date should be within 5s: ", twoYearsAgo, DateUtil.getSQLdateXyearsAgoFromNow(2)
		.getTime(), 5000);
		}
	
	public void testSetSQLMonthRange0() {
		java.sql.Date month1 = new java.sql.Date(0l);
		java.sql.Date month2 = new java.sql.Date(0l);
		int year1 = new GregorianCalendar().get(Calendar.YEAR);
		DateUtil.setSQLMonthRange(month1, 8, 0, month2, 11, 0);
		int year2 = new GregorianCalendar().get(Calendar.YEAR);
		if (year1 != year2)
			DateUtil.setSQLMonthRange(month1, 8, 0, month2, 11, 0);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(month1);
		assertEquals(cal.get(Calendar.YEAR), year2);
		assertEquals(cal.get(Calendar.MONTH), 8);
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), 1);
		cal.setTime(month2);
		assertEquals(cal.get(Calendar.YEAR), year2);
		assertEquals(cal.get(Calendar.MONTH), 11);
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), 31);
	}

	public void testSetSQLMonthRange1() {
		java.sql.Date month1 = new java.sql.Date(0l);
		java.sql.Date month2 = new java.sql.Date(0l);
		int year1 = new GregorianCalendar().get(Calendar.YEAR);
		DateUtil.setSQLMonthRange(month1, 8, 1, month2, 11, 1);
		int year2 = new GregorianCalendar().get(Calendar.YEAR);
		if (year1 != year2)
			DateUtil.setSQLMonthRange(month1, 8, 1, month2, 11, 1);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(month1);
		assertEquals(cal.get(Calendar.YEAR), year2 - 1);
		assertEquals(cal.get(Calendar.MONTH), 8);
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), 1);
		cal.setTime(month2);
		assertEquals(cal.get(Calendar.YEAR), year2 - 1);
		assertEquals(cal.get(Calendar.MONTH), 11);
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), 31);
	}

}
