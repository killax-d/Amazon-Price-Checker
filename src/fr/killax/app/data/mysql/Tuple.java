package fr.killax.app.data.mysql;

public class Tuple {

	private Object[] objs;
	
	public Tuple(Object... objs) {
		this.objs = objs;
	}
	
	public String toString() {
		String ret = "(";
		for(int i = 0; i < objs.length; i++) {
			ret += (i != 0 ? ", " : "") + "'" + objs[i] + "'";
		}
		ret += ")";
		return ret;
	}
}
