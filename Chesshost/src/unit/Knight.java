package unit;

import game_basic.Locate;
import game_basic.Unit;

public class Knight extends Unit{
	public Knight(Locate location, boolean isWhite,int cnt) {
		super(location, isWhite,"Knight",cnt);
	}
	public boolean move(int ins) {		// ��8����4������6  ������kill:7 6��������kill:9 �ι�move : 88
		Locate where = this.getLocate();
		Locate destination;
		int x = where.getX();
		int y = where.getY();
		int x2 = 8 , y2 = 8;
		
		this.out();											//��� �����ǿ��� �����ִ´�. ���� �ٽ� setlocation���� ����
		if(ins == 448) {			//��2��1
			x2 = x-2;
			y2 = y+1;
		}
		else if(ins == 442) {	//��2��1
			x2 = x-2;
			y2 = y-1;
		}
		else if(ins == 224) {	//��2��1
			x2 = x-1;
			y2 = y-2;
		}
		else if(ins == 226) {	//��2��1
			x2 = x+1;
			y2 = y-2;
		}
		else if(ins == 662) {	//��2��1
			x2 = x+2;
			y2 = y-1;
		}
		else if(ins == 668) {	//��2��1
			x2 = x+2;
			y2 = y+1;
		}
		else if(ins == 886) {	//��2��1
			x2 = x+1;
			y2 = y+2;
		}
		else if(ins == 884) {	//��2��1
			x2 = x-1;
			y2 = y+2;
		}
		
		if( !(0 <= x2 && x2 <= 7 && 0 <= y2 && y2 <=7)) return false;		//ü���ǹ����� ������ �׳�����,�߰���, ���⼭ ins�� ������ins�� �ƴѰ͵� �Ÿ���.
		
		destination = Locate.location[x2][y2];
		if(!(destination.isEmpty())) {							//���̰� �����ϴ°��
			if(destination.getUnit().getIsWhite() == this.getIsWhite()) return false;	//�������� ������ �ʽ��ϴ�.
			destination.getUnit().die(this);					//�ٸ������׿�����
		}
		this.setLocate(destination);
		
		boolean out;
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
		boolean result = false;
		if(this.getLocate().getY() != 7) {
			if(this.getLocate().up().roadExist(7, 1)) result = true;				//�����
			else if(this.getLocate().up().roadExist(9, 1)) result = true;			//����
		}
		if(this.getLocate().getY() != 0) {
			if(this.getLocate().down().roadExist(1, 1)) result = true;			//������
			else if(this.getLocate().down().roadExist(3, 1)) result = true;		//���Ͽ�
		}
		if(this.getLocate().getX() != 0) {
			if(this.getLocate().left().roadExist(7, 1)) result = true;			//���»�
			else if(this.getLocate().left().roadExist(1, 1)) result = true;		//������
		}
		if(this.getLocate().getX() != 7) {
			if(this.getLocate().right().roadExist(9, 1)) result = true;			//����
			else if(this.getLocate().right().roadExist(3, 1)) result = true;		//�����
		}
		
		return result;	
	}
	public boolean beAbleToKillKing() {
		boolean result = false;
		if(this.getLocate().getY() != 7) {
			if(this.getLocate().up().isThereEnemy(7, 1, this.getIsWhite())){		//	��λ� ������ �ִ�. ü���� ���� �ƴϰ�.
				Unit blocker = this.getLocate().up(2).left().getUnit();
				if(blocker.getName()=="King")	//�������� ���̰� �ٸ����̸�?
				result = true;
			}
			else if(this.getLocate().up().isThereEnemy(9, 1, this.getIsWhite())){		//	��λ� ������ �ִ�. ü���� ���� �ƴϰ�.
				Unit blocker = this.getLocate().up(2).right().getUnit();
				if(blocker.getName()=="King")	//�������� ���̰� �ٸ����̸�?
				result = true;
			}
		}
		if(this.getLocate().getY() != 0) {
			if(this.getLocate().down().isThereEnemy(1, 1, this.getIsWhite())) {		//	��λ� ������ �ִ�. ü���� ���� �ƴϰ�.
				Unit blocker = this.getLocate().down(2).left().getUnit();
				if(blocker.getName()=="King")	//�������� ���̰� �ٸ����̸�?
				result = true;
			}
			else if(this.getLocate().down().isThereEnemy(3, 1, this.getIsWhite())) {		//	��λ� ������ �ִ�. ü���� ���� �ƴϰ�.
				Unit blocker = this.getLocate().down(2).right().getUnit();
				if(blocker.getName()=="King" )	//�������� ���̰� �ٸ����̸�?
				result = true;
			}
		}
		if(this.getLocate().getX() != 0) {
			if(this.getLocate().left().isThereEnemy(7, 1, this.getIsWhite())){		//	��λ� ������ �ִ�. ü���� ���� �ƴϰ�.
				Unit blocker = this.getLocate().left(2).up().getUnit();
				if(blocker.getName()=="King" )	//�������� ���̰� �ٸ����̸�?
				result = true;
			}
			else if(this.getLocate().left().isThereEnemy(1, 1, this.getIsWhite())) {		//	��λ� ������ �ִ�. ü���� ���� �ƴϰ�.
				Unit blocker = this.getLocate().left(2).down().getUnit();
				if(blocker.getName()=="King" )	//�������� ���̰� �ٸ����̸�?
				result = true;
			}
		}
		if(this.getLocate().getX() != 7) {
			if(this.getLocate().right().isThereEnemy(9, 1, this.getIsWhite())){		//	��λ� ������ �ִ�. ü���� ���� �ƴϰ�.
				Unit blocker = this.getLocate().right(2).up().getUnit();
				if(blocker.getName()=="King" )	//�������� ���̰� �ٸ����̸�?
				result = true;
			}
			else if(this.getLocate().right().isThereEnemy(3, 1, this.getIsWhite())) {		//	��λ� ������ �ִ�. ü���� ���� �ƴϰ�.
				Unit blocker = this.getLocate().right(2).down().getUnit();
				if(blocker.getName()=="King")	//�������� ���̰� �ٸ����̸�?
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
