package DAOs.Implements;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;

import DAOs.Interfaces.ExecuteTravelDAO;
import Entities.ExecuteTravel;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;
import Factories.EMFactory;

public class ExecuteTravelDAOImpl implements ExecuteTravelDAO{
	
	private EntityManager em;
	private EntityTransaction tx;
	private final String errorMsg = "Intiner·rio n√£o encotrado";


	@Override
	public Long insert(ExecuteTravel exTravel) {
		try {
			em = EMFactory.newSession();
			tx = em.getTransaction();
			tx.begin();
			
			em.persist(exTravel);
			tx.commit();
			return exTravel.getId();
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
	public void update(ExecuteTravel exTravel) throws ObjectNotFoundException, ObjectVersionException {
		ExecuteTravel ext = null;
		
		try {
			em = EMFactory.newSession();
			tx = em.getTransaction();
			tx.begin();
			
			ext = em.find(ExecuteTravel.class, exTravel.getId(), LockModeType.PESSIMISTIC_WRITE);
			
			if(ext == null)
			{
				tx.rollback();
				throw new ObjectNotFoundException(errorMsg);
			}
			
			em.merge(exTravel);
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
			
			ExecuteTravel ext = em.find(ExecuteTravel.class, new Long(id), LockModeType.PESSIMISTIC_WRITE);
			
			if(ext == null)
			{
				tx.rollback();
				throw new ObjectNotFoundException(errorMsg);
			}
			
			em.remove(ext);
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
	public ExecuteTravel getExTravel(Long id) throws ObjectNotFoundException {
		try {
			em = EMFactory.newSession();
			
			ExecuteTravel ext = em.find(ExecuteTravel.class, new Long(id));
			
			if(ext == null)
			{
				throw new ObjectNotFoundException(errorMsg);
			}
			
			return ext;
		}
		finally
		{
			this.closeEM();
		}
	}

	@Override
	public List<ExecuteTravel> getAllExTravelsByDate(Long travelId, Date date) throws ObjectNotFoundException {
		// TODO Implementar getAllExTravelsByDate
		try
		{	
			em = EMFactory.newSession();
			String cmd = "select e from execute_travel e where e.id_travel=" + travelId.toString() + " AND e.date LIKE " + date.toString() + " order by e.id";
			@SuppressWarnings("unchecked")
			List<ExecuteTravel> exTravels = em.createQuery(cmd).getResultList(); // JPQL

			return exTravels;
		} 
		finally
		{   
			this.closeEM();
		}
	}

	@Override
	public List<ExecuteTravel> getAllExTravelsByDateAndDirection(Long travelId, String direction, Date date)
			throws ObjectNotFoundException {
		try
		{	
			em = EMFactory.newSession();
			String cmd = "select e from execute_travel e where e.id_travel=" + travelId.toString() + " AND e.date = " + date.toString() + " AND e.direction LIKE " + direction + " order by e.id";
			@SuppressWarnings("unchecked")
			List<ExecuteTravel> exTravels = em.createQuery(cmd).getResultList(); // JPQL

			return exTravels;
		} 
		finally
		{   
			this.closeEM();
		}
	}

	@Override
	public List<ExecuteTravel> getAllExTravelsByPeriod(Long travelId, Date start, Date finish)
			throws ObjectNotFoundException {
		try
		{	
			em = EMFactory.newSession();
			String cmd = "select e from execute_travel e where e.id_travel=" + travelId.toString() + " AND e.date >= " + start.toString() + " AND e.date <= " + finish.toString() + " order by e.id";
			@SuppressWarnings("unchecked")
			List<ExecuteTravel> exTravels = em.createQuery(cmd).getResultList(); // JPQL

			return exTravels;
		} 
		finally
		{   
			this.closeEM();
		}
	}

	@Override
	public List<ExecuteTravel> getAllExTravelsByPeriodAndDirection(Long travelId, String direction, Date start,
			Date finish) throws ObjectNotFoundException {
		try
		{	
			em = EMFactory.newSession();
			String cmd = "select e from execute_travel e where e.id_travel=" + travelId.toString() + " AND e.date >= " + start.toString() + " AND e.date <= " + finish.toString() + " AND e.direction LIKE " + direction + " order by e.id";
			@SuppressWarnings("unchecked")
			List<ExecuteTravel> exTravels = em.createQuery(cmd).getResultList(); // JPQL

			return exTravels;
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
