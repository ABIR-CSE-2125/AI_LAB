package asg1;

import java.util.*;

public class DLS {
    static int isPresent;

    public static void traverse_sol(Graph graph, int root, int target, int lvl, int[] visited,
            Stack<Integer> stack) {

        if (lvl < 0)
            return;
        visited[root] = 1;
        stack.push(root);
        if (root == target) {
            isPresent = 1;
            for (Integer elem : stack) {
                System.out.print(elem + " -- ");
            }
            System.out.println();
        }
        for (int i : graph.adjList.get(root)) {
            if (visited[i] != 1)
                traverse_sol(graph, i, target, lvl - 1, visited, stack);
        }
        // int i = stack.pop();
    }

    public static void solution_path_dls(Graph graph) {
        int visited[] = new int[graph.numberOfNodes + 1];
        Scanner sc = new Scanner(System.in);
        List<Integer> dfs = new ArrayList<>();
        Arrays.fill(visited, 0);
        System.out.println("!!-- ENTER THE STARTING NODE --!!");
        int root = sc.nextInt();
        System.out.println("!!-- ENTER THE TARGET NODE --!!");
        int target = sc.nextInt();
        System.out.println("!!--ENTER DEPTH LEVEL--!!");
        int level = sc.nextInt();
        isPresent = 0;
        traverse_sol(graph, root, target, level, visited, new Stack<>());
        if (isPresent == 0)
            System.out.println("!!!-- NO SUCH ELEMENT FOUND --!!!");
    }
}
