package fr.killax.app.data;

public class User {

	private String gender;
	private String lastname;
	private String firstname;
	private String email;
	
	public User(String gender, String lastname, String firstname, String email) {
		this.gender = gender;
		this.lastname = lastname.toUpperCase();
		this.firstname = firstname.substring(0, 1).toUpperCase() + firstname.substring(1, firstname.length());
		this.email = email;
	}
	
	public String getGender() {
		return gender;
	}

	public String getLastName() {
		return lastname;
	}

	public String getFirstName() {
		return firstname;
	}

	public String getEmail() {
		return email;
	}
	
	public String toString() {
		return String.format("%s. %s %s", gender, lastname, firstname);
	}
}
