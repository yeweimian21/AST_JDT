package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import edu.ncsu.csc.itrust.action.ViewImmunizationReportAction;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.beans.RequiredProceduresBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ViewImmunizationReportActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private ViewImmunizationReportAction action;
	
	@Override
	public void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testGetAllImmunizations() throws Exception {
		//Adam Sandler - all required immunizations plus some
		action = new ViewImmunizationReportAction(factory, 300L, 300L);
		List<ProcedureBean> procs = action.getAllImmunizations(300L);
		assertEquals(8, procs.size());
		assertEquals("90696", procs.get(0).getCPTCode());
	}
	
	public void testGetRequiredImmunizations() throws Exception {
		//Adam Sandler - kindergarten immunizations
		action = new ViewImmunizationReportAction(factory, 300L, 300L);
		List<RequiredProceduresBean> reqs = action.getRequiredImmunizations(300L, 0);
		assertEquals(6, reqs.size());
		assertEquals("90696", reqs.get(0).getCptCode());
		assertEquals("90712", reqs.get(1).getCptCode());
		assertEquals("90707", reqs.get(2).getCptCode());
		assertEquals("90645", reqs.get(3).getCptCode());
		assertEquals("90371", reqs.get(4).getCptCode());
		assertEquals("90396", reqs.get(5).getCptCode());
		
		//Charlie Chaplin - adult immunizations
		action = new ViewImmunizationReportAction(factory, 308L, 308L);
		reqs = action.getRequiredImmunizations(308L, 2);
		assertEquals(4, reqs.size());
		assertEquals("90696", reqs.get(0).getCptCode());
		assertEquals("90712", reqs.get(1).getCptCode());
		assertEquals("90707", reqs.get(2).getCptCode());
		assertEquals("90371", reqs.get(3).getCptCode());
	}
	
	public void testGetNeededImmunizations() throws Exception {
		//Adam Sandler - No immunizations needed
		action = new ViewImmunizationReportAction(factory, 300L, 300L);
		List<RequiredProceduresBean> needed = action.getNeededImmunizations(300L, 0);
		assertEquals(0, needed.size());
		
		//Christina Aguillera - Prior diagnosis
		action = new ViewImmunizationReportAction(factory, 305L, 305L);
		needed = action.getNeededImmunizations(305L, 0);
		assertEquals(0, needed.size());
		
		//Charlie Chaplin - Age limit
		action = new ViewImmunizationReportAction(factory, 308L, 308L);
		needed = action.getNeededImmunizations(308L, 2);
		assertEquals(2, needed.size());
		assertEquals("90696", needed.get(0).getCptCode());
		assertEquals("90707", needed.get(1).getCptCode());
	}
	
	@Test
	public void testGetHcpNameFromID() throws Exception {
		//Kelly Doctor (MID 9000000000)
		action = new ViewImmunizationReportAction(factory, 300L, 300L);
		assertEquals("Kelly Doctor", action.getHcpNameFromID("9000000000"));
		
		//Shelly Vang (MID 8000000001)
		assertEquals("Shelly Vang", action.getHcpNameFromID("8000000011"));
		
		//Nonexistent HCP (MID 2020202020)
		try {
			action.getHcpNameFromID("2020202020");
			fail();
		} catch(ITrustException e) {
			assertEquals("User does not exist", e.getMessage());
		}
	}
}
