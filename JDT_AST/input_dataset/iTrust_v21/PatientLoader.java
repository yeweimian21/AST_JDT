package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PatientHistoryBean;

/**
 * A loader for PatientBeans.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class PatientLoader implements BeanLoader<PatientBean> {
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * loadList
	 * @param rs rs
	 * @throws SQLException
	 */
	public List<PatientBean> loadList(ResultSet rs) throws SQLException {
		List<PatientBean> list = new ArrayList<PatientBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	private void loadCommon(ResultSet rs, PatientBean p) throws SQLException{
		p.setMID(rs.getInt("MID"));
		p.setFirstName(rs.getString("firstName"));
		p.setLastName(rs.getString("LastName"));
		Date dateOfBirth = rs.getDate("DateOfBirth");
		if (dateOfBirth != null){
			p.setDateOfBirthStr(DATE_FORMAT.format(dateOfBirth));
		}
		Date dateOfDeath = rs.getDate("DateOfDeath");
		if (dateOfDeath != null){
			p.setDateOfDeathStr(DATE_FORMAT.format(dateOfDeath));
		}
		p.setCauseOfDeath(rs.getString("CauseOfDeath"));
		p.setEmail(rs.getString("Email"));
		p.setStreetAddress1(rs.getString("address1"));
		p.setStreetAddress2(rs.getString("address2"));
		p.setCity(rs.getString("City"));
		p.setState(rs.getString("State"));
		p.setZip((rs.getString("Zip")));
		p.setPhone((rs.getString("phone")));
		p.setEmergencyName(rs.getString("eName"));
		p.setEmergencyPhone(rs.getString("ePhone"));
		p.setIcName(rs.getString("icName"));
		p.setIcAddress1(rs.getString("icAddress1"));
		p.setIcAddress2(rs.getString("icAddress2"));
		p.setIcCity(rs.getString("icCity"));
		p.setIcState(rs.getString("icState"));
		p.setIcZip(rs.getString("icZip"));
		p.setIcPhone(rs.getString("icPhone"));
		p.setIcID(rs.getString("icID"));
		p.setMotherMID(rs.getString("MotherMID"));
		p.setFatherMID(rs.getString("FatherMID"));
		p.setBloodTypeStr(rs.getString("BloodType"));
		p.setEthnicityStr(rs.getString("Ethnicity"));
		p.setGenderStr(rs.getString("Gender"));
		p.setTopicalNotes(rs.getString("TopicalNotes"));
		p.setCreditCardType(rs.getString("CreditCardType"));
		p.setCreditCardNumber(rs.getString("CreditCardNumber"));
		p.setDirectionsToHome(rs.getString("DirectionsToHome"));
		p.setReligion(rs.getString("Religion"));
		p.setLanguage(rs.getString("Language"));
		p.setSpiritualPractices(rs.getString("SpiritualPractices"));
		p.setAlternateName(rs.getString("AlternateName"));
		Date dateOfDeactivation = rs.getDate("DateOfDeactivation");
		if (dateOfDeactivation != null){
			p.setDateOfDeactivationStr(DATE_FORMAT.format(dateOfDeactivation));
		}
	}
	
	/**
	 * loadSingle
	 * @param rs rs
	 * @return p
	 * @throws SQLException
	 */
	public PatientBean loadSingle(ResultSet rs) throws SQLException {
		PatientBean p = new PatientBean();
		loadCommon(rs, p);
		return p;
	}

	/**
	 * loadSingleHistory
	 * @param rs rs
	 * @return p
	 * @throws SQLException
	 */
	public PatientHistoryBean loadSingleHistory(ResultSet rs) throws SQLException {
		PatientHistoryBean p = new PatientHistoryBean();
		p.setChangeMID(rs.getLong("changeMID"));
		Date changeDate = rs.getDate("changeDate");
		if (changeDate != null){ 
			p.setChangeDateStr(DATE_FORMAT.format(changeDate));
		}
		loadCommon(rs, p);
		return p;
	}
	
	/**
	 * loadParameters
	 * @throws SQLException
	 */
	public PreparedStatement loadParameters(PreparedStatement ps, PatientBean p) throws SQLException {
		int i = 1;
		ps.setString(i++, p.getFirstName());
		ps.setString(i++, p.getLastName());
		ps.setString(i++, p.getEmail());
		ps.setString(i++, p.getStreetAddress1());
		ps.setString(i++, p.getStreetAddress2());
		ps.setString(i++, p.getCity());
		ps.setString(i++, p.getState());
		ps.setString(i++, p.getZip());
		ps.setString(i++, p.getPhone());
		ps.setString(i++, p.getEmergencyName());
		ps.setString(i++, p.getEmergencyPhone());
		ps.setString(i++, p.getIcName());
		ps.setString(i++, p.getIcAddress1());
		ps.setString(i++, p.getIcAddress2());
		ps.setString(i++, p.getIcCity());
		ps.setString(i++, p.getIcState());
		ps.setString(i++, p.getIcZip());
		ps.setString(i++, p.getIcPhone());
		ps.setString(i++, p.getIcID());
		Date date = null;
		try {
			date = new java.sql.Date(DATE_FORMAT.parse(p.getDateOfBirthStr())
					.getTime());
		} catch (ParseException e) {
			//TODO
		}
		ps.setDate(i++, date);
		date = null;
		try {
			date = new java.sql.Date(DATE_FORMAT.parse(p.getDateOfDeathStr())
					.getTime());
		} catch (ParseException e) {
			if ("".equals(p.getDateOfDeathStr())){
				date = null;
			}else{
				
			}
		}
		ps.setDate(i++, date);
		ps.setString(i++, p.getCauseOfDeath());
		ps.setString(i++, p.getMotherMID());
		ps.setString(i++, p.getFatherMID());
		ps.setString(i++, p.getBloodType().getName());
		ps.setString(i++, p.getEthnicity().getName());
		ps.setString(i++, p.getGender().getName());
		ps.setString(i++, p.getTopicalNotes());
		ps.setString(i++, p.getCreditCardType());
		ps.setString(i++, p.getCreditCardNumber());
		ps.setString(i++, p.getDirectionsToHome());
		ps.setString(i++, p.getReligion());
		ps.setString(i++, p.getLanguage());
		ps.setString(i++, p.getSpiritualPractices());
		ps.setString(i++, p.getAlternateName());
		date = null;
		try {
			date = new java.sql.Date(DATE_FORMAT.parse(p.getDateOfDeactivationStr())
					.getTime());
		} catch (ParseException e) {
			if ("".equals(p.getDateOfDeactivationStr())){
				date = null;
			}else{
				
			}
		}catch (NullPointerException e) {
			if ("".equals(p.getDateOfDeactivationStr())){
				date = null;
			}else{
				
			}
		}
		ps.setDate(i++, date);
		return ps;
	}
}
