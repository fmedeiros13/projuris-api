package br.com.projuris.factory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAOFactory {
	
	private static EntityManagerFactory emf = null; 

	public EntityManagerFactory getEntityManagerFactory(){

		if (emf == null)
			emf = Persistence.createEntityManagerFactory("system");
		
		return emf;
	}
	
	public DAOFactory() {

	}
}
