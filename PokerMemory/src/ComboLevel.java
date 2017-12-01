import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ComboLevel extends FlushLevel{
	int [] cardsRankArray = new int [5];
	String [] cardsSuitsArray = new String [5];
	boolean cardsFormStraight = false;
	boolean cardsFormFlush = false; 
	boolean cardsFormFullHouse = false;
	
	
	protected ComboLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Combo Level");
		this.setCardsToTurnUp(5);
		this.setCardsPerRow(10);
		this.setRowsPerGrid(5);
	}

		@Override
		protected void makeDeck() {
			

			//back card
			ImageIcon backIcon = this.getCardIcons()[this.getTotalCardsPerDeck()];

			int cardsToAdd[] = new int[getRowsPerGrid() * getCardsPerRow()];
			for(int i = 0; i < (getRowsPerGrid() * getCardsPerRow()); i++)
			{
				cardsToAdd[i] = i;
			}

			// randomize the order of the deck
			this.randomizeIntArray(cardsToAdd);

			// make each card object
			for(int i = 0; i < cardsToAdd.length; i++)
			{
				// number of the card, randomized
				int num = cardsToAdd[i];
				// make the card object and add it to the panel
				String rank = cardNames[num].substring(0, 1);
				String suit = cardNames[num].substring(1, 2);
				this.getGrid().add( new Card(this, this.getCardIcons()[num], backIcon, num, rank, suit));
			}
		}

		
		
		@Override
		protected boolean turnUp(Card card) {
			// the card may be turned
			if(this.getTurnedCardsBuffer().size() < getCardsToTurnUp()) 
			{
				// add the card to the list
				this.getTurnedCardsBuffer().add(card);
				if(this.getTurnedCardsBuffer().size() == getCardsToTurnUp())
				{
					// We are uncovering the last card in this turn
					// Record the player's turn
					this.getTurnsTakenCounter().increment();
					
					//turned card's suits are stored in an array of strings
					for(int i = 0; i<getTurnedCardsBuffer().size();i++)
					{
						cardsSuitsArray[i] = this.getTurnedCardsBuffer().get(i).getSuit();
					}
					
					Card otherCard1 = (Card) this.getTurnedCardsBuffer().get(0);
					Card otherCard2 = (Card) this.getTurnedCardsBuffer().get(1);
					Card otherCard3 = (Card) this.getTurnedCardsBuffer().get(2);
					Card otherCard4 = (Card) this.getTurnedCardsBuffer().get(3);
					Card otherCard5 = (Card) this.getTurnedCardsBuffer().get(4);
					
					//compare strings to see if the suits match up; if they do, cards form a straight
					if((card.getSuit().equals(otherCard1.getSuit())) && (card.getSuit().equals(otherCard2.getSuit())) && (card.getSuit().equals(otherCard3.getSuit())) && (card.getSuit().equals(otherCard4.getSuit())) && (card.getSuit().equals(otherCard5.getSuit()))){
						cardsFormFlush = true;
					}else {
						cardsFormFlush = false;
					}
				
				
					/*if((card.getSuit().equals(otherCard1.getSuit())) && (card.getSuit().equals(otherCard2.getSuit())) && (card.getSuit().equals(otherCard3.getSuit())) && (card.getSuit().equals(otherCard4.getSuit())) && (card.getSuit().equals(otherCard5.getSuit()))){
						
						cardsFormFlush = true;
						
					}
					else
					{
						// The cards do not match, so start the timer to turn them down
						cardsFormFlush = false;
					}*/
					
//					if(cardsFormFlush==true) {
//						// Five cards match, so remove them from the list (they will remain face up)
//						this.getTurnedCardsBuffer().clear();
//					}
//					else
//					{
//						// The cards do not match, so start the timer to turn them down
//						this.getTurnDownTimer().start();
//					
//					}
	
					//turn ranks from string to int and store them in an array of ints
					for(int i = 0; i<this.getTurnedCardsBuffer().size();i++) {
						switch (this.getTurnedCardsBuffer().get(i).getRank()) {
						case "2": cardsRankArray[i] = 2;
						break;
						case "3": cardsRankArray[i] = 3;
						break;
						case "4": cardsRankArray[i] = 4;
						break;
						case "5": cardsRankArray[i] = 5;
						break;
						case "6": cardsRankArray[i] = 6;
						break;
						case "7": cardsRankArray[i] = 7;
						break;
						case "8": cardsRankArray[i] = 8;
						break;
						case "9": cardsRankArray[i] = 9;
						break;
						case "t": cardsRankArray[i] = 10;
						break;
						case "j": cardsRankArray[i] = 11;
						break;
						case "q": cardsRankArray[i] = 12;
						break;
						case "k": cardsRankArray[i] = 13;
						break;
						case "a": cardsRankArray[i] = 14;
						break;
						default: break;
						}
					}
					
					//Sort Array of int Ranks in ascending order
					Arrays.sort(cardsRankArray);		
					
					//Check if they are in sequence (ex. 3,4,5,6,7) by comparing each value to the value in first positon of array
					if(cardsRankArray[0]==(cardsRankArray[1])-1 && cardsRankArray[0]==(cardsRankArray[2])-2 && cardsRankArray[0]==(cardsRankArray[3])-3 && cardsRankArray[0]==(cardsRankArray[4])-4) {
							cardsFormStraight = true;
						}else {
							cardsFormStraight = false;
					}
					
					//Check if cards form a full house (trio and a pair) ex. 4,4,4,7,7 OR 4,4,7,7,7
					if(((cardsRankArray[0]==cardsRankArray[1])&&(cardsRankArray[0]==cardsRankArray[2])&&(cardsRankArray[3]==cardsRankArray[4]))||((cardsRankArray[0]==cardsRankArray[1])&&(cardsRankArray[2]==cardsRankArray[3])&&(cardsRankArray[2]==cardsRankArray[4])) ) {
						cardsFormFullHouse = true;
					}else {
						cardsFormFullHouse = false;
					}
					
					//If the cards are a straight OR a flush OR a fullHouse, leave them up
					if(cardsFormStraight||cardsFormFlush||cardsFormFullHouse) {
						this.getTurnedCardsBuffer().clear();
					}else {
						this.getTurnDownTimer().start();
					}
					
					//Object [] possibleHands = {"Straight","Flush","Full House","Pass"};
					
					//if(cardsFormStraight || cardsFormFlush || cardsFormFullHouse) {
					//	JOptionPane.showMessageDialog(getMainFrame(), "Select One:", "Possible Winning Hands", JOptionPane.INFORMATION_MESSAGE,null,possibleHands,possibleHands[0]);
					//}
					
				}
				return true;
				
			}
					return false;
					
			}
	}