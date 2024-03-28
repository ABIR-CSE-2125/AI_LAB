package asg1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import org.w3c.dom.Node;

public class informedWaterJug {
    static int cap1, cap2;
    static int tar;

    static class Pair {
        int first; /* First Jug */
        int second;/* Second Jug */
        int cost;
        int heuristic;

        Pair(int i, int j, int c) {
            first = i;
            second = j;
            cost += c;
            heuristic = 0;
        }

        Pair(int i, int j, int c, int h) {
            first = i;
            second = j;
            cost += c;
            heuristic = h;
        }

        Pair(int i, int j) {
            first = i;
            second = j;
            cost = 0;
            heuristic = 0;
        }

        @Override
        public String toString() {
            return "< " + first + " , " + second + " >";
        }

        @Override
        public boolean equals(Object obj) {
            Pair t = (Pair) obj;
            return this.first == t.first && this.second == t.second;
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }

    static class ComparatorUCS implements Comparator<Pair> {
        @Override
        public int compare(Pair n1, Pair n2) {
            return (n1.cost) - (n2.cost);
        }
    }

    static class ComparatorBEFS implements Comparator<Pair> {
        @Override
        public int compare(Pair n1, Pair n2) {
            return (n1.heuristic) - (n2.heuristic);
        }
    }

    static class Comparatorastar implements Comparator<Pair> {
        @Override
        public int compare(Pair n1, Pair n2) {
            return (n1.heuristic + n1.cost) - (n2.heuristic + n2.cost);
        }
    }

    static Pair fillJug1(Pair p) {
        p.first = cap1;
        p.cost += 3;

        return p;
    }

    static Pair fillJug2(Pair p) {
        p.second = cap2;
        p.cost += 3;
        return p;
    }

    static Pair transfer122(Pair p) {
        int dif = cap2 - p.second; // Calculate space available in jug 2
        if (p.first <= dif) { // If jug 1 can fit into jug 2
            p.second += p.first; // Pour all water from jug 1 to jug 2
            p.first = 0; // Empty jug 1
        } else { // If jug 1 cannot fit completely into jug 2
            p.first -= dif; // Pour water from jug 1 to fill jug 2
            p.second = cap2; // Fill jug 2 to its capacity
        }
        p.cost += 2;
        return p;
    }

    static Pair transfer221(Pair p) {
        int dif = cap1 - p.first; // Calculate space available in jug 1
        if (p.second <= dif) { // If jug 2 can fit into jug 1
            p.first += p.second; // Pour all water from jug 2 to jug 1
            p.second = 0; // Empty jug 2
        } else { // If jug 2 cannot fit completely into jug 1
            p.second -= dif; // Pour water from jug 2 to fill jug 1
            p.first = cap1; // Fill jug 1 to its capacity
        }
        p.cost += 2;
        return p;
    }

    static Pair emptyJug1(Pair p) {
        p.first = 0;
        p.cost += 1;
        return p;
    }

    static Pair emptyJug2(Pair p) {
        p.second = 0;
        p.cost += 1;
        return p;
    }

    void printPath(Map<Pair, Pair> parentMap, Pair target, List<Pair> path) {
        // System.out.println(target);
        // System.out.println(parentMap);
        while (!(parentMap.get(target)).equals(new Pair(-1, -1, -1))) {
            // if (!path.contains(target)) {
            path.add(target);
            Pair parentState = parentMap.get(target);
            target = parentState;
            // }
        }
        Pair absoluteParent = new Pair(0, 0);
        // if (!path.contains(absoluteParent)) {
        path.add(new Pair(0, 0));
        // }
        Collections.reverse(path);
    }

    void ucs(int cap1, int cap2, int target, Set<Pair> visited, List<Pair> path) {
        Map<Pair, Pair> parentMap = new HashMap<>();
        if (tar > Math.max(cap1, cap2)) {
            return;
        }
        PriorityQueue<Pair> q = new PriorityQueue<>(new ComparatorUCS());
        Pair init = new Pair(0, 0);
        q.offer(init);
        parentMap.put(init, new Pair(-1, -1, -1));
        visited.add(init);
        // int i = 6;
        while (!q.isEmpty()) {
            Pair temp = q.poll();
            System.out
                    .println("======================================================================================");
            System.out.println("Current Element : " + temp + " f(x) : " + temp.cost);
            Pair[] open = new Pair[6];
            open[0] = fillJug1(new Pair(temp.first, temp.second, temp.cost));
            open[1] = fillJug2(new Pair(temp.first, temp.second, temp.cost));
            open[2] = transfer122(new Pair(temp.first, temp.second, temp.cost));
            open[3] = transfer221(new Pair(temp.first, temp.second, temp.cost));
            open[4] = emptyJug1(new Pair(temp.first, temp.second, temp.cost));
            open[5] = emptyJug1(new Pair(temp.first, temp.second, temp.cost));

            for (int i = 0; i < open.length; i++) {
                if (target == temp.first || target == temp.second) {
                    // parentMap.put(open[i], temp);
                    printPath(parentMap, temp, path);
                    return;
                }
            }
            // If the pair has not been visited and the pair is valid then add them in to
            // the queue
            for (int i = 0; i < open.length; i++) {
                if (!visited.contains(open[i])) {
                    parentMap.put(open[i], temp);
                    q.offer(open[i]);
                    visited.add(open[i]);
                }
            }
        }
    }

