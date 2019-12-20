package entities;

import java.util.Map;

public class ActivityReport extends Report {

	private double median;
	private double standardDeviation;
	private Map distributionFrequency;

	public double getMedian() {
		return this.median;
	}

	/**
	 * 
	 * @param median
	 */
	public void setMedian(double median) {
		this.median = median;
	}

	public double getStandardDeviation() {
		return this.standardDeviation;
	}

	/**
	 * 
	 * @param standardDeviation
	 */
	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	public Map getDistributionFrequency() {
		return this.distributionFrequency;
	}

	/**
	 * 
	 * @param distributionFrequency
	 */
	public void setDistributionFrequency(Map distributionFrequency) {
		this.distributionFrequency = distributionFrequency;
	}

}