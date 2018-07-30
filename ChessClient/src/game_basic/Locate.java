package game_basic;

import java.util.ArrayList;

import added_Function.GUIboard.conTimeRun;

public class Locate {						
	public static Locate[][] location = new Locate[8][8];				//����Ǵ� ��
	public static Locate[][] location2 = new Locate[8][8];				//�ӽ������ ��
	private boolean first = false;
	private int x, y;
	private Unit unit;
	public Locate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setUnit(Unit unit) {
		if(unit == null) this.unit = null;
		else this.unit = unit;
	}
	public Unit getUnit() {
		return this.unit;
	}
	public void giveFirst() {				//�ڱ��� �޽�Ʈ�� ���ֲ���
		if(this.getUnit()!=null) this.getUnit().setFirst(this.first);
	}
	public void setFirst(boolean first) {	//�������� �־����� �װ� �� �����̼��� �޽�Ʈ��
		this.first = first;
	}
	public boolean getFirst() {
		return this.first;
	}
	public Locate up() {					//�밢�� ��������.
		Locate out = null;
		int x = this.x;
		int y = this.y;
		if (y!=7) out = location[x][y+1];
		return out;
	}
	public Locate up(int ins) {				//�������̵�
		Locate out = null;
		int x = this.x;
		int y = this.y;
		if (y + ins <= 7) out = location[x][y+ins];
		return out;
	}
	public Locate down() {
		Locate out = null;
		int x = this.x;
		int y = this.y;
		if (y!=0) out = location[x][y-1];
		return out;
	}
	public Locate down(int ins) {
		Locate out = null;
		int x = this.x;
		int y = this.y;
		if (y- ins >= 0) out = location[x][y-ins];
		return out;
	}
	public Locate left() {
		Locate out = null;
		int x = this.x;
		int y = this.y;
		if (x!=0) out = location[x-1][y];
		return out;
	}
	public Locate left(int ins) {
		Locate out = null;
		int x = this.x;
		int y = this.y;
		if (x - ins >= 0) out = location[x-ins][y];
		return out;
	}
	public Locate right() {
		Locate out = null;
		int x = this.x;
		int y = this.y;
		if (x!=7) out = location[x+1][y];
		return out;
	}
	public Locate right(int ins) {
		Locate out = null;
		int x = this.x;
		int y = this.y;
		if (x + ins <= 7) out = location[x+ins][y];
		return out;
	}
	public boolean isEmpty() {							//ü�������� �̷����̼��� ����ִ�. ���� �����̾���.  false-> �������ִ�.
		return (this.unit == null);
	}
	public boolean isEmpty(int dir, int len) {			//���������� �ƹ������ٴ� �� �������ִ� �Ž�� ���������� false�� �������������û��� //82461379
		boolean out = true;
		if(len==0) return this.isEmpty();
		if(dir == 8){										//����
			if(this.y + len > 7) out = false;			//����� ü���� ������ ����°Ÿ� ������ ����.
			else {
				for(int cnt = 1 ; cnt <= len ; cnt++) {
					if( !(this.up(cnt).isEmpty())) {		//������������ false�� �ϰ� break, ����������!
						out = false;
						break;
					}
				}								//������ �����°͵� �ƴϰ� ������ ��ο� �ִ��͵� �ƴϸ� true�̴�.����ִ�!�����ִ�!
			}
		}
		else if(dir==2) {										//�Ʒ���
			if(this.y - len < 0) out = false;
			else for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.down(cnt).isEmpty())) {		//������������ false�� �ϰ� break, ����������!
					out = false;
					break;
				}
			}
		}
		else if(dir==4){										//��������
			if(this.x - len < 0) out = false;
			else for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.left(cnt).isEmpty())) {		//������������ false�� �ϰ� break, ����������!
					out = false;
					break;
				}
			}
		}
		else if(dir==6){										//����������
			if(this.x + len > 7) out = false;
			else for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.right(cnt).isEmpty())) {		//������������ false�� �ϰ� break, ����������!
					out = false;
					break;
				}
			}
		}
		else if(dir==1){										//���ʾƷ���
			if(this.x - len < 0 || this.y - len < 0) out = false;			//x��ǥ�� y��ǥ�� 0���� �۾����� �ȵȴ�. ü���ǹ��̴�!
			else for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.down(cnt).left(cnt).isEmpty())) {		//������������ false�� �ϰ� break, ����������!
					out = false;
					break;
				}
			}
		}
		else if(dir==3){										//�����ʾƷ���
			if(this.x + len > 7 || this.y - len < 0 ) out = false;
			else for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.down(cnt).right(cnt).isEmpty())) {		//������������ false�� �ϰ� break, ����������!
					out = false;
					break;
				}
			}
		}
		else if(dir==7){										//��������
			if(this.x - len < 0 || this.y + len > 7) out = false;
			else for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.up(cnt).left(cnt).isEmpty())) {		//������������ false�� �ϰ� break, ����������!
					out = false;
					break;
				}
			}
		}
		else if(dir==9){										//����������
			if(this.x + len > 7 || this.y + len > 7) out = false;
			else for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.up(cnt).right(cnt).isEmpty())) {		//������������ false�� �ϰ� break, ����������!
					out = false;
					break;
				}
			}
		}
		else out = false;
		return out;									//true : ���� �ְ� �ű�� �������� false : ���� ���������ְ� �ű⿡ �������־ ������ ���ϼ����ְ� �߸�.
	}
	public boolean roadExist(int dir, int len) {			//location ��ü�� ������ �������ִ� �Ž��
		boolean out = false;
		int x2 = 8, y2 = 8;									
		if(dir == 1) {
			x2 = this.x - len;
			y2 = this.y - len;
		}
		else if(dir == 2) {
			x2 = this.x;
			y2 = this.y - len;
		}
		else if(dir == 3) {
			x2 = this.x + len;
			y2 = this.y - len;
		}
		else if(dir == 4) {
			x2 = this.x - len;
			y2 = this.y;
		}
		else if(dir == 6) {
			x2 = this.x + len;
			y2 = this.y;
		}
		else if(dir == 7) {
			x2 = this.x - len;
			y2 = this.y + len;
		}
		else if(dir == 8) {
			x2 = this.x;
			y2 = this.y + len;
		}
		else if(dir == 9) {
			x2 = this.x + len;
			y2 = this.y + len;
		}															//dir�� ������ input�� �ƴϸ� x2,y2�� ������ ���ְ� out�� true�� ����� ����.
		if( 0 <= x2 && x2 <= 7 && 0 <= y2 && y2 <=7) out =true;
		
		return out;
	}
	public Locate movedPoint(int dir, int len) {
		Locate out = null;
		if(dir==1) out = this.down(len).left(len);
		else if(dir==2) out = this.down(len);
		else if(dir==3) out = this.down(len).right(len);
		else if(dir==4) out = this.left(len);
		else if(dir==6) out = this.right(len);
		else if(dir==7) out = this.up(len).left(len);
		else if(dir==8) out = this.up(len);
		else if(dir==9) out = this.up(len).right(len);
		return out;
	}
	public Locate breakPoint(int dir, int len) {			//null�� �ƴϴ� �Լ��ǹ��� �ƴϰ� �ȿ��ִ� �Լ������� ��λ� ������ �ִ�.�� ��ġ�� ��ȯ���ָ�.
		Locate out = null;
		if(!this.roadExist(dir, len)) { 
			//System.out.println("�ٵ��ǹ��̶� �Լ��� ����.dir,len"+dir+len);
			return null;			//���࿡ �ٵ��ǹ��̸� �Լ��� �����Ƿ� �̸� ����
		}
		if(dir==1){										//���ʾƷ���
			for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.down(cnt).left(cnt).isEmpty())) {	//������������  �ű⼭ ��ġ�� ��ȯ�Ѵ�
					out = this.down(cnt).left(cnt);
					break;
				}
			}
		}
		else if(dir==2) {										//�Ʒ���
			for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.down(cnt).isEmpty())) {				//������������  �ű⼭ ��ġ�� ��ȯ�Ѵ�
					out = this.down(cnt);
					break;
				}
			}
		}
		else if(dir==3){										//�����ʾƷ���
			for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.down(cnt).right(cnt).isEmpty())) {		///������������  �ű⼭ ��ġ�� ��ȯ�Ѵ�
					out = this.down(cnt).right(cnt);
					break;
				}
			}
		}
		else if(dir==4){										//��������
			for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.left(cnt).isEmpty())) {		//������������  �ű⼭ ��ġ�� ��ȯ�Ѵ�
					out = this.left(cnt);
					break;
				}
			}
		}
		else if(dir==6){										//����������
			for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.right(cnt).isEmpty())) {		//������������  �ű⼭ ��ġ�� ��ȯ�Ѵ�
					out = this.right(cnt);
					break;
				}
			}
		}
		else if(dir==7){										//��������
			for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.up(cnt).left(cnt).isEmpty())) {		//������������  �ű⼭ ������ ��ȯ�Ѵ�
					out = this.up(cnt).left(cnt);
					break;
				}
			}
		}
		else if(dir == 8){										//����
			for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.up(cnt).isEmpty())) {		//������������  �ű⼭ ������ ��ȯ�Ѵ�
					out = this.up(cnt);
					break;
				}
			}							
		}
		else if(dir==9){										//����������
			for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.up(cnt).right(cnt).isEmpty())) {		//������������  �ű⼭ ������ ��ȯ�Ѵ�
					out = this.up(cnt).right(cnt);
					break;
				}
			}
		}
		else if (len == 0) out = this;
		return out;
	}
	public static void saveLocate() {						//unit�� location�� ������ location�� �ڱ����� �Ҵ��Ŵ	//location1 -> location2
		for (int cnt2 = 0 ; cnt2 < 8 ; cnt2++) {				//location�� ������ �׳� ȥ�� ����ų������.
			for (int cnt = 0 ; cnt < 8 ; cnt++) {		//�� unit�ϳ��� location����������.
				location2[cnt][cnt2].setUnit(location[cnt][cnt2].getUnit());			//unit��ġ��������� ���´������
				if(location[cnt][cnt2].isEmpty()) location2[cnt][cnt2].setFirst(false);//�״�� unit first�� location2�� take�ؼ� ����
				else location2[cnt][cnt2].setFirst(location[cnt][cnt2].getFirst());
			}														
		}
	}
	public static void loadLocate() {  //location2-> location1
		for (int cnt = 0 ; cnt < 8 ; cnt++) {				
			for (int cnt2 = 0 ; cnt2 < 8 ; cnt2++) {
				location[cnt][cnt2].setUnit(location2[cnt][cnt2].getUnit());			//unit��ġ�ε�, ������ ���´�θ�����ش�.
				if(location[cnt][cnt2].getUnit()!=null)
						location[cnt][cnt2].getUnit().setLocate(location[cnt][cnt2]); 	//�׸��� �״�� �����̼ǵ� ���󰡼� ���ֵ鵵 �����̼�����������
				
				if(location2[cnt][cnt2].isEmpty()) location[cnt][cnt2].setFirst(false);		
				else location[cnt][cnt2].setFirst(location2[cnt][cnt2].getFirst());		//�״�� unit first���θ� location�� ����
				location[cnt][cnt2].giveFirst();
			}														
		}
	}
	public static ArrayList<ArrayList<String>> LocateToArrayList (Locate[][] locate) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>> ();
		for (int y = 7 ; y >=0 ; y--) {
			ArrayList<String> row = new ArrayList<String>();
			result.add(row);
			for (int x = 0 ; x < 8 ; x++) {
				String str ="";
				if(locate[x][y].getUnit()==null) str = "null";
				else {
					Unit unit = locate[x][y].getUnit();
					int cnt = unit.getCnt();
					if(unit.getName().equals("Pawn")) str = str.concat("P");
					else if(unit.getName().equals("Knight")) str = str.concat("N");
					else if(unit.getName().equals("Bishop")) str = str.concat("B");
					else if(unit.getName().equals("Rook")) str = str.concat("R");
					else if(unit.getName().equals("Queen")) str = str.concat("Q");
					else str = str.concat("K");
					
					if(cnt<10) str = str.concat(String.valueOf(cnt));
					else str = str.concat(String.valueOf((char)(cnt+55)));
					
					if(unit.isFirst()) str = str.concat("T");
					else str = str.concat("F");
				}
				row.add(str);
			}														
		}
		return result;
	}
	public static void ArrayListToLocate(ArrayList<ArrayList<String>> dataList, conTimeRun c1, conTimeRun c2) {
		for (int y = 0 ; y < 8 ; y++) {
			for (int x = 0 ; x < 8 ; x++) {
				String str = dataList.get(y).get(x);
				int y2 = 7 - y;
				Locate where = Locate.location[x][y2];
				if(str.equals("null")) where.setUnit(null);
				else {
					int cnt = (int)str.charAt(1);
					if(cnt >= 65 ) cnt -= 55;
					else cnt -= 48;
					if(str.charAt(0) == 'P') where.setUnit(Unit.pawn[cnt]);
					else if(str.charAt(0) == 'N') where.setUnit(Unit.knight[cnt]);
					else if(str.charAt(0) == 'B') where.setUnit(Unit.bishop[cnt]);
					else if(str.charAt(0) == 'R') where.setUnit(Unit.rook[cnt]);
					else if(str.charAt(0) == 'Q') where.setUnit(Unit.queen[cnt]);
					else if(str.charAt(0) == 'K') where.setUnit(Unit.king[cnt]);
					where.getUnit().setLocate(where); 				//�״�� ���󰡼� ���ֵ� ������Ʈ�� ��������.
					
					if(str.charAt(2) == 'T') where.setFirst(true);	//T�� T�λ��·� area�� �÷���
					else where.setFirst(false);						//�ƴ� ����
					where.getUnit().setFirst(where.getFirst());		//�÷��׵� ������ unit�� ����.
				}
			}														
		}
		
		for(int cnt = 0 ; cnt < 16 ; cnt++) {				//���� ���ֵ� �� ����, �״ϱ� ���ֵ��� �����̼��� �ƴµ� �����̼��� ������ �𸣴� ���. ���ֵ��� �����̼��� ����.
			if(Unit.pawn[cnt].getLocate()!= null && Unit.pawn[cnt].getLocate().getUnit() != null&&
					!Unit.pawn[cnt].getLocate().getUnit().equals(Unit.pawn[cnt])){		//���������䵥 �����̼��� ������ ������ �����ƴϴ�
				//System.out.println("����ƽ��ϴ�."+cnt);
				Unit.pawn[cnt].onlySetLocate(null);
			}
			else if(Unit.pawn[cnt].getLocate() != null && Unit.pawn[cnt].getLocate().getUnit() == null) {
				//System.out.println("����ƽ��ϴ�."+cnt);
				Unit.pawn[cnt].onlySetLocate(null);
			}
			if(cnt<4) {
				if(Unit.knight[cnt].getLocate()!= null &&Unit.knight[cnt].getLocate().getUnit() != null&&
						!Unit.knight[cnt].getLocate().getUnit().equals(Unit.knight[cnt])) {
					Unit.knight[cnt].onlySetLocate(null);
				}
				else if(Unit.knight[cnt].getLocate() != null && Unit.knight[cnt].getLocate().getUnit() == null) {
					System.out.println("����ƽ��ϴ�."+cnt);
					Unit.knight[cnt].onlySetLocate(null);
				}
				if(Unit.bishop[cnt].getLocate()!= null &&Unit.bishop[cnt].getLocate().getUnit() != null&&
						!Unit.bishop[cnt].getLocate().getUnit().equals(Unit.bishop[cnt])) {
					Unit.bishop[cnt].onlySetLocate(null);
				}
				else if(Unit.bishop[cnt].getLocate() != null && Unit.bishop[cnt].getLocate().getUnit() == null) {
					System.out.println("����ƽ��ϴ�."+cnt);
					Unit.bishop[cnt].onlySetLocate(null);
				}
				if(Unit.rook[cnt].getLocate()!= null &&Unit.rook[cnt].getLocate().getUnit() != null&&
						!Unit.rook[cnt].getLocate().getUnit().equals(Unit.rook[cnt])) {
					Unit.rook[cnt].onlySetLocate(null);
				}
				else if(Unit.rook[cnt].getLocate() != null && Unit.rook[cnt].getLocate().getUnit() == null) {
					System.out.println("����ƽ��ϴ�."+cnt);
					Unit.rook[cnt].onlySetLocate(null);
				}
			}
			if(cnt<2) {
				if(Unit.queen[cnt].getLocate()!= null &&Unit.queen[cnt].getLocate().getUnit() != null&&
						!Unit.queen[cnt].getLocate().getUnit().equals(Unit.queen[cnt])) {
					Unit.queen[cnt].onlySetLocate(null);
				}
				else if(Unit.queen[cnt].getLocate() != null && Unit.queen[cnt].getLocate().getUnit() == null) {
					System.out.println("����ƽ��ϴ�."+cnt);
					Unit.queen[cnt].onlySetLocate(null);
				}
			}
		}
		int p1 = Integer.parseInt(dataList.get(8).get(0));
		int p2 = Integer.parseInt(dataList.get(8).get(1));
		c1.setP(p1);
		c2.setP(p2);
		saveLocate();
		if(dataList.get(8).get(2).equals("TRUE") && c2.getIsRuuning()) {
			Chess.turn = "TRUE";
			Chess.setStr("continue");
		}
		if(dataList.get(8).get(2).equals("FALSE") && c1.getIsRuuning()) {
			Chess.turn = "FALSE";
			Chess.setStr("continue");
		}
	}
	public boolean isThereSome(int dir, int len) {			//�װ��� �����̼��� �ְ� ����ġ�� �ƹ������� ������ �������ִ� �Ž��
		return (this.roadExist(dir, len) && this.movedPoint(dir, len).getUnit()!=null);
	}
	public boolean isThereAlly(int dir,int len){			//�װ��� �����̼��� �ְ� ����ġ�� �츮������ ������ �������ִ� �Ž��
		return (this.roadExist(dir, len) && this.movedPoint(dir, len).getUnit()!=null 
				&& this.movedPoint(dir, len).getUnit().getIsWhite() == this.getUnit().getIsWhite());
	}
	public boolean isThereAlly(int dir,int len,boolean isWhite){			//�װ��� �����̼��� �ְ� ����ġ�� �츮������ ������ �������ִ� �Ž��
		return (this.roadExist(dir, len) && this.movedPoint(dir, len).getUnit()!=null 
				&& this.movedPoint(dir, len).getUnit().getIsWhite() == isWhite);
	}
	public boolean isThereEnemy(int dir, int len) {			//�װ��� �����̼��� �ְ� ����ġ�� �������� ������ �������ִ� �Ž��
		//System.out.println(this.x+"^^"+this.y);
		return (this.roadExist(dir, len) && this.movedPoint(dir, len).getUnit()!=null 
				&& this.movedPoint(dir, len).getUnit().getIsWhite() != this.getUnit().getIsWhite());
	}
	public boolean isThereEnemy(int dir, int len, boolean isWhite) {			//�װ��� �����̼��� �ְ� ����ġ�� �������� ������ �������ִ� �Ž��
		return (this.roadExist(dir, len) && this.movedPoint(dir, len).getUnit()!=null 
				&& this.movedPoint(dir, len).getUnit().getIsWhite() != isWhite);
	}
}
