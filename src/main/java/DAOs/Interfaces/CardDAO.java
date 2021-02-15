package DAOs.Interfaces;

import Entities.Card;
import Exceptions.CardNotFoundException;

import java.util.List;

public interface CardDAO {
	Long insert(Card card);
	
	void update(Card card)
		throws CardNotFoundException;
	
	void delete(Long code)
		throws CardNotFoundException;
	
	Card getCard(Long code)
		throws CardNotFoundException;
	
	List<Card> getAllCards();
}
