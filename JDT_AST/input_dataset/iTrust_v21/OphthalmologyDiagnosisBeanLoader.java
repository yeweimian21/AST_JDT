package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.OphthalmologyDiagnosisBean;

/**
 * A loader for OphthalmologyDiagnosisBean.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency.
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader} 
 */

public class OphthalmologyDiagnosisBeanLoader implements BeanLoader<OphthalmologyDiagnosisBean> {
	private boolean loadOPDiagnosisID = false;

	public OphthalmologyDiagnosisBeanLoader() {
		loadOPDiagnosisID = false;
	}

	public OphthalmologyDiagnosisBeanLoader(boolean loadOPDiagnosisID) {
		this.loadOPDiagnosisID = loadOPDiagnosisID;
	}

	public List<OphthalmologyDiagnosisBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<OphthalmologyDiagnosisBean> list = new ArrayList<OphthalmologyDiagnosisBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	public OphthalmologyDiagnosisBean loadSingle(ResultSet rs) throws SQLException {

		OphthalmologyDiagnosisBean diag = new OphthalmologyDiagnosisBean(rs.getString("Code"), rs.getString("Description"), rs.getString("Chronic"), rs.getString("URL"));
		if (loadOPDiagnosisID) {
			diag.setOpDiagnosisID(rs.getInt("ID"));
			diag.setVisitID(rs.getLong("VisitID"));
		}
		return diag;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, OphthalmologyDiagnosisBean bean) throws SQLException {
		throw new IllegalStateException("unimplemented!");
	}
}
