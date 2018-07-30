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
				System.out.println("☆ White turn : please type the xy-coordinate of a unit you want to control");
				return false;
			}								//킹이 이동한경우
			else if(blackCheck()) {
				displayBoard();
				System.out.println("king is on check condition. you can not do castling");
				System.out.println("☆ White turn : please type the xy-coordinate of a unit you want to control");
				return false;
			}								//킹이 체크상태가아니어야한다.
					//입력부//
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
					//입력종료//
			/////////////////queensidecastling/////////////////////////////////
			if(input ==1) {
				if(Locate.location[1][0].isEmpty() && Locate.location[2][0].isEmpty() && Locate.location[3][0].isEmpty()) {//가는길이 비어있는가
					if(Unit.rook[0].isFirst()) {			//rook은 처음움직이세요?
						if(!(blackIsAbleToGo(Locate.location[1][0])||blackIsAbleToGo(Locate.location[2][0])||blackIsAbleToGo(Locate.location[3][0]))) {
							System.out.println("실행됨");
							rooktogo = Unit.rook[0];		//queenside로 바꿔줌
							rooktogo.out();
							rooktogo.setLocate(Locate.location[3][0]);
							Unit.king[0].out();
							Unit.king[0].setLocate(Locate.location[2][0]);
							result = true;
						}
						else {
							displayBoard();
							System.out.println("Path is attacked!.you can not do castling.");
							System.out.println("☆ White turn : please type the xy-coordinate of a unit you want to control");
							return false;
						}
					}
					else {
						displayBoard();
						System.out.println("rook has moved.you can not do castling.");
						System.out.println("☆ White turn : please type the xy-coordinate of a unit you want to control");
						return false;
					}
				}
				else {
					displayBoard();
					System.out.println("location is not empty.you can not do castling.");
					System.out.println("☆ White turn : please type the xy-coordinate of a unit you want to control");
					return false;
				}
			}
			/////////////////kingsidecastling/////////////////////////////////
			else if(input == 2) {
				if(Locate.location[5][0].isEmpty() && Locate.location[6][0].isEmpty()) {
					if(Unit.rook[1].isFirst()) {
						if(!(blackIsAbleToGo(Locate.location[5][0])||blackIsAbleToGo(Locate.location[6][0]))) {
							rooktogo = Unit.rook[1];		//queenside로 바꿔줌
							rooktogo.out();
							rooktogo.setLocate(Locate.location[5][0]);
							Unit.king[0].out();
							Unit.king[0].setLocate(Locate.location[6][0]);
							result = true;
						}
						else {
							displayBoard();
							System.out.println("Path is attacked!.you can not do castling.");
							System.out.println("☆ White turn : please type the xy-coordinate of a unit you want to control");
							return false;
						}
					}
					else {
						displayBoard();
						System.out.println("rook has moved.you can not do castling.");
						System.out.println("☆ White turn : please type the xy-coordinate of a unit you want to control");
						return false;
					}
				}
				else {
					displayBoard();
					System.out.println("location is not empty.you can not do castling.");
					System.out.println("☆ White turn : please type the xy-coordinate of a unit you want to control");
					return false;
				}
			}
		}
		if(rooktogo!=null) rooktogo.noMoreFirst();
		System.out.println("whitecastling result"+result);
		return result;
	}
	public static boolean whiteCheck() {			//화이트가 체크에 성공하였다.
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
							Locate.loadLocate();							//일단판은 원래대로 해놓을게 검사했으니까
							if(cnt2 == 15 && cnt3 == 7) qu =false;	//체크블랙인데 전부 체크블랙이셨네? 그럼 퀸도막을수없네요
							else continue; 				//아직 검사할거남아있으니까 break안하고 다음 케이스들검사합니다.
						}
						Locate.loadLocate();						//체크블랙아닌지봤으니까
						break;								//나갈게여 다음애들 검사하러 갑니다.
					}
				}
				
			}
			else {
				for(int cnt2 : ins) {
					if(Unit.pawn[cnt].getLocate()!= null) {
						Unit.pawn[cnt].move(cnt2);
					}
					if(whiteCheck()) {					//체크블랙이네?
						Locate.loadLocate();					//일단판은 원래대로 해놓을게 검사했으니까
						if(cnt==15 && cnt2 == 22) pa = false;//체크블랙인데 전부 체크블랙이셨네? 그럼폰은 막을수없네요
						continue;						//아직 검사할거 남아있으니까 다음 ins 검사할게여
					}
					Locate.loadLocate();						//체크블랙인지아닌지봤으니까
					break;								//나갈게여 다음애들 검사하러 갑니다.
				}
			}
		}
		///////////////////////////////////////////////////////////
		int blackOfficerNum[] = {2,3};
		for (int cnt: blackOfficerNum) {
			
			int knightIns[] = {448,442,884,886,668,662,224,226};
			for(int cnt2 : knightIns) {
				if(Unit.knight[cnt].getLocate()!= null) Unit.knight[cnt].move(cnt2);
				if(whiteCheck()) {					//체크블랙이네?
					Locate.loadLocate();					//일단판은 원래대로 해놓을게 검사했으니까
					if(cnt==3 && cnt2 == 226) kn = false;		//체크블랙인데 전부 체크블랙이셨네? 그럼 나이트는 못막쥐
					else continue;						//아직 검사할거 남아있으니까 다음 ins 검사할게여
				}
				Locate.loadLocate();						//체크블랙아닌지봤으니까 원래대로해놓구
				break;								//나갈게여 다음애들 검사하러 갑니다.
			}
			
			int bishopDir[] = {1,3,7,9};
			for(int cnt2 : bishopDir) {
				int len[] = {1,2,3,4,5,6,7};
				for(int cnt3 : len) {
					if(Unit.bishop[cnt].getLocate()!= null) Unit.bishop[cnt].move(cnt2, cnt3);
					if(whiteCheck()) {
						Locate.loadLocate();							//일단판은 원래대로 해놓을게 검사했으니까
						if(cnt==3 && cnt2 == 9 && cnt3 == 7) bi =false;	//체크블랙인데 전부 체크블랙이셨네? 그럼 비숍막을수없네요
						else continue; 				//아직 검사할거남아있으니까 break안하고 다음 케이스들검사합니다.
					}
					Locate.loadLocate();						//체크블랙아닌지봤으니까
					break;								//나갈게여 다음애들 검사하러 갑니다.
				}
			}
			int rookDir[] = {2,4,6,8};
			for(int cnt2 : rookDir) {
				int len[] = {1,2,3,4,5,6,7};
				for(int cnt3 : len) {
					if(Unit.rook[cnt].getLocate()!= null) Unit.rook[cnt].move(cnt2, cnt3);
					if(whiteCheck()) {
						Locate.loadLocate();							//일단판은 원래대로 해놓을게 검사했으니까
						if(cnt==1 && cnt2 == 8 && cnt3 == 7) ro =false;	//체크블랙인데 전부 체크블랙이셨네? 그럼 룩도막을수없네요
						else continue; 				//아직 검사할거남아있으니까 break안하고 다음 케이스들검사합니다.
					}
					Locate.loadLocate();						//체크블랙아닌지봤으니까
					break;								//나갈게여 다음애들 검사하러 갑니다.
				}
			}
		}				//white officer종료
		///////////////////////////////////////////////////////////////////
		int queenDir[] = {1,3,7,9,2,4,6,8};
		for(int cnt2 : queenDir) {
			int len[] = {1,2,3,4,5,6,7};
			for(int cnt3 : len) {
				if(Unit.queen[1].getLocate()!= null) Unit.queen[1].move(cnt2, cnt3);
				if(whiteCheck()) {
					Locate.loadLocate();							//일단판은 원래대로 해놓을게 검사했으니까
					if(cnt2 == 8 && cnt3 == 7) qu =false;	//체크블랙인데 전부 체크블랙이셨네? 그럼 퀸도막을수없네요
					else continue; 				//아직 검사할거남아있으니까 break안하고 다음 케이스들검사합니다.
				}
				Locate.loadLocate();						//체크블랙아닌지봤으니까
				break;								//나갈게여 다음애들 검사하러 갑니다.
			}
		}
		int num[] = {1,2,3,4,6,7,8,9};
		for (int cnt: num) {
			Unit.king[1].move(cnt);
			if(whiteCheck()) {
				Locate.loadLocate();							//일단판은 원래대로 해놓을게 검사했으니까
				if(cnt==9) ki =false;	//체크블랙인데 전부 체크블랙이셨네? 그럼 퀸도막을수없네요
				else continue; 				//아직 검사할거남아있으니까 break안하고 다음 케이스들검사합니다.
			}
			Locate.loadLocate();						//체크블랙아닌지봤으니까
			break;	
		}
		if (pa||kn||bi||ro||qu||ki) {				//누군가가 막을수있으면 냅둔다.
		}
		else {										//아무도 못막으면 끝.
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
			System.out.println("☆ White turn : please type the xy-coordinate of");
			System.out.println("a unit you want to control");
			str = catchCoordinate(server.getSender(),true);
			boolean isWhite = true;
			if(castled || str.equals("continue")) break;																			//캐슬링 성공한자들
			else if(str.equals("castling") || str.equals("Castling")  || str.equals("CASTLING") ){			//캐슬링 실패한자들
				sc.nextLine();
				str = sc.nextLine();
			}							//그냥 가는자들
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
				System.out.println("You typed the coordiante ofblack unit. please retype your unit");		//블랙이면 다시입력받음
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
				if(result) break;		//무빙 성공하면 나간다.
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
				System.out.println("★ Black turn : please type the x and y of a unit you want to control");
				return false;
			}								//킹이 이동한경우
			else if(whiteCheck()) {;
				displayBoard();
				System.out.println("king is on check condition. you can not do castling");
				System.out.println("★ Black turn : please type the x and y of a unit you want to control");
				return false;
			}								//킹이 체크상태가아니어야한다.
					//입력부//
			System.out.println("select.");
			System.out.println("1. Queen side Castling(left rook)");
			System.out.println("2. king side Castling(right rook)");
			/*while(true) {
				input = sc.nextInt();
				if(input == 1 || input == 2) break;
				else System.out.println("wrong input.retype.");
			}*/
			input-= 1;
					//입력종료//
			/////////////////kingsidecastling/////////////////////////////////
			if(input ==1) {
				if(Locate.location[1][7].isEmpty() && Locate.location[2][7].isEmpty() && Locate.location[3][7].isEmpty()) {
					if(Unit.rook[2].isFirst()) {
						if(!(whiteIsAbleToGo(Locate.location[1][7])||whiteIsAbleToGo(Locate.location[2][7])||whiteIsAbleToGo(Locate.location[3][7]))) {
							rooktogo = Unit.rook[2];		//queenside로 바꿔줌
							rooktogo.out();
							rooktogo.setLocate(Locate.location[3][7]);
							Unit.king[1].out();
							Unit.king[1].setLocate(Locate.location[2][7]);
							result = true;
						}
						else {
							displayBoard();
							System.out.println("Path is attacked!.you can not do castling.");
							System.out.println("★ Black turn : please type the x and y of a unit you want to control");
							displayBoard();
							return false;
						}
					}
					else {
						displayBoard();
						System.out.println("rook has moved.you can not do castling.");
						System.out.println("★ Black turn : please type the x and y of a unit you want to control");
						return false;
					}
				}
				else {
					displayBoard();
					System.out.println("location is not empty.you can not do castling.");
					System.out.println("★ Black turn : please type the x and y of a unit you want to control");
					return false;
				}
			}
			/////////////////queensidecastling/////////////////////////////////
			else if(input == 2) {
				if(Locate.location[5][7].isEmpty() && Locate.location[6][7].isEmpty()) {
					if(Unit.rook[3].isFirst()) {
						if(!(whiteIsAbleToGo(Locate.location[5][7])||whiteIsAbleToGo(Locate.location[6][7]))) {
							rooktogo = Unit.rook[3];		//queenside로 바꿔줌
							rooktogo.out();
							rooktogo.setLocate(Locate.location[5][7]);
							Unit.king[1].out();
							Unit.king[1].setLocate(Locate.location[6][7]);
							result = true;
						}
						else {
							displayBoard();
							System.out.println("Path is attacked!.you can not do castling.");
							System.out.println("★ Black turn : please type the x and y of a unit you want to control");
							return false;
						}
					}
					else {
						displayBoard();
						System.out.println("rook has moved.you can not do castling.");
						System.out.println("★ Black turn : please type the x and y of a unit you want to control");
						return false;
					}
				}
				else {
					displayBoard();
					System.out.println("location is not empty.you can not do castling.");
					System.out.println("★ Black turn : please type the x and y of a unit you want to control");
					return false;
				}
			}
		}
		if(rooktogo!=null) rooktogo.noMoreFirst();
		return result;
	}
	public static boolean blackCheck() {						//흑진영에서 백킹을 죽일수있는가?
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
							Locate.loadLocate();							//일단판은 원래대로 해놓을게 검사했으니까
							if(cnt2 == 8 && cnt3 == 7) qu =false;	//체크블랙인데 전부 체크블랙이셨네? 그럼 퀸도막을수없네요
							else continue; 				//아직 검사할거남아있으니까 break안하고 다음 케이스들검사합니다.
						}
						Locate.loadLocate();						//체크블랙아닌지봤으니까
						break;								//나갈게여 다음애들 검사하러 갑니다.
					}
				}
				
			}
			else {
				for(int cnt2 : ins) {
					if(Unit.pawn[cnt].getLocate()!= null) Unit.pawn[cnt].move(cnt2);
					if(blackCheck()) {					//체크블랙이네?
						Locate.loadLocate();					//일단판은 원래대로 해놓을게 검사했으니까
						if(cnt==7 && cnt2 == 88) pa = false;//체크블랙인데 전부 체크블랙이셨네? 그럼폰은 막을수없네요
						continue;						//아직 검사할거 남아있으니까 다음 ins 검사할게여
					}
					Locate.loadLocate();						//체크블랙인지아닌지봤으니까
					break;								//나갈게여 다음애들 검사하러 갑니다.
				}
			}
		}
		///////////////////////////////////////////////////////////
		int whiteOfficerNum[] = {0,1};
		for (int cnt: whiteOfficerNum) {
			
			int knightIns[] = {448,442,884,886,668,662,224,226};
			for(int cnt2 : knightIns) {
				if(Unit.knight[cnt].getLocate()!= null) Unit.knight[cnt].move(cnt2);
				if(blackCheck()) {					//체크블랙이네?
					Locate.loadLocate();					//일단판은 원래대로 해놓을게 검사했으니까
					if(cnt==1 && cnt2 == 226) kn = false;		//체크블랙인데 전부 체크블랙이셨네? 그럼 나이트는 못막쥐
					else continue;						//아직 검사할거 남아있으니까 다음 ins 검사할게여
				}
				Locate.loadLocate();						//체크블랙아닌지봤으니까 원래대로해놓구
				break;								//나갈게여 다음애들 검사하러 갑니다.
			}
			
			int bishopDir[] = {1,3,7,9};
			for(int cnt2 : bishopDir) {
				int len[] = {1,2,3,4,5,6,7};
				for(int cnt3 : len) {
					if(Unit.bishop[cnt].getLocate()!= null) Unit.bishop[cnt].move(cnt2, cnt3);
					if(blackCheck()) {
						Locate.loadLocate();							//일단판은 원래대로 해놓을게 검사했으니까
						if(cnt==1 && cnt2 == 9 && cnt3 == 7) bi =false;	//체크블랙인데 전부 체크블랙이셨네? 그럼 비숍막을수없네요
						else continue; 				//아직 검사할거남아있으니까 break안하고 다음 케이스들검사합니다.
					}
					Locate.loadLocate();						//체크블랙아닌지봤으니까
					break;								//나갈게여 다음애들 검사하러 갑니다.
				}
			}
			int rookDir[] = {2,4,6,8};
			for(int cnt2 : rookDir) {
				int len[] = {1,2,3,4,5,6,7};
				for(int cnt3 : len) {
					if(Unit.rook[cnt].getLocate()!= null) Unit.rook[cnt].move(cnt2, cnt3);
					if(blackCheck()) {
						Locate.loadLocate();							//일단판은 원래대로 해놓을게 검사했으니까
						if(cnt==1 && cnt2 == 8 && cnt3 == 7) ro =false;	//체크블랙인데 전부 체크블랙이셨네? 그럼 룩도막을수없네요
						else continue; 				//아직 검사할거남아있으니까 break안하고 다음 케이스들검사합니다.
					}
					Locate.loadLocate();						//체크블랙아닌지봤으니까
					break;								//나갈게여 다음애들 검사하러 갑니다.
				}
			}
		}				//white officer종료
		///////////////////////////////////////////////////////////////////
		int queenDir[] = {1,3,7,9,2,4,6,8};
		for(int cnt2 : queenDir) {
			int len[] = {1,2,3,4,5,6,7};
			for(int cnt3 : len) {
				if(Unit.queen[0].getLocate()!= null) Unit.queen[0].move(cnt2, cnt3);
				if(blackCheck()) {
					Locate.loadLocate();							//일단판은 원래대로 해놓을게 검사했으니까
					if(cnt2 == 8 && cnt3 == 7) qu =false;	//체크블랙인데 전부 체크블랙이셨네? 그럼 퀸도막을수없네요
					else continue; 				//아직 검사할거남아있으니까 break안하고 다음 케이스들검사합니다.
				}
				Locate.loadLocate();						//체크블랙아닌지봤으니까
				break;								//나갈게여 다음애들 검사하러 갑니다.
			}
		}
		int num[] = {1,2,3,4,6,7,8,9};
		for (int cnt: num) {
			Unit.king[0].move(cnt);
			if(blackCheck()) {
				Locate.loadLocate();							//일단판은 원래대로 해놓을게 검사했으니까
				if(cnt==9) ki =false;	//체크블랙인데 전부 체크블랙이셨네? 그럼 퀸도막을수없네요
				else continue; 				//아직 검사할거남아있으니까 break안하고 다음 케이스들검사합니다.
			}
			Locate.loadLocate();						//체크블랙아닌지봤으니까
			break;	
		}

		if (pa||kn||bi||ro||qu||ki) {				//누군가가 막을수있으면 냅둔다.
		}
		else {										//아무도 못막으면 끝.
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
			System.out.println("★Black turn : please type the xy-coordinate of");
			System.out.println("a unit you want to control");
			str2 = catchCoordinate(server.getSender(),false);
			//castled = blackCastling(str2, sc);
			if(castled || str2.equals("continue")) break;																			//캐슬링 성공한자들
			else if(str2.equals("castling") || str2.equals("Castling")  || str2.equals("CASTLING") ){
				sc.nextLine();
				str2 = sc.nextLine();
			}							//그냥 가는자들
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
				System.out.println("You typed the coordiante of white unit. please retype your unit");		//화이트면 다시입력받음
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
				if(instructMove(a, b, sc, server.getSender(), false)) break;		//무빙 성공하면 입력받음.
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
		while(true) {							//도착지 입력부
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
		}				//x,y,x2,y2는 0<=<=7임이 보장됨.도착지입력부종료
		
		if( x == x2 && y == y2 ) {
			System.out.println("You have to move unit to another place.");
			return false;				
		}
		Locate where = Locate.location[x][y];
		Unit unit = where.getUnit();
		String name = where.getUnit().getName();
		if(name == "Pawn") {
			if( x2 == x - 1 && y2 == y) out = where.getUnit().move(4);			//왼쪽
			else if( x2 == x + 1 && y2 == y) out = where.getUnit().move(6);		//오른쪽
			else if( x2 == x && y2 == y+1) out = where.getUnit().move(8);		//위한번
			else if( x2 == x && y2 == y+2) out = where.getUnit().move(88);		//위두번
			else if( x2 == x && y2 == y-1) out = where.getUnit().move(2);		//아래한번
			else if( x2 == x && y2 == y-2) out = where.getUnit().move(22);		//아래두번
			else if( x2 == x-1 && y2 == y-1) out = where.getUnit().move(1);		//좌상
			else if( x2 == x-1 && y2 == y+1) out = where.getUnit().move(7);		//좌하
			else if( x2 == x+1 && y2 == y+1) out = where.getUnit().move(9);		//우상
			else if( x2 == x+1 && y2 == y-1) out = where.getUnit().move(3);		//우하
		}
		else if(name == "Knight") {
			if( x2 == x - 1 && y2 == y+2) out = where.getUnit().move(884);				//상상좌
			else if( x2 == x + 1 && y2 == y + 2) out = where.getUnit().move(886);		//상상우
			else if( x2 == x - 1 && y2 == y - 2) out = where.getUnit().move(224);		//하하좌
			else if( x2 == x + 1 && y2 == y - 2) out = where.getUnit().move(226);		//하하우
			else if( x2 == x - 2 && y2 == y + 1) out = where.getUnit().move(448);		//좌좌상
			else if( x2 == x - 2 && y2 == y - 1) out = where.getUnit().move(442);		//좌좌하
			else if( x2 == x + 2 && y2 == y + 1) out = where.getUnit().move(668);		//우우상
			else if( x2 == x + 2 && y2 == y - 1) out = where.getUnit().move(662);		//우우하
		}
		else if(name == "Bishop" && Math.abs(x-x2) == Math.abs(y-y2)) {			//1차적으로 x2와 y2의 인풋에 제한을 검.
			int len = Math.abs(x-x2);
			int dir = 0;
			if(x2 < x && y2 > y) dir = 7;		//상좌
			else if (x2 > x && y2 > y) dir = 9;	//상우
			else if (x2 < x && y2 < y) dir = 1;	//하좌
			else if (x2 > x && y2 < y) dir = 3;	//하우
			out = where.getUnit().move(dir, len);
		}
		
		else if(name == "Rook") {
			int len = 0;
			int dir = 0;
			if(x2 == x && y2 > y) {					//상
				dir = 8;
				len = y2 - y;
			}
			else if (x2 == x && y2 <  y) {			//하
				dir = 2;
				len = y - y2;
			}
			else if (x2 <  x && y2 == y) {			//좌
				dir = 4;
				len = x - x2;
			}
			else if (x2 >  x && y2 == y) {			//우
				dir = 6;
				len = x2 - x;
			}
			out = where.getUnit().move(dir, len);
		}
		
		else if(name == "Queen") {
			int len = 0;
			int dir = 0;
			if(x2 == x && y2 > y) {					//상
				dir = 8;
				len = y2 - y;
			}
			else if (x2 == x && y2 <  y) {			//하
				dir = 2;
				len = y - y2;
			}
			else if (x2 <  x && y2 == y) {			//좌
				dir = 4;
				len = x - x2;
			}
			else if (x2 >  x && y2 == y) {			//우
				dir = 6;
				len = x2 - x;
			}
			else if( Math.abs(x-x2) == Math.abs(y-y2)) {
				len = Math.abs(x-x2);
				if(x2 < x && y2 > y) dir = 7;		//상좌
				else if (x2 > x && y2 > y) dir = 9;	//상우
				else if (x2 < x && y2 < y) dir = 1;	//하좌
				else if (x2 > x && y2 < y) dir = 3;	//하우
			}
			out = where.getUnit().move(dir, len);
		}
		else if(name == "King") {
			boolean castled = false;
			int dir = 0;
			//System.out.println("실행되었따1");
			if(unit.getCnt() == 0) {
				if(Locate.location[x2][y2].getUnit() != null &&Locate.location[x2][y2].getUnit().getName().equals("Rook")) {
					//System.out.println("실행되었따");
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
			if(x2 == x && y2 == y + 1) 		 dir = 8;			//상
			else if (x2 == x   && y2 == y-1) dir = 2;			//하
			else if (x2 == x-1 && y2 == y) dir = 4;			//좌
			else if (x2 == x+1 && y2 == y) dir = 6;			//우
			else if(x2 == x-1 && y2 == y + 1) dir = 7;		//상좌
			else if (x2 == x+1 && y2 == y + 1) dir = 9;	//상우
			else if (x2 == x-1 && y2 == y-1) dir = 1;	//하좌
			else if (x2 == x+1 && y2 == y-1) dir = 3;	//하우
			
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
			System.out.print((char)cnt+"♠ ");
		}
		System.out.println("0");
		for (int cnt2 = 7 ; cnt2 >= 0 ; cnt2--) { //내부에서는 X축 0~7, Y축 0~7로 작동합니다.
			System.out.print((cnt2+1)+" ");
			for (int cnt = 0 ; cnt < 8 ; cnt++) {
				if(Locate.location[cnt][cnt2].isEmpty()) {
					if(cnt2 % 2 == 0) {
						if(cnt%2==0)System.out.print("■■ ") ;
						else System.out.print("□□ ") ;
					}
					else {
						if(cnt%2==0)System.out.print("□□ ") ;
						else System.out.print("■■ ") ;
					}
					continue;
				}
				if(Locate.location[cnt][cnt2].getUnit().getIsWhite()) System.out.print("○");
				else System.out.print("●");
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
			System.out.print((char)cnt+"♠ ");
		}
		System.out.println("0");
	}
	//체스판 생성
	public static void makeBoard() {
		boolean isWhite;
		int fix, x;			
		for (int cnt = 0 ; cnt < 8 ; cnt++) {
			for (int cnt2 = 0 ; cnt2 < 8 ; cnt2++) {
				Locate.location[cnt][cnt2] = new Locate(cnt,cnt2);		//체스판 위치 객체 인스턴스화
			}														//내부에서는 X축 0~7, Y축 0~7로 작동합니다.
		}
		for (int cnt = 0 ; cnt < 8 ; cnt++) {
			for (int cnt2 = 0 ; cnt2 < 8 ; cnt2++) {
				Locate.location2[cnt][cnt2] = new Locate(cnt,cnt2);		//체스판 위치 객체 인스턴스화
			}														//내부에서는 X축 0~7, Y축 0~7로 작동합니다.
		}
		
	//pawn 생성
		isWhite = true;												//진영을 체크해주는 변수입니다.											
		fix = 0;													//fix는 white일땐 0 , black일땐 5의값을 지닙니다.
		x = 0;
		for (int cnt = 0; cnt < 16 ; cnt++) {
			if (cnt==8){											//cnt가 8이 되어 black이면
				x = 0;
				isWhite = false; 
				fix += 5;
			}
			Unit.pawn[cnt] = new Pawn(Locate.location[x][1+fix], isWhite, cnt);	//폰을 인스턴스화하며 위치와 진영을 입력해줍니다.
			x++;													//cnt와 함께 x좌표도 증가
		}
		
	//knight,bishop,rook 생성
		isWhite = true;												//진영을 체크해주는 변수입니다.											
		fix = 0;													//fix는 white일땐 0 , black일땐 5의값을 지닙니다.
		for (int cnt = 0 ; cnt < 4; cnt++) {						//0,1->white 1,2->black
			//knight 생성
			switch (cnt) {
				case 2: 
					isWhite = false; 
					fix += 7;										//cnt가 2이면 그때부터는 black
				case 0: 
					x = 1; 
					break;											//cnt가 0,2면 x값에 1할당
				default : 
					x = 6;											//cnt가 1,3이면 x값에 6할당
			}
			Unit.knight[cnt] = new Knight(Locate.location[x][fix], isWhite, cnt);	//나이트를 인스턴스화하며 위치와 진영을 입력해줍니다.
			
			//bishop 생성
			switch (cnt) {
			case 2: 
			case 0: 
				x = 2; 
				break;												//cnt가 0,2면 x값에 2할당
			default : 
				x = 5;												//cnt가 1,3이면 x값에 5할당
			}
			Unit.bishop[cnt] = new Bishop(Locate.location[x][fix], isWhite, cnt);	//비숍을 인스턴스화하며 위치와 진영을 입력해줍니다.
			
			//rook 생성
			switch (cnt) {
			case 2: 
			case 0: 
				x = 0; 
				break;												//cnt가 0,2면 x값에 0할당
			default : 
				x = 7;												//cnt가 1,3이면 x값에 7할당
			}
			Unit.rook[cnt] = new Rook(Locate.location[x][fix], isWhite, cnt);		//룩을 인스턴스화하며 위치와 진영을 입력해줍니다.
		}
	
	//queen과 king 생성
		isWhite = true;
		fix = 0;
		for(int cnt = 0 ; cnt < 2; cnt++) {
			if(cnt==1) {											//cnt가 1이면 그때부터는 black
				isWhite = false; 
				fix += 7;
			}
			Unit.queen[cnt] = new Queen(Locate.location[3][fix], isWhite, cnt);		//퀸을 인스턴스화하며 위치와 진영을 입력해줍니다.
			Unit.king[cnt] = new King(Locate.location[4][fix],isWhite, cnt);			//킹인스턴스화
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
	public static void setTime(GUIboard g,boolean isWhite) {			//시계의 흐름을 바꿔줍니다.
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
			Locate.saveLocate();//성공시 위치세이브..
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
	public static void terminateGame(boolean winner) {				//게임을 종료해주고 king-live flag를 false로 만듭니다.
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