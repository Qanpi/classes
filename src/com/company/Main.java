package com.company;
import static ibio.Helpers.*; //static allows to use the methods without calling the class

public class Main {
    public static void main(String[] args) {
        hw3("rsabrsmnrrsrsxyz", "abrs");
//        Pseudocode.test();
    }

    static void hw1() {
        for(int k=0; k<11; k++) {
            double a = k * Math.PI / 5;
            System.out.print(a + ", ");
            System.out.print(Math.sin(a) + ", ");
            System.out.println(Math.cos(a));
        }
    }

    static void hw2() {
        boolean RUNNING = true;
        while (RUNNING) {
            String OPERATION = inputString("give operation: ");
            if (OPERATION.equals("end"))
                RUNNING = false;
            else {
                int A = inputInt("give first integer operand: ");
                int B = inputInt("give second integer operand: ");
                if (OPERATION.equals("add")) {
                    System.out.println(A+B);
                } else if (OPERATION.equals("sub")) {
                    System.out.println(A-B);
                } else {
                    System.out.println("unknown operation");
                }
            }
        }
    }

    static void hw21() {
        for(boolean RUNNING=true; RUNNING; ) { //completely changes the game
            String OPERATION = inputString("give operation: ");
            if (OPERATION.equals("end"))
                RUNNING = false;
            else {
                int A = inputInt("give first integer operand: ");
                int B = inputInt("give second integer operand: ");
                if (OPERATION.equals("add")) {
                    System.out.println(A+B);
                } else if (OPERATION.equals("sub")) {
                    System.out.println(A-B);
                } else {
                    System.out.println("unknown operation");
                }
            }
        }
    }

    static void hw3(String cmd1, String cmd2) {
        int l = cmd2.length();
        String cut = cmd1;
        while (true) {
            int start = cut.indexOf(cmd2);
            if (start < 0) break;
            String arg = cut.substring(0, start);
            if(arg.length()>0) System.out.print(arg + " ");
            cut = cut.substring(start + l);
        }
        System.out.println(cut);
    }
}
