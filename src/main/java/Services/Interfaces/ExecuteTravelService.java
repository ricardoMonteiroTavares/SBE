package Services.Interfaces;

import java.util.Calendar;
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

	List<ExecuteTravel> getAllExTravelsByDate(Long travelId, Calendar date);
	
	List<ExecuteTravel> getAllExTravelsByDateAndDirection(Long travelId, String direction, Calendar date);
	
	List<ExecuteTravel> getAllExTravelsByPeriod(Long travelId, Calendar start, Calendar finish);
	
	List<ExecuteTravel> getAllExTravelsByPeriodAndDirection(Long travelId, String direction, Calendar start, Calendar finish);
}
