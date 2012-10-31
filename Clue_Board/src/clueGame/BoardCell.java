package clueGame;

public abstract class BoardCell implements Comparable<BoardCell>{
	private int row;
	private int col;
	private int location;
	
	private String cell;
	
	public BoardCell(){
	}
	
	public BoardCell(int loc){
		location = loc;
	}
	
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
	
	public int getLocation(){
		return location;
	}
	
	
	public int getIndex(){
		return col;
		
	}
}
