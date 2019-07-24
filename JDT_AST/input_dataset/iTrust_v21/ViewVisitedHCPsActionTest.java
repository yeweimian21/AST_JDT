package edu.ncsu.csc.itrust.unit.action;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.ViewVisitedHCPsAction;
import edu.ncsu.csc.itrust.beans.HCPVisitBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;


/**
 * Test class for the ViewVisitedHCPsAction class.
 *
 */
public class ViewVisitedHCPsActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private ViewVisitedHCPsAction action;
	private ViewVisitedHCPsAction action2;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		gen.patient_hcp_vists();
		action = new ViewVisitedHCPsAction(factory, 2L);
		action2 = new ViewVisitedHCPsAction(factory, 1L);
	}

	public void testGetVisitedHCPsRed() {
		// Get all visited HCPs without any filtering.
		List<HCPVisitBean> beans = action.getVisitedHCPs();

		assertEquals("Gandalf Stormcrow", beans.get(0).getHCPName());
		assertEquals("Mary Shelley", beans.get(1).getHCPName());
		assertEquals("Lauren Frankenstein", beans.get(2).getHCPName());
		assertEquals("Jason Frankenstein", beans.get(3).getHCPName());
		assertEquals("Kelly Doctor", beans.get(4).getHCPName());
	}

	public void testGetVisitedHCPsRed2() {
		// Get all visited HCPs without any filtering.
		List<HCPVisitBean> beans = action2.getVisitedHCPs();

		assertEquals("Gandalf Stormcrow", beans.get(0).getHCPName());
		assertEquals("Kelly Doctor", beans.get(1).getHCPName());
	}

	public void testCheckDeclared() throws Exception {
		assertEquals(false, action.checkDeclared(0));
		assertEquals(true, action.checkDeclared(9000000003L));
		assertEquals(false, action.checkDeclared(9668301824L));
		assertEquals(false, action.checkDeclared(9000000000L));
	}

	public void testFilterHCPList1() {
		// Get all visited HCPs without any filtering. (The empty strings are 
		// ignored.)
		List<HCPVisitBean> beanslist = action.filterHCPList("", "", "");
		ArrayList<HCPVisitBean> beans = new ArrayList<HCPVisitBean>(beanslist);
		assertEquals(5, beans.size());
		assertEquals("Kelly Doctor", beans.get(4).getHCPName());
		assertEquals("Jason Frankenstein", beans.get(3).getHCPName());
		assertEquals("Lauren Frankenstein", beans.get(2).getHCPName());
		assertEquals("Mary Shelley", beans.get(1).getHCPName());
		assertEquals("Gandalf Stormcrow", beans.get(0).getHCPName());
	}

	public void testFilterHCPList2() {
		// Get all visited HCPs without any filtering. (The nulls are ignored.)
		List<HCPVisitBean> beanslist = action.filterHCPList(null, null, null);
		ArrayList<HCPVisitBean> beans = new ArrayList<HCPVisitBean>(beanslist);
		assertEquals(5, beans.size());
		assertEquals("Kelly Doctor", beans.get(4).getHCPName());
		assertEquals("Jason Frankenstein", beans.get(3).getHCPName());
		assertEquals("Lauren Frankenstein", beans.get(2).getHCPName());
		assertEquals("Mary Shelley", beans.get(1).getHCPName());
		assertEquals("Gandalf Stormcrow", beans.get(0).getHCPName());
	}
	
	public void testFilterHCPList3() {
		// Filter on last name and specialty.
		List<HCPVisitBean> beanslist = action.filterHCPList("Frank", "surgeon", "");
		ArrayList<HCPVisitBean> beans = new ArrayList<HCPVisitBean>(beanslist);
		assertEquals(1, beans.size());
		assertEquals("Jason Frankenstein", beans.get(0).getHCPName());
	}

	public void testFilterHCPListByName() {
		// Filter on last name only.
		List<HCPVisitBean> beanslist = action.filterHCPList("Frank", "", "");
		ArrayList<HCPVisitBean> beans = new ArrayList<HCPVisitBean>(beanslist);
		assertEquals(2, beans.size());
		assertEquals("Jason Frankenstein", beans.get(1).getHCPName());
		assertEquals("Lauren Frankenstein", beans.get(0).getHCPName());
	}
	
	public void testFilterHCPListBySpecialty() {
		// Filter on specialty only.
		List<HCPVisitBean> beanslist = action.filterHCPList("", "surgeon", "");
		ArrayList<HCPVisitBean> beans = new ArrayList<HCPVisitBean>(beanslist);
		assertEquals(3, beans.size());
		assertEquals("Kelly Doctor", beans.get(2).getHCPName());
		assertEquals("Jason Frankenstein", beans.get(1).getHCPName());
		assertEquals("Mary Shelley", beans.get(0).getHCPName());
	}
	
	public void testFilterHCPListByZip() {
		// Filter on zip only.
		List<HCPVisitBean> beanslist = action.filterHCPList("", "", "27605");
		ArrayList<HCPVisitBean> beans = new ArrayList<HCPVisitBean>(beanslist);
		assertEquals(1, beans.size());
		assertEquals("Lauren Frankenstein", beans.get(0).getHCPName());
	}
	
	public void testDeclareAndUndeclareHCP() throws Exception {
		action.declareHCP("Kelly Doctor");
		assertEquals(true, action.checkDeclared(9000000000L));
		action.undeclareHCP("Kelly Doctor");
		assertEquals(false, action.checkDeclared(9000000000L));
	}
	
	public void testDeclareHCP() throws Exception {
		assertEquals(false, action2.checkDeclared(9010000006L));
		action2.declareHCP("Mary Shelley");
		assertEquals(true, action2.checkDeclared(9010000006L));
	}
	
	public void testGetNamedHCP() throws Exception {
		HCPVisitBean bean = action.getNamedHCP("Kelly Doctor");
		assertEquals("Kelly Doctor", bean.getHCPName());
	}

}
