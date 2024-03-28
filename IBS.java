package asg1;

/**
 * IBS
 */
import java.util.*;

public class IBS {
    static int isPresent;
    static Stack<Integer> st;

    public static void traverse_sol(Graph graph, int root, int target, int[] visited,
            Stack<Integer> stack, int breadthCutoff) {

        int local = breadthCutoff;
        visited[root] = 1;
        stack.push(root);
        if (root == target) {
            isPresent = 1;
        }
        for (int i : graph.adjList.get(root)) {
            if (visited[i] != 1 && local > 0 && isPresent == 0) {
                traverse_sol(graph, i, target, visited, stack, breadthCutoff);
                local--;
            }
        }
    }

    public static void _solution_path_(Graph graph) {
        int visited[] = new int[graph.numberOfNodes + 1];
        Scanner sc = new Scanner(System.in);
        List<Integer> dfs = new ArrayList<>();
        Arrays.fill(visited, 0);
        System.out.println("!!-- ENTER THE STARTING NODE --!!");
        int root = sc.nextInt();
        System.out.println("!!-- ENTER THE TARGET NODE --!!");
        int target = sc.nextInt();
        // System.out.println("!!-- ENTER THE MAX DEPTH --!!");
        // int maxDepth = sc.nextInt();

        isPresent = 0;
        int breadthCutoff = 0;
        st = new Stack<>();
        while (isPresent == 0) {
            traverse_sol(graph, root, target, visited, st, breadthCutoff++);
            for (Integer elem : st) {
                dfs.add(elem);
            }
            System.out.println("b = " + (breadthCutoff - 1) + " Order : " + dfs);
            Arrays.fill(visited, 0);
            dfs.clear();
            st.clear();
        }
        if (isPresent == 0) {
            System.out.println("!!!-- NO SUCH ELEMENT FOUND --!!!");
            return;
        }
        // System.out.println("b : " + breadthCutoff);
    }
}