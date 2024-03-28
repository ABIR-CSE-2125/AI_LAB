package asg1;

import java.util.*;

public class HillClimb {

    static int BIT_LENGTH = 6;

    String encoded(int num) {

        String temp = Integer.toBinaryString(num);
        String padding = "";
        for (int i = 0; i < BIT_LENGTH - temp.length(); i++) {
            padding += "0";
        }

        return padding + temp;
    }

    int decode(String bin) {
        return Integer.parseInt(bin, 2);
    }

    String bitFlipper(String str, int index) {
        char temp[] = str.toCharArray();
        if (temp[BIT_LENGTH - 1 - index] == '0') {
            temp[BIT_LENGTH - 1 - index] = '1';
        } else {
            temp[BIT_LENGTH - 1 - index] = '0';
        }
        String res = String.valueOf(temp);
        // System.out.println(res);
        return res;
    }

    double evalFunc(int x) {
        return x * Math.cos(x / 2);
        // return x * x;
    }

    double MAX = Integer.MIN_VALUE;

    double maximiseWithFlip(int initVal) {
        double initFunc = evalFunc(initVal);
        PriorityQueue<Double> open = new PriorityQueue<>(Comparator.reverseOrder());// Keeps track of the best sol
        Map<Double, Integer> yTox = new HashMap<>();// Keeps track of y => x relation
        Set<Integer> visitedSet = new HashSet<>();// Stores the already exanded x values;

        // Initialising everything

        visitedSet.add(initVal);
        yTox.put(initFunc, initVal);
        open.add(initFunc);
        int iter = 0;
        while (MAX < open.peek()) {
            System.out.println("Iter : " + (iter++));
            MAX = open.poll();
            System.out.println("MAX : " + MAX);
            open.clear();
            int x = yTox.get(MAX);
            System.out.println("To Be explored -> X : " + x);
            yTox.clear();

            for (int i = 0; i < BIT_LENGTH; i++) {
                System.out.println("flipping at index : " + i);
                String tempString = encoded(x);
                // Random r = new Random();
                // int step = r.nextInt(100) % 47;
                // step = (step) % BIT_LENGTH;
                // System.out.println("Random flip Index : " + step);
                int step = i;
                tempString = bitFlipper(tempString, step);
                System.out.println("New Binary String " + tempString);
                int temp = decode(tempString);
                System.out.println("New Number " + temp);
                if (temp >= 50)
                    continue;
                double testVal = evalFunc(temp);
                System.out.println("Evaluated Value : " + testVal);
                yTox.put(testVal, temp);
                open.add(testVal);
            }
            // if (visitedSet.contains(x))
            // break;
            // else
            // visitedSet.add(x);
        }
        return MAX;
    }

    public static void main(String[] args) {
        HillClimb hc = new HillClimb();

        System.out.println(

                hc.maximiseWithFlip(32));
    }

}