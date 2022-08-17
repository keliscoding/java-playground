package io.github.zam0k.restwithspringbootandjavaerudio.math;

public class SimpleMath {

    public Double sum(Double numberOne, Double numberTwo)
            throws Exception {
        return numberOne + numberTwo;
    }

    public Double subtraction(Double numberOne,
                              Double numberTwo) {
        return numberOne - numberTwo;
    }

    public Double multiply(Double numberOne,
                           Double numberTwo) {
        return numberOne * numberTwo;
    }

    public Double divide(Double numberOne,
                         Double numberTwo) {
        return numberOne / numberTwo;
    }

    public Double arithmeticAverage(Double numberOne,
                                    Double numberTwo) {
        return (numberOne + numberTwo)/2;
    }

    public Double squareRoot(Double number) {
        return Math.sqrt(number);
    }

}
