package clueGame;

import java.util.ArrayList;
import java.util.Random;

import clueGame.Card.CardType;

public class Player {
	private String name;
	private String color;
	private int startingLocation;
	protected ArrayList<Card> hand;
	private int location;
	private boolean hasWeaponCard;
	private boolean hasPlayerCard;
	private boolean hasRoomCard;
	
	private ArrayList<Card> cardsNotSeen;
	private ArrayList<Card> cardsSeen;
	private char lastRoomVisited;
	
	public Board board;
	private Random generator = new Random();
	
	public Player(String name, String color, int start, Board board){
		this.name=name;
		this.color=color;
		this.startingLocation=start;
		location=start;
		hand = new ArrayList<Card>();
		
		cardsSeen = new ArrayList<Card>();
		cardsNotSeen = new ArrayList<Card>();
		lastRoomVisited='W';
		this.board = board;
	}
	
	public void setDeck(ArrayList<Card> cards){
		cardsNotSeen=cards;
		System.out.println("Cards size: "+cards.size());
	}
	
	public void show(Card c) {
		//Remove the given card from the list of cardsNotSeen
		System.out.println(cardsNotSeen==null);
		for(int i=0; i<cardsNotSeen.size(); i++){
			if(c.equals(cardsNotSeen.get(i))){
				cardsNotSeen.remove(i);
				cardsSeen.add(c);
				return;
			}
		}
	}
	
	public Card disproveSuggestion(Card rCard, Card wCard, Card pCard) {
		//Randomly select card to be shown if more than one
		//Create list of conflicting cards and then randomly select one
		ArrayList<Card> conflicts = new ArrayList<Card>();
		for(Card c:hand){
			if(rCard.equals(c) || wCard.equals(c) || pCard.equals(c)){
				conflicts.add(c);
			}
		}
		for(Card c:cardsSeen){
			if(rCard.equals(c) || wCard.equals(c) || pCard.equals(c)){
				conflicts.add(c);
			}
		}
		
		//Make sure cards in hand are seen
		if(conflicts.size()==0){
			return null;
		}
		int cardIndex = generator.nextInt(conflicts.size());
		return conflicts.get(cardIndex);
		
	}
	
	//for testing
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getStartingLocation() {
		return startingLocation;
	}

	public void setStartingLocation(int startingLocation) {
		this.startingLocation = startingLocation;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public int getLocation() {
		return location;
	}
	
	public void setLocation(int location){
		this.location = location;
	}

	




	public static boolean isEqualHand(ArrayList<Card> hand1,
			ArrayList<Card> hand2) {
		
		//If hand sizes are different then we know they are not the same
		if(hand1.size() != hand2.size()){
			return false;
		}
		
		//Check every card in each hand equal to each other
		for(int i=0; i<hand2.size(); i++){
			for(int j=0; j<hand2.size(); j++){
				System.out.println("Hand1 card: "+hand1.get(i).getName()+ " Hand2 card: " + hand2.get(j).getName());
				if(hand1.get(i).equals(hand2.get(j))){
					return true;
				}
			}
		}
		return false;

	}

	public boolean hasCardType(CardType type){
		if(hand==null){
			return false;
		}
		for(Card card:hand){
			if(card.getCardType() == type){
				return true;
			}
		}
		return false;
		
	}
	
	public void addCardToHand(Card card){
		hand.add(card);
	}
	
	public ArrayList<Card> getUnseenCards(){
		return cardsNotSeen;
	}
	
	public ArrayList<Card> getSeenCards(){
		return cardsSeen;
	}
}
