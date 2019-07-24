package edu.ncsu.csc.itrust.unit.bean;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.ObstetricsRecordBean;
import edu.ncsu.csc.itrust.enums.DeliveryType;
import edu.ncsu.csc.itrust.enums.PregnancyStatus;

public class ObstetricsRecordBeanTest {
	private static final double E = 0.000001; //allowable error for double compare
	@Test
	public void testGetSet() throws ParseException {
		ObstetricsRecordBean bean = new ObstetricsRecordBean();
		bean.setMid(1L);
		bean.setOid(2L);
		bean.setPregId(1L);
		bean.setLmp("01/05/2014");
		bean.setEdd("10/11/2014");
		bean.setWeeksPregnant("35-1");
		bean.setDateVisit("05/06/2014");
		bean.setYearConception(2001);
		bean.setHoursInLabor(6.12);
		bean.setDeliveryType(DeliveryType.Miscarriage);
		bean.setPregnancyStatus(PregnancyStatus.Initial);
		bean.setWeight(154.23);
		bean.setBloodPressureS(101);
		bean.setBloodPressureD(57);
		bean.setFhr(72);
		bean.setFhu(20);
		
		assertEquals(bean.getMid(), 1L);
		assertEquals(bean.getOid(), 2L);
		assertEquals(bean.getPregId(), 1L);
		assertEquals(bean.getLmp(), new SimpleDateFormat("MM/dd/yyyy").parse("01/05/2014"));
		assertEquals(bean.getLmpString(), "01/05/2014");
		assertEquals(bean.getEdd(), new SimpleDateFormat("MM/dd/yyyy").parse("10/11/2014"));
		assertEquals(bean.getEddString(), "10/11/2014");
		assertEquals(bean.getWeeksPregnant(), "35-1");
		assertEquals(bean.getDateVisit(), new SimpleDateFormat("MM/dd/yyyy").parse("05/06/2014"));
		assertEquals(bean.getDateVisitString(), "05/06/2014");
		assertEquals(bean.getYearConception(), 2001);
		assertEquals(bean.getHoursInLabor(), 6.12, E);
		assertEquals(bean.getDeliveryType(), DeliveryType.Miscarriage);
		assertEquals(bean.getPregnancyStatus(), PregnancyStatus.Initial);
		assertEquals(bean.getWeight(), 154.23, E);
		assertEquals(bean.getBloodPressureS(), 101);
		assertEquals(bean.getBloodPressureD(), 57);
		assertEquals(bean.getFhr(), 72);
		assertEquals(bean.getFhu(), 20, E);
	}
	
	@Test
	public void testEqualsToString() {
		ObstetricsRecordBean bean1 = new ObstetricsRecordBean();
		bean1.setMid(1L);
		bean1.setOid(2L);
		bean1.setPregId(1L);
		bean1.setLmp("01/05/2014");
		bean1.setEdd("10/11/2014");
		bean1.setWeeksPregnant("35-1");
		bean1.setDateVisit("05/06/2014");
		bean1.setYearConception(2001);
		bean1.setHoursInLabor(6.12);
		bean1.setDeliveryType(DeliveryType.Miscarriage);
		bean1.setPregnancyStatus(PregnancyStatus.Initial);
		bean1.setWeight(154.23);
		bean1.setBloodPressureS(101);
		bean1.setBloodPressureD(57);
		bean1.setFhr(72);
		bean1.setFhu(20);
		
		ObstetricsRecordBean bean2 = new ObstetricsRecordBean();
		bean2.setMid(1L);
		bean2.setOid(2L);
		bean2.setPregId(1L);
		bean2.setLmp("01/05/2014");
		bean2.setEdd("10/11/2014");
		bean2.setWeeksPregnant("35-1");
		bean2.setDateVisit("05/06/2014");
		bean2.setYearConception(2001);
		bean2.setHoursInLabor(6.12);
		bean2.setDeliveryType(DeliveryType.Miscarriage);
		bean2.setPregnancyStatus(PregnancyStatus.Initial);
		bean2.setWeight(154.23);
		bean2.setBloodPressureS(101);
		bean2.setBloodPressureD(57);
		bean2.setFhr(72);
		bean2.setFhu(20);
		
		assertEquals(bean1, bean2); //object compare
		assertEquals(bean1.toString(), bean2.toString()); //string compare
		assertEquals(bean1.hashCode(), bean2.hashCode()); //hashcode compare
		
		//now, check that if I change one the comparison fails
		bean2.setWeight(96.1);
		assertFalse(bean1.equals(bean2));
		bean2.setWeight(154.23);
		bean2.setEdd("05/06/2014");
		assertFalse(bean1.equals(bean2));
		bean2.setEdd("10/11/2014");
		bean2.setFhr(71);
		assertFalse(bean1.equals(bean2));
		bean2.setFhr(72);
		bean2.setBloodPressureD(102);
		assertFalse(bean1.equals(bean2));
		bean2.setBloodPressureD(57);
		bean2.setDateVisit(null);
		assertFalse(bean1.equals(bean2));
		bean2.setDateVisit("05/06/2014");
		bean2.setDeliveryType(DeliveryType.Caesarean);
		assertFalse(bean1.equals(bean2));
		bean2.setDeliveryType(DeliveryType.Miscarriage);
		bean2.setFhu(62.3);
		assertFalse(bean1.equals(bean2));
		bean2.setFhu(20);
	}
	
	@Test
	public void testError() {
		//make a bean with improperly formatted dates
		ObstetricsRecordBean bean3 = new ObstetricsRecordBean();
		bean3.setMid(1L);
		bean3.setOid(2L);
		bean3.setPregId(1L);
		bean3.setLmp("01234");
		bean3.setEdd("01234");
		bean3.setWeeksPregnant("35-1");
		bean3.setDateVisit("01234");
		bean3.setYearConception(2001);
		bean3.setHoursInLabor(6.12);
		bean3.setDeliveryType(DeliveryType.Miscarriage);
		bean3.setPregnancyStatus(PregnancyStatus.Initial);
		bean3.setWeight(154.23);
		bean3.setBloodPressureS(101);
		bean3.setBloodPressureD(57);
		bean3.setFhr(72);
		bean3.setFhu(20);
		
		assertNull(bean3.getLmp());
		assertNull(bean3.getEdd());
		assertNull(bean3.getDateVisit());
		
		//make a bean with null dates
		ObstetricsRecordBean bean4 = new ObstetricsRecordBean();
		bean4.setMid(1L);
		bean4.setOid(2L);
		bean4.setPregId(1L);
		bean4.setLmp(null);
		bean4.setEdd(null);
		bean4.setWeeksPregnant("35-1");
		bean4.setDateVisit(null);
		bean4.setYearConception(2001);
		bean4.setHoursInLabor(6.12);
		bean4.setDeliveryType(DeliveryType.Miscarriage);
		bean4.setPregnancyStatus(PregnancyStatus.Initial);
		bean4.setWeight(154.23);
		bean4.setBloodPressureS(101);
		bean4.setBloodPressureD(57);
		bean4.setFhr(72);
		bean4.setFhu(20);
		
		assertNull(bean4.getLmp());
		assertNull(bean4.getEdd());
		assertNull(bean4.getDateVisit());
	}
}
