package edu.ncsu.csc.itrust.unit.action;

import java.sql.SQLException;

import edu.ncsu.csc.itrust.action.ViewHelperAction;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import junit.framework.TestCase;

public class ViewHelperActionTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
	}
	
	public void testUpdateUserPrefs() throws FormValidationException, SQLException, ITrustException {
		assertTrue(ViewHelperAction.calculateColor("000000", "FFFFFF", 0).equals("000000"));
		assertTrue(ViewHelperAction.calculateColor("000000", "FFFFFF", 1.0).equals("FFFFFF"));
		assertTrue(ViewHelperAction.calculateColor("000000", "FFFFFF", 0.5).equals("7F7F7F"));
	}

}
