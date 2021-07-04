package GUI;

import java.util.ArrayList;
import java.util.List;

public class Perceptron {

    double alpha;
    List<Double> vectorW;
    double t;

    public Perceptron(int vectorSize, double alpha) {
        this.alpha = alpha;
        this.vectorW = new ArrayList<>();

        // -------------- WYPEŁNIENIE WEKTORA WARTOŚCIAMI Z ZAKRESU (-5, 5) --------------

        for (int i = 0; i < vectorSize ; i++) {
            this.vectorW.add((Math.random() * 10) - 5);
        }

        // -------------- NADANIE 't' WARTOŚCI Z ZAKRESU (-5, 5) --------------

        this.t = Math.random()*10-5;
    }

    public void learn(Attribute attribute, int d){

        double net = 0;
        int y;
        int size = attribute.getDataLines().size();
        List<Double> tmp = attribute.getDataLines();

        // ------------ OBLICZANIE X * W ------------
        for (int i = 0; i < size ; i++) {
            net += tmp.get(i) * this.vectorW.get(i);
        }

        if (this.t <= net){
            y = 1;
        } else{
            y = 0;
        }

        if (y != d) {

            List<Double> vectorWPrime = new ArrayList<>(this.vectorW);

            // W' = W + (d-y) * Alpha * X

            for (int i = 0; i < size; i++) {
                vectorWPrime.set(i, (this.vectorW.get(i) + ((d - y) * alpha * tmp.get(i))));
            }

            this.vectorW = vectorWPrime;
            this.t = t + (d - y) * alpha * -1;
        }
    }

    public int evaluate(Attribute attribute){

        double net = 0;
        int size = attribute.getDataLines().size();
        List<Double> tmp = attribute.getDataLines();

        // ------------ OBLICZANIE X * W ------------

        for (int i = 0; i < size ; i++) {
            net += tmp.get(i) * this.vectorW.get(i);
        }

        if (this.t <= net){
            return 1;
        }else{
            return 0;
        }
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setVectorW(List<Double> vectorW) {
        this.vectorW = vectorW;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getAlpha(){
        return alpha;
    }

    public List<Double> getVectorW() {
        return vectorW;
    }

    public double getT() {
        return t;
    }
}
