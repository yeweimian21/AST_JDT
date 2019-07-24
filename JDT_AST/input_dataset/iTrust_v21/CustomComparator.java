package edu.ncsu.csc.itrust.beans;

import java.util.Comparator;

public class CustomComparator implements Comparator<PersonnelBean> {
	@Override
	public int compare(PersonnelBean bean1, PersonnelBean bean2)
	{
		return (int) (bean1.getMID() - bean2.getMID());
	}

}
