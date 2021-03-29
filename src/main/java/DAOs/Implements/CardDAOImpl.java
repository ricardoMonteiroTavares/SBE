package DAOs.Implements;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;

import DAOs.Interfaces.CardDAO;
import Entities.Card;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;
import Factories.EMFactory;

public class CardDAOImpl implements CardDAO {

	private EntityManager em;
	private EntityTransaction tx;
	private final String errorMsg = "Cartão não encotrado";
	
	@Override
	public Long insert(Card card) 
	{
		
		try {
			em = EMFactory.newSession();
			tx = em.getTransaction();
			tx.begin();
			
			em.persist(card);
			tx.commit();
			return card.getCode();
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
	public void update(Card card) throws ObjectNotFoundException, ObjectVersionException {
		Card c = null;
		
		try {
			em = EMFactory.newSession();
			tx = em.getTransaction();
			tx.begin();
			
			c = em.find(Card.class, card.getCode(), LockModeType.PESSIMISTIC_WRITE);
			
			if(c == null)
			{
				tx.rollback();
				throw new ObjectNotFoundException(errorMsg);
			}
			
			em.merge(card);
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
	public void delete(Long code) throws ObjectNotFoundException {
		
		try {
			em = EMFactory.newSession();
			tx = em.getTransaction();
			tx.begin();
			
			Card card = em.find(Card.class, new Long(code), LockModeType.PESSIMISTIC_WRITE);
			
			if(card == null)
			{
				tx.rollback();
				throw new ObjectNotFoundException(errorMsg);
			}
			
			em.remove(card);
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
	public Card getCard(Long code) throws ObjectNotFoundException {
		try {
			em = EMFactory.newSession();
			
			Card card = em.find(Card.class, new Long(code));
			
			if(card == null)
			{
				throw new ObjectNotFoundException(errorMsg);
			}
			
			return card;
		}
		finally
		{
			this.closeEM();
		}
	}

	@Override
	public List<Card> getAllCards() {
		try
		{	
			em = EMFactory.newSession();
			
			@SuppressWarnings("unchecked")
			List<Card> cards = em.createQuery("select c from Card c order by c.code").getResultList(); // JPQL

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
