import java.util.Scanner;

public class GPA_Calculator{

    public static void main(String[] args) {


        Subject []subjects = {
                new Subject("CCC121", 3.0),
                new Subject("GEC107", 3.0),
                new Subject("GEC108", 3.0),
                new Subject("ITE131", 3.0),
                new Subject("MAT061", 5.0),
                new Subject("NIH001", 3.0),
                new Subject("PED013", 2.0)
        };

        System.out.println("Input your grades for your subjects(Leave blank if you have no grade for that subject yet)");
        for(int i = 0; i < subjects.length; i++){

            Scanner input = new Scanner(System.in);

            System.out.print(subjects[i].getName() +": ");
            try{
                subjects[i].setGrade(Double.parseDouble(input.nextLine()));
            }catch(NumberFormatException NE){
                subjects[i].setGrade(null);
            }
        }

        double gpa = getGPA(subjects);
        System.out.println("Your GPA is: " +gpa);
    }

    public static double getGPA(Subject[] subjects){

        double total_units = 0;
        double total_weighted_grades = 0;

        for(int i = 0; i < subjects.length; i++){
            if(subjects[i].grade() == null) continue;
            subjects[i].setWeightedGrade();

            total_weighted_grades += subjects[i].getWeightedGrade();
            total_units += subjects[i].units();
        }

        return total_weighted_grades / total_units;

    }
}
class Subject {
    private String name;
    private double units;
    private Double grade;
    private double weighted_grade;
    Subject(String name, double units){
        this.name = name;
        this.units = units;
    }
    public void setWeightedGrade(){
        weighted_grade = grade * units;
    }

    public double getWeightedGrade() {
        return weighted_grade;
    }

    public void setGrade(Double grade){
        this.grade = grade;
    }
    public String getName(){
        return name;
    }

    public double units(){
        return units;
    }

    public Double grade(){
        return grade;
    }
}
