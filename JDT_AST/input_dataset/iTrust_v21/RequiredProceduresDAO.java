package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.loaders.OfficeVisitLoader;
import edu.ncsu.csc.itrust.beans.loaders.ProcedureBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.RequiredProceduresBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.beans.RequiredProceduresBean;

/**
 * DAO object that interacts with the requiredprocedures database table.
 * The requiredprocedures table contains information about each of the procedures
 * that is required in order for a patient to enroll in public school, etc.
 */
public class RequiredProceduresDAO {

	/** Factory to create DAO objects */
	private DAOFactory factory;
	/** Bean loaders for all beans used */
	private ProcedureBeanLoader procLoader = new ProcedureBeanLoader();
	private RequiredProceduresBeanLoader reqLoader = new RequiredProceduresBeanLoader();
	private OfficeVisitLoader visitLoader = new OfficeVisitLoader();
	/** DAO for interacting with diagnoses databases */
	private DiagnosesDAO diagnosisDAO;
	
	/**
	 * Creates a new DAO to interact with the requredprocedures database table.
	 * 
	 * @param factory DAOFactory that creates database objects.
	 */
	public RequiredProceduresDAO(DAOFactory factory) {
		this.factory = factory;
		this.diagnosisDAO = factory.getDiagnosesDAO();
	}
	
	/**
	 * Returns a list of all procedures that a specified patient has had.
	 * @param pid PID of the patient
	 * @return list of all procedures performed
	 * @throws DBException if the database is not valid
	 */
	public List<ProcedureBean> getAllImmunizations(long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ovprocedure INNER JOIN cptcodes " +
										"ON ovprocedure.CPTCode = cptcodes.Code " +
										"INNER JOIN officevisits " +
										"ON ovprocedure.VisitID = officevisits.ID " +
										"WHERE Attribute = 'immunization' " +
										"AND PatientID = ?");
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			List<ProcedureBean> loadlist = procLoader.loadAll(rs);
			rs.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Returns a list of all required procedures for the specified age group
	 * (0 for kindergarten, 1 for sixth grade, and 2 for college aged patients).
	 * @param pid PID of the patient
	 * @param ageGroup numeric value that represents the age group of the patient
	 * @return list of all required procedures
	 * @throws DBException if the database is not valid
	 */
	public List<RequiredProceduresBean> getRequiredImmunizations(long pid, int ageGroup) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM requiredprocedures "
									+ "WHERE ageGroup = ?");
			ps.setInt(1, ageGroup);
			ResultSet rs = ps.executeQuery();
			List<RequiredProceduresBean> loadlist = reqLoader.loadList(rs);
			rs.close();
			return loadlist;
			
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		
	}
	
	/**
	 * Returns a list of all required procedures for the specified age group that
	 * the specified patient has not yet received.
	 * @param pid PID of the patient
	 * @param ageGroup 0 if the patient is in kindergarten, 1 if sixth grade, 2 if college aged
	 * @return list of all needed procedures
	 * @throws DBException if the database is not valid
	 */
	public List<RequiredProceduresBean> getNeededImmunizations(long pid, int ageGroup) throws DBException {
		
		//Get a list of all required immunizations that the patient does not yet have.
		List<ProcedureBean> patientImmunizations = getAllImmunizations(pid);
		List<RequiredProceduresBean> reqImmunizations = getRequiredImmunizations(pid, ageGroup);
		ArrayList<RequiredProceduresBean> neededImmunizations = new ArrayList<RequiredProceduresBean>();
		
		for(RequiredProceduresBean reqProc : reqImmunizations) {
			boolean needsProc = true;
			for(ProcedureBean patientProc : patientImmunizations) {
				if(patientProc.getCPTCode().equals(reqProc.getCptCode())) {
					needsProc = false;
				}
			}
			if(needsProc) {
				neededImmunizations.add(reqProc);
			}
		}
		
		//Get a list of all appropriate diagnoses
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM officevisits "
									+ "WHERE PatientID = ?");
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			List<OfficeVisitBean> visits = visitLoader.loadList(rs);
			List<DiagnosisBean> diagnoses = new ArrayList<DiagnosisBean>();
			if(diagnoses != null) {
				for(OfficeVisitBean visit : visits) {
					diagnoses.addAll(diagnosisDAO.getList(visit.getVisitID()));
				}
			}
			
			ArrayList<RequiredProceduresBean> returnList = new ArrayList<RequiredProceduresBean>();
			
			for(RequiredProceduresBean needed : neededImmunizations) {
				boolean needsVaccine = true;
				for(DiagnosisBean diagnosis : diagnoses) {
					if(diagnosis.getICDCode().equals("35.00") && needed.getCptCode().equals("90396")) {
						needsVaccine = false;
					}
				}
				if(needsVaccine) {
					returnList.add(needed);
				}
			}
			
			rs.close();
			return returnList;
		} catch(SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}
