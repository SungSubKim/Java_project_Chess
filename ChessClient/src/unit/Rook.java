package unit;

import game_basic.Locate;
import game_basic.Unit;

public class Rook extends Unit {
	public Rook(Locate location, boolean isWhite,int cnt) {
		super(location, isWhite,"Rook",cnt);
	}
	public boolean move(int dir, int len) {		// ��8����4������6  ������kill:7 6��������kill:9 �ι�move : 88
		Locate where = this.getLocate();
		if(!(where.roadExist(dir, len))) return false;			//���̾����� �׳� �Լ����� ,dir�� len�� ������ �̴ϴ�.
		if(dir == 1 || dir == 3 || dir == 7 || dir == 9) return false;	//dir�� �߰����� ����
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
			out = true;										//�̵�����
			this.noMoreFirst();
		}
		return out;										//�ٲ������ true,���������� false�� ��ȯ�Ѵ�.
	}
	public boolean beAbleToMove() {
		boolean result;	
		if(this.getLocate().isThereEnemy(2, 1) || this.getLocate().isThereEnemy(4, 1) ||
				this.getLocate().isThereEnemy(6, 1) || this.getLocate().isThereEnemy(8, 1)) {		//�������� ������
			result = true;
		}
		else if (this.getLocate().roadExist(8, 1) && !this.getLocate().isThereSome(8, 1)) result = true; 	//�� �ƹ������� ����������
		else if (this.getLocate().roadExist(2, 1) && !this.getLocate().isThereSome(2, 1)) result = true; 	//�Ͽ� �ƹ������� ����������
		else if (this.getLocate().roadExist(4, 1) && !this.getLocate().isThereSome(4, 1)) result = true; 	//�¿� �ƹ������� ����������
		else if (this.getLocate().roadExist(6, 1) && !this.getLocate().isThereSome(6, 1)) result = true; 	//�쿡 �ƹ������� ����������
		else result = false;
		
		return result;	
	}
	public boolean beAbleToKillKing() {
		boolean result = false;
		int num[] = {2, 4, 6, 8};
		for(int cnt : num) {
			int cnt2;
			for(cnt2 = 1; cnt2 <= 7 ; cnt2++) if(!this.getLocate().roadExist(cnt,cnt2)) break; //cnt�������� cnt2���̸�ŭ
			int len = cnt2 - 1;					//len�� �����Ǳ���
			if(this.getLocate().breakPoint(cnt, len) != null) {		//null�� �ƴϴ� ���� ��λ� ������ �ִ�. ü���� ���� �ƴϰ�.
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
