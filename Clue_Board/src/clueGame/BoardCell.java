package clueGame;

public abstract class BoardCell implements Comparable<BoardCell>{
	private int row;
	private int col;
	protected String cell;
	public boolean isWalkway() {
		return false;
	}
	
	public boolean isRoom() {
		return false;
	}
	
	
	public void draw() {
		
	}

	public boolean isDoorWay() {
		return false;
	}	
}
