package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import edu.ncsu.csc.itrust.action.ViewAdverseEventAction;
import edu.ncsu.csc.itrust.beans.AdverseEventBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

/**
 * ViewAdverseEventActionTest
 */
public class ViewAdverseEventActionTest extends TestCase {
	private ViewAdverseEventAction action;
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	
	@Override
	protected void setUp() throws Exception {
		action = new ViewAdverseEventAction(factory);
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();	
		gen.adverseEvent1();
	}
	
	/**
	 * testGetAdverseEvent
	 * @throws Exception
	 */
	public void testGetAdverseEvent() throws Exception{
		AdverseEventBean bean = action.getAdverseEvent(1);
		assertNotNull(bean);
	}
	
	/**
	 * testGetUnremovedAdverseEventsByCode
	 * @throws Exception
	 */
	public void testGetUnremovedAdverseEventsByCode() throws Exception {
		List<AdverseEventBean> beanList = action.getUnremovedAdverseEventsByCode("548684985");
		assertNotNull(beanList);
		assertFalse(beanList.isEmpty());
		
		assertEquals(beanList.get(0).getDescription(), "Stomach cramps and migraine headaches after taking this drug");
		
	}
	
	/**
	 * testGetNameForCode
	 * @throws Exception
	 */
	public void testGetNameForCode() throws Exception {
		String name = action.getNameForCode("548684985");
		assertEquals(name, "Citalopram Hydrobromide");
	}
}
