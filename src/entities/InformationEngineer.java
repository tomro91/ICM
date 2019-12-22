package entities;

import java.io.Serializable;

public class InformationEngineer extends ChangeInitiator implements Serializable {

	private InfoSystem managedSystem;
	private Position position;

	public InfoSystem getManagedSystem() {
		return this.managedSystem;
	}

	/**
	 * 
	 * @param managedSystem
	 */
	public void setManagedSystem(InfoSystem managedSystem) {
		this.managedSystem = managedSystem;
	}

	public Position getPosition() {
		// TODO - implement InformationEngineer.getPosition
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param position
	 */
	public void setPosition(InformationEngineerPhasePosition.PhasePosition position) {
		// TODO - implement InformationEngineer.setPosition
		throw new UnsupportedOperationException();
	}

}