package Services.Interfaces;

import java.util.Calendar;
import java.util.List;

import Entities.Boarding;
import Exceptions.ObjectNotFoundException;

public interface BoardingService {
	Boarding insert(Boarding boarding) 
		throws ObjectNotFoundException;

	void delete(Boarding boarding)
		throws ObjectNotFoundException;

	Boarding get(Long id)
		throws ObjectNotFoundException;

	List<Boarding> getAllBoardingsByDate(Long cardCode, Calendar date);
	
	List<Boarding> getAllBoardingsByPeriod(Long cardCode, Calendar start, Calendar finish);
}
