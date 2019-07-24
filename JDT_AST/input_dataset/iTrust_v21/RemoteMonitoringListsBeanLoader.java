package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.TelemedicineBean;

/**
 * A loader for RemoteMonitoringDataBeans.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class RemoteMonitoringListsBeanLoader implements BeanLoader<TelemedicineBean> {

	public List<TelemedicineBean> loadList(ResultSet rs) throws SQLException {
		List<TelemedicineBean> list = new ArrayList<TelemedicineBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, TelemedicineBean bean) throws SQLException {
		throw new IllegalStateException("unimplemented!");
	}

	public TelemedicineBean loadSingle(ResultSet rs) throws SQLException {
		TelemedicineBean d = new TelemedicineBean();
		d.setSystolicBloodPressureAllowed(rs.getBoolean("SystolicBloodPressure"));
		d.setDiastolicBloodPressureAllowed(rs.getBoolean("DiastolicBloodPressure"));
		d.setGlucoseLevelAllowed(rs.getBoolean("GlucoseLevel"));
		d.setHeightAllowed(rs.getBoolean("Height"));
		d.setWeightAllowed(rs.getBoolean("Weight"));
		d.setPedometerReadingAllowed(rs.getBoolean("PedometerReading"));
		return d;
	}
}
