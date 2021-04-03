package DAOs.Implements;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import DAOs.Interfaces.TravelDAO;
import Entities.Travel;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;

@Repository
public class TravelDAOImpl implements TravelDAO {

	@PersistenceContext
	private EntityManager em;
	private final String errorMsg = "Viagem não encotrada";
	
	@Override
	public Long insert(Travel travel) {
		try {
			em.persist(travel);
			
			return travel.getId();
		}
		catch(RuntimeException e) {
			throw e;
		}		
	}

	@Override
	public void update(Travel travel) throws ObjectNotFoundException, ObjectVersionException {
		Travel t = null;
		try {			
			t = em.find(Travel.class, travel.getId(), LockModeType.PESSIMISTIC_WRITE);
			
			if(t == null)
			{				
				throw new ObjectNotFoundException(errorMsg);
			}
			
			em.merge(travel);		
		}
		catch(OptimisticLockException e) {			
			throw new ObjectVersionException();
		}
		catch(RuntimeException e) {
			throw e;
		}
		
	}

	@Override
	public void delete(Long id) throws ObjectNotFoundException {
		try {
			
			Travel travel = em.find(Travel.class, new Long(id), LockModeType.PESSIMISTIC_WRITE);
			
			if(travel == null)
			{				
				throw new ObjectNotFoundException(errorMsg);
			}
			
			em.remove(travel);			
		}
		catch(RuntimeException e) {
			throw e;
		}
		
	}

	@Override
	public Travel getTravel(Long id) throws ObjectNotFoundException {

		Travel travel = em.find(Travel.class, new Long(id));
		
		if(travel == null)
		{
			throw new ObjectNotFoundException(errorMsg);
		}
		
		return travel;
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Travel> getAllTravels() {

		return em.createQuery("select t from Travel t order by t.id").getResultList(); // JPQL
		
	}

}
