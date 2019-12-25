package entities;

import java.io.Serializable;

public class InformationEngineer extends ChangeInitiator implements Serializable {

	private InfoSystem managedSystem;

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


}