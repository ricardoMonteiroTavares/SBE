package DAOs.Implements;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;

import DAOs.Interfaces.CardDAO;
import Entities.Card;
import Exceptions.CardNotFoundException;
import Factories.EMFactory;

public class CardDAOImpl implements CardDAO {

	private EntityManager em;
	private EntityTransaction tx;
	private final String errorMsg = "Cartão não encotrado";
	
	@Override
	public String insert(Card card) 
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
			this.runtimeException(tx, e);
			return null;
		}
		finally {
			this.closeEM(em);
		}
	}

	@Override
	public void update(Card card) throws CardNotFoundException {
		Card c = null;
		
		try {
			em = EMFactory.newSession();
			tx = em.getTransaction();
			tx.begin();
			
			c = em.find(Card.class, card.getCode(), LockModeType.PESSIMISTIC_WRITE);
			
			if(c == null)
			{
				tx.rollback();
				throw new CardNotFoundException(errorMsg);
			}
			
			em.merge(card);
			tx.commit();
		}
		catch(RuntimeException e) {
			this.runtimeException(tx, e);
		}
		finally {
			this.closeEM(em);
		}
		
	}

	@Override
	public void delete(String code) throws CardNotFoundException {
		
		try {
			em = EMFactory.newSession();
			tx = em.getTransaction();
			tx.begin();
			
			Card card = em.find(Card.class, new String(code), LockModeType.PESSIMISTIC_WRITE);
			
			if(card == null)
			{
				tx.rollback();
				throw new CardNotFoundException(errorMsg);
			}
			
			em.remove(card);
			tx.commit();
		}
		catch(RuntimeException e) {
			this.runtimeException(tx, e);
		}
		finally {
			this.closeEM(em);
		}
		
	}

	@Override
	public Card getCard(String code) throws CardNotFoundException {
		try {
			em = EMFactory.newSession();
			
			Card card = em.find(Card.class, code);
			
			if(card == null)
			{
				throw new CardNotFoundException(errorMsg);
			}
			
			return card;
		}
		finally
		{
			this.closeEM(em);
		}
	}

	@Override
	public List<Card> getAllCards() {
		try
		{	
			em = EMFactory.newSession();
			
			@SuppressWarnings("unchecked")
			List<Card> cards = em.createQuery("select c from card c order by c.code").getResultList(); // JPQL

			return cards;
		} 
		finally
		{   
			this.closeEM(em);
		}
	}
	
	private void closeEM(EntityManager em) 
	{
		em.close();
	}
	
	private void runtimeException(EntityTransaction tx, RuntimeException e) throws RuntimeException
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
