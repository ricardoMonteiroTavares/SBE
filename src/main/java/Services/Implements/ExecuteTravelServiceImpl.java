package Services.Implements;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import DAOs.Interfaces.ExecuteTravelDAO;
import Entities.ExecuteTravel;
import Exceptions.ObjectNotFoundException;
import Services.Interfaces.ExecuteTravelService;

public class ExecuteTravelServiceImpl implements ExecuteTravelService {

	@Autowired
	ExecuteTravelDAO dao;

	private final String errorMsg = "Intinerário não encotrado";
	
	@Override
	@Transactional
	public ExecuteTravel insert(ExecuteTravel exTravel) 
	{
		return dao.insert(exTravel);
	}

	@Override
	@Transactional
	public void update(ExecuteTravel exTravel) throws ObjectNotFoundException 
	{
		try
		{
			dao.getByLockMode(exTravel.getId());
			dao.update(exTravel);
		}
		catch(ObjectNotFoundException e)
		{
			throw new ObjectNotFoundException(errorMsg);
		}
		catch(RuntimeException e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public void delete(ExecuteTravel exTravel) throws ObjectNotFoundException 
	{
		try 
		{
			dao.getByLockMode(exTravel.getId());
			dao.delete(exTravel);
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
	public ExecuteTravel get(Long id) throws ObjectNotFoundException 
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
	public List<ExecuteTravel> getAllExTravelsByDate(Long travelId, Date date) 
	{
		try 
		{
			return dao.getAllExTravelsByDate(travelId, date);
		}
		catch(RuntimeException e) 
		{
			throw e;
		}
	}

	@Override
	public List<ExecuteTravel> getAllExTravelsByDateAndDirection(Long travelId, String direction, Date date)
	{
		try 
		{
			return dao.getAllExTravelsByDateAndDirection(travelId, direction, date);
		}
		catch(RuntimeException e) 
		{
			throw e;
		}
	}

	@Override
	public List<ExecuteTravel> getAllExTravelsByPeriod(Long travelId, Date start, Date finish)
	{
		try 
		{
			return dao.getAllExTravelsByPeriod(travelId, start, finish);
		}
		catch(RuntimeException e) 
		{
			throw e;
		}
	}

	@Override
	public List<ExecuteTravel> getAllExTravelsByPeriodAndDirection(Long travelId, String direction, Date start,
			Date finish) {
		try 
		{
			return dao.getAllExTravelsByPeriodAndDirection(travelId, direction, start, finish);
		}
		catch(RuntimeException e) 
		{
			throw e;
		}
	}

}
