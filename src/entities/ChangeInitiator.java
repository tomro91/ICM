package entities;

import java.io.Serializable;

public class ChangeInitiator implements Serializable {

	private Integer id;
	private String firstName;
	private String lastName;
	private Title title;
	private String email;
	private String phoneNumber;
	private CiDepartment department;
	private String password;
	private Position position;

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public Title getTitle() {
		return this.title;
	}

	public String getEmail() {
		return this.email;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public CiDepartment getDepartment() {
		return this.department;
	}

	public String getPassword() {
		return this.password;
	}

	public Integer getId() {
		return this.id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setDepartment(CiDepartment department) {
		this.department = department;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public enum Title {
		STUDENT,
		LECTURER,
		ADMINISTRATION,
		INFOENGINEER
	}

}