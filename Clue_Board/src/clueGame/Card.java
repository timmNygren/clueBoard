package clueGame;

public class Card {
	
	public enum CardType {PERSON, WEAPON, ROOM}
	
	private String name;
	private CardType cardType;
	
	public boolean equals(Card card) {
		if (name.equals(card.name) && cardType == card.cardType){
			return true;
		}
		return false;
	}
	
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	
	
	
	
}
