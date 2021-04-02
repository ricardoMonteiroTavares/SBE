package DAOs.Implements;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import DAOs.Interfaces.BoardingDAO;
import Entities.Boarding;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;

@Repository
public class BoardingDAOImpl implements BoardingDAO {
	
	@PersistenceContext
	private EntityManager em;
	private final String errorMsg = "Embarque não encotrado";

	@Override
	public Long insert(Boarding boarding) {
		try {
			
			em.persist(boarding);
			
			return boarding.getId();
		}
		catch(RuntimeException e) {
			throw e;
		}		
	}

	@Override
	public void update(Boarding boarding) throws ObjectNotFoundException, ObjectVersionException {
		Boarding b = null;
		
		try {			
			
			b = em.find(Boarding.class, boarding.getId(), LockModeType.PESSIMISTIC_WRITE);
			
			if(b == null)
			{				
				throw new ObjectNotFoundException(errorMsg);
			}
			
			em.merge(boarding);			
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
			
			Boarding boarding = em.find(Boarding.class, new Long(id), LockModeType.PESSIMISTIC_WRITE);
			
			if(boarding == null)
			{			
				throw new ObjectNotFoundException(errorMsg);
			}
			
			em.remove(boarding);
		}
		catch(RuntimeException e) {
			throw e;
		}		
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Boarding> getAllBoardingsByDate(Long cardCode, Date date){
				
		String cmd = "select b from boarding b where b.id_card=" + cardCode.toString() + " AND b.date = " + date.toString() + " order by b.id";
		return em.createQuery(cmd).getResultList(); // JPQL
	
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Boarding> getAllBoardingsByPeriod(Long cardCode, Date start, Date finish){
					
		String cmd = "select b from boarding b where b.id_card=" + cardCode.toString() + " AND b.date = " + start.toString() + " AND b.date <= " + finish.toString() + " order by b.id";		
		return em.createQuery(cmd).getResultList(); // JPQL
		
	}

}
