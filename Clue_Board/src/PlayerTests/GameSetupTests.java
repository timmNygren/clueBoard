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
	public static ArrayList<Player> computers = board.getComputers();
	
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		board = new Board();
		board.loadConfigFiles("clueboard.csv","Legend.txt" , "player.txt", "cards.txt"); 
	}
	
	
	@Test
	public void loadPeopleTest() {
		//Test Human Player
		HumanPlayer player = board.getHumanPlayer();
		assertTrue(player.getName().equals("Jean-Luc"));
		assertTrue(player.getColor().equals("Black"));
		assertTrue(player.getStartingLocation() == 12);
		
		//Test Computer Player "Zeus Deuces"
		assertTrue(computers.get(0).getName().equals("Zeus Deuces"));
		assertTrue(computers.get(0).getColor().equals("Red"));
		assertTrue(computers.get(0).getStartingLocation()==137);
		
		//Test Computer Player "Jesus F."
		assertTrue(computers.get(4).getName().equals("Jesus F."));
		assertTrue(computers.get(4).getColor().equals("Blue"));
		assertTrue(computers.get(4).getStartingLocation()==443);
		
	}
	
	@Test
	public void loadCardsTest() {
		//Store Cards
		ArrayList<Card> testDeck = board.getDeck();
		
		//Check Number of Cards
		assertTrue(testDeck.size()==21);
		
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
		assertEquals(roomCards,7);
		
		//Test if deck contains specific cards
		Card testCard = new Card("Rusty Knife",CardType.WEAPON);
		assertTrue(testDeck.contains(testCard));
		testCard = new Card("Library",CardType.ROOM);
		assertTrue(testDeck.contains(testCard));
		testCard = new Card("Zeus Deuces",CardType.PERSON);
		assertTrue(testDeck.contains(testCard));
	}
	
	@Test
	public void dealCardsTest() {
		//Store Cards
		ArrayList<Card> testDeck = board.getDeck();
		
		
				
		//Deal Cards to Players
		board.deal();
		
		//Test if all cards have been dealt
		assertEquals(testDeck.size(),0);
		
		//Test if all players have the same amount of cards
		for(Player cPlayer:computers){
			assertEquals(cPlayer.getHand().size(),3);
		}
		
		//Test that all players have unique hands
		for (int i = 0; i < computers.size()-1; ++i) {
			for (int j = i + 1; j < computers.size(); ++j) {
				assertEquals(Player.isEqualHand(computers.get(i).getHand(), computers.get(j).getHand()),false);
			}
		}
	}
	
	

}
