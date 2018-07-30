package unit;

import game_basic.Chess;
import game_basic.Locate;
import game_basic.Unit;

public class King extends Unit {
	public King(Locate location, boolean isWhite, int cnt) {
		super(location, isWhite,"King",cnt);
	}
	public boolean move(int dir) {		// ��8����4������6  ������kill:7 6��������kill:9 �ι�move : 88
		Locate where = this.getLocate();
		if(!(where.roadExist(dir, 1))) return false;			//���̾����� �׳� �Լ����� dir�� �߸��� ��ǲ�̿��� ����
		this.out();											//��� �����ǿ��� �����ִ´�. ���� �ٽ� setlocation���� ����
		boolean out;
		if(where.isThereSome(dir, 1)) {						//������ ���������ִ�.
			if(where.movedPoint(dir, 1).getUnit().getIsWhite() == this.getIsWhite()) out = false;	//�������� ������ �ʽ��ϴ�.
			else {
				where.movedPoint(dir, 1).getUnit().die(this);
				this.setLocate(where.movedPoint(dir, 1));
			}
		}
		else {												//�׳� �������ΰ��°��
			this.setLocate(where.movedPoint(dir,1));
		}
		if(this.getLocate() == null) {				//�̵�����
			this.setLocate(where);			
			out = false;
		}
		else {
			this.noMoreFirst();
			out = true;
		}//�̵�����										//�̵�����
		return out;										//�ٲ������ true,���������� false�� ��ȯ�Ѵ�.
	}
	public boolean beAbleToMove() {
		boolean result;	
		if(this.getLocate().isThereEnemy(1, 1) || this.getLocate().isThereEnemy(3, 1) ||
				this.getLocate().isThereEnemy(7, 1) || this.getLocate().isThereEnemy(9, 1)) {		//�������� ������ - queen
			result = true;
		}
		else if(this.getLocate().isThereEnemy(2, 1) || this.getLocate().isThereEnemy(4, 1) ||
				this.getLocate().isThereEnemy(6, 1) || this.getLocate().isThereEnemy(8, 1)) {		//�������� ������ -rook
			result = true;
		}
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
	public void die(Unit murder) {
		if(murder.getIsWhite()) System.out.println("white " + murder.getName() + " killed Black " + this.getName());
		else System.out.println("Black " + murder.getName() + " killed white " + this.getName());
		Chess.terminateGame(!(this.getIsWhite()));			//�ݴ����� �̱�.
	}
	public boolean beAbleToKillKing() {
		boolean result = false;
		int num[] = {1, 2, 3, 4, 6, 7, 8, 9};
		for(int cnt : num) {
			if(this.getLocate().breakPoint(cnt, 1) != null) {		//null�� �ƴϴ� ��λ� ������ �ִ�. ü���� ���� �ƴϰ�.
				Unit blocker = this.getLocate().breakPoint(cnt, 1).getUnit();
				if(blocker.getName()=="King" 
						&& this.getIsWhite() != blocker.getIsWhite())	//�������� ���̰� �ٸ����̸�?
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
