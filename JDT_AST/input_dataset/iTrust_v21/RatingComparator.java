package edu.ncsu.csc.itrust.beans;

import java.util.Comparator;

import edu.ncsu.csc.itrust.action.ReviewsAction;
import edu.ncsu.csc.itrust.exception.DBException;

public class RatingComparator implements Comparator<PersonnelBean> {
	ReviewsAction action;
	@Override
	public int compare(PersonnelBean bean1, PersonnelBean bean2)
	{
			double avg1 = 0;
			double avg2 = 0;
			try {
				avg1 = action.getAverageRating(bean1.getMID());
				avg2 = action.getAverageRating(bean2.getMID());
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return	Double.compare(avg1, avg2);
	}
	
	public RatingComparator(ReviewsAction action)
	{
		this.action = action;
	}

}
