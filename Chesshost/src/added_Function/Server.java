package added_Function;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import added_Function.Sender;
import added_Function.Reader;
import game_basic.Chess;

public class Server extends Thread {
	private Sender sender;
	private Reader reader;
	GUIboard g;
	public Server(GUIboard g) {
		this.g =g;
	}
	public void run() {
		try {
			ServerSocket ss = new ServerSocket(8000);
			Socket s = ss.accept();
			System.out.println("연결되었습니다");
			sender = new Sender(s,g);
			reader = new Reader(s,g);
			sender.start();
			reader.start();
			System.out.println("쓰레드를 시작합니다");
			while(Chess.getKing_live()) Thread.sleep(1000);
			s.close();
			ss.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	public Sender getSender() {
		return this.sender;
	}
	public Reader getReader() {
		return this.reader;
	}
}
