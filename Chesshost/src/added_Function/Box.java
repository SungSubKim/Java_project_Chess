package added_Function;

import java.util.ArrayList;

public class Box implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4341057918458148821L;
	private String str = "empty";
	private ArrayList<ArrayList<String>> map = null;
	public String getStr() {
		return this.str;
	}
	public ArrayList<ArrayList<String>> getMap() {
		return this.map;
	}
	public void setMap(ArrayList<ArrayList<String>> map) {
		this.map = map;
	}
	public void setStr(String string) {
		this.str = string;
	}
}