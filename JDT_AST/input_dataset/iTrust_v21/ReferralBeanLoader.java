package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.ReferralBean;

/**
 * A loader for ReferralBeans.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class ReferralBeanLoader implements BeanLoader<ReferralBean> {
	
	public ReferralBeanLoader() {

	}

	public List<ReferralBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<ReferralBean> list = new ArrayList<ReferralBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	public ReferralBean loadSingle(ResultSet rs) throws SQLException {
		ReferralBean ref = new ReferralBean();
		SimpleDateFormat thisdateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm a");
		// ERIC: fixed to correct for the mysql database bug plaguing java 1.5
		ref.setId(rs.getInt("id"));
		ref.setSenderID(rs.getLong("SenderID"));
		ref.setReceiverID(rs.getLong("ReceiverID"));
		ref.setPatientID(rs.getInt("PatientID"));
		ref.setReferralDetails(rs.getString("ReferralDetails"));
		ref.setOvid(rs.getLong("OVID"));
		ref.setTimeStamp(thisdateFormat.format(rs.getTimestamp("TimeStamp")));
		ref.setViewedByHCP(rs.getBoolean("viewed_by_HCP"));
		ref.setViewedByPatient(rs.getBoolean("viewed_by_patient"));
		ref.setPriority(rs.getInt("PriorityCode"));
		
		return ref;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, ReferralBean ref) throws SQLException {
		ps.setLong(1, ref.getPatientID());
		ps.setLong(2, ref.getSenderID());
		ps.setLong(3, ref.getReceiverID());
		ps.setString(4, ref.getReferralDetails());
		ps.setLong(5, ref.getOvid());
		ps.setBoolean(6, ref.isViewedByPatient());
		ps.setBoolean(7, ref.isViewedByHCP());
		ps.setInt(8, ref.getPriority());
		return ps;
	}
}
