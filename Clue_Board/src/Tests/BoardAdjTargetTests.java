package Tests;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTests {
	private static Board board;
	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		board = new Board();
		board.loadConfigFiles("clueboard.csv", "Legend.txt", "player.txt", "");
		board.calcAdjacencies();
	}

	// Ensure that player does not move around within room
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 0));
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(board.calcIndex(14, 22));
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(board.calcIndex(17, 3));
		Assert.assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(board.calcIndex(10, 8));
		Assert.assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(board.calcIndex(10, 2));
		Assert.assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(board.calcIndex(6, 13));
		Assert.assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(3, 3));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(3, 4)));
		// TEST DOORWAY LEFT
		testList = board.getAdjList(board.calcIndex(11, 1));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(11, 0)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(board.calcIndex(9, 15));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(10, 15)));
		//TEST DOORWAY UP
		testList = board.getAdjList(board.calcIndex(16, 17));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(15, 17)));
		
	}

	// Test a variety of walkway scenarios
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just one walkway piece
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 12));
		Assert.assertTrue(testList.contains(board.calcIndex(1, 12)));
		Assert.assertEquals(1, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(board.calcIndex(6, 0));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 1)));
		Assert.assertTrue(testList.contains(board.calcIndex(7, 0)));
		Assert.assertEquals(3, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(board.calcIndex(5, 18));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 18)));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 19)));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 17)));
		Assert.assertEquals(3, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(board.calcIndex(5,5));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 5)));
		Assert.assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(board.calcIndex(19, 6));
		Assert.assertTrue(testList.contains(board.calcIndex(18, 6)));
		Assert.assertEquals(1, testList.size());
		
		// Test on ridge edge of board, next to 1 room piece
		testList = board.getAdjList(board.calcIndex(5, 22));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 21)));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 22)));
		Assert.assertEquals(2, testList.size());

	}

	// Test adjacency at entrance to rooms
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(4, 4));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 3)));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 4)));
		Assert.assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(board.calcIndex(10, 14));
		Assert.assertTrue(testList.contains(board.calcIndex(9, 14)));
		Assert.assertTrue(testList.contains(board.calcIndex(11, 14)));
		Assert.assertTrue(testList.contains(board.calcIndex(10, 13)));
		Assert.assertTrue(testList.contains(board.calcIndex(10, 15)));
		Assert.assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(board.calcIndex(10, 0));
		Assert.assertTrue(testList.contains(board.calcIndex(9, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(11, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(10, 1)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(board.calcIndex(14, 0));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 1)));
		Assert.assertEquals(3, testList.size());
	}

	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(board.calcIndex(6, 0), 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 0))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 0))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 1))));	
		
		board.calcTargets(board.calcIndex(5, 5), 1);
		targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 6))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 4))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 5))));	
	}
	// Tests of just walkways, 2 steps
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(board.calcIndex(5, 22), 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 21))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 20))));
		
		board.calcTargets(board.calcIndex(0, 12), 2);
		targets= board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 12))));
			
	}
	// Tests of just walkways, 4 steps
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(board.calcIndex(19, 6), 4);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 6))));

		
		// Includes a path that doesn't have enough length
		board.calcTargets(board.calcIndex(6, 0), 4);
		targets= board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 0))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 1))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 3))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 4))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 0))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 2))));
	}	
	// Tests of just walkways plus one door, 6 steps
	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(board.calcIndex(19, 6), 6);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 4))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 5))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 7))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 8))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 6))));
	}	
	
	// Test getting into a room
	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(board.calcIndex(11, 15), 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 16))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 14))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 13))));

	}
	
	// Test getting into room, doesn't require all steps
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(board.calcIndex(11, 0), 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 0))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 1))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 0))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 1))));
	}

	// Test getting out of a room
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(board.calcIndex(16, 16), 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 16))));
		// Take two steps
		board.calcTargets(board.calcIndex(16, 16), 2);
		targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 17))));

	}

}
