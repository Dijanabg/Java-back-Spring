package com.iktpreobuka.project.entities.dto;

public class UserDTO {
	private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String repeatedPassword;
    private String email;
	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserDTO(String firstName, String lastName, String username, String password, String repeatedPassword,
			String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.repeatedPassword = repeatedPassword;
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepeatedPassword() {
		return repeatedPassword;
	}
	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "UserDTO [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username + ", password="
				+ password + ", repeatedPassword=" + repeatedPassword + ", email=" + email + "]";
	}
    
    
}
