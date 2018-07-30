package added_Function;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import game_basic.Chess;
import game_basic.Locate;

public class Reader extends Thread {
	private Socket s;
	private GUIboard g;
	private Box box = new Box();
	public Box getBox() {
		return this.box;
	}
	public Reader(Socket s,GUIboard g) {
		this.s = s;
		this.g = g;
	}
	public void run() {
		InputStream in;
		try {
			in = s.getInputStream();
			ObjectInputStream dos = new ObjectInputStream(in);
			while(Chess.getKing_live()) {
				box = (Box)dos.readObject();
				if(box.getMap() == null) {
					String instr = box.getStr();
					Chess.setStr(instr);
					box.setStr("empty");
				}else {
					Locate.ArrayListToLocate(box.getMap(), g.getConTimeRun(1), g.getConTimeRun(2));
					//box.setMap(null);
					try {
						Thread.sleep(300);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					g.boardSet();
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}