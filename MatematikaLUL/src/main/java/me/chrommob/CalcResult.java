package me.chrommob;

public class CalcResult {
    private final double result;
    private final double[] array;
    public CalcResult(double result, double[] array) {
        this.result = result;
        this.array = array;
    }


    public double getResult() {
        return result;
    }

    public double[] getArray() {
        return array;
    }
}
