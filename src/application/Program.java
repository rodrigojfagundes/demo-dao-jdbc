package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//instanciando um SELLERDAO, chamado de sellerDao... DAI em VEZ de dar um
		//NEW sellerDao... ..... Nos vamos chamar o METODO CREATESELLERDAO
		//q ta dentro da CLASSE DAOFACTORY... E esse metodo vai ser o responsavel
		//por criar um novo SellerDao :)
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		Seller seller = sellerDao.findById(3);
		
		System.out.println(seller);
	}
}
