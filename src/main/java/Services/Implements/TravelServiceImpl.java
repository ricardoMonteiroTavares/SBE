package Services.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import DAOs.Interfaces.TravelDAO;
import Entities.Travel;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;
import Services.Interfaces.TravelService;

public class TravelServiceImpl implements TravelService{

	@Autowired
	TravelDAO dao;
	
	private final String errorMsg = "Viagem não encotrada";
	
	@Override
	@Transactional
	public Travel insert(Travel travel) 
	{
		return dao.insert(travel);
	}

	@Override
	@Transactional
	public void update(Travel travel) throws ObjectNotFoundException 
	{
		try 
		{
			dao.update(travel);	
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
	@Transactional
	public void delete(Travel travel) throws ObjectNotFoundException 
	{
		try 
		{
			dao.delete(travel);
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
	public Travel get(Long id) throws ObjectNotFoundException 
	{
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
	public List<Travel> getAllTravels() 
	{		
		return dao.getAllTravels();
	}

}
