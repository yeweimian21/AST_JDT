package edu.ncsu.csc.itrust.unit.bean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.ReviewsAction;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.beans.RatingComparator;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Tests the comparator for ratings
 *
 */
public class RatingComparatorTest extends TestCase
{
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private ReviewsAction action;
	
	/**
	 * Gets the standard data and initializes a review action
	 */
	@Override
	protected void setUp() throws IOException, SQLException
	{
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		action = new ReviewsAction(factory, 2L);
	}
	
	
	/**
	 * Tests the comparator of two things
	 */
	public void testComparator()
	{
		PersonnelDAO dao = new PersonnelDAO(factory);
		List<PersonnelBean> list = new ArrayList<PersonnelBean>();
		try {
			list = dao.getAllPersonnel();
		} catch (DBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try
		{
			if(list.size() != 0)
				Collections.sort(list, new RatingComparator(action));
			
		}
		catch(Exception e)
		{
			fail();
		}
		
	}
}
