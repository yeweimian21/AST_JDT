package edu.ncsu.csc.itrust.unit.risk.factors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.risk.factors.ChildhoodInfectionFactor;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ChildhoodInfectionFactorTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private ChildhoodInfectionFactor factor;
	private PatientBean p;

	@Override
	protected void setUp() throws Exception {
		factor = new ChildhoodInfectionFactor(factory, 2L, 250.3, 487);
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hospitals();
		gen.hcp0();
		gen.patient2();
		p = factory.getPatientDAO().getPatient(2L);
	}

	public void testNoInfectionsAtAll() throws Exception {
		assertFalse(factor.hasRiskFactor());
	}

	public void testNoInfectionsDuringChildhood() throws Exception {
		addInfection(new Date(), 250.3);
		assertFalse(factor.hasRiskFactor());
	}

	public void testOneInfectionDuringChildhood() throws Exception {
		addInfection(new SimpleDateFormat("MM/dd/yyyy").parse(p.getDateOfBirthStr()), 250.3);
		assertTrue(factor.hasRiskFactor());
	}

	public void testManyInfectionsDuringChildhood() throws Exception {
		addInfection(new SimpleDateFormat("MM/dd/yyyy").parse(p.getDateOfBirthStr()), 250.3);
		addInfection(new SimpleDateFormat("MM/dd/yyyy").parse(p.getDateOfBirthStr()), 487);
		assertTrue(factor.hasRiskFactor());
	}

	public void testRegularPatient2() throws Exception {
		factor = new ChildhoodInfectionFactor(factory, 2L, 79.1, 79.3);
		assertTrue(factor.hasRiskFactor());
	}

	public void testDBException() throws Exception {
		this.factory = EvilDAOFactory.getEvilInstance();
		factor = new ChildhoodInfectionFactor(factory, 2L, 250.3, 487);
		assertFalse(factor.hasFactor());
	}

	private void addInfection(Date date, double icd) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO officevisits(VisitDate,PatientID, hcpid, hospitalid) VALUES(?, 2, 9000000000, '1')");
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
