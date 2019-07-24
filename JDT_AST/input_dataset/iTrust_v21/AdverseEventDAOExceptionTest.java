package edu.ncsu.csc.itrust.unit.dao.adverseevent;

import edu.ncsu.csc.itrust.dao.mysql.AdverseEventDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import junit.framework.TestCase;

public class AdverseEventDAOExceptionTest extends TestCase {
	private AdverseEventDAO evilDAO = EvilDAOFactory.getEvilInstance().getAdverseEventDAO();

	@Override
	protected void setUp() throws Exception {
	}
	
	public void testGetReportsFor() throws Exception{
		try {
			evilDAO.getReportsFor(1);
			fail("Should have thrown DBException");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testGetHCPMID() throws Exception {
		try {
			evilDAO.getHCPMID(1);
			fail("Should have thrown DBException");
		} catch (DBException e){
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testGetReport() throws Exception {
		try {
			evilDAO.getReport(1);
			fail("Should have thrown DBException");
		}catch (DBException e){
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}
