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
	
	
	
	
	//a primeiro metodo � o INSERT para INSERIR no BD um OBJETO do tipo
	//SELLER...
	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		try {
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

	//criano a opera��o UPDATE para atualizr os valores do BD, q recebe um
	//OBJETO do tipo SELLER como argumento
	@Override
	public void update(Seller obj) {
				PreparedStatement st = null;
				try {
					st = conn.prepareStatement(
							"UPDATE seller "
							+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
							+ "WHERE Id = ?");
					
					st.setString(1, obj.getName());
					
					st.setString(2, obj.getEmail());
					
					st.setDate(3, new java.sql.Date(obj.getBirthDate().getDate()));
					
					
					st.setDouble(4, obj.getBaseSalary());
					
					st.setInt(5, obj.getDepartment().getId());
					
					st.setInt(6, obj.getId());
					
					st.executeUpdate();
					
			}

			catch(SQLException e) {
				throw new DbException(e.getMessage());
				}
				finally {
					DB.closeStatement(st);
				}
		
	}
	
	//metodo q recebe uma ID para deletar um Seller do BD
	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			//passando o comando para DELETAR a TABELA SELLER onde o ID for igual o ID
			//recebido no ID do metodo
			st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
			
			//informando Q a PRIMEIRA INTERROGA��O do comando ali de cima, vai receber
			//o VALOR q ta no ID do metodo DELETEBYID
			st.setInt(1, id);
			 st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		//fechando as conexoes
		finally {
			DB.closeStatement(st);
		}
	}

	//operacao q recebe um ID e retorna um SELLER associado a esse ID...
	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
//basicamente buscando todos campos de vendedor + o nome do departamento
//restrigimos a BUSCA onde o ID do SELLER seja IGUAL ao valor passado
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
	
	
	
	//criando o metodo q vai PEGAR os DADOS do BANCO q sao da tabela SELLER
	//e ADD esses dados ao OBJETO do tipo SELER
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		//passando o SALARIO q ta em SELLER no BANCO para o nosso OBJETO
		//do tipo SELLER
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