package edu.ncsu.csc.itrust.beans;

import edu.ncsu.csc.itrust.enums.FlagValue;

/**
 * FlagsBean represents a flag.
 */
public class FlagsBean {
	private long fid;
	private long mid;
	private long pregId;
	private FlagValue flagValue;
	private boolean flagged;
	
	/** the max */
	public static final int FHR_MAX_TRIGGER = 170;
	/** the min */
	public static final int FHR_MIN_TRIGGER = 105;
	/** the max */
	public static final int SYS_BP_MAX_TRIGGER = 140;
	/** the min */
	public static final int DIA_BP_MAX_TRIGGER = 90;
	/** the weight bounds*/
	public static final double WEIGHTGAINBMIBOUNDS[] = {18.5, 24.9, 29.9, 999};
	/** minimum */
	public static final double WEIGHTGAINBMIMIN[] = {28, 25, 15, 11};
	/** maximum */
	public static final double WeightGainBMIMax[] = {40, 35, 25, 20};
	/** twins minimum */
	public static final double WeightGainTwinsBMIMin[] = {37, 37, 31, 25};
	/** twins maximum */
	public static final double WeightGainTwinsBMIMax[] = {54, 54, 50, 42};
	
	/**
	 * Returns the FID
	 * @return the fid
	 */
	public long getFid() {
		return fid;
	}
	
	/**
	 * Sets the FID
	 * @param fid
	 */
	public void setFid(long fid) {
		this.fid = fid;
	}
	
	/**
	 * Returns the MID
	 * @return
	 */
	public long getMid() {
		return mid;
	}
	
	/**
	 * Sets the MID
	 * @param mid
	 */
	public void setMid(long mid) {
		this.mid = mid;
	}
	
	/**
	 * Returns the flag enum value
	 * @return
	 */
	public FlagValue getFlagValue() {
		return flagValue;
	}
	
	/**
	 * Sets the flag enum value
	 * @param flagValue
	 */
	public void setFlagValue(FlagValue flagValue) {
		this.flagValue = flagValue;
	}
	
	/**
	 * Returns true if the flag is set
	 * @return
	 */
	public boolean isFlagged() {
		return flagged;
	}
	
	/**
	 * Sets the flag boolean value
	 * @param flagged
	 */
	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}
	
	/**
	 * Returns the pregnancy ID
	 * @return
	 */
	public long getPregId() {
		return pregId;
	}
	
	/**
	 * Sets the pregnancy ID
	 * @param pregId
	 */
	public void setPregId(long pregId) {
		this.pregId = pregId;
	}
	
}
