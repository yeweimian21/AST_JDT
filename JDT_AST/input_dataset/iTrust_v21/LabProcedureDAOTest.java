package edu.ncsu.csc.itrust.unit.dao.labprocedure;

import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class LabProcedureDAOTest extends TestCase {
	
	private LabProcedureDAO lpDAO = TestDAOFactory.getTestInstance().getLabProcedureDAO();
	private TestDataGenerator gen;

	protected void setUp() throws Exception {
		super.setUp();
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}

	public void testMarkViewed() throws Exception {
		LabProcedureBean bean = new LabProcedureBean();
		bean.setPid(1);
		bean.statusComplete();
		bean.setViewedByPatient(false);
		long id = lpDAO.addLabProcedure(bean);
		
		bean = lpDAO.getLabProcedure(id);
		assertEquals(false, bean.isViewedByPatient());
		bean.setViewedByPatient(true);
		lpDAO.markViewed(bean);
		
		bean = lpDAO.getLabProcedure(id);
		assertEquals(true, bean.isViewedByPatient());
	}

}
