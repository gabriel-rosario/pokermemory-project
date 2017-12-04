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
				
				for(int i= 0; i<cardsArray.length;i++) {
					rankSum = rankSum + cardsArray[i];
				}
				
				// get the other card (which was already turned up)
				Card otherCard1 = (Card) this.getTurnedCardsBuffer().get(0);
				Card otherCard2 = (Card) this.getTurnedCardsBuffer().get(1);
				if((card.getRank().equals(otherCard1.getRank())) && (card.getRank().equals(otherCard2.getRank()))) {
					// Three cards match, so remove them from the list (they will remain face up)
					score += score + 100 + rankSum;
					getMainFrame().setScore(score);
					this.getTurnedCardsBuffer().clear();
				}
				else
				{
					// The cards do not match, so start the timer to turn them down
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

		//Empieza el counter en la primera posicion del grid
		for (int i =0; i< this.getGrid().size()-2;i++) {
			//Verifica si la carta esta facedown
			if(!this.getGrid().get(i).isFaceUp()) {
				//if it is facedown pues guardar en rank en una variable (fue para limpiarlo un poco y para el debugger)
				String iRank = this.getGrid().get(i).getRank();
				for(int j = i+1;j<this.getGrid().size()-1;j++) {
					//if it was facedown empieza otro loop en i+1 y verifica si esa carta esta facedown
					if(!this.getGrid().get(j).isFaceUp()) {
						String jRank = this.getGrid().get(j).getRank(); //save it en variable para limpieza
						//if las dos cartas son iguales pues empieza OTRO loop para la tercera carta
						if(iRank.equals(jRank)) {
							for(int k = j+1;k<this.getGrid().size();k++) {
								//si esta facedown pues compara otra vez el rank de J y K
								if(!this.getGrid().get(k).isFaceUp()) {
									String kRank = this.getGrid().get(k).getRank(); //save rank en variable para limpieza
									//si jRank y kRank son igual, pues las tres cartas son igual so existe todavia un winning hand faceDown
									if(jRank.equals(kRank)) {
										return false;
									}
								}
							}
						}	
					}
				}
			}
		}
		//si corre por el loop y no encuentra nada pues va a devolver true y pasa el game over
		return true;
	}
	
}
