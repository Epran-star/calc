package mycalc.machine;

import java.util.ArrayList;
import java.util.List;



/**

 */
public class machine{
	boolean finished = false;
	boolean infinite = false;
	machine l;
	machine r;
	byte[] left = new byte[1];
	byte[] right = new byte[1];
	byte[] output = new byte[1];
	public machine(byte[] left,byte[] right){
		this.left=left;
		this.right=right;
	}
	
	public machine(String[] str){
		
	}
	
	public machine(){
		
	}
	
	public static byte[] add(byte[] left, byte[] right) {
		byte[] output = new byte[1];
		
		return output;
	}
}
