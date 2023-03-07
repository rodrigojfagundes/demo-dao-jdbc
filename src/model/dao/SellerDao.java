package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDao {
	
		//declarando as OPERACOES q tem q ser feitas em um Seller/vendedor
	
		void insert(Seller obj);
		
		void update(Seller obj);
		
		void deleteById(Integer id);
		
		Seller findById(Integer id);
		
		List<Seller> findAll();
		
	
	
}
