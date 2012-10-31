package clueGame;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import clueGame.Card.CardType;

public class ComputerPlayer extends Player {
	private ArrayList<Card> cardsNotSeen;
	private ArrayList<Card> cardsSeen;
	private char lastRoomVisited;
	private Suggestion playerSuggestion;
	public Object lastRoom;
	private Card roomCard;
	private char roomInitial;
	
	private Random generator = new Random();
	
	public Board board;
	
	public ComputerPlayer(String name, String color, int start, Board board) throws FileNotFoundException, BadConfigFormatException {
		super(name,color,start, board);
		cardsNotSeen = super.getUnseenCards();
		cardsSeen = super.getSeenCards();
		this.board = board;
		//initialized notSeen with every card
	}

	
	
	public char getLastRoom(){
		return lastRoomVisited;
	}
//	public void show(Card c) {
//		//Remove the given card from the list of cardsNotSeen
//		System.out.println(cardsNotSeen==null);
//		for(int i=0; i<cardsNotSeen.size(); i++){
//			if(c.equals(cardsNotSeen.get(i))){
//				cardsNotSeen.remove(i);
//				cardsSeen.add(c);
//				return;
//			}
//		}
//	}
	public BoardCell pickLocation(Set<BoardCell> targets) {
		//Convert the set to a list so that it is easier to work with
		ArrayList<BoardCell> targetList = new ArrayList<BoardCell>();
		targetList.addAll(targets);

		
		//Iterate through
		for(BoardCell b:targetList){
			if(b.isDoorWay() && ((RoomCell)b).getInitial() != lastRoomVisited){
				setLocation(b.getLocation());
				return b;
			}
		}
		
		//If no doors are found pick a random target
		setLocation(targetList.get(generator.nextInt(targets.size())).getLocation());
		return targetList.get(generator.nextInt(targets.size()));
		
		
	}
	
	public Suggestion createSuggestion(RoomCell room) {
		cardsNotSeen = this.getUnseenCards();
		cardsSeen = this.getSeenCards();
		System.out.println("Create Suggestion has received RoomCell: "+room.getInitial());
		System.out.println("CardsSeen:");
		printCards(cardsSeen);
		System.out.println("CardsNotSeen");
		printCards(cardsNotSeen);
		//Make a suggestion from the cards from not Seen, choose one of each type
		//First update seen vs. notSeen
		
		//Make sure there are enough cards of each type to make a suggestion
		int people=0;
		int weapons=0;
		System.out.println("Cards not seen size: "+cardsNotSeen==null);
		for(Card card:cardsNotSeen){
			if(card.getCardType() == CardType.PERSON){
				people++;
			}
			if(card.getCardType() == CardType.WEAPON){
				weapons++;
			}
		}
		
		if(!(people >= 1 && weapons >= 1)){
			System.out.println("Not enough information to create suggestion");
			System.out.println(cardsNotSeen.size());
			return null;
		}
		
		//At this point we should have at least one card of each type
		ArrayList<Card> suggestedCards = new ArrayList<Card>();
		boolean hasPerson=false;
		int personIndex=-1;
		boolean hasWeapon=false;
		int weaponIndex=-1;
		int randomIndex = generator.nextInt( cardsNotSeen.size() );
		
		while(suggestedCards.size()<2){
			if(hasPerson==false && cardsNotSeen.get(randomIndex).getCardType()==CardType.PERSON){
				suggestedCards.add(cardsNotSeen.get(randomIndex));
				personIndex=suggestedCards.size()-1;
				hasPerson=true;
			} else if(hasWeapon==false && cardsNotSeen.get(randomIndex).getCardType()==CardType.WEAPON){
				suggestedCards.add(cardsNotSeen.get(randomIndex));
				weaponIndex=suggestedCards.size()-1;
				hasWeapon=true;
			}
			randomIndex = generator.nextInt( cardsNotSeen.size() );
		}
		
		System.out.println("Confirm room name: "+board.getRooms().get(room.getInitial()));
		roomCard = new Card(board.getRooms().get(room.getInitial()), CardType.ROOM);
		System.out.println("Confirm roomCard "+roomCard.getName());
		System.out.println("board.getRooms(): "+board.getRooms().get(room.getInitial()));
		
		playerSuggestion = new Suggestion(suggestedCards.get(personIndex),suggestedCards.get(weaponIndex),roomCard);

		
		return playerSuggestion;
		
	}
	
	public Suggestion getSuggestion(){
		System.out.println("HERE");
//		System.out.println("Person: "+playerSuggestion.person.getName());
//		System.out.println("Room: "+playerSuggestion.room.getName());
//		System.out.println("Weapon: "+playerSuggestion.weapon.getName());
		return playerSuggestion;
	}
	
//	public void updateSeen(Card seen) {
//		show(seen);
//		cardsSeen.add(seen);
//	}
	

	
	public void setLastVisited(BoardCell cell) {
		if (cell instanceof RoomCell) {
			
		}
	}

	public void setSuggestion(Card cPetersonCard, Card fakeCard, Card fakeCard2) {
		// TODO Auto-generated method stub
		
	}

	
	public void setRoomCard(Card rc){
		roomCard = rc;
	}
	
	public void printCards(ArrayList<Card> cardsToPrint){
		System.out.println("Printing Cards");
		for(Card c:cardsToPrint){
			System.out.println("Card: " + c.getName() + " Type: " + c.getCardType().toString());
		}
		System.out.println("End Printing Cards");
	}
	
}
