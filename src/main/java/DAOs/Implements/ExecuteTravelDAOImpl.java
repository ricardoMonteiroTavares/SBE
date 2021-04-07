package DAOs.Implements;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import DAOs.Interfaces.ExecuteTravelDAO;
import Entities.ExecuteTravel;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;

@Repository
public class ExecuteTravelDAOImpl implements ExecuteTravelDAO {
	
	@PersistenceContext
	private EntityManager em;	
	private final String errorMsg = "Intinerário nÃ£o encotrado";


	@Override
	public Long insert(ExecuteTravel exTravel) {
		try {					
			em.persist(exTravel);
			
			return exTravel.getId();
		}
		catch(RuntimeException e) {
			throw e;
		}
		
	}

	@Override
	public void update(ExecuteTravel exTravel) throws ObjectNotFoundException, ObjectVersionException {
		ExecuteTravel ext = null;
		
		try {
			
			ext = em.find(ExecuteTravel.class, exTravel.getId(), LockModeType.PESSIMISTIC_WRITE);
			
			if(ext == null)
			{
				throw new ObjectNotFoundException(errorMsg);
			}
			
			em.merge(exTravel);
		}
		catch(OptimisticLockException e) {			
			throw new ObjectVersionException("A operação não foi efetuada: Os dados que você tentou salvar foram modificados por outro usuário");
		}
		catch(RuntimeException e) {
			throw e;
		}		
		
	}

	@Override
	public void delete(Long id) throws ObjectNotFoundException {
		try {
			
			ExecuteTravel ext = em.find(ExecuteTravel.class, new Long(id), LockModeType.PESSIMISTIC_WRITE);
			
			if(ext == null)
			{				
				throw new ObjectNotFoundException(errorMsg);
			}
			
			em.remove(ext);
		}
		catch(RuntimeException e) {
			throw e;
		}
		
	}

	@Override
	public ExecuteTravel getExTravel(Long id) throws ObjectNotFoundException {		
			
		ExecuteTravel ext = em.find(ExecuteTravel.class, new Long(id));
		
		if(ext == null)
		{
			throw new ObjectNotFoundException(errorMsg);
		}
		
		return ext;
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ExecuteTravel> getAllExTravelsByDate(Long travelId, Date date) throws ObjectNotFoundException {	

		String cmd = "select e from execute_travel e where e.id_travel=" + travelId.toString() + " AND e.date LIKE " + date.toString() + " order by e.id";
		
		return em.createQuery(cmd).getResultList(); // JPQL
			
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ExecuteTravel> getAllExTravelsByDateAndDirection(Long travelId, String direction, Date date)
			throws ObjectNotFoundException {
		
		String cmd = "select e from execute_travel e where e.id_travel=" + travelId.toString() + " AND e.date = " + date.toString() + " AND e.direction LIKE " + direction + " order by e.id";
		
		return em.createQuery(cmd).getResultList(); // JPQL

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ExecuteTravel> getAllExTravelsByPeriod(Long travelId, Date start, Date finish)
			throws ObjectNotFoundException {
		
		String cmd = "select e from execute_travel e where e.id_travel=" + travelId.toString() + " AND e.date >= " + start.toString() + " AND e.date <= " + finish.toString() + " order by e.id";
		
		return em.createQuery(cmd).getResultList(); // JPQL
	
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ExecuteTravel> getAllExTravelsByPeriodAndDirection(Long travelId, String direction, Date start,
			Date finish) throws ObjectNotFoundException {

		String cmd = "select e from execute_travel e where e.id_travel=" + travelId.toString() + " AND e.date >= " + start.toString() + " AND e.date <= " + finish.toString() + " AND e.direction LIKE " + direction + " order by e.id";
		
		return em.createQuery(cmd).getResultList(); // JPQL

	}

}
