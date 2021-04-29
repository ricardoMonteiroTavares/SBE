package Services.Interfaces;

import java.util.List;

import Annotations.Profile;
import Entities.Card;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;

public interface CardService {
	@Profile(profiles={"admin","user"})
	Card insert(Card card);

	@Profile(profiles={"admin","user"})
	void update(Card card)
		throws ObjectNotFoundException, ObjectVersionException;

	@Profile(profiles={"admin"})
	void delete(Card card)
		throws ObjectNotFoundException;

	@Profile(profiles={"admin","user"})
	Card get(Long code)
		throws ObjectNotFoundException;

	@Profile(profiles={"admin"})
	List<Card> getAllCards();
}
