package Factories;

import org.springframework.context.annotation.Bean;

import DAOs.Implements.BoardingDAOImpl;
import DAOs.Implements.CardDAOImpl;
import DAOs.Implements.ExecuteTravelDAOImpl;
import DAOs.Implements.TravelDAOImpl;
import Interceptors.DAOIntercept;
import net.sf.cglib.proxy.Enhancer;

public class DAOFactory {

	@Bean
	public static BoardingDAOImpl getBoardingDAO() throws Exception {
		return getDAO(DAOs.Implements.BoardingDAOImpl.class);
	}
	
	@Bean
	public static CardDAOImpl getCardDAO() throws Exception {
		return getDAO(DAOs.Implements.CardDAOImpl.class);
	}
	
	@Bean
	public static TravelDAOImpl getTravelDAO() throws Exception {
		return getDAO(DAOs.Implements.TravelDAOImpl.class);
	}
	
	@Bean
	public static ExecuteTravelDAOImpl getExTravelDAO() throws Exception {
		return getDAO(DAOs.Implements.ExecuteTravelDAOImpl.class);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getDAO(Class<T> type)
	{			
		return (T) Enhancer.create(type, new DAOIntercept());
	}
}
