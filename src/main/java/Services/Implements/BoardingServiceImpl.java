package Services.Implements;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import DAOs.Interfaces.BoardingDAO;
import DAOs.Interfaces.CardDAO;
import DAOs.Interfaces.ExecuteTravelDAO;
import Entities.Boarding;
import Entities.Card;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;
import Services.Interfaces.BoardingService;

public class BoardingServiceImpl implements BoardingService {

	@Autowired
	BoardingDAO dao;
	
	@Autowired
	ExecuteTravelDAO exDao;
	
	@Autowired
	CardDAO cardDao;
	
	@Override
	@Transactional
	public Boarding insert(Boarding boarding) throws ObjectNotFoundException, ObjectVersionException
	{
		try 
		{
			Card card = cardDao.getByLockMode(boarding.getId_card());
			
			double newBalance = card.getBalance() - exDao.get(boarding.getId_executeTravel()).getTicketValue();
			card.setBalance(newBalance);
			
			cardDao.update(card);
			return dao.insert(boarding);
		}
		catch(ObjectNotFoundException | ObjectVersionException e) 
		{
			throw e;
		}
	}

	@Override
	@Transactional
	public void delete(Boarding boarding) throws ObjectNotFoundException, ObjectVersionException 
	{
		try 
		{
			dao.get(boarding.getId());
			Card card = cardDao.getByLockMode(boarding.getId_card());
			
			double newBalance = card.getBalance() + exDao.get(boarding.getId_executeTravel()).getTicketValue();
			card.setBalance(newBalance);
			
			cardDao.update(card);
			dao.delete(boarding);	
		}		
		catch(ObjectNotFoundException | ObjectVersionException | RuntimeException e) 
		{
			throw e;
		}		
	}

	@Override
	public Boarding get(Long id) throws ObjectNotFoundException {
		try 
		{
			return dao.get(id);
		}
		catch(ObjectNotFoundException | RuntimeException e) 
		{			
			throw e;
		}
	}

	@Override
	public List<Boarding> getAllBoardingsByDate(Long cardCode, Calendar date) {
		try 
		{
			return dao.getAllBoardingsByDate(cardCode, date);
		}		
		catch(RuntimeException e) 
		{
			throw e;
		}
	}

	@Override
	public List<Boarding> getAllBoardingsByPeriod(Long cardCode, Calendar start, Calendar finish) {
		try 
		{
			return dao.getAllBoardingsByPeriod(cardCode, start, finish);
		}		
		catch(RuntimeException e) 
		{
			throw e;
		}
	}

}
