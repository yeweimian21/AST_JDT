package edu.ncsu.csc.itrust.unit.dao.family;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.mysql.FamilyDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

public class FamilyDAOExceptionTest extends TestCase {
	private FamilyDAO evilDAO = EvilDAOFactory.getEvilInstance().getFamilyDAO();

	@Override
	protected void setUp() throws Exception {
	}

	public void testFamilyMemberException() throws Exception {
		try {
			evilDAO.getParents(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

}
