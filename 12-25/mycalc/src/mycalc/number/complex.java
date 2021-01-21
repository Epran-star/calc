package mycalc.number;

public class complex {
	number num1;
	number num2;
	public complex(number num1,number num2) {
		this.num1 = new number(num1.numberToString());
		this.num2 = new number(num2.numberToString());
	}
	public complex(String num1,String num2) {
		this.num1 = new number(num1);
		this.num2 = new number(num2);
	}
	
	public complex add(complex comp1) {
		complex result = new complex("","");
		result.num1=this.num1.multiply(comp1.num2).add(this.num2.multiply(comp1.num1));
		result.num2=this.num2.multiply(comp1.num2);
		return result;
	}
	
	public complex sub(complex comp1) {
		complex result = new complex("","");
		result.num1=this.num1.multiply(comp1.num2).sub(this.num2.multiply(comp1.num1));
		result.num2=this.num2.multiply(comp1.num2);
		return result;
	}
	
	public complex multiply(complex comp1) {
		complex result = new complex("","");
		result.num1=this.num1.multiply(comp1.num1);
		result.num2=this.num2.multiply(comp1.num2);
		return result;
	}
	
	public complex divide(complex comp1){
		complex result = new complex("","");
		result.num1=this.num1.multiply(comp1.num2);
		result.num2=this.num2.multiply(comp1.num1);
		return result;
	}
	
	public complex power(complex comp1) throws Exception {
		number c=comp1.complexToNumber();
		complex result = new complex("","");
		result.num1=this.num1.power(c);
		result.num2=this.num2.power(c);
		return result;
	}
	
	public number complexToNumber() {
		number result=new number("");
		result =this.num1.divide(this.num2);
		return result;
	}
	
	public complex operate(char op,complex comp) throws Exception {
		if(op=='+') {
			return this.add(comp);
		}
		if(op=='-') {
			return this.sub(comp);
		}
		if(op=='*') {
			return this.multiply(comp);
		}
		if(op=='/') {
			return this.divide(comp);
		}
		if(op=='^') {
			return this.power(comp);
		}
		return null;
	}
	
	public void print() {
		num1.print();
		num2.print();
	}
}
