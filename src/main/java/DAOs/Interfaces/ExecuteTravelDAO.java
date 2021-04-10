package DAOs.Interfaces;

import java.util.Date;
import java.util.List;

import Annotations.GetList;
import Entities.ExecuteTravel;

public interface ExecuteTravelDAO extends DAO<ExecuteTravel, Long>{
	
	@GetList
	List<ExecuteTravel> getAllExTravelsByDate(Long travelId, Date date);

	@GetList
	List<ExecuteTravel> getAllExTravelsByDateAndDirection(Long travelId, String direction, Date date);
	
	@GetList
	List<ExecuteTravel> getAllExTravelsByPeriod(Long travelId, Date start, Date finish);
	
	@GetList
	List<ExecuteTravel> getAllExTravelsByPeriodAndDirection(Long travelId, String direction, Date start, Date finish);
}
