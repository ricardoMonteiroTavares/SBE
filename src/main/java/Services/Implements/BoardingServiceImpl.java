package Services.Implements;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import DAOs.Interfaces.BoardingDAO;
import DAOs.Interfaces.CardDAO;
import DAOs.Interfaces.ExecuteTravelDAO;
import Entities.Boarding;
import Entities.Card;
import Exceptions.ObjectNotFoundException;
import Services.Interfaces.BoardingService;

public class BoardingServiceImpl implements BoardingService {

	@Autowired
	BoardingDAO dao;
	
	@Autowired
	ExecuteTravelDAO exDao;
	
	@Autowired
	CardDAO cardDao;
	
	private final String errorMsg = "Embarque, Cartão ou Intinerário não encotrado";
	
	@Override
	@Transactional
	public Boarding insert(Boarding boarding) throws ObjectNotFoundException
	{
		try 
		{
			Card card = cardDao.get(boarding.getId_card());
			
			double newBalance = card.getBalance() - exDao.get(boarding.getId_executeTravel()).getTicketValue();
			card.setBalance(newBalance);
			
			cardDao.update(card);
			return dao.insert(boarding);
		}
		catch(ObjectNotFoundException e) 
		{
			throw e;
		}
	}

	@Override
	@Transactional
	public void delete(Boarding boarding) throws ObjectNotFoundException 
	{
		try 
		{
			Card card = cardDao.get(boarding.getId_card());
			
			double newBalance = card.getBalance() + exDao.get(boarding.getId_executeTravel()).getTicketValue();
			card.setBalance(newBalance);
			
			cardDao.update(card);
			dao.delete(boarding);	
		}
		catch(ObjectNotFoundException e) 
		{			
			throw new ObjectNotFoundException(errorMsg);
		}
		catch(RuntimeException e) 
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
		catch(ObjectNotFoundException e) 
		{			
			throw new ObjectNotFoundException(errorMsg);
		}
		catch(RuntimeException e) 
		{
			throw e;
		}
	}

	@Override
	public List<Boarding> getAllBoardingsByDate(Long cardCode, Date date) {
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
	public List<Boarding> getAllBoardingsByPeriod(Long cardCode, Date start, Date finish) {
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
