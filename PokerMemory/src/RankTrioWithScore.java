/**
 * Stores currently turned cards, allows only three cards to be uncovered on each turn
 * Also handles turning cards back down after a delay if cards have different ranks
 *
 * @author Michael Leonhard (Original Author)
 * @author Modified by Bienvenido Vélez (UPRM)
 * @version Sept 2017
 */

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class RankTrioWithScore extends EqualPairLevel {
	
	MemoryFrameWithScore memoryFrame = new MemoryFrameWithScore();
	long score = 0;
	int [] cardsArray = new int [3];
	long rankSum = 0;
	int handsCounter = 0;

	// TRIO LEVEL: The goal is to find, on each turn, three cards with the same rank

	protected RankTrioWithScore(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Trio Level");
		this.setCardsToTurnUp(3);
		this.setCardsPerRow(10);
		this.setRowsPerGrid(5);
	}

	@Override
	protected void makeDeck() {
		// In Trio level the grid consists of distinct cards, no repetitions

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
				
				//Turn Ranks from Strings to int.  Store Ranks in an array
				for(int i = 0; i < this.getTurnedCardsBuffer().size();i++) {
					switch (this.getTurnedCardsBuffer().get(i).getRank()) {
					case "2": cardsArray[i] = 2;
					break;
					case "3": cardsArray[i] = 3;
					break;
					case "4": cardsArray[i] = 4;
					break;
					case "5": cardsArray[i] = 5;
					break;
					case "6": cardsArray[i] = 6;
					break;
					case "7": cardsArray[i] = 7;
					break;
					case "8": cardsArray[i] = 8;
					break;
					case "9": cardsArray[i] = 9;
					break;
					case "t": cardsArray[i] = 10;
					break;
					case "j": cardsArray[i] = 11;
					break;
					case "q": cardsArray[i] = 12;
					break;
					case "k": cardsArray[i] = 13;
					break;
					case "a": cardsArray[i] = 20;
					break;
					default: break;
					}
				}
				
				//Calculate Sum of Ranks
				for(int i= 0; i<cardsArray.length;i++) {
					rankSum = rankSum + cardsArray[i];
				}
				
				// get the other card (which was already turned up)
				Card otherCard1 = (Card) this.getTurnedCardsBuffer().get(0);
				Card otherCard2 = (Card) this.getTurnedCardsBuffer().get(1);
				if((card.getRank().equals(otherCard1.getRank())) && (card.getRank().equals(otherCard2.getRank()))) {
					// Three cards match, so remove them from the list (they will remain face up).  Add 100 + sum of ranks
					score += 100 + rankSum;
					getMainFrame().setScore(score);
					this.getTurnedCardsBuffer().clear();
					handsCounter++;
				}
				else
				{
					// The cards do not match, so start the timer to turn them down.  Set reset rankSum to 0.  Remove 5 points.
					this.getTurnDownTimer().start();
					rankSum = 0;
					score -= 5;
					getMainFrame().setScore(score);
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	protected boolean  isGameOver(){

		//hay un maximo de 12 trios.  cuando se llega a 12, game over.
		if(handsCounter==12) {
			return true;
		}else {
			return false;
		}
		
	}
	
}
