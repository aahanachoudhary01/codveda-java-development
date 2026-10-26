
import java.util.Scanner;

public class Calculator {

    static double add(double a, double b) {
        return a + b;
    }

    static double subtract(double a, double b) {
        return a - b;
    }

    static double multiply(double a, double b) {
        return a * b;
    }

    static double divide(double a, double b) {
        if (b == 0.0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a / b;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            while (true) {
                System.out.println("\n===== BASIC CALCULATOR =====");
                System.out.println("1. Addition (+)");
                System.out.println("2. Subtraction (-)");
                System.out.println("3. Multiplication (*)");
                System.out.println("4. Division (/)");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");

                String choice = sc.nextLine().trim();

                if (choice.equals("5")) {
                    System.out.println("Calculator closed.");
                    break;
                }

                if (!choice.matches("[1-4]")) {
                    System.out.println("Invalid choice. Enter 1 to 5.");
                    continue;
                }

                double a, b;

                try {
                    System.out.print("Enter first number: ");
                    a = Double.parseDouble(sc.nextLine().trim());

                    System.out.print("Enter second number: ");
                    b = Double.parseDouble(sc.nextLine().trim());

                    if (!Double.isFinite(a) || !Double.isFinite(b)) {
                        System.out.println("Please enter finite numbers only.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number. Please try again.");
                    continue;
                }

                try {
                    double result;

                    switch (choice) {
                        case "1":
                            result = add(a, b);
                            break;
                        case "2":
                            result = subtract(a, b);
                            break;
                        case "3":
                            result = multiply(a, b);
                            break;
                        case "4":
                            result = divide(a, b);
                            break;
                        default:
                            continue;
                    }

                    if (!Double.isFinite(result)) {
                        System.out.println(
                            "Result is outside the supported numeric range."
                        );
                    } else {
                        System.out.println("Result: " + result);
                    }

                } catch (ArithmeticException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } finally {
            sc.close();
        }
    }
}
