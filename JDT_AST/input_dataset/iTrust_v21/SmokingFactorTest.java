package edu.ncsu.csc.itrust.unit.risk.factors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.risk.factors.SmokingFactor;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * SmokingFactorTest
 */
public class SmokingFactorTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private SmokingFactor factor;

	@Override
	protected void setUp() throws Exception {
		factor = new SmokingFactor(factory, 2L);
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient2();
	}

	/**
	 * testHasHistory
	 * @throws Exception
	 */
	public void testHasHistory() throws Exception {
		assertTrue(factor.hasRiskFactor());
	}

	/**
	 * testHasNoHistory
	 * @throws Exception
	 */
	public void testHasNoHistory() throws Exception {
		clearHistoryOfSmoking();
		assertFalse(factor.hasRiskFactor());
	}
	
	/**
	 * testDBException
	 * @throws Exception
	 */
	public void testDBException() throws Exception {
		this.factory = EvilDAOFactory.getEvilInstance();
		factor = new SmokingFactor(factory, 2L);
		assertFalse(factor.hasFactor());
	}

	private void clearHistoryOfSmoking() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM personalhealthinformation WHERE PatientID=2 AND Smoker=1");
			ps.executeUpdate();
		}
		catch (SQLException ex){
			throw ex;
		}
		finally{
			DBUtil.closeConnection(conn, ps);
		}
	}
}
