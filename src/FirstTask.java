import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FirstTask {

    /*
        Find the number of correct bracket expressions containing N opening and N closing brackets.
        N is entered from the keyboard. N is a non-negative integer.
    */

    public static void main(String[] args) {
        System.out.println("Program started - enter '/q' for exit\n");

        // try with resources for automatically close input stream
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            // infinity loop for possibility handle errors and input field without restart app
            while (true){
                try {
                    System.out.print("Enter the number (should be a positive integer): ");
                    String enteredLine = reader.readLine();// Enter a string line

                    if (enteredLine.equals("/q")) // Condition for exit
                        break;

                    long enteredNumber = validateNumber(enteredLine); // parse and validate entered number
                    long result = findCombinationCount(enteredNumber); // find number of possible variant brackets

                    System.out.printf("Number of correct bracketed expressions for %d = %d", enteredNumber, result); // print result
                    break;
                } catch (Exception e) {
                    // Empty because there is a System.out.print for throwable exception
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    /*
         validateNumber() -> function that parse entered String number and check for non-negative and illegal format
         Number should be integer and bigger than 0.
    */
    public static long validateNumber(String number) throws IllegalArgumentException {
        long result = 0;

        try{
            result = Long.parseLong(number);
        } catch (IllegalArgumentException e){
            System.out.println("Unexpected number format, try again"); // Log error
            throw new IllegalArgumentException(); // throw exception for restart infinity loop
        }

        if (result <= 0){
            System.out.println("Number can't be negative or zero, try again");
            throw new NumberFormatException(); // throw exception for restart infinity loop
        }

        return result;
    }


    /*
        findCombinationCount() -> function that searches for the number of combinations using the Catalan number
            Cn = (2n)! / ((n+1)! * n!)
        where:
            numerator = (2n)!
            denominator = (n+1)! * n!
    */
    public static long findCombinationCount(long number){
        long numerator = factorial(number * 2);
        long denominator = factorial(number + 1) * factorial(number);

        return numerator / denominator;
    }

    /*
         factorial() -> function that calculates the factorial of number
    */
    public static long factorial(long number) throws IllegalArgumentException{
        if (number < 0)
            throw new IllegalArgumentException("Number for factorial cannot be negative");

        long result = 1L;

        for (int i = 2; i <= number; i++) {
            result *= i;
        }

        return result;
    }
}