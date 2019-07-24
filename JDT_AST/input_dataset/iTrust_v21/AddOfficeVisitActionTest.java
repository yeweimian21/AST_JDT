package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.AddOfficeVisitAction;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class AddOfficeVisitActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private AddOfficeVisitAction action;

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.patient1();
		action = new AddOfficeVisitAction(factory, "1");
	}

	public void testAddEmpty() throws Exception {
			long hcpID = 9000000000L;
			long ovID = action.addEmptyOfficeVisit(hcpID);
			OfficeVisitBean ov = factory.getOfficeVisitDAO().getOfficeVisit(ovID);
			assertEquals(hcpID, ov.getHcpID());
			assertEquals(1, ov.getPatientID());
			assertEquals(new OfficeVisitBean().getVisitDateStr(), ov.getVisitDateStr());
	}

	public void testGetOfficeVisits() throws Exception {
		List<OfficeVisitBean> ovs = factory.getOfficeVisitDAO().getAllOfficeVisits(1);
		List<OfficeVisitBean> actualOvs = action.getAllOfficeVisits();
		assertEquals(ovs.size(), actualOvs.size());
		for (int i = 0; i < ovs.size(); i++) {
			assertEquals(ovs.get(i).getID(), actualOvs.get(i).getID());
		}
	}
	
	public void testGetUserName() throws Exception {
		assertEquals("Random Person", action.getUserName());
	}
}
