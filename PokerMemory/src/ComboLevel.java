import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class ComboLevel extends FlushLevel{
	int [] cardsRankArray = new int [5];
	String [] cardsSuitsArray = new String [5];
	boolean cardsFormStraight = false;
	boolean cardsFormFlush = false; 
	boolean cardsFormFullHouse = false;
	long score = 0;
	long rankSum = 0;

	
	protected ComboLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Combo Level");
		this.setCardsToTurnUp(5);
		this.setCardsPerRow(10);
		this.setRowsPerGrid(5);
		
	}

		
		protected int convertRanksToInt(String rank) {
			int intRank = 0;
				switch (rank) {
				case "2": intRank = 2;
				break;
				case "3": intRank = 3;
				break;
				case "4": intRank = 4;
				break;
				case "5": intRank = 5;
				break;
				case "6": intRank = 6;
				break;
				case "7": intRank = 7;
				break;
				case "8": intRank = 8;
				break;
				case "9": intRank = 9;
				break;
				case "t": intRank = 10;
				break;
				case "j": intRank = 11;
				break;
				case "q": intRank = 12;
				break;
				case "k": intRank = 13;
				break;
				case "a": intRank = 14;
				break;
				default: break;
				}
				return intRank;

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
					
					//calculate sum of ranks
					for(int i= 0; i<cardsRankArray.length;i++) {
						rankSum = rankSum + cardsRankArray[i];
					}
					
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
					card.faceUp();
					
					//Show JOptionPane if a winning hand is selected
					if(cardsFormStraight||cardsFormFlush||cardsFormFullHouse) {
						//Custom JOptionPane if only Straight is a winning hand
						if(cardsFormStraight) {
							Object [] possibleHands = {"Straight","Pass"};
							
							int selectedHand = JOptionPane.showOptionDialog(getMainFrame(),"Select a winning hand","Pick a Winning Hand",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, possibleHands,possibleHands[0]);
						
							switch(selectedHand) {
							case 0:
								if(cardsFormStraight) {
									if(cardsRankArray[4]==14) {
										score = 1000+100*20;
										this.getTurnedCardsBuffer().clear();
									}else {
										score = 1000+100*cardsRankArray[4];
										this.getTurnedCardsBuffer().clear();
									}
								}else {
									score-=5;
									this.getTurnDownTimer().start();
								}
								break;
							case 1:
								score -= 5;
								this.getTurnDownTimer().start();
								break;
							}
						//Custom JOptionPane if only Flush is a winning hand

						}else if(cardsFormFlush) {
							Object [] possibleHands = {"Flush","Pass"};
							
							int selectedHand = JOptionPane.showOptionDialog(getMainFrame(),"Select a winning hand","Pick a Winning Hand",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, possibleHands,possibleHands[0]);
						
							switch(selectedHand) {
							case 0:
								if(cardsFormFlush) {
									score = 700 + rankSum;
									this.getTurnedCardsBuffer().clear();
								}else {
									score -= 5;
									this.getTurnDownTimer().start();
								}
							case 1:
								score -= 5;
								this.getTurnDownTimer().start();
								break;
							}
							
							//Custom JOptionPane if only FullHouse is a winning hand
						}else if(cardsFormFullHouse) {
							Object [] possibleHands = {"Full House","Pass"};
							
							int selectedHand = JOptionPane.showOptionDialog(getMainFrame(),"Select a winning hand","Pick a Winning Hand",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, possibleHands,possibleHands[0]);
						
							switch(selectedHand) {
							case 0:
								if(cardsFormFullHouse) {
									score = 1000 + rankSum;
									this.getTurnedCardsBuffer().clear();
								}else {
									score -= 5;
									this.getTurnDownTimer().start();
								}
							case 1:
								score -= 5;
								this.getTurnDownTimer().start();
								break;
							}
							//Custom JOptionPane if only Straight Flush is a winning hand
						}else if(cardsFormStraight&&cardsFormFlush){
							Object [] possibleHands = {"Straight","Flush","Pass"};
							
							int selectedHand = JOptionPane.showOptionDialog(getMainFrame(),"Select a winning hand","Pick a Winning Hand",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, possibleHands,possibleHands[0]);
						
							switch(selectedHand) {
							case 0:
								if(cardsFormStraight) {
									if(cardsRankArray[4]==14) {
										score = 1000+100*20;
										this.getTurnedCardsBuffer().clear();
									}else {
										score = 1000+100*cardsRankArray[4];
										this.getTurnedCardsBuffer().clear();
									}
								}else {
									score-=5;
									this.getTurnDownTimer().start();
								}
								break;
							case 1:
								if(cardsFormFlush) {
									score = 700 + rankSum;
									this.getTurnedCardsBuffer().clear();
								}else {
									score -= 5;
									this.getTurnDownTimer().start();
								}
							case 2:
								score -= 5;
								this.getTurnDownTimer().start();
								break;
							}
						}
						
						getMainFrame().setScore(score);
					}else {
						this.getTurnDownTimer().start();
						score-=5;
						getMainFrame().setScore(score);
					}
				}
				return true;
				
			}
			return false;
					
			}				
	
		
		@Override
		protected boolean  isGameOver(){
			int [] possibleHandsArray = new int [5];
			int [] orderedPossibleHandsArray = new int[5];
			boolean isStraight = false;
			boolean isFlush = false;
			boolean isFullHouse = false;
			
			//Empieza el counter en la primera posicion del grid
			for (int i =0; i< this.getGrid().size()-4;i++) {
				//Verifica si la carta esta facedown
				if(!this.getGrid().get(i).isFaceUp()) {
					possibleHandsArray[0] = convertRanksToInt(this.getGrid().get(i).getRank());
						for(int j = i+1;j<this.getGrid().size()-3;j++) {
						//if it was facedown empieza otro loop en i+1 y verifica si esa carta esta facedown
						if(!this.getGrid().get(j).isFaceUp()) {
							possibleHandsArray[1] = convertRanksToInt(this.getGrid().get(j).getRank());
								for(int k = j+1;k<this.getGrid().size()-2;k++) {
									if(!this.getGrid().get(k).isFaceUp()) {
										possibleHandsArray[2] = convertRanksToInt(this.getGrid().get(k).getRank());
											for(int p=k+1;p<this.getGrid().size()-1;p++) {
												if(!this.getGrid().get(p).isFaceUp()) {
													possibleHandsArray[3] = convertRanksToInt(this.getGrid().get(p).getRank());
														for(int q = p+1;q<this.getGrid().size();q++) {
															if(!this.getGrid().get(q).isFaceUp()) {
																possibleHandsArray[4] = convertRanksToInt(this.getGrid().get(q).getRank());
																for(int counter = 0; counter<5;counter++) {
																	orderedPossibleHandsArray[counter] = possibleHandsArray[counter];
																}
																Arrays.sort(orderedPossibleHandsArray);
																if(orderedPossibleHandsArray[0]==(orderedPossibleHandsArray[1]-1) && orderedPossibleHandsArray[0]==(orderedPossibleHandsArray[2]-2) && orderedPossibleHandsArray[0]==(orderedPossibleHandsArray[3]-3) && orderedPossibleHandsArray[0]==(orderedPossibleHandsArray[4]-4)) {
																	isStraight = true;
																}else if(((orderedPossibleHandsArray[0]==orderedPossibleHandsArray[1])&&(orderedPossibleHandsArray[0]==orderedPossibleHandsArray[2])&&(orderedPossibleHandsArray[3]==orderedPossibleHandsArray[4]))||((orderedPossibleHandsArray[0]==orderedPossibleHandsArray[1])&&(orderedPossibleHandsArray[2]==orderedPossibleHandsArray[3])&&(orderedPossibleHandsArray[2]==orderedPossibleHandsArray[4])) ) {
																	isFullHouse = true;
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
																	isFlush = true;
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
			
			
			if(isFlush||isStraight||isFullHouse) {
				return false;
			}
			
			//si corre por el loop y no encuentra nada pues va a devolver true y pasa el game over
			return true;
		}
		
}