package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.OphthalmologySurgeryRecordBean;

/**
 * A loader for OphthalmologySurgeryRecordBean.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class OphthalmologySurgeryRecordLoader implements BeanLoader<OphthalmologySurgeryRecordBean> {
	
	/**
	 * Returns a list of beans with data from a ResultSet.
	 * @param rs ResultSet containing data from the database
	 * @return A list of OphthalmologySurgeryRecordBean created from the ResultSet parameter.
	 * @throws SQLException thrown when there is a error resulting from accessing a field of the ResultSet.
	 */
	public List<OphthalmologySurgeryRecordBean> loadList(ResultSet rs) throws SQLException {
		List<OphthalmologySurgeryRecordBean> list = new ArrayList<OphthalmologySurgeryRecordBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}
	
	/**
	 * Internal method used to load results into beans from ResultSets.
	 * @param rs ResultSet that data is coming out of.
	 * @param p bean that data is going into.
	 * @throws SQLException thrown when there is a error resulting from accessing a field of the ResultSet.
	 */
	private void loadCommon(ResultSet rs, OphthalmologySurgeryRecordBean p) throws SQLException {
		p.setMid(rs.getLong("mid"));
		p.setOid(rs.getLong("oid"));
		p.setVisitDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date(rs.getDate("dateVisit").getTime())));
		p.setLastName(rs.getString("docLastName"));
		p.setFirstName(rs.getString("docFirstName"));
		if(rs.getObject("vaNumOD") != null){
			p.setVaNumOD(rs.getInt("vaNumOD"));
		} else{
			p.setVaNumOD(null);
		}
		if(rs.getObject("vaDenOD") != null){
			p.setVaDenOD(rs.getInt("vaDenOD"));
		} else{
			p.setVaDenOD(null);
		}
		if(rs.getObject("vaNumOS") != null){
			p.setVaNumOS(rs.getInt("vaNumOS"));
		} else{
			p.setVaNumOS(null);
		}
		if(rs.getObject("vaDenOS") != null){
			p.setVaDenOS(rs.getInt("vaDenOS"));
		} else{
			p.setVaDenOS(null);
		}
		if(rs.getObject("sphereOD") != null){
			p.setSphereOD(rs.getDouble("sphereOD"));
		} else{
			p.setSphereOD(null);
		}
		if(rs.getObject("sphereOS") != null){
			p.setSphereOS(rs.getDouble("sphereOS"));
		} else{
			p.setSphereOS(null);
		}
		if(rs.getObject("cylinderOD") != null){
			p.setCylinderOD(rs.getDouble("cylinderOD"));
		} else{
			p.setCylinderOD(null);
		}
		if(rs.getObject("cylinderOS") != null){
			p.setCylinderOS(rs.getDouble("cylinderOS"));
		} else{
			p.setCylinderOS(null);
		}
		if(rs.getObject("axisOD") != null){
			p.setAxisOD(rs.getInt("axisOD"));
		} else{
			p.setAxisOD(null);
		}
		if(rs.getObject("axisOS") != null){
			p.setAxisOS(rs.getInt("axisOS"));
		} else{
			p.setAxisOS(null);
		}
		if(rs.getObject("addOD") != null){
			p.setAddOD(rs.getDouble("addOD"));
		} else{
			p.setAddOD(null);
		}
		if(rs.getObject("addOS") != null){
			p.setAddOS(rs.getDouble("addOS"));
		} else{
			p.setAddOS(null);
		}
		if(rs.getObject("surgery") != null){
			p.setSurgery(rs.getString("surgery"));
		} else{
			p.setSurgery(null);
		}
		if(rs.getObject("surgeryNotes") != null){
			p.setSurgeryNotes(rs.getString("surgeryNotes"));
		} else{
			p.setSurgeryNotes(null);
		}
	}
	
	/**
	 * Takes the first result out of a ResultSet and returns a bean with that data in it.
	 * @param rs ResultSet containing data from the database.
	 * @return p bean containing the data.
	 * @throws SQLException thrown when there is a error resulting from accessing a field of the ResultSet.
	 */
	public OphthalmologySurgeryRecordBean loadSingle(ResultSet rs) throws SQLException {
		OphthalmologySurgeryRecordBean p = new OphthalmologySurgeryRecordBean();
		loadCommon(rs, p);
		return p;
	}
	
	/**
	 * Loads values into the parameters of a preparedStatement from the given OphthalmologySurgeryRecordBean.
	 * @param ps The PreparedStatement that will have it's parameters filled in.
	 * @param p The OphthalmologySurgeryRecordBean used to fill in the parameters of the preparedStatement.
	 * @return The preparedStatement with the parameters filled in.
	 * @throws SQLException thrown when there is a error resulting from accessing a field of the ResultSet.
	 */
	public PreparedStatement loadParameters(PreparedStatement ps, OphthalmologySurgeryRecordBean p) throws SQLException {
		int i = 1;
	    ps.setLong(i++, p.getMid());
		ps.setDate(i++, new java.sql.Date(p.getVisitDate().getTime()));
		ps.setString(i++, p.getLastName());
		ps.setString(i++, p.getFirstName());
		if(p.getVaNumOD() != null){
			ps.setInt(i++, p.getVaNumOD());
		} else{
			ps.setNull(i++, Types.NULL);
		}
		if(p.getVaDenOD() != null){
			ps.setInt(i++, p.getVaDenOD());
		} else{
			ps.setNull(i++, Types.NULL);
		}
		if(p.getVaNumOS() != null){
			ps.setInt(i++, p.getVaNumOS());
		} else{
			ps.setNull(i++, Types.NULL);
		}
		if(p.getVaDenOS() != null){
			ps.setInt(i++, p.getVaDenOS());
		} else{
			ps.setNull(i++, Types.NULL);
		}
		if(p.getSphereOD() != null){
			ps.setDouble(i++, p.getSphereOD());
		} else{
			ps.setNull(i++, Types.NULL);
		}
		if(p.getSphereOS() != null){
			ps.setDouble(i++, p.getSphereOS());
		} else{
			ps.setNull(i++, Types.NULL);
		}
		if(p.getCylinderOD() != null){
			ps.setDouble(i++, p.getCylinderOD());
		} else{
			ps.setNull(i++, Types.NULL);
		}
		if(p.getCylinderOS() != null){
			ps.setDouble(i++, p.getCylinderOS());
		} else{
			ps.setNull(i++, Types.NULL);
		}
		if(p.getAxisOD() != null){
			ps.setInt(i++, p.getAxisOD());
		} else{
			ps.setNull(i++, Types.NULL);
		}
		if(p.getAxisOS() != null){
			ps.setInt(i++, p.getAxisOS());
		} else{
			ps.setNull(i++, Types.NULL);
		}
		if(p.getAddOD() != null){
			ps.setDouble(i++, p.getAddOD());
		} else{
			ps.setNull(i++, Types.NULL);
		}
		if(p.getAddOS() != null){
			ps.setDouble(i++, p.getAddOS());
		} else{
			ps.setNull(i++, Types.NULL);
		}
		if(p.getSurgery() != null){
			ps.setString(i++, p.getSurgery());
		} else{
			ps.setNull(i++, Types.NULL);
		}
		if(p.getSurgeryNotes() != null){
			ps.setString(i++, p.getSurgeryNotes());
		} else{
			ps.setNull(i++, Types.NULL);
		}
		return ps;
	}
}