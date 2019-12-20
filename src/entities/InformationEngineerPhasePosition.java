package entities;

public class InformationEngineerPhasePosition {

	private InformationEngineer informationEngineer;
	private int phaseId;
	private PhasePosition phasePosition;

	public InformationEngineer getInformationEngineer() {
		return this.informationEngineer;
	}

	/**
	 * 
	 * @param informationEngineer
	 */
	public void setInformationEngineer(InformationEngineer informationEngineer) {
		this.informationEngineer = informationEngineer;
	}

	public int getPhaseId() {
		return this.phaseId;
	}

	/**
	 * 
	 * @param phaseId
	 */
	public void setPhaseId(int phaseId) {
		this.phaseId = phaseId;
	}

	public PhasePosition getPhasePosition() {
		return this.phasePosition;
	}

	/**
	 * 
	 * @param phasePosition
	 */
	public void setPhasePosition(PhasePosition phasePosition) {
		this.phasePosition = phasePosition;
	}


	public enum PhasePosition {
		EVALUATOR,
		EXECUTIVE_LEADER,
		TESTER,
		PHASE_LEADER
	}

}