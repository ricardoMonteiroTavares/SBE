package DAOs.Implements;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;

import DAOs.Interfaces.BoardingDAO;
import Entities.Boarding;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;
import Factories.EMFactory;

public class BoardingDAOImpl implements BoardingDAO{
	
	private EntityManager em;
	private EntityTransaction tx;
	private final String errorMsg = "Embarque não encotrado";

	@Override
	public Long insert(Boarding boarding) {
		try {
			em = EMFactory.newSession();
			tx = em.getTransaction();
			tx.begin();
			
			em.persist(boarding);
			tx.commit();
			return boarding.getId();
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
	public void update(Boarding boarding) throws ObjectNotFoundException, ObjectVersionException {
		Boarding b = null;
		
		try {
			em = EMFactory.newSession();
			tx = em.getTransaction();
			tx.begin();
			
			b = em.find(Boarding.class, boarding.getId(), LockModeType.PESSIMISTIC_WRITE);
			
			if(b == null)
			{
				tx.rollback();
				throw new ObjectNotFoundException(errorMsg);
			}
			
			em.merge(boarding);
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
			
			Boarding boarding = em.find(Boarding.class, new Long(id), LockModeType.PESSIMISTIC_WRITE);
			
			if(boarding == null)
			{
				tx.rollback();
				throw new ObjectNotFoundException(errorMsg);
			}
			
			em.remove(boarding);
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
	public List<Boarding> getAllBoardingsByDate(Long cardCode, Date date) throws ObjectNotFoundException {
		try
		{	
			em = EMFactory.newSession();
			String cmd = "select b from boarding b where b.id_card=" + cardCode.toString() + " AND b.date = " + date.toString() + " order by b.id";
			@SuppressWarnings("unchecked")
			List<Boarding> boardings = em.createQuery(cmd).getResultList(); // JPQL

			return boardings;
		} 
		finally
		{   
			this.closeEM();
		}
	}

	@Override
	public List<Boarding> getAllBoardingsByPeriod(Long cardCode, Date start, Date finish)
			throws ObjectNotFoundException {
		try
		{	
			em = EMFactory.newSession();
			String cmd = "select b from boarding b where b.id_card=" + cardCode.toString() + " AND b.date = " + start.toString() + " AND b.date <= " + finish.toString() + " order by b.id";
			@SuppressWarnings("unchecked")
			List<Boarding> boardings = em.createQuery(cmd).getResultList(); // JPQL

			return boardings;
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
