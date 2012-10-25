package clueGame;

public class WalkwayCell extends BoardCell {
	
	@Override
	public boolean isWalkway() {
		return true;
	}
	
	@Override
	public void draw() {
		System.out.print("W ");
	}

	@Override
	public int compareTo(BoardCell o) {
		if (o.equals(this))
			return 0;
		return 1;
	}	
}