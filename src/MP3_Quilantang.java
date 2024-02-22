public class MP3_Quilantang {
    public static char[] op = new char[100];
    public static char[] pos = new char[100];
    public static double[] res = new double[100];
    public static void main(String[] args) throws Exception {
        String infix = "4-1";
        System.out.println("infix Form: "+infix);

        String postfix = infToPos(infix);
        System.out.println("Postfix Form: "+postfix);

        double res = result(postfix);
        System.out.println("Result: "+res);
       //display postfix
    }
    public static String infToPos(String inf) throws Exception {
        for(char ch : inf.toCharArray())
        {
            if(isOperator(ch)) {
                while (Top(op)!=-1 && getPrecedence(ch) <= getPrecedence(op[Top(op)])) {
                    Push(store(Pop(op)), pos);
                }
                Push(ch, op);
            }
            else if(ch=='(')
            {
                Push(ch,op);
            }
            else if(ch==')')
            {
                while(Top(op) != -1 && op[Top(op)] !='(') {
                    Push(Pop(op), pos);
                    if(Top(op) != -1 && op[Top(op)] == '('){
                        Pop(op);
                        break;
                    }
                }
            }
            else if(isOperand(ch))
            {

                Push(ch, pos);
            }
        }
        while(Top(op)!=-1)
        {
            Push(Pop(op), pos);
        }
        return new String(pos, 0, Top(pos) + 1);
    }

    public static double result(String postfix) throws Exception {
        if(Top(res) != -1) {
            for (char ch : postfix.toCharArray()) {
                if (isOperand(ch)) {
                    String temp = ""+ch;
                    Push(Double.parseDouble(temp), res);
                } else if (isOperator(ch)) {
                    char op = ch;
                    double opd2 = Pop(res);
                    double opd1 = Pop(res);
                    double result = operations(opd1, opd2, op);
                    Push(result, res);

                }
            }
        }
        return res[0];
    }

     //*/
    public static double operations(double opd1, double opd2, char op)
    {
        switch(op)
        {
            case '+':
                return opd1 + opd2;
            case '-':
                return opd1 - opd2;
            case '*':
                return opd1 * opd2;
            case '/':
                if(opd2 == 0) throw new ArithmeticException("Anything divided by zero is undefined.");
                return opd1 / opd2;
            case '^':
                for(int i = 0; i < opd2 - 1; i++) {
                    opd1 = opd1 * opd2;
                }
                return opd1;
            default:
                throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }

    public static void Push(char element, char []stac) throws Exception
    {
        int top = Top(stac);
        top++;
        stac[top] = element;
    }
    public static void Push(double element, double []stac) throws Exception
    {
        int top = Top(stac);
        top++;
        stac[top] = element;
    }
    public static char Pop(char []stac) throws Exception
    {
        char temp = 0;
        int top = Top(stac);
        temp = store(stac[top]);
        stac[top] = 0;
        return temp;
    }
    public static double Pop(double []stac) throws Exception
    {
        double temp = 0;
        int top = Top(stac);
        temp = store(stac[top]);
        stac[top] = 0;
        return temp;
    }
    public static char store(char value)
    {
        char temp = value;
        return temp;
    }
    public static double store(double value)
    {
        double temp = value;
        return temp;
    }
    public static int Top(char []stac)
    {
        int top = 0;
        for(int i = 0; i<stac.length; i++)
        {
            if(stac[i]==0) {
                top = i-1;
                break;
            }
        }
        return top;
    }
    public static int Top(double []stac)
    {
        int top = 0;
        for(int i = 0; i<stac.length; i++)
        {
            if(stac[i]==0) {
                top = i-1;
                break;
            }
        }
        return top;
    }
    public static boolean isEmpty(char []stac)
    {
        for(int i = 0; i < stac.length; i++)
        {
            if(stac[i] != 0)
            {
                return false;
            }
        }
        return true;
    }
    public static boolean isFull(char []stac)
    {
        for(int i = 0; i < stac.length; i++)
        {
            if(stac[i] == 0)
            {
                return false;
            }
        }
        return true;
    }
    public static boolean isOperand(char value)
    {
        if(value == '0' || value == '1' || value == '2' || value == '3' || value == '4' || value == '5' || value == '6' || value == '7' || value == '8' || value == '9')
        {
            return true;
        }
        else return false;
    }
    public static boolean isOperator(char value)
    {
        if(value == '+' || value == '-' || value == '*' || value == '/' || value == '^')
        {
            return true;
        }
        else return false;
    }
    public static int getPrecedence(char operator) {
        switch (operator) {
            case '^':
                return 3;
            case '*':
            case '/':
                return 2;
            case '+':
            case '-':
                return 1;
            default:
                return 0;
        }
    }

}


