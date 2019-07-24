package edu.ncsu.csc.itrust.unit.dao.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class GetLoginFailureTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	AuthDAO authDAO = factory.getAuthDAO();
	private TestDataGenerator gen = new TestDataGenerator();
	private String ipAddr = "192.168.1.1";

	@Override
	protected void setUp() throws Exception {
		gen.clearLoginFailures();
	}

	public void testGetLoginFailuresNoEntry() throws Exception {
		assertEquals(0, authDAO.getLoginFailures(ipAddr));
	}

	// no need to do it *exactly* if we have -10s and +10s; we don't need that level of accuracy

	public void testGetLoginFailuresWithEntry5() throws Exception {
		// also doing BVA - make it 10s less than the timeout time
		addLoginFailure(5, new Timestamp(System.currentTimeMillis() - (AuthDAO.LOGIN_TIMEOUT - 10000)));
		assertEquals(5, authDAO.getLoginFailures(ipAddr));
	}

	public void testGetLoginFailures15MinutesAgo() throws Exception {
		// for BVA here, make it 10s greater than the timeout time
		addLoginFailure(5, new Timestamp(System.currentTimeMillis() - (AuthDAO.LOGIN_TIMEOUT) + 10000));
		assertEquals(5, authDAO.getLoginFailures(ipAddr));
	}

	private void addLoginFailure(int count, Timestamp lastFailure) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO loginfailures(IPAddress,failureCount, lastFailure) "
					+ "VALUES(?,?,?)");
			ps.setString(1, ipAddr);
			ps.setInt(2, count);
			ps.setTimestamp(3, lastFailure);
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBUtil.closeConnection(conn, ps);
		}

	}
}
