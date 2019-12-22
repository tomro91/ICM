package entities;

import java.time.LocalDate;

public class Phase {

	private PhaseName name;
	private InformationEngineer leader;
	private LocalDate deadLine;
	private PhaseStatus phaseStatus;
	private boolean extensionRequest;
	private int phaseExceptionDuration;
	private String changeRequestId;
	private LocalDate exceptionTime;

	public enum PhaseName {
		EVALUATION,
		EXAMINATION,
		EXECUTION,
		VALIDATION,
		CLOSING
	}

	public PhaseName getName() {
		return this.name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(PhaseName name) {
		this.name = name;
	}

	public InformationEngineer getLeader() {
		return this.leader;
	}

	/**
	 * 
	 * @param leader
	 */
	public void setLeader(InformationEngineer leader) {
		this.leader = leader;
	}

	public LocalDate getDeadLine() {
		return this.deadLine;
	}

	/**
	 * 
	 * @param deadLine
	 */
	public void setDeadLine(LocalDate deadLine) {
		this.deadLine = deadLine;
	}

	public PhaseStatus getPhaseStatus() {
		return this.phaseStatus;
	}

	/**
	 * 
	 * @param phaseStatus
	 */
	public void setPhaseStatus(PhaseStatus phaseStatus) {
		this.phaseStatus = phaseStatus;
	}

	public boolean isExtensionRequest() {
		return this.extensionRequest;
	}

	/**
	 * 
	 * @param extensionRequest
	 */
	public void setExtensionRequest(boolean extensionRequest) {
		this.extensionRequest = extensionRequest;
	}

	public int getPhaseExceptionDuration() {
		return this.phaseExceptionDuration;
	}

	/**
	 * 
	 * @param phaseExceptionDuration
	 */
	public void setPhaseExceptionDuration(int phaseExceptionDuration) {
		this.phaseExceptionDuration = phaseExceptionDuration;
	}

	public String getChangeRequestId() {
		return this.changeRequestId;
	}

	/**
	 * 
	 * @param changeRequestId
	 */
	public void setChangeRequestId(String changeRequestId) {
		this.changeRequestId = changeRequestId;
	}

	public LocalDate getExceptionTime() {
		return exceptionTime;
	}

	public void setExceptionTime(LocalDate exceptionTime) {
		this.exceptionTime = exceptionTime;
	}

	public enum PhaseStatus {
		PHASE_LEADER_ASSIGNED,
		PHASE_EXEC_LEADER_ASSIGNED,
		TIME_REQUESTED,
		TIME_APPROVED,
		IN_PROCESS,
		DONE
	}

}