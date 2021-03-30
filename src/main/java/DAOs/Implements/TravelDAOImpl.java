package DAOs.Implements;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;

import DAOs.Interfaces.TravelDAO;
import Entities.Travel;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;
import Factories.EMFactory;

public class TravelDAOImpl implements TravelDAO{

	private EntityManager em;
	private EntityTransaction tx;
	private final String errorMsg = "Viagem não encotrada";
	
	@Override
	public Long insert(Travel travel) {
		try {
			em = EMFactory.newSession();
			tx = em.getTransaction();
			tx.begin();
			
			em.persist(travel);
			tx.commit();
			return travel.getId();
		}
		catch(RuntimeException e) {
			this.runtimeException(e);
			return null;
		}
		finally {
			this.closeEM();
		}
	}

	@Override
	public void update(Travel travel) throws ObjectNotFoundException, ObjectVersionException {
		Travel t = null;
		try {
			em = EMFactory.newSession();
			tx = em.getTransaction();
			tx.begin();
			
			t = em.find(Travel.class, travel.getId(), LockModeType.PESSIMISTIC_WRITE);
			
			if(t == null)
			{
				tx.rollback();
				throw new ObjectNotFoundException(errorMsg);
			}
			
			em.merge(travel);
			tx.commit();
		}
		catch(OptimisticLockException e) {
			if(tx != null)
			{
				tx.rollback();
			}
			throw new ObjectVersionException();
		}
		catch(RuntimeException e) {
			this.runtimeException(e);
		}
		finally {
			this.closeEM();
		}
		
		
	}

	@Override
	public void delete(Long id) throws ObjectNotFoundException {
		try {
			em = EMFactory.newSession();
			tx = em.getTransaction();
			tx.begin();
			
			Travel travel = em.find(Travel.class, new Long(id), LockModeType.PESSIMISTIC_WRITE);
			
			if(travel == null)
			{
				tx.rollback();
				throw new ObjectNotFoundException(errorMsg);
			}
			
			em.remove(travel);
			tx.commit();
		}
		catch(RuntimeException e) {
			this.runtimeException(e);
		}
		finally {
			this.closeEM();
		}
		
	}

	@Override
	public Travel getTravel(Long id) throws ObjectNotFoundException {
		try {
			em = EMFactory.newSession();
			
			Travel travel = em.find(Travel.class, new Long(id));
			
			if(travel == null)
			{
				throw new ObjectNotFoundException(errorMsg);
			}
			
			return travel;
		}
		finally
		{
			this.closeEM();
		}
	}

	@Override
	public List<Travel> getAllTravels() {
		try
		{	
			em = EMFactory.newSession();
			
			@SuppressWarnings("unchecked")
			List<Travel> cards = em.createQuery("select t from Travel t order by t.id").getResultList(); // JPQL

			return cards;
		} 
		finally
		{   
			this.closeEM();
		}
	}
	
	private void closeEM() 
	{
		em.close();
	}
	
	private void runtimeException(RuntimeException e) throws RuntimeException
	{
		if (tx != null)
		{	
			try
			{	
				tx.rollback();
			}
			catch(RuntimeException he)
			{ }
		}
		throw e;
	}

}
