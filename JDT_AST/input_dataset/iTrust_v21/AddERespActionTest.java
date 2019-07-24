/**
 * Tests for AddPatientAction
 */

package edu.ncsu.csc.itrust.unit.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.AddERespAction;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.Role;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class AddERespActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private AddERespAction action;
	
	
	/**
	 * Sets up defaults
	 */
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		action = new AddERespAction(factory, 9000000000L);
	}
	
	/**
	 * Tests adding a new ER
	 * @throws Exception
	 */
	public void testAddER() throws Exception {
		gen.clearAllTables();
		PersonnelBean person = new PersonnelBean();
		person.setRole(Role.ER);
		person.setFirstName("Para");
		person.setLastName("Medic");
		person.setEmail("Paramedic@itrust.com");
		long newMID = action.add(person);
		assertEquals(person.getMID(), newMID);
	}
	
}
