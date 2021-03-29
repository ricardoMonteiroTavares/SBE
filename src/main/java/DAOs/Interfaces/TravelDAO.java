package DAOs.Interfaces;

import java.util.List;

import Entities.Travel;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;

public interface TravelDAO {
	Long insert(Travel travel);
	
	void update(Travel travel)
		throws ObjectNotFoundException, ObjectVersionException;
	
	void delete(Long id)
		throws ObjectNotFoundException;
	
	Travel getTravel(Long id)
		throws ObjectNotFoundException;
	
	List<Travel> getAllTravels();
}
