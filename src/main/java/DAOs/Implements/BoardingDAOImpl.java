package DAOs.Implements;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import DAOs.Interfaces.BoardingDAO;
import Entities.Boarding;

@Repository
public abstract class BoardingDAOImpl extends DAOImpl<Boarding, Long> implements BoardingDAO {
	
	public BoardingDAOImpl() {
		super(Boarding.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Boarding> getAllBoardingsByDate(Long cardCode, Date date){
				
		String cmd = "select b from boarding b where b.id_card=" + cardCode.toString() + " AND b.date = " + date.toString() + " order by b.id";
		return em.createQuery(cmd).getResultList(); // JPQL
	
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Boarding> getAllBoardingsByPeriod(Long cardCode, Date start, Date finish){
					
		String cmd = "select b from boarding b where b.id_card=" + cardCode.toString() + " AND b.date = " + start.toString() + " AND b.date <= " + finish.toString() + " order by b.id";		
		return em.createQuery(cmd).getResultList(); // JPQL
		
	}

}
