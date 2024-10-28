package environment;

import java.io.Serializable;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.*;

/** Class representing the state of a game running locally
 * 
 * @author luismota
 *
 */
public class LocalBoard extends Board implements Serializable{

	public static final int NUM_SNAKES = 2;
	private static final int NUM_OBSTACLES = 25;
	private static final int NUM_SIMULTANEOUS_MOVING_OBSTACLES = 1;

	public LocalBoard() {

		for (int i = 0; i < NUM_SNAKES; i++) {
			AutomaticSnake snake = new AutomaticSnake(i, this);
			snakes.add(snake);
		}


		addObstacles( NUM_OBSTACLES);
		moving_obsticles();
		Goal goal=addGoal();
//		System.err.println("All elements placed");
	}

	public void init() {
		for(Snake s:snakes)
			s.start();
		ExecutorService executor = Executors.newFixedThreadPool(NUM_SIMULTANEOUS_MOVING_OBSTACLES);
		for (Obstacle obstacle : this.getObstacles()) {
			Runnable move = new ObstacleMover(obstacle, this);
			executor.execute(move);
		}
		executor.shutdown();
		setChanged();
	}

	

	@Override
	public void handleKeyPress(int keyCode) {
		// do nothing... No keys relevant in local game
	}

	@Override
	public void handleKeyRelease() {
		// do nothing... No keys relevant in local game
	}

	@Override
	public void update(Observable o, Object arg) {

	}

	@Override
	public void run() {

	}


//----------------------------- New Code ---------------------------------//

	public void moving_obsticles(){ //adicionar lá em cima para se poder aceder depois
	}


}
