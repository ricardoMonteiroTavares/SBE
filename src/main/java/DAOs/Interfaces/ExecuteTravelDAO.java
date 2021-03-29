package DAOs.Interfaces;

import java.util.Date;
import java.util.List;

import Entities.ExecuteTravel;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;

public interface ExecuteTravelDAO {
	Long insert(ExecuteTravel exTravel);
	
	void update(ExecuteTravel exTravel)
		throws ObjectNotFoundException, ObjectVersionException;
	
	void delete(Long id)
		throws ObjectNotFoundException;
	
	ExecuteTravel getExTravel(Long id)
			throws ObjectNotFoundException;
	
	List<ExecuteTravel> getAllExTravelsByDate(Long travelId, Date date)
			throws ObjectNotFoundException;
	
	List<ExecuteTravel> getAllExTravelsByDateAndDirection(Long travelId, String direction, Date date)
			throws ObjectNotFoundException;
	
	List<ExecuteTravel> getAllExTravelsByPeriod(Long travelId, Date start, Date finish)
			throws ObjectNotFoundException;
	
	List<ExecuteTravel> getAllExTravelsByPeriodAndDirection(Long travelId, String direction, Date start, Date finish)
			throws ObjectNotFoundException;
}
