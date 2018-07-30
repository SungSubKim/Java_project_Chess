package game_basic;		//host

import java.awt.Color;

import added_Function.Server;
import java.util.Scanner;

import added_Function.GUIboard;
import added_Function.Sender;
import unit.Bishop;
import unit.King;
import unit.Knight;
import unit.Pawn;
import unit.Queen;
import unit.Rook;
public class Chess {
	public static String turn ="no";
	private static String str = "empty";
	private static Unit victim;
	private static boolean king_live = true;
	public static boolean getKing_live() {
		return Chess.king_live;
	}
	public static void setKing_live(boolean kinglive) {
		Chess.king_live =kinglive;
	}
	public static void setStr(String str) {
		Chess.str = str;
	}
	public static String getStr() {
		return Chess.str;
	}
	public static void setVictim(Unit victim) {
		Chess.victim = victim;
	}
	public static Unit getVictim() {
		return Chess.victim;
	}
	public static boolean whiteCastling(String str, Scanner sc,int input) {
		boolean result = false;
		Rook rooktogo = null;			
		if(str.equals("castling") || str.equals("Castling")  || str.equals("CASTLING") ) {
			if(!Unit.king[0].isFirst()) {
				displayBoard();
				System.out.println("king have moved in game. you can not do castling");
				System.out.println("�� White turn : please type the xy-coordinate of a unit you want to control");
				return false;
			}								//ŷ�� �̵��Ѱ��
			else if(blackCheck()) {
				displayBoard();
				System.out.println("king is on check condition. you can not do castling");
				System.out.println("�� White turn : please type the xy-coordinate of a unit you want to control");
				return false;
			}								//ŷ�� üũ���°��ƴϾ���Ѵ�.
					//�Էº�//
			System.out.println("select.");
			System.out.println("1. Queen side Castling(left rook)");
			System.out.println("2. king side Castling(right rook)");
			/*while(true) {
				input = sc.nextInt();
				if(input == 1 || input == 2) break;
				else System.out.println("wrong input.retype.");
			}*/
			input += 1;
			System.out.println(input);
					//�Է�����//
			/////////////////queensidecastling/////////////////////////////////
			if(input ==1) {
				if(Locate.location[1][0].isEmpty() && Locate.location[2][0].isEmpty() && Locate.location[3][0].isEmpty()) {//���±��� ����ִ°�
					if(Unit.rook[0].isFirst()) {			//rook�� ó�������̼���?
						if(!(blackIsAbleToGo(Locate.location[1][0])||blackIsAbleToGo(Locate.location[2][0])||blackIsAbleToGo(Locate.location[3][0]))) {
							System.out.println("�����");
							rooktogo = Unit.rook[0];		//queenside�� �ٲ���
							rooktogo.out();
							rooktogo.setLocate(Locate.location[3][0]);
							Unit.king[0].out();
							Unit.king[0].setLocate(Locate.location[2][0]);
							result = true;
						}
						else {
							displayBoard();
							System.out.println("Path is attacked!.you can not do castling.");
							System.out.println("�� White turn : please type the xy-coordinate of a unit you want to control");
							return false;
						}
					}
					else {
						displayBoard();
						System.out.println("rook has moved.you can not do castling.");
						System.out.println("�� White turn : please type the xy-coordinate of a unit you want to control");
						return false;
					}
				}
				else {
					displayBoard();
					System.out.println("location is not empty.you can not do castling.");
					System.out.println("�� White turn : please type the xy-coordinate of a unit you want to control");
					return false;
				}
			}
			/////////////////kingsidecastling/////////////////////////////////
			else if(input == 2) {
				if(Locate.location[5][0].isEmpty() && Locate.location[6][0].isEmpty()) {
					if(Unit.rook[1].isFirst()) {
						if(!(blackIsAbleToGo(Locate.location[5][0])||blackIsAbleToGo(Locate.location[6][0]))) {
							rooktogo = Unit.rook[1];		//queenside�� �ٲ���
							rooktogo.out();
							rooktogo.setLocate(Locate.location[5][0]);
							Unit.king[0].out();
							Unit.king[0].setLocate(Locate.location[6][0]);
							result = true;
						}
						else {
							displayBoard();
							System.out.println("Path is attacked!.you can not do castling.");
							System.out.println("�� White turn : please type the xy-coordinate of a unit you want to control");
							return false;
						}
					}
					else {
						displayBoard();
						System.out.println("rook has moved.you can not do castling.");
						System.out.println("�� White turn : please type the xy-coordinate of a unit you want to control");
						return false;
					}
				}
				else {
					displayBoard();
					System.out.println("location is not empty.you can not do castling.");
					System.out.println("�� White turn : please type the xy-coordinate of a unit you want to control");
					return false;
				}
			}
		}
		if(rooktogo!=null) rooktogo.noMoreFirst();
		System.out.println("whitecastling result"+result);
		return result;
	}
	public static boolean whiteCheck() {			//ȭ��Ʈ�� üũ�� �����Ͽ���.
		boolean result = false;
		for(int cnt = 0 ; cnt < 8; cnt++) {
			if(Unit.pawn[cnt].getLocate()!=null &&Unit.pawn[cnt].beAbleToKillKing()) return true;
		}
		for(int cnt = 0 ; cnt < 2; cnt++) {
			if(Unit.knight[cnt].getLocate()!=null &&Unit.knight[cnt].beAbleToKillKing()) return true;
			else if(Unit.bishop[cnt].getLocate()!=null &&Unit.bishop[cnt].beAbleToKillKing()) return true;
			else if(Unit.rook[cnt].getLocate()!=null &&Unit.rook[cnt].beAbleToKillKing()) return true;
		}
		if(Unit.queen[0].getLocate()!=null &&Unit.queen[0].beAbleToKillKing()) return true;
		else if(Unit.king[0].getLocate()!=null &&Unit.king[0].beAbleToKillKing()) return true;
		return result;
	}
	public static void whiteCheckMate() {
		System.out.println("Checking..");
		Unit tmp = victim;
		Locate.saveLocate();
		boolean pa=true, kn = true, bi = true,ro = true,qu= true,ki = true;
		////////////////////////////////////////////////////////////
		int blackPawnNum[] = {8,9,10,11,12,13,14,15};
		for (int cnt: blackPawnNum) {
			int ins[]= {1,3,4,6,2,22};
			if(Unit.pawn[cnt].getName().equals("Queen")) {
				
				int queenDir[] = {1,3,7,9,2,4,6,8};
				for(int cnt2 : queenDir) {
					int len[] = {1,2,3,4,5,6,7};
					for(int cnt3 : len) {
						if(Unit.pawn[cnt].getLocate()!= null) Unit.pawn[cnt].move(cnt2, cnt3);
						if(whiteCheck()) {
							Locate.loadLocate();							//�ϴ����� ������� �س����� �˻������ϱ�
							if(cnt2 == 15 && cnt3 == 7) qu =false;	//üũ���ε� ���� üũ���̼̳�? �׷� �������������׿�
							else continue; 				//���� �˻��Ұų��������ϱ� break���ϰ� ���� ���̽���˻��մϴ�.
						}
						Locate.loadLocate();						//üũ���ƴ��������ϱ�
						break;								//�����Կ� �����ֵ� �˻��Ϸ� ���ϴ�.
					}
				}
				
			}
			else {
				for(int cnt2 : ins) {
					if(Unit.pawn[cnt].getLocate()!= null) {
						Unit.pawn[cnt].move(cnt2);
					}
					if(whiteCheck()) {					//üũ���̳�?
						Locate.loadLocate();					//�ϴ����� ������� �س����� �˻������ϱ�
						if(cnt==15 && cnt2 == 22) pa = false;//üũ���ε� ���� üũ���̼̳�? �׷����� ���������׿�
						continue;						//���� �˻��Ұ� ���������ϱ� ���� ins �˻��ҰԿ�
					}
					Locate.loadLocate();						//üũ�������ƴ��������ϱ�
					break;								//�����Կ� �����ֵ� �˻��Ϸ� ���ϴ�.
				}
			}
		}
		///////////////////////////////////////////////////////////
		int blackOfficerNum[] = {2,3};
		for (int cnt: blackOfficerNum) {
			
			int knightIns[] = {448,442,884,886,668,662,224,226};
			for(int cnt2 : knightIns) {
				if(Unit.knight[cnt].getLocate()!= null) Unit.knight[cnt].move(cnt2);
				if(whiteCheck()) {					//üũ���̳�?
					Locate.loadLocate();					//�ϴ����� ������� �س����� �˻������ϱ�
					if(cnt==3 && cnt2 == 226) kn = false;		//üũ���ε� ���� üũ���̼̳�? �׷� ����Ʈ�� ������
					else continue;						//���� �˻��Ұ� ���������ϱ� ���� ins �˻��ҰԿ�
				}
				Locate.loadLocate();						//üũ���ƴ��������ϱ� ��������س���
				break;								//�����Կ� �����ֵ� �˻��Ϸ� ���ϴ�.
			}
			
			int bishopDir[] = {1,3,7,9};
			for(int cnt2 : bishopDir) {
				int len[] = {1,2,3,4,5,6,7};
				for(int cnt3 : len) {
					if(Unit.bishop[cnt].getLocate()!= null) Unit.bishop[cnt].move(cnt2, cnt3);
					if(whiteCheck()) {
						Locate.loadLocate();							//�ϴ����� ������� �س����� �˻������ϱ�
						if(cnt==3 && cnt2 == 9 && cnt3 == 7) bi =false;	//üũ���ε� ���� üũ���̼̳�? �׷� ����������׿�
						else continue; 				//���� �˻��Ұų��������ϱ� break���ϰ� ���� ���̽���˻��մϴ�.
					}
					Locate.loadLocate();						//üũ���ƴ��������ϱ�
					break;								//�����Կ� �����ֵ� �˻��Ϸ� ���ϴ�.
				}
			}
			int rookDir[] = {2,4,6,8};
			for(int cnt2 : rookDir) {
				int len[] = {1,2,3,4,5,6,7};
				for(int cnt3 : len) {
					if(Unit.rook[cnt].getLocate()!= null) Unit.rook[cnt].move(cnt2, cnt3);
					if(whiteCheck()) {
						Locate.loadLocate();							//�ϴ����� ������� �س����� �˻������ϱ�
						if(cnt==1 && cnt2 == 8 && cnt3 == 7) ro =false;	//üũ���ε� ���� üũ���̼̳�? �׷� �赵���������׿�
						else continue; 				//���� �˻��Ұų��������ϱ� break���ϰ� ���� ���̽���˻��մϴ�.
					}
					Locate.loadLocate();						//üũ���ƴ��������ϱ�
					break;								//�����Կ� �����ֵ� �˻��Ϸ� ���ϴ�.
				}
			}
		}				//white officer����
		///////////////////////////////////////////////////////////////////
		int queenDir[] = {1,3,7,9,2,4,6,8};
		for(int cnt2 : queenDir) {
			int len[] = {1,2,3,4,5,6,7};
			for(int cnt3 : len) {
				if(Unit.queen[1].getLocate()!= null) Unit.queen[1].move(cnt2, cnt3);
				if(whiteCheck()) {
					Locate.loadLocate();							//�ϴ����� ������� �س����� �˻������ϱ�
					if(cnt2 == 8 && cnt3 == 7) qu =false;	//üũ���ε� ���� üũ���̼̳�? �׷� �������������׿�
					else continue; 				//���� �˻��Ұų��������ϱ� break���ϰ� ���� ���̽���˻��մϴ�.
				}
				Locate.loadLocate();						//üũ���ƴ��������ϱ�
				break;								//�����Կ� �����ֵ� �˻��Ϸ� ���ϴ�.
			}
		}
		int num[] = {1,2,3,4,6,7,8,9};
		for (int cnt: num) {
			Unit.king[1].move(cnt);
			if(whiteCheck()) {
				Locate.loadLocate();							//�ϴ����� ������� �س����� �˻������ϱ�
				if(cnt==9) ki =false;	//üũ���ε� ���� üũ���̼̳�? �׷� �������������׿�
				else continue; 				//���� �˻��Ұų��������ϱ� break���ϰ� ���� ���̽���˻��մϴ�.
			}
			Locate.loadLocate();						//üũ���ƴ��������ϱ�
			break;	
		}
		if (pa||kn||bi||ro||qu||ki) {				//�������� ������������ ���д�.
		}
		else {										//�ƹ��� �������� ��.
			System.out.println("..but checkMAte!");
			terminateGame(true);
		}
		victim = tmp;
		System.out.println("Checked.");
	}
	public static void whiteTurn(Scanner sc,GUIboard g,Server server) {
		String str;
		boolean castled = false;
		int a =8,b =8;
		while(true) {
			displayBoard();
			displayKillLog();
			System.out.println("�� White turn : please type the xy-coordinate of");
			System.out.println("a unit you want to control");
			str = catchCoordinate(server.getSender(),true);
			boolean isWhite = true;
			if(castled || str.equals("continue")) break;																			//ĳ���� �������ڵ�
			else if(str.equals("castling") || str.equals("Castling")  || str.equals("CASTLING") ){			//ĳ���� �������ڵ�
				sc.nextLine();
				str = sc.nextLine();
			}							//�׳� �����ڵ�
			if(str.length()!= 2) {
				System.out.println("Inappropriate length-You typed wrong form of xy cordinate. please retype");
				continue;
			}
			a = (int)str.charAt(0);
			b = (int)str.charAt(1);
			if((0<=a && a<=7) || (0<=b && b<=7) ) {
				System.out.println("This kind of input is not allowed. please retype");
				continue;
			}
			else if(65 <= a && a <= 72) a -= 65;
			else if(97 <= a && a <= 104) a -= 97;
			if(49 <= b && b <= 56) b -= 49;
			if(a< 0 || a> 7 || b < 0 || b > 7 ) {
				System.out.println("You typed coordinate out of board. please retype");
				continue;
			}
			else if(Locate.location[a][b].getUnit() == null) {
				System.out.println(Locate.location[a][b].getUnit());
				System.out.println("You typed null space. please retype");
				continue;
			}
			else isWhite = Locate.location[a][b].getUnit().getIsWhite();
			
			if(!isWhite) {
				System.out.println("You typed the coordiante ofblack unit. please retype your unit");		//���̸� �ٽ��Է¹���
				continue;
			}
			Unit unit = Locate.location[a][b].getUnit();
			if(unit.beAbleToMove()) {
				g.getButton(a, b).setBackground(Color.BLUE);
				break;
			}
			else unit.notBeAbleToMovePrint();
		}	
		if(!castled && !str.equals("continue")) { 
		System.out.println("type the x and y of destination");
			
			while (true) {
				boolean result = instructMove(a, b, sc,server.getSender(), true);
				if(result) break;		//���� �����ϸ� ������.
				else System.out.println("sorry, your destination is invalid. retype.");
			}
		}
	}
	public static boolean whiteIsAbleToGo(Locate location) {
		boolean result = false;
		for(int cnt = 0 ; cnt < 8 ; cnt++) if(Unit.pawn[cnt].beAbleToGoTo(location)) result = true;
		for(int cnt = 0 ; cnt < 2 ; cnt++) {
			if(Unit.knight[cnt].beAbleToGoTo(location)) result = true;
			else if(Unit.bishop[cnt].beAbleToGoTo(location)) result = true;
			else if(Unit.rook[cnt].beAbleToGoTo(location)) result = true;
		}
		if(Unit.king[0].beAbleToGoTo(location)) result = true;
		if(Unit.queen[0].beAbleToGoTo(location)) result = true;
		return result;
	}
	public static boolean blackCastling(String str, Scanner sc,int input) {
		boolean result = false;
		Rook rooktogo = null;
		if(str.equals("castling") || str.equals("Castling")  || str.equals("CASTLING") ) {
			if(!Unit.king[1].isFirst()) {
				displayBoard();
				System.out.println("king have moved in game. you can not do castling");
				System.out.println("�� Black turn : please type the x and y of a unit you want to control");
				return false;
			}								//ŷ�� �̵��Ѱ��
			else if(whiteCheck()) {;
				displayBoard();
				System.out.println("king is on check condition. you can not do castling");
				System.out.println("�� Black turn : please type the x and y of a unit you want to control");
				return false;
			}								//ŷ�� üũ���°��ƴϾ���Ѵ�.
					//�Էº�//
			System.out.println("select.");
			System.out.println("1. Queen side Castling(left rook)");
			System.out.println("2. king side Castling(right rook)");
			/*while(true) {
				input = sc.nextInt();
				if(input == 1 || input == 2) break;
				else System.out.println("wrong input.retype.");
			}*/
			input-= 1;
					//�Է�����//
			/////////////////kingsidecastling/////////////////////////////////
			if(input ==1) {
				if(Locate.location[1][7].isEmpty() && Locate.location[2][7].isEmpty() && Locate.location[3][7].isEmpty()) {
					if(Unit.rook[2].isFirst()) {
						if(!(whiteIsAbleToGo(Locate.location[1][7])||whiteIsAbleToGo(Locate.location[2][7])||whiteIsAbleToGo(Locate.location[3][7]))) {
							rooktogo = Unit.rook[2];		//queenside�� �ٲ���
							rooktogo.out();
							rooktogo.setLocate(Locate.location[3][7]);
							Unit.king[1].out();
							Unit.king[1].setLocate(Locate.location[2][7]);
							result = true;
						}
						else {
							displayBoard();
							System.out.println("Path is attacked!.you can not do castling.");
							System.out.println("�� Black turn : please type the x and y of a unit you want to control");
							displayBoard();
							return false;
						}
					}
					else {
						displayBoard();
						System.out.println("rook has moved.you can not do castling.");
						System.out.println("�� Black turn : please type the x and y of a unit you want to control");
						return false;
					}
				}
				else {
					displayBoard();
					System.out.println("location is not empty.you can not do castling.");
					System.out.println("�� Black turn : please type the x and y of a unit you want to control");
					return false;
				}
			}
			/////////////////queensidecastling/////////////////////////////////
			else if(input == 2) {
				if(Locate.location[5][7].isEmpty() && Locate.location[6][7].isEmpty()) {
					if(Unit.rook[3].isFirst()) {
						if(!(whiteIsAbleToGo(Locate.location[5][7])||whiteIsAbleToGo(Locate.location[6][7]))) {
							rooktogo = Unit.rook[3];		//queenside�� �ٲ���
							rooktogo.out();
							rooktogo.setLocate(Locate.location[5][7]);
							Unit.king[1].out();
							Unit.king[1].setLocate(Locate.location[6][7]);
							result = true;
						}
						else {
							displayBoard();
							System.out.println("Path is attacked!.you can not do castling.");
							System.out.println("�� Black turn : please type the x and y of a unit you want to control");
							return false;
						}
					}
					else {
						displayBoard();
						System.out.println("rook has moved.you can not do castling.");
						System.out.println("�� Black turn : please type the x and y of a unit you want to control");
						return false;
					}
				}
				else {
					displayBoard();
					System.out.println("location is not empty.you can not do castling.");
					System.out.println("�� Black turn : please type the x and y of a unit you want to control");
					return false;
				}
			}
		}
		if(rooktogo!=null) rooktogo.noMoreFirst();
		return result;
	}
	public static boolean blackCheck() {						//���������� ��ŷ�� ���ϼ��ִ°�?
		boolean result = false;
		for(int cnt = 8 ; cnt < 16; cnt++) if(Unit.pawn[cnt].getLocate()!=null &&Unit.pawn[cnt].beAbleToKillKing()) return true;
		for(int cnt = 2 ; cnt < 4; cnt++) {
			if(Unit.knight[cnt].getLocate()!=null &&Unit.knight[cnt].beAbleToKillKing()) return true;
			else if(Unit.bishop[cnt].getLocate()!=null &&Unit.bishop[cnt].beAbleToKillKing()) return true;
			else if(Unit.rook[cnt].getLocate()!=null &&Unit.rook[cnt].beAbleToKillKing()) return true;
		}
		if(Unit.queen[1].getLocate()!=null &&Unit.queen[1].beAbleToKillKing()) return true;
		else if(Unit.king[1].getLocate()!=null &&Unit.king[1].beAbleToKillKing()) return true;
		return result;
	}
	public static void blackCheckMate() {
		System.out.println("Checking..");
		Unit tmp = victim;
		Locate.saveLocate();
		boolean pa=true, kn = true, bi = true,ro = true,qu= true,ki = true;
		////////////////////////////////////////////////////////////
		int whitePawnNum[] = {0,1,2,3,4,5,6,7};
		for (int cnt: whitePawnNum) {
			int ins[] = {4,6,8,7,9,88};
			if(Unit.pawn[cnt].getName().equals("Queen")) {
				
				int queenDir[] = {1,3,7,9,2,4,6,8};
				for(int cnt2 : queenDir) {
					int len[] = {1,2,3,4,5,6,7};
					for(int cnt3 : len) {
						if(Unit.pawn[cnt].getLocate()!= null) Unit.pawn[cnt].move(cnt2, cnt3);
						if(blackCheck()) {
							Locate.loadLocate();							//�ϴ����� ������� �س����� �˻������ϱ�
							if(cnt2 == 8 && cnt3 == 7) qu =false;	//üũ���ε� ���� üũ���̼̳�? �׷� �������������׿�
							else continue; 				//���� �˻��Ұų��������ϱ� break���ϰ� ���� ���̽���˻��մϴ�.
						}
						Locate.loadLocate();						//üũ���ƴ��������ϱ�
						break;								//�����Կ� �����ֵ� �˻��Ϸ� ���ϴ�.
					}
				}
				
			}
			else {
				for(int cnt2 : ins) {
					if(Unit.pawn[cnt].getLocate()!= null) Unit.pawn[cnt].move(cnt2);
					if(blackCheck()) {					//üũ���̳�?
						Locate.loadLocate();					//�ϴ����� ������� �س����� �˻������ϱ�
						if(cnt==7 && cnt2 == 88) pa = false;//üũ���ε� ���� üũ���̼̳�? �׷����� ���������׿�
						continue;						//���� �˻��Ұ� ���������ϱ� ���� ins �˻��ҰԿ�
					}
					Locate.loadLocate();						//üũ�������ƴ��������ϱ�
					break;								//�����Կ� �����ֵ� �˻��Ϸ� ���ϴ�.
				}
			}
		}
		///////////////////////////////////////////////////////////
		int whiteOfficerNum[] = {0,1};
		for (int cnt: whiteOfficerNum) {
			
			int knightIns[] = {448,442,884,886,668,662,224,226};
			for(int cnt2 : knightIns) {
				if(Unit.knight[cnt].getLocate()!= null) Unit.knight[cnt].move(cnt2);
				if(blackCheck()) {					//üũ���̳�?
					Locate.loadLocate();					//�ϴ����� ������� �س����� �˻������ϱ�
					if(cnt==1 && cnt2 == 226) kn = false;		//üũ���ε� ���� üũ���̼̳�? �׷� ����Ʈ�� ������
					else continue;						//���� �˻��Ұ� ���������ϱ� ���� ins �˻��ҰԿ�
				}
				Locate.loadLocate();						//üũ���ƴ��������ϱ� ��������س���
				break;								//�����Կ� �����ֵ� �˻��Ϸ� ���ϴ�.
			}
			
			int bishopDir[] = {1,3,7,9};
			for(int cnt2 : bishopDir) {
				int len[] = {1,2,3,4,5,6,7};
				for(int cnt3 : len) {
					if(Unit.bishop[cnt].getLocate()!= null) Unit.bishop[cnt].move(cnt2, cnt3);
					if(blackCheck()) {
						Locate.loadLocate();							//�ϴ����� ������� �س����� �˻������ϱ�
						if(cnt==1 && cnt2 == 9 && cnt3 == 7) bi =false;	//üũ���ε� ���� üũ���̼̳�? �׷� ����������׿�
						else continue; 				//���� �˻��Ұų��������ϱ� break���ϰ� ���� ���̽���˻��մϴ�.
					}
					Locate.loadLocate();						//üũ���ƴ��������ϱ�
					break;								//�����Կ� �����ֵ� �˻��Ϸ� ���ϴ�.
				}
			}
			int rookDir[] = {2,4,6,8};
			for(int cnt2 : rookDir) {
				int len[] = {1,2,3,4,5,6,7};
				for(int cnt3 : len) {
					if(Unit.rook[cnt].getLocate()!= null) Unit.rook[cnt].move(cnt2, cnt3);
					if(blackCheck()) {
						Locate.loadLocate();							//�ϴ����� ������� �س����� �˻������ϱ�
						if(cnt==1 && cnt2 == 8 && cnt3 == 7) ro =false;	//üũ���ε� ���� üũ���̼̳�? �׷� �赵���������׿�
						else continue; 				//���� �˻��Ұų��������ϱ� break���ϰ� ���� ���̽���˻��մϴ�.
					}
					Locate.loadLocate();						//üũ���ƴ��������ϱ�
					break;								//�����Կ� �����ֵ� �˻��Ϸ� ���ϴ�.
				}
			}
		}				//white officer����
		///////////////////////////////////////////////////////////////////
		int queenDir[] = {1,3,7,9,2,4,6,8};
		for(int cnt2 : queenDir) {
			int len[] = {1,2,3,4,5,6,7};
			for(int cnt3 : len) {
				if(Unit.queen[0].getLocate()!= null) Unit.queen[0].move(cnt2, cnt3);
				if(blackCheck()) {
					Locate.loadLocate();							//�ϴ����� ������� �س����� �˻������ϱ�
					if(cnt2 == 8 && cnt3 == 7) qu =false;	//üũ���ε� ���� üũ���̼̳�? �׷� �������������׿�
					else continue; 				//���� �˻��Ұų��������ϱ� break���ϰ� ���� ���̽���˻��մϴ�.
				}
				Locate.loadLocate();						//üũ���ƴ��������ϱ�
				break;								//�����Կ� �����ֵ� �˻��Ϸ� ���ϴ�.
			}
		}
		int num[] = {1,2,3,4,6,7,8,9};
		for (int cnt: num) {
			Unit.king[0].move(cnt);
			if(blackCheck()) {
				Locate.loadLocate();							//�ϴ����� ������� �س����� �˻������ϱ�
				if(cnt==9) ki =false;	//üũ���ε� ���� üũ���̼̳�? �׷� �������������׿�
				else continue; 				//���� �˻��Ұų��������ϱ� break���ϰ� ���� ���̽���˻��մϴ�.
			}
			Locate.loadLocate();						//üũ���ƴ��������ϱ�
			break;	
		}

		if (pa||kn||bi||ro||qu||ki) {				//�������� ������������ ���д�.
		}
		else {										//�ƹ��� �������� ��.
			System.out.println("..but checkMAte!");
			terminateGame(false);
		}
		victim = tmp;
		System.out.println("Checkd.");
	}
	public static void blackTurn( Scanner sc,GUIboard g, Server server) {
		String str2;
		boolean castled = false;
		int a = 8;
		int b = 8;
		boolean isWhite= false;
		while(true) {
			displayBoard();
			displayKillLog();
			System.out.println("��Black turn : please type the xy-coordinate of");
			System.out.println("a unit you want to control");
			str2 = catchCoordinate(server.getSender(),false);
			//castled = blackCastling(str2, sc);
			if(castled || str2.equals("continue")) break;																			//ĳ���� �������ڵ�
			else if(str2.equals("castling") || str2.equals("Castling")  || str2.equals("CASTLING") ){
				sc.nextLine();
				str2 = sc.nextLine();
			}							//�׳� �����ڵ�
			if(str2.length()!= 2) {
				System.out.println("Inappropriate length-You typed wrong form of xy cordinate. please retype");
				continue;
			}
			a = (int)str2.charAt(0);
			b = (int)str2.charAt(1);
			if((0<=a && a<=7) || (0<=b && b<=7) ) {
				System.out.println("This kind of input is not allowed. please retype");
				continue;
			}
			else if(65 <= a && a <= 72) a -= 65;
			else if(97 <= a && a <= 104) a -= 97;
			if(49 <= b && b <= 56) b -= 49;
			if(a< 0 || a> 7 || b < 0 || b > 7 ) {
				System.out.println("You typed coordinate out of board. please retype");
				continue;
			}
			else if(Locate.location[a][b].getUnit() == null) {
				System.out.println(Locate.location[a][b].getUnit());
				System.out.println("You typed null space. please retype");
				continue;
			}
			else isWhite = Locate.location[a][b].getUnit().getIsWhite();
			if(isWhite) {
				System.out.println("You typed the coordiante of white unit. please retype your unit");		//ȭ��Ʈ�� �ٽ��Է¹���
				continue;
			}
			Unit unit = Locate.location[a][b].getUnit();
			if(unit.beAbleToMove()) {
				g.getButton(a, b).setBackground(Color.BLUE);
				break;
			}
			else unit.notBeAbleToMovePrint();
		}			
		if(!castled && !str2.equals("continue")) {
			System.out.println("type the x and y of destination");
			while (true) {
				if(instructMove(a, b, sc, server.getSender(), false)) break;		//���� �����ϸ� �Է¹���.
				else System.out.println("sorry, your destination is invalid. retype.");
			}
		}
	}
	public static boolean blackIsAbleToGo(Locate location) {
		boolean result = false;
		for(int cnt = 8 ; cnt < 16 ; cnt++) if(Unit.pawn[cnt].beAbleToGoTo(location)) result = true;
		for(int cnt = 2 ; cnt < 4 ; cnt++) {
			if(Unit.knight[cnt].beAbleToGoTo(location)) result = true;
			else if(Unit.bishop[cnt].beAbleToGoTo(location)) result = true;
			else if(Unit.rook[cnt].beAbleToGoTo(location)) result = true;
		}
		if(Unit.king[1].beAbleToGoTo(location)) result = true;
		if(Unit.queen[1].beAbleToGoTo(location)) result = true;
		return result;
	}
	public static boolean instructMove(int x, int y, Scanner sc,Sender sender,boolean isWhite) {
		boolean out = false;
		int x2, y2;
		while(true) {							//������ �Էº�
			Chess.setStr("empty");
			String str = catchCoordinate(sender, isWhite);
			if(str.length()!= 2) {
				System.out.println("You typed wrong coordinate. please retype");
				continue;
			}
			x2 = (int)str.charAt(0);
			y2 = (int)str.charAt(1);
			if((0<=x2 && x2<=7) || (0<=y2 && y2<=7)) {
				System.out.println("You typed wrong coordinate. please retype");
				continue;
			}
			else if(65 <= x2 && x2 <= 72) x2 -= 65;
			else if(97 <= x2 && x2 <= 104) x2 -= 97;
			if(49 <= y2 && y2 <= 56) y2 -= 49;
			if((0<=x2 && x2<=7) && (0<=y2 && y2<=7)) break;
			else System.out.println("You typed wrong coordinate. please retype");
		}				//x,y,x2,y2�� 0<=<=7���� �����.�������Էº�����
		
		if( x == x2 && y == y2 ) {
			System.out.println("You have to move unit to another place.");
			return false;				
		}
		Locate where = Locate.location[x][y];
		Unit unit = where.getUnit();
		String name = where.getUnit().getName();
		if(name == "Pawn") {
			if( x2 == x - 1 && y2 == y) out = where.getUnit().move(4);			//����
			else if( x2 == x + 1 && y2 == y) out = where.getUnit().move(6);		//������
			else if( x2 == x && y2 == y+1) out = where.getUnit().move(8);		//���ѹ�
			else if( x2 == x && y2 == y+2) out = where.getUnit().move(88);		//���ι�
			else if( x2 == x && y2 == y-1) out = where.getUnit().move(2);		//�Ʒ��ѹ�
			else if( x2 == x && y2 == y-2) out = where.getUnit().move(22);		//�Ʒ��ι�
			else if( x2 == x-1 && y2 == y-1) out = where.getUnit().move(1);		//�»�
			else if( x2 == x-1 && y2 == y+1) out = where.getUnit().move(7);		//����
			else if( x2 == x+1 && y2 == y+1) out = where.getUnit().move(9);		//���
			else if( x2 == x+1 && y2 == y-1) out = where.getUnit().move(3);		//����
		}
		else if(name == "Knight") {
			if( x2 == x - 1 && y2 == y+2) out = where.getUnit().move(884);				//�����
			else if( x2 == x + 1 && y2 == y + 2) out = where.getUnit().move(886);		//����
			else if( x2 == x - 1 && y2 == y - 2) out = where.getUnit().move(224);		//������
			else if( x2 == x + 1 && y2 == y - 2) out = where.getUnit().move(226);		//���Ͽ�
			else if( x2 == x - 2 && y2 == y + 1) out = where.getUnit().move(448);		//���»�
			else if( x2 == x - 2 && y2 == y - 1) out = where.getUnit().move(442);		//������
			else if( x2 == x + 2 && y2 == y + 1) out = where.getUnit().move(668);		//����
			else if( x2 == x + 2 && y2 == y - 1) out = where.getUnit().move(662);		//�����
		}
		else if(name == "Bishop" && Math.abs(x-x2) == Math.abs(y-y2)) {			//1�������� x2�� y2�� ��ǲ�� ������ ��.
			int len = Math.abs(x-x2);
			int dir = 0;
			if(x2 < x && y2 > y) dir = 7;		//����
			else if (x2 > x && y2 > y) dir = 9;	//���
			else if (x2 < x && y2 < y) dir = 1;	//����
			else if (x2 > x && y2 < y) dir = 3;	//�Ͽ�
			out = where.getUnit().move(dir, len);
		}
		
		else if(name == "Rook") {
			int len = 0;
			int dir = 0;
			if(x2 == x && y2 > y) {					//��
				dir = 8;
				len = y2 - y;
			}
			else if (x2 == x && y2 <  y) {			//��
				dir = 2;
				len = y - y2;
			}
			else if (x2 <  x && y2 == y) {			//��
				dir = 4;
				len = x - x2;
			}
			else if (x2 >  x && y2 == y) {			//��
				dir = 6;
				len = x2 - x;
			}
			out = where.getUnit().move(dir, len);
		}
		
		else if(name == "Queen") {
			int len = 0;
			int dir = 0;
			if(x2 == x && y2 > y) {					//��
				dir = 8;
				len = y2 - y;
			}
			else if (x2 == x && y2 <  y) {			//��
				dir = 2;
				len = y - y2;
			}
			else if (x2 <  x && y2 == y) {			//��
				dir = 4;
				len = x - x2;
			}
			else if (x2 >  x && y2 == y) {			//��
				dir = 6;
				len = x2 - x;
			}
			else if( Math.abs(x-x2) == Math.abs(y-y2)) {
				len = Math.abs(x-x2);
				if(x2 < x && y2 > y) dir = 7;		//����
				else if (x2 > x && y2 > y) dir = 9;	//���
				else if (x2 < x && y2 < y) dir = 1;	//����
				else if (x2 > x && y2 < y) dir = 3;	//�Ͽ�
			}
			out = where.getUnit().move(dir, len);
		}
		else if(name == "King") {
			boolean castled = false;
			int dir = 0;
			//System.out.println("����Ǿ���1");
			if(unit.getCnt() == 0) {
				if(Locate.location[x2][y2].getUnit() != null &&Locate.location[x2][y2].getUnit().getName().equals("Rook")) {
					//System.out.println("����Ǿ���");
					int cnt =Locate.location[x2][y2].getUnit().getCnt();
					System.out.println(cnt);
					if(cnt == 0 || cnt ==1) castled = whiteCastling("castling",sc ,cnt);
				}
			}
			else {
				if(Locate.location[x2][y2].getUnit() != null &&Locate.location[x2][y2].getUnit().getName().equals("Rook")) {
					int cnt =Locate.location[x2][y2].getUnit().getCnt();
					if(cnt == 2 || cnt ==3) castled = blackCastling("castling",sc ,cnt);
				}
			}
			if(x2 == x && y2 == y + 1) 		 dir = 8;			//��
			else if (x2 == x   && y2 == y-1) dir = 2;			//��
			else if (x2 == x-1 && y2 == y) dir = 4;			//��
			else if (x2 == x+1 && y2 == y) dir = 6;			//��
			else if(x2 == x-1 && y2 == y + 1) dir = 7;		//����
			else if (x2 == x+1 && y2 == y + 1) dir = 9;	//���
			else if (x2 == x-1 && y2 == y-1) dir = 1;	//����
			else if (x2 == x+1 && y2 == y-1) dir = 3;	//�Ͽ�
			
			if(!castled) out = where.getUnit().move(dir);
			else out = true;
		}
		if(out && name.equals("Pawn")) {
			if(unit.getIsWhite()) {
				if(y2 == 7) unit.promote();
			}
			else {
				if(y2==0) unit.promote();
			}
		}
		unit.getLocate().setFirst(unit.isFirst());
		return out;
	}
	public static void displayKillLog() {
		if(victim!=null) {
			if(victim.getMurder().getIsWhite()) System.out.println("white " + victim.getMurder().getName() + " killed Black " + victim.getName());
			else System.out.println("Black " + victim.getMurder().getName() + " killed white " + victim.getName());
			victim = null;
		}
	}
	public static void displayBoard() {
		System.out.println("displayBoard is worked.");
		System.out.print("0 ");
		for (int cnt = 65 ; cnt <= 72 ; cnt++) {
			System.out.print((char)cnt+"�� ");
		}
		System.out.println("0");
		for (int cnt2 = 7 ; cnt2 >= 0 ; cnt2--) { //���ο����� X�� 0~7, Y�� 0~7�� �۵��մϴ�.
			System.out.print((cnt2+1)+" ");
			for (int cnt = 0 ; cnt < 8 ; cnt++) {
				if(Locate.location[cnt][cnt2].isEmpty()) {
					if(cnt2 % 2 == 0) {
						if(cnt%2==0)System.out.print("��� ") ;
						else System.out.print("��� ") ;
					}
					else {
						if(cnt%2==0)System.out.print("��� ") ;
						else System.out.print("��� ") ;
					}
					continue;
				}
				if(Locate.location[cnt][cnt2].getUnit().getIsWhite()) System.out.print("��");
				else System.out.print("��");
				String name = Locate.location[cnt][cnt2].getUnit().getName();
				if(name.equals("King"))	System.out.print("K");
				else if(name.equals("Queen"))	System.out.print("Q");
				else if(name.equals("Rook"))	System.out.print("R");
				else if(name.equals("Bishop"))	System.out.print("B");
				else if(name.equals("Knight"))	System.out.print("N");
				else System.out.print("P");
				System.out.print(" ");
			}	
			System.out.println((cnt2+1));
		}
		System.out.print("0 ");
		for (int cnt = 65 ; cnt <= 72 ; cnt++) {
			System.out.print((char)cnt+"�� ");
		}
		System.out.println("0");
	}
	//ü���� ����
	public static void makeBoard() {
		boolean isWhite;
		int fix, x;			
		for (int cnt = 0 ; cnt < 8 ; cnt++) {
			for (int cnt2 = 0 ; cnt2 < 8 ; cnt2++) {
				Locate.location[cnt][cnt2] = new Locate(cnt,cnt2);		//ü���� ��ġ ��ü �ν��Ͻ�ȭ
			}														//���ο����� X�� 0~7, Y�� 0~7�� �۵��մϴ�.
		}
		for (int cnt = 0 ; cnt < 8 ; cnt++) {
			for (int cnt2 = 0 ; cnt2 < 8 ; cnt2++) {
				Locate.location2[cnt][cnt2] = new Locate(cnt,cnt2);		//ü���� ��ġ ��ü �ν��Ͻ�ȭ
			}														//���ο����� X�� 0~7, Y�� 0~7�� �۵��մϴ�.
		}
		
	//pawn ����
		isWhite = true;												//������ üũ���ִ� �����Դϴ�.											
		fix = 0;													//fix�� white�϶� 0 , black�϶� 5�ǰ��� ���մϴ�.
		x = 0;
		for (int cnt = 0; cnt < 16 ; cnt++) {
			if (cnt==8){											//cnt�� 8�� �Ǿ� black�̸�
				x = 0;
				isWhite = false; 
				fix += 5;
			}
			Unit.pawn[cnt] = new Pawn(Locate.location[x][1+fix], isWhite, cnt);	//���� �ν��Ͻ�ȭ�ϸ� ��ġ�� ������ �Է����ݴϴ�.
			x++;													//cnt�� �Բ� x��ǥ�� ����
		}
		
	//knight,bishop,rook ����
		isWhite = true;												//������ üũ���ִ� �����Դϴ�.											
		fix = 0;													//fix�� white�϶� 0 , black�϶� 5�ǰ��� ���մϴ�.
		for (int cnt = 0 ; cnt < 4; cnt++) {						//0,1->white 1,2->black
			//knight ����
			switch (cnt) {
				case 2: 
					isWhite = false; 
					fix += 7;										//cnt�� 2�̸� �׶����ʹ� black
				case 0: 
					x = 1; 
					break;											//cnt�� 0,2�� x���� 1�Ҵ�
				default : 
					x = 6;											//cnt�� 1,3�̸� x���� 6�Ҵ�
			}
			Unit.knight[cnt] = new Knight(Locate.location[x][fix], isWhite, cnt);	//����Ʈ�� �ν��Ͻ�ȭ�ϸ� ��ġ�� ������ �Է����ݴϴ�.
			
			//bishop ����
			switch (cnt) {
			case 2: 
			case 0: 
				x = 2; 
				break;												//cnt�� 0,2�� x���� 2�Ҵ�
			default : 
				x = 5;												//cnt�� 1,3�̸� x���� 5�Ҵ�
			}
			Unit.bishop[cnt] = new Bishop(Locate.location[x][fix], isWhite, cnt);	//����� �ν��Ͻ�ȭ�ϸ� ��ġ�� ������ �Է����ݴϴ�.
			
			//rook ����
			switch (cnt) {
			case 2: 
			case 0: 
				x = 0; 
				break;												//cnt�� 0,2�� x���� 0�Ҵ�
			default : 
				x = 7;												//cnt�� 1,3�̸� x���� 7�Ҵ�
			}
			Unit.rook[cnt] = new Rook(Locate.location[x][fix], isWhite, cnt);		//���� �ν��Ͻ�ȭ�ϸ� ��ġ�� ������ �Է����ݴϴ�.
		}
	
	//queen�� king ����
		isWhite = true;
		fix = 0;
		for(int cnt = 0 ; cnt < 2; cnt++) {
			if(cnt==1) {											//cnt�� 1�̸� �׶����ʹ� black
				isWhite = false; 
				fix += 7;
			}
			Unit.queen[cnt] = new Queen(Locate.location[3][fix], isWhite, cnt);		//���� �ν��Ͻ�ȭ�ϸ� ��ġ�� ������ �Է����ݴϴ�.
			Unit.king[cnt] = new King(Locate.location[4][fix],isWhite, cnt);			//ŷ�ν��Ͻ�ȭ
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String catchCoordinate(Sender sender,boolean areUsender) {
		String str="empty";
		try {
			for(Chess.setStr("empty"); str.equals("empty") ;Thread.sleep(10)) 
				str = Chess.getStr();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Chess.setStr("empty");
		System.out.println(str);
		if(areUsender) sender.getBox().setStr(str);
		return str;
	}
	public static void setTime(GUIboard g,boolean isWhite) {			//�ð��� �帧�� �ٲ��ݴϴ�.
		if(isWhite) {
			g.getConTimeRun(1).setIsRunning(true);
			g.getConTimeRun(2).setIsRunning(false);
			g.getTimer(1).setForeground(Color.BLACK);
			g.getTimer(2).setForeground(Color.GRAY);
		}
		else {
			g.getConTimeRun(1).setIsRunning(false);
			g.getConTimeRun(2).setIsRunning(true);
			g.getTimer(1).setForeground(Color.GRAY);
			g.getTimer(2).setForeground(Color.BLACK);
		}
	}
	public static void gameStart(Scanner sc) {
		GUIboard g =new GUIboard("Chess 1.0 White");
		Server server = new Server(g);
		server.start();
		while(server.getSender()== null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		g.setVisible(true);
		g.setAlwaysOnTop(true);
		while(true) {
			Locate.saveLocate();//������ ��ġ���̺�..
			if(Chess.turn.equals("TRUE"))cleaner(server);
			whiteTurn(sc,g,server);
			if(Chess.blackCheck()) {
				Locate.loadLocate();
				g.boardSet();
				System.out.println("Your king will be dead. retype.(Check)");
				continue;
			}
			else if(Chess.whiteCheck()) System.out.println("White checks Black. Hide king!");
			else System.out.println("White's turn ends.");
			g.boardSet();
			setTime(g,false);
			whiteCheckMate();
			if(!Chess.king_live) break;
			Locate.saveLocate();
			if(Chess.turn.equals("FALSE"))cleaner(server);
			while(true) {
				blackTurn(sc,g ,server);
				if(Chess.whiteCheck()) {
					System.out.println("Your king will be dead. retype.(Check)");
					Locate.loadLocate();
					g.boardSet();
					continue;
				}
				else if(Chess.blackCheck()) System.out.println("black checks White. Hide king!");
				else System.out.println("Black's turn ends.");
				g.boardSet();
				break;
			}
			setTime(g,true);
			blackCheckMate();
			if(!Chess.king_live) break;
		}
	}
	public static void cleaner(Server server) {
		server.getReader().getBox().setStr("empty");
		server.getSender().getBox().setStr("empty");
		Chess.setStr("empty");
		Chess.turn = "no";
	}
	public static void terminateGame(boolean winner) {				//������ �������ְ� king-live flag�� false�� ����ϴ�.
		if(winner)System.out.println("winner is white :D");
		else System.out.println("winner is black ^^7");
		Chess.king_live = false;
	}
	public static void main(String[] args) {
		System.out.println("Java Chess 1.0 Proto type");
		Scanner sc = new Scanner(System.in);
		makeBoard();
		gameStart(sc);
		System.out.println("Game is end.");
		displayBoard();
		sc.close();
		System.exit(0);
	}

}