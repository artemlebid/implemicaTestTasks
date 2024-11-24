import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

public class ThirdTask {

    /*
        Find the sum of the digits in the number 100! (i.e. 100 factorial)
        Correct answer: 648
    */

    public static void main(String[] args) {
        BigInteger resultOfFact = factorial(100); // calculate factorial
        int sumOfDigits = sumOfNumber(resultOfFact); // find a sum

        System.out.println(sumOfDigits); // print a result
    }

    /*
         sumOfNumber() -> function that counts the sum of digits in a number
         Firstly we create String 'stringNumber' from number
         Second step, split line on the characters, map to integer number and put to list
         Thirdly return sum of items in list with reduce() function
    */
    private static int sumOfNumber(BigInteger number) {
        String stringNumber = number.toString();
        List<Integer> a = Stream.of(stringNumber.split("")).map(Integer::parseInt).toList();

        return a.stream().reduce(0, Integer::sum);
    }


    /*
         factorial() -> function that calculates the factorial of number
         return value is BigDecimal cause 100! = is tooooo long value
    */
    public static BigInteger factorial(int number) throws IllegalArgumentException{
        if (number < 0)
            throw new IllegalArgumentException();

        BigInteger fact = BigInteger.ONE;

        for (int i = 2; i <= number; i++) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }

        return fact;
    }
}
