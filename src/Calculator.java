import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {
    private static final Map<String, Integer> romanNumerals = new HashMap<>();

    static {
        romanNumerals.put("I", 1);
        romanNumerals.put("II", 2);
        romanNumerals.put("III", 3);
        romanNumerals.put("IV", 4);
        romanNumerals.put("V", 5);
        romanNumerals.put("VI", 6);
        romanNumerals.put("VII", 7);
        romanNumerals.put("VIII", 8);
        romanNumerals.put("IX", 9);
        romanNumerals.put("X", 10);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите выражение (например, 3 + II):");
        String input = scanner.nextLine().trim();

        try {
            String[] parts = input.split(" ");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Неверный формат выражения");
            }

            String operand1 = parts[0].trim();
            String operator = parts[1].trim();
            String operand2 = parts[2].trim();

            int num1, num2;

            if (isRomanNumeral(operand1) && isRomanNumeral(operand2)) {
                num1 = romanToArabic(operand1);
                num2 = romanToArabic(operand2);
            } else if (isArabicNumeral(operand1) && isArabicNumeral(operand2)) {
                num1 = Integer.parseInt(operand1);
                num2 = Integer.parseInt(operand2);
            } else {
                throw new IllegalArgumentException("Числа должны быть либо арабскими, либо римскими");
            }

            int result;
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        throw new ArithmeticException("Деление на ноль");
                    }
                    result = num1 / num2;
                    break;
                default:
                    throw new IllegalArgumentException("Неверная операция");
            }

            if (isRomanNumeral(operand1) && isRomanNumeral(operand2)) {
                if (result <= 0) {
                    throw new IllegalArgumentException("Результат римского числа не может быть меньше 1");
                }
                System.out.println(arabicToRoman(result));
            } else {
                System.out.println(result);
            }

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static boolean isArabicNumeral(String input) {
        try {
            int value = Integer.parseInt(input);
            return value >= 1 && value <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isRomanNumeral(String input) {
        return romanNumerals.containsKey(input);
    }

    private static int romanToArabic(String input) {
        return romanNumerals.get(input);
    }

    private static String arabicToRoman(int input) {
        if (input < 1 || input > 3999) {
            throw new IllegalArgumentException("Значение должно быть в диапазоне от 1 до 3999");
        }

        StringBuilder roman = new StringBuilder();
        for (Map.Entry<String, Integer> entry : romanNumerals.entrySet()) {
            while (input >= entry.getValue()) {
                roman.append(entry.getKey());
                input -= entry.getValue();
            }
        }
        return roman.toString();
    }
}