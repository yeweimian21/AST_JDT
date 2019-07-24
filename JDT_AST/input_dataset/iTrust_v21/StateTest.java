package edu.ncsu.csc.itrust.unit.enums;

import edu.ncsu.csc.itrust.enums.State;
import junit.framework.TestCase;

public class StateTest extends TestCase {
	public void testParse() throws Exception {
		assertEquals(State.NC, State.parse("NC"));
		assertEquals(State.PA, State.parse("Pennsylvania"));
		assertEquals(State.NC, State.parse("NOT A STATE!"));
	}
}
