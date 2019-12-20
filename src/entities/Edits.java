package entities;

import java.time.LocalDate;

public class Edits {

	private String editorName;
	private LocalDate editDate;
	private String description;

	public String getEditorName() {
		return this.editorName;
	}

	/**
	 * 
	 * @param editorName
	 */
	public void setEditorName(String editorName) {
		this.editorName = editorName;
	}

	public LocalDate getEditDate() {
		return this.editDate;
	}

	/**
	 * 
	 * @param editDate
	 */
	public void setEditDate(LocalDate editDate) {
		this.editDate = editDate;
	}

	public String getDescription() {
		return this.description;
	}

	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}