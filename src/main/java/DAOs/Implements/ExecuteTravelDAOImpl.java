package DAOs.Implements;

import org.springframework.stereotype.Repository;

import DAOs.Interfaces.ExecuteTravelDAO;
import Entities.ExecuteTravel;

@Repository
public abstract class ExecuteTravelDAOImpl extends DAOImpl<ExecuteTravel, Long> implements ExecuteTravelDAO {
	
	public ExecuteTravelDAOImpl() {
		super(ExecuteTravel.class);
	}

}
