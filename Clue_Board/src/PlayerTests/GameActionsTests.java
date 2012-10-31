package PlayerTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;
import clueGame.Card.CardType;

public class GameActionsTests {
	//declarations/variables
	public static Board board;
	public static ArrayList<ComputerPlayer> computers;
	public static  ArrayList<Card> cards;
	public static Suggestion suggestion;
	public static ComputerPlayer testPlayer;
	public static Card fakeCard;
	public static Card syringeCard;
	public static Card pSHCard;
	public static Card cPetersonCard;
	public static Card kitchenCard;
	public static Card conservatoryCard;
	public static Card ballRoomCard;
	public static Card feralMonkeyCard;
	public static Card majicard;
	public static Card barackCard;
	public static Card steveCard;
	public static Card studyCard;
	public static Card kidneyCard;
	public static Card bananaCard;
	public static Card loungeCard;
	public static Card personCard;
	public static Card libraryCard;
	public static ArrayList<Card> cardsCopy;
	
	
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		board = new Board();
		board.loadConfigFiles("clueboard.csv","Legend.txt" , "Players.txt", "Cards.txt");
		testPlayer = new ComputerPlayer(null,null,187,board);
		suggestion = new Suggestion(cPetersonCard, majicard, kitchenCard);
		cards = new ArrayList<Card>();
		computers = new ArrayList<ComputerPlayer>();
		fakeCard = new Card("fake",CardType.PERSON);
		personCard = new Card("NAME HERE",CardType.PERSON);
		
		
		// these cards are static variables
		libraryCard = new Card("Library", CardType.ROOM);
		conservatoryCard = new Card("Conservatory",CardType.ROOM);
		pSHCard = new Card("Philip Seymour Hoffman", Card.CardType.PERSON);
		cards.add(pSHCard);
		cPetersonCard = new Card("Cricket Peterson", Card.CardType.PERSON);
		cards.add(cPetersonCard);
		barackCard = new Card("Barack Obama", Card.CardType.PERSON);
		cards.add(barackCard);
		steveCard = new Card("Steve", Card.CardType.PERSON);
		cards.add(steveCard);
		kitchenCard = new Card("Kitchen", Card.CardType.ROOM);
		cards.add(kitchenCard);
		ballRoomCard = new Card("Ballroom", Card.CardType.ROOM);
		cards.add(ballRoomCard);
		studyCard = new Card("Study", Card.CardType.ROOM);
		cards.add(studyCard);
		loungeCard = new Card("Lounge", Card.CardType.ROOM);
		cards.add(loungeCard);
		syringeCard = new Card("Syringe full of malaria", Card.CardType.WEAPON);
		cards.add(syringeCard);
		feralMonkeyCard = new Card("Feral Monkey", Card.CardType.WEAPON);
		cards.add(feralMonkeyCard);
		bananaCard = new Card("Slippery Banana Peel", Card.CardType.WEAPON);
		cards.add(bananaCard);
		kidneyCard = new Card("Kidneys sold on black market", Card.CardType.WEAPON);
		cards.add(kidneyCard);
		majicard = new Card("Magikarp", Card.CardType.WEAPON);
		cards.add(majicard);
		
