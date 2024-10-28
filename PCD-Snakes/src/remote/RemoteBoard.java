package remote;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import environment.LocalBoard;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import game.*;
import gui.BoardComponent;
import gui.SnakeGui;

import javax.swing.*;

/** Remote representation of the game, no local threads involved.
 * Game state will be changed when updated info is received from Srver.
 * Only for part II of the project.
 * @author luismota
 *
 */
public class RemoteBoard extends Board implements Serializable {

    private String localhost;

    private Board board;

    private BoardComponent boardGui;

    private SnakeGui snakeGUI;

    public static final int NUM_SNAKES = 2;
    private static final int NUM_OBSTACLES = 25;
    private static final int NUM_SIMULTANEOUS_MOVING_OBSTACLES = 3;
    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 800;
    private JFrame frame = new JFrame("Snake.io");

    private PrintWriter clientOut;

    public RemoteBoard(PrintWriter clientOut){
        this.clientOut = clientOut;


    }

    @Override
    public void handleKeyPress(int keyCode) {
        clientOut.println(keyCode);
    }

    @Override
    public void init() {

    }

    @Override
    public void handleKeyRelease() {
        // TODO
    }

    @Override
    public void update(Observable o, Object arg) {
        boardGui.repaint();
    }

    @Override
    public void run() {

    }

    public static RemoteBoard createRemoteBoardFromBoard(Board board2, PrintWriter clientOut){
        RemoteBoard board1 = new RemoteBoard(clientOut);
        board1.setCells(board2.getCells());
        board1.setSnakes(board2.getSnakes());
        board1.setObstacles(board2.getObstacles());
        board1.setGoalPosition(board2.getGoalPosition());
        board1.setFinished(board2.isFinished());
        return board1;
    }


}
