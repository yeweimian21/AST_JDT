
package edu.ncsu.csc.itrust.unit.action;

import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.action.ViewApptRequestsAction;
import edu.ncsu.csc.itrust.beans.ApptRequestBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

@SuppressWarnings("unused")
public class ViewNumberOfPendingAppointmentsActionTest extends TestCase {
	private ViewApptRequestsAction action;
	private DAOFactory factory;
	private long mid = 1L;
	private long hcpId = 9000000000L;
	
	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		
		gen.pendingAppointmentAlert();
		this.factory = TestDAOFactory.getTestInstance();
		this.action = new ViewApptRequestsAction( this.hcpId, this.factory);
	}
	
	public void testGetNumRequest() throws SQLException, DBException {
		List<ApptRequestBean> reqs = action.getApptRequests();
		assertEquals(1, action.getNumRequests(reqs));
	}
	
}
