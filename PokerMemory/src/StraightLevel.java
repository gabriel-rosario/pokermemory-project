import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StraightLevel extends FlushLevel{
	
	int [] cardsArray = new int [5];
	boolean differentSuits = false;
	boolean cardsFormStraight = false;
	MemoryFrameWithScore memoryFrame = new MemoryFrameWithScore();
	long score = 0;
	//private ScoreLabel scoreLabel;


	
	protected StraightLevel( TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Straight Level");
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
					
					for(int j = 0;j<this.getTurnedCardsBuffer().size()-1;j++) {
						if(this.getTurnedCardsBuffer().get(j).getSuit().equals(this.getTurnedCardsBuffer().get(j+1).getSuit())){
							 differentSuits = false;
						}else {
							differentSuits = true;
							break;
						}
					}
					
					//Make an Array of type Int to store ranks of cards as numbers; not strings
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

					//Check if they are in sequence (ex. 3,4,5,6,7) by comparing each value to the value in first positon of array
					if((cardsArray[0]==(cardsArray[1])-1 && cardsArray[0]==(cardsArray[2])-2 && cardsArray[0]==(cardsArray[3])-3 && cardsArray[0]==(cardsArray[4])-4)&&differentSuits){
							cardsFormStraight = true;
						}else {
							cardsFormStraight = false;
						}
					
					
					//if the cards are a Straight, leave them face up, clear buffer. if last card is A(rank = 14), score is 1000+100*20
					if(cardsFormStraight==true) {
						if(cardsArray[4]==14) {
							this.getTurnedCardsBuffer().clear();
							score+=1000+100*20;
							getMainFrame().setScore(score);
						}else {
							this.getTurnedCardsBuffer().clear();
							score += 1000 + 100*cardsArray[4];
							getMainFrame().setScore(score);
						}
					}
					else
					{
						// The cards do not match, so start the timer to turn them down.  remove 5 points.
						this.getTurnDownTimer().start();
						score-=5;
						getMainFrame().setScore(score);
					}

				}
				return true;
			}
			
			return false;
			
		}
		
		//No Time to do GameOver
		/*@Override
		protected boolean  isGameOver(){
			
			
		}*/
	
}
	

