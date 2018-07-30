package unit;


import game_basic.Locate;
import game_basic.Unit;

public class Queen extends Unit {
	public Queen(Locate location, boolean isWhite,int cnt) {
		super(location, isWhite,"Queen", cnt);
	}
	public boolean move(int dir, int len) {		// ��8����4������6  ������kill:7 6��������kill:9 �ι�move : 88
		
		Locate where = this.getLocate();
		if(!(where.roadExist(dir, len))) return false;			//���̾����� �׳� �Լ����� ,dir�� len�� ������ �̴ϴ�.
		
		Locate destination= where.movedPoint(dir, len);
		this.out();											//��� �����ǿ��� �����ִ´�. ���� �ٽ� setlocation���� ����
		boolean out;
		if(!where.isEmpty(dir, len-1))  out =false;			//�������� ����ΰ�������.
		else if(!destination.isEmpty()){					//1.�������� �Ʊ� 2. �������� ���� 
			if (destination.getUnit().getIsWhite() == this.getIsWhite()) return false;	//�������� ������ �ʽ��ϴ�.
			else {
				destination.getUnit().die(this);
				this.setLocate(destination);
			}			
		}
		else this.setLocate(where.movedPoint(dir,len));		//3. �������� �ƹ����ƿ�����.
		
		if(this.getLocate() == null) {				//�̵�����
			this.setLocate(where);			
			out = false;
		}
		else {
			this.noMoreFirst();
			out = true;										//�̵�����
		
		}
		return out;										//�ٲ������ true,���������� false�� ��ȯ�Ѵ�.
	}
	public boolean beAbleToMove() {
		boolean result;	
		if(this.getLocate().isThereEnemy(1, 1) || this.getLocate().isThereEnemy(3, 1) ||
				this.getLocate().isThereEnemy(7, 1) || this.getLocate().isThereEnemy(9, 1)) result = true;
		else if(this.getLocate().isThereEnemy(2, 1) || this.getLocate().isThereEnemy(4, 1) ||
				this.getLocate().isThereEnemy(6, 1) || this.getLocate().isThereEnemy(8, 1)) 	result = true;
		else if (this.getLocate().roadExist(7, 1) && !this.getLocate().isThereSome(7, 1)) result = true; 	//���¿� �ƹ������� ����������
		else if (this.getLocate().roadExist(9, 1) && !this.getLocate().isThereSome(9, 1)) result = true; 	//��쿡 �ƹ������� ����������
		else if (this.getLocate().roadExist(1, 1) && !this.getLocate().isThereSome(1, 1)) result = true; 	//���¿� �ƹ������� ����������
		else if (this.getLocate().roadExist(3, 1) && !this.getLocate().isThereSome(3, 1)) result = true; 	//�Ͽ쿡 �ƹ������� ����������
		else if (this.getLocate().roadExist(8, 1) && !this.getLocate().isThereSome(8, 1)) result = true; 	//�� �ƹ������� ����������
		else if (this.getLocate().roadExist(2, 1) && !this.getLocate().isThereSome(2, 1)) result = true; 	//�Ͽ� �ƹ������� ����������
		else if (this.getLocate().roadExist(4, 1) && !this.getLocate().isThereSome(4, 1)) result = true; 	//�¿� �ƹ������� ����������
		else if (this.getLocate().roadExist(6, 1) && !this.getLocate().isThereSome(6, 1)) result = true; 	//�쿡 �ƹ������� ����������
		else result = false;
		
		return result;	
	}
	public boolean beAbleToKillKing() {
		boolean result = false;
		int num[] = {1, 2, 3, 4, 6, 7, 8, 9};
		for(int cnt : num) {
			int cnt2;
			for(cnt2 = 1; cnt2 <= 7 ; cnt2++) if(!this.getLocate().roadExist(cnt,cnt2)) break; //cnt�������� cnt2���̸�ŭ
			int len = cnt2 - 1;									//len�� �����ִ� ������ ����
			if(this.getLocate().breakPoint(cnt, len) != null) {		//null�� �ƴϴ�  ��λ� ������ �ִ�. ü���� ���� �ƴϰ�.
				Unit blocker = this.getLocate().breakPoint(cnt, len).getUnit();
				if((blocker.getName() == "King") 
						&& (this.getIsWhite() != blocker.getIsWhite()))	//�������� ���̰� �ٸ����̸�?
				result = true;
			}
		}
		return result;
	}
	public boolean move(int ins) {
		// TODO Auto-generated method stub
		return false;
	}

}
