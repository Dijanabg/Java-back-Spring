package com.iktpreobuka.project.entities;

public class UserEntity {
	
	    private Integer id;
	    private String firstName;
	    private String lastName;
	    private String username;
	    private String password;
	    private String email;
	    private EUserRole userRole;
	    
	
		public UserEntity() {
			super();
		}
		
		
		public UserEntity(Integer id, String firstName, String lastName, String username, String password, String email,
				EUserRole userRole) {
			super();
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.username = username;
			this.password = password;
			this.email = email;
			this.userRole = userRole;
		}


		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
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
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		
		public EUserRole getUserRole() {
			return userRole;
		}


		public void setUserRole(EUserRole userRole) {
			this.userRole = userRole;
		}


		@Override
		public String toString() {
			return "UserEntity [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username="
					+ username + ", password=" + password + ", email=" + email + "]";
		}
	    
}
