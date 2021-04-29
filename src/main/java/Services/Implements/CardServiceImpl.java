package Services.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import DAOs.Interfaces.CardDAO;
import Entities.Card;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;
import Services.Interfaces.CardService;

@Repository
public class CardServiceImpl implements CardService {

	@Autowired
	CardDAO dao;
	
	@Override
	@Transactional
	public Card insert(Card card) 
	{			
		return dao.insert(card);
	}

	@Override
	@Transactional
	public void update(Card card) throws ObjectNotFoundException, ObjectVersionException 
	{
		
		try 
		{
			dao.getByLockMode(card.getCode());
			dao.update(card);	
		}
		catch(ObjectNotFoundException | ObjectVersionException | RuntimeException e) 
		{
			throw e;
		}
		
	}

	@Override
	@Transactional
	public void delete(Card card) throws ObjectNotFoundException 
	{
		try 
		{
			dao.getByLockMode(card.getCode());
			dao.delete(card);
		}
		catch(ObjectNotFoundException | RuntimeException e) 
		{
			throw e;
		}
		
	}

	@Override
	public Card get(Long code) throws ObjectNotFoundException 
	{	
		try 
		{
			return dao.get(code);
		}
		catch(ObjectNotFoundException | RuntimeException e) 
		{
			throw e;
		}
		
	}

	@Override
	public List<Card> getAllCards() 
	{		
		return dao.getAllCards();	
	}
	

}
