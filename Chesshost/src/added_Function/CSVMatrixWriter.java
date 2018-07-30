package added_Function;

import java.util.ArrayList;
import java.io.*;

public class CSVMatrixWriter {
	String nowPath = new String();
	public CSVMatrixWriter (String nowPath) {
		this.nowPath = nowPath;
	}
	public void writer (ArrayList<ArrayList<String>> dataList, String Filename, int p1, int p2, boolean whosturn) {
		try {
			System.out.println(System.getProperty("user.dir"));
			FileOutputStream fis = new FileOutputStream(nowPath+"\\"+Filename);
			OutputStreamWriter isr = new OutputStreamWriter(fis);
			BufferedWriter br = new BufferedWriter(isr);
			for( int cnt = 0; cnt < dataList.size() ; cnt++ ) {
				for ( int i = 0 ; i < dataList.get(cnt).size() ; i ++) {
					br.write(dataList.get(cnt).get(i));
					br.write(",");
				}
				br.write("\n");
			}
			br.write(String.valueOf(p1)+","+String.valueOf(p2)+","+whosturn+"\n");
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
	
}
