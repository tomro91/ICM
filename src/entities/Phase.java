package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

public class Phase implements Serializable {

	private PhaseName name;
	private LocalDate deadLine;
	private PhaseStatus phaseStatus;
	private boolean extensionRequest;
	private Integer changeRequestId;
	private LocalDate exceptionTime;
	private Map<IEPhasePosition.PhasePosition, IEPhasePosition> iePhasePosition;

	public enum PhaseName {
		EVALUATION,
		EXAMINATION,
		EXECUTION,
		VALIDATION,
		CLOSING
	}

	public enum PhaseStatus {
		SUBMITTED,
		PHASE_LEADER_ASSIGNED,
		PHASE_EXEC_LEADER_ASSIGNED,
		TIME_REQUESTED,
		TIME_APPROVED,
		IN_PROCESS,
		DONE
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


	public Integer getChangeRequestId() {
		return this.changeRequestId;
	}

	/**
	 * 
	 * @param changeRequestId
	 */
	public void setChangeRequestId(Integer changeRequestId) {
		this.changeRequestId = changeRequestId;
	}

	public LocalDate getExceptionTime() {
		return exceptionTime;
	}

	public void setExceptionTime(LocalDate exceptionTime) {
		this.exceptionTime = exceptionTime;
	}

	public Map<IEPhasePosition.PhasePosition, IEPhasePosition> getIePhasePosition() {
		return iePhasePosition;
	}

	public void setIePhasePosition(Map<IEPhasePosition.PhasePosition, IEPhasePosition> iePhasePosition) {
		this.iePhasePosition = iePhasePosition;
	}



}