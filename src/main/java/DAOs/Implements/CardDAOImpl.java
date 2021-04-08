package DAOs.Implements;

import DAOs.Interfaces.CardDAO;
import Entities.Card;

public abstract class CardDAOImpl extends DAOImpl<Card, Long> implements CardDAO {

	public CardDAOImpl() {
		super(Card.class);
	}	

}
