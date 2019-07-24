package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.MonitorAdverseEventAction;
import edu.ncsu.csc.itrust.beans.AdverseEventBean;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class MonitorAdverseEventActionTest extends TestCase {
	MonitorAdverseEventAction action;
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hcp0();
		gen.pha0();
		gen.patient1();
		gen.adverseEventPres();
		gen.ndCodes();
		gen.cptCodes();
		action = new MonitorAdverseEventAction(TestDAOFactory.getTestInstance(), 9999999990L);
	}
	
	@Override
	protected void tearDown() throws Exception{
		gen.clearAllTables();
	}

	public void testGetName() throws Exception{
		
		assertEquals("Random Person", action.getName(1L));
	}
	
	public void testGetNoName()throws Exception{
		assertEquals("Patient no longer exists", action.getName(4));
	}
	
	public void testGetPrescriptions() throws Exception{
		
		List<AdverseEventBean> beanList = action.getReports(true, "11/08/2008", "11/10/2010");
		assertEquals("Prioglitazone", beanList.get(0).getDrug());
	}
	
	public void testGetImmunizations() throws Exception{
		List<AdverseEventBean> beanList = action.getReports(false, "11/08/2008", "11/10/2010");
		assertEquals("Hepatitis B", beanList.get(0).getDrug());
	}
	
	
	public void testRemove() throws Exception{
		action.remove(2);
		List<AdverseEventBean> beanList = action.getReports(false, "11/08/2008", "11/10/2010");
		assertEquals("removed", beanList.get(0).getStatus());
	}

	public void testSendMessage() throws Exception{
		assertEquals("9999999990 I'll fix you up", action.sendEmail(1L, "I'll fix you up"));
	}
}
