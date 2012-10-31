package clueGame;
import java.io.*;
import java.util.*;

import clueGame.Card.CardType;
import clueGame.RoomCell.DoorDirection;


public class Board {
	private ArrayList<BoardCell> cells;
	private Map<Character, String> rooms;
	private int [][] grid;		//contain indices 
	private BoardCell [][] board;		//contain room symbols
	private int numRows=0;
	private int numColumns=0;
	private Map<Integer, LinkedList<Integer>> adjMtx;
	private Set<BoardCell> targets;
	private Set<Integer> targetsInt;
	private ArrayList<Integer> seen = new ArrayList<Integer>();
	private ArrayList<Card> deck = new ArrayList<Card>();
	
	private ArrayList<ComputerPlayer> computers;
	private HumanPlayer player;
	
	private Random generator = new Random();
	
	public Board() {
		adjMtx = new TreeMap<Integer, LinkedList<Integer>>();
	}
	
	public Board(String legendFile) throws FileNotFoundException, BadConfigFormatException {
		loadLegend(legendFile);
	}

	public ArrayList<Card> getDeck(){
		return deck;
	}
	
	public void loadConfigFiles(String boardFile, String legendFile, String playerFile, String deckFile) throws FileNotFoundException, BadConfigFormatException {
		loadDeck(deckFile);
		loadLegend(legendFile);
		loadPlayer(playerFile);
		getRowCol(boardFile);
		loadBoard(boardFile);
		fillGrid();
	}
	

	private void loadDeck(String deckFile) throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(deckFile);
		Scanner in = new Scanner(reader);
		Card tempCard;
		
		while (in.hasNextLine()) {
			String[] line = in.nextLine().split("\\, ");
			if(line[0].contentEquals("R")){
				tempCard = new Card(line[1],CardType.ROOM);
			}else if(line[0].contentEquals("W")){
				tempCard = new Card(line[1],CardType.WEAPON);
			}else if(line[0].contentEquals("P")){
				tempCard = new Card(line[1],CardType.PERSON);
			}else{
				throw new BadConfigFormatException("Invalid Card Type: "+line[0]);
			}
			deck.add(tempCard);
		}
			
