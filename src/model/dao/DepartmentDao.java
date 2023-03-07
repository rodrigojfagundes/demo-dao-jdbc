package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {
	
	//declarando as OPERACOES q tem q ser feitas em um DEPARTMENT

	void insert(Department obj);
	
	void update(Department obj);
	
	void deleteById(Integer id);
	
	Department findById(Integer id);
	
	List<Department> findAll();
	
}
