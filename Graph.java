package asg1;

import java.util.*;
// import javafx.util.Pair;

public class Graph {
    List<List<Integer>> adjList;
    int numberOfNodes;
    int edgeCount;

    public void createGraph(int numberOfNodes, List<List<Integer>> edges) {
        // edges contains the pair of nodes !!!
        adjList = new ArrayList<>();

        for (int i = 0; i < numberOfNodes + 1; i++) {
            adjList.add(new ArrayList<>());
        }

        for (List<Integer> li : edges) {
            int src = li.get(0);
            int dest = li.get(1);
            adjList.get(src).add(dest);
            adjList.get(dest).add(src);
        }
    }

    Graph() {
        Scanner sc = new Scanner(System.in);
        List<List<Integer>> edges = new ArrayList<>();
        System.out.println("ENTER THE NUMBER OF NODES");
        this.numberOfNodes = sc.nextInt();
        System.out.println("ENTER THE NUMBER OF Edges");
        this.edgeCount = sc.nextInt();
        System.out.println("ENTER THE EDGE SEQUENCE : <SRC,DEST>");
        sc.nextLine();
        do {
            System.out.println("<_,_>");
            String seq = sc.nextLine();
            List<Integer> temp = new ArrayList<>();
            int src = Integer.parseInt(seq.substring(0, 2));
            int dest = Integer.parseInt(seq.substring(3, 5));
            temp.add(src);
            temp.add(dest);
            edges.add(temp);
            edgeCount--;
        } while (edgeCount > 0);

        // sc.close();
        createGraph(numberOfNodes, edges);
    }
}
