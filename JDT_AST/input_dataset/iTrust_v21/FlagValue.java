package edu.ncsu.csc.itrust.enums;

/**
 * FlagValue is an enum that represents the different flags that exist in iTrust for obstetrics.
 */
public enum FlagValue {
	/** Has High Blood Pressure */
	HighBloodPressure("High Blood Pressure", "hbp"), 
	/** Has an advanced maternal age */
	AdvancedMaternalAge("Advanced Maternal Age", "ama"),
	/** Has maternal allergies*/
	MaternalAllergies("Maternal Allergies", "allergies"), 
	/** Has low lying placenta */
	LowLyingPlacenta("Low-Lying Placenta", "placenta"),
	/** Has a genetic miscarriage */
	GeneticMiscarriage("Genetic Miscarriage", "geneticMiscarriage"), 
	/** Has abnormality */
	AbnormalFHR("Abnormal FHR", "fhr"),
	/** Has twins */
	Twins("Twins", "twins"), 
	/** Has a weight change */
	WeightChange("Abnormal Weight Change", "weightChange"), 
	/** has rh negative blood type */
	rhNeg("Negative Blood Type", "rhNeg"),
	/** has preexisting conditions */
	PreExistingConditions("Pregnancy relevant pre-existing conditions", "pec");
	
	private String name; //represents a good output string
	private String id; //represents a good id/name for an HTML form
	
	/**
	 * Constructor for a FlagValue that takes a name and id.
	 * @param name the flag name
	 * @param id the flag ID
	 */
	private FlagValue(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	/**
	 * Custom replacement method for the un-overridable valueOf method.
	 * Parses non-case sensitive and can use either ID or Name as needed.
	 * @param idOrName A string, assumed to likely be a match to either ID or name of some FlagValue
	 * @return The corresponding FlagValue
	 */
	public static FlagValue parseEnum(String idOrName) {
		for(FlagValue v : values())
            if(v.id.equalsIgnoreCase(idOrName) || v.name.equalsIgnoreCase(idOrName)) return v;
        throw new IllegalArgumentException();
	}
	
	/**
	 * Returns the ID of the flag.
	 * @return the ID of the flag
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * Returns the string value of the flag.
	 * @return the string value of the flag
	 */
	@Override
	public String toString() {
		if (name == null)
			return "";
		return name;
	}
}
