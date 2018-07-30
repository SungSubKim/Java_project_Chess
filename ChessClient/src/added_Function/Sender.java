package added_Function;

import java.io.IOException;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import game_basic.Chess;

public class Sender extends Thread {
	Socket s;
	GUIboard g;
	private Box box = new Box();
	public Box getBox() {
		return this.box;
	}
	public void setMap(ArrayList<ArrayList<String>> map) {
		this.box.setMap(map);
	}
	public Sender(Socket s,GUIboard g) {
		this.s = s;
		this.g = g;
	}
	public void run() {
		try {
			g.getRestart().setSender(this);
			g.getLoad().setSender(this);
			OutputStream out = s.getOutputStream();
			ObjectOutputStream dos = new ObjectOutputStream(out);
			while(Chess.getKing_live()) {
				box = new Box();
				box.setMap(null);
				box.setStr("empty");
				for(box.setStr("empty"); box.getStr().equals("empty") && box.getMap() == null ;Thread.sleep(30));
				dos.writeObject(this.box);
			}
			
		} catch(IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}