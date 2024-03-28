package asg1;

// import asg1.Graph;
// import asg1.BFS;
// import asg1.DFS;
import java.util.*;
// import asg1.DLS;
// import asg1.IDS;
// import asg1.IBS;

public class Project_Traverse {
    public static void main(String[] args) {
        Graph graph = new Graph();
        int visited[] = new int[graph.numberOfNodes + 1];
        Scanner sc = new Scanner(System.in);
        int trav;
        for (List<Integer> list : graph.adjList) {
            System.out.println(list);
        }
        // System.out.println("\nBFS TRAVERSAL : ");
        // BFS.bfsTrvaersal(graph);
        // System.out.println("\nPATH IN BFS : ");
        // BFS.solution_path_bfs(graph);
        // System.out.println("\nPATH IN DFS : ");
        // DFS.solution_path_dfs(graph);
        // System.out.println("\nDFS TRAVERSAL : ");
        // DFS.dfsTrvaersal(graph);
        System.out.println("\nPATH IN DLS : ");
        DLS.solution_path_dls(graph);
        // System.out.println("\nPATH IN IDS : ");
        // IDS._solution_path_(graph);

        // System.out.println("\nPATH IN IBS : ");
        // IBS._solution_path_(graph);
    }
}
