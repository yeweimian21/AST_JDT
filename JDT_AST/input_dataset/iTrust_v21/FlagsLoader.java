package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.FlagsBean;
import edu.ncsu.csc.itrust.enums.FlagValue;

/**
 * A loader for ObstetricsRecordBeans.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class FlagsLoader implements BeanLoader<FlagsBean> {
	/**
	 * loadList
	 * @param rs rs
	 * @throws SQLException
	 */
	public List<FlagsBean> loadList(ResultSet rs) throws SQLException {
		List<FlagsBean> list = new ArrayList<FlagsBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	/**
	 * Loads the FID, MID, and flag value from the result set into the bean.
	 * @param rs
	 * @param p
	 * @throws SQLException
	 */
	private void loadCommon(ResultSet rs, FlagsBean p) throws SQLException {
		p.setFid(rs.getLong("FID"));
		p.setMid(rs.getLong("MID"));
		p.setFlagValue(FlagValue.valueOf(rs.getString("flagValue")));
		p.setFlagged(true); //if this record is found, this is true
	}
	
	/**
	 * loadSingle
	 * @param rs rs
	 * @return p
	 * @throws SQLException
	 */
	public FlagsBean loadSingle(ResultSet rs) throws SQLException {
		FlagsBean p = new FlagsBean();
		loadCommon(rs, p);
		return p;
	}
	
	/**
	 * loadParameters
	 * Sets MID, FID (which is auto-incremented later by the table), and FlagValue.
	 * Since the only time this will be called is for putting things into the table,
	 * it is reasonable to assume that we want the flagged value to be true.
	 * @throws SQLException
	 */
	public PreparedStatement loadParameters(PreparedStatement ps, FlagsBean p) throws SQLException {
		int i = 1;
	    ps.setLong(i++, p.getFid());
	    ps.setLong(i++, p.getMid());
	    ps.setLong(i++, p.getPregId());
	    ps.setString(i++, p.getFlagValue().toString());
		return ps;
	}
	
}