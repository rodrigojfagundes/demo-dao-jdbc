package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		
		PreparedStatement st = null;
		try {
			//passando para var ST o valor do nosso CONN (conexao com o DB) e chamando o
			//metodo prepareStatement para dizer quais vao ser os comandos SQL a serem
			//rodados
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)", 

					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());			
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getDate()));
			st.setDouble(4, obj.getBaseSalary());
			
			
			st.setInt(5, obj.getDepartment().getId());
			

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			
			else {
			throw new DbException("unexpected error! no rows affected!");
		}
	}
	catch(SQLException e) {
		throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
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
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " 
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
			

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
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	//criando o metodo q vai PEGAR os DADOS do BANCO q sao da tabela
	//DEPARTMENT e ADD esses dados ao OBJETO do tipo DEPARTMENT no caso DEP
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));		
		return dep;
	}


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

			//Executando o comando de busca acima, e passando o valor de resultado
			//para a variavel RS
			rs = st.executeQuery();
			
			//criando uma LISTA/LIST para armazenar os VENDEDORES do DEPARTMENT
			List<Seller> list = new ArrayList<>();

			Map<Integer, Department> map = new HashMap();
			
			
			//usando o WHILE para PERCORRER linha por linha com da tabela 
			//DEPARTMENT e PEGANDO todos os SELLERS/vendedor desse
			//DEPARTMENT
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
