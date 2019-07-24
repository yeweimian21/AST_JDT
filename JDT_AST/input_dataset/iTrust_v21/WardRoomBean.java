package edu.ncsu.csc.itrust.beans;

/**
 * A bean for storing data about a WardRoom.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters. 
 * to create these easily)
 */
public class WardRoomBean {
	
	long roomID = 0;
	Long occupiedBy = null;
	long inWard = 0;
	String roomName = "";
	String status = "Clean";

	public WardRoomBean(long roomID, long occupiedBy, long inWard, String roomName, String status){
		this.roomID = roomID;
		this.occupiedBy = occupiedBy;
		this.inWard = inWard;
		this.roomName = roomName;
		this.status = status;
	}

	public long getRoomID() {
		return roomID;
	}

	public void setRoomID(long roomID) {
		this.roomID = roomID;
	}

	public Long getOccupiedBy() {
		return occupiedBy;
	}

	public void setOccupiedBy(Long occupiedBy) {
		this.occupiedBy = occupiedBy;
	}

	public long getInWard() {
		return inWard;
	}

	public void setInWard(long inWard) {
		this.inWard = inWard;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass().equals(this.getClass()) && this.equals((WardRoomBean) obj);
	}

	@Override
	public int hashCode() {
		return 42; // any arbitrary constant will do
	}

	private boolean equals(WardRoomBean other) {
		return roomID == other.roomID && roomName.equals(other.roomName);
	}
}
