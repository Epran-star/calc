package mycalc.number;

import java.util.ArrayList;
import mycalc.exception.*;

public class number{
	private boolean sign;//true is +, false is - 
	private ArrayList<Object> sequence = new ArrayList<>();//example:2.3141 in 2.3141e-10
	private int notation;//example:-10 in 2.3141e-10
	
	public number(String str) {
		char[] stringArr = str.toCharArray();
		this.sign = true;
		boolean dot=false;
		this.notation=0;
		for (char token:stringArr){
			switch(token) {
			case '-':
				this.sign = false;
				break;
			case '.':
				if(!dot) {
					this.notation = this.sequence.size()-1;
					dot=true;					
				}else {
					System.out.println("an error occured: number can not be resolved");
				}
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				this.sequence.add((byte)token-(byte)'0');
				break;
			default:
				System.out.println("an error occured: number can not be resolved");
			}
		}
		if(!dot) {
			this.notation=this.sequence.size()-1;
		}
	}
	
	public void reverseSign() {
		this.sign =! this.sign;
	}
	
	public int getLastSignificantDigitAbsolutePosition() {//example:-14 in 2.3141e-10
		return (int)(this.notation-this.sequence.size()+1);
	}
	
	public String numberToString() {
		this.indent();
		String str="";
		if(this.sign==false) {
			str = str.concat("-");
		}
		if(this.notation<-1) {
			str = str.concat(".");
				str = str.concat(repeatString("",-this.notation,"0"));
		}
		for (int i = 0; i < this.sequence.size(); i++){
			if(this.notation==i-1){
				str = str.concat(".");
			}
			str = str.concat(this.sequence.get(i).toString());
		}
		if(this.sequence.isEmpty()) {
			str="0";
			this.notation=0;
		}
		return str;
	}
	
	public void indent() {//wipe the redundant "0"s 
		for (int i = 0; i <= this.sequence.size()-1; i++){
			if((int)this.sequence.get(i) == 0) {
				this.sequence.remove(i);
				this.notation--;
				i--;
			}else {
				break;
			}	
		}
		for (int i = this.sequence.size()-1; i > this.notation && i>=0 ; i--){
			if((int)this.sequence.get(i) == 0) {
				this.sequence.remove(i);
			}else {
				break;
			}
		}
		//this.printSequence();
		//System.out.println(this.sign+"    "+this.notation);
	}
	
	public void print() {
		String str= "";
		str = str.concat(this.numberToString());
		System.out.println(str);
	}
	
	public void print_e_notation() {
		this.indent();
		String str="";
		if(!this.sign) {
			str = str.concat("-");
		}
		for (int i = 0; i < this.sequence.size(); i++){
			if(i==1){
				str = str.concat(".");
			}
			str = str.concat(this.sequence.get(i).toString());
		}
		if(this.notation>0) {
			str = str.concat("e+"+this.notation);
		}else if(this.notation<0) {
			str = str.concat("e"+this.notation);
		}
		System.out.println(str);
	}
	public void printSequence() {
		for (Object obj:this.sequence){
			System.out.print(obj);
		}
		System.out.println();
	}
	
	public int getNotation() {
		return this.notation;
	}
	
	public Object get(int position) {
		return this.sequence.get(position);
	}
	
	/*public void alignTo(number num) {	//align this number to the other by adding "0"s
		int diff = this.getNotation() - num.getNotation();
		if(diff<=0) {
			this.notation=this.notation - diff;
			for (int i=0;i<-diff;i++) {
				this.sequence.add(0,0);
			}
		}else if(diff>0) {
			
		}
	}*/
	
	public number add(number num){
		number copy = new number(this.numberToString());

		number addend = new number(num.numberToString());

		number result = new number(this.numberToString());
		result.sequence.clear();
		result.notation=0;
		
		
		//align		
		int aligna=0;
		int alignb=0;
		aligna=copy.getNotation()-addend.getNotation();
		alignb=copy.sequence.size()-addend.sequence.size()-aligna;
		//System.out.println(aligna+"    "+alignb);
		
		result.notation = addend.notation;
		if(aligna<0) {
			result.notation = addend.notation;
			for(int i=0;i<-aligna;i++) {
				copy.sequence.add(0,0);
			}
		}
		if(aligna>0) {
			result.notation = copy.notation;
			for(int i=0;i<aligna;i++) {
				addend.sequence.add(0,0);
			}
		}
		if(alignb<0) {
			for(int i=0;i<-alignb;i++) {
				copy.sequence.add(0);
			}
		}
		if(alignb>0) {
			for(int i=0;i<alignb;i++) {
				addend.sequence.add(0);
			}
		}
		
		
		//copy.printSequence();
		//System.out.println(copy.sign+"    "+copy.notation);
		//addend.printSequence();
		//System.out.println(addend.sign+"    "+addend.notation);
		//add them up
		int a;
		if(copy.sign==addend.sign) {
			int carry=0;
			for(int i=copy.sequence.size()-1;i>=0;i--) {
				a = (int)copy.sequence.get(i) + (int)addend.sequence.get(i) + carry;
				carry = a/10;
				a = a % 10;
				result.sequence.add(0,a);
			}
			if(carry>0) {
				result.notation++;
				result.sequence.add(0,carry);
			}
		}else {
			int carry=0;
			for(int i=copy.sequence.size()-1;i>=0;i--) {
				a = (int)copy.sequence.get(i) - (int)addend.sequence.get(i) +carry;
				carry=0;
				while(a<0) {
					carry--;
					a += 10;
				}
				result.sequence.add(0,a);
			}
			if(carry<0) {
				//System.out.println("opposite");
				result=num.add(this);
			}
		}
		//result.printSequence();
		//System.out.println(result.sign+"    "+result.notation);
		result.indent();
		return result;
	}

