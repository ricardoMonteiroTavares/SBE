package Service.Interfaces;

import java.util.Date;
import java.util.List;

import Entities.ExecuteTravel;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;

public interface ExecuteTravelService {
	ExecuteTravel insert(ExecuteTravel exTravel);

	void update(ExecuteTravel exTravel)
		throws ObjectNotFoundException, ObjectVersionException;

	void delete(ExecuteTravel id)
		throws ObjectNotFoundException;

	ExecuteTravel get(Long id)
			throws ObjectNotFoundException;

	List<ExecuteTravel> getAllExTravelsByDate(Long travelId, Date date)
			throws ObjectNotFoundException;
}
