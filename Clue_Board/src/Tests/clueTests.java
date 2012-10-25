package Tests;

// Doing a static import allows me to write assertEquals rather than
// Assert.assertEquals
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.RoomCell;

public class clueTests {
	// I made this static because I only want to set it up one 
	// time (using @BeforeClass), no need to do setup before each test
	private static Board board;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 20;
	public static final int NUM_COLUMNS = 23;
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		board = new Board();
		board.loadConfigFiles("clueboard.csv", "Legend.txt");
		
	}
	@Test
	public void testRoomsSize() {
		Map<Character, String> rooms = board.getRooms();
		assertEquals(NUM_ROOMS, rooms.size());
	}
	@Test
	public void testRoomChars() {
		Map<Character, String> rooms = board.getRooms();
		assertEquals("Conservatory", rooms.get('C'));
		assertEquals("Ballroom", rooms.get('B'));
		assertEquals("Billiard room", rooms.get('R'));
		assertEquals("Dining room", rooms.get('D'));
		assertEquals("Walkway", rooms.get('W'));
		assertEquals("Kitchen", rooms.get('K'));
		assertEquals("Library", rooms.get('L'));
		assertEquals("Study", rooms.get('S'));
		assertEquals("Lounge", rooms.get('O'));
		assertEquals("Hall", rooms.get('H'));
		assertEquals("Closet", rooms.get('X'));
		
	}
	
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	

	@Test
	public void testDoorDirections() {
		RoomCell room = board.getRoomCellAt(4, 3);
		assertTrue(room.isDoorWay());		
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
		
		room = board.getRoomCellAt(4, 8);
		assertTrue(room.isDoorWay());		
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		
		room = board.getRoomCellAt(10, 1);
		assertTrue(room.isDoorWay());		
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
		
		room = board.getRoomCellAt(15, 1);
		assertTrue(room.isDoorWay());			
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());	
		
		room = board.getRoomCellAt(2 , 2);		
		assertFalse(room.isDoorWay());
			

	}

	@Test
	public void testRoomInitials() {
		assertEquals('C', board.getRoomCellAt(10, 2).getInitial());
		assertEquals('R', board.getRoomCellAt(14, 20).getInitial());
		assertEquals('B', board.getRoomCellAt(18, 3).getInitial());
		assertEquals('O', board.getRoomCellAt(11, 9).getInitial());
		assertEquals('K', board.getRoomCellAt(3, 10).getInitial());
		assertEquals('D', board.getRoomCellAt(7, 17).getInitial());
		assertEquals('L', board.getRoomCellAt(3, 2).getInitial());
		assertEquals('S', board.getRoomCellAt(19, 17).getInitial());
		assertEquals('H', board.getRoomCellAt(2, 20).getInitial());
		assertEquals('X', board.getRoomCellAt(19, 10).getInitial());
	}
	
	@Test
	public void testCalcIndex() {
		assertEquals(0, board.calcIndex(0, 0));
		assertEquals(50, board.calcIndex(2, 4));
		assertEquals(24, board.calcIndex(1, 1));
		assertEquals(435, board.calcIndex(NUM_ROWS-2, NUM_COLUMNS-2));
		assertEquals(376, board.calcIndex(16, 8));
		assertEquals(66, board.calcIndex(2, 20));		
	}
	

}