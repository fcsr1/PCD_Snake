package environment;

import game.GameElement;
import game.Goal;
import game.Obstacle;
import game.Snake;

import java.io.Serializable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** Main class for game representation.
 * 
 * @author luismota
 *
 */
public class Cell implements Serializable {
	private BoardPosition position;
	private Snake ocuppyingSnake = null;
	private GameElement gameElement=null;

	protected Lock lock = new ReentrantLock();

	private Condition isFree = lock.newCondition();
	
	public GameElement getGameElement() {
		return gameElement;
	}


	public Cell(BoardPosition position) {
		super();
		this.position = position;
	}

	public BoardPosition getPosition() {
		return position;
	}

	public void request(Snake snake) throws InterruptedException {
		lock.lock();
		while (isOccupied()) {
			isFree.await();
		}
		ocuppyingSnake=snake;
		lock.unlock();
	}

	public void release() {
		lock.lock();
		ocuppyingSnake=null;
		isFree.signalAll();
		lock.unlock();
	}

	public boolean isOcupiedBySnake() {
		return ocuppyingSnake!=null;
	}


	public  void setGameElement(GameElement element) {
		//sincronizar
		// TODO coordination and mutual exclusion
		gameElement=element;

	}

	public void removeObstacle() {
		if (gameElement instanceof Obstacle) {
			gameElement = null;
		}
	}

	public boolean isOccupied() {
		return isOcupiedBySnake() || (gameElement!=null && gameElement instanceof Obstacle);
	}


	public Snake getOcuppyingSnake() {
		return ocuppyingSnake;
	}


	public  Goal removeGoal() {
		// TODO
		return null;
	}


	public Goal getGoal() {
		return (Goal)gameElement;
	}


	public boolean isOcupiedByGoal() {
		return (gameElement!=null && gameElement instanceof Goal);
	}


	//----------------------------- New Code ---------------------------------//

	public void setPosition(BoardPosition position){
		this.position = position;
	}

	public Condition getIsFree() {
		return isFree;
	}

}
