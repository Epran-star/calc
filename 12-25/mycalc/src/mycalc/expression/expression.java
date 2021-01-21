package mycalc.expression;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import mycalc.number.complex;
import mycalc.number.number;



public class expression {
	
    private final List<Object> tokens; // �ñ��ʽ�е����� Token
    private List<String> types; // �ñ��ʽ�е����� Token
    private final boolean postfix;    // �ñ��ʽ�Ƿ�Ϊ��׺���ʽ�ı�ʶ

    public expression(List<Object> tokens,List<String> types, boolean postfix) {
        this.tokens = tokens;
        this.types = types;
        this.postfix = postfix;
    }

    public expression(List<Object> tokens) {
        this(tokens,null, false);
    }
    
	public static expression parse(String expr){
		String REG_EXPR
        = "\\s*("
        + "(\\(-(\\d*\\.\\d*|\\d+)\\))" + "|" // negative number
        + "(\\d*\\.\\d*|\\d+)" + "|" // number
        + "(\\+|-|\\*|/|\\^)" + "|" // operator
        + "(\\(|\\))" + "|" // ()
        + "([A-Za-z]+\\(.*\\))" // function
        + ")\\s*";

		Pattern PATTERN = Pattern.compile(REG_EXPR);
        
        Objects.requireNonNull(expr);
        expr = expr.trim();
        if (expr.startsWith("-") || expr.startsWith("+")) {
            expr = "0" + expr;
        }

        ArrayList<Object> ts = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        
        Matcher matcher = PATTERN.matcher(expr);
        int start = 0, end = expr.length();

        while (start < end) {
            matcher.region(start, end);
            if (matcher.lookingAt()) { 
                Object token = getToken(matcher);
                ts.add(token);
                String type = getType(matcher);
                types.add(type);
                start = matcher.end(); 

            } else {
                String errorExpr = expr.substring(start);
                throw new IllegalArgumentException("undefined characters in��" + errorExpr);
            }
        }

		return new expression(ts,types,false);
	}
	
    private static Object getToken(Matcher matcher) {
        // matcher.group(0) ƥ����������matcher.group(1) ƥ���һ������
        String m = matcher.group(1);

        if (m != null) {
            if (matcher.group(2) != null) { // negative number
                return new complex(new number("-" + matcher.group(3)), new number("1"));
            } else if (matcher.group(4) != null) { // number
                return new complex(new number(matcher.group(4)), new number("1"));

            } else if (matcher.group(5) != null) { // operator
                return matcher.group(5).charAt(0);

            } else if (matcher.group(6) != null) { // ()
                return matcher.group(6).charAt(0);

            } else if (matcher.group(7) != null) { // function
            	//--------------------
            	return 0;
            }
        }
        throw new RuntimeException("Expression.getToken: Unbelievable"); // �������������²��ᷢ��
    }

    
    private static String getType(Matcher matcher) {
        // matcher.group(0) ƥ����������matcher.group(1) ƥ���һ������
        String m = matcher.group(1);

        if (m != null) {
            if (matcher.group(2) != null) { // negative number
                return "number";
            } else if (matcher.group(4) != null) { // number
                return "number";

            } else if (matcher.group(5) != null) { // operator
                return "operator";

            } else if (matcher.group(6) != null) { // ()
                return "bracket";

            } else if (matcher.group(7) != null) { // function
            	//--------------------
            	return "function";
            }
        }
        throw new RuntimeException("Expression.getToken: Unbelievable"); // �������������²��ᷢ��
    }
    
    public expression toPostfixExpr() {
        ArrayDeque<Object> S = new ArrayDeque<>(); // �����ջ
        ArrayList<Object> L = new ArrayList<>();   // �����м������б�
        
        int i=0;
        for (Object token : tokens) {
            switch (types.get(i)) {
                case "number":
                    L.add(token);
                    break;

                case "operator":
                    boolean back = true;

                    while (back) {
                        back = false;

                        if (S.isEmpty()) { // �����ջΪ��
                            S.push(token);

                        } else {  // �����ջ��Ϊ��
                            Object top = S.peek();

                            // �����ջջ��Ϊ '('
                            if (top.toString().charAt(0)=='(') {
                                S.push(token);

                                // op �����ȼ����������ջջ��Ԫ�ص����ȼ�
                            } else if (expression.priority(token.toString().charAt(0))>expression.priority(top.toString().charAt(0))) {
                                S.push(token);

                            } else { // op �����ȼ�С�������ջջ��Ԫ�ص����ȼ�
                                L.add(S.pop());
                                back = true; // �ص� while
                            }
                        }
                    }
                    break;
                case "bracket":
                    if (token.toString().charAt(0)=='(') {         	
                        S.push(token);
                    } else {
                        for (Object t = S.pop(); t.toString().charAt(0)!='('; t = S.pop()) {
                            L.add(t);
                        }
                    }
                    break;
                default:
                    break;
            }
            i++;
        }
        while (!S.isEmpty()) {
            L.add(S.pop());
        }
        return new expression(L,null, true); // true ��ʾ�ñ��ʽΪ��׺���ʽ
    }
    
    public complex calculate() throws Exception {
        if (this.postfix==false) {
            System.out.println("error");
        }

        ArrayDeque<Object> stack = new ArrayDeque<>();

        for (Object token : tokens) {

            if (token.toString().charAt(0)!='+'&&token.toString().charAt(0)!='-'&&token.toString().charAt(0)!='*'&&token.toString().charAt(0)!='/'&&token.toString().charAt(0)!='^') {
                stack.push(token);

            } else {
                complex n1 = (complex) stack.pop();
                complex n2 = (complex) stack.pop();
                char op = token.toString().charAt(0);

                complex result = n2.operate(op, n1);
                stack.push(result);
            }

        }

        if (stack.size() != 1) { // ջ�����ʣ�µĲ�ֹһ������˵�����ʽ������
        	System.out.println("error");;
        }

        return (complex) stack.pop();
    }
    
    public static int priority(char str) {
    	if(str=='^') {
    		return 2;
    	}
    	if(str=='*'|str=='/') {
    		return 1;
    	}
    	
    	return 0;
    }
}
