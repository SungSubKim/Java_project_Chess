package unit;

import game_basic.Locate;
import game_basic.Unit;

public class Knight extends Unit{
	public Knight(Locate location, boolean isWhite,int cnt) {
		super(location, isWhite,"Knight",cnt);
	}
	public boolean move(int ins) {		// 위8왼쪽4오른쪽6  왼쪽위kill:7 6오른쪽위kill:9 두번move : 88
		Locate where = this.getLocate();
		Locate destination;
		int x = where.getX();
		int y = where.getY();
		int x2 = 8 , y2 = 8;
		
		this.out();											//잠시 보드판에서 나가있는다. 그후 다시 setlocation으로 착륙
		if(ins == 448) {			//좌2상1
			x2 = x-2;
			y2 = y+1;
		}
		else if(ins == 442) {	//좌2하1
			x2 = x-2;
			y2 = y-1;
		}
		else if(ins == 224) {	//하2좌1
			x2 = x-1;
			y2 = y-2;
		}
		else if(ins == 226) {	//하2우1
			x2 = x+1;
			y2 = y-2;
		}
		else if(ins == 662) {	//우2하1
			x2 = x+2;
			y2 = y-1;
		}
		else if(ins == 668) {	//우2상1
			x2 = x+2;
			y2 = y+1;
		}
		else if(ins == 886) {	//상2우1
			x2 = x+1;
			y2 = y+2;
		}
		else if(ins == 884) {	//상2좌1
			x2 = x-1;
			y2 = y+2;
		}
		
		if( !(0 <= x2 && x2 <= 7 && 0 <= y2 && y2 <=7)) return false;		//체스판밖으로 나가면 그냥종료,추가로, 여기서 ins가 규정된ins가 아닌것도 거른다.
		
		destination = Locate.location[x2][y2];
		if(!(destination.isEmpty())) {							//죽이고 가야하는경우
			if(destination.getUnit().getIsWhite() == this.getIsWhite()) return false;	//같은팀은 죽이지 않습니다.
			destination.getUnit().die(this);					//다른팀은죽여야죠
		}
		this.setLocate(destination);
		
		boolean out;
		if(this.getLocate() == null) {				//이동실패
			this.setLocate(where);			
			out = false;
		}
		else {
			this.noMoreFirst();
			out = true;
		}//이동성공
		return out;										//바뀌었으면 true,같지않으면 false를 반환한다.
	}
	public boolean beAbleToMove() {
		boolean result = false;
		if(this.getLocate().getY() != 7) {
			if(this.getLocate().up().roadExist(7, 1)) result = true;				//상상좌
			else if(this.getLocate().up().roadExist(9, 1)) result = true;			//상상우
		}
		if(this.getLocate().getY() != 0) {
			if(this.getLocate().down().roadExist(1, 1)) result = true;			//하하좌
			else if(this.getLocate().down().roadExist(3, 1)) result = true;		//하하우
		}
		if(this.getLocate().getX() != 0) {
			if(this.getLocate().left().roadExist(7, 1)) result = true;			//좌좌상
			else if(this.getLocate().left().roadExist(1, 1)) result = true;		//좌좌하
		}
		if(this.getLocate().getX() != 7) {
			if(this.getLocate().right().roadExist(9, 1)) result = true;			//우우상
			else if(this.getLocate().right().roadExist(3, 1)) result = true;		//우우하
		}
		
		return result;	
	}
	public boolean beAbleToKillKing() {
		boolean result = false;
		if(this.getLocate().getY() != 7) {
			if(this.getLocate().up().isThereEnemy(7, 1, this.getIsWhite())){		//	경로상에 유닛이 있다. 체스판 밖이 아니고.
				Unit blocker = this.getLocate().up(2).left().getUnit();
				if(blocker.getName()=="King")	//그유닛이 왕이고 다른팀이면?
				result = true;
			}
			else if(this.getLocate().up().isThereEnemy(9, 1, this.getIsWhite())){		//	경로상에 유닛이 있다. 체스판 밖이 아니고.
				Unit blocker = this.getLocate().up(2).right().getUnit();
				if(blocker.getName()=="King")	//그유닛이 왕이고 다른팀이면?
				result = true;
			}
		}
		if(this.getLocate().getY() != 0) {
			if(this.getLocate().down().isThereEnemy(1, 1, this.getIsWhite())) {		//	경로상에 유닛이 있다. 체스판 밖이 아니고.
				Unit blocker = this.getLocate().down(2).left().getUnit();
				if(blocker.getName()=="King")	//그유닛이 왕이고 다른팀이면?
				result = true;
			}
			else if(this.getLocate().down().isThereEnemy(3, 1, this.getIsWhite())) {		//	경로상에 유닛이 있다. 체스판 밖이 아니고.
				Unit blocker = this.getLocate().down(2).right().getUnit();
				if(blocker.getName()=="King" )	//그유닛이 왕이고 다른팀이면?
				result = true;
			}
		}
		if(this.getLocate().getX() != 0) {
			if(this.getLocate().left().isThereEnemy(7, 1, this.getIsWhite())){		//	경로상에 유닛이 있다. 체스판 밖이 아니고.
				Unit blocker = this.getLocate().left(2).up().getUnit();
				if(blocker.getName()=="King" )	//그유닛이 왕이고 다른팀이면?
				result = true;
			}
			else if(this.getLocate().left().isThereEnemy(1, 1, this.getIsWhite())) {		//	경로상에 유닛이 있다. 체스판 밖이 아니고.
				Unit blocker = this.getLocate().left(2).down().getUnit();
				if(blocker.getName()=="King" )	//그유닛이 왕이고 다른팀이면?
				result = true;
			}
		}
		if(this.getLocate().getX() != 7) {
			if(this.getLocate().right().isThereEnemy(9, 1, this.getIsWhite())){		//	경로상에 유닛이 있다. 체스판 밖이 아니고.
				Unit blocker = this.getLocate().right(2).up().getUnit();
				if(blocker.getName()=="King" )	//그유닛이 왕이고 다른팀이면?
				result = true;
			}
			else if(this.getLocate().right().isThereEnemy(3, 1, this.getIsWhite())) {		//	경로상에 유닛이 있다. 체스판 밖이 아니고.
				Unit blocker = this.getLocate().right(2).down().getUnit();
				if(blocker.getName()=="King")	//그유닛이 왕이고 다른팀이면?
				result = true;
			}
		}
		
		return result;	
	}
	@Override
	public boolean move(int dir, int len) {
		// TODO Auto-generated method stub
		return false;
	}
}
