package Services.Interfaces;

import java.util.Date;
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

	List<Boarding> getAllBoardingsByDate(Long cardCode, Date date);
	
	List<Boarding> getAllBoardingsByPeriod(Long cardCode, Date start, Date finish);
}
