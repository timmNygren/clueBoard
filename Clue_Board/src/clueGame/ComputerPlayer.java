package clueGame;

import java.util.ArrayList;
import java.util.Set;

public class ComputerPlayer extends Player {
	private Set<BoardCell> locations;
	private char lastRoomVisited;
	private Solution playerSuggestion;
	public Object lastRoom;
	
	
	public ComputerPlayer() {
		super();
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
	
	public void createSuggestion() {
		//stub
	}
	
	public Solution getSuggestion(){
		return playerSuggestion;
	}
	
	public void updateSeen(Card seen) {
		//stub
	}
	
	public void setLastVisited(BoardCell cell) {
		if (cell instanceof RoomCell) {
			
		}
	}

	public void setSuggestion(String string, String string2, String string3) {
		// TODO Auto-generated method stub
		
	}
}
