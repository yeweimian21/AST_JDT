package edu.ncsu.csc.itrust.unit.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ProcedureBean;

/**
 */
public class ProcedureBeanTest extends TestCase {
	
	/**
	 * testProcedureBean
	 * @throws Exception
	 */
	public void testProcedureBean() throws Exception {
		ProcedureBean pb = new ProcedureBean();
		pb.setHcpid("9000000000");
		assertEquals("9000000000", pb.getHcpid());
		pb.setOvProcedureID(385730L);
		assertEquals(385730L, pb.getOvProcedureID());
	}
}
