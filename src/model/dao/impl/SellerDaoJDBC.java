package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;


public class SellerDaoJDBC implements SellerDao{
	
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	
	//a primeiro metodo é o INSERT para INSERIR no BD um OBJETO do tipo
	//SELLER...
	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			//falando q a var ST vai receber o nosso CONN.prepare
			st = conn.prepareStatement(
//basicamente buscando todos campos de vendedor + o nome do departamento
//restrigimos a BUSCA onde o ID do SELLER seja IGUAL a... o valor passado nas (?)
					"SELECT seller.*,department.Name as DepName " 
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
			
		//definindo qual sera o VALOR do "???" INTERROGACAO de WHERE seller.id=?
			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep);
				return obj;
			}
			return null;
		}
		
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();

		obj.setId(rs.getInt("Id"));
		//passando o NOME q ta em SELLER no BANCO para o nosso OBJETO do
		//tipo SELLER
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		
		return obj;
	}


	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	//operacao para RETORNAR todos os SELLER/VENDEDOR em forma de LISTA :)
	@Override
	public List<Seller> findAll() {		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
		"SELECT seller.*,department.Name as DepName "
		+ "FROM seller INNER JOIN department "
		+ "ON seller.DepartmentId = department.Id "
		+ "ORDER BY Name");

			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap();
			while (rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
					
				}
				Seller obj = instantiateSeller(rs, dep);	
				list.add(obj);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	//metodo para encontrar um VENDEDOR/SELLER a partir de um DEPARTAMENTO informado
	@Override
	public List<Seller> findByDepartment(Department department) {
		
				PreparedStatement st = null;
		
				ResultSet rs = null;
		
				try {
		
					st = conn.prepareStatement(
				"SELECT seller.*,department.Name as DepName "
				+ "FROM seller INNER JOIN department "
				+ "ON seller.DepartmentId = department.Id "
				+ "WHERE DepartmentId = ? "
				+ "ORDER BY Name");
					
					st.setInt(1, department.getId());
					rs = st.executeQuery();
					List<Seller> list = new ArrayList<>();
					Map<Integer, Department> map = new HashMap();
					
					while (rs.next()) {
						
						Department dep = map.get(rs.getInt("DepartmentId"));
						
						if(dep == null) {
							dep = instantiateDepartment(rs);
							map.put(rs.getInt("DepartmentId"), dep);
							
						}
						Seller obj = instantiateSeller(rs, dep);
						list.add(obj);
					}
					return list;
				}
				
				catch(SQLException e) {
					throw new DbException(e.getMessage());
				}
				finally {
					DB.closeStatement(st);
					DB.closeResultSet(rs);
				}	
	}
}
