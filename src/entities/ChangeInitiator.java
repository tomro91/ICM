package entities;

public class ChangeInitiator {

	private String firstName;
	private String lastName;
	private Title title;
	private String email;
	private String phoneNumber;
	private CiDepartment department;
	private String password;
	private String id;

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

	public String getId() {
		return this.id;
	}


	public enum Title {
		STUDENT,
		LECTURER,
		ADMINISTRATION
	}

}