import java.util.*;

public class NumberGuessingGame {
    public int randomNumber() {
        Random rand = new Random();
        return rand.nextInt(100) + 1;
    }

    public String checkGuess(int guess, int number) {
        if (guess < 1 || guess > 100) {
            return "Invalid guess! Please enter a number between 1 and 100.";
        }
        if (guess < number) {
            return "Too low!";
        } else if (guess > number) {
            return "Too high!";
        } else {
            return "Congratulations! You've guessed the number!";
        }
    }

    public static void main(String[] args) {
        NumberGuessingGame game = new NumberGuessingGame();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Number Guessing Game!");

        boolean playAgain = true;

        while (playAgain) {
            int number = game.randomNumber();
            int maxAttempts = 8;
            int attemptsUsed = 0;

            System.out.println("\nI have selected a number between 1 and 100.");
            System.out.println("You have " + maxAttempts + " attempts to guess it.");

            boolean guessedCorrectly = false;

            while (attemptsUsed < maxAttempts) {
                System.out.print("Enter your guess: ");
                String input = scanner.nextLine();

                if (input.trim().isEmpty()) {
                    System.out.println("Input cannot be empty. Please enter a number.");
                    continue;
                }

                int guess;
                try {
                    guess = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a valid integer.");
                    continue;
                }

                attemptsUsed++;
                String result = game.checkGuess(guess, number);
                System.out.println(result);

                if (result.equals("Congratulations! You've guessed the number!")) {
                    guessedCorrectly = true;
                    break;
                } else {
                    System.out.println("Attempts remaining: " + (maxAttempts - attemptsUsed));
                }
            }

            if (!guessedCorrectly) {
                System.out.println("Game over! You didn't guess the number. It was: " + number);
            }

            // Ask if user wants to play again
            System.out.print("Do you want to play another round? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (!(response.equals("yes") || response.equals("y"))) {
                playAgain = false;
                System.out.println("Thanks for playing! Goodbye!");
            }
        }

        scanner.close();
    }
}
