package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.beans.VisitFlag;
import edu.ncsu.csc.itrust.beans.forms.VisitReminderReturnForm;
import edu.ncsu.csc.itrust.beans.loaders.VisitReminderReturnFormLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for queries related to patient reminders
 * 
 * DAO stands for Database Access Object. All DAOs are intended to be reflections of the database, that is,
 * one DAO per table in the database (most of the time). For more complex sets of queries, extra DAOs are
 * added. DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor than a factory. All DAOs should be
 * accessed by DAOFactory (@see {@link DAOFactory}) and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 * 
 *  
 * 
 */
public class VisitRemindersDAO {
	private DAOFactory factory;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public VisitRemindersDAO(DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * Returns a list of patients associated with a given HCP.
	 * 
	 * @param hcpid The MID of the HCP in question.
	 * @return A java.util.List of Visit Reminder Forms.
	 */
	public List<VisitReminderReturnForm> getPatients(long hcpid) throws DBException {
		
	
		VisitReminderReturnForm temp;
		VisitReminderReturnFormLoader loader = new VisitReminderReturnFormLoader();
		List<VisitReminderReturnForm> patients = new ArrayList<VisitReminderReturnForm>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					  "SELECT DISTINCT" 
					+ "       ? as hid, "
					+ "       dhcp.patientid, "
					+ "       p.lastName, "
					+ "       p.firstName, "
					+ "       p.phone "
					+ "  FROM "
					+ "       patients p, "
					+ "       declaredhcp dhcp "
					+ " WHERE "
					+ "		dhcp.hcpid = ? "
					+ " AND "
					+ "     p.MID = dhcp.patientid "
					+ " AND "
					+ "		p.dateofdeath is null "
					+ " AND "
					+ "     p.dateofbirth > DATE_SUB(CURDATE(), INTERVAL 19 YEAR) "
					);

			ps.setLong(1, hcpid);
			ps.setLong(2, hcpid);
			rs = ps.executeQuery();

			while (rs.next()) {
				temp = loader.loadSingle(rs);
				patients.add(temp);
			}
			rs.close();
		}
		catch (SQLException e) {
			
			throw new DBException(e);
		} 
		finally {
			DBUtil.closeConnection(conn, ps);
		}
		return patients;
	}


	/**
	 * Returns a list of patients who need a visit for this HCP. This means:
	 * 
	 * Alive patient, no office visit for > 1 yr, diagnosed: - Diabetes: 250.xx - Asthma: 493.xx -
	 * Circulatory-System Disease: [ 390.00 , 459.99 ]
	 * 
	 * @param hcpid The MID of the HCP in question.
	 * @return A java.util.List of visit reminders.
	 */
	public List<VisitReminderReturnForm> getDiagnosedVisitNeeders(long hcpid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					"SELECT  hid, MID, lastName, firstName, phone, ICDcode, visitDate FROM " +
					"(SELECT DISTINCT " +
					"  ? as hid, " +
					"  p.MID as MID, " +
					"  p.lastName, " +
					"  p.firstName, " +
					"  p.phone, " +
					"  ovd.ICDcode " +
					" FROM " +
					"  patients p, " +
					"  officevisits ov, " +
					"  ovdiagnosis ovd " +
					" WHERE " +
					"  p.MID = ov.PatientID " +
					" AND " +
					"  p.DateOfDeath is null " +
					" AND " +
					"  ov.ID = ovd.VisitID " +
					" AND " +
					"  (" +
					"    ovd.ICDCode BETWEEN ? AND ? " +
					"   OR " +
					"    ovd.ICDCode BETWEEN ? AND ? " +
					"   OR " +
					"    ovd.ICDCode BETWEEN ? AND ? " +
					"  ) " +
					" AND " +
					"  p.MID NOT IN " +
					"   ( " +
					"    SELECT p.MID FROM patients p, officevisits ov " +
					"    WHERE " +
					"     p.MID = ov.PatientID " +
					"	 AND " +
					"     ov.visitDate > DATE_SUB(CURDATE(), INTERVAL 1 YEAR) " +
					"   ) " +
					") a " +
					" INNER JOIN " +
					"  ( " +
					"   SELECT p.MID, MAX(ov.visitDate) as visitDate FROM patients p, officevisits ov " +
					"   WHERE " +
					"    p.MID = ov.PatientID " +
					"   GROUP BY " +
					"    p.mid " +
					"  ) b " +
					" USING(MID) " +
					"ORDER BY " +
					" lastName, firstName, MID, ICDcode ");

			ps.setLong(1, hcpid);

			ps.setFloat(2, 250.0f);
			ps.setFloat(3, 250.99f);
				
			ps.setFloat(4, 390.0f);
			ps.setFloat(5, 459.99f);
				
			ps.setFloat(6, 493.0f);
			ps.setFloat(7, 493.99f);
			
			rs = ps.executeQuery();
			List<VisitReminderReturnForm> patients = new ArrayList<VisitReminderReturnForm>();
			VisitReminderReturnForm temp = null;
			VisitReminderReturnFormLoader loader = new VisitReminderReturnFormLoader();
			long lastId = -1;
			while (rs.next()) {
				if (lastId == rs.getLong(2)) {
					patients.get(patients.size()-1).addVisitFlag(new VisitFlag(VisitFlag.DIAGNOSED, rs.getString(6)));
				} else {
					lastId = rs.getLong(2);
					temp = loader.loadSingle(rs);
					temp.addVisitFlag(new VisitFlag(VisitFlag.DIAGNOSED, rs.getString(6)));
					temp.addVisitFlag(new VisitFlag(VisitFlag.LAST_VISIT, rs.getString(7)));
					patients.add(temp);
				}
			}
			rs.close();
			return patients;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns a list of old people who need a flu shot
	 * 
	 * CURRENT SPEC: Alive patient, over 50 years old, no flu shot - 90656, 90658, 90660 a) during the months
	 * 09 - 12 of current calendar year if in 09 - 12 b) during the months 09 - 12 of last calendar year if
	 * not in 09 - 12
	 * 
	 * Determines current date and calls one of two private methods these methods return different sets of
	 * people - a: people who haven't had a flu shot yet this year and need one - b: people who missed last
	 * year's flu shot and need a check-up
	 * 
	 * @return A java.util.List of Visit Reminder Forms.
	 * @param hcpid The MID of the HCP in question.
	 * @param patientBirthday The birthday of the patient in question.
	 * @throws DBException
	 */
	public List<VisitReminderReturnForm> getFluShotDelinquents(long hcpid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		/*
		 * Fall 2007 Bug Fix: Use the correct range for a zero-based calendar (eg January is 0)
		 * 
		 */
		boolean thisYear = DateUtil.currentlyInMonthRange(8, 11);

		String flagType = thisYear ? VisitFlag.MISSING_MEDICATION : VisitFlag.MISSED_MEDICATION;

		java.sql.Date september = new java.sql.Date(0l), december = new java.sql.Date(0l);
		DateUtil.setSQLMonthRange(september, 8, thisYear ? 0 : 1, december, 11, thisYear ? 0 : 1);

		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT DISTINCT " +
					"? as hid, p.mid as patientid, p.lastname, p.firstname, p.phone " +
					"FROM officevisits ov, patients p " +
					"WHERE (ov.patientid=p.mid " +
					"OR p.mid NOT IN (SELECT ov.patientid FROM officevisits ov)) " +
					"AND p.dateofdeath IS NULL " +
					"AND p.dateofbirth < DATE_SUB(CURDATE(), INTERVAL 50 YEAR) " +
					"AND p.mid NOT IN " +
					"(SELECT patientid AS mid FROM officevisits ov, ovprocedure op " +
					"WHERE ov.id=op.visitid " +
					"AND CPTCode IN (90656, 90658, 90660) " +
					"AND ((ov.visitdate BETWEEN ? AND ?) " +
					"OR (ov.visitdate BETWEEN ? AND ?))) " +
					"ORDER BY lastname, firstname, p.mid");
					
			ps.setLong(1, hcpid);
			ps.setDate(2, september);
			ps.setDate(3, december);
			ps.setDate(4, september);
			ps.setDate(5, december);
			rs = ps.executeQuery();
			List<VisitReminderReturnForm> patients = new ArrayList<VisitReminderReturnForm>();
			VisitReminderReturnForm temp;
			VisitReminderReturnFormLoader loader = new VisitReminderReturnFormLoader();
			while (rs.next()) {
				temp = loader.loadSingle(rs);
				temp.addVisitFlag(new VisitFlag(flagType, "Flu Shot"));
				patients.add(temp);
			}
			rs.close();
			return patients;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}	
}