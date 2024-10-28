package gui;

import java.io.Console;
import java.io.IOException;

import javax.net.ssl.StandardConstants;

import environment.LocalBoard;
import game.Server;
import remote.RemoteBoard;


public class Main {

		public static final long timeToSleep = 10000L;

	public static void main(String[] args) {
		new Server(new LocalBoard());
	}
}


