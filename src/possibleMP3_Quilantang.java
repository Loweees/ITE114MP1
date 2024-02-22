import java.util.*;
public class possibleMP3_Quilantang {
    public static Scanner s = new Scanner(System.in);
    public static int rear = -1;
    public static int front = -1;
    public static int[] stac = new int[10];
    public static void main(String []args){
        char[] infix = {'(','2','+','3',')','*','4','-','(','1','5','/','3',')','^','2'};

    }

    public void Push()
    {
        int element = elementInput();
        if(!(isEmpty()||isFull()))
        {
            rear += 1;
            stac[rear] = element;
        }
        else if(isEmpty())
        {
            rear++;
            front++;
            stac[rear] = element;
        }
        else
        {
        System.out.println("Stack is full! Pop an element first before pushing a new element");
        }
    }
    public void Pop()
    {
        if(!isEmpty()){
            int temp = store(stac[rear]);
            stac[rear] = 0;
            if(rear == 0) front--;
            rear--;
            System.out.println("Pop "+temp);
        }
        else System.out.println("The stack is empty! Push an element before popping");
    }
    public int store(int value)
    {
        int temp = value;
        return temp;
    }
    public boolean isEmpty()
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
    public boolean isFull()
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
    public static int elementInput()
    {
        try
        {
            int temp = Integer.parseInt(s.nextLine());
            if (temp < 1) throw new Exception("Element must be a positive integer. Try again");
            return temp;
        }
        catch(Exception e)
        {
            System.out.println("Input numbers only. Try again.");
            return elementInput();
        }
    }
    public static boolean elementDistinct(int[] arr, int element)
    {
        for (int j : arr) {
            if (element == j) return false;
        }
        return true;
    }
}

