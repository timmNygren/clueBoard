package clueGame;

public class Suggestion {
	public Card person;
	public Card weapon;
	public Card room;
	
	public Suggestion(Card person, Card weapon, Card room) {
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}
	
	public boolean equals(Suggestion s) {
		return (s.person.equals(person) && s.room.equals(room) && s.weapon.equals(weapon));
	}
}
