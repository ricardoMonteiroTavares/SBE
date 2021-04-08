package DAOs.Interfaces;

import java.util.Date;
import java.util.List;

import Entities.ExecuteTravel;

public interface ExecuteTravelDAO extends DAO<ExecuteTravel, Long>{
	
	List<ExecuteTravel> getAllExTravelsByDate(Long travelId, Date date);

	List<ExecuteTravel> getAllExTravelsByDateAndDirection(Long travelId, String direction, Date date);
	
	List<ExecuteTravel> getAllExTravelsByPeriod(Long travelId, Date start, Date finish);
	
	List<ExecuteTravel> getAllExTravelsByPeriodAndDirection(Long travelId, String direction, Date start, Date finish);
}
