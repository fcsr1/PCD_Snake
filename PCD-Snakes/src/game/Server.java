package game;

import environment.Board;
import environment.LocalBoard;
import gui.Main;
import gui.SnakeGui;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Serializable {

    public static final int PORT = 9517;

    private Board board;
    private SnakeGui gui;

    private int players = 0;

    public Server(Board board) {
        this.board = board;
        gui = new SnakeGui(board, 600, 600);
        gui.init();
        try {
            initServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ClientHandler extends Thread {
        private BufferedReader in;
        private HumanSnake snake;
        private ObjectOutputStream out;

        public ClientHandler(Socket socket) throws IOException {
            connect(socket);
            snake = new HumanSnake(LocalBoard.NUM_SNAKES + players, board);
            board.addSnake(snake);
            snake.start();
            System.out.println("Human snake added on Server");
            board.setChanged();
            players++;
        }

        @Override
        public void run() {
            try {
                serverConnection();
            } catch (IOException ignored) {

            }
        }

        void connect(Socket socket) throws IOException {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        private void serverConnection() throws IOException {
            out.writeObject(board);
            new Listener().start();
            new Sender().start();
        }

        private class Listener extends Thread {

            @Override
            public void run() {
                try {
                    while(true) {
                        int keyCode = Integer.parseInt(in.readLine());
                        System.err.println("direção"+keyCode);
                        snake.updateCurrentDirection(keyCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private class Sender extends Thread {

            @Override
            public void run() {
                while (true) {
                    try {
                        out.reset();
                        out.writeObject(board);
                        sleep(Board.REMOTE_REFRESH_INTERVAL);

                    }catch(Exception e){
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }
    }

    private void initServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);

        try {
            while(true) {
                Socket socket = serverSocket.accept();
                if(!board.isFinished()){
                    new ClientHandler(socket).start();
                    System.out.println("Ligação Começou");
                }
            }

        } finally {
            serverSocket.close();
        }
    }



}
