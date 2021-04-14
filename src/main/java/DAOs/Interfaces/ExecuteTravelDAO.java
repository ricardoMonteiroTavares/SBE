package DAOs.Interfaces;

import java.util.Calendar;
import java.util.List;

import Annotations.GetList;
import Entities.ExecuteTravel;

public interface ExecuteTravelDAO extends DAO<ExecuteTravel, Long>{
	
	@GetList
	List<ExecuteTravel> getAllExTravelsByDate(Long travelId, Calendar date);

	@GetList
	List<ExecuteTravel> getAllExTravelsByDateAndDirection(Long travelId, String direction, Calendar date);
	
	@GetList
	List<ExecuteTravel> getAllExTravelsByPeriod(Long travelId, Calendar start, Calendar finish);
	
	@GetList
	List<ExecuteTravel> getAllExTravelsByPeriodAndDirection(Long travelId, String direction, Calendar start, Calendar finish);
}
