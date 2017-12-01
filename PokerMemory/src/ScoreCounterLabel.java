
import java.awt.Color;

import javax.swing.JLabel;

public class ScoreCounterLabel extends JLabel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int score = 0;
	
	public ScoreCounterLabel()
	{
		super();
		reset();
	}
	
	public int getScore(){
		return this.score;
		}
	
	/**
	 * Update the text label with the current counter value
	*/
	private void update()
	{
		setForeground(Color.BLACK);	
		setText(Integer.toString(this.score));
		setHorizontalTextPosition(JLabel.CENTER);	}
	
	/**
	 * Default constructor, starts counter at 0
	*/
	
	/**
	 * Increments the counter and updates the text label
	*/
	public void increment(int points)
	{
		this.score = this.score + points;
		update();
	}
	
	/**
	 * Resets the counter to zero and updates the text label
	*/
	public void reset()
	{
		this.score = 0;
		update();
	}
}