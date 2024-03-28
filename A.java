package asg1;

import java.util.*;

public class A {
    public void aStarSearch(WeightedGraph wg, int[] heuristic, int start, Set<Integer> goal,
            List<List<WeightedGraph.Edge>> adj) {
        // System.out.println("Size of adjList" + adj.size());
        PriorityQueue<WeightedGraph.Node> pq = new PriorityQueue<>(new WeightedGraph.NodeComparatorA());
        pq.add(new WeightedGraph.Node(start, 0, heuristic[start]));
        boolean[] visited = new boolean[wg.edgeCount + 2];
        Arrays.fill(visited, false);
        int[] parent = new int[wg.edgeCount + 2];
        Arrays.fill(parent, -1);
        int g = 0;
        // Map<Integer, Integer> goalCost = new HashMap<>();
        while (!pq.isEmpty()) {
            WeightedGraph.Node currentNode = pq.poll();
            int u = currentNode.vertex;
            // System.out.println(u);

            if (visited[u])
                continue;

            visited[u] = true;

            if (goal.contains(u)) {
                // goalCost.put(u, currentNode.cost + heuristic[u]);
                g = u;
            }

            for (WeightedGraph.Edge edge : adj.get(u)) {
                if (edge.isNul()) {
                    continue;
                }
                int v = edge.destination;
                int weight = edge.weight;
                if (!visited[v]) {
                    // System.out.println(
                    // "Node : " + currentNode.vertex + " f(x) : " + (currentNode.cost +
                    // currentNode.heuristic));
                    int newCost = currentNode.cost + weight;
                    System.out.println("Node : " + v + " f(x) : " + (newCost + currentNode.heuristic));
                    pq.add(new WeightedGraph.Node(v, newCost, heuristic[v]));
                    parent[v] = u;
                }
            }
        }
        // int minGoal = Integer.MAX_VALUE, minNode = 0;
        // for (Map.Entry<Integer, Integer> entry : goalCost.entrySet()) {
        // System.out.println("Goal : " + entry.getKey() + " cost : " +
        // entry.getValue());
        // if (entry.getValue() < minGoal) {
        // minGoal = entry.getValue();
        // minNode = entry.getKey();
        // }
        // }
        printPath(parent, g);
    }

    private void printPath(int[] parent, int goal) {
        List<Integer> path = new ArrayList<>();
        int current = goal;
        while (current != -1) {
            path.add(current);
            current = parent[current];
        }
        Collections.reverse(path);
        System.out.println("Path from start to goal: " + path);
    }

    public void operate() {
        Scanner sc = new Scanner(System.in);
        // System.out.println("Enter the number of edges : ");
        // int edgeCount = sc.nextInt();
        WeightedGraph wg = new WeightedGraph(9);
        // System.out.println("Enter the number of node : ");
        // int nodeCount = sc.nextInt();
        // for (int i = 1; i < edgeCount + 2; i++) {
        // // System.out.println(i);
        // wg.addEdge(i, -1, -1);
        // }
        int heuristic[] = new int[10 + 1];
        // for (int i = 1; i < nodeCount + 1; i++) {
        // System.out.println("Enter the heuristic value for node: " + i);
        // heuristic[i] = sc.nextInt();
        // }
        // hardcode --------------------------
        heuristic[1] = 20;
        heuristic[2] = 15;
        heuristic[3] = 10;
        heuristic[4] = 6;
        heuristic[5] = 7;
        heuristic[6] = 2;
        heuristic[7] = 4;
        heuristic[8] = 0;
        heuristic[9] = 0;
        heuristic[10] = 3;

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
            System.out.println("Node : ");
            int temp = sc.nextInt();
            goal.add(temp);
        }
        aStarSearch(wg, heuristic, start, goal, wg.getAdjList());
    }

    public static void main(String[] args) {
        A sa = new A();
        sa.operate();
    }
}
