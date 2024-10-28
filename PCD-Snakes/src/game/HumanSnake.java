package game;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import gui.SnakeGui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;

/** Class for a remote snake, controlled by a human
  * 
  * @author luismota
  *
  */
public class HumanSnake extends Snake implements Serializable {

	public String currentDirection = "null";

	public HumanSnake(int id, Board board) {
		super(id, board);
	}

	@Override
	public void run() {
		doInitialPositioning();
		try {
			Thread.sleep(1000);
		}catch (Exception e){
			e.printStackTrace();
		}
		while(!getBoard().isFinished()){
			try{
				this.move(getCellToMove());
			}catch (Exception e){
				e.printStackTrace();
			}

			try {
				sleep(100);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}


	public Cell getCellToMove() {
		Cell cellToMove = null;
		if (currentDirection.equals("up")) {
			cellToMove = getBoard().getCell(this.getCells().getFirst().getPosition().getCellAbove());
		} else if (currentDirection.equals("down")) {
			cellToMove = getBoard().getCell(this.getCells().getFirst().getPosition().getCellBelow());
		} else if (currentDirection.equals("left")) {
			cellToMove = getBoard().getCell(this.getCells().getFirst().getPosition().getCellLeft());
		} else if (currentDirection.equals("right")) {
			cellToMove = getBoard().getCell(this.getCells().getFirst().getPosition().getCellRight());
		}
		return cellToMove;
	}




	public void updateCurrentDirection(int keyCode){
		switch (keyCode){
			case 38:
				currentDirection = "up";
				break;
			case 40:
				currentDirection = "down";
				break;
			case 37:
				currentDirection = "left";
				break;
			case 39:
				currentDirection = "right";
				break;
			default:
				currentDirection = "null";
		}
	}

}
