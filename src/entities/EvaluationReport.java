package entities;

import java.time.LocalDate;

public class EvaluationReport {

	private int reportId;
	private InfoSystem infoSystem;
	private String requiredChange;
	private String expectedResult;
	private String risksAndConstraints;
	private LocalDate EvaluatedTime;

	public int getReportId() {
		return this.reportId;
	}

	/**
	 * 
	 * @param reportId
	 */
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public InfoSystem getInfoSystem() {
		return this.infoSystem;
	}

	/**
	 * 
	 * @param infoSystem
	 */
	public void setInfoSystem(InfoSystem infoSystem) {
		this.infoSystem = infoSystem;
	}

	public String getRequiredChange() {
		return this.requiredChange;
	}

	/**
	 * 
	 * @param requiredChange
	 */
	public void setRequiredChange(String requiredChange) {
		this.requiredChange = requiredChange;
	}

	public String getExpectedResult() {
		return this.expectedResult;
	}

	/**
	 * 
	 * @param expectedResult
	 */
	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}

	public String getRisksAndConstraints() {
		return this.risksAndConstraints;
	}

	/**
	 * 
	 * @param risksAndConstraints
	 */
	public void setRisksAndConstraints(String risksAndConstraints) {
		this.risksAndConstraints = risksAndConstraints;
	}

	public LocalDate getEvaluatedTime() {
		// TODO - implement EvaluationReport.getEvaluatedTime
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param EvaluatedTime
	 */
	public void setEvaluatedTime(LocalDate EvaluatedTime) {
		// TODO - implement EvaluationReport.setEvaluatedTime
		throw new UnsupportedOperationException();
	}

}