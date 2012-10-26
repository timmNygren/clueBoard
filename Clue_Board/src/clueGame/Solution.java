package clueGame;

public class Solution {
	public String person;
	public String weapon;
	public String room;
	
	public Solution(String person, String weapon, String room) {
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}
	
	public boolean equals(Solution s){
		if(person.equals(s.person) && weapon.equals(s.weapon) && room.equals(s.room)){
			return true;
		}else{
			return false;
		}
		
	}
	
}
