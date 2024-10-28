package game;

import java.io.Serializable;
import java.util.LinkedList;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;
/** Base class for representing Snakes.
 * Will be extended by HumanSnake and AutomaticSnake.
 * Common methods will be defined here.
 * @author luismota
 *
 */
public abstract class Snake extends Thread implements Serializable{
	private static final int DELTA_SIZE = 10;
	protected LinkedList<Cell> cells = new LinkedList<Cell>();
	protected int size = 5;
	protected int valueToAdd = 0;
	private int id;
	private Board board;
	protected Snake(int id,Board board) {
		this.id = id;
		this.board=board;
	}
	public int getSize() {
		return size;
	}
	public int getValueToAdd() { return valueToAdd; }
	public void decreaseValueToAdd() { valueToAdd--; }
	public void setValueToAdd(int value) { valueToAdd = value; }
	public int getIdentification() {
		return id;
	}
	public int getLength() {
		return cells.size();
	}
	public LinkedList<Cell> getCells() {
		return cells;
	}
	public void move(Cell cell) throws InterruptedException {
		if (cell.isOccupied() || cell == null)
			return;
		cell.request(this);
		if (cell.getPosition().equals(board.getGoalPosition())) {
			int goalValue = board.getCell(board.getGoalPosition()).getGoal().captureGoal();
			System.out.println(goalValue + "goalValue");
			this.setValueToAdd(getValueToAdd() + goalValue);
		}
		if (this.valueToAdd != 0) {
			this.decreaseValueToAdd();
			LinkedList<Cell> newCells = new LinkedList<>();
			newCells.add(cell);
			newCells.addAll(cells);
			cells = newCells;
			System.out.println("value to add: " + this.valueToAdd + " / snake size: " + this.cells.size());
		} else {
			LinkedList<Cell> newCells = new LinkedList<>();
			newCells.add(cell);
			newCells.addAll(cells.subList(0, cells.size()-1));
			board.getCell(cells.getLast().getPosition()).release();
			cells = newCells;
			System.out.println("snake size: " + this.cells.size());
		}
		board.setChanged();
	}
	public LinkedList<BoardPosition> getPath() {
		LinkedList<BoardPosition> coordinates = new LinkedList<BoardPosition>();
		for (Cell cell : cells) {
			coordinates.add(cell.getPosition());
		}
		return coordinates;
	}	
	public void doInitialPositioning() {
		int posX = 0;
		int posY = (int) (Math.random() * Board.NUM_ROWS);
		BoardPosition at = new BoardPosition(posX, posY);
		try {
			board.getCell(at).request(this);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cells.add(board.getCell(at));
//		System.err.println("Snake "+getIdentification()+" starting at:"+getCells().getLast());
	}
	public Board getBoard() {
		return board;
	}
	
	
}
