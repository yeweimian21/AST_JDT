package edu.ncsu.csc.itrust.unit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * AutoIncrementTest
 */
public class AutoIncrementTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = TestDAOFactory.getTestInstance().getConnection();
			ps = conn.prepareStatement("DROP TABLE IF EXISTS testincrement");
			ps.executeUpdate();
			ps.close();
			ps = conn.prepareStatement("CREATE TABLE testincrement(id integer auto_increment primary key)");
			ps.executeUpdate();
		} finally {
			DBUtil.closeConnection(conn, ps);
		}

	}

	/**
	 * testNoIcrementCollision
	 * @throws DBException
	 */
	public void testNoIncrementCollision() throws DBException {
		Connection conn1 = null;
		PreparedStatement ps = null;
		try {
			conn1 = TestDAOFactory.getTestInstance().getConnection();
			ps = conn1.prepareStatement("INSERT INTO testincrement VALUES()");
			ps.executeUpdate();
			doTheSecond();
			assertEquals(1L, DBUtil.getLastInsert(conn1));
			// See? It's on a per-connection basis. Nothing to worry about.
		} catch (SQLException ex) {
			//TODO
		} finally {
			DBUtil.closeConnection(conn1, ps);
		}

	}

	private void doTheSecond() throws SQLException, DBException {
		Connection conn2 = null;
		PreparedStatement ps = null;
		try {
			conn2 = TestDAOFactory.getTestInstance().getConnection();
			ps = conn2.prepareStatement("INSERT INTO testincrement VALUES()");
			ps.executeUpdate();
			assertEquals(2L, DBUtil.getLastInsert((conn2)));
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBUtil.closeConnection(conn2, ps);
		}
	}

	@Override
	protected void tearDown() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = TestDAOFactory.getTestInstance().getConnection();
			ps = conn.prepareStatement("DROP TABLE IF EXISTS testincrement");
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBUtil.closeConnection(conn, ps);
		}

	}
}
