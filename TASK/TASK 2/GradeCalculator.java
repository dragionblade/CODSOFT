import java.util.Scanner;

public class GradeCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean calculateAgain = true;

        while (calculateAgain) {
            System.out.print("Enter the number of subjects: ");
            int subjects = scanner.nextInt();

            int totalMarks = 0;

            for (int i = 1; i <= subjects; i++) {
                int marks;
                while (true) {
                    System.out.print("Enter marks obtained in subject " + i + " (out of 100): ");
                    marks = scanner.nextInt();
                    if (marks >= 0 && marks <= 100) {
                        break; // valid input
                    } else {
                        System.out.println("Invalid marks! Please enter a value between 0 and 100.");
                    }
                }
                totalMarks += marks;
            }

            double average = (double) totalMarks / subjects;

            // Grade calculation with fail below 35%
            String grade;
            if (average < 35) {
                grade = "Fail";
            } else if (average >= 90) {
                grade = "A+";
            } else if (average >= 80) {
                grade = "A";
            } else if (average >= 70) {
                grade = "B";
            } else if (average >= 60) {
                grade = "C";
            } else if (average >= 50) {
                grade = "D";
            } else {
                grade = "E";
            }

            // Display results
            System.out.println("\n--- Results ---");
            System.out.println("Total Marks: " + totalMarks + " out of " + (subjects * 100));
            System.out.printf("Average Percentage: %.2f%%\n", average);
            System.out.println("Grade: " + grade);

            // Ask user if they want to calculate another score
            System.out.print("\nDo you want to calculate another score? (yes/no): ");
            scanner.nextLine(); // consume leftover newline
            String response = scanner.nextLine().trim().toLowerCase();

            if (!response.equals("yes") && !response.equals("y")) {
                calculateAgain = false;
                System.out.println("Thank you for using the Marks Calculator!");
            }
        }

        scanner.close();
    }
}
