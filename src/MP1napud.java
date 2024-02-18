/*
Quilantang, Luis Marco R.
ITE114 - IT2D.1
MACHINE PROBLEM 1 - INFIX TO POSTFIX CONVERSION AND STACK OPERATIONS

This program deals with Matrices using multidimensional arrays.
This program applies the Matrix theory in its basic operations. The operations also uses concepts of multidimensional array.
 */
import java.util.*;
public class MP1napud {
    public static Scanner scan = new Scanner(System.in);
    public static void main (String[] args){
        Matrix A = createMatrix("A");
        Matrix B = createMatrix("B");
        Matrix C = createMatrix("C");
        System.out.println("----------------------------------------------------------------------");


        int r = 0;
        do  //this do-while loop handles the choice "yes or no" at the near end of the code. The loop ends when the user chooses no.
        {
            if(r==1) {
                A = createMatrix("A");
                B = createMatrix("B");
                C = createMatrix("C");
                operations(A, B, C);
            }else if(r==2){
                operations(A,B,C);
            }else{
                operations(A,B,C);
            }
            System.out.println("Do you want to input new Matrices? Press 1 for yes, Press 2 for no");
            r = choose();
        }while(true);
    }
    public static Matrix createMatrix(String name) {
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Setting up for Matrix " + name + ".");
        System.out.println("How many rows do you want. Number of rows must be greater than or equal to 1 but not greater than 10.");
        int n = indexInput();
        System.out.println("How many columns do you want. Number of columns must be greater than or equal to 1 but not greater than 10.");
        int m = indexInput();
        int[][] matrix = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                try {
                    System.out.print("Matrix " + name + " [" + i + "][" + j + "] = ");
                    matrix[i][j] = Integer.parseInt(scan.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Input integers only. Try again.");
                    j--;
                }
            }
        }
        return new Matrix(matrix);
    }
    public static void operations(Matrix m1, Matrix m2, Matrix m3){
        System.out.println("Choose an operation to perform.");
        System.out.println("----------------------------------------------------------------------");
        System.out.println("0 - Matrix Addition");
        System.out.println("1 - Matrix Multiplication");
        System.out.println("2 - Matrix Scalar Multiplication");
        System.out.println("3 - Matrix Diagonalizing");
        System.out.println("4 - END");
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Enter Choice: ");
        int choice = -1;    //setting up for choices.
        do  //error trapping for choices, traps out input that are not of the choices indicated and non integer inputs.
        {
            try
            {
                choice = Integer.parseInt(scan.nextLine());
                if(choice<0 || choice>4) //this code runs if input is now an integer
                {
                    System.out.println("Input should be within range. Try again.");
                }
            }
            catch(NumberFormatException e)  //it catches inputs that are not integers.
            {
                System.out.println("Input numbers only. Try again.");
            }
        }while(choice<0 || choice>4);
        switch(choice)  //choices laid out in different cases to run the intended method. does not need a default since "choice" is by default only within the range of the choices because of the error trapping.
        {
            case 0:
                Matrix sum = new Matrix();
                int compa = sumCompatibility(m1,m2,m3);
                if(sumCompatibility(m1,m2,m3)==0){
                    System.out.println("Matrices are not compatible, cannot do operation.");
                    break;
                }
                else if(sumCompatibility(m1,m2,m3)==1){
                    sumMethod(sum,m1,m2,compa);
                }
                else if(sumCompatibility(m1,m2,m3)==2){
                    sumMethod(sum,m2,m3,compa);
                }
                else if(sumCompatibility(m1,m2,m3)==3){
                    sumMethod(sum,m1,m3,compa);
                }
                int x = 0;
                do  //this do-while loop handles the choice "yes or no" at the near end of the code. The loop ends when the user chooses no.
                {
                    if(x==1){
                        Matrix another = createMatrix("new");
                        if(sum.getMatrix().length!=another.getMatrix().length || sum.getMatrix()[0].length!=another.getMatrix()[0].length) {
                            System.out.println("Matrices are not compatible, cannot do operation.");
                        }
                        else{
                            System.out.println("Let new Matrix = previous Matrix + newly generated Matrix");
                            Matrix.addTraverse(sum,another);
                            sum = Matrix.add(sum, another);
                            System.out.println("new Matrix:");
                            sum.printMatrix();
                        }
                    }else if(x==2){
                        break;
                    }
                    System.out.println("Do you want to generate another matrix? 1-Yes, 2-No");
                    x = choose();
                }while(true);
                break;
            case 1:
                Matrix prod = new Matrix();
                if(multiplyCompatibility(m1,m2,m3)==0){
                    System.out.println("Matrices are not compatible, cannot do operation.");
                    break;
                }
                else if(multiplyCompatibility(m1,m2,m3)==1) {
                    System.out.println("Compatibility found! Choose which Matrix to become the multiplicand. 1-Matrix A, 2-Matrix B");
                    System.out.println("Note: Matrices can still be incompatible depending on which it becomes the multiplicand.");
                    System.out.print("Input choice here: ");
                    int choose = choose();
                    if (choose == 1) {
                        if (m1.getMatrix()[0].length != m2.getMatrix().length) {
                            System.out.println("Matrix size criteria for multiply are not met, cannot do operation.");
                        } else {
                            System.out.println("Let new Matrix = Matrix A * Matrix B");
                            Matrix.multiplyTraverse(m1, m2);
                            prod = Matrix.multiply(m1, m2);
                            System.out.println("New Matrix:");
                            prod.printMatrix();
                        }
                    } else if (choose == 2) {
                        if (m2.getMatrix()[0].length != m1.getMatrix().length) {
                            System.out.println("Matrix size criteria for multiply are not met, cannot do operation.");
                        } else {
                            System.out.println("Let new Matrix = Matrix B * Matrix A");
                            Matrix.multiplyTraverse(m2, m1);
                            prod = Matrix.multiply(m2, m1);
                            System.out.println("New Matrix:");
                            prod.printMatrix();

                        }
                    }
                }
                else if(multiplyCompatibility(m1,m2,m3)==2) {
                    System.out.println("Compatibility found! Choose which Matrix to become the multiplicand. 1-Matrix B, 2-Matrix C");
                    System.out.println("Note: Matrices can still be incompatible depending on which it becomes the multiplicand.");
                    System.out.print("Input choice here: ");
                    int choose = choose();
                    if (choose == 1) {
                        if (m2.getMatrix()[0].length != m3.getMatrix().length) {
                            System.out.println("Matrix size criteria for multiply are not met, cannot do operation.");
                        } else {
                            System.out.println("Let new Matrix = Matrix B * Matrix C");
                            Matrix.multiplyTraverse(m2, m3);
                            prod = Matrix.multiply(m2, m3);
                            System.out.println("Matrix C:");
                            prod.printMatrix();
                        }
                    } else if (choose == 2) {
                        if (m3.getMatrix()[0].length != m2.getMatrix().length) {
                            System.out.println("Matrix size criteria for multiply are not met, cannot do operation.");
                        } else {
                            System.out.println("Let new Matrix = Matrix B * Matrix C");
                            Matrix.multiplyTraverse(m3, m2);
                            prod = Matrix.multiply(m3, m2);
                            System.out.println("New Matrix:");
                            prod.printMatrix();
                        }
                    }
                }
                else if(multiplyCompatibility(m1,m2,m3)==3) {
                    System.out.println("Compatibility found! Choose which Matrix to become the multiplicand. 1-Matrix A, 2-Matrix C");
                    System.out.println("Note: Matrices can still be incompatible depending on which it becomes the multiplicand.");
                    System.out.print("Input choice here: ");
                    int choose = choose();
                    if (choose == 1) {
                        if (m1.getMatrix()[0].length != m3.getMatrix().length) {
                            System.out.println("Matrix size criteria for multiply are not met, cannot do operation.");
                        } else {
                            System.out.println("Let new Matrix = Matrix A * Matrix C");
                            Matrix.multiplyTraverse(m1, m2);
                            prod = Matrix.multiply(m1, m2);
                            System.out.println("New Matrix:");
                            prod.printMatrix();
                        }
                    } else if (choose == 2) {
                        if (m3.getMatrix()[0].length != m1.getMatrix().length) {
                            System.out.println("Matrix size criteria for multiply are not met, cannot do operation.");
                        } else {
                            System.out.println("Let new Matrix = Matrix B * Matrix C");
                            Matrix.multiplyTraverse(m2, m1);
                            prod = Matrix.multiply(m2, m1);
                            System.out.println("New Matrix:");
                            prod.printMatrix();

                        }
                    }
                }
                int y = 0;
                do  //this do-while loop handles the choice "yes or no" at the near end of the code. The loop ends when the user chooses no.
                {
                    if(y==1){
                        Matrix another = createMatrix("new");
                        if(prod.getMatrix().length!=another.getMatrix()[0].length && prod.getMatrix()[0].length!=another.getMatrix().length) {
                            System.out.println("Matrices are not compatible, cannot do operation.");
                        }
                        else{
                            System.out.println("Compatibility found! Choose which Matrix to become the multiplicand. 1-previous Matrix, 2-newly generated Matrix");
                            System.out.println("Note: Matrices can still be incompatible depending on which it becomes the multiplicand.");
                            System.out.print("Input choice here: ");
                            int choose = choose();
                            if (choose == 1) {
                                if (prod.getMatrix()[0].length != another.getMatrix().length) {
                                    System.out.println("Matrix size criteria for multiply are not met, cannot do operation.");
                                } else {
                                    System.out.println("Let new Matrix = previous Matrix + newly generated Matrix");
                                    Matrix.multiplyTraverse(prod, another);
                                    prod = Matrix.multiply(prod, another);
                                    System.out.println("New Matrix:");
                                    prod.printMatrix();
                                }
                            } else if (choose == 2) {
                                if (another.getMatrix()[0].length != prod.getMatrix().length) {
                                    System.out.println("Matrix size criteria for multiply are not met, cannot do operation.");
                                } else {
                                    System.out.println("Let new Matrix = newly generated Matrix + previous Matrix");
                                    Matrix.multiplyTraverse(another, prod);
                                    prod = Matrix.multiply(another, prod);
                                    System.out.println("New Matrix:");
                                    prod.printMatrix();

                                }
                            }
                        }
                    }else if(y==2){
                        break;
                    }
                    System.out.println("Do you want to generate another matrix? 1-Yes, 2-No");
                    y = choose();
                }while(true);
                break;
            case 2:
                Matrix sprod = new Matrix();
                System.out.println("Choose which Matrix you want to use in this operation: 1-Matrix A, 2-Matrix B, 3-Matrix C");
                int a = threeWay();
                System.out.println("Input a constant that you want to multiply with the matrix.");
                int xscalar = integer();
                switch(a) {
                    case 1:
                        scalarMultplyMethod(sprod,m1,a,xscalar);
                        break;
                    case 2:
                        scalarMultplyMethod(sprod,m2,a,xscalar);
                        break;
                    case 3:
                        scalarMultplyMethod(sprod,m3,a,xscalar);
                        break;
                }
                a=0;
                int z = 0;
                do  //this do-while loop handles the choice "yes or no" at the near end of the code. The loop ends when the user chooses no.
                {
                    System.out.println("Input a constant that you want to multiply with the matrix.");
                    int yscalar= integer();
                    if(z==1){
                        System.out.println("Let new Matrix = previous Matrix * "+yscalar);
                        Matrix.scalarMultiplyTraverse(sprod, yscalar);
                        sprod = Matrix.scalarMultiply(sprod, yscalar);
                        System.out.println("New Matrix:");
                        sprod.printMatrix();
                    }else if(z==2){
                        break;
                    }
                    System.out.println("Do you want to generate another matrix? 1-Yes, 2-No");
                    z = choose();
                }while(true);
            case 3:
                Matrix dia = new Matrix();
                System.out.println("Choose which Matrix you want to use in this operation: 1-Matrix A, 2-Matrix B, 3-Matrix C");
                int b = threeWay();
                System.out.println("Input a constant that will be used to diagonalize the matrix.");
                int diag = integer();
                switch(b) {
                    case 1:
                        if (m1.getMatrix().length != m1.getMatrix()[0].length) {
                            System.out.println("Matrix size are not equal, cannot do operation.");
                        } else {
                            System.out.println("Let new Matrix = diagonalized Matrix A");
                            dia = Matrix.diagonalize(m1, diag);
                            System.out.println("New Matrix:");
                            dia.printMatrix();
                        }
                        break;
                    case 2:
                        if (m2.getMatrix().length != m2.getMatrix()[0].length) {
                            System.out.println("Matrix size are not equal, cannot do operation.");
                        } else {
                            System.out.println("Let new Matrix = diagonalized Matrix B");
                            dia = Matrix.diagonalize(m3, diag);
                            System.out.println("New Matrix:");
                            dia.printMatrix();
                        }
                        break;
                    case 3:
                        if (m3.getMatrix().length != m3.getMatrix()[0].length) {
                            System.out.println("Matrix size are not equal, cannot do operation.");
                        } else {
                            System.out.println("Let new Matrix = diagonalized Matrix C");
                            dia = Matrix.diagonalize(m3, diag);
                            System.out.println("New Matrix:");
                            dia.printMatrix();
                        }
                        break;
                }
                int xx = 0;
                do  //this do-while loop handles the choice "yes or no" at the near end of the code. The loop ends when the user chooses no.
                {
                    System.out.println("Input a new constant that will be used to diagonalize the matrix.");
                    int diago= integer();
                    if(xx==1){
                        System.out.println("Let new Matrix = previous Matrix * "+diago);
                        Matrix.scalarMultiplyTraverse(dia, diago);
                        sprod = Matrix.scalarMultiply(dia, diago);
                        System.out.println("New Matrix:");
                        sprod.printMatrix();
                    }else if(xx==2){
                        break;
                    }
                    System.out.println("Do you want to generate another matrix? 1-Yes, 2-No");
                    xx = choose();
                }while(true);
                break;
            case 4:
                System.out.println("Program Terminating...");
                System.out.println("Program end");
                System.exit(1);
        }
        System.out.println("----------------------------------------------------------------------");
    }
    public static void sumMethod(Matrix sum, Matrix x, Matrix y, int z){
        String n1 = "";
        String n2 = "";
        if(z==1){
            n1 = "A";
            n2 = "B";
        }else if(z==2){
            n1 = "B";
            n2 = "C";
        }else if(z==3){
            n1 = "A";
            n2 = "C";
        }
        System.out.println("Matrices "+n1+" and "+n2+" are compatible.");
        System.out.println("Let new Matrix = Matrix "+n1+" + Matrix "+n2);
        Matrix.addTraverse(x,y);
        sum = Matrix.add(x,y);
        System.out.println("new Matrix:");
        sum.printMatrix();
    }
    public static void scalarMultplyMethod(Matrix sprod, Matrix m, int z, int scalar){
        String name = "";
        if(z==1){
            name = "A";
        }else if(z==2){
            name = "B";
        }else if(z==3){
            name = "C";
        }
        System.out.println("Let new Matrix = Matrix "+name+" * " +scalar);
        Matrix.scalarMultiplyTraverse(m, scalar);
        sprod = Matrix.scalarMultiply(m, scalar);
        System.out.println("new Matrix:");
        sprod.printMatrix();
    }
    public static int sumCompatibility(Matrix m1, Matrix m2, Matrix m3){
        if(m1.getMatrix().length==m2.getMatrix().length && m1.getMatrix()[0].length==m2.getMatrix()[0].length){
            return 1;
        }
        else if(m3.getMatrix().length==m2.getMatrix().length && m3.getMatrix()[0].length==m2.getMatrix()[0].length){
            return 2;
        }
        else if(m3.getMatrix().length==m1.getMatrix().length && m3.getMatrix()[0].length==m1.getMatrix()[0].length){
            return 3;
        }
        return 0;
    }
    public static int multiplyCompatibility(Matrix m1, Matrix m2, Matrix m3){
        if(m1.getMatrix().length==m2.getMatrix()[0].length || m1.getMatrix()[0].length==m2.getMatrix().length){
            return 1;
        }
        else if(m2.getMatrix().length==m3.getMatrix()[0].length || m2.getMatrix()[0].length==m3.getMatrix().length){
            return 2;
        }
        else if(m1.getMatrix().length==m3.getMatrix()[0].length || m1.getMatrix()[0].length==m3.getMatrix().length){
            return 3;
        }
        return 0;
    }
    public static int indexInput(){ //this method handles the index user input, it traps inputs that doesn't fit the criteria (size = (1<=n<=10)) or non integers
        try
        {
            int temp = Integer.parseInt(scan.nextLine());
            if(temp < 1 || temp > 10) throw new Exception("Input not matching the size criteria. Try again.");
            return temp;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage()+" is not an integer. Try again.");
            return indexInput();
        }
    }
    public static int integer(){
        while(true){  //error trapping for choices, traps out input that are not of the choices indicated and non integer inputs.
            try {
                return Integer.parseInt(scan.nextLine());
            }
            catch(InputMismatchException e){  //it catches inputs that are not integers.
                System.out.println("Input integers only. Try again.");
                Integer.parseInt(scan.nextLine());
            }
        }
    }
    public static int choose(){
        int x = 0;
        do  //error trapping for choices, traps out input that are not of the choices indicated and non integer inputs.
        {
            try
            {
                x = Integer.parseInt(scan.nextLine());
                if(x<1 || x>2) //this code runs if input is now an integer
                {
                    System.out.println("Input should be within range. Try again.");
                }
            }
            catch(NumberFormatException e)  //it catches inputs that are not integers.
            {
                System.out.println("Input numbers only. Try again.");
            }
        }while(x<0 || x>2);
        return x;
    }
    public static int threeWay(){
        int x = 0;
        do  //error trapping for choices, traps out input that are not of the choices indicated and non integer inputs.
        {
            try
            {
                x = Integer.parseInt(scan.nextLine());
                if(x<1 || x>3) //this code runs if input is now an integer
                {
                    System.out.println("Input should be within range. Try again.");
                }
            }
            catch(NumberFormatException e)  //it catches inputs that are not integers.
            {
                System.out.println("Input numbers only. Try again.");
            }
        }while(x<0 || x>3);
        return x;
    }
}
class Matrix{
    int [][]matrix;     //2d array for the matrix
    //constructor of a matrix
    Matrix(){}
    Matrix(int [][]matrix){
        this.matrix = matrix;
    }
    public int[][] getMatrix(){
        return this.matrix;
    }
    //prints the matrix
    public void printMatrix(){
        for (int[] ints : this.matrix) {
            System.out.print("|");
            for (int anInt : ints) {
                System.out.printf("%3d ", anInt);
            }
            System.out.print("|");
            System.out.println();
        }
    }
    //addition of two matrices
    public static void addTraverse(Matrix A, Matrix B){
        int[][] m1 = A.getMatrix();
        int[][] m2 = B.getMatrix();
        for(int i=0; i<m1.length; i++){
            System.out.print("|");
            for(int j=0; j<m1[i].length; j++) {
                System.out.printf("%9s ", (m1[i][j] + " + " + m2[i][j]));
                if (j + 1 == m1[0].length) {
                    System.out.printf("%3s ", "");
                }
            }
            System.out.print("|");
            System.out.println();
        }
    }
    public static Matrix add(Matrix A, Matrix B){
        int[][] m1 = A.getMatrix();
        int[][] m2 = B.getMatrix();
        int[][] sum = new int [m1.length][m1[0].length];
        for(int i=0; i<m1.length; i++){
            for(int j=0; j<m1[i].length; j++){
                sum[i][j] = m1[i][j] + m2[i][j];
            }
        }
        return new Matrix(sum);
    }
    //multiplication of two matrices
    public static void multiplyTraverse(Matrix A, Matrix B){
        int[][] m1 = A.getMatrix();
        int[][] m2 = B.getMatrix();
        for(int i=0; i<m1.length; i++){
            System.out.print("|");
            for(int j=0; j<m2[0].length; j++){
                for(int k = 0; k<m1[0].length; k++){
                    if(k+1 == m1[0].length) {
                        System.out.printf("(" + m1[i][k] + ")(" + m2[k][j] + ") %4s","");
                    }else if(k==0){
                        System.out.printf("%10s",("(" + m1[i][k] + ")(" + m2[k][j] + ")+"));
                    }else{
                        System.out.printf(("(" + m1[i][k] + ")(" + m2[k][j] + ")+"));
                    }
                }
            }
            System.out.print("|");
            System.out.println();
        }
    }
    public static Matrix multiply(Matrix A, Matrix B){
        int[][] m1 = A.getMatrix();
        int[][] m2 = B.getMatrix();
        int[][] prod = new int [m1.length][m2[0].length];
        for(int i = 0; i<m1.length; i++){
            for(int j = 0; j<m2[0].length; j++){
                prod[i][j] = 0;
                for(int k = 0; k<m1[0].length; k++){
                    prod[i][j] = prod[i][j] + (m1[i][k] * m2[k][j]);
                }
            }
        }
        return new Matrix(prod);
    }
    //multiplication with a constant
    public static void scalarMultiplyTraverse(Matrix m1, int c){
        int[][] arr = m1.getMatrix();
        for(int i=0; i<arr.length; i++){
            System.out.print("|");
            for(int j=0; j<arr[i].length; j++){
                System.out.printf("%6s",(c+"("+arr[i][j]+")"));
            }
            System.out.print("|");
            System.out.println();
        }
    }
    public static Matrix scalarMultiply(Matrix m1, int c){
        int[][] arr = m1.getMatrix();
        int[][] prod = new int [arr.length][arr[0].length];
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[i].length; j++){
                prod[i][j] = arr[i][j] * c;
            }
        }
        return new Matrix(prod);
    }
    //diagonalization of a matrix
    public static Matrix diagonalize(Matrix m1, int x) {
        int[][] arr = m1.getMatrix();
        int[][] dia = new int [arr.length][arr[0].length];
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[i].length; j++){
                if(i==j){
                    dia[i][j] = x;
                }else{
                    dia[i][j] = arr[i][j];
                }
            }
        }
        return new Matrix(dia);
    }
}