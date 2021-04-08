package DAOs.Implements;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import DAOs.Interfaces.ExecuteTravelDAO;
import Entities.ExecuteTravel;
import Exceptions.ObjectNotFoundException;

@Repository
public abstract class ExecuteTravelDAOImpl extends DAOImpl<ExecuteTravel, Long> implements ExecuteTravelDAO {
	
	public ExecuteTravelDAOImpl() {
		super(ExecuteTravel.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ExecuteTravel> getAllExTravelsByDate(Long travelId, Date date) throws ObjectNotFoundException {	

		String cmd = "select e from execute_travel e where e.id_travel=" + travelId.toString() + " AND e.date LIKE " + date.toString() + " order by e.id";
		
		return em.createQuery(cmd).getResultList(); // JPQL
			
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ExecuteTravel> getAllExTravelsByDateAndDirection(Long travelId, String direction, Date date)
			throws ObjectNotFoundException {
		
		String cmd = "select e from execute_travel e where e.id_travel=" + travelId.toString() + " AND e.date = " + date.toString() + " AND e.direction LIKE " + direction + " order by e.id";
		
		return em.createQuery(cmd).getResultList(); // JPQL

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ExecuteTravel> getAllExTravelsByPeriod(Long travelId, Date start, Date finish)
			throws ObjectNotFoundException {
		
		String cmd = "select e from execute_travel e where e.id_travel=" + travelId.toString() + " AND e.date >= " + start.toString() + " AND e.date <= " + finish.toString() + " order by e.id";
		
		return em.createQuery(cmd).getResultList(); // JPQL
	
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ExecuteTravel> getAllExTravelsByPeriodAndDirection(Long travelId, String direction, Date start,
			Date finish) throws ObjectNotFoundException {

		String cmd = "select e from execute_travel e where e.id_travel=" + travelId.toString() + " AND e.date >= " + start.toString() + " AND e.date <= " + finish.toString() + " AND e.direction LIKE " + direction + " order by e.id";
		
		return em.createQuery(cmd).getResultList(); // JPQL

	}

}