	public number sub(number num){
		number copy=new number(num.numberToString());
		copy.reverseSign();
		return this.add(copy);
	}
	
	public number multiply(number num) {
		number copy = new number(this.numberToString());

		number multiplier = new number(num.numberToString());

		number result = new number("0");
		
		if(copy.numberToString()=="0" | multiplier.numberToString()=="0") {
			return result;
		}
		
		int a=0;
		int size1=copy.sequence.size();
		int size2=multiplier.sequence.size();
		for(int i=1;i<=size1;i++) {
			for(int ii=1;ii<=size2;ii++) {
				if(result.sequence.size()-1-i-ii+2<0) {
					result.sequence.add(0,0);
				};
				a=(int)copy.sequence.get(size1-i)*(int)multiplier.sequence.get(size2-ii);
				int c=0;
				while(a!=0) {
					if(result.sequence.size()-1-i-ii+2-c<0) {
						result.sequence.add(0,0);
					};
					a=a+(int)result.sequence.get(result.sequence.size()-1-i-ii+2-c);
					result.sequence.set(result.sequence.size()-1-i-ii+2-c,a%10);
					a=a/10;
					c++;
				}
			}
		}
		result.sign=(copy.sign==multiplier.sign);
		result.notation=copy.notation+multiplier.notation+result.sequence.size()-copy.sequence.size()-multiplier.sequence.size()+1;
		//result.printSequence();
		//System.out.println(result.sign+"    "+result.notation);
		result.indent();
		return result;
	}
	

	
	public number power(number base,number power) throws Exception{
		if(!power.isInteger()) {
			throw new IllegalArgumentException("the power("+power.numberToString()+") here is not an integer");
		}
		
		if (power.numberToString()=="0")
	        return new number("1");
	    else if (power.mod2() == 1)
	        return this.power(base,power.sub(new number("1"))).multiply(base);
	    else
	    {
	        number temp = power(base, power.div2());
	        return temp.multiply(temp);
	    }
	}
	
	public number power(number power) throws Exception{
		return power(this,power);
	}
	
	public number divide(number num) {
		number copy= new number(this.numberToString());
		number divisor= new number(num.numberToString());
		number result= new number("0");
		number temp= new number("0");
		number a= new number("1");
		boolean sign=(copy.sign==divisor.sign);
		divisor.sign=true;
		copy.sign=true;
		a.notation=copy.notation-divisor.notation;
		for(int i=0;i<a.notation;i++) {
			a.sequence.add(0);
		}
		divisor.notation=copy.notation;
		for(int i=0;i<a.notation;i++) {
			divisor.sequence.add(0);
		}
		
		if(divisor.numberToString()=="0") {
			System.out.println("divisor cannot be 0");
			return result;
		}
		
		while(copy.numberToString()!="0") {
			temp=copy.sub(divisor);
			while(temp.sign==true) {
				copy=temp;
				result=result.add(a);
				temp=copy.sub(divisor);

			}
			if(a.notation<-100) {
				result.sign=sign;
				return result;
			}
			divisor.notation--;
			a.notation--;
		}
		result.sign=sign;
		return result;
	}
	
	public number div2() {
		number result= new number("");
		result.notation=this.notation;
		int a=0;
		for(int i=0;i<this.sequence.size();i++) {
			a=(int)this.sequence.get(i)+a*10;
			result.sequence.add(a/2);
			a=a%2;
		}
		while(a!=0) {
			a=a*10;
			result.sequence.add(a/2);
			a=a%2;	
		}
		return result;
	}
	
	public int mod2() {
		return (int)this.sequence.get(this.sequence.size()-1)%2;
	}
	
	
	public boolean isInteger() {
		if(this.sequence.size()<=this.notation+1) {
			return true;
		}
		return false;
	}
	
	public static String repeatString(String str, int n, String seg) {
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < n; i++) {
	      sb.append(str).append(seg);
	    }
	    return sb.substring(0, sb.length() - seg.length());
	  }
}

