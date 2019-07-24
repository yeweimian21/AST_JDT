package edu.ncsu.csc.itrust.beans;

/**
 * ApptRequestBean
 */
public class ApptRequestBean {
	private ApptBean requestedAppt;
	private Boolean status;

	/**
	 * getRequestedAppt
	 * @return requestedAppt
	 */
	public ApptBean getRequestedAppt() {
		return requestedAppt;
	}

	/**
	 * isPending
	 * @return status
	 */
	public boolean isPending() {
		return status == null;
	}

	/**
	 * isAccepted
	 * @return status
	 */
	public boolean isAccepted() {
		return status != null && status.booleanValue();
	}

	/**
	 * setRequestedAppt
	 * @param appt appt
	 */
	public void setRequestedAppt(ApptBean appt) {
		requestedAppt = appt;
	}

	/**
	 * setPending
	 * @param pending pending
	 */
	public void setPending(boolean pending) {
		if (pending) {
			status = null;
		} else {
			status = Boolean.valueOf(false);
		}
	}

	/**
	 * If setPending(false) has not been called before using this method, this method will have no effect.
	 * 
	 * @param accepted accepted
	 */
	public void setAccepted(boolean accepted) {
		if (status != null) {
			status = Boolean.valueOf(accepted);
		}
	}
}
