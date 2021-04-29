package Services.Interfaces;

import java.util.Calendar;
import java.util.List;

import Annotations.Profile;
import Entities.ExecuteTravel;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;

public interface ExecuteTravelService {
	
	@Profile(profiles={"company"})
	ExecuteTravel insert(ExecuteTravel exTravel);
	
	@Profile(profiles={"company"})
	void update(ExecuteTravel exTravel)
		throws ObjectNotFoundException, ObjectVersionException;

	@Profile(profiles={"company"})
	void delete(ExecuteTravel exTravel)
		throws ObjectNotFoundException;

	@Profile(profiles={"company"})
	ExecuteTravel get(Long id)
			throws ObjectNotFoundException;

	@Profile(profiles={"company"})
	List<ExecuteTravel> getAllExTravelsByDate(Long travelId, Calendar date)
			throws ObjectNotFoundException;
	
	@Profile(profiles={"company"})
	List<ExecuteTravel> getAllExTravelsByDateAndDirection(Long travelId, String direction, Calendar date)
			throws ObjectNotFoundException;
	
	@Profile(profiles={"company"})
	List<ExecuteTravel> getAllExTravelsByPeriod(Long travelId, Calendar start, Calendar finish)
			throws ObjectNotFoundException;
	
	@Profile(profiles={"company"})
	List<ExecuteTravel> getAllExTravelsByPeriodAndDirection(Long travelId, String direction, Calendar start, Calendar finish) 
			throws ObjectNotFoundException;
}
