import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class FlushLevel extends RankTrioLevel{
	
	int [] cardsArray = new int[5];
	int [] possibleHandsArray = new int [5];
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

			//Empieza el counter en la primera posicion del grid
			for (int i =0; i< this.getGrid().size()-4;i++) {
				//Verifica si la carta esta facedown
				if(!this.getGrid().get(i).isFaceUp()) {
					//if it is facedown pues guardar en rank en una variable (fue para limpiarlo un poco y para el debugger)
					String iSuit = this.getGrid().get(i).getSuit();
					for(int j = i+1;j<this.getGrid().size()-3;j++) {
						//if it was facedown empieza otro loop en i+1 y verifica si esa carta esta facedown
						if(!this.getGrid().get(j).isFaceUp()) {
							String jSuit = this.getGrid().get(j).getSuit(); //save it en variable para limpieza
							//if las dos cartas son iguales pues empieza OTRO loop para la tercera carta
							if(iSuit.equals(jSuit)) {
								for(int k = j+1;k<this.getGrid().size()-2;k++) {
									//si esta facedown pues compara otra vez el rank de J y K
									if(!this.getGrid().get(k).isFaceUp()) {
										String kSuit = this.getGrid().get(k).getSuit(); //save rank en variable para limpieza
										//si jRank y kRank son igual, pues las tres cartas son igual so existe todavia un winning hand faceDown
										if(jSuit.equals(kSuit)) {
											for(int p=k+1;p<this.getGrid().size()-1;p++) {
												if(!this.getGrid().get(p).isFaceUp()) {
													String pSuit = this.getGrid().get(p).getSuit();
													if(kSuit.equals(pSuit)) {
														for(int q = p+1;q<this.getGrid().size();q++) {
															if(!this.getGrid().get(q).isFaceUp()) {
																String qSuit = this.getGrid().get(q).getSuit();
																if(pSuit.equals(qSuit)) {
																	return false;
																}
															}
														}
													}
												}
											}
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
	

