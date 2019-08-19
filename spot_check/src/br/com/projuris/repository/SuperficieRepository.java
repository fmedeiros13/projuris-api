package br.com.projuris.repository;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.projuris.factory.DAOFactory;
import br.com.projuris.model.Superficie;

@RequestScoped
public class SuperficieRepository {
	
	@Inject
	DAOFactory daoFactory;
	
	public void save(Superficie superficie) {
		
		EntityManager em = daoFactory.getEntityManagerFactory().createEntityManager();
		
		try {
			em.getTransaction().begin();
			em.persist(superficie);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Superficie> findAll() {
		
		EntityManager em = this.daoFactory.getEntityManagerFactory().createEntityManager();
		
		String sql = "SELECT * FROM superficie;";
		
		List<Superficie> superficies = new ArrayList<Superficie>();
		
		try {
			em.getTransaction().begin();
			Query query = em.createNativeQuery(sql);
			superficies = (List<Superficie>) query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		
		return superficies;
	}

	public Object findById(Long id) {

		EntityManager em = this.daoFactory.getEntityManagerFactory().createEntityManager();
		
		String sql = "SELECT * FROM superficie WHERE id=" + id + ";";
		
		Object response = null;
		
		try {
			em.getTransaction().begin();
			Query query = em.createNativeQuery(sql);		
			response = query.getSingleResult();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		
		return response;
	}

}
