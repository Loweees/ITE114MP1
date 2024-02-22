/*
Quilantang, Luis Marco R.
CCC121 - IT2B.1
MACHINE PROBLEM 3 - INFIX TO POSTFIX CONVERSION AND STACK OPERATIONS

This program will convert infix expressions into postfix expression using the basic stack operations.
This program will also get the result of the postfix expression, alsu using the basic stack operations.
*/
import java.lang.String;
import java.util.*;
public class MP3_QuilantangV2 {
    public static Scanner scan = new Scanner(System.in);
    public static void main(String[] args)
    {
        int yorn = 0;
        do{
            char[] op = new char[100];
            String[] stack = new String[100];
            String[] pos = new String[100];
            double[] res = new double[100];
            Arrays.fill(stack, "");
            infPosRes(pos, op, res);
            do
           /*this do-while loop handles the choice "yes or no". Within the loop it analyses the user's input. this loop ends whether the user inputs a yes
              or a no regardless. however, if the user inputs a yes, the whole code will run again, but if the user inputs a no, the program will end.*/
            {
                try
                {
                    System.out.println("Do you want to try again? Press 1 for yes, Press 2 for no");
                    yorn = Integer.parseInt(scan.nextLine());
                    if(!(yorn==1||yorn==2))//handles the input that are neither the choices. (other integer inputs besides 1 or 2)
                    {
                        System.out.println("Input one of the two number choices only, try again.");
                    }
                    if(yorn==2)//handles the input that is 2
                    {
                        System.out.println("Program Terminating...");
                        System.out.println("Program end");
                        System.exit(1);
                    }
                }
                catch(NumberFormatException e)//handles the input that are neither the choices. (string inputs)
                {
                    System.out.println("Input one of the two number choices only, try again.");
                    yorn=0;
                }
            }while(yorn!=1);
        }while(yorn==1);
    }
    public static void infPosRes(String[] pos, char[] op, double[] res)
    {
        System.out.println("Enter Infix Form: ");
        String infix = scan.nextLine();
        System.out.println("infix Form: ");
        char[] ch = infix.toCharArray();
        for(int i = 0; i<ch.length; i++)
        {
            if(i!=ch.length&&isOperand(ch[i])){
                if(((i+1)!=ch.length)&&(isOperand(ch[i+1]))&&(isOperand(ch[i+2])))
                {
                    System.out.print(ch[i] + "" + ch[i + 1]);
                    if(isOperand(ch[i+2])) {
                    System.out.println(ch[i + 2]);
                    i++;
                    }
                    i++;
                }
                else if(((i+1)!=ch.length)&&(isOperand(ch[i+1]))){
                    System.out.print(ch[i] + "" + ch[i + 1]);
                    i++;
                    System.out.println("");
                }
                else System.out.println(ch[i]);
            }
            if(isOperator(ch[i])||ch[i]=='('||ch[i]==')')
            {
                System.out.println(ch[i]);
            }
        }
        try {
            infToPos(infix, pos, op);
            System.out.println("Postfix Form: ");
            for(int i = 0; i < pos.length; i++)
            {
                if(pos[i+1].equals(""))
                {
                    System.out.println(pos[+i]+" ");
                    break;
                }
                else
                {
                    System.out.println(pos[i]+" ");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        double results;
        try{
            results = result(pos,res);
            System.out.println("Result: "+results);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //display postfix
    }
    public static String infToPos(String inf, String[] pos, char[] op) throws Exception
    {
        Arrays.fill(pos, "");
        char[] ch = inf.toCharArray();
        for(int i = 0; i<ch.length; i++)
        {
            if((i)!=ch.length){
                if(isOperator(ch[i])) {
                    while (!isEmpty(op) && getPrecedence(ch[i]) <= getPrecedence(op[Top(op)])) {
                        Push(Pop(op), pos);
                    }
                    Push(ch[i], op);
                }
                else if(ch[i]=='(')
                {
                    Push(ch[i],op);
                }
                else if(ch[i]==')')
                {
                    while(!isEmpty(op) && op[Top(op)] !='(') {
                        Push(Pop(op), pos);
                        if(Top(op) != -1 && op[Top(op)] == '('){
                            Pop(op);
                            break;
                        }
                    }
                }
                else if(isOperand(ch[i]))
                {
                    if(((i+1)!=ch.length)&&(isOperand(ch[i+1]))&&(isOperand(ch[i+2]))){
                        char a = ch[i];
                        char b = ch[i+1];
                        String s = a+""+b;
                        if(isOperand(ch[i+2])) {
                            char c = ch[i + 2];
                            s = a+""+b+""+c;
                        }
                        Push(s, pos);
                        i=i+2;
                    }
                    else if(((i+1)!=ch.length)&&(isOperand(ch[i+1]))){
                        char a = ch[i];
                        char b = ch[i+1];
                        String s = a+""+b;
                        Push(s, pos);
                        i++;
                    }
                    else if(((i+1)!=ch.length)&&(ch[i+1] == '(')){
                        Push(ch[i], pos);
                        while (!isEmpty(op) && getPrecedence('*') <= getPrecedence(op[Top(op)])) {
                            Push(Pop(op), pos);
                        }
                        Push('*', op);
                    }
                    else Push(ch[i], pos);
                }
            }
        }
        while(!isEmpty(op))
        {
            Push(Pop(op), pos);
        }
        return String.join("", pos).trim();
    }
    public static double result(String[] postfix, double[] res) throws Exception
    {
        if(isEmpty(res)) {
            for (String s : postfix) {
                if (isOperand(s)) {
                    double temp = Double.parseDouble(s);
                    Push(temp, res);
                }
                else if (isOperator(s)) {
                    double opd2 = Pop(res);
                    double opd1 = Pop(res);
                    double result = operations(opd1, opd2, s);
                    Push(result, res);
                }
                if(s.equals("")&&Top(res)==0)
                    break;
            }
        }
        return res[0];
    }
    public static double operations(double opd1, double opd2, String op)
    {
        switch(op)
        {
            case "+":
                return opd1 + opd2;
            case "-":
                return opd1 - opd2;
            case "*":
                return opd1 * opd2;
            case "/":
                if(opd2 == 0) throw new ArithmeticException("Anything divided by zero is undefined.");
                return opd1 / opd2;
            case "^":
                double result = 1.0;
                for(int i = 0; i < opd2; i++) {
                    result *= opd1;
                }
                return result;
            default:
                throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }
    public static void traverse(String[] arr) //printing out the sequence
    {
        System.out.println("Sequence:");
        for(int i = 0; i < arr.length; i++)
        {
            if(arr[i+1].equals(""))
            {
                System.out.println(arr[+i]+" ");
                break;
            }
            else
            {
                System.out.println(arr[i]+" ");
            }
        }
    }
    public static void Push(String element, String []stac) throws Exception
    {
        if(isFull(stac)) throw new Exception("Stack is Full. You can't push more elements into the stack.");
        else {
            int top = Top(stac);
            top++;
            stac[top] = element;
        }

    }
    public static void Push(char element, String []stac) throws Exception
    {
        if(isFull(stac)) throw new Exception("Stack is Full. You can't push more elements into the stack.");
        else {
            int top = Top(stac);
            top++;
            stac[top] = String.valueOf(element);
        }
    }
    public static void Push(char element, char []stac) throws Exception
    {
        if(isFull(stac)) throw new Exception("Stack is Full. You can't push more elements into the stack.");
        else {
            int top = Top(stac);
            top++;
            stac[top] = element;
        }
    }
    public static void Push(double element, double []stac) throws Exception
    {
        if(isFull(stac)) throw new Exception("Stack is Full. You can't push more elements into the stack.");
        else {
            int top = Top(stac);
            top++;
            stac[top] = element;
        }
    }
    public static String Pop(String []stac) throws Exception
    {
        String temp;
        if(isEmpty(stac)) throw new Exception("Stack is Empty. You can't pop more elements from the stack.");
        else {
            int top = Top(stac);
            temp = store(stac[top]);
            stac[top] = "";
        }
        return temp;
    }
    public static char Pop(char []stac) throws Exception
    {
        char temp;
        if(isEmpty(stac)) throw new Exception("Stack is Empty. You can't pop more elements from the stack.");
        else {
            int top = Top(stac);
            temp = store(stac[top]);
            stac[top] = 0;
        }
        return temp;
    }
    public static double Pop(double []stac) throws Exception
    {
        double temp;
        if(isEmpty(stac)) throw new Exception("Stack is Empty. You can't pop more elements from the stack.");
        else {
            int top = Top(stac);
            temp = store(stac[top]);
            stac[top] = 0;
        }
        return temp;
    }
    public static String Peek(int index, String[] stac)
    {
        return stac[index];
    }
    public static String store(String value)
    {
        String temp = value;
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
    public static int Top(String []stac)
    {
        int top = 0;
        for (int i = 0; i < stac.length ; i++) {
            if (stac[i].equals("")) {
                top = i-1;
                break;
            }
        }
        return top;
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
    public static boolean isEmpty(double []stac)
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
    public static boolean isEmpty(String []stac)
    {
        for(int i = 0; i < stac.length; i++)
        {
            if(!(stac[i].equals("")))
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
    public static boolean isFull(double []stac)
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
    public static boolean isFull(String []stac)
    {
        for(int i = 0; i < stac.length; i++)
        {
            if(stac[i].equals(""))
            {
                return false;
            }
        }
        return true;
    }
    public static boolean isOperand(char value)
    {
        return value == '0' || value == '1' || value == '2' || value == '3' || value == '4' || value == '5' || value == '6' || value == '7' || value == '8' || value == '9';
    }
    public static boolean isOperand(String value)
    {
        try
        {
            Double.parseDouble(value);
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }
    public static boolean isOperator(char value)
    {
        return value == '+' || value == '-' || value == '*' || value == '/' || value == '^';
    }
    public static boolean isOperator(String value)
    {
        return value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/") || value.equals("^");
    }
    public static int operandCounter(String [] stac)
    {
        int count = 0;
        for(int i = 0; i<Top(stac);i++)
        {
            if(isOperand(stac[i]))
            {
                count++;
            }
        }
        return count;
    }
    public static int operatorCounter(String[] stac)
    {
        int count = 0;
        for(int i = 0; i<Top(stac);i++)
        {
            if(isOperator(stac[i]))
            {
                count++;
            }
        }
        return count;
    }
    public static int getPrecedence(char operator)
    {
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
    public static int indexRange(String[] arr)
    {
        int temp = 0;
        for(int i=0; i<arr.length;i++)
        {
            if(arr[i+1].equals(""))
            {
                temp = i;
                break;
            }
        }
        return temp;
    }
}