    void befs(int cap1, int cap2, int target, Set<Pair> visited, List<Pair> path) {
        Map<Pair, Pair> parentMap = new HashMap<>();
        if (tar > Math.max(cap1, cap2)) {
            return;
        }
        PriorityQueue<Pair> q = new PriorityQueue<>(new ComparatorBEFS());
        Pair init = new Pair(0, 0);
        init.heuristic = tar;
        q.offer(init);
        parentMap.put(init, new Pair(-1, -1, -1));
        visited.add(init);
        // int i = 6;
        while (!q.isEmpty()) {
            Pair temp = q.poll();
            System.out
                    .println("======================================================================================");
            System.out.println("Current Element : " + temp + " f(x) : " + temp.heuristic);
            Pair[] open = new Pair[6];
            open[0] = fillJug1(new Pair(temp.first, temp.second, temp.cost));
            open[1] = fillJug2(new Pair(temp.first, temp.second, temp.cost));
            open[2] = transfer122(new Pair(temp.first, temp.second, temp.cost));
            open[3] = transfer221(new Pair(temp.first, temp.second, temp.cost));
            open[4] = emptyJug1(new Pair(temp.first, temp.second, temp.cost));
            open[5] = emptyJug1(new Pair(temp.first, temp.second, temp.cost));
            for (int i = 0; i < open.length; i++) {
                if (target == temp.first || target == temp.second) {
                    // parentMap.put(open[i], temp);
                    printPath(parentMap, temp, path);
                    return;
                }
            }
            // If the pair has not been visited and the pair is valid then add them in to
            // the queue
            for (int i = 0; i < open.length; i++) {
                int h1 = Math.abs(tar - open[i].first);
                int h2 = Math.abs(tar - open[i].second);
                int h = Math.min(h1, h2);
                open[i].heuristic = h;
            }

            for (int i = 0; i < open.length; i++) {
                if (!visited.contains(open[i])) {
                    parentMap.put(open[i], temp);
                    q.offer(open[i]);
                    visited.add(open[i]);
                }
            }
        }
    }

    void aStar(int cap1, int cap2, int target, Set<Pair> visited, List<Pair> path) {
        Map<Pair, Pair> parentMap = new HashMap<>();
        if (tar > Math.max(cap1, cap2)) {
            return;
        }
        PriorityQueue<Pair> q = new PriorityQueue<>(new Comparatorastar());
        Pair init = new Pair(0, 0);
        init.heuristic = tar;
        q.offer(init);
        parentMap.put(init, new Pair(-1, -1, -1));
        visited.add(init);
        // int i = 6;
        while (!q.isEmpty()) {
            Pair temp = q.poll();
            System.out
                    .println("======================================================================================");
            System.out.println("Current Element : " + temp + " f(x) : " + (temp.heuristic + temp.cost));
            Pair[] open = new Pair[6];
            open[0] = fillJug1(new Pair(temp.first, temp.second, temp.cost));
            open[1] = fillJug2(new Pair(temp.first, temp.second, temp.cost));
            open[2] = transfer122(new Pair(temp.first, temp.second, temp.cost));
            open[3] = transfer221(new Pair(temp.first, temp.second, temp.cost));
            open[4] = emptyJug1(new Pair(temp.first, temp.second, temp.cost));
            open[5] = emptyJug1(new Pair(temp.first, temp.second, temp.cost));
            for (int i = 0; i < open.length; i++) {
                if (target == temp.first || target == temp.second) {
                    // parentMap.put(open[i], temp);
                    printPath(parentMap, temp, path);
                    return;
                }
            }
            // If the pair has not been visited and the pair is valid then add them in to
            // the queue
            for (int i = 0; i < open.length; i++) {
                int h1 = Math.abs(tar - open[i].first);
                int h2 = Math.abs(tar - open[i].second);
                int h = Math.min(h1, h2);
                open[i].heuristic = h;
            }

            for (int i = 0; i < open.length; i++) {
                if (!visited.contains(open[i])) {
                    parentMap.put(open[i], temp);
                    q.offer(open[i]);
                    visited.add(open[i]);
                }
            }
        }
    }

    public static void main(String[] args) {
        informedWaterJug iwg = new informedWaterJug();
        Set<Pair> visitedSet = new HashSet<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Capacity of First Jug : ");
        cap1 = sc.nextInt();
        System.out.println("Capacity of Second Jug : ");
        cap2 = sc.nextInt();
        System.out.println("Target Measurement : ");
        tar = sc.nextInt();
        System.out.println("Solution Path in UCS");
        List<Pair> pathUCS = new ArrayList<>();
        iwg.ucs(cap1, cap2, tar, visitedSet, pathUCS);
        System.out.println("The path for Uniform Cost Search : " + pathUCS);

        System.out.println("Solution Path in BEFS");
        visitedSet.clear();
        List<Pair> pathBEFS = new ArrayList<>();
        iwg.befs(cap1, cap2, tar, visitedSet, pathBEFS);
        System.out.println("The path for Greedy Best First Search : " + pathBEFS);
        System.out.println("Solution Path in A star ");
        visitedSet.clear();
        List<Pair> pathA = new ArrayList<>();
        iwg.aStar(cap1, cap2, tar, visitedSet, pathA);
        System.out.println("The path for A star Search : " + pathA);

    }

}
