package Services.Interfaces;

import java.util.Date;
import java.util.List;

import Entities.Boarding;
import Exceptions.ObjectNotFoundException;

public interface BoardingService {
	Boarding insert(Boarding boarding);

	void delete(Boarding id)
		throws ObjectNotFoundException;

	Boarding get(Long code)
			throws ObjectNotFoundException;

	List<Boarding> getAllBoardingsByDate(Long cardCode, Date date);
}
