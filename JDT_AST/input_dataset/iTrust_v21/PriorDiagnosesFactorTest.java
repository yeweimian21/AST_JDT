package edu.ncsu.csc.itrust.unit.risk.factors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.risk.factors.PriorDiagnosisFactor;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class PriorDiagnosesFactorTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private PriorDiagnosisFactor factor;

	@Override
	protected void setUp() throws Exception {
		factor = new PriorDiagnosisFactor(factory, 2L, 250.3, 487);
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hospitals();
		gen.hcp0();
		gen.patient2();
	}

	public void testNoInfections() throws Exception {
		assertFalse(factor.hasRiskFactor());
	}

	public void testOneInfection() throws Exception {
		addInfection(new Date(), 250.3);
		assertTrue(factor.hasRiskFactor());
	}

	public void testTwoInfections() throws Exception {
		addInfection(new Date(), 250.3);
		addInfection(new Date(), 487);
		assertTrue(factor.hasRiskFactor());
	}
	
	public void testDBException() throws Exception {
		this.factory = EvilDAOFactory.getEvilInstance();
		factor = new PriorDiagnosisFactor(factory, 2L, 250.3, 487);
		assertFalse(factor.hasFactor());
	}

	private void addInfection(Date date, double icd) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
		conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO officevisits(VisitDate, hcpid, PatientID, hospitalid) VALUES(?,9000000000, 2, '1')");
			ps.setDate(1, new java.sql.Date(date.getTime()));
			ps.executeUpdate();
			ps.close();
			ps = conn.prepareStatement("INSERT INTO ovdiagnosis(VisitID, ICDCode) VALUES(?,?)");
			ps.setLong(1, DBUtil.getLastInsert(conn));
			ps.setDouble(2, icd);
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
