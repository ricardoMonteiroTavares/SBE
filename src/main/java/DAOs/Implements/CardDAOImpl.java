package DAOs.Implements;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import DAOs.Interfaces.CardDAO;
import Entities.Card;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;
import Factories.EMFactory;

@Repository
public class CardDAOImpl implements CardDAO {

	@PersistenceContext
	private EntityManager em;
	
	private final String errorMsg = "Cartão não encotrado";
	
	@Override
	public Long insert(Card card) 
	{
		
		try {			
			em.persist(card);
			
			return card.getCode();
		}
		catch(RuntimeException e) {
			throw e;
		}	
	}

	@Override
	public void update(Card card) throws ObjectNotFoundException, ObjectVersionException {
		Card c = null;
		
		try {
			
			c = em.find(Card.class, card.getCode(), LockModeType.PESSIMISTIC_WRITE);
			
			if(c == null)
			{		
				throw new ObjectNotFoundException(errorMsg);
			}
			
			em.merge(card);			
		}
		catch(OptimisticLockException e) {			
			throw new ObjectVersionException();
		}
		catch(RuntimeException e) {
			throw e;
		}
		
	}

	@Override
	public void delete(Long code) throws ObjectNotFoundException {
		
		try {
			em = EMFactory.newSession();
			
			Card card = em.find(Card.class, new Long(code), LockModeType.PESSIMISTIC_WRITE);
			
			if(card == null)
			{				
				throw new ObjectNotFoundException(errorMsg);
			}
			
			em.remove(card);
		}
		catch(RuntimeException e) {
			throw e;
		}
		
	}

	@Override
	public Card getCard(Long code) throws ObjectNotFoundException {
			
		Card card = em.find(Card.class, new Long(code));
		
		if(card == null)
		{
			throw new ObjectNotFoundException(errorMsg);
		}
		
		return card;
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Card> getAllCards() {		
		
		return em.createQuery("select c from Card c order by c.code").getResultList(); // JPQL
		
	}
	

}
