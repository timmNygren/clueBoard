package PlayerTests;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import clueGame.*;
import clueGame.Card.CardType;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class GameSetupTests {
	//Declarations/variables
	public static Board board;
	public static ArrayList<ComputerPlayer> computers;
	
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		board = new Board();
		board.loadConfigFiles("clueboard.csv","Legend.txt" , "Players.txt", "Cards.txt"); 
		computers = board.getComputers();
	}
	
	
	@Test
	public void loadPeopleTest() {
		//Test Human Player
		HumanPlayer player = board.getHumanPlayer();
		assertTrue(player.getName().equals("Jean-Luc Picard"));
		assertTrue(player.getColor().equals("Black"));
		assertTrue(player.getStartingLocation() == 5);
		
		//Test Computer Player "Zeus Deuces"
		assertTrue(computers.get(0).getName().equals("Zeus Deuces"));
		assertTrue(computers.get(0).getColor().equals("Blue"));
		assertTrue(computers.get(0).getStartingLocation()==115);
		
		//Test Computer Player "Harriet Tubman"
		assertTrue(computers.get(4).getName().equals("Harriet Tubman"));
		assertTrue(computers.get(4).getColor().equals("Purple"));
		assertTrue(computers.get(4).getStartingLocation()==230);
		
	}
	
	@Test
	public void loadCardsTest() {
		//Store Cards
		ArrayList<Card> testDeck = board.getDeck();
		
		//Check Number of Cards
		assertEquals(testDeck.size(),23);
		
		//Check Correct amount of each card
		int weaponCards=0;
		int personCards=0;
		int roomCards=0;
		for(Card c:testDeck){
			if(c.getCardType()==CardType.PERSON){
				personCards++;
			}
			if(c.getCardType()==CardType.WEAPON){
				weaponCards++;
			}
			if(c.getCardType()==CardType.ROOM){
				roomCards++;
			}
		}	
		assertEquals(weaponCards,7);
		assertEquals(personCards,7);
		assertEquals(roomCards,9);
		
		//Test if deck contains specific cards
		Card testCard = new Card("Rusty Knife",CardType.WEAPON);
		boolean rustyKnifeFound = false;
		for(Card c:testDeck){
			if(c.equals(testCard)){
				rustyKnifeFound= true;
			}
		}
		assertTrue(rustyKnifeFound);
		
		
		testCard = new Card("Library",CardType.ROOM);
		boolean LibraryFound = false;
		for(Card c:testDeck){
			if(c.equals(testCard)){
				LibraryFound= true;
			}
		}
		assertTrue(LibraryFound);
		

		testCard = new Card("Zeus Deuces",CardType.PERSON);
		boolean ZeusFound = false;
		for(Card c:testDeck){
			if(c.equals(testCard)){
				ZeusFound= true;
			}
		}
		assertTrue(ZeusFound);
	}
	
	@Test
	public void dealCardsTest() {
		//Store Cards
		ArrayList<Card> testDeck = board.getDeck();
		
		
		//Make sure testDeck has cards beforehand
		assertFalse(testDeck.size()==0);
		
		//Deal Cards to Players
		board.deal();
		
		//Test if all cards have been dealt, by checking all players have 3 cards
		boolean hasThreeCards = false;
		for(ComputerPlayer cPlayer:computers){
			if(cPlayer.getHand().size()==3){
				hasThreeCards = true;
			}
			assertTrue(hasThreeCards);
			hasThreeCards=false;
		}
		assertEquals(board.getHumanPlayer().getHand().size(),3);
		
		
		//Test that all players have unique hands
		for (int i = 0; i < computers.size()-1; ++i) {
			for (int j = i + 1; j < computers.size(); ++j) {
				assertFalse(Player.isEqualHand(computers.get(i).getHand(), computers.get(j).getHand()));
			}
		}
	}
	
	

}
