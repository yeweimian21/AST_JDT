package edu.ncsu.csc.itrust.unit.dao.auth;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.enums.Role;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

public class AuthDAOExceptionTest extends TestCase {
	private AuthDAO evilDAO = EvilDAOFactory.getEvilInstance().getAuthDAO();

	@Override
	protected void setUp() throws Exception {
	}

	public void testAddUserException() throws Exception {
		try {
			evilDAO.addUser(0L, Role.ADMIN, "");
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testCheckUserExistsException() throws Exception {
		try {
			evilDAO.checkUserExists(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testLoginFailuresException() throws Exception {
		try {
			evilDAO.getLoginFailures("");
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetUserNameException() throws Exception {
		try {
			evilDAO.getUserName(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetUserRoleException() throws Exception {
		try {
			evilDAO.getUserRole(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testRecordLoginFailureException() throws Exception {
		try {
			evilDAO.recordLoginFailure("");
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testResetPasswordException() throws Exception {
		try {
			evilDAO.resetPassword(0L, "");
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testSetSecurityQuestionAnswer() throws Exception {
		try {
			evilDAO.setSecurityQuestionAnswer(null, null, 0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testGetSecurityAnswer() throws Exception {
		try {
			evilDAO.getSecurityQuestion(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testGetSecurityQuestion() throws Exception {
		try {
			evilDAO.getSecurityAnswer(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testRecordResetPasswordFailure() throws Exception {
		try {
			evilDAO.recordResetPasswordFailure("");
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testIsDependent() throws Exception {
		try {
			evilDAO.isDependent(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testSetDependency() throws Exception {
		try {
			evilDAO.setDependent(0L, true);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
}
