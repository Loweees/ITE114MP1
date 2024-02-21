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
    public static Matrix[] matrices = new Matrix[10];
    public static int count;
    public static String[] name = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q"};
    public static void main (String[] args){
        System.out.println("How many Matrices do you want to create? Must be at least 2 but no more than 10.");
        int mat = range();
        start(mat);
        System.out.println("----------------------------------------------------------------------");
        int r = 0;
        do  //this do-while loop handles the choice "yes or no" at the near end of the code. The loop ends when the user chooses no.
        {
            if(r==1) {
                clear();
                System.out.println("How many Matrices do you want to create? Must be at least 2 but no more than 10.");
                mat = range();
                start(mat);
                operations(matrices, mat);
            }else{
                operations(matrices, mat);
            }
            System.out.println("Do you want to input new Matrices? Press 1 for yes, Press 2 for no");
            r = choose();
        }while(true);
    }
    public static void start(int x){
        for(int i = 0; i<x; i++){
            matrices[i] = createMatrix(name[i]);
            count++;
        }
    }
    public static void clear(){
        count = 0;
        System.out.println("Clearing current inputs of matrices...");
        Arrays.fill(matrices, null);
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
    public static void operations(Matrix[] m, int a){
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
                Matrix sum;
                if(sumCompatibility(matrices)){
                    sum = sumMethod(matrices);
                    System.out.println("New Matrix:");
                    sum.printMatrix();
                }
                else{
                    System.out.println("Matrix sizes are not equal, cannot do operation.");
                    break;
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
                Matrix prod;
                if(multiplyCompatibility(matrices)){
                    prod = multiplyMethod(matrices);
                    System.out.println("New Matrix:");
                    prod.printMatrix();
                }
                else{
                    System.out.println("Matrices are not compatible, cannot do operation.");
                    break;
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
                                    System.out.println("Do you want to use the other matrix as the multiplicand? 1-Yes, 2-No");
                                    int yn = choose();
                                    if(yn==1){
                                        System.out.println("Let new Matrix = newly generated Matrix + previous Matrix");
                                        Matrix.multiplyTraverse(another, prod);
                                        prod = Matrix.multiply(another, prod);
                                        System.out.println("New Matrix:");
                                        prod.printMatrix();
                                    }
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
                                    int yn = choose();
                                    if(yn==1){
                                        System.out.println("Let new Matrix = previous Matrix + newly generated Matrix");
                                        Matrix.multiplyTraverse(prod, another);
                                        prod = Matrix.multiply(prod, another);
                                        System.out.println("New Matrix:");
                                        prod.printMatrix();
                                    }
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
                Matrix sprod = sMultiply(matrices);
                System.out.println("new Matrix:");
                sprod.printMatrix();
                int z = 0;
                do  //this do-while loop handles the choice "yes or no" at the near end of the code. The loop ends when the user chooses no.
                {
                    if(z==1){
                        System.out.println("Input a constant that you want to multiply with the matrix.");
                        int yscalar= integer();
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
                break;
            case 3:
                Matrix dia;
                if(diagCompatibility(matrices)){
                    dia = diagonal(matrices);
                    System.out.println("New Matrix:");
                    dia.printMatrix();
                }
                else{
                    System.out.println("All Matrices are not square matrix. Cannot do operation.");
                    break;
                }
                int xx = 0;
                do  //this do-while loop handles the choice "yes or no" at the near end of the code. The loop ends when the user chooses no.
                {
                    if(xx==1){
                        System.out.println("Input a new constant that will be used to diagonalize the matrix.");
                        int diago= integer();
                        System.out.println("Let new Matrix = diagonalized previous Matrix");
                        dia = Matrix.diagonalize(dia, diago);
                        System.out.println("New Matrix:");
                        dia.printMatrix();
                    }else if(xx==2){
                        break;
                    }
                    System.out.println("Do you want to try another constant? 1-Yes, 2-No");
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
    //method to print out the monologue for scalar multiplication
    public static Matrix sMultiply(Matrix[] m){
        Matrix sprod;
        System.out.println("Choose which Matrix you want to use in this operation.");
        for(int i = 0; i<count; i++){
            System.out.println((i+1)+" - Matrix "+name[i]);
        }
        int d = decision(count);
        System.out.println("Input a constant that you want to multiply with the matrix.");
        int scalar = integer();
        System.out.println("Let new Matrix = Matrix "+name[d-1]+" * " +scalar);
        Matrix.scalarMultiplyTraverse(m[d-1], scalar);
        sprod = Matrix.scalarMultiply(m[d-1], scalar);
        return sprod;
    }
    //method to print out the monologue for diagonalization
    public static boolean diagCompatibility(Matrix[] m){
        boolean t = false;
        for(int i=0; i<count; i++){
            if(m[i].getMatrix().length == m[i].getMatrix()[0].length){
                t=true;
            }
        }
        return t;
    }
    public static Matrix diagonal(Matrix[] m){
        Matrix dia;
        int de = 1;
        int[] arr = new int[10];
        System.out.println("Here are matrices that are compatible for diagonalization:");
        for(int i = 0; i<count; i++){
            if (m[i].getMatrix().length == m[i].getMatrix()[0].length) {
                System.out.println(de+" - Matrix "+name[i]);
                arr[de-1]=i;
                de++;
            }
        }
        System.out.println("Which Matrix do you want to use in this operation.");
        int b = decision(de);
        System.out.println("Input a constant that will be used to diagonalize the matrix.");
        int diag = integer();
        System.out.println("Let new Matrix = diagonalized Matrix "+name[arr[b-1]]);
        dia = Matrix.diagonalize(m[arr[b-1]], diag);
        return dia;
    }
    //method that looks for matrix size compatibility for addition
    public static boolean sumCompatibility(Matrix[] m){
        boolean tf = false;
        for(int i=0; i<count; i++){
            for(int j=i+1; j<count; j++) {
                if (m[i].getMatrix().length == m[j].getMatrix().length && m[i].getMatrix()[0].length==m[j].getMatrix()[0].length){
                    tf = true;
                    break;
                }
            }
            if(tf) break;
        }
        return tf;
    }
    //method to print out the monologue for addition
    public static Matrix sumMethod(Matrix[] m){
        Matrix sum;
        int dis = 1;
        int[][] arr = new int[45][2];
        System.out.println("Here are pairs of matrices that are compatible for addition:");
        for(int i=0; i<count; i++){
            for(int j=i+1; j<count; j++) {
                if (m[i].getMatrix().length == m[j].getMatrix().length && m[i].getMatrix()[0].length==m[j].getMatrix()[0].length){
                    System.out.println((dis)+" - Matrices "+name[i]+" and "+name[j]);
                    arr[dis-1][0]=i;
                    arr[dis-1][1]=j;
                    dis++;
                }
            }
        }
        System.out.print("Which pair do you want to perform the addition? ");
        int des = decision(dis);
        System.out.println("Let new Matrix = Matrix "+name[arr[des-1][0]]+" + Matrix "+name[arr[des-1][1]]);
        Matrix.addTraverse(m[arr[des-1][0]],m[arr[des-1][1]]);
        sum = Matrix.add(m[arr[des-1][0]], m[arr[des-1][1]]);
        return sum;
    }
    //method that looks for matrix size compatibility for multiplication
    public static boolean multiplyCompatibility(Matrix[] m){
        boolean tf = false;
        for(int i=0; i<count; i++){
            for(int j=0; j<count; j++) {
                if(j!=i && m[i].getMatrix().length == m[j].getMatrix()[0].length){
                    tf = true;
                    break;
                }
            }
            if(tf) break;
        }
        return tf;
    }
    //method to print out the monologue for multiplication
    public static Matrix multiplyMethod(Matrix[] m){
        Matrix prod = new Matrix();
        int dis = 1;
        int[][] arr = new int[90][2];
        System.out.println("Here are pairs of matrices that are compatible for multiplication:");
        for(int i=0; i<count; i++){
            for(int j=0; j<count; j++) {
                if(j!=i && m[i].getMatrix()[0].length == m[j].getMatrix().length){
                    System.out.println((dis)+" - Matrices "+name[i]+" and "+name[j]);
                    arr[dis-1][0]=i;
                    arr[dis-1][1]=j;
                    dis++;
                }
            }
        }

        //diri decide if magchoose pa unsay multiplicand or dili na. ugma napud kay kapoy. Lines 353-392

        System.out.println("Note: First called matrix from the pair is the main multiplicand.");
        System.out.print("Which pair do you want to perform the multiplication? ");
        int des = decision(dis);
        System.out.println("Compatibility found! Choose which Matrix to become the multiplicand. 1-Matrix "+name[arr[des-1][0]]+", 2-Matrix "+name[arr[des-1][1]]);
        System.out.println("Note: Matrices can still be incompatible depending on which it becomes the multiplicand.");
        System.out.print("Input choice here: ");
        int choose = choose();
        if (choose == 1) {
            if (m[arr[des-1][0]].getMatrix()[0].length != m[arr[des-1][1]].getMatrix().length) {
                System.out.println("Matrix size criteria for multiply are not met, cannot do operation.");
                System.out.println("Do you want to use the other matrix as the multiplicand? 1-Yes, 2-No");
                int yn = choose();
                if(yn==1){
                    prod = mult2(m, arr, des-1);
                }
            } else {
                prod = mult1(m, arr, des-1);
            }
        } else if (choose == 2) {
            if (m[arr[des-1][1]].getMatrix()[0].length != m[arr[des-1][0]].getMatrix().length) {
                System.out.println("Matrix size criteria for multiply are not met, cannot do operation.");
                System.out.println("Do you want to use the other matrix as the multiplicand? 1-Yes, 2-No");
                int yn = choose();
                if(yn==1){
                    prod = mult1(m, arr, des-1);
                }
            } else {
                prod = mult2(m, arr, des-1);
            }
        }
        return prod;
    }
    public static Matrix mult2(Matrix[] m, int[][] arr, int des) {
        Matrix prod;
        System.out.println("Let new Matrix = Matrix "+name[arr[des][1]]+" * Matrix "+name[arr[des][0]]);
        Matrix.multiplyTraverse(m[arr[des][1]], m[arr[des][0]]);
        prod = Matrix.multiply(m[arr[des][1]], m[arr[des][0]]);
        return prod;
    }
    public static Matrix mult1(Matrix[] m, int[][] arr, int des) {
        Matrix prod;
        System.out.println("Let new Matrix = Matrix "+name[arr[des][0]]+" * Matrix "+name[arr[des][1]]);
        Matrix.multiplyTraverse(m[arr[des][0]], m[arr[des][1]]);
        prod = Matrix.multiply(m[arr[des][0]], m[arr[des][1]]);
        return prod;
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
    public static int decision(int y){
        int x = 0;
        do  //error trapping for choices, traps out input that are not of the choices indicated and non integer inputs.
        {
            try
            {
                x = Integer.parseInt(scan.nextLine());
                if(x<1 || x>y) //this code runs if input is now an integer
                {
                    System.out.println("Input should be within range. Try again.");
                }
            }
            catch(NumberFormatException e)  //it catches inputs that are not integers.
            {
                System.out.println("Input numbers only. Try again.");
            }
        }while(x<0 || x>y);
        return x;
    }
    public static int range(){
        int x = 0;
        do  //error trapping for choices, traps out input that are not of the choices indicated and non integer inputs.
        {
            try
            {
                x = Integer.parseInt(scan.nextLine());
                if(x<2 || x>10) //this code runs if input is now an integer
                {
                    System.out.println("Input should be within range. Try again.");
                }
            }
            catch(NumberFormatException e)  //it catches inputs that are not integers.
            {
                System.out.println("Input numbers only. Try again.");
            }
        }while(x<2 || x>10);
        return x;
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
                    if(k==0&&k+1 == m1[0].length){
                        System.out.printf("%10s",("(" + m1[i][k] + ")(" + m2[k][j] + ")"));
                        System.out.printf("%3s ", "");
                    }else if(k==0){
                        System.out.printf("%10s",("(" + m1[i][k] + ")(" + m2[k][j] + ")+"));
                    }else if(k+1 == m1[0].length) {
                        System.out.printf("(" + m1[i][k] + ")(" + m2[k][j] + ") %4s","");
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
    //scalar multiplication of a matrix
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