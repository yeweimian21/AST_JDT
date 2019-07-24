package edu.ncsu.csc.itrust.unit.dao;

import static org.easymock.classextension.EasyMock.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import junit.framework.TestCase;
import org.easymock.classextension.IMocksControl;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class DBUtilTest extends TestCase {
	public void testCanAccessProductionDriver() throws Exception {
		// Should never be able to do this because JUnit is not running under Tomcat
		assertFalse(DBUtil.canObtainProductionInstance());
	}
	
	public void testClosingNullPS() throws Exception {
		Connection conn = TestDAOFactory.getTestInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement("SHOW TABLES");
		DBUtil.closeConnection(conn, ps);
	}
	
	// The following test uses an advanced concept not taught in CSC326 at NCSU called Mock Objects.
	// Feel free to take a look at how this works, but know that you will not need to know mock objects
	// to do nearly everything in iTrust. Unless your assignment mentions mock objects somewhere, you should
	// not need them.
	//
	// But, if you are interested in a cool unit testing concept, feel free to look at this code as an
	// example.
	//
	// This class uses the EasyMock library to manage the mock objects. http://easymock.org/
	
	public void testException() throws Exception {
		IMocksControl ctrl = createControl();
		Connection mockConn = ctrl.createMock(Connection.class);
		mockConn.close();
		expectLastCall().andThrow(new SQLException("Testing!"));
		ctrl.replay();
		
		DBUtil.closeConnection(mockConn, null);
		
		ctrl.verify();
		
	}
}
