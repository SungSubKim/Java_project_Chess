package added_Function;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game_basic.Chess;
import game_basic.Locate;
import game_basic.Unit;

public class GUIboard extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar menubar = new JMenuBar();
	private JMenu file = new JMenu("File");
	private JMenu others = new JMenu("ohters");
	private JMenuItem save = new JMenuItem("Save");
	private JMenuItem load = new JMenuItem("Load");
	private JMenuItem restart = new JMenuItem("Restart");
	private JMenuItem exit = new JMenuItem("Exit");
	private timeLabel timer1 = new timeLabel(true);
	private timeLabel timer2 = new timeLabel(false);
	private conTimeRun conTime1Run = new conTimeRun(timer1);
	private conTimeRun conTime2Run = new conTimeRun(timer2);
	private Thread conTime1 = new Thread(conTime1Run);
	private Thread conTime2 = new Thread(conTime2Run);
	private LocationButton[][] location = new LocationButton[8][8];
	private JPanel times = new JPanel(new FlowLayout(FlowLayout.LEFT,50, 0));
	private JPanel board = new JPanel(new GridLayout(10,10));
	private String pawnW = "C:/Image/pawnW.png";
	private String pawnB = "C:/Image/pawnB.png";
	private String knightW = "C:/Image/knightW.png";
	private String knightB = "C:/Image/knightB.png";
	private String bishopW = "C:/Image/bishopW.png";
	private String bishopB = "C:/Image/bishopB.png";
	private String rookW = "C:/Image/rookW.png";
	private String rookB = "C:/Image/rookB.png";
	private String queenW = "C:/Image/queenW.png";
	private String queenB = "C:/Image/queenB.png";
	private String kingW = "C:/Image/kingW.png";
	private String kingB = "C:/Image/kingB.png";
	private LoadListener load1 = new LoadListener(conTime1Run,conTime2Run);
	private RestartListener restart1 = new RestartListener(conTime1Run, conTime2Run);
	public RestartListener getRestart() {
		return this.restart1;
	}
	public LoadListener getLoad() {
		return this.load1;
	}
	public GUIboard() {
		
	}
	public GUIboard (String name) {
		super(name);
		setLayout(null);
		menuAdd();
		timerAdd();
		boardAdd();
		setBounds(950,10,1000,1050);
	}
	public void menuAdd() {
		menubar.add(file);
		menubar.add(others);
		file.add(save);
		file.add(load);
		others.add(restart);
		others.add(exit);
		setJMenuBar(menubar);
		save.addActionListener(new SaveListener(conTime1Run,conTime2Run));
		load.addActionListener(load1);
		restart.addActionListener(new RestartListener(conTime1Run,conTime2Run));
		exit.addActionListener(new ExitListener());
	}
	public void timerAdd() {
		times.add(timer1);
		times.add(timer2);
		timer1.setFont(new Font("¸¼Àº °íµñ",Font.PLAIN,30));
		timer2.setFont(new Font("¸¼Àº °íµñ",Font.PLAIN,30));
		timer2.setForeground(Color.GRAY);
		add(times);
		times.setBounds(50,0, 800, 50);
		conTime1.start();
		conTime1Run.setIsRunning(true);
		conTime2.start();
		timer2.setText("Black timer : 0s");
	}
	public void boardAdd() {
		board.add(new JLabel(""));
		for (int cnt = 0 ; cnt < 8 ; cnt++) {
			for(int cnt2 = 0; cnt2 < 8 ; cnt2++) {
				ImageIcon icon = null;
				location[cnt][cnt2] = new LocationButton(cnt,cnt2);
				icon = new ImageIcon(new BufferedImage(64,64,BufferedImage.TYPE_INT_ARGB));
				location[cnt][cnt2].setIcon(icon);
				if(cnt2%2 ==0) {
					if(cnt%2==0) location[cnt][cnt2].setBackground(Color.GRAY);
					else location[cnt][cnt2].setBackground(Color.WHITE);
				}
				else
				{
					if(cnt%2==0) location[cnt][cnt2].setBackground(Color.WHITE);
					else location[cnt][cnt2].setBackground(Color.GRAY);
				}
				location[cnt][cnt2].addActionListener(new LocationListener(conTime1Run,conTime2Run));
			}
		}								//¹öÆ° °´Ã¼ »ý¼º
		for (int cnt = 0 ; cnt < 8 ; cnt++) {
			JLabel label = new JLabel(""+(char)(65+cnt),SwingConstants.CENTER);
			board.add(label);
			label.setFont(new Font("¸¼Àº °íµñ",Font.BOLD,30));
		}			//À§¿¡ abcdefgh»ý¼º
		board.add(new JLabel(""));
		for (int cnt2 = 7; cnt2 >= 0 ; cnt2--) {
			JLabel label = new JLabel(""+(cnt2+1),SwingConstants.CENTER);
			JLabel label2 = new JLabel(""+(cnt2+1),SwingConstants.CENTER);
			board.add(label);
			label.setFont(new Font("¸¼Àº °íµñ",Font.BOLD,30));
			label2.setFont(new Font("¸¼Àº °íµñ",Font.BOLD,30));
			for (int cnt = 0 ; cnt < 8 ; cnt++) board.add(location[cnt][cnt2]);
			board.add(label2);
		}					//Ã¼½ººí·Ï»ý¼º
		board.add(new JLabel(""));
		for (int cnt = 0 ; cnt < 8 ; cnt++) {
			JLabel label = new JLabel(""+(char)(65+cnt),SwingConstants.CENTER);
			board.add(label);
			label.setFont(new Font("¸¼Àº °íµñ",Font.BOLD,30));
		}
		board.add(new JLabel(""));		//¾Æ·¡abcdefgh»ý¼º
		boardSet();
		add(board);
		board.setBounds(10, 30, 950, 900);
	}
	public void boardSet() {
		
		for (int cnt = 0 ; cnt < 8 ; cnt++) {
			for(int cnt2 = 0; cnt2 < 8 ; cnt2++) {
				if(cnt2%2 ==0) {
					if(cnt%2==0) location[cnt][cnt2].setBackground(Color.GRAY);
					else location[cnt][cnt2].setBackground(Color.WHITE);
				}
				else
				{
					if(cnt%2==0) location[cnt][cnt2].setBackground(Color.WHITE);
					else location[cnt][cnt2].setBackground(Color.GRAY);
				}
				ImageIcon icon = null;
				if(location[cnt][cnt2].getLocate().getUnit()== null)
					icon = new ImageIcon(new BufferedImage(64,64,BufferedImage.TYPE_INT_ARGB));
				else {
					try {
						Unit unit = location[cnt][cnt2].getLocate().getUnit();
						if(unit.getName().equals("Pawn")) {
								if(unit.getIsWhite()) icon = new ImageIcon(ImageIO.read(new File(pawnW)));
								else icon = new ImageIcon(ImageIO.read(new File(pawnB)));
						}
						else if(unit.getName().equals("Knight")) {
							if(unit.getIsWhite()) icon = new ImageIcon(ImageIO.read(new File(knightW)));
							else icon = new ImageIcon(ImageIO.read(new File(knightB)));
						}
						else if(unit.getName().equals("Bishop")) {
							if(unit.getIsWhite()) icon = new ImageIcon(ImageIO.read(new File(bishopW)));
							else icon = new ImageIcon(ImageIO.read(new File(bishopB)));
						}
						else if(unit.getName().equals("Rook")) {
							if(unit.getIsWhite()) icon = new ImageIcon(ImageIO.read(new File(rookW)));
							else icon = new ImageIcon(ImageIO.read(new File(rookB)));
						}
						else if(unit.getName().equals("Queen")) {
							if(unit.getIsWhite()) icon = new ImageIcon(ImageIO.read(new File(queenW)));
							else icon = new ImageIcon(ImageIO.read(new File(queenB)));
						}
						else if(unit.getName().equals("King")) {
							if(unit.getIsWhite()) icon = new ImageIcon(ImageIO.read(new File(kingW)));
							else icon = new ImageIcon(ImageIO.read(new File(kingB)));
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				location[cnt][cnt2].setIcon(icon);
			}
		}
	}
	public class timeLabel extends JLabel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		boolean isWhite;
		public timeLabel(boolean isWhite) {
			this.isWhite=isWhite;
			if(isWhite) this.setText("White Timer : ");
			else this.setText("Black Timer : ");
		}
		public boolean getIsWhite() {
			return this.isWhite;
		}
	}
	public timeLabel getTimer(int num) {
		timeLabel result;
		if(num==1) result = this.timer1;
		else result = this.timer2;
		return result;
	}
	public class conTimeRun implements Runnable {
		private int p = 0;
		private timeLabel timer;
		private boolean isRunnig = false;
		public int getP() {
			return this.p;
		}
		public void setP(int p) {
			this.p = p;
			if(timer.getIsWhite()) {
		        if(p<60)timer.setText("White Timer: "+p+"s");
		        else if(p<3600) timer.setText("White Timer: "+p/60+"m "+p%60+"s");
		        else  timer.setText("White Timer: "+(p/3600)+"h "+
		        (p%3600)/60+"m "+p%60+"s");
			}
			else {
		        if(p<60)timer.setText("Black Timer: "+p+"s");
		        else if(p<3600) timer.setText("Black Timer: "+p/60+"m "+p%60+"s");
		        else  timer.setText("Black Timer: "+(p/3600)+"h "+
		        (p%3600)/60+"m "+p%60+"s");
			}
		}
		public conTimeRun(timeLabel timer) {
			this.timer = timer;
		}
		public boolean getIsRuuning() {
			return this.isRunnig;
		}
		public void setIsRunning(boolean run) {
			this.isRunnig = run;
		}
		public void run() {
			while (true) {
				if(isRunnig) runTimeSet();
				else {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		public void runTimeSet() {
			if(timer.getIsWhite()) {
		        if(p<60)timer.setText("White Timer: "+p+"s");
		        else if(p<3600) timer.setText("White Timer: "+p/60+"m "+p%60+"s");
		        else  timer.setText("White Timer: "+(p/3600)+"h "+
		        (p%3600)/60+"m "+p%60+"s");
		        try {
		            Thread.sleep(1000);
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		        p++;
		        if (p == 1200) {
		        	System.out.println("oh! you spended all time!");
		        	Chess.terminateGame(false);
		        	this.setIsRunning(false);
		        	System.exit(0);
		        }
			}
			else {
		        if(p<60)timer.setText("Black Timer: "+p+"s");
		        else if(p<3600) timer.setText("Black Timer: "+p/60+"m "+p%60+"s");
		        else  timer.setText("Black Timer: "+(p/3600)+"h "+
		        (p%3600)/60+"m "+p%60+"s");
		        try {
		            Thread.sleep(1000);
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		        p++;
		        if (p == 1200) { 
		        	System.out.println("oh! you spended all time!");
		        	Chess.terminateGame(true);
		        	this.setIsRunning(false);
		        	System.exit(0);
		        }
			}
		}
	}
	public class LocationButton extends JButton {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Locate locate;
		public LocationButton(int cnt,int cnt2) {
			locate = Locate.location[cnt][cnt2];
		}
		public Locate getLocate() {
			return this.locate;
		}
	}
	class LocationListener implements ActionListener{
		conTimeRun co1;
		conTimeRun co2;
		public LocationListener(conTimeRun c1, conTimeRun c2) {
			this.co1 = c1;
			this.co2 = c2;
		}
		public void actionPerformed(ActionEvent e) {
			LocationButton l = (LocationButton)e.getSource();
			int x = l.getLocate().getX();
			int y = l.getLocate().getY();
			char c1 = (char)(65+x);
			char c2 = (char)(49+y);
			String str = new Character(c1).toString();
			if(this.co2.isRunnig) Chess.setStr(str.concat(new Character(c2).toString()));
			else System.out.println("You cant control White unit");
		}
	}
	class SaveListener implements ActionListener{
		conTimeRun c1;
		conTimeRun c2;
		public SaveListener(conTimeRun c1, conTimeRun c2) {
			this.c1 = c1;
			this.c2 = c2;
		}
		public void actionPerformed(ActionEvent e) {
			int p1 = c1.getP();
			int p2 = c2.getP();
			boolean whosturn = c1.getIsRuuning();
			new CSVMatrixWriter(System.getProperty("user.dir")).
			writer(Locate.LocateToArrayList(Locate.location) , "maplog.csv", p1, p2, whosturn);
		}
	}
	class LoadListener implements ActionListener{
		conTimeRun c1;
		conTimeRun c2;
		Sender sender;
		public void setSender(Sender sender) {
			this.sender = sender;
		}
		public LoadListener(conTimeRun c1, conTimeRun c2) {
			this.c1 = c1;
			this.c2 = c2;
		}
		public void actionPerformed(ActionEvent e) {
			ArrayList<ArrayList<String>> map =new CSVMatrixReader(System.getProperty("user.dir")).reader("maplog.csv");
			Locate.ArrayListToLocate(map, c1, c2);

			try {
				Thread.sleep(300);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			boardSet();
			sender.setMap(map);
		}
	}
	class RestartListener implements ActionListener{
		conTimeRun c1;
		conTimeRun c2;
		Sender sender;
		public void setSender(Sender sender) {
			this.sender = sender;
		}
		public RestartListener(conTimeRun c1, conTimeRun c2) {
			this.c1 = c1;
			this.c2 = c2;
		}
		public void actionPerformed(ActionEvent e) {
			ArrayList<ArrayList<String>> map =new CSVMatrixReader(System.getProperty("user.dir")).reader("restart.csv");
			Locate.ArrayListToLocate(map, c1, c2);

			try {
				Thread.sleep(300);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			boardSet();
			sender.setMap(map);
		}
	}
	class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	public LocationButton getButton(int a, int b) {
		return this.location[a][b];
	}
	public conTimeRun getConTimeRun(int num) {
		conTimeRun result;
		if(num ==1) result = conTime1Run;
		else result = conTime2Run;
		return result;
	}
}
