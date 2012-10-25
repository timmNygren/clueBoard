package clueGame;

public class RoomCell extends BoardCell {
	private DoorDirection doorDirection;
	private char room_init;
	private String room;
	
	public RoomCell(String r) {
		room = r;
		room_init = room.toUpperCase().charAt(0);
		switch(room.toUpperCase().charAt(1)) {
			
		
			case 'U': doorDirection = DoorDirection.UP;
						break;
			case 'D': doorDirection = DoorDirection.DOWN;
						break;
			case 'L': doorDirection = DoorDirection.LEFT;
						break;
			case 'R': doorDirection = DoorDirection.RIGHT;
						break;
			default: doorDirection = DoorDirection.NONE;
						break;
		}
		
	}
	
	public enum DoorDirection {
		UP,DOWN,LEFT,RIGHT,NONE;

	}
	
	@Override
	public boolean isDoorWay() {
		if (doorDirection.equals(DoorDirection.NONE))
			return false;
		return true;
	}
	@Override
	public boolean isRoom() {
		return true;
	}
	
	@Override
	public void draw() {
		System.out.print(room);
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return room_init;
	}
	@Override
	public int compareTo(BoardCell cell) {
		if (cell.equals(this))
			return 0;
		return 1;
	}	
}
