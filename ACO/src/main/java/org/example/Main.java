package org.example;

public class Main {
    public static void main(String[] args) {
        Aco aco = new Aco();
        aco.init(2000);
        aco.run(20);
        aco.reportResult();
    }
}