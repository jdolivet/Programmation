package warmup;

import java.util.HashSet;
import java.util.Set;

public class Quadratic {

    /**
     * Find the integer roots of a quadratic equation, ax^2 + bx + c = 0.
     * @param a coefficient of x^2
     * @param b coefficient of x
     * @param c constant term.  Requires that a, b, and c are not ALL zero.
     * @return all integers x such that ax^2 + bx + c = 0.
     */
    public static Set<Integer> roots(int a, int b, int c) {
        Set<Integer> solutions = new HashSet<Integer>();
        if (a == 0){
            if (c != 0 && (int)(- c / b) == - c / b){
            solutions.add(- c / b);
            }
            return solutions;
        }
        long delta = (long) b * (long) b - 4 * (long) a * (long) c;
        if (delta == 0){
            double sol = (-b)/(2 * a);
            if ((int) sol == sol){
                solutions.add((int) sol);
            }
        }
        if (delta > 0){
            double sol1 = (- b + Math.sqrt(delta))/(2 * a);
                if ((int) sol1 == sol1){
                    solutions.add((int) sol1);
                }      
            double sol2 = (- b - Math.sqrt(delta))/(2 * a);
                if ((int) sol2 == sol2){
                    solutions.add((int) sol2);
                } 
        }
        return solutions;
    }

    
    /**
     * Main function of program.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("For the equation x^2 - 4x + 3 = 0, the possible solutions are:");
        Set<Integer> result = roots(1, -4, 3);
        System.out.println(result);
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
