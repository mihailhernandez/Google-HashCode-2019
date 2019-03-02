package main;

import model.Pizza;

public class Main {

    public static void main(String[] args) {
        Pizza pizza = new Pizza(args[0]);
        pizza.printPizza();
        pizza.solve();
        pizza.createSolutionFile();
    }
}
