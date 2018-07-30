package unit;

import game_basic.Locate;
import game_basic.Unit;

public class Pawn extends Unit {
	boolean promoted = false;
	public Pawn(Locate location,boolean isWhite,int cnt) {
		super(location,isWhite,"Pawn",cnt);
	}															// 82461379
	public void promote() {
		this.promoted=true;
		this.setName("Queen");
		if(this.getIsWhite())System.out.println("White pawn: haha, I am promoted to "+this.getName()+"!");
		else System.out.println("Black pawn: haha, I am promoted to "+this.getName()+"!");
	}
	public boolean move(int dir) {		// 위8왼쪽4오른쪽6  왼쪽위kill:7 6오른쪽위kill:9 두번move : 88
		boolean out;
		Locate where = this.getLocate();
		int x = where.getX();
		int y = where.getY();
		this.out();											//잠시 보드판에서 나가있는다. 그후 다시 setlocation으로 착륙
		
		if (dir==4  && where.isEmpty(4, 1)) this.setLocate(where.left());
		else if (dir==6 && where.isEmpty(6, 1)) this.setLocate(where.right());
		
		if(this.getIsWhite()) {							//백색폰 기준무브	, 즉 백색폰인데 맞지않는 direction을 입력하면 작동을안함
			if (dir==8  && where.isEmpty(8, 1)) this.setLocate(where.up());
			else if (dir==88 && where.isEmpty(8, 2)&& this.isFirst()) { //첫턴에만 두번움직일수있습니다.
				this.setLocate(where.up(2));
			}
			else if (where.roadExist(7, 1) && !(where.isEmpty(7, 1)) && dir==7 ) {			//좌상에 죽일 유닛이있을때
				if(Locate.location[x-1][y+1].getUnit().getIsWhite() == this.getIsWhite()) out= false;	//같은팀은 죽이지 않습니다.
				else {
					Locate.location[x-1][y+1].getUnit().die(this);			//죽이고 그자리에이동
					this.setLocate(where.up().left());
				}
				
			}
			else if (where.roadExist(9, 1) && !(where.isEmpty(9, 1)) && dir==9) {
				if(Locate.location[x+1][y+1].getUnit().getIsWhite() == this.getIsWhite()) {
					System.out.println("can not kill same team ");
					out= false;	//같은팀은 죽이지 않습니다.
				}
				else {
					Locate.location[x+1][y+1].getUnit().die(this);
					this.setLocate(where.up().right());
				}
			}
		}
		
		else {											//흑색폰 기준무브
			if (dir==2  && where.isEmpty(2, 1) ) this.setLocate(where.down());
			else if (dir==22 && where.isEmpty(2, 2)&& this.isFirst())this.setLocate(where.down(2));
			else if (where.roadExist(1, 1) && !(where.isEmpty(1, 1)) && dir==1 ) {
				if(Locate.location[x-1][y-1].getUnit().getIsWhite() == this.getIsWhite()) {
					System.out.println("can not kill same team TT");
					out= false;	//같은팀은 죽이지 않습니다.
				}
				else {
					Locate.location[x-1][y-1].getUnit().die(this);			//죽이고 그자리에이동
					this.setLocate(where.down().left());
				}
			}
			else if (where.roadExist(3, 1) && !(where.isEmpty(3, 1)) && dir==3) {
				if(Locate.location[x+1][y-1].getUnit().getIsWhite() == this.getIsWhite()) {
					System.out.println("can not kill same team TT");
					out= false;	//같은팀은 죽이지 않습니다.
				}
				else {
					Locate.location[x+1][y-1].getUnit().die(this);
					this.setLocate(where.down().right());
					}
			}
		}
		if(this.getLocate() == null) {				//이동실패
			this.setLocate(where);			
			out = false;
		}
		else {											//이동성공
			this.noMoreFirst();
			out = true;
		}
		return out;										//바뀌었으면 true,같지않으면 false를 반환한다.
	}
	public boolean beAbleToMove() {
		boolean result;
		if(this.getIsWhite()) {			//백일때
			if(this.getLocate().isThereSome(8, 1) && this.getLocate().isThereSome(4, 1)
					&& this.getLocate().isThereSome(6, 1) && !this.getLocate().isThereSome(7, 1)
					&& !this.getLocate().isThereSome(9, 1)) result = false;	//앞옆 다막히고 죽일적없을때 움직일 수 없다.
			else result = true;		//움직일 수 없다
		}
		else {							//흑일때
			if(this.getLocate().isThereSome(2, 1) && this.getLocate().isThereSome(4, 1)
					&& this.getLocate().isThereSome(6, 1) && !this.getLocate().isThereSome(1, 1)
					&& !this.getLocate().isThereSome(3, 1)) result = false;	//앞옆 다막히고 죽일적없을때 움직일 수 없다.
			else result = true;		//움직일 수 없다
		}
		return result;	
	}
	public boolean beAbleToKillKing() {
		if(this.getLocate() == null) return false;
		boolean result = false;
		if(this.getName().equals("Pawn")) {
			if(this.getIsWhite()) {
				if(this.getLocate().isThereEnemy(7, 1) &&									//상좌에 누군가 있고 그 누군가가 킹이면 폰은 킹을 끝장낼수있따->즉체크
						this.getLocate().movedPoint(7, 1).getUnit() == Unit.king[1]) {
					result = true;
				}
				else if(this.getLocate().isThereEnemy(9, 1) &&
						this.getLocate().movedPoint(9, 1).getUnit() == Unit.king[1]) {
					result = true;
				}
			}
			else {
				if(this.getLocate().isThereEnemy(1, 1) &&									
						this.getLocate().movedPoint(1, 1).getUnit() == Unit.king[0]) {
					result = true;
				}
				else if(this.getLocate().isThereEnemy(3, 1) &&
						this.getLocate().movedPoint(3, 1).getUnit() == Unit.king[0]) {
					result = true;
				}
			}
		}
		else if (this.getName().equals("Queen")) {
			result = false;
			int num[] = {1, 2, 3, 4, 6, 7, 8, 9};
			for(int cnt : num) {
				int cnt2;
				for(cnt2 = 1; cnt2 <= 7 ; cnt2++) if(!this.getLocate().roadExist(cnt,cnt2)) break; //cnt방향으로 cnt2길이만큼
				int len = cnt2 - 1;									//len은 길이있는 최후의 길이
				if(this.getLocate().breakPoint(cnt, len) != null) {		//null이 아니다  경로상에 유닛이 있다. 체스판 밖이 아니고.
					Unit blocker = this.getLocate().breakPoint(cnt, len).getUnit();
					if((blocker.getName() == "King") 
							&& (this.getIsWhite() != blocker.getIsWhite()))	//그유닛이 왕이고 다른팀이면?
					result = true;
				}
			}
		}
		return result;
	}
	@Override
	public boolean move(int dir, int len) {		// 위8왼쪽4오른쪽6  왼쪽위kill:7 6오른쪽위kill:9 두번move : 88
		
		Locate where = this.getLocate();
		if(!(where.roadExist(dir, len))) return false;			//길이없으면 그냥 함수종료 ,dir과 len에 제한을 겁니다.
		
		Locate destination= where.movedPoint(dir, len);
		this.out();											//잠시 보드판에서 나가있는다. 그후 다시 setlocation으로 착륙
		boolean out;
		if(!where.isEmpty(dir, len-1))  out =false;			//목적지로 제대로갈수없다.
		else if(!destination.isEmpty()){					//1.목적지에 아군 2. 목적지에 적군 
			if (destination.getUnit().getIsWhite() == this.getIsWhite()) return false;	//같은팀은 죽이지 않습니다.
			else {
				destination.getUnit().die(this);
				this.setLocate(destination);
			}			
		}
		else this.setLocate(where.movedPoint(dir,len));		//3. 목적지에 아무도아예없음.
		
		if(this.getLocate() == null) {				//이동실패
			this.setLocate(where);			
			out = false;
		}
		else {
			this.noMoreFirst();
			out = true;										//이동성공
		
		}
		return out;										//바뀌었으면 true,같지않으면 false를 반환한다.
	}
}
