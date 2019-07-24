package edu.ncsu.csc.itrust.unit.db;

import edu.ncsu.csc.itrust.unit.DBBuilder;
import junit.framework.TestCase;

public class DBBuilderTest extends TestCase {

	// Make sure that the actual database can be rebuilt
	// This is run twice so that we check the "drop tables" script
	public void testRebuildNoException() throws Exception {
		DBBuilder.rebuildAll();
		DBBuilder.rebuildAll();
	}
}
