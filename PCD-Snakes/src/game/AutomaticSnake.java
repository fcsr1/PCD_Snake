package game;

import environment.LocalBoard;
import environment.Cell;
import environment.Board;
import environment.BoardPosition;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class AutomaticSnake extends Snake implements Serializable {
	public AutomaticSnake(int id, Board board) {
		super(id,board);

	}

	@Override
	public void run() {
		try{
		doInitialPositioning();
		//Thread.sleep(1000);
		System.err.println("initial size:" + cells.size());
		while(!Thread.interrupted()) {
			if(getBoard().isFinished()){
				interrupt();
			}
			//while (this.getCells().getFirst().getPosition() != this.getBoard().getGoalPosition()) {
				moveTo(this.getBoard().getGoalPosition());
				sleep(Board.PLAYER_PLAY_INTERVAL);
			//}

		}
		} catch (InterruptedException e) {
			System.out.println("oh no I'm dyiiiiiiing");
		}

	}

	private void moveTo(BoardPosition goal) throws InterruptedException {
		Cell initPos = this.getCells().getFirst();
		Cell newPos = null;
		synchronized (initPos.getPosition()) {
				if (initPos.getPosition().x < goal.x) {
					newPos = this.getBoard().getCell(initPos.getPosition().getCellRight());
				} else if (initPos.getPosition().x > goal.x) {
					newPos = this.getBoard().getCell(initPos.getPosition().getCellLeft());
				} else if (initPos.getPosition().y < goal.y) {
					newPos = this.getBoard().getCell(initPos.getPosition().getCellBelow());
				} else if (initPos.getPosition().y > goal.y) {
					newPos = this.getBoard().getCell(initPos.getPosition().getCellAbove());
				}

				if (newPos != null) {
					this.move(newPos);
				}
			}
		}
	}
