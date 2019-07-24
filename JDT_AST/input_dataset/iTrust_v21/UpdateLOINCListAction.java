package edu.ncsu.csc.itrust.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import edu.ncsu.csc.itrust.beans.LOINCbean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.LOINCDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.LOINCBeanValidator;

/**
 * Handles updating the LOINC Used.
 * 
 * Logical Observation Identifiers Names and Codes (LOINC) is a database and universal standard for
 * identifying medical laboratory observations.
 * 
 * @see http://loinc.org/
 */
public class UpdateLOINCListAction {
	private LOINCDAO lDAO;
	private LOINCBeanValidator validator = new LOINCBeanValidator();

	/**
	 * Sets up defaults
	 * 
	 * @param factory
	 *            The DAOFactory used to create the DAOs used in this action.
	 */
	public UpdateLOINCListAction(DAOFactory factory) {
		lDAO = factory.getLOINCDAO();
	}

	/**
	 * Adds a new LOINC
	 * 
	 * @param diagn
	 *            New LOINC
	 * @return Status message
	 * @throws FormValidationException
	 */
	public String add(LOINCbean diagn) throws FormValidationException, ITrustException {
		validator.validate(diagn);

		List<LOINCbean> lblist = lDAO.getProcedures(diagn.getLabProcedureCode());

		if (!lblist.isEmpty()) {
			throw new FormValidationException("Error: Code already exists.");
		}
		lDAO.addLOINC(diagn);
		return "Success: " + diagn.getLabProcedureCode() + " added";
	}

	/**
	 * Updates a LOINC
	 * 
	 * @param diagn
	 *            new information to update (but same code)
	 * @return Status message
	 * @throws FormValidationException
	 */
	public String updateInformation(LOINCbean diagn) throws FormValidationException {
		validator.validate(diagn);
		try {
			int rows = lDAO.update(diagn);
			if (0 == rows) {
				return "Error: Code not found.";
			} else {
				return "Success: " + diagn.getLabProcedureCode() + " updated";
			}
		} catch (DBException e) {
			
			return e.getMessage();
		}
	}

	/**
	 * 
	 * @param beanInfo an array of string data to add to the LOINC bean
	 * @return a LOINCbean created from the beanInfo
	 */
	private LOINCbean createBean(String[] beanInfo) {
		LOINCbean bean = new LOINCbean();
		for (int i = 0; i < beanInfo.length; i++) {
			boolean empty = beanInfo[i] == null || beanInfo[i].isEmpty();
			if (empty) {
				beanInfo[i] = null;
			}
			switch (i) {
			case 0:
				bean.setLabProcedureCode(beanInfo[i]);
				break;
			case 1:
				bean.setComponent(beanInfo[i]);
				break;
			case 2:
				bean.setKindOfProperty(beanInfo[i]);
				break;
			case 3:
				bean.setTimeAspect(beanInfo[i]);
				break;
			case 4:
				bean.setSystem(beanInfo[i]);
				break;
			case 5:
				bean.setScaleType(beanInfo[i]);
				break;
			case 6:
				bean.setMethodType(beanInfo[i]);
				break;
			default:
				break;
			}
		}
		return bean;
	}

	/**
	 * Reads a LOINC data file line by line, looking for valid LOINC data to add to the database.
	 * 
	 * @param data
	 *            an InputStream associated with the file to parse
	 * @param ignoreDupData
	 *            false if existing in the database should be updated if new data exists in the file
	 * @return a list of messages concerning the parsing of the file
	 * @throws DBException 
	 */
	public List<String> parseLOINCFile(InputStream data, boolean ignoreDupData) throws DBException {
		ArrayList<String> results = new ArrayList<String>();
		BufferedReader buf;
		try {
			buf = new BufferedReader(new InputStreamReader(data, "UTF-8"));
			String line = null;
			int linesInserted = 0;
			int dupDataSkipped = 0;
			int dupDataUpdated = 0;
			int lnum = 1;
			// Match for starting with a LOINC code: "NUMBERS AND DASHES"
			String labRegEx = "^\"([\\d-]*)\"\\t";
			Pattern req = Pattern.compile(labRegEx);
			try {
				while ((line = buf.readLine()) != null) {
					try {
						Matcher reqMatch = req.matcher(line);
						// The line must start with a LOINC code to be further processed
						boolean found = line.length() > 0 && line.charAt(0) == '"' && reqMatch.find();
						if (!found) {
							results.add("IGNORED LINE " + lnum + ": " + line);
						} else {
							String beanInfo[] = new String[7];
							// split the line along tabs
							String parts[] = line.split("\t");
							
							// the line must have been split into at least 2 parts to continue
							if (parts != null && !parts[0].equals(line)) {
								for (int i = 0; i < beanInfo.length && i < parts.length; i++) {
									// this was an empty field
									if (parts[i].length() <= 2) {
										beanInfo[i] = "";
									} else if (parts[i].charAt(0) != '"'
											|| parts[i].charAt(parts[i].length() - 1) != '"') {
										// The first 7 fields of LOINC data must be surrounded by quotes. If they aren't then it's wrong.
										throw new FormValidationException(
												"Bad LOINC data line. All fields must be surrounded by quotation marks.");
									} else {
										// strip quotes
										beanInfo[i] = parts[i].substring(1, parts[i].length() - 1);
									}
								}

								LOINCbean bean = createBean(beanInfo);

								boolean duplicate = false;
								try {
									add(bean);
									// success
									linesInserted++;
								} catch (Exception e) {
									if (!e.getMessage().contains("Code already exists")) {
										results.add("ERROR, LINE " + lnum + ": " + line + " " + e.getMessage());
									} else {
										duplicate = true;
									}
								}
								if (duplicate) {
									if (!ignoreDupData) {
										String res = "";
										res = updateInformation(bean);
										if (res.contains("Error")) {
											throw new FormValidationException(res);
										} else {
											// We updated some data
											dupDataUpdated++;
										}
									} else {
										// We skipped a line
										dupDataSkipped++;
									}
								}
							}
						}
					} catch (FormValidationException e) {
						// If we got this we should report the line as an error
						results.add("ERROR, LINE " + lnum + ": " + line + " " + e.getMessage());
					}
					lnum++;
				}
			} catch (IOException e) {
				// this is an IO error and not a data error
				// treat it differently
				results.add("ERROR: " + e.getMessage());
			}
			// No data was changed
			if (linesInserted <= 0 && dupDataUpdated <= 0) {
				// Files are only invalid if we didnt add any new data nor update any existing data.
				if (dupDataUpdated <= 0 && dupDataSkipped <= 0) {
					results.add("File invalid. No LOINC data added.");
				} else {
					// Otherwise, we just skipped existing data lines.
					results.add("No new LOINC data added. " + dupDataSkipped
							+ " lines detected as duplicate data.");
				}
			} else {
				// Tell the user how many lines were updated/inserted.
				results.add("Successfully added " + linesInserted + " lines of new LOINC data. Updated "
						+ dupDataUpdated + " lines of existing LOINC data.");
			}
			
		} catch (UnsupportedEncodingException e1) {
			results.add("ERROR: " + e1.getMessage());;
		}
		return results;
	}
	
}
