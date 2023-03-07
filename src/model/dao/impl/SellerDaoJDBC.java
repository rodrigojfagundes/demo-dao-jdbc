package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
	
	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	//criano a operação UPDATE para atualizr os valores do BD, q recebe um
	//OBJETO do tipo SELLER como argumento
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
//basicamente buscando todos campos de vendedor + o nome do departamento, q ganha um
//restrigimos a BUSCA onde o ID do SELLER seja IGUAL ao valor passado
					"SELECT seller.*,department.Name as DepName " 
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
			
		//definindo qual sera o VALOR do "???" INTERROGACAO de WHERE seller.id=?
		//no caso, sera o VALOR do ID q foi passado la em cima como parametro
		//no metodo FINDBYID
			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {

				Department dep = new Department();

				dep.setId(rs.getInt("DepartmentId"));

				dep.setName(rs.getString("DepName"));

				Seller obj = new Seller();

				obj.setId(rs.getInt("Id"));

				obj.setName(rs.getString("Name"));

				obj.setEmail(rs.getString("Email"));

				obj.setBaseSalary(rs.getDouble("BaseSalary"));

				obj.setBirthDate(rs.getDate("BirthDate"));

				obj.setDepartment(dep);
				
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

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}	
}
