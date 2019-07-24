package edu.ncsu.csc.itrust.unit.enums;

import edu.ncsu.csc.itrust.enums.FlagValue;
import junit.framework.TestCase;

public class FlagValueTest extends TestCase {
	public void testParse() throws Exception {
		assertEquals(FlagValue.HighBloodPressure, FlagValue.parseEnum("High Blood Pressure"));
		assertEquals(FlagValue.AdvancedMaternalAge, FlagValue.parseEnum("Advanced Maternal Age"));
		assertEquals(FlagValue.HighBloodPressure, FlagValue.parseEnum("High Blood Pressure"));
		assertEquals(FlagValue.MaternalAllergies, FlagValue.parseEnum("Maternal Allergies"));
		assertEquals(FlagValue.LowLyingPlacenta, FlagValue.parseEnum("Low-Lying Placenta"));
		assertEquals(FlagValue.GeneticMiscarriage, FlagValue.parseEnum("Genetic Miscarriage"));
		assertEquals(FlagValue.AbnormalFHR, FlagValue.parseEnum("Abnormal FHR"));
		assertEquals(FlagValue.Twins, FlagValue.parseEnum("Twins"));
		assertEquals(FlagValue.WeightChange, FlagValue.parseEnum("Abnormal Weight Change"));
	}
}
