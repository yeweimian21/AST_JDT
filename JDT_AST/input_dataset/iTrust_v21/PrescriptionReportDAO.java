package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.PrescriptionReportBean;
import edu.ncsu.csc.itrust.beans.loaders.PrescriptionReportBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class PrescriptionReportDAO {

	private DAOFactory factory;
	private PrescriptionReportBeanLoader loader = new PrescriptionReportBeanLoader();
	
	public PrescriptionReportDAO(DAOFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * Returns a list of information related to prescription reports given all of the office visits and the
	 * patient ID. The patient ID is necessary in case the office visit IDs are for different patients (the
	 * disambiguation is for security reasons).
	 * 
	 * @param ovIDs A java.util.List of Longs for the office visits.
	 * @param patientID A long representing the MID of the patient in question.
	 * @return A java.util.List of prescription reports.
	 * @throws DBException
	 */
	public List<PrescriptionReportBean> byDate(long patientID, String startDate, String endDate)
			throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ndcodes, ovmedication, officevisits "
					+ "WHERE ndcodes.Code=ovmedication.NDCode AND ovmedication.VisitID=officevisits.ID "
					+ "AND PatientID=? AND ((DATE(?) < ovmedication.EndDate AND DATE(?) > ovmedication.StartDate)"
					+ "OR (DATE(?) > ovmedication.StartDate  AND DATE(?) < ovmedication.EndDate ) OR "
					+ "(DATE(?) <= ovmedication.StartDate AND DATE(?) >= ovmedication.StartDate)) "
					+ "ORDER BY VisitDate DESC");
			ps.setLong(1, patientID);
			ps.setString(2, startDate);
			ps.setString(3, startDate);
			ps.setString(4, endDate);
			ps.setString(5, endDate);
			ps.setString(6, startDate);
			ps.setString(7, endDate);
			ResultSet rs = ps.executeQuery();
			List<PrescriptionReportBean> loadlist = loader.loadList(rs);
			rs.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Returns a list of information related to prescription reports given all of the office visits and the
	 * patient ID. The patient ID is necessary in case the office visit IDs are for different patients (the
	 * disambiguation is for security reasons).
	 * 
	 * @param ovIDs A java.util.List of Longs for the office visits.
	 * @param patientID A long representing the MID of the patient in question.
	 * @return A java.util.List of prescription reports.
	 * @throws DBException
	 */
	public List<PrescriptionReportBean> byOfficeVisitAndPatient(List<Long> ovIDs, long patientID)
			throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			String preps = buildPreps(ovIDs.size());
			ps = conn.prepareStatement("SELECT * FROM ndcodes, ovmedication, officevisits "
					+ "WHERE ndcodes.Code=ovmedication.NDCode AND ovmedication.VisitID=officevisits.ID "
					+ "AND PatientID=? AND VisitID IN(" + preps + ") ORDER BY VisitDate DESC");
			ps.setLong(1, patientID);
			prepareOVIDs(ps, ovIDs);
			ResultSet rs = ps.executeQuery();
			List<PrescriptionReportBean> loadlist = loader.loadList(rs);
			rs.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	private void prepareOVIDs(PreparedStatement ps, List<Long> ovIDs) throws SQLException {
		for (int i = 0; i < ovIDs.size(); i++) {
			ps.setLong(i + 2, ovIDs.get(i));
		}
	}

	private String buildPreps(int size) {
		StringBuffer prep = new StringBuffer();
		String result = "";
		for (int i = 0; i < size; i++) {
			prep.append("?,");
		}
		result = prep.toString();
		if (prep.length() > 0){
			return result.substring(0, prep.length() - 1);
		}else{
			return result;
		}
	}
	

}
