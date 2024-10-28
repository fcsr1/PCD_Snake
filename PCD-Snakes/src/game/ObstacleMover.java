package game;

import environment.Board;
import environment.LocalBoard;

import java.io.Serializable;

public class ObstacleMover extends Thread implements Serializable {
	private Obstacle obstacle;
	private Board board;
	
	public ObstacleMover(Obstacle obstacle, Board board) {
		super();
		this.obstacle = obstacle;
		this.board = board;
	}

	@Override
	public void run() {
		try {
			while (obstacle.getRemainingMoves() > 0) {
				Thread.sleep(Obstacle.OBSTACLE_MOVE_INTERVAL);
				moving();
				obstacle.decrementRemainingMoves();
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private void moving(){
		if(this.obstacle.getRemainingMoves() < 0){

		}else {

			board.removeObstacle(obstacle);

			board.addGameElement(obstacle);
			board.getObstacles().add(obstacle);

		}
	}


}