		in.close();
	}

	public void loadLegend(String inputFile) throws FileNotFoundException, BadConfigFormatException {
		rooms = new TreeMap<Character, String>();
		FileReader reader = new FileReader(inputFile);
		Scanner in = new Scanner(reader);
		
		while (in.hasNextLine()) {
			String[] line = in.nextLine().split("\\, ");
			if (line.length != 2) {
				throw new BadConfigFormatException(inputFile);
			}
			rooms.put(line[0].charAt(0), line[1]);
		}
			
		in.close();
	}
	
	//load in the players from a file
	public void loadPlayer(String inputFile) throws FileNotFoundException, NumberFormatException, BadConfigFormatException {
		computers =  new ArrayList<ComputerPlayer>();
		FileReader reader = new FileReader(inputFile);
		Scanner in = new Scanner(reader);
		
		while (in.hasNextLine()) {
			String[] line = in.nextLine().split("\\, ");
			if(line[0].contentEquals("H")){

				player = new HumanPlayer(line[1],line[2],Integer.parseInt(line[3]), this);
			}else if(line[0].contentEquals("C")){
				System.out.println();
				ComputerPlayer tempComp = new ComputerPlayer(line[1],line[2],Integer.parseInt(line[3]), this);
				computers.add(tempComp);
				computers.get(computers.size()-1).setDeck(deck);
			}
		}
			
		in.close();
		
	}
	
	public void selectAnswer() {
		System.out.println("NOT IMPLEMENTED");
	}
	
	public void deal() {
		//Deal Player Hand
		ArrayList<Card> copyDeck = deck;
		int randomIndex = generator.nextInt( copyDeck.size() );
		while(player.getHand().size()<3){
			if(!player.hasCardType(copyDeck.get(randomIndex).getCardType())){

				player.addCardToHand(copyDeck.get(randomIndex));
				copyDeck.remove(randomIndex);
				randomIndex = generator.nextInt(copyDeck.size());
			}
			randomIndex = generator.nextInt(copyDeck.size());
		}
		System.out.println();

			for(ComputerPlayer cPlayer:computers){
				while(cPlayer.getHand().size()<3){
					if(!cPlayer.hasCardType(copyDeck.get(randomIndex).getCardType())){

						cPlayer.addCardToHand(copyDeck.get(randomIndex));
						copyDeck.remove(randomIndex);
						randomIndex = generator.nextInt(copyDeck.size());
					}
					randomIndex = generator.nextInt(copyDeck.size());
				}	
				System.out.println();
			}
			
	}
	
	
	public boolean checkAccusation(String person, String room, String weapon) {
		System.out.println("DEFAULT RETURN FALSE");
		return false;
	}
	
	public void handleSuggestion(String person, String weapon, String room) {
		System.out.println("NOT IMPLEMENTED");
	}
	
	public ArrayList<ComputerPlayer> getComputers() {
		return computers;
	}
	
	public HumanPlayer getHumanPlayer() {
		return player;
	}
	
	public void loadBoard(String inputFile) throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(inputFile);
		Scanner in = new Scanner(reader);
		cells = new ArrayList<BoardCell>();
		
		board = new BoardCell[numRows][numColumns];
		int rows = 0;
		while (in.hasNextLine()) {
			int cols = 0;
			String[] line = in.nextLine().split("\\,");
			
			if (line.length != numColumns) {
				throw new BadConfigFormatException(inputFile);
			}
				
			for (String x : line) {
				if (!rooms.containsKey(x.charAt(0))) {
					throw new BadConfigFormatException(inputFile);
				}
				if (x.equalsIgnoreCase("w")) {
					WalkwayCell wCell = new WalkwayCell(cells.size());
					cells.add(wCell);
					board[rows][cols] = wCell;
				} else {
					RoomCell rCell = new RoomCell(x + " ", cells.size());
					cells.add(rCell);
					board[rows][cols] = rCell;
					
				}
				
				cols++;
			}
			rows++;
		}
		in.close();
		
		
	}
	
	public void printBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j].draw();
			}
			System.out.println("");
		}
	}
	
	public void getRowCol(String inputFile) throws FileNotFoundException, BadConfigFormatException{
		int most = 0, previous = 0;
		FileReader reader = new FileReader(inputFile);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()) {
			String[] line = in.nextLine().split("\\,");
			if (previous != 0 && line.length != previous) {
				throw new BadConfigFormatException(inputFile);
			}
			numColumns = line.length;
			numRows++;
			previous = line.length;
		}			
		in.close();
	}
	
	public void fillGrid() {
		grid = new int [numRows][numColumns];
		int x = 0;
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				grid[i][j] = x;
				x++;
			}			
		}
	}
	
	public int calcIndex(int rowNum, int columnNum) {
		try{
			int index = grid[rowNum][columnNum];
			return index;
		}
		catch(ArrayIndexOutOfBoundsException e){
			return -1;
		}
		
	}
	
	public BoardCell getRoomCellAt(int row, int col) {

		return board[row+1][col+1];
	}

	//GETTERS
	public BoardCell getCellAt(int i) {
		return cells.get(i);
	}

	public Map<Character, String> getRooms() {		
		return rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	
	public void calcAdjacencies(){
		//This room will be used to add to the list of adjacencies
		RoomCell tempRoom;
		
		//This variable keeps track of where to put each adjecencies list
		int position = 0;
		
		//Iterate through the entire board and calculate adjacencies for every cell
		for(int row=0; row<numRows; row++){
			for(int col=0; col<numColumns; col++){
				//Create a list to store the adjacencies for the current cell
				LinkedList<Integer> list = new LinkedList<Integer>();
				
				
				//If the cell is a room cell, it is not necessary to calculate adjacencies
				if(board[row][col].isRoom() && !board[row][col].isDoorWay()){
					//Store the adjacencies and update position
					adjMtx.put(position, list);

					position++;
					continue;
				}
				
				//If the cell is a door then add only one adjacency according to the door direction
				if (board[row][col].isDoorWay()) {

					tempRoom =(RoomCell) board[row][col];
					if (tempRoom.getDoorDirection() == DoorDirection.UP)
						list.add(grid[row-1][col]);
					if (tempRoom.getDoorDirection() == DoorDirection.RIGHT)
						list.add(grid[row][col+1]);
					if (tempRoom.getDoorDirection() == DoorDirection.DOWN)
						list.add(grid[row+1][col]);
					if (tempRoom.getDoorDirection() == DoorDirection.LEFT)
						list.add(grid[row][col-1]);	
					adjMtx.put(position, list);

					position++;
					continue;
				} 
				
				//If the cell is a walkway, check all sides of cell and make sure not to add adjacencies
				//that are off the board
				if(!board[row][col].isRoom()){
					
					//Check Above
					if(calcIndex(row-1,col)!=-1){
						//If a door is found, only add it to the adjacencies list if the door direction
						//indicates that it can be entered from the current cell
						if(board[row-1][col].isDoorWay()){
							//Create a roomcell so that door direction can be accessed
							RoomCell tempDoor = (RoomCell) board[row-1][col];
							if(tempDoor.getDoorDirection() == DoorDirection.DOWN){
								list.add(grid[row-1][col]);
							}
						}else if(board[row-1][col].isWalkway()){
							list.add(grid[row-1][col]);
						}
						
					}
					
					//Check Below
					if(calcIndex(row+1,col)!=-1){
						//If a door is found, only add it to the adjacencies list if the door direction
						//indicates that it can be entered from the current cell
						if(board[row+1][col].isDoorWay()){
							//Create a roomcell so that door direction can be accessed
							RoomCell tempDoor = (RoomCell) board[row+1][col];
							if(tempDoor.getDoorDirection() == DoorDirection.UP){
								list.add(grid[row+1][col]);
							}
						}else if(board[row+1][col].isWalkway()){
							list.add(grid[row+1][col]);
						}
						
					}
					
					//Check Left
					if(calcIndex(row,col-1)!=-1){
						
						//If a door is found, only add it to the adjacencies list if the door direction
						//indicates that it can be entered from the current cell
						if(board[row][col-1].isDoorWay()){
							//Create a roomcell so that door direction can be accessed
							RoomCell tempDoor = (RoomCell) board[row][col-1];
							if(tempDoor.getDoorDirection() == DoorDirection.RIGHT){
								list.add(grid[row][col-1]);
							}
						}else if(board[row][col-1].isWalkway()){
							list.add(grid[row][col-1]);
						}
						
						
					}
					
					//Check Right
					if(calcIndex(row,col+1)!=-1){
						//If a door is found, only add it to the adjacencies list if the door direction
						//indicates that it can be entered from the current cell
						if(board[row][col+1].isDoorWay()){
							//Create a roomcell so that door direction can be accessed
							RoomCell tempDoor = (RoomCell) board[row][col+1];
							if(tempDoor.getDoorDirection() == DoorDirection.LEFT){
								list.add(grid[row][col+1]);
							}
						}else if(board[row][col+1].isWalkway()){
							list.add(grid[row][col+1]);
						}

					}
				}
				
				//Store the adjacencies and update position
				adjMtx.put(position, list);

				position++;
				
				
			}
		}
	}
	

	public void printAdj() {
		Set<Integer> keySet = adjMtx.keySet();
		for (Integer key : keySet)
			System.out.println(key + " && " + adjMtx.get(key));
	}
	
	public LinkedList<Integer> getAdjList(int i) {
		return adjMtx.get(i);
	}
	
	public void setSeen(int index) {
		seen.clear();
		seen.add(index);
	}
	
	
	//resets the target list, calls bridge function, removes the starting grid cell if present
	public void calcTargets(int start, int steps) {
		targets = new HashSet<BoardCell>();
		targetsInt = new TreeSet<Integer>();
		bridge(start, steps, start, -1, steps);
		if (targets.contains(getCellAt(start))){
			targets.remove(getCellAt(start));
		}
		for (Integer key: targetsInt)
			targets.add(getCellAt(key));
		
	}
	
	public void bridge(int start, int steps, int begin, int prev, int intSteps) {
//		System.out.println("Start = "+ start);
//		System.out.println("Rows: "+ numRows + " Cols: "+numColumns);
//		System.out.println(adjMtx.get(5));
		calcAdjacencies();
		for (int a = 0; a < adjMtx.get(start).size(); a++) {
			if (steps == intSteps-1) {
				setSeen(begin);
			}
			if (!(seen.contains(start))){
				if (steps == 0) {
					targets.add(getCellAt(start));
					return;
				}			
				if (getCellAt(start).isDoorWay())
					targets.add(getCellAt(start));
				seen.add(start);
				bridge(adjMtx.get(start).get(a), steps-1, begin, start, intSteps);	
				seen.remove(seen.size() - 1);
				
			}			
		}		
	}
	
	public void printTargets() {
		for (Integer key : targetsInt)
			System.out.println(key);
		for (BoardCell key : targets)
			key.draw();
	}

	public Set<BoardCell> getTargets() {
		
		return targets;
	}
	
//	public static void main(String[] args) throws FileNotFoundException, BadConfigFormatException {
//		Board board = new Board();
//		
//		board.loadConfigFiles("clueboard.csv", "Legend.txt", "player.txt", "cards.txt");
//		System.out.println(board.numRows + ", " + board.numColumns);
//		board.calcAdjacencies();
//
//		
//		
//	}
}
