import java.util.*;
public class MP1napud {
    public static Scanner scan = new Scanner(System.in);
    public static int count;
    public static Matrix[] matrices = new Matrix[100];
    public static void main (String[] args){
        Matrix A = createMatrix("1");
        matrices[count] = A;
        count++;
        Matrix B = createMatrix("2");
        matrices[count] = B;
        count++;
        System.out.println("Matrix 1");
        A.printMatrix();
        System.out.println("Matrix 2");
        B.printMatrix();

        showMatrices(matrices, count);


        int yorn = 0;
        do  //this do-while loop handles the choice "yes or no" at the near end of the code. The loop ends when the user chooses no.
        {
            Matrix C = operations();
            if(C!=null) {
                matrices[count] = C;
                count++;
            }
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
    public static Matrix operations(){
        System.out.println("Choose an option: 0-adding matrices, 1-multiplying matrices, 2-multiplying a matrix w/ a constant, 3-diagonalizing a matrix, 4-show made matrices");
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

        Matrix result = null;

        switch(choice)  //choices laid out in different cases to run the intended method. does not need a default since "choice" is by default only within the range of the choices because of the error trapping.
        {
            case 0:
                if(count>2){
                    System.out.println("Choose which matrices you want to add:");
                    int num = 0;
                    int[] option = new int[2];
                    while(num<2) {
                        matrixChoice(matrices, count);
                        option[num] = matrixNum(count);
                        num++;
                    }
                    if (matrices[option[0]-1].getMatrix().length != matrices[option[1]-1].getMatrix().length || matrices[option[0]-1].getMatrix()[0].length != matrices[option[1]-1].getMatrix()[0].length) {
                        System.out.println("Matrix size are not equal, cannot do operation.");
                    } else {
                        System.out.println("Let Matrix "+(count+1)+" = Matrix "+option[0]+" + Matrix "+option[1]);
                        result = Matrix.add(matrices[option[0]-1], matrices[option[1]-1]);
                        result.printMatrix();
                    }
                    break;

                }
                else{
                    if (matrices[0].getMatrix().length != matrices[1].getMatrix().length || matrices[0].getMatrix()[0].length != matrices[1].getMatrix()[0].length) {
                        System.out.println("Matrix size are not equal, cannot do operation.");
                    } else {
                        System.out.println("Let Matrix 3 = Matrix 1 + Matrix 2");
                        result = Matrix.add(matrices[0], matrices[1]);
                        result.printMatrix();
                    }
                    break;
                }
            case 1:
                if(count>2){
                    System.out.println("Choose which matrices you want to multiply:");
                    int num = 0;
                    int[] option = new int[2];
                    while(num<2) {
                        matrixChoice(matrices, count);
                        option[num] = matrixNum(count);
                        num++;
                    }
                    System.out.println("Choose which Matrix to become the multiplicand. 1-Matrix "+option[0]+", 2-Matrix "+option[1]);
                    int choose = choose();
                    if (choose == 1) {

                        if (matrices[option[0]-1].getMatrix()[0].length != matrices[option[1]-1].getMatrix().length) {
                            System.out.println("Matrix size criteria for multiply are not met, cannot do operation.");
                        } else {
                            System.out.println("Let Matrix "+(count+1)+" = Matrix "+option[0]+" * Matrix "+option[1]);
                            result = Matrix.multiply(matrices[option[0]-1], matrices[option[1]-1]);
                            result.printMatrix();
                        }
                    } else if (choose == 2) {
                        if (matrices[option[1]-1].getMatrix()[0].length != matrices[option[0]-1].getMatrix().length) {
                            System.out.println("Matrix size criteria for multiply are not met, cannot do operation.");
                        } else {
                            System.out.println("Let Matrix "+(count+1)+" = Matrix "+option[0]+" * Matrix "+option[1]);
                            result = Matrix.multiply(matrices[option[1]-1], matrices[option[0]-1]);
                            result.printMatrix();
                        }
                    }
                }
                else {
                    System.out.println("Choose which Matrix to become the multiplicand. 1-Matrix A, 2-Matrix B");
                    int choose = choose();
                    if (choose == 1) {

                        if (matrices[0].getMatrix()[0].length != matrices[1].getMatrix().length) {
                            System.out.println("Matrix size criteria for multiply are not met, cannot do operation.");
                        } else {
                            System.out.println("Let Matrix "+(count+1)+" = Matrix 1 * Matrix 2");
                            result = Matrix.multiply(matrices[0], matrices[1]);
                            result.printMatrix();
                        }
                    } else if (choose == 2) {
                        if (matrices[0].getMatrix()[0].length != matrices[1].getMatrix().length) {
                            System.out.println("Matrix size criteria for multiply are not met, cannot do operation.");
                        } else {
                            System.out.println("Let Matrix "+(count+1)+" = Matrix 2 * Matrix 1");
                            result = Matrix.multiply(matrices[0], matrices[1]);
                            result.printMatrix();
                        }
                    }
                }
                break;
            case 2:
                System.out.println("Input a constant that you want to multiply with the matrix.");
                int constant = integer();
                System.out.println("Now choose a matrix to be multiplied by a constant:");
                matrixChoice(matrices, count);
                int ch = matrixNum(count);
                System.out.println("Let Matrix "+(count+1)+" = Matrix "+ch+" * "+constant);
                result = Matrix.multiplyConstant(matrices[ch-1], constant);
                result.printMatrix();
                break;
            case 3:
                System.out.println("Choose a matrix to be diagonalized:");
                matrixChoice(matrices, count);
                int cho = matrixNum(count);
                if(matrices[cho-1].getMatrix().length != matrices[cho-1].getMatrix()[0].length) {
                    System.out.println("Matrix size are not equal, cannot do operation.");
                }
                else{
                    System.out.println("Input a constant that you want to use to diagonalize the Matrix");
                    int diag = integer();
                    System.out.println("Let Matrix "+(count+1)+"= diagonalized Matrix "+cho);
                    Matrix dia = Matrix.diagonalize(matrices[cho-1], diag);
                    dia.printMatrix();
                }
                break;
            case 4:
                showMatrices(matrices, count);
                break;
        }
        System.out.println("----------------------------------------------------------------------");
        return result;
    }
    public static void showMatrices(Matrix[] arr, int count){
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Matrices: ");
        for(int i = 0; i<count; i++){
            if(arr[i+1]==null){
                System.out.print("Matrix "+(i+1));
            }else{
                System.out.print("Matrix "+(i+1)+", ");
            }
        }
        System.out.println();
        System.out.println("----------------------------------------------------------------------");
    }
    public static void matrixChoice(Matrix[] arr, int count){
        System.out.print("[");
        for(int i = 0; i<count; i++){
            if(arr[i+1]==null){
                System.out.print((i+1)+"-Matrix "+(i+1));
            }else{
                System.out.print((i+1)+"-Matrix "+(i+1)+", ");
            }
        }
        System.out.print("]: ");
    }
    public static int matrixNum(int x){
        try {
            int choice = Integer.parseInt(scan.nextLine());
            if(choice<0 || choice>x) {//this code runs if input is now an integer
                System.out.println("Input should be within range. Try again");
            }
            return choice;
        }
        catch(NumberFormatException e){  //it catches inputs that are not integers.
            System.out.println("Input numbers only. Try again.");
            return matrixNum(x);
        }
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
        }while(x<0 || x>9);
        return x;
    }
}
class Matrix{
    int [][]matrix;     //2d array for the matrix
    //constructor of a matrix
    Matrix(int [][]matrix){
        this.matrix = matrix;
    }
    public int[][] getMatrix(){
        return this.matrix;
    }
    //prints the matrix
    public void printMatrix(){
        for(int i = 0; i < this.matrix.length; i++) {
            System.out.print("|");
            for(int j = 0; j < this.matrix[i].length; j++){
                System.out.printf("%3d ", this.matrix[i][j]);
            }
            System.out.print("|");
            System.out.println();
        }
    }
    //addition of two matrices
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
    public static Matrix multiply(Matrix A, Matrix B){
        int[][] m1 = A.getMatrix();
        int[][] m2 = B.getMatrix();
        int[][] prod = new int [m1.length][m2[0].length];
        for(int i = 0; i<m1.length; i++){
            for(int j = 0; j<m2[0].length; j++){
                prod[i][j] = 0;
                for(int k = 0; k<m1.length; k++){
                    prod[i][j] = prod[i][j] + (m1[i][k] * m2[k][j]);
                }
            }
        }
        return new Matrix(prod);
    }
    //multiplication with a constant
    public static Matrix multiplyConstant(Matrix m1, int c){
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