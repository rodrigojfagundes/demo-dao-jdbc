package model.dao;

import java.util.List;

import model.entities.Department;

		//declarando as OPERACOES q tem q ser feitas em uma CLASSE q IMPLEMENTA
		//essa INTERFACE aqui... 
public interface DepartmentDao {
	

	void insert(Department obj);
	

	void update(Department obj);
	

	void deleteById(Integer id);
	

	Department findById(Integer id);
	
	List<Department> findAll();
	
}
