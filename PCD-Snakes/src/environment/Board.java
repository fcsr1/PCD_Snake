package environment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import game.*;

public abstract class Board extends Observable implements Serializable {
	protected Cell[][] cells;
	private BoardPosition goalPosition;
	public static final long PLAYER_PLAY_INTERVAL = 100;
	public static final long REMOTE_REFRESH_INTERVAL = 100;
	public static final int NUM_COLUMNS = 30;
	public static final int NUM_ROWS = 30;
	protected LinkedList<Snake> snakes = new LinkedList<Snake>();
	private LinkedList<Obstacle> obstacles= new LinkedList<Obstacle>();
	public boolean isFinished;

	public Board() {
		cells = new Cell[NUM_COLUMNS][NUM_ROWS];
		for (int x = 0; x < NUM_COLUMNS; x++) {
			for (int y = 0; y < NUM_ROWS; y++) {
				cells[x][y] = new Cell(new BoardPosition(x, y));
			}
		}
		isFinished = false;
	}

	public Cell getCell(BoardPosition cellCoord) {
		if (cellCoord.x < 0 || cellCoord.y < 0 || cellCoord.x >= NUM_COLUMNS || cellCoord.y >= NUM_ROWS)
			System.err.println("Invalid coordinates: " + cellCoord.x + ", " + cellCoord.y);
		return cells[cellCoord.x][cellCoord.y];
	}

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

	protected BoardPosition getRandomPosition() {
		return new BoardPosition((int) (Math.random() *NUM_ROWS),(int) (Math.random() * NUM_ROWS));
	}

	public BoardPosition getGoalPosition() {
		return goalPosition;
	}

	public void setGoalPosition(BoardPosition goalPosition) {
		this.goalPosition = goalPosition;
	}
	
	public void addGameElement(GameElement gameElement) {
		boolean placed=false;
		while(!placed) {
			BoardPosition pos=getRandomPosition();
			if(!getCell(pos).isOccupied() && !getCell(pos).isOcupiedByGoal()) {
				getCell(pos).setGameElement(gameElement);
				if(gameElement instanceof Goal) {
					setGoalPosition(pos);
					System.out.println("Goal placed at:"+pos);
				}
				placed=true;
			}
		}
	}

	public List<BoardPosition> getNeighboringPositions(Cell cell) {
		ArrayList<BoardPosition> possibleCells=new ArrayList<BoardPosition>();
		BoardPosition pos=cell.getPosition();
		if(pos.x>0)
			possibleCells.add(pos.getCellLeft());
		if(pos.x<NUM_COLUMNS-1)
			possibleCells.add(pos.getCellRight());
		if(pos.y>0)
			possibleCells.add(pos.getCellAbove());
		if(pos.y<NUM_ROWS-1)
			possibleCells.add(pos.getCellBelow());
		return possibleCells;

	}

	public Goal addGoal() {
		Goal goal=new Goal(this);
		addGameElement( goal);
		return goal;
	}



	public void addObstacles(int numberObstacles) {
		// clear obstacle list , necessary when resetting obstacles.
		getObstacles().clear();
		while(numberObstacles>0) {
			Obstacle obs=new Obstacle(this);
			addGameElement( obs);
			getObstacles().add(obs);
			numberObstacles--;
		}
	}

	public void removeObstacle(Obstacle obstacle) {
		for (int x = 0; x < NUM_COLUMNS; x++) {
			for (int y = 0; y < NUM_ROWS; y++) {
				Cell cell = cells[x][y];
				if (cell.getGameElement() == obstacle) {
					cell.removeObstacle();
					getObstacles().remove(obstacle);
					cell.release();
					break;
				}
			}
		}
	}

	public LinkedList<Snake> getSnakes() {
		return snakes;
	}

	public void setSnakes(LinkedList<Snake> snakes) {
		this.snakes = snakes;
	}
	@Override
	public void setChanged() {
		super.setChanged();
		notifyObservers();
	}

	public LinkedList<Obstacle> getObstacles() {
		return obstacles;
	}

	public void setObstacles(LinkedList<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}

	
	public abstract void init(); 
	


	public abstract void handleKeyRelease();

	public void addSnake(Snake snake) {
		snakes.add(snake);
	}

	public void setFinished(boolean b) {
		isFinished=b;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public abstract void update(Observable o, Object arg);

	public abstract void run();

	public void handleKeyPress(int keyCode) {
	}

	public void releaseSnake() throws InterruptedException {
		System.out.println(snakes.size());
		for(Snake snake : snakes){
			if(snake instanceof AutomaticSnake){
				while(true){
					List<BoardPosition> neighbours = getNeighboringPositions(snake.getCells().getFirst());
					int random = (int)(Math.random() * neighbours.size());
					if(!getCell(neighbours.get(random)).isOccupied()){
						snake.move(getCell(neighbours.get(random)));
						Thread.sleep(Board.PLAYER_PLAY_INTERVAL);
						break;
					}
				}
			}
		}
	}

	public boolean isWithinBounds(BoardPosition position){
		return position.x >= 0 && position.y >= 0 && position.x < Board.NUM_ROWS && position.y < Board.NUM_COLUMNS;
	}
}