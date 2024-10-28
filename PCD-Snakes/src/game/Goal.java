package game;

import environment.Board;
import environment.LocalBoard;

import java.io.Serializable;

public class Goal extends GameElement implements Serializable{
	private int value=1;
	private Board board;
	public static final int MAX_VALUE=9;
	public Goal( Board board2) {
		this.board = board2;
	}
	
	public int getValue() {
		return value;
	}
	public void incrementValue() throws InterruptedException {
		value++;
	}

	public int captureGoal() throws InterruptedException {
		board.getCell(board.getGoalPosition()).setGameElement(null);
		board.addGameElement(this);
		incrementValue();
		if(value == MAX_VALUE){
			board.isFinished = true;
		}
		return value-1;
	}
}
