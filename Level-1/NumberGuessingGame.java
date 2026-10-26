
import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    private static final int MIN = 1;
    private static final int MAX = 100;
    private static final int MAX_ATTEMPTS = 7;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int secretNumber = random.nextInt(MAX - MIN + 1) + MIN;
        int attempts = 0;
        boolean guessedCorrectly = false;

        System.out.println("===== NUMBER GUESSING GAME =====");
        System.out.println("Guess a number between " + MIN + " and " + MAX);
        System.out.println("You have " + MAX_ATTEMPTS + " valid attempts.");

        while (attempts < MAX_ATTEMPTS) {
            System.out.print(
                "\nAttempt " + (attempts + 1) + "/" + MAX_ATTEMPTS
                + " - Enter your guess: "
            );

            String input = sc.nextLine().trim();
            int guess;

            try {
                guess = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a whole number.");
                continue;
            }

            if (guess < MIN || guess > MAX) {
                System.out.println("Guess must be between 1 and 100.");
                continue;
            }

            attempts++;

            if (guess == secretNumber) {
                guessedCorrectly = true;
                System.out.println("Correct! You guessed the number.");
                System.out.println("Attempts used: " + attempts);
                break;
            } else if (guess < secretNumber) {
                System.out.println("Too low! Try a higher number.");
            } else {
                System.out.println("Too high! Try a lower number.");
            }

            System.out.println(
                "Attempts remaining: " + (MAX_ATTEMPTS - attempts)
            );
        }

        if (!guessedCorrectly) {
            System.out.println("\nGame over! No attempts left.");
            System.out.println("The correct number was: " + secretNumber);
        }

        sc.close();
    }
}
