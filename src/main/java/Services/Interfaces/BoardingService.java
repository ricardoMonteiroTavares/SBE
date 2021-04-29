package Services.Interfaces;

import java.util.Calendar;
import java.util.List;

import Annotations.Profile;
import Entities.Boarding;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;

public interface BoardingService {
	
	@Profile(profiles={"user"})
	Boarding insert(Boarding boarding) 
		throws ObjectNotFoundException, ObjectVersionException;

	@Profile(profiles={"admin"})
	void delete(Boarding boarding)
		throws ObjectNotFoundException, ObjectVersionException;

	@Profile(profiles={"admin","user"})
	Boarding get(Long id)
		throws ObjectNotFoundException;

	@Profile(profiles={"admin","user"})
	List<Boarding> getAllBoardingsByDate(Long cardCode, Calendar date);
	
	@Profile(profiles={"admin","user"})
	List<Boarding> getAllBoardingsByPeriod(Long cardCode, Calendar start, Calendar finish);
}
