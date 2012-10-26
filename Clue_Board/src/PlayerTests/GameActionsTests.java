package PlayerTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;

public class GameActionsTests {
	//declarations/variables
	public static Board board;
	public static ArrayList<ComputerPlayer> computers = board.getComputers();
	public static Solution solution;
	
	
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		board = new Board();
		board.loadConfigFiles("clueboard.csv","Legend.txt" , "player.txt", "cards.txt"); 
		solution = new Solution("Cricket Peterson", "Herring", "Kitchen");
		
	}
	
	
	@Test
	public void accusationTest() {
		computers.get(0).createSuggestion();
		Solution testSolution = computers.get(0).getSuggestion();
		
		//Assert that the accusation is true
		assertTrue(testSolution.equals(solution));
	}
	
	@Test
	public void selectLocationTest() {
		//Test random preference
		ComputerPlayer player = new ComputerPlayer();
		
		// Pick a location with no rooms in target, just three targets
		board.calcTargets(360, 2);
		int loc_358Tot = 0;
		int loc_382Tot = 0;
		int loc_362Tot = 0;
		int loc_384Tot = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = board.getCellAt(player.pickLocation(board.getTargets()));
			if (selected == board.getCellAt(358))
				loc_358Tot++;
			else if (selected == board.getCellAt(382))
				loc_382Tot++;
			else if (selected == board.getCellAt(362))
				loc_362Tot++;
			else if (selected == board.getCellAt(384))
				loc_384Tot++;
			else
				fail("Invalid target selected");
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, loc_384Tot);
		
		
		//Test Randomness of locations chosen
		
	}
	
	@Test
	public void disproveSuggestionTest() {
		//stub
	}
	
	@Test
	public void makeSuggestionTest() {
		//stub
	}

}
