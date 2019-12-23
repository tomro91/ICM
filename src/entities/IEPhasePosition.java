package entities;

import java.io.Serializable;

public class IEPhasePosition implements Serializable {

	private InformationEngineer informationEngineer;
	private Phase.PhaseName phaseName;
	private PhasePosition phasePosition;

	public enum PhasePosition {
		EVALUATOR,
		EXECUTIVE_LEADER,
		TESTER,
		PHASE_LEADER
	}

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

	public Phase.PhaseName getPhaseName() {
		return this.phaseName;
	}

	/**
	 * 
	 * @param phaseName
	 */
	public void setPhaseName(Phase.PhaseName phaseName) {
		this.phaseName = phaseName;
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




}