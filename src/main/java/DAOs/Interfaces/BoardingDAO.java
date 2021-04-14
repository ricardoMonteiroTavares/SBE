package DAOs.Interfaces;

import java.util.Calendar;
import java.util.List;

import Annotations.GetList;
import Entities.Boarding;

public interface BoardingDAO extends DAO<Boarding, Long>{
	
	@GetList
	List<Boarding> getAllBoardingsByDate(Long cardCode, Calendar date);
	
	@GetList
	List<Boarding> getAllBoardingsByPeriod(Long cardCode, Calendar start, Calendar finish);
}
