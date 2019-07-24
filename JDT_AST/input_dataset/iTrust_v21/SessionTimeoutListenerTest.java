package edu.ncsu.csc.itrust.unit.serverutils;

import javax.servlet.http.HttpSessionEvent;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.server.SessionTimeoutListener;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class SessionTimeoutListenerTest extends TestCase {
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.timeout();
	}

	// This uses a rudimentary mock object system - where we create these objects that are
	// essentially stubs, except for keeping track of info passed to them.
	public void testListenerWorked() throws Exception {
		SessionTimeoutListener listener = new SessionTimeoutListener(TestDAOFactory.getTestInstance());
		HttpSessionEvent event = new MockHttpSessionEvent();
		listener.sessionCreated(event);
		assertEquals(1200, MockHttpSession.mins);
	}

	public void testNothingWithSessionDestroyed() throws Exception {
		SessionTimeoutListener listener = new SessionTimeoutListener(TestDAOFactory.getTestInstance());
		listener.sessionDestroyed(null);
	}

	public void testDBException() throws Exception {
		SessionTimeoutListener listener = new SessionTimeoutListener();
		listener.sessionCreated(new MockHttpSessionEvent());
		assertEquals(1200, MockHttpSession.mins);
	}

	
}
