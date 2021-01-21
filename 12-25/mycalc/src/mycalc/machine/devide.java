package mycalc.machine;

import java.util.ArrayList;
import java.util.List;

public class devide extends machine{
	public devide(byte[] left, byte[] right){
		super(left, right);
	}
	public byte[] calculate(int num){
		return this.output;
	}
	public static byte[] add(ArrayList<Object> left, ArrayList<Object> right) {
		return null;
	}
}