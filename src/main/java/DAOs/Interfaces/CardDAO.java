package DAOs.Interfaces;

import Entities.Card;
import Exceptions.CardNotFoundException;

import java.util.List;

public interface CardDAO {
	String insert(Card card);
	
	void update(Card card)
		throws CardNotFoundException;
	
	void delete(String code)
		throws CardNotFoundException;
	
	Card getCard(String code)
		throws CardNotFoundException;
	
	List<Card> getAllCards();
}
