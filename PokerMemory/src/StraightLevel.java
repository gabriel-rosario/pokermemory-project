import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StraightLevel extends FlushLevel{
	
	int [] cardsArray = new int [5];
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
	
		//Convert Ranks to Int
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
					if(cardsArray[0]==(cardsArray[1])-1 && cardsArray[0]==(cardsArray[2])-2 && cardsArray[0]==(cardsArray[3])-3 && cardsArray[0]==(cardsArray[4])-4) {
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
		
		@Override
		protected boolean  isGameOver(){
			
			int [] possibleHandsArray = new int [5];
			int [] orderedPossibleHandsArray = new int[5];

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
			//si corre por el loop y no encuentra nada pues va a devolver true y pasa el game over
			return true;
		}
	
}
	

