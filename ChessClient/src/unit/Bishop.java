package unit;

import game_basic.Locate;
import game_basic.Unit;

public class Bishop extends Unit {
	public Bishop(Locate location, boolean isWhite, int cnt) {
		super(location, isWhite,"Bishop", cnt);
	}
	public boolean move(int dir, int len) {		// 위8왼쪽4오른쪽6  왼쪽위kill:7 6오른쪽위kill:9 두번move : 88
		Locate where = this.getLocate();
		if(!(where.roadExist(dir, len))) return false;			//길이없으면 그냥 함수종료 ,dir과 len에 제한을 겁니다.
		if(dir == 2 || dir ==4 || dir == 6 || dir== 8) return false;	//dir에 추가적인 제한
		this.out();											//잠시 보드판에서 나가있는다. 그후 다시 setlocation으로 착륙
		boolean out;
		Locate destination= where.movedPoint(dir, len);
		if(!where.isEmpty(dir, len-1))  out =false;			//목적지로 제대로갈수없다.
		else if(!destination.isEmpty()){					//1.목적지에 아군 2. 목적지에 적군 3. 목적지에 아무도아예없음.
			if (destination.getUnit().getIsWhite() == this.getIsWhite()) return false;	//같은팀은 죽이지 않습니다.
			else {
				destination.getUnit().die(this);
				this.setLocate(destination);
			}			
		}
		else this.setLocate(where.movedPoint(dir,len));	
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
		boolean result;	
		if(this.getLocate().isThereEnemy(1, 1) || this.getLocate().isThereEnemy(3, 1) ||
				this.getLocate().isThereEnemy(7, 1) || this.getLocate().isThereEnemy(9, 1)) {		//죽일적이 있을때
			result = true;
		}
		else if (this.getLocate().roadExist(7, 1) && !this.getLocate().isThereSome(7, 1)) result = true; 	//상좌에 아무도없고 길이있을때
		else if (this.getLocate().roadExist(9, 1) && !this.getLocate().isThereSome(9, 1)) result = true; 	//상우에 아무도없고 길이있을때
		else if (this.getLocate().roadExist(1, 1) && !this.getLocate().isThereSome(1, 1)) result = true; 	//하좌에 아무도없고 길이있을때
		else if (this.getLocate().roadExist(3, 1) && !this.getLocate().isThereSome(3, 1)) result = true; 	//하우에 아무도없고 길이있을때
		else result = false;
		
		return result;	
	}
	public boolean beAbleToKillKing() {
		boolean result = false;
		int num[] = {1, 3, 7, 9};
		for(int cnt : num) {
			int cnt2;
			for(cnt2 = 1; cnt2 <= 7 ; cnt2++) if(!this.getLocate().roadExist(cnt,cnt2)) break; //cnt방향으로 cnt2길이만큼
			int len = cnt2 - 1;	
			if(this.getLocate().breakPoint(cnt, len) != null) {		//null이 아니다 경로상에 유닛이 있다. 체스판 밖이 아니고.
				Unit blocker = this.getLocate().breakPoint(cnt, len).getUnit();
				if(blocker.getName()=="King" 
						&& this.getIsWhite() != blocker.getIsWhite())	//그유닛이 왕이고 다른팀이면?
				result = true;
			}
		}
		return result;
	}
	@Override
	public boolean move(int ins) {
		// TODO Auto-generated method stub
		return false;
	}

}
