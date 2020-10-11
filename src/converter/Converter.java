package converter;

import java.util.Scanner;

public class Converter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int sourceRadix = 0;
        String sourceNumber = "";
        int targetRadix = 0;
        boolean wrongInput = false;

        do {
            System.out.println("Enter source radix, number to convert and target radix");
            try {
                sourceRadix = Integer.valueOf(scanner.nextLine());
                sourceNumber = scanner.nextLine();
                targetRadix = Integer.valueOf(scanner.nextLine());

                if (sourceRadix < 1 || sourceRadix > 36 || targetRadix < 1 || targetRadix > 36) {
                    throw new Exception();
                }
            } catch (Exception e) {
                wrongInput = true;
                System.out.println("Wrong input try again");
            }
        } while (wrongInput);

        String integerPart = "";
        String fractionalPart = "";
        String[] numberInParts;

        if (sourceNumber.contains(".")) {
            numberInParts = sourceNumber.split("\\.");
            integerPart = numberInParts[0];
            fractionalPart = numberInParts[1];
        } else {
            integerPart = sourceNumber;
        }

        int decimalInteger = 0;
        double decimalFractional = 0.0;

        if (sourceRadix == 1) {
            decimalInteger = (integerPart + "").length();
            decimalFractional = (fractionalPart + "").length();

        } else if (sourceRadix != 10) {
            decimalInteger = Integer.parseInt(integerPart, sourceRadix);
            String[] tempFractional = fractionalPart.split("");

            int fractionalPartLength = 0;
            if (!fractionalPart.isEmpty()) {
                fractionalPartLength = tempFractional.length;
            }

            for (int i = 0; i < fractionalPartLength; i++) {
                int fractionalPartDigit = 0;
                if (tempFractional[i].matches("[a-zA-Z]+")) {
                    fractionalPartDigit = Character.digit(tempFractional[i].charAt(0), sourceRadix);
                } else {
                    fractionalPartDigit = Integer.parseInt(tempFractional[i]);
                }
                decimalFractional += fractionalPartDigit / Math.pow(sourceRadix, i + 1);
            }
        } else {
            decimalInteger = Integer.parseInt(integerPart);
            decimalFractional = Double.parseDouble("0." + fractionalPart);
        }


        StringBuilder convertedInteger = new StringBuilder("");
        StringBuilder convertedFractional = new StringBuilder("");

        if (targetRadix == 1) {

            for (int i = 0; i < decimalInteger; i++) {
                convertedInteger.append(1);
            }

            for (int i = 0; i < decimalFractional; i++) {
                convertedFractional.append(1);
            }

        } else {
            convertedInteger.append(Integer.toString(decimalInteger, targetRadix));
            double fractionalInConvert = decimalFractional;
            for (int i = 0; i < 5; i++) {
                fractionalInConvert *= targetRadix;
                String[] tempFractional = String.valueOf(fractionalInConvert).split("\\.");

                int fractionalDigit = Integer.parseInt(tempFractional[0]);
                if (fractionalDigit > 9) {
                    char convertedFractionalDigit = Character.forDigit(fractionalDigit, targetRadix);
                    convertedFractional.append(convertedFractionalDigit);
                } else {
                    convertedFractional.append(fractionalDigit);
                }
                fractionalInConvert = Double.parseDouble("0." + tempFractional[1]);
            }
        }

        String convertedNumber = "";
        if (fractionalPart.isEmpty()) {
            convertedNumber = convertedInteger.toString();
        } else {
            convertedNumber = convertedInteger.toString() + "." + convertedFractional.toString();
        }

        System.out.println(convertedNumber);
    }
}
