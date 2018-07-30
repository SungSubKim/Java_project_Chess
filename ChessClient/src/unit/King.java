package unit;

import game_basic.Chess;
import game_basic.Locate;
import game_basic.Unit;

public class King extends Unit {
	public King(Locate location, boolean isWhite, int cnt) {
		super(location, isWhite,"King",cnt);
	}
	public boolean move(int dir) {		// 위8왼쪽4오른쪽6  왼쪽위kill:7 6오른쪽위kill:9 두번move : 88
		Locate where = this.getLocate();
		if(!(where.roadExist(dir, 1))) return false;			//길이없으면 그냥 함수종료 dir이 잘못된 인풋이여도 종료
		this.out();											//잠시 보드판에서 나가있는다. 그후 다시 setlocation으로 착륙
		boolean out;
		if(where.isThereSome(dir, 1)) {						//그위에 누군가가있다.
			if(where.movedPoint(dir, 1).getUnit().getIsWhite() == this.getIsWhite()) out = false;	//같은팀은 죽이지 않습니다.
			else {
				where.movedPoint(dir, 1).getUnit().die(this);
				this.setLocate(where.movedPoint(dir, 1));
			}
		}
		else {												//그냥 직빵으로가는경우
			this.setLocate(where.movedPoint(dir,1));
		}
		if(this.getLocate() == null) {				//이동실패
			this.setLocate(where);			
			out = false;
		}
		else {
			this.noMoreFirst();
			out = true;
		}//이동성공										//이동성공
		return out;										//바뀌었으면 true,같지않으면 false를 반환한다.
	}
	public boolean beAbleToMove() {
		boolean result;	
		if(this.getLocate().isThereEnemy(1, 1) || this.getLocate().isThereEnemy(3, 1) ||
				this.getLocate().isThereEnemy(7, 1) || this.getLocate().isThereEnemy(9, 1)) {		//죽일적이 있을때 - queen
			result = true;
		}
		else if(this.getLocate().isThereEnemy(2, 1) || this.getLocate().isThereEnemy(4, 1) ||
				this.getLocate().isThereEnemy(6, 1) || this.getLocate().isThereEnemy(8, 1)) {		//죽일적이 있을때 -rook
			result = true;
		}
		else if (this.getLocate().roadExist(7, 1) && !this.getLocate().isThereSome(7, 1)) result = true; 	//상좌에 아무도없고 길이있을때
		else if (this.getLocate().roadExist(9, 1) && !this.getLocate().isThereSome(9, 1)) result = true; 	//상우에 아무도없고 길이있을때
		else if (this.getLocate().roadExist(1, 1) && !this.getLocate().isThereSome(1, 1)) result = true; 	//하좌에 아무도없고 길이있을때
		else if (this.getLocate().roadExist(3, 1) && !this.getLocate().isThereSome(3, 1)) result = true; 	//하우에 아무도없고 길이있을때
		else if (this.getLocate().roadExist(8, 1) && !this.getLocate().isThereSome(8, 1)) result = true; 	//상에 아무도없고 길이있을때
		else if (this.getLocate().roadExist(2, 1) && !this.getLocate().isThereSome(2, 1)) result = true; 	//하에 아무도없고 길이있을때
		else if (this.getLocate().roadExist(4, 1) && !this.getLocate().isThereSome(4, 1)) result = true; 	//좌에 아무도없고 길이있을때
		else if (this.getLocate().roadExist(6, 1) && !this.getLocate().isThereSome(6, 1)) result = true; 	//우에 아무도없고 길이있을때
		else result = false;
		
		return result;	
	}
	public void die(Unit murder) {
		if(murder.getIsWhite()) System.out.println("white " + murder.getName() + " killed Black " + this.getName());
		else System.out.println("Black " + murder.getName() + " killed white " + this.getName());
		Chess.terminateGame(!(this.getIsWhite()));			//반대쪽이 이김.
	}
	public boolean beAbleToKillKing() {
		boolean result = false;
		int num[] = {1, 2, 3, 4, 6, 7, 8, 9};
		for(int cnt : num) {
			if(this.getLocate().breakPoint(cnt, 1) != null) {		//null이 아니다 경로상에 유닛이 있다. 체스판 밖이 아니고.
				Unit blocker = this.getLocate().breakPoint(cnt, 1).getUnit();
				if(blocker.getName()=="King" 
						&& this.getIsWhite() != blocker.getIsWhite())	//그유닛이 왕이고 다른팀이면?
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
