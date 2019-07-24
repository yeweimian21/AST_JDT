package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.VerboseReferralBean;

/**
 * A loader for ReferralBeans.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class VerboseReferralBeanLoader implements BeanLoader<VerboseReferralBean> {
	

	public VerboseReferralBeanLoader() {
	}

	public List<VerboseReferralBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<VerboseReferralBean> list = new ArrayList<VerboseReferralBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	public VerboseReferralBean loadSingle(ResultSet rs) throws SQLException {
		VerboseReferralBean ref = new VerboseReferralBean();
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
		
		ref.setOvDate(new SimpleDateFormat("MM/dd/yyyy").format(rs.getDate("visitDate")));
		ref.setPatientName(rs.getString("patientName"));
		ref.setReceiverName(rs.getString("receiverName"));
		ref.setSenderName(rs.getString("senderName"));
		
		return ref;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, VerboseReferralBean ref) throws SQLException {
		throw new RuntimeException("Not Implemented");
	}
}
