package game_basic;

import unit.Bishop;
import unit.King;
import unit.Knight;
import unit.Pawn;
import unit.Queen;
import unit.Rook;

abstract public class Unit {
		//PAWN 16�� �迭�� �������ݴϴ�.
	public static Pawn[] pawn = new Pawn[16];								//white pawn�� 0-7, black pawn�� 8-15�Դϴ�.
	public static King[] king = new King[2];								//0-1 :white , 2-3:black(king,queen)
	public static Queen[] queen = new Queen[2];									
	public static Knight[] knight = new Knight[4];							//0:white, 1 :black (Knight,bishop,rook)
	public static Bishop[] bishop = new Bishop[4];
	public static Rook[] rook = new Rook[4];
	private Unit murder;
	private boolean first;
	private boolean isWhite;
	private Locate location;
	private String name;
	private int cnt;
	public Unit(Locate location, boolean isWhite, String name, int cnt) {
		this.location = location;
		this.isWhite = isWhite;
		this.name = name;
		this.cnt = cnt;
		this.first=  true;
		location.setUnit(this); 					//��ġ�� unit�� �Է�
		location.setFirst(this.first);
	}
	public int getCnt() {
		return this.cnt;
	}
	public void setName(String str) {
		this.name = str;
	}
	public boolean isFirst() {
		return this.first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public void noMoreFirst() {
		this.first = false;
	}
	
	public void setLocate(Locate location) {
		this.location = location;
		location.setUnit(this);
	}
	public void onlySetLocate(Locate location) {
		this.location = location;
	}
	public Locate getLocate() {
		return this.location;
	}
	
	public boolean getIsWhite() {
		return this.isWhite;
	}
	
	public String getName() {
		return this.name;
	}
	public void out() {
		this.getLocate().setUnit(null);
		this.getLocate().setFirst(false);
		this.location = null;
	}
	public void die(Unit murder) {
		this.murder = murder;
		Chess.setVictim(this);
		this.out();
	}
	public Unit getMurder() {
		return this.murder;
	}
	public void notBeAbleToMovePrint() {
		System.out.println(this.getName() + " - I can not move. Selcect other unit.");
	}
	public void promote() {
		
	}
	abstract public boolean move(int ins);
	abstract public boolean move(int dir, int len);
	abstract public boolean beAbleToMove();
	abstract public boolean beAbleToKillKing();
	public boolean beAbleToGoTo(Locate location) {
		Locate.saveLocate();
		boolean out = false;
		int x = this.getLocate().getX();
		int y = this.getLocate().getY();
		int x2 = location.getX();
		int y2 = location.getY();
		String name = this.getName();
		if(name == "Pawn") {
			if( x2 == x - 1 && y2 == y) out = this.move(4);			//����
			else if( x2 == x + 1 && y2 == y) out = this.move(6);		//������
			else if( x2 == x && y2 == y+1) out = this.move(8);		//���ѹ�
			else if( x2 == x && y2 == y+2) out = this.move(88);		//���ι�
			else if( x2 == x && y2 == y-1) out = this.move(2);		//�Ʒ��ѹ�
			else if( x2 == x && y2 == y-2) out = this.move(22);		//�Ʒ��ι�
			else if( x2 == x-1 && y2 == y-1) out = this.move(1);		//�»�
			else if( x2 == x-1 && y2 == y+1) out = this.move(7);		//����
			else if( x2 == x+1 && y2 == y+1) out = this.move(9);		//���
			else if( x2 == x+1 && y2 == y-1) out = this.move(3);		//����
		}
		else if(name == "Knight") {
			if( x2 == x - 1 && y2 == y+2) out = this.move(884);				//�����
			else if( x2 == x + 1 && y2 == y + 2) out = this.move(886);		//����
			else if( x2 == x - 1 && y2 == y - 2) out = this.move(224);		//������
			else if( x2 == x + 1 && y2 == y - 2) out = this.move(226);		//���Ͽ�
			else if( x2 == x - 2 && y2 == y + 1) out = this.move(448);		//���»�
			else if( x2 == x - 2 && y2 == y - 1) out = this.move(442);		//������
			else if( x2 == x + 2 && y2 == y + 1) out = this.move(668);		//����
			else if( x2 == x + 2 && y2 == y - 1) out = this.move(662);		//�����
		}
		else if(name == "Bishop" && Math.abs(x-x2) == Math.abs(y-y2)) {			//1�������� x2�� y2�� ��ǲ�� ������ ��.
			int len = Math.abs(x-x2);
			int dir = 0;
			if(x2 < x && y2 > y) dir = 7;		//����
			else if (x2 > x && y2 > y) dir = 9;	//���
			else if (x2 < x && y2 < y) dir = 1;	//����
			else if (x2 > x && y2 < y) dir = 3;	//�Ͽ�
			out = this.move(dir, len);
		}
		
		else if(name == "Rook") {
			int len = 0;
			int dir = 0;
			if(x2 == x && y2 > y) {					//��
				dir = 8;
				len = y2 - y;
			}
			else if (x2 == x && y2 <  y) {			//��
				dir = 2;
				len = y - y2;
			}
			else if (x2 <  x && y2 == y) {			//��
				dir = 4;
				len = x - x2;
			}
			else if (x2 >  x && y2 == y) {			//��
				dir = 6;
				len = x2 - x;
			}
			out = this.move(dir, len);
		}
		
		else if(name == "Queen") {
			int len = 0;
			int dir = 0;
			if(x2 == x && y2 > y) {					//��
				dir = 8;
				len = y2 - y;
			}
			else if (x2 == x && y2 <  y) {			//��
				dir = 2;
				len = y - y2;
			}
			else if (x2 <  x && y2 == y) {			//��
				dir = 4;
				len = x - x2;
			}
			else if (x2 >  x && y2 == y) {			//��
				dir = 6;
				len = x2 - x;
			}
			else if( Math.abs(x-x2) == Math.abs(y-y2)) {
				len = Math.abs(x-x2);
				if(x2 < x && y2 > y) dir = 7;		//����
				else if (x2 > x && y2 > y) dir = 9;	//���
				else if (x2 < x && y2 < y) dir = 1;	//����
				else if (x2 > x && y2 < y) dir = 3;	//�Ͽ�
			}
			out = this.move(dir, len);
		}
		else if(name == "King") {
			int dir = 0;
			if(x2 == x && y2 == y + 1) 		 dir = 8;			//��
			else if (x2 == x   && y2 == y-1) dir = 2;			//��
			else if (x2 == x-1 && y2 == y) dir = 4;			//��
			else if (x2 == x+1 && y2 == y) dir = 6;			//��
			else if(x2 == x-1 && y2 == y + 1) dir = 7;		//����
			else if (x2 == x+1 && y2 == y + 1) dir = 9;	//���
			else if (x2 == x-1 && y2 == y-1) dir = 1;	//����
			else if (x2 == x+1 && y2 == y-1) dir = 3;	//�Ͽ�
			
			out = this.move(dir);
		}
		Locate.loadLocate();
		return out;
	}
}

