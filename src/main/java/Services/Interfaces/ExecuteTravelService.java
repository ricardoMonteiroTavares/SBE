package Services.Interfaces;

import java.util.Date;
import java.util.List;

import Entities.ExecuteTravel;
import Exceptions.ObjectNotFoundException;

public interface ExecuteTravelService {
	ExecuteTravel insert(ExecuteTravel exTravel);

	void update(ExecuteTravel exTravel)
		throws ObjectNotFoundException;

	void delete(ExecuteTravel exTravel)
		throws ObjectNotFoundException;

	ExecuteTravel get(Long id)
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
