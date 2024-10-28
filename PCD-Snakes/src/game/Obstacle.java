package game;

import environment.Board;
import environment.LocalBoard;

import java.io.Serializable;

public class Obstacle extends GameElement implements Serializable {
	
	
	private static final int NUM_MOVES=3;
	protected static final int OBSTACLE_MOVE_INTERVAL = 1000;
	private int remainingMoves=NUM_MOVES;
	private Board board;
	public Obstacle(Board board) {
		super();
		this.board = board;
	}
	
	public int getRemainingMoves() {
		return remainingMoves;
	}

	public void decrementRemainingMoves(){
		remainingMoves --;
	}

}
