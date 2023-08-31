package com.example.projectservice.model;



public class User {
	
	public Long id;
	
	public String UserName;
	
	public String fullName;
	
	public String password;
	
	public String cnfmPassword;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCnfmPassword() {
		return cnfmPassword;
	}

	public void setCnfmPassword(String cnfmPassword) {
		this.cnfmPassword = cnfmPassword;
	}



	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Long id, String userName, String fullName, String password, String cnfmPassword) {
		super();
		this.id = id;
		UserName = userName;
		this.fullName = fullName;
		this.password = password;
		this.cnfmPassword = cnfmPassword;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", UserName=" + UserName + ", fullName=" + fullName + ", password=" + password
				+ ", cnfmPassword=" + cnfmPassword + "]";
	}

}
