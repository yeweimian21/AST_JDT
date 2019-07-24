/**
 * Tests AddHCPAction in that the validator is checked and the proper DAOs are hit.
 * 
 * This tests is an example of using Mock Objects. If you haven't covered this in class (yet, or at all), then
 * disregard this class. If you are trying to learn about unit testing with Mock Objects, this is a great
 * class to start with.
 */

package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.AddLTAction;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.enums.Role;
import edu.ncsu.csc.itrust.unit.testutils.ActionTestWithMocks;

import static org.easymock.classextension.EasyMock.*;

public class AddLTActionTest extends ActionTestWithMocks {
	private AddLTAction action;
	private PersonnelBean personnel;

	/**
	 * Sets up defaults
	 */
	@Override
	protected void setUp() throws Exception {
		// Step 0. Initialize the mocks and other necessary objects.
		super.initMocks();
		// Step 1. Initialize any other classes we need.
		personnel = new PersonnelBean();
		personnel.setFirstName("Cosmo");
		personnel.setLastName("Kramer");
		personnel.setEmail("cosmo@kramer.com");
		personnel.setRole(Role.LT);

	}

	/**
	 * Tests adding a new LT
	 * 
	 * @throws Exception
	 * 
	 */
	public void testAddLT() throws Exception {
		// Step 2. For each test, set up the expectations of what will be called (started in initMocks)
		expect(personnelDAO.addEmptyPersonnel(Role.LT)).andReturn(5000000001L).once();
		personnelDAO.editPersonnel(personnel);
		expectLastCall().once();
		// Step 3. Exit recording mode, go into playback mode
		control.replay(); // Don't forget this!

		// Step 3. Actually run the method under test, checking its return value
		action = new AddLTAction(factory, 5000000002L); //this lines needs to be AFTER the replay()
		long newMID = action.add(personnel);
		assertEquals(5000000001L, newMID);

		// Step 4. Verify the mocks were hit as you expected
		control.verify(); // Don't forget this!
	}
}
