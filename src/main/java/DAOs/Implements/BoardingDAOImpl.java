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
	private final String cardErrorMsg = "Cart√£o n√£o encotrado";
	private final String boardingErrorMsg = "Embarque n√£o encotrado";

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
				throw new ObjectNotFoundException(boardingErrorMsg);
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
				throw new ObjectNotFoundException(boardingErrorMsg);
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
		// TODO Fazer a funÁ„o getAllBoardingsByDate
		return null;
	}

	@Override
	public List<Boarding> getAllBoardingsByPeriod(Long cardCode, Date start, Date finish)
			throws ObjectNotFoundException {
		// TODO Fazer a funÁ„o getAllBoardingsByPeriod
		return null;
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
