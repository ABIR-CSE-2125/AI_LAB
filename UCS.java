package asg1;

// import java.lang.reflect.WildcardType;
import java.util.*;

public class UCS {
    public void uniformCostSearch(WeightedGraph wg, int start, Set<Integer> goal, List<List<WeightedGraph.Edge>> adj) {
        // System.out.println("Size of adjList" + adj.size());
        PriorityQueue<WeightedGraph.Node> pq = new PriorityQueue<>(new WeightedGraph.NodeComparatorUCS());
        pq.add(new WeightedGraph.Node(start, 0));
        boolean[] visited = new boolean[wg.edgeCount + 2];
        Arrays.fill(visited, false);
        int[] parent = new int[wg.edgeCount + 2];
        Arrays.fill(parent, -1);
        Map<Integer, Integer> goalCost = new HashMap<>();
        while (!pq.isEmpty()) {
            WeightedGraph.Node currentNode = pq.poll();
            int u = currentNode.vertex;
            // System.out.println(u);

            if (visited[u])
                continue;

            visited[u] = true;

            if (goal.contains(u)) {
                goalCost.put(u, currentNode.cost);
            }

            for (WeightedGraph.Edge edge : adj.get(u)) {
                if (edge.isNul()) {
                    continue;
                }
                int v = edge.destination;
                int weight = edge.weight;
                if (!visited[v]) {
                    int newCost = currentNode.cost + weight;
                    pq.add(new WeightedGraph.Node(v, newCost));
                    parent[v] = u;
                }
            }
        }
        int minGoal = Integer.MAX_VALUE, minNode = 0;
        for (Map.Entry<Integer, Integer> entry : goalCost.entrySet()) {
            if (entry.getValue() < minGoal) {
                minGoal = entry.getValue();
                minNode = entry.getKey();
            }
        }
        printPath(parent, minNode, minGoal);
    }

    private void printPath(int[] parent, int goal, int gc) {
        List<Integer> path = new ArrayList<>();
        int current = goal;
        while (current != -1) {
            path.add(current);
            current = parent[current];
        }
        Collections.reverse(path);
        System.out.println("Path from start to goal: " + path + " with cost " + gc);
    }

    public void operate() {

        Scanner sc = new Scanner(System.in);

        // System.out.println("Enter the number of edges : ");
        // int edgeCount = sc.nextInt();
        WeightedGraph wg = new WeightedGraph(9);

        // for (int i = 1; i < edgeCount + 2; i++) {
        // // System.out.println(i);
        // wg.addEdge(i, -1, -1);
        // }

        // for (int i = 0; i < edgeCount; i++) {
        // int src, dst, cost;
        // System.out.println("Enter source,destination,weight");
        // src = sc.nextInt();
        // dst = sc.nextInt();
        // cost = sc.nextInt();
        // WeightedGraph.Edge temp = wg.adjList.get(src).get(wg.adjList.get(src).size()
        // - 1);
        // if (temp.isNul()) {
        // wg.adjList.get(src).clear();
        // }
        // wg.addEdge(src, dst, cost);
        // }
        wg.addEdge(1, 2, 1);
        wg.addEdge(1, 3, 2);
        wg.addEdge(2, 4, 2);
        wg.addEdge(2, 5, 7);
        wg.addEdge(3, 6, 4);
        wg.addEdge(3, 7, 3);
        wg.addEdge(5, 8, 1);
        wg.addEdge(6, 9, 3);
        wg.addEdge(9, 10, 4);
        wg.addEdge(4, -1, -1);
        wg.addEdge(7, -1, -1);
        wg.addEdge(10, -1, -1);
        for (List<WeightedGraph.Edge> il : wg.getAdjList()) {
            for (WeightedGraph.Edge edge : il) {
                System.out.println(edge);
            }
        }
        System.out.println("Enter start Node : ");
        int start = sc.nextInt();
        System.out.println("Enter Number of Goal Nodes : ");
        int goalNOdeNo = sc.nextInt();
        Set<Integer> goal = new HashSet<>();
        for (int i = 0; i < goalNOdeNo; i++) {
            int temp = sc.nextInt();
            goal.add(temp);
        }
        uniformCostSearch(wg, start, goal, wg.getAdjList());
    }

    public static void main(String[] args) {
        UCS sa = new UCS();
        sa.operate();

    }
}
