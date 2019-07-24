package edu.ncsu.csc.itrust.unit.dao.flags;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.FlagsBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FlagsDAO;
import edu.ncsu.csc.itrust.enums.FlagValue;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class FlagsDAOTest {
	DAOFactory prodDAO = TestDAOFactory.getTestInstance();
	FlagsDAO dao = new FlagsDAO(prodDAO);
	FlagsBean bean1;
	FlagsBean bean2;
	
	@Before
	public void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.uc64();
		
		//make a bean
		bean1 = new FlagsBean();
		bean1.setMid(400L);
		bean1.setPregId(0L);
		bean1.setFlagValue(FlagValue.Twins);
		bean1.setFlagged(false);
		
		bean2 = new FlagsBean();
		bean2.setMid(400L);
		bean2.setPregId(0L);
		bean2.setFlagValue(FlagValue.HighBloodPressure);
		bean2.setFlagged(true);
	}
	
	@Test
	public void testGetSetFlag() throws DBException {
		//add the bean
		dao.setFlag(bean1);
		assertEquals(dao.getFlag(bean1), bean1);
		
		//set the flag to true
		bean1.setFlagged(true);
		dao.setFlag(bean1);
		assertEquals(dao.getFlag(bean1), bean1);
		
		//assert it exists by OID
		dao.setFlag(bean2);
		assertEquals(dao.getFlag(bean2), bean2);
	}
}
