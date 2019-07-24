package edu.ncsu.csc.itrust.beans;

import java.util.Comparator;

import edu.ncsu.csc.itrust.action.ZipCodeAction;
import edu.ncsu.csc.itrust.exception.DBException;

public class DistanceComparator implements Comparator<PersonnelBean> {
	ZipCodeAction action;
	String patientZipCode;
	
	@Override
	public int compare(PersonnelBean bean1, PersonnelBean bean2)
	{
				try {
					return (int) (action.calcDistance(bean1.getZip(), patientZipCode) - action.calcDistance(bean2.getZip(), patientZipCode));
				} catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return -1;
	}
	
	public DistanceComparator(ZipCodeAction action, String patientZipCode)
	{
		this.action = action;
		this.patientZipCode = patientZipCode;
	}

}
