package edu.ncsu.csc.itrust.unit.bean;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.OphthalmologySurgeryRecordBean;

public class OphthalmologySurgeryRecordBeanTest {
	/**Allowed value for the difference of two 'equivalent' doubles.*/
	private static final double ERROR = 0.000001;

	@Test
	public void testGetSet() throws ParseException{
		OphthalmologySurgeryRecordBean bean1 = new OphthalmologySurgeryRecordBean();
		bean1.setMid(401L);
		bean1.setOid(1L);
		bean1.setLastName("Bridges");
		bean1.setFirstName("Lamar");
		bean1.setVisitDate("01/22/2015");
		bean1.setVaNumOD(27);
		bean1.setVaDenOD(12);
		bean1.setVaNumOS(27);
		bean1.setVaDenOS(12);
		bean1.setSphereOD(12.25);
		bean1.setSphereOS(14.25);
		bean1.setCylinderOD(1.1);
		bean1.setCylinderOS(140.25);
		bean1.setAxisOD(14);
		bean1.setAxisOS(23);
		bean1.setAddOD(2.6);
		bean1.setAddOS(140.75);
		bean1.setSurgery("Laser surgery");
		bean1.setSurgeryNotes("Went well.");
		
		assertEquals(bean1.getMid(), 401L, ERROR);
		assertEquals(bean1.getOid(), 1L, ERROR);
		assertEquals(bean1.getLastName(), "Bridges");
		assertEquals(bean1.getFirstName(), "Lamar");
		assertEquals(bean1.getVisitDateString(), "01/22/2015");
		assertEquals(bean1.getVisitDate(), new SimpleDateFormat("MM/dd/yyyy").parse("01/22/2015"));
		assertEquals(bean1.getVaNumOD().intValue(), 27);
		assertEquals(bean1.getVaDenOD().intValue(), 12);
		assertEquals(bean1.getVaNumOS().intValue(), 27);
		assertEquals(bean1.getVaDenOS().intValue(), 12);
		assertEquals(bean1.getSphereOD().doubleValue(), 12.25, ERROR);
		assertEquals(bean1.getSphereOS().doubleValue(), 14.25, ERROR);
		assertEquals(bean1.getCylinderOD().doubleValue(), 1.1, ERROR);
		assertEquals(bean1.getCylinderOS().doubleValue(), 140.25, ERROR);
		assertEquals(bean1.getAxisOD().intValue(), 14);
		assertEquals(bean1.getAxisOS().intValue(), 23);
		assertEquals(bean1.getAddOD().doubleValue(), 2.6, ERROR);
		assertEquals(bean1.getAddOS().doubleValue(), 140.75, ERROR);
		assertEquals(bean1.getSurgery(), "Laser surgery");
		assertEquals(bean1.getSurgeryNotes(), "Went well.");
	}
	
	@Test
	public void testEquality(){
		OphthalmologySurgeryRecordBean bean1 = new OphthalmologySurgeryRecordBean();
		bean1.setMid(401L);
		bean1.setOid(1L);
		bean1.setLastName("Bridges");
		bean1.setFirstName("Lamar");
		bean1.setVisitDate("01/22/2015");
		bean1.setVaNumOD(27);
		bean1.setVaDenOD(12);
		bean1.setVaNumOS(27);
		bean1.setVaDenOS(12);
		bean1.setSphereOD(12.25);
		bean1.setSphereOS(14.25);
		bean1.setCylinderOD(1.1);
		bean1.setCylinderOS(140.25);
		bean1.setAxisOD(14);
		bean1.setAxisOS(23);
		bean1.setAddOD(2.6);
		bean1.setAddOS(140.75);
		bean1.setSurgery("Laser surgery");
		bean1.setSurgeryNotes("Went well.");
		
		OphthalmologySurgeryRecordBean bean2 = new OphthalmologySurgeryRecordBean();
		bean2.setMid(401L);
		bean2.setOid(1L);
		bean2.setLastName("Bridges");
		bean2.setFirstName("Lamar");
		bean2.setVisitDate("01/22/2015");
		bean2.setVaNumOD(27);
		bean2.setVaDenOD(12);
		bean2.setVaNumOS(27);
		bean2.setVaDenOS(12);
		bean2.setSphereOD(12.25);
		bean2.setSphereOS(14.25);
		bean2.setCylinderOD(1.1);
		bean2.setCylinderOS(140.25);
		bean2.setAxisOD(14);
		bean2.setAxisOS(23);
		bean2.setAddOD(2.6);
		bean2.setAddOS(140.75);
		bean2.setSurgery("Laser surgery");
		bean2.setSurgeryNotes("Went well.");
		
		
		assertEquals(bean1, bean2);
		assertEquals(bean1.toString(), bean2.toString());
		assertEquals(bean1.hashCode(), bean2.hashCode());
		
		
		bean1.setVisitDate(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setVisitDate("01/22/2015");
		
		bean1.setVaNumOD(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setVaNumOD(27);
		
		bean1.setVaDenOD(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setVaDenOD(12);
		
		bean1.setVaNumOS(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setVaNumOS(27);
		
		bean1.setVaDenOS(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setVaDenOS(12);
		
		bean1.setSphereOD(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setSphereOD(12.25);
		
		bean1.setSphereOS(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setSphereOS(14.25);
		
		bean1.setCylinderOD(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setCylinderOD(1.1);
		
		bean1.setCylinderOS(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setCylinderOS(140.235);
		
		bean1.setAxisOD(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setAxisOD(14);
		
		bean1.setAxisOS(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setAxisOS(23);
	
		bean1.setAddOD(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setAddOD(2.6);
		
		bean1.setAddOS(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setAddOS(140.75);
		
		bean1.setSurgery(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setSurgery("Laser surgery");

		bean1.setSurgeryNotes(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setSurgeryNotes("Went well.");
		
		
	}
	
	@Test
	public void testInvalidDates(){
		// Make a bean with an invalid date
		OphthalmologySurgeryRecordBean bean1 = new OphthalmologySurgeryRecordBean();
		bean1.setVisitDate("Not even a date");
		assertNull(bean1.getVisitDate());
		
		bean1.setVisitDate("2015-12-12");
		assertNull(bean1.getVisitDate());
		
		bean1.setVisitDate(null);
		assertNull(bean1.getVisitDate());
	}
	
}
