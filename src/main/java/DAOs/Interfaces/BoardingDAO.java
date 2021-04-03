package DAOs.Interfaces;

import java.util.Date;
import java.util.List;

import Entities.Boarding;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;

public interface BoardingDAO {
	Long insert(Boarding boarding);
	
	void delete(Long id)
		throws ObjectNotFoundException;
	
	Boarding getBoarding(Long code)
			throws ObjectNotFoundException;
	
	List<Boarding> getAllBoardingsByDate(Long cardCode, Date date);
	
	List<Boarding> getAllBoardingsByPeriod(Long cardCode, Date start, Date finish);
}
