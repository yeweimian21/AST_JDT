package edu.ncsu.csc.itrust.unit.bean;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.FlagsBean;
import edu.ncsu.csc.itrust.enums.FlagValue;

public class FlagBeanTest {

	@Test
	public void testGetSet() throws ParseException {
		FlagsBean bean = new FlagsBean();
		bean.setFid(1L);
		bean.setMid(1L);
		bean.setPregId(1L);
		bean.setFlagValue(FlagValue.AbnormalFHR);
		bean.setFlagged(true);

		assertEquals(bean.getFid(), 1L);
		assertEquals(bean.getMid(), 1L);
		assertEquals(bean.getPregId(), 1L);
		assertEquals(bean.getFlagValue(), FlagValue.AbnormalFHR);
		assertEquals(bean.isFlagged(), true);		
	}
}
