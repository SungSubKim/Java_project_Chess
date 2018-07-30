package added_Function;

import java.io.IOException;
import java.net.Socket;

import game_basic.Chess;

public class Client extends Thread {
	private Sender sender;
	private Reader reader;
	GUIboard g;
	public Client(GUIboard g) {
		this.g =g;
	}
	public void run() {
		try {
			System.out.println("요청을시도합니다");
			Socket s = new Socket("127.0.0.1",8000);
			System.out.println("연결  성공하였습니다");
			sender = new Sender(s,g);
			reader = new Reader(s,g);
			sender.start();
			reader.start();
			while(Chess.getKing_live()) Thread.sleep(1000);
			s.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	public Sender getSender() {
		return this.sender;
	}
	public Reader getReader() {
		// TODO Auto-generated method stub
		return this.reader;
	}
}