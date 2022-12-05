package me.chrommob;

import java.util.*;

public class Main {
    private static HashMap<double[], Double> calc = new HashMap<>();
    private static HashMap<double[], Double> tried = new HashMap<>();
    private static Set<Double> divisors = new HashSet<>();
    public static void main(String[] args) {
        System.out.println("Napiš maximální exponent.");
        int exp = getNumber();
        int[] input = new int[exp];
        for (int i = 1; i <= exp; i++) {
            System.out.println("Napiš " + i + ". číslo.");
            input[i-1] = getNumber();
        }
        long startDiv = System.currentTimeMillis();
        getDivisors(input);
        long endDiv = System.currentTimeMillis();
        System.out.println("Získání dělitelů trvalo " + (endDiv - startDiv) + " ms.");
        for (int e = exp; e > 0; e--) {
            double[] array = new double[e];
            if (calc.isEmpty()) {
                for (int i = 0; i < e; i++) {
                    array[i] = input[i];
                }
            } else {
                array = (double[]) calc.keySet().toArray()[calc.size() - 1];
            }
            boolean hadResult = false;
            for (int d = 0; d < divisors.size(); d++) {
                if (hasResult(array, e)) {
                    hadResult = true;
                }
            }
            if (!hadResult) break;
        }

        //calc.forEach((k, v) -> System.out.println("Výsledek pro " + v + " je " + Arrays.toString(k)));
        System.out.println("Výsledky jsou: " + new HashSet<>(calc.values()));
    }

    public static boolean hasResult(double[] input, int exp) {
        for (double divisor : divisors) {
            //Check if input is in tried by looping through all keys
            CalcResult calcResult = calcResult(divisor, input, exp);
            if (calcResult == null) continue;
            tried.put(input, divisor);
            if (calcResult.getResult() == 0) {
                if (!calc.containsKey(calcResult.getArray())) {
                    calc.put(calcResult.getArray(), divisor);
                }
                return true;
            }
        }
        return false;
    }

    public static CalcResult calcResult(double divisor, double[] input, int loops) {
        boolean isTried = false;
        for (double[] key : tried.keySet()) {
            if (Arrays.equals(key, input)) {
                if (tried.get(key).equals(divisor)) {
                    System.out.println("Výsledek pro " + divisor + " je " + Arrays.toString(input) + " už byl zjištěn.");
                    isTried = true;
                    break;
                }
            }
        }
        if (isTried) {
            return null;
        }
        double result;
        double[] calc = new double[loops];
        calc[0] = input[0];
        for (int i = 1; i < loops; i++) {
            calc[i] = calc[i-1] * divisor + input[i];
        }
        result = calc[loops-1];
        if (result == 0) {
            System.out.println("Vstup: " + Arrays.toString(input) + " | Výstup: " + Arrays.toString(calc) + " | Dělitel: " + divisor);
        }
        return new CalcResult(result, calc);
    }

    private static void getDivisors(int[] input) {
        int i = input[input.length - 1];
        int x = input[0];
        getDivisors(i);
        getDivisors(x);
        System.out.println("Dělitelé jsou: " + divisors);
    }
    
    private static void getDivisors(int input) {
        int newInput = Math.abs(input);
        for (double test = 0; test <= newInput; test++) {
            if (test == 0) continue;
            if (newInput % test != 0) continue;
            divisors.add(test);
            divisors.add(-test);
        }
        for (double divisor : new HashSet<>(divisors)) {
            divisors.add(divisor / input);
            divisors.add(-divisor / input);
        }
    }

    public static int getNumber() {
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }
}