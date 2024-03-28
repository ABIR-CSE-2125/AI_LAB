package asg1;

// import asg1.Graph;
import java.util.*;

public class DFS {
    public static void traverse(List<Integer> list, Graph graph, int root, int[] visited) {
        visited[root] = 1;
        list.add(root);
        for (int i : graph.adjList.get(root)) {
            if (visited[i] != 1)
                traverse(list, graph, i, visited);
        }
    }

    public static void dfsTrvaersal(Graph graph) {
        System.out.println("!!-- LET'S START DFS --!!");
        // Graph graph = new Graph();
        int visited[] = new int[graph.numberOfNodes + 1];
        Scanner sc = new Scanner(System.in);
        int flag = 1;
        while (flag == 1) {
            // if(sc.hasNextInt()){
            List<Integer> dfs = new ArrayList<>();
            Arrays.fill(visited, 0);
            System.out.println("!!-- ENTER THE STARTING POINT --!!");
            int root = sc.nextInt();
            traverse(dfs, graph, root, visited);
            System.out.println(dfs + " with root as : " + root);
            System.out.println("\nChange the root? 0/1");
            flag = sc.nextInt();
        }
    }

    public static void traverse_sol(Graph graph, int root, int target, int[] visited,
            Stack<Integer> stack) {
        visited[root] = 1;
        stack.push(root);
        if (root == target) {
            for (Integer elem : stack) {
                System.out.print(elem + " -- ");
            }
            System.out.println();
        }
        for (int i : graph.adjList.get(root)) {
            if (visited[i] != 1)
                traverse_sol(graph, i, target, visited, stack);
        }
        int i = stack.pop();
    }

    public static void solution_path_dfs(Graph graph) {
        int visited[] = new int[graph.numberOfNodes + 1];
        Scanner sc = new Scanner(System.in);
        List<Integer> dfs = new ArrayList<>();
        Arrays.fill(visited, 0);
        System.out.println("!!-- ENTER THE STARTING NODE --!!");
        System.out.println("!!-- ENTER THE TARGET NODE --!!");
        int root = sc.nextInt();
        int target = sc.nextInt();
        traverse_sol(graph, root, target, visited, new Stack<>());
    }
}