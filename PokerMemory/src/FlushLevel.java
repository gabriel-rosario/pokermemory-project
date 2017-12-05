import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class FlushLevel extends RankTrioLevel{
	
	int [] cardsArray = new int[5];
	int [] possibleHandsArray = new int [5];
	long rankSum = 0;
	long score = 0;
	int handsCounter = 0;
	
	protected FlushLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("FLush Level");
		this.setCardsToTurnUp(5);
		this.setCardsPerRow(10);
		this.setRowsPerGrid(5);
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
					
					//Make an Array of type Int to store ranks of cards as numbers; not stings
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
						case "a": cardsArray[i] = 14;
						break;
						default: break;
						}
					}
					
					Arrays.sort(cardsArray);
					
					//calculate sum of ranks
					for(int i= 0; i<cardsArray.length;i++) {
						rankSum = rankSum + cardsArray[i];
					}
					
					// get the other card (which was already turned up)
					Card otherCard1 = (Card) this.getTurnedCardsBuffer().get(0);
					Card otherCard2 = (Card) this.getTurnedCardsBuffer().get(1);
					Card otherCard3 = (Card) this.getTurnedCardsBuffer().get(2);
					Card otherCard4 = (Card) this.getTurnedCardsBuffer().get(3);
					Card otherCard5 = (Card) this.getTurnedCardsBuffer().get(4);
					
					
					if((card.getSuit().equals(otherCard1.getSuit())) && (card.getSuit().equals(otherCard2.getSuit())) && (card.getSuit().equals(otherCard3.getSuit())) && (card.getSuit().equals(otherCard4.getSuit())) && (card.getSuit().equals(otherCard5.getSuit()))){
						// Five cards' suits match, so remove them from the list (they will remain face up).  if the last card is A(rank = 14) add 6 to the score
						if(cardsArray[4] == 14) {
							this.getTurnedCardsBuffer().clear();
							score+=700+rankSum+6;
							getMainFrame().setScore(score);
						}else {
							this.getTurnedCardsBuffer().clear();
							score+=700+rankSum;
							getMainFrame().setScore(score);
						}
						handsCounter++;

					}
					else
					{
						// The cards do not match, so start the timer to turn them down.  reset rankSum to 0. remove 5 points.
						this.getTurnDownTimer().start();
						score-=5;
						rankSum=0;
						getMainFrame().setScore(score);
					}
				}
				return true;
			}
			return false;
		}
		
		@Override
		protected boolean  isGameOver(){
			if(handsCounter == 8) {
				return true;
			}else {
				return false;
			}
		}
	

	}
	

