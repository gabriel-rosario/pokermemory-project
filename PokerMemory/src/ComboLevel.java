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
	boolean differentSuits = false;

	
	protected ComboLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Combo Level");
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
					
					for(int j = 0;j<this.getTurnedCardsBuffer().size()-1;j++) {
						if(this.getTurnedCardsBuffer().get(j).getSuit().equals(this.getTurnedCardsBuffer().get(j+1).getSuit())){
							 differentSuits = false;
						}else {
							differentSuits = true;
							break;
						}
					}
					
					//Check if they are in sequence (ex. 3,4,5,6,7) by comparing each value to the value in first positon of array
					if((cardsRankArray[0]==(cardsRankArray[1])-1 && cardsRankArray[0]==(cardsRankArray[2])-2 && cardsRankArray[0]==(cardsRankArray[3])-3 && cardsRankArray[0]==(cardsRankArray[4])-4)&&differentSuits) {
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
									score = 1500 + rankSum;
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
	
		//No time to do gameOver
		/*@Override
		protected boolean  isGameOver(){
			
			
			//si corre por el loop y no encuentra nada pues va a devolver true y pasa el game over
			return true;
		}*/
		
}