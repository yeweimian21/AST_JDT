package edu.ncsu.csc.itrust.unit.dao.fakeemail;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

public class EmailExceptionTest extends TestCase {

	private DAOFactory factory;

	public void testGetAllException() throws Exception {
		factory = EvilDAOFactory.getEvilInstance();
		try {
			factory.getFakeEmailDAO().getAllEmails();
			fail("exception should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetPersonException() throws Exception {
		factory = EvilDAOFactory.getEvilInstance();
		try {
			factory.getFakeEmailDAO().getEmailsByPerson("gstormcrow@iTrust.org");
			fail("exception should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testSendException() throws Exception {
		factory = EvilDAOFactory.getEvilInstance();
		try {
			factory.getFakeEmailDAO().sendEmailRecord(new Email());
			fail("exception should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

}
