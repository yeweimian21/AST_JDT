package edu.ncsu.csc.itrust.unit.dao.LOINC;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.LOINCbean;
import edu.ncsu.csc.itrust.dao.mysql.LOINCDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class LOINCDAOTest extends TestCase {
	private LOINCDAO lDAO = TestDAOFactory.getTestInstance().getLOINCDAO();
	
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.loincs();
	}

	public void testAddLOINC() throws Exception {
		LOINCbean lb = new LOINCbean();
		lb.setLabProcedureCode("33333-3");
		lb.setComponent("boo");
		lb.setKindOfProperty("");
		lb.setMethodType(" ");
		lb.setScaleType("453");
		lb.setSystem("Windows");
		lb.setTimeAspect("30 minutes");	
		lDAO.addLOINC(lb);
		LOINCbean lb2 = lDAO.getProcedures("33333-3").get(0);
		assertEquals("boo", lb2.getComponent());
	}
	
	public void testUpdate() throws Exception {
		LOINCbean lb = new LOINCbean();
		lb.setLabProcedureCode("77777-3");
		lb.setComponent("boo");
		lb.setKindOfProperty("");
		lb.setMethodType(" ");
		lb.setScaleType("453");
		lb.setSystem("Windows");
		lb.setTimeAspect("30 minutes");	
		lDAO.addLOINC(lb);
		
		lb = new LOINCbean();
		lb.setLabProcedureCode("77777-3");
		lb.setComponent("works");
		lb.setKindOfProperty("");
		lb.setMethodType(" ");
		lb.setScaleType("453");
		lb.setSystem("Windows");
		lb.setTimeAspect("30 minutes");	
		lDAO.update(lb);
		LOINCbean lb2 = lDAO.getProcedures("77777-3").get(0);
		assertEquals("works", lb2.getComponent());
	}
	
	public void testGetAllLOINC() throws Exception {
		LOINCbean lb = new LOINCbean();
		lb.setLabProcedureCode("77777-3");
		lb.setComponent("boo");
		lb.setKindOfProperty("");
		lb.setMethodType(" ");
		lb.setScaleType("453");
		lb.setSystem("Windows");
		lb.setTimeAspect("30 minutes");	
		lDAO.addLOINC(lb);
		
		lb = new LOINCbean();
		lb.setLabProcedureCode("77777-3");
		lb.setComponent("works");
		lb.setKindOfProperty("");
		lb.setMethodType(" ");
		lb.setScaleType("453");
		lb.setSystem("Windows");
		lb.setTimeAspect("30 minutes");	
		lDAO.update(lb);
		List<LOINCbean> lb2 = lDAO.getAllLOINC();
		assertEquals(5, lb2.size());
	}
	
}
