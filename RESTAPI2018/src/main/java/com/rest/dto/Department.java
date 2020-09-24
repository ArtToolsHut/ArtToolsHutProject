package com.rest.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@XmlRootElement
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
property = "deptId")
@Entity
public class Department {
	@Id@GeneratedValue
	private int deptId;
	private String deptName;
	private String location;
	
	@OneToMany(mappedBy="department",fetch = FetchType.LAZY)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Employee> empList =new ArrayList<Employee>();
	
	public Department() {
		super();
	}

	public Department(int deptId, String deptName, String location, List<Employee> empList) {
		super();
		this.deptId = deptId;
		this.deptName = deptName;
		this.location = location;
		this.empList = empList;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Employee> getEmpList() {
		return empList;
	}

	public void setEmpList(List<Employee> empList) {
		this.empList = empList;
	}
}