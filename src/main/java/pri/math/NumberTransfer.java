package pri.math;

/*0-9 = 0-9, a-z = 10-31, A-Z = 32-61*/
public class NumberTransfer {

    public static void main(String[] args) {
        new NumberTransfer();
    }

    public NumberTransfer() {
        // Examples
        System.out.println(transfer("ILoveU", 62, 32));
        System.out.println(transfer("dove", 16, 8));
    }

    public String transfer(String origin, int originBase, int targetBase) {
        int decimal = stringToDecimal(origin, originBase);
        return decimalToTargetBase(decimal, targetBase);
    }

    // warning: The sum could be very large, consider to use BigInteger
    private int stringToDecimal(String number, int base) {
        char[] chars = number.toCharArray();
        int index = 0;

        int sum = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            int digitDecimal = digitToDecimal(chars[i], base, index);
            sum += digitDecimal;
            index++;
        }

        return sum;
    }

    private String decimalToTargetBase(int decimal, int base) {
        StringBuilder result = new StringBuilder();

        while (decimal >= base) {
            int remainder = decimal % base;
            decimal /= base;

            char digit = toChar(remainder);
            result.insert(0, digit);
        }

        if (decimal > 0) {
            char digit = toChar(decimal);
            result.insert(0, digit);
        }

        return result.toString();
    }


    // warning: lack of error handling
    private int charToDecimal(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'a' && (int) c <= 'z') {
            return c - 'a' + 10;
        }
        if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 26;
        }
        return 0;
    }

    // warning: lack of error handling
    private int digitToDecimal(char c, int base, int digit) {
        int decimal = charToDecimal(c);
        if (decimal >= base) {
            return 0;
        }

        int weight = 1;
        for (int i = digit; i > 0; i--) {
            weight *= base;
        }
        return decimal * weight;
    }

    private char toChar(int i) {
        if (i < 10) {
            return (char) ('0' + i);
        }
        if (i < 36) {
            return (char) ('a' + i - 10);
        }
        if (i < 62) {
            return (char) ('A' + i - 26);
        }
        return ' ';
    }
}
