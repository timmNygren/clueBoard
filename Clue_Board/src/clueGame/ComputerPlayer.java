package clueGame;

import java.util.ArrayList;
import java.util.Set;

import clueGame.Card.CardType;

public class ComputerPlayer extends Player {
	private Set<BoardCell> locations;
	private int location;
	private ArrayList<Card> notSeen;
	private char lastRoomVisited;
	private Solution playerSuggestion;
	public Object lastRoom;
	
	
	public ComputerPlayer() {
		super();
		//initialized notSeen with every card
	}
	
	public int pickLocation(Set<BoardCell> targets) {
		for(BoardCell room:targets){
			if(room.isRoom() && ((RoomCell) room).getInitial() == lastRoomVisited){
				targets.remove(room);
			}else if(room.isRoom()){
				locations.add(room);
				return 0;
			}
		}
		return 0;
		
	}
	
	public Suggestion createSuggestion(BoardCell room) {
		//stub
		Card temp3 = new Card(room.toString()/*Most likely not right*/, CardType.ROOM);
		Card temp = new Card("stuff", CardType.PERSON);
		Card temp2 = new Card("other", CardType.WEAPON);
		//make sure room is current room computer is in
		Suggestion suggestion = new Suggestion(temp, temp2, temp3);
		return suggestion;
	}
	
	public Solution getSuggestion(){
		return playerSuggestion;
	}
	
	public void updateSeen(Card seen) {
		//stub
		//removed seen cards from notSeen
	}
	
	public void setLastVisited(BoardCell cell) {
		if (cell instanceof RoomCell) {
			
		}
	}

	public void setSuggestion(String string, String string2, String string3) {
		// TODO Auto-generated method stub
		
	}
	
	public void setLocation(int location) {
		this.location = location;
	}
}
