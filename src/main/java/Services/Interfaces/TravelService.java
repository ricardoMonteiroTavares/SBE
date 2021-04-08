package Services.Interfaces;

import java.util.List;

import Entities.Travel;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;

public interface TravelService {
	Travel insert(Travel travel);

	void update(Travel travel)
		throws ObjectNotFoundException;

	void delete(Travel travel)
		throws ObjectNotFoundException;

	Travel get(Long id)
		throws ObjectNotFoundException;

	List<Travel> getAllTravels();
}
