package com.ts.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import com.rest.dto.Department;
import com.ts.db.HibernateTemplate;

public class DeptDAO {
	private SessionFactory factory = null;
	
	public int register(Department dept) {		
		return HibernateTemplate.addObject(dept);
	}

	public Department getDept(int deptId) {
		return (Department)HibernateTemplate.getObject(Department.class,deptId);
	}

	public List<Department> getAllDepts() {
		List<Department> depts=(List)HibernateTemplate.getObjectListByQuery("From Department");
		return depts;	
	}

	public List<Department> getDeptByName(String deptName) {	
		return (List)HibernateTemplate.getObjectListByName(Department.class,"deptName",deptName);
	}
}