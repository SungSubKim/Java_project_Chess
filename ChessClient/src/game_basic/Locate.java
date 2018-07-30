package game_basic;

import java.util.ArrayList;

import added_Function.GUIboard.conTimeRun;

public class Locate {						
	public static Locate[][] location = new Locate[8][8];				//진행되는 맵
	public static Locate[][] location2 = new Locate[8][8];				//임시저장용 맵
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
	public void giveFirst() {				//자기의 펄스트를 유닛껄로
		if(this.getUnit()!=null) this.getUnit().setFirst(this.first);
	}
	public void setFirst(boolean first) {	//진리값이 주어지면 그걸 이 로케이션의 펄스트로
		this.first = first;
	}
	public boolean getFirst() {
		return this.first;
	}
	public Locate up() {					//대각은 구현안함.
		Locate out = null;
		int x = this.x;
		int y = this.y;
		if (y!=7) out = location[x][y+1];
		return out;
	}
	public Locate up(int ins) {				//오버라이딩
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
	public boolean isEmpty() {							//체스판위의 이로케이션은 비어있다. 위에 유닛이없다.  false-> 유닛이있다.
		return (this.unit == null);
	}
	public boolean isEmpty(int dir, int len) {			//길이있을땐 아무도없다는 걸 보장해주는 매써드 길이잇을떈 false는 누군가있음을시사함 //82461379
		boolean out = true;
		if(len==0) return this.isEmpty();
		if(dir == 8){										//위로
			if(this.y + len > 7) out = false;			//결과가 체스판 밖으로 벗어나는거면 갈수가 없다.
			else {
				for(int cnt = 1 ; cnt <= len ; cnt++) {
					if( !(this.up(cnt).isEmpty())) {		//유닛이있으면 false로 하고 break, 갈수가없다!
						out = false;
						break;
					}
				}								//밖으로 나가는것도 아니고 유닛이 경로에 있던것도 아니면 true이다.비어있다!갈수있다!
			}
		}
		else if(dir==2) {										//아래로
			if(this.y - len < 0) out = false;
			else for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.down(cnt).isEmpty())) {		//유닛이있으면 false로 하고 break, 갈수가없다!
					out = false;
					break;
				}
			}
		}
		else if(dir==4){										//왼쪽으로
			if(this.x - len < 0) out = false;
			else for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.left(cnt).isEmpty())) {		//유닛이있으면 false로 하고 break, 갈수가없다!
					out = false;
					break;
				}
			}
		}
		else if(dir==6){										//오른쪽으로
			if(this.x + len > 7) out = false;
			else for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.right(cnt).isEmpty())) {		//유닛이있으면 false로 하고 break, 갈수가없다!
					out = false;
					break;
				}
			}
		}
		else if(dir==1){										//왼쪽아래로
			if(this.x - len < 0 || this.y - len < 0) out = false;			//x좌표나 y좌표가 0보다 작아지면 안된다. 체스판밖이다!
			else for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.down(cnt).left(cnt).isEmpty())) {		//유닛이있으면 false로 하고 break, 갈수가없다!
					out = false;
					break;
				}
			}
		}
		else if(dir==3){										//오른쪽아래로
			if(this.x + len > 7 || this.y - len < 0 ) out = false;
			else for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.down(cnt).right(cnt).isEmpty())) {		//유닛이있으면 false로 하고 break, 갈수가없다!
					out = false;
					break;
				}
			}
		}
		else if(dir==7){										//왼쪽위로
			if(this.x - len < 0 || this.y + len > 7) out = false;
			else for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.up(cnt).left(cnt).isEmpty())) {		//유닛이있으면 false로 하고 break, 갈수가없다!
					out = false;
					break;
				}
			}
		}
		else if(dir==9){										//오른쪽위로
			if(this.x + len > 7 || this.y + len > 7) out = false;
			else for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.up(cnt).right(cnt).isEmpty())) {		//유닛이있으면 false로 하고 break, 갈수가없다!
					out = false;
					break;
				}
			}
		}
		else out = false;
		return out;									//true : 길이 있고 거기로 갈수있음 false : 길이 없을수도있고 거기에 유닛이있어서 못가는 거일수도있고 잘모름.
	}
	public boolean roadExist(int dir, int len) {			//location 객체가 있음을 보장해주는 매써드
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
		}															//dir이 규정된 input이 아니면 x2,y2에 영향을 못주고 out도 true로 만들수 없다.
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
	public Locate breakPoint(int dir, int len) {			//null이 아니다 함수판밖이 아니고 안에있는 함수판위에 경로상에 유닛이 있다.그 위치를 반환해주마.
		Locate out = null;
		if(!this.roadExist(dir, len)) { 
			//System.out.println("바둑판밖이라서 함수가 터짐.dir,len"+dir+len);
			return null;			//만약에 바둑판밖이면 함수가 터지므로 미리 방지
		}
		if(dir==1){										//왼쪽아래로
			for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.down(cnt).left(cnt).isEmpty())) {	//유닛이있으면  거기서 위치를 반환한다
					out = this.down(cnt).left(cnt);
					break;
				}
			}
		}
		else if(dir==2) {										//아래로
			for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.down(cnt).isEmpty())) {				//유닛이있으면  거기서 위치를 반환한다
					out = this.down(cnt);
					break;
				}
			}
		}
		else if(dir==3){										//오른쪽아래로
			for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.down(cnt).right(cnt).isEmpty())) {		///유닛이있으면  거기서 위치를 반환한다
					out = this.down(cnt).right(cnt);
					break;
				}
			}
		}
		else if(dir==4){										//왼쪽으로
			for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.left(cnt).isEmpty())) {		//유닛이있으면  거기서 위치를 반환한다
					out = this.left(cnt);
					break;
				}
			}
		}
		else if(dir==6){										//오른쪽으로
			for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.right(cnt).isEmpty())) {		//유닛이있으면  거기서 위치를 반환한다
					out = this.right(cnt);
					break;
				}
			}
		}
		else if(dir==7){										//왼쪽위로
			for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.up(cnt).left(cnt).isEmpty())) {		//유닛이있으면  거기서 유닛을 반환한다
					out = this.up(cnt).left(cnt);
					break;
				}
			}
		}
		else if(dir == 8){										//위로
			for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.up(cnt).isEmpty())) {		//유닛이있으면  거기서 유닛을 반환한다
					out = this.up(cnt);
					break;
				}
			}							
		}
		else if(dir==9){										//오른쪽위로
			for(int cnt = 1 ; cnt <= len ; cnt++) {
				if( !(this.up(cnt).right(cnt).isEmpty())) {		//유닛이있으면  거기서 유닛을 반환한다
					out = this.up(cnt).right(cnt);
					break;
				}
			}
		}
		else if (len == 0) out = this;
		return out;
	}
	public static void saveLocate() {						//unit은 location을 먹으면 location을 자기한테 할당시킴	//location1 -> location2
		for (int cnt2 = 0 ; cnt2 < 8 ; cnt2++) {				//location은 유닛을 그냥 혼자 가리킬수있음.
			for (int cnt = 0 ; cnt < 8 ; cnt++) {		//즉 unit하나에 location여러개가능.
				location2[cnt][cnt2].setUnit(location[cnt][cnt2].getUnit());			//unit위치저장없으면 없는대로저장
				if(location[cnt][cnt2].isEmpty()) location2[cnt][cnt2].setFirst(false);//그당시 unit first를 location2로 take해서 저장
				else location2[cnt][cnt2].setFirst(location[cnt][cnt2].getFirst());
			}														
		}
	}
	public static void loadLocate() {  //location2-> location1
		for (int cnt = 0 ; cnt < 8 ; cnt++) {				
			for (int cnt2 = 0 ; cnt2 < 8 ; cnt2++) {
				location[cnt][cnt2].setUnit(location2[cnt][cnt2].getUnit());			//unit위치로드, 없으면 없는대로만들어준다.
				if(location[cnt][cnt2].getUnit()!=null)
						location[cnt][cnt2].getUnit().setLocate(location[cnt][cnt2]); 	//그리고 그대로 로케이션도 따라가서 유닛들도 로케이션지정시켜줌
				
				if(location2[cnt][cnt2].isEmpty()) location[cnt][cnt2].setFirst(false);		
				else location[cnt][cnt2].setFirst(location2[cnt][cnt2].getFirst());		//그당시 unit first여부를 location에 저장
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
					where.getUnit().setLocate(where); 				//그대로 따라가서 유닛도 로케이트를 지정해줌.
					
					if(str.charAt(2) == 'T') where.setFirst(true);	//T면 T인상태로 area을 플래그
					else where.setFirst(false);						//아님 말고
					where.getUnit().setFirst(where.getFirst());		//플래그된 정보를 unit에 전달.
				}
			}														
		}
		
		for(int cnt = 0 ; cnt < 16 ; cnt++) {				//죽은 유닛들 고리 제거, 그니까 유닛들은 로케이션을 아는데 로케이션은 유닛을 모르는 경우. 유닛들의 로케이션을 지움.
			if(Unit.pawn[cnt].getLocate()!= null && Unit.pawn[cnt].getLocate().getUnit() != null&&
					!Unit.pawn[cnt].getLocate().getUnit().equals(Unit.pawn[cnt])){		//난원래여긴데 로케이션이 지정한 유닛은 내가아니다
				//System.out.println("실행됐습니다."+cnt);
				Unit.pawn[cnt].onlySetLocate(null);
			}
			else if(Unit.pawn[cnt].getLocate() != null && Unit.pawn[cnt].getLocate().getUnit() == null) {
				//System.out.println("실행됐습니다."+cnt);
				Unit.pawn[cnt].onlySetLocate(null);
			}
			if(cnt<4) {
				if(Unit.knight[cnt].getLocate()!= null &&Unit.knight[cnt].getLocate().getUnit() != null&&
						!Unit.knight[cnt].getLocate().getUnit().equals(Unit.knight[cnt])) {
					Unit.knight[cnt].onlySetLocate(null);
				}
				else if(Unit.knight[cnt].getLocate() != null && Unit.knight[cnt].getLocate().getUnit() == null) {
					System.out.println("실행됐습니다."+cnt);
					Unit.knight[cnt].onlySetLocate(null);
				}
				if(Unit.bishop[cnt].getLocate()!= null &&Unit.bishop[cnt].getLocate().getUnit() != null&&
						!Unit.bishop[cnt].getLocate().getUnit().equals(Unit.bishop[cnt])) {
					Unit.bishop[cnt].onlySetLocate(null);
				}
				else if(Unit.bishop[cnt].getLocate() != null && Unit.bishop[cnt].getLocate().getUnit() == null) {
					System.out.println("실행됐습니다."+cnt);
					Unit.bishop[cnt].onlySetLocate(null);
				}
				if(Unit.rook[cnt].getLocate()!= null &&Unit.rook[cnt].getLocate().getUnit() != null&&
						!Unit.rook[cnt].getLocate().getUnit().equals(Unit.rook[cnt])) {
					Unit.rook[cnt].onlySetLocate(null);
				}
				else if(Unit.rook[cnt].getLocate() != null && Unit.rook[cnt].getLocate().getUnit() == null) {
					System.out.println("실행됐습니다."+cnt);
					Unit.rook[cnt].onlySetLocate(null);
				}
			}
			if(cnt<2) {
				if(Unit.queen[cnt].getLocate()!= null &&Unit.queen[cnt].getLocate().getUnit() != null&&
						!Unit.queen[cnt].getLocate().getUnit().equals(Unit.queen[cnt])) {
					Unit.queen[cnt].onlySetLocate(null);
				}
				else if(Unit.queen[cnt].getLocate() != null && Unit.queen[cnt].getLocate().getUnit() == null) {
					System.out.println("실행됐습니다."+cnt);
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
	public boolean isThereSome(int dir, int len) {			//그곳에 로케이션이 있고 그위치에 아무유닛이 있음을 보장해주는 매써드
		return (this.roadExist(dir, len) && this.movedPoint(dir, len).getUnit()!=null);
	}
	public boolean isThereAlly(int dir,int len){			//그곳에 로케이션이 있고 그위치에 우리유닛이 있음을 보장해주는 매써드
		return (this.roadExist(dir, len) && this.movedPoint(dir, len).getUnit()!=null 
				&& this.movedPoint(dir, len).getUnit().getIsWhite() == this.getUnit().getIsWhite());
	}
	public boolean isThereAlly(int dir,int len,boolean isWhite){			//그곳에 로케이션이 있고 그위치에 우리유닛이 있음을 보장해주는 매써드
		return (this.roadExist(dir, len) && this.movedPoint(dir, len).getUnit()!=null 
				&& this.movedPoint(dir, len).getUnit().getIsWhite() == isWhite);
	}
	public boolean isThereEnemy(int dir, int len) {			//그곳에 로케이션이 있고 그위치에 적유닛이 있음을 보장해주는 매써드
		//System.out.println(this.x+"^^"+this.y);
		return (this.roadExist(dir, len) && this.movedPoint(dir, len).getUnit()!=null 
				&& this.movedPoint(dir, len).getUnit().getIsWhite() != this.getUnit().getIsWhite());
	}
	public boolean isThereEnemy(int dir, int len, boolean isWhite) {			//그곳에 로케이션이 있고 그위치에 적유닛이 있음을 보장해주는 매써드
		return (this.roadExist(dir, len) && this.movedPoint(dir, len).getUnit()!=null 
				&& this.movedPoint(dir, len).getUnit().getIsWhite() != isWhite);
	}
}