		ArrayList<Card> testHand = new ArrayList<Card>();
		testHand.add(cPetersonCard);
		testHand.add(majicard);
		testHand.add(kitchenCard);
		testPlayer.setHand(testHand);
		ArrayList<Card> cardsCopy = (ArrayList<Card>) cards.clone();
		testPlayer.setDeck(cardsCopy);
		
	}
	
	
	@Test
	public void accusationTest() {
		//Show player all relevant cards
		cardsCopy = (ArrayList<Card>) cards.clone();
		//testPlayer.setDeck(cardsCopy);
		System.out.println("size: "+cardsCopy.size());
		System.out.println();
		System.out.println("-----------------");
		for(Card c:cardsCopy){
			System.out.println("c: "+c==null);
			if(( !(c.equals(cPetersonCard) || c.equals(majicard) || c.equals(kitchenCard)))){
				
				testPlayer.show(c);
				System.out.println("Showing card: "+c.getName());
			}
		}
		
		System.out.println("Location:" +testPlayer.getLocation() );
		System.out.println(board.getCellAt(testPlayer.getLocation()).isRoom());
		//createSuggestion gets players current room and returns a suggestion
		Suggestion testSolution = testPlayer.createSuggestion((RoomCell)board.getCellAt(testPlayer.getLocation()));
		
		
		System.out.println("---------------INHERITANCE-----------------------");
		System.out.println("Person: "+testSolution.person.getName());
		System.out.println("Weapon: "+testSolution.weapon.getName());
		System.out.println("Room: "+testSolution.room.getName());
		
		//Assert that the accusation is true
		suggestion = new Suggestion(cPetersonCard, majicard, conservatoryCard);
		assertTrue(testSolution.equals(suggestion));
	}
	
	
	@Test
	public void selectLocationTest() throws FileNotFoundException, BadConfigFormatException {
		ComputerPlayer player = new ComputerPlayer(null, null, 0,board);
		
		// Pick a location with no rooms in target, just four targets
		board.calcTargets(149, 1);
		
		int loc_148Tot = 0;
		int loc_126Tot = 0;
		int loc_150Tot = 0;
		int loc_172Tot = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(148)){
				loc_148Tot++;
			}else if (selected == board.getCellAt(126)){
				loc_126Tot++;
			}else if (selected == board.getCellAt(150)){
				loc_150Tot++;
			}else if (selected == board.getCellAt(172)){
				loc_172Tot++;
			}else{
				System.out.println("Selected = "+selected.isRoom());
				fail("Invalid target selected");
			}
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, loc_148Tot + loc_126Tot + loc_150Tot + loc_172Tot);
		
		// Ensure each target was selected more than once
		System.out.println("149: "+loc_148Tot);
		System.out.println("127: "+loc_126Tot);
		System.out.println("151: "+loc_150Tot);
		System.out.println("173: "+loc_172Tot);
		

		assertTrue(loc_148Tot > 10);
		assertTrue(loc_126Tot > 10);
		assertTrue(loc_150Tot > 10);
		assertTrue(loc_172Tot > 10);

		
		//Test room preference
		board.calcTargets(96,1);
		for(int i=0; i<100; i++){
			RoomCell selected = (RoomCell) player.pickLocation(board.getTargets());
			assertTrue(selected.isDoorWay());
			assertFalse(player.getLastRoom()==selected.getInitial());
		}
		
		
	}
	
	@Test
	public void disproveSuggestionTest() throws FileNotFoundException, BadConfigFormatException {
		//Test for one player, one correct match
		
		ComputerPlayer testPlayer1 = new ComputerPlayer(null, null, 0,board);
		ArrayList<Card> player1hand = new ArrayList<Card>();
		ArrayList<Card> player2hand = new ArrayList<Card>();
		ArrayList<Card> player3hand = new ArrayList<Card>();
		ArrayList<Card> humanHand = new ArrayList<Card>();
		
		player1hand.add(pSHCard);
		player1hand.add(cPetersonCard);
		player1hand.add(kitchenCard);
		player1hand.add(ballRoomCard);
		player1hand.add(syringeCard);
		player1hand.add(feralMonkeyCard);
		testPlayer1.setHand(player1hand);
		assertTrue(testPlayer1.disproveSuggestion(cPetersonCard, loungeCard, bananaCard).equals(cPetersonCard));
		assertTrue(testPlayer1.disproveSuggestion(barackCard, kitchenCard, bananaCard).equals(kitchenCard));
		assertTrue(testPlayer1.disproveSuggestion(barackCard, loungeCard, feralMonkeyCard).equals(feralMonkeyCard));
		assertTrue(testPlayer1.disproveSuggestion(barackCard, loungeCard, bananaCard) == null);

		//Test for one player, multiple possible matches
		boolean pshCardReturned=false;
		boolean kitchenCardReturned=false;
		boolean syringeCardReturned=false;
		boolean wrongCardReturned = false;
		
		player1hand.add(syringeCard);
		testPlayer1.setLocation(187);
		for(int i=0; i<100; i++){
			Card testCard = testPlayer1.disproveSuggestion(pSHCard, kitchenCard, syringeCard);
			if(testCard.equals(pSHCard)){
				pshCardReturned=true;
			}else
			if(testCard.equals(kitchenCard)){
				kitchenCardReturned=true;
			}else
			if(testCard.equals(syringeCard)){
				syringeCardReturned=true;
			}else{
				System.out.println("Card: "+testCard.getName());
				wrongCardReturned = true;
			}
		}
		
		assertTrue(pshCardReturned);
		assertTrue(kitchenCardReturned);
		assertTrue(syringeCardReturned);
		assertFalse(wrongCardReturned);
		
		//Test that all players are queried
		//Make test players
		HumanPlayer human = new HumanPlayer(null, null, 0, board);
		ComputerPlayer cPlayer1 = new ComputerPlayer(null, null, 0, board);
		ComputerPlayer cPlayer2 = new ComputerPlayer(null, null, 0, board);
		ComputerPlayer cPlayer3 = new ComputerPlayer(null, null, 0, board);
		computers.add(cPlayer1);
		computers.add(cPlayer2);
		computers.add(cPlayer3);
		//Give players their hands
		ArrayList<Card> hand = new ArrayList<Card>();
		player1hand.add(pSHCard);
		player1hand.add(kidneyCard);
		player1hand.add(kitchenCard);
		cPlayer1.setHand(player1hand);
		player2hand.add(ballRoomCard);
		player2hand.add(syringeCard);
		player2hand.add(barackCard);
		cPlayer2.setHand(player2hand);
		player3hand.add(studyCard);
		player3hand.add(steveCard);
		player3hand.add(feralMonkeyCard);
		cPlayer3.setHand(player3hand);
		humanHand.add(cPetersonCard);
		humanHand.add(syringeCard);
		humanHand.add(feralMonkeyCard);
		humanHand.add(personCard);
		human.setHand(humanHand);
		
		
		//made a suggestion which no players could disprove, and ensured that null was returned
		boolean nullReturned=true;
		for(int i=0; i<computers.size(); i++){
			if(computers.get(i).disproveSuggestion(fakeCard, fakeCard, fakeCard)!=null){
				nullReturned=false;
				break;
			}
		}
		
		assertTrue(nullReturned);
		

		//made a suggestion that only the human could disprove, and ensured that the correct Card was returned.
		nullReturned=true;
		for(int i=0; i<computers.size(); i++){
			System.out.println("------------------------------------");
			if(computers.get(i).disproveSuggestion(personCard, fakeCard, fakeCard)!=null){
				
				System.out.println("Index of disaproval: "+i);
				System.out.println("Number of computers "+ computers.size());
				System.out.println("Evidence:" + computers.get(i).disproveSuggestion(personCard, fakeCard, fakeCard).getName());
				nullReturned=false;
			}
		}
		assertTrue(human.disproveSuggestion(personCard, fakeCard, fakeCard).equals(personCard));
		assertTrue(nullReturned);
		
		
		
		//The person who made the suggestion was the only one who could disprove it, null was returned
		computers.get(0).setSuggestion(cPetersonCard, fakeCard, fakeCard);
		for(int i=1; i<computers.size(); i++){
			if(computers.get(i).disproveSuggestion(cPetersonCard, fakeCard, fakeCard)!=null){
				nullReturned=false;
			}
		}
		
		//To ensure that the players are not queried in the same order each time
		player2hand.clear();
		player2hand.add(ballRoomCard);
		player2hand.add(syringeCard);
		player2hand.add(barackCard);
		cPlayer2.setHand(player2hand);
		player3hand.clear();
		player3hand.add(studyCard);
		player3hand.add(barackCard);
		player3hand.add(feralMonkeyCard);
		cPlayer3.setHand(player3hand);
		computers.get(0).setSuggestion(barackCard, kitchenCard, majicard);
		boolean c1Chosen =false;
		boolean c2Chosen =false;
		boolean otherChosen =false;
		for(int j=0; j<100; j++){
			for(int i=1; i<computers.size(); i++){
				if(computers.get(i).disproveSuggestion(barackCard, kitchenCard, majicard)!=null){
					if(i==1){
						c1Chosen=true;
					}
					if(i==2){
						c2Chosen=true;
					}else{
						otherChosen = true;
					}
				}
			}
		}
		assertTrue(c1Chosen);
		assertTrue(c2Chosen);
		assertTrue(otherChosen);
	}
	
	
	@Test
	public void makeSuggestionTest() throws FileNotFoundException, BadConfigFormatException {
		ComputerPlayer computer = new ComputerPlayer(null, null, 0, board);
		Suggestion computerSuggestion;
		cardsCopy = (ArrayList<Card>) cards.clone();
		computer.setDeck(cards);
		
		
		System.out.println("Seen size before:" + computer.getSeenCards().size());
		
		computer.setLocation(72);
		computer.show(pSHCard);
		computer.show(cPetersonCard);
		computer.show(steveCard);
		computer.show(kidneyCard);
		computer.show(bananaCard);
		computer.show(feralMonkeyCard);
		computer.show(majicard);
		System.out.println("Seen size after:" + computer.getSeenCards().size());

		System.out.println("----------------------------------------------------------------");
		computerSuggestion = computer.createSuggestion((RoomCell)board.getCellAt(computer.getLocation()));
		System.out.println("Person:" + computerSuggestion==null);
		
		
		assertTrue(barackCard.equals(computerSuggestion.person));
		assertTrue(syringeCard.equals(computerSuggestion.weapon));
		System.out.println("Computer room! "+computerSuggestion.room.getName());
		assertTrue(libraryCard.equals(computerSuggestion.room));

	}
	
}
