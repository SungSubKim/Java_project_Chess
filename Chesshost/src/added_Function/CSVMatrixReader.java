package added_Function;

import java.util.ArrayList;		//33�̷��� ���������� �ȵȴ� �������� ���͵�
import java.io.*;				//��� ������ �����ȴٴ� ��ǲ�� ����

public class CSVMatrixReader {
	String nowPath = new String();
	ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
	public CSVMatrixReader (String nowPath) {
		this.nowPath = nowPath;
	}
	public ArrayList<ArrayList<String>> reader(String Filename) {
		try {
			FileInputStream fis = new FileInputStream(nowPath+"\\"+Filename);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String data = new String();
			
			for( int cnt = 0; (data = br.readLine()) != null ; cnt++ ) {
				dataList.add(new ArrayList<String>());
				String[] dataArray = data.split(",");
				for ( int i = 0 ; i < dataArray.length ; i ++) {
					dataList.get(cnt).add(dataArray[i]);
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
		}
		return dataList;
	}
}
