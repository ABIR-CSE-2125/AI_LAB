package asg1;

import asg1.Graph;
import java.util.*;

public class BFS {

    public static void traverse(Graph graph, int root, List<Integer> bfs, int[] visited) {
        Queue<Integer> q = new LinkedList<>();
        // int visited[] = new int[graph.numberOfNodes+1];
        // Arrays.fill(visited,0);
        q.offer(root);
        visited[root] = 1;
        while (!q.isEmpty()) {
            int temp = q.poll();
            // System.out.print(temp + "\t");
            bfs.add(temp);
            for (Integer i : graph.adjList.get(temp)) {
                if (visited[i] == 0) {
                    q.add(i);
                    visited[i] = 1;
                }
            }
        }
    }

    public static void bfsTrvaersal(Graph graph) {
        System.out.println("!!-- LET'S START BFS --!!");
        List<Integer> bfs = new ArrayList<>();
        int visited[] = new int[graph.numberOfNodes + 1];
        Scanner sc = new Scanner(System.in);
        int flag = 1;
        while (flag == 1) {
            System.out.println("!!-- ENTER THE STARTING POINT --!!");
            Arrays.fill(visited, 0);
            int root = sc.nextInt();
            traverse(graph, root, bfs, visited);
            System.out.println(bfs + " with root as : " + root);
            System.out.println("\nChange the root? 0/1");
            flag = sc.nextInt();
        }
    }

    public static void traverse_sol(Graph graph, int root, int target, List<Integer> bfs, int[] visited) {
        Queue<Integer> q = new LinkedList<>();

        q.offer(root);
        visited[root] = 1;
        while (!q.isEmpty()) {
            int temp = q.poll();
            bfs.add(temp);
            if (temp == target)
                return;
            for (Integer i : graph.adjList.get(temp)) {
                if (visited[i] == 0) {
                    q.add(i);
                    visited[i] = 1;
                }
            }
        }
    }

    public static void solution_path_bfs(Graph graph) {
        List<Integer> bfs = new ArrayList<>();
        int visited[] = new int[graph.numberOfNodes + 1];
        Arrays.fill(visited, 0);
        Scanner sc = new Scanner(System.in);
        int flag = 1;
        System.out.println("!!-- ENTER THE STARTING NODE --!!");
        System.out.println("!!-- ENTER THE TARGET NODE --!!");
        int root = sc.nextInt();
        int target = sc.nextInt();
        traverse_sol(graph, root, target, bfs, visited);

        List<Integer> res = new ArrayList<>();
        int key = target;
        visited[key] = 0;
        res.add(key);
        List<Integer> list = new ArrayList<>(graph.adjList.get(key));
        int t = 0;
        while (t < list.size()) {
            int i = list.get(t);
            res.add(i);
            if (i == root)
                break;
            if (visited[i] == 1) {
                for (int it = i; it < visited.length; it++) {
                    visited[it] = 0;
                }
                key = i;
                list.clear();
                list.addAll(graph.adjList.get(key));
            } else {
                t++;
            }

        }
        for (int i = res.size() - 1; i >= 0; i--) {
            System.err.print(res.get(i) + " -- ");
        }
    }

}
