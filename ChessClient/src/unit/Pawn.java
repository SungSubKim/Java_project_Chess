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
	public boolean move(int dir) {		// ��8����4������6  ������kill:7 6��������kill:9 �ι�move : 88
		boolean out;
		Locate where = this.getLocate();
		int x = where.getX();
		int y = where.getY();
		this.out();											//��� �����ǿ��� �����ִ´�. ���� �ٽ� setlocation���� ����
		
		if (dir==4  && where.isEmpty(4, 1)) this.setLocate(where.left());
		else if (dir==6 && where.isEmpty(6, 1)) this.setLocate(where.right());
		
		if(this.getIsWhite()) {							//����� ���ع���	, �� ������ε� �����ʴ� direction�� �Է��ϸ� �۵�������
			if (dir==8  && where.isEmpty(8, 1)) this.setLocate(where.up());
			else if (dir==88 && where.isEmpty(8, 2)&& this.isFirst()) { //ù�Ͽ��� �ι������ϼ��ֽ��ϴ�.
				this.setLocate(where.up(2));
			}
			else if (where.roadExist(7, 1) && !(where.isEmpty(7, 1)) && dir==7 ) {			//�»� ���� ������������
				if(Locate.location[x-1][y+1].getUnit().getIsWhite() == this.getIsWhite()) out= false;	//�������� ������ �ʽ��ϴ�.
				else {
					Locate.location[x-1][y+1].getUnit().die(this);			//���̰� ���ڸ����̵�
					this.setLocate(where.up().left());
				}
				
			}
			else if (where.roadExist(9, 1) && !(where.isEmpty(9, 1)) && dir==9) {
				if(Locate.location[x+1][y+1].getUnit().getIsWhite() == this.getIsWhite()) {
					System.out.println("can not kill same team ");
					out= false;	//�������� ������ �ʽ��ϴ�.
				}
				else {
					Locate.location[x+1][y+1].getUnit().die(this);
					this.setLocate(where.up().right());
				}
			}
		}
		
		else {											//����� ���ع���
			if (dir==2  && where.isEmpty(2, 1) ) this.setLocate(where.down());
			else if (dir==22 && where.isEmpty(2, 2)&& this.isFirst())this.setLocate(where.down(2));
			else if (where.roadExist(1, 1) && !(where.isEmpty(1, 1)) && dir==1 ) {
				if(Locate.location[x-1][y-1].getUnit().getIsWhite() == this.getIsWhite()) {
					System.out.println("can not kill same team TT");
					out= false;	//�������� ������ �ʽ��ϴ�.
				}
				else {
					Locate.location[x-1][y-1].getUnit().die(this);			//���̰� ���ڸ����̵�
					this.setLocate(where.down().left());
				}
			}
			else if (where.roadExist(3, 1) && !(where.isEmpty(3, 1)) && dir==3) {
				if(Locate.location[x+1][y-1].getUnit().getIsWhite() == this.getIsWhite()) {
					System.out.println("can not kill same team TT");
					out= false;	//�������� ������ �ʽ��ϴ�.
				}
				else {
					Locate.location[x+1][y-1].getUnit().die(this);
					this.setLocate(where.down().right());
					}
			}
		}
		if(this.getLocate() == null) {				//�̵�����
			this.setLocate(where);			
			out = false;
		}
		else {											//�̵�����
			this.noMoreFirst();
			out = true;
		}
		return out;										//�ٲ������ true,���������� false�� ��ȯ�Ѵ�.
	}
	public boolean beAbleToMove() {
		boolean result;
		if(this.getIsWhite()) {			//���϶�
			if(this.getLocate().isThereSome(8, 1) && this.getLocate().isThereSome(4, 1)
					&& this.getLocate().isThereSome(6, 1) && !this.getLocate().isThereSome(7, 1)
					&& !this.getLocate().isThereSome(9, 1)) result = false;	//�տ� �ٸ����� ������������ ������ �� ����.
			else result = true;		//������ �� ����
		}
		else {							//���϶�
			if(this.getLocate().isThereSome(2, 1) && this.getLocate().isThereSome(4, 1)
					&& this.getLocate().isThereSome(6, 1) && !this.getLocate().isThereSome(1, 1)
					&& !this.getLocate().isThereSome(3, 1)) result = false;	//�տ� �ٸ����� ������������ ������ �� ����.
			else result = true;		//������ �� ����
		}
		return result;	
	}
	public boolean beAbleToKillKing() {
		if(this.getLocate() == null) return false;
		boolean result = false;
		if(this.getName().equals("Pawn")) {
			if(this.getIsWhite()) {
				if(this.getLocate().isThereEnemy(7, 1) &&									//���¿� ������ �ְ� �� �������� ŷ�̸� ���� ŷ�� ���峾���ֵ�->��üũ
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
				for(cnt2 = 1; cnt2 <= 7 ; cnt2++) if(!this.getLocate().roadExist(cnt,cnt2)) break; //cnt�������� cnt2���̸�ŭ
				int len = cnt2 - 1;									//len�� �����ִ� ������ ����
				if(this.getLocate().breakPoint(cnt, len) != null) {		//null�� �ƴϴ�  ��λ� ������ �ִ�. ü���� ���� �ƴϰ�.
					Unit blocker = this.getLocate().breakPoint(cnt, len).getUnit();
					if((blocker.getName() == "King") 
							&& (this.getIsWhite() != blocker.getIsWhite()))	//�������� ���̰� �ٸ����̸�?
					result = true;
				}
			}
		}
		return result;
	}
	@Override
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
}
