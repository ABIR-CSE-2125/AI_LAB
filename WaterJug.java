package asg1;

import java.nio.file.Path;
import java.text.ParseException;
import java.util.*;

public class WaterJug {
    static int cap1, cap2;
    static int tar;
    static boolean isPresent_dfs;
    static boolean isPresent_ids;
    static boolean isPresent_ibs;

    static class Pair {
        int first; /* First Jug */
        int second;/* Second Jug */

        Pair(int i, int j) {
            first = i;
            second = j;
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

    static Pair fillJug1(Pair p) {
        p.first = cap1;
        return p;
    }

    static Pair fillJug2(Pair p) {
        p.second = cap2;
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
        return p;
    }

    static Pair emptyJug1(Pair p) {
        p.first = 0;
        return p;
    }

    static Pair emptyJug2(Pair p) {
        p.second = 0;
        return p;
    }

    void printPath(Map<Pair, Pair> parentMap, Pair target, List<Pair> path) {
        // System.out.println(target);
        // System.out.println(parentMap);
        while (!(parentMap.get(target)).equals(new Pair(-1, -1))) {
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

    void bfs(int cap1, int cap2, int target, Set<Pair> visited, List<Pair> path) {
        Map<Pair, Pair> parentMap = new HashMap<>();
        if (tar > Math.max(cap1, cap2)) {
            return;
        }
        Queue<Pair> q = new LinkedList<>();
        Pair init = new Pair(0, 0);
        q.offer(init);
        parentMap.put(init, new Pair(-1, -1));
        visited.add(init);
        // int i = 6;
        while (!q.isEmpty()) {
            Pair temp = q.poll();
            System.out
                    .println("======================================================================================");
            System.out.println("Current Element : " + temp);
            Pair[] open = new Pair[6];
            open[0] = fillJug1(new Pair(temp.first, temp.second));
            open[1] = fillJug2(new Pair(temp.first, temp.second));
            open[2] = transfer122(new Pair(temp.first, temp.second));
            open[3] = transfer221(new Pair(temp.first, temp.second));
            open[4] = emptyJug1(new Pair(temp.first, temp.second));
            open[5] = emptyJug1(new Pair(temp.first, temp.second));
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

    void dfs(Pair root, int cap1, int cap2, int target, Set<Pair> visited, Stack<Pair> st) {
        st.push(root);
        if (root.first == target || root.second == target) {
            isPresent_dfs = true;
            return;
        }
        visited.add(root);
        Pair[] open = new Pair[6];
        open[0] = fillJug1(new Pair(root.first, root.second));
        open[1] = fillJug2(new Pair(root.first, root.second));
        open[2] = transfer122(new Pair(root.first, root.second));
        open[3] = transfer221(new Pair(root.first, root.second));
        open[4] = emptyJug1(new Pair(root.first, root.second));
        open[5] = emptyJug1(new Pair(root.first, root.second));
        for (int i = 0; i < open.length; i++) {
            if (!visited.contains(open[i])) {
                System.out.println("Pair : " + open[i] + "is expanded");
                dfs(open[i], cap1, cap2, target, visited, st);
            }
        }
        if (isPresent_dfs == false) {
            Pair p = st.pop();
        }
    }

    void printPathIDS(Map<Pair, Pair> parentMap, Pair target, List<Pair> path) {
        // System.out.println("Current target : " + target);
        // System.out.println(parentMap);
        while (!(parentMap.get(target)).equals(new Pair(-1, -1))) {
            if (!path.contains(target)) {
                path.add(target);
            }
            target = parentMap.get(target);
        }
        Pair absoluteParent = new Pair(0, 0);
        if (!path.contains(absoluteParent)) {
            path.add(absoluteParent);
        }
    }

    // static Pair targetPair;

    void ids(Pair root, int cap1, int cap2, int target, int lvl, Set<Pair> visited, Stack<Pair> st,
            Map<Pair, Pair> parentMap, List<Pair> path) {
        // System.out.println(parentMap);
        st.push(root);
        visited.add(root);
        if (lvl == 0) {
            if (root.first == target || root.second == target) {
                isPresent_ids = true;
                printPathIDS(parentMap, root, path);
            }
            // System.out.println("and returned by lvl 0");
            return;
        }
        if (root.first == target || root.second == target) {
            isPresent_ids = true;
            printPathIDS(parentMap, root, path);
            return;
        }
        Pair[] open = new Pair[6];
        open[0] = fillJug1(new Pair(root.first, root.second));
        open[1] = fillJug2(new Pair(root.first, root.second));
        open[2] = transfer122(new Pair(root.first, root.second));
        open[3] = transfer221(new Pair(root.first, root.second));
        open[4] = emptyJug1(new Pair(root.first, root.second));
        open[5] = emptyJug1(new Pair(root.first, root.second));

        for (int i = 0; i < open.length; i++) {
            if (!visited.contains(open[i])) {
                parentMap.put(open[i], root);
                System.out.println("Pair : " + open[i] + "is expanded");
                ids(open[i], cap1, cap2, target, lvl - 1, visited, st, parentMap, path);
            }
        }
    }

    void printPathIBS(Map<Pair, Pair> parentMap, Pair target, List<Pair> path) {
        // System.out.println("Current target : " + target);
        // System.out.println(parentMap);
        while (!(parentMap.get(target)).equals(new Pair(-1, -1))) {
            if (!path.contains(target)) {
                path.add(target);
            }
            target = parentMap.get(target);
        }
        Pair absoluteParent = new Pair(0, 0);
        if (!path.contains(absoluteParent)) {
            path.add(absoluteParent);
        }
    }

    void ibs(Pair root, int cap1, int cap2, int target, int breadthCutoff, Set<Pair> visited,
            Stack<Pair> st,
            Map<Pair, Pair> parentMap, List<Pair> path) {

        int local = breadthCutoff;
        st.push(root);
        visited.add(root);
        if (root.first == target || root.second == target) {
            System.out.println("Found the target");
            isPresent_ibs = true;
            printPathIBS(parentMap, root, path);
            return;
        }
        Pair[] open = new Pair[6];
        open[0] = fillJug1(new Pair(root.first, root.second));
        open[1] = fillJug2(new Pair(root.first, root.second));
        open[2] = transfer122(new Pair(root.first, root.second));
        open[3] = transfer221(new Pair(root.first, root.second));
        open[4] = emptyJug1(new Pair(root.first, root.second));
        open[5] = emptyJug1(new Pair(root.first, root.second));
        for (int i = 0; i < open.length; i++) {
            if (!visited.contains(open[i]) && local > 0 && isPresent_ibs == false) {
                parentMap.put(open[i], root);
                System.out.println("Pair : " + open[i] + "is expanded");
                ibs(open[i], cap1, cap2, target, breadthCutoff, visited, st, parentMap, path);
                if (isPresent_ibs == true)
                    break;
                local--;
            }
        }
    }

    public static void main(String[] args) {
        WaterJug wt = new WaterJug();
        Set<Pair> visitedSet = new HashSet<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Capacity of First Jug : ");
        cap1 = sc.nextInt();
        System.out.println("Capacity of Second Jug : ");
        cap2 = sc.nextInt();
        System.out.println("Target Measurement : ");
        tar = sc.nextInt();
        // // BFS----------------------------------------------------
        System.out.println("Solution Path in BFS");
        List<Pair> path = new ArrayList<>();
        wt.bfs(cap1, cap2, tar, visitedSet, path);
        System.out.println("The path for bfs : " + path);
        // // DFS -------------------------------------------------------
        System.out.println("Solution Path in DFS");
        isPresent_dfs = false;
        visitedSet.clear();
        Stack<Pair> st = new Stack<>();
        wt.dfs(new Pair(0, 0), cap1, cap2, tar, visitedSet, st);
        List<Pair> list = new LinkedList<>();
        System.out.print("The path for dfs : ");
        if (isPresent_dfs == true) {
            for (Pair pair : st) {
                if (pair.first == tar || pair.second == tar) {
                    System.out.println(pair + "!!");
                    break;
                }
                System.out.print(pair + " --> ");
            }
        }
        // IDS
        // ---------------------------------------------------------------------
        System.out.println("Solution Path in IDS");
        int level = 0;
        visitedSet.clear();
        isPresent_ids = false;
        List<Pair> ids_path = new ArrayList<>();
        // Set<Pair> ids_path = new HashSet<>();
        Stack<Pair> ids_stack = new Stack<>();
        Map<Pair, Pair> parentMapIDS = new HashMap<>();
        while (true) {
            Pair init = new Pair(0, 0);
            parentMapIDS.put(init, new Pair(-1, -1));
            System.out.println("Iter : " + (level));
            wt.ids(init, cap1, cap2, tar, level++, visitedSet, ids_stack, parentMapIDS,
                    ids_path);
            if (isPresent_ids == true) {
                Collections.reverse(ids_path);
                System.out.println("Solution Path : in iter : " + (level - 1) + " : " +
                        ids_path);
                break;
            }
            System.out.println("d = " + (level - 1) + " traversal : " + ids_stack);
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            visitedSet.clear();
            ids_path.clear();
            ids_stack.clear();
            parentMapIDS.clear();
        }
        // //
        // IBS-----------------------------------------------------------------------------------
        System.out.println("Solution Path in IBS : ");
        isPresent_ibs = false;
        int breadthCutoff = 0;
        List<Pair> ibs_path = new ArrayList<>();
        Stack<Pair> ibs_stack = new Stack<>();
        Map<Pair, Pair> parentMapIBS = new HashMap<>();
        while (true) {
            Pair init = new Pair(0, 0);
            parentMapIBS.put(init, new Pair(-1, -1));
            wt.ibs(init, cap1, cap2, tar, breadthCutoff++, visitedSet, ibs_stack,
                    parentMapIBS, ibs_path);
            if (isPresent_ibs == true) {
                Collections.reverse(ibs_path);
                System.out.println("Solution Path : in iter : " + (breadthCutoff - 1) + " : "
                        + ibs_path);
                break;
            }
            System.out.println("b = " + (breadthCutoff - 1) + " traversal : " +
                    ibs_stack);
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            visitedSet.clear();
            ibs_path.clear();
            ibs_stack.clear();
            parentMapIBS.clear();
        }
    }
}
