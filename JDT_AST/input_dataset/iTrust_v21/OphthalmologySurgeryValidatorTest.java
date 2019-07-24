package edu.ncsu.csc.itrust.unit.validate.bean;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.OphthalmologySurgeryRecordBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.OphthalmologySurgeryValidator;

public class OphthalmologySurgeryValidatorTest {

	OphthalmologySurgeryValidator v;
	
	@Before
	public void setUp(){
		v = new OphthalmologySurgeryValidator();
	}
	
	@Test
	public void testValidateAllNull(){
		OphthalmologySurgeryRecordBean badbean = new OphthalmologySurgeryRecordBean();
		try{
			v.validate(badbean);
			fail();
		}catch(FormValidationException e){
			//do nothing, we made it
		}	
	}
	
	@Test
	public void testValidateAxisNoCylinder(){
		OphthalmologySurgeryRecordBean goodbean = new OphthalmologySurgeryRecordBean();
		try{
			goodbean.setVisitDate("10/14/2015");
			goodbean.setLastName("Tran");
			goodbean.setFirstName("Brooke");
			goodbean.setVaNumOD(20);
			goodbean.setVaDenOD(10);
			goodbean.setVaNumOS(20);
			goodbean.setVaDenOS(10);
			goodbean.setSphereOD(1.00);
			goodbean.setSphereOS(1.00);
			//Notice that no cylinder is given
			goodbean.setAxisOD(10);
			goodbean.setAxisOS(10);
			goodbean.setAddOD(1.00);
			goodbean.setAddOS(1.00);
			v.validate(goodbean);
		}catch(FormValidationException e){
			fail(); //this is a valid bean
		}	
	}
	
	@Test
	public void testValidateCylinderNoAxis(){
		OphthalmologySurgeryRecordBean badbean = new OphthalmologySurgeryRecordBean();
		try{
			badbean.setVisitDate("10/14/2015");
			badbean.setLastName("Tran");
			badbean.setFirstName("Brooke");
			badbean.setVaNumOD(20);
			badbean.setVaDenOD(10);
			badbean.setVaNumOS(20);
			badbean.setVaDenOS(10);
			badbean.setSphereOD(1.00);
			badbean.setSphereOS(1.00);
			badbean.setCylinderOD(10.0);
			badbean.setCylinderOS(10.0);
			//Notice that no axis is given
			badbean.setAddOD(1.00);
			badbean.setAddOS(1.00);
			v.validate(badbean);
			fail(); //this is an invalid bean
		}catch(FormValidationException e){
			//do nothing, this bean is supposed to be invalid
		}	
	}
	
	@Test
	public void testValidateLowBoundaries(){
		OphthalmologySurgeryRecordBean goodbean = new OphthalmologySurgeryRecordBean();
		try{
			goodbean.setVisitDate("10/14/2015");
			goodbean.setLastName("Tran");
			goodbean.setFirstName("Brooke");
			goodbean.setVaNumOD(1);
			goodbean.setVaDenOD(1);
			goodbean.setVaNumOS(1);
			goodbean.setVaDenOS(1);
			goodbean.setSphereOD(-10.00);
			goodbean.setSphereOS(-10.00);
			goodbean.setCylinderOD(-10.00);
			goodbean.setCylinderOS(-10.00);
			goodbean.setAxisOD(1);
			goodbean.setAxisOS(1);
			goodbean.setAddOD(.75);
			goodbean.setAddOS(.75);
			v.validate(goodbean);
		}catch(FormValidationException e){
			fail(); //this is a valid bean
		}	
	}
	
	@Test
	public void testValidateHighBoundaries(){
		OphthalmologySurgeryRecordBean goodbean = new OphthalmologySurgeryRecordBean();
		try{
			goodbean.setVisitDate("10/14/2015");
			goodbean.setLastName("Tran");
			goodbean.setFirstName("Brooke");
			goodbean.setVaNumOD(2147483647); //integer max
			goodbean.setVaDenOD(2147483647);
			goodbean.setVaNumOS(2147483647); //integer max
			goodbean.setVaDenOS(2147483647);
			goodbean.setSphereOD(10.00);
			goodbean.setSphereOS(10.00);
			goodbean.setCylinderOD(10.00);
			goodbean.setCylinderOS(10.00);
			goodbean.setAxisOD(180);
			goodbean.setAxisOS(180);
			goodbean.setAddOD(3.00);
			goodbean.setAddOS(3.00);
			v.validate(goodbean);
		}catch(FormValidationException e){
			fail(); //this is a valid bean
		}	
	}
	
	@Test
	public void testValidateNotRounded(){
		OphthalmologySurgeryRecordBean badbean = new OphthalmologySurgeryRecordBean();
		try{
			badbean.setVisitDate("10/14/2015");
			badbean.setLastName("Tran");
			badbean.setFirstName("Brooke");
			badbean.setVaNumOD(1);
			badbean.setVaDenOD(1);
			badbean.setVaNumOS(1);
			badbean.setVaDenOS(1);
			badbean.setSphereOD(9.78);
			badbean.setSphereOS(10.00);
			badbean.setCylinderOD(10.00);
			badbean.setCylinderOS(10.00);
			badbean.setAxisOD(180);
			badbean.setAxisOS(180);
			badbean.setAddOD(3.00);
			badbean.setAddOS(3.00);
			v.validate(badbean);
			fail();
		}catch(FormValidationException e){
			 //do nothing, this bean should fail
		}	
	}
}