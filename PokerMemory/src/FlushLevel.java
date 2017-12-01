import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class FlushLevel extends RankTrioLevel{
	
	int [] cardsArray = new int[5];
	long rankSum = 0;
	long score = 0;
	
	protected FlushLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("FLush Level");
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
						// Three cards match, so remove them from the list (they will remain face up)
						if(cardsArray[4] == 14) {
							this.getTurnedCardsBuffer().clear();
							score+=700+rankSum+6;
							getMainFrame().setScore(score);
						}else {
							this.getTurnedCardsBuffer().clear();
							score+=700+rankSum;
							getMainFrame().setScore(score);
						}

					}
					else
					{
						// The cards do not match, so start the timer to turn them down
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
		
		/*//GAME OVER 
		@Override
		protected boolean  isGameOver(){
			for(int i = 0; i<this.getGrid().size()-2;i++) {
				for(int j = i+1; j<this.getGrid().size()-1;j++) {
					for(int k = j+1;j<this.getGrid().size();k++) {
						if(this.getGrid().get(i).isFaceUp());
					}
				}
			}
			//for (int i =0; i< this.getGrid().size();i++)
				//if(!this.getGrid().get(i).isFaceUp()) return false;


			return true;
		}*/
	

	}
	

