package edu.ncsu.csc.itrust.beans;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import edu.ncsu.csc.itrust.enums.DeliveryType;
import edu.ncsu.csc.itrust.enums.PregnancyStatus;

public class ObstetricsRecordBean {
	private long mid;
	private long oid;
	private long pregId;
	private String lmp;
	private String edd;
	private String weeksPregnant;
	private String dateVisit;
	private int yearConception;
	private double hoursInLabor;
	private DeliveryType deliveryType;
	private PregnancyStatus pregnancyStatus;
	private double weight;
	private int bloodPressureS;
	private int bloodPressureD;
	private int fhr;
	private double fhu;
	
	public long getMid() {
		return mid;
	}
	public void setMid(long mid) {
		this.mid = mid;
	}
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	public long getPregId() {
		return pregId;
	}
	public void setPregId(long pid) {
		this.pregId = pid;
	}
	public Date getLmp() {
		if (lmp == null)
			return null;
		Date d = null; 
		try {
			d = new SimpleDateFormat("MM/dd/yyyy").parse(lmp);
		} catch (ParseException e) {
			return null; //if can't parse, it might as well be null
		}
		return d;
	}
	public String getLmpString() {
		return lmp;
	}
	public void setLmp(String lmp) {
		this.lmp = lmp;
	}
	public Date getEdd() {
		if (edd == null)
			return null;
		Date d = null; 
		try {
			d = new SimpleDateFormat("MM/dd/yyyy").parse(edd);
		} catch (ParseException e) {
			return null; //if can't parse, it might as well be null
		}
		return d;
	}
	public String getEddString() {
		return edd;
	}
	public void setEdd(String edd) {
		this.edd = edd;
	}
	public String getWeeksPregnant() {
		return weeksPregnant;
	}
	public void setWeeksPregnant(String weeksPregnant) {
		this.weeksPregnant = weeksPregnant;
	}
	public Date getDateVisit() {
		if (dateVisit == null)
			return null;
		Date d = null; 
		try {
			d = new SimpleDateFormat("MM/dd/yyyy").parse(dateVisit);
		} catch (ParseException e) {
			return null; //if can't parse, it might as well be null
		}
		return d;
	}
	public String getDateVisitString() {
		return dateVisit;
	}
	public void setDateVisit(String dateVisit) {
		this.dateVisit = dateVisit;
	}
	public int getYearConception() {
		return yearConception;
	}
	public void setYearConception(int yearConception) {
		this.yearConception = yearConception;
	}
	public double getHoursInLabor() {
		return hoursInLabor;
	}
	public void setHoursInLabor(double hoursInLabor) {
		this.hoursInLabor = hoursInLabor;
	}
	public DeliveryType getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}
	public PregnancyStatus getPregnancyStatus() {
		return pregnancyStatus;
	}
	public void setPregnancyStatus(PregnancyStatus pregnancyStatus) {
		this.pregnancyStatus = pregnancyStatus;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public int getBloodPressureS() {
		return bloodPressureS;
	}
	public void setBloodPressureS(int bloodPressureS) {
		this.bloodPressureS = bloodPressureS;
	}
	public int getBloodPressureD() {
		return bloodPressureD;
	}
	public void setBloodPressureD(int bloodPressureD) {
		this.bloodPressureD = bloodPressureD;
	}
	public int getFhr() {
		return fhr;
	}
	public void setFhr(int fhr) {
		this.fhr = fhr;
	}
	public double getFhu() {
		return fhu;
	}
	public void setFhu(double fhu) {
		this.fhu = fhu;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bloodPressureD;
		result = prime * result + bloodPressureS;
		result = prime * result
				+ ((dateVisit == null) ? 0 : dateVisit.hashCode());
		result = prime * result
				+ ((deliveryType == null) ? 0 : deliveryType.hashCode());
		result = prime * result + ((edd == null) ? 0 : edd.hashCode());
		result = prime * result + fhr;
		long temp;
		temp = Double.doubleToLongBits(fhu);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(hoursInLabor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((lmp == null) ? 0 : lmp.hashCode());
		result = prime * result + (int) (mid ^ (mid >>> 32));
		result = prime * result + (int) (oid ^ (oid >>> 32));
		result = prime * result + (int) (pregId ^ (pregId >>> 32));
		result = prime * result
				+ ((pregnancyStatus == null) ? 0 : pregnancyStatus.hashCode());
		result = prime * result
				+ ((weeksPregnant == null) ? 0 : weeksPregnant.hashCode());
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + yearConception;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObstetricsRecordBean other = (ObstetricsRecordBean) obj;
		if (bloodPressureD != other.bloodPressureD)
			return false;
		if (bloodPressureS != other.bloodPressureS)
			return false;
		if (dateVisit == null) {
			if (other.dateVisit != null)
				return false;
		} else if (!dateVisit.equals(other.dateVisit))
			return false;
		if (deliveryType != other.deliveryType)
			return false;
		if (edd == null) {
			if (other.edd != null)
				return false;
		} else if (!edd.equals(other.edd))
			return false;
		if (fhr != other.fhr)
			return false;
		if (Double.doubleToLongBits(fhu) != Double.doubleToLongBits(other.fhu))
			return false;
		if (Double.doubleToLongBits(hoursInLabor) != Double
				.doubleToLongBits(other.hoursInLabor))
			return false;
		if (lmp == null) {
			if (other.lmp != null)
				return false;
		} else if (!lmp.equals(other.lmp))
			return false;
		if (mid != other.mid)
			return false;
		if (oid != other.oid)
			return false;
		if (pregId != other.pregId)
			return false;
		if (pregnancyStatus != other.pregnancyStatus)
			return false;
		if (weeksPregnant == null) {
			if (other.weeksPregnant != null)
				return false;
		} else if (!weeksPregnant.equals(other.weeksPregnant))
			return false;
		if (Double.doubleToLongBits(weight) != Double
				.doubleToLongBits(other.weight))
			return false;
		if (yearConception != other.yearConception)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ObstetricsRecordBean [mid=" + mid + ", oid=" + oid + ", pid="
				+ pregId + ", lmp=" + lmp + ", edd=" + edd + ", weeksPregnant="
				+ weeksPregnant + ", dateVisit=" + dateVisit
				+ ", yearConception=" + yearConception + ", hoursInLabor="
				+ hoursInLabor + ", deliveryType=" + deliveryType
				+ ", pregnancyStatus=" + pregnancyStatus + ", weight=" + weight
				+ ", bloodPressureS=" + bloodPressureS + ", bloodPressureD="
				+ bloodPressureD + ", fhr=" + fhr + ", fhu=" + fhu + "]";
	}
	
}
