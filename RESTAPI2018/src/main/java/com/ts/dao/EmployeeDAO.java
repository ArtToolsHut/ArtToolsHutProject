package com.ts.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import com.rest.dto.Employee;
import com.ts.db.HibernateTemplate;

public class EmployeeDAO {

	private SessionFactory factory = null;

	public Employee getEmpByUserPass(String loginId,String password) {

		return (Employee)HibernateTemplate.getObjectByUserPass(loginId,password);
	}

	public int register(Employee employee) {
		java.util.Date utilDate = new java.sql.Date(employee.getJoinDate().getTime()); 
		return HibernateTemplate.addObject(employee);
	}

	public List<Employee> getAllEmployees() {
		List<Employee> employees=(List)HibernateTemplate.getObjectListByQuery("From Employee");
		System.out.println("Inside All Employees ..."+employees);
		return employees;	
	}

	public Employee getEmployee(int id) {
		return (Employee)HibernateTemplate.getObject(Employee.class,id);
	}


	public void getEmployees(int deptId) {
		String query= "from Employee where deptId = 1";
		System.out.println("get employees is called...");
		List<Object> obj = (List<Object>) HibernateTemplate.getObjectListByQuery(query);
		System.out.println("Testing get employees :" + obj); 
		for(Object emp: obj){
			Employee employee = (Employee)emp;
			System.out.println(employee.getEmpName());
		}
	}

	public Employee getEmpByEmail(String email) {
		return (Employee)HibernateTemplate.getObjectByEmail(email);
	}
}