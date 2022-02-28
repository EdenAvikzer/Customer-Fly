package entity;

import java.sql.Date;

public class Customer {
	
	private int passportNum;
	private String firstName;
	private String lastName;
	private String email;
	private String primaryCitizenship;
	private Date birthDate;
	private String password;


	
	public Customer(int passportNum, String firstName, String lastName, String email, String primaryCitizenship,
			Date birthDate, String password) {
		super();
		this.passportNum = passportNum;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.primaryCitizenship = primaryCitizenship;
		this.birthDate = birthDate;
		this.password = password;
	}

	//partinal constractor
	public Customer(int passportNum, String password) {
		super();
		this.passportNum = passportNum;
		this.password = password;
	}
	
	public Customer(int passportNum2, String fname, String lname, String mail) {
		super();
		this.passportNum = passportNum2;
		this.firstName = fname;
		this.lastName = lname;
		this.email = mail;
	}
	
	public Customer(int passportNum2, String firstName2, String lastName2, String email2, String primaryCitizenship2,
			Date birthDate2) {
		super();
		this.passportNum = passportNum2;
		this.firstName = firstName2;
		this.lastName = lastName2;
		this.email = email2;
		this.primaryCitizenship = primaryCitizenship2;
		this.birthDate = birthDate2;
		
		
	}

	public Customer(int passportNum) {
		super();
		this.passportNum = passportNum;
		
	}

	public String getPrimaryCitizenship() {
		return primaryCitizenship;
	}

	public void setPrimaryCitizenship(String primaryCitizenship) {
		this.primaryCitizenship = primaryCitizenship;
	}

	public int getPassportNum() {
		return passportNum;
	}
	public void setPassportNum(int passportNum) {
		this.passportNum = passportNum;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return firstName + " " +  lastName + " email: " + email;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + passportNum;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (passportNum != other.passportNum)
			return false;
		return true;
	}
}
