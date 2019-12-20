package entities;

public class PerformanceReport extends Report {

	private int extensionDuration;
	private int repeatedPhasesDuration;

	public int getExtensionDuration() {
		return this.extensionDuration;
	}

	/**
	 * 
	 * @param extensionDuration
	 */
	public void setExtensionDuration(int extensionDuration) {
		this.extensionDuration = extensionDuration;
	}

	public int getRepeatedPhasesDuration() {
		return this.repeatedPhasesDuration;
	}

	/**
	 * 
	 * @param repeatedPhasesDuration
	 */
	public void setRepeatedPhasesDuration(int repeatedPhasesDuration) {
		this.repeatedPhasesDuration = repeatedPhasesDuration;
	}

}