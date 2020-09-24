package com.rest.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@XmlRootElement
@Entity
public class Employee {
	@Id@GeneratedValue
	private int empId;
	private String empName;
	private String gender;
	private Date joinDate;
	private double salary;
	private String email;
	private String password;
	
	@ManyToOne
	@JoinColumn(name="deptId")
	private Department department;

	public Employee() {
		super();
	}

	public Employee(int empId, String empName, String gender, Date joinDate, double salary, String email,
			String password, Department department) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.gender = gender;
		this.joinDate = joinDate;
		this.salary = salary;
		this.email = email;
		this.password = password;
		this.department = department;
	}
	
	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
}