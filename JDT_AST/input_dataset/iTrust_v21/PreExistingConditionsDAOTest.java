package edu.ncsu.csc.itrust.unit.dao.obstetrics;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.List;

import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PreExistingConditionsDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class PreExistingConditionsDAOTest {
	DAOFactory prodDAO = TestDAOFactory.getTestInstance();
	
	@Test
	public void testPreExistingConditions() {
		PreExistingConditionsDAO conditions = new PreExistingConditionsDAO(prodDAO);
		
		//Test the validate function
		assertTrue(PreExistingConditionsDAO.validateStr("Test String"));
		assertFalse(PreExistingConditionsDAO.validateStr(null));
		
		//Add some preexisting conditions
		String output1 = conditions.putConditionByMID(1L, "ConditionTest1");
		assertEquals(output1, "Pre-existing condition added OK");
		output1 = conditions.putConditionByMID(1L, "Monkey Butt");
		assertEquals(output1, "Pre-existing condition added OK");
		output1 = conditions.putConditionByMID(1L, "");
		assertEquals(output1, "Cannot add empty string as a pre-existing condition");
		output1 = conditions.putConditionByMID(1L, "This string is longer than is allowed by the rules that govern preexisting conditions");
		assertEquals(output1, "Maximum length of 60 exceeded");
		output1 = conditions.putConditionByMID(1L, "This. Is. An. Illegal. Entry. #illegal");
		assertEquals(output1, "Illegal condition. Only alphanumerics - , and _ are allowed.");
		
		//Get the preexisting conditions we added
		try {
			List<String> conditionList = conditions.getConditionsByMID(1L);
			assertEquals(conditionList.get(0), "ConditionTest1");
			assertEquals(conditionList.get(1), "Monkey Butt");
		} catch (DBException e) {
			fail();
		}
	}

}
