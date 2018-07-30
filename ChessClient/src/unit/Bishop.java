package unit;

import game_basic.Locate;
import game_basic.Unit;

public class Bishop extends Unit {
	public Bishop(Locate location, boolean isWhite, int cnt) {
		super(location, isWhite,"Bishop", cnt);
	}
	public boolean move(int dir, int len) {		// ��8����4������6  ������kill:7 6��������kill:9 �ι�move : 88
		Locate where = this.getLocate();
		if(!(where.roadExist(dir, len))) return false;			//���̾����� �׳� �Լ����� ,dir�� len�� ������ �̴ϴ�.
		if(dir == 2 || dir ==4 || dir == 6 || dir== 8) return false;	//dir�� �߰����� ����
		this.out();											//��� �����ǿ��� �����ִ´�. ���� �ٽ� setlocation���� ����
		boolean out;
		Locate destination= where.movedPoint(dir, len);
		if(!where.isEmpty(dir, len-1))  out =false;			//�������� ����ΰ�������.
		else if(!destination.isEmpty()){					//1.�������� �Ʊ� 2. �������� ���� 3. �������� �ƹ����ƿ�����.
			if (destination.getUnit().getIsWhite() == this.getIsWhite()) return false;	//�������� ������ �ʽ��ϴ�.
			else {
				destination.getUnit().die(this);
				this.setLocate(destination);
			}			
		}
		else this.setLocate(where.movedPoint(dir,len));	
		if(this.getLocate() == null) {				//�̵�����
			this.setLocate(where);			
			out = false;
		}
		else {
			this.noMoreFirst();
			out = true;
		}//�̵�����									
		return out;										//�ٲ������ true,���������� false�� ��ȯ�Ѵ�.
	}
	public boolean beAbleToMove() {
		boolean result;	
		if(this.getLocate().isThereEnemy(1, 1) || this.getLocate().isThereEnemy(3, 1) ||
				this.getLocate().isThereEnemy(7, 1) || this.getLocate().isThereEnemy(9, 1)) {		//�������� ������
			result = true;
		}
		else if (this.getLocate().roadExist(7, 1) && !this.getLocate().isThereSome(7, 1)) result = true; 	//���¿� �ƹ������� ����������
		else if (this.getLocate().roadExist(9, 1) && !this.getLocate().isThereSome(9, 1)) result = true; 	//��쿡 �ƹ������� ����������
		else if (this.getLocate().roadExist(1, 1) && !this.getLocate().isThereSome(1, 1)) result = true; 	//���¿� �ƹ������� ����������
		else if (this.getLocate().roadExist(3, 1) && !this.getLocate().isThereSome(3, 1)) result = true; 	//�Ͽ쿡 �ƹ������� ����������
		else result = false;
		
		return result;	
	}
	public boolean beAbleToKillKing() {
		boolean result = false;
		int num[] = {1, 3, 7, 9};
		for(int cnt : num) {
			int cnt2;
			for(cnt2 = 1; cnt2 <= 7 ; cnt2++) if(!this.getLocate().roadExist(cnt,cnt2)) break; //cnt�������� cnt2���̸�ŭ
			int len = cnt2 - 1;	
			if(this.getLocate().breakPoint(cnt, len) != null) {		//null�� �ƴϴ� ��λ� ������ �ִ�. ü���� ���� �ƴϰ�.
				Unit blocker = this.getLocate().breakPoint(cnt, len).getUnit();
				if(blocker.getName()=="King" 
						&& this.getIsWhite() != blocker.getIsWhite())	//�������� ���̰� �ٸ����̸�?
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
