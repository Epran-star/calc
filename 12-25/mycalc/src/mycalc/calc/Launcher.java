package mycalc.calc;

import mycalc.expression.*;
import mycalc.number.*;
import mycalc.machine.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;


/**

 */
public class Launcher {

    public static void main(String[] args) throws Exception {

        System.out.println("TESTING");

        try (Reader in = new InputStreamReader(System.in);
                BufferedReader reader = new BufferedReader(in)) {

            String line;
            while (true) {
                System.out.print("> ");
                line = reader.readLine();

                if (null == line
                        || "e".equalsIgnoreCase(line)
                        || "exit".equalsIgnoreCase(line)) {
                    break;
                } else if (line.isEmpty()) {
                    continue;
                }
                //
                
                /*System.out.println("notation:"+num.getNotation());
                System.out.println("last:"+num.getLastSignificantDigitAbsolutePosition());
                System.out.println(num.numberToString());*/
                /*num.alignTo(new number("21946712647182"));
                num.print();
                num.printSequence();
                num.print_e_notation();
                num.printSequence();
                System.out.println(num.getNotation());
                System.out.println(num.getLastSignificantDigitAbsolutePosition());*/
                
                
                
                /*complex comp1=new complex("-298144456555555555489789748964564658997.1456856472","1");
                complex comp2=new complex(line,"1");
                
                comp1.print();
                comp2.print();
                
                complex result = comp1.power(comp2);
                
                System.out.println("-298144456555555555489789748964564658997.1456856472¡¤"+num.numberToString()+"=");
                result.complexToNumber().print();*/
                
                /*number a=num;
                number b=new number("2");
                number c=new number("-2");
                number d=new number("1");
                a.multiply(d).add(c.multiply(b)).print();
                b.multiply(d).print();*/
                
                complex a=expression.parse(line).toPostfixExpr().calculate();
                
                a.complexToNumber().print();
            }
            
        }
    }
}
